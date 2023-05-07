package com.github.akagawatsurunaki.novappro.service.stu;

import cn.hutool.core.collection.CollectionUtil;
import com.github.akagawatsurunaki.novappro.mapper.CourseApplicationMapper;
import com.github.akagawatsurunaki.novappro.mapper.CourseMapper;
import com.github.akagawatsurunaki.novappro.model.database.approval.CourseApplication;
import com.github.akagawatsurunaki.novappro.model.database.course.Course;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import com.github.akagawatsurunaki.novappro.util.CourseUtil;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseApplDetailService {

    @Getter
    private static final CourseApplDetailService instance = new CourseApplDetailService();

    public Pair<ServiceMessage, List<Course>> getAppliedCourses(@Nullable String flowNo){
        if (flowNo == null || flowNo.isBlank()) {
            return new ImmutablePair<>(
                    ServiceMessage.of(ServiceMessage.Level.WARN, "流水号不能为空"),
                    new ArrayList<>()
            );
        }

        return _getAppliedCourses(flowNo);

    }

    public Pair<ServiceMessage, List<Course>> _getAppliedCourses(@NonNull String flowNo) {

        try (var session = MyDb.use().openSession(true)) {

            val courseApplicationMapper = session.getMapper(CourseApplicationMapper.class);
            val courseMapper = session.getMapper(CourseMapper.class);

            var courseAppl = courseApplicationMapper.selectByFlowNo(flowNo);

            if (courseAppl == null) {
                return new ImmutablePair<>(
                        ServiceMessage.of(ServiceMessage.Level.ERROR, "流水号为"+flowNo+"的课程申请对象(Course Application)不存在"),
                        new ArrayList<>()
                );
            }

            var courseCodeList = courseAppl.getApproCourses();
            assert courseCodeList != null && !courseCodeList.isBlank();

            List<String> courseCodes = CourseUtil.getCourseCodes(courseCodeList);
            assert courseCodes!=null && !courseCodes.isEmpty();

            val result = courseMapper.selectCourses(courseCodes);
            assert result!=null && !result.isEmpty();

            return new ImmutablePair<>(
                    ServiceMessage.of(ServiceMessage.Level.SUCCESS, "查询到"+result.size()+"门已经申请的课程"),
                    result
            );

        }
    }

    public Triple<ServiceMessage, List<Course>, CourseApplication> updateAppliedCourses(@Nullable String flowNo,
                                                                                        @Nullable String[] courseCodesToUpdate) {
        if (flowNo == null || flowNo.isBlank()) {
            return new ImmutableTriple<>(
                    ServiceMessage.of(ServiceMessage.Level.WARN, "流水号不可为空"),
                    null,
                    null
            );
        }

        if (courseCodesToUpdate == null || courseCodesToUpdate.length == 0) {
            return new ImmutableTriple<>(
                    ServiceMessage.of(ServiceMessage.Level.WARN, "更新的课程代码列表为空"),
                    null,
                    null
            );
        }

        return _updateAppliedCourses(flowNo, Arrays.stream(courseCodesToUpdate).toList());
    }


    private Triple<ServiceMessage, List<Course>, CourseApplication> _updateAppliedCourses(@NonNull String flowNo,
                                                                                          @NonNull List<String> courseCodesToUpdate) {

        try (var session = MyDb.use().openSession(true)) {

            val courseApplicationMapper = session.getMapper(CourseApplicationMapper.class);
            val courseMapper = session.getMapper(CourseMapper.class);

            // 如果课程代码中含有空字符串则删除
            courseCodesToUpdate = courseCodesToUpdate.stream().dropWhile(String::isBlank).toList();

            // 如果有课程代码是重复的, 则去重
            courseCodesToUpdate = CollectionUtil.distinct(courseCodesToUpdate);

            // 获取将要被更新的课程
            val courses = courseMapper.selectCourses(courseCodesToUpdate);

            if (courses == null || courses.size() != courseCodesToUpdate.size()) {
                return new ImmutableTriple<>(
                        ServiceMessage.of(ServiceMessage.Level.ERROR, "更新的课程列表中含有非法字符"),
                        new ArrayList<>(),
                        null
                );
            }

            // 获取指定流水号的课程申请
            val courseApplication = courseApplicationMapper.selectByFlowNo(flowNo);

            // 如果课程申请不存在
            if (courseApplication == null) {
                return new ImmutableTriple<>(
                        ServiceMessage.of(ServiceMessage.Level.ERROR, "流水号为" + flowNo + "的课程申请对象(Course Application)不存在。"),
                        courses,
                        null
                );
            }

            // 修改对应的课程申请中的课程代码s
            courseApplication.setApproCourses(CourseUtil.toCourseCodesStr(courseCodesToUpdate));

            // 校验更改是否成功
            val rows = courseApplicationMapper.update(courseApplication);

            // 如果更新失败，将返回带有未更新前的课程申请对象
            if (rows != 1) {
                return new ImmutableTriple<>(
                        ServiceMessage.of(ServiceMessage.Level.ERROR, "数据库更新流水号为" + flowNo + "的课程申请遇到失败。"),
                        courses,
                        courseApplication
                );
            }

            val updatedCourseApplication = courseApplicationMapper.selectByFlowNo(flowNo);

            assert updatedCourseApplication != null;

            return new ImmutableTriple<>(
                    ServiceMessage.of(ServiceMessage.Level.SUCCESS, "更新课程申请成功！"),
                    courses,
                    updatedCourseApplication
            );
        }
    }
}
