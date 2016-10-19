/**
 *  风控模块表维护脚本
 * 
 */
ALTER TABLE sctrans.T_TO_RC_MORTGAGE_INFO ADD REFER_CODE varchar(32) COMMENT '相关编号;身份证号,产证编号,银行卡号';
ALTER TABLE sctrans.T_TO_RC_MORTGAGE_INFO ADD REFER_NAME varchar(32) COMMENT '相关名称:银行名称';
ALTER TABLE sctrans.T_TO_RC_MORTGAGE_INFO ADD REFER_USER varchar(32) COMMENT '相关人: 产权人，银行卡所有者姓名';
ALTER TABLE sctrans.T_TO_RC_MORTGAGE_INFO ADD REFER_ADDREASS varchar(256) COMMENT '相关地址:房屋地址';
ALTER TABLE sctrans.T_TO_RC_MORTGAGE_INFO ADD IS_DELETED char(1) COMMENT '是否删除 Y:是; N:否';
