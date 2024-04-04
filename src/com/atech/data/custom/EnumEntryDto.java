package com.atech.data.custom;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Created by andy on 13/01/17.
 */
public class EnumEntryDto
{

    private int code;
    private String name;
    private Integer version;
    private String format;
    private String fields;
    // Object response;
    private String description;

    public Map<String, FieldDto> additionalFields = null;


    public EnumEntryDto()
    {
        this((Map<String, FieldDto>) null);
    }


    public EnumEntryDto(Map<String, FieldDto> additionalFieldsInput)
    {
        if (additionalFieldsInput != null)
        {
            additionalFields = new HashMap<String, FieldDto>();

            for (Map.Entry<String, FieldDto> entry : additionalFieldsInput.entrySet())
            {
                additionalFields.put(entry.getKey(), entry.getValue().clone());
            }
        }
    }


    public EnumEntryDto(EnumEntryDto dto)
    {
        this(dto.getAdditionalFields());
    }


    public String generateFields()
    {
        StringBuffer sb = new StringBuffer();

        sb.append("    int code;\n");
        sb.append("    String description;\n");
        sb.append("    Integer version;\n");
        sb.append("    String format;\n");
        sb.append("    String fields;\n");

        if (MapUtils.isNotEmpty(additionalFields))
        {
            for (Map.Entry<String, FieldDto> entry : additionalFields.entrySet())
            {
                sb.append("    " + entry.getValue().type + " " + entry.getKey() + ";\n");
            }
        }

        sb.append("\n");

        return sb.toString();
    }


    public String generateConstructor(String enumName)
    {
        StringBuffer sb = new StringBuffer();

        sb.append("    " + enumName + "(int code, String description, Integer version, //\n");
        sb.append("                  String format, //\n");
        sb.append("                  String fields, //\n");

        if (MapUtils.isNotEmpty(additionalFields))
        {
            for (Map.Entry<String, FieldDto> entry : additionalFields.entrySet())
            {
                sb.append("                  " + entry.getValue().type + " " + entry.getKey() + ", //\n");
            }
        }

        sb.setLength(sb.length() - 5);
        sb.append(")\n");
        sb.append("    {\n");

        sb.append("        this.code = code;\n");
        sb.append("        this.description = description;\n");
        sb.append("        this.version = version;\n");
        sb.append("        this.format = format;\n");
        sb.append("        this.fields = fields;\n");

        if (MapUtils.isNotEmpty(additionalFields))
        {
            for (Map.Entry<String, FieldDto> entry : additionalFields.entrySet())
            {
                sb.append("        this." + entry.getKey() + " = " + entry.getKey() + ";\n");
            }
        }

        sb.append("    }\n");
        return sb.toString();
    }


    public String generateMethods()
    {
        StringBuffer sb = new StringBuffer();

        sb.append(generateMethod("int", "code"));
        sb.append(generateMethod("String", "description"));
        sb.append(generateMethod("int", "version"));
        sb.append(generateMethod("String", "format"));
        sb.append(generateMethod("String", "fields"));

        if (MapUtils.isNotEmpty(additionalFields))
        {
            for (Map.Entry<String, FieldDto> entry : additionalFields.entrySet())
            {
                sb.append(generateMethod(entry.getValue().type, entry.getKey()));
            }
        }

        return sb.toString();
    }


    private String generateMethod(String type, String variable)
    {
        return "    public " + type + " get" + StringUtils.capitalize(variable) + //
                "()\n    {\n" + "        return this." + variable + ";\n" + "    }\n\n";
    }


    public Map<String, FieldDto> getAdditionalFields()
    {
        return additionalFields;
    }


    public String generateEntry()
    {
        StringBuffer sb = new StringBuffer();

        String prefix = StringUtils.repeat(" ", 8);

        sb.append(StringUtils.repeat(" ", 4) + name + "(" + code + ", " + description + ", " + version);
        sb.append(", //\n");

        sb.append(prefix);

        if (format != null)
            sb.append("\"" + format + "\", ");
        else
            sb.append(", " + format + ", ");
        sb.append("// \n");

        sb.append(prefix);

        if (fields != null)
            sb.append("\"" + fields + "\"");
        else
            sb.append(fields);

        if (MapUtils.isNotEmpty(additionalFields))
        {
            for (Map.Entry<String, FieldDto> entry : additionalFields.entrySet())
            {
                sb.append(", // \n");
                sb.append(prefix);
                sb.append(encapsulateByType(entry.getValue().type, entry.getValue().value));
            }
        }
        else
        {
            sb.append(" // \n");
        }

        sb.append("    )");

        return sb.toString();
    }


    public String encapsulateByType(String type, String value)
    {
        if (type.equals("String"))
        {
            return "\"" + value + "\"";
        }
        else
        {
            return value;
        }
    }


    public int getCode()
    {
        return code;
    }


    public void setCode(int code)
    {
        this.code = code;
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public Integer getVersion()
    {
        return version;
    }


    public void setVersion(Integer version)
    {
        this.version = version;
    }


    public String getFormat()
    {
        return format;
    }


    public void setFormat(String format)
    {
        this.format = format;
    }


    public String getFields()
    {
        return fields;
    }


    public void setFields(String fields)
    {
        this.fields = fields;
    }


    public String getDescription()
    {
        return description;
    }


    public void setDescription(String description)
    {
        this.description = description;
    }


    public void setAdditionalField(String key, String value)
    {
        this.additionalFields.get(key).value = value;
    }
}
