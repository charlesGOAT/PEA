package cgauthier_G20_A01_ContactList;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

public class AddFrame extends JFrame implements WindowListener {// AddFrame class begins

	private JFrame frmAddContact;// frame labels, text fields and combo boxes
	private JTextField fldEmail;
	private JTextField fldCellPhone;
	private JTextField fldHomePhone;
	private JTextField fldTwitter;
	private JTextField fldCompany;
	private JTextField fldDepartement;
	private JTextField fldJobTitle;
	private JLabel lblEmail;
	private JTextField fldProvince;
	private JTextField fldCity;
	private JTextField fldStreet;
	private JTextField fldLastName;
	private JTextField fldFirstName;
	private JLabel lblRelationship;
	private JLabel lblInstagram;
	private JLabel lblTwitter;
	private JLabel lblProvince;
	private JLabel lblCity;
	private JLabel lblStreet;
	private JLabel lblLastName;
	private JLabel lblFirstName;
	private JLabel lblBirthDay;
	private JLabel lblBirthMonth;
	private JLabel lblBirthYear;
	private JLabel lblCellPhone;
	private JLabel lblHomePhone;
	private JButton btnPersonalContact;
	private JButton btnBusinessContact;
	private JLabel lblCompany;
	private JLabel lblDepartement;
	private JLabel lblJobTitle;
	private JButton btnAddContact;
	private JButton btnClear;
	private char contactType;
	private Contacts contact;
	private JComboBox cmbxBirthMonth;
	private JComboBox cmbxBirthDay;
	private JComboBox cmbxBirthYear;
	private JComboBox cmbxRelationship;
	private JTextField fldInstagram;
	private JLabel lblNewLabel;
	private JTextField fldPostalCode;
	private JLabel lblPostalCode;
	private JTextField fldBusinessContact;// end of frame labels, text fields and combo boxes
	private Information information;// variable that is used to set information in the informationBusiness.txt file
	private Information information2;// variable that is used to set information in the informationPersonnal.txt
	private BusinessContacts Business;// regular BusinessContacts object
	private PersonnalContacts personnal;// regular PersonnalContacts object
	private int i = 0;

	public static void main(String[] args) {// start of main method
		EventQueue.invokeLater(new Runnable() {// EventQueue
			public void run() {
				try {// try
					AddFrame window = new AddFrame();
					window.frmAddContact.setVisible(true);
				} // end of try
				catch (Exception e) {// catch
					e.printStackTrace();
				} // end of catch
			}// end of run
		});// end of EventQueue
	}// end of main method

