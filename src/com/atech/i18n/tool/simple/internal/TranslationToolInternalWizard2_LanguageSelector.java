package com.atech.i18n.tool.simple.internal;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.LineBorder;

import org.apache.commons.collections.CollectionUtils;

import com.atech.data.enums.InternalSetting;
import com.atech.data.enums.LanguageISO639;
import com.atech.graphics.layout.TableLayoutUtil;
import com.atech.help.HelpCapable;
import com.atech.i18n.info.LanguageModule;
import com.atech.i18n.info.SupportedLanguage;
import com.atech.i18n.tool.simple.data.TranslationToolConfigurationDto;
import com.atech.i18n.tool.simple.util.DataAccessTT;
import com.atech.utils.ATDataAccessLMAbstract;
import com.atech.utils.ATSwingUtils;

import info.clearthought.layout.TableLayout;

/**
 * Created by andy on 29.06.17.
 */
public class TranslationToolInternalWizard2_LanguageSelector extends JDialog implements ActionListener, HelpCapable
{

    private static final long serialVersionUID = -884555927235544690L;

    JFrame parent;
    ATDataAccessLMAbstract dataAccess;
    // Map<String, List<LanguageISO639>> languageFamiliesWithMembers;

    JComboBox comboBoxModule;
    JComboBox comboBoxLanguages;
    JButton helpButton;
    // Map<String, LanguageSelectedDto> languageMap = null;

    List<LanguageSelectedDto> languageList = null;
    String lastSelectedLanaguge = null;
    TranslationToolConfigurationDto configuration;


    public TranslationToolInternalWizard2_LanguageSelector(JFrame frame, ATDataAccessLMAbstract dataAccess,
            TranslationToolConfigurationDto configuration)
    {
        super(frame);
        parent = frame;
        this.dataAccess = dataAccess;

        dataAccess.addComponent(this);

        createLanguageMap(configuration);

        // languageFamiliesWithMembers =
        // LanguageISO639.getLanguageFamiliesWithMembers();

        initGUI();

        // setVisible(true);
    }


    private void createLanguageMap(TranslationToolConfigurationDto configuration)
    {
        // languageMap = new HashMap<String, LanguageSelectedDto>();
        languageList = new ArrayList<LanguageSelectedDto>();
        this.configuration = configuration;

        Set<String> editedLanguages = null;

        if (configuration.getSettings() != null)
        {
            editedLanguages = configuration.getSettings().getEditedLanguages();
            lastSelectedLanaguge = configuration.getSettings().getLastTranslatedLanguage();
        }

        for (SupportedLanguage language : configuration.getSupportedLanguages())
        {
            LanguageSelectedDto selection = new LanguageSelectedDto(language.getLanguageDefinition());
            selection.setIsEdited(isLanguageEdited(language.getLanguageDefinition().getCode1(), editedLanguages));

            languageList.add(selection);
        }

        languageList.add(new LanguageSelectedDto(null));

    }


    private boolean isLanguageEdited(String languageShort, Set<String> editedLanguages)
    {
        if (CollectionUtils.isEmpty(editedLanguages))
        {
            return false;
        }

        return editedLanguages.contains(languageShort);
    }


    private void initGUI()
    {
        int width = 580;
        int height = 300;

        ATSwingUtils.initLibrary();

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, width, height);
        panel.setBorder(new LineBorder(Color.black));
        panel.setLayout(TableLayoutUtil.createVerticalLayout(60, TableLayout.FILL, 10, 30, 10));

        getContentPane().add(panel);

        String titleMessage = "Select Language & Module";

        this.setTitle(titleMessage);
        panel.add(ATSwingUtils.getTitleLabel(titleMessage, ATSwingUtils.FONT_BIG_BOLD), "0,0");

        JPanel bodyPanel = new JPanel();
        bodyPanel.setBorder(new LineBorder(Color.red));

        double[][] sizes = { { 50, 0.4, 20, 0.6, 80 }, //
                             { 30, 30, 30, 30, TableLayout.FILL } };

        bodyPanel.setLayout(new TableLayout(sizes));

        createNameLabel("Language", bodyPanel, "1, 1");
        // lblDoctor = ATSwingUtils.getLabel("", bodyPanel, "3, 1",
        // ATSwingUtils.FONT_NORMAL);

        // Map<String, List<LanguageISO639>> languageFamiliesWithMembers =
        // LanguageISO639.getLanguageFamiliesWithMembers();

