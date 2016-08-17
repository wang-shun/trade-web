
select t.CODE,t.CUR_COUNT,t.CUR_SEQ from (
 select d.CODE,v.CUR_COUNT,v.CUR_SEQ,ROW_NUMBER() over(partition by v.SYS_SEQ_DEF_ID ORDER BY v.id desc) rn
	from SYS_SEQ_DEF d
	inner join SYS_SEQ_VAL v on d.ID = v.SYS_SEQ_DEF_ID
	where d.CODE in ('CJBH_CODE','ZYDK_CODE','WDDK_CODE','CHANDIAO_CODE')) t where t.rn = 1;

/**修改zookeeper 定义 **/
update scpf_dev.dbo.SYS_SEQ_DEF set SEQ_PATTERN = 'ZY-{0}-{1}-{2,number,0000}',ROLL_KEY_DEF = 'ZY-{0}-{1}' where code='ZYDK_CODE';
update scpf_dev.dbo.SYS_SEQ_DEF set SEQ_PATTERN = 'WD-{0}-{1}-3{2,number,000}',ROLL_KEY_DEF = 'WD-{0}-{1}' where code='WDDK_CODE';
update scpf_dev.dbo.SYS_SEQ_DEF set SEQ_PATTERN = 'SHYC-{0}-{1}-{2,number,0000}',ROLL_KEY_DEF = 'SHYC-{0}-{1}' where code='CJBH_CODE';
update scpf_dev.dbo.SYS_SEQ_DEF set SEQ_PATTERN = 'ZY-JR-{0}-{1,number,0000}',ROLL_KEY_DEF = 'ZY-JR-{0}' where code='ELOAN_CODE';
update scpf_dev.dbo.SYS_SEQ_DEF set SEQ_PATTERN = 'CD{0}{1,number,0000}',ROLL_KEY_DEF = 'CD{0}' where code='CHANDIAO_CODE';

/** 查询需增加zooKeeper节点 **/
select * from SYS_SEQ_VAL 
where SYS_SEQ_DEF_ID = ( select id from SYS_SEQ_DEF where code  ='CJBH_CODE') order by CREATE_DATE  desc;

select * from SYS_SEQ_VAL 
where SYS_SEQ_DEF_ID = ( select id from SYS_SEQ_DEF where code  ='ZYDK_CODE') order by CREATE_DATE  desc;

select * from SYS_SEQ_VAL 
where SYS_SEQ_DEF_ID = ( select id from SYS_SEQ_DEF where code  ='CHANDIAO_CODE') order by CREATE_DATE  desc;

select * from SYS_SEQ_VAL 
where SYS_SEQ_DEF_ID = ( select id from SYS_SEQ_DEF where code  ='WDDK_CODE') order by CREATE_DATE  desc;

select * from SYS_SEQ_VAL 
where SYS_SEQ_DEF_ID = ( select id from SYS_SEQ_DEF where code  ='ELOAN_CODE') order by CREATE_DATE  desc;