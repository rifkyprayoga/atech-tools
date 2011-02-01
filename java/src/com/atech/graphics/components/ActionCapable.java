package com.atech.graphics.components;

public interface ActionCapable
{

    
    public void receiveAction(int action_code);
    
    public void receiveData(Object data);
    
    public int[] getActionPossible();
    
    
    
}
