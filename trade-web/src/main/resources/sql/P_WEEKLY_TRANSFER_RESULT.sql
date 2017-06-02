-- =============================================
-- Author:		gongjd
-- Create date: 2017-03-10
-- Description:	每周的sctrans.T_RPT_WEEKLY_CASE_BASE_INFO生成结果插入到结果表
-- =============================================
ALTER PROCEDURE [sctrans].[P_WEEKLY_TRANSFER_RESULT](
     @belong_week_end int = 0
)
	
AS
BEGIN

DECLARE @belong_week_start int;
DECLARE @belong_week_start_date datetime;
DECLARE @belong_week_end_date datetime;
DECLARE @this_month_start int;
DECLARE @this_month_start_date datetime;
DECLARE @last_month_start int;
DECLARE @last_month_start_date datetime;
DECLARE @last_month_end int;
DECLARE @last_month_end_date datetime;
DECLARE @weekday int;
DECLARE @last_period_week_4 datetime;
DECLARE @temp_int int;

DECLARE @sql1 nvarchar(max);
DECLARE @sql2 nvarchar(max);
DECLARE @sql3 nvarchar(max);
DECLARE @sql4 nvarchar(max);

DECLARE @chart_id varchar(32);
DECLARE @chart_id1 varchar(32);
DECLARE @chart_id2 varchar(32);
DECLARE @chart_id3 varchar(32);
DECLARE @chart_id4 varchar(32);

DECLARE @sql_string nvarchar(max);
DECLARE @result nvarchar(max);

BEGIN TRAN--开始事务
BEGIN TRY

IF @belong_week_end = 0
	BEGIN
		--默认之前一个统计周期：如果今天是周5、6，默认本周四；如果今天是周1、2、3、4、7，默认上周四
		PRINT '未传参数，默认上个统计周！';
		set @weekday = datepart(dw,getdate());
		IF @weekday in (6, 7)
		BEGIN
			set @last_period_week_4 = dateadd(dw,5-datepart(dw,getdate()),getdate());
		END 
		ELSE IF  @weekday in (1, 2, 3, 4, 5)
		BEGIN
			set @last_period_week_4 = dateadd(dw,-2-datepart(dw,getdate()),getdate());
		END 	
		print @last_period_week_4;
		set @belong_week_end = year(@last_period_week_4)*10000 + month(@last_period_week_4)*100 + day(@last_period_week_4);
		print @belong_week_end;
	END

set @temp_int = @belong_week_end;
set @belong_week_end_date = convert(datetime, convert(varchar, @belong_week_end));
set @belong_week_start_date = dateadd(day,-6,@belong_week_end_date);
set @this_month_start_date = dateadd(d,-day(@belong_week_end_date)+1,@belong_week_end_date);
set @last_month_start_date = dateadd(m,-1,@this_month_start_date);
set @last_month_end_date = dateadd(d,-1,@this_month_start_date);

set @belong_week_start = cast(@belong_week_start_date as int);
set @belong_week_end = cast(@belong_week_end_date as int);
set @this_month_start = cast(@this_month_start_date as int);
set @last_month_start = cast(@last_month_start_date as int);
set @last_month_end = cast(@last_month_end_date as int);

set @chart_id1 = 'loanLoseList';
set @chart_id2 = 'assessmentList';
set @chart_id3 = 'LoankaList';
set @chart_id4 = 'eloanList';

--执行游标查询
DECLARE weeklyResultCursor CURSOR FOR  SELECT CHART_ID,SQL_STRING FROM sctrans.T_RPT_ORIGIN_SQL WHERE CHART_ID in (@chart_id1,@chart_id2,@chart_id3,@chart_id4)

	--打开游标
	OPEN weeklyResultCursor

	FETCH NEXT FROM weeklyResultCursor INTO @chart_id,@sql_string
    WHILE @@fetch_status = 0
    begin 
        set @sql_string = replace(@sql_string,'#startWeekDay#',@belong_week_start);
        set @sql_string = replace(@sql_string,'#endWeekDay#',@belong_week_end);
        set @sql_string = replace(@sql_string,'#belongEndWeekDay#',@belong_week_end);
        set @sql_string = replace(@sql_string,'#lastMonthStartDay#',@last_month_start);
        set @sql_string = replace(@sql_string,'#lastMonthEndDay#',@last_month_end);
        set @sql_string = N'select @r = (select * from (' + @sql_string + ') m for xml path);';

        exec sp_executesql @sql_string,N'@r nvarchar(max) output',@result output;

		insert into sctrans.T_RPT_RESULT_RECORD(CHART_ID,RESULT_XML_STRING,PATCH_TIME_INT,IS_DELETED,CREATE_TIME,CREATE_BY) values(@chart_id,@result,@temp_int,'0',getdate(),system_user);

		FETCH NEXT FROM weeklyResultCursor INTO @chart_id,@sql_string;
    end
    CLOSE weeklyResultCursor
    DEALLOCATE weeklyResultCursor
    
    COMMIT TRAN 
    
	END TRY
    BEGIN CATCH
        print error_message();--打印错误信息
        ROLLBACK TRAN; --回滚保存点的事务   
    END CATCH
END