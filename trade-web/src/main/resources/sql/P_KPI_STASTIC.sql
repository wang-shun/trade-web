USE [sctrans_prd]
GO

/****** Object:  StoredProcedure [sctrans].[P_KPI_STASTIC]    Script Date: 2016/10/13 16:28:20 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [sctrans].[P_KPI_STASTIC]
	-- Add the parameters for the stored procedure here
	@belongMonth datetime 
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
delete from sctrans.T_TS_KPI_SRV_CASE where BELONG_MONTH=@belongMonth and TYPE <> 'IMP';
    -- Insert statements for procedure here
	----S 1 设置Team_id和DistrictId
update tks set tks.TEAM_ID=tab.team_id ,tks.DISTRICT_ID=tab.district_id,tks.org_id=tab.org_id
from 
sctrans.T_TS_KPI_SRV_CASE as tks
inner join sctrans.T_TS_AWARD_BASE as tab
on tks.case_code=tab.case_code and tks.srv_code=tab.srv_code and tab.is_deleted='0' and tks.BELONG_MONTH=@belongMonth;
------S 2 计算交易主管数据
--S 2.1 插入交易主管数据
insert into sctrans.T_TS_KPI_SRV_CASE (BELONG_MONTH,CASE_CODE,SRV_CODE,TEAM_ID,DISTRICT_ID,CREATE_TIME,type,org_id)
select @belongMonth,a.CASE_CODE,a.SRV_CODE,a.TEAM_ID,a.DISTRICT_ID,GETDATE(),'GEN',a.org_id from sctrans.T_TS_AWARD_BASE as a 
where  a.is_deleted='0' 
 and a.SRV_CODE in ('zbjr','qzjd') and a.case_code + a.team_id in 
 (select case_code+team_id from sctrans.t_ts_kpi_srv_case where BELONG_MONTH=@belongMonth and TYPE='IMP');
 
 ;
 --S 2.2 汇总
UPDATE sctrans.T_TS_KPI_SRV_CASE
SET T_TS_KPI_SRV_CASE.SATISFACTION = 
(select AVG(SRV.SATISFACTION) from sctrans.T_TS_KPI_SRV_CASE SRV WHERE SRV.CASE_CODE = T_TS_KPI_SRV_CASE.CASE_CODE AND SRV.TEAM_ID = T_TS_KPI_SRV_CASE.TEAM_ID AND SRV.TYPE = 'IMP' 
GROUP BY SRV.CASE_CODE,SRV.TEAM_ID),
T_TS_KPI_SRV_CASE.SRV_PART = 
(select (count(SRV.SATISFACTION)+0.00)/count(SRV.PKID) from sctrans.T_TS_KPI_SRV_CASE SRV WHERE SRV.CASE_CODE = T_TS_KPI_SRV_CASE.CASE_CODE AND SRV.TEAM_ID = T_TS_KPI_SRV_CASE.TEAM_ID AND SRV.TYPE = 'IMP' 
GROUP BY SRV.CASE_CODE,SRV.TEAM_ID)
FROM sctrans.T_TS_KPI_SRV_CASE
WHERE T_TS_KPI_SRV_CASE.SRV_CODE in ('zbjr','qzjd') and t_ts_kpi_srv_case.BELONG_MONTH=@belongMonth ;

 --S 3.1 插入总监数据
insert into sctrans.T_TS_KPI_SRV_CASE (BELONG_MONTH,CASE_CODE,SRV_CODE,TEAM_ID,DISTRICT_ID,CREATE_TIME,type,org_id)
select @belongMonth,a.CASE_CODE,a.SRV_CODE,a.TEAM_ID,a.DISTRICT_ID,GETDATE(),'GEN',a.org_id from sctrans.T_TS_AWARD_BASE as a 
where  a.is_deleted='0' 
 and a.SRV_CODE in ('director') and a.case_code + a.DISTRICT_ID in 
 (select case_code+DISTRICT_ID from sctrans.t_ts_kpi_srv_case where BELONG_MONTH=@belongMonth and TYPE='IMP');

--S 3.2汇总
UPDATE sctrans.T_TS_KPI_SRV_CASE
SET T_TS_KPI_SRV_CASE.SATISFACTION = 
(select AVG(SRV.SATISFACTION) from sctrans.T_TS_KPI_SRV_CASE SRV WHERE SRV.CASE_CODE = T_TS_KPI_SRV_CASE.CASE_CODE AND SRV.DISTRICT_ID = T_TS_KPI_SRV_CASE.DISTRICT_ID AND SRV.TYPE = 'IMP' 
GROUP BY SRV.CASE_CODE,SRV.DISTRICT_ID),
T_TS_KPI_SRV_CASE.SRV_PART = 
(select (count(SRV.SATISFACTION)+0.00)/count(SRV.PKID) from sctrans.T_TS_KPI_SRV_CASE SRV WHERE SRV.CASE_CODE = T_TS_KPI_SRV_CASE.CASE_CODE AND SRV.DISTRICT_ID = T_TS_KPI_SRV_CASE.DISTRICT_ID AND SRV.TYPE = 'IMP' 
GROUP BY SRV.CASE_CODE,SRV.DISTRICT_ID)
FROM sctrans.T_TS_KPI_SRV_CASE
WHERE T_TS_KPI_SRV_CASE.SRV_CODE in ('director') and T_TS_KPI_SRV_CASE.BELONG_MONTH=@belongMonth;
--S 4.1 插入总经理数据
insert into sctrans.T_TS_KPI_SRV_CASE (BELONG_MONTH,CASE_CODE,SRV_CODE,TEAM_ID,DISTRICT_ID,CREATE_TIME,type,org_id)
select @belongMonth,a.CASE_CODE,a.SRV_CODE,a.TEAM_ID,a.DISTRICT_ID,GETDATE(),'GEN',a.org_id from sctrans.T_TS_AWARD_BASE as a 
where  a.is_deleted='0' 
 and a.SRV_CODE in ('GeneralManager') and a.case_code in 
 (select case_code from sctrans.t_ts_kpi_srv_case where BELONG_MONTH=@belongMonth and TYPE='IMP');
 
--S 4.2汇总
  UPDATE sctrans.T_TS_KPI_SRV_CASE
SET T_TS_KPI_SRV_CASE.SATISFACTION = 
(select AVG(SRV.SATISFACTION) from sctrans.T_TS_KPI_SRV_CASE SRV WHERE SRV.CASE_CODE = T_TS_KPI_SRV_CASE.CASE_CODE AND SRV.TYPE = 'IMP' and SRV.DISTRICT_ID is not null
GROUP BY SRV.CASE_CODE),
T_TS_KPI_SRV_CASE.SRV_PART = 
(select (count(SRV.SATISFACTION)+0.00)/count(SRV.PKID) from sctrans.T_TS_KPI_SRV_CASE SRV WHERE SRV.CASE_CODE = T_TS_KPI_SRV_CASE.CASE_CODE  AND SRV.TYPE = 'IMP' and SRV.DISTRICT_ID is not null
GROUP BY SRV.CASE_CODE)
FROM sctrans.T_TS_KPI_SRV_CASE
WHERE T_TS_KPI_SRV_CASE.SRV_CODE in ('GeneralManager') and T_TS_KPI_SRV_CASE.BELONG_MONTH=@belongMonth;

 --S 5.1 插入助理数据
 INSERT INTO sctrans.T_TS_KPI_SRV_CASE
        ( BELONG_MONTH ,
          CASE_CODE ,
          SRV_CODE ,
          TEAM_ID ,
          DISTRICT_ID ,
          CREATE_TIME ,
          type,org_id
        )
        SELECT  @belongMonth ,
                a.CASE_CODE ,
                a.SRV_CODE ,
                a.TEAM_ID ,
                a.DISTRICT_ID ,
                GETDATE() ,
                'GEN',a.org_id
        FROM    sctrans.T_TS_AWARD_BASE AS a
        WHERE   a.is_deleted = '0'
                AND a.SRV_CODE IN ( 'TeamAssistant' )
                AND a.case_code + a.TEAM_ID IN (
                SELECT  case_code + TEAM_ID
                FROM    sctrans.t_ts_kpi_srv_case
                WHERE   BELONG_MONTH = @belongMonth
                        AND TYPE = 'IMP' );

--S 5.1汇总
 UPDATE sctrans.T_TS_KPI_SRV_CASE
 SET    T_TS_KPI_SRV_CASE.SATISFACTION = ( SELECT   AVG(SRV.SATISFACTION)
                                           FROM     sctrans.T_TS_KPI_SRV_CASE SRV
                                           WHERE    SRV.CASE_CODE = T_TS_KPI_SRV_CASE.CASE_CODE
                                                    AND SRV.TEAM_ID = T_TS_KPI_SRV_CASE.TEAM_ID
                                                    AND SRV.TYPE = 'IMP'
													AND SRV.SRV_CODE IN ('TransSign','ComLoanProcess')
                                           GROUP BY SRV.CASE_CODE 
                                         ) ,
        T_TS_KPI_SRV_CASE.SRV_PART = ( SELECT   ( COUNT(SRV.SATISFACTION)
                                                  + 0.00 ) / COUNT(SRV.PKID)
                                       FROM     sctrans.T_TS_KPI_SRV_CASE SRV
                                       WHERE    SRV.CASE_CODE = T_TS_KPI_SRV_CASE.CASE_CODE
                                                AND SRV.TEAM_ID = T_TS_KPI_SRV_CASE.TEAM_ID
												AND SRV.SRV_CODE IN ('TransSign','ComLoanProcess')
                                                AND SRV.TYPE = 'IMP'
                                       GROUP BY SRV.CASE_CODE
                                     )
 FROM   sctrans.T_TS_KPI_SRV_CASE
 WHERE  T_TS_KPI_SRV_CASE.SRV_CODE IN ( 'TeamAssistant' )
        AND T_TS_KPI_SRV_CASE.BELONG_MONTH = @belongMonth;

 --S 6.1 插入高级主管数据
insert into sctrans.T_TS_KPI_SRV_CASE (BELONG_MONTH,CASE_CODE,SRV_CODE,TEAM_ID,DISTRICT_ID,CREATE_TIME,type,org_id)
select @belongMonth,a.CASE_CODE,a.SRV_CODE,a.TEAM_ID,a.DISTRICT_ID,GETDATE(),'GEN',a.org_id from sctrans.T_TS_AWARD_BASE as a 
where  a.is_deleted='0' 
 and a.SRV_CODE in ('Senior_Manager') and a.case_code + a.team_id in 
 (select case_code+team_id from sctrans.t_ts_kpi_srv_case where BELONG_MONTH=@belongMonth and TYPE='IMP');

--S 6.2汇总
UPDATE sctrans.T_TS_KPI_SRV_CASE
SET T_TS_KPI_SRV_CASE.SATISFACTION = 
(select AVG(SRV.SATISFACTION) from sctrans.T_TS_KPI_SRV_CASE SRV WHERE SRV.CASE_CODE = T_TS_KPI_SRV_CASE.CASE_CODE AND SRV.TEAM_ID = T_TS_KPI_SRV_CASE.TEAM_ID AND SRV.TYPE = 'IMP' 
GROUP BY SRV.CASE_CODE,SRV.TEAM_ID),
T_TS_KPI_SRV_CASE.SRV_PART = 
(select (count(SRV.SATISFACTION)+0.00)/count(SRV.PKID) from sctrans.T_TS_KPI_SRV_CASE SRV WHERE SRV.CASE_CODE = T_TS_KPI_SRV_CASE.CASE_CODE AND SRV.TEAM_ID = T_TS_KPI_SRV_CASE.TEAM_ID AND SRV.TYPE = 'IMP' 
GROUP BY SRV.CASE_CODE,SRV.TEAM_ID)
FROM sctrans.T_TS_KPI_SRV_CASE
WHERE T_TS_KPI_SRV_CASE.SRV_CODE in ('Senior_Manager') and t_ts_kpi_srv_case.BELONG_MONTH=@belongMonth ;

END

GO

