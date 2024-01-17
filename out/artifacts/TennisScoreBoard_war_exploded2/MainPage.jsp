<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <meta charset="UTF-8" />
    <link
      href="https://fonts.googleapis.com/css2?family=Roboto&display=swap"
      rel="stylesheet"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/StyleMainPage.css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Main Page</title>
  </head>
  <body>
    <div id="heading">
      <h1 id="title">Tennis Score</h1>
    </div>
    <br />
    <div id="containerNewMatch">
      <form id="newMatchForm" action="new-match" method="get">
        <input type="submit" id="buttonNewMatch" value="new match" />
      </form>
      <form id="matchesForm" action="matches/" method="get">
        <input type="hidden" name="page" value="1" />
        <input type="hidden" name="filter_by_player_name" value="" />
        <input type="submit" id="buttonMatches" value="matches" />
      </form>
    </div>
  </body>
</html>
