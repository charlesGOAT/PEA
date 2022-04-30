using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using HVK.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using System.IO;
using Newtonsoft.Json;
using System.Security.Claims;

namespace HVK.Controllers
{
    [Authorize(Roles="user,clerk")]

    public class ReservationsController : Controller
    {
        private readonly HVK_Team3Context _context;

        public ReservationsController(HVK_Team3Context context)
        {
            _context = context;
        }

        
        
        // GET: Reservations
        public async Task<IActionResult> Index()
        {
            return View(await _context.Reservations.ToListAsync());
        }

        // GET: Reservations/Details/5
  
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var reservation = await _context.Reservations
                .FirstOrDefaultAsync(m => m.ReservationId == id);
            if (reservation == null)
            {
                return NotFound();
            }

            return View(reservation);
        }

        // GET: Reservations/Creat e
       
        public IActionResult Create(int? id)
        {
            var CustId = id;
            if (CustId == null)
            {
                return RedirectToAction("Index", "Login");
            }
            TempData["TempCustId"] = CustId;
            TempData["ClerkCustomer"] = CustId;
            var customerPets = _context.Pets.Where(p => p.CustomerId == CustId).ToList();
            var petCheckedList = new Dictionary<int,bool>();
            foreach (var pet in customerPets)
            {
                petCheckedList.Add(pet.PetId,true);
            }
            var CreateResObj = new CreateReservationCheckDates_UI();
            CreateResObj.CustomerId = (int)id;
            CreateResObj.PetsList = customerPets;
            CreateResObj.PetChecked = petCheckedList;
           // CreateResObj.CustomerId = (int)CustId;

            return View(CreateResObj);
        }

        // POST: Reservations/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create(CreateReservationCheckDates_UI createReservationCheckData) {
            var data = createReservationCheckData;
            var CustId = createReservationCheckData.CustomerId;
            var pets = _context.Pets.Where(p => p.CustomerId == CustId).ToList();
            data.PetsList = pets;

            TempData["TempCustId"] = CustId;
            TempData["ClerkCustomer"] = CustId;

            var petSelectedBool = false;

            foreach (var petSelected in data.PetChecked)
            {
                if (petSelected.Value)
                {
                    petSelectedBool = true;
                }
            }

