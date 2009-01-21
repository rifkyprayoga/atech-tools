package com.atech.db.hibernate.tool;

import java.util.Hashtable;
import java.util.ArrayList;

/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
 *  Copyright (C) 2007  Andy (Aleksander) Rozman (Atech-Software)
 *  
 *  
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 *  
 *  
 *  For additional information about this project please visit our project site on 
 *  http://atech-tools.sourceforge.net/ or contact us via this emails: 
 *  andyrozman@users.sourceforge.net or andy@atech-software.com
 *  
 *  @author Andy
 *
*/


public class DatabaseDefinitions
{

    Hashtable<String, String> tableOfDatabases = null;
    ArrayList<String> listOfDatabases = null;

    int hibernate_main_version = 3;

    public String databaseSettings[] = 
    {

        // DB2
        "DB2",
        "com.ibm.db2.jcc.DB2Driver",
        "jdbc:db2://<hostname>:<port>/<database>",
	"",
        "DB2Dialect",

        // DB2 AS/400
        "DB2 AS/400",
        "com.ibm.db2.jcc.DB2Driver",
        "jdbc:db2://<hostname>:<port>/<database>",
	"",
        "DB2400Dialect",

        // DB2 OS390
        "DB2 OS390",
	"com.ibm.db2.jcc.DB2Driver",
	"jdbc:db2://<hostname>:<port>/<database>",
	"",
        "DB2390Dialect",

	// Derby
	"Derby",
	"org.apache.derby.jdbc.EmbeddedDriver",
	"jdbc:derby:;databaseName=<database>;user=<username>;password=<password>",
	"",
	"DerbyDialect",

	// Derby Read-only JAR
	"Derby Read-only JAR",
	"org.apache.derby.jdbc.EmbeddedDriver",
	"jdbc:derby:jar:(<path_to_archive>)<path_within_archive>;user=<username>;password=<password>",
	"",
	"DerbyDialect",

        // FrontBase
        "FrontBase",
        "com.frontbase.jdbc.FBJDriver",
        "jdbc:FrontBase://<hostname>:<port>/<database>",
	"",
        "FrontbaseDialect",

        // Firebird
        "Firebird",
        "org.firebirdsql.jdbc.FBDriver",
        "jdbc:firebirdsql://<db_tag>:<port>/<db_path>",
	"3050",
        "FirebirdDialect",

        // HypersonicSQL
        "HypersonicSQL Server",
        "org.hsqldb.jdbcDriver",
        "jdbc:hsqldb:hsql://<hostname>/<database>",
	"",
        "HSQLDialect",

	// HypersonicSQL
	"HypersonicSQL File",
	"org.hsqldb.jdbcDriver",
	"jdbc:hsqldb:file:<db_path>",
	"",
	"HSQLDialect",

	// HypersonicSQL
	"HypersonicSQL Memory",
	"org.hsqldb.jdbcDriver",
	"jdbc:hsqldb:mem:<db_tag>",
	"",
	"HSQLDialect",

        // Informix
        "Informix",
        "com.informix.jdbc.IfxDriver",
        "jdbc:informix-sqli://<hostname>:<port>/<database>:informixserver=<db_server_name>",
	"",
        "InformixDialect",

        // Ingres
        "Ingres",
        "ca.edbc.jdbc.EdbcDriver",
        "jdbc:edbc://<hostname>:<port>/<database>-<server_class>",
	"",
        "IngresDialect",

        // Interbase
        "Interbase",
        "interbase.interclient.Driver",
        "jdbc:interbase://<hostname>/<database>",
	"",
        "InterbaseDialect",

	// JDataStore
	"JDataStore",
	"com.borland.datastore.jdbc.DataStoreDriver",
	"jdbc:borland:dslocal:<file>",
	"",
	"JDataStoreDialect",

        // Microsoft SQL Server
        "Microsoft SQL Server",
        "com.microsoft.jdbc.sqlserver.SQLServerDriver",
        "jdbc:microsoft:sqlserver://<hostname>:<port>;DatabaseName=<database>",
	"1433",
        "SQLServerDialect",

        // Mckoi SQL
        "Mckoi SQL",
        "com.mckoi.JDBCDriver",
        "jdbc:mckoi://<hostname>:<port>/<database>/",
	"",
        "MckoiDialect",

	// Mimer
	"MimerSQL",
	"com.mimer.jdbc.Driver",
	"jdbc:mimer://<hostname>/<database>",
	"",
	"MimerSQLDialect",

        // MySQL
        "MySQL",
        "com.mysql.jdbc.Driver",
        "jdbc:mysql://<hostname>:<port>/<database>?user=<username>&password=<password>&useUnicode=true&characterEncoding=utf-8",
	"",
        "MySQLDialect",

	// MySQL InnoDb
	"MySQL InnoDb",
	"com.mysql.jdbc.Driver",
	"jdbc:mysql://<hostname>:<port>/<database>?user=<username>&password=<password>&useUnicode=true&characterEncoding=utf-8",
	"",
	"MySQLInnoDBDialect",

	// MySQL MyIsam
	"MySQL MyISAM",
	"com.mysql.jdbc.Driver",
	"jdbc:mysql://<hostname>:<port>/<database>?user=<username>&password=<password>&useUnicode=true&characterEncoding=utf-8",
	"",
	"MySQLMyISAMDialect",



        // Oracle (any version)
        "Oracle (any version)",
	"oracle.jdbc.OracleDriver",
	"jdbc:oracle:oci:@<hostname>:<port>:<sid>",
	"1521",
        "OracleDialect",

        // Oracle 9/10g
        "Oracle 9/10g",
        "oracle.jdbc.OracleDriver",
        "jdbc:oracle:oci:@<hostname>:<port>:<sid>",
	"1521",
        "Oracle9Dialect",

        // Pointbase
        "Pointbase",
        "com.pointbase.jdbc.jdbcUniversalDriver",
        "jdbc:pointbase:server://<hostname>/<database>",
	"",
        "PointbaseDialect",

        // PostgreSQL
        "PostgreSQL",
        "org.postgresql.Driver",
        "jdbc:postgresql://<hostname>:<port>/<database>?user=<username>&password=<password>",
	"5432",
        "PostgreSQLDialect",

        // Progress
        "Progress",
        "com.progress.sql.jdbc.JdbcProgressDriver",
	"jdbc:jdbcprogress:T:<hostname>:<port>|<service_name>:<database>",
	"",
        "ProgressDialect",


	// RDMSOS2200Dialect
	"RDMSOS 2200",
	"com.unisys.os2200.rdms.jdbc.RdmsDriver",
	"jdbc:rdms:schema=<database>; host=<hostname>; port=<port>",
	"",
	"RDMSOS2200Dialect",

        // SAP DB
        "SAP DB",
        "com.sap.dbtech.jdbc.DriverSapDB",
        "jdbc:sapdb://<hostname>:<port>/<database>",
	"",
        "SAPDBDialect",

        // Sybase
        "Sybase",
        "com.sybase.jdbc3.jdbc.SybDriver",
        "jdbc:sybase:Tds:<hostname>:<port>/<database>",
	"2048",
        "SybaseDialect",

	// Sybase 11
	"Sybase 11",
	"com.sybase.jdbc3.jdbc.SybDriver",
	"jdbc:sybase:Tds:<hostname>:<port>/<database>",
	"2048",
	"Sybase11Dialect",

        // Sybase Anywhere
        "Sybase Anywhere",
	"com.sybase.jdbc3.jdbc.SybDriver",
	"jdbc:sybase:Tds:<hostname>:<port>/<database>",
	"2048",
        "SybaseAnywhereDialect",

	// TimesTenDialect
	"TimesTen",
	"com.timesten.jdbc.TimesTenDriver",
	"jdbc:timesten:direct:<database>",
	"",
	"TimesTenDialect",

	// Unknown
	"Unsupported Db",
	"Undefined",
	"Undefined",
	"",
	"Dialect",

    };


