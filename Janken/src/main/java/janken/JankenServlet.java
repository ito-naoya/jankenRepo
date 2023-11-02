package janken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/janken")
public class JankenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public JankenServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	//勝ちメッセージ
	private static String victoryMsg = "🎉🎉🎉🎉🎉あなたの勝ちです!!!🎉🎉🎉🎉🎉";

	//負けメッセージ
	private static String loseMsg = "😭あなたの負けです😭";

	//引き分けメッセージ
	private static String drawMsg = "😀😀😀あいこです😀😀😀";

	//２人対戦時の対戦結果取得メソッド
	private static String getResult(String myHand, String enemyHand) {
		String result = "";
		if (myHand.equals(enemyHand)) {
			result = drawMsg;
		} else if (myHand.equals("rock") && enemyHand.equals("scissors") ||
				myHand.equals("scissors") && enemyHand.equals("paper") ||
				myHand.equals("paper") && enemyHand.equals("rock")) {
			result = victoryMsg;
		} else {
			result = loseMsg;
		}
		return result;
	}

	//３人対戦時の対戦結果取得メソッド
	private static String getResult(String myHand, String firstEnemyHand, String secondEnemyHand) {
		String result = "";
		if (myHand.equals(firstEnemyHand) && myHand.equals(secondEnemyHand)
				|| !myHand.equals(firstEnemyHand) && !firstEnemyHand.equals(secondEnemyHand)
						&& !myHand.equals(secondEnemyHand)) {
			result = drawMsg;
		} else if (myHand.equals("rock") && !firstEnemyHand.equals("paper") && !secondEnemyHand.equals("paper")
				|| myHand.equals("scissors") && !firstEnemyHand.equals("rock") && !secondEnemyHand.equals("rock")
				|| myHand.equals("paper") && !firstEnemyHand.equals("scissors")
						&& !secondEnemyHand.equals("scissors")) {
			result = victoryMsg;
		} else {
			result = loseMsg;
		}
		return result;
	}

	//対戦結果取得
	private static String getResult(int enemyCount, String myHand, ArrayList<String> enemyHands) {
		String result = "";
		//３人対戦時
		if (enemyCount == 2) {
			result = myHand != "" ? getResult(myHand, enemyHands.get(0), enemyHands.get(1)) : "";
			//２人対戦時
		} else if (enemyCount == 1) {
			result = myHand != "" ? getResult(myHand, enemyHands.get(0)) : "";
		}
		return result;
	}

	//コンピュータの選ぶ手をランダムで生成
	private static ArrayList<String> createEnemyHand(int enemyCount, String myHand) {
		//コンピュータの選ぶ手用List定義
		ArrayList<String> createdEnemyHands = new ArrayList<String>();

		//コンピュータの選ぶ手の選択肢の定義
		String[] choicesEnemyhands = { "rock", "paper", "scissors" };

		//プレイヤーが選択している場合
		if (!myHand.equals("")) {
			for (int i = 0; i < enemyCount; i++) {
				Random rand = new Random();
				int num = rand.nextInt(choicesEnemyhands.length);
				String randomEnemyHand = choicesEnemyhands[num];
				createdEnemyHands.add(randomEnemyHand);
			}
			//未選択の場合
		} else {
			createdEnemyHands.add("");
		}
		return createdEnemyHands;
	}

	//じゃんけん画像のpath
	private static String createImagePath(String hand) {
		String imgPath = hand.equals("") ? "./images/janken-plate_emptyhand.jpg"
				: "./images/janken-plate_" + hand + ".jpg";
		return imgPath;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//対戦人数の取得
		int enemyCount = request.getParameter("enemyCount") == null ? 1
				: Integer.parseInt(request.getParameter("enemyCount"));

		//プレイヤーの選ぶ手の取得
		String myHand = request.getParameter("myHand") == null ? "" : request.getParameter("myHand");

		//コンピュータの選ぶ手の取得
		ArrayList<String> enemyHands = createEnemyHand(enemyCount, myHand);

		//対戦結果の取得
		String result = getResult(enemyCount, myHand, enemyHands);

		if (!myHand.equals("") && enemyCount == 2) {
			request.setAttribute("myHand", createImagePath(myHand));
			request.setAttribute("firstEnemyHand", createImagePath(enemyHands.get(0)));
			request.setAttribute("secondEnemyHand", createImagePath(enemyHands.get(1)));
			request.setAttribute("result", "結果 : " + result);
		} else if (!myHand.equals("") && enemyCount == 1) {
			request.setAttribute("myHand", createImagePath(myHand));
			request.setAttribute("firstEnemyHand", createImagePath(enemyHands.get(0)));
			request.setAttribute("secondEnemyHand", createImagePath(""));
			request.setAttribute("result", "結果 : " + result);
		} else if (myHand.equals("")) {
			request.setAttribute("myHand", createImagePath(""));
			request.setAttribute("firstEnemyHand", createImagePath(""));
			request.setAttribute("secondEnemyHand", createImagePath(""));
			request.setAttribute("result", "結果 : " + result);
		}

		String view = "/WEB-INF/views/janken.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//		doGet(request, response);
		doGet(request, response);
	}

}
