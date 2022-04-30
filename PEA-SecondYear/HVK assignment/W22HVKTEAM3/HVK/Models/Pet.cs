using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using static HVK.Models.RangeUntilCurrentYear;
#nullable disable

namespace HVK.Models {
    public partial class Pet {
        public Pet() {
            PetReservations = new HashSet<PetReservation>();
            PetVaccinations = new HashSet<PetVaccination>();
        }

        public Pet(int petId, string name, string gender, string breed, int? birthyear, int customerId, string dogSize, bool climber, bool barker, string specialNotes)
        {
            PetId = petId;
            Name = name;
            Gender = gender;
            Breed = breed;
            Birthyear = birthyear;
            CustomerId = customerId;
            DogSize = dogSize;
            Climber = climber;
            Barker = barker;
            SpecialNotes = specialNotes;
        }

        public int PetId { get; set; }
        [StringLength(25, ErrorMessage = "Name must not be longer than 25 characters.")]
        [MaxLength(25)] 
        [DataType(DataType.Text)]
        [Required(ErrorMessage = "Name is required.")]
        public string Name { get; set; }

        [Required(ErrorMessage ="Pet's gender is required.")]
        public string Gender { get; set; }
        [DataType(DataType.Text)]
        public string Breed { get; set; }
        [Display(Name = "Birth Year")]
        [CheckDate]
        public int? Birthyear { get; set; }
        public int CustomerId { get; set; }
        [Display(Name = "Size")]
        public string DogSize { get; set; }
        public bool Climber { get; set; }
        public bool Barker { get; set; }
        [Display(Name = "Special Notes")]
        public string SpecialNotes { get; set; }
        public enum Size { Small, Medium, Large }
        public enum GenderOptions { Female, Male }

        public virtual Customer Customer { get; set; }
        public virtual ICollection<PetReservation> PetReservations { get; set; }
        public virtual ICollection<PetVaccination> PetVaccinations { get; set; }

        

    }
}
