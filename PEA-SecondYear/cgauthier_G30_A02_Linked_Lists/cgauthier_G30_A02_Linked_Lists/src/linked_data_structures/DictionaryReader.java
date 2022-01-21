package linked_data_structures;
import java.util.regex.*;  
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;
public class DictionaryReader implements Serializable{//DictionaryReader is used to read the dictionary Author, Charles-Etienne Gauthier
	private String word;
	private SinglyLinkedList<String>allWords = new SinglyLinkedList();
	
	
	public boolean readFromFile() throws IOException {// readFromFile
		try {// try
			File file = new File("dictionary.txt");
			int mainCounter = 0;
			Scanner fileReader = new Scanner(file);

			while (fileReader.hasNext()) {// while
				String line = fileReader.nextLine();
				while (line.trim().isEmpty()) {// while

					line = fileReader.nextLine();
				} // while
				 // catch FileNotFoundException
				 int sizeMinusOne = allWords.getLength()-1;
                 if(allWords.getLength()==0) {//if
                                 sizeMinusOne=0;
                 }//if
                 int randomizer = (int)Math.floor(Math.random()*(sizeMinusOne));
                 allWords.add(line,randomizer);

				
				
	}//while
			
			int i =0;
			while(i<allWords.getLength()) {//while
				if(!verifyWord(allWords.getElementAt(i))) {//if
					allWords.remove(i);
				i=0;
				}//if
				else {//else
					++i;
				}//else
				
				
			}//while
return true;
}//try
		catch(FileNotFoundException e) {//catch
	System.out.print(e);
	return false;
}//catch
		}//readFromFile
	public String getWord() {//getWord
		return word;
	}//getWord
	public void setWord(String word) {// setWord(String word)
		this.word = word;
	}// setWord(String word)
	public SinglyLinkedList<String> getAllWords() {//getAllWords()
		return allWords;
	}//getAllWords()
	public void setAllWords(SinglyLinkedList<String> allWords) {//setAllWords(SinglyLinkedList<String> allWords
		this.allWords = allWords;
	}//setAllWords(SinglyLinkedList<String> allWords
	public boolean verifyWord(String word) {//verifyWord
		return Pattern.matches("^[a-zA-Z0-9\\.\\,\\-\\ \\$\\#\\!\\?]+$", word)&&word.trim().length()>6&&word.length()<25;
		}//verifyWord

}//DictionaryReader is used to read the dictionary
