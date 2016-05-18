package com.atech.graphics.components;

import java.awt.*;

/**
 * Created by andy on 17.02.16.
 */
public interface DialogSizePersistInterface
{

    String getSettingKey();


    Dimension getMinimalSize();


    Dimension getDefaultSize();


    Window getContainer();

}
