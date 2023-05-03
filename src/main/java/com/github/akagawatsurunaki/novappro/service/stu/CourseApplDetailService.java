package com.github.akagawatsurunaki.novappro.service.stu;

import cn.hutool.core.collection.CollectionUtil;
import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.mapper.CourseApplicationMapper;
import com.github.akagawatsurunaki.novappro.mapper.CourseMapper;
import com.github.akagawatsurunaki.novappro.mapper.impl.CourseApplicationMapperImpl;
import com.github.akagawatsurunaki.novappro.model.database.course.Course;
import com.github.akagawatsurunaki.novappro.util.CourseUtil;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class CourseApplDetailService {

    @Getter
    private static final CourseApplDetailService instance = new CourseApplDetailService();

    private static final CourseApplicationMapper COURSE_APPLICATION_MAPPER = CourseApplicationMapperImpl.getInstance();

    @Deprecated
    public List<Course> getAppliedCourses(@NonNull String flowNo) {
        if (flowNo.isEmpty()) {
            return null;
        }

        var vc_courseAppl = COURSE_APPLICATION_MAPPER.selectByFlowNo(flowNo);
        if (vc_courseAppl.getLeft() == VC.Mapper.OK) {
            var courseAppl = vc_courseAppl.getRight();
            var courseCodeList = courseAppl.getApproCourseIds();

            if (courseCodeList == null) {
                return null;
            }
            if (courseCodeList.isEmpty()) {
                return null;
            }

            List<String> courseCodes = CourseUtil.getCourseCodes(courseCodeList);

            try (SqlSession session = MyDb.use().openSession(true)) {
                var courseMapper = session.getMapper(CourseMapper.class);
                return courseMapper.selectCourses(courseCodes);
            }
        }
        return null;
    }

    public Pair<VC.Service, List<Course>> updateAppliedCourses(@NonNull String flowNo,
                                                               @NonNull List<String> courseCodesToUpdate) {
        // 校验单号是否存在
        var vc_ca = COURSE_APPLICATION_MAPPER.selectByFlowNo(flowNo);

        if (vc_ca.getLeft() != VC.Mapper.OK) {
            return new ImmutablePair<>(VC.Service.NO_SUCH_COURSE_APPL, null);
        }

        // 如果有课程重复, 则去重
        courseCodesToUpdate = CollectionUtil.distinct(courseCodesToUpdate);

        // 校验这些课程是否存在

        try (SqlSession session = MyDb.use().openSession(true)) {
            var courseMapper = session.getMapper(CourseMapper.class);
            var courses = courseMapper.selectCourses(courseCodesToUpdate);

            if (courses != null) {

                // 修改对应的course申请
                var courseAppl = vc_ca.getRight();
                courseAppl.setApproCourseIds(CourseUtil.toCourseCodesStr(courseCodesToUpdate));

                // 校验更改是否成功
                var vc_ = COURSE_APPLICATION_MAPPER.update(courseAppl);

                if (vc_.getLeft() != VC.Mapper.OK) {
                    return new ImmutablePair<>(VC.Service.ERROR, null);
                }
                return new ImmutablePair<>(VC.Service.OK, courses);
            }

            return new ImmutablePair<>(VC.Service.NO_SUCH_COURSE, null);
        }
    }

//    private Pair<VC.Service, List<Course>> delAppliedCourses(@NonNull String flowNo,
//                                                             @NonNull List<String> courseCodesToDel) {
//        List<Course> result = new ArrayList<>();
//
//        if (courseCodesToDel.isEmpty()) {
//            return new ImmutablePair<>(VC.Service.MEANINGLESS, result);
//        }
//
//        // 要删除的课程在课程表中
//        var vc_courses = COURSE_MAPPER.selectCourses(courseCodesToDel);
//
//        if (vc_courses.getLeft() != VC.Mapper.OK) {
//            return new ImmutablePair<>(VC.Service.ERROR, null);
//        }
//
//        // 要删除的课程在申请中
//        var vc_courseAppl = COURSE_APPLICATION_MAPPER.selectByFlowNo(flowNo);
//
//        if (vc_courseAppl.getLeft() != VC.Mapper.OK) {
//            return new ImmutablePair<>(VC.Service.ERROR, null);
//        }
//
//        var courseApplication = vc_courseAppl.getRight();
//        var courseCodesInAppl = CourseUtil.getCourseCodes(courseApplication.getApproCourseIds());
//
//        // 获取交集
//        List<String> intersectedCourseCodes = (List<String>) CollectionUtil.intersection(courseCodesToDel,
//                courseCodesInAppl);
//
//        // 再次获取要删除的课程
//        var vc_delCourses = COURSE_MAPPER.selectCourses(intersectedCourseCodes);
//
//        if (vc_delCourses.getLeft() != VC.Mapper.OK) {
//            return new ImmutablePair<>(VC.Service.ERROR, null);
//        }
//
//        // 修改申请实体
//        courseApplication.setApproCourseIds(CourseUtil.toCourseCodesStr(intersectedCourseCodes));
//
//        var vc_ = COURSE_APPLICATION_MAPPER.update(courseApplication);
//
//        if (vc_.getLeft() != VC.Mapper.OK) {
//            return new ImmutablePair<>(VC.Service.ERROR, null);
//        }
//        return new ImmutablePair<>(VC.Service.OK, null);
//    }
}
