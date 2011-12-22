using System;

using System.Collections.Generic;
using System.Text;
using System.Drawing;

namespace ATechTools.GUI.Config
{
    public class ConfigEntry
    {

        public static char element_delimiter = ',';
        public static char setting_delimiter = ';';

        //private string configName = null;

        //private string moduleName = null;

        private int x = -1;

        private Color foreColor = Color.Empty;

        public Color ForeColor
        {
            get { return foreColor; }
            set { foreColor = value; }
        }
        private Color backColor = Color.Empty;

        public Color BackColor
        {
            get { return backColor; }
            set { backColor = value; }
        }


        public int X
        {
            get { return x; }
            set { x = value; }
        }

        private int y = -1;

        public int Y
        {
            get { return y; }
            set { y = value; }
        }

        private int width = -1;

        public int Width
        {
            get { return width; }
            set { width = value; }
        }

        private int height = -1;

        public int Height
        {
            get { return height; }
            set { height = value; }
        }

        private bool max = false;

        public bool Max
        {
            get { return max; }
            set { max = value; }
        }


        public ConfigEntry(string settings)
        {
            LoadSettings(settings);
        }  // ConfigEntry


        public void LoadSettings(string settings)
        {
            if (settings.Contains("["))
            {
                settings = settings.Trim();
                settings = settings.Substring(settings.IndexOf("[")+1, settings.Length-1);
                settings = settings.Substring(0, settings.IndexOf("]"));

                string[] commands = settings.Split(setting_delimiter);

                for (int i = 0; i < commands.Length; i++)
                {

                    string cmd_text = "";
                    string cmd_value = "";
                    int idx = commands[i].IndexOf("=");

                    cmd_text = commands[i].Substring(0, idx);
                    cmd_value = commands[i].Substring(idx+1);

                    if (cmd_text.Equals("Bounds"))
                    {
                        ResolveCoordinates(cmd_value);
                    }
                    else if (cmd_text.Equals("ForeColor")) 
                    {
                        this.foreColor = ResolveColor(cmd_value);
                    }
                    else if (cmd_text.Equals("BackColor"))
                    {
                        this.backColor = ResolveColor(cmd_value);
                    }


                
                }







            }
            else
            {

                // Simple settings

                if (settings.ToUpper() == "MAX")
                    max = true;
                else
                {
                    ResolveCoordinates(settings);
                }

            }

        }


        private void ResolveCoordinates(string data)
        {

            try
            {
                string[] koords = data.Split(element_delimiter);

                if (koords.Length != 4)
                    return;

                try
                {
                    this.x = Convert.ToInt32(koords[0].Trim());
                }
                catch
                {
                }


                try
                {
                    this.y = Convert.ToInt32(koords[1].Trim());
                }
                catch
                {
                }

                try
                {
                    this.width = Convert.ToInt32(koords[2].Trim());
                }
                catch
                {
                }

                try
                {
                    this.height = Convert.ToInt32(koords[3].Trim());
                }
                catch
                {
                }

            }
            catch (Exception ex)
            {
                Console.WriteLine("Exception: " + ex);
            }
        
        }



        private Color ResolveColor(string data)
        {
            int r, g, b, a;

            string[] dta_elems = data.Split(element_delimiter);

            if ((dta_elems.Length != 3) && (dta_elems.Length != 4) && (dta_elems.Length != 1))
                 return Color.Empty;

            if (dta_elems.Length == 3)
            {
                r = GetIntNumber(dta_elems[0]);
                g = GetIntNumber(dta_elems[1]);
                b = GetIntNumber(dta_elems[2]);

                return Color.FromArgb(255, r, g, b);
            }
            else if (dta_elems.Length == 4)
            {
                a = GetIntNumber(dta_elems[0]);
                r = GetIntNumber(dta_elems[1]);
                g = GetIntNumber(dta_elems[2]);
                b = GetIntNumber(dta_elems[3]);

                return Color.FromArgb(a, r, g, b);
            }
            else
            {
                return Color.FromName(dta_elems[0]);
            }

        }



        public int GetIntNumber(string data)
        {
            try
            {
                return Convert.ToInt32(data.Trim());
            }
            catch
            {
                return -1;
            }
        }





    } // class
}