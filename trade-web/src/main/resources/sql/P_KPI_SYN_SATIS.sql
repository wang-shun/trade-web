USE [sctrans_dev]
GO
/****** Object:  StoredProcedure [sctrans].[P_KPI_SYN_SATIS]    Script Date: 2017/5/19 16:35:49 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:    <Author,,Name>
-- Create date: <Create Date,,>
-- Description: <Description,,>
-- =============================================
CREATE PROCEDURE [sctrans].[P_KPI_SYN_SATIS]
  -- Add the parameters for the stored procedure here
  @guohu_approve_time datetime, 
  @belong_month datetime
AS
BEGIN
  -- 1.先将BELONG_MONTH为传入参数的数据删除，避免数据重复
  SET NOCOUNT ON;

  BEGIN TRY
    BEGIN  TRAN

  delete from sctrans.T_TS_KPI_SRV_CASE where BELONG_MONTH=@belong_month and TYPE = 'IMP';

  --2.导入数据
  insert into sctrans.T_TS_KPI_SRV_CASE (BELONG_MONTH,CASE_CODE,SRV_CODE,SALER_SATIS,BUYER_SATIS,SATISFACTION,SALER_CALLBACK,BUYER_CALLBACK,CAN_CALLBACK,SALER_COMMENT,
    BUYER_COMMENT,TEAM_ID,DISTRICT_ID,CREATE_TIME,TYPE,ORG_ID)

  select @belong_month,a.CASE_CODE,a.SRV_CODE,s.SALER_SIGN_SAT,s.BUYER_SIGN_SAT,(s.SALER_SIGN_SAT+s.BUYER_SIGN_SAT)/2,s.SALER_PHONE_OK,s.BUYER_PHONE_OK,
  CASE WHEN (s.SALER_PHONE_OK = '1' AND s.BUYER_PHONE_OK = '1') THEN '1' ELSE '0' END,s.SALER_SIGN_COM,s.BUYER_SIGN_COM,a.TEAM_ID,a.DISTRICT_ID,GETDATE(),'IMP',a.org_id 
  from sctrans.T_TS_AWARD_CASE_CENTAL as c 
  left join sctrans.T_CS_CASE_SATISFACTION as s on c.CASE_CODE = s.CASE_CODE
  left join sctrans.T_TS_AWARD_BASE as a on s.CASE_CODE = a.CASE_CODE
  where  a.is_deleted='0' and c.GUOHU_APPROVE_TIME <= @guohu_approve_time and s.STATUS = '7' and a.SRV_CODE = 'TransSign'  UNION ALL

  select @belong_month,a.CASE_CODE,a.SRV_CODE,s.SALER_LOANCLOSE_SAT,NULL,s.SALER_LOANCLOSE_SAT,s.SALER_PHONE_OK,s.BUYER_PHONE_OK,
  CASE WHEN (s.SALER_PHONE_OK = '1' AND s.BUYER_PHONE_OK = '1') THEN '1' ELSE '0' END,s.SALER_LOANCLOSE_COM,NULL,a.TEAM_ID,a.DISTRICT_ID,GETDATE(),'IMP',a.org_id 
  from sctrans.T_TS_AWARD_CASE_CENTAL as c 
  left join sctrans.T_CS_CASE_SATISFACTION as s on c.CASE_CODE = s.CASE_CODE
  left join sctrans.T_TS_AWARD_BASE as a on s.CASE_CODE = a.CASE_CODE
  where  a.is_deleted='0' and c.GUOHU_APPROVE_TIME <= @guohu_approve_time and s.STATUS = '7' and a.SRV_CODE = 'LoanClose'  UNION ALL

  select @belong_month,a.CASE_CODE,a.SRV_CODE,s.SALER_GUOHU_SAT,s.BUYER_GUOHU_SAT,(s.SALER_GUOHU_SAT+s.BUYER_GUOHU_SAT)/2,s.SALER_PHONE_OK,s.BUYER_PHONE_OK,
  CASE WHEN (s.SALER_PHONE_OK = '1' AND s.BUYER_PHONE_OK = '1') THEN '1' ELSE '0' END,s.SALER_GUOHU_COM,s.BUYER_GUOHU_COM,a.TEAM_ID,a.DISTRICT_ID,GETDATE(),'IMP',a.org_id 
  from sctrans.T_TS_AWARD_CASE_CENTAL as c 
  left join sctrans.T_CS_CASE_SATISFACTION as s on c.CASE_CODE = s.CASE_CODE
  left join sctrans.T_TS_AWARD_BASE as a on s.CASE_CODE = a.CASE_CODE
  where  a.is_deleted='0' and c.GUOHU_APPROVE_TIME <= @guohu_approve_time and s.STATUS = '7' and a.SRV_CODE = 'Guohu'  UNION ALL

  select @belong_month,a.CASE_CODE,a.SRV_CODE,NULL,s.BUYER_PSFLOAN_SAT,s.BUYER_PSFLOAN_SAT,s.SALER_PHONE_OK,s.BUYER_PHONE_OK,
  CASE WHEN (s.SALER_PHONE_OK = '1' AND s.BUYER_PHONE_OK = '1') THEN '1' ELSE '0' END,NULL,s.BUYER_PSFLOAN_COM,a.TEAM_ID,a.DISTRICT_ID,GETDATE(),'IMP',a.org_id 
  from sctrans.T_TS_AWARD_CASE_CENTAL as c 
  left join sctrans.T_CS_CASE_SATISFACTION as s on c.CASE_CODE = s.CASE_CODE
  left join sctrans.T_TS_AWARD_BASE as a on s.CASE_CODE = a.CASE_CODE
  where  a.is_deleted='0' and c.GUOHU_APPROVE_TIME <= @guohu_approve_time and s.STATUS = '7' and a.SRV_CODE = 'PSFSign'  UNION ALL

  select @belong_month,a.CASE_CODE,a.SRV_CODE,NULL,s.BUYER_COMLOAN_SAT,s.BUYER_COMLOAN_SAT,s.SALER_PHONE_OK,s.BUYER_PHONE_OK,
  CASE WHEN (s.SALER_PHONE_OK = '1' AND s.BUYER_PHONE_OK = '1') THEN '1' ELSE '0' END,NULL,s.BUYER_COMLOAN_COM,a.TEAM_ID,a.DISTRICT_ID,GETDATE(),'IMP',a.org_id 
  from sctrans.T_TS_AWARD_CASE_CENTAL as c 
  left join sctrans.T_CS_CASE_SATISFACTION as s on c.CASE_CODE = s.CASE_CODE
  left join sctrans.T_TS_AWARD_BASE as a on s.CASE_CODE = a.CASE_CODE
  where  a.is_deleted='0' and c.GUOHU_APPROVE_TIME <= @guohu_approve_time and s.STATUS = '7' and a.SRV_CODE = 'ComLoanProcess';

    COMMIT TRAN;

  END TRY

  BEGIN CATCH
  ROLLBACK TRAN;
     --SELECT ERROR_MESSAGE() AS ErrorMessage;  
    SELECT ERROR_NUMBER() AS ErrorNumber  
        ,ERROR_SEVERITY() AS ErrorSeverity  
        ,ERROR_STATE() AS ErrorState  
        ,ERROR_PROCEDURE() AS ErrorProcedure  
        ,ERROR_LINE() AS ErrorLine  
        ,ERROR_MESSAGE() AS ErrorMessage; 
  END CATCH;  

END
