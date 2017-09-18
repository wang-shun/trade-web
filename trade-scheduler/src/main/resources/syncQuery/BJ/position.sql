SELECT dept.id dept_id,dept.code dept_code,dept.fullName dept_fullname,
p.id position_id,p.NAME position_name
 FROM ORG_Position p
 INNER JOIN ORG_Unit dept ON dept.id = p.unitid
 WHERE (p.isdelete = 0	AND dept.NAME NOT LIKE '%取消%' AND dept.NAME NOT LIKE '%删除%' AND dept.statusid = 1)
