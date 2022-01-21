package linked_data_structures;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.junit.jupiter.api.Test;

class ScoreBoardTest {

	@Test//test if the serialization actually works
	void test() throws Exception {
		ScoreBoard score = new ScoreBoard();
		FileInputStream inStream = new FileInputStream("scoreBoard.ser");
		ObjectInputStream objectInputFile = new ObjectInputStream(inStream);
		score.setDifferentPlayers((DoublyLinkedList) objectInputFile.readObject());
		inStream.close();
		objectInputFile.close();
		
		assertEquals(true,score.getDifferentPlayers()!=null);
		
	}
	@Test 
	void test2() throws Exception{//test to see if the game can get the names and they are not just equal to nothing
		ScoreBoard score = new ScoreBoard();
		FileInputStream inStream = new FileInputStream("scoreBoard.ser");
		ObjectInputStream objectInputFile = new ObjectInputStream(inStream);
		score.setDifferentPlayers((DoublyLinkedList) objectInputFile.readObject());
		inStream.close();
		objectInputFile.close();
		score.setSelectedPlayer(score.getDifferentPlayers().getElementAt(0));
		
		assertEquals(true,score.getSelectedPlayer().getName()!=null);
	}

	@Test 
	void test3() throws Exception{//test to see if the game can deserialize the amount of wins a player has
		ScoreBoard score = new ScoreBoard();
		FileInputStream inStream = new FileInputStream("scoreBoard.ser");
		ObjectInputStream objectInputFile = new ObjectInputStream(inStream);
		score.setDifferentPlayers((DoublyLinkedList) objectInputFile.readObject());
		inStream.close();
		objectInputFile.close();
		score.setSelectedPlayer(score.getDifferentPlayers().getElementAt(0));
		
		assertEquals(true,(Integer)score.getSelectedPlayer().getNumberGamesPlayed()!=null);
	}
	
	@Test
	void test4() throws Exception{
		ScoreBoard score = new ScoreBoard();
		FileInputStream inStream = new FileInputStream("scoreBoard.ser");
		ObjectInputStream objectInputFile = new ObjectInputStream(inStream);
		score.setDifferentPlayers((DoublyLinkedList) objectInputFile.readObject());
		inStream.close();
		objectInputFile.close();
		score.sort();
		
		assertEquals(true,score.getDifferentPlayers().getElementAt(0).getName().compareTo(score.getDifferentPlayers().getElementAt(1).getName())<0);
		
	}
}
