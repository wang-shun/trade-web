GO

/****** Object:  Table [sctrans].[T_RPT_DAYLY_REPORT_FORM]    Script Date: 2017/1/5 11:06:20 *
*@author caoy
*负责主管以上的岗位查看8个报表的技术数据的每日整理
**/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [sctrans].[T_RPT_DAYLY_REPORT_FORM](
	[PK_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[CASE_CODE] [varchar](32) NULL,
	[LOAN_REQ] [char](2) NULL,
	[LOAN_TYPE] [varchar](32) NULL,
	[CASE_STATUS] [varchar](32) NULL,
	[DISTRICT_ID] [varchar](32) NULL,
	[CASE_TRANSFER_AGREE_DATE] [datetime] NULL,
	[IS_LOSE] [char](2) NULL,
	[MORT_TOTAL_AMOUNT] [decimal](18, 2) NULL,
	[COM_AMOUNT] [decimal](18, 2) NULL,
	[PRF_AMOUNT] [decimal](18, 2) NULL,
	[FIN_ORG_CODE] [varchar](32) NULL,
	[IS_TMP_BANK] [char](2) NULL,
	[DISPATCH_TIME] [datetime] NULL,
	[REAL_CON_TIME] [datetime] NULL,
	[CREATE_TIME] [datetime] NULL,
	[UPDATE_TIME] [datetime] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'案件编号' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_DAYLY_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'CASE_CODE'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'贷款需求' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_DAYLY_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'LOAN_REQ'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'贷款类型' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_DAYLY_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'LOAN_TYPE'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'案件状态' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_DAYLY_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'CASE_STATUS'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'区域ID' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_DAYLY_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'DISTRICT_ID'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'过户审批通过时间' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_DAYLY_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'CASE_TRANSFER_AGREE_DATE'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否流失' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_DAYLY_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'IS_LOSE'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'贷款总额' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_DAYLY_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'MORT_TOTAL_AMOUNT'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'商业贷款金额' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_DAYLY_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'COM_AMOUNT'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公积金贷款金额' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_DAYLY_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'PRF_AMOUNT'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'贷款机构' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_DAYLY_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'FIN_ORG_CODE'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'是否是临时银行' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_DAYLY_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'IS_TMP_BANK'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'分单时间' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_DAYLY_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'DISPATCH_TIME'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'签约时间' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_DAYLY_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'REAL_CON_TIME'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建时间' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_DAYLY_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'CREATE_TIME'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'更新时间' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_DAYLY_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'UPDATE_TIME'
GO


