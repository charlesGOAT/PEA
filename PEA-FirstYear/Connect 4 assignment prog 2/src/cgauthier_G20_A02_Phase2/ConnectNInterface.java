package cgauthier_G20_A02_Phase2;

import java.util.Scanner;
import java.util.StringTokenizer;

public class ConnectNInterface {// start of ConnectNInterface

	public static char currentColor;
	public static ConnectNGame game = new ConnectNGame();
	public static String player1;

	public ConnectNInterface() {
		// TODO Auto-generated constructor stub
	}

	static Scanner keyboard = new Scanner(System.in);

	public static void newBoard(int numRows, int numColumns) {// newBoard method. it creates a empty board based on
		int playCount = 0;
		// player input of the number of columns and rows
		if (!game.resumeGame) {
			game.board = new char[numRows][numColumns];
			for (int i = 0; i < game.board.length; i++) {// for

				for (int w = 0; w < game.board[i].length; w++) {// for
					game.board[i][w] = 'E';
					System.out.print(game.board[i][w] + " ");
				} // for
				System.out.println("");
			} // for
		} else {
			for (int i = 0; i < game.getBoard().length; i++) {// for

				for (int w = 0; w < game.getBoard()[i].length; w++) {// for
					if (game.getBoard()[i][w] == 'Y' || game.getBoard()[i][w] == 'R')
						++playCount;
					System.out.print(game.getBoard()[i][w] + " ");
				} // for
				System.out.println("");
			} // for
			game.plays = playCount;
		}
	}
	// end of method

	public static void displayInstructions() {// start of displayInstructions
		System.out.println(
				"Press S to save the state of your game or press U anytime during the game. To Quit game press Q");
	}// end of displayInstructions

	public static void displayPlays() {// start of displayPlays. This is the method that lets players play.
		if (game.resumeGame) {
			game.board = game.getBoard();

		}
		boolean wrongInput = false;
		int undoCounter = 0;
		int rowPosition = 0;
		int colPosition = 0;
		int currentPlayer = 1;
		if (game.resumeGame) {
			currentPlayer = game.getCurrentPlayer();
		}
		String playerInput;
		boolean notTaken = true;
		while (game.gameOver() == 'C') {
//			undoCounter = 0;
			game.setBoard(game.board);
			boolean validToken = false;
			if (currentPlayer == 1) {// beginning of if
				System.out.println(game.getPlayerOne() + ", enter the square number(row,column) of your move -->");
				currentColor = 'Y';
				game.setCurrentPlayer(currentPlayer);
				++currentPlayer;
			} // end of if
			else if (currentPlayer == 2) {// beginning of if
				System.out.println(game.getPlayerTwo() + ", enter the square number(row,column) of your move -->");
				currentColor = 'R';
				game.setCurrentPlayer(currentPlayer);
				--currentPlayer;
			} // end of if
			while (!validToken) {// beginning of while

				if (wrongInput)
					game.board[rowPosition][colPosition] = 'E';

				playerInput = keyboard.nextLine();

				while (!Character.isDigit(playerInput.charAt(0))) {// if
					if (playerInput.charAt(0) != 'Q' && playerInput.charAt(0) != 'U' && playerInput.charAt(0) != 'S') {
						System.out.println("Illegal Character: enter something else");
						playerInput = keyboard.nextLine();
					}
					if (playerInput.charAt(0) == 'Q') {
						System.out.println("Goodbye!");
						System.exit(0);
					}
					if (playerInput.charAt(0) == ('U') && game.plays == 0) {
						System.out.println("Cannot undo on the first move of the game");
					}
					if (playerInput.charAt(0) == ('U') && game.plays > 0) {
						game.board[rowPosition][colPosition] = 'E';
						++undoCounter;
						if (undoCounter == 1 && currentColor == 'R') {
							currentColor = 'Y';
							++currentPlayer;
							++undoCounter;
							System.out.println("Enter the Row and Column");
						} else if (undoCounter == 1 && currentColor == 'Y') {
							currentColor = 'R';
							--currentPlayer;
							++undoCounter;
							System.out.println("Enter the Row and Column");
						} else if (undoCounter >= 2) {
							System.out.println("you cannot undo two times(or more) the same turn");
						}
						playerInput = keyboard.nextLine();
					}
					if (playerInput.charAt(0) == 'S') {// if
						if (currentPlayer == 1)
							game.setCurrentPlayer(2);
						if (currentPlayer == 2)
							game.setCurrentPlayer(1);
						game.saveGame();
						System.out.println("Enter the Row and Column");
						playerInput = keyboard.nextLine();
					} // if
				} // if
				if (Character.isDigit(playerInput.charAt(0))) {
					StringTokenizer separateComma = new StringTokenizer(playerInput, ",");
					rowPosition = game.getRows() - Integer.parseInt(separateComma.nextToken());
					colPosition = Integer.parseInt(separateComma.nextToken()) - 1;
					while (rowPosition < 0 || rowPosition >= game.getRows() || colPosition < 0
							|| colPosition > game.getColumns() || game.board[rowPosition][colPosition] != 'E') {// start
																												// of
																												// while

						System.out.println(
								"you cannot place a token outside the board or that has already a token in place.");
						playerInput = keyboard.nextLine();
						separateComma = new StringTokenizer(playerInput, ",");
						rowPosition = game.getRows() - Integer.parseInt(separateComma.nextToken());
						colPosition = Integer.parseInt(separateComma.nextToken()) - 1;
					} // end of while
				}
				for (int i = 0; i < game.board.length; i++) {// for loop

					for (int w = 0; w < game.board[i].length; w++) {// for
						if (game.board[i][w] != game.board[rowPosition][colPosition]
								&& (game.board[i][w] != 'R' && game.board[i][w] != 'Y'))
							game.board[i][w] = 'E';

						else
							game.board[rowPosition][colPosition] = currentColor;
					} // for
				} // for loop
				for (int i = 0; i < game.board.length; i++) {// for

					for (int w = 0; w < game.board[i].length; w++) {// for
						System.out.print(game.board[i][w] + " ");
					} // for
					System.out.println("");
				} // for
				if (rowPosition != game.getRows() - 1 && (game.board[rowPosition + 1][colPosition] == 'E')) {// beginning
																												// of if

					System.out.println("You cannot put a token over an empty space. Please put a valid token");
					wrongInput = true;
				} // end of if

				else {// else
					validToken = true;
					wrongInput = false;
					game.setColPosition(colPosition);
					game.setRowPosition(rowPosition);
				} // else
				++game.plays;
				undoCounter = 0;
			} // end of while
		}
		if (game.gameOver() == 'T') {// beginning of if
			System.out.println("Tie!");
		} // end of if
		if (game.gameOver() == 'W' && currentColor == 'Y') {// beginning of if
			System.out.println(game.getPlayerOne() + " won!");
		} // end of if
		if (game.gameOver() == 'W' && currentColor == 'R') {// beginning of if
			System.out.println(game.getPlayerTwo() + " won!");
		} // end of if

	}// end of display plays

