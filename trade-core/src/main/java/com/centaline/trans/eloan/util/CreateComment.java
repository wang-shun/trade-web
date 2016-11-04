package com.centaline.trans.eloan.util;

import com.centaline.trans.eloan.entity.ToRcMortgageInfo;
import com.centaline.trans.eloan.enums.MortgageCategoryEnum;

public class CreateComment {
	
	public static String createMmComment(ToRcMortgageInfo toRcMortgageInfo){
		String comment = null;
		if(MortgageCategoryEnum.CARDED.getCode().equals(toRcMortgageInfo.getMortgageCategory())) {
			comment = "身份证姓名:"+toRcMortgageInfo.getReferName()+";身份证号码:"+ toRcMortgageInfo.getReferCode();
			
		} else if(MortgageCategoryEnum.BANKCARD.getCode().equals(toRcMortgageInfo.getMortgageCategory())) {
			comment = "户名:"+toRcMortgageInfo.getReferName()+";银行卡号:"+ toRcMortgageInfo.getReferCode();
		} else if(MortgageCategoryEnum.PROPERTY_CARD.getCode().equals(toRcMortgageInfo.getMortgageCategory())) {
			comment = "产权编号:"+toRcMortgageInfo.getReferCode()+";产权人姓名:"+ toRcMortgageInfo.getReferName()+";房屋地址:"+toRcMortgageInfo.getReferAddreass();
		} else if(MortgageCategoryEnum.OTHER_CARD.getCode().equals(toRcMortgageInfo.getMortgageCategory())) {
			comment = "他证编号:"+toRcMortgageInfo.getReferCode()+";他项权利人:"+ toRcMortgageInfo.getReferName()+";房屋地址:"+toRcMortgageInfo.getReferAddreass();
		} 
		return comment;
	}
}
