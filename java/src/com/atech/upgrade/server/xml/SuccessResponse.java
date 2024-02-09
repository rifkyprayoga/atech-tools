package com.atech.upgrade.server.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.atech.upgrade.server.dto.ApplicationUpdateDTO;

/**
 * Created by andy on 14.03.15.
 */
@Root
public class SuccessResponse
{

    @Element
    public boolean foundNewVersion;

    @Element(required = false)
    public ApplicationUpdateDTO applicationUpdate;


    public void setFoundNewVersion(boolean foundNewVersion)
    {
        this.foundNewVersion = foundNewVersion;
    }


    public ApplicationUpdateDTO getApplicationUpdate()
    {
        return applicationUpdate;
    }


    public void setApplicationUpdate(ApplicationUpdateDTO applicationUpdate)
    {
        this.applicationUpdate = applicationUpdate;
    }
}
