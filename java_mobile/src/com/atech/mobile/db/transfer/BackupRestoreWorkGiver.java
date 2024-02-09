package com.atech.mobile.db.transfer;



// this one should be extended, we have several variables that need to be filled

public interface BackupRestoreWorkGiver //extends BackupRestoreBase 
{

    
	//public String getTargetName();
    
    public void setStatus(int procents);
	
	public void setTask(String task_name);
    
    
	// old
	//public String getObjectClassName();
	
	//public String getObjectHeader();
	
	//public String getObjectValues();
	
}
