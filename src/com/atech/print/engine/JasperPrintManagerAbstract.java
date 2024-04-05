package com.atech.print.engine;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  This file is part of ATech Tools library.
 *  
 *  JasperPrintManagerAbstract - Abstract Print Manager for Jasper Reports
 *  Copyright (C) 2010  Andy (Aleksander) Rozman (Atech-Software)
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

    private static final Logger LOG = LoggerFactory.getLogger(JasperPrintManagerAbstract.class);


    public abstract String getBaseDir();


    public abstract String getSubReportDir();


    public void startJasperPrint(String report_name,
                                 Map<String, String> parameters,
                                 JRBeanCollectionDataSource collection) {
        startJasperPrint(report_name, null, parameters, collection);
    }


    public void startJasperPrint(String reportName, String subReports,
                                 Map<String, String> parameters,
                                 JRDataSource collection) {
        Map<String, Object> objectMap = parameters.entrySet()
                .stream().collect(Collectors.toMap(
                        String::valueOf,
                        v-> v));
        try
        {
            String baseDir = this.getBaseDir();

            parameters.put("SUBREPORT_DIR", this.getSubReportDir());

            checkIfReportCompiled(baseDir, reportName);

            checkIfSubreportsCompiled(this.getSubReportDir(), subReports);

            String res = JasperFillManager.fillReportToFile(baseDir + reportName + ".jasper", objectMap, collection);
            LOG.debug("Jasper report filled with data and \nJasper .jrprint file created in " + baseDir
                    + ". Return data: " + res);

            File sourceFile = new File(baseDir + reportName + ".jrprint");
            JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);

            JasperViewer.viewReport(jasperPrint, false);

        }
        catch (Exception ex)
        {
            LOG.error("Error printing: " + ex, ex);
        }

    }


    public void checkIfSubreportsCompiled(String subReportsDirectory, String subReports)
    {
        if (StringUtils.isBlank(subReports))
            return;

        StringTokenizer strtok = new StringTokenizer(subReports, ",");

        while (strtok.hasMoreTokens())
        {
            checkIfReportCompiled(subReportsDirectory, strtok.nextToken().trim());
        }
    }


    public void checkIfReportCompiled(String base_dir, String report_name)
    {
        boolean reCreate = false;

        File jasperFile = new File(base_dir + report_name + ".jasper");
        File jrxmlFile = new File(base_dir + report_name + ".jrxml");

        if (!jasperFile.exists())
        {
            reCreate = true;
        }
        else
        {
            if (jrxmlFile.lastModified() > jasperFile.lastModified())
            {
                reCreate = true;
            }
        }

        if (reCreate)
        {
            try
            {
                JasperCompileManager.compileReportToFile(base_dir + report_name + ".jrxml",
                    base_dir + report_name + ".jasper");
            }
            catch (Exception ex)
            {
                LOG.error("Error re/compiling report: " + ex, ex);
            }
        }
    }

}
