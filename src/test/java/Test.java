import cn.hutool.core.util.StrUtil;
import com.github.akagawatsurunaki.novappro.mapper.impl.CourseMapperImpl;
import com.github.akagawatsurunaki.novappro.model.approval.CourseApplication;
import com.github.akagawatsurunaki.novappro.model.course.Course;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        CourseApplication courseApplication = new CourseApplication();
        courseApplication.setAppliedCourses(new ArrayList<>());
    }
}
