using System;
using System.Collections.Generic;
using System.Text;
using ICSharpCode.SharpZipLib.Checksums;
using ICSharpCode.SharpZipLib.Zip;
using System.IO;
using log4net;

namespace ATechTools.File.Zips
{
    public class ZipFiles
    {
        private string m_outputFileName = null;
        private string[] m_fileList = null;

        private static readonly ILog log = LogManager.GetLogger(typeof(ZipFiles).Name);

        public ZipFiles(string outputFileName, string[] fileList, bool autozip)
        {
            this.m_outputFileName = outputFileName;
            this.m_fileList = fileList;

            if (autozip)
            {
                DoZipFiles();
            }
        }


        public bool DoZipFiles()
        {
            Crc32 crc = new Crc32();
            ZipOutputStream zs = null;





            try
            {
                //Log.LogMessage(MSBuild.Community.Tasks.Properties.Resources.ZipCreating, _zipFileName);

                using (zs = new ZipOutputStream(System.IO.File.Create(m_outputFileName)))
                {

                    // make sure level in range 
                    //_zipLevel = System.Math.Max(0, _zipLevel);
                    //_zipLevel = System.Math.Min(9, _zipLevel);

                    zs.SetLevel(9);
                    //if (!string.IsNullOrEmpty(_comment))
                    //    zs.SetComment(_comment);

                    byte[] buffer = new byte[32768];
                    // add files to zip 
                    //foreach (ITaskItem fileItem in _files)
                    foreach(string filex in m_fileList)
                    {
                        string name = filex; // fileItem.ItemSpec;



                        FileInfo file = new FileInfo(name);
                        if (!file.Exists)
                        {
                            //Log.LogWarning(MSBuild.Community.Tasks.Properties.Resources.FileNotFound, file.FullName);
                            continue;
                        }

                        // clean up name 
                        //if (_flatten)
                        //    name = file.Name;
                        //else if (!string.IsNullOrEmpty(_workingDirectory)
                        //&& name.StartsWith(_workingDirectory, true, CultureInfo.InvariantCulture))
                        //    name = name.Remove(0, _workingDirectory.Length);

                        name = ZipEntry.CleanName(file.Name);

                        ZipEntry entry = new ZipEntry(name);
                        entry.DateTime = file.LastWriteTime;
                        entry.Size = file.Length;

                        using (FileStream fs = file.OpenRead())
                        {
                            crc.Reset();
                            long len = fs.Length;
                            while (len > 0)
                            {
                                int readSoFar = fs.Read(buffer, 0, buffer.Length);
                                crc.Update(buffer, 0, readSoFar);
                                len -= readSoFar;
                            }
                            entry.Crc = crc.Value;
                            zs.PutNextEntry(entry);

                            len = fs.Length;
                            fs.Seek(0, SeekOrigin.Begin);
                            while (len > 0)
                            {
                                int readSoFar = fs.Read(buffer, 0, buffer.Length);
                                zs.Write(buffer, 0, readSoFar);
                                len -= readSoFar;
                            }
                        }
                        //Log.LogMessage(MSBuild.Community.Tasks.Properties.Resources.ZipAdded, name);
                    } // foreach file 
                    zs.Finish();
                }
                //Log.LogMessage(MSBuild.Community.Tasks.Properties.Resources.ZipSuccessfully, _zipFileName);
                return true;
            }
            catch (Exception exc)
            {
                log.Error("Error zipping files. Ex.: " + exc, exc);
                //Log.LogErrorFromException(exc);
                return false;
            }
            finally
            {
                if (zs != null)
                    zs.Close();
            }
        } 

    }
}
