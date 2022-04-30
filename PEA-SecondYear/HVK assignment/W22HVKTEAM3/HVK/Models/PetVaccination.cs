using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

#nullable disable

namespace HVK.Models
{
    public partial class PetVaccination
    {
        [DataType(DataType.Date)]
        public DateTime ExpiryDate { get; set; }
        public int VaccinationId { get; set; }
        public int PetId { get; set; }
        public bool VaccinationChecked { get; set; }
        public enum AllVaccinations { Bordatella, Distemper , Hepatitis , Parainfluenza , Parovirus , Rabies }

        public virtual Pet Pet { get; set; }
        public virtual Vaccination Vaccination { get; set; }

        public PetVaccination() { }

        public PetVaccination( int vaccinationId, int petId, bool vaccinationChecked, Pet pet, Vaccination vaccination)
        {
            VaccinationId = vaccinationId;
            PetId = petId;
            VaccinationChecked = vaccinationChecked;
            Pet = pet;
            Vaccination = vaccination;
        }
    }
}
