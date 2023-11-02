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

	private static String victoryMsg = "🎉🎉🎉🎉🎉あなたの勝ちです!!!🎉🎉🎉🎉🎉";
	private static String loseMsg = "😭あなたの負けです😭";
	private static String drawMsg = "😀😀😀あいこです😀😀😀";

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

	private static String getResult(String myHand, String firstEnemyHand, String secondEnemy2Hand) {
		String result = "";
		if (myHand.equals(firstEnemyHand) && myHand.equals(secondEnemy2Hand)
				|| !myHand.equals(firstEnemyHand) && !firstEnemyHand.equals(secondEnemy2Hand)
						&& !myHand.equals(secondEnemy2Hand)) {
			result = drawMsg;
		} else if (myHand.equals("rock") && !firstEnemyHand.equals("paper") && !secondEnemy2Hand.equals("paper")
				|| myHand.equals("scissors") && !firstEnemyHand.equals("rock") && !secondEnemy2Hand.equals("rock")
				|| myHand.equals("paper") && !firstEnemyHand.equals("scissors")
						&& !secondEnemy2Hand.equals("scissors")) {
			result = victoryMsg;
		} else {
			result = loseMsg;
		}
		return result;
	}

	private static String getResult(int enemyCount, String myHand, ArrayList<String> enemyHands) {
		String result;
		if (enemyCount == 2) {
			result = myHand != "" ? getResult(myHand, enemyHands.get(0), enemyHands.get(1)) : "";
		} else {
			result = myHand != "" ? getResult(myHand, enemyHands.get(0)) : "";
		}
		return result;
	}

	private static ArrayList<String> createEnemyHand(int enemyCount, String myHand) {

		ArrayList<String> createdEnemyHands = new ArrayList<String>();
		String[] choicesEnemyhands = { "rock", "paper", "scissors" };

		if (!myHand.equals("")) {
			for (int i = 0; i < enemyCount; i++) {
				Random rand = new Random();
				int num = rand.nextInt(choicesEnemyhands.length);
				String randomEnemyHand = choicesEnemyhands[num];
				createdEnemyHands.add(randomEnemyHand);
			}
		} else {
			createdEnemyHands.add(myHand);
		}
		return createdEnemyHands;
	}

	private static String createImagePath(String hand) {
		String path = hand.equals("") ? "./images/janken-plate_emptyhand.jpg"
				: "./images/janken-plate_" + hand + ".jpg";
		return path;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int enemyCount = request.getParameter("enemyCount") == null ? 1
				: Integer.parseInt(request.getParameter("enemyCount"));

		String myHand = request.getParameter("myHand") == null ? "" : request.getParameter("myHand");

		ArrayList<String> enemyHands = createEnemyHand(enemyCount, myHand);

		String result = getResult(enemyCount, myHand, enemyHands);

		if (!myHand.equals("") && enemyCount == 2) {
			request.setAttribute("myHand", createImagePath(myHand));
			request.setAttribute("firstEnemyHand", createImagePath(enemyHands.get(0)));
			request.setAttribute("secondEnemyHand", createImagePath(enemyHands.get(1)));
			request.setAttribute("result", "result : " + result);
		} else if (!myHand.equals("") && enemyCount == 1) {
			request.setAttribute("myHand", createImagePath(myHand));
			request.setAttribute("firstEnemyHand", createImagePath(enemyHands.get(0)));
			request.setAttribute("secondEnemyHand", createImagePath(""));
			request.setAttribute("result", "result : " + result);
		} else if (myHand.equals("")) {
			request.setAttribute("myHand", createImagePath(myHand));
			request.setAttribute("firstEnemyHand", createImagePath(""));
			request.setAttribute("secondEnemyHand", createImagePath(""));
			request.setAttribute("result", "result : " + result);
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
