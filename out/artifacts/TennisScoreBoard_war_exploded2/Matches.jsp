<%@ page import="com.dragunov.tennisscoreboard.models.Match" %>
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
    <title>Matches</title>
  </head>
  <body>
    <div id="heading">
      <h1 id="title">Tennis Score</h1>
    </div>
    <br />
    <div id="newMatchAndSearchFormContainer">
      <form
        id="formNewMatch"
        action="${pageContext.request.contextPath}/new-match"
        method="get"
      >
        <input type="submit" id="newMatch" value="new match" />
      </form>
      <div id="searchContainer">
        <form id="searchForm" action="" method="get">
          <div id="playerName">
            <h2>name:</h2>
          </div>
          <input type="hidden" name="page" value="1" />
          <div id="inputText">
            <input id="input" type="text" name="filter_by_player_name" />
          </div>
          <div id="tap">
            <input id="buttonSearch" type="submit" value="search" />
          </div>
        </form>
      </div>
    </div>
    <br />
    <div id="tableWinners">
      <h2 class="headersWinner">Player 1</h2>
      <h2 class="headersWinner">Player 2</h2>
      <h2 class="headersWinner">Winner</h2>
    </div>

      <c:forEach var="MatchModel" items="${matchPage}">
        <div id="tableWinners">
      <h3 class="dataWinner">${MatchModel.player1.name}</h3>
      <h3 class="dataWinner">${MatchModel.player2.name}</h3>
      <h3 class="dataWinner">${MatchModel.winner.name}</h3>
    </div>
      </c:forEach>

    <br />
    <div class="form-button">
      <c:forEach var="i" begin="1" end="${requestScope.quantityOfPages}" step="1">
      <form class="pagination" action="" method="get">
        <input type="hidden" name="page" value="${i}" />
        <input type="hidden" name="filter_by_player_name" value="${filter}" />
        <input type="submit" id="matches" value="${i}" />
      </form>
      </c:forEach>
    </div>
  </body>
</html>
