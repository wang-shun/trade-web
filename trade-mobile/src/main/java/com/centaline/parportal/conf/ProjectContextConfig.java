/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package com.centaline.parportal.conf;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aist.common.quickQuery.service.CustomDictService;
import com.aist.common.quickQuery.service.impl.CacheableSqlCustomDictServiceImpl;
import com.centaline.trans.common.service.impl.QuickQueryCaseSalerInfoServiceImpl;
import com.centaline.trans.common.service.impl.QuickQueryDictCustomDictServiceImpl;
import com.centaline.trans.common.service.impl.QuickQueryFinOrgImpl;
import com.centaline.trans.common.service.impl.QuickQueryGetCaseOverviewInfoServiceImpl;
import com.centaline.trans.common.service.impl.QuickQueryGetGlCaseCountServiceImpl;
import com.centaline.trans.common.service.impl.QuickQueryGetOperatorInfoServiceImpl;
import com.centaline.trans.common.service.impl.QuickQueryGetRishControlOfficerInfoServiceImpl;
import com.centaline.trans.common.service.impl.QuickQueryGetSolutionListServiceImpl;
import com.centaline.trans.common.service.impl.QuickQueryHouTaiServiceImpl;
import com.centaline.trans.common.service.impl.QuickQueryLightServiceImpl;
import com.centaline.trans.common.service.impl.QuickQueryMortgageFinOrgValueServiceImpl;
import com.centaline.trans.common.service.impl.QuickQueryMortgageStringChangeServiceImpl;
import com.centaline.trans.common.service.impl.QuickQueryMultiDictCustomDictServiceImpl;
import com.centaline.trans.common.service.impl.QuickQueryOrgCustomDictServiceImpl;
import com.centaline.trans.common.service.impl.QuickQueryPropertyServiceImpl;
import com.centaline.trans.common.service.impl.QuickQuerySrvsDictCustomDictServiceImpl;
import com.centaline.trans.common.service.impl.QuickQueryUserCustomDictServiceImpl;
import com.centaline.trans.common.service.impl.QuickQuerygetLoanTypeNameServiceImpl;

/**
 * 
 * @author sstonehu
 * @version $Id: ProjectContextConfig.java, v 0.1 2016年12月12日 上午6:02:38 sstonehu Exp $
 */
@Configuration
public class ProjectContextConfig {

    //人员、组织相关CustomDict
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

    @Bean(name = "qqUserIdMobile")
    public CustomDictService qqUserIdMobile() {
        QuickQueryUserCustomDictServiceImpl dict = new QuickQueryUserCustomDictServiceImpl();
        dict.setProp("mobile");
        return dict;
    };

    @Bean(name = "qqcdMortType")
    public CustomDictService qqcdMortName() {
        QuickQuerySrvsDictCustomDictServiceImpl dict = new QuickQuerySrvsDictCustomDictServiceImpl();
        dict.setDictType("30016");
        return dict;
    };

    @Bean(name = "qqcdMortLendWay")
    public CustomDictService qqcdMortLendWay() {
        QuickQuerySrvsDictCustomDictServiceImpl dict = new QuickQuerySrvsDictCustomDictServiceImpl();
        dict.setDictType("30017");
        return dict;
    };

    @Bean(name = "qqcdCaseStatus")
    public CustomDictService qqcdCaseStatus() {
        QuickQuerySrvsDictCustomDictServiceImpl dict = new QuickQuerySrvsDictCustomDictServiceImpl();
        dict.setDictType("30001");
        return dict;
    };
    
