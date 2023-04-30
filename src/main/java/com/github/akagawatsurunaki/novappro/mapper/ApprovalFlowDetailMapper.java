package com.github.akagawatsurunaki.novappro.mapper;

import cn.hutool.core.date.chinese.SolarTerms;
import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlowDetail;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.SQLException;
import java.util.List;

public interface ApprovalFlowDetailMapper {

    Pair<VerifyCode.Mapper, ApprovalFlowDetail> insert(@NonNull ApprovalFlowDetail approvalFlowDetail);

    Pair<VerifyCode.Mapper, ApprovalFlowDetail> select(@NonNull String flowNo);

    Pair<VerifyCode.Mapper, List<String>> selectFlowNoByApproverId(@NonNull Integer approverId);


    VerifyCode.Mapper updateApproStatus(@NonNull String flowNo, @NonNull Integer id,
                                        @NonNull ApprovalStatus status) throws SQLException;

    VerifyCode.Mapper updateApproRemark(@NonNull String flowNo, @NonNull Integer id,
                                        @NonNull String remark) throws SQLException;
}
