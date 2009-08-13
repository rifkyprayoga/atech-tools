using System;
using ATechTools.Util;

namespace ATechTools.I18n
{

    public class ResourceBundle : PropertiesFile
    {





        public string GetString(string key)
        {
            return this.Get(key);
        }





    }

}


