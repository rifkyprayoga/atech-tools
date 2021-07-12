package com.atech.misc.browser;

import java.awt.*;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.update.startup.os.OSType;

/**
 * Created by andy on 09/03/18.
 */
public class BrowserStart {

    private static final Logger LOG = LoggerFactory.getLogger(BrowserStart.class);

    public static void startBrowser(String url)
    {
        if ((Desktop.isDesktopSupported()) && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
        {
            LOG.debug("Desktop API Supported.");
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
                
            } catch (Exception  ex) {
                LOG.error("Error opening browser via DesktopAPI: {}", ex.getMessage(), ex);
            }
        }
        else
        {
            //String os = System.getProperty("os.name").toLowerCase();
            OSType osType = OSType.getOSByType();

            String execString = "";

            if (osType==OSType.Windows)
            {
                execString = "rundll32 url.dll,FileProtocolHandler " + url;
            }
            else if (osType==OSType.Mac)
            {
                execString = "open " + url;
            }
            else
            {
                execString = "xdg-open " + url;
            }

            LOG.debug("Desktop API Not Supported. Trying with '{}'", execString);


            executeWithRuntime(execString);
        }
    }


    public static void startBrowser(String command, String url)
    {
        executeWithRuntime(command + " " + url);
    }

    public static void executeWithRuntime(String commandLine)
    {
        Runtime runtime = Runtime.getRuntime();

        try
        {
            runtime.exec(commandLine);
        } catch (Exception ex) {
            LOG.error("Error executing runtime for command: {}", ex.getMessage(), ex );
        }
    }

} 