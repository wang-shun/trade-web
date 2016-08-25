
/**修改zookeeper 定义 **/
update dbo.SYS_SEQ_DEF set SEQ_PATTERN = 'ZY-{0}-{1}-{2,number,0000}',ROLL_KEY_DEF = 'ZY-{0}-{1}' where code='ZYDK_CODE';
update dbo.SYS_SEQ_DEF set SEQ_PATTERN = 'WD-{0}-{1}-3{2,number,000}',ROLL_KEY_DEF = 'WD-{0}-{1}' where code='WDDK_CODE';
update dbo.SYS_SEQ_DEF set SEQ_PATTERN = 'SHYC-{0}-{1}-{2,number,0000}',ROLL_KEY_DEF = 'SHYC-{0}-{1}' where code='CJBH_CODE';
update dbo.SYS_SEQ_DEF set SEQ_PATTERN = 'ZY-JR-{0}-{1,number,0000}',ROLL_KEY_DEF = 'ZY-JR-{0}' where code='ELOAN_CODE';
update dbo.SYS_SEQ_DEF set SEQ_PATTERN = 'CD{0}{1,number,0000}',ROLL_KEY_DEF = 'CD{0}' where code='CHANDIAO_CODE';