	public AddFrame() {// AddFrame
		Contacts.open();// calling Contact Method
		contact = null;
		this.addWindowListener(this);
		information = new Information(".\\informationBusiness.txt");
		information2 = new Information(".\\informationPersonnal.txt");
		frmAddContact = new JFrame();
		frmAddContact.setTitle("Add contact");
		frmAddContact.setBounds(100, 100, 624, 638);
		frmAddContact.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAddContact.getContentPane().setLayout(null);

		lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(10, 11, 61, 27);
		frmAddContact.getContentPane().add(lblFirstName);

		fldFirstName = new JTextField();
		fldFirstName.setBounds(72, 14, 151, 20);
		frmAddContact.getContentPane().add(fldFirstName);
		fldFirstName.setColumns(10);

		lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(10, 49, 61, 14);
		frmAddContact.getContentPane().add(lblLastName);

		fldLastName = new JTextField();
		fldLastName.setBounds(72, 46, 151, 20);
		frmAddContact.getContentPane().add(fldLastName);
		fldLastName.setColumns(10);

		fldStreet = new JTextField();
		fldStreet.setBounds(72, 77, 167, 20);
		frmAddContact.getContentPane().add(fldStreet);
		fldStreet.setColumns(10);

		lblStreet = new JLabel("Street ");
		lblStreet.setBounds(34, 80, 76, 14);
		frmAddContact.getContentPane().add(lblStreet);

		lblCity = new JLabel("City");
		lblCity.setBounds(46, 111, 46, 14);
		frmAddContact.getContentPane().add(lblCity);

		fldCity = new JTextField();
		fldCity.setBounds(72, 108, 151, 20);
		frmAddContact.getContentPane().add(fldCity);
		fldCity.setColumns(10);

		lblProvince = new JLabel("Province");
		lblProvince.setBounds(25, 142, 46, 14);
		frmAddContact.getContentPane().add(lblProvince);

		fldProvince = new JTextField();
		fldProvince.setBounds(72, 139, 151, 20);
		frmAddContact.getContentPane().add(fldProvince);
		fldProvince.setColumns(10);

		fldEmail = new JTextField();
		fldEmail.setColumns(10);
		fldEmail.setBounds(395, 105, 151, 20);
		frmAddContact.getContentPane().add(fldEmail);

		fldCellPhone = new JTextField();
		fldCellPhone.setColumns(10);
		fldCellPhone.setBounds(395, 136, 151, 20);
		frmAddContact.getContentPane().add(fldCellPhone);

		lblBirthDay = new JLabel("Birth day");
		lblBirthDay.setBounds(348, 49, 61, 14);
		frmAddContact.getContentPane().add(lblBirthDay);

		lblBirthMonth = new JLabel("Birth month");
		lblBirthMonth.setBounds(348, 17, 69, 14);
		frmAddContact.getContentPane().add(lblBirthMonth);

		lblBirthYear = new JLabel("Birth Year");
		lblBirthYear.setBounds(339, 80, 61, 14);
		frmAddContact.getContentPane().add(lblBirthYear);

		lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(351, 111, 46, 14);
		frmAddContact.getContentPane().add(lblEmail);

		lblCellPhone = new JLabel("Cell Phone");
		lblCellPhone.setBounds(324, 142, 61, 14);
		frmAddContact.getContentPane().add(lblCellPhone);

		lblHomePhone = new JLabel("Home Phone");
		lblHomePhone.setBounds(10, 469, 82, 14);
		frmAddContact.getContentPane().add(lblHomePhone);

		btnPersonalContact = new JButton("Personal Contact");
		btnPersonalContact.addActionListener(new ActionListener() {// Action Listener
			public void actionPerformed(ActionEvent arg0) {// action performed
				btnPersonalContact_actionPerformed();// calling btnPersonalContact_actionPerformed()
			}// End of action performed
		});// end of Action Listener
		btnPersonalContact.setBounds(88, 258, 151, 75);
		frmAddContact.getContentPane().add(btnPersonalContact);

		btnBusinessContact = new JButton("Business Contact");
		btnBusinessContact.addActionListener(new ActionListener() {// Action Listener
			public void actionPerformed(ActionEvent e) {// action performed
				btnBusinessContact_actionPerformed();// calling btnBusinessContact_actionPerformed()
			}// End of action performed
		});// end of Action Listener
		btnBusinessContact.setBounds(380, 258, 151, 75);
		frmAddContact.getContentPane().add(btnBusinessContact);

		lblInstagram = new JLabel("Instagram");
		lblInstagram.setBounds(10, 413, 61, 14);
		frmAddContact.getContentPane().add(lblInstagram);

		lblRelationship = new JLabel("Relation");
		lblRelationship.setBounds(10, 444, 82, 14);
		frmAddContact.getContentPane().add(lblRelationship);

		fldCompany = new JTextField();
		fldCompany.setEnabled(false);
		fldCompany.setColumns(10);
		fldCompany.setBounds(366, 379, 151, 20);
		frmAddContact.getContentPane().add(fldCompany);

		lblCompany = new JLabel("Company");
		lblCompany.setBounds(310, 382, 46, 14);
		frmAddContact.getContentPane().add(lblCompany);

		fldDepartement = new JTextField();
		fldDepartement.setEnabled(false);
		fldDepartement.setColumns(10);
		fldDepartement.setBounds(366, 408, 151, 20);
		frmAddContact.getContentPane().add(fldDepartement);

		lblDepartement = new JLabel("Departement");
		lblDepartement.setBounds(296, 413, 76, 14);
		frmAddContact.getContentPane().add(lblDepartement);

		fldJobTitle = new JTextField();
		fldJobTitle.setEnabled(false);
		fldJobTitle.setColumns(10);
		fldJobTitle.setBounds(366, 441, 151, 20);
		frmAddContact.getContentPane().add(fldJobTitle);

		lblJobTitle = new JLabel("Job Title");
		lblJobTitle.setBounds(310, 444, 46, 14);
		frmAddContact.getContentPane().add(lblJobTitle);

		btnAddContact = new JButton("Add Contact");
		btnAddContact.addActionListener(new ActionListener() {// Action listener
			public void actionPerformed(ActionEvent e) {// action performed
				btnAdd_actionPerformed();// calling btnAdd_actionPerformed()
			}// End of action performed
		});// end of Action Listener
		btnAddContact.setEnabled(false);
		btnAddContact.setBounds(88, 497, 151, 75);
		frmAddContact.getContentPane().add(btnAddContact);

		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {// Action listener
			public void actionPerformed(ActionEvent e) {// action performed
				clearAllFields();// calling clearAllFields()
			}// End of action performed
		});// end of Action Listene
		btnClear.setBounds(366, 497, 151, 75);
		frmAddContact.getContentPane().add(btnClear);

