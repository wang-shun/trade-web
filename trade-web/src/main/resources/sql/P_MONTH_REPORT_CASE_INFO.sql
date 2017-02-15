USE [sctrans_dev]
GO
/****** Object:  StoredProcedure [sctrans].[P_MONTH_REPORT_CASE_INFO]    Script Date: 2017/2/15 15:41:03 ******/
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
	CASE_DISTRICT_ID,
	CASE_ORG_ID_F,
	CASE_DISPATCH_TIME,
	CASE_CON_PRICE,
	CASE_REAL_CON_TIME,
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
	IS_TMP_BANK,
	IS_RUWEI_BANK,
	CASE_REC_STATUS,
	LOAN_LOST_AMOUNT,
	CREATE_TIME,
	BELONG_MONTH
	)
SELECT
	CASE_CODE,
	CASE_LOAN_REQ,
	CASE_STATUS,
	CASE_PROPERTY,
	CASE_DISTRICT_ID,
	CASE_ORG_ID_F,
	CASE_DISPATCH_TIME,
	CASE_CON_PRICE,
	CASE_REAL_CON_TIME,
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
	IS_TMP_BANK,
	IS_RUWEI_BANK,
	CASE_REC_STATUS,
	LOAN_LOST_AMOUNT,
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

