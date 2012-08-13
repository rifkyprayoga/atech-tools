// -----------------------------------------------------------------------
// <copyright file="ModuleReportSP_Linked.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace AtechTools.Core.Db.Application
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class ModuleReportSP_Linked :  ModuleReportSP
    {

        string reportRowguid;
        string reportPart;

        string value;
        bool staticValue = false;


        public string ReportRowguid
        {
            get { return reportRowguid; }
            set { reportRowguid = value; }
        }

        public string ReportPart
        {
            get { return reportPart; }
            set { reportPart = value; }
        }




        public string Value
        {
            get { return this.value; }
            set { this.value = value; }
        }

        public bool StaticValue
        {
            get { return staticValue; }
            set { staticValue = value; }
        }



        public ModuleReportSP_Linked(ModuleReportSP sp_def)
        {
            this.sPName = sp_def.SPName;
            this.sPDescription = sp_def.SPDescription;
            this.parameterDescription = sp_def.ParameterDescription;
            this.parameterName = sp_def.ParameterName;
            this.parameterType = sp_def.ParameterType;
            this.parameterTypeFenix = sp_def.ParameterTypeFenix;
            this.required = sp_def.Required;
            this.orderNumber = sp_def.OrderNumber;
        }


        public void SetLink(ModuleReportSP_Link lnk)
        {
            this.reportRowguid = lnk.ReportRowguid;
            this.reportPart = lnk.ReportPart;
        }


        public void SetStaticValue(ModuleReportSPParameter param)
        {
            this.value = param.ParameterValue;
            this.staticValue = true;
        }


    }
}
