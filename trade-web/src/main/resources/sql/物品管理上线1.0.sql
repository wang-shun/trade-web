1.执行T_Material_Manage.sql
2.执行T_RISK_CONTROL.sql
3.同步SYS_SEQ_DEF的ITEM_CODE字段
4.删除抵押合同字典选项 : DELETE FROM scpf_dev.dbo.SYS_DICT WHERE ID = 'ff80808156ff6246015703c9d0d90035'
5.同步附件环节 : 
   T_TO_ACCESSORY_LIST
   RiskControl_Card
   RiskControl_Mortgage