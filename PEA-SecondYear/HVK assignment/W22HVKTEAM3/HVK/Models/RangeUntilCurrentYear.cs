using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;
using HVK.Models;

namespace HVK.Models
{
    public class RangeUntilCurrentYear
    {
        public sealed class CheckDate : ValidationAttribute
        {
            protected override ValidationResult IsValid(object value, ValidationContext validationContext)
            {
                int date = DateTime.Now.Year;
                if (value != null)
                {
                    if ((int)value >= 2000 && (int)value < date)
                    {
                        return ValidationResult.Success;
                    }
                    else
                    {
                        return new ValidationResult("Date must be between the year 2000 and " + DateTime.Now.Year);
                    }
                }
                else
                {
                    return ValidationResult.Success;
                }
            }
        }
    }
}
