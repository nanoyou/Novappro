package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.model.approval.CourseApplication;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

public interface CourseApplicationMapper {

    Pair<VerifyCode, CourseApplication> insertCourseApplication(@NonNull CourseApplication courseApplication);


    enum VerifyCode {
        MAPPER_OK,
        SQL_EXCEPTION,
        ZERO_ROW_INSERTED,
        FEW_ROWS_INSERTED
    }

}
