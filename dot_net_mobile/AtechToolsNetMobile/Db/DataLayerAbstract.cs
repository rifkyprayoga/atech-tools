using System;

using System.Collections.Generic;
using System.Text;
using System.Data.SqlServerCe;
using System.IO;
using System.Data;

namespace AtechToolsNetMobile.Db
{
    public abstract class DataLayerAbstract
    {

        protected string sql_source = null;
        protected string db_filename = null;
        protected SqlCeConnection m_connection = null;


        public DataLayerAbstract()
        { 
        }

        public abstract void InitDataLayer();



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

                    SqlCeEngine engine = new SqlCeEngine(sql_source);
                    engine.CreateDatabase();
                    engine.Dispose();
                    
                    CreateConnection();

                    CreateTables();
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




        }

        char[] chh = { '/' };


        protected abstract void CheckPath();
/*        {
            string[] paths = this.db_filename.Split(chh);

            DirectoryInfo di = new DirectoryInfo(paths[0]);

            for (int i = 1; i < paths.Length - 1; i++)
            { 
                
            }


        }*/



        public void CheckConnectionOpen()
        {
            if (this.m_connection.State == ConnectionState.Closed)
                this.m_connection.Open();
        }



        private void CreateConnection()
        {
            this.m_connection = new SqlCeConnection(sql_source);
        }


        protected abstract void CreateTables();


        public SqlCeConnection Connection
        {
            get
            {
                return this.m_connection;
            }
        }


    }
}
