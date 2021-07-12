package com.atech.utils;

import org.apache.log4j.*;
import org.apache.log4j.rolling.RollingFileAppender;
import org.apache.log4j.rolling.TimeBasedRollingPolicy;

import com.atech.data.user_data_dir.UserDataDirectory;

/**
 * Created by andy on 16/12/17.
 */
public class Log4jUtil
{

    static String name;
    static UserDataDirectory userDataDirectory = UserDataDirectory.getInstance();


    // https://stackoverflow.com/questions/8965946/configuring-log4j-loggers-programmatically

    public static void initLogger(String name_)
    {
        Logger.getRootLogger().removeAllAppenders();

        name = name_;
    }


    public static ConsoleAppender createConsoleAppender(Level threshold, String pattern)
    {
        ConsoleAppender console = new ConsoleAppender(); // create appender

        console.setLayout(new PatternLayout(pattern));
        console.setThreshold(threshold);
        console.activateOptions();

        return console;
    }


    public static Appender createDailyRollingFileAppender(Level threshold, String pattern, String filePattern)
    {
        DailyRollingFileAppender dailyRollingFileAppender = new DailyRollingFileAppender();
        dailyRollingFileAppender.setLayout(new PatternLayout(pattern));
        dailyRollingFileAppender.setThreshold(threshold);
        dailyRollingFileAppender.setFile(userDataDirectory.getUserDataDirectory() + "/log/" + name + ".log");
        dailyRollingFileAppender.setDatePattern("'.'" + filePattern); // "'.'yyyy-MM-dd"
        dailyRollingFileAppender.activateOptions();

        return dailyRollingFileAppender;
    }


    public static Appender createDailyZippedRollingFileAppender(Level threshold, String pattern, String filePattern)
    {
        RollingFileAppender rollingFileAppender = new RollingFileAppender();
        rollingFileAppender.setLayout(new PatternLayout(pattern));
        rollingFileAppender.setThreshold(threshold);
        rollingFileAppender.setFile(userDataDirectory.getUserDataDirectory() + "/log/" + name + ".log");
        // dailyRollingFileAppender.setDatePattern("'.'yyyy-MM-dd");

        TimeBasedRollingPolicy timeBasedRollingPolicy = new TimeBasedRollingPolicy();
        timeBasedRollingPolicy.setFileNamePattern(userDataDirectory.getUserDataDirectory() + "/log/" + name + ".%d{"
                + filePattern + "}.zip");
        rollingFileAppender.setRollingPolicy(timeBasedRollingPolicy);

        rollingFileAppender.activateOptions();

        return rollingFileAppender;

    }

}
