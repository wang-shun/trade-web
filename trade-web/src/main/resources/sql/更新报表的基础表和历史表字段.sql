
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


ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD  [ELOAN_KA_APPLY_COUNT] [decimal](18, 0);
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD 	[ELOAN_KA_CARD_COUNT] [decimal](18, 0);
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD 	[ELOAN_KA_CARD_AMOUNT] [decimal](18, 2);
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD 	[ELOAN_KA_CARD_TYPE] [varchar](64);

exec [sctrans].[P_ALTER_COLUMN_FOR_MONTH_CASE_INFO] 201702,'ELOAN_KA_APPLY_COUNT,ELOAN_KA_CARD_COUNT,ELOAN_KA_CARD_AMOUNT,ELOAN_KA_CARD_TYPE',''
