/**
 *  金融模块表维护脚本
 * 
 */
ALTER TABLE sctrans.T_TO_RC_MORTGAGE_INFO ADD REFER_CODE varchar(32) ;
ALTER TABLE sctrans.T_TO_RC_MORTGAGE_INFO ADD REFER_NAME varchar(32) ;
ALTER TABLE sctrans.T_TO_RC_MORTGAGE_INFO ADD REFER_USER varchar(32) ;
ALTER TABLE sctrans.T_TO_RC_MORTGAGE_INFO ADD REFER_ADDREASS varchar(256) ;
ALTER TABLE sctrans.T_TO_RC_MORTGAGE_INFO ADD IS_DELETED char(1);
ALTER TABLE sctrans.T_TO_RC_MORTGAGE_INFO ADD ITEM_MANAGER varchar(32);
execute sp_addextendedproperty 'MS_Description', 
   '仓库管理员',
   'schema', 'sctrans', 'table', 'T_TO_RC_MORTGAGE_INFO', 'column', 'ITEM_MANAGER'
go
execute sp_addextendedproperty 'MS_Description', 
   '是否删除 Y:是; N:否',
   'schema', 'sctrans', 'table', 'T_TO_RC_MORTGAGE_INFO', 'column', 'IS_DELETED'
go
execute sp_addextendedproperty 'MS_Description', 
   '相关地址:房屋地址',
   'schema', 'sctrans', 'table', 'T_TO_RC_MORTGAGE_INFO', 'column', 'REFER_ADDREASS'
go
execute sp_addextendedproperty 'MS_Description', 
   '相关人: 产权人，银行卡所有者姓名',
   'schema', 'sctrans', 'table', 'T_TO_RC_MORTGAGE_INFO', 'column', 'REFER_USER'
go
execute sp_addextendedproperty 'MS_Description', 
   '相关名称:银行名称',
   'schema', 'sctrans', 'table', 'T_TO_RC_MORTGAGE_INFO', 'column', 'REFER_NAME'
go
execute sp_addextendedproperty 'MS_Description', 
   '相关编号;身份证号,产证编号,银行卡号',
   'schema', 'sctrans', 'table', 'T_TO_RC_MORTGAGE_INFO', 'column', 'REFER_CODE'
go




ALTER TABLE sctrans.T_MM_MATERIAL_ITEM ADD IS_DELETED char(1);
execute sp_addextendedproperty 'MS_Description', 
   '是否删除 Y:是; N:否',
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'IS_DELETED'
go

