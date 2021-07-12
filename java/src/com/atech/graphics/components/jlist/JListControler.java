package com.atech.graphics.components.jlist;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import com.atech.graphics.layout.TableLayoutUtil;
import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;

import info.clearthought.layout.TableLayout;

/**
 * Created by andy on 15.02.16.
 */
public class JListControler extends JPanel implements ActionListener
{

    private static final long serialVersionUID = -5879594997733420873L;

    ATDataAccessAbstract dataAccess;
    I18nControlAbstract i18nControl;

    JList listComponent;
    List<Object> listData = new ArrayList<Object>();
    JListControlerActions controlerActions;

    public double LEFT_MARGIN = 10;
    public double RIGHT_MARGIN = 10;
    public double SPLITER_WIDTH = 10;
    public int BUTTON_WIDTH = 30;
    public int BUTTON_HEIGHT = 30;
    public double BUTTON_SPLITER = 10;
    public double BUTTON_TOP_MARGIN = 10;
    public double BUTTON_BOTTOM_MARGIN = 10;


    public JListControler(ATDataAccessAbstract dataAccess, JListControlerActions controlerActions)
    {
        this.dataAccess = dataAccess;
        this.i18nControl = dataAccess.getI18nControlInstance();

        this.controlerActions = controlerActions;

        initGUI();
    }


    public JListControler(ATDataAccessAbstract dataAccess, JListControlerActions controlerActions,
            JListControlerSettings controlerSettings)
    {

        this.dataAccess = dataAccess;
        this.i18nControl = dataAccess.getI18nControlInstance();

        this.controlerActions = controlerActions;

        setDefaultsWithSettings(controlerSettings);

        initGUI();
    }


    private void setDefaultsWithSettings(JListControlerSettings controlerSettings)
    {
        this.LEFT_MARGIN = getCorrectValue(controlerSettings.getLeftMargin(), this.LEFT_MARGIN);
        this.RIGHT_MARGIN = getCorrectValue(controlerSettings.getRightMargin(), RIGHT_MARGIN);
        this.SPLITER_WIDTH = getCorrectValue(controlerSettings.getSpliterWidth(), SPLITER_WIDTH);
        this.RIGHT_MARGIN = getCorrectValue(controlerSettings.getRightMargin(), RIGHT_MARGIN);
        this.BUTTON_WIDTH = getCorrectValue(controlerSettings.getButtonWidth(), BUTTON_WIDTH);
        this.BUTTON_HEIGHT = getCorrectValue(controlerSettings.getButtonHeight(), BUTTON_HEIGHT);
        this.BUTTON_SPLITER = getCorrectValue(controlerSettings.getButtonSpliter(), BUTTON_SPLITER);
        this.BUTTON_TOP_MARGIN = getCorrectValue(controlerSettings.getButtonTopMargin(), BUTTON_TOP_MARGIN);
        this.BUTTON_BOTTOM_MARGIN = getCorrectValue(controlerSettings.getButtonBottomMargin(), BUTTON_BOTTOM_MARGIN);
    }


    public JListControler(ATDataAccessAbstract dataAccess, Double leftMargin, Double spliterWidth, Double rightMargin,
            Integer buttonWidth, Integer buttonHeight, Double buttonTopMargin, Double buttonBottomMargin,
            Double buttonSpliter, JListControlerActions controlerActions)
    {
        this.dataAccess = dataAccess;
        this.i18nControl = dataAccess.getI18nControlInstance();

        this.LEFT_MARGIN = getCorrectValue(leftMargin, this.LEFT_MARGIN);
        this.RIGHT_MARGIN = getCorrectValue(rightMargin, RIGHT_MARGIN);
        this.SPLITER_WIDTH = getCorrectValue(spliterWidth, SPLITER_WIDTH);
        this.RIGHT_MARGIN = getCorrectValue(rightMargin, RIGHT_MARGIN);
        this.BUTTON_WIDTH = getCorrectValue(buttonWidth, BUTTON_WIDTH);
        this.BUTTON_HEIGHT = getCorrectValue(buttonHeight, BUTTON_HEIGHT);
        this.BUTTON_SPLITER = getCorrectValue(buttonSpliter, BUTTON_SPLITER);
        this.BUTTON_TOP_MARGIN = getCorrectValue(buttonTopMargin, BUTTON_TOP_MARGIN);
        this.BUTTON_BOTTOM_MARGIN = getCorrectValue(buttonBottomMargin, BUTTON_BOTTOM_MARGIN);

        initGUI();
    }


    private double getCorrectValue(Double valueToSet, double originalValue)
    {
        return valueToSet != null ? valueToSet : originalValue;
    }


    private int getCorrectValue(Integer valueToSet, int originalValue)
    {
        return valueToSet != null ? valueToSet : originalValue;
    }


    public void loadData(List<? extends Object> list)
    {
        this.listData.clear();
        this.listData.addAll(list);
        populateJListExtended();
    }


    public List<? extends Object> getData()
    {
        return this.listData;
    }


