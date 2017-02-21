
DROP TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO]
CREATE TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO](
	[PKID] [bigint] IDENTITY(1,1) NOT NULL,
	[CASE_CODE] [varchar](32) NULL,
	[CASE_LOAN_REQ] [varchar](5) NULL,
	[CASE_STATUS] [nvarchar](10) NULL,
	[CASE_PROPERTY] [nvarchar](10) NULL,

	[CASE_LEADING_PROCESS_ID_F] [varchar](64) NULL,--案件主办userID
	[CASE_REAL_NAME_F] [varchar](32) NULL,--案件主办姓名
	[CASE_ORG_NAME_F] [varchar](64) NULL,--组别名称
	[CASE_DISTRICT_ID] [varchar](64) NULL,--贵宾服务部ID
	[CASE_DISTRICT_NAME] [varchar](64) NULL,--贵宾服务部名称
	[CASE_ORG_ID_F] [varchar](64) NULL,
	[CASE_DISPATCH_TIME] [datetime] NULL,
	[CASE_CON_PRICE] [decimal](18, 2) NULL,
	[CASE_REAL_CON_TIME] [datetime] NULL,
	[EVA_EVAL_FEE] [decimal](18, 2) NULL,--案件评估费
	[EVA_RECORD_TIME] [datetime] NULL,--案件评估费确实时间
	[ELOAN_PRO] [varchar](256) NULL,--案件对应的金融类产品
	[ELOAN_PRO_AMOUNT] [decimal](18, 2) NULL,--金融产品总金额
	[ELOAN_KA] [varchar](64) NULL,--案件对应的卡类产品
	[CASE_USE_CARD_PAY_CN] [varchar](10) NULL,--案件是否刷卡
	[CASE_CARD_PAY_AMOUNT] [decimal](18, 2) NULL,--刷卡金额



	[TRANSFER_REAL_HT_TIME] [datetime] NULL,
	[TRANSFER_APP_TIME] [datetime] NULL,
	[TRANSFER_ISPASS] [numeric](19, 0) NULL,
	[MORT_SIGN_DATE] [datetime] NULL,
	[MORT_TYPE] [varchar](32) NULL,
	[MORT_TOTAL_AMOUNT] [decimal](18, 2) NULL,
	[MORT_COM_AMOUNT] [decimal](18, 2) NULL,
	[MORT_PRF_AMOUNT] [decimal](18, 2) NULL,
	[MORT_FA_FIN_ORG_CODE] [varchar](32) NULL,
	[MORT_FIN_ORG_CODE] [varchar](32) NULL,
	[MORT_FIN_BRANCH_NAME] [varchar](64) NULL,--银行名称
	[MORT_FIN_SUB_BRANCH_NAME] [varchar](64) NULL,--支行名称
	
	
	[IS_TMP_BANK] [varchar](2) NULL,
	[IS_RUWEI_BANK] [varchar](100) NULL,
	[IS_DELEGATE_YUCUI] [varchar](2) NULL,--是否中原办理
	[CASE_REC_STATUS] [varchar](32) NULL,
	[LOAN_LOST_AMOUNT] [decimal](18, 2) NULL,
	
	[JQYDS_NAME] [varchar](32) NULL,--区域董事姓名
	[JQYDS_EMPLOYEE_CODE] [varchar](32) NULL,--区域董事雇员编号
	[JQYZJ_NAME] [varchar](32) NULL,--区域总监姓名
	[JQYZJ_CODE] [varchar](32) NULL,--区域总监雇员编号
	[JQYJL_NAME] [varchar](32) NULL,--区域经理姓名
	[JQYJL_EMPLOYEE_CODE] [varchar](32) NULL,--区域经理雇员编号
	[JFHJL_NAME] [varchar](32) NULL,--分行经理姓名	
	[JFHJL_EMPLOYEE_CODE] [varchar](32) NULL,--分行经理雇员编号	


	[CREATE_TIME] [datetime] NULL,
	[BELONG_MONTH] [int] NULL
) ON [PRIMARY]

