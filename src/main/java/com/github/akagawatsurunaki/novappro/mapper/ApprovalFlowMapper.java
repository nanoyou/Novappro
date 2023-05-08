package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlow;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApprovalFlowMapper {

    int insert(ApprovalFlow approvalFlow);

    ApprovalFlow select(@Param("flowNo") @NonNull String flowNo);

    List<ApprovalFlow> selectAll();

    int updateApproStatus(@Param("flowNo") @NonNull String flowNo,
                          @Param("status") @NonNull ApprovalStatus status);

}
