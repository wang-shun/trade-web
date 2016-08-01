
select t.CODE,t.CUR_COUNT,t.CUR_SEQ from (
 select d.CODE,v.CUR_COUNT,v.CUR_SEQ,ROW_NUMBER() over(partition by v.SYS_SEQ_DEF_ID ORDER BY v.id desc) rn
	from SYS_SEQ_DEF d
	inner join SYS_SEQ_VAL v on d.ID = v.SYS_SEQ_DEF_ID
	where d.CODE in ('CJBH_CODE','ZYDK_CODE','WDDK_CODE','CHANDIAO_CODE')) t where t.rn = 1;