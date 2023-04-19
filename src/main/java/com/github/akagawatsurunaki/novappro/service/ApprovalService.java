package com.github.akagawatsurunaki.novappro.service;

import com.github.akagawatsurunaki.novappro.constant.VerifyCode;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalFlowDetailMapper;
import com.github.akagawatsurunaki.novappro.mapper.ApprovalFlowMapper;
import com.github.akagawatsurunaki.novappro.mapper.UserMapper;
import com.github.akagawatsurunaki.novappro.mapper.impl.ApprovalFlowDetailMapperImpl;
import com.github.akagawatsurunaki.novappro.mapper.impl.ApprovalFlowMapperImpl;
import com.github.akagawatsurunaki.novappro.mapper.impl.UserMapperImpl;
import com.github.akagawatsurunaki.novappro.model.frontend.ApplItem;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class ApprovalService {

    @Getter
    private static final ApprovalService instance = new ApprovalService();

    private static final ApprovalFlowMapper APPROVAL_FLOW_MAPPER = ApprovalFlowMapperImpl.getInstance();
    private static final ApprovalFlowDetailMapper APPROVAL_FLOW_DETAIL_MAPPER =
            ApprovalFlowDetailMapperImpl.getInstance();
    private static final UserMapper USER_MAPPER = UserMapperImpl.getInstance();

    // TODO: TEST
    /**
     * 获取一个ApplItem对象
     */
    public Pair<VerifyCode.Service, ApplItem> getApplItem(@NonNull String flowNo) {

        var vc_af = APPROVAL_FLOW_MAPPER.select(flowNo);

        if (vc_af.getLeft() == VerifyCode.Mapper.OK) {

            var approFlow = vc_af.getRight();
            var vc_afd = APPROVAL_FLOW_DETAIL_MAPPER.select(flowNo);

            if (vc_afd.getLeft() == VerifyCode.Mapper.OK) {

                var applFlowDetail = vc_afd.getRight();

                var vc_addUser = USER_MAPPER.selectUserById(approFlow.getAddUserId());
                var vc_approver = USER_MAPPER.selectUserById(applFlowDetail.getAuditUserId());

                if (vc_addUser.getLeft() == VerifyCode.Mapper.OK && vc_approver.getLeft() == VerifyCode.Mapper.OK) {

                    var applicant = vc_addUser.getRight();
                    var approver = vc_approver.getRight();

                    var applItem = ApplItem.builder()
                            .flowNo(approFlow.getFlowNo())
                            .title(approFlow.getTitle())
                            .applicantName(applicant.getUsername())
                            .addTime(approFlow.getAddTime())
                            .approverName(approver.getUsername())
                            .approvalStatus(applFlowDetail.getAuditStatus())
                            .build();

                    return new ImmutablePair<>(VerifyCode.Service.OK, applItem);
                }
            }
        }
        return new ImmutablePair<>(VerifyCode.Service.ERROR, null);
    }


    /*
    TODO: 默认于老师为课程申请的审批人, 登录后, 网页上将会显示当前需要审批的审批.

    登陆后跳转到审批人的界面, 界面显示了loginUser的可以进行审批的审批列表.


    appl_list

    |---------------------------------------------|
    |审批流号  |标题|发起人|审批人|申请时间|申请状态 |操作  |
    -----------------------------------------------
    | weuusa  你好  我      待我审批    |查看详情   |
    -----------------------------------------------
    |...                                          |

    点入其中一个审批列表

    其中有一分类为课程审批, 将会

    appl_detail

    申请详情

    flowNo applTitle
    applicant..
    applDate
    applCourses
    applStatus
    --------------------------------------------
    | 显示流水号:           |          申请标题  |
    --------------------------------------------
    |申请人信息
    --------------------------------------------
    |学号: 2398833
    |姓名: 你好
    --------------------------------------------
    |申请内容
    --------------------------------------------
    |申请时间: 2023年4月18日 12:12
    |申请内容: 申请人请求申请如下的课程
    | 课程名称(课程编号) | 授课教师
    |
    --------------------------------------------
    |审批状态: 待审批
    --------------------------------------------

    appro_comment

    审批意见
    [
                                                ]
    submit_appl_status

    [同意]              [拒绝]               [取消]

     */

}
