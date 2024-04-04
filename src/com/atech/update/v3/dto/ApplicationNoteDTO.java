package com.atech.update.v3.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by andy on 13.03.15.
 */
@Root
public class ApplicationNoteDTO
{

    @Element
    public String applicationName;

    @Element
    public Integer versionNumber; // integer,

    @Element(required = false)
    public String author; // character varying(100),

    @Element(required = false)
    public Long dtInfo; // bigint,

    @Element(required = false)
    public String subject; // character varying(100),

    @Element(required = false)
    public String note; // character varying(10000),

    @Element
    public boolean active; // boolean,


    public ApplicationNoteDTO()
    {
    }


    public ApplicationNoteDTO(ResultSet rs) throws SQLException
    {
        this.applicationName = rs.getString("application_name");
        this.versionNumber = rs.getInt("version_number");
        this.author = rs.getString("author");
        this.dtInfo = rs.getLong("dt_info");
        this.subject = rs.getString("subject");
        this.note = rs.getString("note");
        this.active = rs.getBoolean("active");
    }

}
