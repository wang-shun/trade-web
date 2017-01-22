USE [sctrans_dev]
GO
/****** Object:  StoredProcedure [sctrans].[P_DAILY_REPORT_CASE_INFO]    Script Date: 2017/1/22 14:38:06 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		caoy
-- Create date: 2017-01-06
-- Description:	sctrans.T_RPT_DAYLY_REPORT_FORM_LOG 报表的每日生成数据的功能
-- =============================================
ALTER PROCEDURE [sctrans].[P_DAILY_REPORT_CASE_INFO]

AS
BEGIN
	DECLARE @update_date datetime;

	set @update_date = getdate();
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	BEGIN TRY
	 begin tran

	truncate table [sctrans].[T_RPT_CASE_BASE_INFO];
	with wf as
	(
		select act_t.ASSIGNEE_,act_t.END_TIME_,act_t.TASK_DEF_KEY_,act_t.PROC_INST_ID_,workflow.CASE_CODE,act_t.DELETE_REASON_ from sctrans.ACT_HI_TASKINST act_t with(nolock) left join sctrans.T_TO_WORKFLOW workflow with(nolock) on workflow.INST_CODE = act_t.PROC_INST_ID_
		WHERE act_t.DELETE_REASON_='completed'
	)
	INSERT INTO sctrans.T_RPT_CASE_BASE_INFO(
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
	MORTGAGET_FIN_ORG_CODE,--贷款机构
	MORTGAGET_IS_TMP_BANK,--是否是临时银行
	IS_LOSE,--是否流失
	CREATE_TIME
	)
SELECT
		C.[CASE_CODE],
		CA.[LOAN_REQ],--贷款需求
		CA.STATUS AS [CASE_STATUS],--案件状态
		CA.CASE_PROPERTY AS CASE_PROPERTY,--案件属性
		CA.[DISTRICT_ID] AS CASE_DISTRICT,--区域组织
		CA.[ORG_ID] AS CASE_ORG,--所属组织
		I.DISPATCH_TIME AS RECEIVED_TIME,--分单时间
		I.REQUIRE_PROCESSOR_ID AS RECEIVED_USER,--接收人
		(SELECT ts.org_id FROM sctrans.v_user_job_org_main as ts with(nolock) WHERE user_id=I.REQUIRE_PROCESSOR_ID) AS RECEIVED_TEAM_ID,--接单人组别（新增） 
		(SELECT DISTRICT_ID FROM sctrans.v_yucui_org_hierarchy with(nolock) WHERE TEAM_ID=(SELECT ts.org_id FROM sctrans.v_user_job_org_main as ts with(nolock) WHERE user_id=I.REQUIRE_PROCESSOR_ID)) AS RECEIVED_DISTRICT_ID,--接单人贵宾服务部
			
		(SELECT CON_PRICE FROM sctrans.T_TO_SIGN ts with(nolock) WHERE ts.CASE_CODE= C.CASE_CODE ) AS SIGN_CON_PRICE,--签约合同价
		(SELECT REAL_CON_TIME from  sctrans.T_TO_SIGN ts with(nolock) where ts.CASE_CODE=C.CASE_CODE ) AS SIGN_TIME,--签约时间	
		(SELECT top(1) ASSIGNEE_ FROM wf as ww WHERE ww.TASK_DEF_KEY_ = 'TransSign' AND ww.CASE_CODE=C.CASE_CODE   ORDER BY  END_TIME_ DESC ) AS SIGN_USER,--签约人
		(SELECT top (1) ORG_ID  FROM sctrans.SYS_USER with(nolock)  WHERE USERNAME = (SELECT top (1) ASSIGNEE_  FROM wf as ww  WHERE ww.TASK_DEF_KEY_ = 'TransSign' AND ww.CASE_CODE=C.CASE_CODE ORDER BY    END_TIME_ DESC )) as SIGN_TEAM_ID,--签约人组织	
		(SELECT DISTRICT_ID  FROM sctrans.v_yucui_org_hierarchy with(nolock) WHERE  TEAM_ID =(SELECT  top (1) ORG_ID  FROM sctrans.SYS_USER with(nolock)  WHERE USERNAME = (SELECT top 1 ASSIGNEE_ FROM  wf as ww WHERE ww.TASK_DEF_KEY_ = 'TransSign' AND ww.CASE_CODE=C.CASE_CODE  ORDER BY  END_TIME_ DESC))) as SIGN_DISTRICT_ID,--签约人贵宾服务部 						
		th.REAL_HT_TIME as HOUSE_TRANFER_TIME ,--过户时间
		(SELECT top (1) ASSIGNEE_ FROM wf as ww WHERE ww.TASK_DEF_KEY_ = 'Guohu' AND ww.CASE_CODE=C.CASE_CODE ORDER BY END_TIME_ DESC ) as HOUSE_TRANFER_USER,--过户人
		(SELECT top (1) ORG_ID FROM sctrans.SYS_USER with(nolock) WHERE USERNAME = ( SELECT top 1 ASSIGNEE_ FROM wf as ww WHERE ww.TASK_DEF_KEY_ = 'Guohu' AND ww.CASE_CODE=C.CASE_CODE ORDER BY END_TIME_ DESC ) ) as HOUSE_TRANFER_TEAM_ID,--过户组别
		(SELECT DISTRICT_ID FROM sctrans.v_yucui_org_hierarchy with(nolock) WHERE TEAM_ID = ( SELECT top (1) ORG_ID FROM sctrans.SYS_USER with(nolock) WHERE USERNAME = ( SELECT top 1 ASSIGNEE_ FROM wf as ww WHERE ww.TASK_DEF_KEY_ = 'Guohu' AND ww.CASE_CODE=C.CASE_CODE ORDER BY END_TIME_ DESC ) ) ) as HOUSE_TRANFER_DISTRICT_ID,--过户贵宾服务部		
		(SELECT top (1) ww.END_TIME_ FROM wf as ww WHERE ww.TASK_DEF_KEY_ = 'GuohuApprove' AND ww.CASE_CODE=C.CASE_CODE ORDER BY END_TIME_ DESC )  AS HOURSE_TRANSFER_APPROVE_DATE,--过户审批时间		
		
		(select top(1) V.LONG_ FROM wf as ww   left join sctrans.ACT_HI_VARINST V on ww.PROC_INST_ID_=v.PROC_INST_ID_ WHERE ww.TASK_DEF_KEY_ = 'GuohuApprove' AND V.NAME_='GuohuApprove' AND ww.CASE_CODE=C.CASE_CODE ORDER BY END_TIME_ DESC		 )AS HOURSE_TRANSFER_AGREE_STATUS,--过户审批状态 1是通过 0是驳回
		
		(SELECT APPROVE_TIME from  sctrans.t_to_close  ts with(nolock) where ts.CASE_CODE=C.CASE_CODE ) AS CLOSE_TIME,--结案时间	
		(SELECT top (1) ASSIGNEE_ FROM wf as ww WHERE ww.TASK_DEF_KEY_ = 'CaseClose' AND ww.CASE_CODE=C.CASE_CODE ORDER BY END_TIME_ DESC   ) AS CLOSE_USER,--结案用户
		(SELECT top (1) ORG_ID FROM sctrans.SYS_USER with(nolock) WHERE USERNAME = ( SELECT top 1 ASSIGNEE_ FROM wf as ww WHERE ww.TASK_DEF_KEY_ = 'CaseClose' AND ww.CASE_CODE=C.CASE_CODE ORDER BY END_TIME_ DESC )  ) as CLOSE_TEAM_ID,--结案店组
		(SELECT DISTRICT_ID FROM sctrans.v_yucui_org_hierarchy with(nolock) WHERE TEAM_ID = ( SELECT top (1) ORG_ID FROM sctrans.SYS_USER with(nolock) WHERE USERNAME = ( SELECT top 1 ASSIGNEE_ FROM wf as ww WHERE ww.TASK_DEF_KEY_ = 'CaseClose' AND ww.CASE_CODE=C.CASE_CODE ORDER BY END_TIME_ DESC ) )  ) as CLOSE_DISTRICT_ID,--结案贵宾服务部
		
		MG.SIGN_DATE as MORTAGE_SIGN_DATE,--贷款签约时间
		MG.MORT_TYPE AS MORTGAGE_LOAN_TYPE,--贷款类型
		MG.MORT_TOTAL_AMOUNT AS MORTGAGET_TOTAL_AMOUNT,--贷款总额
		case
			when  MG.MORT_TYPE='30016001' THEN MG.MORT_TOTAL_AMOUNT
			when  MG.MORT_TYPE='30016002' THEN (MG.MORT_TOTAL_AMOUNT-convert(decimal(18, 2),isnull(MG.PRF_AMOUNT,0)))
			else 0
		end MORTGAGET_COM_AMOUNT,--商业款金额
		case
			when  MG.MORT_TYPE='30016003' then MG.MORT_TOTAL_AMOUNT 
			when  MG.MORT_TYPE='30016002' THEN (MG.MORT_TOTAL_AMOUNT-convert(decimal(18, 2),isnull(MG.COM_AMOUNT,0)))
			else 0
		end MORTGAGET_PRF_AMOUNT,--公积金贷款金额		
		
		ISNULL(MG.LAST_LOAN_BANK,MG.FIN_ORG_CODE) AS MORTGAGET_FIN_ORG_CODE,--贷款机构
		MG.IS_TMP_BANK AS MORTGAGET_IS_TMP_BANK,--是否是临时银行
		case 
			when CA.LOAN_REQ=1 and MG.IS_DELEGATE_YUCUI=1 and (MG.MORT_TYPE='30016001' or MG.MORT_TYPE ='30016002' ) THEN  '0'--没流失
			when CA.LOAN_REQ=1 and  MG.IS_DELEGATE_YUCUI=0 and (MG.MORT_TYPE='30016001' or MG.MORT_TYPE ='30016002' ) THEN  '1'--流失	
		end   IS_LOSE,--是否流失
		GETDATE() as CREATE_TIME

		FROM      
		(select distinct case_code from SCTRANS.T_TO_CASE with(nolock)) C 
		INNER join SCTRANS.T_TO_CASE CA with(nolock) on c.CASE_CODE=CA.CASE_CODE
		LEFT JOIN sctrans.T_TO_MORTGAGE MG with(nolock) ON  MG.CASE_CODE=C.CASE_CODE AND MG.IS_ACTIVE=1 AND (MG.LAST_LOAN_BANK is not null and MG.LAST_LOAN_BANK<>'')
		LEFT JOIN SCTRANS.T_TO_CASE_INFO I with(nolock) 			ON C.CASE_CODE = I.CASE_CODE
		LEFT JOIN sctrans.T_TO_HOUSE_TRANSFER TH with(nolock)       ON TH.CASE_CODE=C.CASE_CODE
		where 1=1

		commit tran;

	END TRY

	BEGIN CATCH
	--回滚
	    rollback tran;
		select error_message();
		THROW;
	END CATCH

END
