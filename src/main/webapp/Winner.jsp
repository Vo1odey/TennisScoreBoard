<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <meta charset="UTF-8" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Theme.css" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Winner</title>
</head>
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
    <td class="pName"><h2>${player1.name}</h2></td>
    <td><h2>${player1.gameScore.set}</h2></td>
    <td><h2>${player1.gameScore.game}</h2></td>
    <td><h2>${player1.gameScore.point.getValue()}</h2></td>
  </tr>
  <tr>
    <td class="pName"><h2>${player2.name}</h2></td>
    <td><h2>${player2.gameScore.set}</h2></td>
    <td><h2>${player2.gameScore.game}</h2></td>
    <td><h2>${player2.gameScore.point.getValue()}</h2></td>
  </tr>
</table>
<br />
<table id="winner">
  <tr>
    <th><h1 id="colorWin">Is a Winner!</h1></th>
  </tr>
  <tr>
    <td><h2>${winner}</h2></td>
  </tr>
</table>
<br />
<form id="formLeft" action="new-match" method="get">
  <input type="submit" id="newgame" value="New Match" />
</form>
<form id="formRight" action="matches/" method="get">
  <input type="hidden" name="page" value="1">
  <input type="hidden" name="filter_by_player_name" value="">
  <input type="submit" id="matches" value="Matches" />
</form>
</html>
