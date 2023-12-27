<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <style>
        .column {
            float: left;
            width: 33.33%;
            padding: 10px;
            box-sizing: border-box;

        }
        .centered {
            text-align: center;
        }
        .button-container {
            text-align: center; /* Центрируем кнопки относительно контейнера */
            padding: 20px;
            margin-top: 30px; /* Устанавливаем отступ сверху */
        }
        .button-left {
            float: left; /* Выравниваем кнопку слева */
            margin-right: 20px; /* Устанавливаем отступ справа */

        }
        .button-right {
            float: right; /* Выравниваем кнопку справа */
            margin-left: 20px; /* Устанавливаем отступ слева */
        }
        .button-left, .button-right {
            padding: 10px 20px; /* добавить внутренний отступ для кнопок */
        }
    </style>
</head>
<body>
<div class="centered">

<h1>Match Score</h1>

<br/>

<div class="column" style="background-color:#aaa;">
    <h2>SET</h2>
    <p>Содержимое колонки 1</p>
</div>

<div class="column" style="background-color:#bbb;">
    <h2>GAME</h2>
    <p>Содержимое колонки 2</p>
</div>

<div class="column" style="background-color:#ccc;">
    <h2>POINT</h2>
    <p>Содержимое колонки 3</p>
</div>
</div>


<div class="button-container">
    <form action="match-score" method="get">
        <button class="button-left" type="submit">Player 1</button>
    </form>
    <form action="match-score" method="get">
        <button class="button-right" type="submit">Player 2</button>
    </form>
</div>
</body>
</html>