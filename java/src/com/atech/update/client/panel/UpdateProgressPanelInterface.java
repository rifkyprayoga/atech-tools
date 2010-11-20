package com.atech.update.client.panel;

public interface UpdateProgressPanelInterface 
{

    public abstract void setBaseStatus(String status);
    
    public abstract void setStatus(String status);
    
    public abstract void setProgress(int proc);

    public abstract void setJobStatus(String status);
    
}
