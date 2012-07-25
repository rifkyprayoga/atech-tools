using System;

using System.Collections.Generic;
using System.Text;


namespace ATechTools.File.Import
{
    public abstract class ImportExportManagerAbstract
    {

        private object importExportConfig = null;

        public object ImportExportConfig
        {
            get { return importExportConfig; }
            set { importExportConfig = value; }
        }

        //private static ImportExportManagerAbstract s_iem;

        private string[] import_profiles = null; //{ "ATech MDO" };

        private string[] export_profiles = null; //{ "ATech MDO" };


        private ImportTypeInterface[] import_profiles_type = null;

        private ImportTypeInterface[] export_profiles_type = null;

        private ImportExportManagerAbstract()
        {
            SetUp();
        }


        public abstract void SetUp();



/*
        public static ImportExportManagerAbstract Instance
        {
            get
            {
                if (s_iem == null)
                {
                    s_iem = new ImportExportManagerAbstract();
                }

                return s_iem;
            }
        }
        */

        public string[] GetImportProfiles()
        {
            return this.import_profiles;
        }

        public string[] GetExportProfiles()
        {
            return this.export_profiles;
        }



        public ImportTypeInterface GetImportType(int type_nr)
        {
            return import_profiles_type[type_nr]; // new ATechImportExport();
        }

        public ImportTypeInterface GetExportType(int type_nr)
        {
            return export_profiles_type[type_nr];
        }

        /*
        public ImportTypeInterface GetImportExportType()
        {
            return new ATechImportExport();
        }*/



    }
}
