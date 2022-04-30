using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using HVK.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Authorization;
using System.Security.Claims;

namespace HVK.Controllers
{
    [Authorize(Roles = "user,clerk")]
    public class PetsController : Controller
    {
        private readonly HVK_Team3Context _context;

        public PetsController(HVK_Team3Context context)
        {
            _context = context;
        }

        // GET: Pets
        [HttpGet]
        public async Task<IActionResult> Index()
        {
            var CustId = HttpContext.Session.GetInt32("_CustId");
            if (CustId == null || CustId < 0)
            {
                return RedirectToAction("Index", "Login");
            }
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> Index([Bind("PetId,Name,Gender,Breed,Birthyear,DogSize,Climber,Barker,SpecialNotes,CustomerId")] Pet pet)
        {
            var CustId = HttpContext.Session.GetInt32("_CustId");
            var userIdentity = (ClaimsIdentity)User.Identity;
            var claims = userIdentity.Claims;
            var roleClaimType = userIdentity.RoleClaimType;
            var role = claims.Where(c => c.Type == ClaimTypes.Role).ToList().First();
            var MyRole = role.Value;

            if (CustId == null || CustId < 0)
            {
                return RedirectToAction("Index", "Login");
            }
            if (!ModelState.IsValid)
                return View(pet);
            else
            {
                try
                {
                    if (MyRole == "user")
                    {
                        pet.CustomerId = (int)CustId;
                    }
                    else {
              
                        var ClerkCustomerId = -1;
                        if (TempData.Peek("ClerkCustomer") != null)
                        {
                            ClerkCustomerId = (int)TempData.Peek("ClerkCustomer");
                        }

                        if (ClerkCustomerId != -1)
                        {
                            pet.CustomerId = ClerkCustomerId;
                            _context.Add(pet);
                            await _context.SaveChangesAsync();
                            TempData["SuccessfulChange"] = pet.Name + " was successfully added.";
                            return RedirectToAction("Index", "Home", new { id = ClerkCustomerId});
                        }
                        else {
                            return RedirectToAction("Index", "Clerk");
                        }
                        
                    }
                    
                    _context.Add(pet);
                    await _context.SaveChangesAsync();
                    TempData["SuccessfulChange"] = pet.Name + " was successfully added.";
                    return RedirectToAction("Index", "Home");
                }
                catch
                {
                    return View(pet);
                }
            }
        }


       
        // GET: Pets/Edit/5
        [HttpGet]
        public async Task<IActionResult> Edit(int? id)
        {

            var CustId = HttpContext.Session.GetInt32("_CustId");

            var userIdentity = (ClaimsIdentity)User.Identity;
            var claims = userIdentity.Claims;
            var roleClaimType = userIdentity.RoleClaimType;
            var role = claims.Where(c => c.Type == ClaimTypes.Role).ToList().First();
            var MyRole = role.Value;

            //if (CustId == null || CustId < 0)
            //{
            //    return RedirectToAction("Index", "Login");
            //}
            if (id == null)
            {
                return NotFound();
            }

            var pet = await _context.Pets.FindAsync(id);
            if (CustId != pet.CustomerId && MyRole != "clerk")
            {
                return RedirectToAction("Index", "Login");
            }

            if (pet == null)
            {
                return NotFound();
            }
            ViewData["CustomerId"] = new SelectList(_context.Customers, "CustomerId", "FirstName", pet.CustomerId);
            return View(pet);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit([Bind("PetId,Name,Gender,Breed,Birthyear,DogSize,Climber,Barker,SpecialNotes,CustomerId")] Pet pet)
        {
            var CustId = HttpContext.Session.GetInt32("_CustId");
            var userIdentity = (ClaimsIdentity)User.Identity;
            var claims = userIdentity.Claims;
            var roleClaimType = userIdentity.RoleClaimType;
            var role = claims.Where(c => c.Type == ClaimTypes.Role).ToList().First();
            var MyRole = role.Value;
            if (ModelState.IsValid)
            {
                try
                {

                    _context.Update(pet);
                    await _context.SaveChangesAsync();
                    TempData["SuccessfulChange"] = pet.Name + " was successfully updated.";
                    if (MyRole == "user")
                    {
                        return RedirectToAction("Index", "Home");
                    }
                    else {

                        return RedirectToAction("Index", "Home", new { id= pet.Customer.CustomerId});
                    }
                }
                catch
                {
                    return View(pet);
                }
            }
            ViewData["CustomerId"] = new SelectList(_context.Customers, "CustomerId", "FirstName", pet.CustomerId);
            return View(pet);
        }
       

        //GET: Pets/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            var pet = await _context.Pets
                .Include(p => p.Customer)
                .FirstOrDefaultAsync(m => m.PetId == id);
            if (pet == null)
            {
                return NotFound();
            }

            var petDel = new DeletePet(pet.PetId, false, pet.Name);

            return View(petDel);
        }

        //POST: Pets/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Delete(DeletePet dp)
        {
            var id = dp.PetId;

            var pet = await _context.Pets.Include(x => x.Customer).Where(x => x.PetId == id).FirstAsync();
            if (ModelState.IsValid)
            {
                try
                {
                    

                    var petVaccinations = await _context.PetVaccinations.Where(p => p.Pet.PetId == id).ToListAsync();
                    var petReservations = await _context.PetReservations.Where(p => p.Pet.PetId == id).ToListAsync();
                    var petReservationServices = await _context.PetReservationServices.Where(p=> p.PetReservation.PetId == id).ToListAsync();
                    var petReservationDiscounts = await _context.PetReservationDiscounts.Where(p=>p.PetReservation.PetId == id).ToListAsync();
                    var petMedications = await _context.Medications.Include(p => p.PetReservation).Where(p => p.PetReservation.PetId == id).ToListAsync();
                    if (petVaccinations != null && petVaccinations.Count() > 0)
                    {
                        foreach (var petvacc in petVaccinations)
                        {
                            _context.PetVaccinations.Remove(petvacc);
                          
                        }  
                        await _context.SaveChangesAsync();
                    }

                    if (petReservationDiscounts != null && petReservationDiscounts.Count() > 0)
                    {
                        foreach (var prd in petReservationDiscounts)
                        {
                            _context.PetReservationDiscounts.Remove(prd);
                           
                        } 
                        
                        await _context.SaveChangesAsync();
                    }
                    if (petReservationServices != null && petReservationServices.Count() > 0)
                    {
                        foreach (var prs in petReservationServices)
                        {
                            _context.PetReservationServices.Remove(prs);
                            
                        }
                        await _context.SaveChangesAsync();
                    }

                    if (petMedications != null && petMedications.Count() > 0)
                    {
                        foreach (var pm in petMedications)
                        {
                            _context.Medications.Remove(pm);

                        }
                        await _context.SaveChangesAsync();
                    }

                    if (petReservations != null && petReservations.Count() > 0)
                    {
                        foreach (var pr in petReservations)
                        {
                            _context.PetReservations.Remove(pr);
                            
                        }
                        await _context.SaveChangesAsync();
                    }
                    _context.Pets.Remove(pet);
                    await _context.SaveChangesAsync();
                    TempData["SuccessfulChange"] = pet.Name + " was successfully removed.";
                }
                catch (Exception e)
                {
                    return View(dp);
                }
            }
            else {
                
                return View(dp);
            }
            var customerId = pet.Customer.CustomerId;
            return RedirectToAction("Index", "Home", new { id = customerId });
                
        }

        private bool PetExists(int id)
        {
            return _context.Pets.Any(e => e.PetId == id);
        }
    }
}
