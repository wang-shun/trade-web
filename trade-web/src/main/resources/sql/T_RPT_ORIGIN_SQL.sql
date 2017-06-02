--原始sql表，用于存储报表等原始的sql
CREATE TABLE sctrans.T_RPT_ORIGIN_SQL (
	PKID bigint NOT NULL IDENTITY(1,1),
  	CHART_ID varchar(32) NOT NULL,
  	CHART_NAME nvarchar(100),
  	SQL_STRING nvarchar(max),
  	DESCRIPTION nvarchar(256), 
	IS_DELETED char(1),
	CREATE_TIME datetime,
	CREATE_BY varchar(32),
	UPDATE_TIME datetime,
	UPDATE_BY varchar(32)

CONSTRAINT [PK_T_RPT_ORIGIN_SQL] PRIMARY KEY CLUSTERED 
(
	[PKID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
)
ON [PRIMARY]

--初始化sql
insert into sctrans.T_RPT_ORIGIN_SQL(CHART_ID,CHART_NAME,SQL_STRING,IS_DELETED,CREATE_TIME,CREATE_BY) values 
('loanLoseList','贷款流失率周报表','select
				week.CASE_ORG_ID_F ORG_ID,
				sum(case when week.CASE_REC_STATUS = 1 and week.BELONG_WEEK_END_DAY = #belongEndWeekDay# and cast(convert(varchar(8) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) between #startWeekDay# and 
				#endWeekDay# then 1 else 0 end) LOSE_NUM_WEEK,
				sum(case when week.CASE_REC_STATUS = 0 and week.BELONG_WEEK_END_DAY = #belongEndWeekDay# and cast(convert(varchar(8) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) between #startWeekDay# and 
				#endWeekDay# then 1 else 0 end) REC_NUM_WEEK,
				sum(case when week.BELONG_WEEK_END_DAY = #belongEndWeekDay# and cast(convert(varchar(8) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) between #startWeekDay# and 
				#endWeekDay# then 1 else 0 end) TOTAL_NUM_WEEK,
				sum(isnull(case when week.CASE_REC_STATUS = 1 and week.BELONG_WEEK_END_DAY = #belongEndWeekDay# and cast(convert(varchar(8) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) between #startWeekDay# and 
				#endWeekDay# then week.MORT_TOTAL_AMOUNT else 0 end,0)) LOSE_AMOUNT_WEEK,
				sum(isnull(case when week.CASE_REC_STATUS = 0 and week.BELONG_WEEK_END_DAY = #belongEndWeekDay# and cast(convert(varchar(8) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) between #startWeekDay# and 
				#endWeekDay# then week.MORT_TOTAL_AMOUNT else 0 end,0)) REC_AMOUNT_WEEK,
				sum(isnull(case when week.BELONG_WEEK_END_DAY = #belongEndWeekDay# and cast(convert(varchar(8) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) between #startWeekDay# and 
				#endWeekDay# then week.MORT_TOTAL_AMOUNT else 0 end,0)) TOTAL_AMOUNT_WEEK,			
				sum(case when week.CASE_REC_STATUS = 1 and week.BELONG_WEEK_END_DAY/100 = #belongEndWeekDay#/100 and cast(convert(varchar(8) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) between #startWeekDay# and 
				#endWeekDay# then 1 else 0 end) LOSE_NUM_MONTH,
				sum(case when week.BELONG_WEEK_END_DAY/100 = #belongEndWeekDay#/100 and cast(convert(varchar(8) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) between #startWeekDay# and 
				#endWeekDay# then 1 else 0 end) TOTAL_NUM_MONTH,
				(select count(1) from sctrans.T_RPT_HISTORY_CASE_BASE_INFO hi where hi.CASE_ORG_ID_F = week.CASE_ORG_ID_F and hi.CASE_REC_STATUS = 1 and hi.BELONG_MONTH = #endWeekDay#/100-1 and hi.MORT_TYPE in (''30016001'',''30016002'') and hi.TRANSFER_ISPASS = 1) LOSE_NUM_LAST_MONTH,
				(select count(1) from sctrans.T_RPT_HISTORY_CASE_BASE_INFO hi where hi.CASE_ORG_ID_F = week.CASE_ORG_ID_F and hi.BELONG_MONTH = #endWeekDay#/100-1 and hi.MORT_TYPE in (''30016001'',''30016002'') and hi.TRANSFER_ISPASS = 1) TOTAL_NUM_LAST_MONTH,
				sum(isnull(case when week.CASE_REC_STATUS = 1 and week.BELONG_WEEK_END_DAY/100 = #belongEndWeekDay#/100 and cast(convert(varchar(6) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) = #endWeekDay#/100 then week.MORT_TOTAL_AMOUNT else 0 end,0)) LOSE_AMOUNT_MONTH,
				sum(isnull(case when week.BELONG_WEEK_END_DAY/100 = #belongEndWeekDay#/100 and cast(convert(varchar(6) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) = #endWeekDay#/100  then week.MORT_TOTAL_AMOUNT else 0 end,0)) TOTAL_AMOUNT_MONTH		
				from sctrans.T_RPT_WEEKLY_CASE_BASE_INFO week where week.MORT_TYPE in (''30016001'',''30016002'') and week.TRANSFER_ISPASS = 1 group by week.CASE_ORG_ID_F','0',getdate(),system_user),
('assessmentList','评估转化率周报表','select 
				week.CASE_ORG_ID_F ORG_ID,	
				sum(case when week.BELONG_WEEK_END_DAY = #belongEndWeekDay# and cast(convert(varchar(8) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) between #startWeekDay# and 
				#endWeekDay# then 1 else 0 end) REC_NUM_WEEK,	
				sum(case when week.BELONG_WEEK_END_DAY = #belongEndWeekDay# and cast(convert(varchar(8) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) between #startWeekDay# and 
				#endWeekDay# and week.EVA_EVAL_FEE is not null then 1 else 0 end) EVA_NUM_WEEK,	
				sum(case when week.BELONG_WEEK_END_DAY/100 = #belongEndWeekDay#/100 and cast(convert(varchar(6) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) = #endWeekDay#/100  then 1 else 0 end) REC_NUM_MONTH,	
				sum(case when week.BELONG_WEEK_END_DAY/100 = #belongEndWeekDay#/100 and cast(convert(varchar(6) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) = #endWeekDay#/100  and week.EVA_EVAL_FEE is not null then 1 else 0 end) EVA_NUM_MONTH,
				sum(isnull(case when week.BELONG_WEEK_END_DAY = #belongEndWeekDay# and cast(convert(varchar(8) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) between #startWeekDay# and 
				#endWeekDay# then week.MORT_COM_AMOUNT*0.001 else 0 end,0)) EVA_AMOUNT_WEEK,
				sum(isnull(case when week.BELONG_WEEK_END_DAY = #belongEndWeekDay# and cast(convert(varchar(8) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) between #startWeekDay# and 
				#endWeekDay# then week.EVA_EVAL_FEE else 0 end,0)) EVA_ACT_AMOUNT_WEEK,
				sum(isnull(case when week.BELONG_WEEK_END_DAY/100 = #belongEndWeekDay#/100 and cast(convert(varchar(6) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) = #endWeekDay#/100  then week.MORT_COM_AMOUNT*0.001 else 0 end,0)) EVA_AMOUNT_MONTH,
				sum(isnull(case when week.BELONG_WEEK_END_DAY/100 = #belongEndWeekDay#/100 and cast(convert(varchar(6) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) = #endWeekDay#/100  then week.EVA_EVAL_FEE else 0 end,0)) EVA_ACT_AMOUNT_MONTH
				from sctrans.T_RPT_WEEKLY_CASE_BASE_INFO week where week.MORT_TYPE in (''30016001'',''30016002'') and week.TRANSFER_ISPASS = 1 and week.CASE_REC_STATUS = 0 group by week.CASE_ORG_ID_F','0',getdate(),system_user),
('LoankaList','金融产品转化率[卡类]周报表','select 
				week.CASE_ORG_ID_F ORG_ID,
				sum(case when week.BELONG_WEEK_END_DAY = #belongEndWeekDay# and cast(convert(varchar(8) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) between #startWeekDay# and 
				#endWeekDay# then 1 else 0 end) GUOHU_NUM_WEEK,
				sum(case when week.BELONG_WEEK_END_DAY = #belongEndWeekDay# and cast(convert(varchar(8) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) between #startWeekDay# and 
				#endWeekDay# and week.ELOAN_KA is not null then 1 else 0 end) KA_APP_NUM_WEEK,
				sum(case when week.BELONG_WEEK_END_DAY/100 = #belongEndWeekDay#/100 and cast(convert(varchar(6) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) = #endWeekDay#/100  then 1 else 0 end) GUOHU_NUM_MONTH,
				sum(case when week.BELONG_WEEK_END_DAY/100 = #belongEndWeekDay#/100 and cast(convert(varchar(6) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) = #endWeekDay#/100  and week.ELOAN_KA is not null then 1 else 0 end) KA_APP_NUM_MONTH,
				sum(case when week.BELONG_WEEK_END_DAY/100 = #belongEndWeekDay#/100 and cast(convert(varchar(6) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) = #endWeekDay#/100  and week.ELOAN_KA is not null and week.ELOAN_KA_AMOUNT is not null then 1 else 0 end) KA_NUM_MONTH,
				(select count(1) from sctrans.T_TO_ELOAN_CASE ec where ec.EXCUTOR_TEAM = week.CASE_ORG_ID_F and EC.LOAN_SRV_CODE IN (''30004005'',''30004015'') and cast(convert(varchar(6) , EC.CREATE_TIME, 112 ) as int) = #endWeekDay#/100) ELOAN_KA_APP_NUM_MONTH_1,
				(select count(1) from sctrans.T_TO_LOAN_AGENT la where la.MIGRATE IS NULL and la.EXECUTOR_TEAM = week.CASE_ORG_ID_F and LA.LOAN_SRV_CODE IN (''30004005'',''30004015'') and cast(convert(varchar(6) , LA.CREATE_TIME, 112 ) as int) = #endWeekDay#/100) ELOAN_KA_APP_NUM_MONTH_2			
				from sctrans.T_RPT_WEEKLY_CASE_BASE_INFO week where week.TRANSFER_ISPASS = 1 group by week.CASE_ORG_ID_F','0',getdate(),system_user),
('eloanList','金融产品转化率[卡类]周报表','select 
				week.CASE_ORG_ID_F ORG_ID,
				sum(case when week.BELONG_WEEK_END_DAY = #belongEndWeekDay# and cast(convert(varchar(8) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) between #startWeekDay# and 
				#endWeekDay# then 1 else 0 end) GUOHU_NUM_WEEK,
				sum(isnull(case when week.BELONG_WEEK_END_DAY = #belongEndWeekDay# and cast(convert(varchar(8) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) between #startWeekDay# and 
				#endWeekDay# then week.CASE_CON_PRICE else 0 end,0)) HOUSE_PRICE_WEEK,
				sum(case when week.BELONG_WEEK_END_DAY = #belongEndWeekDay# and cast(convert(varchar(8) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) between #startWeekDay# and 
				#endWeekDay# and week.ELOAN_PRO is not null then 1 else 0 end) PRO_APP_NUM_WEEK,
				sum(isnull(case when week.BELONG_WEEK_END_DAY = #belongEndWeekDay# and cast(convert(varchar(8) ,week.TRANSFER_APP_PASS_TIME, 112 ) as int) between #startWeekDay# and 
				#endWeekDay# and week.ELOAN_PRO is not null then week.ELOAN_PRO_AMOUNT else 0 end,0)) PRO_APP_AMOUNT_WEEK,		
				sum(case when week.BELONG_WEEK_END_DAY/100 = #belongEndWeekDay#/100 and cast(convert(varchar(8) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) between #startWeekDay# and 
				#endWeekDay# then 1 else 0 end) GUOHU_NUM_MONTH,
				sum(isnull(case when week.BELONG_WEEK_END_DAY/100 = #belongEndWeekDay#/100 and cast(convert(varchar(6) , TRANSFER_APP_PASS_TIME, 112 ) as int) = #endWeekDay#/100  then week.CASE_CON_PRICE else 0 end,0)) HOUSE_PRICE_MONTH,
				sum(case when week.BELONG_WEEK_END_DAY/100 = #belongEndWeekDay#/100 and cast(convert(varchar(6) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) = #endWeekDay#/100  and week.ELOAN_PRO is not null then 1 else 0 end) PRO_APP_NUM_MONTH,
				sum(isnull(case when week.BELONG_WEEK_END_DAY/100 = #belongEndWeekDay#/100 and cast(convert(varchar(6) , week.TRANSFER_APP_PASS_TIME, 112 ) as int) = #endWeekDay#/100  and week.ELOAN_PRO is not null then week.ELOAN_PRO_AMOUNT else 0 end,0)) PRO_APP_AMOUNT_MONTH,
				(select count(1) from sctrans.T_TO_ELOAN_CASE ec where ec.EXCUTOR_TEAM = week.CASE_ORG_ID_F and EC.LOAN_SRV_CODE NOT IN (''30004005'',''30004015'') and cast(convert(varchar(6) , EC.CREATE_TIME, 112 ) as int) = #endWeekDay#/100)  ELOAN_PRO_APP_NUM_MONTH_1,
				(select count(1) from sctrans.T_TO_LOAN_AGENT la where la.MIGRATE IS NULL and la.EXECUTOR_TEAM = week.CASE_ORG_ID_F and LA.LOAN_SRV_CODE NOT IN (''30004005'',''30004015'') and cast(convert(varchar(6) , LA.CREATE_TIME, 112 ) as int) = #endWeekDay#/100)  ELOAN_PRO_APP_NUM_MONTH_2		
				from sctrans.T_RPT_WEEKLY_CASE_BASE_INFO week where week.TRANSFER_ISPASS = 1 group by week.CASE_ORG_ID_F','0',getdate(),system_user);