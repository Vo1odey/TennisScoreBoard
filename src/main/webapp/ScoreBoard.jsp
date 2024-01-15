<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <meta charset="UTF-8" />
    <link
      href="https://fonts.googleapis.com/css2?family=Roboto&display=swap"
      rel="stylesheet"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/StyleMainPage.css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Score</title>
  </head>
  <body>
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
    <div class="tableData">
      <h2 class="data">${player1.playerMatchScore.countSet.set}</h2>
      <h2 class="data">${player1.playerMatchScore.countGame.game}</h2>
      <h2 class="data">${player1.playerMatchScore.countPoint.renderPoint}</h2>
      <h2 class="data">${player1.name}</h2>
    </div>
    <div class="tableData">
      <h2 class="data">${player2.playerMatchScore.countSet.set}</h2>
      <h2 class="data">${player2.playerMatchScore.countGame.game}</h2>
      <h2 class="data">${player2.playerMatchScore.countPoint.renderPoint}</h2>
      <h2 class="data">${player2.name}</h2>
    </div>
    <br />
    <div id="playerWonContainer">
      <form
        id="firstPlayerWonForm"
        action="match-score?uuid=${uuid}"
        method="post"
      >
        <input
          type="submit"
          id="p1WonButton"
          name="${player1.id}"
          value="${player1.name} win"
        />
        <input type="hidden" name="player_id" value="${player1.id}" />
      </form>
      <form
        id="secondPlayerWonForm"
        action="match-score?uuid=${uuid}"
        method="post"
      >
        <input
          type="submit"
          id="p2WonButton"
          name="${player2.id}"
          value="${player2.name} win"
        />
        <input type="hidden" name="player_id" value="${player2.id}" />
      </form>
    </div>
  </body>
</html>
