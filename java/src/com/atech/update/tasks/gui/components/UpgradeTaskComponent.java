package com.atech.update.tasks.gui.components;

import java.awt.*;

import javax.swing.*;

import com.atech.update.tasks.def.UpgradeTaskDefinition;
import com.atech.utils.ATSwingUtils;

/**
 * Created by andy on 23.03.15.
 */
public abstract class UpgradeTaskComponent extends JPanel
{

    UpgradeTaskDefinition definition;
    JProgressBar progressBar;


    public UpgradeTaskComponent(UpgradeTaskDefinition definition)
    {
        super();

        init(); // here
        prepare();
        startOperation();

    }


    private void init()
    {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(640, 50));

        ATSwingUtils.initLibrary();

        ATSwingUtils.getLabel("fdhjhfsdkkfshd", 0, 10, 300, 35, this, ATSwingUtils.FONT_NORMAL);

        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);

    }


    protected abstract void prepare();


    protected abstract void startOperation();

}
