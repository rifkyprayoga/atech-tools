package com.atech.print.engine;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.atech.i18n.I18nControlAbstract;
import com.atech.tools.pdf.IcePdfViewer;

public class PrintProcessor
{
    private I18nControlAbstract i18nControl = null;
    private PrintRequester printRequester = null;
    private static final Log LOG = LogFactory.getLog(PrintProcessor.class); 

    
    public PrintProcessor(I18nControlAbstract i18nControl, PrintRequester printRequester)
    {
        this.i18nControl = i18nControl;
        this.printRequester = printRequester;
    }
    
    
    public void displayPDF(String fileName) throws Exception
    {
        if ((this.printRequester.isExternalPdfViewerActivated()) && //
            (StringUtils.isNotBlank(this.printRequester.getExternalPdfViewer())))
        {
            this.displayPDFExternal(fileName);
        }
        else
        {
            this.displayPDFInternal(fileName);
        }
    }
    
    
    
    public void displayPDFInternal(String fileName) throws Exception
    {
        LOG.debug("displayPDFInternal: " + fileName);
        
        
        
        //System.out.println("Name: " + fileName);
        
        //File fl = new File(".." + File.separator + "data" + File.separator + "temp" + File.separator);
        //File file = new File(name);


        try
        {

            IcePdfViewer viewer = new IcePdfViewer(this.i18nControl.getSelectedLanguageLocale(), 
                !this.printRequester.disableLookAndFeelSettingForInternalPdfViewer());
            
            viewer.openWithFilename(fileName);
            
        }
        catch (RuntimeException ex)
        {
            pdfViewerRunError(ex, null);
        }
        catch (Exception ex)
        {
            pdfViewerRunError(ex, null);
        }
    }
    
    
    /**
     * Display PDF
     * 
     * @param name name must be full path to file name (not just name as it was in previous versions)
     * @throws Exception
     */
    public void displayPDFExternal(String name) throws Exception
    {
        LOG.debug("displayPDFExternal: " + name);
        
        String pdfViewer = this.printRequester.getExternalPdfViewer(); 
        
        if (StringUtils.isBlank(pdfViewer))
        {
            printerSettingsNotSetError();
        }

        File acr = new File(pdfViewer);
        if (!acr.exists())
        {
            printerSettingsNotSetError();
        }

        String execPath = "";

        try
        {
            String par = this.printRequester.getExternalPdfViewerParameters().trim();
            
            if (par.length()>0)
            {
                if (par.contains("%PDF_FILE%"))
                {
                    execPath = acr.getAbsoluteFile() + " " + par.replace("%PDF_FILE%", name);
                }
                else
                {
                    execPath = acr.getAbsoluteFile() + " " + par + " " + name;
                }
            }
            else
            {
                execPath = acr.getAbsoluteFile() + " " + name;
            }
            
            Runtime.getRuntime().exec(execPath);
        }
        catch (RuntimeException ex)
        {
            pdfViewerRunError(ex, execPath);
        }
        catch (Exception ex)
        {
            pdfViewerRunError(ex, execPath);
        }
    }


    private void pdfViewerRunError(Exception ex, String execPath) throws Exception
    {
        this.printRequester.setErrorMessages(this.i18nControl.getMessage("PDF_VIEVER_RUN_ERROR"), null);
        
        if (execPath!=null)
        {
            LOG.debug("Exec path for PdfViewer: " + execPath);
        }
        LOG.error(String.format("%s running AcrobatReader: %s", ex instanceof RuntimeException ? "RuntimeException" : "Error", ex), ex);
        throw ex;
    }


    private void printerSettingsNotSetError() throws Exception
    {
        this.printRequester.setErrorMessages(this.i18nControl.getMessage("PRINTING_SETTINGS_NOT_SET"), 
            this.i18nControl.getMessage("PRINTING_SETTINGS_NOT_SET_SOL"));
        LOG.error("Printer settings not set correctly. ");

        throw new Exception(this.i18nControl.getMessage("PRINTING_SETTINGS_NOT_SET"));
    }
    
    
    
}
