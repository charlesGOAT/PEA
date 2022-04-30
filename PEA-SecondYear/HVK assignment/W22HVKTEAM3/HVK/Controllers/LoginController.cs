using HVK.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Security.Claims;
using Microsoft.AspNetCore.Http;

namespace HVK.Controllers
{
    public class LoginController : Controller
    {
        private readonly HVK_Team3Context _context;
        public LoginController(HVK_Team3Context context){
            _context = context;
                }
        public async Task<IActionResult> Index()
        {
            HttpContext.Session.SetInt32("_CustId", -1);
          await  HttpContext.SignOutAsync();
            return View();
        }
        [HttpPost]
        public async Task<IActionResult> Index(Login login) {
            if (!ModelState.IsValid) {
                return View(login);
            }
            bool IsClerk = false;
            var DBContext = _context.Customers.Where(c => (c.Email == login.Email && c.Email != null) || (c.Phone == login.Phone && c.Phone !=null));
            int CustomerId = -1;
            try
            {
                 CustomerId = DBContext.First().CustomerId;
            }
            catch {
                TempData["CustNotFound"] = "Invalid email/phone number or password";
                return View();
            }
            
            dynamic claims;
            if (DBContext.First().FirstName == "Sally" && DBContext.First().LastName == "Reed")
            {
                claims = new List<Claim> {
                    new Claim(ClaimTypes.Name, DBContext.First().Email ?? DBContext.First().Phone),
                    new Claim(ClaimTypes.Role,"clerk")
                };
                IsClerk = true;
            }
            else
            {

                claims = new List<Claim> {
                    new Claim(ClaimTypes.Name, DBContext.First().Email ?? DBContext.First().Phone ),
                    new Claim(ClaimTypes.Role,"user")
                };
            }
            
            var claimsIdentity = new ClaimsIdentity(claims, CookieAuthenticationDefaults.AuthenticationScheme);
            var authProperties = new AuthenticationProperties
            {
                //IsPersistent = true;
            };

            await HttpContext.SignInAsync(
    CookieAuthenticationDefaults.AuthenticationScheme, new ClaimsPrincipal(claimsIdentity), authProperties);


            HttpContext.Session.SetInt32("_CustId", CustomerId);
            if (IsClerk) {
                return RedirectToAction("Index", "Clerk");
            }
           
            return RedirectToAction("Index","Home");
        }
    }
}
