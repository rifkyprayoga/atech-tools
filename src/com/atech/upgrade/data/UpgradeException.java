package com.atech.upgrade.data;

/**
 * Created by andy on 26.11.15.
 */
public class UpgradeException extends Exception
{

    public UpgradeException(String s)
    {
        super(s);
    }


    public UpgradeException(String s, Exception ex)
    {
        super(s, ex);
    }
}
