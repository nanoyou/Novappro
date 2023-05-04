package com.github.akagawatsurunaki.novappro.service.init;

import com.github.akagawatsurunaki.novappro.mapper.ApprovalAuthorityMapper;
import com.github.akagawatsurunaki.novappro.util.MyDb;
import lombok.val;

public class InitService {

    public boolean isTableApprovalAuthorityInited() {
        try (var session = MyDb.use().openSession(true)){
            val approvalAuthorityMapper = session.getMapper(ApprovalAuthorityMapper.class);

        }
        return true;
    }


}
