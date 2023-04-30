import com.github.akagawatsurunaki.novappro.enumeration.ApprovalStatus;
import com.github.akagawatsurunaki.novappro.enumeration.BusType;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalFlowDetailMapper;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalFlowMapper;
import com.github.akagawatsurunaki.novappro.mapper.impl.ApprovalFlowDetailMapperImpl;
import com.github.akagawatsurunaki.novappro.mapper.impl.ApprovalFlowMapperImpl;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlow;
import com.github.akagawatsurunaki.novappro.model.database.approval.ApprovalFlowDetail;
import com.github.akagawatsurunaki.novappro.util.CourseUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args){

    }

    static void func1() {
        ApprovalFlowMapper approvalFlowMapper = ApprovalFlowMapperImpl.getInstance();
        var af = new ApprovalFlow("1231234", ApprovalStatus.APPROVED, "asdf", BusType.LINEAR, 123, new Date());
        approvalFlowMapper.insert(af);
    }

    static void func2() {
        ApprovalFlowDetailMapper approvalFlowDetailMapper = ApprovalFlowDetailMapperImpl.getInstance();
        var s = new ApprovalFlowDetail(111, "234", 12, "sdff", new Date(), ApprovalStatus.SUBMITTED);
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
//    static void func5(){
//        ApplItem applItem = new ApplItem();
//        var s = ZhFieldUtil.getZhValue(ApplItem.class, ApplItem.Fields.applicantName);
//        System.out.println(s);
//    }
//
//    static void func6() {
//        var s = ApprovalService.getInstance().getApplItems("APFL2021000216818008100493182");
//        System.out.println(s);
//    }

    static void func7() {


    }

}
