using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
#nullable disable


namespace HVK.Models
{
    public partial class Customer
    {
        public Customer()
        {
            Pets = new HashSet<Pet>();
        }

        public Customer(int customerId, string firstName, string lastName, string phone, string email)
        {
            CustomerId = customerId;
            FirstName = firstName;
            LastName = lastName;
            Phone = phone;
            Email = email;
        }



        public enum Provinces { QC, ON, AB, BC, MB, NB, NL, NS, PE, SK };
        public int CustomerId { get; set; }
        [DataType(DataType.Text)]
        [Display(Name = "First Name")]
        [Required(ErrorMessage = "First name is a required field")]
        [StringLength(25, ErrorMessage = "First name cannot be longer than 25 characters")]
        public string FirstName { get; set; }
        [DataType(DataType.Text)]
        [Display(Name = "Last Name")]
        [Required(ErrorMessage = "Last name is a required field")]
        [StringLength(25, ErrorMessage = "Last name cannot be longer than 25 characters")]
        public string LastName { get; set; }
        [DataType(DataType.Text)]
        [Display(Name = "Street")]
        [StringLength(40, ErrorMessage = "Street cannot be longer than 40 characters")]
        public string Street { get; set; }
        [DataType(DataType.Text)]
        [Display(Name = "City")]
        [StringLength(25, ErrorMessage = "City cannot be longer than 25 characters")]
        public string City { get; set; }
        public string Province { get; set; }
        [Display(Name = "Postal Code")]
        [DataType(DataType.Text)]
        [RegularExpression("^[A-Za-z][0-9][A-Za-z][0-9][A-Za-z][0-9]$", ErrorMessage = "Postal code has to follow a specific format")]
        public string PostalCode { get; set; }
        [DataType(DataType.Text)]
        [StringLength(25)]
        [PhoneValidation("CellPhone", "Email", ErrorMessage = "Either phone number or email is required.")]
        [RegularExpression("^[0-9]{10}$", ErrorMessage = "Must Follow pattern XXXXXXXXXX")]
        public string Phone { get; set; }
        [DataType(DataType.Text)]
        [StringLength(25)]
        [RegularExpression("^[0-9]{10}$", ErrorMessage = "Must Follow pattern XXXXXXXXXX")]
        public string CellPhone { get; set; }
        [DataType(DataType.Text)]
        [StringLength(50, ErrorMessage = "Email cannot be longer than 50 characters")]
        [RegularExpression("^[A-Za-z0-9.\\-]+@[A-Za-z0-9\\-.]+\\.[a-z]+$", ErrorMessage = "Emails should follow this format: a@c.com")]
        public string Email { get; set; }
        [StringLength(25, ErrorMessage = "Emergency first name cannot be longer than 25 characters")]
        [DataType(DataType.Text)]
        public string EmergencyContactFirstName { get; set; }
        [StringLength(25, ErrorMessage = "Emergency first name cannot be longer than 25 characters")]
        [DataType(DataType.Text)]
        public string EmergencyContactLastName { get; set; }
        [StringLength(25)]
        [DataType(DataType.Text)]
        [EmergencyValidation("EmergencyContactFirstName", "EmergencyContactLastName")]
        [RegularExpression("[0-9]{10}$", ErrorMessage = "Must Follow pattern XXXXXXXXXX")]
        public string EmergencyContactPhone { get; set; }
        [Required(ErrorMessage = "Password is a required field")]
        [Compare("ConfirmPassword", ErrorMessage = "Password and Confirm Password are not matching")]
        public string Password { get; set; }
        [NotMapped]
        [Required(ErrorMessage = "Confirm Password is required")]
        public string ConfirmPassword { get; set; }
        public virtual ICollection<Pet> Pets { get; set; }
    }
}

