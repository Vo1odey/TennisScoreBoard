<%@ page import="com.dragunov.tennisscoreboard.models.PlayerModel" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <link rel="stylesheet" href="Theme.css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Score</title>
  </head>
  <body>
    <div class="container">
      <h1 id="heading">Tennis Score</h1>
      <br />
      <%
        PlayerModel player1 = (PlayerModel) request.getAttribute("player1");
        PlayerModel player2 = (PlayerModel) request.getAttribute("player2");
        String uuid = request.getAttribute("uuid").toString();
      %>
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
      <% %>
      <form action="match-score?uuid=<%= uuid %>" method="post">
        <input
          type="submit"
          id="p1form"
          name="<%=player1.getId()%>"
          value="<%=player1.getName()%> win"
        />
        <input type="hidden" name="player_id" value="<%=player1.getId()%>" />
      </form>
      <form action="match-score?uuid=<%= uuid %>" method="post">
        <input
          type="submit"
          id="p2form"
          name="<%=player2.getId()%>"
          value="<%=player2.getName()%> win"
        />
        <input type="hidden" name="player_id" value="<%=player2.getId()%>" />
      </form>
    </div>
  </body>
</html>