    private void initGUI()
    {
        // table layout

        // this.setBackground(Color.magenta);
        this.setLayout(TableLayoutUtil.createHorizontalLayout(LEFT_MARGIN, TableLayout.FILL, SPLITER_WIDTH,
            BUTTON_WIDTH + 4, RIGHT_MARGIN));

        // this.add(createPanel(Color.magenta), "0, 0");
        // this.add(createPanel(Color.blue), "1, 0");
        // this.add(createPanel(Color.red), "2, 0");
        // this.add(createPanel(Color.cyan), "3, 0");
        // this.add(createPanel(Color.magenta), "4, 0");
        //
        // if (true)
        // return;

        this.listComponent = new JList();
        // this.listComponent.setBackground(Color.cyan);
        this.listComponent.addMouseListener(new MouseAdapter()
        {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2)
                {
                    itemEdit();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(this.listComponent);
        this.add(scrollPane, "1, 0");

        JPanel buttonsPanel = new JPanel();
        // buttonsPanel.setBackground(Color.green);
        buttonsPanel.setLayout(TableLayoutUtil.createVerticalLayout(BUTTON_TOP_MARGIN, BUTTON_HEIGHT, BUTTON_SPLITER,
            BUTTON_HEIGHT, BUTTON_SPLITER, BUTTON_HEIGHT, BUTTON_BOTTOM_MARGIN));

        buttonsPanel.add(createButton("ADD", "item_add", "folder_add.png"), "0, 1");

        buttonsPanel.add(createButton("EDIT", "item_edit", "folder_edit.png"), "0, 3");

        buttonsPanel.add(createButton("DELETE", "item_delete", "folder_delete.png"), "0, 5");

        this.add(buttonsPanel, "3, 0");

    }


    private JPanel createPanel(Color color)
    {
        JPanel p = new JPanel();
        p.setBackground(color);
        return p;
    }


    private JButton createButton(String text, String command, String iconName)
    {

        JButton button = new JButton(
                ATSwingUtils.getImageIcon(iconName, BUTTON_WIDTH - 8, BUTTON_HEIGHT - 8, this, dataAccess));
        button.setActionCommand(command);
        button.addActionListener(this);
        button.setToolTipText(i18nControl.getMessage(text));

        return button;
    }


    public void actionPerformed(ActionEvent e)
    {

        String action = e.getActionCommand();

        if (action.equals("item_add"))
        {

            Object item = this.controlerActions.executeItemAddAction();

            if (item != null)
            {
                this.listData.add(item);
                populateJListExtended();
            }

        }
        else if (action.equals("item_edit"))
        {

            if (checkIfSelectionEmpty())
            {
                return;
            }

            itemEdit();

        }
        else if (action.equals("item_delete"))
        {

            if (checkIfSelectionEmpty())
            {
                return;
            }

            int ii = JOptionPane.showConfirmDialog(this, i18nControl.getMessage("ARE_YOU_SURE_DELETE"),
                i18nControl.getMessage("ERROR"), JOptionPane.YES_NO_OPTION);

            if (ii == JOptionPane.YES_OPTION)
            {
                Object item = getSelectedItem();

                this.listData.remove(item);

                this.controlerActions.executeItemDeleteAction(item);

                populateJListExtended();
            }

        }

    }


    private boolean checkIfSelectionEmpty()
    {

        if (this.listComponent.isSelectionEmpty())
        {
            JOptionPane.showConfirmDialog(this, i18nControl.getMessage("SELECT_ITEM_FIRST"),
                i18nControl.getMessage("ERROR"), JOptionPane.CLOSED_OPTION);
            return true;
        }

        return false;
    }


    private void itemEdit()
    {
        this.controlerActions.executeItemEditAction(getSelectedItem());

        populateJListExtended();
    }


    private Object getSelectedItem()
    {

        return this.listData.get(this.listComponent.getSelectedIndex());
    }


    /**
     * Populates CheckBoxList component
     */
    public void populateJListExtended()
    {
        DefaultListModel newListModel = new DefaultListModel();

        for (int i = 0; i < listData.size(); i++)
        {
            newListModel.addElement(listData.get(i));
        }

        this.listComponent.setModel(newListModel);
    }


    @Override
    public String toString()
    {
        return "JListWithControlButtons{" + "listData=" + listData + ", LEFT_MARGIN=" + LEFT_MARGIN + ", RIGHT_MARGIN="
                + RIGHT_MARGIN + ", SPLITER_WIDTH=" + SPLITER_WIDTH + ", BUTTON_WIDTH=" + BUTTON_WIDTH
                + ", BUTTON_HEIGHT=" + BUTTON_HEIGHT + ", BUTTON_SPLITER=" + BUTTON_SPLITER + ", BUTTON_TOP_MARGIN="
                + BUTTON_TOP_MARGIN + ", BUTTON_BOTTOM_MARGIN=" + BUTTON_BOTTOM_MARGIN + '}';
    }
}
