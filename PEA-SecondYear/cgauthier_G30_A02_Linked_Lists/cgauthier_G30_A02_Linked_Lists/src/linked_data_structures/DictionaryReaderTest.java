package linked_data_structures;

import static org.junit.Assert.assertEquals;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class DictionaryReaderTest {

	@Test
	void test() {//check if the file exists and that it actually gets the words
		DictionaryReader dictionary = new DictionaryReader();
		try {
			dictionary.readFromFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException s) {
			s.printStackTrace();
		}
		assertEquals(true,dictionary.getAllWords()!=null);
		
	}
	
	@Test
	void test1(){//check if regex pattern matches
		DictionaryReader dictionary = new DictionaryReader();
		assertEquals(true,dictionary.verifyWord("Hello Richard"));
	}
	@Test 
	void test2() {//check if regex pattern matches even with special characters
		DictionaryReader dictionary = new DictionaryReader();
		assertEquals(true,dictionary.verifyWord("Hello Richard$$!?"));
	}
	@Test 
	void test3() {//check if regex does not match (too many characters)
		DictionaryReader dictionary = new DictionaryReader();
		assertEquals(false,dictionary.verifyWord("Hello Richard$$!fdsafffffffffffffffffffffffffffff?"));
	}
	@Test 
	void test4() {//check if regex does not match (too few characters)
		DictionaryReader dictionary = new DictionaryReader();
		assertEquals(false,dictionary.verifyWord(""));
	}
}
