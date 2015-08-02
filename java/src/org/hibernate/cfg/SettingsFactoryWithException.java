package org.hibernate.cfg;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.ConnectionReleaseMode;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cache.NoCacheProvider;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.Settings;
import org.hibernate.cfg.SettingsFactory;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.DialectFactory;
import org.hibernate.exception.SQLExceptionConverter;
import org.hibernate.exception.SQLExceptionConverterFactory;
import org.hibernate.transaction.TransactionFactory;
import org.hibernate.util.PropertiesHelper;
import org.hibernate.util.StringHelper;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * This class just extends default functionailty. When there is problem getting metadata
 * (eg. problem connection with database), runtime exception is thrown.
 */
public class SettingsFactoryWithException extends SettingsFactory {


    private Log log = LogFactory.getLog(SettingsFactoryWithException.class);

    public Settings buildSettings(Properties props) {
        Settings settings = new Settings();

        //SessionFactory name:

        String sessionFactoryName = props.getProperty(Environment.SESSION_FACTORY_NAME);
        settings.setSessionFactoryName(sessionFactoryName);

        //JDBC and connection settings:

        ConnectionProvider connections = createConnectionProvider(props);
        settings.setConnectionProvider(connections);

        //Interrogate JDBC metadata

        boolean metaSupportsScrollable = false;
        boolean metaSupportsGetGeneratedKeys = false;
        boolean metaSupportsBatchUpdates = false;
        String databaseName = null;
        int databaseMajorVersion = 0;

        try {
            Connection conn = connections.getConnection();
            try {
                DatabaseMetaData meta = conn.getMetaData();
                databaseName = meta.getDatabaseProductName();
                databaseMajorVersion = getDatabaseMajorVersion(meta);
                log.info("RDBMS: " + databaseName + ", version: " + meta.getDatabaseProductVersion() );
                log.info("JDBC driver: " + meta.getDriverName() + ", version: " + meta.getDriverVersion() );

                metaSupportsScrollable = meta.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE);
                metaSupportsBatchUpdates = meta.supportsBatchUpdates();

                if ( Environment.jvmSupportsGetGeneratedKeys() ) {
                    try {
                        Boolean result = (Boolean) DatabaseMetaData.class.getMethod("supportsGetGeneratedKeys", null)
                                .invoke(meta, null);
                        metaSupportsGetGeneratedKeys = result.booleanValue();
                    }
                    catch (AbstractMethodError ame) {
                        metaSupportsGetGeneratedKeys = false;
                    }
                    catch (Exception e) {
                        metaSupportsGetGeneratedKeys = false;
                    }
                }

            }
            finally {
                connections.closeConnection(conn);
            }
        }
        catch (SQLException sqle) {
            log.warn("Could not obtain connection metadata", sqle);
            throw new HibernateException(sqle);
        }
        catch (UnsupportedOperationException uoe) {
            // user supplied JDBC connections
        }

        //SQL Dialect:
        Dialect dialect = determineDialect( props, databaseName, databaseMajorVersion );
        settings.setDialect(dialect);

        //use dialect default properties
        final Properties properties = new Properties();
        properties.putAll( dialect.getDefaultProperties() );
        properties.putAll(props);

        // Transaction settings:

        TransactionFactory transactionFactory = createTransactionFactory(properties);
        settings.setTransactionFactory(transactionFactory);
        settings.setTransactionManagerLookup( createTransactionManagerLookup(properties) );

        boolean flushBeforeCompletion = PropertiesHelper.getBoolean(Environment.FLUSH_BEFORE_COMPLETION, properties);
        log.info("Automatic flush during beforeCompletion(): " + enabledDisabled(flushBeforeCompletion) );
        settings.setFlushBeforeCompletionEnabled(flushBeforeCompletion);

        boolean autoCloseSession = PropertiesHelper.getBoolean(Environment.AUTO_CLOSE_SESSION, properties);
        log.info("Automatic session close at end of transaction: " + enabledDisabled(autoCloseSession) );
        settings.setAutoCloseSessionEnabled(autoCloseSession);

        //JDBC and connection settings:

        int batchSize = PropertiesHelper.getInt(Environment.STATEMENT_BATCH_SIZE, properties, 0);
        if ( !metaSupportsBatchUpdates ) batchSize = 0;
        if (batchSize>0) log.info("JDBC batch size: " + batchSize);
        settings.setJdbcBatchSize(batchSize);
        boolean jdbcBatchVersionedData = PropertiesHelper.getBoolean(Environment.BATCH_VERSIONED_DATA, properties, false);
        if (batchSize>0) log.info("JDBC batch updates for versioned data: " + enabledDisabled(jdbcBatchVersionedData) );
        settings.setJdbcBatchVersionedData(jdbcBatchVersionedData);
        settings.setBatcherFactory( createBatcherFactory(properties, batchSize) );

