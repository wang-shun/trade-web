SELECT emp.[EM_ID],emp.[Name],emp.[Sex],emp.[Birthday],emp.[EMail],
substring(email, 1, charindex('@', email) - 1) account,emp.[Code],
substring(emp.[Mobile], 1, 11) mobile,ou.ID AS deptID,ou.code AS deptCode,
ou.fullname AS deptName,p.ID AS PositionID,p.NAME AS positionName
 FROM [dbo].[HM_Employees] emp
 INNER JOIN AIS20061025163636.dbo.ORG_Position_Employee pe ON emp.em_ID = pe.emid
 INNER JOIN AIS20061025163636.dbo.ORG_Position p ON pe.positionID = p.ID
 INNER JOIN AIS20061025163636.dbo.ORG_Unit ou ON p.UnitID = ou.ID
 WHERE emp.STATUS = 1AND pe.IsPrimary = 1 ORDER BY emp.code DESC
