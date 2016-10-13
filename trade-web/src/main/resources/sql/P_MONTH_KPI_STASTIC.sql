USE [sctrans_prd]
GO

/****** Object:  StoredProcedure [sctrans].[P_MONTH_KPI_STASTIC]    Script Date: 2016/10/13 16:27:12 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

ALTER PROCEDURE [sctrans].[P_MONTH_KPI_STASTIC]
@belongMonth datetime,
@createBy VARCHAR(200)
AS
BEGIN
	 /**
		月度KPI计算
		****这里计算的时候对于一个人即是一个组织的管理岗又是这个组织的上级的管理岗的情况在这考虑，计算奖金的时候一并处理****
		1)金融产品达标率计算
			1.1顾问是否达标按当月是否完成一单(每月完成单数大于一单，大于单数需滚入下月考核)
			1.2管理层是否达标按本组织当月金融产品完成率(当月金融产品完成数/当月过户数)
		2)贷款流失率计算
			2.1只有主办才有贷款流失率的考核
		*/
--1)金融产品达标率计算开始
--维护组织信息
  UPDATE m
  SET m.participant=uojm.user_id,
  m.TEAM_ID = uojm.org_id,
  m.DISTRICT_ID = oh.DISTRICT_ID,
  m.JOB_CODE = uojm.JOB_CODE,
  m.create_time = getdate(),
  m.org_id = uojm.org_id,
  m.participant_name=uojm.USERNAME
  from sctrans.T_TS_KPI_PSN_MONTH m
  inner join sctrans.v_user_job_org_main uojm
  on m.participant_name  = uojm.EMPLOYEE_CODE
  and m.belong_month=@belongMonth
  and m.type='JRDBL'
  and m.comments like '%IMP%'
 -- and m.participant is null
  inner join sctrans.v_yucui_org_hierarchy oh
  on uojm.org_id = oh.org_id  ;


  ----拿到每个交易顾问的金融滚动单数
  update m
  set m.FIN_ORDER_ROLL = cast((select top 1 result 
					from sctrans.T_TS_KPI_PSN_MONTH m2 
					where datediff(mm,m2.BELONG_MONTH,@belongMonth)>0
					and m2.IS_DELETE = 0 
					and m2.PARTICIPANT = m.PARTICIPANT
					order by m2.belong_month desc )as int)
  from sctrans.T_TS_KPI_PSN_MONTH m 
  where m.belong_month=@belongMonth
  and m.type='JRDBL'
  and m.JOB_CODE = 'consultant'
  and m.IS_DELETE = 0;
  ----生成组级别的KPI数据（主管和高级主管)
  INSERT INTO sctrans.T_TS_KPI_PSN_MONTH
          ( 
            PARTICIPANT ,
			PARTICIPANT_NAME,
			JOB_CODE,
            TEAM_ID ,
            DISTRICT_ID ,
            ORG_ORDER 
			,BELONG_MONTH ,
            TYPE ,
			CREATE_TIME ,
            CREATE_BY ,
            UPDATE_TIME ,
            UPDATE_BY,
			COMMENTS,
			ORG_ID
          )
 SELECT ol.USER_ID, ol.USERNAME, ol.JOB_CODE, ol.org_id, a.DISTRICT_ID, a.ORG_ORDER
  , @belongMonth, 'JRDBL', GETDATE(), @createBy, GETDATE(), @createBy,'CMP', a.TEAM_ID
  FROM (
  SELECT m.TEAM_ID,m.DISTRICT_ID, SUM(m.FIN_ORDER) ORG_ORDER
  FROM  sctrans.T_TS_KPI_PSN_MONTH m
  WHERE m.TYPE = 'JRDBL'
  AND m.BELONG_MONTH = @belongMonth
  GROUP BY m.TEAM_ID, m.DISTRICT_ID
 ) AS a
 INNER JOIN sctrans.v_user_job_org_leader ol
 ON a.TEAM_ID = ol.org_id
 AND  (ol.JOB_CODE = 'Manager' or ol.JOB_CODE = 'Senior_Manager');
 
 --生成贵宾服务部级的KPI(总监)
 INSERT INTO sctrans.T_TS_KPI_PSN_MONTH
          ( 
            PARTICIPANT ,
			PARTICIPANT_NAME,
			JOB_CODE,
          
            DISTRICT_ID ,
            ORG_ORDER 
			,BELONG_MONTH ,
            TYPE ,
			CREATE_TIME ,
            CREATE_BY ,
            UPDATE_TIME ,
            UPDATE_BY,
			COMMENTS,
			ORG_ID
          )
 SELECT  ol.USER_ID, ol.USERNAME, ol.JOB_CODE, a.DISTRICT_ID, a.ORG_ORDER
  , @belongMonth, 'JRDBL', GETDATE(), @createBy, GETDATE(), @createBy,'CMP',a.DISTRICT_ID
  FROM (
  SELECT m.DISTRICT_ID, SUM(m.FIN_ORDER) ORG_ORDER
  FROM  sctrans.T_TS_KPI_PSN_MONTH m
  WHERE m.TYPE = 'JRDBL'
  AND m.BELONG_MONTH = @belongMonth
  and m.COMMENTS = 'IMP'
  GROUP BY  m.DISTRICT_ID
 ) AS a
 INNER JOIN sctrans.v_user_job_org_leader ol
 ON a.DISTRICT_ID = ol.org_id
 AND  ol.JOB_CODE = 'director';
  ---生成HQ级别KPI数据
  INSERT INTO sctrans.T_TS_KPI_PSN_MONTH
          ( 
            PARTICIPANT ,
			PARTICIPANT_NAME,
			JOB_CODE,
			ORG_ID,
            
            ORG_ORDER 
			,BELONG_MONTH ,
            TYPE ,
			CREATE_TIME ,
            CREATE_BY ,
            UPDATE_TIME ,
            UPDATE_BY,
			COMMENTS
          )
 SELECT  ol.USER_ID, ol.USERNAME, ol.JOB_CODE, ol.org_id, a.ORG_ORDER
  , @belongMonth, 'JRDBL', GETDATE(),@createBy, GETDATE(), @createBy,'CMP'
  FROM (
  SELECT  SUM(m.FIN_ORDER) ORG_ORDER
  FROM  sctrans.T_TS_KPI_PSN_MONTH m
  WHERE m.TYPE = 'JRDBL'
  AND m.BELONG_MONTH = @belongMonth
  and m.COMMENTS = 'IMP'
 ) AS a
 INNER JOIN sctrans.v_user_job_org_leader ol
 ON ol.JOB_CODE = 'GeneralManager'
 AND ol.ORG_ID = 'ff8080814f459a78014f45a73d820006'
 ;

 ---将主管、高级主管、总监、总经理的个人数据标注为删除，只看组织数据
 update m
 set m.is_delete=1,
 m.comments = m.comments+'|DelMgrPersonData'
 from sctrans.T_TS_KPI_PSN_MONTH m 
 where m.TYPE = 'JRDBL'
  AND m.BELONG_MONTH = @belongMonth
  and m.comments like '%IMP%'
  and m.job_code in ('Manager','Senior_Manager', 'director','GeneralManager');

