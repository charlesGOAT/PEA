using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Http;
using HVK.Models;
using Microsoft.Extensions.Logging;
using Microsoft.AspNetCore.Authorization;
using Newtonsoft.Json;

namespace HVK.Controllers
{
    [Authorize(Roles="clerk")]
    public class ClerkController : Controller
    {
        private readonly HVK_Team3Context _context;

        public ClerkController(HVK_Team3Context context)
        {
            _context = context;
        }
        public async Task<IActionResult> Index(int? id, string searchString)
        {
            //var dbquery = from r in _context.Runs select r;
            var allRuns = _context.Runs.ToList();
            List<Run> RunList = new List<Run>();

            DateTime dt2 = new DateTime(2022, 06, 6);
            var apr = _context.PetReservations.Where(x => x.Reservation.StartDate < DateTime.Now && (x.Reservation.EndDate > DateTime.Now)).ToList();
            var allRes = _context.Reservations.Include(x=>x.PetReservations).ThenInclude(x=>x.Pet).ThenInclude(x=>x.Customer).ToList();
            var allPetRes = _context.PetReservations.Include(x=>x.Reservation).ThenInclude(x=>x.PetReservations).ThenInclude(x=>x.Pet).ThenInclude(x=>x.Customer).ToList();
            //var currentReservations = _context.Reservations.Where(x=>x.Status == 3).OrderBy(x=>x.EndDate).ToList();
            var futureReservations = _context.Reservations.Where(x=>x.EndDate.Date >= DateTime.Today.Date && x.Status == 2).OrderBy(x => x.StartDate).ToList();
            var allCustomers = _context.Customers.ToList();
            List<PetReservation> PetResList = new List<PetReservation>();
            List<Pet> PetsList = new List<Pet>();
            List<Reservation> FutureResList = new List<Reservation>();
            var allPetsList = _context.Pets.ToList();
            List<Reservation> ResList = new List<Reservation>();

            List<Customer> CustList = new List<Customer>();

            foreach (var c in allCustomers) {
                CustList.Add(new Customer(c.CustomerId, c.FirstName, c.LastName, c.Phone, c.Email));
            }

            foreach (var run in allRuns)
            {
                Run runobj = new Run(run.RunId, run.Size, run.Covered, run.Location, run.Status);
                RunList.Add(runobj);
            }

            foreach (var petRes in allPetRes)
            {
                PetReservation allPetResObj = new PetReservation(petRes.PetReservationId, petRes.PetId, petRes.ReservationId, petRes.RunId);
                PetResList.Add(allPetResObj);
            }

            foreach (var pet in allPetsList)
            {
                Pet petObj = new Pet();
                petObj.Name = pet.Name;
                petObj.PetId = pet.PetId;
                petObj.CustomerId = pet.CustomerId;
                PetsList.Add(petObj);
            }

            foreach (var res in allRes) {
                Reservation allResObj = new Reservation(res.ReservationId, res.StartDate, res.EndDate, res.Status);
                ResList.Add(allResObj);
            }

            foreach (var fr in futureReservations) {
                FutureResList.Add(new Reservation(fr.ReservationId, fr.StartDate, fr.EndDate, fr.Status));
            }

            string RunListString = JsonConvert.SerializeObject(RunList);
            ViewBag.Runs = RunListString;

            string PetResListString = JsonConvert.SerializeObject(PetResList);
            ViewBag.PetRes = PetResListString;

            string AllPetsListString = JsonConvert.SerializeObject(PetsList);
            ViewBag.AllPets = AllPetsListString;

            string ResListString = JsonConvert.SerializeObject(ResList);
            ViewBag.AllReservations = ResListString;

            string FutureResListString = JsonConvert.SerializeObject(FutureResList);
            ViewBag.FutureReservations = FutureResListString;


            string CustListString = JsonConvert.SerializeObject(CustList);
            ViewBag.CustomersList = CustListString;

            var customers = from c in _context.Customers select c;
            var info = await _context.Customers.Include(x => x.Pets).ThenInclude(y => y.PetReservations).ThenInclude(z => z.Reservation).Include(t => t.Pets).ThenInclude(u => u.PetReservations).ThenInclude(u => u.Run).ToListAsync();

            if (!String.IsNullOrEmpty(searchString))
            {
                //info = (List<Customer>)info.Where(s => s.LastName!.Contains(searchString));
                var pets = _context.Pets.Include(x => x.Customer).Where(x => x.Name.Contains(searchString) ||  x.Customer.LastName.Contains(searchString) || x.Customer.FirstName.Contains(searchString) || x.Customer.Phone.Contains(searchString) || x.Customer.Email.Contains(searchString)).OrderBy(x => x.Customer.LastName).ThenBy(x => x.Customer.FirstName).ToList();
                var custs = _context.Customers.Where(x => (x.Pets.Count == 0) &&  ( x.LastName.Contains(searchString) || x.FirstName.Contains(searchString) || x.Phone.Contains(searchString) || x.Email.Contains(searchString))).OrderBy(x => x.LastName).ThenBy(x => x.FirstName).ToList();
                var custidlist = new List<int>();
                var custSearchMatches = new List<Customer>();

                foreach (var pet in pets) {
                    if (!custidlist.Contains(pet.Customer.CustomerId)) {
                        custidlist.Add(pet.Customer.CustomerId);
                        custSearchMatches.Add(pet.Customer);
                    }

                }
                foreach (var cust in custs) {
                    custSearchMatches.Add(cust);
                }

                //customers = customers.Include(x=>x.Pets).Where(x => x.LastName!.Contains(searchString) || (x.FirstName!.Contains(searchString)) || (x.Phone!.Contains(searchString)) || (x.Email!.Contains(searchString)));
                return View("Search",  custSearchMatches);
            } else if (id == -1)
            {
                TempData["resbtnId"] = id;
                var custSearchMatches = new List<Customer>();
                return View("Search", custSearchMatches);
            }else
            {
                return View(allRes);
                //return View(await customers.ToListAsync());
            }
        }
        [HttpPost]
        public string Index(string searchString, bool notUsed)
        {
            return "From [HttpPost]Index: filter on " + searchString;
        }


