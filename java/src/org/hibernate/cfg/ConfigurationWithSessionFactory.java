package org.hibernate.cfg;

/**
 * This is just changed Configuration. Constructor with SettingsFactory is now public, so that
 * we can create this Configuration from outside classes.
 */
public class ConfigurationWithSessionFactory extends Configuration {

    public ConfigurationWithSessionFactory(SettingsFactory factory)
    {
        super(factory);
    }

}
