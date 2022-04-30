using System.ComponentModel.DataAnnotations;

public sealed class EmergencyValidation : ValidationAttribute {
    public string EmergencyFirstName { get; private set; }
    public string EmergencyLastName { get; private set; }

    public EmergencyValidation(string _EmergencyFirstName, string _EmergencyLastName) : base() {
        EmergencyFirstName = _EmergencyFirstName;
        EmergencyLastName = _EmergencyLastName;
    }

    protected override ValidationResult IsValid(object value, ValidationContext validationContext) {

        var First = validationContext.ObjectInstance.GetType().GetProperty(EmergencyFirstName);
        var FirstFieldValue = First.GetValue(validationContext.ObjectInstance, null);
        var Last = validationContext.ObjectInstance.GetType().GetProperty(EmergencyLastName);
        var LastFieldValue = Last.GetValue(validationContext.ObjectInstance, null);



        if ((string.IsNullOrEmpty((string)FirstFieldValue)) && ((string.IsNullOrEmpty((string)value) && ((string.IsNullOrEmpty((string) LastFieldValue)))))) {
            return ValidationResult.Success;
        } else if (!(string.IsNullOrEmpty((string)FirstFieldValue)) && !(string.IsNullOrEmpty((string)value)) && !(string.IsNullOrEmpty((string)LastFieldValue))) {
            return ValidationResult.Success;
        } else {
            return new ValidationResult("To register the emergency contact, please complete all fields");
        }

    }
}
    

