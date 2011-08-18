using System;

using System.Collections.Generic;
using System.Text;

namespace AtechToolsNetMobile.File
{
    public class FileReaderConfig : FileReader 
    {

        private Dictionary<string, string> data = null;



        public FileReaderConfig(string filename) : base(filename)
        { 
        }


        public override bool IsLineData(string line)
        {
            return (line.IndexOf("=") > 0);
        }


        public void ReadConfigFile()
        { 
            string line;
            while ((line = this.GetNextDataLine()) != null)
            {
                int idx = line.IndexOf("=");
                this.data.Add(line.Substring(0, idx), line.Substring(idx + 1));
            }
        }


        public Dictionary<string, string> GetData()
        {
            return this.data;
        }

    }
}
