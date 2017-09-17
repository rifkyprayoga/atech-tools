package com.atech.i18n.tool.simple.internal;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;

import com.atech.data.enums.LanguageISO639;
import com.atech.graphics.dialogs.StandardDialogForObject;
import com.atech.graphics.layout.TableLayoutUtil;
import com.atech.i18n.tool.simple.data.TranslationToolSettingsDto;
import com.atech.utils.ATDataAccessLMAbstract;
import com.atech.utils.ATSwingUtils;

import info.clearthought.layout.TableLayout;

/**
 * Created by andy on 29.06.17.
 */
public class TTIConfiguration extends StandardDialogForObject
{

    private static final long serialVersionUID = -901330927737036233L;
    JFrame parent;
    ATDataAccessLMAbstract dataAccess;
    Map<String, List<LanguageISO639>> languageFamiliesWithMembers;

    JComboBox comboBoxLanguage;
    TranslationToolSettingsDto settings;


    public TTIConfiguration(JDialog dialog, ATDataAccessLMAbstract dataAccess)
    {
        super(dialog, dataAccess);
    }


    public TTIConfiguration(JDialog dialog, ATDataAccessLMAbstract dataAccess, TranslationToolSettingsDto settings,
            boolean editValue)
    {
        super(dialog, dataAccess, settings, true);
    }


    // public TTIConfiguration(JFrame frame, ATDataAccessLMAbstract dataAccess)
    // {
    // super(frame, dataAccess);
    // parent = frame;
    // this.dataAccess = dataAccess;
    //
    // dataAccess.addComponent(this);
    //
    // // languageFamiliesWithMembers =
    // // LanguageISO639.getLanguageFamiliesWithMembers();
    //
    // initGUI();
    //
    // setVisible(true);
    // }

    @Override
    public void loadData(Object dataObject)
    {

    }


    @Override
    public boolean saveData()
    {
        return false;
    }


    public void initGUI()
    {
        int width = 500;
        int height = 250;

        ATSwingUtils.initLibrary();

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, width, height);
        panel.setBorder(new LineBorder(Color.black));
        panel.setLayout(TableLayoutUtil.createVerticalLayout(60, TableLayout.FILL, 10, 30, 10));

        getContentPane().add(panel);

        String titleMessage = "Select Language / Module";

        this.setTitle(titleMessage);
        panel.add(ATSwingUtils.getTitleLabel(titleMessage, ATSwingUtils.FONT_BIG_BOLD), "0,0");

        JPanel bodyPanel = new JPanel();
        bodyPanel.setBorder(new LineBorder(Color.red));

        double[][] sizes = { { 40, 0.4, 10, 0.6, 10, 40, 40 }, //
                             { 10, 30, 10, 30, TableLayout.FILL } };

        bodyPanel.setLayout(new TableLayout(sizes));

        createNameLabel("Language Family", bodyPanel, "1, 1");
        // lblDoctor = ATSwingUtils.getLabel("", bodyPanel, "3, 1",
        // ATSwingUtils.FONT_NORMAL);

        // Map<String, List<LanguageISO639>> languageFamiliesWithMembers =
        // LanguageISO639.getLanguageFamiliesWithMembers();

        Set<String> families = languageFamiliesWithMembers.keySet();

        // Set familiesOrdered = SetUtils.orderedSet(families);

        Object[] sortedFamilies = getSortedList(families);

        final JComboBox comboBoxLangFamily = new JComboBox(getSortedList(families));
        comboBoxLangFamily.addItemListener(new ItemListener()
        {

            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                    System.out.println("Item state changed: " + e.getItem().toString());

                    comboBoxLanguage.removeAllItems();

                    Object[] sortedListLanguage = getSortedListLanguage(languageFamiliesWithMembers.get(e.getItem()));

                    for (Object item : sortedListLanguage)
                    {
                        comboBoxLanguage.addItem(item);
                    }

                }
            }
        });

        bodyPanel.add(comboBoxLangFamily, "3, 1");

        //
        //
        createNameLabel("Language", bodyPanel, "1, 3");

        String selectedFamily = (String) sortedFamilies[0];

        comboBoxLanguage = new JComboBox(getSortedListLanguage(languageFamiliesWithMembers.get(selectedFamily)));

        bodyPanel.add(comboBoxLanguage, "3, 3");

        // dtAppointment = new DateTimeComponent(dataAccess,
        // DateTimeComponent.ALIGN_VERTICAL, 10,
        // DateTimeComponent.TIME_MAXIMAL_MINUTE);
        // bodyPanel.add(dtAppointment, "3, 3");
        //
        // dtAppointment.setDateTimeAsCurrent();
        //
        // currentTime = dtAppointment.getDateTime();
        //
        // createNameLabel("APPOINTMENT_TEXT", bodyPanel, "1, 5");
        // tfAppointment = ATSwingUtils.getTextField("", bodyPanel, "3, 5",
        // ATSwingUtils.FONT_NORMAL);
        //
        // createNameLabel("COMMENT", bodyPanel, "1, 7");
        // taComment = ATSwingUtils.getTextArea("", bodyPanel, "3, 7",
        // ATSwingUtils.FONT_NORMAL);

        panel.add(bodyPanel, "0, 1");

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(new LineBorder(Color.green));
        buttonsPanel.setLayout(TableLayoutUtil.createHorizontalLayout(20, 120, 6, 120, TableLayout.FILL, 120, 20));

        JButton helpButton = ATSwingUtils.createHelpButton(this, dataAccess, "../help.png");
        buttonsPanel.add(helpButton, "1, 0");

        JButton button = new JButton("  Cancel");
        button.setIcon(ATSwingUtils.getImageIcon_22x22("../cancel.png", this, dataAccess));
        button.setActionCommand("cancel");
        button.addActionListener(this);
        buttonsPanel.add(button, "3, 0");

        button = new JButton(" Next");
        button.setIcon(ATSwingUtils.getImageIcon_22x22("../ok.png", this, dataAccess));
        button.setActionCommand("next");
        button.addActionListener(this);
        buttonsPanel.add(button, "5, 0");

        panel.add(buttonsPanel, "0, 3");

        setBounds(100, 100, width, height + 20);

        // this.getContentPane().add(buttonsPanel, "0, 3");

        // this.dataAccess.centerJDialog(this, parent);
    }


    private Object[] getSortedList(Set<String> stringSet)
    {
        List<String> outList = new ArrayList<String>();

        for (String s : stringSet)
        {
            outList.add(s);
        }

        return getSortedList(outList);

    }


    private Object[] getSortedList(List<String> list)
    {
        Collections.sort(list);
        return list.toArray();
    }


    private Object[] getSortedListLanguage(List<LanguageISO639> list)
    {
        Collections.sort(list);
        return list.toArray();
    }


    private void createNameLabel(String keyword, JPanel panel, String layoutConstraint)
    {
        ATSwingUtils.getLabel(keyword + ":", panel, layoutConstraint, ATSwingUtils.FONT_NORMAL_BOLD);
    }


    public void actionPerformed(ActionEvent e)
    {
        String actionCommand = e.getActionCommand();

        System.out.println("Unknown action: " + actionCommand);
    }


    @Override
    public String getHelpId()
    {
        return null;
    }


    @Override
    public Object getEditedObject()
    {
        return this.settings;
    }

}
