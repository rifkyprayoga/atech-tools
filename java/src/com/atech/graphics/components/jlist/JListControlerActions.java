package com.atech.graphics.components.jlist;

import javax.swing.*;

/**
 * Created by andy on 15.02.16.
 */
public interface JListControlerActions
{

    /**
     * This is custom action for adding element. If element was added, it should be put into listData,
     * which will be then re-rendered.
     */
    Object executeItemAddAction();


    /**
     * This is custom action for editing element.
     */
    void executeItemEditAction(Object item);


    /**
     * This is custom action for deleting element. If we need we can also execute special action, but if element
     * is not in db, we don't need to implementat this.
     */
    void executeItemDeleteAction(Object item);


    /**
     * get Parent Dialog
     */
    JDialog getParentDialog();


    /**
     * set Parent Dialog
     */
    void setParentDialog(JDialog dialog);

}
