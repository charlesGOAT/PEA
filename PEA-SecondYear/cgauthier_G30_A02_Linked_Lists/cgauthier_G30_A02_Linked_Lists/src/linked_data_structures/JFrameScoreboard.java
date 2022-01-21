package linked_data_structures;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

public class JFrameScoreboard extends JFrame implements Serializable {//JFrameScoreboard. This frame is used to see the scoreboard Author, Charles-Etienne Gauthier
	private HangmanFrame hang;
	private JPanel contentPane;
	private JPanel panel;
	private JLabel scoreboard;
	private JTextArea textArea;
	private ScoreBoard score = new ScoreBoard();

	public void initialize() throws Exception {//initialize
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 727, 649);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBounds(10, 11, 691, 588);
		contentPane.add(panel);
		panel.setLayout(null);

		scoreboard = new JLabel("Scoreboard");
		scoreboard.setBounds(300, 26, 107, 14);
		panel.add(scoreboard);
		scoreboard.setFont(new Font("Monospaced", Font.PLAIN, 16));

		textArea = new JTextArea();
		textArea.setBounds(10, 51, 671, 526);
		panel.add(textArea);
		loadGame();
		
		textArea.append("Name"+"\t"+"Games Played"+"\t"+ "Games Won" +"\t"+"Games lost"+"\n");
		score.sort();
		for (int i = 0; i < score.getDifferentPlayers().getLength(); ++i) {//for
			
			textArea.append("\n");
			
			textArea.append( score.getDifferentPlayers().getElementAt(i).getName()+"\t"+
					score.getDifferentPlayers().getElementAt(i).getNumberGamesPlayed()+"\t"+
					score.getDifferentPlayers().getElementAt(i).getNumberGamesWon()+"\t"+
					(score.getDifferentPlayers().getElementAt(i).getNumberGamesPlayed()
							- score.getDifferentPlayers().getElementAt(i).getNumberGamesWon()));
			textArea.append("\n");

		}//for

	}//initialize

	public void loadGame() throws Exception {//loadGame
		FileInputStream inStream = new FileInputStream("scoreBoard.ser");
		ObjectInputStream objectInputFile = new ObjectInputStream(inStream);
		score.setDifferentPlayers((DoublyLinkedList) objectInputFile.readObject());
		inStream.close();
		objectInputFile.close();
	}//loadGame

	/**
	 * Create the frame.
	 * 
	 * @throws Exception
	 */
	public JFrameScoreboard() throws Exception {//constructor
		initialize();
	}//constructor

	public JFrameScoreboard(HangmanFrame hangman) throws Exception {//constructor
		hang = hangman;
		initialize();
	}//constructor
}//JFrameScoreboard. This frame is used to see the scoreboard
