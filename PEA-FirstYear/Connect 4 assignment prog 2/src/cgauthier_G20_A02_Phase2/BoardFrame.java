package cgauthier_G20_A02_Phase2;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class BoardFrame extends JFrame implements ActionListener {// class
//these are all my variables for the class
	ConnectNGame game = new ConnectNGame();
	private JPanel boardContent;
	private JPanel connectPanel;
	private JButton connectButtons[][];
	private JLabel whoseTurn;
	private JPanel ticTacToePanel = new JPanel();
	private static char player[] = { 'Y', 'R' };
	private static char currentPlayer = player[0];;
	private char connectBoard[][] = new char[game.getRows()][game.getColumns()];
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenu mnAbout;
	private JMenu mnHelp;
	private JMenu mnExit;
	private JMenuItem mntmSaveGame;
	private JMenuItem mntmUndoMove;
	private JMenuItem mntmRestoreGame;
	private JMenuItem mntmNewGame;
	private JMenuItem mntmAbout;
	private JMenuItem mntmHelp;
	private JMenuItem mntmExit;
	private int undoCounter = 0;
	private int quitPrompt = 0;
	private int next;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {// main
		EventQueue.invokeLater(new Runnable() {
			public void run() {// start of run
				try {// start of try
					BoardFrame frame = new BoardFrame();
					frame.setVisible(true);
				} // end of try
				catch (Exception e) {// beginning of catch
					e.printStackTrace();
				} // end of catch
			}// end of run
		});
	}// end of main

	/**
	 * Create the frame.
	 */
	public BoardFrame() {// beginning of the constructor
		setTitle("Connect Board");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 635, 497);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmSaveGame = new JMenuItem("Save Game");
		mnFile.add(mntmSaveGame);
		mntmSaveGame.addActionListener(this);
		mntmUndoMove = new JMenuItem("Undo move");
		mnFile.add(mntmUndoMove);
		mntmUndoMove.addActionListener(this);

		mntmRestoreGame = new JMenuItem("Restore Game");
		mnFile.add(mntmRestoreGame);
		mntmRestoreGame.addActionListener(this);

		mntmNewGame = new JMenuItem("New Game");
		mnFile.add(mntmNewGame);
		mntmNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnNewGame_actionPerformed(arg0);
			}
		});
		mnAbout = new JMenu("About");
		menuBar.add(mnAbout);

		mntmAbout = new JMenuItem("About");
		mnAbout.add(mntmAbout);
		mntmAbout.addActionListener(this);

		mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		mntmHelp = new JMenuItem("Help");
		mnHelp.add(mntmHelp);
		mntmHelp.addActionListener(this);

		mnExit = new JMenu("Exit");
		menuBar.add(mnExit);

		mntmExit = new JMenuItem("Exit");
		mnExit.add(mntmExit);
		mntmExit.addActionListener(this);

		boardContent = new JPanel();
		boardContent.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(boardContent);
		boardContent.setLayout(null);

		connectPanel = new JPanel();
		connectPanel.setBounds(10, 11, 599, 332);
		boardContent.add(connectPanel);

	}// end of class

	private void btnNewGame_actionPerformed(ActionEvent e) {// beginning of btnNewGame_actionPerformed

		if (e.getSource() == mntmNewGame) {// if
			if (quitPrompt >= 1) {
				next = JOptionPane.showConfirmDialog(this, "You must quit before doing a new game. Quit?", "New Game",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (next == JOptionPane.YES_OPTION) {
					System.exit(1);
				}
			} else if (quitPrompt == 0) {
				ConnectNFrame userPrompt = new ConnectNFrame(this);
				userPrompt.setVisible(true);
				++quitPrompt;
			}
		} // if

	}// end of btnNewGame_actionPerformed

	public void initializeBoard() {// initializeBoard. This initializes a new board
		if (game.getRows() > 0) {// if
			connectPanel.setLayout(new GridLayout(game.getRows(), game.getColumns()));
			connectButtons = new JButton[game.getRows()][game.getColumns()];
			connectBoard = new char[game.getRows()][game.getColumns()];
			for (int row = 0; row < connectButtons.length; ++row) {// for
				for (int col = 0; col < connectButtons[row].length; ++col) {// for
					connectBoard[row][col] = 'E';
					connectButtons[row][col] = new JButton("E");
					connectButtons[row][col].setFont(new Font("board", Font.BOLD, 24));
					connectPanel.add(connectButtons[row][col]);
					connectButtons[row][col].addActionListener(this);
					if (row != game.getRows() - 1) {// if
						connectButtons[row][col].setEnabled(false);
					} // if
				} // for
			} // for
			connectPanel.setVisible(true);
		} // if

	}// end of initializeBoard

	@Override
	public void actionPerformed(ActionEvent e) {// action performed
		if (game.getRows() > 0) {// if
			for (int row = 0; row < connectButtons.length; ++row) {// first for loop

				for (int col = 0; col < connectButtons[row].length; ++col) {// beginning of second for loop
					if (e.getSource() == connectButtons[row][col]) {// beginning of if statement
						takeTurn(row, col);
						game.setRowPosition(row);
						game.setColPosition(col);
					} // end of if statement

				} // end of second for loop
			} // end of first for loop
		} // end of if statement
		if (e.getSource() == mntmAbout) {// if the about menu is clicked
			JOptionPane.showMessageDialog(this, "Charles-Etienne Gauthier\nHeritageCollege\n2021", "About",
					JOptionPane.INFORMATION_MESSAGE);
		} // end of if statement
		if (e.getSource() == mntmExit) {// if the Exit menu is clicked
			JOptionPane.showMessageDialog(this, "Goodbye!", "Exit", JOptionPane.INFORMATION_MESSAGE);
			System.exit(1);
		} // end of if statement
		if (e.getSource() == mntmHelp) {// if the help menu is clicked
			JOptionPane.showMessageDialog(this,
					"To be the first player to Make the numbers of checks the player selected \n the same color checkers in a row diagonally, horizontally or vertically.",
					"Help", JOptionPane.INFORMATION_MESSAGE);
		} // end of if statement
		if (e.getSource() == mntmSaveGame) {// if the save menu is clicked
			if (game.plays == 0) {
				JOptionPane.showMessageDialog(this, "Cannot save a board with no information", "Empty Board",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (currentPlayer == 'Y') {
				game.currentPlayer = 1;
				game.setBoard(connectBoard);
				game.saveGame();
				JOptionPane.showMessageDialog(this, "Game succesfully saved!", "Saved",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (currentPlayer == 'R') {
				game.currentPlayer = 2;
				game.setBoard(connectBoard);
				game.saveGame();
				JOptionPane.showMessageDialog(this, "Game succesfully saved!", "Saved",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} // end of if statement

		if (e.getSource() == mntmRestoreGame) {// if the restore game menu is clicked

			if (quitPrompt >= 1) {
				next = JOptionPane.showConfirmDialog(this, "You must quit before doing a new game. Quit?", "New Game",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (next == JOptionPane.YES_OPTION) {
					System.exit(1);
				}
			}

			else if (quitPrompt == 0) {
				game.setState('R');
				game.gameStart();
				restoreGame();
				revalidate();
				++quitPrompt;
			}
		} // end of if statement
		if (e.getSource() == mntmUndoMove) {// if undo move is clicked
			if (game.plays > 0) {// if statement
				connectBoard[game.getRowPosition()][game.getColPosition()] = 'E';
				connectButtons[game.getRowPosition()][game.getColPosition()].setText("E");
				connectButtons[game.getRowPosition()][game.getColPosition()].setEnabled(true);
				connectButtons[game.getRowPosition() - 1][game.getColPosition()].setEnabled(false);
				++undoCounter;
			} // end of if statement
		} // end of if statement
	}// end of the action performed

	private void takeTurn(int row, int col) {// start of takeTurn
		int next;
		String playerWinner = null;
		markSquare(row, col);
		char winner = game.gameOver();
		// if the game is not over yet, change currentPlayers
		if (winner == 'C') {// if statement
			if (currentPlayer == 'Y' && undoCounter == 0)
				currentPlayer = 'R';
			else if (currentPlayer == 'R' && undoCounter == 0)
				currentPlayer = 'Y';
			if (undoCounter == 1 && currentPlayer == 'R') {
				currentPlayer = 'Y';
				++undoCounter;
			} else if (undoCounter == 1 && currentPlayer == 'Y') {
				currentPlayer = 'R';
				++undoCounter;
			} else if (undoCounter == 2) {
				currentPlayer = currentPlayer;
				--undoCounter;
				--undoCounter;
			}
		} // end of takeTurn
		else {
			// if the game is over inform the players of the result and ask if they want to
			// play again

			if (currentPlayer == 'Y')
				playerWinner = game.getPlayerOne();
			if (currentPlayer == 'R')
				playerWinner = game.getPlayerTwo();
			if (winner == 'T')
				next = JOptionPane.showConfirmDialog(this, "Game Over! It's a tie! Nobody won!!! Play again?",
						"Game over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

			else
				next = JOptionPane.showConfirmDialog(this, "Game Over! " + playerWinner + " won!!! Play again?",
						"Game over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (next == JOptionPane.YES_OPTION)
				newGame();
			else
				System.exit(0);
		}
	} // takeTurn(int, int)

	private void markSquare(int row, int col) {// start of markSquare
		char player = currentPlayer;
		if (undoCounter == 1 && currentPlayer == 'R') {
			player = 'Y';
			++undoCounter;
		} else if (undoCounter == 1 && currentPlayer == 'Y') {
			player = 'R';
			++undoCounter;
		} else if (undoCounter == 2) {
			player = player;
			--undoCounter;
			--undoCounter;
		}
		connectButtons[row][col].setEnabled(false);
		connectButtons[row][col].setText("" + player);
		if (row != 0)
			connectButtons[row - 1][col].setEnabled(true);
		// don't allow two players to select the same square
		// mark the square on the board

		game.board = connectBoard;
		game.setRowPosition(row);
		game.setColPosition(col);
		game.play(row, col, player);
	} // markSquare(int, int);

	private void newGame() {// start of newGame
		game.plays = 0;
		for (int row = 0; row < connectButtons.length; ++row) {// start of for loop

			for (int col = 0; col < connectButtons[row].length; ++col) {// start of second for loop
				connectButtons[row][col].setEnabled(true);
				connectButtons[row][col].setText("E");
				connectBoard[row][col] = 'E';
				if (row != game.getRows() - 1) {// start of if statement
					connectButtons[row][col].setEnabled(false);
				} // end of if statement
			} // end of second for loop

		} // end of for loop

	}// end of newGame

	private void restoreGame() {// start of restoreGame
		int playsCounter = 0;
		connectBoard = game.getBoard();
		connectButtons = new JButton[game.getRows()][game.getColumns()];
		connectPanel.setLayout(new GridLayout(game.getRows(), game.getColumns()));
		for (int row = 0; row < connectButtons.length; ++row) {// start of first loop
			for (int col = 0; col < connectButtons[row].length; ++col) {// start loop
				if (connectBoard[row][col] != 'Y' && connectBoard[row][col] != 'R') {// start of if statement
					connectBoard[row][col] = 'E';
					connectButtons[row][col] = new JButton("E");
					connectButtons[row][col].setFont(new Font("board", Font.BOLD, 24));
					connectPanel.add(connectButtons[row][col]);
					connectButtons[row][col].addActionListener(this);

					if (row != game.getRows() - 1)
						connectButtons[row][col].setEnabled(false);

				} // end of if statement

				else if (connectBoard[row][col] == 'Y') {// start of else if
					connectBoard[row][col] = 'Y';
					connectButtons[row][col] = new JButton("Y");
					connectButtons[row][col].setFont(new Font("board", Font.BOLD, 24));
					connectPanel.add(connectButtons[row][col]);
					connectButtons[row][col].addActionListener(this);
					connectButtons[row][col].setEnabled(false);
					if (connectBoard[row - 1][col] == 'E')
						connectButtons[row - 1][col].setEnabled(true);
					++playsCounter;
				} // end of else if statement
				else if (connectBoard[row][col] == 'R') {// start of else if
					connectBoard[row][col] = 'R';
					connectButtons[row][col] = new JButton("R");
					connectButtons[row][col].setFont(new Font("board", Font.BOLD, 24));
					connectPanel.add(connectButtons[row][col]);
					connectButtons[row][col].addActionListener(this);
					connectButtons[row][col].setEnabled(false);
					if (connectBoard[row - 1][col] == 'E')
						connectButtons[row - 1][col].setEnabled(true);
					++playsCounter;

				} // end of else if statement
			} // end of second for loop
		} // end of first loop
		if (game.getCurrentPlayer() == 1)
			currentPlayer = 'Y';
		else
			currentPlayer = 'R';
		game.getPlayerOne();
		game.getPlayerTwo();
		connectPanel.setVisible(true);
		game.plays = playsCounter;
	}// end of restoreGame

}// end of class
