SELECT ou.ID,ou.Name,ou.Code,pa.id parentid,ou.CategoryID,ou.CreateTime
,ou.EndTime,ou.OrderCode,ou.FullName,'',
da.HRMS_UserField_4 ccaidepid,da.HRMS_UserField_11 hroc
 FROM ORG_Unit ou
 left JOIN ORG_Unit pa ON pa.code = substring(ou.code, 1, (len(ou.code) - CHARINDEX('.', reverse(ou.code))))
 left JOIN ORG_Department d ON ou.ID = d.UnitID
 LEFT JOIN ORG_DepartmentAttch da ON d.UnitID = da.UnitID
 WHERE  ou.NAME NOT LIKE '%取消%' AND ou.NAME NOT LIKE '%删除%' AND ou.statusid = 1 and ou.FullName like '%天津%'
 ORDER BY ou.code ASC
