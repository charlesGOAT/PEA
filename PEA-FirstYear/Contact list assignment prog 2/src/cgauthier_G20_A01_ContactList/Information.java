package cgauthier_G20_A01_ContactList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Information {// Information class
	private String informationFileName;
	private FileWriter informationWriter;

	public Information(String info) {// Information constructor
		open(info);
	}// end of Information constructor

	public void open(String info) {
		informationFileName = info;
		File infoFile = new File(informationFileName);
		try {// try
			informationWriter = new FileWriter(infoFile, true);
		} // try
		catch (IOException e) {
			System.out.println("ERROR: File " + informationFileName + "could not opened: " + e.getMessage());
		} // catch
	} // open(String)

	public boolean writeBusiness(BusinessContacts contact) {// writeBusiness
		try {// try
			if (contact.getContactNumber().charAt(0) == 'B') {// start of if statement

				informationWriter.write(contact.getContactNumber() + "~" + contact.getFirstName() + "~"
						+ contact.getLastName() + "~" + contact.getBday() + "~" + contact.getMonth() + "~"
						+ contact.getYear() + "~" + contact.getStreet() + "~" + contact.getCity() + "~"
						+ contact.getProvince() + "~" + contact.getPostalCode() + "~" + contact.getEmailAdress() + "~"+contact.getCellPhone()+"~"+
						 contact.getBusinessPhoneNumber() + "~" + contact.getCompanyName() + "~" + contact.getDepartement() + "~"
						+ contact.getJobTitle());
				informationWriter.write("\n");
			} // end of if statement

		} // try
		catch (IOException e) {
			System.out.println("ERROR: Business Contact " + contact.getContactNumber() + "could not be written to file "
					+ informationFileName + ": " + e.getMessage());
			return false;
		} // catch
		return true;
	}

	public boolean writePersonnal(PersonnalContacts contact) {// writePersonnalContacts
		try {// try
			if (contact.getContactNumber().charAt(0) == 'P') {// end of if statement
				informationWriter.write(contact.getContactNumber() + "~" + contact.getFirstName() + "~"
						+ contact.getLastName() + "~" + contact.getBday() + "~" + contact.getMonth() + "~"
						+ contact.getYear() + "~" + contact.getStreet() + "~" + contact.getCity()
						+ contact.getProvince() + contact.getPostalCode() + contact.getEmailAdress() + "~" +contact.getCellPhone()+"~"+
						 contact.getHomePhone() + "~" + contact.getTwitter() + "~" + contact.getInstagram() + "~"
						+ contact.getRelationship());
				informationWriter.write("\n");
			} // end of if statement
		} // try
		catch (IOException e) {// catch
			System.out.println("ERROR: Personnal Contacts " + contact.getContactNumber()
					+ "could not be written to file " + informationFileName + ": " + e.getMessage());
			return false;
		} // catch
		return true;
	}// writePersonnal end

	public void close() {
		try {// try
			informationWriter.close();
		} // try
		catch (IOException e) {// catch
			System.out.println("ERROR: File " + informationFileName + "could not closed: " + e.getMessage());
		} // catch
	}// end of close
}// end of class Information
