import cn.hutool.core.util.StrUtil;
import com.github.akagawatsurunaki.novappro.mapper.impl.CourseMapperImpl;
import com.github.akagawatsurunaki.novappro.model.course.Course;

public class Test {
    public static void main(String[] args) {
        String s = StrUtil.toUnderlineCase(Course.class.getSimpleName());
        System.out.println(s);
        CourseMapperImpl.getInstance().selectAllCourses();
    }
}
