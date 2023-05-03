package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.model.database.file.UploadFile;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

public interface UploadFileMapper {

    UploadFile selectByFlowNo(@NonNull String flowNo);

    int insert(@NonNull UploadFile uploadFile);
}
