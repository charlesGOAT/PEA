package cgauthier_G20_A02_Phase2;

import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;

public class GameReader {// class
	ConnectNGame game = new ConnectNGame();
	public String row;
	public String col;
	public String check;
	public String playerN1;
	public String playerN2;
	public String turn;
	public String line;
	public char savedBoard[][];
	public StringTokenizer delimeters;
	public GameSaver saver = new GameSaver();

	public GameReader() {
		// TODO Auto-generated constructor stub
	}

	public boolean ReadFromFile() {// ReadFromFile()

		try {// try
			File myObj = new File(saver.informationFileName);
			Scanner myReader = new Scanner(myObj);
			line = myReader.nextLine();
			delimeters = new StringTokenizer(line, "~\n");
			row = delimeters.nextToken();
			game.setRows(Integer.parseInt(row));
			line = myReader.nextLine();
			delimeters = new StringTokenizer(line, "~\n");
			col = delimeters.nextToken();
			game.setCol(Integer.parseInt(col));
			line = myReader.nextLine();
			delimeters = new StringTokenizer(line, "~\n");
			check = delimeters.nextToken();
			game.setChecks(Integer.parseInt(check));
			line = myReader.nextLine();
			delimeters = new StringTokenizer(line, "~\n");
			playerN1 = delimeters.nextToken();
			game.setPlayerOne(playerN1);
			line = myReader.nextLine();
			delimeters = new StringTokenizer(line, "~\n");
			playerN2 = delimeters.nextToken();
			game.setPlayerTwo(playerN2);
			line = myReader.nextLine();
			delimeters = new StringTokenizer(line, "~\n");
			turn = delimeters.nextToken();
			game.setCurrentPlayer(Integer.parseInt(turn));
			savedBoard = new char[Integer.parseInt(row)][Integer.parseInt(col)];

			for (int i = 0; i < game.getRows(); ++i) {// for
				line = myReader.nextLine();
				delimeters = new StringTokenizer(line, "~\n");
				for (int w = 0; w < game.getColumns(); ++w) {// for
					savedBoard[i][w] = delimeters.nextToken().charAt(0);

				} // for
			} // for

			game.setBoard(savedBoard);
			myReader.close();
			return true;

		} // try

		catch (Exception e) {// catch
			System.out.println("An error occurred.");
			e.printStackTrace();
			return false;
		} // catch

	} // ReadFromFile()
}// end of class
