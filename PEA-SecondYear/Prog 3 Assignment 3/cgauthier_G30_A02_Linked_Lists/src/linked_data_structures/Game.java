package linked_data_structures;
import java.io.*;
import java.util.LinkedHashSet;
import java.util.regex.*;


//This class is for the game logic of hangman. Author, Charles-Etienne
public class Game implements Serializable {//Game
	private SinglyLinkedList<String> dictionaryNodes;
	private SinglyLinkedList<Character> word = new SinglyLinkedList<Character>();
	private SinglyLinkedList<Character> lettersGuessed = new SinglyLinkedList<Character>();
	private int currentScore;
	private Player thePlayer;
	private int triesRemaining =6;
	private int gamePlayed;
	private DictionaryReader reader;
	private int listCounter;
	private String theWordWDuplicates = "";
	private String unknownWord;
	private boolean winOrLost;
	private char randLet;

	public SinglyLinkedList<String> getDictionaryNodes() {//getDictionaryNodes
		return (SinglyLinkedList<String>) dictionaryNodes;
	}//getDictionaryNodes

	public void setDictionaryNodes(SinglyLinkedList<String> dictionaryNodes) {//setDictionaryNodes(SinglyLinkedList<String> dictionaryNodes)
		this.dictionaryNodes = dictionaryNodes;
	}//setDictionaryNodes(SinglyLinkedList<String> dictionaryNodes)

	public SinglyLinkedList<Character> getWord() {//getWord
		return word;
	}//getWord
    public Player playerSelected(Player thePlayer) {//Player playerSelected(Player thePlayer)
    	return thePlayer;
    }//Player playerSelected(Player thePlayer)
	public void setWord(SinglyLinkedList<String> words) {//setWord(SinglyLinkedList<String> words)
		
		word = null;
		word = new SinglyLinkedList<Character>();
		lettersGuessed=null;
		lettersGuessed = new SinglyLinkedList<Character>();
		LinkedHashSet<Character> set = new LinkedHashSet<Character>();
		for (int i = words.getElementAt(getListCounter()).length()-1; i >-1; --i) {//for
			set.add(words.getElementAt(getListCounter()).toLowerCase().charAt(i));
		}//for
		theWordWDuplicates = words.getElementAt(listCounter).toLowerCase();
		unknownWord = theWordWDuplicates.replaceAll("[A-Za-z]", "_ ");
		set.forEach(content -> {
			if(Pattern.matches("^[A-Za-z]$", content.toString()))	
			word.add(content);
		
		});
	}//setWord(SinglyLinkedList<String> words)
//increment listCounter

	public boolean getHint() {//getHint
		int randLetter = (int) (Math.random() * (word.getLength() - 1));
		--triesRemaining;
		randLet=word.getElementAt(randLetter);
		return selectLetter(word.getElementAt(randLetter),false);
	}//getHint
	public char getRandLet() {//getRandLet
		return randLet;
	}//getRandLet()

	public void setRandLet(char randLet) {//setRandLet
		this.randLet = randLet;
	}//setRandLet

	

	public boolean selectLetter(char letter,boolean letterBeenGuessed) {//selectLetter(char letter,boolean letterBeenGuessed)
		char[] newWord;
		for (int i = 0; i < word.getLength(); ++i)
			if (letter == word.getElementAt(i)) {//if
				unknownWord= unknownWord.replaceAll("  +", ",./");
				unknownWord= unknownWord.replaceAll(" ", "");
				unknownWord=unknownWord.replace(",./", "  ");
				
				newWord = unknownWord.toCharArray();
				for (int j = 0; j < theWordWDuplicates.replace(" ", "  ").length(); ++j) {//for
					if (theWordWDuplicates.replace(" ", "  ").charAt(j) == letter) {//if

						newWord[j] = letter;
					//fix error when there's a space
					}//if
				}//for
				unknownWord="";
				for(int y =0; y<newWord.length; ++y) {//for
					unknownWord+=newWord[y]+" ";
				}//for
				if(!letterBeenGuessed)//if
				lettersGuessed.add(letter);
				word.remove(i);
				return true;
			}//if
		--triesRemaining;
		if(!letterBeenGuessed)//if
		lettersGuessed.add(letter);
		return false;
	}//selectLetter(char letter,boolean letterBeenGuessed)
	// decrement length everytime player guesses word/ remove letter

	public Boolean isWinOrLost() {//Boolean isWinOrLost()
		if (word.getLength() == 0 && triesRemaining != 0) {//if
			++listCounter;
setWinOrLost(true);
			return true;
		}//if 
		else if (triesRemaining == 0) {//else if
			++listCounter;
setWinOrLost(false);
			return true;
		}//else if
		return false;
	}//else if

	public void setWinOrLost(boolean winOrLost) {//setWinOrLost(boolean winOrLost)
		this.winOrLost = winOrLost;
	}//setWinOrLost(boolean winOrLost)
	public boolean getWinOrLost() {//getWinOrLost()
		return winOrLost;
	}//getWinOrLost
	
	public SinglyLinkedList<Character> getLettersGuessed() {//SinglyLinkedList<Character> getLettersGuessed()
		return lettersGuessed;
	}//SinglyLinkedList<Character> getLettersGuessed()

	public void setLettersGuessed(SinglyLinkedList<Character> lettersGuessed) {//setLettersGuessed(SinglyLinkedList<Character>
		this.lettersGuessed = lettersGuessed;
	}//setLettersGuessed(SinglyLinkedList<Character>

	public int getCurrentScore() {//getLettersGuessed(SinglyLinkedList<Character>
		return currentScore;
	}//getLettersGuessed(SinglyLinkedList<Character>

	public void setCurrentScore(int currentScore) {//setCurrentScore
		this.currentScore = currentScore;
	}//setCurrentScore

	public Player getThePlayer() {//getThePlayer
		return thePlayer;
	}//getThePlayer

	public void setThePlayer(Player thePlayer) {//setThePlayer
		this.thePlayer = thePlayer;
	}//setThePlayer

	public int getTriesRemaining() {//getTriesRemaining
		return triesRemaining;
	}//getTriesRemaining

	public void setTriesRemaining(int triesRemaining) {//setTriesRemaining(int triesRemaining)
		this.triesRemaining = triesRemaining;
	}//setTriesRemaining(int triesRemaining)

	public int getGamePlayed() {//public int getGamePlayed()
		return gamePlayed;
	}//public int getGamePlayed()

	public void setGamePlayed(int gamePlayed) {//setGamePlayed
		this.gamePlayed = gamePlayed;
	}//setGamePlayed

	public DictionaryReader getReader() {//DictionaryReader getReader()
		return reader;
	}//DictionaryReader getReader()

	public void setReader(DictionaryReader reader) {//DictionaryReader setReader()
		this.reader = reader;
	}//DictionaryReader setReader()

	public int getListCounter() {//getListCounter()
		return listCounter;
	}//getListCounter

	public void setListCounter(int listCounter) {//setListCounter
		this.listCounter = listCounter;
	}//setListCounter

	public String getTheWordWDuplicates() {//getTheWordWDuplicates()
		return theWordWDuplicates;
	}//getTheWordWDuplicates()

	public void setTheWordWDuplicates(String theWordWDuplicates) {//setTheWordWDuplicates()
		this.theWordWDuplicates = theWordWDuplicates;
	}//setTheWordWDuplicates()

	public String getUnknownWord() {//getUnknownWord
		return unknownWord;
	}//getUnknownWord

	public void setUnknownWord(String unknownWord) {//setUnknownWord
		this.unknownWord = unknownWord;
	}//setUnknownWord
}//Game
