package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.model.approval.ApplicationEntity;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

public interface ApplicationEntityMapper {

    Pair<VerifyCode, ApplicationEntity> insertApplicationEntity(@NonNull ApplicationEntity applicationEntity);

    Pair<VerifyCode, ApplicationEntity> selectApplicationEntityByCode(@NonNull String code);

    enum VerifyCode {
        MAPPER_OK,
        SQL_EXCEPTION,
        ZERO_ROW_INSERTED,
        NO_SUCH_ENTITY
    }

}
