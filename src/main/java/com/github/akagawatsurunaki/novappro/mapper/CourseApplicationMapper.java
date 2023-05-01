package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.model.database.approval.CourseApplication;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface CourseApplicationMapper {

    Pair<VC.Mapper, CourseApplication> insert(@NonNull CourseApplication courseApplication);
    Pair<VC.Mapper, List<CourseApplication>> select(@NonNull Integer userId);

    Pair<VC.Mapper, CourseApplication> selectByFlowNo(@NonNull String flowNo);

    Pair<VC.Mapper, CourseApplication> update(@NonNull CourseApplication courseApplication);
    Pair<VC.Mapper, CourseApplication> update(@NonNull List<CourseApplication> courseApplications);

    Pair<VC.Mapper, CourseApplication> delete(@NonNull CourseApplication courseApplication);

}
