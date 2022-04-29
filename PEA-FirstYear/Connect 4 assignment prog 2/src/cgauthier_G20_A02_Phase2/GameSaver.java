package cgauthier_G20_A02_Phase2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GameSaver {
	String informationFileName = "currentGame.txt";
	private FileWriter informationWriter;
	public ConnectNGame gameObject = new ConnectNGame();

	public void open() {// open

		File infoFile = new File(informationFileName);
		try {// try
			informationWriter = new FileWriter(infoFile);
		} catch (IOException e) {
			System.out.println("ERROR: File " + informationFileName + "could not opened: " + e.getMessage());
		} // catch
			// try
	}// open

	public boolean saveGame() {
		try {// try
				// start of if statement
			informationWriter.write(gameObject.getRows() + "\n" + gameObject.getColumns() + "\n"
					+ gameObject.getChecks() + "\n" + gameObject.getPlayerOne() + "\n" + gameObject.getPlayerTwo()
					+ "\n" + gameObject.getCurrentPlayer() + "\n");

			for (int i = 0; i < gameObject.getBoard().length; ++i) {
				for (int w = 0; w < gameObject.getBoard()[i].length; ++w) {
					if (w == gameObject.getBoard().length) {
						informationWriter.write(gameObject.getBoard()[i][w]);
					} else
						informationWriter.write(gameObject.getBoard()[i][w] + "~");
				}
				informationWriter.write("\n");
			}
			return true;
			// end of if statement

		} // try
		catch (IOException e) {
			System.out.println("ERROR: Save didn't work ");
			return false;
		} // catch

	}

	public void close() {// close
		try {// try
			informationWriter.close();
		} // try
		catch (IOException e) {// catch
			System.out.println("ERROR: File " + informationFileName + "could not closed: " + e.getMessage());
		} // catch
	}// end of close
}