using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace HVK.Models
{
    public class ReservationDatesValidation : ValidationAttribute
    {
        public string StartDate { get; private set; }
        public string EndDate { get; private set; }

        public ReservationDatesValidation(string startDate, string endDate) : base()
        {
            this.StartDate = startDate;
            this.EndDate = endDate;
            
        }

        protected override ValidationResult IsValid(object value, ValidationContext validationContext)
        {

            var MyStartDate = validationContext.ObjectInstance.GetType().GetProperty("StartDate");
            var StartDateFieldValue = (DateTime?)MyStartDate.GetValue(validationContext.ObjectInstance, null);

            var MyEndDate = validationContext.ObjectInstance.GetType().GetProperty("EndDate");
            var EndDateFieldValue = (DateTime?)MyEndDate.GetValue(validationContext.ObjectInstance, null);

             if (StartDateFieldValue <= EndDateFieldValue && StartDateFieldValue >= DateTime.Today) {
                return ValidationResult.Success;
            } else if (StartDateFieldValue < DateTime.Today) {
                return new ValidationResult("Start Date must be today or later.");
            }

            return new ValidationResult("Start Date must come before the End Date");
        }
    }
}

