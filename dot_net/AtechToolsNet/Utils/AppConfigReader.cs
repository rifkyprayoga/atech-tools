using System;

using System.Collections.Generic;
using System.Text;
//using System.Configuration;

namespace AtechToolsNetMobile.Utils
{
    public abstract class AppConfigReader
    {

        protected Dictionary<String, String> ht_values = new Dictionary<string, string>();


        public AppConfigReader()
        {
            LoadConfigValues(GetKeywords());

            
        }


        public void LoadConfigValues(string[] keys)
        {

            for (int i = 0; i < keys.Length; i++)
            { 
                //ht_values.Add(keys[i], ConfigurationManager.AppSettings[keys[i]]);

            }



        }



        public void LoadConfigValues()
        {

            ///foreach(string key in ConfigurationManager.AppSettings)
            {
                //ht_values.Add(key, ConfigurationManager.AppSettings[key]);

            }



        }




        public abstract string[] GetKeywords();


    }
}
