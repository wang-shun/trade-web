
DROP TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO]
CREATE TABLE [sctrans].[T_RPT_HISTORY_CASE_BASE_INFO](
	[PKID] [bigint] IDENTITY(1,1) NOT NULL,
	[CASE_CODE] [varchar](32) NULL,
	[CASE_LOAN_REQ] [varchar](5) NULL,
	[CASE_STATUS] [nvarchar](10) NULL,
	[CASE_PROPERTY] [nvarchar](10) NULL,
	[CASE_DISTRICT_ID] [varchar](64) NULL,
	[CASE_ORG_ID_F] [varchar](64) NULL,
	[CASE_DISPATCH_TIME] [datetime] NULL,
	[CASE_CON_PRICE] [decimal](18, 2) NULL,
	[CASE_REAL_CON_TIME] [datetime] NULL,
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
	[IS_TMP_BANK] [varchar](2) NULL,
	[IS_RUWEI_BANK] [varchar](100) NULL,
	[CASE_REC_STATUS] [varchar](32) NULL,
	[LOAN_LOST_AMOUNT] [decimal](18, 2) NULL,
	[CREATE_TIME] [datetime] NULL,
	[BELONG_MONTH] [int] NULL
) ON [PRIMARY]