            if (ModelState.IsValid)
            {
                if (!petSelectedBool)
                {
                    TempData["CreateResDatesErrorMsg"] = "A pet must be selected";
                    return View(createReservationCheckData);
                }

                try
                {
                    var NumRuns = _context.Runs.ToList().Count();
                    var ReservationSpan = (((DateTime)createReservationCheckData.EndDate).Date).Subtract(((DateTime)createReservationCheckData.StartDate).Date);
                    int ReservationLength = (int)ReservationSpan.TotalDays;

                    bool isRoom = true;

                    for (int i = 0; i < ReservationLength - 1; i++)
                    {
                        var TodayReservationsCount = _context.PetReservations.Where(x => x.Reservation.StartDate < ((DateTime)createReservationCheckData.EndDate).Date && x.Reservation.EndDate >= ((DateTime)createReservationCheckData.StartDate).Date).ToList().Count();
                        if ((NumRuns - TodayReservationsCount) < pets.Count())
                        {
                            isRoom = false;
                        }
                    }

                    if (isRoom)
                    {
                        List<Pet> tempList = new();
                        Dictionary<int, bool> tempDictionary1 = new();
                        Dictionary<int, bool> tempDictionary2 = new();
                        foreach (var petSelected in data.PetChecked)
                        {
                            if (data.PetChecked[petSelected.Key])
                            {
                                Pet pet = _context.Pets.Where(p => p.CustomerId == CustId).Where(x=>x.PetId == petSelected.Key).First();
                                tempList.Add(pet);
                                tempDictionary1.Add(pet.PetId, false);
                                tempDictionary2.Add(pet.PetId, false);
                            }
                        }


                        return View("ServicesSummary", new CustomReservation(data.StartDate, data.EndDate, tempList, new List<int>(), new Dictionary<int, List<string>>(), new Dictionary<int, List<string>>(), tempDictionary1, tempDictionary2));
                    }
                    else
                    {
                        TempData["CreateResDatesErrorMsg"] = "Unfortunately, there is no availability for the selected dates";
                        return View(createReservationCheckData);
                    }

                }
                catch(Exception ex)
                {
                    return RedirectToAction(nameof(Index));
                }
                return RedirectToAction(nameof(Index));
            }
            return View(createReservationCheckData);


        }

        // GET: Reservations/Edit/5
       
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }
            var reservation = await _context.Reservations.Include(x=>x.PetReservations).ThenInclude(x=>x.Pet).ThenInclude(x=>x.Customer).Include(x=>x.PetReservations).ThenInclude(x=>x.PetReservationServices).Where(x=>x.ReservationId == id).FirstAsync();
            
            var customer = reservation.PetReservations.First().Pet.Customer.CustomerId;
            CustomReservation custom = new();
            custom.ReservationToCustom(reservation);
            custom.CustomerId = customer;

        TempData["TempCustId"] = custom.CustomerId;
            TempData["ClerkCustomer"] = custom.CustomerId ;
            if (reservation == null)
            {
                return NotFound();
            }
            return View(custom);
        }

        // POST: Reservations/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]

        public async Task<IActionResult> Edit(CustomReservation custom)
        {
            if (custom.Walk == null)
            {
                custom.Walk = new();
            }

            if (custom.Playtime == null)
            {
                custom.Playtime = new();
            }

            var data = custom;
            var CustId = custom.CustomerId;
            var pets = _context.Pets.Where(p => p.CustomerId == CustId).ToList();
            data.PetsList = pets;
            TempData["TempCustId"] = custom.CustomerId;
            TempData["ClerkCustomer"] = custom.CustomerId;
            var petSelectedBool = false;

            foreach (var petSelected in data.PetChecked)
            {
                if (petSelected.Value)
                {
                    petSelectedBool = true;
                }
            }

            if (ModelState.IsValid)
            {

                if (!petSelectedBool)
                {
                    TempData["CreateResDatesErrorMsg"] = "A pet must be selected";
                    return View(custom);
                }

                try
                {
                    var NumRuns = _context.Runs.ToList().Count();
                    var ReservationSpan = (((DateTime)custom.EndDate).Date).Subtract(((DateTime)custom.StartDate).Date);
                    int ReservationLength = (int)ReservationSpan.TotalDays;

                    bool isRoom = true;

                    for (int i = 0; i < ReservationLength - 1; i++)
                    {
                        var TodayReservationsCount = _context.PetReservations.Where(x => x.Reservation.StartDate < ((DateTime)custom.EndDate).Date && x.Reservation.EndDate >= ((DateTime)custom.StartDate).Date).ToList().Count();
                        if ((NumRuns - TodayReservationsCount) < pets.Count() && custom.EndDate!=_context.Reservations.Where(r=>r.ReservationId == custom.ReservationId).First().EndDate && custom.StartDate != _context.Reservations.Where(r => r.ReservationId == custom.ReservationId).First().StartDate)
                        {
                            isRoom = false;
                        }
                    }

                    if (isRoom)
                    {
                        List<Pet> tempList = new();
                        
                        foreach (var petSelected in data.PetChecked)
                        {
                            if (data.PetChecked[petSelected.Key])
                            {
                                if (_context.PetReservations.Include(x => x.Pet).Any(x => x.ReservationId == custom.ReservationId && x.Pet.PetId == petSelected.Key))
                                {
                                    custom.AssignToCustom(_context.Pets.Where(x => x.PetId == petSelected.Key).First());
                                    tempList.Add(_context.Pets.Where(x => x.PetId == petSelected.Key).First());
                                }
                                else
                                {
                                    Pet pet = _context.Pets.Where(p => p.CustomerId == CustId).Where(x => x.PetId == petSelected.Key).First();
                                    tempList.Add(pet);
                                  
                                    custom.Walk.Add(petSelected.Key,false);
                                    custom.Playtime.Add(petSelected.Key, false);
                                }
                                
                            }
                        }



                        return View("EditServices", new CustomReservation(data.StartDate, data.EndDate, tempList, (custom.PetIds ?? new List<int>()), (custom.MedicationNameList ?? new Dictionary<int, List<string>>()), (custom.MedicationDescriptionList ?? new Dictionary<int, List<string>>()), custom.Playtime, custom.Walk) {MedEndDate = custom.MedEndDate ?? new(), SpecialInstruct = custom.SpecialInstruct ?? new() , ReservationId = custom.ReservationId});
                    }
                    else
                    {
                        TempData["CreateResDatesErrorMsg"] = "Unfortunately, there is no availability for the selected dates";
                        return View(custom);
                    }

                }
                catch (Exception ex)
                {
                    return RedirectToAction(nameof(Index));
                }
                return RedirectToAction(nameof(Index));
            }
            return View(custom);

        }

        // GET: Reservations/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var reservation = await _context.Reservations
                .Where(x => x.ReservationId == id)
                .Include(x => x.PetReservations)
                .ThenInclude(x => x.Pet)
                .FirstOrDefaultAsync(m => m.ReservationId == id);
            if (reservation == null)
            {
                return NotFound();
            }

            return View(reservation);
        }

        // POST: Reservations/Delete/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Delete(Reservation reservation)
        {
            
            if (ModelState.IsValid)
            {
                try
                {
                    var id = reservation.ReservationId;

                    var res = await _context.Reservations.FindAsync(id);
                   

                    var petReservations = await _context.PetReservations.Where(r => r.ReservationId == id).ToListAsync();
                    var petReservationServices = await _context.PetReservationServices.Where(r => r.PetReservation.ReservationId == id).ToListAsync();
                    var petReservationDiscounts = await _context.PetReservationDiscounts.Where(r => r.PetReservation.ReservationId == id).ToListAsync();
                    var Medications = await _context.Medications.Where(r => r.PetReservation.ReservationId == id).ToListAsync();
                    var petId = petReservations.First().PetId;
                    var pet = _context.Pets.Where(x=>x.PetId == petId).First();
                    var custId = pet.CustomerId;
                    if (petReservationDiscounts != null)
                    {
                        foreach (var petResDisc in petReservationDiscounts)
                        {
                            _context.PetReservationDiscounts.Remove(petResDisc);
                            await _context.SaveChangesAsync();
                        }
                    }

                    if (petReservationServices != null)
                    {
                        foreach (var prs in petReservationServices)
                        {
                            _context.PetReservationServices.Remove(prs);
                            await _context.SaveChangesAsync();
                        }
                    }


                    if (Medications != null)
                    {
                        foreach (var md in Medications)
                        {
                            _context.Medications.Remove(md);
                            await _context.SaveChangesAsync();
                        }
                    }


                    if (petReservations != null)
                    {
                        foreach (var pr in petReservations)
                        {
                            _context.PetReservations.Remove(pr);
                            await _context.SaveChangesAsync();
                        }
                    }
                    _context.Reservations.Remove(res);
                    await _context.SaveChangesAsync();
                    return RedirectToAction("Index", "Home", new { id = custId });
                }
                catch
                {
                    return View(reservation);
                }
            }
            else
            {

                return View(reservation);
            }
            return RedirectToAction("Index", "Home");
        }

  
        public async Task<IActionResult> ServicesSummary(CustomReservation custom) {
           

            return View(custom);
        }

        [HttpPost]
        public async Task<IActionResult> AddMedication(CustomReservation custom, string sub) {
            //will have to fix
            custom.PetsList = _context.Pets.Where(x => custom.PetIds.Contains(x.PetId)).ToList();

            var custPets = _context.Pets.Where(x=>x.CustomerId == custom.CustomerId).ToList();

            custom.PetChecked = new();


            foreach (var pet in custPets) {
                if (custom.PetsList.Contains(pet))
                {
                    custom.PetChecked.Add(pet.PetId, true);
                }
                else {
                    custom.PetChecked.Add(pet.PetId, false);
                }
            }

            var userIdentity = (ClaimsIdentity)User.Identity;
            var claims = userIdentity.Claims;
            var roleClaimType = userIdentity.RoleClaimType;
            var role = claims.Where(c => c.Type == ClaimTypes.Role).ToList().First();
            var MyRole = role.Value;

            //int TempCustId = -1;
            //if (TempData.Peek("TempCustId") != null) {
            //    TempCustId = (int)TempData.Peek("TempCustId");
            //}

            if (sub == "back") {
                //start date, end date, petslist
                var obj = new CreateReservationCheckDates_UI() { StartDate = custom.StartDate, EndDate = custom.EndDate, PetChecked = custom.PetChecked, PetsList = custom.PetsList, CustomerId = custom.CustomerId};
                return View("Create", obj);
            }

            if (sub == "Submit")
            {
                bool flag = true;
                if (custom.MedicationDescriptionList != null)
                {
                    foreach (var Description in custom.MedicationDescriptionList)
                    {
                        var counter = 0;
                        foreach (var MyDescription in Description.Value)
                        {
                            if (String.IsNullOrWhiteSpace(MyDescription) || MyDescription.Length > 50 || String.IsNullOrWhiteSpace(custom.MedicationNameList[Description.Key][counter]) || custom.MedicationNameList[Description.Key][counter].Length > 50
                                || String.IsNullOrWhiteSpace(custom.SpecialInstruct[Description.Key][counter]) || custom.SpecialInstruct[Description.Key][counter].Length > 50)
                            {
                                flag = !flag;
                            }
                            ++counter;
                        }
                    }
                    if (!flag)
                    {

                        TempData["Error"] = "Be sure that the fields are filled out and less than 50 characters";
                        return View("ServicesSummary", custom);
                    }
                }
                var MyCustomObject = custom;
                TempData["CustomRes"] = JsonConvert.SerializeObject(MyCustomObject);
                return View("SummaryPage", MyCustomObject);
            }
            else if (sub == "Update")

            {
                bool flag = true;
                if (custom.MedicationDescriptionList != null)
                {
                    foreach (var Description in custom.MedicationDescriptionList)
                    {
                        var counter = 0;
                        foreach (var MyDescription in Description.Value)
                        {
                            if (String.IsNullOrWhiteSpace(MyDescription) || MyDescription.Length > 50 || String.IsNullOrWhiteSpace(custom.MedicationNameList[Description.Key][counter]) || custom.MedicationNameList[Description.Key][counter].Length > 50
                                || String.IsNullOrWhiteSpace(custom.SpecialInstruct[Description.Key][counter]) || custom.SpecialInstruct[Description.Key][counter].Length > 50)
                            {
                                flag = !flag;
                            }
                            ++counter;
                        }
                    }
                    if (!flag)
                    {
                        TempData["Error"] = "Be sure that the fields are filled out and less than 50 characters";
                        return View("EditServices", custom);
                    }

                }

                await custom.UpdateDatabase();

                if (MyRole == "clerk")
                {
                    return RedirectToAction("Index", "Clerk");
                }
                else
                {
                    return RedirectToAction("Index", "Home");

                }

            }
            
            else if (sub.Substring(0, 10).Contains("Add"))
            {
                var PetName = sub.Substring(19);

                custom.SelectedPet = _context.Pets.FirstOrDefault(x => x.Name == PetName).PetId;


                if (custom.MedicationDescriptionList != null)
                {
                    if (custom.MedicationDescriptionList.ContainsKey(custom.SelectedPet))
                    {
                        //will have to add more

                        custom.MedicationDescriptionList[custom.SelectedPet].Add("");
                        custom.MedicationNameList[custom.SelectedPet].Add("");
                        custom.SpecialInstruct[custom.SelectedPet].Add("");
                        custom.MedEndDate[custom.SelectedPet].Add((DateTime)custom.EndDate);

                    }
                    else
                    {
                        custom.AddToServices(custom.SelectedPet);
                    }
                }
                else
                {

                    custom.CreateObjs();
                    custom.AddToServices(custom.SelectedPet);
                }


                if (custom.ReservationId == 0)
                {
                    return View("ServicesSummary", custom);
                }
                else {
                    return View("EditServices", custom);
                }
            }

            else
            {

                var PetName = sub.Substring(22);
                custom.SelectedPet = _context.Pets.FirstOrDefault(x => x.Name == PetName).PetId;
                if (custom.MedicationDescriptionList != null)
                {
                    if (custom.MedicationDescriptionList.ContainsKey(custom.SelectedPet))
                    {
                        custom.MedicationDescriptionList[custom.SelectedPet].RemoveAt(custom.MedicationDescriptionList[custom.SelectedPet].Count - 1);
                        custom.MedicationNameList[custom.SelectedPet].RemoveAt(custom.MedicationNameList[custom.SelectedPet].Count - 1);
                        custom.MedEndDate[custom.SelectedPet].RemoveAt(custom.MedEndDate[custom.SelectedPet].Count - 1);
                        custom.SpecialInstruct[custom.SelectedPet].RemoveAt(custom.SpecialInstruct[custom.SelectedPet].Count - 1);
                    }

                    if (custom.ReservationId == 0)
                    {
                        return View("ServicesSummary", custom);
                    }
                    else
                    {
                        return View("EditServices", custom);
                    }
                }
                else
                {
                    custom.CreateObjs();

                }
                if (custom.ReservationId == 0)
                {
                    return View("ServicesSummary", custom);
                }
                else
                {
                    return View("EditServices", custom);
                }
            }

        }








        public async Task<IActionResult> SummaryPage() {

            CustomReservation custom = JsonConvert.DeserializeObject<CustomReservation>(TempData["CustomRes"].ToString());

            var userIdentity = (ClaimsIdentity)User.Identity;
            var claims = userIdentity.Claims;
            var roleClaimType = userIdentity.RoleClaimType;
            var role = claims.Where(c => c.Type == ClaimTypes.Role).ToList().First();
            var MyRole = role.Value;
            var scope_identity_petRes = new List<int>();


            var MyRes = new Reservation() { StartDate = custom.StartDate.Value, EndDate = custom.EndDate.Value, Status = 2 };
            _context.Reservations.Add(MyRes);
            await _context.SaveChangesAsync();
            var scope_identity = MyRes.ReservationId;



            foreach(var MyPet in custom.PetsList) {
                var PetRes = new PetReservation() { PetId = MyPet.PetId, ReservationId = scope_identity };
                _context.PetReservations.Add(PetRes);
                await _context.SaveChangesAsync();                
                scope_identity_petRes.Add(PetRes.PetReservationId);
                
                var PetResS = new PetReservationService() {PetReservationId = PetRes.PetReservationId, ServiceId = 1 };
                _context.PetReservationServices.Add(PetResS);
                await _context.SaveChangesAsync();

                if ( custom.Playtime[MyPet.PetId] ) {
                    var Playtime = new PetReservationService() { PetReservationId = PetRes.PetReservationId, ServiceId = 5 };
                    _context.PetReservationServices.Add(Playtime);
                    await _context.SaveChangesAsync();
                }

                if ( custom.Walk[MyPet.PetId] ) {
                    var Walk = new PetReservationService() { PetReservationId = PetRes.PetReservationId, ServiceId = 2 };
                    _context.PetReservationServices.Add(Walk);
                    await _context.SaveChangesAsync();
                }

                

            }
            if (custom.MedicationDescriptionList != null)
            {
                foreach (var PetResM in custom.MedicationDescriptionList)
                {
                    var counter = 0;

                    foreach (var InPetResM in PetResM.Value)
                    {


                        var tempMed = new Medication()
                        {
                            Dosage = PetResM.Value[counter],
                            EndDate = custom.MedEndDate[PetResM.Key][counter],
                            Name = custom.MedicationNameList[PetResM.Key][counter],
                            SpecialInstruct = custom.SpecialInstruct[PetResM.Key][counter],
                            PetReservationId = _context.PetReservations.Where(x => x.PetId == PetResM.Key).OrderBy(x => x.PetReservationId).Last().PetReservationId
                        };
                        _context.Medications.Add(tempMed);
                        await _context.SaveChangesAsync();
                        _context.ChangeTracker.Clear();
                        if (!_context.PetReservationServices.Any(x => x.PetReservationId == tempMed.PetReservationId && x.ServiceId == 4)) {
                            var Id = tempMed.PetReservationId;
                            var tempService = new PetReservationService() { ServiceId = 4, PetReservationId = Id };
                            _context.PetReservationServices.Add(tempService);
                            await _context.SaveChangesAsync();
                        }
                        ++counter;

                    }

                }
            }



            if (MyRole == "clerk")
            {
                return RedirectToAction("Index", "Clerk");
            }
            else
            {
                return RedirectToAction("Index", "Home");
               
            }

        }

        private bool ReservationExists(int id)
        {
            return _context.Reservations.Any(e => e.ReservationId == id);
        }

        public IActionResult backbutton(int? id)
        {
            TempData["btnid"] = id;
            if (id == 1)
            {
                return RedirectToAction("EditServices", "Reservations");
            }
            else if (id == 2)
            {
                return RedirectToAction("ServicesSummary", "Reservations");
            }
            else
            {
                return View("Index");
            }

        }
    }
}
