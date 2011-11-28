using System;

using System.Collections.Generic;
using System.Text;
using System.IO;

namespace ATechTools.File
{
    public class FileReaderImport : FileReader
    {
        public static string dataDelimiter = "#\\|#";

        private int databaseVersion = 1;

        public int DatabaseVersion
        {
            get { return databaseVersion; }
            set { databaseVersion = value; }
        }
        private int tableVersion = 1;

        public int TableVersion
        {
            get { return tableVersion; }
            set { tableVersion = value; }
        }
        private string dbClass = "";

        public string DbClass
        {
            get { return dbClass; }
            set { dbClass = value; }
        }


        public FileReaderImport(string filename) : base (filename)
        {
        }






        public override bool IsLineData(string line)
        {
            if ((!line.Trim().StartsWith(";"))) // || (line.Trim().Length>0))
                return true;
            else
                return false;
        }


        public override void ProcessHeader()
        {
            // DbClass:
            // Database version: 
            // Table version:

            foreach (string v in this.headers)
            {

                if (v.IndexOf("DbClass:") > -1)
                {
                    this.DbClass = ReadParameterData(v, "DbClass:");
                }
                else if (v.IndexOf("Database version:") > -1)
                {
                    this.DatabaseVersion = Convert.ToInt32(ReadParameterData(v, "Database version:"));
                }
                else if (v.IndexOf("Table version:") > -1)
                {
                    this.TableVersion = Convert.ToInt32(ReadParameterData(v, "Table version:"));
                }
            
            }



        }




        public string ReadParameterData(string data, string parameter)
        {
            string val = data.Substring(data.IndexOf(parameter) + parameter.Length + 1);
            return val.Trim();
        }

    }
}
