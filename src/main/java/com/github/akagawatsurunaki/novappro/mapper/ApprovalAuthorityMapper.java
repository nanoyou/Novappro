package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.model.database.ApprovalAuthority;
import lombok.NonNull;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApprovalAuthorityMapper {

    int insert(ApprovalAuthority approvalAuthority);

    List<ApprovalAuthority> selectByUserId(@NonNull Integer userId);

    List<Integer> selectUserIdsByCourseCode(@NonNull String courseCode);

    List<Integer> selectUserIdsByCourseCodeDesc(@NonNull String courseCode);

    List<ApprovalAuthority> selectAll();

    int delete(@Param("userId") @NonNull Integer userId,
               @Param("courseCode") @NonNull String courseCode);

    ApprovalAuthority selectByUserIdAndCourseCode(@Param("userId") @NonNull Integer userId,
                                                  @Param("courseCode") @NonNull String courseCode);
}
