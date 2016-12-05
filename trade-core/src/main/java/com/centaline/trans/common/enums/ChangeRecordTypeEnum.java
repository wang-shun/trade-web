package com.centaline.trans.common.enums;

/**
 * ClassName: ChangeRecordTypeEnum <br/> 
 * 变更记录类型 
 * date: 2016年12月2日 上午10:57:52 <br/> 
 * 
 * @author gongjd 
 * @version  
 * @since JDK 1.8
 */
public enum ChangeRecordTypeEnum {
	
	OWNER("1","责任人"),PARTNER("2","合作对象");
	
    private String name;

    private String code;

    private ChangeRecordTypeEnum(String code, String name) {
        this.name = name;
        this.code = code;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
