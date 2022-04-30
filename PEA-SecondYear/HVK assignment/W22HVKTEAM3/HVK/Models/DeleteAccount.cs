using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.ComponentModel.DataAnnotations;

namespace HVK.Models
{
    public class DeleteAccount
    {
        [DeleteValidation("ConfirmDelete")]
        public bool ConfirmDelete { get; set; }
    }
}
