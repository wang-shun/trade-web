--报表结果表，用于存储报表结果
CREATE TABLE sctrans.T_RPT_RESULT_RECORD (
	PKID bigint NOT NULL IDENTITY(1,1),
  	CHART_ID varchar(32) NOT NULL,
  	RESULT_XML_STRING nvarchar(max),
  	PATCH_TIME_INT int,
  	DESCRIPTION nvarchar(256), 
	IS_DELETED char(1),
	CREATE_TIME datetime,
	CREATE_BY varchar(32),
	UPDATE_TIME datetime,
	UPDATE_BY varchar(32)

CONSTRAINT [PK_T_RPT_RESULT_RECORD] PRIMARY KEY CLUSTERED 
(
	[PKID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
)
ON [PRIMARY]