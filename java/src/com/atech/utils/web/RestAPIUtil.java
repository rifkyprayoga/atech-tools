package com.atech.utils.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by andy on 12/03/18.
 */
public class RestAPIUtil
{

    public static Gson gson = new Gson();

    public Gson getGson() {
        return gson;
    }

    public <E extends Object> E doGetCallJson(String urlString, //
                                                 RestAuthorization restAuthorization, //
                                                 Map<String,String> authorizationParameters, //
                                                 Class<E> clazz, Class<E> clazzCollection)
    {
        try
        {
            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (restAuthorization!=RestAuthorization.None) {
                conn.setRequestProperty("Authorization", "Basic " + "");
            }

            if (conn.getResponseCode() != 200)
            {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String fullData = "";
            StringBuilder stringBuilder = new StringBuilder();

            String output;
            // System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null)
            {
                stringBuilder.append(output);
            }

            conn.disconnect();

            //System.out.println("Response: " + stringBuilder.toString());

            if (clazzCollection!=null)
            {
                //E[] bean = gson.fromJson(stringBuilder.toString() ,E[].class);
                return null;
            }
            else {
                return gson.fromJson(stringBuilder.toString(), clazz);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }



    public String doGetCallJsonString(String urlString, //
                                              RestAuthorization restAuthorization, //
                                              Map<String,String> authorizationParameters //
                                              )
    {
        try
        {
            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (restAuthorization!=RestAuthorization.None) {
                conn.setRequestProperty("Authorization", "Basic " + "");
            }

            if (conn.getResponseCode() != 200)
            {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String fullData = "";
            StringBuilder stringBuilder = new StringBuilder();

            String output;
            // System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null)
            {
                stringBuilder.append(output);
            }

            conn.disconnect();

            System.out.println("Response: " + stringBuilder.toString());

            return stringBuilder.toString();

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }



    public <E extends Object> List<E> doGetCallJsonList(String url, RestAuthorization restAuthorization, Map<String,String> authorizationParameters, Class<E[]> clazz) {

        String data = doGetCallJsonString(url, restAuthorization, authorizationParameters);

        //E[] bean = gson.fromJson(data ,E[].class);

        Type listType = new TypeToken<ArrayList<E>>(){}.getType();
        //List<E> yourClassList = new Gson().fromJson(data, listType);


        final E[] jsonToObject = new Gson().fromJson(data, clazz);

        return Arrays.asList(jsonToObject);


        //return gson.fromJson(data, listType);
    }



    public static void main(String[] args) throws IOException
    {
        URL url = new URL("INSERT YOUR SERVER REQUEST");
        // Insert your JSON query request
        String query = "{'PARAM1': 'VALUE','PARAM2': 'VALUE','PARAM3': 'VALUE','PARAM4': 'VALUE'}";
        // It change the apostrophe char to double colon char, to form a correct JSON string
        query = query.replace("'", "\"");

        try
        {
            // make connection
            URLConnection urlc = url.openConnection();
            // It Content Type is so importan to support JSON call
            urlc.setRequestProperty("Content-Type", "application/xml");
            Msj("Conectando: " + url.toString());
            // use post mode
            urlc.setDoOutput(true);
            urlc.setAllowUserInteraction(false);

            // send query
            PrintStream ps = new PrintStream(urlc.getOutputStream());
            ps.print(query);
            Msj("Consulta: " + query);
            ps.close();

            // get result
            BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            String l = null;
            while ((l = br.readLine()) != null)
            {
                Msj(l);
            }
            br.close();
        }
        catch (Exception e)
        {
            Msj("Error ocurrido");
            Msj(e.toString());
        }
    }


    private static void Msj(String texto)
    {
        System.out.println(texto);
    }


}
