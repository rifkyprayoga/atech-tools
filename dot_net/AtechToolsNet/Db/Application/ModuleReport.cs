using System;
using System.Collections.Generic;
using System.Text;
using System.Data;
using AtechToolsNet.GUI.Selector;
using System.Data.SqlClient;

namespace ATechTools.Db.Application
{
    public class ModuleReport //: DatabaseObjectMBO
    {
        private string moduleID;
        private string reportName;
        private string reportFile;
        private string reportParameters;

        public string ModuleID
        {
            get { return moduleID; }
            set { moduleID = value; }
        }


        public string ReportName
        {
            get { return reportName; }
            set { reportName = value; }
        }


        public string ReportFile
        {
            get { return reportFile; }
            set { reportFile = value; }
        }


        public string ReportParameters
        {
            get { return reportParameters; }
            set { reportParameters = value; }
        }

    }
}
