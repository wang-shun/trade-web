USE [sctrans_dev]
GO
/****** Object:  StoredProcedure [sctrans].[P_MONTH_REPORT_CASE_INFO]    Script Date: 2017/2/8 10:10:54 ******/
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

	--删除当月统计的数据
    DELETE  FROM sctrans.T_RPT_HISTORY_CASE_BASE_INFO
    WHERE   BELONG_MONTH = @belong_month;

	INSERT INTO sctrans.T_RPT_HISTORY_CASE_BASE_INFO(
	CASE_CODE,--案件编号
	LOAN_REQ,--贷款需求
	CASE_STATUS,--CASE_STATUS
	CASE_PROPERTY,--案件属性
	DISTRICT_ID,--所属贵宾服务部
	TEAM_ID,--所属组织
	RECEIVED_TIME,--接单时间
	RECEIVED_USER,--接单人
	RECEIVED_TEAM_ID,--接单人组别
	RECEIVED_DISTRICT_ID,--接单人贵宾服务部
	
	SIGN_CON_PRICE,--签约合同价
	SIGN_TIME,--签约时间
	SIGN_USER,--签约人
	SIGN_TEAM_ID,--签约人组织
	SIGN_DISTRICT_ID,--签约人贵宾服务部	
	HOUSE_TRANFER_TIME,--过户时间	
	HOUSE_TRANFER_USER,--过户人
	HOUSE_TRANFER_TEAM_ID,--过户组别
	HOUSE_TRANFER_DISTRICT_ID,--过户贵宾服务部	
	HOURSE_TRANSFER_APPROVE_DATE,--过户审批时间--------xiugai!!!!!
	HOURSE_TRANSFER_AGREE_STATUS,--过户审批状态	
	CLOSE_TIME,--结案时间
	CLOSE_USER,--结案用户
	CLOSE_TEAM_ID,--结案店组
	CLOSE_DISTRICT_ID,--结案贵宾服务部
	
	MORTAGE_SIGN_DATE,--贷款签约时间
	MORTGAGE_LOAN_TYPE,--贷款类型
	MORTGAGET_TOTAL_AMOUNT,--贷款总额
	MORTGAGET_COM_AMOUNT,--商业贷款金额
	MORTGAGET_PRF_AMOUNT,--公积金贷款金额
	FA_FIN_ORG_CODE,--贷款机构父类
	MORTGAGET_FIN_ORG_CODE,--贷款机构
	MORTGAGET_IS_TMP_BANK,--是否是临时银行
	RUWEI_BANK,--是否是入围银行
	IS_LOSE,--是否流失
	LOST_AMOUNT,--贷款流失金额
	CREATE_TIME,
	BELONG_MONTH--所属月份
	)
SELECT
	CASE_CODE,--案件编号
	LOAN_REQ,--贷款需求
	CASE_STATUS,--CASE_STATUS
	CASE_PROPERTY,--案件属性
	DISTRICT_ID,--所属贵宾服务部
	TEAM_ID,--所属组织
	RECEIVED_TIME,--接单时间
	RECEIVED_USER,--接单人
	RECEIVED_TEAM_ID,--接单人组别
	RECEIVED_DISTRICT_ID,--接单人贵宾服务部
	SIGN_CON_PRICE,--签约合同价
	SIGN_TIME,--签约时间
	SIGN_USER,--签约人
	SIGN_TEAM_ID,--签约人组织
	SIGN_DISTRICT_ID,--签约人贵宾服务部	
	HOUSE_TRANFER_TIME,--过户时间	
	HOUSE_TRANFER_USER,--过户人
	HOUSE_TRANFER_TEAM_ID,--过户组别
	HOUSE_TRANFER_DISTRICT_ID,--过户贵宾服务部	
	HOURSE_TRANSFER_APPROVE_DATE,--过户审批时间--------xiugai!!!!!
	HOURSE_TRANSFER_AGREE_STATUS,--过户审批状态	
	CLOSE_TIME,--结案时间
	CLOSE_USER,--结案用户
	CLOSE_TEAM_ID,--结案店组
	CLOSE_DISTRICT_ID,--结案贵宾服务部
	
	MORTAGE_SIGN_DATE,--贷款签约时间
	MORTGAGE_LOAN_TYPE,--贷款类型
	MORTGAGET_TOTAL_AMOUNT,--贷款总额
	MORTGAGET_COM_AMOUNT,--商业贷款金额
	MORTGAGET_PRF_AMOUNT,--公积金贷款金额
	FA_FIN_ORG_CODE,--贷款机构父类
	MORTGAGET_FIN_ORG_CODE,--贷款机构
	MORTGAGET_IS_TMP_BANK,--是否是临时银行
	RUWEI_BANK,--是否是入围银行
	IS_LOSE,--是否流失
	LOST_AMOUNT,--贷款流失金额
	GETDATE(),
	@belong_month--所属月份

	FROM    
	  SCTRANS.T_RPT_CASE_BASE_INFO

	END TRY

	BEGIN CATCH
		

	END CATCH

END

