using System;
using System.Collections.Generic;

#nullable disable

namespace HVK.Models
{
    public partial class Run
    {
        public Run()
        {
            PetReservations = new HashSet<PetReservation>();
        }

        public int RunId { get; set; }
        public string Size { get; set; }
        public bool Covered { get; set; }
        public string Location { get; set; }
        public decimal? Status { get; set; }
        public virtual ICollection<PetReservation> PetReservations { get; set; }
        //public List<Run> AllRuns { get; set; }

        public Run(int runId, string size, bool covered, string location, decimal? status)
        {
            RunId = runId;
            Size = size;
            Covered = covered;
            Location = location;
            Status = status;
        }
    }
}
