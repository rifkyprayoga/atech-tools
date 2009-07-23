

using System.IO;
using log4net;
using System.Collections.Generic;



namespace ATechTools.Util
{

    /// <summary>
    /// This is like java's Properties class
    /// </summary>
    public class PropertiesFile
    {

        private ILog log = LogManager.GetLogger(typeof(PropertiesFile));
        private static char[] delim = { '=' };
        private Dictionary<string, string> entries;




        public PropertiesFile()
        {
            entries = new Dictionary<string, string>();

        }

        public bool LoadFile(string filename)
        {

            FileInfo fi = new FileInfo(filename);

            if (!fi.Exists)
            {
                log.Error("Properties file '" + filename + "' is missing.");
                return false;
            }

            StreamReader s = File.OpenText(filename);
            string read = null;
            string[] parts = null;

            while ((read = s.ReadLine()) != null)
            {
                read = read.Trim();
                if ((read.StartsWith(";")) || (read.StartsWith("[")) || (read.StartsWith("#")))
                {
                    // ignore
                }
                else
                {
                    if (read.Contains("="))
                    {
                        parts = read.Split(delim);
                        this.entries.Add(parts[0], parts[1]);
                    }
                }

            }

            s.Close();





            return false;

        }



        public string Get(string key)
        { 
            if (this.entries.ContainsKey(key))
                return this.entries[key];
            else
                return null;
        }



    }


}
