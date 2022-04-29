package linked_data_structures;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import java.io.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HangmanFrame extends JFrame implements ActionListener, Serializable {//HangmanFrame. This class is used to display the frame Author, Charles-Etienne Gauthier

	private JPanel contentPane;
	private JTextField textFieldGuess;
	private JMenu fileMenu;
	private JMenu hintMenu;
	private JMenuItem mntmHint;
	private JMenu scoreMenu;
	private JMenuItem mntmScoreboard;
	private JMenu aboutMenu;
	private JMenuItem mntmAbout;
	private JLabel lblGuess;
	private JLabel lblLettersGuessed;
	private JTextArea textAreaGuessed;
	private JButton btnGuess;
	private JMenuBar menuBar;
	private JMenuItem mntmNewGame;
	private ImageIcon hangManImg = new ImageIcon("../images/Hangman.png");
	private JLabel lblNewLabel;
	private JLabel lblUnknownWord;
	private Game game = new Game();
	private DictionaryReader reader = new DictionaryReader();
	private JLabel lblTriesRemaining;
	private JLabel lblName;
	private ScoreBoard score = new ScoreBoard();
	private SaveGame save = new SaveGame();
	private static HangmanFrame frame = new HangmanFrame();
   
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {//main
		EventQueue.invokeLater(new Runnable() {//main
			public void run() {//run

				frame.setVisible(true);

			}
		});//run
	}//main

	/**
	 * Create the frame.
	 */
	public HangmanFrame() {//constructor
		setTitle("Hangman");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage("H:\\420-G30\\Assignments\\cgauthier_G30_A02_Linked_Lists\\Hangman.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 665, 670);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		

		mntmNewGame = new JMenuItem("New Game");
		fileMenu.add(mntmNewGame);
		mntmNewGame.addActionListener(this);

		
		hintMenu = new JMenu("Hint");
		menuBar.add(hintMenu);
		hintMenu.setEnabled(false);

		mntmHint = new JMenuItem("Get a hint");
		hintMenu.add(mntmHint);
		mntmHint.addActionListener(this);
		scoreMenu = new JMenu("Score");
		menuBar.add(scoreMenu);

		mntmScoreboard = new JMenuItem("Scoreboard");
		scoreMenu.add(mntmScoreboard);
		mntmScoreboard.addActionListener(this);

		aboutMenu = new JMenu("About");
		menuBar.add(aboutMenu);

		mntmAbout = new JMenuItem("About the program");
		aboutMenu.add(mntmAbout);
		mntmAbout.addActionListener(this);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 206, 209));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblGuess = new JLabel("Guess a Letter");
		lblGuess.setForeground(new Color(255, 255, 255));
		lblGuess.setFont(new Font("Monospaced", Font.PLAIN, 13));
		lblGuess.setBounds(52, 428, 112, 20);
		contentPane.add(lblGuess);

		textFieldGuess = new JTextField();
		textFieldGuess.setBounds(62, 459, 25, 20);
		contentPane.add(textFieldGuess);
		textFieldGuess.setColumns(1);

		lblLettersGuessed = new JLabel("Letters Guessed");
		lblLettersGuessed.setForeground(new Color(255, 255, 255));
		lblLettersGuessed.setFont(new Font("Monospaced", Font.PLAIN, 13));
		lblLettersGuessed.setBounds(323, 428, 120, 20);
		contentPane.add(lblLettersGuessed);

		textAreaGuessed = new JTextArea();
		textAreaGuessed.setEnabled(false);
		textAreaGuessed.setBounds(270, 457, 215, 22);
		contentPane.add(textAreaGuessed);

		btnGuess = new JButton("Guess Letter");
		btnGuess.setBackground(new Color(0, 191, 255));
		btnGuess.setForeground(new Color(0, 0, 0));
		btnGuess.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnGuess.addActionListener(this);
		btnGuess.setBounds(52, 527, 328, 47);
		contentPane.add(btnGuess);

		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(
				"hangman1.gif"));
		lblNewLabel.setBounds(20, 44, 427, 301);
		contentPane.add(lblNewLabel);

		lblUnknownWord = new JLabel("");
		lblUnknownWord.setFont(new Font("Monospaced", Font.PLAIN, 15));
		lblUnknownWord.setBounds(52, 356, 530, 49);
		contentPane.add(lblUnknownWord);

		lblTriesRemaining = new JLabel("Tries Remaining:");
		lblTriesRemaining.setForeground(new Color(255, 255, 255));
		lblTriesRemaining.setFont(new Font("Monospaced", Font.PLAIN, 15));
		lblTriesRemaining.setBounds(457, 44, 182, 20);
		contentPane.add(lblTriesRemaining);

		lblName = new JLabel("Name:");
		lblName.setForeground(new Color(255, 255, 255));
		lblName.setFont(new Font("Monospaced", Font.PLAIN, 15));
		lblName.setBounds(20, 11, 126, 14);
		contentPane.add(lblName);

		lblTriesRemaining.setText("Tries Remaining:" + game.getTriesRemaining());
		setInvisible();
		
		if(score.getNumPlayers()<1) {//if
			scoreMenu.setEnabled(false);
		}//if
	}//constructor

	public void setInvisible() {//setInvisible
		lblName.setVisible(false);
		lblNewLabel.setVisible(false);
		lblTriesRemaining.setVisible(false);
		lblGuess.setVisible(false);
		lblLettersGuessed.setVisible(false);
		textFieldGuess.setVisible(false);
		textAreaGuessed.setVisible(false);
		btnGuess.setVisible(false);
	}//setInvisible

	public void setVisiblev() {//setVisiblev()
		lblName.setVisible(true);
		lblNewLabel.setVisible(true);
		lblTriesRemaining.setVisible(true);
		lblGuess.setVisible(true);
		lblLettersGuessed.setVisible(true);
		textFieldGuess.setVisible(true);
		textAreaGuessed.setVisible(true);
		btnGuess.setVisible(true);
	}//setVisiblev()

	@Override
	public void actionPerformed(ActionEvent e) {//actionPerformed
		// TODO Auto-generated method stub
		if (e.getSource() == mntmAbout) {//if
			JOptionPane.showMessageDialog(this,
					"Hello, and welcome to Charles-Etienne's Hangman game!\n To start, select the option to create a new game and enter your name or choose an existing player.\n\n The rules are simple, select a letter, and if the letter is in the word, it will show up, if not the tries will decrement. \nYou have 6 tries and if the counter hits zero, the game is lost",
					"About", JOptionPane.INFORMATION_MESSAGE);

		}//if
		if (e.getSource() == mntmNewGame) {//if
			JFrameNewPlayer newGame = new JFrameNewPlayer(this);
			newGame.setVisible(true);

		}//if
		if (e.getSource() == mntmScoreboard) {//if
			try {
				JFrameScoreboard score = new JFrameScoreboard(this);
				score.setVisible(true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			

		}//if
		if (e.getSource() == btnGuess) {//if

			lblTriesRemaining.setText("Tries Remaining:" + game.getTriesRemaining());
			if (textFieldGuess.getText().replace(" ", "").length() > 1) {//if
				JOptionPane.showMessageDialog(this, "You must enter only one letter", "Too many letters",
						JOptionPane.ERROR_MESSAGE);
			} //if
			else if (textFieldGuess.getText().replace(" ", "").length() == 0) {//if
				JOptionPane.showMessageDialog(this, "You must enter a letter", "You must enter a letter",
						JOptionPane.ERROR_MESSAGE);
			}//if
			else if(!Character.isAlphabetic(textFieldGuess.getText().charAt(0))) {//if
				JOptionPane.showMessageDialog(this, "The input must be a Letter",
						"Letter already guessed", JOptionPane.ERROR_MESSAGE);
			} //if
			else if (textAreaGuessed.getText().contains(textFieldGuess.getText().replace(" ", ""))) {//if
				JOptionPane.showMessageDialog(this, "The letter must not have already been guessed",
						"Letter already guessed", JOptionPane.ERROR_MESSAGE);
			}//if 
			else {//if
				textAreaGuessed.append(" " + textFieldGuess.getText().toLowerCase().charAt(0));
				if (game.selectLetter(textFieldGuess.getText().toLowerCase().charAt(0), false)) {//if

					lblUnknownWord.setText(game.getUnknownWord());
					if (game.isWinOrLost()) {//if
						score.getSelectedPlayer()
								.setNumberGamesPlayed(score.getSelectedPlayer().getNumberGamesPlayed() + 1);
						score.getSelectedPlayer().setListCounter(score.getSelectedPlayer().getListCounter() + 1);
						textAreaGuessed.setText("");
						if (game.getWinOrLost()) {//if
							score.getSelectedPlayer().setLettersGuessed(null);
							score.getSelectedPlayer()
									.setNumberGamesWon(score.getSelectedPlayer().getNumberGamesWon() + 1);
							score.getSelectedPlayer().setListCounter(score.getSelectedPlayer().getListCounter() + 1);
							JOptionPane.showMessageDialog(this, "You Won The Round :D", "Won!",
									JOptionPane.INFORMATION_MESSAGE);
							startGame();
						}//if

					}//if

				}//if 
				else {//else
					lblTriesRemaining.setText("Tries Remaining:" + game.getTriesRemaining());
					setImage(game.getTriesRemaining());
					if (game.isWinOrLost()) {//if
						score.getSelectedPlayer()
						.setNumberGamesPlayed(score.getSelectedPlayer().getNumberGamesPlayed() + 1);
						score.getSelectedPlayer().setListCounter(score.getSelectedPlayer().getListCounter() + 1);
						if (!game.getWinOrLost()) {//if
							lblUnknownWord.setText(game.getUnknownWord());
							textAreaGuessed.setText("");
							score.getSelectedPlayer().setLettersGuessed(null);
							JOptionPane.showMessageDialog(this, "You Lost The Round :( The answer was: "+game.getTheWordWDuplicates(), "lost",
									JOptionPane.ERROR_MESSAGE);
							startGame();
						}//if
					}//if
				}//else

			}//if
			for (int i = 0; i < score.getDifferentPlayers().getLength(); i++) {
				if (score.getDifferentPlayers().getElementAt(i).getName() == score.getSelectedPlayer().getName()
						&& score.getSelectedPlayer() != null) {//if
					score.getDifferentPlayers().find(i)
							.setElement(new Player(score.getSelectedPlayer().getName(),
									score.getSelectedPlayer().getNumberGamesPlayed(),
									score.getSelectedPlayer().getNumberGamesWon(), game.getLettersGuessed(),
									score.getSelectedPlayer().getWordArray(), game.getListCounter()));
					save.saveAllPlayers(score.getDifferentPlayers());
				}//if
			}

			textFieldGuess.setText("");
		}//if

		if (e.getSource() == mntmHint) {//if

			
			setImage(game.getListCounter());
			if (game.getTriesRemaining() == 1) {
				JOptionPane.showMessageDialog(this, "Cannot request an hint on last try", "Hint last try",
						JOptionPane.ERROR_MESSAGE);
			} 
			else if (game.getWord().getLength()==1) {
				JOptionPane.showMessageDialog(this, "Cannot request a hint if there is only one letter left", "Hint last Letter",
						JOptionPane.ERROR_MESSAGE);
			}
			else {
				lblTriesRemaining.setText("Tries Remaining:" + (game.getTriesRemaining()-1));
				setImage(game.getTriesRemaining()-1);
				game.getHint();
				textAreaGuessed.append(" " + game.getRandLet());
				lblUnknownWord.setText(game.getUnknownWord());

				if ((game.isWinOrLost())) {
					score.getSelectedPlayer()
							.setNumberGamesPlayed(score.getSelectedPlayer().getNumberGamesPlayed() + 1);
					score.getSelectedPlayer().setListCounter(score.getSelectedPlayer().getListCounter() + 1);
					textAreaGuessed.setText("");
					if (game.getWinOrLost()) {
						score.getSelectedPlayer().setNumberGamesWon(score.getSelectedPlayer().getNumberGamesWon() + 1);
						score.getSelectedPlayer().setListCounter(score.getSelectedPlayer().getListCounter() + 1);
						score.getSelectedPlayer().setLettersGuessed(null);
						JOptionPane.showMessageDialog(this, "You Won The Round :D", "Won!",
								JOptionPane.INFORMATION_MESSAGE);
						startGame();
					}
				}
			}
			for (int i = 0; i < score.getDifferentPlayers().getLength(); i++) {//for
				if (score.getDifferentPlayers().getElementAt(i).getName() == score.getSelectedPlayer().getName()
						&& score.getSelectedPlayer() != null) {//if
					score.getDifferentPlayers().find(i)
							.setElement(new Player(score.getSelectedPlayer().getName(),
									score.getSelectedPlayer().getNumberGamesPlayed(),
									score.getSelectedPlayer().getNumberGamesWon(), game.getLettersGuessed(),
									score.getSelectedPlayer().getWordArray(), game.getListCounter()));
					save.saveAllPlayers(score.getDifferentPlayers());
				}//if
			}//for

		}//if

	}//actionPerformed

	public void setLblName(String label) {//setLblName
		lblName.setText(label);
	}//setLblName

	public void startGame() {//startGame
	    try {//try
		scoreMenu.setEnabled(true);
		hintMenu.setEnabled(true);
		btnGuess.setEnabled(true);
    	hintMenu.setEnabled(true);
    	textFieldGuess.setEnabled(true);
		game.setTriesRemaining(6);
		textAreaGuessed.setText("");
		game.setListCounter(score.getSelectedPlayer().getListCounter());
		
		game.setWord(score.getSelectedPlayer().getWordArray());
		if (score.getSelectedPlayer().getLettersGuessed() != null) {//if
			game.setLettersGuessed(score.getSelectedPlayer().getLettersGuessed());
			int size = game.getLettersGuessed().getLength();
			for (int i = 0; i < size; ++i) {//for
				game.selectLetter(game.getLettersGuessed().getElementAt(i), true);
				textAreaGuessed.append(game.getLettersGuessed().getElementAt(i).toString());

			}//for
		}//if

		lblTriesRemaining.setText("Tries Remaining:" + game.getTriesRemaining());
		setImage(game.getTriesRemaining());
		lblUnknownWord.setText(game.getUnknownWord());
	    }//try
	    catch(Exception e) {//catch
	    	JOptionPane.showMessageDialog(this, "The List no-longer has any words, Please create a new game", "Game Over!",
					JOptionPane.INFORMATION_MESSAGE);
	    	btnGuess.setEnabled(false);
	    	hintMenu.setEnabled(false);
	    	textFieldGuess.setEnabled(false);
	    }//catch

	}//startGame
public void setImage(int triesRemaining) {
	switch(triesRemaining) {
	case 6:
		lblNewLabel.setIcon(new ImageIcon(
				"hangman1.gif"));
		break;
	case 5:
		lblNewLabel.setIcon(new ImageIcon(
				"hangman2.gif"));
		break;
	case 4:
		lblNewLabel.setIcon(new ImageIcon(
				"hangman3.gif"));
		break;
	case 3:
		lblNewLabel.setIcon(new ImageIcon(
				"hangman4.gif"));
		break;
	case 2:
		lblNewLabel.setIcon(new ImageIcon(
				"hangman5.gif"));
		break;
	case 1:
		lblNewLabel.setIcon(new ImageIcon(
				"hangman6.gif"));
		break;
	}
}
}//HangmanFrame 
