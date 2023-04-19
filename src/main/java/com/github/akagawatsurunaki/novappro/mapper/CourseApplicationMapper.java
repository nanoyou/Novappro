package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.model.database.approval.CourseApplication;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface CourseApplicationMapper {

    Pair<VerifyCode.Mapper, CourseApplication> insert(@NonNull CourseApplication courseApplication);
    Pair<VerifyCode.Mapper, List<CourseApplication>> select(@NonNull Integer userId);

    Pair<VerifyCode.Mapper, CourseApplication> selectByFlowNo(@NonNull String flowNo);

    Pair<VerifyCode.Mapper, CourseApplication> update(@NonNull CourseApplication courseApplication);
    Pair<VerifyCode.Mapper, CourseApplication> update(@NonNull List<CourseApplication> courseApplications);

    Pair<VerifyCode.Mapper, CourseApplication> delete(@NonNull CourseApplication courseApplication);

}
