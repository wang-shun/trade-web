SELECT emp.em_ID AS empID,emp.NAME AS empName,ou.ID AS deptID,
ou.fullname AS deptName,p.ID AS PositionID,p.NAME AS positionName,pe.IsPrimary,p.IsPrincipal isleader
 FROM HM_Employees emp
 INNER JOIN ORG_Position_Employee pe ON emp.em_ID = pe.emid
 INNER JOIN ORG_Position p ON pe.positionID = p.ID
 INNER JOIN ORG_Unit ou ON p.UnitID = ou.ID
 WHERE ou.statusID = 1 AND emp.STATUS = 1 AND p.isdelete = 0
 ORDER BY emp.em_id
