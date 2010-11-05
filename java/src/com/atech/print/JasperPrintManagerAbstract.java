package com.atech.print;

import java.io.File;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public abstract class JasperPrintManagerAbstract
{
    
    
    
    public abstract String getBaseDir();
    
    public abstract String getSubReportDir();
    
    
    
    public void startJasperPrint(String report_name, HashMap parameters, JRBeanCollectionDataSource collection)
    {
        try
        {
            //String base_dir = "X:/JasperReports/";
            
            String base_dir = this.getBaseDir();
            
            //WorkHours.jrxml
            //Map parameters_full = new HashMap();
            //parameters.put("SUBREPORT_DIR", "X:/JasperReports/");
            
            parameters.put("SUBREPORT_DIR", this.getSubReportDir());
            
            
            if (!new File(base_dir + report_name + ".jasper").exists())
            {
                JasperCompileManager.compileReportToFile(
                    base_dir + report_name + ".jrxml",
                    base_dir + report_name + ".jasper");
                
                
                
                //JasperDesign jasperDesign = JasperManager.loadXmlDesign(base_dir + report_name + ".jrxml");
                //JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
                
            }
            
            
            
            //file the .jasper file with data from the data source
            //JasperDesign jasperDesign = JasperManager.loadXmlDesign(base_dir + report_name + ".jrxml");
            //JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
            
            //JasperFillManager.fillReportToFile(jasperReport, base_dir + report_name + ".jrprint", parameters, collection);
            
            JasperFillManager.fillReportToFile(base_dir + report_name + ".jasper", parameters, collection);
            
            
            
//            JasperFillManager.fillReportToFile(jasperReport, parameters, collection);
            
            System.out.println( "Jasper report filled with data and \nJasper .jrprint file created in " + base_dir );
        
            
//            JasperExportManager.exportReportToPdfFile( base_dir + report_name + ".jrprint");
//            System.out.println( "PDF file created using .jrprint Jasper file");
            
            
            
            //JasperPrint jasperPrint = new JasperPrint();
            //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, collection);
            
            
            File sourceFile = new File(base_dir + report_name + ".jrprint");
            
            JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);
    
            
/*            
            File destFile = new File(sourceFile.getParent(), jasperPrint.getName() + ".rtf");
            
            JRRtfExporter pdfExporter = new JRRtfExporter();
            
            pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            pdfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
            
            pdfExporter.exportReport();
            
            System.out.println( "RTF file created using .jrprint Jasper file");
*/            
            
            
            JasperViewer.viewReport(jasperPrint); 
        
        
        }
        catch(Exception ex)
        {
            System.out.println("Error printing: " + ex);
            ex.printStackTrace();
        }
    
    }
    
    
    
    
    
    
    
    
}
