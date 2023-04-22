package com.github.akagawatsurunaki.novappro.service.appro;

import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.model.frontend.CourseAppItemDetail;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

public class CourseApplDetailItemService {
    @Getter
    private static final ApprovalService instance = new ApprovalService();

}
