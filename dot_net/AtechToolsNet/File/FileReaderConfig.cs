using System;

using System.Collections.Generic;
using System.Text;

namespace ATechTools.File
{
    public class FileReaderConfig : FileReader 
    {

        private Dictionary<string, string> data = null;



        public FileReaderConfig(string filename) : base(filename)
        {
            data = new Dictionary<string, string>();
        }


        public override bool IsLineData(string line)
        {
            return (line.IndexOf("=") > 0);
        }


        public void ReadConfigFile()
        { 
            string line;
            StartReading();
            while ((line = this.GetNextDataLine()) != null)
            {
                int idx = line.IndexOf("=");

                try
                {
                    this.data.Add(line.Substring(0, idx), line.Substring(idx + 1));
                }
                catch { }
            }
            EndReading();
        }


        public Dictionary<string, string> GetData()
        {
            return this.data;
        }

    }
}
