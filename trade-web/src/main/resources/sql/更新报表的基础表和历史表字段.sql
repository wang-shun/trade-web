
/**已执行过
ALTER TABLE [sctrans].[T_RPT_CASE_BASE_INFO] ADD  MORTAGE_SIGN_DATE datetime;
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD  MORTAGE_SIGN_DATE datetime;**/

/*已执行过
ALTER TABLE [sctrans].[T_RPT_CASE_BASE_INFO] alter column  SIGN_CON_PRICE [decimal](18, 2);
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] alter column  SIGN_CON_PRICE [decimal](18, 2);
*/


/*已执行过
ALTER TABLE [sctrans].[T_RPT_CASE_BASE_INFO] alter column  SIGN_CON_PRICE [decimal](18, 2);
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] alter column  SIGN_CON_PRICE [decimal](18, 2);*/


/*
ALTER TABLE [sctrans].[T_RPT_CASE_BASE_INFO] ADD  FA_FIN_ORG_CODE varchar(32);--金融机构父类
ALTER TABLE [sctrans].[T_RPT_CASE_BASE_INFO] ADD  LOST_AMOUNT [decimal](18, 2);--流失金额

ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD  FA_FIN_ORG_CODE varchar(32);--金融机构父类
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD  LOST_AMOUNT [decimal](18, 2);--流失金额*/

/*
ALTER TABLE [sctrans].[T_RPT_CASE_BASE_INFO] ADD  RUWEI_BANK varchar(100);--是否是入围银行
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD  RUWEI_BANK varchar(100);--是否是入围银行*/

/*ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD  [GROUP_ORG_ID] varchar(32);
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD  [BUSIAR_ORG_ID] varchar(32);
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD  [BUSISWZ_ORG_ID] varchar(32);
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD  [BUSIWZ_ORG_ID] varchar(32);
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD  [BUSISH_ORG_ID] varchar(32);*/


/*ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD   [ELOAN_KA_APPLY_COUNT]  [decimal](18, 0);
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD 	   [ELOAN_KA_CARD_COUNT]   [decimal](18, 0);
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD 	   [ELOAN_KA_CARD_AMOUNT]  [decimal](18, 2);
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD 	   [ELOAN_KA_CARD_TYPE]    [varchar](64);

exec [sctrans].[P_ALTER_COLUMN_FOR_MONTH_CASE_INFO] 201702,'ELOAN_KA_APPLY_COUNT,ELOAN_KA_CARD_COUNT,ELOAN_KA_CARD_AMOUNT,ELOAN_KA_CARD_TYPE',''*/


/*
EXEC sp_rename 'sctrans.T_RPT_HISTORY_CASE_BASE_INFO.JQYZJ_CODE', 'JQYZJ_EMPLOYEE_CODE', 'column'*/

/*
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD 	[CASE_TAX_USER_ID] varchar(32);
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD 	[CASE_PRICING_USER_ID] varchar(32);
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD 	[CASE_PLIMIT_USER_ID] varchar(32);
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD 	[CASE_PSF_USER_ID] varchar(32);
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD 	[CASE_TRANSFER_USER_ID] varchar(32);
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD 	[CASE_GETBOOK_USER_ID] varchar(32);

exec [sctrans].[P_ALTER_COLUMN_FOR_MONTH_CASE_INFO] 201703,'CASE_TAX_USER_ID,CASE_PRICING_USER_ID,CASE_PLIMIT_USER_ID,CASE_PSF_USER_ID,CASE_TRANSFER_USER_ID,CASE_GETBOOK_USER_ID',''*/
/*

alter table sctrans.T_RPT_CASE_BASE_INFO  add TAX_HOUSE_HODING_TAX    decimal(18,2)
alter table sctrans.T_RPT_CASE_BASE_INFO  add TAX_PERSONAL_INCOME_TAX decimal(18,2)
alter table sctrans.T_RPT_CASE_BASE_INFO  add TAX_BUSINESS_TAX        decimal(18,2)
alter table sctrans.T_RPT_CASE_BASE_INFO  add TAX_CONTRACT_TAX        decimal(18,2)
alter table sctrans.T_RPT_CASE_BASE_INFO  add TAX_LAND_INCREMENT_TAX  decimal(18,2)


alter table sctrans.T_RPT_CASE_BASE_INFO  add TRANSFER_HOUSE_HODING_TAX    decimal(18,2)
alter table sctrans.T_RPT_CASE_BASE_INFO  add TRANSFER_PERSONAL_INCOME_TAX decimal(18,2)
alter table sctrans.T_RPT_CASE_BASE_INFO  add TRANSFER_BUSINESS_TAX        decimal(18,2)
alter table sctrans.T_RPT_CASE_BASE_INFO  add TRANSFER_CONTRACT_TAX        decimal(18,2)
alter table sctrans.T_RPT_CASE_BASE_INFO  add TRANSFER_LAND_INCREMENT_TAX  decimal(18,2)
*/
--过户表增加是否陪同字段
ALTER TABLE [sctrans].T_TO_HOUSE_TRANSFER ADD ACCOMPANY  [char](1);  --是否陪同
ALTER TABLE [sctrans].T_TO_HOUSE_TRANSFER ADD ACCOMPANY_REASON  [nvarchar](320);  --陪同原因
ALTER  TABLE sctrans.T_TO_HOUSE_TRANSFER  ADD 	ACCOMPANY_OTHERS_REASON   [varchar](1000);  --陪同选择原因其他手写原因

