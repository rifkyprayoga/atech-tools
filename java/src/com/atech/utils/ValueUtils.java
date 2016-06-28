package com.atech.utils;

import org.apache.commons.lang.StringUtils;

import com.atech.utils.data.ExceptionHandling;

/**
 * Created by andy on 30/05/16.
 */
public class ValueUtils
{

    /**
     * Gets the int value from string.
     *
     * @param aValue the a value
     *
     * @return the int value from string
     */
    public static int getIntValueFromString(String aValue)
    {
        return getIntValueFromString(aValue, 0, null);
    }


    /**
     * Gets the int value from string.
     *
     * @param aValue the a value
     *
     * @return the int value from string
     */
    public static int getIntValueFromStringWithoutException(String aValue, int defaultValue)
    {
        return getIntValueFromString(aValue, defaultValue, ExceptionHandling.CATCH_EXCEPTION);
    }


    //
    // Graph Config Properties

    /**
     * Gets the int value from string.
     *
     * @param aValue the a value
     *
     * @return the int value from string
     */
    public static int getIntValueFromString(String aValue, int defaultValue)
    {
        try
        {
            return getIntValueFromStringWithException(aValue, defaultValue, null);
        }
        catch (Exception ex)
        {
            return 0;
        }
    }


    /**
     * Gets the int value from string.
     *
     * @param aValue the a value
     *
     * @return the int value from string
     */
    public static int getIntValueFromString(String aValue, int defaultValue,
            ExceptionHandling overridenExceptionHandling)
    {
        try
        {
            return getIntValueFromStringWithException(aValue, defaultValue, overridenExceptionHandling);
        }
        catch (Exception ex)
        {
            return 0;
        }
    }


    /**
     * Gets the int value from string, can also throw Exception
     *
     * @param aValue the a value
     * @param def_value the def_value
     *
     * @return the int value from string
     */
    public static int getIntValueFromStringWithException(String aValue, int def_value,
            ExceptionHandling overridenExceptionHandling) throws Exception
    {
        int out = def_value;

        try
        {
            if (StringUtils.isBlank(aValue))
                return def_value;

            // parse float, doesn't look in locale for decimal sign
            aValue = aValue.replace(",", ".");

            out = Integer.parseInt(aValue);
        }
        catch (Exception ex)
        {
            // processException(ex, "Error on parsing string to get int [" +
            // aValue + "]:");
        }

        return out;
    }

    // public static void processException(Exception ex, String errorString)
    // throws Exception
    // {
    // processException(ex, errorString, null);
    // }

    // public static void processException(Exception ex, String errorString,
    // ExceptionHandling overrideExceptionHandling)
    // throws Exception
    // {
    // ExceptionHandling exceptionHandling = overrideExceptionHandling == null ?
    // numberParsingExceptionHandling
    // : overrideExceptionHandling;
    //
    // if (exceptionHandling == ExceptionHandling.THROW_EXCEPTION)
    // {
    // throw ex;
    // }
    // else if (exceptionHandling ==
    // ExceptionHandling.CATCH_EXCEPTION_WITH_STACK_TRACE)
    // {
    // LOG.error(errorString + ex, ex);
    // }
    // else
    // {
    // LOG.error(errorString + ex);
    // }
    //
    // }

}