---更新主管和高级主管过户数
UPDATE m
 SET m.TOTAL_CASE = a.team_case
--SELECT  m.TEAM_ID, a.team_case
 FROM sctrans.T_TS_KPI_PSN_MONTH m
 INNER JOIN 
	 (SELECT c.ORG_ID, COUNT(1) AS team_case
		 FROM sctrans.T_TO_CASE c
		 INNER JOIN (SELECT t.end_time_ as GUO_HU_APPROVE_TIME,p.BUSINESS_KEY_
          FROM      sctrans.act_hi_varinst v
                    INNER JOIN ( SELECT t.proc_inst_id_ ,
                                        MAX(end_time_) end_time_
                                 FROM   sctrans.act_hi_taskinst t
                                 WHERE  t.task_def_key_ = 'GuohuApprove'
                                 GROUP BY t.proc_inst_id_
                               ) t ON t.proc_inst_id_ = v.PROC_INST_ID_
                                      AND v.name_ = 'GuohuApprove'
                                      AND v.long_ =1
					INNER JOIN sctrans.ACT_HI_PROCINST p
					on p.PROC_INST_ID_=v.PROC_INST_ID_) bec
		 ON c.CASE_CODE = bec.BUSINESS_KEY_
		 INNER JOIN sctrans.V_RPT_CASE_PROCESS_STATIS cs
		 ON c.CASE_CODE = cs.CASE_CODE
		 AND DATEDIFF(mm,cs.HOUSE_TRANFER_TIME,@belongMonth)=0
		 AND DATEDIFF(mm,bec.GUO_HU_APPROVE_TIME,@belongMonth)=0
		 GROUP BY c.ORG_ID) a
 ON  m.TEAM_ID= a.ORG_ID
  where 
  m.TYPE = 'JRDBL'
  AND m.BELONG_MONTH = @belongMonth
  AND m.JOB_CODE in ('Manager','Senior_Manager')
  and m.is_delete=0 ;
