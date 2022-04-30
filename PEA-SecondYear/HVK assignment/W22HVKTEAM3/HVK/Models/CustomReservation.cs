using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;
namespace HVK.Models
{
    public class CustomReservation : CreateReservationCheckDates_UI
    {

        public Dictionary<int, List<string>> MedicationNameList { get; set; }

        public Dictionary<int, List<string>> MedicationDescriptionList { get; set; }
        public List<int> PetIds { get; set; }
        public int SelectedPet { get; set; }
        public Dictionary<int, bool> Playtime { get; set; }
        public Dictionary<int, bool> Walk { get; set; }
        public Dictionary<int, List<DateTime>> MedEndDate { get; set; }
        public Dictionary<int, List<string>> SpecialInstruct { get; set; }

        public int ReservationId { get; set; }


        public CustomReservation()
        {
        }
        public CustomReservation(DateTime? startDate, DateTime? endDate, List<Pet> petsList, List<int> petIds, Dictionary<int, List<string>> medicationNameList, Dictionary<int, List<string>> medicationDescriptionList) : base(startDate, endDate, petsList)
        {
            StartDate = startDate;
            EndDate = endDate;
            PetsList = petsList;
            PetIds = petIds;
            MedicationNameList = medicationNameList;
            MedicationDescriptionList = medicationDescriptionList;

        }
        public CustomReservation(DateTime? startDate, DateTime? endDate, List<Pet> petsList, List<int> petIds, Dictionary<int, List<string>> medicationNameList, Dictionary<int, List<string>> medicationDescriptionList, int selectedPet) : base(startDate, endDate, petsList)
        {
            StartDate = startDate;
            EndDate = endDate;
            PetsList = petsList;
            PetIds = petIds;
            MedicationNameList = medicationNameList;
            MedicationDescriptionList = medicationDescriptionList;
            SelectedPet = selectedPet;
        }

        public CustomReservation(DateTime? startDate, DateTime? endDate, List<Pet> petsList, List<int> petIds, Dictionary<int, List<string>> medicationNameList, Dictionary<int, List<string>> medicationDescriptionList, Dictionary<int, bool> playtime, Dictionary<int, bool> walk)
        {

            StartDate = startDate;
            EndDate = endDate;
            PetsList = petsList;
            MedicationNameList = medicationNameList;
            MedicationDescriptionList = medicationDescriptionList;
            PetIds = petIds;
            Playtime = playtime;
            Walk = walk;
        }


        public void CreateObjs()
        {
            this.MedicationDescriptionList = new();
            this.MedicationNameList = new();
            this.SpecialInstruct = new();
            this.MedEndDate = new();
        }
        public void AddToServices(int id)
        {
            this.MedicationDescriptionList.Add(id, new List<string>() { "" });
            this.MedicationNameList.Add(id, new List<string>() { "" });
            this.SpecialInstruct.Add(id, new List<string>() { "" });
            this.MedEndDate.Add(id, new List<DateTime>() { (DateTime)this.EndDate });
        }

        public void ReservationToCustom(Reservation res)
        {

            var PetReservations = res.PetReservations;
            this.ReservationId = res.ReservationId;
            this.StartDate = res.StartDate;
            this.EndDate = res.EndDate;
            PetsList = new();
            PetIds = new();
            PetChecked = new();
            Walk = new();
            Playtime = new();
            this.CreateObjs();

            HVK_Team3Context _context = new();
            var petCustomer = _context.Pets.Include(x => x.Customer).Where(x => res.PetReservations.First().PetId == x.PetId).First();
            var resPets1 = _context.Reservations.Where(x => x.ReservationId == ReservationId).Include(x=>x.PetReservations).ThenInclude(x=>x.Pet).ToList();
            List<Pet> resPets = new();
            foreach (var item in resPets1) {
                foreach (var MyPet in item.PetReservations)
                {
                    resPets.Add(MyPet.Pet);
                }
            }

            var AllPets = _context.Pets.Where(x => x.CustomerId == petCustomer.Customer.CustomerId).ToList();





            foreach (var Pet in AllPets)
            {
                this.PetsList.Add(Pet);

                if (resPets.Contains(Pet))
                {
                    this.PetChecked.Add(Pet.PetId, true);
                }
                else
                {
                    this.PetChecked.Add(Pet.PetId, false);
                }



            }
        }

        public void AssignToCustom(Pet PetsReservation)
        {

            HVK_Team3Context _context = new();
            var ThePetReservation = _context.PetReservations.Where(x => x.ReservationId == ReservationId && x.PetId == PetsReservation.PetId).Include(x => x.Pet).Include(x => x.PetReservationServices).ThenInclude(x => x.Service).Include(x => x.Medications).First();
            var PetReservationServices = _context.PetReservationServices.Where(x=>x.PetReservationId == ThePetReservation.PetReservationId).ToList();
          


            if (PetReservationServices.Any(x => x.ServiceId == 2))
            {
                this.Walk[PetsReservation.PetId] = true;
            }
            else
            {
                this.Walk[PetsReservation.PetId] = false;
            }
            if (PetReservationServices.Any(x => x.ServiceId == 5))
            {
                this.Playtime[PetsReservation.PetId] = true;
            }
            else
            {
                this.Playtime[PetsReservation.PetId] = false;
            }
            if (MedicationDescriptionList==null) {
                this.CreateObjs();
               
            }
            this.MedicationDescriptionList.Add(ThePetReservation.PetId, new List<string>());
            this.MedicationNameList.Add(ThePetReservation.PetId, new List<string>());
            this.SpecialInstruct.Add(ThePetReservation.PetId, new List<string>());
            this.MedEndDate.Add(ThePetReservation.PetId, new List<DateTime>());
            foreach (var Medication in ThePetReservation.Medications)
            {

                this.MedEndDate[ThePetReservation.PetId].Add((DateTime)Medication.EndDate);
                this.MedicationDescriptionList[ThePetReservation.PetId].Add(Medication.Dosage);
                this.MedicationNameList[ThePetReservation.PetId].Add(Medication.Name);
                this.SpecialInstruct[ThePetReservation.PetId].Add(Medication.SpecialInstruct);


            }
        }

