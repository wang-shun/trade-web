package com.centaline.parportal.mobile.login;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.web.validate.AjaxResponse;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.parportal.mobile.login.entity.TAppEquipmentBindingDetail;
import com.centaline.parportal.mobile.login.service.TAppEquipmentBindingDetailService;

/**
 * 三级市场APP 登录绑定 PC端解绑
 * @author yumin3
 *
 */
@Controller
@RequestMapping(value = "/app")
public class loginUnbindingController {

    @Autowired
    private TAppEquipmentBindingDetailService tappEquipmentBindingDetailService;

    @Autowired
    private UamSessionService                 uamSessionService;                //当前登录用户信息

    /**
     * APP登录绑定列表
     * @return
     */
    @RequestMapping(value = "loginUnbinding")
    public String loginUnbinding(Model model) {

        //获取当前的登录用户
        SessionUser sessionUser = uamSessionService.getSessionUser();
        //        if (SalesJob.JWYGW.getCode().equals(sessionUser.getServiceJobCode())) { //物业顾问
        //            model.addAttribute("jobCode", SalesJob.JWYGW.getCode());
        //            model.addAttribute("orgId", sessionUser.getId());
        //
        //        } else if (SalesJob.JFHJL.getCode().equals(sessionUser.getServiceJobCode())
        //                   || SalesJob.JFHMS.getCode().equals(sessionUser.getServiceJobCode())) { //分行经理,分行秘书
        //            model.addAttribute("jobCode", SalesJob.JFHJL.getCode());
        //            model.addAttribute("orgId", sessionUser.getBusigrpId());
        //
        //        } else if (SalesJob.JQYJL.getCode().equals(sessionUser.getServiceJobCode())) { //区经,品控
        //            model.addAttribute("jobCode", SalesJob.JQYJL.getCode());
        //            model.addAttribute("orgId", sessionUser.getBusiarId()); //片区
        //
        //        } else if (SalesJob.JQYZJ.getCode().equals(sessionUser.getServiceJobCode())) { //区总监
        //            model.addAttribute("jobCode", SalesJob.JQYZJ.getCode());
        //            model.addAttribute("orgId", sessionUser.getBusiswzId()); //小战区
        //
        //        } else if (SalesJob.JQYDS.getCode().equals(sessionUser.getServiceJobCode())
        //                   || SalesJob.JPKZY.getCode().equals(sessionUser.getServiceJobCode())) { //区董 品控
        //            model.addAttribute("jobCode", SalesJob.JQYDS.getCode());
        //            model.addAttribute("orgId", sessionUser.getBusiwzId()); //战区
        //
        //        } else if (SalesJob.JFZJL.getCode().equals(sessionUser.getServiceJobCode())) {//副总
        //            model.addAttribute("jobCode", SalesJob.JFZJL.getCode());
        //            model.addAttribute("orgId", sessionUser.getBusibaId()); //大区
        //        }
        model.addAttribute("userServiceDepId", sessionUser.getServiceDepId());
        model.addAttribute("orglevel", sessionUser.getServiceDepHierarchy());//部门层次
        return "mobile/appLoginUnbindding";
    }

    /**
     * 手机解绑  修改状态病更新修改时间
     */
    @RequestMapping(value = "appUnbinding")
    @ResponseBody
    public AjaxResponse<Boolean> appUnbinding(long id) {
        AjaxResponse<Boolean> res = new AjaxResponse<Boolean>();

        TAppEquipmentBindingDetail tAppEquipmentBindingDetail = new TAppEquipmentBindingDetail();
        tAppEquipmentBindingDetail.setId(id);
        tAppEquipmentBindingDetail.setUpdateTime(new Date());
        tAppEquipmentBindingDetail.setStatus(0);
        boolean bool = tappEquipmentBindingDetailService
            .updateStatusByPrimaryKey(tAppEquipmentBindingDetail);
        if (bool) {
            res.setSuccess(true);
            res.setMessage("解绑成功!");
        } else {
            res.setSuccess(false);
            res.setMessage("解绑失败!");
        }

        return res;
    }
}
