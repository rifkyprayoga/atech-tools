using System;
using System.Collections.Generic;
using System.Text;
using AtechTools.Core.Db;
using log4net;

namespace ATechTools.Db.Application
{
    public class ModuleInstance
    {

        #region Variables
        private static readonly ILog log = LogManager.GetLogger(typeof(ModuleInstance));
        private string moduleID;
        private string moduleName;
        private ModuleHelp moduleHelp = null;
        private List<ModuleReport> moduleReports = null;
        private Dictionary<string, ModuleReport> moduleReportsTable = null;

        public Dictionary<string, ModuleReport> ModuleReportsTable
        {
            get { return moduleReportsTable; }
            set { moduleReportsTable = value; }
        }
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
            this.moduleReportsTable = new Dictionary<string, ModuleReport>();
        }



        public void AddHelp(ModuleHelp mh)
        {
            this.moduleHelp = mh;
        }


        public void AddReport(ModuleReport mr)
        {

            string.IsNullOrWhiteSpace(mr.ModuleSub);

            if (string.IsNullOrWhiteSpace(mr.ModuleSub))   //((mr.ModuleSub == null) && (mr.ModuleSub.Trim().Length == 0))
            {
                if (moduleReports == null)
                    this.moduleReports = new List<ModuleReport>();

                this.moduleReports.Add(mr);
                if (this.moduleReportsTable.ContainsKey(mr.ReportName))
                {
                    
                }
                this.moduleReportsTable.Add(mr.ReportName, mr);
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

                if (this.moduleReportsTable.ContainsKey(mr.ReportName))
                {
                    log.Debug("Sub report '" + mr.ReportName + "' contains duplicated definitions (" + mr.Rowguid + ").");
                }
                this.moduleReportsTable.Add(mr.ReportName, mr);

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


        public bool HasReports(string subs)
        {
            if (this.moduleReportsSubs == null)
                return false;
            else
            {
                if (this.moduleReportsSubs.ContainsKey(subs))
                {
                    return (this.moduleReportsSubs[subs].Count > 0);
                }
                else
                    return false;
            }
        }




        public int GetReportCount()
        {
            if (this.moduleReports == null)
                return 0;
            else
                return this.moduleReports.Count;
        }

        public override string ToString()
        {
            return ModuleName;
        }

        public int GetReportCount(string subs)
        {
            if (this.moduleReportsSubs == null)
                return 0;
            else
            {
                if (this.moduleReportsSubs.ContainsKey(subs))
                {
                    return (this.moduleReportsSubs[subs].Count);
                }
                else
                    return 0;
            }
        }


        public List<ModuleReport> GetModuleReports(string sub)
        {
            if (string.IsNullOrWhiteSpace(sub))
            {
                return this.moduleReports;
            }
            else
            {
                if (this.moduleReportsSubs.ContainsKey(sub))
                {
                    return (this.moduleReportsSubs[sub]);
                }
                else
                    return new List<ModuleReport>();
            }
        }



    }
}
