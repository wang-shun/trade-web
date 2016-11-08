if exists (select 1
            from  sysobjects
           where  id = object_id('sctrans.T_RM_TRADECENTER_SCHEDULE')
            and   type = 'U')
   drop table sctrans.T_RM_TRADECENTER_SCHEDULE
go

/*==============================================================*/
/* Table: T_RM_TRADECENTER_SCHEDULE                             */
/*==============================================================*/
create table sctrans.T_RM_TRADECENTER_SCHEDULE (
   PKID                 bigint        identity(1,1)       not null,
   DUTY_OFFICER         varchar(32)          not null,
   DUTY_DATE            varchar(10)          not null,
   TRADE_CENTER_ID      bigint               not null,
   DUTY_TYPE            char(1)              not null,
   CREATE_TIME          datetime             null,
   CREATE_BY            varchar(32)          null,
   UPDATE_TIME          datetime             null,
   UPDATE_BY            varchar(32)          null,
   constraint PK_T_RM_TRADECENTER_SCHEDULE primary key (PKID)
)