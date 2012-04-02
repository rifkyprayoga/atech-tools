using System;
using System.Collections.Generic;
using System.Text;

namespace ATechTools.Db.Application
{
    public class ModuleInstance
    {

        #region Variables
        private string moduleID;
        private string moduleName;
        private ModuleHelp moduleHelp = null;
        private List<ModuleReport> moduleReports = null;
        #endregion



        public List<ModuleReport> ModuleReports
        {
            get { return moduleReports; }
            set { moduleReports = value; }
        }

        public string ModuleID
        {
            get { return moduleID; }
            set { moduleID = value; }
        }

        public string ModuleName
        {
            get { return moduleName; }
            set { moduleName = value; }
        }


        public ModuleHelp ModuleHelp
        {
            get { return moduleHelp; }
            set { moduleHelp = value; }
        }


        public ModuleInstance()
        {
            this.moduleReports = new List<ModuleReport>();
        }



        public void AddHelp(ModuleHelp mh)
        {
            this.moduleHelp = mh;
        }


        public void AddReport(ModuleReport mr)
        {
            if (moduleReports == null)
            {
                this.moduleReports = new List<ModuleReport>();
            }

            this.moduleReports.Add(mr);

        }




    }
}
