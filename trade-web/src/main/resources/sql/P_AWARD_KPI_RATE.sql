USE [sctrans_prd]
GO
/****** Object:  StoredProcedure [sctrans].[P_AWARD_KPI_RATE]    Script Date: 2016/10/13 16:25:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [sctrans].[P_AWARD_KPI_RATE]
@belongMonth datetime,
@createBy VARCHAR(200),
@createTime datetime
AS
BEGIN
	DECLARE
	   @isExistKpiPay VARCHAR(10),@isExistSrvCase VARCHAR(10),@isExistMonthKpi VARCHAR(10);
   -- 查询该月份绩效奖金是否生成
   -- SELECT @isExistKpiPay = COUNT(*) FROM sctrans.T_TS_AWARD_KPI_PAY WHERE DATEDIFF(mm, BELONG_MONTH, @belongMonth) = 0
   -- 统计rate数据的前提条件
   SELECT @isExistSrvCase = COUNT(*) FROM sctrans.T_TS_KPI_SRV_CASE WHERE DATEDIFF(mm, BELONG_MONTH, @belongMonth) = 0;
   SELECT @isExistMonthKpi = COUNT(*) FROM sctrans.T_TS_KPI_PSN_MONTH WHERE DATEDIFF(mm, BELONG_MONTH, @belongMonth) = 0;
   if @isExistSrvCase=0 OR @isExistMonthKpi=0
   BEGIN
     RETURN;
   END

   DELETE FROM sctrans.T_TS_AWARD_KPI_PAY WHERE DATEDIFF(m, BELONG_MONTH,@belongMonth) = 0;
   INSERT INTO sctrans.T_TS_AWARD_KPI_PAY (BELONG_MONTH,STATUS,CREATE_BY,CREATE_TIME)VALUES (@belongMonth,0,@createBy,@createTime);
   -- 根据案件环节KPI更新相关数据
   DELETE FROM sctrans.T_TS_AWARD_KPI_RATE WHERE DATEDIFF(m, BELONG_MONTH,@belongMonth) = 0;
   INSERT INTO sctrans.T_TS_AWARD_KPI_RATE(BELONG_MONTH,CASE_CODE,SRV_CODE,TEAM_ID,DISTRICT_ID,SRV_CASE_ID,PARTICIPANT,SKPI_RATE,CREATE_BY,CREATE_TIME,SRV_PART
   ,COM_AMOUNT_TOTAL,
   COM_AMOUNT_LS,COM_LS_RATE,COM_LS_KPI,MKPI,FIN_ORDER,FIN_ORDER_ROLL,FIN_ORDER_RATE,ORG_ORDER,AB_SRV_PART,ORG_ID)
	 (
	 
		SELECT @belongMonth,srvCase.CASE_CODE,srvCase.SRV_CODE,srvCase.TEAM_ID,srvCase.DISTRICT_ID,srvCase.PKID,
		ab.PARTICIPANT,
		(CASE
						WHEN (srvCase.SATISFACTION >= 0 AND srvCase.SATISFACTION <6) THEN
							'0.6'
						WHEN (srvCase.SATISFACTION >= 6 AND srvCase.SATISFACTION <7) THEN
							'0.8'
						WHEN (srvCase.SATISFACTION >= 7 AND srvCase.SATISFACTION <8) THEN
							'1.0'
						WHEN (srvCase.SATISFACTION >= 8 AND srvCase.SATISFACTION <9) THEN
							'1.2'
						WHEN (srvCase.SATISFACTION >=9) THEN
							'1.4'
						ELSE
							NULL
					 END),
		 @createBy,@createTime,srvCase.SRV_PART,
			/**贷款流失*/
			ls.COM_AMOUNT_TOTAL ,
			ls.COM_AMOUNT_LS ,
			ls.COM_LS_RATE ,
			CASE WHEN ls.COM_LS_RATE <= 0.3 THEN 1.4
			WHEN ls.COM_LS_RATE >0.3 AND ls.COM_LS_RATE <=0.4 THEN 1.2
			WHEN ls.COM_LS_RATE >0.4 AND ls.COM_LS_RATE <=0.5 THEN 1.0
			WHEN ls.COM_LS_RATE >0.5 AND ls.COM_LS_RATE <=0.55 THEN 0.8
			WHEN ls.COM_LS_RATE IS NULL THEN 1.4
			ELSE 0.6 END,
			/**金融贷款是否达标*/
			CASE WHEN jr.JOB_CODE IN ('consultant') 
			THEN   CASE WHEN ISNULL(jr.FIN_ORDER,0) + ISNULL(jr.FIN_ORDER_ROLL,0) -1 >=0 THEN 1 ELSE 0 END
			ELSE   CASE WHEN  jr.FIN_ORDER_RATE -0.04 >= 0 THEN 1 ELSE 0 END END
			,jr.FIN_ORDER,jr.FIN_ORDER_ROLL,jr.FIN_ORDER_RATE,jr.ORG_ORDER
			/**环节占比*/
			,ab.srv_part_in / ab.srv_part_total ,ab.ORG_ID
		 FROM sctrans.T_TS_KPI_SRV_CASE srvCase
     INNER JOIN sctrans.T_TS_AWARD_BASE ab
     ON srvCase.CASE_CODE = ab.CASE_CODE
     AND srvCase.SRV_CODE = ab.SRV_CODE
     AND srvCase.ORG_ID = ab.ORG_ID
	 LEFT JOIN sctrans.T_TS_KPI_PSN_MONTH jr
	ON ab.PARTICIPANT = jr.PARTICIPANT
	AND ab.ORG_ID = jr.ORG_ID
	AND jr.TYPE = 'JRDBL'
	and DATEDIFF(mm, jr.BELONG_MONTH, @belongMonth) = 0
	AND jr.IS_DELETE = 0
	left JOIN sctrans.T_TS_KPI_PSN_MONTH ls
	ON ab.PARTICIPANT = ls.PARTICIPANT
	AND ab.org_ID = ls.ORG_ID
	AND ls.TYPE = 'DKLSL'
    AND DATEDIFF(mm, ls.BELONG_MONTH, @belongMonth) = 0 
	AND ISNULL(ls.IS_DELETE,0) = 0
	WHERE ab.IS_DELETED = 0
	and DATEDIFF(mm, srvCase.BELONG_MONTH, @belongMonth) = 0
	and exists (select 'x' from sctrans.T_TS_KPI_SRV_CASE where CASE_CODE=ab.CASE_CODE and DATEDIFF(mm, BELONG_MONTH, @belongMonth) = 0)
		);

