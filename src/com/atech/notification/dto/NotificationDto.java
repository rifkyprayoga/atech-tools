package com.atech.notification.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by andy on 17/06/16.
 */
public class NotificationDto
{

    String to;
    String from;
    String subject;
    String body;


    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("to", to).append("from", from).append("subject", subject)
                .append("body", body).toString();
    }
}
