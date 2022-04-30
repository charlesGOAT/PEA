using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace HVK.Models
{

    public sealed class LoginValidation : ValidationAttribute
    {
        public string Password { get; set; }

        public string Phone { get; private set; }
        public LoginValidation(string _Phone, string _Password) : base()
        {
            Phone = _Phone;
            Password = _Password;
        }
        protected override ValidationResult IsValid(object value, ValidationContext validationContext)
        {
            var First = validationContext.ObjectInstance.GetType().GetProperty(Phone);
            var FirstFieldValue = First.GetValue(validationContext.ObjectInstance, null);
            var PasswordField = validationContext.ObjectInstance.GetType().GetProperty(Password);
            var PasswordValue = PasswordField.GetValue(validationContext.ObjectInstance, null);
            var DBContext = (HVK_Team3Context)validationContext.GetService(typeof(HVK_Team3Context));


            if ((value == null || (string)value == "") && ((string)FirstFieldValue == "" || (string)FirstFieldValue == null))
            {
                return new ValidationResult("Phone nor Email was inputted");
            }
            else
            {
                dynamic checkPhone;
                if (DBContext.Customers.Where(c => c.Phone == (string)FirstFieldValue).Any())
                {
                     checkPhone = DBContext.Customers.Where(c => c.Phone == (string)FirstFieldValue).First();
                }
                else {
                    checkPhone = null;
                }

                if (checkPhone != null && checkPhone.Password == (string)PasswordValue)
                {
                    return ValidationResult.Success;
                }
                else
                if (checkPhone != null && !(string.IsNullOrEmpty((string)value)))
                {
                    var checkEmail = DBContext.Customers.Where(c => c.Email == (string)value);
                    if (checkEmail.Any()&&checkEmail.First().Password==(string)PasswordValue)
                    {
                        return ValidationResult.Success;
                    }
                }
            }

            return new ValidationResult("Invalid email/phone number or password");
        }


    }

}

