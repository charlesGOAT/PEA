using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace HVK.Models
{
    public class FormattingService
    {

        public string SizeString(string size) {
            string sizeString = "Unknown Size";
            switch(size.ToUpper()){
                case "S":
                    sizeString = "Small";
                    break;
                case "M":
                    sizeString = "Medium";
                    break;
                case "L":
                    sizeString = "Large";
                    break;
            }

            return sizeString;
        }
    }
}
