/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.centaline.parportal.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.service.impl.CacheableSqlCustomDictServiceImpl;
import com.centaline.trans.common.service.impl.QuickQueryMortgageFinOrgValueServiceImpl;
import com.centaline.trans.common.service.impl.QuickQueryMortgageStringChangeServiceImpl;
import com.centaline.trans.common.service.impl.QuickQueryOrgCustomDictServiceImpl;
import com.centaline.trans.common.service.impl.QuickQueryPropertyServiceImpl;
import com.centaline.trans.common.service.impl.QuickQuerySrvsDictCustomDictServiceImpl;

/**
 * 
 * @author sstonehu
 * @version $Id: ProjectContextConfig.java, v 0.1 2016年12月12日 上午6:02:38 sstonehu Exp $
 */
@Configuration
public class ProjectContextConfig {

    @Bean(name = "qqcdMortName")
    public CustomDictService qqcdMortName() {
        QuickQuerySrvsDictCustomDictServiceImpl dict = new QuickQuerySrvsDictCustomDictServiceImpl();
        dict.setDictType("30016");
        return dict;
    };

    @Bean(name = "qqcdFinOrgNameDict")
    public CustomDictService qqcdFinOrgNameDict() {
        CacheableSqlCustomDictServiceImpl dict = new CacheableSqlCustomDictServiceImpl();
        dict.setSql("select FIN_ORG_CODE [KEY], FIN_ORG_NAME VALUE from sctrans.T_TS_FIN_ORG");
        return dict;
    };

    @Bean(name = "qqPropertyAddr")
    public CustomDictService qqPropertyAddr() {
        QuickQueryPropertyServiceImpl dict = new QuickQueryPropertyServiceImpl();
        dict.setDictType("mortgageAddr");
        return dict;
    };

    @Bean(name = "qqFinOrgNameYC")
    public CustomDictService qqFinOrgNameYC() {
        QuickQueryMortgageFinOrgValueServiceImpl dict = new QuickQueryMortgageFinOrgValueServiceImpl();
        dict.setDictType("mortgageFinOrg");
        return dict;
    };

    @Bean(name = "qqFenHang")
    public CustomDictService qqFenHang() {
        QuickQueryPropertyServiceImpl dict = new QuickQueryPropertyServiceImpl();
        dict.setDictType("fenHang");
        return dict;
    };

    @Bean(name = "qqcdOrgIdName")
    public CustomDictService qqcdOrgIdName() {
        QuickQueryOrgCustomDictServiceImpl dict = new QuickQueryOrgCustomDictServiceImpl();
        dict.setProp("orgName");
        return dict;
    };

    @Bean(name = "qqRealName")
    public CustomDictService qqRealName() {
        QuickQueryPropertyServiceImpl dict = new QuickQueryPropertyServiceImpl();
        dict.setDictType("getRealName");
        return dict;
    };

    @Bean(name = "qqIsLost")
    public CustomDictService qqIsLost() {
        QuickQueryMortgageStringChangeServiceImpl dict = new QuickQueryMortgageStringChangeServiceImpl();
        dict.setDictType("isLost");
        return dict;
    };

    @Bean(name = "qqSignPrice")
    public CustomDictService qqSignPrices() {
        QuickQueryPropertyServiceImpl dict = new QuickQueryPropertyServiceImpl();
        dict.setDictType("signPrice");
        return dict;
    };

    @Bean(name = "qqCaseProperty")
    public CustomDictService qqCaseProperty() {
        QuickQueryMortgageFinOrgValueServiceImpl dict = new QuickQueryMortgageFinOrgValueServiceImpl();
        dict.setDictType("caseProperty");
        return dict;
    };

    @Bean(name = "qqMortgageAmount")
    public CustomDictService qqMortgageAmount() {
        QuickQueryMortgageStringChangeServiceImpl dict = new QuickQueryMortgageStringChangeServiceImpl();
        dict.setDictType("mortgageAmount");
        return dict;
    };

}
