package com.github.akagawatsurunaki.novappro.service.stu;

import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.mapper.CourseMapper;
import com.github.akagawatsurunaki.novappro.model.database.course.Course;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class CourseService {

    @Getter
    private static final CourseService instance = new CourseService();

    public Pair<VC.Service, List<Course>> getAllCourses() {

        try (SqlSession session = MyDb.use().openSession(true)) {

            var courseMapper = session.getMapper(CourseMapper.class);
            var courses = courseMapper.selectAllCourses();
            if (courses != null) {
                if (courses.isEmpty()) {
                    return new ImmutablePair<>(VC.Service.NO_SUCH_ENTITY, new ArrayList<>());
                }
                return new ImmutablePair<>(VC.Service.OK, courses);
            }
            return new ImmutablePair<>(VC.Service.ERROR, null);
        }
    }

}
