// -----------------------------------------------------------------------
// <copyright file="DatabaseLogger.cs" company="">
// TODO: Update copyright text.
// </copyright>
// -----------------------------------------------------------------------

namespace AtechTools.Core.Db
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Text;
    using System.Data.SqlClient;
    using System.Data;

    /// <summary>
    /// TODO: Update summary.
    /// </summary>
    public class DatabaseLogger
    {


        public static String ParameterValueForSQL(SqlParameter sp)
        {
            String retval = "";

            if ((sp.Value == DBNull.Value) || (sp.Value == null))
            {
                return "NULL";
            }



            switch (sp.SqlDbType)
            {
                case SqlDbType.Char:
                case SqlDbType.NChar:
                case SqlDbType.NText:
                case SqlDbType.NVarChar:
                case SqlDbType.Text:
                case SqlDbType.Time:
                case SqlDbType.VarChar:
                case SqlDbType.Xml:
                case SqlDbType.Date:
                case SqlDbType.DateTime:
                case SqlDbType.DateTime2:
                case SqlDbType.DateTimeOffset:
                    if (sp.Value == null)
                        retval = "NULL";
                    else
                        retval = "'" + sp.Value.ToString().Replace("'", "''") + "'";
                    break;

                case SqlDbType.Bit:
                    bool val = Convert.ToBoolean(sp.Value);
                    retval = "" + val;
                    break;

                    //case SqlDbType.

                case SqlDbType.BigInt:
                case SqlDbType.Decimal:
                case SqlDbType.Float:
                case SqlDbType.Int:
                case SqlDbType.Real:
                case SqlDbType.SmallInt:
                case SqlDbType.SmallMoney:
                    return "" + sp.Value;


                default:
                    retval = sp.Value.ToString().Replace("'", "''");
                    break;
            }

            return retval;
        }



        public static String CommandAsSql(SqlCommand sc)
        {
            if (sc == null)
                return "SqlCommand object is null";

            StringBuilder sql = new StringBuilder();
            Boolean FirstParam = true;

            sql.AppendLine("use " + sc.Connection.Database + ";");
            switch (sc.CommandType)
            {
                case CommandType.StoredProcedure:
                    sql.AppendLine("declare @return_value int;");

                    foreach (SqlParameter sp in sc.Parameters)
                    {
                        if ((sp.Direction == ParameterDirection.InputOutput) || (sp.Direction == ParameterDirection.Output))
                        {
                            sql.Append("declare " + sp.ParameterName + "\t" + sp.SqlDbType.ToString() + "\t= ");

                            sql.AppendLine(((sp.Direction == ParameterDirection.Output) ? "null" : ParameterValueForSQL(sp)) + ";");

                        }
                    }

                    sql.AppendLine("exec [" + sc.CommandText + "]");

                    foreach (SqlParameter sp in sc.Parameters)
                    {
                        if (sp.Direction != ParameterDirection.ReturnValue)
                        {
                            sql.Append((FirstParam) ? "\t" : "\t, ");

                            if (FirstParam) FirstParam = false;

                            if (sp.Direction == ParameterDirection.Input)
                                sql.AppendLine(sp.ParameterName + " = " + ParameterValueForSQL(sp)); //.ParameterValueForSQL());
                            else
                                sql.AppendLine(sp.ParameterName + " = " + sp.ParameterName + " output");
                        }
                    }
                    sql.AppendLine(";");

                    sql.AppendLine("select 'Return Value' = convert(varchar, @return_value);");

                    foreach (SqlParameter sp in sc.Parameters)
                    {
                        if ((sp.Direction == ParameterDirection.InputOutput) || (sp.Direction == ParameterDirection.Output))
                        {
                            sql.AppendLine("select '" + sp.ParameterName + "' = convert(varchar, " + sp.ParameterName + ");");
                        }
                    }
                    break;
                case CommandType.Text:
                    if (sc.Parameters.Count == 0)
                        sql.AppendLine(sc.CommandText);
                    else
                    {
                        string sq = sc.CommandText;

                        foreach (SqlParameter sp in sc.Parameters)
                        {
                            if ((sp.Direction == ParameterDirection.InputOutput) || (sp.Direction == ParameterDirection.Input))
                            {
                                sq = sq.Replace(sp.ParameterName, ParameterValueForSQL(sp));
                            }
                        }


                        sql.Append(sq);
                    }
                    break;
            }

            return sql.ToString();
        }




    }
}
