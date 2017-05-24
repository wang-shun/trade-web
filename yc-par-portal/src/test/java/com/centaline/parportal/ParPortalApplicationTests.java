package com.centaline.parportal;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.centaline.parportal.exception.CheckParametersException;
import com.centaline.parportal.mobile.common.web.OperateListController;
import com.centaline.parportal.mobile.eloancase.web.ELoanCaseController;
import com.centaline.parportal.mobile.mortgage.web.MortgageController;
import com.centaline.parportal.mobile.mortgage.web.MortgageListController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParPortalApplicationTests
{

    @Autowired
    private MortgageListController mortgageListController;

    @Autowired
    private MortgageController mortgageController;

    @Autowired
    private ELoanCaseController eLoanCaseController;

    @Autowired
    private OperateListController operateListController;

    @Test
    public void mortList() throws CheckParametersException
    {
//        String result = mortgageListController.caseList(1, 10, null, null, null);

//        System.out.println(result);
    }

    @Test
    public void mortDetail() throws CheckParametersException
    {
        String result = mortgageController.mortgageCaseDetail("42");

        System.out.println(result);
    }

    @Test
    public void mortReject() throws CheckParametersException
    {
        String result = mortgageController.accept("10244", "true", "1065101", "1065094", "ACCEPT", "ZY-AJ-201512-0949", null);

        System.out.println(result);
    }

    @Test
    public void mortSuppleInfo() throws CheckParametersException
    {
        String result = mortgageController.suppleInfo("42", "CMT", "ZY-ZL-201612-0017", "备注");

        System.out.println(result);

    }

    @Test
    public void eLoanSuppleInfo() throws CheckParametersException
    {
        // String result = eLoanCaseController.suppleInfo("ZY-JR-201705-0032",
        // "BUJIAN", "ZY-ZL-201704-0008", "备注");

        String random = UUID.randomUUID().toString();
        System.out.println(random);
    }

    @Test
    public void eloanCaseList()
    {
//        String result = eLoanCaseController.list(1, 10, null, null, null);
//        System.out.println(result);
    }

    @Test
    public void eloanCaseDetail() throws CheckParametersException
    {
        String result = eLoanCaseController.mortgageCaseDetail("ZY-JR-201704-0118");

        System.out.println(result);
    }

    @Test
    public void followUpInELoanCase()
    {
        String result = eLoanCaseController.followUp("ZY-JR-201704-0063", "LOAN_RELEASED", "ZY-SH-201704-0013", "AAAA");

        System.out.println(result);
    }

    @Test
    public void mortBankAudit()
    {
        String result = mortgageController.followUp("10204", "true", "1058664", "1058652", "MORT_APPROVED", "ZY-AJ-201601-0192", null, "2017/5/2");

        System.out.println(result);
    }

    @Test
    public void operateList()
    {
        String result = operateListController.operateList(1, 2, null, null);
        System.out.println(result);
    }

}
