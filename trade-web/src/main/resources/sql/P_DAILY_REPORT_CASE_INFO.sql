USE [sctrans_dev]
GO

/****** Object:  StoredProcedure [sctrans].[P_DAILY_REPORT_CASE_INFO]    Script Date: 2017/2/4 14:36:40 ******/  
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<zhuody>
-- Create date: <2017-02-04>
-- Description:	<caseBaseInfo table>
-- =============================================
CREATE PROCEDURE [sctrans].[P_DAILY_REPORT_CASE_INFO]
AS
BEGIN

	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	BEGIN TRY
		BEGIN  TRAN
	--DELETE DATA
		TRUNCATE TABLE [sctrans].[T_RPT_CASE_BASE_INFO];
	
    -- Insert statements for procedure here
		INSERT INTO sctrans.[T_RPT_CASE_BASE_INFO]
           (	
				CASE_PKID                   ,
				CASE_CODE                   ,
				CASE_STATUS                 ,
				CASE_STATUS_CN              ,
				CASE_PROPERTY               ,
				CASE_PROPERTY_CN            ,
				HOUSR_PROPERTY_CODE         ,
				HOUSR_PROPERTY_ADDR         ,
				HOUSR_DIST_CODE             ,
				HOUSR_DIST_CODE_CN          ,
				HOUSR_SQUARE                ,
				CASE_ORIGIN                 ,
				CASE_ORIGIN_CN              ,
				CASE_LOAN_REQ               ,
				CASE_LOAN_REQ_CN            ,
				CASE_LEADING_PROCESS_ID_F   ,
				CASE_EMPLOYEE_CODE_F        ,
				CASE_USERNAME_F             ,
				CASE_REAL_NAME_F            ,
				CASE_MOBILE_F               ,
				CASE_ORG_ID_F               ,
				CASE_ORG_NAME_F             ,
				CASE_LEADING_PROCESS_ID_B   ,
				CASE_EMPLOYEE_CODE_B        ,
				CASE_USERNAME_B             ,
				CASE_REAL_NAME_B            ,
				CASE_MOBILE_B               ,
				CASE_ORG_ID_B               ,
				CASE_ORG_NAME_B             ,
				CASE_DIRECTOR_REAL_NAME     ,
				CASE_DIRECTOR_USER_ID       ,
				CASE_DIRECTOR_MOBILE        ,
				CASE_DIRECTOR_ORG_ID        ,
				CASE_DIRECTOR_ORG_NAME      ,
				CASE_MANAGER_REAL_NAME      ,
				CASE_MANAGER_USER_ID        ,
				CASE_MANAGER_MOBILE         ,
				CASE_MANAGER_ORG_ID         ,
				CASE_MANAGER_ORG_NAME       ,
				CASE_ASSISTANT_REAL_NAME    ,
				CASE_ASSISTANT_USER_ID      ,
				CASE_ASSISTANT_MOBILE       ,
				CASE_ASSISTANT_ORG_ID       ,
				CASE_ASSISTANT_ORG_NAME     ,
				CASE_ISSUBSCRIBE            ,
				CASE_TARGET_CODE            ,
				CASE_DISTRICT_ID            ,
				CASE_DISTRICT_NAME          ,
				CTM_CODE                    ,
				AGENT_NAME                  ,
				AGENT_CODE                  ,
				AGENT_PHONE                 ,
				AGENT_USERNAME              ,
				GRP_CODE                    ,
				GRP_NAME                    ,
				AR_CODE                     ,
				AR_NAME                     ,
				SWZ_CODE                    ,
				SWZ_NAME                    ,
				WZ_CODE                     ,
				WZ_NAME                     ,
				BA_CODE                     ,
				BA_NAME                     ,
				JQYJL_NAME                  ,
				JQYJL_EMPLOYEE_CODE         ,
				JQYJL_PHONE                 ,
				JQYZJ_NAME                  ,
				JQYZJ_CODE                  ,
				JQYZJ_PHONE                 ,
				JQYDS_NAME                  ,
				JQYDS_EMPLOYEE_CODE         ,
				JQYDS_PHONE                 ,
				JFHJL_NAME                  ,
				JFHJL_EMPLOYEE_CODE         ,
				JFHJL_PHONE                 ,
				GUEST_NAME_UP               ,
				GUEST_PHONE_UP              ,
				GUEST_NAME_DOWN             ,
				GUEST_PHONE_DOWN            ,
				MORT_TYPE                   ,
				MORT_TYPE_CN                ,
				MORT_TOTAL_AMOUNT           ,
				MORT_COM_AMOUNT             ,
				MORT_COM_DISCOUNT           ,
				MORT_PRF_AMOUNT             ,
				MORT_CUST_NAME              ,
				MORT_CUST_PHONE             ,
				MORT_LOANER_NAME            ,
				MORT_LOANER_PHONE           ,
				IS_LOANER_ARRIVE            ,
				IS_LOANER_ARRIVE_CN         ,
				IS_DELEGATE_YUCUI           ,
				IS_DELEGATE_YUCUI_CN        ,
				IS_TMP_BANK                 ,
				IS_TMP_BANK_CN              ,
				IS_RUWEI_BANK				,
				MORT_FIN_ORG_CODE           ,
				MORT_FA_FIN_ORG_CODE		,
				MORT_FIN_BRANCH_NAME        ,
				MORT_FIN_SUB_BRANCH_NAME    ,
				MORT_SIGN_DATE              ,
				MORT_APPR_DATE              ,
				MORT_LEND_DATE              ,
				CASE_REC_STATUS             ,
				CASE_REC_STATUS_CN          ,
				LOAN_LOST_AMOUNT			,
				LOAN_SELF_DEL_REASON        ,
				LOAN_LOST_APPLY_REASON      ,
				LOAN_REC_LETTER_NO          ,
				LOAN_LOST_CONFIRM_CODE      ,
				LOAN_CREATE_TIME            ,
				LOAN_APP_TIME               ,
				LOAN_APP_OPE_NAME           ,
				LOAN_APP_OPE_USERID         ,
				LOAN_APP_OPE_ORGID          ,
				LOAN_APP_OPE_ORGNAME        ,
				LOAN_AGENT_USER_ID          ,
				LOAN_AGENT_REAL_NAME        ,
				LOAN_AGENT_ORG_ID           ,
				LOAN_AGENT_ORG_NAME         ,
				CASE_CREATE_TIME            ,
				CASE_DISPATCH_TIME          ,
				CASE_REAL_CON_TIME          ,
				CASE_CON_PRICE              ,
				CASE_REAL_PRICE             ,
				CASE_HOUSE_UNIT_PRICE       ,
				CASE_TAX_TIME               ,
				CASE_PRICING_TIME           ,
				CASE_TAX_PRICING            ,
				CASE_REAL_PLS_TIME          ,
				TRANSFER_REAL_HT_TIME       ,
				TRANSFER_CREATE_TIME        ,
				TRANSFER_SUB_TIME           ,
				TRANSFER_APP_TIME           ,
				TRANSFER_APP_PASS_TIME      ,
				TRANSFER_TRADE_NAME         ,
				TRANSFER_COMMENT            ,
				TRANSFER_ISPASS             ,
				TRANSFER_ISPASS_CN          ,
				TRANSFER_APP_OPERATOR_USERID,
				TRANSFER_APP_OPERATOR_NAME  ,
				TRANSFER_CONTENT			,
				TRANSFER_NOT_APPROVE        ,
				CASE_REAL_PROPERTY_GET_TIME ,
				CASE_CLOSE_TIME             ,
				CASE_EVA_COMPANY            ,
				SPV_TYPE					,
				SPV_TYPE_CN					,
				SPV_AMOUNT					,
				SPV_SIGN_TIME				,
				EVA_EVAL_FEE                ,
				EVA_RECORD_TIME             ,
				ELOAN_PRO                   ,
				ELOAN_PRO_AMOUNT            ,
				ELOAN_KA                    ,
				ELOAN_KA_AMOUNT_STR         ,
				ELOAN_KA_AMOUNT             ,
				CASE_USE_CARD_PAY           ,
				CASE_USE_CARD_PAY_CN        ,
				CASE_CARD_PAY_AMOUNT        ,
				INFO_CREATE_BY              ,
				INFO_CREATE_TIME            
                                 		   
		   )
			SELECT  
				C.PKID,
				C.CASE_CODE,
				C.STATUS,
				(SELECT  TOP 1 D1.NAME  FROM sctrans.SYS_DICT D1 with(nolock) WHERE D1.CODE = C.STATUS AND D1.IS_DELETED = '0') STATUS_CN,
				C.CASE_PROPERTY,
				(SELECT  TOP 1 D2.NAME  FROM sctrans.SYS_DICT D2 with(nolock) WHERE D2.CODE = C.CASE_PROPERTY AND D2.IS_DELETED = '0') CASE_PROPERTY_CN,
				P.PROPERTY_CODE,
				P.PROPERTY_ADDR,
				P.DIST_CODE,
				(SELECT  TOP 1 D3.NAME  FROM sctrans.SYS_DICT D3 with(nolock) WHERE D3.CODE = P.DIST_CODE AND D3.IS_DELETED = '0' ) DIST_CODE_CN,		
				P.SQUARE,

				C.CASE_ORIGIN,
				CASE 
					WHEN C.CASE_ORIGIN = 'CTM'    THEN  '推送案件'--案件来源
					WHEN C.CASE_ORIGIN = 'INPUT'  THEN  '自录案件'
					WHEN C.CASE_ORIGIN = 'MERGE'  THEN  '合流案件'
					ELSE   '待确认' 
				END   CASE_ORIGIN_CN,		
				C.LOAN_REQ,
				CASE 
					WHEN C.LOAN_REQ = 1  THEN  '有'--有贷款需求
					WHEN C.LOAN_REQ = 0  THEN  '无'--无
					ELSE   '不确定' 
				END   LOAN_REQ_CN,
				--前台组人员信息
				C.LEADING_PROCESS_ID,
				V1.EMPLOYEE_CODE,
				V1.USERNAME,
				V1.REAL_NAME,
				V1.MOBILE,
				C.ORG_ID,
				(SELECT ORG_NAME FROM sctrans.SYS_ORG with(nolock) WHERE ID = C.ORG_ID)ORG_NAME_F,
		
				--后台组人员信息		
				SIP.PROCESSOR_ID,
				V2.EMPLOYEE_CODE,
				V2.USERNAME,
				V2.REAL_NAME,
				V2.MOBILE,
				SIP.ORG_ID,
				(SELECT ORG_NAME FROM sctrans.SYS_ORG with(nolock) WHERE ID = SIP.ORG_ID)ORG_NAME_B,
		

				--案件总监信息
				A1.REAL_NAME,
				A1.USER_ID,
				A1.MOBILE,
				A1.ORG_ID,
				A1.ORG_NAME,
				--主管信息
				A2.REAL_NAME,
				A2.USER_ID,
				A2.MOBILE,
				A2.ORG_ID,
				A2.ORG_NAME,
				--助理信息
				A3.REAL_NAME,
				A3.USER_ID,
				A3.MOBILE,
				A3.ORG_ID,
				A3.ORG_NAME,
		
				CASE
					WHEN (SELECT  COUNT(*)  FROM  sctrans.T_TO_MODULE_SUBSCRIBE with(nolock) WHERE MODULE_CODE = C.CASE_CODE) >0 THEN '是'
					ELSE '否'
				END CASE_ISSUBSCRIBE,--案件是否被关注
				CI.TARGET_CODE,
				C.DISTRICT_ID,
				(SELECT O1.ORG_NAME FROM sctrans.SYS_ORG O1 with(nolock)  WHERE O1.ID = C.DISTRICT_ID )DISTRICT_ID_CN,

				CI.CTM_CODE,
				CI.AGENT_NAME,
				CI.AGENT_CODE,
				CI.AGENT_PHONE,
				CI.AGENT_USERNAME,--登录用户名
				CI.GRP_CODE,
				CI.GRP_NAME,
				CI.AR_CODE,
				CI.AR_NAME,
				CI.SWZ_CODE,
				CI.SWZ_NAME,	
				CI.WZ_CODE,
				CI.WZ_NAME,
				CI.BA_CODE,
				CI.BA_NAME,

				SH.JQYJL_MGR,
				SH.JQYDS_MGR_CODE,--EMPLOYCODE
				SH.JQYDS_PHONE,
				SH.JQYZJ_MGR,
				SH.JQYZJ_MGR_CODE,
				SH.JQYZJ_PHONE,
				SH.JQYDS_MGR,
				SH.JQYDS_MGR_CODE,
				SH.JQYDS_PHONE,
				SH.JFHJL_MGR,
				SH.JFHJL_MGR_CODE,
				SH.JFHJL_PHONE,
				--上下家信息
				(SELECT STUFF((SELECT ','+ GUEST_NAME FROM sctrans.T_TG_GUEST_INFO NUP with(nolock) WHERE NUP.CASE_CODE = C.CASE_CODE AND TRANS_POSITION = '30006001' for xml path('')),1,1,''))GUEST_NAME_UP,
				(SELECT STUFF((SELECT ','+ GUEST_PHONE FROM sctrans.T_TG_GUEST_INFO PUP with(nolock) WHERE PUP.CASE_CODE = C.CASE_CODE AND TRANS_POSITION = '30006001' for xml path('')),1,1,''))GUEST_PHONE_UP,
				(SELECT STUFF((SELECT ','+ GUEST_NAME FROM sctrans.T_TG_GUEST_INFO NDW with(nolock) WHERE NDW.CASE_CODE = C.CASE_CODE AND TRANS_POSITION = '30006002' for xml path('')),1,1,''))GUEST_NAME_DOWN,
				(SELECT STUFF((SELECT ','+ GUEST_PHONE FROM sctrans.T_TG_GUEST_INFO PDW with(nolock) WHERE PDW.CASE_CODE = C.CASE_CODE AND TRANS_POSITION = '30006002' for xml path('')),1,1,''))GUEST_PHONE_DOWN,

				M.MORT_TYPE,
				(SELECT  TOP 1 D4.NAME  FROM sctrans.SYS_DICT D4 with(nolock) WHERE D4.CODE = M.MORT_TYPE AND D4.IS_DELETED = '0') MORT_TYPE_CN,
				M.MORT_TOTAL_AMOUNT,
				M.COM_AMOUNT,
				M.COM_DISCOUNT,
				M.PRF_AMOUNT,

				M.CUST_NAME,
				(SELECT  TG.GUEST_PHONE FROM sctrans.T_TG_GUEST_INFO TG with(nolock) WHERE TG.PKID = M.CUST_CODE)CUST_PHONE,
				M.LOANER_NAME,--主贷人
				M.LOANER_PHONE,
				M.IS_LOANER_ARRIVE,
				CASE 
					WHEN M.IS_LOANER_ARRIVE = 1  THEN  '是'--信贷员签约是否到场
					WHEN M.IS_LOANER_ARRIVE = 0  THEN  '否'--否
					ELSE   '不确定' 
				END   IS_LOANER_ARRIVE_CN,
				M.IS_DELEGATE_YUCUI,
				CASE 
					WHEN M.IS_DELEGATE_YUCUI = 1  THEN  '是'--是否中原办理
					WHEN M.IS_DELEGATE_YUCUI = 0  THEN  '否'--否
					ELSE   '不确定' 
				END   IS_DELEGATE_YUCUI_CN,
				M.IS_TMP_BANK,
				CASE 
					WHEN M.IS_TMP_BANK = 1  THEN  '是'--是否临时银行
					WHEN M.IS_TMP_BANK = 0  THEN  '否'--否
					ELSE   '不确定' 
				END   IS_TMP_BANK_CN,
				(SELECT TOP 1 S.TAGS from sctrans.T_TS_SUP S with(nolock) WHERE S.FIN_ORG_CODE = ISNULL(M.LAST_LOAN_BANK,M.FIN_ORG_CODE)) AS RUWEI_BANK,
				ISNULL(M.LAST_LOAN_BANK,M.FIN_ORG_CODE) FIN_ORG_CODE,
				ISNULL((SELECT FA_FIN_ORG_CODE FROM sctrans.T_TS_FIN_ORG with(nolock) WHERE FIN_ORG_CODE = ISNULL(M.LAST_LOAN_BANK,M.FIN_ORG_CODE)),ISNULL(M.LAST_LOAN_BANK,M.FIN_ORG_CODE))FA_FIN_ORG_CODE,
				(SELECT FIN_ORG_NAME_YC FROM sctrans.T_TS_FIN_ORG FFO with(nolock) WHERE FFO.FIN_ORG_CODE = (SELECT FA_FIN_ORG_CODE FROM sctrans.T_TS_FIN_ORG with(nolock) WHERE FIN_ORG_CODE = ISNULL(M.LAST_LOAN_BANK,M.FIN_ORG_CODE)))MORT_FIN_BRANCH_NAME,
				(SELECT FIN_ORG_NAME_YC FROM sctrans.T_TS_FIN_ORG ZFO with(nolock) WHERE ZFO.FIN_ORG_CODE = ISNULL(M.LAST_LOAN_BANK,M.FIN_ORG_CODE)) MORT_FIN_SUB_BRANCH_NAME,
				M.SIGN_DATE,--签贷
				M.APPR_DATE,--批贷
				M.LEND_DATE,--放贷		
				CASE 
					WHEN C.LOAN_REQ=1 and  M.IS_DELEGATE_YUCUI=1 and M.MORT_TYPE IN ('30016001' ,'30016002' ) THEN  '0'--没流失
					WHEN C.LOAN_REQ=1 and  M.IS_DELEGATE_YUCUI=0 and M.MORT_TYPE IN ('30016001' ,'30016002' ) THEN  '1'--流失					
				END   CASE_REC_STATUS,
				CASE 
					WHEN C.LOAN_REQ=1 and  M.IS_DELEGATE_YUCUI=1 and M.MORT_TYPE IN ('30016001' ,'30016002' ) THEN  '收单'--没流失
					WHEN C.LOAN_REQ=1 and  M.IS_DELEGATE_YUCUI=0 and M.MORT_TYPE IN ('30016001' ,'30016002' ) THEN  '流失'--流失	
					ELSE '不确定'
				END   CASE_REC_STATUS_CN,
				CASE 
					WHEN C.LOAN_REQ=1 and M.IS_DELEGATE_YUCUI=1 and M.MORT_TYPE IN ('30016001' ,'30016002' ) THEN  0 --没流失
					WHEN C.LOAN_REQ=1 and M.IS_DELEGATE_YUCUI=0 and M.MORT_TYPE IN ('30016001' ,'30016002' ) THEN  M.COM_AMOUNT--流失	
				END LOAN_LOST_AMOUNT,--贷款流失金额

				M.SELF_DEL_REASON,
				M.LOANLOST_APPLY_REASON,
				M.REC_LETTER_NO,-- 推荐函编号
				M.LOANLOST_CONFIRM_CODE,--贷款流失确认函编号
				M.CREATE_TIME, --贷款流失申请时间
				--100
				WA.END_TIME_, --贷款流失审核时间
				(SELECT U.REAL_NAME FROM sctrans.SYS_USER U  with(nolock) WHERE U.USERNAME = WA.ASSIGNEE_)LOAN_APP_OPE_NAME,
				(SELECT U.ID FROM sctrans.SYS_USER U with(nolock) WHERE U.USERNAME = WA.ASSIGNEE_)LOAN_APP_OPE_USERID,
				(SELECT U.ORG_ID FROM sctrans.SYS_USER U with(nolock) WHERE U.USERNAME = WA.ASSIGNEE_)LOAN_APP_OPE_ORGID,
				(SELECT ORG_NAME FROM sctrans.SYS_ORG with(nolock) WHERE ID=(SELECT TOP 1 U.ORG_ID FROM sctrans.SYS_USER U with(nolock) WHERE U.USERNAME = WA.ASSIGNEE_))LOAN_APP_OPE_ORGNAME,

		
				M.LOAN_AGENT,--贷款专员userId
				(SELECT U.REAL_NAME FROM sctrans.SYS_USER U with(nolock) WHERE U.ID = M.LOAN_AGENT)LOAN_AGENT_NAME,
				M.LOAN_AGENT_TEAM, --贷款专员orgId
				(SELECT ORG.ORG_NAME FROM sctrans.SYS_ORG ORG with(nolock) WHERE ORG.ID = M.LOAN_AGENT_TEAM) LOAN_AGENT_ORG_NAME,

				--相关时间信息
				C.CREATE_TIME,--报单日期
				CI.DISPATCH_TIME,--分单日期
				--案件预计完成时间
				--(SELECT  TP.EST_PART_TIME  FROM  sctrans.T_TO_TRANS_PLAN TP WHERE TP.CASE_CODE = C.CASE_CODE) CASE_EST_PART_TIME,   有重复
				S.REAL_CON_TIME,
				S.CON_PRICE,
				S.REAL_PRICE,

				CASE 		       	
					WHEN  P.SQUARE = 0	THEN  ROUND(CONVERT(FLOAT,S.REAL_PRICE)/CONVERT(FLOAT,1),2) 	
					ELSE  ROUND(CONVERT(FLOAT,S.REAL_PRICE)/ISNULL(CONVERT(FLOAT,P.SQUARE),1),2)
				END  CASE_HOUSE_UNIT_PRICE,	 --过户单价
				(SELECT  TAX.TAX_TIME  FROM  sctrans.T_TO_TAX TAX  with(nolock) WHERE TAX.CASE_CODE = C.CASE_CODE) CASE_TAX_TIME,

				(SELECT  P1.PRICING_TIME  FROM  sctrans.T_TO_PRICING P1 with(nolock) WHERE P1.CASE_CODE = C.CASE_CODE) CASE_PRICING_TIME, --核价时间
				(SELECT  P2.TAX_PRICING  FROM  sctrans.T_TO_PRICING P2 with(nolock) WHERE P2.CASE_CODE = C.CASE_CODE) CASE_TAX_PRICING, --税务核定价格
				(SELECT  PS.REAL_PLS_TIME  FROM  sctrans.T_TO_PURCHASE_LIMIT_SEARCH PS with(nolock) WHERE PS.CASE_CODE = C.CASE_CODE) CASE_REAL_PLS_TIME, --税务核定价格
				HT.REAL_HT_TIME,--实际过户时间
				HT.CREATE_TIME, --过户创建时间
				(SELECT MIN(AT.END_TIME_) FROM SCTRANS.ACT_HI_TASKINST AT with(nolock) WHERE AT.TASK_DEF_KEY_=N'Guohu'  AND AT.PROC_INST_ID_= W.INST_CODE ) TRANSFER_SUB_TIME,
				(SELECT MAX(AT.END_TIME_) FROM SCTRANS.ACT_HI_TASKINST AT with(nolock) WHERE AT.TASK_DEF_KEY_=N'GuohuApprove'  AND AT.PROC_INST_ID_= W.INST_CODE) TRANSFER_APP_TIME,
				--(SELECT AV.LAST_UPDATED_TIME_ FROM SCTRANS.ACT_HI_VARINST AV with(nolock) WHERE AV.NAME_=N'GuohuApprove'  AND AV.LONG_=1   AND AV.PROC_INST_ID_= W.INST_CODE) TRANSFER_LAST_UPDATED_TIME_,
				(SELECT MAX(END_TIME_) FROM SCTRANS.ACT_HI_TASKINST AT  with(nolock)  INNER JOIN  SCTRANS.ACT_HI_VARINST AV  with(nolock) ON AT.PARENT_TASK_ID_ = AV.PROC_INST_ID_ AND AV.LONG_ = 1  AND AV.NAME_ =N'GuohuApprove' WHERE AT.TASK_DEF_KEY_=N'GuohuApprove'  AND  AT.DELETE_REASON_ = 'completed' AND AT.PROC_INST_ID_=W.INST_CODE) TRANSFER_LAST_UPDATED_TIME,
				(SELECT REAL_NAME FROM  sctrans.SYS_USER with(nolock) WHERE USERNAME =(SELECT TOP 1 AT.ASSIGNEE_ FROM SCTRANS.ACT_HI_TASKINST AT with(nolock) WHERE AT.TASK_DEF_KEY_=N'Guohu'  AND AT.PROC_INST_ID_= W.INST_CODE  ORDER BY AT.END_TIME_ DESC))TRANSFER_TRADE_NAME,
				HT.COMMENT,
				(SELECT AV.LONG_ FROM SCTRANS.ACT_HI_VARINST AV with(nolock) WHERE AV.NAME_=N'GuohuApprove'   AND  AV.PROC_INST_ID_= W.INST_CODE) TRANSFER_LONG_,
		
				CASE 
					WHEN (SELECT AV.LONG_ FROM SCTRANS.ACT_HI_VARINST AV with(nolock) WHERE AV.NAME_=N'GuohuApprove'   AND AV.PROC_INST_ID_= W.INST_CODE)=1 THEN  '是'--没流失
					WHEN (SELECT AV.LONG_ FROM SCTRANS.ACT_HI_VARINST AV with(nolock) WHERE AV.NAME_=N'GuohuApprove'   AND AV.PROC_INST_ID_= W.INST_CODE)=0 THEN  '否'--流失	
					ELSE '不确定'
				END   TRANSFER_LONG,
			  
				(SELECT  TOP 1 OPERATOR  FROM sctrans.T_TO_APPROVE_RECORD with(nolock)  WHERE  PART_CODE =N'GuohuApprove' AND CASE_CODE = C.CASE_CODE ORDER BY OPERATOR_TIME DESC)TRANSFER_APP_OPERATOR,
				(SELECT U.REAL_NAME FROM sctrans.SYS_USER U with(nolock) WHERE U.ID = ((SELECT  TOP 1 OPERATOR  FROM sctrans.T_TO_APPROVE_RECORD  with(nolock) WHERE  PART_CODE =N'GuohuApprove' AND CASE_CODE = C.CASE_CODE ORDER BY OPERATOR_TIME DESC)))TRANSFER_APP_NAME,
				(SELECT  TOP 1 CONTENT  FROM sctrans.T_TO_APPROVE_RECORD  with(nolock) WHERE  PART_CODE =N'GuohuApprove' AND CASE_CODE = C.CASE_CODE  ORDER BY  OPERATOR_TIME  DESC)TRANSFER_CONTENT,
				(SELECT  TOP 1 NOT_APPROVE  FROM sctrans.T_TO_APPROVE_RECORD  with(nolock) WHERE  PART_CODE =N'GuohuApprove' AND CASE_CODE = C.CASE_CODE  ORDER BY  OPERATOR_TIME  DESC)TRANSFER_NOT_APPROVE,
		
		
				/* 截止上面 查询不会出现多条记录的情况*/

				(SELECT  GB.REAL_PROPERTY_GET_TIME  FROM  sctrans.T_TO_GET_PROPERTY_BOOK GB with(nolock) WHERE GB.CASE_CODE = C.CASE_CODE) CASE_REAL_PROPERTY_GET_TIME, --实际领证时间
				C.ClOSE_TIME,--结案时间
				(SELECT FIN_ORG_NAME FROM sctrans.T_TS_FIN_ORG with(nolock) WHERE FIN_ORG_CODE = (SELECT TOP 1 FIN_ORG_CODE FROM sctrans.T_TO_EVA_REPORT with(nolock)  WHERE CASE_CODE = C.CASE_CODE))CASE_FIN_ORG_NAME,-- 案件评估公司
				
				(SELECT STUFF((SELECT  ','+S1.SPV_TYPE  FROM sctrans.T_TO_SPV S1 with(nolock) WHERE S1.IS_DELETED = '0' AND S1.CASE_CODE = C.CASE_CODE for xml path('')),1,1,'')) SPV_TYPE,
				 NULL,
				(SELECT STUFF((SELECT  ','+CONVERT(NVARCHAR,S2.AMOUNT)  FROM sctrans.T_TO_SPV S2 with(nolock) WHERE S2.IS_DELETED = '0' AND S2.CASE_CODE = C.CASE_CODE for xml path('')),1,1,'')) SPV_AMOUNT,
				(SELECT STUFF((SELECT  ','+CONVERT(NVARCHAR,S3.SIGN_TIME,20)  FROM sctrans.T_TO_SPV S3 with(nolock) WHERE S3.IS_DELETED = '0' AND S3.CASE_CODE = C.CASE_CODE for xml path('')),1,1,'')) SPV_SIGN_TIME,				
				
				TR.EVAL_FEE,--评估费 单位元
				TR.RECORD_TIME,

				(SELECT STUFF((SELECT ','+D.NAME FROM  SCTRANS.SYS_DICT D with(nolock) WHERE D.CODE IN (SELECT EC.LOAN_SRV_CODE FROM SCTRANS.T_TO_ELOAN_CASE EC with(nolock) WHERE  EC.CASE_CODE=C.CASE_CODE AND EC.LOAN_SRV_CODE NOT IN ('30004005','30004015') AND EC.STATUS != 'ABAN') AND D.TYPE='yu_serv_cat_code_tree' AND (D.TAG LIKE '%Eloan%' or D.TAG LIKE '%eplus%') for xml path('')),1,1,''))  ELOAN_PRO_TYPE,
				(SELECT SUM(EC.APPLY_AMOUNT)  FROM sctrans.T_TO_ELOAN_CASE EC with(nolock) WHERE EC.CASE_CODE = C.CASE_CODE  AND  EC.LOAN_SRV_CODE NOT IN ('30004005','30004015') AND EC.STATUS != 'ABAN' ) ELOAN_APPLYAMOUNT_COUNT,
				(SELECT STUFF((SELECT ','+D.NAME FROM  SCTRANS.SYS_DICT D with(nolock) WHERE D.CODE IN (SELECT EC.LOAN_SRV_CODE FROM SCTRANS.T_TO_ELOAN_CASE EC with(nolock) WHERE  EC.CASE_CODE=C.CASE_CODE AND EC.LOAN_SRV_CODE IN ('30004005','30004015') AND EC.STATUS != 'ABAN' ) AND D.TYPE='yu_serv_cat_code_tree' AND (D.TAG LIKE '%Eloan%' or D.TAG LIKE '%eplus%') for xml path('')),1,1,''))  ELOAN_PRO_TYPE_KA,
				(SELECT STUFF((SELECT  ','+CAST(EC.APPLY_AMOUNT as varchar) FROM SCTRANS.T_TO_ELOAN_CASE EC with(nolock) WHERE  EC.CASE_CODE=C.CASE_CODE AND EC.LOAN_SRV_CODE IN ('30004005','30004015') AND EC.STATUS != 'ABAN'  for xml path('')),1,1,'')) AS ELOAN_KA_AMOUNT_STR,
				(SELECT SUM(EC.APPLY_AMOUNT)  FROM sctrans.T_TO_ELOAN_CASE EC with(nolock)  WHERE EC.CASE_CODE = C.CASE_CODE  AND  EC.LOAN_SRV_CODE  IN ('30004005','30004015') AND EC.STATUS != 'ABAN' ) ELOAN_KA_AMOUNT,

				HT.USE_CARD_PAY,--是否刷卡
				CASE 
					WHEN  HT.USE_CARD_PAY = 1 THEN  '是'--刷卡
					WHEN  HT.USE_CARD_PAY = 0 THEN  '否'--不刷卡	
					ELSE '不确定'
				END   CASE_USE_CARD_PAY_CN,
				HT.CARD_PAY_AMOUNT,--刷卡金额
				'CREATEBY_PROCEDURE',
				GETDATE()
	
		
			  FROM		 sctrans.T_TO_CASE C with(nolock)
			  LEFT JOIN  sctrans.T_TO_CASE_INFO CI with(nolock)				 ON C.CASE_CODE=CI.CASE_CODE
			  LEFT JOIN  sctrans.T_TG_SERV_ITEM_AND_PROCESSOR SIP with(nolock) ON SIP.CASE_CODE = C.CASE_CODE AND SIP.SRV_CODE = '3000401002'
			  LEFT JOIN  sctrans.SYS_USER U	with(nolock)					 ON CI.AGENT_CODE=U.ID
			  LEFT JOIN  sctrans.T_SYS_ORG_HIERARCHY SH	with(nolock)		 ON U.ORG_ID = SH.ORG_ID
			  LEFT JOIN  sctrans.T_TO_PROPERTY_INFO P with(nolock)		     ON C.CASE_CODE=P.CASE_CODE
			  LEFT JOIN  sctrans.V_USER_ORG_JOB_ACTIVE A1 with(nolock)		 ON A1.ORG_ID = (SELECT O.PARENT_ID FROM sctrans.SYS_ORG O WHERE O.ID= C.ORG_ID) AND A1.JOB_CODE='director'

			  LEFT JOIN  sctrans.V_USER_ORG_JOB_ACTIVE A2 with(nolock)		 ON A2.ORG_ID = C.ORG_ID AND A2.JOB_CODE='Manager'
			  LEFT JOIN  sctrans.V_USER_ORG_JOB_ACTIVE A3 with(nolock)		 ON A3.ORG_ID = C.ORG_ID AND A3.JOB_CODE='TeamAssistant'
			  LEFT JOIN  sctrans.SYS_USER V1	with(nolock)				 ON V1.ID = C.LEADING_PROCESS_ID
			  LEFT JOIN  sctrans.SYS_USER V2	with(nolock)				 ON V2.ID = SIP.PROCESSOR_ID
			  LEFT JOIN  sctrans.T_TO_MORTGAGE M with(nolock)				 ON M.CASE_CODE = C.CASE_CODE AND M.IS_ACTIVE = '1'
	  
			  LEFT JOIN(
	 				SELECT				
						W.CASE_CODE,
						ACT.END_TIME_,
						ACT.ASSIGNEE_								
					FROM
						sctrans.T_TO_WORKFLOW W with(nolock)
					INNER JOIN
						sctrans.ACT_HI_TASKINST ACT with(nolock) ON ACT.PROC_INST_ID_ = W.INST_CODE
						AND ACT.TASK_DEF_KEY_ in ('LoanlostApproveManager','LoanlostApproveDirector') and  DELETE_REASON_='completed'
						AND ACT.ID_ = (SELECT MAX(ID_) FROM sctrans.ACT_HI_TASKINST  WHERE PROC_INST_ID_ = ACT.PROC_INST_ID_  and   DELETE_REASON_='completed'
						AND TASK_DEF_KEY_ in ('LoanlostApproveManager','LoanlostApproveDirector'))
				
					WHERE W.BUSINESS_KEY='operation_process' AND W.STATUS IN (0,4)
			  )WA ON WA.CASE_CODE = C.CASE_CODE
			  LEFT JOIN  sctrans.T_TO_SIGN S	with(nolock)			ON S.CASE_CODE = C.CASE_CODE
			  LEFT JOIN  sctrans.T_TO_HOUSE_TRANSFER HT	with(nolock)	ON HT.CASE_CODE = C.CASE_CODE
			  LEFT JOIN  sctrans.T_TO_WORKFLOW W with(nolock)			ON C.CASE_CODE = W.CASE_CODE	AND  W.BUSINESS_KEY = 'operation_process' AND W.STATUS IN (0, 4)
			  LEFT JOIN  sctrans.T_TO_EVA_FEE_RECORD TR	with(nolock)	ON TR.CASE_CODE = C.CASE_CODE

		COMMIT TRAN;


		--定义变量
		
		DECLARE @PIKD_APPRECORD INT
		DECLARE @TRANSFER_NOT_APPROVE VARCHAR(200)
		DECLARE @sqlAppRecord VARCHAR(1000)
		
		DECLARE @PIKD_SPVTYPE INT
		DECLARE @SPV_TYPE_CN VARCHAR(200)
		DECLARE @sqlSpvType VARCHAR(1000)

		
		UPDATE sctrans.T_RPT_CASE_BASE_INFO SET TRANSFER_LAST_CONTENT = TRANSFER_CONTENT WHERE TRANSFER_NOT_APPROVE IS NULL
		--执行游标查询
		DECLARE AppRecordCursor CURSOR FOR SELECT PKID,TRANSFER_NOT_APPROVE FROM sctrans.[T_RPT_CASE_BASE_INFO] with(nolock) WHERE TRANSFER_NOT_APPROVE like '%reason%'
		--打开游标
		OPEN AppRecordCursor
		--循环游标
		WHILE 0=0 BEGIN
			FETCH NEXT FROM AppRecordCursor INTO  @PIKD_APPRECORD,@TRANSFER_NOT_APPROVE
			IF @@FETCH_STATUS<>0 BEGIN
				--没有记录，跳出游标循环
				BREAK
			END
			SET @sqlAppRecord ='UPDATE sctrans.T_RPT_CASE_BASE_INFO SET TRANSFER_LAST_CONTENT = ISNULL(TRANSFER_CONTENT,'''') + STUFF((SELECT '',''+NAME FROM (SELECT NAME FROM sctrans.SYS_DICT WHERE CODE='''+ replace(@TRANSFER_NOT_APPROVE,',',''' UNION ALL SELECT NAME FROM  sctrans.SYS_DICT WHERE CODE=''')+''')  temp for xml path('''')),1,1,'''') WHERE PKID = '+CONVERT(varchar(10),@PIKD_APPRECORD)
			EXEC (@sqlAppRecord)
		END

			
		--执行游标查询
		DECLARE SpvTypeCursor CURSOR FOR SELECT PKID,SPV_TYPE FROM sctrans.[T_RPT_CASE_BASE_INFO] with(nolock) WHERE SPV_TYPE IS NOT NULL
		--打开游标
		OPEN SpvTypeCursor
		--循环游标
		WHILE 0=0 BEGIN
			FETCH NEXT FROM SpvTypeCursor INTO @PIKD_SPVTYPE,@SPV_TYPE_CN
			IF @@FETCH_STATUS<>0 BEGIN
				--没有记录，跳出游标循环
				BREAK
			END
			SET @sqlSpvType ='UPDATE sctrans.T_RPT_CASE_BASE_INFO SET SPV_TYPE_CN = ISNULL(SPV_TYPE,'''') + STUFF((SELECT '',''+NAME FROM (SELECT NAME FROM sctrans.SYS_DICT WHERE TAG LIKE ''%Eloan%'' AND TYPE=''yu_serv_cat_code_tree'' AND CODE='''+ replace(@SPV_TYPE_CN,',',''' UNION ALL SELECT NAME FROM  sctrans.SYS_DICT WHERE TAG LIKE ''''%Eloan%'''' AND TYPE=''''yu_serv_cat_code_tree'''' AND CODE=''')+''')  temp for xml path('''')),1,1,'''') WHERE PKID = '+CONVERT(varchar(10),@PIKD_SPVTYPE)
			PRINT @sqlSpvType
			EXEC (@sqlSpvType)
		END
		



		--关闭游标
		CLOSE AppRecordCursor
		--销毁游标
		DEALLOCATE AppRecordCursor

		--关闭游标
		CLOSE SpvTypeCursor
		--销毁游标
		DEALLOCATE SpvTypeCursor

	END TRY

	BEGIN CATCH  
		 ROLLBACK TRAN;
		 --SELECT ERROR_MESSAGE() AS ErrorMessage;  
		SELECT ERROR_NUMBER() AS ErrorNumber  
        ,ERROR_SEVERITY() AS ErrorSeverity  
        ,ERROR_STATE() AS ErrorState  
        ,ERROR_PROCEDURE() AS ErrorProcedure  
        ,ERROR_LINE() AS ErrorLine  
        ,ERROR_MESSAGE() AS ErrorMessage; 

		--关闭游标
		CLOSE AppRecordCursor
		--销毁游标
		DEALLOCATE AppRecordCursor

				--关闭游标
		CLOSE SpvTypeCursor
		--销毁游标
		DEALLOCATE SpvTypeCursor
	END CATCH;  

END

GO

