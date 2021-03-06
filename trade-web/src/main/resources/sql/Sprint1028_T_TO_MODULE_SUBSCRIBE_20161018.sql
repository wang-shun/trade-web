USE [sctrans_dev]
GO

/****** Object:  Table [sctrans].[T_TO_MODULE_SUBSCRIBE]    Script Date: 2016/10/19 10:49:46 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [sctrans].[T_TO_MODULE_SUBSCRIBE](
	[PKID] [bigint] IDENTITY(1,1) NOT NULL,
	[SUBSCRIBER_ID] [varchar](32) NULL,
	[CASE_CODE] [varchar](32) NULL,
	[CREATE_TIME] [datetime] NULL,
	[SUBSCRIBE_TYPE] [varchar](32) NULL,
	[MODULE_TYPE] [varchar](50) NULL,
	[REMARK] [text] NULL,
 CONSTRAINT [PK_T_TO_CASE_SUBSCRIBE] PRIMARY KEY CLUSTERED 
(
	[PKID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO
