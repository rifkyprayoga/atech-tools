package com.atech.update.v3.test;

import java.io.StringWriter;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.atech.upgrade.server.db.DataLayerUpdateServletV3;
import com.atech.upgrade.server.xml.UpdateServerResponse;

/**
 * Created by andy on 13.03.15.
 */
public class TestServletWithout
{

    public static void serializeAll()
    {
        UpdateServerResponse usr = new UpdateServerResponse();

        usr.setApplicationName("ggc");

        String result = serializeUpdateServerResponse(usr);

        System.out.println("result: " + result);

    }


    public static String serializeUpdateServerResponse(UpdateServerResponse response)
    {

        try
        {
            Serializer serializer = new Persister();
            // Example example = new Example("Example message", 123);
            // File result = new File("example.xml");

            StringWriter stringWriter = new StringWriter();

            serializer.write(response, stringWriter);

            return stringWriter.toString();
        }
        catch (Exception ex)
        {
            System.out.println("Ex: " + ex);
            ex.printStackTrace();

            // LOG.error("serialize: " + ex, ex);

            return null;
        }
    }


    public static void main(String args[])
    {
        // serializeAll();
        database();
    }


    private static void database()
    {
        DataLayerUpdateServletV3 dl = DataLayerUpdateServletV3.getInstance();

        // System.out.println("Is app present: " +
        // dl.isApplicationPresent("ggc"));
        System.out.println("Is version present: " + dl.isVersionPresent("ggc", 20));
    }

}
