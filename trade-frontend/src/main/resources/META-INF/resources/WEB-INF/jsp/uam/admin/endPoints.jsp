<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Endpoint list</title></head>
<body>
<table>
  <thead>
  <tr>
    <th>path</th>
    <th>methods</th>
    <th>consumes</th>
    <th>produces</th>
    <th>params</th>
    <th>headers</th>
    <th>custom</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${endPoints}" var="endPoint">
    <tr>
      <td><a href="${ctx}" target="blank">${endPoint.patternsCondition}</a></td>
      <td>${endPoint.methodsCondition}</td>
      <td>${endPoint.consumesCondition}</td>
      <td>${endPoint.producesCondition}</td>
      <td>${endPoint.paramsCondition}</td>
      <td>${endPoint.headersCondition}</td>
      <td>${empty endPoint.customCondition ? "none" : endPoint.customCondition}</td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>