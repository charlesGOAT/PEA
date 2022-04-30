using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace HVK.Models
{
    public class Login
    {
      
        [StringLength(50, ErrorMessage = "Email cannot be longer than 50 characters")]
        [LoginValidation("Phone","Password")]
        public string Email { get; set; }
        [Required(ErrorMessage = "Password is required")]
        
        public string Password { get; set; }

        public string Phone { get; set; }
        public Login()
        {

        }
      
        public Login(string phone, string email, string password)
        {
            Phone = phone;
            Password = password;
            Email = email;
        }
        

    }
}
