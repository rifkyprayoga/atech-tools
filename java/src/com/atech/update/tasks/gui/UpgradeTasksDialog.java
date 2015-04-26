package com.atech.update.tasks.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.atech.i18n.I18nControlAbstract;
import com.atech.update.tasks.gui.components.UTCCopyFiles;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;

/**
 * Created by andy on 23.03.15.
 */
public class UpgradeTasksDialog extends JDialog implements ActionListener
{

    protected I18nControlAbstract i18nControl = null;
    protected ATDataAccessAbstract dataAccess = null;
    protected boolean wasSuccessful = false;
    protected JDialog parent;

    private JPanel panel;
    private JButton helpButton;


    public UpgradeTasksDialog(JDialog dialog, ATDataAccessAbstract dataAccess)
    {

        super(dialog, "", true);
        parent = dialog;
        this.dataAccess = dataAccess;
        this.i18nControl = dataAccess.getI18nControlInstance();

        init();
    }


    private void init()
    {

    }


    private void initGUI()
    {
        ATSwingUtils.initLibrary();

        getContentPane().setLayout(null);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 500, 400);

        this.setBounds(0, 0, 500, 400);

        this.getContentPane().add(panel, null);

        ATSwingUtils.centerJDialog(this, this.parent);

        // setSize(480, 400);

        ATSwingUtils.getTitleLabel(i18nControl.getMessage("UPGRADE_TASKS"), 0, 22, 480, 40, panel,
            ATSwingUtils.FONT_BIG_BOLD);
        this.setTitle(i18nControl.getMessage("UPGRADE_TASKS"));

        JScrollPane pane = new JScrollPane();

        // TODO

        pane.add(new UTCCopyFiles(null), null);

        // buttons
        ATSwingUtils.getButton("   " + i18nControl.getMessage("CLOSE"), 50, 320, 125, 25, panel,
            ATSwingUtils.FONT_NORMAL, "ok.png", "close", this, dataAccess);

        // ATSwingUtils.getButton("   " + i18nControl.getMessage("CANCEL"), 180,
        // 320, 125, 25, panel,
        // ATSwingUtils.FONT_NORMAL, "cancel.png", "cancel", this, dataAccess);

        helpButton = ATSwingUtils.createHelpButtonByBounds(310, 320, 125, 25, this, ATSwingUtils.FONT_NORMAL,
            dataAccess);
        panel.add(helpButton);

    }


    public void actionPerformed(ActionEvent e)
    {

    }
}