        public async Task<IActionResult> StartPetVisit(int? id)
        {

            var lastAction = "";
            if (TempData.Peek("LastAction") != null) {
                lastAction = TempData["LastAction"].ToString();
            
            }
            
            TempData["LastAction"] = "StartPetVisit";
            TempData["Id"] = id;
            var startvisit = _context.Reservations.Include(x => x.PetReservations).ThenInclude(x => x.Pet).ThenInclude(p=>p.Customer).Where(x => x.ReservationId == id).FirstOrDefault();
            var Customer = startvisit.PetReservations.First().Pet.Customer;
            var CustObj = new Customer(Customer.CustomerId, Customer.FirstName, Customer.LastName, Customer.Phone, Customer.Email);

            List<Pet> PetsList = new List<Pet>();
            var allPetsList = _context.Pets.ToList();
            foreach (var pet in allPetsList)
            {
                Pet petObj = new Pet();
                petObj.Name = pet.Name;
                petObj.PetId = pet.PetId;
                petObj.DogSize = pet.DogSize;
                PetsList.Add(petObj);
            }
            string AllPetsListString = JsonConvert.SerializeObject(PetsList);
            ViewBag.AllPets = AllPetsListString;
            
            string CustomerString = JsonConvert.SerializeObject(CustObj);
            ViewBag.CustomerObjString = CustomerString;
            return View(startvisit);
        }

        public async Task<IActionResult> ConfirmStartVisit(int? id)
        {
            var reservation = _context.Reservations.Include(x => x.PetReservations).ThenInclude(x => x.Pet).ThenInclude(p => p.Customer).Where(x => x.ReservationId == id).FirstOrDefault();
            if (ModelState.IsValid)
            {
                try
                {
                    reservation.Status = 3;
                    _context.Update(reservation);
                    await _context.SaveChangesAsync();
                }
                catch
                {
                    return View();
                }
            }
            return RedirectToAction("Index", "Clerk");
        }

