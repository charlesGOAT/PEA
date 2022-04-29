package linked_data_structures;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GameTest {

	@Test
	void test() {// prove that the hint decrements the tries remaining
		String word = "Yolo";
		SinglyLinkedList<String> theWord = new SinglyLinkedList<String>();
		theWord.add(word);
		Game game = new Game();
		game.setTriesRemaining(6);
		game.setWord(theWord);
		game.getHint();
		assertEquals(true, game.getTriesRemaining() == 5);
	}

	@Test
	void test2() {// prove that when you guess a letter that is contained two times in the same
					// word, it will guess the two.  
		String word = "Yolo";
		SinglyLinkedList<String> theWord = new SinglyLinkedList<String>();
		theWord.add(word);
		Game game = new Game();
		game.setTriesRemaining(6);
		game.setWord(theWord);
		game.selectLetter('o',false);
		assertEquals(true, game.getWord().getLength()==2);
	}

	@Test
	void test3() {// prove that when you guess a letter 2 times, it is added to the array lettersGuessed only once
		String word = "Yolo";
		SinglyLinkedList<String> theWord = new SinglyLinkedList<String>();
		theWord.add(word);
		Game game = new Game();
		game.setTriesRemaining(6);
		game.setWord(theWord);
		game.selectLetter('o',false);
		game.selectLetter('o',true);
		assertEquals(true, game.getLettersGuessed().getLength()==1);
	}
	@Test
	void test4() {// prove that when you guess a letter that's not in the word, the tries remaining decrement
		String word = "Yolo";
		SinglyLinkedList<String> theWord = new SinglyLinkedList<String>();
		theWord.add(word);
		Game game = new Game();
		game.setTriesRemaining(6);
		game.setWord(theWord);
		game.selectLetter('U',false);
		assertEquals(true, game.getTriesRemaining()==5);
	}
	@Test
	void test5() {// prove that when you guess a letter, you need to guess all letters to win
		String word = "Hello Richard!";
		SinglyLinkedList<String> theWord = new SinglyLinkedList<String>();
		theWord.add(word);
		Game game = new Game();
		game.setTriesRemaining(6);
		game.setWord(theWord);
		game.selectLetter('o',false);
		assertEquals(true, game.isWinOrLost()==false);
	}
	@Test
	void test6() {// prove that when you guess a letter, you need to guess all letters to win
		String word = "Hello";
		SinglyLinkedList<String> theWord = new SinglyLinkedList<String>();
		theWord.add(word);
		Game game = new Game();
		game.setTriesRemaining(6);
		game.setWord(theWord);
		game.selectLetter('o',false);
		game.selectLetter('h',false);
		game.selectLetter('l',false);
		game.selectLetter('e',false);
		assertEquals(true, game.isWinOrLost()==true);
	}
	@Test
	void test7() {// prove that when the tries remaining exceeds the limit, the game is lost
		String word = "yuitvzjg";
		SinglyLinkedList<String> theWord = new SinglyLinkedList<String>();
		theWord.add(word);
		Game game = new Game();
		game.setTriesRemaining(6);
		game.setWord(theWord);
		game.selectLetter('o',false);
		game.selectLetter('h',false);
		game.selectLetter('l',false);
		game.selectLetter('e',false);
		game.selectLetter('k',false);
		game.selectLetter('b',false);
		assertEquals(true, game.isWinOrLost()==true);
		assertEquals(true, game.getWinOrLost()==false);
	}
	
	
}