---更新总监过户数
UPDATE m
  SET m.TOTAL_CASE = a.dist_case
 FROM sctrans.T_TS_KPI_PSN_MONTH m
 INNER JOIN 
	 (SELECT oh.DISTRICT_ID, oh.DISTRICT_NAME, COUNT(1) AS dist_case
		 FROM sctrans.T_TO_CASE c
		 INNER JOIN (SELECT t.end_time_ as GUO_HU_APPROVE_TIME,p.BUSINESS_KEY_
          FROM      sctrans.act_hi_varinst v
                    INNER JOIN ( SELECT t.proc_inst_id_ ,
                                        MAX(end_time_) end_time_
                                 FROM   sctrans.act_hi_taskinst t
                                 WHERE  t.task_def_key_ = 'GuohuApprove'
                                 GROUP BY t.proc_inst_id_
                               ) t ON t.proc_inst_id_ = v.PROC_INST_ID_
                                      AND v.name_ = 'GuohuApprove'
                                      AND v.long_ =1
					INNER JOIN sctrans.ACT_HI_PROCINST p
					on p.PROC_INST_ID_=v.PROC_INST_ID_) bec
		 ON c.CTM_CODE = bec.BUSINESS_KEY_
		 INNER JOIN sctrans.V_RPT_CASE_PROCESS_STATIS cs
		 ON c.CASE_CODE = cs.CASE_CODE
		 AND DATEDIFF(mm,cs.HOUSE_TRANFER_TIME,@belongMonth)=0
		 AND DATEDIFF(mm,bec.GUO_HU_APPROVE_TIME,@belongMonth)=0
		 INNER JOIN sctrans.v_yucui_org_hierarchy oh
		 ON c.ORG_ID = oh.ORG_ID
		 GROUP BY oh.DISTRICT_ID, oh.DISTRICT_NAME) a
 ON  m.TYPE = 'JRDBL'
  AND m.BELONG_MONTH = @belongMonth
  AND m.JOB_CODE = 'director'
  AND m.DISTRICT_ID= a.DISTRICT_ID
  AND m.IS_DELETE = 0;
