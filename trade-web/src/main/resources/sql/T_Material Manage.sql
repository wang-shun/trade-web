/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2012                    */
/* Created on:     2016/10/18 18:58:21                          */
/*==============================================================*/


if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('sctrans.T_MM_IO_BATCH') and o.name = 'FK_T_MM_IO__REFERENCE_T_TO_ATT')
alter table sctrans.T_MM_IO_BATCH
   drop constraint FK_T_MM_IO__REFERENCE_T_TO_ATT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('sctrans.T_MM_ITEM_BATCH') and o.name = 'FK_T_MM_ITE_REFERENCE_T_MM_MAT')
alter table sctrans.T_MM_ITEM_BATCH
   drop constraint FK_T_MM_ITE_REFERENCE_T_MM_MAT
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('sctrans.T_MM_ITEM_BATCH') and o.name = 'FK_T_MM_ITE_REFERENCE_T_MM_IO_')
alter table sctrans.T_MM_ITEM_BATCH
   drop constraint FK_T_MM_ITE_REFERENCE_T_MM_IO_
go

if exists (select 1
            from  sysobjects
           where  id = object_id('sctrans.T_MM_IO_BATCH')
            and   type = 'U')
   drop table sctrans.T_MM_IO_BATCH
go

if exists (select 1
            from  sysobjects
           where  id = object_id('sctrans.T_MM_ITEM_BATCH')
            and   type = 'U')
   drop table sctrans.T_MM_ITEM_BATCH
go

if exists (select 1
            from  sysobjects
           where  id = object_id('sctrans.T_MM_MATERIAL_ITEM')
            and   type = 'U')
   drop table sctrans.T_MM_MATERIAL_ITEM
go

/*==============================================================*/
/* Table: T_MM_IO_BATCH                                         */
/*==============================================================*/
create table sctrans.T_MM_IO_BATCH (
   PKID                 bigint               identity,
   ATTACH_ID            bigint               null,
   CASE_CODE            varchar(32)          null,
   LOG_ACTION           varchar(32)          null,
   MANAGER              varchar(32)          null,
   ACTION_USER          varchar(32)          null,
   ACTION_PRE_DATE      datetime             null,
   ACTION_REASON        nvarchar(256)        null,
   ACTION_REMARK        nvarchar(256)         null,
   CREATE_BY            varchar(32)          null,
   CREATE_TIME          datetime             null,
   UPDATE_BY            varchar(32)          null,
   UPDATE_TIME          datetime             null,
   constraint PK_T_MM_IO_BATCH primary key (PKID)
)
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_IO_BATCH')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ATTACH_ID')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'ATTACH_ID'

end


execute sp_addextendedproperty 'MS_Description', 
   '附件ID',
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'ATTACH_ID'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_IO_BATCH')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'CASE_CODE')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'CASE_CODE'

end


execute sp_addextendedproperty 'MS_Description', 
   '案件编号',
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'CASE_CODE'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_IO_BATCH')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'LOG_ACTION')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'LOG_ACTION'

end


execute sp_addextendedproperty 'MS_Description', 
   '动作:  <1>申请<2>借用 <3>归还 <4> 退还',
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'LOG_ACTION'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_IO_BATCH')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'MANAGER')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'MANAGER'

end


execute sp_addextendedproperty 'MS_Description', 
   '负责人',
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'MANAGER'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_IO_BATCH')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ACTION_USER')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'ACTION_USER'

end


execute sp_addextendedproperty 'MS_Description', 
   '相关人',
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'ACTION_USER'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_IO_BATCH')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ACTION_PRE_DATE')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'ACTION_PRE_DATE'

end


execute sp_addextendedproperty 'MS_Description', 
   '预计相关日期',
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'ACTION_PRE_DATE'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_IO_BATCH')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ACTION_REASON')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'ACTION_REASON'

end


execute sp_addextendedproperty 'MS_Description', 
   '理由',
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'ACTION_REASON'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_IO_BATCH')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ACTION_REMARK')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'ACTION_REMARK'