    public ArrayList<String> getListOfAvailableDatabases()
    {
        if (listOfDatabases == null) 
        {
            listOfDatabases = new ArrayList<String>();

            for (int i=0; i<this.databaseSettings.length; i+=5) 
            {
                listOfDatabases.add(getDatabaseName(i));
            }
        }

        return listOfDatabases;
    }


    public Hashtable<String, String> getTableOfAvailableDatabases()
    {
		if (tableOfDatabases == null) 
		{
		    tableOfDatabases = new Hashtable<String, String>();
	
		    for (int i=0; i<this.databaseSettings.length; i+=5) 
		    {
		    	tableOfDatabases.put(getDatabaseName(i), "" +i);
		    }
		}
	
		return tableOfDatabases;
    }



    public String getDatabaseName(int index)
    {
        return databaseSettings[index];
    }

    public String getJdbcDriver(int index)
    {
        return databaseSettings[index+1];
    }

    public String getJdbcURL(int index)
    {
        return databaseSettings[index+2];
    }

    public String getDatabasePort(int index)
    {
	return databaseSettings[index+3];
    }


    public String getHibernateDialect(int index)
    {
	String prefix = "";

	if (hibernate_main_version>2)
	    prefix = "org.hibernate.dialect.";
	else
	    prefix = "net.sf.dialect.";

        return prefix + databaseSettings[index+4];
    }

    public String getHibernateDialectWithout(int index)
    {
	return databaseSettings[index+4];
    }


}
