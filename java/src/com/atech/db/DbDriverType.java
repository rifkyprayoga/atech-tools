package com.atech.db;

/**
 * Created by andy on 17/05/16.
 */
public enum DbDriverType
{

    DB2("DB2", "com.ibm.db2.jcc.DB2Driver", //
            "jdbc:db2://<hostname>:<port>/<database>", "", "DB2Dialect"), //

    DB2_AS_400("DB2 AS/400", "com.ibm.db2.jcc.DB2Driver", //
            "jdbc:db2://<hostname>:<port>/<database>", "", "DB2400Dialect"),

    DB2_OS390("DB2 OS390", "com.ibm.db2.jcc.DB2Driver", //
            "jdbc:db2://<hostname>:<port>/<database>", "", "DB2390Dialect"),

    Derby("Derby", "org.apache.derby.jdbc.EmbeddedDriver",
            "jdbc:derby:;databaseName=<database>;user=<username>;password=<password>", "", "DerbyDialect"),

    DerbyReadOnly("Derby Read-only JAR", "org.apache.derby.jdbc.EmbeddedDriver",
            "jdbc:derby:jar:(<path_to_archive>)<path_within_archive>;user=<username>;password=<password>", "",
            "DerbyDialect"),

    FrontBase("FrontBase", "com.frontbase.jdbc.FBJDriver", //
            "jdbc:FrontBase://<hostname>:<port>/<database>", "", "FrontbaseDialect"),

    Firebird("Firebird", "org.firebirdsql.jdbc.FBDriver", //
            "jdbc:firebirdsql://<db_tag>:<port>/<db_path>", "3050", "FirebirdDialect"),

    HypersonicSQL("HypersonicSQL Server", "org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://<hostname>/<database>", "",
            "HSQLDialect"),

    HypersonicSQLFile("HypersonicSQL File", "org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:<db_path>", "", "HSQLDialect"),

    HypersonicSQLMemory("HypersonicSQL Memory", "org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:<db_tag>", "", "HSQLDialect"),

    Informix("Informix", "com.informix.jdbc.IfxDriver",
            "jdbc:informix-sqli://<hostname>:<port>/<database>:informixserver=<db_server_name>", "", "InformixDialect"),

    Ingres("Ingres", "ca.edbc.jdbc.EdbcDriver", "jdbc:edbc://<hostname>:<port>/<database>-<server_class>", "",
            "IngresDialect"),

    Interbase("Interbase", "interbase.interclient.Driver", "jdbc:interbase://<hostname>/<database>", "",
            "InterbaseDialect"),

    JDataStore("JDataStore", "com.borland.datastore.jdbc.DataStoreDriver", "jdbc:borland:dslocal:<file>", "",
            "JDataStoreDialect"),

    MicrosoftSQLServer("Microsoft SQL Server", "com.microsoft.jdbc.sqlserver.SQLServerDriver",
            "jdbc:microsoft:sqlserver://<hostname>:<port>;DatabaseName=<database>", "1433", "SQLServerDialect"),

    MckoiSQL("Mckoi SQL", "com.mckoi.JDBCDriver", "jdbc:mckoi://<hostname>:<port>/<database>/", "", "MckoiDialect"),

    Mimer("MimerSQL", "com.mimer.jdbc.Driver", "jdbc:mimer://<hostname>/<database>", "", "MimerSQLDialect"),

    MySQL("MySQL", "com.mysql.jdbc.Driver",
            "jdbc:mysql://<hostname>:<port>/<database>?user=<username>&password=<password>&useUnicode=true&characterEncoding=utf-8",
            "", "MySQLDialect"),

    MySQL_InnoDb("MySQL InnoDb", "com.mysql.jdbc.Driver",
            "jdbc:mysql://<hostname>:<port>/<database>?user=<username>&password=<password>&useUnicode=true&characterEncoding=utf-8",
            "", "MySQLInnoDBDialect"),

    MySQL_MyISAM("MySQL MyISAM", "com.mysql.jdbc.Driver",
            "jdbc:mysql://<hostname>:<port>/<database>?user=<username>&password=<password>&useUnicode=true&characterEncoding=utf-8",
            "", "MySQLMyISAMDialect"),

    Oracle("Oracle (any version)", "oracle.jdbc.OracleDriver", "jdbc:oracle:oci:@<hostname>:<port>:<sid>", "1521",
            "OracleDialect"),

    Oracle_9_10("Oracle 9/10g", "oracle.jdbc.OracleDriver", "jdbc:oracle:oci:@<hostname>:<port>:<sid>", "1521",
            "Oracle9Dialect"),

    Pointbase("Pointbase", "com.pointbase.jdbc.jdbcUniversalDriver", "jdbc:pointbase:server://<hostname>/<database>",
            "", "PointbaseDialect"),

    // PostgreSQL
            PostgreSQL("PostgreSQL", "org.postgresql.Driver",
                    "jdbc:postgresql://<hostname>:<port>/<database>?user=<username>&password=<password>", "5432",
                    "PostgreSQLDialect"),

    // Progress
                    Progress("Progress", "com.progress.sql.jdbc.JdbcProgressDriver",
                            "jdbc:jdbcprogress:T:<hostname>:<port>|<service_name>:<database>", "", "ProgressDialect"),

    // RDMSOS2200Dialect
                            RDMSOS_2200("RDMSOS 2200", "com.unisys.os2200.rdms.jdbc.RdmsDriver",
                                    "jdbc:rdms:schema=<database>; host=<hostname>; port=<port>", "",
                                    "RDMSOS2200Dialect"),

    // SAP DB
                                    SAP_DB("SAP DB", "com.sap.dbtech.jdbc.DriverSapDB",
                                            "jdbc:sapdb://<hostname>:<port>/<database>", "", "SAPDBDialect"),

    // Sybase
                                            Sybase("Sybase", "com.sybase.jdbc3.jdbc.SybDriver",
                                                    "jdbc:sybase:Tds:<hostname>:<port>/<database>", "2048",
                                                    "SybaseDialect"),

    // Sybase 11
                                                    Sybase_11("Sybase 11", "com.sybase.jdbc3.jdbc.SybDriver",
                                                            "jdbc:sybase:Tds:<hostname>:<port>/<database>", "2048",
                                                            "Sybase11Dialect"),

    // Sybase Anywhere
                                                            Sybase_Anywhere("Sybase Anywhere",
                                                                    "com.sybase.jdbc3.jdbc.SybDriver",
                                                                    "jdbc:sybase:Tds:<hostname>:<port>/<database>",
                                                                    "2048", "SybaseAnywhereDialect"),

    // TimesTenDialect
                                                                    TimesTen("TimesTen",
                                                                            "com.timesten.jdbc.TimesTenDriver",
                                                                            "jdbc:timesten:direct:<database>", "",
                                                                            "TimesTenDialect"),

    // H2 SQL
                                                                            H2_SQL("H2 SQL", "org.h2.Driver",
                                                                                    "jdbc:h2:<db_path>", "",
                                                                                    "H2Dialect"),

    // Unknown
                                                                                    UnsupportedDb("Unsupported Db",
                                                                                            "Undefined", "Undefined",
                                                                                            "", "Dialect"),

    ;

    @Deprecated
    DbDriverType()
    {
    }


    DbDriverType(String friendlyName, String driverClass, String urlTemplate, String defaultPort,
            String hibernateDialect)
    {

    }

}
