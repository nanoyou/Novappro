package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlowDetail;
import lombok.NonNull;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface ApprovalFlowDetailMapper {

    int insert(@NonNull ApprovalFlowDetail approvalFlowDetail);

    List<ApprovalFlowDetail> selectByFlowNo(@Param("flowNo") @NonNull String flowNo);

    ApprovalFlowDetail select(@Param("flowNo") @NonNull String flowNo,
                              @Param("auditUserId") @NonNull Integer auditUserId);

    List<String> selectFlowNoByApproverId(@Param("approverId") @NonNull Integer approverId);


    int updateApproStatus(@Param("flowNo") @NonNull String flowNo,
                          @Param("id") @NonNull Integer id,
                          @Param("status") @NonNull ApprovalStatus status);

    int updateApproRemark(@Param("flowNo") @NonNull String flowNo,
                          @Param("id") @NonNull Integer id,
                          @Param("remark") @NonNull String remark);
}
