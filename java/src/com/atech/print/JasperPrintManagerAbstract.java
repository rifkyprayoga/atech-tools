package com.atech.print;

import java.io.File;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
 *  Copyright (C) 2007  Andy (Aleksander) Rozman (Atech-Software)
 *  
 *  
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 *  
 *  
 *  For additional information about this project please visit our project site on 
 *  http://atech-tools.sourceforge.net/ or contact us via this emails: 
 *  andyrozman@users.sourceforge.net or andy@atech-software.com
 *  
 *  @author Andy
 *
*/


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
            
            boolean recreate = false;
            
            
            File f1 = new File(base_dir + report_name + ".jasper");
            File f2 = new File(base_dir + report_name + ".jrxml");
            
            
            if (!f1.exists())
            {
                recreate = true;
            }
            else
            {
                if (f2.lastModified() > f1.lastModified())
                    recreate = true;
            }
            

            if (recreate)
            {
                JasperCompileManager.compileReportToFile(
                    base_dir + report_name + ".jrxml",
                    base_dir + report_name + ".jasper");
            }
            
            
            
            //file the .jasper file with data from the data source
            //JasperDesign jasperDesign = JasperManager.loadXmlDesign(base_dir + report_name + ".jrxml");
            //JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
            
            //JasperFillManager.fillReportToFile(jasperReport, base_dir + report_name + ".jrprint", parameters, collection);
            
            String res = JasperFillManager.fillReportToFile(base_dir + report_name + ".jasper", parameters, collection);
            
            
            
//            JasperFillManager.fillReportToFile(jasperReport, parameters, collection);
            
            System.out.println( "Jasper report filled with data and \nJasper .jrprint file created in " + base_dir + " Return data: " + res );
        
            
//            JasperExportManager.exportReportToPdfFile( base_dir + report_name + ".jrprint");
//            System.out.println( "PDF file created using .jrprint Jasper file");
            
            
            
            //JasperPrint jasperPrint = new JasperPrint();
            //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, collection);

            File f = new File(base_dir);
            System.out.println("Path: " + f.getAbsolutePath());
            
            
            
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
            
            
            JasperViewer.viewReport(jasperPrint, false); 
        
        
        }
        catch(Exception ex)
        {
            System.out.println("Error printing: " + ex);
            ex.printStackTrace();
        }
    
    }
    
    
    
    
    
    
    
    
}
