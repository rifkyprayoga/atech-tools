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
        private Dictionary<string, List<ModuleReport>> moduleReportsSubs = null;
        
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
            if ((mr.ModuleSub == null) && (mr.ModuleSub.Length == 0))
            {
                if (moduleReports == null)
                    this.moduleReports = new List<ModuleReport>();

                this.moduleReports.Add(mr);
            }
            else
            {
                if (this.moduleReportsSubs == null)
                    this.moduleReportsSubs = new Dictionary<string, List<ModuleReport>>();

                if (this.moduleReportsSubs.ContainsKey(mr.ModuleSub))
                {
                    this.moduleReportsSubs[mr.ModuleSub].Add(mr);
                }
                else
                {
                    List<ModuleReport> lst = new List<ModuleReport>();
                    lst.Add(mr);
                    this.moduleReportsSubs.Add(mr.ModuleSub, lst);
                }
            }

        }


        public void AddReports(List<ModuleReport> list_mr)
        {
            foreach (ModuleReport mr in list_mr)
            {
                AddReport(mr);
            }
        }




        public bool HasReports()
        {
            return (GetReportCount() > 0);
        }


        public int GetReportCount()
        {
            if (this.moduleReports == null)
                return 0;
            else
                return this.moduleReports.Count;
        }


    }
}
