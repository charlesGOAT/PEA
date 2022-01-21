package linked_data_structures;

import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.*;
import java.lang.reflect.Array;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.io.*;

public class JFrameNewPlayer extends JFrame implements ActionListener, Serializable {// JFrameNewPlayer Is used to display the different players and user can select from. Author, Charles-Etienne Gauthier

	private JPanel contentPane;
	private JTextField textFieldName;
	private JLabel lblPlayerName;
	private JLabel lblExistingPlayer;
	private JComboBox comboBoxExistingPlayer;
	private JButton btnNewButton;
	private JLabel lblNewPlayer;
	ScoreBoard score = new ScoreBoard();
	Game game = new Game();
	SaveGame save = new SaveGame();
	HangmanFrame hangman = new HangmanFrame();

	/**
	 * Launch the application.
	 * 
	 * @throws Exception
	 */
	private void initialize() throws Exception {//initialize
		setTitle("Player Name");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblPlayerName = new JLabel("Enter player name:");
		lblPlayerName.setFont(new Font("Monospaced", Font.PLAIN, 13));
		lblPlayerName.setBounds(41, 81, 168, 28);
		contentPane.add(lblPlayerName);

		textFieldName = new JTextField();
		textFieldName.setBounds(190, 86, 131, 20);
		contentPane.add(textFieldName);
		textFieldName.setColumns(25);

		lblExistingPlayer = new JLabel("Existing Player?");
		lblExistingPlayer.setFont(new Font("Monospaced", Font.PLAIN, 13));
		lblExistingPlayer.setBounds(41, 155, 168, 28);
		contentPane.add(lblExistingPlayer);

		comboBoxExistingPlayer = new JComboBox();
		comboBoxExistingPlayer.setBounds(190, 159, 131, 22);
		contentPane.add(comboBoxExistingPlayer);
		comboBoxExistingPlayer.addActionListener(this);

		btnNewButton = new JButton("Play!");
		btnNewButton.setBounds(126, 210, 195, 40);
		contentPane.add(btnNewButton);

		lblNewPlayer = new JLabel("New Player");
		lblNewPlayer.setFont(new Font("Monospaced", Font.PLAIN, 20));
		lblNewPlayer.setBounds(163, 11, 158, 28);
		contentPane.add(lblNewPlayer);
		btnNewButton.addActionListener(this);
		try {//try
			loadGame();
		}//try 
		catch (Exception e) {//catch
		}//catch

		comboBoxExistingPlayer.addItem("");
		for (int i = 0; i < score.getDifferentPlayers().getLength(); ++i) {//for
			comboBoxExistingPlayer.addItem(score.getDifferentPlayers().getElementAt(i).getName());

		}//for
		comboBoxExistingPlayer.addActionListener(this);
	}//initialize

	/**
	 * Create the frame.
	 */
	public JFrameNewPlayer() {//constructor
		try {//try
			initialize();
		}//try 
		catch (Exception e) {//catch
			// TODO Auto-generated catch block
			
		}//catch
	}//constructor

	public JFrameNewPlayer(HangmanFrame hangman) {//constructor
		this.hangman = hangman;
		try {//try
			initialize();
		}//try 
		catch (Exception e) {//catch
			// TODO Auto-generated catch block
			
		}//catch
	}//constructor
	
	public void loadGame() throws Exception {//loadGame()
		FileInputStream inStream = new FileInputStream("scoreBoard.ser");
		ObjectInputStream objectInputFile = new ObjectInputStream(inStream);
		score.setDifferentPlayers((DoublyLinkedList) objectInputFile.readObject());
		inStream.close();
		objectInputFile.close();
	}//loadGame()

	@Override
	public void actionPerformed(ActionEvent e) {//actionPerformed
		// TODO Auto-generated method stub
		if (e.getSource() == btnNewButton) {//if
			ArrayList<String> nameArray = new ArrayList<String>();
			for (int i = 1; i < comboBoxExistingPlayer.getItemCount(); ++i) {//for
				nameArray.add((String) comboBoxExistingPlayer.getItemAt(i));
			}//for

			if (textFieldName.getText().replace(" ", "").equals("")
					&& comboBoxExistingPlayer.getSelectedItem().toString().equals("")) {
				JOptionPane.showMessageDialog(this, "You must enter a valid name for the player", "Title missing",
						JOptionPane.ERROR_MESSAGE);

			}//if
			else if(textFieldName.getText().replace(" ", "").length()>10) {//else if
				JOptionPane.showMessageDialog(this, "The Name of the player is too big",
					"Long Name", JOptionPane.ERROR_MESSAGE);
				}//else if 
			else if (nameArray.contains(textFieldName.getText().replace(" ", ""))) {//else if
				JOptionPane.showMessageDialog(this, "A new Player cannot have the same name as an existing player",
						"Same Name", JOptionPane.ERROR_MESSAGE);
			}//else if 
			else if (!comboBoxExistingPlayer.getSelectedItem().toString().equals("")
					&& !textFieldName.getText().replace(" ", "").equals("")) {//else if
				JOptionPane.showMessageDialog(this, "Cannot select both an existing player and new Player",
						"Title missing", JOptionPane.ERROR_MESSAGE);
			}//else if 
			else if (!comboBoxExistingPlayer.getSelectedItem().toString().equals("")) {
				for (int i = 0; i < score.getDifferentPlayers().getLength(); ++i) {
					if ((String) comboBoxExistingPlayer.getSelectedItem() == score.getDifferentPlayers().getElementAt(i)
							.getName()) {//if
						ScoreBoard.setSelectedPlayer(score.getDifferentPlayers().getElementAt(i));

					}//if
				}//for
				hangman.setLblName(comboBoxExistingPlayer.getSelectedItem().toString());
			}//else if 
			else {//else
				hangman.setLblName(textFieldName.getText());
				Player newPlayer = new Player(textFieldName.getText(), 0, 0);
				score.getDifferentPlayers().add(newPlayer);
				save.saveAllPlayers(score.getDifferentPlayers());
				ScoreBoard.setSelectedPlayer(newPlayer);
			
			}//else
			hangman.startGame();
			hangman.setVisible(true);
			hangman.setVisiblev();
			this.setVisible(false);
		}//if
		
	}//actionPerformed
}// JFrameNewPlayer Is used to
