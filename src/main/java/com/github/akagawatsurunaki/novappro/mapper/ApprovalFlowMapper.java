package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlow;
import jdk.jshell.Snippet;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

public interface ApprovalFlowMapper {

    Pair<VerifyCode.Mapper, ApprovalFlow> insert(ApprovalFlow approvalFlow);

    Pair<VerifyCode.Mapper, ApprovalFlow> select(@NonNull String flowNo);

    VerifyCode.Mapper updateApproStatus(@NonNull String flowNo, @NonNull ApprovalStatus status);

}
