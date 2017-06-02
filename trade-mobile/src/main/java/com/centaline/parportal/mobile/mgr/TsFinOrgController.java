package com.centaline.parportal.mobile.mgr;

import com.aist.uam.userorg.remote.UamUserOrgService;
import com.centaline.trans.mgr.entity.TsFinOrg;
import com.centaline.trans.mgr.service.TsFinOrgService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caoyuan7 on 2017/4/25.
 */
@Controller
@RequestMapping(value = "/manage")
public class TsFinOrgController {


    @Autowired
    private TsFinOrgService tsFinOrgService;

    @RequestMapping(value = "queryBankListByPcode")
    @ResponseBody
    public Map<String, Object> queryBankListByPcode(String pcode, String flag,String tag,String nowCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<TsFinOrg> bankList = tsFinOrgService.findParentBankList(flag,tag,nowCode);
        map.put("bankList", bankList);
        if (!StringUtils.isBlank(pcode)) {
            TsFinOrg tsFinOrg = tsFinOrgService.findBankByFinOrg(pcode);
            if (tsFinOrg != null) {
                map.put("bankCode", tsFinOrg.getFaFinOrgCode());
                return map;
            }
        }
        map.put("bankCode", "");
        return map;
    }

    /**
     * 根据分行编号查询egu或非egu支行下拉列表
     * @param faFinOrgCode
     * @param flag
     * @return
     */
    @RequestMapping(value = "queryBankListByParentCode")
    @ResponseBody
    public List<TsFinOrg> findBankListByParentCode(String faFinOrgCode,String flag,String tag,String nowCode) {

        List<TsFinOrg> bankList = tsFinOrgService.findBankListByParentCode(flag, faFinOrgCode,tag,nowCode);
        return bankList;
    }
}
