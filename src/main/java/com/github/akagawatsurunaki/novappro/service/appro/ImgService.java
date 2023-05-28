package com.github.akagawatsurunaki.novappro.service.appro;

import com.github.akagawatsurunaki.novappro.mapper.UploadFileMapper;
import com.github.akagawatsurunaki.novappro.model.frontend.ServiceMessage;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.val;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

@NoArgsConstructor
public class ImgService {

    @Getter
    private static final ImgService instance = new ImgService();

    public Pair<ServiceMessage, String> getImg(@NonNull String pathPrefix, @Nullable String flowNo) {
        if (flowNo == null || flowNo.isBlank()) {
            return ImmutablePair.of(ServiceMessage.of(ServiceMessage.Level.WARN, "流水号不能为空"), null);
        }

        try (var session = MyDb.use().openSession(true)) {
            val uploadFileMapper = session.getMapper(UploadFileMapper.class);
            val uploadFile = uploadFileMapper.selectByFlowNo(flowNo);
            val fullPath = pathPrefix + "/" + uploadFile.getFileName();
            System.out.println("fullPath = " + fullPath);
            return ImmutablePair.of(ServiceMessage.of(ServiceMessage.Level.SUCCESS, "查询到1张图片"), fullPath);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ImmutablePair.of(ServiceMessage.of(ServiceMessage.Level.ERROR, "未找到指定图片"), null);

    }
}
