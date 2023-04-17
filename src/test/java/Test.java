import cn.hutool.core.util.EnumUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.json.JSONUtil;
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
import com.github.akagawatsurunaki.novappro.util.CourseUtil;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args){
        List<String> list = new ArrayList<>();
        list.add("123123");
        list.add("12341234");
        var json = JSONUtil.toJsonStr(list);
        System.out.println(json);
        var ll = JSONUtil.toList(json, String.class);


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

//    static void func3() {
//        ApprovalFlowDetailMapper approvalFlowDetailMapper = ApprovalFlowDetailMapperImpl.getInstance();
//        Pair<VerifyCode.Mapper, ApprovalFlowDetail> pair = approvalFlowDetailMapper.select(111);
//        ApprovalFlowDetail approvalFlowDetail = pair.getRight();
//        System.out.println("approvalFlowDetail = " + approvalFlowDetail);
//    }

    static void func4() {
        List<String> strs = new ArrayList<>();
        strs.add("1111");
        strs.add("2222");
        List<String> courseCodes = CourseUtil.getCourseCodes(strs.toString());
    }

}
