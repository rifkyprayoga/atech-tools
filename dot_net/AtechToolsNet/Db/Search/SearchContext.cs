using System;
using System.Collections.Generic;
using System.Text;


namespace ATechTools.Db.Search
{
    public abstract class SearchContext
    {

        List<SearchParameter> searchParameters = new List<SearchParameter>();

        public List<SearchParameter> SearchParameters
        {
            get { return searchParameters; }
            set { searchParameters = value; }
        }



        public void AddSearchParameter(SearchParameter srch_par)
        {
            this.searchParameters.Add(srch_par);
        }



        public string GetSearchWhere(string tableName, string val)
        {

            if (val.Length == 0)
                return val;

            StringBuilder sb2 = new StringBuilder();

            int active_params = 0;
            bool isInt = false;
            bool isLong = false;
            bool isDecimal = false;

            try
            {
                Int64.Parse(val);
                isLong = true;
                isDecimal = true;
            }
            catch
            { 
            }


            try
            {
                Int32.Parse(val);
                isInt = true;
                isDecimal = true;
            }
            catch
            {
            }
            

            if (!isDecimal)
            {
                try
                {
                    Decimal.Parse(val);
                    isDecimal = true;
                }
                catch
                {
                }
            }

            

            for (int i = 0; i < searchParameters.Count; i++)
            {
                if (searchParameters[i].Enabled)
                {

                    string sbTemp = "";

                    if (searchParameters[i].CommandType == SearchParameter.COMMAND_EQUALS)
                    {
                        sbTemp = "=";

                        if (searchParameters[i].SqlType == SearchParameter.SQLTYPE_STRING)
                        {
                            sbTemp += "'" + val + "'";
                        }
                        else if (searchParameters[i].SqlType == SearchParameter.SQLTYPE_LONG)
                        {
                            if (!isLong)
                                sbTemp = null;
                            else
                                sbTemp += val;
                        }
                        else if (searchParameters[i].SqlType == SearchParameter.SQLTYPE_INT)
                        {
                            if (!isInt)
                                sbTemp = null;
                            else
                                sbTemp += val;
                        }
                        else if (searchParameters[i].SqlType == SearchParameter.SQLTYPE_DECIMAL)
                        {
                            if (!isDecimal)
                                sbTemp = null;
                            else
                                sbTemp += val;
                        }
                        else
                        {
                            sbTemp += val;
                        }

                    }
                    else if (searchParameters[i].CommandType == SearchParameter.COMMAND_LIKE)
                    {
                        sbTemp = " LIKE ";

                        if (searchParameters[i].SqlType == SearchParameter.SQLTYPE_STRING)
                        {
                            sbTemp += "'%" + val + "%'";
                        }
                        else
                        {
                            sbTemp += val;
                        }
                    }


                    if ((sbTemp != null) && (sbTemp.Length > 4))
                    {
                        bool exclude = false;
                        if (searchParameters[i].HasSubSearch && searchParameters[i].SqlSubParameter.ExcludeFromPrimary)
                        {
                            exclude = true;
                        }

                        if (!exclude)
                        {
                            if (active_params > 0)
                                sb2.Append(" OR ");

                            active_params++;

                            sb2.Append("(" + searchParameters[i].SqlParameter + " " + sbTemp);

                            sb2.Append(")");
                        }
                    }
                    
                    if (searchParameters[i].HasSubSearch)
                    {
                        if ((sbTemp != null) && (sbTemp.Length > 4))
                        {
                            string subwhere = searchParameters[i].GetSubSearch(tableName, sbTemp);
                            if (!string.IsNullOrEmpty(subwhere))
                            {
                                if (active_params > 0)
                                    sb2.Append(" OR ");

                                active_params++;
                                sb2.Append("(" + subwhere);
                                sb2.Append(")");
                            }
                        }
                    }
                }

            }







/*
            for (int i = 0; i < searchParameters.Count; i++)
            {
                if (searchParameters[i].Enabled)
                {

                    if (active_params > 0)
                        sb.Append(" OR ");

                    active_params++;

                    sb.Append("(" + searchParameters[i].SqlParameter + " ");


                    if (searchParameters[i].CommandType == SearchParameter.COMMAND_EQUALS)
                    {
                        sb.Append("=");

                        if (searchParameters[i].SqlType == SearchParameter.SQLTYPE_STRING)
                        {
                            sb.Append("'" + val + "'");
                        }
                        else if (searchParameters[i].SqlType == SearchParameter.SQLTYPE_LONG)
                        {
 
                        }
                        else
                        {
                            sb.Append(val);
                        }

                    }
                    else if (searchParameters[i].CommandType == SearchParameter.COMMAND_LIKE)
                    {
                        sb.Append(" LIKE ");

                        if (searchParameters[i].SqlType == SearchParameter.SQLTYPE_STRING)
                        {
                            sb.Append("'%" + val + "%'");
                        }
                        else
                        {
                            sb.Append(val);
                        }
                    }

                    sb.Append(")");
                }

            }*/


            return sb2.ToString();
        
        }


        public void SetFilterParameters(long parameters)
        {
            foreach (SearchParameter sp in this.searchParameters)
            {
                if ((parameters & sp.Index) == sp.Index)
                    sp.Enabled = true;
                else
                    sp.Enabled = false;
            }

        }


        public long GetFilterParameters()
        {
            long parameters = 0L;

            foreach (SearchParameter sp in this.searchParameters)
            {
                if (sp.Enabled)
                {
                    parameters += sp.Index;
                }
            }

            return parameters;
        }


    }
}