end


execute sp_addextendedproperty 'MS_Description', 
   '备注',
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'ACTION_REMARK'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_IO_BATCH')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'CREATE_BY')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'CREATE_BY'

end


execute sp_addextendedproperty 'MS_Description', 
   '创建人',
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'CREATE_BY'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_IO_BATCH')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'CREATE_TIME')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'CREATE_TIME'

end


execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'CREATE_TIME'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_IO_BATCH')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'UPDATE_BY')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'UPDATE_BY'

end


execute sp_addextendedproperty 'MS_Description', 
   '更新人',
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'UPDATE_BY'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_IO_BATCH')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'UPDATE_TIME')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'UPDATE_TIME'

end


execute sp_addextendedproperty 'MS_Description', 
   '更新时间',
   'schema', 'sctrans', 'table', 'T_MM_IO_BATCH', 'column', 'UPDATE_TIME'
go

/*==============================================================*/
/* Table: T_MM_ITEM_BATCH                                       */
/*==============================================================*/
create table sctrans.T_MM_ITEM_BATCH (
   PKID                 BIGINT               not null,
   BATCH_ID             BIGINT               null,
   ITEM_ID              BIGINT               null,
   constraint PK_T_MM_ITEM_BATCH primary key (PKID)
)
go

/*==============================================================*/
/* Table: T_MM_MATERIAL_ITEM                                    */
/*==============================================================*/
create table sctrans.T_MM_MATERIAL_ITEM (
   PKID                 bigint               not null,
   CASE_CODE            varchar(32)          null,
   ITEM_CODE            varchar(32)          null,
   ITEM_NAME            varchar(256)         null,
   ITEM_CATEGORY        VARCHAR(32)          null,
   ITEM_INPUT_TIME      datetime             null,
   ITEM_OUTPUT_TIME     datetime             null,
   ACTION_PRE_DATE      datetime             null,
   ITEM_BACK_TIME       datetime             null,
   ITEM_RESOURCE        varchar(32)          null,
   ITEM_MANAGER         varchar(32)          null,
   ITEM_ADDR_CODE       varchar(32)          null,
   ITEM_BUSINESS_REMARK nvarchar(256)        null,
   ITEM_REMARK          nvarchar(256)        null,
   ITEM_STATUS          varchar(32)          null,
   CREATE_BY            varchar(32)          null,
   CREATE_TIME          datetime             null,
   UPDATE_BY            varchar(32)          null,
   UPDATE_TIME          datetime             null,
   constraint PK_T_MM_MATERIAL_ITEM primary key (PKID),
   constraint AK_UQ_ITEM_CODE_T_MM_MAT unique (ITEM_CODE)
)
go

if exists (select 1 from  sys.extended_properties
           where major_id = object_id('sctrans.T_MM_MATERIAL_ITEM') and minor_id = 0)
begin 
   execute sp_dropextendedproperty 'MS_Description',  
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM' 
 
end 


execute sp_addextendedproperty 'MS_Description',  
   '客户物品
   Material Manage', 
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_MATERIAL_ITEM')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'CASE_CODE')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'CASE_CODE'

end


execute sp_addextendedproperty 'MS_Description', 
   '案件编号',
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'CASE_CODE'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_MATERIAL_ITEM')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ITEM_CODE')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_CODE'

end


execute sp_addextendedproperty 'MS_Description', 
   '物品编号',
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_CODE'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_MATERIAL_ITEM')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ITEM_NAME')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_NAME'

end


execute sp_addextendedproperty 'MS_Description', 
   '物品名称',
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_NAME'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_MATERIAL_ITEM')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ITEM_CATEGORY')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_CATEGORY'

end


execute sp_addextendedproperty 'MS_Description', 
   '物品类别 <1>身份证 <2>银行卡 <3>产权证 <4>抵押合同 <5>他证
   ',
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_CATEGORY'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_MATERIAL_ITEM')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ITEM_INPUT_TIME')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_INPUT_TIME'

