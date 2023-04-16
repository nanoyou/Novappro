package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.model.approval.CourseApproFlow;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

public interface CourseApproFlowMapper {

    Pair<VerifyCode.Mapper, CourseApproFlow> insert(@NonNull CourseApproFlow courseApproFlow);

    Pair<VerifyCode.Mapper, CourseApproFlow> select();
}
