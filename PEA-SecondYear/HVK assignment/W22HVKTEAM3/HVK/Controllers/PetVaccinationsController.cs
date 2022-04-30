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
    public class PetVaccinationsController : Controller
    {
        private readonly HVK_Team3Context _context;

        public PetVaccinationsController(HVK_Team3Context context)
        {
            _context = context;
        }



        // GET: PetVaccinations/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            var userIdentity = (ClaimsIdentity)User.Identity;
            var claims = userIdentity.Claims;
            var roleClaimType = userIdentity.RoleClaimType;
            var role = claims.Where(c => c.Type == ClaimTypes.Role).ToList().First();
            var MyRole = role.Value;

            var CustId = HttpContext.Session.GetInt32("_CustId");
            if ((CustId == null || CustId < 0) && (MyRole == "user"))
            {
                return RedirectToAction("Index", "Login");
            }
            if (id == null)
            {
                return NotFound();
            }

            Pet p =  _context.Pets.Include(x=>x.PetVaccinations).ThenInclude(x=>x.Vaccination).Where(x=>x.PetId == id).First();
            if ((CustId != p.CustomerId) && (MyRole == "user"))
            {
                return RedirectToAction("Index", "Login");
            }

            ViewBag.PetName = p.Name;
            var petVaccinationList = await _context.PetVaccinations.Where(p => p.PetId == id).Include(p=>p.Vaccination).ToListAsync();
            var vaccInPetObj = p.PetVaccinations;

            var allVacc = await _context.Vaccinations.ToListAsync();
            List<PetVaccination> newPVList = new List<PetVaccination>();
            List<int> allVaccIds = new List<int>();
            foreach (var vacc in allVacc) {
                allVaccIds.Add(vacc.VaccinationId);
            }

            List<int> PetVaccIds = new List<int>();
            List<int> MissingVaccIds = new List<int>();
            foreach (var vaccId in vaccInPetObj)
            {
                PetVaccIds.Add(vaccId.VaccinationId);
            }

            foreach (var missingVacc in allVaccIds) {
                if (!PetVaccIds.Contains(missingVacc)) {
                    newPVList.Add(new PetVaccination() {PetId = p.PetId, VaccinationId = missingVacc, Vaccination = allVacc.Where(x=>x.VaccinationId == missingVacc).First(), VaccinationChecked = false });
                }
            }

            foreach (var newVacc in newPVList) {
                petVaccinationList.Add(newVacc);
            }

            //add missing empty vaccines to list
            p.PetVaccinations = petVaccinationList;

            foreach (var pv in p.PetVaccinations) {
                pv.Pet = p;
            }

            var PV_UI = new PetVaccination_UI(p);

            PV_UI.AssignPetVaccinationsFromList(petVaccinationList);

            return View(PV_UI);
        }

        [HttpPost]
        public async Task<IActionResult> Edit(PetVaccination_UI pV_UI)
        {
            var userIdentity = (ClaimsIdentity)User.Identity;
            var claims = userIdentity.Claims;
            var roleClaimType = userIdentity.RoleClaimType;
            var role = claims.Where(c => c.Type == ClaimTypes.Role).ToList().First();
            var MyRole = role.Value;

            var CustId = HttpContext.Session.GetInt32("_CustId");
            if ((CustId == null || CustId < 0) && (MyRole == "user"))
            {
                return RedirectToAction("Index", "Login");
            }


            if (ModelState.IsValid) {

                var petVaccinations = _context.PetVaccinations.Include(pv=>pv.Vaccination).Where(pv => pv.PetId == pV_UI.PetId).ToList();
                var FoundVaccination = false;
                if (pV_UI.BordetellaExpiryDate != null) {

                    foreach (var vacc in petVaccinations) {
                        if (vacc.Vaccination.Name.ToLower() == "bordetella") {
                            FoundVaccination = true;
                            vacc.ExpiryDate = (DateTime) pV_UI.BordetellaExpiryDate;
                            vacc.VaccinationChecked = pV_UI.BordetellaVaccine.VaccinationChecked;
                            _context.Update(vacc);
                            _context.SaveChanges();
                        }
                    }
                    if (! FoundVaccination) {
                        var bordatellaId = _context.Vaccinations.Where(v=>v.Name == "Bordetella").First().VaccinationId;
                        var newPetVacc = new PetVaccination();
                        newPetVacc.PetId = pV_UI.PetId;
                        newPetVacc.VaccinationId = bordatellaId;
                        newPetVacc.ExpiryDate = (DateTime)pV_UI.BordetellaExpiryDate;
                        _context.Add(newPetVacc);
                        _context.SaveChanges();
                    }
                }

                FoundVaccination = false;
                if (pV_UI.DistemperExpiryDate != null)
                {

                    foreach (var vacc in petVaccinations)
                    {
                        if (vacc.Vaccination.Name.ToLower() == "distemper")
                        {
                            FoundVaccination = true;
                            vacc.ExpiryDate = (DateTime)pV_UI.DistemperExpiryDate;
                            vacc.VaccinationChecked = pV_UI.DistemperVaccine.VaccinationChecked;
                            _context.Update(vacc);
                            _context.SaveChanges();
                        }
                    }
                    if (!FoundVaccination)
                    {
                        var DistemperId = _context.Vaccinations.Where(v => v.Name == "Distemper").First().VaccinationId;
                        var newPetVacc = new PetVaccination();
                        newPetVacc.PetId = pV_UI.PetId;
                        newPetVacc.VaccinationId = DistemperId;
                        newPetVacc.ExpiryDate = (DateTime)pV_UI.DistemperExpiryDate;
                        _context.Add(newPetVacc);
                        _context.SaveChanges();
                    }
                }

                FoundVaccination = false;
                if (pV_UI.HepatitisExpiryDate != null)
                {
                    foreach (var vacc in petVaccinations)
                    {
                        if (vacc.Vaccination.Name.ToLower() == "hepatitis")
                        {
                            FoundVaccination = true;
                            vacc.ExpiryDate = (DateTime)pV_UI.HepatitisExpiryDate;
                            vacc.VaccinationChecked = pV_UI.HepatitisVaccine.VaccinationChecked;
                            _context.Update(vacc);
                            _context.SaveChanges();
                        }
                    }
                    if (!FoundVaccination)
                    {
                        var HepatitisId = _context.Vaccinations.Where(v => v.Name == "Hepatitis").First().VaccinationId;
                        var newPetVacc = new PetVaccination();
                        newPetVacc.PetId = pV_UI.PetId;
                        newPetVacc.VaccinationId = HepatitisId;
                        newPetVacc.ExpiryDate = (DateTime)pV_UI.HepatitisExpiryDate;
                        _context.Add(newPetVacc);
                        _context.SaveChanges();
                    }
                }
                FoundVaccination = false;
                if (pV_UI.ParainfluenzaExpiryDate != null)
                {
                    foreach (var vacc in petVaccinations)
                    {
                        if (vacc.Vaccination.Name.ToLower() == "parainfluenza")
                        {
                            FoundVaccination = true;
                            vacc.ExpiryDate = (DateTime)pV_UI.ParainfluenzaExpiryDate;
                            vacc.VaccinationChecked = pV_UI.ParainfluenzaVaccine.VaccinationChecked;
                            _context.Update(vacc);
                            _context.SaveChanges();
                        }
                    }

                    if (!FoundVaccination)
                    {
                        var ParainfluenzaId = _context.Vaccinations.Where(v => v.Name == "Parainfluenza").First().VaccinationId;
                        var newPetVacc = new PetVaccination();
                        newPetVacc.PetId = pV_UI.PetId;
                        newPetVacc.VaccinationId = ParainfluenzaId;
                        newPetVacc.ExpiryDate = (DateTime)pV_UI.ParainfluenzaExpiryDate;

                        
                        _context.Add(newPetVacc);
                        _context.SaveChanges();
                    }
                }

                FoundVaccination = false;

                if (pV_UI.ParovirusExpiryDate != null)
                {
                    foreach (var vacc in petVaccinations)
                    {
                        if (vacc.Vaccination.Name.ToLower() == "parovirus")
                        {
                            FoundVaccination = true;
                            vacc.ExpiryDate = (DateTime)pV_UI.ParovirusExpiryDate;
                            vacc.VaccinationChecked = pV_UI.ParovirusVaccine.VaccinationChecked;
                            _context.Update(vacc);
                            _context.SaveChanges();
                        }
                    }
                    if (!FoundVaccination)
                    {
                        var ParovirusId = _context.Vaccinations.Where(v => v.Name == "Parovirus").First().VaccinationId;
                        var newPetVacc = new PetVaccination();
                        newPetVacc.PetId = pV_UI.PetId;
                        newPetVacc.VaccinationId = ParovirusId;
                        newPetVacc.ExpiryDate = (DateTime)pV_UI.ParovirusExpiryDate;
                        _context.Add(newPetVacc);
                        _context.SaveChanges();
                    }
                }

                FoundVaccination = false;

                if (pV_UI.RabiesExpiryDate != null)
                {
                    foreach (var vacc in petVaccinations)
                    {
                        if (vacc.Vaccination.Name.ToLower() == "rabies")
                        {
                            FoundVaccination = true;
                            vacc.ExpiryDate = (DateTime)pV_UI.RabiesExpiryDate;
                            vacc.VaccinationChecked = pV_UI.RabiesVaccine.VaccinationChecked;
                            _context.Update(vacc);
                            _context.SaveChanges();
                        }
                    }
                    if (!FoundVaccination)
                    {
                        var RabiesId = _context.Vaccinations.Where(v => v.Name == "Rabies").First().VaccinationId;
                        var newPetVacc = new PetVaccination();
                        newPetVacc.PetId = pV_UI.PetId;
                        newPetVacc.VaccinationId = RabiesId;
                        newPetVacc.ExpiryDate = (DateTime)pV_UI.RabiesExpiryDate;
                        _context.Add(newPetVacc);
                        _context.SaveChanges();
                    }
                }
            }


            var customerId = _context.Pets.Include(x=>x.Customer).Where(x => x.PetId == pV_UI.PetId).First().Customer.CustomerId;

            return RedirectToAction("Index", "Home", new { id = customerId });
        }

        [HttpPost]
        public async Task<IActionResult> EditAll(PetVaccination_UI pV_UI)
        {

            var CustId = HttpContext.Session.GetInt32("_CustId");
            if (CustId == null || CustId < 0)
            {
                return RedirectToAction("Index", "Login");
            }

            var ChangeAllVaccinationDates = pV_UI.ChangeAllDatesDate;
            if (ChangeAllVaccinationDates == null) {
                return RedirectToAction("Edit", "PetVaccinations", new { id = pV_UI.PetId });
            } else {

                if (ModelState.IsValid)
                {

                    var petVaccinations = _context.PetVaccinations.Include(pv => pv.Vaccination).Where(pv => pv.PetId == pV_UI.PetId).ToList();
                    var allGeneralVaccinations = _context.Vaccinations.ToList();
                    var addedPetVaccs = new List<PetVaccination>();


                    foreach (var generalVacc in allGeneralVaccinations)
                    {
                        var FoundVaccination = false;
                        foreach (var vacc in petVaccinations)
                        {
                            if (generalVacc.Name == vacc.Vaccination.Name)
                            {
                                FoundVaccination = true;
                                vacc.ExpiryDate = (DateTime)ChangeAllVaccinationDates;
                                vacc.VaccinationChecked = false;
                                _context.Update(vacc);
                                _context.SaveChanges();
                            }
                        }

                        if (!FoundVaccination)
                        {
                            var newPetVacc = new PetVaccination();
                            newPetVacc.ExpiryDate = (DateTime)ChangeAllVaccinationDates;
                            newPetVacc.PetId = pV_UI.PetId;
                            newPetVacc.VaccinationId = generalVacc.VaccinationId;
                            addedPetVaccs.Add(newPetVacc);
                        }

                    }

                    foreach (var addedVacc in addedPetVaccs)
                    {
                        _context.Add(addedVacc);
                        _context.SaveChanges();
                    }
                }
            }
            var customerId = _context.Pets.Include(x => x.Customer).Where(x => x.PetId == pV_UI.PetId).First().Customer.CustomerId;

            return RedirectToAction("Index", "Home", new { id = customerId });
        }



    }
}