---将金融达标率翻译成1.4  1.2
UPDATE aw
SET aw.MKPIV= CASE WHEN  aw.MKPI =1 THEN 1.4 ELSE 1.2 END
FROM sctrans.T_TS_AWARD_KPI_RATE aw 
WHERE DATEDIFF(mm, aw.BELONG_MONTH, @belongMonth) = 0;
--计算最终绩效
UPDATE aw
SET aw.KPI_RATE_SUM= CASE WHEN  aw.COM_LS_KPI <aw.SKPI_RATE and aw.COM_LS_KPI< aw.MKPIV THEN aw.COM_LS_KPI
						  WHEN aw.SKPI_RATE <aw.COM_LS_KPI and aw.SKPI_RATE< aw.MKPIV THEN aw.SKPI_RATE
						  ELSE aw.MKPIV  END
FROM sctrans.T_TS_AWARD_KPI_RATE aw
WHERE DATEDIFF(mm, aw.BELONG_MONTH, @belongMonth) = 0;
--如果 满意度没有的话那就是0
UPDATE aw
SET aw.KPI_RATE_SUM= 0
FROM sctrans.T_TS_AWARD_KPI_RATE aw
WHERE DATEDIFF(mm, aw.BELONG_MONTH, @belongMonth) = 0
	and aw.SKPI_RATE is null;
UPDATE aw
SET aw.KPI_RATE_SUM= aw.SKPI_RATE
FROM sctrans.T_TS_AWARD_KPI_RATE aw
WHERE DATEDIFF(mm, aw.BELONG_MONTH, @belongMonth) = 0
 and aw.SRV_CODE='TeamAssistant';
 ----计算管理层的基础奖金
update ab 
set ab.base_amount=mabc.srv_fee
from sctrans.T_TS_AWARD_BASE ab
left join sctrans.T_TS_AWARD_KPI_RATE rate 
	on ab.CASE_CODE = rate.CASE_CODE
		AND ab.PARTICIPANT = rate.PARTICIPANT
		AND ab.SRV_CODE = rate.SRV_CODE
left join sctrans.T_TS_MANAGEMENT_AWARD_BASE_CONFIG mabc
on ab.participant=mabc.user_id and ab.org_id=mabc.org_Id
where ab.IS_DELETED = 0 and  DATEDIFF(mm, rate.BELONG_MONTH, @belongMonth) = 0
and ab.srv_code in ('zbjr','qzjd','director','GeneralManager','Senior_Manager');
;
--总经理不看环节占比 这里直接更新成1
update ab 
set ab.SRV_PART_IN=1,ab.SRV_PART_TOTAL=1,ab.SRV_PART=1
from sctrans.T_TS_AWARD_BASE ab
left join sctrans.T_TS_AWARD_KPI_RATE rate 
	on ab.CASE_CODE = rate.CASE_CODE
		AND ab.PARTICIPANT = rate.PARTICIPANT
		AND ab.SRV_CODE = rate.SRV_CODE
