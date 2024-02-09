using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace AtechTools.Core.Utils
{
    public class DataUtil
    {

        public static string[] separator_keyvalue = { "=" };

        public DataUtil()
        { 
        }


        public string[] GetParametersFromStringAsArray(string value, string[] split_par)
        {
            string[] setAll = value.Split(split_par, StringSplitOptions.RemoveEmptyEntries);


            for (int i = 0; i < setAll.Length; i++)
            {
                setAll[i] = setAll[i].Trim();
            }


            return setAll;
        }



        public Dictionary<string,string> GetParametersFromStringAsDictionary(string value, string[] split_par)
        {
            string[] setAll = value.Split(split_par, StringSplitOptions.None);
            Dictionary<string, string> dt = new Dictionary<string, string>();

            for (int i = 0; i < setAll.Length; i++)
            {
                setAll[i] = setAll[i].Trim();

                if (!dt.ContainsKey(setAll[i]))
                {
                    dt.Add(setAll[i], setAll[i]);
                }

            }

            return dt;
        }


        public string[] GetKeyValuePair(string val)
        {
            return GetParametersFromStringAsArray(val, DataUtil.separator_keyvalue);
        }


        public long GetLongValue(string val)
        {
            return GetLongValue(val, 0L);
        }


        public long GetLongValue(string val, long def_val)
        {
            try
            {
                return Int64.Parse(val);
            }
            catch //(Exception ex)
            {
                return def_val;
            }
        }

        public int GetIntValue(string val)
        {
            return GetIntValue(val, 0);
        }


        public int GetIntValue(string val, int def_val)
        {
            try
            {
                int x = Int32.Parse(val);
                return x;
            }
            catch //(Exception ex)
            {
                return def_val;
            }
        }


        public bool GetBoolValue(string val)
        {
            return GetBoolValue(val, false);
        }


        public bool GetBoolValue(string val, bool def_val)
        {
            try
            {
                return Boolean.Parse(val);
            }
            catch //(Exception ex)
            {
                return def_val;
            }
        }





        public Single GetSingleValue(string val)
        {
            return GetSingleValue(val, 0.0f);
        }


        public Single GetSingleValue(string val, Single def_val)
        {
            try
            {
                return Single.Parse(val);
            }
            catch //(Exception ex)
            {
                return def_val;
            }
        }



        public Double GetDoubleValue(string val)
        {
            return GetDoubleValue(val, 0.0d);
        }


        public Double GetDoubleValue(string val, Double def_val)
        {
            try
            {
                return Double.Parse(val);
            }
            catch //(Exception ex)
            {
                return def_val;
            }
        }




        public static string TimeDifference(DateTime dt1, DateTime dt2, int range)
        {
            TimeSpan ts = dt2.Subtract(dt1);
            return ts.Seconds + "." + ts.Milliseconds;
        }






    }
}
