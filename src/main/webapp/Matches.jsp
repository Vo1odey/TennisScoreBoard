<%@ page import="com.dragunov.tennisscoreboard.models.MatchModel" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <meta charset="UTF-8" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/StyleMatches.css" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Matches</title>
</head>
<h1 id="heading">Tennis Score</h1>
<div class="form-container">
  <form id="newMatch" action="${pageContext.request.contextPath}/new-match" method="get">
    <input type="submit" id="back" value="new match" />
  </form>
  <form id="search" action="" method="get">
    <table>
      <tr>
        <th><h2 id="player">player name:</h2></th>
        <input type="hidden" name="page" value="1">
        <th><input id="text" type="text" name="filter_by_player_name" /></th>
        <th><input id="tap" type="submit" value="search" /></th>
      </tr>
    </table>
  </form>
</div>
<br />
<table id="ListMatches">
  <tr>
    <th class="tableHeader"><h2>Player 1</h2></th>
    <th class="tableHeader"><h2>Player 2</h2></th>
    <th class="tableHeader"><h2>Winner</h2></th>
  </tr>
  <c:forEach var="MatchModel" items="${matchPage}">
    <tr>
      <td class="tableData"><h3>${MatchModel.player1.name}</h3></td>
      <td class="tableData"><h3>${MatchModel.player2.name}</h3></td>
      <td class="tableData"><h3>${MatchModel.winner.name}</h3></td>
    </tr>
  </c:forEach>
</table>
<br />
<div class="form-button">
  <c:forEach var="i" begin="1" end="${requestScope.quantityOfPages}" step="1">
    <form class="pagination" action="" method="get">
      <input type="hidden" name="page" value="${i}">
      <input type="hidden" name="filter_by_player_name" value="${filter}">
      <input type="submit" id="matches" value="${i}">
    </form>
  </c:forEach>
</div>
</html>
