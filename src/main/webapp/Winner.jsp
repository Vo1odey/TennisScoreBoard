<%@ page import="com.dragunov.tennisscoreboard.models.PlayerModel" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Theme.css" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Winner</title>
</head>
<h1 id="heading">Tennis Score</h1>
<br />
<% String winner; PlayerModel player1 = (PlayerModel)
        request.getAttribute("player1"); PlayerModel player2 = (PlayerModel)
        request.getAttribute("player2"); if (player1.getGameScore().getSet() == 2) {
  winner = player1.getName(); } else { winner = player2.getName(); } %>
<table id="Score">
  <tr>
    <th id="title"></th>
    <th id="title"><h2 id="headColor">Set</h2></th>
    <th id="title"><h2 id="headColor">Game</h2></th>
    <th id="title"><h2 id="headColor">Point</h2></th>
  </tr>
  <tr>
    <td id="pName"><h2>${player1.name}</h2></td>
    <td><h2><%= player1.getGameScore().getSet()%></h2></td>
    <td><h2><%= player1.getGameScore().getGame()%></h2></td>
    <td><h2><%= player1.getGameScore().getPoint().getValue()%></h2></td>
  </tr>
  <tr>
    <td id="pName"><h2>${player2.name}</h2></td>
    <td><h2><%= player2.getGameScore().getSet()%></h2></td>
    <td><h2><%= player2.getGameScore().getGame()%></h2></td>
    <td><h2><%= player2.getGameScore().getPoint().getValue()%></h2></td>
  </tr>
</table>
<br />
<table id="winner">
  <tr>
    <th><h1 id="colorWin">Is a Winner!</h1></th>
  </tr>
  <tr>
    <td><h2><%=winner%></h2></td>
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