        public async Task<bool> UpdateDatabase()
        {
            var id = this.ReservationId;
            HVK_Team3Context _context = new();

            var Reservation = _context.Reservations.Where(r => r.ReservationId == id).Include(x => x.PetReservations).ThenInclude(x => x.PetReservationServices).Include(x => x.ReservationDiscounts).Include(x => x.PetReservations).ThenInclude(x => x.PetReservationServices).Include(x => x.PetReservations).ThenInclude(x => x.Medications).AsNoTracking().First();


            foreach (var Medications in Reservation.PetReservations)
            {
                if (Medications != null)
                {
                    foreach (var md in Medications.Medications)
                    {
                        _context.Medications.Remove(md);
                       
                        await _context.SaveChangesAsync();
                    }
                }
            }

            foreach (var petReservationDiscounts in Reservation.PetReservations) {
                if (petReservationDiscounts != null)
                {
                    foreach (var petResDisc in petReservationDiscounts.PetReservationDiscounts)
                    {
                        _context.PetReservationDiscounts.Remove(petResDisc);

                        await _context.SaveChangesAsync();
                    }
                }
            }

            foreach(var petReservationServices in Reservation.PetReservations)
            if (petReservationServices != null)
            {
                foreach (var prs in petReservationServices.PetReservationServices)
                {
                    _context.PetReservationServices.Remove(prs);
                    
                    await _context.SaveChangesAsync();
                }
            }
           

            foreach (var petReservations in Reservation.PetReservations ) {
                if (petReservations != null)
                {
                    foreach (var pr in Reservation.PetReservations)
                    {
                        _context.PetReservations.Remove(pr);
                        
                        await _context.SaveChangesAsync();
                    }
                }
            }
         
            _context.Reservations.Remove(Reservation);
            await _context.SaveChangesAsync();





            var MyRes = new Reservation() { StartDate = this.StartDate.Value, EndDate = this.EndDate.Value, Status = 2 };
            _context.Reservations.Add(MyRes);
            await _context.SaveChangesAsync();
            var scope_identity = MyRes.ReservationId;



            foreach (var MyPet in this.PetsList)
            {
                var PetRes = new PetReservation() { PetId = MyPet.PetId, ReservationId = scope_identity };
                _context.PetReservations.Add(PetRes);
                await _context.SaveChangesAsync();

                var PetResS = new PetReservationService() { PetReservationId = PetRes.PetReservationId, ServiceId = 1 };
                _context.PetReservationServices.Add(PetResS);
                await _context.SaveChangesAsync();

                if (this.Playtime[MyPet.PetId])
                {
                    var Playtime = new PetReservationService() { PetReservationId = PetRes.PetReservationId, ServiceId = 5 };
                    _context.PetReservationServices.Add(Playtime);
                    await _context.SaveChangesAsync();
                }

                if (this.Walk[MyPet.PetId])
                {
                    var Walk = new PetReservationService() { PetReservationId = PetRes.PetReservationId, ServiceId = 2 };
                    _context.PetReservationServices.Add(Walk);
                    await _context.SaveChangesAsync();
                }



            }
            if (this.MedicationDescriptionList != null)
            {
                foreach (var PetResM in this.MedicationDescriptionList)
                {
                    var counter = 0;

                    foreach (var InPetResM in PetResM.Value)
                    {


                        var tempMed = new Medication()
                        {
                            Dosage = PetResM.Value[counter],
                            EndDate = this.MedEndDate[PetResM.Key][counter],
                            Name = this.MedicationNameList[PetResM.Key][counter],
                            SpecialInstruct = this.SpecialInstruct[PetResM.Key][counter],
                            PetReservationId = _context.PetReservations.Where(x => x.PetId == PetResM.Key).AsNoTracking().OrderBy(x => x.PetReservationId).Last().PetReservationId
                        };
                        _context.Medications.Add(tempMed);
                        await _context.SaveChangesAsync();
                        _context.ChangeTracker.Clear();
                        if (_context.PetReservationServices.Any(x => x.PetReservationId == tempMed.PetReservationId && x.ServiceId == 4))
                        {
                            var tempService = new PetReservationService() { ServiceId = 4, PetReservationId = tempMed.PetReservationId };
                            _context.PetReservationServices.Add(tempService);
                            await _context.SaveChangesAsync();
                        }
                        ++counter;

                    }

                }
            }






            return true;
        }
    
    }
}
