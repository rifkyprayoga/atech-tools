package com.atech.graphics;

public class GuiAction {

	private long lastAction = 0;
	private boolean actionSuccess = false;

	public GuiAction() {
		this.lastAction = 0;
		this.actionSuccess = false;
	}

	public long getLastAction() {
		return lastAction;
	}

	public void setLastAction(long lastAction) {
		this.lastAction = lastAction;
	}

	public boolean isActionSuccess() {
		return actionSuccess;
	}

	public void setActionSuccess(boolean actionSuccess) {
		this.actionSuccess = actionSuccess;
	}

	@Override
	public String toString()
	{
		return "GuiAction [lastAction=" + this.lastAction + ", success=" + this.actionSuccess + "]";
	}



}
