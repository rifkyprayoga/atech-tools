package com.atech.i18n.tool.client.admin.panels;

import com.atech.graphics.components.EditableAbstractPanel;
import com.atech.i18n.tool.client.DataAccessTT;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JTextField;

// TODO: Auto-generated Javadoc
/**
 * The Class PanelAKeyWordEdit.
 */
public class PanelAKeyWordEdit extends EditableAbstractPanel
{
    
    private static final long serialVersionUID = 5708024079414667402L;
    private JLabel jLabel = null;
    private JTextField jTextField = null;

    /**
     * Instantiates a new panel a key word edit.
     */
    public PanelAKeyWordEdit()
    {
        super(true, DataAccessTT.getInstance().getI18nControlInstance());
		initialize();
        init();
    }
    

    /**
     * This method initializes this
     * 
     */
    private void initialize() {
        jLabel = new JLabel();
        jLabel.setText("Keyword Edit");
        jLabel.setBounds(new Rectangle(216, 37, 38, 16));
        this.setLayout(null);
        this.setSize(new Dimension(474, 437));
        this.add(jLabel, null);
        this.add(getJTextField(), null);
    		
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


    /**
     * This method initializes jTextField	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextField()
    {
        if (jTextField == null)
        {
            jTextField = new JTextField();
            jTextField.setBounds(new Rectangle(169, 120, 182, 29));
        }
        return jTextField;
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