        boolean useScrollableResultSets = PropertiesHelper.getBoolean(Environment.USE_SCROLLABLE_RESULTSET, properties, metaSupportsScrollable);
        log.info("Scrollable result sets: " + enabledDisabled(useScrollableResultSets) );
        settings.setScrollableResultSetsEnabled(useScrollableResultSets);

        boolean wrapResultSets = PropertiesHelper.getBoolean(Environment.WRAP_RESULT_SETS, properties, false);
        log.debug( "Wrap result sets: " + enabledDisabled(wrapResultSets) );
        settings.setWrapResultSetsEnabled(wrapResultSets);

        boolean useGetGeneratedKeys = PropertiesHelper.getBoolean(Environment.USE_GET_GENERATED_KEYS, properties, metaSupportsGetGeneratedKeys);
        log.info("JDBC3 getGeneratedKeys(): " + enabledDisabled(useGetGeneratedKeys) );
        settings.setGetGeneratedKeysEnabled(useGetGeneratedKeys);

        Integer statementFetchSize = PropertiesHelper.getInteger(Environment.STATEMENT_FETCH_SIZE, properties);
        if (statementFetchSize!=null) log.info("JDBC result set fetch size: " + statementFetchSize);
        settings.setJdbcFetchSize(statementFetchSize);

        String releaseModeName = PropertiesHelper.getString( Environment.RELEASE_CONNECTIONS, properties, "auto" );
        log.info( "Connection release mode: " + releaseModeName );
        ConnectionReleaseMode releaseMode;
        if ( "auto".equals(releaseModeName) ) {
            releaseMode = transactionFactory.getDefaultReleaseMode();
        }
        else {
            releaseMode = ConnectionReleaseMode.parse( releaseModeName );
            if ( releaseMode == ConnectionReleaseMode.AFTER_STATEMENT && !connections.supportsAggressiveRelease() ) {
                log.warn( "Overriding release mode as connection provider does not support 'after_statement'" );
                releaseMode = ConnectionReleaseMode.AFTER_TRANSACTION;
            }
        }
        settings.setConnectionReleaseMode( releaseMode );

        //SQL Generation settings:

        String defaultSchema = properties.getProperty(Environment.DEFAULT_SCHEMA);
        String defaultCatalog = properties.getProperty(Environment.DEFAULT_CATALOG);
        if (defaultSchema!=null) log.info("Default schema: " + defaultSchema);
        if (defaultCatalog!=null) log.info("Default catalog: " + defaultCatalog);
        settings.setDefaultSchemaName(defaultSchema);
        settings.setDefaultCatalogName(defaultCatalog);

        Integer maxFetchDepth = PropertiesHelper.getInteger(Environment.MAX_FETCH_DEPTH, properties);
        if (maxFetchDepth!=null) log.info("Maximum outer join fetch depth: " + maxFetchDepth);
        settings.setMaximumFetchDepth(maxFetchDepth);
        int batchFetchSize = PropertiesHelper.getInt(Environment.DEFAULT_BATCH_FETCH_SIZE, properties, 1);
        log.info("Default batch fetch size: " + batchFetchSize);
        settings.setDefaultBatchFetchSize(batchFetchSize);

        boolean comments = PropertiesHelper.getBoolean(Environment.USE_SQL_COMMENTS, properties);
        log.info( "Generate SQL with comments: " + enabledDisabled(comments) );
        settings.setCommentsEnabled(comments);

        boolean orderUpdates = PropertiesHelper.getBoolean(Environment.ORDER_UPDATES, properties);
        log.info( "Order SQL updates by primary key: " + enabledDisabled(orderUpdates) );
        settings.setOrderUpdatesEnabled(orderUpdates);

        //Query parser settings:

        settings.setQueryTranslatorFactory( createQueryTranslatorFactory(properties) );

        Map querySubstitutions = PropertiesHelper.toMap(Environment.QUERY_SUBSTITUTIONS, " ,=;:\n\t\r\f", properties);
        log.info("Query language substitutions: " + querySubstitutions);
        settings.setQuerySubstitutions(querySubstitutions);

        // Second-level / query cache:

        boolean useSecondLevelCache = PropertiesHelper.getBoolean(Environment.USE_SECOND_LEVEL_CACHE, properties, true);
        log.info( "Second-level cache: " + enabledDisabled(useSecondLevelCache) );
        settings.setSecondLevelCacheEnabled(useSecondLevelCache);

