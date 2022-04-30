using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace HVK.Models
{
    public class DeletePetValidation : ValidationAttribute
    {
        public string DeleteConfirmation { get; private set; }

        public DeletePetValidation(string _deleteConfirmation) : base()
        {
            DeleteConfirmation = _deleteConfirmation;
        }

        protected override ValidationResult IsValid(object value, ValidationContext validationContext)
        {

            var confirmation = validationContext.ObjectInstance.GetType().GetProperty(DeleteConfirmation);
            var confirmationFieldValue = (bool) confirmation.GetValue(validationContext.ObjectInstance, null);


            if (confirmationFieldValue)
            {
                return ValidationResult.Success;
            }
            
            return new ValidationResult("Confirmation checkbox needs to be selected to proceed.");
        }
    }
}

