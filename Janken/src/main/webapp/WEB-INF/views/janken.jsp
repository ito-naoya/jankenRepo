<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/style.css">
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="janken">

		<div id="enemyCountDiv">
			<input type="checkbox" name="enemyCount" value="2" id="enemyCount">
			<label for="enemyCount">CP2を追加</label>
		</div>

<div id="rspDiv">
		<div id="rockDiv">
			<input type="radio" name="myHand" value="rock" id="rock"> <label
				for="rock"> <img class="myHand"
				src="./images/janken-plate_rock.jpg">
			</label>
		</div>


		<div id="scissorsDiv">
			<input type="radio" name="myHand" value="scissors" id="scissors">
			<label for="scissors"> <img class="myHand"
				src="./images/janken-plate_scissors.jpg">
			</label>
		</div>

		<div id="paperDiv">
			<input type="radio" name="myHand" value="paper" id="paper"> <label
				for="paper"> <img class="myHand"
				src="./images/janken-plate_paper.jpg">
			</label>
		</div>
</div>

		<input type="submit" value="じゃんけん！ぽん！！" id="rsp">
	</form>


	<div class="divFlex">
		<p>Your :</p>
		<img class="jankenImgSize" src="${myHand}">
	</div>
	<br>

	<div class="divFlex">
		<p>firstEnemy :</p>
		<img class="jankenImgSize" src="${firstEnemyHand}">
	</div>
	<br>

	<div class="divFlex">
		<p>secondEnemy :</p>
		<img class="jankenImgSize" src="${secondEnemyHand}">
	</div>
	<br>

	<p>${result}</p>

</body>
</html>
