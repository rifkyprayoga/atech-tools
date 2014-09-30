package com.atech.print.engine;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATDataAccessAbstract;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * This file is part of ATech Tools library.
 * <one line to give the library's name and a brief idea of what it does.>
 * Copyright (C) 2007 Andy (Aleksander) Rozman (Atech-Software)
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * For additional information about this project please visit our project site
 * on
 * http://atech-tools.sourceforge.net/ or contact us via this emails:
 * andyrozman@users.sourceforge.net or andy@atech-software.com
 *
 * @author Andy
 */

/**
 * Filename: PrintAbstract
 * Description: Abstract class for printing via creation of PDF (iText)
 * Author: andyrozman {andy@atech-software.com}
 */

public abstract class PrintAbstractIText extends PdfPageEventHelper
{
    private static final Log LOG = LogFactory.getLog(PrintAbstractIText.class);

    protected ATDataAccessAbstract dataAccess = null;
    protected I18nControlAbstract i18nControl = null;
    String name = "";

    protected BaseFont baseFontHelvetica = null;
    protected BaseFont baseFontTimes = null;
    protected Font textFontNormal = null;
    protected Font textFontBold = null;
    protected Font textFontItalic = null;

    protected ITextDocumentPrintSettings documentSettings;

    /**
     * Print root must always contain trailing slash, so ../data/print/ is
     * correct, while ../data/print is incorrect.
     * It should be stored under GGC main structure, so that parent of last
     * directory (in this case data) already
     * exists. And of course unix path divider must be used. (/ instead of \ on
     * windows)
     */
    private static String printRoot = "../data/print/";

    /**
     * Constructor
     *
     * @param dataAccess
     * @param doInit
     */
    public PrintAbstractIText(ATDataAccessAbstract dataAccess, boolean doInit)
    {
        this.dataAccess = dataAccess;
        this.i18nControl = dataAccess.getI18nControlInstance();

        if (doInit)
        {
            init();
        }
    }

    /**
     * Constructor
     *
     * @param i18nControl
     * @param doInit
     */
    public PrintAbstractIText(I18nControlAbstract i18nControl, boolean doInit)
    {
        this.i18nControl = i18nControl;

        if (doInit)
        {
            init();
        }
    }

    public PrintAbstractIText(ATDataAccessAbstract dataAccess, boolean doInit, PrintParameters parameters)
    {
        this.i18nControl = i18nControl;

        if (doInit)
        {
            initData();
            init();
        }
    }

    public void initData()
    {
    }