left join sctrans.T_TS_MANAGEMENT_AWARD_BASE_CONFIG mabc
on ab.participant=mabc.user_id and ab.org_id=mabc.org_Id
where ab.IS_DELETED = 0 and  DATEDIFF(mm, rate.BELONG_MONTH, @belongMonth) = 0
and ab.srv_code  ='GeneralManager';
---- 绩效奖金明细
   DELETE FROM sctrans.T_TS_AWARD_KPI_PAY_DETAIL WHERE DATEDIFF(m, BELONG_MONTH,@belongMonth) = 0
	 INSERT INTO sctrans.T_TS_AWARD_KPI_PAY_DETAIL(BELONG_MONTH,AWARD_KPI_PAY_ID,CASE_CODE,SRV_CODE,PARTICIPANT,AWARD_BASE_ID,KPI_RATE_ID,AWARD_KPI_MONEY,CREATE_BY,CREATE_TIME)
	 (
		SELECT @belongMonth,
    (SELECT TOP 1 PKID FROM sctrans.T_TS_AWARD_KPI_PAY WHERE DATEDIFF(mm, T_TS_AWARD_KPI_PAY.BELONG_MONTH, @belongMonth) = 0),
    base.CASE_CODE,
		base.SRV_CODE,
    base.PARTICIPANT,
    base.PKID,
    rate.PKID,
    base.BASE_AMOUNT*rate.AB_SRV_PART*ISNULL(rate.KPI_RATE_SUM, 1),
    @createBy,
    @createTime
    FROM sctrans.T_TS_AWARD_BASE base
		INNER JOIN sctrans.T_TS_AWARD_KPI_RATE rate
		ON base.CASE_CODE = rate.CASE_CODE
		AND base.PARTICIPANT = rate.PARTICIPANT
		AND base.SRV_CODE = rate.SRV_CODE
		and base.ORG_ID=rate.ORG_ID
		--AND base.TEAM_ID = rate.TEAM_ID
    --AND base.DISTRICT_ID = rate.DISTRICT_ID
		AND rate.BELONG_MONTH = @belongMonth
    WHERE rate.KPI_RATE_SUM IS NOT NULL
    AND base.IS_DELETED = 0
		);
  update d  set d.AWARD_KPI_MONEY= base.BASE_AMOUNT*rate.AB_SRV_PART*ISNULL(rate.KPI_RATE_SUM, 1)*rate.SRV_PART
   from 
  sctrans.T_TS_AWARD_KPI_PAY_DETAIL d
  INNER JOIN sctrans.T_TS_AWARD_BASE base
	on base.PKID = d.AWARD_BASE_ID
INNER JOIN sctrans.T_TS_AWARD_KPI_RATE rate
		ON base.CASE_CODE = rate.CASE_CODE
		AND base.PARTICIPANT = rate.PARTICIPANT
		and base.ORG_ID=rate.ORG_ID
		AND base.SRV_CODE = rate.SRV_CODE
   where d.SRV_CODE in ('qzjd','zbjr','Senior_Manager')
   AND rate.BELONG_MONTH = @belongMonth
    and rate.KPI_RATE_SUM IS NOT NULL
   ;

  update d  set d.AWARD_KPI_MONEY= base.BASE_AMOUNT*ISNULL(rate.KPI_RATE_SUM, 1)*rate.SRV_PART
   from 
  sctrans.T_TS_AWARD_KPI_PAY_DETAIL d
  INNER JOIN sctrans.T_TS_AWARD_BASE base
	on base.PKID = d.AWARD_BASE_ID
INNER JOIN sctrans.T_TS_AWARD_KPI_RATE rate
		ON base.CASE_CODE = rate.CASE_CODE
		AND base.PARTICIPANT = rate.PARTICIPANT
		AND base.SRV_CODE = rate.SRV_CODE

   where d.SRV_CODE ='TeamAssistant'
    AND rate.BELONG_MONTH = @belongMonth
    and rate.KPI_RATE_SUM IS NOT NULL;



    
    -- 更新总的绩效奖金,案件数量,发放人员
    UPDATE sctrans.T_TS_AWARD_KPI_PAY
    SET 
    AWARD_KPI_SUM = (SELECT SUM(AWARD_KPI_MONEY) FROM sctrans.T_TS_AWARD_KPI_PAY_DETAIL WHERE T_TS_AWARD_KPI_PAY_DETAIL.BELONG_MONTH = @belongMonth),
    CASE_COUNT = (SELECT COUNT(DISTINCT CASE_CODE) FROM sctrans.T_TS_AWARD_KPI_PAY_DETAIL WHERE T_TS_AWARD_KPI_PAY_DETAIL.BELONG_MONTH = @belongMonth),
    USER_COUNT = (SELECT COUNT(DISTINCT PARTICIPANT) FROM sctrans.T_TS_AWARD_KPI_PAY_DETAIL WHERE T_TS_AWARD_KPI_PAY_DETAIL.BELONG_MONTH = @belongMonth)
    WHERE DATEDIFF(mm, BELONG_MONTH, @belongMonth) = 0;
END
