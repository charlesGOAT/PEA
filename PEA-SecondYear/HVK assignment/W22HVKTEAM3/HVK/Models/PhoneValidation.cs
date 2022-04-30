using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace HVK.Models {
    public class PhoneValidation:ValidationAttribute {
        public string Phone { get; private set; }
        public string Email { get; private set; }

        public PhoneValidation(string Phone,string Email) : base() { 
            this.Phone = Phone;
            this.Email = Email;
        }
        
        protected override ValidationResult IsValid(object value, ValidationContext validationContext)
        {

            var MyPhone = validationContext.ObjectInstance.GetType().GetProperty(Phone);
            var PhoneFieldValue = MyPhone.GetValue(validationContext.ObjectInstance, null);

            var MyEmail = validationContext.ObjectInstance.GetType().GetProperty(Email);
            var EmailFieldValue = MyEmail.GetValue(validationContext.ObjectInstance, null);


         
                if ( !((string)PhoneFieldValue==null || (string)PhoneFieldValue == "") || !((string)EmailFieldValue == null || (string)EmailFieldValue == "") || !((string) value == null || (string) value == ""))
                {
                    return ValidationResult.Success;
                }
            
            return new ValidationResult("One of phone or email must be filled out");
        }
    }
}

