using System;

using System.Collections.Generic;
using System.Text;

namespace ATechTools.GUI.Config
{
    public class ConfigModule
    {

        private string moduleName = null;

        private ConfigEntry moduleSettings = null;

        private Dictionary<string, ConfigEntry> elements = null;

        public ConfigModule(string module)
        {
            this.moduleName = module;
            this.elements = new Dictionary<string, ConfigEntry>();
        }


        public void Add(string key, string value)
        {
            elements.Add(key, new ConfigEntry(value));
        }


        public void LoadSettings(Dictionary<string, string> data)
        {
            foreach (string key in data.Keys)
            {
                if (key.StartsWith(this.moduleName + ".") )
                { 
                    int x = this.moduleName.Length +1;
                    Add(key.Substring(x, key.Length -x), data[key]);
                }
            }

            int z = this.elements.Count;

        }



        public string ModuleName
        {
            get { return moduleName; }
            set { moduleName = value; }
        }


        public ConfigEntry ModuleSettings
        {
            get { return moduleSettings; }
            set { moduleSettings = value; }
        }


        public Dictionary<string, ConfigEntry> Elements
        {
            get { return elements; }
            set { elements = value; }
        } 


    }
}
