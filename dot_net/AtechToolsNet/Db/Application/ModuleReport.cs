// CHANGES
//  - DejanB    26.08.2012  Added IDReport and OrderNumber

using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using AtechTools.Core.Db.Application;
using log4net;

namespace ATechTools.Db.Application
{

    public enum ReportWhereType
    {
        NoWhere = 1,
        SpecifiedWhere = 2,
        ObjectWhere = 3
    }



    public abstract class ModuleReport : DatabaseObject
    {

        private static readonly ILog log = LogManager.GetLogger(typeof(ModuleReport)); 

        
        protected string rowguid;

        protected string moduleID;
        protected string reportName;
        protected string reportFile;
        protected string reportParameters;
        protected string cRC;
        protected string moduleSub;
        protected string sPName;
        protected string idReport;
        protected int orderNumber;
        protected string helpFileName;

        public string HelpFileName
        {
            get { return helpFileName; }
            set { helpFileName = value; }
        }
        string whereCondition;
        private ReportWhereType whereType;
        private bool isTemplate;
        private ModuleHelp reportHelp;
        Dictionary<string, ModuleReportPart> reportParts = null;
        List<ModuleReportSP_Linked> sPParameters = null;
        Dictionary<string, ModuleReportSP_Linked> sPParameters_dict = null;





        public string SPName
        {
            get { return sPName; }
            set { sPName = value; }
        }

        public bool IsTemplate
        {
            get { return isTemplate; }
            set { isTemplate = value; }
        }
        private string parameterType;

        public string ParameterType
        {
            get { return parameterType; }
            set { parameterType = value; }
        }

        private bool hasSPParameters = false;

        public bool HasSPParameters
        {
            get { return hasSPParameters; }
            set { hasSPParameters = value; }
        }


        public ModuleHelp ReportHelp
        {
            get { return reportHelp; }
            set { reportHelp = value; }
        }


        public Dictionary<string, ModuleReportPart> ReportParts
        {
            get { return reportParts; }
            set { reportParts = value; }
        }



        public List<ModuleReportSP_Linked> SPParameters
        {
            get { return sPParameters; }
            set { sPParameters = value; }
        }



        public string Rowguid
        {
            get { return rowguid; }
            set { rowguid = value; }
        }

        public string ModuleID
        {
            get { return moduleID; }
            set { moduleID = value; }
        }

