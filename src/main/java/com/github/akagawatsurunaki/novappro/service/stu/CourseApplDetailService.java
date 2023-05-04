package com.github.akagawatsurunaki.novappro.service.stu;

import cn.hutool.core.collection.CollectionUtil;
import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.mapper.CourseApplicationMapper;
import com.github.akagawatsurunaki.novappro.mapper.CourseMapper;
import com.github.akagawatsurunaki.novappro.model.database.course.Course;
import com.github.akagawatsurunaki.novappro.util.CourseUtil;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.NonNull;
import lombok.val;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class CourseApplDetailService {

    @Getter
    private static final CourseApplDetailService instance = new CourseApplDetailService();

    public List<Course> getAppliedCourses(@NonNull String flowNo) {

        try (var session = MyDb.use().openSession(true)) {

            val courseApplicationMapper = session.getMapper(CourseApplicationMapper.class);
            val courseMapper = session.getMapper(CourseMapper.class);

            var courseAppl = courseApplicationMapper.selectByFlowNo(flowNo);

            if (courseAppl != null) {
                var courseCodeList = courseAppl.getApproCourseIds();

                if (courseCodeList == null || courseCodeList.isEmpty()) {
                    return null;
                }

                List<String> courseCodes = CourseUtil.getCourseCodes(courseCodeList);

                return courseMapper.selectCourses(courseCodes);
            }
        }
        return null;
    }

    public Pair<VC.Service, List<Course>> updateAppliedCourses(@NonNull String flowNo,
                                                               @NonNull List<String> courseCodesToUpdate) {

        try (var session = MyDb.use().openSession(true)) {

            val courseApplicationMapper = session.getMapper(CourseApplicationMapper.class);
            var courseMapper = session.getMapper(CourseMapper.class);
            var courses = courseMapper.selectCourses(courseCodesToUpdate);

            var courseApplication = courseApplicationMapper.selectByFlowNo(flowNo);

            // 检查课程申请是否存在
            if (courseApplication != null) {

                // 如果有课程重复, 则去重
                courseCodesToUpdate = CollectionUtil.distinct(courseCodesToUpdate);

                // 校验这些课程是否存在
                if (courses != null) {

                    // 修改对应的course申请
                    courseApplication.setApproCourseIds(CourseUtil.toCourseCodesStr(courseCodesToUpdate));

                    // 校验更改是否成功
                    if (courseApplicationMapper.update(courseApplication) != null) {
                        return new ImmutablePair<>(VC.Service.OK, courses);
                    }
                }
                return new ImmutablePair<>(VC.Service.NO_SUCH_COURSE, null);
            } else {
                return new ImmutablePair<>(VC.Service.NO_SUCH_COURSE_APPL, null);
            }
        }
    }
}