---更新总经理过户数
UPDATE m
  SET m.TOTAL_CASE = a.dist_case
 FROM sctrans.T_TS_KPI_PSN_MONTH m
 INNER JOIN 
	 (SELECT oh.HQ_ID, COUNT(1) AS dist_case
		 FROM sctrans.T_TO_CASE c
		 INNER JOIN (SELECT t.end_time_ as GUO_HU_APPROVE_TIME,p.BUSINESS_KEY_
          FROM      sctrans.act_hi_varinst v
                    INNER JOIN ( SELECT t.proc_inst_id_ ,
                                        MAX(end_time_) end_time_
                                 FROM   sctrans.act_hi_taskinst t
                                 WHERE  t.task_def_key_ = 'GuohuApprove'
                                 GROUP BY t.proc_inst_id_
                               ) t ON t.proc_inst_id_ = v.PROC_INST_ID_
                                      AND v.name_ = 'GuohuApprove'
                                      AND v.long_ =1
					INNER JOIN sctrans.ACT_HI_PROCINST p
					on p.PROC_INST_ID_=v.PROC_INST_ID_) bec
		 ON c.CTM_CODE = bec.BUSINESS_KEY_
		 INNER JOIN sctrans.V_RPT_CASE_PROCESS_STATIS cs
		 ON c.CASE_CODE = cs.CASE_CODE
		 AND DATEDIFF(mm,cs.HOUSE_TRANFER_TIME,@belongMonth)=0
		 AND DATEDIFF(mm,bec.GUO_HU_APPROVE_TIME,@belongMonth)=0
		 INNER JOIN sctrans.v_yucui_org_hierarchy oh
		 ON c.ORG_ID = oh.ORG_ID
		 GROUP BY oh.HQ_ID) a
 ON  m.TYPE = 'JRDBL'
  AND m.BELONG_MONTH = @belongMonth
  AND m.JOB_CODE = 'GeneralManager'
  AND m.ORG_ID= a.HQ_ID
  and m.is_delete=0;
  --计算管理层金融产品完成率
  UPDATE m
  SET m.FIN_ORDER_RATE = CASE WHEN m.TOTAL_CASE IS NOT NULL
                   THEN CAST( ISNULL(m.ORG_ORDER,0) AS FLOAT ) / CAST( m.TOTAL_CASE AS FLOAT)
                   ELSE 1
              END 
  FROM      sctrans.T_TS_KPI_PSN_MONTH m
  WHERE     m.TYPE = 'JRDBL'
            AND m.BELONG_MONTH = @belongMonth
            AND m.JOB_CODE IN ( 'director', 'Manager','Senior_Manager','GeneralManager' )
			and m.IS_DELETE = 0;
--计算金融达标率考核结果
UPDATE m
SET m.RESULT = CASE WHEN m.JOB_CODE IN ('consultant') THEN ISNULL(m.FIN_ORDER,0) + ISNULL(m.FIN_ORDER_ROLL,0) -1
	ELSE m.FIN_ORDER_RATE -0.04 end
FROM      sctrans.T_TS_KPI_PSN_MONTH m
  WHERE     m.TYPE = 'JRDBL'
            AND m.BELONG_MONTH =@belongMonth
			AND m.IS_DELETE = 0;
--1)金融产品达标率计算结束
--2)贷款流失率计算开始
--维护贷款相关统计数据 
 INSERT INTO sctrans.T_TS_KPI_PSN_MONTH
          ( 
            PARTICIPANT ,
			PARTICIPANT_NAME,
			JOB_CODE,
            TEAM_ID ,
            DISTRICT_ID ,
			ORG_ID,
            COM_AMOUNT_TOTAL,
			COM_AMOUNT_YC,
			COM_AMOUNT_LS,
			BELONG_MONTH ,
            TYPE ,
			CREATE_TIME ,
            CREATE_BY ,
            UPDATE_TIME ,
            UPDATE_BY 
          )
SELECT tmp.LEADING_PROCESS_ID, om.USERNAME, om.JOB_CODE, om.ORG_ID, oh.DISTRICT_ID, oh.ORG_ID,
 tmp.com_total, tmp.com_yc_total, tmp.com_total - tmp.com_yc_total, @belongMonth, 'DKLSL', GETDATE(), @createBy, GETDATE(), @createBy
FROM( SELECT c2.LEADING_PROCESS_ID, SUM(m.COM_AMOUNT) AS com_total, SUM(m.COM_AMOUNT * m.IS_DELEGATE_YUCUI) com_yc_total
		FROM (SELECT t.end_time_ as GUO_HU_APPROVE_TIME,p.BUSINESS_KEY_
          FROM      sctrans.act_hi_varinst v
                    INNER JOIN ( SELECT t.proc_inst_id_ ,
                                        MAX(end_time_) end_time_
                                 FROM   sctrans.act_hi_taskinst t
                                 WHERE  t.task_def_key_ = 'GuohuApprove'
                                 GROUP BY t.proc_inst_id_
                               ) t ON t.proc_inst_id_ = v.PROC_INST_ID_
                                      AND v.name_ = 'GuohuApprove'
                                      AND v.long_ =1
					INNER JOIN sctrans.ACT_HI_PROCINST p
					on p.PROC_INST_ID_=v.PROC_INST_ID_) c
		INNER JOIN sctrans.T_TO_CASE c2
		ON c.BUSINESS_KEY_ = c2.CASE_CODE  ---1355
		 INNER JOIN sctrans.V_RPT_CASE_PROCESS_STATIS cs
		 ON c.BUSINESS_KEY_ = cs.CASE_CODE
		 AND DATEDIFF(mm,cs.HOUSE_TRANFER_TIME,@belongMonth)=0
		 AND DATEDIFF(mm,c.GUO_HU_APPROVE_TIME,@belongMonth)=0
		INNER JOIN sctrans.T_TO_MORTGAGE m
		ON c.BUSINESS_KEY_ = m.CASE_CODE   
		AND m.MORT_TYPE IN ('30016001','30016002') 
		AND m.COM_AMOUNT IS NOT NULL    
		GROUP BY c2.LEADING_PROCESS_ID
		) tmp  