        public string ModuleSub
        {
            get { return moduleSub; }
            set { moduleSub = value; }
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



        public string CRC
        {
            get { return cRC; }
            set { cRC = value; }
        }

        public string IdReport
        {
            get { return this.idReport; }
            set { this.idReport = value; }
        }

        public int OrderNumber
        {
            get { return orderNumber; }
            set { orderNumber = value; }
        }




        public ReportWhereType WhereType
        {
            get { return whereType; }
            set { whereType = value; }
        }


        public string WhereCondition
        {
            get { return whereCondition; }
            set { whereCondition = value; }
        }


        public void AddReportPart(ModuleReportPart mrp)
        {
            /*
            if (this.report_parts_by_sub == null)
                this.report_parts_by_sub = new Dictionary<string, List<ModuleReportPart>>();

            if (this.report_parts_by_sub.ContainsKey(mrp.TargetSubType))
            {

            }
            else
            { 
            }*/

            if (this.reportParts == null)
                this.reportParts = new Dictionary<string, ModuleReportPart>();

            this.reportParts.Add(mrp.ReportPart, mrp);

        }


        public void AddReportSPLink(ModuleReportSP_Link link, List<ModuleReportSP> sps)
        {
            if (link.ReportPart == "")
            {
                AddReportSPLinkProcess(link, sps);
                hasSPParameters = true;
            }
            else
            { 
                if (!this.reportParts.ContainsKey(link.ReportPart))
                {
                    log.Error("Report part " + link.ReportPart + " is not present, so we cannot add SP " + sps[0].SPName + " to it.");
                    return;
                }
                
                this.reportParts[link.ReportPart].AddReportSPLinkProcess(link, sps);
                hasSPParameters = true;
            }
        }


        public void AddReportSPLinkProcess(ModuleReportSP_Link link, List<ModuleReportSP> sps)
        {
            if (sPParameters == null)
            {
                sPParameters = new List<ModuleReportSP_Linked>();
                sPParameters_dict =  new Dictionary<string,ModuleReportSP_Linked>();
            }

            foreach (ModuleReportSP sp in sps)
            {
                ModuleReportSP_Linked lnked = new ModuleReportSP_Linked(sp);
                lnked.SetLink(link);
                
                sPParameters.Add(lnked);
                sPParameters_dict.Add(lnked.ParameterName, lnked);
            }
        }


        public void AddStaticParameters(List<ModuleReportSPParameter> parameters)
        {
            foreach (ModuleReportSPParameter pa in parameters)
            {
                if (pa.ReportPart == "")
                {
                    AddStaticParameter(pa);
                }
                else
                {
                    if (!this.reportParts.ContainsKey(pa.ReportPart))
                    {
                        log.Error("Report part " + pa.ReportPart + " is not present, so we cannot add static paramter " + pa.ParameterName);
                        return;
                    }

                    this.reportParts[pa.ReportPart].AddStaticParameter(pa);
                }
            }
        }


        public void AddStaticParameter(ModuleReportSPParameter param)
        {
            this.sPParameters_dict[param.ParameterName].SetStaticValue(param);
        }



        public override bool AddDb(SqlConnection conn, bool is_id_set)
        {
            throw new NotImplementedException();
        }

        public override bool AddDb(SqlConnection conn)
        {
            throw new NotImplementedException();
        }

        public override bool EditDb(SqlConnection conn)
        {
            throw new NotImplementedException();
        }

        public override bool DeleteDb(SqlConnection conn)
        {
            throw new NotImplementedException();
        }

        public override bool CreateTable(SqlConnection conn)
        {
            throw new NotImplementedException();
        }

        public override string GetDbColumns(int type)
        {
            throw new NotImplementedException();
        }

        public override bool GetDb(SqlDataReader reader, int type)
        {
            throw new NotImplementedException();
        }

        public abstract override bool GetDb(DataRow row, int type);
/*
        {
            this.rowguid = GetStringValueNotNull(row["rowguid"], "");
            this.moduleID = GetStringValueNotNull(row["ModuleID"], "");
            this.reportName = GetStringValueNotNull(row["ReportName"], "");
            this.reportFile = GetStringValueNotNull(row["ReportFile"], "");
            this.reportParameters = GetStringValueNotNull(row["ReportParameters"], "");
            this.cRC = GetStringValueNotNull(row["CRC"], "");
            this.moduleSub = GetStringValueNotNull(row["ModuleSub"], "");
            //this.sPName = GetStringValueNotNull(row["SPName"], "");
            this.whereCondition = GetStringValueNotNull(row["WhereCondition"], "");

            this.isTemplate = GetBoolValueNotNull(row["IsTemplate"], false);
            this.parameterType = GetStringValueNotNull(row["ParameterType"], "");

            this._idReport = GetStringValueNotNull(row["IDReport"], "");
            this.orderNumber = GetIntValueNotNull(row["OrderNumber"], 0);

            this.helpFileName = GetStringValueNotNull(row["HelpURL"], "");




            ProcessWhere();

            return true;
        }

        public void ProcessWhere()
        { 
        }
        */

        public override bool ImportDb(string[] elements, int table_version)
        {
            throw new NotImplementedException();
        }

        public override int TableVersion
        {
            get { throw new NotImplementedException(); }
        }

        public override string TableName
        {
            get { throw new NotImplementedException(); }
        }

        public override string ExportDb(int table_version)
        {
            throw new NotImplementedException();
        }

        public override string ToString()
        {
            return reportName;
        }
    }
}
