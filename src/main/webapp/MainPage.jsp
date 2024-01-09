<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/StyleMainPage.css" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Main Page</title>
</head>
<body>
<h1 id="heading">Tennis Score</h1>
<br />
<form id="newMatch" action="new-match" method="get">
    <input type="submit" class="matches" value="new match" />
</form>
<form id="MatchList" action="matches/" method="get">
    <input type="hidden" name="page" value="1" />
    <input type="hidden" name="filter_by_player_name" value="" />
    <input type="submit" class="matches" value="matches" />
</form>
</body>
</html>
