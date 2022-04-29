package cgauthier_G20_A01_ContactList;

public class PersonnalContacts extends Contacts {// PersonnalContacts
	private String homePhone;
	private String twitter;
	private String instagram;
	private int relationship;

	public PersonnalContacts() {// PersonnalContacts constructor

		super();
		contactNumber = "P";
		homePhone = "n/a";
		twitter = "n/a";
		instagram = "n/a";
		relationship = 0;

	}// PersonnalContacts constructor

	public PersonnalContacts(String fname, String name, String emailAdress) {// PersonnalContacts constructor

		super(fname, name, emailAdress);
		contactNumber = "P";
		homePhone = "n/a";
		twitter = "n/a";
		instagram = "n/a";
		relationship = 0;

	}// PersonnalContacts constructor

	public PersonnalContacts(String fname, String name, int birthDay, int bMonth, int bYear, String street, String city,
			String province, String postalCode, String emailAdress, String cellPhone, String homeP, String tweet,
			String insta, int relation) {// PersonnalContacts constructor

		super(fname, name, birthDay, bMonth, bYear, street, city, province, postalCode, emailAdress, cellPhone);
		contactNumber = "P";
		setHomePhone(homeP);
		setTwitter(tweet);
		setInstagram(insta);
		setRelationship(relation);

	}// PersonnalContacts constructor

	public String getHomePhone() {// getHomePhone
		return homePhone;
	}// getHomePhone

	public void setHomePhone(String homeP) {// setHomePhone
		homePhone = homeP;
	}// setHomePhone

	public String getTwitter() {// getTwitter
		return twitter;
	}// getTwitter

	public void setTwitter(String tweet) {// setTwitter
		twitter = tweet;
	}// setTwitter

	public String getInstagram() {// getInstagram
		return instagram;
	}// getInstagram

	public void setInstagram(String insta) {// setInstagram
		instagram = insta;
	}// setInstagram

	public int getRelationship() {// getRelationship
		return relationship;
	}// getRelationship

	public void setRelationship(int relation) {// setRelationship
		relationship = relation;
	}// setRelationship

}// PersonnalContacts
