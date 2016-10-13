USE [sctrans_prd]
GO
/****** Object:  StoredProcedure [sctrans].[P_SYN_NEW_CASE]    Script Date: 2016/10/13 16:41:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [sctrans].[P_SYN_NEW_CASE]
	@ctm_code NVARCHAR(100) = '',@period integer = 2
AS
BEGIN
	DECLARE @cnt INT;
	DECLARE @seq_start integer;
	DECLARE @seq_sql NVARCHAR(200);
	DECLARE @syn_date datetime;
	DECLARE @start_syn_date datetime;
	DECLARE @case_count integer = 0 ;

	set @syn_date = getdate();

	select @start_syn_date = max(start_time) from [sctrans].[T_TL_SYN_CASE_LOG] where [case_count] > 0 and is_manual = 0;

	if (@start_syn_date is null or @period != 2)
	begin
		set @start_syn_date = dateadd(HOUR,-@period,@syn_date);
	end
	--导入日志 [sctrans].[V_TradeCaseInfo]
	if @ctm_code is null or @ctm_code = ''
		begin
			INSERT INTO [sctrans].[T_TL_CTM_CASEINFO_LOG]
				   ([Id],[RootID],[ctm_code],[agent_username],[agent_realname]
				   ,[agent_mobile],[grp_code],[grp_name],[grp_mgr_username],[grp_mgr_realname]
				   ,[grp_mgr_mobile],[ar_code],[ar_name],[ar_mgr_username],[ar_mgr_realname]
				   ,[ar_mgr_mobile],[wz_direct_username],[wz_direct_realname],[property_address],[property_id]
				   ,[del_code],[consult_username],[consult_realname],[create_time],[update_time]
				   ,[syn_date])
			 select [Id],[RootID],[ctm_code],[agent_username],[agent_realname]
				   ,[agent_mobile],[grp_code],[grp_name],[grp_mgr_username],[grp_mgr_realname]
				   ,[grp_mgr_mobile],[ar_code],[ar_name],[ar_mgr_username],[ar_mgr_realname]
				   ,[ar_mgr_mobile],[wz_direct_username],[wz_direct_realname],[property_address],[property_id]
				   ,[del_code],[consult_username],[consult_realname],[create_time],[update_time]
				   ,@syn_date
			from [sctrans].[V_TradeCaseInfo] tci 
			where not exists (select 1 from  [sctrans].[T_TL_CTM_CASEINFO_LOG] log where log.ctm_code = tci.[ctm_code] )
				and [update_time] >= @start_syn_date
				and [property_address] is not null
				and tci.exchangetype = '买卖';
		end 
	else 
		begin
			INSERT INTO [sctrans].[T_TL_CTM_CASEINFO_LOG]
					([Id],[RootID],[ctm_code],[agent_username],[agent_realname]
					,[agent_mobile],[grp_code],[grp_name],[grp_mgr_username],[grp_mgr_realname]
					,[grp_mgr_mobile],[ar_code],[ar_name],[ar_mgr_username],[ar_mgr_realname]
					,[ar_mgr_mobile],[wz_direct_username],[wz_direct_realname],[property_address],[property_id]
					,[del_code],[consult_username],[consult_realname],[create_time],[update_time]
					,[syn_date])
				select [Id],[RootID],[ctm_code],[agent_username],[agent_realname]
					,[agent_mobile],[grp_code],[grp_name],[grp_mgr_username],[grp_mgr_realname]
					,[grp_mgr_mobile],[ar_code],[ar_name],[ar_mgr_username],[ar_mgr_realname]
					,[ar_mgr_mobile],[wz_direct_username],[wz_direct_realname],[property_address],[property_id]
					,[del_code],[consult_username],[consult_realname],[create_time],[update_time]
					,@syn_date
			from [sctrans].[V_TradeCaseInfo] tci 
			where not exists (select 1 from  [sctrans].[T_TL_CTM_CASEINFO_LOG] log where log.ctm_code = tci.[ctm_code] )
				and ctm_code = @ctm_code
				and [property_address] is not null
				and tci.exchangetype = '买卖';
		end 


	--导入日志 [sctrans].[V_TradeClientInfo]
	INSERT INTO [sctrans].[T_TL_CTM_CLIENTINFO_LOG]
           ([Id],[RootID],[ctm_code],[cust_code],[cust_position]
           ,[name],[mobile],[cert_type],[cert_code] ,[syn_date])
	select [Id],[RootID],[ctm_code],[cust_code],[cust_position]
           ,[name],[mobile],[cert_type],[cert_code],@syn_date
		from [sctrans].[V_TradeClientInfo]  tci
		where  not exists (select 1 from  [sctrans].[T_TL_CTM_CLIENTINFO_LOG] log where log.ctm_code = tci.[ctm_code] )
			and exists (select 1 from [sctrans].[V_TradeCaseInfo] ci where ci.ctm_code = tci.[ctm_code] );

	SELECT @cnt = count(1) FROM sys.sequences where name = 'seq_case_code';
	if @cnt > 0 
	begin
		drop SEQUENCE [sctrans].[seq_case_code];
	end 

	select @seq_start=(max(right(CASE_CODE,4)) + 1) from sctrans.T_TO_CASE_INFO WITH (NOLOCK) where CHARINDEX(CONVERT(char(6), @syn_date, 112),CASE_CODE) > 0;

	if @seq_start is null
	begin 
		set @seq_start=1
	end 

	SET @seq_sql = 'CREATE SEQUENCE [sctrans].[seq_case_code] START WITH ' + CONVERT(varchar(6),@seq_start) + ' INCREMENT BY 1';

	exec(@seq_sql)

	BEGIN TRY
	BEGIN TRANSACTION;

	
	
	-- 导入[T_TO_CASE_INFO]
	INSERT INTO [sctrans].[T_TO_CASE_INFO]
           ([CTM_CODE],[AGENT_CODE],[CASE_CODE],[REQUIRE_PROCESSOR_ID]
           ,[IS_RESPONSED],[RES_DATE],[AGENT_NAME],[AGENT_USERNAME]
           ,[AGENT_PHONE],[IMPORT_TIME] ,[DISPATCH_TIME],[GRP_CODE]
           ,[GRP_NAME],[AR_CODE],[AR_NAME],[TARGET_CODE]
		   ,QYJL_NAME,SWZ_CODE,SWZ_NAME,QYZJ_NAME
		   ,WZ_CODE,WZ_NAME,QJDS_NAME, BA_CODE,BA_NAME
           ,[UPDATE_BY],[UPDATE_TIME],[CREATE_TIME],CREATE_BY)
	select [CTM_CODE],u.ID,'ZY-SH-'+ CONVERT(char(6), getdate(), 112) + '-' + right('0000'+ CONVERT(varchar(4),NEXT VALUE FOR [sctrans].[seq_case_code]),4),
				(select top 1 l.user_id from sctrans.SYS_USER_ORG_JOB uoj1  
				inner join sctrans.SYS_ORG o on o.id = uoj1.ORG_ID
				inner join sctrans.T_TS_TEAM_SCOPE_TARGET tst on tst.GRP_CODE = o.ORG_CODE
				inner join sctrans.v_user_job_org_leader l on l.ORG_CODE = tst.YU_TEAM_CODE
				where uoj1.USER_ID = u.id ),
		'0',null,u.REAL_NAME,log.agent_username,
		 u.MOBILE ,@syn_date,@syn_date,grp_code,
		 grp_name,soh.BUSIAR_CODE,soh.BUSIAR_NAME,iif( 
									(select count(1) from sctrans.SYS_ORG where ORG_CODE = grp_code) > 0,
									grp_code,
									(select top 1 GROUP_CODE from sctrans.v_user_org_job uoj , sctrans.v_sys_org_hierarchy soh 
										where uoj.org_id = soh.org_id AND uoj.username = agent_username ) 
									) ,
		 soh.JQYJL_MGR,BUSISWZ_CODE,BUSISWZ_NAME,JQYZJ_MGR,
		 soh.BUSIWZ_CODE,soh.BUSIWZ_NAME,soh.JQYDS_MGR, soh.BUSIBA_CODE,soh.BUSIBA_NAME,
		 'ctm_proc',@syn_date,@syn_date,'ctm_proc'
	from [sctrans].[T_TL_CTM_CASEINFO_LOG] log 
		--left join (select distinct org_code from sctrans.v_sys_org_hierarchy) org on org.org_code = log.grp_code 
		--查询出ar_code,ar_name
		inner join (select * from (select *,ROW_NUMBER() over (partition by username order by IS_DELETED asc, version desc ) rn  from sctrans.SYS_USER where username != '' and username is not null ) t where rn =1 ) u on  u.USERNAME = log.agent_username
		left join [sctrans].[T_SYS_ORG_HIERARCHY] soh on soh.GROUP_ID = u.ORG_ID
		--left join sctrans.SYS_ORG so on so.ID = soh.BUSIAR_ID
	where not exists ( select 1 
		from [sctrans].[T_TO_CASE_INFO] ctd
		where ctd.CTM_CODE = log.ctm_code
	) and log.syn_date > @start_syn_date;

	set @case_count = @@rowcount

	-- 导入[T_TO_CASE]
	INSERT INTO [sctrans].[T_TO_CASE]
           ([CASE_CODE],[CTM_CODE],[CREATE_TIME],[STATUS]
           ,[CASE_PROPERTY],[LEADING_PROCESS_ID],[ORG_ID],[ClOSE_TIME]
           ,[CREATE_BY],[UPDATE_TIME],[UPDATE_BY],[LOAN_REQ])
	select [CASE_CODE],[CTM_CODE],@syn_date,'30001001'
		,'30003003',tci.REQUIRE_PROCESSOR_ID,null,null
		,'ctm_proc',@syn_date,'ctm_proc',null
	 from [sctrans].[T_TO_CASE_INFO] tci
	 where [IMPORT_TIME] > @start_syn_date
	  and not exists (select 1 from [sctrans].[T_TO_CASE] tc where tc.CTM_CODE = tci.CTM_CODE);

	 -- 导入[T_TG_GUEST_INFO]
	INSERT INTO [sctrans].[T_TG_GUEST_INFO]
           ([GUEST_CODE] ,[CASE_CODE] ,[GUEST_NAME],[GUEST_PHONE]
           ,[IDENTIFY_TYPE],[IDENTIFY_NUMBER],[WORK_UNIT],[TRANS_POSITION]
           ,[CTM_CODE], 
		   CREATE_TIME,CREATE_BY,UPDATE_BY,UPDATE_TIME)
     select cust_code,td.CASE_CODE,log.name,log.mobile
		,iif(log.cert_type= '' , null,log.cert_type),iif(log.cert_code='',null,log.cert_code),null,log.cust_position
		,log.ctm_code
		,@syn_date,'ctm_proc','ctm_proc',@syn_date
	 from sctrans.T_TL_CTM_CLIENTINFO_LOG log
		inner join [sctrans].[T_TO_CASE_INFO] td on log.ctm_code = td.CTM_CODE
	where 
		--log.syn_date > @start_syn_date and 
		not exists (select 1 from [sctrans].[T_TG_GUEST_INFO] gi where gi.CTM_CODE = log.ctm_code );

		 -- 导入T_TO_PROPERTY_INF
	INSERT INTO [sctrans].[T_TO_PROPERTY_INFO]
           ([PROPERTY_CODE] ,[PROPERTY_AGENT_ID],[PROPERTY_ADDR],[CASE_CODE]
           ,[TOTAL_FLOOR],[PROPERTY_TYPE],[LOCATE_FLOOR],[SQUARE]

           ,[FINISH_YEAR],[COMMENT],[CTM_CODE],[DIST_CODE]
           ,[CTM_ADDR],[CREATE_TIME],[CREATE_BY],[UPDATE_TIME]
           ,[UPDATE_BY])
     select log.property_id,log.del_code,log.property_address,td.CASE_CODE
		,hb.TOTAL_FLOOR,null,hb.FLOOR,hb.BUILD_SIZE

		,hb.BUILD_END_YEAR,null,log.ctm_code,hb.DISTRICT_CODE
		,log.property_address,@syn_date,'ctm_proc',@syn_date
		,'ctm_proc'
	from [sctrans].T_TL_CTM_CASEINFO_LOG log
	inner join [sctrans].T_TO_CASE_INFO td on td.CTM_CODE = log.ctm_code
	left join sctrans.vi_HM_HouseDelBase hb on hb.HOUDEL_CODE = log.del_code
	where log.syn_date > @start_syn_date
	 and not exists(select 1 from [sctrans].[T_TO_PROPERTY_INFO] tpi where tpi.ctm_code = log.ctm_code )

	COMMIT TRANSACTION;
	END TRY 
	BEGIN CATCH
		ROLLBACK TRANSACTION;
		THROW;
	END CATCH

	if @ctm_code is null or @ctm_code = ''
		begin		
			insert into [sctrans].[T_TL_SYN_CASE_LOG] ([start_time],[end_time],[case_count],[is_manual]) 
			values (@syn_date,getdate(),@case_count,0);
		end 
	else 
		begin
			exec sctrans.P_SYN_SINGLE_CASE @ctm_code = @ctm_code,@syn_date = @syn_date
			insert into [sctrans].[T_TL_SYN_CASE_LOG] ([start_time],[end_time],[case_count],[is_manual]) 
			values (@syn_date,getdate(),1,1);
		end 
END


