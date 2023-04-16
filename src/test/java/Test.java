import cn.hutool.core.util.EnumUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.github.akagawatsurunaki.novappro.annotation.Table;
import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.enumeration.BusType;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalFlowDetailMapper;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalFlowMapper;
import com.github.akagawatsurunaki.novappro.mapper.impl.ApprovalFlowDetailMapperImpl;
import com.github.akagawatsurunaki.novappro.mapper.impl.ApprovalFlowMapperImpl;
import com.github.akagawatsurunaki.novappro.model.approval.ApprovalFlow;
import com.github.akagawatsurunaki.novappro.model.approval.ApprovalFlowDetail;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Date;

public class Test {
    public static void main(String[] args){
        func3();
    }

    static void func1() {
        ApprovalFlowMapper approvalFlowMapper = ApprovalFlowMapperImpl.getInstance();
        var af = new ApprovalFlow("1231234", ApprovalStatus.APPROVED, "asdf", BusType.LINEAR, 123, new Date());
        approvalFlowMapper.insert(af);
    }

    static void func2() {
        ApprovalFlowDetailMapper approvalFlowDetailMapper = ApprovalFlowDetailMapperImpl.getInstance();
        var s = new ApprovalFlowDetail(111, "234", 12, "sdff", new Date(), ApprovalStatus.AUDITING);
        approvalFlowDetailMapper.insert(s);
    }

    static void func3() {
        ApprovalFlowDetailMapper approvalFlowDetailMapper = ApprovalFlowDetailMapperImpl.getInstance();
        Pair<VerifyCode.Mapper, ApprovalFlowDetail> pair = approvalFlowDetailMapper.select(111);
        ApprovalFlowDetail approvalFlowDetail = pair.getRight();
        System.out.println("approvalFlowDetail = " + approvalFlowDetail);
    }

}
