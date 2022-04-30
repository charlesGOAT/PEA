using System;
using System.Collections.Generic;

#nullable disable

namespace HVK.Models
{
    public partial class DailyRate
    {
        public int DailyRateId { get; set; }
        public decimal Rate { get; set; }
        public string DogSize { get; set; }
        public int ServiceId { get; set; }

        public virtual Service Service { get; set; }

        public DailyRate() { }

        public DailyRate(int dailyRateId, decimal rate, string dogSize, int serviceId)
        {
            DailyRateId = dailyRateId;
            Rate = rate;
            DogSize = dogSize;
            ServiceId = serviceId;
        }
    }
}
