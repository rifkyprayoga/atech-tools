package com.atech.graphics.dialogs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import org.apache.commons.lang.StringUtils;

import com.atech.graphics.components.JDecimalTextField;
import com.atech.help.HelpCapable;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;

/**
 * Created by andy on 28.02.15.
 */
public abstract class StandardDialogForObject extends JDialog implements HelpCapable, ActionListener
{

    private static final long serialVersionUID = -2770020069853438147L;

    protected I18nControlAbstract i18nControl = null;
    protected ATDataAccessAbstract dataAccess = null;
    protected boolean wasSuccessful = false;
    protected JDialog parent;
    protected int positionXForEntryLabel = 40;
    protected JPanel mainPanel;
    protected JButton btnHelp;
    protected boolean editValue;


    public StandardDialogForObject(JFrame dialog, ATDataAccessAbstract dataAccess, boolean init)
    {
        super(dialog, "", true);
        // parent = dialog;
        this.dataAccess = dataAccess;
        this.i18nControl = dataAccess.getI18nControlInstance();
        dataAccess.addComponent(this);

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
        dataAccess.addComponent(this);

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
        dataAccess.addComponent(this);

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
        dataAccess.addComponent(this);

        if (init)
        {
            init(dataObject);
        }
    }


    protected void init()
    {
        initGUI();
        if (StringUtils.isNotBlank(this.getHelpId()))
        {
            dataAccess.enableHelp(this);
        }
        this.setVisible(true);
    }


    protected void init(Object dataObject)
    {
        initGUI();
        loadData(dataObject);

        if (StringUtils.isNotBlank(this.getHelpId()))
        {
            dataAccess.enableHelp(this);
        }

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
                // System.out.println("was success");
                this.wasSuccessful = true;
                dispose();
            }
            else
            {
                // System.out.println("not successful");
            }
        }
        else if (actionCommand.equals("cancel"))
        {
            dispose();
        }
        else
        {
            customActionPerformed(actionCommand);
        }
    }


    @Override
    public void dispose()
    {
        this.dataAccess.removeComponent(this);
        super.dispose();
    }


    public void customActionPerformed(String actionCommand)
    {
        System.out.println("Action (" + actionCommand + ") is not supported by " + this.getClass().getSimpleName()
                + ". If you wish to change this you need to override customActionPerformed method.");
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


    public boolean checkIfJDecimalTextFieldIsGreaterThanZero(JDecimalTextField decimalTextField, String fieldName,
            List<String> listFailed)
    {
        int tempInt = ATSwingUtils.getJDecimalTextValueInt(decimalTextField);

        if (tempInt <= 0)
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

        for (String failed : listFailed)
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

        String s = sb.toString(); // .substring(0, sb.length()-2);

        ATSwingUtils.showErrorDialog(this,
            String.format(i18nControl.getMessage("ERROR_WHEN_SAVING_OBJECT"), "Stock Sub Type", s), i18nControl);

        // System.out.println("List Failed: " + listFailed.size() + ", " + s);

    }


    protected void createEntryLabel(String message, int positionY)
    {
        ATSwingUtils.getLabel(i18nControl.getMessage(message) + ":", positionXForEntryLabel, positionY, 120, 25,
            mainPanel, ATSwingUtils.FONT_NORMAL_BOLD);
    }


    protected String getText(JTextField tf)
    {
        if (tf.getText().length() == 0)
            return null;
        else
            return tf.getText();
    }


    protected String getText(JTextArea tf)
    {
        if (tf.getText().length() == 0)
            return null;
        else
            return tf.getText();
    }


    protected void setText(JTextField tf, String text)
    {
        if (StringUtils.isNotBlank(text))
        {
            tf.setText(text);
        }
    }


    protected void setText(JTextArea tf, String text)
    {
        if (StringUtils.isNotBlank(text))
        {
            tf.setText(text);
        }
    }


    public abstract String getHelpId();


    public boolean wasOperationSuccessful()
    {
        return this.wasSuccessful;
    }


    public JButton getHelpButton()
    {
        return this.btnHelp;
    }


    public Component getComponent()
    {
        return this;
    }

}
