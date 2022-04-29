package cgauthier_G20_A02_Phase2;

import java.io.FileWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author charl
 *
 */
public class ConnectNGame {// start of ConnectNGame
	private ConnectNInterface boardObject = new ConnectNInterface();
	public static int plays = 0;
	private String fileInformation;
	private FileWriter writer;
	public char board[][];
	private  char state;
	public static int rows;
	public static int columns;
	public static int checks;
	public int posRow;
	public int posColumn;
	public static String player1;
	public static String player2;
	public static int currentPlayer;
	private char save;
	private String playerOne;
	private String playerTwo;
	private static GameSaver saver = new GameSaver();
	public static char currBoard[][];
	public boolean resumeGame = false;
	public static GameReader read = new GameReader();

	public ConnectNGame() {
		// TODO Auto-generated constructor stub
	}

	public void gameStart() {// beginning of game start method. This method prompts the user to start the
		// game
		if (getState() == 'R') {
			read.ReadFromFile();
			resumeGame = true;
		}
	}
	// end of gameStart method

	

	public void saveGame() {// saveGame
		if (currentPlayer == 1)
			setCurrentPlayer(1);
		if (currentPlayer == 2)
			setCurrentPlayer(2);
		saver.open();
		saver.saveGame();
		saver.close();
	}// saveGame

	public char gameOver() {// gameOver method
		if (checkDiagonalLeftRight(getChecks(), getRowPosition(), getColPosition())) {// if
			return 'W';
		} // if
		else if (checkDiagonalRightLeft(getChecks(), getRowPosition(), getColPosition())) {// else if
			return 'W';
		} // else if
		else if (checkRows(getChecks(), getRowPosition(), getColPosition())) {// else if
			return 'W';
		} // else if
		else if (checkCols(getChecks(), getRowPosition(), getColPosition())) {// else if
			return 'W';
		} // else if
		else if (plays == getRows() * getColumns()) {// else if
			return 'T';
		} // else if
		else
			return 'C';

	}// gameOver method

	public void setRows(int row) {
		rows = row;
	}

	public void setCol(int col) {
		columns = col;
	}

	public static int getRows() {// getRows Method
		return rows;
	}// getRows Method

	public static int getColumns() {// getColumns method
		return columns;
	}// getColumns method

	public void confirmationChecks() {// confirmationChecks method

	}// confirmationChecks method

	public void setChecks(int checker) {
		checks = checker;
	}

	public int getChecks() {// getChecks method
		return checks;

	}// getChecks method

	public boolean checkDiagonalRightLeft(int check, int rows, int column) {// checkDiagonalRightLeft method
		char lastMove[][] = board;
		int i = 1;
		int counter = 0;
		while (rows - i >= 0 && column + i < board[rows].length
				&& lastMove[rows - i][column + i] == lastMove[rows][column] && lastMove[rows][column] != 'E') {// while
			++counter;
			++i;
		} // while
		i = 1;
		while (rows + i < board.length && column - i >= 0 && lastMove[rows + i][column - i] == lastMove[rows][column]
				&& lastMove[rows][column] != 'E') {// while
			++counter;
			++i;
		} // while
		if (counter + 1 >= check)
			return true;
		else
			return false;
	}// checkDiagonalRightLeft method

	public boolean checkDiagonalLeftRight(int check, int rows, int column) {// checkDiagonalLeftRight
		char lastMove[][] = board;
		int i = 1;
		int counter = 0;
		while (rows + i < board.length && column + i < board[rows].length
				&& lastMove[rows + i][column + i] == lastMove[rows][column] && lastMove[rows][column] != 'E') {// while
			++counter;
			++i;
		} // while
		i = -1;
		while (rows + i >= 0 && column + i >= 0 && lastMove[rows + i][column + i] == lastMove[rows][column]
				&& lastMove[rows][column] != 'E') {// while
			++counter;
			--i;
		} // while
		if (counter + 1 >= check)
			return true;
		else
			return false;
	}// checkDiagonalLeftRight

	public void setSave(char saver) {// setSave
		save = saver;
	}// setSave

	public char getSave() {// getSave
		return save;
	}// getSave

	public boolean checkCols(int check, int rows, int column) {// checkCols, checks the columns

		char lastMove[][] = board;
		int i = 1;
		int counter = 0;
		while (rows < board.length && column + i < board[rows].length
				&& lastMove[rows][column + i] == lastMove[rows][column] && lastMove[rows][column] != 'E') {// while
			++counter;
			++i;
		} // while
		i = -1;
		while (rows < board.length && column + i >= 0 && lastMove[rows][column + i] == lastMove[rows][column]
				&& lastMove[rows][column] != 'E') {// while
			++counter;
			--i;
		} // while
		if (counter + 1 >= check)
			return true;
		else
			return false;
	}// checkCols
//sets and gets all the stuff in the board

	public  void setState(char stateSetter) {// setState
		state = stateSetter;
	}// setState

	public  char getState() {// getState
		return state;
	}// getState

	public void setRowPosition(int posR) {// setRowPosition
		posRow = posR;
	}// setRowPosition

	public int getRowPosition() {// getRowPosition
		return posRow;
	}// getRowPosition

	public void setColPosition(int posC) {// setColPosition
		posColumn = posC;

	}// setColPosition

	public int getColPosition() {// getColPosition
		return posColumn;
	}// getColPosition

	public static void setPlayerOne(String playerOne) {// setPlayerOne
		player1 = playerOne;
	}// setPlayerOne

	public static String getPlayerOne() {// getPlayerOne
		return player1;
	}// getPlayerOne

	public static void setPlayerTwo(String playerTwo) {// setPlayerTwo
		player2 = playerTwo;
	}// setPlayerTwo

	public static String getPlayerTwo() {
		return player2;
	}

	public boolean checkRows(int check, int rows, int column) {// start of checkRows

		char lastMove[][] = board;
		int i = 1;
		int counter = 0;
		while (rows + i < board.length && column < board[rows].length
				&& lastMove[rows + i][column] == lastMove[rows][column] && lastMove[rows][column] != 'E') {// while
			++counter;
			++i;
		} // while
		i = -1;
		while (rows + i >= 0 && column < board[rows].length && lastMove[rows + i][column] == lastMove[rows][column]
				&& lastMove[rows][column] != 'E') {// start of while
			++counter;
			--i;
		} // end of while
		if (counter + 1 >= check)
			return true;
		else
			return false;
	}// end of checkRows

	public static void setCurrentPlayer(int currPlayer) {
		currentPlayer = currPlayer;
	}// end

	public static int getCurrentPlayer() {// start of getCurrentPlayer
		return currentPlayer;
	}// end of getCurrentPlayer

//sets and gets the board	
	public static void setBoard(char[][] currentBoard) {// setBoard
		currBoard = currentBoard;
	}// getBoard

	public static char[][] getBoard() {// getBoard
		return currBoard;
	}// getBoard

	public void play(int row, int col, char player) {// play
		board[row][col] = player;
		++plays;
	}// play

	public void newGame() {// newGame()
		for (int row = 0; row < board.length; ++row) {// for

			for (int col = 0; col < board[row].length; ++col) {// for
				board[row][col] = ' ';
			} // for
		} // for
		plays = 0;
	} // newGame()
}// end of class ConnectNGame
