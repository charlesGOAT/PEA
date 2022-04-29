package linked_data_structures;

import java.io.*;

public class Player implements Serializable {//Player Author, Charles-Etienne Gauthier
	private String name;
	private int numberGamesPlayed;
	private int numberGamesWon;
	private DoublyLinkedList<Player> differentPlayers = new DoublyLinkedList<Player>();
	private SinglyLinkedList<String> wordArray = new SinglyLinkedList<String>();
	private String word;

	private SinglyLinkedList<Character> lettersGuessed;
	private int listCounter;
	private DictionaryReader reader = new DictionaryReader();
	private Game game = new Game();

	public SinglyLinkedList<String> getWordArray() {//getWordArray
		return wordArray;
	}//getWordArray

	public void setWordArray(SinglyLinkedList<String> wordArray) {//setWordArray
		this.wordArray = wordArray;
	}//setWordArray

	public String getWord() {//getWord
		return word;
	}//getWord

	public void setWord(String word) {//setWord
		this.word = word;
	}//setWord

	


	public SinglyLinkedList<Character> getLettersGuessed() {//getLettersGuessed
		return lettersGuessed;
	}//getLettersGuessed

	public void setLettersGuessed(SinglyLinkedList<Character> lettersGuessed) {//setLettersGuessed
		this.lettersGuessed = lettersGuessed;
	}//setLettersGuessed

	public int getListCounter() {//getListCounter
		return listCounter;
	}//getListCounter

	public void setListCounter(int listCounter) {//setListCounter(int listCounter)
		this.listCounter = listCounter;
	}//setListCounter(int listCounter)

	public Player() {//constructor
		name = "unknown";
		numberGamesPlayed = 0;
		numberGamesWon = 0;

	}//constructor

	public Player(String theName) {//constructor
		name = theName;
		numberGamesPlayed = 0;
		numberGamesWon = 0;
	}//constructor

	public Player(String theName, int numGames) {//constructor
		name = theName;
		numberGamesPlayed = numGames;
		numberGamesWon = 0;
	}//constructor

	public Player(String theName, int numGames, int gamesWon) {//constructor
		name = theName;
		numberGamesPlayed = numGames;
		numberGamesWon = gamesWon;
		try {//try
			reader.readFromFile();
			game.setDictionaryNodes(reader.getAllWords());
			wordArray = game.getDictionaryNodes();
			
			this.listCounter=0;
game.setListCounter(listCounter);
		}//try 
		catch (IOException e) {//catch
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//catch

	}//constructor

	public Player(String theName, int numGames, int gamesWon,
			SinglyLinkedList<Character> lettersGuessed, SinglyLinkedList<String> wordArray, int listCounter) {//constructor

		name = theName;
		numberGamesPlayed = numGames;
		numberGamesWon = gamesWon;
		
		
		this.lettersGuessed = lettersGuessed;
		game.setLettersGuessed(this.lettersGuessed);
		this.wordArray = wordArray;
		game.setDictionaryNodes(wordArray);
		this.listCounter = listCounter;
		game.setListCounter(this.listCounter);
	}//constructor

	public DoublyLinkedList<Player> getDifferentPlayers() {//getDifferentPlayers
		return differentPlayers;
	}//getDifferentPlayers

	public void setDifferentPlayers(DoublyLinkedList<Player> differentPlayers) {//setDifferentPlayers(DoublyLinkedList<Player> differentPlayers)
		this.differentPlayers = differentPlayers;
	}//setDifferentPlayers(DoublyLinkedList<Player> differentPlayers)

	public String getName() {//getName
		return name;
	}//getName

	public void setName(String theName) {//setName
		name = theName;
	}//setName

	public int getNumberGamesPlayed() {//getNumberGamesPlayed
		return numberGamesPlayed;
	}//getNumberGamesPlayed

	public void setNumberGamesPlayed(int numberGamesPlayed) {//setNumberGamesWon
		this.numberGamesPlayed = numberGamesPlayed;
	}//setNumberGamesWon

	public int getNumberGamesWon() {//getNumberGamesWon
		return numberGamesWon;
	}//getNumberGamesWon

	public void setNumberGamesWon(int numberGamesWon) {//setNumberGamesWon
		this.numberGamesWon = numberGamesWon;
	}//setNumberGamesWon

}//player
