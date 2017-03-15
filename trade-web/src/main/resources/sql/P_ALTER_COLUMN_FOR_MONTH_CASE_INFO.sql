USE [SCTRANS_DEV]
GO
/****** OBJECT:  STOREDPROCEDURE [SCTRANS].[P_ALTER_COLUMN_FOR_MONTH_CASE_INFO]    SCRIPT DATE: 2017/3/14 17:20:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- AUTHOR:		caoy
-- CREATE DATE: 2017-03-14
-- DESCRIPTION:	修改月表特定字段
-- EXAMPLE exec [sctrans].[P_ALTER_COLUMN_FOR_MONTH_CASE_INFO] 201702,'CASE_STATUS_CN','ZY-AJ-201509-0458,ZY-AJ-201509-0459'
-- =============================================
ALTER PROCEDURE [SCTRANS].[P_ALTER_COLUMN_FOR_MONTH_CASE_INFO](
    @BELONG_MONTH VARCHAR(100),--所属月份
		@COLUMNS VARCHAR(1000),--更新字段
		@CASE_CODES VARCHAR(2000)--更新的CASE_CODE
)
AS
BEGIN
	DECLARE @I INT,
	@UP_COLUMN  VARCHAR(1000),--逗号分割获取到更新的字段名称
	@UP_SQL  VARCHAR(2000)--最终执行的更新sql
	SET @UP_COLUMN=''

	SET @UP_SQL=''
	SET @I=0
	WHILE @I<LEN(@COLUMNS)
	BEGIN 
	  SET @I=@I+1
	  IF SUBSTRING(@COLUMNS,@I,1)=','
		 BEGIN
		   SET @UP_COLUMN=@UP_COLUMN+' B.'+LEFT(@COLUMNS,@I-1)+' = A.'+ LEFT(@COLUMNS,@I-1)+','
		   SET @COLUMNS=SUBSTRING(@COLUMNS,@I+1,LEN(@COLUMNS))
		   SET @I=0
		 END  
	END
	SET @UP_COLUMN=@UP_COLUMN+' B.'+@COLUMNS+'= A.'+ @COLUMNS
	
	SET @UP_SQL='UPDATE B SET '+ @UP_COLUMN + ' FROM SCTRANS.T_RPT_CASE_BASE_INFO AS A ,SCTRANS.T_RPT_HISTORY_CASE_BASE_INFO AS B WHERE A.CASE_CODE = B.CASE_CODE AND B.BELONG_MONTH='+ @BELONG_MONTH;
	
	IF LEN(@CASE_CODES)>0--如果指定了更新的CASE_CODE
	 BEGIN
		 set @CASE_CODES  = ''''+replace(@CASE_CODES ,',',''',''')+'''';
		 SET @UP_SQL = @UP_SQL+' AND B.CASE_CODE IN ('+@CASE_CODES+')';
	 END  
	
	PRINT '打印SQL:'+@UP_SQL--打印更新字段的SQL
	
	EXEC (@UP_SQL)--执行sql  

END
