package com.atech.update.v3.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by andy on 13.03.15.
 */
@Root
public class ApplicationUpdateDTO
{

    @Element(required = false)
    private long dtInfo;

    @Element
    public int versionNumber;

    @Element
    public String versionName;

    @Element(required = false)
    public String filename;

    @Element(required = false)
    public long length;

    @Element(required = false)
    public String crc;

    @Element(required = false)
    public long databaseVersion;

    public String xmlData;


    public ApplicationUpdateDTO()
    {
    }


    public ApplicationUpdateDTO(ResultSet rs) throws SQLException
    {
        this.versionName = rs.getString("version_name");
        this.filename = rs.getString("update_filename");
        this.versionNumber = rs.getInt("version_number");
        this.length = rs.getLong("update_length");
        this.crc = rs.getString("update_crc");
        this.xmlData = rs.getString("xml_update_data");
        this.dtInfo = rs.getLong("dt_info");
        this.databaseVersion = rs.getInt("db_version");

    }


    public static String getColumns()
    {
        return " id, application_name, version_number, version_name, update_filename, "
                + "       update_length, update_crc, xml_update_data, db_version, dt_info ";
    }

}

//
// CREATE TABLE application_updates
// (
// id bigint NOT NULL DEFAULT nextval('application_update_id_seq'::regclass),
// applicationName character varying(50),
// versionNumber integer,
// version_name character varying(255),
// update_filename character varying(255),
// update_length bigint,
// update_crc character varying(255),
// xml_update_data text,
// CONSTRAINT application_update_pkey PRIMARY KEY (id)
// )