left join sctrans.v_user_job_org_main om
on tmp.leading_process_id = om.user_id
and om.JOB_CODE = 'consultant'
LEFT JOIN sctrans.v_yucui_org_hierarchy oh
ON om.ORG_ID = oh.TEAM_ID;
----处理主办人已经不是交易顾问的数据
----这种单子不算主办的贷款流失率，但金额需要存入交易组的金额计算，
----所以将其is_delete设为1，在关联查询KPI时不查；job_code设为consultant，在往组内汇总时需要。
----同时将comments字段加上备注'C2M',说明主办已升职为主管
UPDATE m
SET m.PARTICIPANT_name = uoj.USERNAME, m.TEAM_ID = oh.TEAM_ID, m.DISTRICT_ID = oh.DISTRICT_ID,
m.JOB_CODE = 'consultant', m.IS_DELETE='1', m.COMMENTS = 'C2M'
--SELECT * 
FROM sctrans.T_TS_KPI_PSN_MONTH m
INNER JOIN  sctrans.V_USER_ORG_JOB uoj
 ON m.PARTICIPANT = uoj.USER_ID
 AND uoj.ismain=1
INNER JOIN sctrans.v_yucui_org_hierarchy oh
ON uoj.ORG_ID = oh.TEAM_ID
WHERE m.TYPE = 'DKLSL' AND m.BELONG_MONTH = @belongMonth
AND m.PARTICIPANT_name IS NULL;
--所有前台组主管的贷款流失率 
INSERT INTO sctrans.T_TS_KPI_PSN_MONTH
          ( 
            TEAM_ID ,DISTRICT_ID ,ORG_ID,PARTICIPANT, PARTICIPANT_NAME, JOB_CODE,
            COM_AMOUNT_TOTAL, COM_AMOUNT_YC, COM_AMOUNT_LS, BELONG_MONTH ,
            TYPE , CREATE_TIME ,CREATE_BY ,UPDATE_TIME ,UPDATE_BY , IS_DELETE
          )
SELECT tmp.TEAM_ID, tmp.DISTRICT_ID,uoj.ORG_ID ,uoj.USER_ID, uoj.USERNAME, uoj.JOB_CODE, 
  tmp.c_a_t, tmp.c_a_y, tmp.c_a_l, @belongMonth, 'DKLSL', GETDATE(), @createBy, GETDATE(), @createBy,0
FROM 
(
	SELECT m.TEAM_ID,m.DISTRICT_ID, SUM(m.COM_AMOUNT_TOTAL) AS c_a_t, SUM(m.COM_AMOUNT_YC) c_a_y, SUM(m.COM_AMOUNT_LS) c_a_l 
	FROM sctrans.T_TS_KPI_PSN_MONTH m 
	WHERE m.TYPE = 'DKLSL' AND m.BELONG_MONTH = @belongMonth AND m.JOB_CODE = 'consultant' AND m.IS_DELETE = 0
	GROUP BY m.TEAM_ID, m.DISTRICT_ID
) tmp
left JOIN sctrans.V_USER_ORG_JOB uoj
ON tmp.TEAM_ID = uoj.ORG_ID
AND uoj.JOB_CODE in('Manager','Senior_Manager')
AND uoj.IS_LEADER = 1;
--AND uoj.ismain = 1;不能有这个条件，主管的贷款流失率也是按组计算的（某主管在某组别上的流失率是多少）。
--所有前台组的上级总监的贷款流失率 
INSERT INTO sctrans.T_TS_KPI_PSN_MONTH
          ( 
            DISTRICT_ID ,ORG_ID,PARTICIPANT, PARTICIPANT_NAME, JOB_CODE,
            COM_AMOUNT_TOTAL, COM_AMOUNT_YC, COM_AMOUNT_LS, BELONG_MONTH ,
            TYPE , CREATE_TIME ,CREATE_BY ,UPDATE_TIME ,UPDATE_BY , IS_DELETE
          )
