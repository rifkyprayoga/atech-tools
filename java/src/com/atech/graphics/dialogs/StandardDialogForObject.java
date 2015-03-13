package com.atech.graphics.dialogs;

import com.atech.graphics.components.JDecimalTextField;
import com.atech.help.HelpCapable;
import com.atech.i18n.I18nControlAbstract;
import com.atech.print.engine.PrintProcessor;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by andy on 28.02.15.
 */
public abstract class StandardDialogForObject extends JDialog implements HelpCapable, ActionListener
{

    protected I18nControlAbstract i18nControl = null;
    protected ATDataAccessAbstract dataAccess = null;
    protected boolean wasSuccessful = false;
    protected JDialog parent;


    public StandardDialogForObject(JDialog dialog)
    {
        this(dialog, true);
    }

    public StandardDialogForObject(JDialog dialog, boolean init)
    {
        super(dialog, "", true);
        parent = dialog;

        if (init)
        {
            init();
        }
    }

    public StandardDialogForObject(JDialog dialog, ATDataAccessAbstract dataAccess)
    {
        this(dialog, dataAccess, true);
    }


    public StandardDialogForObject(JDialog dialog, ATDataAccessAbstract dataAccess, boolean init)
    {
        super(dialog, "", true);
        parent = dialog;
        this.dataAccess = dataAccess;
        this.i18nControl = dataAccess.getI18nControlInstance();

        if (init)
        {
            init();
        }
    }

    public StandardDialogForObject(JDialog dialog, Object dataObject)
    {
        this(dialog, dataObject, true);
    }


    public StandardDialogForObject(JDialog dialog, Object dataObject, boolean init)
    {
        super(dialog, "", true);
        parent = dialog;

        if (init)
        {
            init(dataObject);
        }
    }

    public StandardDialogForObject(JDialog dialog, ATDataAccessAbstract dataAccess, Object dataObject)
    {
        this(dialog, dataAccess, dataObject, true);
    }

    public StandardDialogForObject(JDialog dialog, ATDataAccessAbstract dataAccess, Object dataObject, boolean init)
    {
        super(dialog);
        parent = dialog;
        this.dataAccess = dataAccess;
        this.i18nControl = dataAccess.getI18nControlInstance();

        if (init)
        {
            init(dataObject);
        }
    }


    protected void init()
    {
        initGUI();
        this.setVisible(true);
    }

    protected void init(Object dataObject)
    {
        initGUI();
        loadData(dataObject);
        this.setVisible(true);
    }

    public abstract void loadData(Object dataObject);


    public abstract boolean saveData();


    public abstract void initGUI();


    /**
     * Was Action Successful (ok clicked). If you have more extended actions than just saving object you need
     * to override this method.
     *
     * @return true if action was successful (dialog closed with OK)
     */
    public boolean actionSuccessful()
    {
        return (saveData());
    }


    public void actionPerformed(ActionEvent ae)
    {
        String actionCommand = ae.getActionCommand();
        if (actionCommand.equals("ok"))
        {
            if (actionSuccessful())
            {
                System.out.println("was success");
                this.wasSuccessful = true;
                this.dispose();
            }
            else
            {
                System.out.println("not successful");
            }
        }
        else if (actionCommand.equals("cancel"))
        {
            this.dispose();
        }
        else
        {
            customActionPerformed(actionCommand);
        }
    }


    public void customActionPerformed(String actionCommand)
    {
        System.out.println("Action (" + actionCommand + ") is not supported by " + this.getClass().getSimpleName() +
                ". If you wish to change this you need to override customActionPerformed method.");
    }


    public boolean checkIfTextFieldSet(JTextField textField, String fieldName, List<String> listFailed)
    {
        if (StringUtils.isBlank(textField.getText()))
        {
            listFailed.add(fieldName);
            return false;
        }
        else
        {
            return true;
        }
    }


    public boolean checkIfJDecimalTextFieldIsGreaterThanZero(JDecimalTextField decimalTextField, String fieldName, List<String> listFailed)
    {
        int tempInt = ATSwingUtils.getJDecimalTextValueInt(decimalTextField);

        if (tempInt<=0)
        {
            listFailed.add(fieldName);
            return false;
        }
        else
        {
            return true;
        }
    }


    public void displayErrorWhenSavingObject(List<String> listFailed)
    {
        StringBuilder sb = new StringBuilder();

        boolean first = true;

        for(String failed : listFailed)
        {
            if (first)
            {
                first = false;
            }
            else
            {
                sb.append(",\n");
            }

            sb.append("               " + i18nControl.getMessage(failed));

        }

        String s = sb.toString(); //.substring(0, sb.length()-2);

        ATSwingUtils.showErrorDialog(this,
                String.format(i18nControl.getMessage("ERROR_WHEN_SAVING_OBJECT"), "Stock Sub Type", s), i18nControl
        );

//        System.out.println("List Failed: " + listFailed.size() + ", " + s);

    }


    public abstract String getHelpId();


    public abstract JButton getHelpButton();


    public abstract Component getComponent();


    public boolean wasOperationSuccessful()
    {
        return this.wasSuccessful;
    }

}