    protected void init()
    {
        createName();

        try
        {
            baseFontHelvetica = BaseFont.createFont("Helvetica", BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
            baseFontTimes = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
            textFontNormal = new Font(this.baseFontHelvetica, getTextSize(), Font.NORMAL);
            textFontBold = new Font(this.baseFontHelvetica, getTextSize(), Font.BOLD);
            textFontItalic = new Font(this.baseFontHelvetica, getTextSize(), Font.ITALIC);
        }
        catch (Exception ex)
        {
            System.out.println("Exception on font create: " + ex);
        }

        createDocument();
    }

    protected Phrase createBoldTextPhrase(String text)
    {
        return new Phrase(this.i18nControl.getMessage(text), this.textFontBold);
    }

    protected Phrase createNormalTextPhrase(String text)
    {
        return new Phrase(this.i18nControl.getMessage(text), this.textFontNormal);
    }

    protected Phrase createEmptyTextPhrase()
    {
        return new Phrase("", this.textFontNormal);
    }

    protected Phrase createItalicTextPhrase(String text)
    {
        return new Phrase(this.i18nControl.getMessage(text), this.textFontItalic);
    }

    /**
     * Get Text Size
     *
     * @return
     */
    public abstract int getTextSize();

    /**
     * Get Name
     *
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns Name of report with Full Path
     *
     * @return
     */
    public String getNameWithPath()
    {
        File f = new File(printRoot + getName());
        return f.getAbsolutePath();
    }

    /**
     * Returns Name of report with Full Path
     *
     * @return
     */
    public String getRelativeNameWithPath()
    {
        return printRoot + getName();
    }

    /**
     * Returns report name as File instance
     *
     * @return
     */
    public File getNameFile()
    {
        File f = new File(printRoot + getName());
        return f;
    }

    /**
     * Create Name
     */
    public void createName()
    {
        checkIfRootExists();

        this.name = this.getFileNameBase() + "_" + this.getFileNameRange() + "_";

        for (int i = 1; i < Integer.MAX_VALUE; i++)
        {
            File f = new File(PrintAbstractIText.printRoot + this.name + i + ".pdf");

            if (!f.exists())
            {
                name += i + ".pdf";
                break;
            }
        }

    }

    /**
     * Get Title
     *
     * @return
     */
    public abstract Paragraph getTitle();

    public void discoverDocumentSettings()
    {
        ITextDocumentPrintSettings ds = getCustomDocumentSettings();

        if (ds == null)
        {
            this.documentSettings = new ITextDocumentPrintSettings();
        }
        else
        {
            this.documentSettings = ds;
        }
    }

    public abstract ITextDocumentPrintSettings getCustomDocumentSettings();

    /**
     * Create Document
     */
    public void createDocument()
    {
        this.discoverDocumentSettings();

        // step1
        File fl = new File(PrintAbstractIText.printRoot + this.getName());
        Document document = new Document(documentSettings.getPageSize(), //
                documentSettings.getLeftMargin(), //
                documentSettings.getRightMargin(), //
                documentSettings.getTopMargin(), //
                documentSettings.getBottomMargin()); // size and margins L R T B

        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(fl.getAbsoluteFile());

            // step2 - asign pdf to file
            PdfWriter writer = PdfWriter.getInstance(document, fos);

            // step3 - set page event, open document and put on title
            writer.setPageEvent(this);

            document.open();
            document.add(getTitle());

            // step4 - fill body of document
            fillDocumentBody(document);

            // step5
            document.close();

        }
        catch (Exception de)
        {
            LOG.error("Error on document creation [" + de.getMessage() + "]: " + de, de);
            de.printStackTrace();
        }
        finally
        {
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (Exception ex)
                {}
            }
        }

    }

    protected void setBackground(int element_cnt, PdfPTable table)
    {
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_BASELINE);

        if (element_cnt % 2 == 1)
        {
            table.getDefaultCell().setGrayFill(0.9f);
        }
        else
        {
            table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        }

    }

    private void checkIfRootExists()
    {
        File f = new File(printRoot);

        if (!f.exists())
        {
            try
            {
                f.mkdir();
            }
            catch (Exception ex)
            {
                LOG.error("Error creating new print directory ! [" + PrintAbstractIText.printRoot + "]. Ex: " + ex, ex);
            }
        }
    }

    /**
     * On End Page
     *
     * @see com.lowagie.text.pdf.PdfPageEventHelper#onEndPage(com.lowagie.text.pdf.PdfWriter,
     *      com.lowagie.text.Document)
     */
    @Override
    public void onEndPage(PdfWriter writer, Document document)
    {
        try
        {
            Rectangle page = document.getPageSize();
            PdfPTable foot = new PdfPTable(new float[] { 90.0f, 10.0f });

            PdfPCell pc = new PdfPCell();
            pc.setBorderColor(BaseColor.WHITE);

            Font f = new Font(this.baseFontTimes, 10, Font.ITALIC | Font.BOLD); // this.base_times

            pc.setPhrase(new Phrase(new Chunk(i18nControl.getMessage("REPORT_FOOTER"), f)));
            pc.setHorizontalAlignment(Element.ALIGN_CENTER);
            foot.addCell(pc);

            PdfPCell pages = new PdfPCell();
            pages.setBorderColor(BaseColor.WHITE);
            pages.setPhrase(new Phrase(new Chunk(i18nControl.getMessage("PAGE") + " " + document.getPageNumber(),
                    textFontItalic)));
            pages.setHorizontalAlignment(Element.ALIGN_RIGHT);

            foot.addCell(pages);

            foot.setTotalWidth(page.getWidth() - document.leftMargin() - document.rightMargin());
            foot.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(), writer.getDirectContent());
        }
        catch (Exception e)
        {
            throw new ExceptionConverter(e);
        }
    }

    /**
     * Create document body.
     *
     * @param document
     * @throws Exception
     */
    public abstract void fillDocumentBody(Document document) throws Exception;

    /**
     * Returns base filename for printing job, this is just part of end filename
     * (starting part)
     *
     * @return
     */
    public abstract String getFileNameBase();

    /**
     * Returns data part of filename for printing job, showing which data is
     * being printed
     *
     * @return
     */
    public abstract String getFileNameRange();

}
