package com.atech.i18n.tool.client.admin.panels;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JLabel;

import com.atech.graphics.components.EditableAbstractPanel;
import com.atech.i18n.tool.client.DataAccessTT;

// TODO: Auto-generated Javadoc
/**
 * The Class PanelAGroupView.
 */
public class PanelAGroupView extends EditableAbstractPanel
{

    private static final long serialVersionUID = -1006849834169661138L;
    private JLabel jLabel = null;

    /**
     * Instantiates a new panel a group view.
     */
    public PanelAGroupView()
    {
        super(false, DataAccessTT.getInstance().getI18nControlInstance());
        initialize();
        init();
    }
    

    /**
     * This method initializes this
     * 
     */
    private void initialize() {
        jLabel = new JLabel();
        jLabel.setText("Module View");
        jLabel.setBounds(new Rectangle(216, 37, 38, 16));
        this.setLayout(null);
        this.setSize(new Dimension(474, 437));
        this.add(jLabel, null);
            
    }


    /**
     * Inits the.
     */
    public void init()
    {
        
    }
    
    
    /** 
     * getWarningString
     */
    @Override
    public String getWarningString(int _action_type)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /** 
     * hasDataChanged
     */
    @Override
    public boolean hasDataChanged()
    {
        // TODO Auto-generated method stub
        return false;
    }

    /** 
     * saveData
     */
    @Override
    public boolean saveData()
    {
        // TODO Auto-generated method stub
        return false;
    }

    /** 
     * setData
     */
    @Override
    public void setData(Object object)
    {
        // TODO Auto-generated method stub
        
    }

    /** 
     * setParent
     */
    @Override
    public void setParent(Object object)
    {
        // TODO Auto-generated method stub
        
    }

    /** 
     * setParentRoot
     */
    @Override
    public void setParentRoot(Object object)
    {
        // TODO Auto-generated method stub
        
    }
    
    
}
