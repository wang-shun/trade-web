if exists (select 1
            from  sysobjects
           where  id = object_id('sctrans.T_TS_RETURN_VISIT_REGISTRATION')
            and   type = 'U')
   drop table sctrans.T_TS_RETURN_VISIT_REGISTRATION
go
create table sctrans.T_TS_RETURN_VISIT_REGISTRATION (
   PKID                 bigint       identity(1,1)     not null,
   PLAN_HISTORY_ID      bigint               null,
   VISIT_REMARK         char(1)              null,
   CONTENT              varchar(255)         null,
   CREATE_TIME          datetime             null,
   CREATE_BY            varchar(32)          null,
   constraint PK_T_TS_RETURN_VISIT_REGISTRAT primary key (PKID)
)