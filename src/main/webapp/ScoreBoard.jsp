<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta charset="UTF-8" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Theme.css" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Score</title>
</head>
<body>
<h1 id="heading">Tennis Score</h1>
<br />
<table id="Score">
  <tr>
    <th></th>
    <th><h2 class="headColor">Set</h2></th>
    <th><h2 class="headColor">Game</h2></th>
    <th><h2 class="headColor">Point</h2></th>
  </tr>
  <tr>
    <td id="pName"><h2>${player1.name}</h2></td>
    <td><h2>${player1.gameScore.set}</h2></td>
    <td><h2>${player1.gameScore.game}</h2></td>
    <c:if test="${player1.gameScore.tieBreakPoint > 0}">
      <td><h2>${player1.gameScore.tieBreakPoint}</h2></td>
    </c:if>
    <c:if test="${player1.gameScore.tieBreakPoint == 0}">
      <td><h2>${player1.gameScore.point.getValue()}</h2></td>
    </c:if>
  </tr>
  <tr>
    <td id="pName"><h2>${player2.name}</h2></td>
    <td><h2>${player2.gameScore.set}</h2></td>
    <td><h2>${player2.gameScore.game}</h2></td>
    <c:if test="${player2.gameScore.tieBreakPoint > 0}">
      <td><h2>${player2.gameScore.tieBreakPoint}</h2></td>
    </c:if>
    <c:if test="${player2.gameScore.tieBreakPoint == 0}">
      <td><h2>${player2.gameScore.point.getValue()}</h2></td>
    </c:if>
  </tr>
</table>
<br />
<form action="match-score?uuid=${uuid}" method="post">
  <input
          type="submit"
          id="p1form"
          name="${player1.id}"
          value="${player1.name} win"
  />
  <input type="hidden" name="player_id" value="${player1.id}" />
</form>
<form action="match-score?uuid=${uuid}" method="post">
  <input
          type="submit"
          id="p2form"
          name="${player2.id}"
          value="${player2.name} win"
  />
  <input type="hidden" name="player_id" value="${player2.id}" />
</form>
</body>
</html>
