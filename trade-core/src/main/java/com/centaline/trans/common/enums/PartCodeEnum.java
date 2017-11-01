package com.centaline.trans.common.enums;

/**
 * Created by wbzhouht on 2017/10/27.
 * 环节编码枚举类
 */
public enum PartCodeEnum {
	RF("caseRecvFlow","接单跟进"),
    WQ("TransSign","网签"),
    JYJH("TransPlanFilling","填写交易计划"),
    JS("RatePayment","缴税"),
    GH("Guohu","过户"),
    DKJQ("LoanClose","贷款结清"),
    GJJSQ("PSFApply","纯公积金申请"),
    GJJQY("PSFSign","纯公积金贷款签约"),
    CGJJSP("PSFApprove","纯公积金贷款审批"),
    ZBDKSP("SelfLoanApprove","自办贷款审批"),
    AJDK("ComLoanProcess","按揭贷款审批"),
    PD("LoanerProcess","派单"),
    DKLS("LoanlostApply","贷款流失申请"),
    ZHDK("ComLoanAndPSFLoanProcess","组合贷款"),
    /**
     * 用于TaskController中代办事项的枚举
     */
    SPFS("LoanlostApproveManager","流失审批（主管）"),
    SPFS1("LoanlostApproveDirector","流失审批（总监）"),
    SPFS2("LoanlostApproveGeneralManager","流失审批（总经理）"),
    SPFS3("LoanlostApproveSeniorManager","审批方式3"),

    ZBPG("EvaReportArise","发起评估"),
    XXPG("OfflineEva","线下评估报告发起"),

    JASP("CaseCloseThirdApprove","归档确认和结案审核"),
    JASP1("CaseCloseFirstApprove","归档确认和结案审核"),
    JASP2("CaseCloseSecondApprove","归档确认和结案审核"),
    WXSP("InvalidCaseApprove","无效审批"),
    FWX("ServiceChangeApply","服务项变更"),
    FWXSP("ServiceChangeApprove","服务项审批"),
    ZJJG("spvOutApply","资金监管解除申请"),
    ZJJGSP("spvOutApprove","资金监管解除审批"),
    FWCQ("serviceRestartApply","服务重启申请"),
    FWCQSP("serviceRestartApprove","服务重启审批"),
    PGCQ("evalServiceRestartApply","评估重启服务申请"),
    PGCQSP("evalServiceRestartApprove","评估重启服务审批"),



    LZ("HouseBookGet","领证"),
    FK("LoanRelease","放款"),
    GHSP("GuohuApprove","过户审批"),
    JA("CaseClose","结案"),
    /**
     * 主要用于填写交易计划时插入的环节
     */
    SDMQ("CommercialLoansSigned","商贷面签"),
    SDCPG("BusinessLoanAssessmentReport","商贷出评估报告"),
    SDPD("CommercialLendingCompleted","商贷批贷完成"),
    GJJDK("ProvidentFundLoanBookApplication","公积金贷款预约申请"),
    GJJMQ("ProvidentFundSigned","公积金面签"),
    GJJPD("ProvidentFundLoanCompletion","公积金批贷完成");
    String code;
    String name;
    private PartCodeEnum(String code,String name){
        this.setCode(code);
        this.setName(name);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
