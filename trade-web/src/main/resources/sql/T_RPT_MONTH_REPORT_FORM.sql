USE [sctrans_dev]
GO

/****** Object:  Table [sctrans].[T_RPT_MONTH_REPORT_FORM]    Script Date: 2017/1/6 17:45:10 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [sctrans].[T_RPT_MONTH_REPORT_FORM](
	[PK_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[MONTH_FORM_TYPE] [varchar](32) NULL,
	[FORM_KEY] [varchar](32) NULL,
	[FORM_DATE] [nchar](10) NULL,
	[MORT_TOTAL_AMOUNT] [decimal](18, 2) NULL,
	[COM_AMOUNT] [decimal](18, 2) NULL,
	[MORT_TOTAL_COUNT] [int] NULL,
	[PRF_COUNT] [int] NULL,
	[PRF_AMOUNT] [decimal](18, 2) NULL,
	[NO_MORT_COUNT] [int] NULL,
	[LOST_COUNT] [int] NULL,
	[LOST_AMOUNT] [decimal](18, 2) NULL,
	[DISTRIBUTE_COUNT] [int] NULL,
	[CON_COUNT] [int] NULL,
	[CREATE_TIME] [datetime] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'数据归属' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_MONTH_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'MONTH_FORM_TYPE'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'数据主键' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_MONTH_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'FORM_KEY'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'统计日期' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_MONTH_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'FORM_DATE'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'贷款总额' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_MONTH_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'MORT_TOTAL_AMOUNT'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'商业贷款金额' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_MONTH_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'COM_AMOUNT'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'商业贷款单数' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_MONTH_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'MORT_TOTAL_COUNT'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公积金单数' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_MONTH_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'PRF_COUNT'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'公积金贷款金额' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_MONTH_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'PRF_AMOUNT'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'无贷款单数' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_MONTH_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'NO_MORT_COUNT'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'流失单数' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_MONTH_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'LOST_COUNT'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'派单数量' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_MONTH_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'DISTRIBUTE_COUNT'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'签约数量' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_MONTH_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'CON_COUNT'
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'创建日期' , @level0type=N'SCHEMA',@level0name=N'sctrans', @level1type=N'TABLE',@level1name=N'T_RPT_MONTH_REPORT_FORM', @level2type=N'COLUMN',@level2name=N'CREATE_TIME'
GO