--天表增加是否陪同字段
ALTER TABLE [sctrans].[T_RPT_CASE_BASE_INFO]  ADD TRANSFER_ACCOMPANY                 [char](1);  --是否陪同
ALTER TABLE [sctrans].[T_RPT_CASE_BASE_INFO]  ADD TRANSFER_ACCOMPANY_REASON          [nvarchar](320);  --陪同原因
ALTER  TABLE sctrans.[T_RPT_CASE_BASE_INFO]   ADD TRANSFER_ACCOMPANY_OTHERS_REASON   [varchar](1000);  --陪同选择原因其他手写原因
ALTER TABLE [sctrans].[T_RPT_CASE_BASE_INFO]  ADD TRANSFER_ACCOMPANY_CN              [nvarchar](100);  --是否陪同转译
ALTER TABLE [sctrans].[T_RPT_CASE_BASE_INFO]  ADD TRANSFER_ACCOMPANY_REASON_CN       [nvarchar](500);  --陪同原因转译



--下家房屋数量
ALTER TABLE [sctrans].[T_TO_SIGN] ADD [HOUSE_QUANTITY]  [char](1); --是否首套 0:首套 1：二套 2：多套
ALTER TABLE [sctrans].[T_RPT_CASE_BASE_INFO] ADD [SIGN_HOUSE_QUANTITY]  [char](1); --是否首套 0:首套 1：二套 2：多套
ALTER TABLE [sctrans].[T_RPT_CASE_BASE_INFO] ADD [SIGN_HOUSE_QUANTITY_CN]  [varchar](50);

--陪同原因
insert into sctrans.SYS_DICT(ID,CODE,PARENT_ID,IS_DEFAULT,ORDERBY,CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY,VERSION,IS_DELETED,TYPE,AVAILABLE,NAME)
values('1b6d05dc258142378e148094a948ecd6','accompany_reason',null,'0','201',GETDATE(),'0E7B291E94EB4CE8E0532429030AE021',GETDATE(),'0E7B291E94EB4CE8E0532429030AE021','0','0','loanlost_not_approve',1,'经纪人陪同原因')

insert into sctrans.SYS_DICT(ID,CODE,PARENT_ID,IS_DEFAULT,ORDERBY,CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY,VERSION,IS_DELETED,TYPE,AVAILABLE,NAME)
values('10668fbda7a645d6b389af240e3141a4','accompanyReason1','1b6d05dc258142378e148094a948ecd6','0','01',GETDATE(),'0E7B291E94EB4CE8E0532429030AE021',GETDATE(),'0E7B291E94EB4CE8E0532429030AE021','0','0','loanlost_not_approve',1,'收佣维护')

insert into sctrans.SYS_DICT(ID,CODE,PARENT_ID,IS_DEFAULT,ORDERBY,CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY,VERSION,IS_DELETED,TYPE,AVAILABLE,NAME)
values('3fee88bc8b084aa297fbee1bef07b0c6','accompanyReason2','1b6d05dc258142378e148094a948ecd6','0','02',GETDATE(),'0E7B291E94EB4CE8E0532429030AE021',GETDATE(),'0E7B291E94EB4CE8E0532429030AE021','0','0','loanlost_not_approve',1,'客户要求')

insert into sctrans.SYS_DICT(ID,CODE,PARENT_ID,IS_DEFAULT,ORDERBY,CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY,VERSION,IS_DELETED,TYPE,AVAILABLE,NAME)
values('6c64056ff90b4489a32bd037f1e2a084','accompanyReason3','1b6d05dc258142378e148094a948ecd6','0','03',GETDATE(),'0E7B291E94EB4CE8E0532429030AE021',GETDATE(),'0E7B291E94EB4CE8E0532429030AE021','0','0','loanlost_not_approve',1,'经理要求')


--陪同附件
INSERT INTO  sctrans.SYS_DICT(ID,CODE,PARENT_ID,IS_DEFAULT,ORDERBY,CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY,VERSION,IS_DELETED,TYPE,AVAILABLE,NAME)

VALUES('2c9280845c3938e0015c81db3c680255','accompany_letter','8a8493d45095534b0150a72d023b644f',0,'02',GETDATE(),'0E7B291E94EB4CE8E0532429030AE021',GETDATE(),'0E7B291E94EB4CE8E0532429030AE021','0','0','yu_file_code','1','经纪人过户陪同确认单')

INSERT INTO  sctrans.T_TO_ACCESORY_LIST(PART_CODE,ACCESSORY_NAME,ACCESSORY_CODE)  VALUES('Guohu','经纪人过户陪同确认单','accompany_letter')

--过户审批驳回原因
INSERT INTO  sctrans.SYS_DICT(ID,CODE,PARENT_ID,IS_DEFAULT,ORDERBY,CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY,VERSION,IS_DELETED,TYPE,AVAILABLE,NAME)

VALUES('cf6c141e64d54efa91f48cf9fd9e40a7','reason15','ff80808155b49d450155b5a6e6890003',0,'16',GETDATE(),'0E7B291E94EB4CE8E0532429030AE021',GETDATE(),'0E7B291E94EB4CE8E0532429030AE021','0','0','guohu_not_approve','1','过户陪同确认单未上传')


update sctrans.SYS_DICT set ORDERBY='100' where ID='ba76da10a9574e05a21443eb04738d24'