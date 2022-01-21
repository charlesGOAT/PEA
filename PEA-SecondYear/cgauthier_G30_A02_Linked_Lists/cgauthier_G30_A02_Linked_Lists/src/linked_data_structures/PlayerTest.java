package linked_data_structures;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {

	@Test
	void test() {
		Player player1 = new Player();
		assertEquals(true, player1.getName() == "unknown");//testing if the default name is unknown
		assertEquals(true, player1.getNumberGamesPlayed() == 0 && player1.getNumberGamesWon() == 0);//testing if the default value is 0
	
	}
@Test
void test2() {
	Player player1 = new Player("Yoooo",1,0);
	assertEquals(true, player1.getName() == "Yoooo");//testing if the default name is unknown
	assertEquals(true, player1.getNumberGamesPlayed() == 1 && player1.getNumberGamesWon() == 0);
}

}
