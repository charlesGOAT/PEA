package linked_data_structures;
import java.io.*;
public class ScoreBoard implements Serializable{//ScoreBoard. gets the scoreboard with a list of players. Author, Charles-Etienne Gauthier
private int numPlayers;
private static DoublyLinkedList<Player> differentPlayers= new DoublyLinkedList<Player>();
private static Player selectedPlayer; 
public int getNumPlayers() {
	return numPlayers;
}
public void setNumPlayers(int numPlayers) {//setNumPlayers
	this.numPlayers = numPlayers;
}//setNumPlayers
public static Player getSelectedPlayer() {//getSelectedPlayer
	return selectedPlayer;
}//getSelectedPlayer()
public static void setSelectedPlayer(Player mySelectedPlayer) {//setSelectedPlayer
	selectedPlayer = mySelectedPlayer;
}//setSelectedPlayer
public ScoreBoard() {
	
}
public void addPlayer(Player thePlayer) {//addPlayer
	differentPlayers.add(thePlayer);
}//addPlayer

public static DoublyLinkedList<Player> getDifferentPlayers() {//getDifferentPlayers
	return differentPlayers;
}//getDifferentPlayers
public static void setDifferentPlayers(DoublyLinkedList<Player> theDifferentPlayers) {//setDifferentPlayers
	differentPlayers = theDifferentPlayers;
}//setDifferentPlayers
public void sort() {//sort()
	boolean sorted = false;
	int loopend  = differentPlayers.getLength();
	while(!sorted && loopend>1) {//while
		sorted = true;
		for(int i = 1; i<loopend; ++i) {//for
			if(differentPlayers.getElementAt(i-1).getName().compareToIgnoreCase(differentPlayers.getElementAt(i).getName())>0) {//if
				switchPlayers(i-1,i);
				sorted=false;
			}//if
		}//for
		--loopend;
	}//while
	
	
}//sort()
private void switchPlayers( int index1, int index2) {//switchPlayers
	Player temp = differentPlayers.getElementAt(index1);
	differentPlayers.find(index1).setElement(differentPlayers.getElementAt(index2));
	differentPlayers.find(index2).setElement(temp);
	
}//switchPlayers

public Player searchPlayerWName(String playerN) {// searchPlayerWName(String playerN)
	for(int i = 0; i<differentPlayers.getLength();++i) {//for
		if(differentPlayers.getElementAt(i).getName().equals(playerN)) {//if
			return differentPlayers.getElementAt(i);
		}//if
	}//for
	return null;
}// searchPlayerWName(String playerN)
}//ScoreBoard
