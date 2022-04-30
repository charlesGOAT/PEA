using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;

namespace HVK.Models
{
    public class PetVaccination_UI
    {
        public int PetId { get; set; }
        public Pet Pet { get; set; }

        [DataType(DataType.Date)]
        public DateTime? BordetellaExpiryDate { get; set; }
        public PetVaccination BordetellaVaccine {get; set;}
        [DataType(DataType.Date)]
        public DateTime? DistemperExpiryDate { get; set; }
        public PetVaccination DistemperVaccine { get; set; }
        [DataType(DataType.Date)]
        public DateTime? HepatitisExpiryDate { get; set; }
        public PetVaccination HepatitisVaccine { get; set; }
        [DataType(DataType.Date)]
        public DateTime? ParainfluenzaExpiryDate { get; set; }
        public PetVaccination ParainfluenzaVaccine { get; set; }
        [DataType(DataType.Date)]
        public DateTime? ParovirusExpiryDate { get; set; }
        public PetVaccination ParovirusVaccine { get; set; }
        [DataType(DataType.Date)]
        public DateTime? RabiesExpiryDate { get; set; }
        public PetVaccination RabiesVaccine { get; set; }
        [DataType(DataType.Date)]
        public DateTime? ChangeAllDatesDate { get; set; }


        public PetVaccination_UI() { 

        }

        public PetVaccination_UI(Pet pet)
        {
            Pet = pet;
            PetId = pet.PetId;
        }

        public PetVaccination_UI(Pet pet, PetVaccination bordetellaVaccination, PetVaccination distemperVaccination, PetVaccination hepatitisVaccination, PetVaccination parainfluenzaVaccination, PetVaccination parovirusVaccination, PetVaccination rabiesVaccination)
        {
            Pet = pet;
            PetId = pet.PetId;
            BordetellaVaccine = bordetellaVaccination;
            BordetellaExpiryDate = bordetellaVaccination.ExpiryDate;
            DistemperVaccine = distemperVaccination;
            DistemperExpiryDate = distemperVaccination.ExpiryDate;
            HepatitisVaccine = hepatitisVaccination;
            HepatitisExpiryDate = hepatitisVaccination.ExpiryDate;
            ParainfluenzaVaccine = parainfluenzaVaccination;
            ParainfluenzaExpiryDate = parainfluenzaVaccination.ExpiryDate;
            ParovirusVaccine = parovirusVaccination;
            ParovirusExpiryDate = parovirusVaccination.ExpiryDate;
            RabiesVaccine = rabiesVaccination;
            RabiesExpiryDate = rabiesVaccination.ExpiryDate;
        }

        public PetVaccination_UI(DateTime? changeAllDatesDate)
        {
            ChangeAllDatesDate = changeAllDatesDate;
            BordetellaExpiryDate = changeAllDatesDate;
            DistemperExpiryDate = changeAllDatesDate;
            HepatitisExpiryDate = changeAllDatesDate;
            ParainfluenzaExpiryDate = changeAllDatesDate;
            ParovirusExpiryDate = changeAllDatesDate;
            RabiesExpiryDate = changeAllDatesDate;

        }

        public PetVaccination_UI(Pet pet, DateTime? changeAllDatesDate)
        {
            Pet = pet;
            PetId = pet.PetId;
            ChangeAllDatesDate = changeAllDatesDate;
            BordetellaExpiryDate = changeAllDatesDate;
            DistemperExpiryDate = changeAllDatesDate;
            HepatitisExpiryDate = changeAllDatesDate;
            ParainfluenzaExpiryDate = changeAllDatesDate;
            ParovirusExpiryDate = changeAllDatesDate;
            RabiesExpiryDate = changeAllDatesDate;

        }

        public PetVaccination_UI(DateTime? bordetellaExpiryDate, DateTime? distemerExpiryDate, DateTime? hepatitisExpiryDate, DateTime? parainfluenzaExpiryDate, DateTime? parovirusExpiryDate, DateTime? rabiesExpiryDate)
        {
            BordetellaExpiryDate = bordetellaExpiryDate;
            DistemperExpiryDate = distemerExpiryDate;
            HepatitisExpiryDate = hepatitisExpiryDate;
            ParainfluenzaExpiryDate = parainfluenzaExpiryDate;
            ParovirusExpiryDate = parovirusExpiryDate;
            RabiesExpiryDate = rabiesExpiryDate;

        }

        public PetVaccination_UI(Pet pet, DateTime? bordetellaExpiryDate, DateTime? distemerExpiryDate, DateTime? hepatitisExpiryDate, DateTime? parainfluenzaExpiryDate, DateTime? parovirusExpiryDate, DateTime? rabiesExpiryDate)
        {
            Pet =  pet;
            PetId = pet.PetId;
            BordetellaExpiryDate = bordetellaExpiryDate;
            DistemperExpiryDate = distemerExpiryDate;
            HepatitisExpiryDate = hepatitisExpiryDate;
            ParainfluenzaExpiryDate = parainfluenzaExpiryDate;
            ParovirusExpiryDate = parovirusExpiryDate;
            RabiesExpiryDate = rabiesExpiryDate;

        }

        public void AssignPetVaccinationsFromList(List<PetVaccination> petVaccList) {

            foreach (var petVacc in petVaccList) {
                switch (petVacc.Vaccination.Name.ToLower()) {
                    case "bordetella":
                        this.BordetellaVaccine = petVacc;
                        this.BordetellaExpiryDate = petVacc.ExpiryDate;
                        break;
                    case "distemper":
                        this.DistemperVaccine = petVacc;
                        this.DistemperExpiryDate = petVacc.ExpiryDate;
                        break;
                    case "hepatitis":
                        this.HepatitisVaccine = petVacc;
                        this.HepatitisExpiryDate = petVacc.ExpiryDate;
                        break;
                    case "parainfluenza":
                        this.ParainfluenzaVaccine = petVacc;
                        this.ParainfluenzaExpiryDate = petVacc.ExpiryDate;
                        break;
                    case "parovirus":
                        this.ParovirusVaccine = petVacc;
                        this.ParovirusExpiryDate = petVacc.ExpiryDate;
                        break;
                    case "rabies":
                        this.RabiesVaccine = petVacc;
                        this.RabiesExpiryDate = petVacc.ExpiryDate;
                        break;
                }
            }

        }

    }
}
