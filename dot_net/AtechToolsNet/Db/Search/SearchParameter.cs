using System;
using System.Collections.Generic;
using System.Text;

namespace ATechTools.Db.Search
{
    public class SearchParameter
    {

        public const int COMMAND_EQUALS = 1;

        public const int COMMAND_LIKE = 2;


        public const int SQLTYPE_STRING = 1;

        public const int SQLTYPE_LONG = 2;

        public const int SQLTYPE_INT = 3;

        public const int SQLTYPE_DECIMAL = 4;

        public const int SQLTYPE_OTHER = 5;


        protected string caption = "";
        protected string sqlParameter = "";
        protected long index = 0;
        protected int commandType = 0;
        protected bool enabled = false;
        protected int sqlType = 0;


        public string Caption
        {
            get { return caption; }
            set { caption = value; }
        }


        public string SqlParameter
        {
            get { return sqlParameter; }
            set { sqlParameter = value; }
        }


        public long Index
        {
            get { return index; }
            set { index = value; }
        }


        public int CommandType
        {
            get { return commandType; }
            set { commandType = value; }
        }


        public bool Enabled
        {
            get { return enabled; }
            set { enabled = value; }
        }


        public int SqlType
        {
            get { return sqlType; }
            set { sqlType = value; }
        }


        public SearchParameter(string caption, string sqlParam, long index, int command_ty, bool enabled, int sqlType)
        { 
            this.caption = caption;
            this.sqlParameter = sqlParam;
            this.index = index;
            this.commandType = command_ty;
            this.enabled = enabled;
            this.sqlType = sqlType;
        }




    }
}
