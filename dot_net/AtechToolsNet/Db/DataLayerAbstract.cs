using System;

using System.Collections.Generic;
using System.Text;
using System.Data.SqlClient;
using System.IO;
using System.Data;
using System.Collections;

// CHANGED:
// - 1.11.2012 [Andy]   - Added Limited Connection (this is free standing connection, which user must dispose off (use in using clause))


namespace ATechTools.Db
{
    public abstract class DataLayerAbstract
    {

        protected string sql_source = null;
        protected string db_filename = null;
        protected SqlConnection m_connection = null;
        protected string errorDescription = null;
        protected int errorCode = 0;
        protected Exception errorException = null;

        public DataLayerAbstract(bool do_init)
        {
            if (do_init)
                InitDataLayer();
        }

        public abstract void InitDataLayer();


        /*
        protected void CheckDb()
        {

            // check if exists, if not create
            if (!(new FileInfo(db_filename).Exists))
            {
                try
                {
                    CheckPath();
                    //string sql_source;
                    //sql_source = "Data Source = ./db/nar_db.mdf; Password = jagchat"

                    //SqlCeEngine engine = new SqlCeEngine(sql_source);
                    //engine.CreateDatabase();
                    //engine.Dispose();
                    
                    CreateConnection();

                    //CreateTables();
                }
                catch //(Exception ex)
                {
                    //MessageBox.Show(ex.Message);
                }

            }
            else
            {
                CreateConnection();
            }




        }*/

        char[] chh = { '/' };


        //protected abstract void CheckPath();
/*        {
            string[] paths = this.db_filename.Split(chh);

            DirectoryInfo di = new DirectoryInfo(paths[0]);

            for (int i = 1; i < paths.Length - 1; i++)
            { 
                
            }


        }*/


        /*
        public void CheckConnectionOpen()
        {
            if (this.m_connection == null)
                this.CreateConnection();
            


            if (this.m_connection.State == ConnectionState.Closed)
            {
                this.CreateConnection();
                
                //this.m_connection.Open();
            }
        }*/



        protected virtual void CreateConnection()
        {
            this.m_connection = new SqlConnection(sql_source);

            //this.m_connection.S
            //this.m_connection.ConnectionTimeout = (30 * 60);
        }


        //protected abstract void CreateTables();


        public SqlConnection LimitedConnection
        {
            get
            {

                SqlConnection conn = new SqlConnection(sql_source);
                conn.Open();
                return conn;
            }
        }




        public SqlConnection Connection
        {
            get
            {
                if (this.m_connection != null)
                {
                    if (this.m_connection.State != ConnectionState.Closed)
                        this.m_connection.Close();
                    this.m_connection.Dispose();
                    this.m_connection = null;
                }

                CreateConnection();
                this.m_connection.Open();
                //CheckConnectionOpen();
                return this.m_connection;
            }
        }


        public SqlConnection GetConnection()
        {
            return this.Connection;
            /*
            SqlConnection conn = null;

            conn = new SqlConnection(sql_source);
            conn.Open();
                //CheckConnectionOpen();
            return conn;*/
        }


        public bool AddDb(DatabaseObject dob)
        {
            try
            {
                dob.AddDb(Connection);
                return true;
            }
            catch (Exception ex)
            {
                this.errorCode = 1;
                this.errorException = ex;
                return false;
            }
        }


        public bool EditDb(DatabaseObject dob)
        {
            try
            {
                dob.EditDb(Connection);
                return true;
            }
            catch (Exception ex)
            {
                this.errorCode = 2;
                this.errorException = ex;
                return false;
            }
        }


        public bool DeleteDb(DatabaseObject dob)
        {
            try
            {
                dob.DeleteDb(Connection);
                return true;
            }
            catch (Exception ex)
            {
                this.errorCode = 3;
                this.errorException = ex;
                return false;
            }
        }


        public DataTable GetDataTableFromSqlDataReader(SqlDataReader dr)
        {
            DataTable dtSchema = dr.GetSchemaTable();
            DataTable dt = new DataTable();
            ArrayList listCols = new ArrayList();
            if (dtSchema != null)
            {
                foreach (DataRow drow in dtSchema.Rows)
                {
                    string columnName = Convert.ToString(drow["columnName"]); //drow["columnName"].ToString();
                    DataColumn column = new DataColumn(columnName, (Type)(drow["DataType"]));
                    column.Unique = (bool)(drow["IsUnique"]);
                    column.AllowDBNull = (bool)(drow["AllowDBNull"]);
                    column.AutoIncrement = (bool)(drow["IsAutoIncrement"]);
                    column.AutoIncrement = (bool)(drow["IsAutoIncrement"]);
                    listCols.Add(column);
                    dt.Columns.Add(column);
                }

                while (dr.Read())
                {
                    DataRow dataRow = dt.NewRow();
                    for (int i = 0; i < listCols.Count; i++)
                    {
                        dataRow[((DataColumn)listCols[i])] = dr[i];
                    }

                    dt.Rows.Add(dataRow);
                }

            }

            return dt;

        }


    }
}
