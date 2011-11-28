using System;

using System.Collections.Generic;
using System.Text;
using System.Data.SqlClient;
using System.Data;
using AtechToolsNet.Utils;


namespace ATechTools.Db
{
    public abstract class DatabaseObjectError : DatabaseObject, IErrorReturn
    {
        public int errorCode = 0;
        public string errorDescription = "";

        public bool WasErrorReceived()
        {
            return (this.errorCode != 0);
        }

        public int GetErrorCode()
        {
            return this.errorCode;
        }

        public string GetErrorDescription()
        {
            return this.errorDescription;
        }
    }
}