        // Set familiesOrdered = SetUtils.orderedSet(families);

        comboBoxLanguages = new JComboBox(languageList.toArray());

        bodyPanel.add(comboBoxLanguages, "3, 1");

        //
        //
        createNameLabel("Module", bodyPanel, "1, 3");

        comboBoxModule = new JComboBox(this.configuration.getModules().toArray());
        bodyPanel.add(comboBoxModule, "3, 3");

        panel.add(bodyPanel, "0, 1");

        JPanel buttonsPanel = new JPanel();
        // buttonsPanel.setBorder(new LineBorder(Color.green));
        buttonsPanel.setLayout(TableLayoutUtil.createHorizontalLayout(20, 120, 6, 120, TableLayout.FILL, 120, 20));

        JButton helpButton = ATSwingUtils.createHelpButton(this, dataAccess, "help.png");
        buttonsPanel.add(helpButton, "1, 0");

        JButton button = new JButton("  Cancel");
        button.setIcon(ATSwingUtils.getImageIcon_22x22("cancel.png", this, dataAccess));
        button.setActionCommand("cancel");
        button.addActionListener(this);
        buttonsPanel.add(button, "3, 0");

        button = new JButton(" Next");
        button.setIcon(ATSwingUtils.getImageIcon_22x22("arrow_right_blue.png", this, dataAccess));
        button.setActionCommand("next");
        button.addActionListener(this);
        buttonsPanel.add(button, "5, 0");

        panel.add(buttonsPanel, "0, 3");

        setBounds(100, 100, width, height + 20);

        this.dataAccess.centerJDialog(this);
    }


    private void createNameLabel(String keyword, JPanel panel, String layoutConstraint)
    {
        ATSwingUtils.getLabel(keyword + ":", panel, layoutConstraint, ATSwingUtils.FONT_NORMAL_BOLD);
    }


    public void actionPerformed(ActionEvent e)
    {
        String actionCommand = e.getActionCommand();

        if (actionCommand.equals("next"))
        {
            LanguageModule selectedModule = (LanguageModule) comboBoxModule.getSelectedItem();

            // System.out.println("comboBoxLanguages.getSelectedIndex()" +
            // comboBoxLanguages.getSelectedIndex());

            LanguageSelectedDto selectedLanguage = (LanguageSelectedDto) comboBoxLanguages.getSelectedItem();

            this.configuration.setSelectedModule(selectedModule);

            if (selectedLanguage.getLanguage() == null)
            {
                this.dataAccess.removeComponent(this);
                new TranslationToolInternalWizard3_NewLanguage(this.parent, this.dataAccess, this.configuration);
            }
            else
            {
                this.configuration.setSelectedLanguage(selectedLanguage.getLanguage());
                this.dataAccess.removeComponent(this);

                new TranslationToolInternalWizard4_StartTool(this.parent, this.dataAccess, this.configuration);
            }
        }
        else if (actionCommand.equals("cancel"))
        {
            this.dataAccess.removeComponent(this);
            this.dispose();
        }
    }


    public static void main(String[] args)
    {
        JDialog d = new JDialog();

        DataAccessTT dd = DataAccessTT.createInstance(d);

        // TranslationToolInternalWizard2_LanguageSelector newLang = new
        // TranslationToolInternalWizard2_LanguageSelector(d, dd);
    }


    public String getHelpId()
    {
        return dataAccess.getInternalSetting(InternalSetting.Help_TranslationTool_Wizard2);
    }


    public JButton getHelpButton()
    {
        return this.helpButton;
    }


    public Component getComponent()
    {
        return this;
    }

    static class LanguageSelectedDto
    {

        private LanguageISO639 language;
        private boolean isEdited;


        public LanguageSelectedDto(LanguageISO639 language)
        {
            this.language = language;
        }


        public LanguageISO639 getLanguage()
        {
            return language;
        }


        public void setLanguage(LanguageISO639 language)
        {
            this.language = language;
        }


        public boolean isEdited()
        {
            return isEdited;
        }


        public void setIsEdited(boolean isEdited)
        {
            this.isEdited = isEdited;
        }


        public String toString()
        {
            if (language == null)
            {
                return "New Language...";
            }
            else
            {
                return this.language.getDescription() + (isEdited ? " (*)" : "");
            }
        }
    }

}
