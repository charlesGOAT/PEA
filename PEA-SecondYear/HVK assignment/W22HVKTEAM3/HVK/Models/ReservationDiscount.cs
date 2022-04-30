using System;
using System.Collections.Generic;

#nullable disable

namespace HVK.Models
{
    public partial class ReservationDiscount
    {
        public int DiscountId { get; set; }
        public int ReservationId { get; set; }

        public virtual Discount Discount { get; set; }
        public virtual Reservation Reservation { get; set; }

        public ReservationDiscount(){ }
        public ReservationDiscount(int discountId, int reservationId)
        {
            DiscountId = discountId;
            ReservationId = reservationId;
        }
    }
}
