package com.atech.upgrade.server.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.atech.upgrade.server.defs.ErrorResponseDef;

/**
 * Created by andy on 14.03.15.
 */
@Root
public class ErrorResponse
{

    @Element
    public int errorCode;

    @Element
    public String errorDescription;

    @Element
    public String errorDescriptionKey;


    public ErrorResponse(ErrorResponseDef def)
    {
        this.errorCode = def.getCommandCode();
        this.errorDescription = def.getCommandDescription();
        this.errorDescriptionKey = def.getCommandDescriptionKey();
    }

}