        public async Task<IActionResult> ConfirmEndVisit(int? id)
        {
            var endvisit = _context.Reservations.Include(x => x.PetReservations).ThenInclude(x => x.Pet).ThenInclude(p => p.Customer).Where(x => x.ReservationId == id).Include(x => x.PetReservations).ThenInclude(x => x.PetReservationServices).ThenInclude(x => x.Service).ThenInclude(x => x.DailyRates).First();
            if (ModelState.IsValid)
            {
                try
                {
                    endvisit.Status = 5;
                    _context.Update(endvisit);
                    await _context.SaveChangesAsync();
                }
                catch
                {
                    return View();
                }
            }
            return RedirectToAction("Index", "Clerk");
        }

        public IActionResult Search(string searchString)
        {
                return View();
        }

        public async Task<IActionResult> Contract(int? id) {
            int ReservationId = (int) id;

            var reservations = _context.Reservations.Include(x=>x.PetReservations).ThenInclude(x=>x.PetReservationServices).ThenInclude(x=>x.Service).Where(x=>x.ReservationId == ReservationId).First();
            var pets = _context.Reservations.Include(x=>x.PetReservations).ThenInclude(x=>x.Pet).ThenInclude(x=>x.Customer).Where(x=>x.ReservationId == ReservationId).First();
            var petResDiscs = _context.Reservations.Include(x=>x.PetReservations).ThenInclude(x=>x.PetReservationDiscounts).ThenInclude(x=>x.Discount).Where(x=>x.ReservationId == ReservationId).First();
            var resDiscs = _context.Reservations.Include(x=>x.ReservationDiscounts).ThenInclude(x=>x.Discount).Where(x=>x.ReservationId == ReservationId).First();
            var servicesList = _context.Services.ToList();
            var DailyRates = _context.DailyRates.ToList();
            var allDiscounts = _context.Discounts.ToList();


            var DiscountsList = new List<Discount>();
            var DailyRatesList = new List<DailyRate>();
            var PetsList = new List<Pet>();
            var PetReservationDiscountsList = new List<PetReservationDiscount>();
            var PetReservationServicesList = new List<PetReservationService>();
            var ReservationDiscounts = new List<ReservationDiscount>();
            var Services = new List<Service>();
            foreach (var pet in pets.PetReservations) {
                PetsList.Add(new Pet(pet.PetId, pet.Pet.Name, pet.Pet.Gender, pet.Pet.Breed, pet.Pet.Birthyear, pet.Pet.CustomerId, pet.Pet.DogSize, pet.Pet.Climber, pet.Pet.Barker, pet.Pet.SpecialNotes));
            }

            foreach (var pr in petResDiscs.PetReservations) {
                foreach (var prd in pr.PetReservationDiscounts) {
                    PetReservationDiscountsList.Add(new PetReservationDiscount(prd.DiscountId, prd.PetReservationId) );
                }
            }

            foreach (var pr in reservations.PetReservations)
            {
                foreach (var prs in pr.PetReservationServices)
                {
                    PetReservationServicesList.Add(new PetReservationService( prs.PetReservationId, prs.ServiceId));
                }
            }

            foreach (var pr in resDiscs.ReservationDiscounts) {
                ReservationDiscounts.Add(new ReservationDiscount(pr.DiscountId, pr.ReservationId));
            }


            foreach (var dr in DailyRates) {
                DailyRatesList.Add(new DailyRate(dr.DailyRateId, dr.Rate, dr.DogSize, dr.ServiceId ));
            }

            foreach (var s in servicesList) {
                Services.Add(new Service(s.ServiceId, s.ServiceDescription));
            }

            foreach (var d in allDiscounts) {
                DiscountsList.Add(new Discount(d.DiscountId, d.Desciption, d.Percentage, d.Type));
            }


            string PetsListString = JsonConvert.SerializeObject(PetsList);
            ViewBag.PetsListObj = PetsListString;

            string PetResDiscListString = JsonConvert.SerializeObject(PetReservationDiscountsList);
            ViewBag.PetResDiscsObj = PetResDiscListString;

            string PetResServsListString = JsonConvert.SerializeObject(PetReservationServicesList);
            ViewBag.PetResServsObj = PetResServsListString;

            string ReservationDiscountsString = JsonConvert.SerializeObject(ReservationDiscounts);
            ViewBag.ReservationDiscountsObj = ReservationDiscountsString;


            string DailyRatesString = JsonConvert.SerializeObject(DailyRatesList);
            ViewBag.DailyRates = DailyRatesString;

            string DiscountsString = JsonConvert.SerializeObject(DiscountsList);
            ViewBag.Discounts = DiscountsString;



            return View(reservations);
        }

