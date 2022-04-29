package cgauthier_G20_A02_Phase2;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ConnectNFrame extends JFrame implements ActionListener {//beginning of class
//these variables are for the frame.
	ConnectNGame game = new ConnectNGame();
BoardFrame boardFrame;
static boolean counter;	
private JPanel contentPane;
public JTextField fldPlayer1;
	public JTextField fldPlayer2;
	public JSlider sldRows;
	public JSlider sldColumns;
	public JSlider sldChecks;
	private JLabel lblRows;
	private JLabel lblColumns;
	private JLabel lblChecks;
	private JLabel lblPlayer1;
	private JLabel lblPlayer2;
	private JButton btnStartGame;
	private JButton btnNewGame;
	private JPanel boardContent;
	
	
	private void initialize()
	{//initialize method. This initializes the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 495, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblRows = new JLabel("Enter The Number Of Rows");
		lblRows.setEnabled(false);
		lblRows.setBackground(Color.LIGHT_GRAY);
		lblRows.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
		lblRows.setBounds(48, 67, 194, 40);
		contentPane.add(lblRows);
		
		lblColumns = new JLabel("Enter The Number Of Columns");
		lblColumns.setEnabled(false);
		lblColumns.setBackground(Color.LIGHT_GRAY);
		lblColumns.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
		lblColumns.setBounds(23, 130, 221, 26);
		contentPane.add(lblColumns);
		
		sldColumns = new JSlider();
		sldColumns.setEnabled(false);
		sldColumns.setMajorTickSpacing(2);
		sldColumns.setForeground(Color.BLACK);
		sldColumns.setPaintTicks(true);
		sldColumns.setPaintLabels(true);
		sldColumns.setBackground(Color.LIGHT_GRAY);
		sldColumns.setToolTipText("");
		sldColumns.setMinorTickSpacing(1);
		sldColumns.setValue(8);
		sldColumns.setMinimum(4);
		sldColumns.setMaximum(12);
		sldColumns.setBounds(253, 122, 134, 45);
		contentPane.add(sldColumns);
		
		sldRows = new JSlider();
		sldRows.setEnabled(false);
		sldRows.setMajorTickSpacing(2);
		sldRows.setPaintTicks(true);
		sldRows.setPaintLabels(true);
		sldRows.setForeground(Color.BLACK);
		sldRows.setBackground(Color.LIGHT_GRAY);
		sldRows.setMinorTickSpacing(1);
		sldRows.setValue(8);
		sldRows.setMinimum(4);
		sldRows.setMaximum(12);
		sldRows.setBounds(252, 55, 133, 52);
		contentPane.add(sldRows);
		
		sldChecks = new JSlider();
		sldChecks.setEnabled(false);
		sldChecks.setPaintLabels(true);
		sldChecks.setPaintTicks(true);
		sldChecks.setMajorTickSpacing(4);
		sldChecks.setMinorTickSpacing(1);
		sldChecks.setValue(6);
		sldChecks.setMinimum(3);
		sldChecks.setMaximum(8);
		sldChecks.setBackground(Color.LIGHT_GRAY);
		sldChecks.setForeground(Color.BLACK);
		sldChecks.setBounds(254, 179, 134, 52);
		contentPane.add(sldChecks);
		
		lblChecks = new JLabel("Enter The Number Of Checks");
		lblChecks.setEnabled(false);
		lblChecks.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
		lblChecks.setBounds(37, 192, 207, 26);
		contentPane.add(lblChecks);
		
		lblPlayer1 = new JLabel("Player 1's name");
		lblPlayer1.setEnabled(false);
		lblPlayer1.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
		lblPlayer1.setBounds(5, 242, 109, 14);
		contentPane.add(lblPlayer1);
		
		fldPlayer1 = new JTextField();
		fldPlayer1.setEnabled(false);
		fldPlayer1.setBounds(124, 240, 91, 20);
		contentPane.add(fldPlayer1);
		fldPlayer1.setColumns(10);
		
		lblPlayer2 = new JLabel("Player 2's name");
		lblPlayer2.setEnabled(false);
		lblPlayer2.setFont(new Font("Yu Gothic", Font.PLAIN, 15));
		lblPlayer2.setBounds(243, 242, 122, 14);
		contentPane.add(lblPlayer2);
		
		fldPlayer2 = new JTextField();
		fldPlayer2.setEnabled(false);
		fldPlayer2.setBounds(365, 240, 104, 20);
		contentPane.add(fldPlayer2);
		fldPlayer2.setColumns(10);
		
		btnStartGame = new JButton("Start Game!");
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnStartGame_actionPerformed();
				
			}
		});
		btnStartGame.setEnabled(false);
		btnStartGame.setBounds(184, 288, 122, 23);
		contentPane.add(btnStartGame);
		
		btnNewGame = new JButton("New Game");
		btnNewGame.setBounds(172, 11, 134, 23);
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnNewGame_actionPerformed();
			}
		});
		contentPane.add(btnNewGame);
	}//end of initialize
	
	public ConnectNFrame() {//default constructor
		
		initialize();
	}//end default constructor

	public ConnectNFrame(BoardFrame aBoardFrame) {//constructor, this calls the initialize method sets a boardFrame
		boardFrame = aBoardFrame;
		initialize();
	}//end of constructor
	
	
	private void btnNewGame_actionPerformed() {//beginning of btnNewGame_actionPerformed. This enables the player inputs
		
		enableFields(true);
	}//end of actionPerformed
	private void btnStartGame_actionPerformed() {//beginning of the btnStartGame_actionPerformed
	if(fldPlayer1.getText().trim().isEmpty()||fldPlayer1.getText().trim().equals(fldPlayer2.getText())) {//if statement. this confirms the player input
		JOptionPane.showMessageDialog(this, "You must enter a valid name for Player 1 (note: the two names cannot be the same)",
				"Title missing", JOptionPane.ERROR_MESSAGE);
	}	//end of if statement
	else if(fldPlayer2.getText().trim().isEmpty()||fldPlayer1.getText().equals(fldPlayer2.getText())) {//else if statement. this confirms the player2 input
		JOptionPane.showMessageDialog(this, "You must enter a valid name for Player 2 (note: the two names cannot be the same)",
				"Title missing", JOptionPane.ERROR_MESSAGE);
	}	//end of else if statement
	else if(sldRows.getValue()<sldChecks.getValue()||sldColumns.getValue()<sldChecks.getValue()) {//confirms the checks
		JOptionPane.showMessageDialog(this, "You must enter a valid number of checks (has to be smaller than the number of rows and colomns or equal )",
				"Check error", JOptionPane.ERROR_MESSAGE);
	}//end of else if
	else{//if all things are correct, the values are set
		game.setCol(sldRows.getValue());
		game.setRows(sldColumns.getValue());
		game.setChecks(sldChecks.getValue());
		game.setPlayerOne(fldPlayer1.getText());
		game.setPlayerTwo(fldPlayer2.getText());
		boardFrame.initializeBoard();	
		boardFrame.setVisible(true);
		this.setVisible(false);

	}//end of else
	}//end of the btnStartGame_actionPerformed
	
	private void enableFields(boolean enabled) {// enablePersonalFields this enables the fields when you click on the new game button
		lblRows.setEnabled(enabled);
		lblColumns.setEnabled(enabled);
		lblPlayer1.setEnabled(enabled);
		lblPlayer2.setEnabled(enabled);
		lblChecks.setEnabled(enabled);
		sldChecks.setEnabled(enabled);
		sldRows.setEnabled(enabled);
		sldColumns.setEnabled(enabled);
		fldPlayer2.setEnabled(enabled);
		fldPlayer1.setEnabled(enabled);
		btnStartGame.setEnabled(enabled);
	}// end of enablePersonalFields

public void setCounter(boolean count) {//beginning of set counter
	counter=count;
}//end of setCounter
public static boolean getCounter() {//beginning of get counter
	return counter;
}//end of getCounter


@Override
public void actionPerformed(ActionEvent e) {//default actionPerformed
	// TODO Auto-generated method stub
	
}//end of default actionPerformed

}// end class
