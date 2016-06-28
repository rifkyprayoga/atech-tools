package com.atech.notification;

import com.atech.notification.dto.NotificationDto;
import com.atech.notification.dto.NotificationResultDto;

/**
 * Created by andy on 17/06/16.
 */
public interface NotificationHandlerInterface
{

    NotificationResultDto sendNotification(NotificationDto notificationDto);

}
