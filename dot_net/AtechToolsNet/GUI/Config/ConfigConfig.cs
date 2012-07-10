using System;

using System.Collections.Generic;
using System.Text;
using System.IO;
using System.Windows.Forms;
//using System.Drawing;
using ATechTools.File;
using System.Drawing;

namespace ATechTools.GUI.Config
{
    public class ConfigConfig : FileReader
    {

        private string configName = null;

        private Dictionary<string, string> settings = null;

        private Dictionary<string, ConfigModule> modules = null;

        public Dictionary<string, ConfigModule> Modules
        {
            get { return modules; }
            set { modules = value; }
        }






        public ConfigConfig(FileInfo fi) : base(fi.FullName)
        {
            modules = new Dictionary<string, ConfigModule>();
            settings = new Dictionary<string, string>();
            configName = fi.FullName;
            LoadData();
        }


        public override bool IsLineData(string line)
        {
            return (line.IndexOf("=") > 0);
        }



        public void LoadData()
        {

            if (!this.FileFound())
                return;

            string s = "";
            Dictionary<string, string> datax = new Dictionary<string, string>();

            StartReading();

            int idx = 0;
            while ((s = GetNextDataLine()) != null)
            {
                idx = s.IndexOf("=");
                datax.Add(s.Substring(0, idx), s.Substring(idx + 1));
            }

            this.EndReading();


            // find modules
            foreach (string key in datax.Keys)
            { 
                idx = key.IndexOf(".");

                if (key.IndexOf("App") >= 0)
                {
                    // settings
                    settings.Add(key, datax[key]);
                }
                else
                {
                    if (idx == -1)
                    {
                        ConfigModule module = new ConfigModule(key);
                        Modules.Add(key, module);
                    }
                }
            }


            // load module data
            foreach (string key in Modules.Keys)
            {
                this.Modules[key].LoadSettings(datax);
            }

            
        }



        public void ReconfigureFormGUI(ConfigurableGUIInterface gui)
        {

            if (Modules.ContainsKey(gui.ModuleName))
            {

                ConfigModule cm = Modules[gui.ModuleName];

                Form f = (Form)gui;

                if (cm.Elements.ContainsKey("Form"))
                {
                    ConfigEntry ce = cm.Elements["Form"];

                    if (ce.Max)
                    {
                        /*
                        Rectangle r = Screen.PrimaryScreen.Bounds;
                        f.Width = r.Width;
                        f.Height = r.Height;*/
                        f.WindowState = FormWindowState.Maximized;
                    }
                    else
                    {
                        if (ce.Width != -1)
                        {
                            f.Width = ce.Width;
                        }

                        if (ce.Height != -1)
                        {
                            f.Height = ce.Height;
                        }
                    }
                }

                foreach (Control c in f.Controls)
                {
                    if (cm.Elements.ContainsKey(c.Name))
                    {
                        ConfigEntry ce = cm.Elements[c.Name];
                        SetControlSettings(c, ce);
                    }
                }            
            }
        }


        private void SetControlSettings(Control c, ConfigEntry ce)
        {
            if (ce.X != -1)
            {
                c.Left = ce.X;
            }

            if (ce.Y != -1)
            {
                c.Top = ce.Y;
            }

            if (ce.Width != -1)
            {
                c.Width = ce.Width;
            }

            if (ce.Height != -1)
            {
                c.Height = ce.Height;
            }

            if (ce.BackColor != Color.Empty)
            {
                c.BackColor = ce.BackColor;
            }

            if (ce.ForeColor != Color.Empty)
            {
                c.ForeColor = ce.ForeColor;
            }

            //c.Font.


        }



    }
}
