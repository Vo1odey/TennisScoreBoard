<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <meta charset="UTF-8" />
    <link
      href="https://fonts.googleapis.com/css2?family=Roboto&display=swap"
      rel="stylesheet"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/StyleMainPage.css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Winner</title>
  </head>
  <div id="heading">
    <h1 id="title">Tennis Score</h1>
  </div>
  <br />
  <div id="tableHeaders">
    <h2 class="headers">Set</h2>
    <h2 class="headers">Game</h2>
    <h2 class="headers">Point</h2>
    <h2 class="headers">Name</h2>
  </div>
  <div id="tableData">
    <h2 class="data">${player1.gameScore.set}</h2>
    <h2 class="data">${player1.gameScore.game}</h2>
    <h2 class="data">${player1.gameScore.point.getValue()}</h2>
    <h2 class="data">${player1.name}</h2>
  </div>
  <div id="tableData">
    <h2 class="data">${player2.gameScore.set}</h2>
    <h2 class="data">${player2.gameScore.game}</h2>
    <h2 class="data">${player1.gameScore.point.getValue()}</h2>
    <h2 class="data">${player2.name}</h2>
  </div>
  <br />
  <h1 id="winner">&#127942 ${winner} &#127942</h1>
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
</html>
