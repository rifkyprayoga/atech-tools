using System;

using System.Collections.Generic;
using System.Text;

namespace AtechToolsNetMobile.GUI.Config
{
    public class ConfigEntry
    {

        public static char delimiter = ',';

        //private string configName = null;

        //private string moduleName = null;

        private int x = -1;

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
            if (settings.ToUpper() == "MAX")
                max = true;
            else
            {
                try
                {
                    string[] koords = settings.Split(delimiter);

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
                catch(Exception ex)
                {
                    Console.WriteLine("Exception: " + ex);
                }
            }

        }  // ConfigEntry

    } // class
}