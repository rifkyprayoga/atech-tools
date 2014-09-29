package com.atech.graphics;

public class GuiActionUtil {


	public static GuiAction checkLastActionTime(GuiAction lastAction)
	{
		long curr = System.currentTimeMillis();
		long timeIn = lastAction.getLastAction()+2000;

		//System.out.println("checkLastActionTime: " + lastAction + ", curr=" + curr + ", timeIn=" + timeIn);

		if ((timeIn!=2000L) && (curr < timeIn))
		{
			lastAction.setActionSuccess(false);
			//System.out.println("false");
			return lastAction;
		}

		lastAction.setActionSuccess(true);
		lastAction.setLastAction(curr);
		//System.out.println("true");
		return lastAction;
	}



}
