package com.atech.update.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.*;

import com.atech.i18n.I18nControlAbstract;
import com.atech.update.client.handler.UpgradeHandlerV3;
import com.atech.update.v3.dto.ApplicationNoteDTO;
import com.atech.update.v3.dto.UpdateNotesDTO;
import com.atech.update.v3.xml.UpdateServerResponse;
import com.atech.utils.ATDataAccessAbstract;
import com.atech.utils.ATSwingUtils;
import com.atech.utils.data.ATechDate;

/**
 * Created by andy on 17.03.15.
 */
public class UpdateNewsDialog extends JDialog implements ActionListener
{

    private static final long serialVersionUID = -5459706427150325095L;

    ATDataAccessAbstract dataAccess = null;
    I18nControlAbstract i18nControl = null;
    private JPanel panel;
    private JButton btnClose;
    List<UpdateNotesDTO> notes = null;
    UpgradeHandlerV3 upgradeHandler;
    JEditorPane editorPane;
    SimpleDateFormat sdf;
    private JButton helpButton;


    public UpdateNewsDialog(JDialog parent, ATDataAccessAbstract da)
    {
        super(parent, "", true);

        dataAccess = da;
        i18nControl = dataAccess.getI18nControlInstance();
        this.sdf = new SimpleDateFormat("dd.MM.yyyy");

        ATSwingUtils.initLibrary();
        this.setBounds(130, 50, 650, 550); // 360

        this.setResizable(false);
        ATSwingUtils.centerJDialog(this, dataAccess.getParent());

        this.dataAccess.addComponent(this);

        upgradeHandler = UpgradeHandlerV3.getInstance();

        init();

        this.setVisible(true);
    }


    private void init()
    {
        panel = new JPanel();
        panel.setBounds(5, 5, 620, 550);
        panel.setLayout(null); // 600 450
        this.getContentPane().add(panel);

        ATSwingUtils.getTitleLabel(i18nControl.getMessage("UPDATE_NEWS_NOTES"), 0, 20, 620, 35, panel,
            ATSwingUtils.FONT_BIG_BOLD);

        this.setTitle(i18nControl.getMessage("UPDATE_NEWS_NOTES"));

        editorPane = new JEditorPane("text/html", "");

        JScrollPane scroll = new JScrollPane(editorPane);
        scroll.setBounds(30, 75, 580, 385);
        panel.add(scroll);
        scroll.repaint();

        scroll.updateUI();

        this.btnClose = ATSwingUtils.getButton("   " + i18nControl.getMessage("CLOSE"), 350, 475, 120, 25, panel, //
            ATSwingUtils.FONT_NORMAL, "cancel.png", "close", this, dataAccess, new int[] { 22, 22 });

        this.helpButton = ATSwingUtils.createHelpButtonByBounds(485, 475, 120, 25, this, ATSwingUtils.FONT_NORMAL,
            dataAccess);
        // this.help_button.setFont(font_normal);
        panel.add(helpButton);

        this.loadData();

    }


    public void loadData()
    {
        Object responseNotes = null;
        this.editorPane.setContentType("text/html");

        try
        {
            responseNotes = this.upgradeHandler.getNotes();
        }
        catch (Exception e)
        {
            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<html><body>");

            htmlContent
                    .append("<h2>There was an Exception while contacting Update Server and/or processing response</h2><br><br>\n");
            htmlContent.append("<b>Exception Message:<b>&nbsp;&nbsp;" + e.getLocalizedMessage());

            htmlContent.append("</body></html>");

            this.editorPane.setText(htmlContent.toString());
        }

        if (responseNotes == null)
        {
            String htmlContent = "<html><body><h2>Undefinied internal error</h2><br><br>\n</body></html>";

            this.editorPane.setText(htmlContent);
        }
        else if (responseNotes instanceof UpdateNotesDTO)
        {
            loadData((UpdateNotesDTO) responseNotes);
        }
        else if (responseNotes instanceof UpdateServerResponse)
        {
            loadData((UpdateServerResponse) responseNotes);
        }

    }