		cmbxBirthDay = new JComboBox();
		cmbxBirthDay.setModel(new DefaultComboBoxModel(
				new String[] { "", "31", "30", "29", "28", "27", "26", "25", "24", "23", "22", "21", "20", "19", "18",
						"17", "16", "15", "14", "13", "12", "11", "10", "9", "8", "7", "6", "5", "4", "3", "2", "1" }));
		cmbxBirthDay.setBounds(419, 46, 151, 20);
		frmAddContact.getContentPane().add(cmbxBirthDay);

		cmbxBirthMonth = new JComboBox();
		cmbxBirthMonth.setModel(new DefaultComboBoxModel(
				new String[] { "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
		cmbxBirthMonth.setBounds(419, 17, 151, 20);
		frmAddContact.getContentPane().add(cmbxBirthMonth);

		cmbxBirthYear = new JComboBox();
		cmbxBirthYear.setModel(new DefaultComboBoxModel(new String[] { "", "2016", "2015", "2014", "2013", "2012",
				"2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000", "1999",
				"1998", "1997", "1996", "1995", "1994", "1993", "1992", "1991", "1990", "1989", "1988", "1987", "1986",
				"1985", "1984", "1983", "1982", "1981", "1980", "1979", "1978", "1977", "1976", "1975", "1974", "1973",
				"1972", "1971", "1970", "1969", "1968", "1967", "1966", "1965", "1964", "1963", "1962", "1961", "1960",
				"1959", "1958", "1957", "1956", "1955", "1954", "1953", "1952", "1951", "1950", "1949", "1948", "1947",
				"1946", "1945", "1944", "1943", "1942", "1941", "1940", "1939", "1938", "1937", "1936", "1935", "1934",
				"1933", "1932", "1931", "1930", "1929", "1928", "1927", "1926", "1925", "1924", "1923", "1922", "1921",
				"1920", "1919", "1918", "1917", "1916" }));
		cmbxBirthYear.setBounds(395, 77, 151, 20);
		frmAddContact.getContentPane().add(cmbxBirthYear);

		cmbxRelationship = new JComboBox();
		cmbxRelationship.setModel(new DefaultComboBoxModel(new String[] { "", "0", "1", "2", "3", "4", "5" }));
		cmbxRelationship.setEnabled(false);
		cmbxRelationship.setBounds(72, 441, 151, 20);
		frmAddContact.getContentPane().add(cmbxRelationship);

		fldInstagram = new JTextField();
		fldInstagram.setEnabled(false);
		fldInstagram.setBounds(72, 410, 151, 20);
		frmAddContact.getContentPane().add(fldInstagram);
		fldInstagram.setColumns(10);

		fldBusinessContact = new JTextField();
		fldBusinessContact.setEnabled(false);
		fldBusinessContact.setBounds(395, 466, 151, 20);
		frmAddContact.getContentPane().add(fldBusinessContact);
		fldBusinessContact.setColumns(10);

		lblNewLabel = new JLabel("Business Contact");
		lblNewLabel.setBounds(271, 469, 101, 14);
		frmAddContact.getContentPane().add(lblNewLabel);

		lblPostalCode = new JLabel("Postal Code");
		lblPostalCode.setBounds(170, 204, 69, 14);
		frmAddContact.getContentPane().add(lblPostalCode);

		fldPostalCode = new JTextField();
		fldPostalCode.setBounds(247, 201, 127, 20);
		frmAddContact.getContentPane().add(fldPostalCode);
		fldPostalCode.setColumns(10);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new CompoundBorder());
		panel_2.setBounds(10, 365, 229, 121);
		frmAddContact.getContentPane().add(panel_2);
				panel_2.setLayout(null);
		
				fldHomePhone = new JTextField();
				fldHomePhone.setBounds(66, 11, 153, 20);
				panel_2.add(fldHomePhone);
				fldHomePhone.setEnabled(false);
				fldHomePhone.setColumns(10);
				
						fldTwitter = new JTextField();
						fldTwitter.setBounds(78, 101, 151, 20);
						panel_2.add(fldTwitter);
						fldTwitter.setEnabled(false);
						fldTwitter.setColumns(10);
						
								lblTwitter = new JLabel("Twitter");
								lblTwitter.setBounds(10, 14, 46, 14);
								panel_2.add(lblTwitter);
	}// end of AddFrame

	public void clearAllFields() {// clearAllFields this will clear all the fields
		fldFirstName.setText("");
		fldLastName.setText("");
		cmbxBirthDay.setSelectedIndex(0);
		cmbxBirthMonth.setSelectedIndex(0);
		cmbxBirthYear.setSelectedIndex(0);
		cmbxRelationship.setSelectedIndex(0);
		fldEmail.setText("");
		fldCellPhone.setText("");
		fldBusinessContact.setText("");
		fldTwitter.setText("");
		fldInstagram.setText("");
		fldHomePhone.setText("");
		fldCompany.setText("");
		fldDepartement.setText("");
		fldJobTitle.setText("");
		lblEmail.setText("");
		fldProvince.setText("");
		fldCity.setText("");
		fldStreet.setText("");
		fldPostalCode.setText("");
		enablePersonalFields(false);
		enableBusinessFields(false);

		btnClear.addActionListener(new ActionListener() {// Action listener
			public void actionPerformed(ActionEvent arg0) {// action performed
				clearAllFields();// call to clear all fields
			}// end of action performed
		});// end of Action Listener
	}// end of clearAllFields

	private void enablePersonalFields(boolean enabled) {// enablePersonalFields
		fldInstagram.setEnabled(enabled);
		fldTwitter.setEnabled(enabled);
		cmbxRelationship.setEnabled(enabled);
		btnAddContact.setEnabled(enabled);
		fldHomePhone.setEnabled(enabled);
	}// end of enablePersonalFields

	private void enableBusinessFields(boolean enabled) {// enableBusinessFields
		fldCompany.setEnabled(enabled);
		fldDepartement.setEnabled(enabled);
		fldJobTitle.setEnabled(enabled);
		btnAddContact.setEnabled(enabled);
		fldBusinessContact.setEnabled(enabled);
	}// end of enableBusinessFields

	private void btnPersonalContact_actionPerformed() {// btnPersonalContact_actionPerformed()
														// enables the personnal field
		contactType = 'P';
		contact = new PersonnalContacts();

		if (cmbxBirthDay.getSelectedItem().toString() == "") {// if statement
			contact.setBDay(0);

		} // end of if statement
		if (cmbxBirthMonth.getSelectedItem().toString() == "") {// if statement
			contact.setBMonth(0);

		} // end of if statement
		if (cmbxBirthYear.getSelectedItem().toString() == "") {// if statement
			contact.setBYear(0);
		} // end of if statement

		if (fldFirstName.getText().length() == 0) {// if statement
			JOptionPane.showMessageDialog(this, "You must enter your first name", "Title missing",
					JOptionPane.ERROR_MESSAGE);
			enablePersonalFields(false);

		} /* end of if statement */ else if (fldLastName.getText().length() == 0) {// if statement
			JOptionPane.showMessageDialog(this, "You must enter your last name", "Title missing",
					JOptionPane.ERROR_MESSAGE);
			enablePersonalFields(false);

		} /* end of if statement */ else if (fldEmail.getText().length() == 0
				|| (fldEmail.getText().indexOf('@') > (fldEmail.getText().indexOf('.')))
				|| !fldEmail.getText().contains("@") || !fldEmail.getText().contains(".")) {// else if statement
			JOptionPane.showMessageDialog(this, "You must enter your E-Mail or it is invalid", "Title missing",
					JOptionPane.ERROR_MESSAGE);
			enablePersonalFields(false);
		} // end of else if statement

		else if (!fldCellPhone.getText().isEmpty()
				&& (fldCellPhone.getText().charAt(3) != '-' || fldCellPhone.getText().charAt(7) != '-')) {// else if
																											// statement
			JOptionPane.showMessageDialog(this, "You must enter your Cell Phone number in the right format",
					"Title missing", JOptionPane.ERROR_MESSAGE);
			enablePersonalFields(false);

		} // end of else if statement

		else if ((cmbxBirthMonth.getSelectedIndex() == 3) && cmbxBirthDay.getSelectedIndex() > 29
				|| (cmbxBirthMonth.getSelectedIndex() == 5) && (cmbxBirthDay.getSelectedIndex() > 31)
				|| (cmbxBirthMonth.getSelectedIndex() == 7) && (cmbxBirthDay.getSelectedIndex() > 31)
				|| (cmbxBirthMonth.getSelectedIndex() == 10) && (cmbxBirthDay.getSelectedIndex() > 31)
				|| (cmbxBirthMonth.getSelectedIndex() == 12) && (cmbxBirthDay.getSelectedIndex() > 30)) {// else if
																											// statement
			JOptionPane.showMessageDialog(this, "This day does not exist", "Title missing", JOptionPane.ERROR_MESSAGE);
			enablePersonalFields(false);

		} /* else if */
		/* end of else if */ else if (fldPostalCode.getText().length() != 6 && fldPostalCode.getText().length() != 0) {

			JOptionPane.showMessageDialog(this, "You must enter your Postal Code in the right format", "Title missing",
					JOptionPane.ERROR_MESSAGE);
			enablePersonalFields(false);

		} /* end of else if */
		else if (fldPostalCode.getText().length() != 0
				&& (Character.isDigit(fldPostalCode.getText().toString().charAt(0))
						|| Character.isDigit(fldPostalCode.getText().toString().charAt(2))
						|| Character.isDigit(fldPostalCode.getText().toString().charAt(4)))) {
			JOptionPane.showMessageDialog(this, "You must enter your Postal Code in the right format", "Title missing",
					JOptionPane.ERROR_MESSAGE);
			enableBusinessFields(false);
		} else {// else statement

			enablePersonalFields(true);

		} // end of else statement
	}// end of btnPersonalContact_actionPerformed()

	private void btnBusinessContact_actionPerformed() {// btnBusinessContact_actionPerformed() enables the buisness
														// fields
		contactType = 'B';
		contact = new BusinessContacts();
		if (cmbxBirthDay.getSelectedItem().toString() == "") {// if statement
			contact.setBDay(0);

		} // if statement
		if (cmbxBirthMonth.getSelectedItem().toString() == "") {// if statement
			contact.setBMonth(0);

		} // if statement
		if (cmbxBirthYear.getSelectedItem().toString() == "") {// if statement
			contact.setBYear(0);
		} // if statement

		if (fldFirstName.getText().length() == 0) {// if statement
			JOptionPane.showMessageDialog(this, "You must enter your first name", "Title missing",
					JOptionPane.ERROR_MESSAGE);
			enableBusinessFields(false);

		} /* if statement */ else if (fldLastName.getText().length() == 0) {// else if statement
			JOptionPane.showMessageDialog(this, "You must enter your last name", "Title missing",
					JOptionPane.ERROR_MESSAGE);
			enableBusinessFields(false);

		} /* else if */ else if (fldEmail.getText().length() == 0
				|| (fldEmail.getText().indexOf('@') > (fldEmail.getText().indexOf('.')))
				|| !fldEmail.getText().contains("@") || !fldEmail.getText().contains(".")) {// else if
			JOptionPane.showMessageDialog(this, "You must enter your E-Mail or it is invalid", "Title missing",
					JOptionPane.ERROR_MESSAGE);
			enableBusinessFields(false);
		} // else if

		else if (!fldCellPhone.getText().isEmpty()
				&& (fldCellPhone.getText().charAt(3) != '-' || fldCellPhone.getText().charAt(7) != '-')) {// else if
			JOptionPane.showMessageDialog(this, "You must enter your Cell Phone number in the right format",
					"Title missing", JOptionPane.ERROR_MESSAGE);
			enableBusinessFields(false);

		} // else if

		else if ((cmbxBirthMonth.getSelectedIndex() == 3) && cmbxBirthDay.getSelectedIndex() > 29
				|| (cmbxBirthMonth.getSelectedIndex() == 5) && (cmbxBirthDay.getSelectedIndex() > 31)
				|| (cmbxBirthMonth.getSelectedIndex() == 7) && (cmbxBirthDay.getSelectedIndex() > 31)
				|| (cmbxBirthMonth.getSelectedIndex() == 10) && (cmbxBirthDay.getSelectedIndex() > 31)
				|| (cmbxBirthMonth.getSelectedIndex() == 12) && (cmbxBirthDay.getSelectedIndex() > 30)) {// else if
			JOptionPane.showMessageDialog(this, "This day does not exist", "Title missing", JOptionPane.ERROR_MESSAGE);
			enableBusinessFields(false);

		} /* else if */ else if (fldPostalCode.getText().length() != 6 && fldPostalCode.getText().length() != 0) {// else
																													// if

			JOptionPane.showMessageDialog(this, "You must enter your Postal Code in the right format", "Title missing",
					JOptionPane.ERROR_MESSAGE);
			enableBusinessFields(false);

		} // else if
		else if (fldPostalCode.getText().length() != 0
				&& (Character.isDigit(fldPostalCode.getText().toString().charAt(0))
						|| Character.isDigit(fldPostalCode.getText().toString().charAt(2))
						|| Character.isDigit(fldPostalCode.getText().toString().charAt(4)))) {
			JOptionPane.showMessageDialog(this, "You must enter your Postal Code in the right format", "Title missing",
					JOptionPane.ERROR_MESSAGE);
			enableBusinessFields(false);
		} else
			enableBusinessFields(true);
	}// btnBusinessContact_actionPerformed()

	private void btnAdd_actionPerformed() {// btnAdd_actionPerformed() very important. Adds information when you click
											// the add button
		if (contactType == 'P') {// if
			contact = new PersonnalContacts();
			enablePersonalFields(true);
			enableBusinessFields(false);
			if (!fldFirstName.getText().isEmpty())// if
				contact.setFirstName((fldFirstName.getText()));// if

			if (!fldPostalCode.getText().isEmpty())// if
				contact.setPostalCode((fldPostalCode.getText()));// if

			if (!fldLastName.getText().isEmpty()) {// if
				contact.setLastName((fldLastName.getText()));
			} // if
			if (!fldStreet.getText().isEmpty()) {// if
				contact.setStreet((fldStreet.getText()));
			} // if
			if (!fldProvince.getText().isEmpty()) {// if
				contact.setProvince((fldProvince.getText()));
			} // if
			if (!fldEmail.getText().isEmpty()) {// if
				contact.setEmailAdress((fldEmail.getText()));
			} // if
			if (!fldCellPhone.getText().isEmpty()) {// if
				contact.setCellPhone(fldCellPhone.getText());
			} // if
			if (!fldTwitter.getText().isEmpty())// if
				((PersonnalContacts) contact).setTwitter(fldTwitter.getText());// if
			if (!fldInstagram.getText().isEmpty())// if
				((PersonnalContacts) contact).setInstagram(fldInstagram.getText());// if
			if (!fldHomePhone.getText().isEmpty()) {// if
				((PersonnalContacts) contact).setHomePhone(fldHomePhone.getText());
				contact.setBDay(Integer.parseInt(cmbxBirthDay.getSelectedItem().toString()));
				contact.setBMonth(Integer.parseInt(cmbxBirthMonth.getSelectedItem().toString()));
				contact.setBYear(Integer.parseInt(cmbxBirthYear.getSelectedItem().toString()));
				((PersonnalContacts) contact)
						.setRelationship(Integer.parseInt(cmbxRelationship.getSelectedItem().toString()));
				contact.setContactNumber();
			} // if
			if (!fldHomePhone.getText().isEmpty()
					&& (fldHomePhone.getText().charAt(3) != '-' || fldHomePhone.getText().charAt(7) != '-')) {// if
				JOptionPane.showMessageDialog(this, "You must enter your Home Phone number in the right format",
						"Title missing", JOptionPane.ERROR_MESSAGE);

			}
			/* if */ if (cmbxRelationship.getSelectedItem() == "") {// else if
				JOptionPane.showMessageDialog(this, "You must enter your relationship", "Title missing",
						JOptionPane.ERROR_MESSAGE);

			} /* else if */ else {// else
				contact.setContactNumber();
				information2.writePersonnal((PersonnalContacts) contact);

				JOptionPane.showMessageDialog(this,
						"The Personnal Contact was successfully added with  code " + contact.getContactNumber(),
						"Successful Add", JOptionPane.INFORMATION_MESSAGE);
				information2.close();
				enablePersonalFields(false);
				enableBusinessFields(false);
				Contacts.close();
			} // else
		} /* if */ else

		if (contactType == 'B') {// if
			contact = new BusinessContacts();
			enableBusinessFields(true);
			enablePersonalFields(false);
			if (!fldFirstName.getText().isEmpty())// if
				contact.setFirstName((fldFirstName.getText()));// if
			if (!fldLastName.getText().isEmpty()) {// if
				contact.setLastName((fldLastName.getText()));
			} // if
			if (!fldPostalCode.getText().isEmpty())// if
				contact.setPostalCode((fldPostalCode.getText()));// if

			if (!fldStreet.getText().isEmpty()) {// if
				contact.setStreet((fldStreet.getText()));
			} // if
			if (!fldProvince.getText().isEmpty()) {// if
				contact.setProvince((fldProvince.getText()));
			} // if
			if (!fldEmail.getText().isEmpty()) {// if
				contact.setEmailAdress((fldEmail.getText()));
			} // if
			if (!fldCellPhone.getText().isEmpty()) {// if
				contact.setCellPhone(fldCellPhone.getText());
			} // if
			if (!fldJobTitle.getText().isEmpty()) {// if
				((BusinessContacts) contact).setJobTitle(fldJobTitle.getText());
			} // if
			if (!fldCompany.getText().isEmpty()) {// if
				((BusinessContacts) contact).setCompanyName(fldCompany.getText());
			} // if
			if (!fldDepartement.getText().isEmpty()) {// if
				((BusinessContacts) contact).setDepartement(fldDepartement.getText());
			} // if
			if (!fldBusinessContact.getText().isEmpty()) {// if
				((BusinessContacts) contact).setBusinessPhoneNumber(fldBusinessContact.getText());
			} // if
			if (!cmbxBirthDay.getSelectedItem().toString().equals(""))// if
				contact.setBDay(Integer.parseInt(cmbxBirthDay.getSelectedItem().toString()));// if
			if (!cmbxBirthMonth.getSelectedItem().toString().equals(""))// if
				contact.setBMonth(Integer.parseInt(cmbxBirthMonth.getSelectedItem().toString()));// if
			if (!cmbxBirthYear.getSelectedItem().toString().equals(""))// if
				contact.setBYear(Integer.parseInt(cmbxBirthYear.getSelectedItem().toString()));// if

			if (fldCompany.getText().length() == 0) {// if
				JOptionPane.showMessageDialog(this, "You must enter your company's name", "Title missing",
						JOptionPane.ERROR_MESSAGE);

			} // if

			else if (!fldBusinessContact.getText().isEmpty() && (fldBusinessContact.getText().charAt(3) != '-'
					|| fldBusinessContact.getText().charAt(7) != '-')) {// if
				JOptionPane.showMessageDialog(this, "You must enter your Business Phone number in the right format",
						"Title missing", JOptionPane.ERROR_MESSAGE);

			} /* if */ else {// else
				contact.setContactNumber();
				information.writeBusiness((BusinessContacts) contact);
				JOptionPane.showMessageDialog(this,
						"The Business Contact was successfully added with code " + contact.getContactNumber(),
						"Successful Add", JOptionPane.INFORMATION_MESSAGE);
				information.close();
				clearAllFields();
				enablePersonalFields(false);
				enableBusinessFields(false);
				Contacts.close();
			} // else

		} // if

	}// end of btnAdd_actionPerformed()

	@Override
	public void windowActivated(WindowEvent arg0) {// windowActivated
		// TODO Auto-generated method stub

	}// end of windowActivated

	@Override
	public void windowClosed(WindowEvent arg0) {// windowClosed
		// TODO Auto-generated method stub

	}// end of windowClosed

	@Override
	public void windowClosing(WindowEvent arg0) {// windowClosing
		// TODO Auto-generated method stub

	}// end of windowClosing

	@Override
	public void windowDeactivated(WindowEvent arg0) {// windowDeactivated
		// TODO Auto-generated method stub

	}// windowDeactivated

	@Override
	public void windowDeiconified(WindowEvent arg0) {// windowDeiconified
		// TODO Auto-generated method stub

	}// windowDeiconified

	@Override
	public void windowIconified(WindowEvent arg0) {// windowIconified
		// TODO Auto-generated method stub

	}// windowIconified

	@Override
	public void windowOpened(WindowEvent arg0) {// windowOpened
		// TODO Auto-generated method stub

	}// windowOpened
}// end of AddFrame
