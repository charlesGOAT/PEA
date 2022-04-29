package cgauthier_G20_A01_ContactList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public abstract class Contacts {// Contacts class
	protected String contactNumber;
	protected String firstName;
	protected String lastName;
	protected int bDay;
	protected int bMonth;
	protected int bYear;
	protected String street;
	protected String city;
	protected String province;
	protected String postalCode;
	protected String emailAdress;
	protected String cellPhone;
	private static int nextNumber;
	private static String numberFilename;
	private static File numberFile;

	public Contacts() {// Contacts constructor

		firstName = "Unknown";
		lastName = "Unknown";
		bDay = 0;
		bMonth = 0;
		bYear = 0;
		street = "n/a";
		city = "n/a";
		province = "n/a";
		postalCode = "n/a";
		emailAdress = "n/a";
		cellPhone = "n/a";
	}// Contacts constructor

	public Contacts(String fname, String name, String emailAdress) {// Contacts constructor
		setFirstName(fname);
		setLastName(name);
		bDay = 0;
		bMonth = 0;
		bYear = 0;
		street = "n/a";
		city = "n/a";
		province = "n/a";
		postalCode = "n/a";
		setEmailAdress(emailAdress);
		cellPhone = "n/a";
	}// Contacts constructor

	public Contacts(String fname, String name, int birthDay, int bMonth, int bYear, String street, String city,
			String province, String postalCode, String emailAdress, String cellPhone) {// Contacts constructor

		setFirstName(fname);
		setLastName(name);
		setBDay(birthDay);
		setBMonth(bMonth);
		setBYear(bYear);
		setStreet(street);
		setCity(city);
		setProvince(province);
		setPostalCode(postalCode);
		setEmailAdress(emailAdress);
		setCellPhone(cellPhone);

	}// Contacts constructor

	public static void open(String filename) {// open(String)
		numberFilename = filename;
		initializeNextNumber();
	} // open(String)

	public static void open() {// open()
		numberFilename = ".\\contacts.txt";
		initializeNextNumber();
	} // open()

	private static void initializeNextNumber() {// initializeNextNumber()
		numberFile = new File(numberFilename);
		Scanner number = null;
		try {// try
			number = new Scanner(numberFile);

		} // try
		catch (FileNotFoundException e) {// catch (FileNotFoundException e)
			System.out.println(
					"ERROR: " + numberFilename + " was not found. The next product number cannot be initialized.");
			System.exit(-1);
		} // catch (FileNotFoundException e)
		catch (IOException e) {// catch (IOException e)
			System.out.println("ERROR opening " + numberFilename + ": " + e.getMessage());
			System.exit(-2);
		} // catch (IOException e)
		while (number.hasNext()) {
			nextNumber = number.nextInt();
		}
	} // initializeNextNumber()

	public static void close() {

		try {// try
			FileWriter numberOut = new FileWriter(numberFile);
			numberOut.write(String.valueOf(nextNumber));
			numberOut.close();
		} // try
		catch (IOException e) {
			System.out.println("ERROR: Could not rewrite " + numberFile + " " + e.getMessage());
		} // catch (IOException e)
	} // close()

	public void setFirstName(String fName) {// setFirstName(String)
		firstName = fName;
	}// setFirstName(String)

	public String getFirstName() {// getFirstName()
		return firstName;
	}// getFirstName()

	public void setLastName(String name) {// setLastName(String)
		lastName = name;
	}// setLastName(String)

	public String getLastName() {// getLastName()
		return lastName;

	}// getLastName()

	public void setBDay(int birthDay) {// setBDay(int)
		bDay = birthDay;
	}// setBDay(int)

	public int getBday() {// getBday()
		return bDay;

	}// getBday()

	public void setBMonth(int birthMonth) {// setBMonth(int)
		bMonth = birthMonth;
	}// setBMonth

	public int getMonth() {// getMonth()
		return bMonth;

	}// getMonth()

	public void setBYear(int birthYear) {// setBYear(int birthYear)
		bYear = birthYear;
	}// setBYear(int birthYear)

	public int getYear() {// getYear
		return bYear;

	}// getYear

	public void setStreet(String streets) {// setStreet(String streets)
		street = streets;
	}// setStreet(String streets)

	public String getStreet() {// getStreet()
		return street;
	}// getStreet()

	public void setCity(String cities) {// setCity(String cities)
		city = cities;
	}// setCity(String cities)

	public String getCity() {// getCity()
		return city;

	}// getCity()

	public void setProvince(String provinces) {// setProvince(String provinces)
		province = provinces;
	}// setProvince(String provinces)

	public String getProvince() {// getProvince()
		return province;
	}// getProvince()

	public void setPostalCode(String postal) {// setPostalCode(String postal)
		postalCode = postal;
	}// setPostalCode(String postal)

	public String getPostalCode() {// getPostalCode()
		return postalCode;
	}// getPostalCode()

	public void setEmailAdress(String mail) {// setEmailAdress
		emailAdress = mail;
	}// setEmailAdress

	public String getEmailAdress() {// getEmailAdress
		return emailAdress;
	}// getEmailAdress

	public void setCellPhone(String cell) {// setCellPhone
		cellPhone = cell;
	}// setCellPhone

	public String getCellPhone() {// getCellPhone
		return cellPhone;
	}// getCellPhone

	public void setContactNumber() {// setContactNumber
		contactNumber += String.valueOf(nextNumber);
		++nextNumber;
	}// setContactNumber

	public String getContactNumber() {// getContactNumber
		return contactNumber;
	}// getContactNumber

}// Contacts class
