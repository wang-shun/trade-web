SELECT ou.ID,ou.Name,ou.Code,pa.id parentid,ou.CategoryID,ou.CreateTime
,ou.EndTime,ou.OrderCode,ou.FullName,substring(da.HRMS_UserField_11, 1, 8) tel,
da.HRMS_UserField_4 ccaidepid,da.HRMS_UserField_14 hroc
 FROM ORG_Unit ou
 INNER JOIN ORG_Unit pa ON pa.code = substring(ou.code, 1, (len(ou.code) - CHARINDEX('.', reverse(ou.code))))
 INNER JOIN ORG_Department d ON ou.ID = d.UnitID
 LEFT JOIN ORG_DepartmentAttch da ON d.UnitID = da.UnitID
 WHERE ou.NAME NOT LIKE '%取消%' AND ou.NAME NOT LIKE '%删除%'  AND ou.statusid = 1
 ORDER BY ou.code ASC 