USE sctrans_dev
GO
/****** Object:  StoredProcedure sctrans.P_MONTH_REPORT_CASE_INFO    Script Date: 2017/2/20 10:10:54 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		gongjd
-- Create date: 2017-02-20
-- Description:	每周的sctrans.T_RPT_CASE_BASE_INFO备份与报表数据的生成
-- =============================================
ALTER PROCEDURE sctrans.P_WEEKLY_REPORT_CASE_INFO(
    @belong_week_start int = 0,
    @belong_week_end int = 0
)
	
AS
BEGIN
	DECLARE @update_date datetime;
	DECLARE @weekday int;
	--开始时间到结束时间整周数  （循环可变）
	DECLARE @whole_week_num int;
	--开始时间到结束时间整周数
	DECLARE @whole_week_num_init int;
	--循环到第几周
	DECLARE @count int;
	DECLARE @count_start int;
	DECLARE @count_end int;
	DECLARE @belong_week_start_date datetime;
	DECLARE @belong_week_end_date datetime;
	DECLARE @last_period_week_4 datetime;
	DECLARE @last_period_week_5 datetime;
 
  IF @belong_week_start = 0 and @belong_week_end = 0
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
			
			set @last_period_week_5 = dateadd(day,-6,@last_period_week_4);
			set @belong_week_start = year(@last_period_week_5)*10000 + month(@last_period_week_5)*100 + day(@last_period_week_5);
			set @belong_week_end = year(@last_period_week_4)*10000 + month(@last_period_week_4)*100 + day(@last_period_week_4);
			set @whole_week_num = 1;
			set @whole_week_num_init = 1;
		END
  ELSE IF @belong_week_start > @belong_week_end
    BEGIN
      PRINT '开始时间不能大于结束时间！';
      RETURN;
    END
  ELSE
    BEGIN
			set @belong_week_start_date = cast(cast(@belong_week_start AS VARCHAR) AS datetime);
			set @belong_week_end_date = cast(cast(@belong_week_end AS VARCHAR) AS datetime);
			
			set @count_start = case when datepart(dw,@belong_week_start_date)>5 then datepart(dd,@belong_week_start_date)-datepart(dw,@belong_week_start_date) else datepart(dd,@belong_week_start_date)-datepart(dw,@belong_week_start_date)+7 end;
			set @count_end = case when datepart(dw,@belong_week_end_date)>5 then datepart(dd,@belong_week_end_date)-datepart(dw,@belong_week_end_date)+7 else datepart(dd,@belong_week_end_date)-datepart(dw,@belong_week_end_date) end;
			
			set @whole_week_num = (@count_end - @count_start) / 7;
			set @whole_week_num_init = @whole_week_num;
			set @weekday = datepart(dw,@belong_week_start_date);
			--获取开始时间之后第一个周四:如果今天是周5、6，默认下周四；如果今天是周1、2、3、4、7，默认本周四
			IF @weekday in (6, 7)
				BEGIN
					set @last_period_week_4 = dateadd(dw,12-datepart(dw,@belong_week_start_date),@belong_week_start_date);
				END
			ELSE IF  @weekday in (1, 2, 3, 4, 5)
				BEGIN
					set @last_period_week_4 = dateadd(dw,5-datepart(dw,@belong_week_start_date),@belong_week_start_date);
				END
      --如果结束时间早于周四，那么将不做统计
			IF datediff(day,@last_period_week_4,@belong_week_end_date) < 0
				BEGIN
					PRINT '该周期不包含周四，无法统计周数据！';
					RETURN;
				END

			set @last_period_week_5 = dateadd(day,-6,@last_period_week_4);
			set @belong_week_start = year(@last_period_week_5)*10000 + month(@last_period_week_5)*100 + day(@last_period_week_5);
			set @belong_week_end = year(@last_period_week_4)*10000 + month(@last_period_week_4)*100 + day(@last_period_week_4);
    END
  
	set @update_date = getdate();
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	BEGIN TRY
	WHILE 	@whole_week_num <> 0
		BEGIN
			IF @whole_week_num_init <> 1
				BEGIN
					set @count = @whole_week_num_init - @whole_week_num;
					set @last_period_week_5 = dateadd(day,CASE @count WHEN 0 THEN 0 ELSE 7 END,@last_period_week_5);
					set @last_period_week_4 = dateadd(day,6,@last_period_week_5);
					set @belong_week_start = cast(convert(VARCHAR(8),@last_period_week_5,112) AS int);
					set @belong_week_end = cast(convert(VARCHAR(8),@last_period_week_4,112) AS int);
					PRINT cast(@whole_week_num_init as VARCHAR)+'+++'+cast(@whole_week_num as VARCHAR) +'+++'+ cast(@belong_week_start as VARCHAR) + '--->' + cast(@belong_week_end as VARCHAR);
				END

			--删除当周统计的数据
			DELETE  FROM sctrans.T_RPT_WEEKLY_CASE_BASE_INFO
			WHERE   BELONG_WEEK_START_DAY =@belong_week_start AND BELONG_WEEK_END_DAY = @belong_week_end;

			INSERT INTO sctrans.T_RPT_WEEKLY_CASE_BASE_INFO(
				CASE_PKID ,
				CASE_CODE ,
				CASE_STATUS ,
				CASE_STATUS_CN ,
				CASE_PROPERTY ,
				CASE_PROPERTY_CN ,
				HOUSR_PROPERTY_CODE ,
				HOUSR_PROPERTY_ADDR ,
				HOUSR_DIST_CODE ,
				HOUSR_DIST_CODE_CN ,
				HOUSR_SQUARE ,
				CASE_ORIGIN ,
				CASE_ORIGIN_CN ,
				CASE_LOAN_REQ ,
				CASE_LOAN_REQ_CN ,
				CASE_LEADING_PROCESS_ID_F ,
				CASE_EMPLOYEE_CODE_F ,
				CASE_USERNAME_F ,
				CASE_REAL_NAME_F ,
				CASE_MOBILE_F ,
				CASE_ORG_ID_F ,
				CASE_ORG_NAME_F ,
				CASE_LEADING_PROCESS_ID_B ,
				CASE_EMPLOYEE_CODE_B ,
				CASE_USERNAME_B ,
				CASE_REAL_NAME_B ,
				CASE_MOBILE_B ,
				CASE_ORG_ID_B ,
				CASE_ORG_NAME_B ,
				CASE_DIRECTOR_REAL_NAME ,
				CASE_DIRECTOR_USER_ID ,
				CASE_DIRECTOR_MOBILE ,
				CASE_DIRECTOR_ORG_ID ,
				CASE_DIRECTOR_ORG_NAME ,
				CASE_MANAGER_REAL_NAME ,
				CASE_MANAGER_USER_ID ,
				CASE_MANAGER_MOBILE ,
				CASE_MANAGER_ORG_ID ,
				CASE_MANAGER_ORG_NAME ,
				CASE_ASSISTANT_REAL_NAME ,
				CASE_ASSISTANT_USER_ID ,
				CASE_ASSISTANT_MOBILE ,
				CASE_ASSISTANT_ORG_ID ,
				CASE_ASSISTANT_ORG_NAME ,
				CASE_ISSUBSCRIBE ,
				CASE_TARGET_CODE ,
				CASE_DISTRICT_ID ,
				CASE_DISTRICT_NAME ,
				CTM_CODE ,
				AGENT_NAME ,
				AGENT_CODE ,
				AGENT_PHONE ,
				AGENT_USERNAME ,
				GRP_CODE ,
				GRP_NAME ,
				AR_CODE ,
				AR_NAME ,
				SWZ_CODE ,
				SWZ_NAME ,
				WZ_CODE ,
				WZ_NAME ,
				BA_CODE ,
				BA_NAME ,
				JQYJL_NAME ,
				JQYJL_EMPLOYEE_CODE ,
				JQYJL_PHONE ,
				JQYZJ_NAME ,
				JQYZJ_EMPLOYEE_CODE ,
				JQYZJ_PHONE ,
				JQYDS_NAME ,
				JQYDS_EMPLOYEE_CODE ,
				JQYDS_PHONE ,
				JFHJL_NAME ,
				JFHJL_EMPLOYEE_CODE ,
				JFHJL_PHONE ,
				GUEST_NAME_UP ,
				GUEST_PHONE_UP ,
				GUEST_NAME_DOWN ,
				GUEST_PHONE_DOWN ,
				MORT_TYPE ,
				MORT_TYPE_CN ,
				MORT_TOTAL_AMOUNT ,
				MORT_COM_AMOUNT ,
				MORT_COM_DISCOUNT ,
				MORT_PRF_AMOUNT ,
				MORT_CUST_NAME ,
				MORT_CUST_PHONE ,
				MORT_LOANER_NAME ,
				MORT_LOANER_PHONE ,
				IS_LOANER_ARRIVE ,
				IS_LOANER_ARRIVE_CN ,
				IS_DELEGATE_YUCUI ,
				IS_DELEGATE_YUCUI_CN ,
				IS_TMP_BANK ,
				IS_TMP_BANK_CN ,
				IS_RUWEI_BANK ,
				MORT_FIN_ORG_CODE ,
				MORT_FA_FIN_ORG_CODE ,
				MORT_FIN_BRANCH_NAME ,
				MORT_FIN_SUB_BRANCH_NAME ,
				MORT_SIGN_DATE ,
				MORT_APPR_DATE ,
				MORT_LEND_DATE ,
				CASE_REC_STATUS ,
				CASE_REC_STATUS_CN ,
				LOAN_LOST_AMOUNT ,
				LOAN_SELF_DEL_REASON ,
				LOAN_LOST_APPLY_REASON ,
				LOAN_REC_LETTER_NO ,
				LOAN_LOST_CONFIRM_CODE ,
				LOAN_CREATE_TIME ,
				LOAN_APP_TIME ,
				LOAN_APP_OPE_NAME ,
				LOAN_APP_OPE_USERID ,
				LOAN_APP_OPE_ORGID ,
				LOAN_APP_OPE_ORGNAME ,
				LOAN_AGENT_USER_ID ,
				LOAN_AGENT_REAL_NAME ,
				LOAN_AGENT_ORG_ID ,
				LOAN_AGENT_ORG_NAME ,
				CASE_CREATE_TIME ,
				CASE_DISPATCH_TIME ,
				CASE_REAL_CON_TIME ,
				CASE_CON_PRICE ,
				CASE_REAL_PRICE ,
				CASE_HOUSE_UNIT_PRICE ,
				CASE_TAX_TIME ,
				CASE_PRICING_TIME ,
				CASE_TAX_PRICING ,
				CASE_REAL_PLS_TIME ,
				TRANSFER_REAL_HT_TIME ,
				TRANSFER_CREATE_TIME ,
				TRANSFER_SUB_TIME ,
				TRANSFER_APP_TIME ,
				TRANSFER_APP_PASS_TIME ,
				TRANSFER_TRADE_NAME ,
				TRANSFER_COMMENT ,
				TRANSFER_ISPASS ,
				TRANSFER_ISPASS_CN ,
				TRANSFER_APP_OPERATOR_USERID ,
				TRANSFER_APP_OPERATOR_NAME ,
				TRANSFER_CONTENT ,
				TRANSFER_NOT_APPROVE ,
				TRANSFER_LAST_CONTENT ,
				CASE_REAL_PROPERTY_GET_TIME ,
				CASE_CLOSE_TIME ,
				CASE_EVA_COMPANY ,
				SPV_TYPE ,
				SPV_TYPE_CN ,
				SPV_AMOUNT ,
				SPV_SIGN_TIME ,
				EVA_EVAL_FEE ,
				EVA_RECORD_TIME ,
				ELOAN_PRO ,
				ELOAN_PRO_AMOUNT ,
				ELOAN_KA ,
				ELOAN_KA_AMOUNT ,
				CASE_USE_CARD_PAY ,
				CASE_USE_CARD_PAY_CN ,
				CASE_CARD_PAY_AMOUNT ,
				CREATE_TIME ,
				BELONG_WEEK_START_DAY ,
				BELONG_WEEK_END_DAY
			)
				SELECT
					CASE_PKID ,
					CASE_CODE ,
					CASE_STATUS ,
					CASE_STATUS_CN ,
					CASE_PROPERTY ,
					CASE_PROPERTY_CN ,
					HOUSR_PROPERTY_CODE ,
					HOUSR_PROPERTY_ADDR ,
					HOUSR_DIST_CODE ,
					HOUSR_DIST_CODE_CN ,
					HOUSR_SQUARE ,
					CASE_ORIGIN ,
					CASE_ORIGIN_CN ,
					CASE_LOAN_REQ ,
					CASE_LOAN_REQ_CN ,
					CASE_LEADING_PROCESS_ID_F ,
					CASE_EMPLOYEE_CODE_F ,
					CASE_USERNAME_F ,
					CASE_REAL_NAME_F ,
					CASE_MOBILE_F ,
					CASE_ORG_ID_F ,
					CASE_ORG_NAME_F ,
					CASE_LEADING_PROCESS_ID_B ,
					CASE_EMPLOYEE_CODE_B ,
					CASE_USERNAME_B ,
					CASE_REAL_NAME_B ,
					CASE_MOBILE_B ,
					CASE_ORG_ID_B ,
					CASE_ORG_NAME_B ,
					CASE_DIRECTOR_REAL_NAME ,
					CASE_DIRECTOR_USER_ID ,
					CASE_DIRECTOR_MOBILE ,
					CASE_DIRECTOR_ORG_ID ,
					CASE_DIRECTOR_ORG_NAME ,
					CASE_MANAGER_REAL_NAME ,
					CASE_MANAGER_USER_ID ,
					CASE_MANAGER_MOBILE ,
					CASE_MANAGER_ORG_ID ,
					CASE_MANAGER_ORG_NAME ,
					CASE_ASSISTANT_REAL_NAME ,
					CASE_ASSISTANT_USER_ID ,
					CASE_ASSISTANT_MOBILE ,
					CASE_ASSISTANT_ORG_ID ,
					CASE_ASSISTANT_ORG_NAME ,
					CASE_ISSUBSCRIBE ,
					CASE_TARGET_CODE ,
					CASE_DISTRICT_ID ,
					CASE_DISTRICT_NAME ,
					CTM_CODE ,
					AGENT_NAME ,
					AGENT_CODE ,
					AGENT_PHONE ,
					AGENT_USERNAME ,
					GRP_CODE ,
					GRP_NAME ,
					AR_CODE ,
					AR_NAME ,
					SWZ_CODE ,
					SWZ_NAME ,
					WZ_CODE ,
					WZ_NAME ,
					BA_CODE ,
					BA_NAME ,
					JQYJL_NAME ,
					JQYJL_EMPLOYEE_CODE ,
					JQYJL_PHONE ,
					JQYZJ_NAME ,
					JQYZJ_EMPLOYEE_CODE ,
					JQYZJ_PHONE ,
					JQYDS_NAME ,
					JQYDS_EMPLOYEE_CODE ,
					JQYDS_PHONE ,
					JFHJL_NAME ,
					JFHJL_EMPLOYEE_CODE ,
					JFHJL_PHONE ,
					GUEST_NAME_UP ,
					GUEST_PHONE_UP ,
					GUEST_NAME_DOWN ,
					GUEST_PHONE_DOWN ,
					MORT_TYPE ,
					MORT_TYPE_CN ,
					MORT_TOTAL_AMOUNT ,
					MORT_COM_AMOUNT ,
					MORT_COM_DISCOUNT ,
					MORT_PRF_AMOUNT ,
					MORT_CUST_NAME ,
					MORT_CUST_PHONE ,
					MORT_LOANER_NAME ,
					MORT_LOANER_PHONE ,
					IS_LOANER_ARRIVE ,
					IS_LOANER_ARRIVE_CN ,
					IS_DELEGATE_YUCUI ,
					IS_DELEGATE_YUCUI_CN ,
					IS_TMP_BANK ,
					IS_TMP_BANK_CN ,
					IS_RUWEI_BANK ,
					MORT_FIN_ORG_CODE ,
					MORT_FA_FIN_ORG_CODE ,
					MORT_FIN_BRANCH_NAME ,
					MORT_FIN_SUB_BRANCH_NAME ,
					MORT_SIGN_DATE ,
					MORT_APPR_DATE ,
					MORT_LEND_DATE ,
					CASE_REC_STATUS ,
					CASE_REC_STATUS_CN ,
					LOAN_LOST_AMOUNT ,
					LOAN_SELF_DEL_REASON ,
					LOAN_LOST_APPLY_REASON ,
					LOAN_REC_LETTER_NO,
					LOAN_LOST_CONFIRM_CODE ,
					LOAN_CREATE_TIME ,
					LOAN_APP_TIME ,
					LOAN_APP_OPE_NAME ,
					LOAN_APP_OPE_USERID ,
					LOAN_APP_OPE_ORGID ,
					LOAN_APP_OPE_ORGNAME ,
					LOAN_AGENT_USER_ID ,
					LOAN_AGENT_REAL_NAME ,
					LOAN_AGENT_ORG_ID ,
					LOAN_AGENT_ORG_NAME ,
					CASE_CREATE_TIME ,
					CASE_DISPATCH_TIME ,
					CASE_REAL_CON_TIME ,
					CASE_CON_PRICE ,
					CASE_REAL_PRICE ,
					CASE_HOUSE_UNIT_PRICE ,
					CASE_TAX_TIME ,
					CASE_PRICING_TIME ,
					CASE_TAX_PRICING ,
					CASE_REAL_PLS_TIME ,
					TRANSFER_REAL_HT_TIME ,
					TRANSFER_CREATE_TIME ,
					TRANSFER_SUB_TIME ,
					TRANSFER_APP_TIME ,
					TRANSFER_APP_PASS_TIME ,
					TRANSFER_TRADE_NAME ,
					TRANSFER_COMMENT ,
					TRANSFER_ISPASS ,
					TRANSFER_ISPASS_CN ,
					TRANSFER_APP_OPERATOR_USERID ,
					TRANSFER_APP_OPERATOR_NAME ,
					TRANSFER_CONTENT ,
					TRANSFER_NOT_APPROVE ,
					TRANSFER_LAST_CONTENT ,
					CASE_REAL_PROPERTY_GET_TIME ,
					CASE_CLOSE_TIME ,
					CASE_EVA_COMPANY ,
					SPV_TYPE ,
					SPV_TYPE_CN ,
					SPV_AMOUNT ,
					SPV_SIGN_TIME ,
					EVA_EVAL_FEE ,
					EVA_RECORD_TIME ,
					ELOAN_PRO ,
					ELOAN_PRO_AMOUNT ,
					ELOAN_KA ,
					ELOAN_KA_AMOUNT ,
					CASE_USE_CARD_PAY ,
					CASE_USE_CARD_PAY_CN ,
					CASE_CARD_PAY_AMOUNT ,
					GETDATE() ,
					@belong_week_start ,
					@belong_week_end
				FROM
					SCTRANS.T_RPT_CASE_BASE_INFO

			SET @whole_week_num = @whole_week_num - 1;
		END
	END TRY

	BEGIN CATCH
		PRINT error_message();
	END CATCH

END

