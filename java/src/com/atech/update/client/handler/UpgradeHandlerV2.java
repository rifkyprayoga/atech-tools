package com.atech.update.client.handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.swing.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atech.i18n.I18nControlAbstract;
import com.atech.update.config.UpdateConfiguration;
import com.atech.update.config.UpdateConfigurationXml;
import com.atech.upgrade.client.gui.UpgradeDialog;
import com.atech.utils.ATDataAccessAbstract;

/**
 * Created by andy on 15.03.15.
 */
public class UpgradeHandlerV2 implements UpgradeHandlerInterface
{

    private static final Logger LOG = LoggerFactory.getLogger(UpgradeHandlerV2.class);

    ATDataAccessAbstract dataAccess;
    UpgradeDialog upgradeDialog;
    I18nControlAbstract i18nControl;
    UpdateConfiguration updateConfiguration;


    public UpgradeHandlerV2(UpgradeDialog updDialog, UpdateConfiguration updateConfiguration,
            ATDataAccessAbstract dataAccess)
    {
        this.dataAccess = dataAccess;
        this.upgradeDialog = updDialog;
        this.updateConfiguration = updateConfiguration;
    }


    public void checkServer()
    {
        if (!dataAccess.getDeveloperMode())
        {
            JOptionPane.showMessageDialog(this.upgradeDialog, i18nControl.getMessage("UPDATE_SERVER_NA"),
                i18nControl.getMessage("INFORMATION"), JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try
        {

            String server_name = this.upgradeDialog.getUpdateSettings().update_server;
            long current_version = 4;
            long current_db = 7;

            String iLine2;

            iLine2 = getServletData(
                server_name + "ATechUpdateSystem?action=get_update_status&" + "product_id=" + dataAccess.getAppName()
                        + "&" + "current_version=" + current_version + "&" + "current_db=" + current_db);

            if (iLine2 == null)
                return;

            String ret_msg = iLine2.substring(
                iLine2.indexOf("RETURN_DATA_START__") + "RETURN_DATA_START__<br>".length(),
                iLine2.indexOf("<br>__RETURN_DATA_END"));

            // resolve result for update system v2
            if (ret_msg.contains("ERR"))
            {
                String err = "";

                if (ret_msg.startsWith("ERR_NO_SUCH_APP"))
                {
                    err = "UPD_ERR_NO_SUCH_APP";
                }
                else if (ret_msg.startsWith("ERR_INTERNAL_ERROR"))
                {
                    err = "UPD_ERR_INTERNAL_ERROR";
                }

                dataAccess.showDialog(this.upgradeDialog, ATDataAccessAbstract.DIALOG_ERROR,
                    i18nControl.getMessage(err));
                this.upgradeDialog.setStatusText(i18nControl.getMessage("STATUS_UPD_FAILED_DATA"));

            }
            else if (ret_msg.contains(";"))
            {
                StringTokenizer strtok = new StringTokenizer(ret_msg, ";");
                Hashtable<String, String> msges = new Hashtable<String, String>();

                String return_info = null;

                while (strtok.hasMoreTokens())
                {
                    String t = strtok.nextToken();
                    msges.put(t.substring(0, t.indexOf("=")), t.substring(t.indexOf("=") + 1));
                }

                // System.out.println(msges);

                this.upgradeDialog.setNextVersion(Long.parseLong(msges.get("NEXT_VERSION")));
                boolean cont = false;

                if (this.upgradeDialog.getNextVersion() == current_version)
                {
                    if (msges.containsKey("NEXT_DB_VERSION"))
                    {
                        System.out.println("UPDATE_FOR_HIGHER_DB_FOUND");
                        return_info = String.format(i18nControl.getMessage("UPDATE_FOR_HIGHER_DB_FOUND"),
                            msges.get("NEXT_DB_VERSION_STR"));
                        this.upgradeDialog.setStatusText(i18nControl.getMessage("STATUS_UPD_NO_VALID_UPDATE"));
                    }
                    else
                    {
                        System.out.println("NO_UPDATE_FOUND");
                        return_info = i18nControl.getMessage("NO_UPDATE_FOUND");
                        this.upgradeDialog.setStatusText(i18nControl.getMessage("STATUS_UPD_NO_UPDATE"));
                    }

                    // status_label.setText(i18nControl.getMessage("STATUS_UPD_NO_VALID_UPDATE"));

                }
                else
                {
                    cont = true;
                    if (msges.containsKey("NEXT_DB_VERSION"))
                    {
                        System.out.println("UPDATE_FOUND_ALSO_HIGHER_DB");
                        return_info = String.format(i18nControl.getMessage("UPDATE_FOUND_ALSO_HIGHER_DB"),
                            msges.get("NEXT_DB_VERSION_STR"), msges.get("NEXT_VERSION_STR"));
                    }
                    else
                    {
                        System.out.println("UPDATE_FOUND_VERSION");
                        return_info = String.format(i18nControl.getMessage("UPDATE_FOUND_VERSION"),
                            msges.get("NEXT_VERSION_STR"));
                    }
                    this.upgradeDialog.setStatusText(i18nControl.getMessage("STATUS_UPD_UPDATE_FOUND"));

                }

                if (cont)
                {

                    // Custom button text
                    Object[] options = { i18nControl.getMessage("YES"), i18nControl.getMessage("NO") };

                    int n = JOptionPane.showOptionDialog(this.upgradeDialog, return_info,
                        i18nControl.getMessage("QUESTION"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);

                    if (n == JOptionPane.YES_OPTION)
                    {

                        iLine2 = this.getServletData(server_name + "ATechUpdateSystem?action=get_xml&" + "product_id="
                                + dataAccess.getAppName() + "&" + "current_version=" + msges.get("NEXT_VERSION"));

                        if (iLine2 == null)
                            // 3.
                            // dataAccess.showDialog(this,
                            // ATDataAccessAbstract.DIALOG_WARNING,
                            // "Error reading xml data !");
                            return;
                        else
                        {
                            ret_msg = iLine2.substring(
                                iLine2.indexOf("RETURN_DATA_START__") + "RETURN_DATA_START__<br>".length(),
                                iLine2.indexOf("<br>__RETURN_DATA_END"));
                            UpdateConfigurationXml ucxml = new UpdateConfigurationXml(ret_msg);

                            this.upgradeDialog.getModel().updateModel(ucxml.getUpdateConfiguration());
                            this.upgradeDialog.getModel().fireTableDataChanged();

                            this.upgradeDialog.getUpdateButton().setEnabled(true);

                            // System.out.println("Got xml: \"" + ret_msg +
                            // "\"");
                            // dataAccess.showDialog(this,
                            // ATDataAccessAbstract.DIALOG_INFO, "Got xml !");
                        }

                        // 3.
                        // dataAccess.showDialog(this,
                        // ATDataAccessAbstract.DIALOG_INFO, "Update YAY !");

                    }
                }
                else
                {
                    dataAccess.showDialog(this.upgradeDialog, ATDataAccessAbstract.DIALOG_INFO, return_info);
                }

            }
            else
            {
                dataAccess.showDialog(this.upgradeDialog, ATDataAccessAbstract.DIALOG_ERROR,
                    i18nControl.getMessage("UPD_ERR_INTERNAL_ERROR"));
                this.upgradeDialog.setStatusText(i18nControl.getMessage("STATUS_UPD_FAILED_COMM_ERROR"));
            }

        }
        catch (Exception ex)
        {
            this.upgradeDialog.setStatusText(i18nControl.getMessage("UPD_ERROR_CONTACTING_SERVER"));
            // System.out.println(ex);

            dataAccess.showDialog(this.upgradeDialog, ATDataAccessAbstract.DIALOG_ERROR,
                i18nControl.getMessage("UPD_ERROR_CONTACTING_SERVER"));

            ex.printStackTrace();
        }

    }


    private String getServletData(String full_url)
    {
        try
        {
            URL url = new URL(full_url);

            LOG.debug("Servlet::URL (" + full_url + ")");

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String iLine, iLine2;
            iLine2 = "";

            while ((iLine = in.readLine()) != null)
            {
                iLine2 += iLine;
                // System.out.println(iLine);
            } // while file

            in.close();

            return iLine2;
        }
        catch (Exception ex)
        {
            LOG.error("Servlet::URL (" + full_url + ")");
            LOG.error("Error contacting servlet: " + ex, ex);
            // ex.printStackTrace();
            this.upgradeDialog.setStatusText(this.i18nControl.getMessage("UPD_ERROR_CONTACTING_SERVER"));
            dataAccess.showDialog(this.upgradeDialog, ATDataAccessAbstract.DIALOG_ERROR,
                i18nControl.getMessage("UPD_ERROR_CONTACTING_SERVER"));

            // System.out.println("Exception reading Web. Ex: " + ex);
            return null;
        }

    }


    public void runUpdate()
    {
        try
        {
            String server_name = this.upgradeDialog.getUpdateSettings().update_server;
            long current_version = 4;
            // long current_db = 7;

            String iLine2;

            iLine2 = getServletData(server_name + "ATechUpdateSystem?action=get_update_list&" + "product_id="
                    + dataAccess.getAppName() + "&" + "current_version=" + current_version + "&" + "next_version="
                    + this.upgradeDialog.getNextVersion());

            if (iLine2 == null)
            {
                System.out.println("No data returned !");
                return;
            }

            String ret_msg = iLine2.substring(
                iLine2.indexOf("RETURN_DATA_START__") + "RETURN_DATA_START__<br>".length(),
                iLine2.indexOf("<br>__RETURN_DATA_END"));

            this.upgradeDialog.processDetailsData(ret_msg);

        }
        catch (Exception ex)
        {
            System.out.println("runUpdate(). Ex.: " + ex);
        }
        // dataAccess.showDialog(this, ATDataAccessAbstract.DIALOG_INFO,
        // "Run Update !");

    }
}
