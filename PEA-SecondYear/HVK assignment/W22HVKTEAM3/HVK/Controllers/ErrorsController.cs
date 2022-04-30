using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace HVK.Controllers
{
    public class ErrorsController : Controller
    {
        public IActionResult Index()
        {
            return View("Error");
        }

        public IActionResult Error404()
        {
            return View();
        }
    }
}