	public static void main(String args[]) {// beginning of void (basically just calls the methods from connectNGame)
		System.out.println("Please Enter N to start a new game or R to resume the game stored in currentGame.txt");
		String gameStart = keyboard.nextLine();
		game.setState(gameStart.charAt(0));
		boolean confirmationChecker = true;

		while (confirmationChecker) {// beginning of while loop

			if (game.getState() != 'N' && game.getState() != 'R') {// beginning of if
				System.out.println("Please enter a valid Letter");
				gameStart = keyboard.nextLine();
				game.setState(gameStart.charAt(0));
			} // if
			else if (game.getState() == 'N') {// else if
				confirmationChecker = false;
			} // else if
			else if (game.getState() == 'R') {// else if
				confirmationChecker = false;
			} // else if
		} // end of if
		game.gameStart();

		if (!game.resumeGame) {// if
			boolean confirmationRows = true;
			System.out.println("Please Enter the number of Rows");
			while (confirmationRows) {// while

				String numberOfRows = keyboard.nextLine();
				if (Integer.parseInt(numberOfRows) < 4 || Integer.parseInt(numberOfRows) > 12
						|| !Character.isDigit(numberOfRows.charAt(0))) {// if
					System.out.println("Please enter a valid number");
				} // if
				else {
					confirmationRows = false;
					game.setRows(Integer.parseInt(numberOfRows));
					System.out.println(numberOfRows);
				}
			} // while
		} // if
		else {
			game.getRows();
			System.out.println(game.getRows());
		}

		if (!game.resumeGame) {// if
			boolean confirmationCols = true;
			System.out.println("Please Enter the number of Columns");
			while (confirmationCols) {// while
				String numberOfCols = keyboard.nextLine();
				if (Integer.parseInt(numberOfCols) < 4 || Integer.parseInt(numberOfCols) > 12
						|| !Character.isDigit(numberOfCols.charAt(0))) {// if
					System.out.println("Please enter a valid number ");
				} // if
				else {// else
					confirmationCols = false;
					game.setCol(Integer.parseInt(numberOfCols));
					System.out.println(numberOfCols);
				} // else
			} // while

		} // if
		else {// else
			game.getColumns();
			System.out.println(game.getColumns());
		} // else

		if (!game.resumeGame) {// if
			boolean confirmationChecks = true;
			System.out.println("Please Enter the value for N, the number of checks");
			while (confirmationChecks) {// while
				String numberOfChecks = keyboard.nextLine();
				if (Integer.parseInt(numberOfChecks) < 3 || Integer.parseInt(numberOfChecks) > 8
						|| Integer.parseInt(numberOfChecks) > game.getRows()
						|| Integer.parseInt(numberOfChecks) > game.getColumns()
						|| !Character.isDigit(numberOfChecks.charAt(0))) {// if
					System.out.println(
							"Please enter a valid number (goes from 3-8 and equal or smaller than the number of columns and rows)");
				} // if
				else
					confirmationChecks = false;
				System.out.println(numberOfChecks);
				game.setChecks(Integer.parseInt(numberOfChecks));
			}
		} // while
		else {// else
			game.getChecks();
			System.out.println(game.getChecks());
		} // else

		if (!game.resumeGame) {// if
			boolean confirmationPlayer = true;
			System.out.println("Please Enter the name of Player 1 yellow:");
			while (confirmationPlayer) {// while
				player1 = keyboard.nextLine();
				if (player1.trim().isEmpty())
					System.out.println("enter a valid name");
				else {// else
					confirmationPlayer = false;
					game.setPlayerOne(player1);
				} // else
			} // while
		} // if
		else {// else
			game.getPlayerOne();
			System.out.println(game.getPlayerOne());
		} // else
		if (!game.resumeGame) {// if
			boolean confirmationPlayer = true;
			System.out.println("Please Enter the name of Player 2 Red:");
			while (confirmationPlayer) {// while
				String player2 = keyboard.nextLine();
				if (player2.trim().isEmpty() || player2.trim().equals(player1))
					System.out.println("enter a valid name");
				else {// else
					confirmationPlayer = false;
					game.setPlayerTwo(player2);
				} // else
			} // while
		} // if
		else {// else
			game.getPlayerTwo();
			System.out.println(game.getPlayerTwo());
		} // else
		displayInstructions();
		newBoard(game.getRows(), game.getColumns());
		displayPlays();
		// end of main method

	}// end of main
}// end of class