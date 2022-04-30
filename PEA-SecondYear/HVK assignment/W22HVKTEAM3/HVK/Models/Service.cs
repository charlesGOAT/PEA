using System;
using System.Collections.Generic;

#nullable disable

namespace HVK.Models
{
    public partial class Service
    {
        public Service()
        {
            DailyRates = new HashSet<DailyRate>();
            PetReservationServices = new HashSet<PetReservationService>();
        }

        public Service(int serviceId, string serviceDescription) {
            ServiceId = serviceId;
            ServiceDescription = serviceDescription;
        }

        public int ServiceId { get; set; }
        public string ServiceDescription { get; set; }

        public virtual ICollection<DailyRate> DailyRates { get; set; }
        public virtual ICollection<PetReservationService> PetReservationServices { get; set; }
    }
}
