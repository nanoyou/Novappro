package com.github.akagawatsurunaki.novappro.service.stu;

import com.github.akagawatsurunaki.novappro.mapper.ApprovalAuthorityMapper;
import com.github.akagawatsurunaki.novappro.mapper.CourseMapper;
import com.github.akagawatsurunaki.novappro.model.database.course.Course;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class CourseService {

    @Getter
    private static final CourseService instance = new CourseService();

    /**
     * 获取可以被审批的课程
     *
     * @return
     */
    public Pair<ServiceMessage, List<Course>> getCoursesCanBeApplied() {
        try (SqlSession session = MyDb.use().openSession(true)) {

            val courseMapper = session.getMapper(CourseMapper.class);
            val approvalAuthorityMapper = session.getMapper(ApprovalAuthorityMapper.class);
            // 获取可以被申请的课程
            val courseCodes = approvalAuthorityMapper.selectCourseIdsCanBeApplied();

            if (courseCodes == null || courseCodes.isEmpty()) {
                return new ImmutablePair<>(
                        new ServiceMessage(ServiceMessage.Level.INFO, "没有课程可以被申请"),
                        new ArrayList<>()
                );
            }
            val result = courseMapper.selectCourses(courseCodes);
            assert !result.isEmpty();

            return new ImmutablePair<>(
                    new ServiceMessage(ServiceMessage.Level.SUCCESS, "您可以申请" + result.size() + "门课程"),
                    result
            );
        }
    }

}
