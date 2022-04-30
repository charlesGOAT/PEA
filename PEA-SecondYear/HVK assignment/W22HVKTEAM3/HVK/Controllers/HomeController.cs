using HVK.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Authorization;
using System.Security.Claims;

namespace HVK.Controllers
{


    [Authorize(Roles = "user, clerk")]

    public class HomeController : Controller
    {
        private readonly ILogger<HomeController> _logger;
        private readonly HVK_Team3Context _db;
        const string SessionCustId = "_CustId";

        public HomeController(ILogger<HomeController> logger, HVK_Team3Context db)
        {
            _logger = logger;
            _db = db;
        }

        public IActionResult Index(int? id)
        {
            var userIdentity = (ClaimsIdentity)User.Identity;
            var claims = userIdentity.Claims;
            var roleClaimType = userIdentity.RoleClaimType;
            var role = claims.Where(c => c.Type == ClaimTypes.Role).ToList().First();
            var MyRole = role.Value;
            var CustId = HttpContext.Session.GetInt32(SessionCustId);

            TempData["LastAction"] = "CustomerHome";

            if (id != null) {
                TempData["ClerkCustomer"] = (int)id;
            }



            if ((CustId == null || CustId < 0) && MyRole == "user")
            {
                return RedirectToAction("Index", "Login");
            }
            else if (MyRole == "clerk")
            {
                var data = _db.Customers.Where(c => c.CustomerId == id).Include(x => x.Pets).ThenInclude(y => y.PetReservations).ThenInclude(z => z.Reservation).FirstOrDefault();
                ViewData["Reservations"] = new List<Reservation>();
                return View(data);
            }
            else
            {
                var data = _db.Customers.Where(c => c.CustomerId == CustId).Include(x => x.Pets).ThenInclude(y => y.PetReservations).ThenInclude(z => z.Reservation).FirstOrDefault();
                ViewData["Reservations"] = new List<Reservation>();
                return View(data);
            }
        }

        public IActionResult PastReservations(int? id)
        {
            var userIdentity = (ClaimsIdentity)User.Identity;
            var claims = userIdentity.Claims;
            var roleClaimType = userIdentity.RoleClaimType;
            var role = claims.Where(c => c.Type == ClaimTypes.Role).ToList().First();
            var MyRole = role.Value;

            if (id != null)
            {
                TempData["ClerkCustomer"] = (int)id;
            }



            var CustId = HttpContext.Session.GetInt32(SessionCustId);

            if (id != -1 && id != null) {
                CustId = id;
            }
            var data = _db.Customers.Where(c => c.CustomerId == CustId).Include(x => x.Pets).ThenInclude(y => y.PetReservations).ThenInclude(z => z.Reservation).ThenInclude(x=>x.PetReservations).ThenInclude(x=>x.Pet).ThenInclude(x=>x.PetReservations).FirstOrDefault();
            List<Reservation> reservations = new List<Reservation>();
            List<int> resIds = new List<int>();


            foreach (var pet in data.Pets) {
                foreach (var pr in pet.PetReservations) {

                    if (!resIds.Contains(pr.Reservation.ReservationId)) {
                        resIds.Add(pr.ReservationId);
                        reservations.Add(pr.Reservation);
                       
                    }
                    
                    
                }
            }

            
            ViewData["Reservations"] = new List<Reservation>();
            return View(reservations);
        }

        [ResponseCache(Duration = 0, Location = ResponseCacheLocation.None, NoStore = true)]
        public IActionResult Error()
        {
            return View(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}
