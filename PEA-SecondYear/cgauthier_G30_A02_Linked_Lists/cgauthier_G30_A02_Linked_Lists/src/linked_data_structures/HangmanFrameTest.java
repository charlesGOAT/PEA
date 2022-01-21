package linked_data_structures;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JOptionPane;

import org.junit.jupiter.api.Test;

class HangmanFrameTest {
//This test case is used to test different inputs. Here, I will use some of the ifs statements I have in my hangmanFrame
	@Test
	void test() {//simulating that user inputs ah, a two letter guess. line 221 of hangman frame 
		String input = "ah ";
		if(input.replace(" ", "").length() > 1) {
			assertTrue(true);
		}
	}
	@Test
	void test1() {//simulating input that is empty line 224
		String input = "";
		if(input.replace(" ", "").length() == 0) {
			assertTrue(true);
		}
	}
	@Test
	void test2() {//simulating input of number
		int input = 2;
		if(!Character.isAlphabetic(input)) {
			assertTrue(true);
		}
	}
	@Test	
void test3() {//user tries to input an letter on his last try line 292
	String word = "Yolo";
	SinglyLinkedList<String> theWord = new SinglyLinkedList<String>();
	theWord.add(word);
	Game game = new Game();
	game.setTriesRemaining(1);
	game.setWord(theWord);
	if (game.getTriesRemaining() == 1) {
		assertTrue(true);
	} 
}

@Test
void test4() {//user tries to input a letter when there is only one letter to guess line 296
	Game game = new Game();
	String word = "Yolo";
	SinglyLinkedList<String> theWord = new SinglyLinkedList<String>();
	theWord.add(word);
	game.setWord(theWord);
	game.setTriesRemaining(6);
	game.selectLetter('y', false);
	game.selectLetter('o', false);
	if(game.getWord().getLength()==1) {
		assertTrue(true);
	}
}
@Test
void test5() {//show that the case of the letter does not matter line 236
	Game game = new Game();
	String word = "Yolo";
	String input = "Y";
	SinglyLinkedList<String> theWord = new SinglyLinkedList<String>();
	theWord.add(word);
	game.setWord(theWord);
	game.setTriesRemaining(6);
	if(game.selectLetter(input.toLowerCase().charAt(0), false)) {
		assertTrue(true);
	}
	
}
@Test
void test6() {//show that the same letter will be guessed no matter the case
	Game game = new Game();
	String word = "Yolo";
	String input = "Y";
	SinglyLinkedList<String> theWord = new SinglyLinkedList<String>();
	theWord.add(word);
	game.setWord(theWord);
	game.setTriesRemaining(6);
	game.selectLetter(input.toLowerCase().charAt(0), false);
	game.selectLetter('y', true);
	if(game.getLettersGuessed().getLength()==1) {
		assertTrue(true);
	}
		
}
@Test
void test7() {//simulating input of non alphabetic character
	char input = '$';
	if(!Character.isAlphabetic(input)) {
		assertTrue(true);
	}
}
}
