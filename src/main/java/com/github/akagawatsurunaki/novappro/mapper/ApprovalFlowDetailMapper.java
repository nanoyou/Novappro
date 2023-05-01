package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlowDetail;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.SQLException;
import java.util.List;

public interface ApprovalFlowDetailMapper {

    Pair<VC.Mapper, ApprovalFlowDetail> insert(@NonNull ApprovalFlowDetail approvalFlowDetail);

    Pair<VC.Mapper, ApprovalFlowDetail> select(@NonNull String flowNo);

    Pair<VC.Mapper, List<ApprovalFlowDetail>> selectList(@NonNull List<String> flowNos);

    Pair<VC.Mapper, List<String>> selectFlowNoByApproverId(@NonNull Integer approverId);


    VC.Mapper updateApproStatus(@NonNull String flowNo, @NonNull Integer id,
                                @NonNull ApprovalStatus status) throws SQLException;

    VC.Mapper updateApproRemark(@NonNull String flowNo, @NonNull Integer id,
                                @NonNull String remark) throws SQLException;
}
