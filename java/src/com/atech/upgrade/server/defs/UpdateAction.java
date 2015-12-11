package com.atech.upgrade.server.defs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andy on 14.03.15.
 */
public enum UpdateAction
{
    None(""), //
    GetInfo("get_info"), //
    GetXmlData("get_xml_data"), //
    GetNotes("get_notes"), //
    GetUpgradeFile("get_upgrade_file"), //
    CheckIfUpgradeAvailable("upgrade_available"), //

    ;

    String urlAction;
    static Map<String, UpdateAction> actionUrlMapping = new HashMap<String, UpdateAction>();

    static
    {
        for (UpdateAction pbt : values())
        {
            actionUrlMapping.put(pbt.urlAction, pbt);
        }
    }


    UpdateAction(String urlAction)
    {
        this.urlAction = urlAction;
    }


    public String getUrlAction()
    {
        return this.urlAction;
    }


    public static UpdateAction getUrlAction(String action)
    {
        if (actionUrlMapping.containsKey(action))
        {
            return actionUrlMapping.get(action);
        }
        else
        {
            return UpdateAction.None;
        }
    }
}
