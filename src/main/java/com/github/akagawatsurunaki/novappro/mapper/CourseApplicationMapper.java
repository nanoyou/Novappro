package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.model.database.approval.CourseApplication;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseApplicationMapper {

    int insert(@NonNull CourseApplication courseApplication);
    List<CourseApplication> select(@Param("addUserId") @NonNull Integer addUserId);

    CourseApplication selectByFlowNo(@Param("flowNo") @NonNull String flowNo);

    int update(@NonNull CourseApplication courseApplications);

}
