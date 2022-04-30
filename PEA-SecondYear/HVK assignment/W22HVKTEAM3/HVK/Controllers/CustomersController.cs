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
using System.Security.Claims;

namespace HVK.Controllers
{
   
    public class CustomersController : Controller
    {
        private readonly HVK_Team3Context _context;
        private readonly ILogger<HomeController> _logger;
        const string SessionCustId = "_CustId";

        public CustomersController(HVK_Team3Context context)
        {
            _context = context;
        }

        // GET: Customers
        public async Task<IActionResult> Index()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> Index(Customer customer)
        {

            if (!ModelState.IsValid)
                return View(customer);
            else
            {
                try
                {
                    dynamic MyRole = null;


                    var userIdentity = (ClaimsIdentity)User.Identity;
                    var claims = userIdentity.Claims;
                    if (claims.Count() != 0)
                    {
                        var roleClaimType = userIdentity.RoleClaimType;
                        var role = claims.Where(c => c.Type == ClaimTypes.Role).ToList().First();
                        MyRole = role.Value;
                    }

                        _context.Add(customer);
                    await _context.SaveChangesAsync();
                    TempData["SuccessfulChange"] = "Your account was successfully created.";
                    if (MyRole == null)
                    {

                        return RedirectToAction("Index", "Login");
                    }
                    else if (MyRole == "user")
                    {
                        return RedirectToAction("Index", "Home");
                    }
                    else { 
                    return RedirectToAction("Index", "Clerk");
                    }
                }
                catch
                {
                    return View(customer);
                }
            }
        }


        [Authorize(Roles = "user,clerk")]

        // GET: Customers/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            var userIdentity = (ClaimsIdentity)User.Identity;
            var claims = userIdentity.Claims;
            var roleClaimType = userIdentity.RoleClaimType;
            var role = claims.Where(c => c.Type == ClaimTypes.Role).ToList().First();
            var MyRole = role.Value;
            var CustId = HttpContext.Session.GetInt32(SessionCustId);

            


            if (CustId == null || CustId < 0)
            {
                return RedirectToAction("Index", "Login");
            }
            else if (MyRole == "clerk")
            {
                var data = _context.Customers.Where(c => c.CustomerId == id).Include(x => x.Pets).ThenInclude(y => y.PetReservations).ThenInclude(z => z.Reservation).FirstOrDefault();
                return View(data);
            } else
            {
                var customer = _context.Customers.Where(c => c.CustomerId == CustId).First();
                return View(customer);
            }

        }

        // POST: Customers/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Roles = "user,clerk")]