        public async Task<IActionResult> EndPetVisit(int? id)
        {
            //var endvisit = _context.Reservations.Include(x => x.PetReservations).ThenInclude(x => x.Pet).ThenInclude(p => p.Customer).Where(x => x.ReservationId == id).Include(x => x.PetReservations).ThenInclude(x => x.PetReservationServices).ThenInclude(x => x.Service).ThenInclude(x => x.DailyRates).FirstOrDefault();
            //var Customer = endvisit.PetReservations.FirstOrDefault().Pet.Customer;
            //var CustObj = new Customer(Customer.CustomerId, Customer.FirstName, Customer.LastName, Customer.Phone, Customer.Email);
            //string CustomerString = JsonConvert.SerializeObject(CustObj);
            //ViewBag.CustomerObjString = CustomerString;
            //return View(endvisit);

            int ReservationId = (int)id;

            var reservations = _context.Reservations.Include(x => x.PetReservations).ThenInclude(x => x.PetReservationServices).ThenInclude(x => x.Service).Where(x => x.ReservationId == ReservationId).First();
            var pets = _context.Reservations.Include(x => x.PetReservations).ThenInclude(x => x.Pet).ThenInclude(x => x.Customer).Where(x => x.ReservationId == ReservationId).First();
            var petResDiscs = _context.Reservations.Include(x => x.PetReservations).ThenInclude(x => x.PetReservationDiscounts).ThenInclude(x => x.Discount).Where(x => x.ReservationId == ReservationId).First();
            var resDiscs = _context.Reservations.Include(x => x.ReservationDiscounts).ThenInclude(x => x.Discount).Where(x => x.ReservationId == ReservationId).First();
            var servicesList = _context.Services.ToList();
            var DailyRates = _context.DailyRates.ToList();
            var allDiscounts = _context.Discounts.ToList();
            var Customer = reservations.PetReservations.FirstOrDefault().Pet.Customer;
            var CustObj = new Customer(Customer.CustomerId, Customer.FirstName, Customer.LastName, Customer.Phone, Customer.Email);

            string CustomerString = JsonConvert.SerializeObject(CustObj);
            ViewBag.CustomerObjString = CustomerString;

            var DiscountsList = new List<Discount>();
            var DailyRatesList = new List<DailyRate>();
            var PetsList = new List<Pet>();
            var PetReservationDiscountsList = new List<PetReservationDiscount>();
            var PetReservationServicesList = new List<PetReservationService>();
            var ReservationDiscounts = new List<ReservationDiscount>();
            var Services = new List<Service>();
            foreach (var pet in pets.PetReservations)
            {
                PetsList.Add(new Pet(pet.PetId, pet.Pet.Name, pet.Pet.Gender, pet.Pet.Breed, pet.Pet.Birthyear, pet.Pet.CustomerId, pet.Pet.DogSize, pet.Pet.Climber, pet.Pet.Barker, pet.Pet.SpecialNotes));
            }

            foreach (var pr in petResDiscs.PetReservations)
            {
                foreach (var prd in pr.PetReservationDiscounts)
                {
                    PetReservationDiscountsList.Add(new PetReservationDiscount(prd.DiscountId, prd.PetReservationId));
                }
            }

            foreach (var pr in reservations.PetReservations)
            {
                foreach (var prs in pr.PetReservationServices)
                {
                    PetReservationServicesList.Add(new PetReservationService(prs.PetReservationId, prs.ServiceId));
                }
            }

            foreach (var pr in resDiscs.ReservationDiscounts)
            {
                ReservationDiscounts.Add(new ReservationDiscount(pr.DiscountId, pr.ReservationId));
            }


            foreach (var dr in DailyRates)
            {
                DailyRatesList.Add(new DailyRate(dr.DailyRateId, dr.Rate, dr.DogSize, dr.ServiceId));
            }

            foreach (var s in servicesList)
            {
                Services.Add(new Service(s.ServiceId, s.ServiceDescription));
            }

            foreach (var d in allDiscounts)
            {
                DiscountsList.Add(new Discount(d.DiscountId, d.Desciption, d.Percentage, d.Type));
            }


            string PetsListString = JsonConvert.SerializeObject(PetsList);
            ViewBag.PetsListObj = PetsListString;

            string PetResDiscListString = JsonConvert.SerializeObject(PetReservationDiscountsList);
            ViewBag.PetResDiscsObj = PetResDiscListString;

            string PetResServsListString = JsonConvert.SerializeObject(PetReservationServicesList);
            ViewBag.PetResServsObj = PetResServsListString;

            string ReservationDiscountsString = JsonConvert.SerializeObject(ReservationDiscounts);
            ViewBag.ReservationDiscountsObj = ReservationDiscountsString;

            string ServicesString = JsonConvert.SerializeObject(Services);
            ViewBag.Services = ServicesString;

            string DailyRatesString = JsonConvert.SerializeObject(DailyRatesList);
            ViewBag.DailyRates = DailyRatesString;

            string DiscountsString = JsonConvert.SerializeObject(DiscountsList);
            ViewBag.Discounts = DiscountsString;



            return View(reservations);
        }

