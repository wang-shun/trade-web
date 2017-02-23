USE [sctrans_dev]
GO
/****** Object:  StoredProcedure [sctrans].[P_MONTH_REPORT_CASE_INFO]    Script Date: 2017/2/23 10:24:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		caoy
-- Create date: 2017-01-10
-- Description:	每个月的sctrans.T_RPT_CASE_BASE_INFO备份与报表数据的生成
-- =============================================
ALTER PROCEDURE [sctrans].[P_MONTH_REPORT_CASE_INFO](
    @belong_month int = 0
)
	
AS
BEGIN
	DECLARE @update_date datetime;



  IF @belong_month = 0
		BEGIN
			set @belong_month = year(getdate())*100 + month(dateadd(month,-1,getdate()));
		END
  

	set @update_date = getdate();
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	BEGIN TRY
	 begin tran
	--删除当月统计的数据
    DELETE  FROM sctrans.T_RPT_HISTORY_CASE_BASE_INFO
    WHERE   BELONG_MONTH = @belong_month;

	INSERT INTO sctrans.T_RPT_HISTORY_CASE_BASE_INFO(
	CASE_CODE,
	CASE_LOAN_REQ,
	CASE_STATUS,
	CASE_PROPERTY,
	[CASE_LEADING_PROCESS_ID_F],--案件主办userID
	[CASE_REAL_NAME_F],--案件主办姓名
	[CASE_ORG_NAME_F],--组别名称
	[CASE_DISTRICT_ID],--贵宾服务部ID
	[CASE_DISTRICT_NAME],--贵宾服务部名称
	CASE_ORG_ID_F,
	CASE_DISPATCH_TIME,
	CASE_CON_PRICE,
	CASE_REAL_CON_TIME,
	[EVA_EVAL_FEE],--案件评估费
	[EVA_RECORD_TIME],--案件评估费确实时间
	[ELOAN_PRO],--案件对应的金融类产品
	[ELOAN_PRO_AMOUNT],--金融产品总金额
	[ELOAN_KA],--案件对应的卡类产品
	[ELOAN_KA_AMOUNT],--卡类产品金额
	[CASE_USE_CARD_PAY_CN],--案件是否刷卡
	[CASE_CARD_PAY_AMOUNT],--刷卡金额
	[TRANSFER_APP_PASS_TIME],--过户审批通过时间
	TRANSFER_REAL_HT_TIME,
	TRANSFER_APP_TIME,
	TRANSFER_ISPASS,
	MORT_SIGN_DATE,
	MORT_TYPE,
	MORT_TOTAL_AMOUNT,
	MORT_COM_AMOUNT,
	MORT_PRF_AMOUNT,
	MORT_FA_FIN_ORG_CODE,
	MORT_FIN_ORG_CODE,
	[MORT_FIN_BRANCH_NAME],--银行名称
	[MORT_FIN_SUB_BRANCH_NAME],--支行名称

	IS_TMP_BANK,
	IS_RUWEI_BANK,
	[IS_DELEGATE_YUCUI],--是否中原办理
	CASE_REC_STATUS,
	LOAN_LOST_AMOUNT,
	[JQYDS_NAME],--区域董事姓名
	[JQYDS_EMPLOYEE_CODE],--区域董事雇员编号
	[JQYZJ_NAME],--区域总监姓名
	[JQYZJ_CODE],--区域总监雇员编号
	[JQYJL_NAME],--区域经理姓名
	[JQYJL_EMPLOYEE_CODE],--区域经理雇员编号
	[JFHJL_NAME],--分行经理姓名	
	[JFHJL_EMPLOYEE_CODE],--分行经理雇员编号	
	CREATE_TIME,
	BELONG_MONTH
	)
SELECT
	CASE_CODE,
	CASE_LOAN_REQ,
	CASE_STATUS,
	CASE_PROPERTY,
	[CASE_LEADING_PROCESS_ID_F],--案件主办userID
	[CASE_REAL_NAME_F],--案件主办姓名
	[CASE_ORG_NAME_F],--组别名称
	[CASE_DISTRICT_ID],--贵宾服务部ID
	[CASE_DISTRICT_NAME],--贵宾服务部名称
	CASE_ORG_ID_F,
	CASE_DISPATCH_TIME,
	CASE_CON_PRICE,
	CASE_REAL_CON_TIME,
	[EVA_EVAL_FEE],--案件评估费
	[EVA_RECORD_TIME],--案件评估费确实时间
	[ELOAN_PRO],--案件对应的金融类产品
	[ELOAN_PRO_AMOUNT],--金融产品总金额
	[ELOAN_KA],--案件对应的卡类产品
	[ELOAN_KA_AMOUNT],--卡类产品金额
	[CASE_USE_CARD_PAY_CN],--案件是否刷卡
	[CASE_CARD_PAY_AMOUNT],--刷卡金额
	[TRANSFER_APP_PASS_TIME],--过户审批通过时间
	TRANSFER_REAL_HT_TIME,
	TRANSFER_APP_TIME,
	TRANSFER_ISPASS,
	MORT_SIGN_DATE,
	MORT_TYPE,
	MORT_TOTAL_AMOUNT,
	MORT_COM_AMOUNT,
	MORT_PRF_AMOUNT,
	MORT_FA_FIN_ORG_CODE,
	MORT_FIN_ORG_CODE,
	[MORT_FIN_BRANCH_NAME],--银行名称
	[MORT_FIN_SUB_BRANCH_NAME],--支行名称
	IS_TMP_BANK,
	IS_RUWEI_BANK,
	[IS_DELEGATE_YUCUI],--是否中原办理
	CASE_REC_STATUS,
	LOAN_LOST_AMOUNT,
	[JQYDS_NAME],--区域董事姓名
	[JQYDS_EMPLOYEE_CODE],--区域董事雇员编号
	[JQYZJ_NAME],--区域总监姓名
	[JQYZJ_CODE],--区域总监雇员编号
	[JQYJL_NAME],--区域经理姓名
	[JQYJL_EMPLOYEE_CODE],--区域经理雇员编号
	[JFHJL_NAME],--分行经理姓名	
	[JFHJL_EMPLOYEE_CODE],--分行经理雇员编号	
	GETDATE(),
	@belong_month--所属月份

	FROM    
	  SCTRANS.T_RPT_CASE_BASE_INFO
	  commit tran;
	END TRY

	BEGIN CATCH
	--回滚
	    rollback tran;
		select error_message();
		THROW;
	END CATCH

END

