<%@ page import="com.dragunov.tennisscoreboard.models.PlayerModel" %>
<%@ page import="com.dragunov.tennisscoreboard.dto.GameScore" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="Theme.css" />
    <title>my page hey</title>
  </head>
  <body>
    <div class="container">
      <h1 id="heading">Tennis Score</h1>
      <br />
      <div id="Score">
        <table>
          <tr>
            <th></th>
            <th><h2>Set</h2></th>
            <th><h2>Game</h2></th>
            <th><h2>Point</h2></th>
          </tr>
          <tr>
            <td><h2>${player1.name}</h2></td>
            <%
              PlayerModel player1 = (PlayerModel) request.getAttribute("player1");
              GameScore p1Score = player1.getGameScore();
            %>
            <td><h2><%= p1Score.getSet()%></h2></td>
            <td><h2><%= p1Score.getGame()%></h2></td>
            <td><h2><%= p1Score.getPoint().getValue()%></h2></td>
          </tr>
          <tr>
            <td><h2>${player2.name}</h2></td>
            <%
              PlayerModel player2 = (PlayerModel) request.getAttribute("player2");
              GameScore p2Score = player2.getGameScore();
            %>
            <td><h2><%= p2Score.getSet()%></h2></td>
            <td><h2><%= p2Score.getGame()%></h2></td>
            <td><h2><%= p2Score.getPoint().getValue()%></h2></td>
          </tr>
        </table>
      </div>
      <br />
      <%
        String uuid = request.getAttribute("uuid").toString();
      %>

      <form action="match-score?uuid=<%= uuid %>" method="post">
        <input type="submit" id="p1form" name="<%=player1.getId()%>" value="<%=player1.getName()%> win" />
        <input type="hidden" name="player_id" value="<%=player1.getId()%>" />
      </form>
      <form action="match-score?uuid=<%= uuid %>" method="post">
        <input type="submit" id="p2form" name="<%=player2.getId()%>" value="<%=player2.getName()%> win" />
        <input type="hidden" name="player_id" value="<%=player2.getId()%>" />
      </form>
    </div>
  </body>
</html>
