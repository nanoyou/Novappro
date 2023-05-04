package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlowDetail;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import javax.naming.InsufficientResourcesException;
import java.sql.SQLException;
import java.util.List;

public interface ApprovalFlowDetailMapper {

    int insert(@NonNull ApprovalFlowDetail approvalFlowDetail);

    List<ApprovalFlowDetail> selectByFlowNo(@NonNull String flowNo);

    ApprovalFlowDetail select(@NonNull String flowNo, @NonNull Integer auditUserId);

    List<String> selectFlowNoByApproverId(@NonNull Integer approverId);


    int updateApproStatus(@NonNull String flowNo, @NonNull Integer id,
                                @NonNull ApprovalStatus status);

    int updateApproRemark(@NonNull String flowNo, @NonNull Integer id,
                                @NonNull String remark) throws SQLException;
}
