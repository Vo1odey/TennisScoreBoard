<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Theme.css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>New Match</title>
  </head>
  <body>
    <h1 id="heading">Tennis Score</h1>
    <br />
    <form id="begin" action="new-match" method="post">
      <table id="NewMatchTable">
        <tr>
          <td>
            <label for="Player1"><h2>Player 1 name:</h2></label>
          </td>
          <td><input type="text" id="Player1" name="Player1" /></td>
        </tr>
        <tr>
          <td>
            <label for="Player2"><h2>Player 2 name:</h2></label>
          </td>
          <td><input type="text" id="Player2" name="Player2" /></td>
        </tr>
      </table>
      <br />
      <br />
      <input type="submit" id="create" value="Begin" />
    </form>
  </body>
</html>
