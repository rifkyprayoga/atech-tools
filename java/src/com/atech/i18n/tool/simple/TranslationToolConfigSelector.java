package com.atech.i18n.tool.simple;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.atech.i18n.tool.simple.data.ConfigurationEntry;
import com.atech.utils.ATSwingUtils;
import com.atech.utils.file.PropertiesFile;

public class TranslationToolConfigSelector extends JDialog implements ActionListener
{

    private static final long serialVersionUID = 810806669693361400L;
    public ConfigurationEntry ce = null;
    public JComboBox cb_selector = null;
    private String config_path = "../config/";
    private String master_path = "../files/master_files/";
    Vector<ConfigurationEntry> configuration_files = null;

    public TranslationToolConfigSelector()
    {
        // super((JDialog)null, "Translation Tool Config Selector", true);
        setTitle("Translation Tool Config Selector");
        this.setModal(true);

        if (this.checkConfigFiles())
        {
            initDialog();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "We encountered problem. You either didn't install\n"
                    + "Base pack and/or Langauge pack. Please install\n"
                    + "both to correct location and restart application.\n", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public TranslationToolConfigSelector(boolean process)
    {
        this.checkConfigFiles();
    }

    private void initDialog()
    {

        ATSwingUtils.initLibrary();
        this.setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 360, 180);
        // panel.setBackground(Color.red);
        this.add(panel);

        // DataAccessTT

        // Vector<ConfigurationEntry> list = discoverConfigFiles();

        ATSwingUtils.getLabel("Please select which file you want to translate!", 40, 20, 350, 25, panel,
            ATSwingUtils.FONT_NORMAL_BOLD);
        // panel.add(label);

        // String[] dtt = { "GGC Core", "GGC Meter Tool" };

        cb_selector = ATSwingUtils.getComboBox(this.configuration_files, 40, 50, 250, 25, panel,
            ATSwingUtils.FONT_NORMAL);

        JButton b = new JButton("Go!");
        b.setBounds(60, 90, 100, 25);
        b.addActionListener(this);
        b.setActionCommand("go");
        panel.add(b);

        b = new JButton("Quit");
        b.setBounds(180, 90, 100, 25);
        b.addActionListener(this);
        b.setActionCommand("quit");
        panel.add(b);

        // ATSwingUtils.getButton("Go !", 70, 80, 120, 25, panel, font, null,
        // "go", this, da)

        this.setBounds(200, 200, 350, 160);
        this.setVisible(true);
    }

    private boolean checkConfigFiles()
    {
        this.configuration_files = this.discoverConfigFiles();
        // System.out.println("Config files found: " +
        // this.configuration_files.size());
        return this.configuration_files.size() > 0;
    }

    private Vector<ConfigurationEntry> discoverConfigFiles()
    {
        Vector<ConfigurationEntry> list = new Vector<ConfigurationEntry>();
        File f = new File(this.config_path);

        System.out.println("" + this.config_path);

        File[] files = f.listFiles(new FileFilter()
        {

            public boolean accept(File f)
            {
                if (f.isDirectory())
                    return false;

                return f.getName().toLowerCase().endsWith(".config");
            }

            /*
             * public String getDescription()
             * {
             * return null;
             * }
             */

        });

        for (File file : files)
        {

            ConfigurationEntry ce = this.getFileConfigurationEntry(file);

            // String s = getFileDescription(files[i]);

            if (ce != null)
            {
                list.add(ce);
            }

            /*
             * if (s!=null)
             * {
             * String file = "../config/" + files[i].getName();
             * // System.out.println("F: " + file);
             * // ConfigurationEntry ce = new ConfigurationEntry(file, s);
             * // list.add(ce);
             * }
             */
        }

        return list;
    }

    public ConfigurationEntry getFileConfigurationEntry(File file)
    {
        try
        {

            PropertiesFile pf = new PropertiesFile(file.getParent() + File.separator + file.getName());
            pf.readFile();

            String mod_desc = null;
            String file_name = null;
            String def_language = null;

            if (pf.containsKey("MODULE_DESCRIPTION"))
            {
                mod_desc = pf.get("MODULE_DESCRIPTION");
            }

            if (pf.containsKey("MASTER_FILE_ROOT"))
            {
                file_name = pf.get("MASTER_FILE_ROOT");
            }

            if (pf.containsKey("MASTER_FILE_LANGUAGE"))
            {
                def_language = pf.get("MASTER_FILE_LANGUAGE");
            }

            if (mod_desc != null)
            {
                ConfigurationEntry ce = new ConfigurationEntry(this.config_path + file.getName(), mod_desc, file_name,
                        def_language);

                File f = new File(this.master_path + ce.getMasterFile());

                if (f.exists())
                    return ce;
                else
                    return null;
            }
            else
                return null;

        }
        catch (Exception ex)
        {
            // return null;
        }

        return null;
    }

    /*
     * public String getFileDescription(File file)
     * {
     * BufferedReader br=null;
     * try
     * {
     * PropertiesFile pf = new PropertiesFile(file.getParent() +
     * file.getName());
     * if (pf.containsKey("MODULE_DESCRIPTION"))
     * {
     * }
     * /*
     * br = new BufferedReader(new FileReader(file));
     * String line = null;
     * while((line=br.readLine())!=null)
     * {
     * if (line.contains("MODULE_DESCRIPTION"))
     * {
     * String s = line.substring("MODULE_DESCRIPTION=".length());
     * // System.out.println(s);
     * return s;
     * }
     * }
     */
    /*
     * }
     * catch(Exception ex)
     * {
     * return null;
     * }
     * finally
     * {
     * try
     * {
     * if (br!=null)
     * br.close();
     * }
     * catch(Exception ex) {}
     * }
     * return null;
     * }
     */

    public void actionPerformed(ActionEvent ae)
    {
        String cmd = ae.getActionCommand();

        if (cmd.equals("go"))
        {
            // System.out.println("GO !!!");
            ce = (ConfigurationEntry) this.cb_selector.getSelectedItem();
            this.dispose();
            new TranslationTool(ce.config_file);
        }
        else if (cmd.equals("quit"))
        {
            ce = null;
            System.exit(0);
        }

    }

    public boolean wasAction()
    {
        return ce != null;
    }

    public ConfigurationEntry getSelectedItem()
    {
        return this.ce;
    }

    public Vector<ConfigurationEntry> getConfigurationFiles()
    {
        return configuration_files;
    }
}
