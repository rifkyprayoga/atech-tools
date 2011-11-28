using System;

using System.Collections.Generic;
using System.Text;
using System.IO;

namespace ATechTools.File
{
    public class FileReader
    {
        protected int data_lines = 0;
        protected bool file_exists = false;
        protected string filename;
        protected TextReader reader;
        protected List<string> headers;


        public FileReader(string filename)
        {
            this.filename = filename;
            PreReadFile();
        }

        public bool FileFound()
        {
            return file_exists;
        }


        /// <summary>
        /// This will get count of lines. If you have special types of file, 
        /// you need to override this.
        /// </summary>
        public void PreReadFile()
        {
            FileInfo fi = new FileInfo(this.filename);
            this.file_exists = fi.Exists;
            this.headers = new List<string>();

            if (!fi.Exists)
                return;

            data_lines = 0;

            // create reader & open file
            TextReader tr = new StreamReader(this.filename);
            string line;

            while ((line = tr.ReadLine()) != null)
            {

                if (IsLineData(line))
                    this.data_lines++;
                else
                    this.headers.Add(line);
            }

            // close the stream
            tr.Close();


            ProcessHeader();

        }



        public int GetDataLineCount()
        {
            return this.data_lines;
        }


        public bool IsHeaderData(string line)
        {
            if (line.Trim().StartsWith(";"))
                return true;
            else
                return false;
        }


        public virtual bool IsLineData(string line)
        {
            return (!IsHeaderData(line));
        }


        public void StartReading()
        {
            reader = new StreamReader(this.filename);
            //this.PreReadFile();
        }

        public string GetNextDataLine()
        {
            string line;

            try
            {

                while ((line = reader.ReadLine()) != null)
                {
                    if (IsLineData(line))
                        return line;
                }
            }
            catch
            { }


            return null;
        }



        public void EndReading()
        {
            reader.Close();
        }


        public virtual void ProcessHeader()
        { 
        }



    }
}
