package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.model.database.approval.CourseApproFlow;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.SQLException;

public interface CourseApproFlowMapper {

    Pair<VerifyCode.Mapper, CourseApproFlow> insert(@NonNull CourseApproFlow courseApproFlow);

    CourseApproFlow select(@NonNull String flowNo) throws SQLException;

    Integer findMaxIdOfCourseApproFlow(@NonNull String flowNo) throws SQLException;
}
