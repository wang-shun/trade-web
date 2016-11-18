if exists (select 1
            from  sysobjects
           where  id = object_id('sctrans.T_TS_TRANS_PLAN_HISTORY_BATCH')
            and   type = 'U')
   drop table sctrans.T_TS_TRANS_PLAN_HISTORY_BATCH
go

/*==============================================================*/
/* Table: T_TS_TRANS_PLAN_HISTORY_BATCH                         */
/*==============================================================*/
create table sctrans.T_TS_TRANS_PLAN_HISTORY_BATCH (
   PKID                 bigint      identity(1,1)         not null,
   CASE_CODE            varchar(32)          not null,
   PART_CODE            varchar(32)          null,
   OLD_EST_PART_TIME    datetime             null,
   NEW_EST_PART_TIME    datetime             null,
   LAST_VISIT_REMARK    char(1)              null,
   CHANGE_REASON        nvarchar(128)        null,
   LAST_CONTENT         varchar(255)         null,
   OPERATE_FLAG         char(1)              default 0,
   CREATE_TIME          datetime             null,
   CREATE_BY            varchar(32)          null,
   UPDATE_TIME          datetime             null,
   UPDATE_BY            varchar(32)          null,
   HISTORY_ID           bigint ,
   constraint PK_T_TS_TRANS_PLAN_HISTORY_BAT primary key (PKID)
)

ALTER TABLE [sctrans].[T_TS_RETURN_VISIT_REGISTRATION] DROP COLUMN PLAN_HISTORY_ID;
ALTER TABLE [sctrans].[T_TS_RETURN_VISIT_REGISTRATION] ADD  BATCH_ID bigint;

ALTER TABLE [sctrans].[T_TS_TRANS_PLAN_HISTORY] ADD  BATCH_ID bigint;

INSERT sctrans.T_TS_TRANS_PLAN_HISTORY_BATCH
        ( CASE_CODE ,
          PART_CODE ,
          OLD_EST_PART_TIME ,
          NEW_EST_PART_TIME ,
		  CHANGE_REASON,
          CREATE_TIME ,
          CREATE_BY ,
          UPDATE_TIME ,
          UPDATE_BY ,
          OPERATE_FLAG,
		  HISTORY_ID
        )
SELECT
	T.CASE_CODE,
	T.PART_CODE,
	T.OLD_EST_PART_TIME,
	T.NEW_EST_PART_TIME,
	T.CHANGE_REASON,
  GETDATE(),
  '0E7B291E94EB4CE8E0532429030AE021',
  GETDATE(),
  '0E7B291E94EB4CE8E0532429030AE021',
   '0',
   T.PKID
FROM
	[sctrans].[T_TS_TRANS_PLAN_HISTORY] T;

UPDATE [sctrans].[T_TS_TRANS_PLAN_HISTORY]  SET  BATCH_ID = (SELECT S.PKID from sctrans.T_TS_TRANS_PLAN_HISTORY_BATCH S where S.HISTORY_ID = [sctrans].[T_TS_TRANS_PLAN_HISTORY].PKID);

ALTER TABLE [sctrans].[T_TS_TRANS_PLAN_HISTORY_BATCH] DROP COLUMN HISTORY_ID;
UPDATE sctrans.T_TS_TASK_PLAN_SET SET PLAN_DAYS=2 where part_code='FirstFollow';
