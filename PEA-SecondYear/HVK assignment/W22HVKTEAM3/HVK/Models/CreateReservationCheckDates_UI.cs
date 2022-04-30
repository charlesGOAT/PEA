using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;

namespace HVK.Models
{
    public class CreateReservationCheckDates_UI
    {
        [DataType(DataType.Date)]
        [Display(Name = "Start Date")]
        [Required(ErrorMessage = "Start Date is required")]
        [ReservationDatesValidation("StartDate", "EndDate")]
        public DateTime? StartDate { get; set; }
        [DataType(DataType.Date)]
        [Display(Name = "End Date")]
        [Required(ErrorMessage = "End Date is required")]
        public DateTime? EndDate { get; set; }
        public List<Pet> PetsList { get; set; }
        public Dictionary<int,bool> PetChecked { get; set; }
       
        public int CustomerId { get; set; }
       
        public CreateReservationCheckDates_UI()
        {
        }
        public CreateReservationCheckDates_UI(DateTime? startDate, DateTime? endDate)
        {
            StartDate = startDate;
            EndDate = endDate;
        }
        public CreateReservationCheckDates_UI(DateTime? startDate, DateTime? endDate, List<Pet> petsList)
        {
            StartDate = startDate;
            EndDate = endDate;
            PetsList = petsList;
        }
    }
}