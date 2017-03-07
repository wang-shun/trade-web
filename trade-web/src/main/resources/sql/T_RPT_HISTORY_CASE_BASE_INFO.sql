DROP TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO]
CREATE TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO](
	[PKID] [bigint] IDENTITY(1,1) NOT NULL,
	[CASE_PKID] [bigint] NULL,
	[CASE_CODE] [varchar](32) NULL,
	[CASE_STATUS] [nvarchar](10) NULL,
	[CASE_STATUS_CN] [nvarchar](20) NULL,
	[CASE_PROPERTY] [nvarchar](10) NULL,
	[CASE_PROPERTY_CN] [nvarchar](20) NULL,
	[HOUSR_PROPERTY_CODE] [varchar](64) NULL,
	[HOUSR_PROPERTY_ADDR] [nvarchar](256) NULL,
	[HOUSR_DIST_CODE] [varchar](64) NULL,
	[HOUSR_DIST_CODE_CN] [varchar](64) NULL,
	[HOUSR_SQUARE] [float] NULL,
	[CASE_ORIGIN] [varchar](20) NULL,
	[CASE_ORIGIN_CN] [varchar](40) NULL,
	[CASE_LOAN_REQ] [varchar](5) NULL,
	[CASE_LOAN_REQ_CN] [varchar](10) NULL,
	[CASE_LEADING_PROCESS_ID_F] [varchar](64) NULL,
	[CASE_EMPLOYEE_CODE_F] [varchar](32) NULL,
	[CASE_USERNAME_F] [varchar](32) NULL,
	[CASE_REAL_NAME_F] [varchar](32) NULL,
	[CASE_MOBILE_F] [varchar](32) NULL,
	[CASE_ORG_ID_F] [varchar](64) NULL,
	[CASE_ORG_NAME_F] [varchar](64) NULL,
	[CASE_LEADING_PROCESS_ID_B] [varchar](64) NULL,
	[CASE_EMPLOYEE_CODE_B] [varchar](32) NULL,
	[CASE_USERNAME_B] [varchar](32) NULL,
	[CASE_REAL_NAME_B] [varchar](32) NULL,
	[CASE_MOBILE_B] [varchar](32) NULL,
	[CASE_ORG_ID_B] [varchar](64) NULL,
	[CASE_ORG_NAME_B] [varchar](64) NULL,
	[CASE_DIRECTOR_REAL_NAME] [varchar](32) NULL,
	[CASE_DIRECTOR_USER_ID] [varchar](64) NULL,
	[CASE_DIRECTOR_MOBILE] [varchar](32) NULL,
	[CASE_DIRECTOR_ORG_ID] [varchar](64) NULL,
	[CASE_DIRECTOR_ORG_NAME] [varchar](64) NULL,
	[CASE_MANAGER_REAL_NAME] [varchar](32) NULL,
	[CASE_MANAGER_USER_ID] [varchar](64) NULL,
	[CASE_MANAGER_MOBILE] [varchar](32) NULL,
	[CASE_MANAGER_ORG_ID] [varchar](64) NULL,
	[CASE_MANAGER_ORG_NAME] [varchar](64) NULL,
	[CASE_ASSISTANT_REAL_NAME] [varchar](32) NULL,
	[CASE_ASSISTANT_USER_ID] [varchar](64) NULL,
	[CASE_ASSISTANT_MOBILE] [varchar](32) NULL,
	[CASE_ASSISTANT_ORG_ID] [varchar](64) NULL,
	[CASE_ASSISTANT_ORG_NAME] [varchar](64) NULL,
	[CASE_ISSUBSCRIBE] [varchar](20) NULL,
	[CASE_TARGET_CODE] [varchar](32) NULL,
	[CASE_DISTRICT_ID] [varchar](64) NULL,
	[CASE_DISTRICT_NAME] [varchar](64) NULL,
	[CTM_CODE] [varchar](32) NULL,
	[AGENT_NAME] [varchar](64) NULL,
	[AGENT_CODE] [varchar](32) NULL,
	[AGENT_PHONE] [nvarchar](32) NULL,
	[AGENT_USERNAME] [varchar](32) NULL,
	[GRP_CODE] [varchar](32) NULL,
	[GRP_NAME] [nvarchar](64) NULL,
	[AR_CODE] [varchar](32) NULL,
	[AR_NAME] [nvarchar](64) NULL,
	[SWZ_CODE] [varchar](32) NULL,
	[SWZ_NAME] [nvarchar](64) NULL,
	[WZ_CODE] [varchar](32) NULL,
	[WZ_NAME] [nvarchar](64) NULL,
	[BA_CODE] [varchar](32) NULL,
	[BA_NAME] [nvarchar](64) NULL,
	[JQYJL_NAME] [varchar](32) NULL,
	[JQYJL_EMPLOYEE_CODE] [varchar](32) NULL,
	[JQYJL_PHONE] [varchar](32) NULL,
	[JQYZJ_NAME] [varchar](32) NULL,
	[JQYZJ_CODE] [varchar](32) NULL,
	[JQYZJ_PHONE] [varchar](32) NULL,
	[JQYDS_NAME] [varchar](32) NULL,
	[JQYDS_EMPLOYEE_CODE] [varchar](32) NULL,
	[JQYDS_PHONE] [varchar](32) NULL,
	[JFHJL_NAME] [varchar](32) NULL,
	[JFHJL_EMPLOYEE_CODE] [varchar](32) NULL,
	[JFHJL_PHONE] [varchar](32) NULL,
	[GUEST_NAME_UP] [varchar](128) NULL,
	[GUEST_PHONE_UP] [varchar](200) NULL,
	[GUEST_NAME_DOWN] [varchar](128) NULL,
	[GUEST_PHONE_DOWN] [varchar](200) NULL,
	[MORT_TYPE] [varchar](32) NULL,
	[MORT_TYPE_CN] [varchar](32) NULL,
	[MORT_TOTAL_AMOUNT] [decimal](18, 2) NULL DEFAULT ((0)),
	[MORT_COM_AMOUNT] [decimal](18, 2) NULL DEFAULT ((0)),
	[MORT_COM_DISCOUNT] [decimal](18, 2) NULL,
	[MORT_PRF_AMOUNT] [decimal](18, 2) NULL DEFAULT ((0)),
	[MORT_CUST_NAME] [varchar](32) NULL,
	[MORT_CUST_PHONE] [varchar](32) NULL,
	[MORT_LOANER_NAME] [varchar](32) NULL,
	[MORT_LOANER_PHONE] [varchar](32) NULL,
	[IS_LOANER_ARRIVE] [varchar](2) NULL,
	[IS_LOANER_ARRIVE_CN] [varchar](10) NULL,
	[IS_DELEGATE_YUCUI] [varchar](2) NULL,
	[IS_DELEGATE_YUCUI_CN] [varchar](10) NULL,
	[IS_TMP_BANK] [varchar](2) NULL,
	[IS_TMP_BANK_CN] [varchar](10) NULL,
	[IS_RUWEI_BANK] [varchar](100) NULL,
	[MORT_FIN_ORG_CODE] [varchar](32) NULL,
	[MORT_FA_FIN_ORG_CODE] [varchar](32) NULL,
	[MORT_FIN_BRANCH_NAME] [varchar](64) NULL,
	[MORT_FIN_SUB_BRANCH_NAME] [varchar](64) NULL,
	[MORT_SIGN_DATE] [datetime] NULL,
	[MORT_APPR_DATE] [datetime] NULL,
	[MORT_LEND_DATE] [datetime] NULL,
	[CASE_REC_STATUS] [varchar](10) NULL,
	[CASE_REC_STATUS_CN] [varchar](32) NULL,
	[LOAN_LOST_AMOUNT] [decimal](18, 2) NULL,
	[LOAN_SELF_DEL_REASON] [nvarchar](2000) NULL,
	[LOAN_LOST_APPLY_REASON] [varchar](2000) NULL,
	[LOAN_REC_LETTER_NO] [nvarchar](64) NULL,
	[LOAN_LOST_CONFIRM_CODE] [varchar](64) NULL,
	[LOAN_CREATE_TIME] [datetime] NULL,
	[LOAN_APP_TIME] [datetime] NULL,
	[LOAN_APP_OPE_NAME] [varchar](32) NULL,
	[LOAN_APP_OPE_USERID] [varchar](64) NULL,
	[LOAN_APP_OPE_ORGID] [varchar](64) NULL,
	[LOAN_APP_OPE_ORGNAME] [varchar](64) NULL,
	[LOAN_AGENT_USER_ID] [varchar](64) NULL,
	[LOAN_AGENT_REAL_NAME] [varchar](32) NULL,
	[LOAN_AGENT_ORG_ID] [varchar](64) NULL,
	[LOAN_AGENT_ORG_NAME] [varchar](64) NULL,
	[CASE_CREATE_TIME] [datetime] NULL,
	[CASE_DISPATCH_TIME] [datetime] NULL,
	[CASE_REAL_CON_TIME] [datetime] NULL,
	[CASE_CON_PRICE] [decimal](18, 2) NULL,
	[CASE_REAL_PRICE] [decimal](18, 2) NULL,
	[CASE_HOUSE_UNIT_PRICE] [decimal](18, 2) NULL,
	[CASE_TAX_TIME] [datetime] NULL,
	[CASE_PRICING_TIME] [datetime] NULL,
	[CASE_TAX_PRICING] [decimal](18, 2) NULL,
	[CASE_REAL_PLS_TIME] [datetime] NULL,
	[TRANSFER_REAL_HT_TIME] [datetime] NULL,
	[TRANSFER_CREATE_TIME] [datetime] NULL,
	[TRANSFER_SUB_TIME] [datetime] NULL,
	[TRANSFER_APP_TIME] [datetime] NULL,
	[TRANSFER_APP_PASS_TIME] [datetime] NULL,
	[TRANSFER_TRADE_NAME] [varchar](32) NULL,
	[TRANSFER_COMMENT] [nvarchar](2000) NULL,
	[TRANSFER_ISPASS] [numeric](19, 0) NULL,
	[TRANSFER_ISPASS_CN] [varchar](20) NULL,
	[TRANSFER_APP_OPERATOR_USERID] [varchar](64) NULL,
	[TRANSFER_APP_OPERATOR_NAME] [varchar](32) NULL,
	[TRANSFER_CONTENT] [varchar](1000) NULL,
	[TRANSFER_NOT_APPROVE] [varchar](500) NULL,
	[TRANSFER_LAST_CONTENT] [varchar](2000) NULL,
	[CASE_REAL_PROPERTY_GET_TIME] [datetime] NULL,
	[CASE_CLOSE_TIME] [datetime] NULL,
	[CASE_EVA_COMPANY] [varchar](64) NULL,
	[SPV_TYPE] [varchar](128) NULL,
	[SPV_TYPE_CN] [varchar](500) NULL,
	[SPV_AMOUNT] [varchar](200) NULL,
	[SPV_SIGN_TIME] [varchar](500) NULL,
	[EVA_EVAL_FEE] [decimal](18, 2) NULL,
	[EVA_RECORD_TIME] [datetime] NULL,
	[ELOAN_PRO] [varchar](256) NULL,
	[ELOAN_PRO_AMOUNT] [decimal](18, 2) NULL,
	[ELOAN_KA] [varchar](64) NULL,
	[ELOAN_KA_AMOUNT_STR] [varchar](32) NULL,
	[ELOAN_KA_AMOUNT] [decimal](18, 2) NULL,
	[CASE_USE_CARD_PAY] [varchar](2) NULL,
	[CASE_USE_CARD_PAY_CN] [varchar](10) NULL,
	[CASE_CARD_PAY_AMOUNT] [decimal](18, 2) NULL,
	[INFO_CREATE_BY] [varchar](32) NULL,
	[INFO_CREATE_TIME] [datetime] NULL,

	[CREATE_TIME] [datetime] NULL,
	[UPDATE_TIME] [datetime] NULL,
	[UPDATE_BY] [varchar](32) NULL,
	[BELONG_MONTH] [datetime] NULL,

	CONSTRAINT [PK_T_TO_CASE_RESULTSET_HISTORY] PRIMARY KEY CLUSTERED 
(
	[PKID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]