package com.github.akagawatsurunaki.novappro.mapper;

import com.github.akagawatsurunaki.novappro.constant.VC;
import com.github.akagawatsurunaki.novappro.model.database.file.UploadFile;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

public interface UploadFileMapper {

    Pair<VC.Mapper, UploadFile> selectByFlowNo(@NonNull String flowNo);

    Pair<VC.Mapper, UploadFile> insert(@NonNull UploadFile uploadFile);
}