SELECT  tmp.DISTRICT_ID,uoj.ORG_ID ,uoj.USER_ID, uoj.USERNAME, 'director', 
  tmp.c_a_t, tmp.c_a_y, tmp.c_a_l, @belongMonth, 'DKLSL', GETDATE(), @createBy, GETDATE(), @createBy,0
FROM 
(
	SELECT m.DISTRICT_ID, SUM(m.COM_AMOUNT_TOTAL) AS c_a_t, SUM(m.COM_AMOUNT_YC) c_a_y, SUM(m.COM_AMOUNT_LS) c_a_l 
	FROM sctrans.T_TS_KPI_PSN_MONTH m 
	WHERE m.TYPE = 'DKLSL' AND m.BELONG_MONTH = @belongMonth AND m.JOB_CODE = 'consultant' AND m.IS_DELETE = 0
	GROUP BY m.DISTRICT_ID
) tmp
left JOIN sctrans.V_USER_ORG_JOB uoj
ON tmp.DISTRICT_ID = uoj.ORG_ID
AND uoj.JOB_CODE = 'director'
AND uoj.IS_LEADER = 1;
--AND uoj.ismain = 1;不能有这个条件，主管的贷款流失率也是按组计算的（某主管在某组别上的流失率是多少）。

--总经理的贷款流失率
 INSERT INTO sctrans.T_TS_KPI_PSN_MONTH ( 
            TEAM_ID ,DISTRICT_ID , PARTICIPANT, PARTICIPANT_NAME, JOB_CODE,
            COM_AMOUNT_TOTAL, COM_AMOUNT_YC, COM_AMOUNT_LS, BELONG_MONTH ,
            TYPE , CREATE_TIME ,CREATE_BY ,UPDATE_TIME ,UPDATE_BY ,IS_DELETE,ORG_ID )
SELECT NULL,  NULL,uoj.USER_ID , uoj.USERNAME, 'GeneralManager', 
  tmp.c_a_t, tmp.c_a_y, tmp.c_a_l, @belongMonth, 'DKLSL', GETDATE(), @createBy, GETDATE(), @createBy,0,'ff8080814f459a78014f45a73d820006'
FROM 
(
	SELECT SUM(m.COM_AMOUNT_TOTAL) AS c_a_t, SUM(m.COM_AMOUNT_YC) c_a_y, SUM(m.COM_AMOUNT_LS) c_a_l 
	FROM sctrans.T_TS_KPI_PSN_MONTH m 
	WHERE m.TYPE = 'DKLSL' AND m.BELONG_MONTH = @belongMonth AND m.JOB_CODE = 'consultant' AND m.IS_DELETE=0
) tmp
INNER JOIN sctrans.V_USER_ORG_JOB uoj
 ON uoj.IS_LEADER = 1
 AND uoj.JOB_CODE = 'GeneralManager'
 AND uoj.ORG_ID = 'ff8080814f459a78014f45a73d820006';
 --AND ol.ORG_ID = 'ff8080814f459a78014f45a73d820006';


 ----计算贷款流水率
UPDATE m
SET m.COM_LS_RATE =  ISNULL(m.COM_AMOUNT_LS,0) / ISNULL(m.COM_AMOUNT_TOTAL,1)
FROM sctrans.T_TS_KPI_PSN_MONTH m 
WHERE m.TYPE = 'DKLSL' AND m.BELONG_MONTH = @belongMonth AND m.IS_DELETE=0
 --计算贷款流失率结束
 --月度KPI计算结束
 END




GO