        public IActionResult RunStatus(int? id)
        {
            var Run = _context.Runs.Where(x => x.RunId == id).FirstOrDefault();
            return View(Run);
        }

        public async Task<IActionResult> ConfirmRunStatus(string answer, int? id)
        {
            var Run = _context.Runs.Where(x => x.RunId == id).FirstOrDefault();
            if(answer == "Available")
            {
                if (ModelState.IsValid)
                {
                    try
                    {
                        Run.Status = 1;
                        _context.Update(Run);
                        await _context.SaveChangesAsync();
                    }
                    catch
                    {
                        return View();
                    }
                }

            } else if(answer == "Needs Cleaning")
            {
                if (ModelState.IsValid)
                {
                    try
                    {
                        Run.Status = 2;
                        _context.Update(Run);
                        await _context.SaveChangesAsync();
                    }
                    catch
                    {
                        return View();
                    }
                }
            } else if (answer == "Needs Maintenance")
            {
                if (ModelState.IsValid)
                {
                    try
                    {
                        Run.Status = 3;
                        _context.Update(Run);
                        await _context.SaveChangesAsync();
                    }
                    catch
                    {
                        return View();
                    }
                }
            } else
            {
                return RedirectToAction("Index", "Clerk");
            }

            return RedirectToAction("Index", "Clerk");
        }

        public IActionResult AssignPet(int? id)
        {
            var pets = _context.PetReservations.Include(x => x.Pet).Where(x => x.Reservation.Status == 3 && x.RunId == null).ToList();
            var Run = _context.Runs.Where(x => x.RunId == id).FirstOrDefault();
            string runId = JsonConvert.SerializeObject(Run);
            ViewBag.runId = runId;
            return View(pets);
        }

        public async Task<IActionResult> ConfirmAssignPet(int? petResId, int? runId)
        {
            var petres = _context.PetReservations.Where(x => x.PetReservationId == petResId).FirstOrDefault();
            if (ModelState.IsValid)
            {
                try
                {
                    petres.RunId = runId;
                    _context.Update(petres);
                    await _context.SaveChangesAsync();
                }
                catch
                {
                    return View();
                }
            }
            return RedirectToAction("Index", "Clerk");
        }

        public async Task<IActionResult> UnassignPet(int? runId)
        {
            var pets = _context.PetReservations.Include(x => x.Pet).Where(x => x.RunId == runId).ToList();
            var Run = _context.Runs.Where(x => x.RunId == runId).FirstOrDefault();
            //string runid = JsonConvert.SerializeObject(Run);
            //ViewBag.runId = runId;
            var petres = _context.PetReservations.Where(x => x.RunId == runId && x.Reservation.Status == 3).FirstOrDefault();
            if (ModelState.IsValid)
            {
                try
                {
                    petres.RunId = null;
                    _context.Update(petres);
                    await _context.SaveChangesAsync();
                }
                catch
                {
                    return View();
                }
            }
            return RedirectToAction("Index", "Clerk");
        }
    }
}