    @Bean(name = "qqcdMortServName")
    public CustomDictService qqcdMortServName() {
        QuickQuerySrvsDictCustomDictServiceImpl dict = new QuickQuerySrvsDictCustomDictServiceImpl();
        dict.setDictType("part_code");
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
    
    @Bean(name = "getGlCaseCount")
    public QuickQueryGetGlCaseCountServiceImpl getGlCaseCount(){
    	QuickQueryGetGlCaseCountServiceImpl dict = new QuickQueryGetGlCaseCountServiceImpl();
    	return dict;
    }

    @Bean(name="getCaseOverviewInfo")
    public QuickQueryGetCaseOverviewInfoServiceImpl getCaseOverviewInfo(){
    	QuickQueryGetCaseOverviewInfoServiceImpl dict = new QuickQueryGetCaseOverviewInfoServiceImpl();
    	dict.setDictType_caseProperty("30003");
    	dict.setDictType_caseStatus("30001");
    	return dict;
    }
    
    @Bean(name="getCaseSalerInfo")
    public QuickQueryCaseSalerInfoServiceImpl getCaseSalerInfo(){
    	QuickQueryCaseSalerInfoServiceImpl dict = new QuickQueryCaseSalerInfoServiceImpl();
    	dict.setTransPosition("30006001");
    	return dict;
    }
    
    @Bean(name="getCaseBuyyerInfo")
    public QuickQueryCaseSalerInfoServiceImpl getCaseBuyyerInfo(){
    	QuickQueryCaseSalerInfoServiceImpl dict = new QuickQueryCaseSalerInfoServiceImpl();
    	dict.setTransPosition("30006002");
    	return dict;
    }
    
    @Bean(name="getQianHouTai")
    public QuickQueryHouTaiServiceImpl getQianHouTai(){
    	QuickQueryHouTaiServiceImpl dict = new QuickQueryHouTaiServiceImpl();
    	return dict;
    }
    
    @Bean(name="qqLightService")
    public QuickQueryLightServiceImpl qqLightServiceImpl() {
    	QuickQueryLightServiceImpl dict = new QuickQueryLightServiceImpl();
    	return dict;
    }
    
    @Bean(name="getLoanTypeName")
    public QuickQuerygetLoanTypeNameServiceImpl getLoanTypeName() {
    	QuickQuerygetLoanTypeNameServiceImpl dict = new QuickQuerygetLoanTypeNameServiceImpl();
    	return dict;
    }
    
    @Bean(name="qqfinOrgIdName")
    public QuickQueryFinOrgImpl qqfinOrgIdName() {
    	QuickQueryFinOrgImpl dict = new QuickQueryFinOrgImpl();
    	return dict;
    }
    
    @Bean(name="getOperatorInfo")
    public QuickQueryGetOperatorInfoServiceImpl getOperatorInfo() {
    	QuickQueryGetOperatorInfoServiceImpl dict = new QuickQueryGetOperatorInfoServiceImpl();
    	return dict;
    }
    
    @Bean(name="getRishControlOfficerInfo")
    public QuickQueryGetRishControlOfficerInfoServiceImpl getRishControlOfficerInfo() {
    	QuickQueryGetRishControlOfficerInfoServiceImpl dict = new QuickQueryGetRishControlOfficerInfoServiceImpl();
    	return dict;
    }
    @Bean(name="qqcdUserIdName")
    public QuickQueryUserCustomDictServiceImpl qqcdUserIdName() {
    	QuickQueryUserCustomDictServiceImpl dict = new QuickQueryUserCustomDictServiceImpl();
    	dict.setProp("realName");
    	return dict;
    }
    @Bean(name="getSolutionList")
    public QuickQueryGetSolutionListServiceImpl getSolutionList() {
    	QuickQueryGetSolutionListServiceImpl dict = new QuickQueryGetSolutionListServiceImpl();
    	return dict;
    }
    
    @Bean(name="qqcdDictPartName")
    public QuickQueryDictCustomDictServiceImpl qqcdDictPartName() {
    	QuickQueryDictCustomDictServiceImpl dict = new QuickQueryDictCustomDictServiceImpl();
    	dict.setDictType("part_code");
    	return dict;
    }
    
    @Bean(name="qqcdDictNotAppeoveName")
    public QuickQueryMultiDictCustomDictServiceImpl qqcdDictNotAppeoveName() {
    	QuickQueryMultiDictCustomDictServiceImpl dict = new QuickQueryMultiDictCustomDictServiceImpl();
    	dict.setDictType("guohu_not_approve");
    	return dict;
    }
    @Bean(name="avatarUrlPrixDict")
    public CustomDictService avatarUrlPrixDict(@Value("${gm.server.url:http://img.sh.centaline.com.cn/salesweb/}")String avatarUrlPrix){
    	return new CustomDictService() {
    		@Override
    		public String getValue(String key) {
    			if(StringUtils.isBlank(key)) {
    				return key;
    			}else{
    				return avatarUrlPrix +"image/"+key+"/80_80_f.jpg";
    			}
    		}
		};
    }
}
