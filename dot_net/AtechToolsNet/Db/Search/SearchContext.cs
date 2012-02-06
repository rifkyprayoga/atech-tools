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



        public string GetSearchWhere(string val)
        {
            StringBuilder sb = new StringBuilder();

            int active_params = 0;

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



            }


            return sb.ToString();
        
        }





    }
}
