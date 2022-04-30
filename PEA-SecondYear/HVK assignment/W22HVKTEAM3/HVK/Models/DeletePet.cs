using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;

namespace HVK.Models
{
    public class DeletePet
    {
        public int PetId { get; set; }

        [DeletePetValidation("ConfirmDelete")]
        public bool ConfirmDelete { get; set; }

        public string Name { get; set; }

        public DeletePet() { 
        
        }

        public DeletePet(int petId, bool confirmDelete, string name) {
            PetId = petId;
            ConfirmDelete = confirmDelete;
            Name = name;
        }
    }
}
