package cgauthier_G20_A01_ContactList;

public class BusinessContacts extends Contacts {// BuisnessContacts class
	private String jobTitle;
	private String departement;
	private String companyName;
	private String BusinessPhoneNumber;

	public BusinessContacts() {// BuisnessContacts constructor
		super();
		contactNumber = "B";
		jobTitle = "n/a";
		departement = "n/a";
		companyName = "n/a";
		BusinessPhoneNumber = "n/a";
	}// BuisnessContacts constructor

	public BusinessContacts(String fname, String name, String emailAdress) {// BuisnessContacts constructor

		super(fname, name, emailAdress);
		contactNumber = "B";
		departement = "n/a";
		jobTitle = "n/a";
		companyName = "n/a";
		BusinessPhoneNumber = "n/a";

	}// BuisnessContacts constructor

	public BusinessContacts(String fname, String name, int birthDay, int bMonth, int bYear, String street, String city,
			String province, String postalCode, String emailAdress, String cellPhone, String dep, String job,
			String company, String BusinessNumber) {// BuisnessContacts constructor

		super(fname, name, birthDay, bMonth, bYear, street, city, province, postalCode, emailAdress, cellPhone);
		contactNumber = "B";
		setDepartement(dep);
		setJobTitle(job);
		setCompanyName(company);
		setBusinessPhoneNumber(BusinessNumber);

	}//BuisnessContacts constructor

	public String getJobTitle() {// getJobTitle
		return jobTitle;
	}// getJobTitle

	public void setJobTitle(String job) {//setJobTitle
		jobTitle = job;
	}//setJobTitle

	public String getDepartement() {//getDepartement
		return departement;
	}//getDepartement

	public void setDepartement(String dep) {//setDepartement
		departement = dep;
	}//setDepartement

	public String getCompanyName() {//getCompanyName
		return companyName;
	}//getCompanyName

	public void setCompanyName(String company) {//setCompanyName
		companyName = company;
	}//setCompanyName

	public String getBusinessPhoneNumber() {// getBusinessPhoneNumber
		return BusinessPhoneNumber;
	}// getBusinessPhoneNumber

	public void setBusinessPhoneNumber(String BusinessNumber) {//setBusinessPhoneNumber
		BusinessPhoneNumber = BusinessNumber;
	}//setBusinessPhoneNumber

}// BuisnessContacts class
