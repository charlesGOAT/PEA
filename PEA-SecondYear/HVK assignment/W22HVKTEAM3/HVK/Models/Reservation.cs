using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;

#nullable disable

namespace HVK.Models
{
    public partial class Reservation
    {
        public Reservation()
        {
            PetReservations = new HashSet<PetReservation>();
            ReservationDiscounts = new HashSet<ReservationDiscount>();
        }

        public int ReservationId { get; set; }
        public DateTime StartDate { get; set; }
        public DateTime EndDate { get; set; }
        public decimal Status { get; set; }

        public virtual ICollection<PetReservation> PetReservations { get; set; }
        public virtual ICollection<ReservationDiscount> ReservationDiscounts { get; set; }
        [NotMapped]
        [DeletePetValidation("ConfirmDelete")]
        public bool ConfirmDelete { get; set; }

        public Reservation(int reservationId, DateTime startDate, DateTime endDate, decimal status)
        {
            ReservationId = reservationId;
            StartDate = startDate;
            EndDate = endDate;
            Status = status;
        }
    }
}
