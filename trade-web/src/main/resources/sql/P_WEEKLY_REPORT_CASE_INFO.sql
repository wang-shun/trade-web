USE sctrans_dev
GO
/****** Object:  StoredProcedure sctrans.P_MONTH_REPORT_CASE_INFO    Script Date: 2017/2/20 10:10:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		gongjd
-- Create date: 2017-02-20
-- Description:	每周的sctrans.T_RPT_CASE_BASE_INFO备份与报表数据的生成
-- =============================================
ALTER PROCEDURE sctrans.P_WEEKLY_REPORT_CASE_INFO(
    @belong_week int = 0
)
	
AS
BEGIN
	DECLARE @update_date datetime;
	DECLARE @weekday int;
	DECLARE @last_week_5 int;
 
  IF @belong_week = 0
		BEGIN
			--默认之前一个统计周期：如果今天是周5、6，默认本周四；如果今天是周1、2、3、4、7，默认上周四
			set @weekday = datepart(dw,getdate());
			IF @weekday = 6 or @weekday = 7
			BEGIN
				set @last_week_5 = dateadd(dw,5-datepart(dw,getdate()),getdate());
			END 
			ELSE IF  @weekday = 1 or @weekday = 2 or @weekday = 3 or @weekday = 4 or @weekday = 5
			BEGIN
				set @last_week_5 = dateadd(dw,-2-datepart(dw,getdate()),getdate());
			END 	
			
			set @belong_week = year(@last_week_5)*10000 + month(@last_week_5)*100 + day(@last_week_5);
		END
  

	set @update_date = getdate();
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	BEGIN TRY

	--删除当周统计的数据
    DELETE  FROM sctrans.T_RPT_WEEKLY_CASE_BASE_INFO
    WHERE   BELONG_WEEK_LAST_DAY = @belong_week;

	INSERT INTO sctrans.T_RPT_WEEKLY_CASE_BASE_INFO(
	CASE_PKID ,
	CASE_CODE ,
	CASE_STATUS ,
	CASE_STATUS_CN ,
	CASE_PROPERTY ,
	CASE_PROPERTY_CN ,
	HOUSR_PROPERTY_CODE ,
	HOUSR_PROPERTY_ADDR ,
	HOUSR_DIST_CODE ,
	HOUSR_DIST_CODE_CN ,
	HOUSR_SQUARE ,
	CASE_ORIGIN ,
	CASE_ORIGIN_CN ,
	CASE_LOAN_REQ ,
	CASE_LOAN_REQ_CN ,
	CASE_LEADING_PROCESS_ID_F ,
	CASE_EMPLOYEE_CODE_F ,
	CASE_USERNAME_F ,
	CASE_REAL_NAME_F ,
	CASE_MOBILE_F ,
	CASE_ORG_ID_F ,
	CASE_ORG_NAME_F ,
	CASE_LEADING_PROCESS_ID_B ,
	CASE_EMPLOYEE_CODE_B ,
	CASE_USERNAME_B ,
	CASE_REAL_NAME_B ,
	CASE_MOBILE_B ,
	CASE_ORG_ID_B ,
	CASE_ORG_NAME_B ,
	CASE_DIRECTOR_REAL_NAME ,
	CASE_DIRECTOR_USER_ID ,
	CASE_DIRECTOR_MOBILE ,
	CASE_DIRECTOR_ORG_ID ,
	CASE_DIRECTOR_ORG_NAME ,
	CASE_MANAGER_REAL_NAME ,
	CASE_MANAGER_USER_ID ,
	CASE_MANAGER_MOBILE ,
	CASE_MANAGER_ORG_ID ,
	CASE_MANAGER_ORG_NAME ,
	CASE_ASSISTANT_REAL_NAME ,
	CASE_ASSISTANT_USER_ID ,
	CASE_ASSISTANT_MOBILE ,
	CASE_ASSISTANT_ORG_ID ,
	CASE_ASSISTANT_ORG_NAME ,
	CASE_ISSUBSCRIBE ,
	CASE_TARGET_CODE ,
	CASE_DISTRICT_ID ,
	CASE_DISTRICT_NAME ,
	CTM_CODE ,
	AGENT_NAME ,
	AGENT_CODE ,
	AGENT_PHONE ,
	AGENT_USERNAME ,
	GRP_CODE ,
	GRP_NAME ,
	AR_CODE ,
	AR_NAME ,
	SWZ_CODE ,
	SWZ_NAME ,
	WZ_CODE ,
	WZ_NAME ,
	BA_CODE ,
	BA_NAME ,
	JQYJL_NAME ,
	JQYJL_EMPLOYEE_CODE ,
	JQYJL_PHONE ,
	JQYZJ_NAME ,
	JQYZJ_CODE ,
	JQYZJ_PHONE ,
	JQYDS_NAME ,
	JQYDS_EMPLOYEE_CODE ,
	JQYDS_PHONE ,
	JFHJL_NAME ,
	JFHJL_EMPLOYEE_CODE ,
	JFHJL_PHONE ,
	GUEST_NAME_UP ,
	GUEST_PHONE_UP ,
	GUEST_NAME_DOWN ,
	GUEST_PHONE_DOWN ,
	MORT_TYPE ,
	MORT_TYPE_CN ,
	MORT_TOTAL_AMOUNT ,
	MORT_COM_AMOUNT ,
	MORT_COM_DISCOUNT ,
	MORT_PRF_AMOUNT ,
	MORT_CUST_NAME ,
	MORT_CUST_PHONE ,
	MORT_LOANER_NAME ,
	MORT_LOANER_PHONE ,
	IS_LOANER_ARRIVE ,
	IS_LOANER_ARRIVE_CN ,
	IS_DELEGATE_YUCUI ,
	IS_DELEGATE_YUCUI_CN ,
	IS_TMP_BANK ,
	IS_TMP_BANK_CN ,
	IS_RUWEI_BANK ,
	MORT_FIN_ORG_CODE ,
	MORT_FA_FIN_ORG_CODE ,
	MORT_FIN_BRANCH_NAME ,
	MORT_FIN_SUB_BRANCH_NAME ,
	MORT_SIGN_DATE ,
	MORT_APPR_DATE ,
	MORT_LEND_DATE ,
	CASE_REC_STATUS ,
	CASE_REC_STATUS_CN ,
	LOAN_LOST_AMOUNT ,
	LOAN_SELF_DEL_REASON ,
	LOAN_LOST_APPLY_REASON ,
	LOAN_REC_LETTER_NO ,
	LOAN_LOST_CONFIRM_CODE ,
	LOAN_CREATE_TIME ,
	LOAN_APP_TIME ,
	LOAN_APP_OPE_NAME ,
	LOAN_APP_OPE_USERID ,
	LOAN_APP_OPE_ORGID ,
	LOAN_APP_OPE_ORGNAME ,
	LOAN_AGENT_USER_ID ,
	LOAN_AGENT_REAL_NAME ,
	LOAN_AGENT_ORG_ID ,
	LOAN_AGENT_ORG_NAME ,
	CASE_CREATE_TIME ,
	CASE_DISPATCH_TIME ,
	CASE_REAL_CON_TIME ,
	CASE_CON_PRICE ,
	CASE_REAL_PRICE ,
	CASE_HOUSE_UNIT_PRICE ,
	CASE_TAX_TIME ,
	CASE_PRICING_TIME ,
	CASE_TAX_PRICING ,
	CASE_REAL_PLS_TIME ,
	TRANSFER_REAL_HT_TIME ,
	TRANSFER_CREATE_TIME ,
	TRANSFER_SUB_TIME ,
	TRANSFER_APP_TIME ,
	TRANSFER_APP_PASS_TIME ,
	TRANSFER_TRADE_NAME ,
	TRANSFER_COMMENT ,
	TRANSFER_ISPASS ,
	TRANSFER_ISPASS_CN ,
	TRANSFER_APP_OPERATOR_USERID ,
	TRANSFER_APP_OPERATOR_NAME ,
	TRANSFER_CONTENT ,
	TRANSFER_NOT_APPROVE ,
	TRANSFER_LAST_CONTENT ,
	CASE_REAL_PROPERTY_GET_TIME ,
	CASE_CLOSE_TIME ,
	CASE_EVA_COMPANY ,
	SPV_TYPE ,
	SPV_TYPE_CN ,
	SPV_AMOUNT ,
	SPV_SIGN_TIME ,
	EVA_EVAL_FEE ,
	EVA_RECORD_TIME ,
	ELOAN_PRO ,
	ELOAN_PRO_AMOUNT ,
	ELOAN_KA ,
	ELOAN_KA_AMOUNT ,
	CASE_USE_CARD_PAY ,
	CASE_USE_CARD_PAY_CN ,
	CASE_CARD_PAY_AMOUNT ,
	CREATE_TIME ,
	BELONG_WEEK_LAST_DAY
	)
SELECT
	CASE_PKID ,
	CASE_CODE ,
	CASE_STATUS ,
	CASE_STATUS_CN ,
	CASE_PROPERTY ,
	CASE_PROPERTY_CN ,
	HOUSR_PROPERTY_CODE ,
	HOUSR_PROPERTY_ADDR ,
	HOUSR_DIST_CODE ,
	HOUSR_DIST_CODE_CN ,
	HOUSR_SQUARE ,
	CASE_ORIGIN ,
	CASE_ORIGIN_CN ,
	CASE_LOAN_REQ ,
	CASE_LOAN_REQ_CN ,
	CASE_LEADING_PROCESS_ID_F ,
	CASE_EMPLOYEE_CODE_F ,
	CASE_USERNAME_F ,
	CASE_REAL_NAME_F ,
	CASE_MOBILE_F ,
	CASE_ORG_ID_F ,
	CASE_ORG_NAME_F ,
	CASE_LEADING_PROCESS_ID_B ,
	CASE_EMPLOYEE_CODE_B ,
	CASE_USERNAME_B ,
	CASE_REAL_NAME_B ,
	CASE_MOBILE_B ,
	CASE_ORG_ID_B ,
	CASE_ORG_NAME_B ,
	CASE_DIRECTOR_REAL_NAME ,
	CASE_DIRECTOR_USER_ID ,
	CASE_DIRECTOR_MOBILE ,
	CASE_DIRECTOR_ORG_ID ,
	CASE_DIRECTOR_ORG_NAME ,
	CASE_MANAGER_REAL_NAME ,
	CASE_MANAGER_USER_ID ,
	CASE_MANAGER_MOBILE ,
	CASE_MANAGER_ORG_ID ,
	CASE_MANAGER_ORG_NAME ,
	CASE_ASSISTANT_REAL_NAME ,
	CASE_ASSISTANT_USER_ID ,
	CASE_ASSISTANT_MOBILE ,
	CASE_ASSISTANT_ORG_ID ,
	CASE_ASSISTANT_ORG_NAME ,
	CASE_ISSUBSCRIBE ,
	CASE_TARGET_CODE ,
	CASE_DISTRICT_ID ,
	CASE_DISTRICT_NAME ,
	CTM_CODE ,
	AGENT_NAME ,
	AGENT_CODE ,
	AGENT_PHONE ,
	AGENT_USERNAME ,
	GRP_CODE ,
	GRP_NAME ,
	AR_CODE ,
	AR_NAME ,
	SWZ_CODE ,
	SWZ_NAME ,
	WZ_CODE ,
	WZ_NAME ,
	BA_CODE ,
	BA_NAME ,
	JQYJL_NAME ,
	JQYJL_EMPLOYEE_CODE ,
	JQYJL_PHONE ,
	JQYZJ_NAME ,
	JQYZJ_CODE ,
	JQYZJ_PHONE ,
	JQYDS_NAME ,
	JQYDS_EMPLOYEE_CODE ,
	JQYDS_PHONE ,
	JFHJL_NAME ,
	JFHJL_EMPLOYEE_CODE ,
	JFHJL_PHONE ,
	GUEST_NAME_UP ,
	GUEST_PHONE_UP ,
	GUEST_NAME_DOWN ,
	GUEST_PHONE_DOWN ,
	MORT_TYPE ,
	MORT_TYPE_CN ,
	MORT_TOTAL_AMOUNT ,
	MORT_COM_AMOUNT ,
	MORT_COM_DISCOUNT ,
	MORT_PRF_AMOUNT ,
	MORT_CUST_NAME ,
	MORT_CUST_PHONE ,
	MORT_LOANER_NAME ,
	MORT_LOANER_PHONE ,
	IS_LOANER_ARRIVE ,
	IS_LOANER_ARRIVE_CN ,
	IS_DELEGATE_YUCUI ,
	IS_DELEGATE_YUCUI_CN ,
	IS_TMP_BANK ,
	IS_TMP_BANK_CN ,
	IS_RUWEI_BANK ,
	MORT_FIN_ORG_CODE ,
	MORT_FA_FIN_ORG_CODE ,
	MORT_FIN_BRANCH_NAME ,
	MORT_FIN_SUB_BRANCH_NAME ,
	MORT_SIGN_DATE ,
	MORT_APPR_DATE ,
	MORT_LEND_DATE ,
	CASE_REC_STATUS ,
	CASE_REC_STATUS_CN ,
	LOAN_LOST_AMOUNT ,
	LOAN_SELF_DEL_REASON ,
	LOAN_LOST_APPLY_REASON ,
	LOAN_REC_LETTER_NO n,
	LOAN_LOST_CONFIRM_CODE ,
	LOAN_CREATE_TIME ,
	LOAN_APP_TIME ,
	LOAN_APP_OPE_NAME ,
	LOAN_APP_OPE_USERID ,
	LOAN_APP_OPE_ORGID ,
	LOAN_APP_OPE_ORGNAME ,
	LOAN_AGENT_USER_ID ,
	LOAN_AGENT_REAL_NAME ,
	LOAN_AGENT_ORG_ID ,
	LOAN_AGENT_ORG_NAME ,
	CASE_CREATE_TIME ,
	CASE_DISPATCH_TIME ,
	CASE_REAL_CON_TIME ,
	CASE_CON_PRICE ,
	CASE_REAL_PRICE ,
	CASE_HOUSE_UNIT_PRICE ,
	CASE_TAX_TIME ,
	CASE_PRICING_TIME ,
	CASE_TAX_PRICING ,
	CASE_REAL_PLS_TIME ,
	TRANSFER_REAL_HT_TIME ,
	TRANSFER_CREATE_TIME ,
	TRANSFER_SUB_TIME ,
	TRANSFER_APP_TIME ,
	TRANSFER_APP_PASS_TIME ,
	TRANSFER_TRADE_NAME ,
	TRANSFER_COMMENT ,
	TRANSFER_ISPASS ,
	TRANSFER_ISPASS_CN ,
	TRANSFER_APP_OPERATOR_USERID ,
	TRANSFER_APP_OPERATOR_NAME ,
	TRANSFER_CONTENT ,
	TRANSFER_NOT_APPROVE ,
	TRANSFER_LAST_CONTENT ,
	CASE_REAL_PROPERTY_GET_TIME ,
	CASE_CLOSE_TIME ,
	CASE_EVA_COMPANY ,
	SPV_TYPE ,
	SPV_TYPE_CN ,
	SPV_AMOUNT ,
	SPV_SIGN_TIME ,
	EVA_EVAL_FEE ,
	EVA_RECORD_TIME ,
	ELOAN_PRO ,
	ELOAN_PRO_AMOUNT ,
	ELOAN_KA ,
	ELOAN_KA_AMOUNT ,
	CASE_USE_CARD_PAY ,
	CASE_USE_CARD_PAY_CN ,
	CASE_CARD_PAY_AMOUNT ,
	GETDATE(),
	@belong_week

	FROM    
	  SCTRANS.T_RPT_CASE_BASE_INFO

	END TRY

	BEGIN CATCH
		

	END CATCH

END

