package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.model.approval.CourseApplication;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

public interface CourseApplicationMapper {

    Pair<VerifyCode.Mapper, CourseApplication> insert(@NonNull CourseApplication courseApplication);
    Pair<VerifyCode.Mapper, CourseApplication> select();
}