        boolean useQueryCache = PropertiesHelper.getBoolean(Environment.USE_QUERY_CACHE, properties);
        log.info( "Query cache: " + enabledDisabled(useQueryCache) );
        settings.setQueryCacheEnabled(useQueryCache);

        if ( useSecondLevelCache || useQueryCache ) {
            // The cache provider is needed when we either have second-level cache enabled
            // or query cache enabled.  Note that useSecondLevelCache is enabled by default
            settings.setCacheProvider( createCacheProvider( properties ) );
        }
        else {
            settings.setCacheProvider( new NoCacheProvider() );
        }

        boolean useMinimalPuts = PropertiesHelper.getBoolean(
                Environment.USE_MINIMAL_PUTS, properties, settings.getCacheProvider().isMinimalPutsEnabledByDefault()
        );
        log.info( "Optimize cache for minimal puts: " + enabledDisabled(useMinimalPuts) );
        settings.setMinimalPutsEnabled(useMinimalPuts);

        String prefix = properties.getProperty(Environment.CACHE_REGION_PREFIX);
        if ( StringHelper.isEmpty(prefix) ) prefix=null;
        if (prefix!=null) log.info("Cache region prefix: "+ prefix);
        settings.setCacheRegionPrefix(prefix);

        boolean useStructuredCacheEntries = PropertiesHelper.getBoolean(Environment.USE_STRUCTURED_CACHE, properties, false);
        log.info( "Structured second-level cache entries: " + enabledDisabled(useStructuredCacheEntries) );
        settings.setStructuredCacheEntriesEnabled(useStructuredCacheEntries);

        if (useQueryCache) settings.setQueryCacheFactory( createQueryCacheFactory(properties) );

        //SQL Exception converter:

        SQLExceptionConverter sqlExceptionConverter;
        try {
            sqlExceptionConverter = SQLExceptionConverterFactory.buildSQLExceptionConverter( dialect, properties );
        }
        catch(HibernateException e) {
            log.warn("Error building SQLExceptionConverter; using minimal converter");
            sqlExceptionConverter = SQLExceptionConverterFactory.buildMinimalSQLExceptionConverter();
        }
        settings.setSQLExceptionConverter(sqlExceptionConverter);

        //Statistics and logging:

        boolean showSql = PropertiesHelper.getBoolean(Environment.SHOW_SQL, properties);
        if (showSql) log.info("Echoing all SQL to stdout");
        settings.setShowSqlEnabled(showSql);

        boolean formatSql = PropertiesHelper.getBoolean(Environment.FORMAT_SQL, properties);
        settings.setFormatSqlEnabled(formatSql);

        boolean useStatistics = PropertiesHelper.getBoolean(Environment.GENERATE_STATISTICS, properties);
        log.info( "Statistics: " + enabledDisabled(useStatistics) );
        settings.setStatisticsEnabled(useStatistics);

        boolean useIdentifierRollback = PropertiesHelper.getBoolean(Environment.USE_IDENTIFIER_ROLLBACK, properties);
        log.info( "Deleted entity synthetic identifier rollback: " + enabledDisabled(useIdentifierRollback) );
        settings.setIdentifierRollbackEnabled(useIdentifierRollback);

        //Schema export:

        String autoSchemaExport = properties.getProperty(Environment.HBM2DDL_AUTO);
        if ( "validate".equals(autoSchemaExport) ) settings.setAutoValidateSchema(true);
        if ( "update".equals(autoSchemaExport) ) settings.setAutoUpdateSchema(true);
        if ( "create".equals(autoSchemaExport) ) settings.setAutoCreateSchema(true);
        if ( "create-drop".equals(autoSchemaExport) ) {
            settings.setAutoCreateSchema(true);
            settings.setAutoDropSchema(true);
        }

        EntityMode defaultEntityMode = EntityMode.parse( properties.getProperty( Environment.DEFAULT_ENTITY_MODE ) );
        log.info( "Default entity-mode: " + defaultEntityMode );
        settings.setDefaultEntityMode( defaultEntityMode );

        return settings;

    }

    protected Dialect determineDialect(Properties props, String databaseName, int databaseMajorVersion) {
        return DialectFactory.buildDialect(props, databaseName, databaseMajorVersion);
    }

    protected static final String enabledDisabled(boolean value) {
        return value ? "enabled" : "disabled";
    }

    protected int getDatabaseMajorVersion(DatabaseMetaData meta) {
        try {
            Method gdbmvMethod = DatabaseMetaData.class.getMethod("getDatabaseMajorVersion", null);
            return ( (Integer) gdbmvMethod.invoke(meta, null) ).intValue();
        }
        catch (NoSuchMethodException nsme) {
            return 0;
        }
        catch (Throwable t) {
            log.debug("could not get database version from JDBC metadata");
            return 0;
        }
    }

}