        public async Task<IActionResult> Edit(Customer customer, int? id)
        {
            var userIdentity = (ClaimsIdentity)User.Identity;
            var claims = userIdentity.Claims;
            var roleClaimType = userIdentity.RoleClaimType;
            var role = claims.Where(c => c.Type == ClaimTypes.Role).ToList().First();
            var MyRole = role.Value;
            var CustId = HttpContext.Session.GetInt32("_CustId");
            var ResId = -1;

            var ClerkCustomerId = -1;
            if (TempData.Peek("ClerkCustomer") != null)
            {
                ClerkCustomerId = (int)TempData.Peek("ClerkCustomer");
            }


            var lastAction = "";

            if (TempData.Peek("LastAction") != null) {
                lastAction = TempData["LastAction"].ToString();
            }

           

            if (TempData.Peek("Id") != null) {
                ResId = (int)TempData.Peek("Id");
                    
            }

            if ((CustId == null || CustId < 0) && MyRole == "user")
            {
                return RedirectToAction("Index", "Login");
            }
            if (ModelState.IsValid)
            {
                try
                {

                    
                    _context.Update(customer);
                    await _context.SaveChangesAsync();
                    TempData["SuccessfulChange"] = "Your account information was successfully updated.";
                    if (MyRole == "clerk" && lastAction == "StartPetVisit")
                    {
                        var data = _context.Customers.Where(c => c.CustomerId == id).Include(x => x.Pets).ThenInclude(y => y.PetReservations).ThenInclude(z => z.Reservation).FirstOrDefault();
                        return RedirectToAction("StartPetVisit", "Clerk", new { id = ResId });
                    }
                    else if (MyRole == "clerk" && lastAction == "CustomerHome") {
                        var data = _context.Customers.Where(c => c.CustomerId == id).Include(x => x.Pets).ThenInclude(y => y.PetReservations).ThenInclude(z => z.Reservation).FirstOrDefault();
                        return RedirectToAction("Index", "Home", new { id = customer.CustomerId });
                    }


                    return RedirectToAction("Index", "Home", new { id = customer.CustomerId });

                }
                catch
                {
                    return View(customer);
                }
            }
            return View(customer);
        }
        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Roles = "user,clerk")]

        public async Task<IActionResult> DeleteAccount()
        {
            var CustId = HttpContext.Session.GetInt32("_CustId");
            if (CustId == null || CustId < 0)
            {
                return RedirectToAction("Index", "Login");
            }
            return View();
        }
        [HttpPost]
        [ValidateAntiForgeryToken]
        [Authorize(Roles = "user,clerk")]

        public async Task<IActionResult> ConfirmDelete(DeleteAccount deleteAccount)
        {
            var CustId = HttpContext.Session.GetInt32("_CustId");
            var userIdentity = (ClaimsIdentity)User.Identity;
            var claims = userIdentity.Claims;
            var roleClaimType = userIdentity.RoleClaimType;
            var role = claims.Where(c => c.Type == ClaimTypes.Role).ToList().First();
            var MyRole = role.Value;

            var ClerkCustomerId = -1;
            if (TempData.Peek("ClerkCustomer") != null)
            {
                ClerkCustomerId = (int)TempData["ClerkCustomer"];
            }

            if ((CustId == null || CustId < 0) && (MyRole == "user")) 
            {
                return RedirectToAction("Index", "Login");
            }

            if (ModelState.IsValid) {
                try
                {

                    var customer = new Customer();

                    if (MyRole == "clerk" && ClerkCustomerId != -1)
                    {
                        customer = _context.Customers.Find(ClerkCustomerId);
                    }
                    else { 
                        customer = _context.Customers.Find(CustId);
                    }
                     
                    var custPets = _context.Pets.Include(p=>p.PetReservations).Where(p=>p.CustomerId == CustId).ToList();
                    var petResIds = new List<int>();
                    var custPetReservations = new List<PetReservation>();
                    var allReservations = _context.Reservations.ToList();
                    var allPetReservationServices = _context.PetReservationServices.ToList();
                    var allPetReservationDiscounts = _context.PetReservationDiscounts.ToList();
                    var allPetVaccinations = _context.PetVaccinations.ToList();
                    var allMedications = _context.Medications.ToList();
                    var allReservationDiscounts = _context.ReservationDiscounts.ToList();
                    var custReservationIds = new List<int>();

                    var petIds = new List<int>();
                    foreach (var pet in custPets) {
                        petIds.Add(pet.PetId);
                    }

                    foreach (var pet in custPets) {
                        foreach (var petRes in pet.PetReservations) {
                            custPetReservations.Add(petRes);
                            petResIds.Add(petRes.PetReservationId);
                        }
                    
                    }

                    foreach (var cpr in custPetReservations) {
                        if (!custReservationIds.Contains(cpr.ReservationId)) {
                            custReservationIds.Add(cpr.ReservationId);
                        }
                    }

                    var custPetReservationDiscounts = new List<PetReservationDiscount>();
                    foreach (var prd in allPetReservationDiscounts) {
                        if (petResIds.Contains(prd.PetReservationId)) {
                            custPetReservationDiscounts.Add(prd);
                        }
                    }

                    var custPetReservationServices = new List<PetReservationService>();
                    foreach (var prs in allPetReservationServices)
                    {
                        if (petResIds.Contains(prs.PetReservationId))
                        {
                            custPetReservationServices.Add(prs);
                        }
                    }

                    var custPetVaccinations = new List<PetVaccination>();
                    foreach (var pv in allPetVaccinations) {
                        if (petIds.Contains(pv.PetId)) {
                            custPetVaccinations.Add(pv);
                        }
                    }

                    var custPetMedications = new List<Medication>();
                    foreach (var med in allMedications) {
                        if (petResIds.Contains(med.PetReservationId)) {
                            custPetMedications.Add(med);
                        }
                    }

                    var custReservations = new List<Reservation>();
                    foreach (var res in allReservations) {
                        if (custReservationIds.Contains(res.ReservationId)) {
                            custReservations.Add(res);
                        }
                    }

                    var custReservationDiscounts = new List<ReservationDiscount>();
                    foreach (var resDisc in allReservationDiscounts) {
                        if (custReservationIds.Contains(resDisc.ReservationId)) {
                            custReservationDiscounts.Add(resDisc);
                        }
                    }

                    //Delete from Database 
                    foreach (var cm in custPetMedications) {
                        _context.Medications.Remove(cm);
                        await _context.SaveChangesAsync();
                    }

                    foreach (var resDisc in custReservationDiscounts) {
                        _context.ReservationDiscounts.Remove(resDisc);
                        await _context.SaveChangesAsync();
                    }


                    foreach (var prd in custPetReservationDiscounts)
                    {
                        _context.PetReservationDiscounts.Remove(prd);
                        await _context.SaveChangesAsync();
                    }


                    foreach (var prs in custPetReservationServices)
                    {
                        _context.PetReservationServices.Remove(prs);
                        await _context.SaveChangesAsync();
                    }

                    foreach (var pr in custPetReservations)
                    {
                        _context.PetReservations.Remove(pr);
                        await _context.SaveChangesAsync();
                    }

                    foreach (var r in custReservations)
                    {
                        _context.Reservations.Remove(r);
                        await _context.SaveChangesAsync();
                    }


                    foreach (var pv in custPetVaccinations)
                    {
                        _context.PetVaccinations.Remove(pv);
                        await _context.SaveChangesAsync();
                    }

                    foreach (var p in custPets)
                    {
                        _context.Pets.Remove(p);
                        await _context.SaveChangesAsync();
                    }

                        _context.Customers.Remove(customer);
                        await _context.SaveChangesAsync();



                    if (MyRole == "user")
                    {
                        TempData["CustDeleted"] = "Your account has been successfully deleted.";
                        return RedirectToAction("Index", "Login");
                    }
                    else {
                       
                        return RedirectToAction("Index", "Clerk");
                    }
                    
                }
                catch {
                    return View("DeleteAccount", deleteAccount);
                }
            }
            return View("DeleteAccount", deleteAccount);
        }
    }
}
