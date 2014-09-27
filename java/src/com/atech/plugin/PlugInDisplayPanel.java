package com.atech.plugin;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;

public class PlugInDisplayPanel extends JPanel
{

    PlugInDisplaySettings m_sett;
    ATDataAccessAbstract m_da;
    JList m_list;
    DefaultListModel model = null;

    public PlugInDisplayPanel(ATDataAccessAbstract da, PlugInDisplaySettings sett)
    {
        super();
        this.m_da = da;
        this.m_sett = sett;

        init();
    }

    public void init()
    {
        this.setLayout(this.m_sett.getLayoutManager());
        this.setBorder(new LineBorder(Color.black));

        ATSwingUtils.initLibrary();

        ATSwingUtils.getLabel(m_da.getI18nControlInstance().getMessage("PLUGIN_DISPLAY_NAME"), 10, 0,
            this.m_sett.getBounds().width - 10, 30, this, ATSwingUtils.FONT_NORMAL_BOLD);

        m_list = new JList();
        m_list.setBackground(Color.red);
        m_list.setCellRenderer(new PlugInInfoCellRenderer());

        model = new DefaultListModel();

        JScrollPane scr = new JScrollPane(m_list);
        scr.setBounds(0, 30, this.m_sett.getBounds().width, this.m_sett.getBounds().height - 30);
        this.add(scr);

        this.setBounds(this.m_sett.getBounds());
    }

    public void addToList(PlugInInfo info)
    {
        this.model.addElement(info);
        this.m_list.setModel(model);
        this.m_list.repaint();
    }

    private class PlugInInfoCellRenderer implements ListCellRenderer
    {

        public Component getListCellRendererComponent(final JList list, final Object value, final int index,
                final boolean isSelected, final boolean cellHasFocus)
        {
            return (JPanel) value;
        }

    }

}
