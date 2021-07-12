package com.atech.notification.mail;

import java.awt.*;
import java.io.IOException;

import com.atech.notification.NotificationHandlerInterface;
import com.atech.notification.dto.NotificationDto;
import com.atech.notification.dto.NotificationResultDto;

/**
 * Created by andy on 17/06/16.
 */
public class EmailNotificationHandler implements NotificationHandlerInterface
{

    public NotificationResultDto sendNotification(NotificationDto notificationDto)
    {
        return null;
    }


    public NotificationResultDto sendNotificationViaNativeMailApp(NotificationDto notificationDto)
    {
        if (Desktop.isDesktopSupported())
        {
            Desktop d = Desktop.getDesktop();

            try
            {
                d.mail();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        else
        {
            System.out.println("Mail sending not supported, displaying the message.");

        }

        return null;
    }


    private void showNotification(NotificationDto notificationDto)
    {
        System.out.println("showNotification N/A:\n " + notificationDto);
    }

}
