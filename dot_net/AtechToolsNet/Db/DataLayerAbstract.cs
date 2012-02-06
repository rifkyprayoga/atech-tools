using System;

using System.Collections.Generic;
using System.Text;
using System.Data.SqlClient;
using System.IO;
using System.Data;

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



        public void CheckConnectionOpen()
        {
            if (this.m_connection == null)
                this.CreateConnection();
            


            if (this.m_connection.State == ConnectionState.Closed)
            {
                this.CreateConnection();
                
                //this.m_connection.Open();
            }
        }



        protected virtual void CreateConnection()
        {
            this.m_connection = new SqlConnection(sql_source);
        }


        //protected abstract void CreateTables();


        public SqlConnection Connection
        {
            get
            {
                if (this.m_connection != null)
                {
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
            SqlConnection conn = null;

            conn = new SqlConnection(sql_source);
            conn.Open();
                //CheckConnectionOpen();
            return conn;
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


    }
}
