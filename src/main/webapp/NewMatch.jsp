<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <meta charset="UTF-8" />
    <link
      href="https://fonts.googleapis.com/css2?family=Roboto&display=swap"
      rel="stylesheet"
    />
    <link rel="stylesheet" href="/StyleMainPage.css" />

    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>New Match</title>
  </head>
  <body>
    <div id="heading">
      <h1 id="title">Tennis Score</h1>
    </div>
    <br />
    <div class="filedForForm">
      <form id="begin" action="new-match" method="post">
        <div class="playersName">
          <label for="Player1"><h2>Player №1</h2></label>
        </div>
        <div>
          <input
            class="filedForInput"
            type="text"
            id="Player1"
            name="Player1"
          />
        </div>
        <div class="playersName">
          <label for="Player2"><h2>Player №2</h2></label>
        </div>
        <div>
          <input
            class="filedForInput"
            type="text"
            id="Player2"
            name="Player2"
          />
        </div>
        <input type="submit" id="create" value="Begin" />
      </form>
    </div>
  </body>
</html>
