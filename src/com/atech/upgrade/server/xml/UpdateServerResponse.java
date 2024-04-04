package com.atech.upgrade.server.xml;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.atech.upgrade.server.defs.ErrorResponseDef;
import com.atech.upgrade.server.defs.UpdateAction;
import com.atech.upgrade.server.dto.ApplicationNoteDTO;
import com.atech.upgrade.server.dto.ApplicationUpdateDTO;

/**
 * Created by andy on 13.03.15.
 */
@Root
public class UpdateServerResponse
{

    @Element(required = false)
    public String applicationName;

    @Element(required = false)
    public int version;

    @Element(required = false)
    public String action;

    @Element(required = false)
    public SuccessResponse successResponse;

    @Element(required = false)
    public ErrorResponse errorResponse;

    @Element(required = false)
    public int notesCount;

    @ElementList(required = false)
    public List<ApplicationNoteDTO> notes;


    public void setErrorResponse(ErrorResponseDef def)
    {
        this.errorResponse = new ErrorResponse(def);
    }


    public void setApplicationName(String applicationName)
    {
        this.applicationName = applicationName;
    }


    public void setVersion(int version)
    {
        this.version = version;
    }


    public void setAction(UpdateAction action)
    {
        this.action = action.getUrlAction();
    }


    public void createSuccessResponse(boolean upgradable, ApplicationUpdateDTO dto)
    {
        SuccessResponse sr = new SuccessResponse();
        sr.foundNewVersion = upgradable;

        if (upgradable)
        {
            sr.applicationUpdate = dto;
        }

        this.successResponse = sr;
    }


    public boolean isFoundNewVersion()
    {
        return this.successResponse.foundNewVersion;
    }


    public ApplicationUpdateDTO getApplicationUpdate()
    {
        return this.successResponse.applicationUpdate;
    }

}
