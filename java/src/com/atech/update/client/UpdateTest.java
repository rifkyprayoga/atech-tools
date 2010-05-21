package com.atech.update.client;

import com.atech.update.config.UpdateConfiguration;
import com.atech.update.config.UpdateConfigurationXml;

public class UpdateTest
{

    public static void main(String args[])
    {
        
        UpdateConfiguration uc = new UpdateConfiguration();
        
        UpdateConfigurationXml ucx = new UpdateConfigurationXml();
        
        ucx.writeXml(uc);
        
        
    }
    
    
    
    
}
