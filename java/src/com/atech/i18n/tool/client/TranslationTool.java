/**
 * 
 */
package com.atech.i18n.tool.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import com.atech.i18n.I18nControlAbstract;
import com.atech.i18n.tool.client.admin.TranslationTreeDialog;
import com.atech.utils.ATSwingUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class TranslationTool.
 * 
 * @author Andy
 */
public class TranslationTool extends JFrame implements ActionListener
{
    
    private static final long serialVersionUID = 2609056469152378167L;

    /**
     * The menus.
     */
    Hashtable<String,JMenu> menus = null; 
    
    /**
     * The m_da.
     */
    DataAccessTT m_da = DataAccessTT.getInstance();
    
    /**
     * The m_ic.
     */
    I18nControlAbstract m_ic = null;
    
    /**
     * The m_version.
     */
    String m_version = "0.0.1";
    
    /**
     * Instantiates a new translation tool.
     */
    public TranslationTool()
    {
        super();

        m_ic = m_da.getI18nControlInstance();
        m_da.startDb();
        
        init();
        
        this.setSize(800, 600);
        this.setVisible(true);
    }

    
    private void init()
    {
        this.setTitle("Translation Tool (v" + m_version + ")");
        initMenus();
    }
    
    private void initMenus()
    {
        JMenuBar mbar = new JMenuBar();
        menus = new Hashtable<String,JMenu>();
        
        JMenu menu = ATSwingUtils.createMenu("FILE", "FILE_TIP", m_ic);
        
        ATSwingUtils.createMenuItem(menu, "LOGIN", "LOGIN_DESC", "login", this, null, m_ic, m_da, this, ATSwingUtils.FONT_NORMAL);
        ATSwingUtils.createMenuItem(menu, "LOGOUT", "LOGOUT_DESC", "logout", this, null, m_ic, m_da, this, ATSwingUtils.FONT_NORMAL);
        menu.addSeparator();
        ATSwingUtils.createMenuItem(menu, "EXIT", "EXIT_DESC", "exit", this, null, m_ic, m_da, this, ATSwingUtils.FONT_NORMAL);
        
        menus.put("FILE", menu);
        mbar.add(menu);
        
        menu = ATSwingUtils.createMenu("TRANSLATOR", "FILE_TIP", m_ic);
        
        ATSwingUtils.createMenuItem(menu, "TRANSLATE", "TRANSLATE_DESC", "tr_translate", this, null, m_ic, m_da, this, ATSwingUtils.FONT_NORMAL);
        //ATSwingUtils.createMenuItem(menu, "LOGOUT", "LOGOUT_DESC", "logout", this, null, m_ic, m_da, this);
        //menu.addSeparator();
        //ATSwingUtils.createMenuItem(menu, "EXIT", "EXIT_DESC", "exit", this, null, m_ic, m_da, this);

        menus.put("TRANSLATOR", menu);
        mbar.add(menu);
        
        
        menu = ATSwingUtils.createMenu("ADMIN", "ADMIN_DESC", m_ic);
        
        ATSwingUtils.createMenuItem(menu, "USER_MGMT", "USER_MGMT_DESC", "adm_user", this, null, m_ic, m_da, this, ATSwingUtils.FONT_NORMAL);
        menu.addSeparator();
        ATSwingUtils.createMenuItem(menu, "TRANSLATION_MGMT", "TRANSLATION_MGMT_DESC", "adm_translation", this, null, m_ic, m_da, this, ATSwingUtils.FONT_NORMAL);
        menu.addSeparator();
        ATSwingUtils.createMenuItem(menu, "LANGUAGES_MGMT", "LANGUAGES_MGMT_DESC", "adm_lang", this, null, m_ic, m_da, this, ATSwingUtils.FONT_NORMAL);
        menu.addSeparator();
        ATSwingUtils.createMenuItem(menu, "EXPORTS", "EXIT_DESC", "adm_exports", this, null, m_ic, m_da, this, ATSwingUtils.FONT_NORMAL);
        
        menus.put("ADMIN", menu);
        mbar.add(menu);
        
        
        menu = ATSwingUtils.createMenu("HELP", "ADMIN_DESC", m_ic);
        
        ATSwingUtils.createMenuItem(menu, "ABOUT", "ABOUT_DESC", "about", this, null, m_ic, m_da, this, ATSwingUtils.FONT_NORMAL);

        menus.put("HELP", menu);
        mbar.add(menu);
        
        this.setJMenuBar(mbar);
        
    }
    
    
    /**
     * The main method.
     * 
     * @param args the args
     */
    public static void main(String[] args)
    {
        new TranslationTool();
        // TODO Auto-generated method stub

    }

    private void cmdQuit()
    {
        System.exit(0);
    }


    /** 
     * actionPerformed
     */
    public void actionPerformed(ActionEvent e)
    {
        // TODO Auto-generated method stub
        String cmd = e.getActionCommand();
        
        if (cmd.equals("exit"))
        {
            cmdQuit();
        }
        else if (cmd.equals("adm_translation"))
        {
            /*TranslationTreeDialog ttd =*/ new TranslationTreeDialog(this, m_da, 1);
        }
        else
            System.out.println("Unknown command: " + cmd);
        
        
    }

}
