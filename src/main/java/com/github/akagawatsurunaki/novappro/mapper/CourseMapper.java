package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.model.database.course.Course;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface CourseMapper {

    Pair<VC.Mapper, List<Course>> selectAllCourses();

    Pair<VC.Mapper, Course> selectCourseByCode(@NonNull String code);

    Pair<VC.Mapper, List<Course>> selectCourses(@NonNull List<String> codes);

}