    private void loadData(UpdateServerResponse response)
    {
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<html><body>");

        htmlContent.append("<h2>There was an error while processing request.</h2><br><br>\n");
        htmlContent.append("<b>Error Code:<b>&nbsp;&nbsp;" + response.errorResponse.errorCode);
        htmlContent.append("<b>Error Description:<b>&nbsp;&nbsp;"
                + i18nControl.getMessage(response.errorResponse.errorDescriptionKey));

        htmlContent.append("</body></html>");

        this.editorPane.setText(htmlContent.toString());
    }


    public void loadData(UpdateNotesDTO updateNotesDTO)
    {
        StringBuilder htmlContent = new StringBuilder();

        htmlContent.append("<html>");
        htmlContent.append("<head>");
        htmlContent.append("<title>News</title>");
        htmlContent.append("<style type=\"text/css\">");

        htmlContent.append("body {");
        htmlContent.append("   FONT-FAMILY: Arial, helvetica, sans-serif;");
        htmlContent.append("   font-size: small;");
        htmlContent.append("}");
        htmlContent.append("td {");
        htmlContent.append("   FONT-FAMILY: Arial, helvetica, sans-serif;");
        htmlContent.append("   border-color: black;");
        htmlContent.append("   border-left-width: 1px;");
        htmlContent.append("   border-left-style: solid;");
        htmlContent.append("   border-right-width: 1px;");
        htmlContent.append("   border-right-style: solid;");
        htmlContent.append("}");
        htmlContent.append("tr.newsheader {");
        htmlContent.append("   background-color: #b2c1ff;");
        htmlContent.append("   border-width: 1px;");
        // htmlContent.append("   border-style: solid;");
        htmlContent.append("   border-color: black;");
        // htmlContent.append("   border-left-width: 4px;");
        htmlContent.append("   border-left-style: solid;");
        // htmlContent.append("   border-right-width: 2px;");
        htmlContent.append("   border-right-style: solid;");
        // htmlContent.append("   border-left-color: black;");

        htmlContent.append("}");

        htmlContent.append("table.container {");
        htmlContent.append("   background-color: #ffffff;");
        htmlContent.append("   border-width: 1px;");
        htmlContent.append("   border-style: solid;");
        htmlContent.append("   border-color: black;");
        htmlContent.append("}");
        htmlContent.append("td.menu {");
        htmlContent.append("   border-color: #000000;");
        htmlContent.append("   border-top-width: 1px;");
        htmlContent.append("   border-top-style: solid;");
        htmlContent.append("   border-bottom-width: 1px;");
        htmlContent.append("   border-bottom-style: solid;");
        htmlContent.append("   background-color: #EBEBEB;");
        htmlContent.append("}");

        htmlContent.append("a.menu {");
        htmlContent.append("   font-weight: bold;");
        htmlContent.append("   color: #000000;");
        htmlContent.append("}");

        htmlContent.append("a:hover {");
        htmlContent.append("   color: #676767;");
        htmlContent.append("   text-decoration: none;");
        htmlContent.append("}");

        htmlContent.append("</style>");
        htmlContent.append("</head>");
        htmlContent.append("<body>");

        for (ApplicationNoteDTO note : updateNotesDTO.notes)
        {
            ATechDate atdate = new ATechDate(ATechDate.FORMAT_DATE_ONLY, note.dtInfo);

            htmlContent
                    .append("<table cellpadding=\"3\" cellspacing=\"0\" align=\"center\" width=\"100%\" class=\"container\">");
            htmlContent.append("<tr class=\"newsheader\">");
            htmlContent.append("<td width=\"20%\">" + this.sdf.format(atdate.getGregorianCalendar().getTime())
                    + "</td>");
            htmlContent.append("<td><b>" + note.subject + "</b></td>");
            htmlContent.append("</tr>");
            htmlContent.append("<tr>");
            htmlContent.append("<td colspan=\"2\">");
            htmlContent.append(note.note);

            htmlContent.append("</td>");
            htmlContent.append("</tr>");
            htmlContent.append("</table>");
            htmlContent.append("<br><hr><br>");
        }

        htmlContent.append("</body>");
        htmlContent.append("</html>");

        this.editorPane.setText(htmlContent.toString());
        this.editorPane.select(1, 1);
    }


    public void actionPerformed(ActionEvent e)
    {
        String action = e.getActionCommand();

        if (action.equals("close"))
        {
            this.dataAccess.removeComponent(this);
            this.dispose();
        }
    }
}
