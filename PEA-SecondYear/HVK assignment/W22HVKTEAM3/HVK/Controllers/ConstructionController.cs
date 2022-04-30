using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace HVK.Controllers {
    public class ConstructionController : Controller {
        public IActionResult Index() {
            return View();
        }
    }
}