end


execute sp_addextendedproperty 'MS_Description', 
   '入库时间',
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_INPUT_TIME'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_MATERIAL_ITEM')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ITEM_OUTPUT_TIME')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_OUTPUT_TIME'

end


execute sp_addextendedproperty 'MS_Description', 
   '借出时间',
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_OUTPUT_TIME'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_MATERIAL_ITEM')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ACTION_PRE_DATE')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ACTION_PRE_DATE'

end


execute sp_addextendedproperty 'MS_Description', 
   '预计相关日期',
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ACTION_PRE_DATE'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_MATERIAL_ITEM')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ITEM_BACK_TIME')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_BACK_TIME'

end


execute sp_addextendedproperty 'MS_Description', 
   '退还时间',
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_BACK_TIME'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_MATERIAL_ITEM')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ITEM_RESOURCE')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_RESOURCE'

end


execute sp_addextendedproperty 'MS_Description', 
   '来源: <1>风控',
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_RESOURCE'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_MATERIAL_ITEM')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ITEM_MANAGER')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_MANAGER'

end


execute sp_addextendedproperty 'MS_Description', 
   '物品保管员',
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_MANAGER'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_MATERIAL_ITEM')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ITEM_ADDR_CODE')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_ADDR_CODE'

end


execute sp_addextendedproperty 'MS_Description', 
   '物品位置编号',
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_ADDR_CODE'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_MATERIAL_ITEM')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ITEM_BUSINESS_REMARK')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_BUSINESS_REMARK'

end


execute sp_addextendedproperty 'MS_Description', 
   '物品业务描述',
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_BUSINESS_REMARK'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_MATERIAL_ITEM')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ITEM_REMARK')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_REMARK'

end


execute sp_addextendedproperty 'MS_Description', 
   '物品备注信息',
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_REMARK'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_MATERIAL_ITEM')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ITEM_STATUS')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_STATUS'

end


execute sp_addextendedproperty 'MS_Description', 
   '物品状态<1> 待入库<2> 在库<3>已借用<4>已退还',
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'ITEM_STATUS'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_MATERIAL_ITEM')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'CREATE_BY')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'CREATE_BY'

end


execute sp_addextendedproperty 'MS_Description', 
   '创建人',
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'CREATE_BY'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_MATERIAL_ITEM')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'CREATE_TIME')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'CREATE_TIME'

end


execute sp_addextendedproperty 'MS_Description', 
   '创建时间',
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'CREATE_TIME'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_MATERIAL_ITEM')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'UPDATE_BY')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'UPDATE_BY'

end


execute sp_addextendedproperty 'MS_Description', 
   '更新人',
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'UPDATE_BY'
go

if exists(select 1 from sys.extended_properties p where
      p.major_id = object_id('sctrans.T_MM_MATERIAL_ITEM')
  and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'UPDATE_TIME')
)
begin
   execute sp_dropextendedproperty 'MS_Description', 
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'UPDATE_TIME'

end


execute sp_addextendedproperty 'MS_Description', 
   '更新时间',
   'schema', 'sctrans', 'table', 'T_MM_MATERIAL_ITEM', 'column', 'UPDATE_TIME'
go

alter table sctrans.T_MM_IO_BATCH
   add constraint FK_T_MM_IO__REFERENCE_T_TO_ATT foreign key (ATTACH_ID)
      references sctrans.T_TO_ATTACHMENT (PKID)
go

alter table sctrans.T_MM_ITEM_BATCH
   add constraint FK_T_MM_ITE_REFERENCE_T_MM_MAT foreign key (ITEM_ID)
      references sctrans.T_MM_MATERIAL_ITEM (PKID)
go

alter table sctrans.T_MM_ITEM_BATCH
   add constraint FK_T_MM_ITE_REFERENCE_T_MM_IO_ foreign key (BATCH_ID)
      references sctrans.T_MM_IO_BATCH (PKID)
go

