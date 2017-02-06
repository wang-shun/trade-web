
/**已执行过
ALTER TABLE [sctrans].[T_RPT_CASE_BASE_INFO] ADD  MORTAGE_SIGN_DATE datetime;
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] ADD  MORTAGE_SIGN_DATE datetime;**/

/*已执行过
ALTER TABLE [sctrans].[T_RPT_CASE_BASE_INFO] alter column  SIGN_CON_PRICE [decimal](18, 2);
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] alter column  SIGN_CON_PRICE [decimal](18, 2);
*/


ALTER TABLE [sctrans].[T_RPT_CASE_BASE_INFO] alter column  SIGN_CON_PRICE [decimal](18, 2);
ALTER TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO] alter column  SIGN_CON_PRICE [decimal](18, 2);