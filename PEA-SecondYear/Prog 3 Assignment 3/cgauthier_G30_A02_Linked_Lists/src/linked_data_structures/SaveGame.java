package linked_data_structures;
import java.io.*;
public class SaveGame implements Serializable{//SaveGame Author, Charles-Etienne Gauthier
public static void saveAllPlayers(DoublyLinkedList<Player> doublyLinkedList) {//saveAllPlayers(DoublyLinkedList<Player> doublyLinkedList)
	try {//try
		FileOutputStream outStream = new FileOutputStream("scoreBoard.ser");
		ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
		objectOutputFile.writeObject(doublyLinkedList);
		outStream.close();
		objectOutputFile.close();
	}//try 
	catch (Exception e) {//catch
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	//catch
	
	
}//saveAllPlayers(DoublyLinkedList<Player> doublyLinkedList)


}//SaveGame
