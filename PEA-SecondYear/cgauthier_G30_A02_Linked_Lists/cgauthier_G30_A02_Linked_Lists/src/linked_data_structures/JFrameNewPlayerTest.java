package linked_data_structures;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

import java.util.*;

class JFrameNewPlayerTest {//this will test some of the methods and if statements from JFrameNewPlayerTest

	@Test
	void test() {
		ScoreBoard score = new ScoreBoard();
		score.getDifferentPlayers().add(new Player("Bruh"));
		String input = "Bruh";
		ArrayList<String> nameArray = new ArrayList<String>();
		for(int i = 0; i<score.getDifferentPlayers().getLength();++i) {
			nameArray.add(score.getDifferentPlayers().getElementAt(i).getName());
		}
		assertEquals(true,nameArray.contains(input));
	}//ask for case tmr

}
