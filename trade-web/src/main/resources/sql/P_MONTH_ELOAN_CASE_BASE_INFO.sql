USE [sctrans_dev]
GO
/****** Object:  StoredProcedure [sctrans].[P_MONTH_ELOAN_CASE_BASE_INFO]    Script Date: 2017/3/31 9:37:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		caoy
-- Create date: 2017-03-22
-- Description:	每个月的sctrans.T_RPT_ELOAN_CASE_BASE_INFO备份与报表数据的生成
-- =============================================
ALTER PROCEDURE [sctrans].[P_MONTH_ELOAN_CASE_BASE_INFO](
    @belong_month int = 0
)
	
AS
BEGIN
	DECLARE @update_date datetime;
	DECLARE @update_year INT;
	DECLARE @update_month INT;


  IF @belong_month = 0
		BEGIN
			set @belong_month = year(getdate())*100 + month(dateadd(month,-1,getdate()));
			set @update_year =year(getdate());
			set @update_month =month(dateadd(month,-1,getdate()));
		END
  

	set @update_date = getdate();
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	BEGIN TRY
	 begin tran
	--删除当月统计的数据
    DELETE  FROM sctrans.T_RPT_ELOAN_CASE_BASE_INFO
    WHERE   BELONG_MONTH = @belong_month;

	INSERT INTO sctrans.[T_RPT_ELOAN_CASE_BASE_INFO](
	[ELOAN_CODE],
	[CASE_CODE],
	[LOAN_SRV_CODE],
	[FIN_ORG_CODE],
	[CUST_NAME],
	[CUST_PHONE],
	[APPLY_TIME],
	[APPLY_AMOUNT],
	[MONTH],
	[SIGN_AMOUNT],
	[SIGN_TIME],
	[EXCUTOR_ID] ,
	[EXCUTOR_TEAM] ,
	[EXCUTOR_DISTRICT] ,
	[ZJ_NAME],
	[ZJ_CODE],
	[CO_NAME],
	[CO_CODE],
	[CO_PART],
	[PD_CODE],
	[PD_NAME],
	[PD_PART],
	[APPLY_CONF_TIME],
	[APPLY_CONF_USER],
	[SIGN_CONF_TIME],
	[SIGN_CONF_USER],
	[IS_REL_FINISH],
	[CHARGE_AMOUNT],
	[REMARK],
	[STATUS],
	[ABAN_REASON],
	[ABAN_TIME],
	[LOANER_ID],
	[LOANER_NAME],
	[LOANER_ORG_CODE],
	[LOANER_ORG_ID],
	[LOANER_PHONE],
	[CUST_PAPER],
	[CUST_CARD_TYPE],
	[CREATE_TIME],
	[BELONG_MONTH],
	[BELONG_FOR_YEAR],
	[BELONG_FOR_MONTH]
	)
SELECT
	[ELOAN_CODE],
	[CASE_CODE],
	[LOAN_SRV_CODE],
	[FIN_ORG_CODE],
	[CUST_NAME],
	[CUST_PHONE],
	[APPLY_TIME],
	[APPLY_AMOUNT],
	[MONTH],
	[SIGN_AMOUNT],
	[SIGN_TIME],
	[EXCUTOR_ID] ,
	[EXCUTOR_TEAM] ,
	[EXCUTOR_DISTRICT] ,
	[ZJ_NAME],
	[ZJ_CODE],
	[CO_NAME],
	[CO_CODE],
	[CO_PART],
	[PD_CODE],
	[PD_NAME],
	[PD_PART],
	[APPLY_CONF_TIME],
	[APPLY_CONF_USER],
	[SIGN_CONF_TIME],
	[SIGN_CONF_USER],
	[IS_REL_FINISH],
	[CHARGE_AMOUNT],
	[REMARK],
	[STATUS],
	[ABAN_REASON],
	[ABAN_TIME],
	[LOANER_ID],
	[LOANER_NAME],
	[LOANER_ORG_CODE],
	[LOANER_ORG_ID],
	[LOANER_PHONE],
	[CUST_PAPER],
	[CUST_CARD_TYPE],
	
	GETDATE(),
	@belong_month,--所属月份
	@update_year,
	@update_month
	FROM    
	  SCTRANS.[T_TO_ELOAN_CASE]
	  commit tran;
	END TRY

	BEGIN CATCH
	--回滚
	    rollback tran;
		select error_message();
		THROW;
	END CATCH

END

