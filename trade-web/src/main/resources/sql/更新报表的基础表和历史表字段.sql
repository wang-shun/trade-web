
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

/*alter table sctrans.T_RPT_CASE_BASE_INFO  add TAX_HOUSE_HODING_TAX    decimal(18,2)
alter table sctrans.T_RPT_CASE_BASE_INFO  add TAX_PERSONAL_INCOME_TAX decimal(18,2)
alter table sctrans.T_RPT_CASE_BASE_INFO  add TAX_BUSINESS_TAX        decimal(18,2)
alter table sctrans.T_RPT_CASE_BASE_INFO  add TAX_CONTRACT_TAX        decimal(18,2)
alter table sctrans.T_RPT_CASE_BASE_INFO  add TAX_LAND_INCREMENT_TAX  decimal(18,2)


alter table sctrans.T_RPT_CASE_BASE_INFO  add TRANSFER_HOUSE_HODING_TAX    decimal(18,2)
alter table sctrans.T_RPT_CASE_BASE_INFO  add TRANSFER_PERSONAL_INCOME_TAX decimal(18,2)
alter table sctrans.T_RPT_CASE_BASE_INFO  add TRANSFER_BUSINESS_TAX        decimal(18,2)
alter table sctrans.T_RPT_CASE_BASE_INFO  add TRANSFER_CONTRACT_TAX        decimal(18,2)
alter table sctrans.T_RPT_CASE_BASE_INFO  add TRANSFER_LAND_INCREMENT_TAX  decimal(18,2)*/


ALTER TABLE [sctrans].[T_TO_SIGN] ADD [HOUSE_QUANTITY]  [char](1);

ALTER TABLE [sctrans].[T_RPT_CASE_BASE_INFO] ADD [SIGN_HOUSE_QUANTITY]  [char](1);
ALTER TABLE [sctrans].[T_RPT_CASE_BASE_INFO] ADD [SIGN_HOUSE_QUANTITY_CN]  [varchar](50);