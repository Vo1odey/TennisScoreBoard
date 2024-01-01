<%@ page import="java.util.List" %>
<%@ page import="com.dragunov.tennisscoreboard.models.MatchModel" %>
<!DOCTYPE html>
<html lang="en">
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
<%
  int numberOfPages = (int) request.getAttribute("numberOfPages");
  String filter = request.getAttribute("filter").toString();
  List<MatchModel> FinishedMatchPage = (List<MatchModel>) request.getAttribute("matchPage");
%>
<table id="ListMatches">
  <tr>
    <th class="tableHeader"><h2>Player 1</h2></th>
    <th class="tableHeader"><h2>Player 2</h2></th>
    <th class="tableHeader"><h2>Winner</h2></th>
  </tr>

    <%
      for (MatchModel match: FinishedMatchPage) {
    %>
    <%=
    "<tr>"+
      "<td class=\"tableData\"><h3>" + match.getPlayer1().getName() + "</h3></td>" +
      "<td class=\"tableData\"><h3>" + match.getPlayer2().getName() + "</h3></td>" +
      "<td class=\"tableData\"><h3>" + match.getWinner().getName() + "</h3></td>" +
    "</tr>"
    %>
    <%
      }
    %>
</table>
<br />
<div class="form-button">
  <%
    for (int i = 1; i <= numberOfPages; i++) {
  %>
  <%=
  "<form class=\"pagination\" action=\"\" method=\"get\">" +
  "<input type=\"hidden\" name=\"page\" value=\"" + i + "\">\n" +
  "<input type=\"hidden\" name=\"filter_by_player_name\" value=\""+ filter +"\">\n" +
  "<input type=\"submit\" id=\"matches\" value=\"" + i + "\" />\n" +
  "</form>"
  %>
  <%
    }
  %>
</div>
</html>
