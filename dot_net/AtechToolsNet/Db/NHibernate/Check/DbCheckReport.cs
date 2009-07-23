
// TODO: Auto-generated Javadoc
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

using System;
using log4net;
namespace ATechTools.Db.NHibernate.Check
{

public class DbCheckReport
{

    
    ILog log = LogManager.GetLogger(typeof(DbCheckReport));

    private String version_db = "0";
    private int db_version = 0;
    private String version_db_required = "0";
    private int db_version_required = 0;
    
    
    private boolean info_read_ok = false;
    
    private String filename = "";
    private boolean file_exists = false;
    
    /**
     * The ic.
     */
    I18nControlAbstract ic;
    private boolean can_be_started = false;
    
    /**
     * Instantiates a new db check report.
     * 
     * @param filename the filename
     * @param ic the ic
     */
    public DbCheckReport(String filename, I18nControlAbstract ic)
    {
        this.ic = ic;
        this.filename = filename;
        readFileInfo();
        evaluateInfo();
    }
    
    /**
     * Read file info.
     */
    public void readFileInfo()
    {
        try
        {
            File f = new File(this.filename);
            
            //Console.WriteLine("exists: " + f.ex)
            
            this.file_exists = f.exists();
            
            if (!file_exists)
                return;
        
            BufferedReader br = new BufferedReader(new FileReader(this.filename));
            
            String line, res=null;
            
            while((line = br.readLine()) != null)
            {
                if (line.contains("|"))
                {
                    res = line;
                    break;
                }
                
            }
            
            br.close();
            
            StringTokenizer strtok = new StringTokenizer(res, "|");
            
            String stat = strtok.nextToken();
            this.version_db = strtok.nextToken();
            this.version_db_required = strtok.nextToken();
            
            if (stat.equals("OK"))
            {
                this.info_read_ok = true;
            }
            
        }
        catch(Exception ex)
        {
            log.error("Error reading report: " + ex, ex);
        }
        
    }
    
    
    /**
     * Evaluate info.
     */
    public void evaluateInfo()
    {
        try
        {
            this.db_version = Int32.parseInt(this.version_db);
            this.db_version_required = Int32.parseInt(this.version_db_required);
            
            if (!this.file_exists)
            {
                log.warn("Db Report file not found !");
                this.can_be_started = true;
            }
            else
            {
                // file exists
                if (this.db_version==this.db_version_required)
                    this.can_be_started = true;
                else
                    this.can_be_started = false;
            }

        }
        catch(Exception ex)
        {
            log.error("Evaluate info exception: " + ex, ex);
            ex.printStackTrace();
        }
    }
    
    
    
    
    /**
     * Can application start.
     * 
     * @return true, if successful
     */
    public boolean canApplicationStart()
    {
        return this.can_be_started;
    }
    

    /**
     * Show error.
     */
    public void showError()
    {
        String ver_desc = "";
        
        if (this.db_version < this.db_version_required)
        {
            // current version is lower
            ver_desc = "DB_VERSION_LOWER";
        }
        else
        {
            ver_desc = "DB_VERSION_HIGHER";
        }
        
        
        String s = String.format(ic.getMessage("DB_HEADER"), this.version_db, this.version_db_required, ic.getMessage(ver_desc));
        
        JOptionPane.showMessageDialog(null, s, ic.getMessage("DB_ERROR_ON_LOAD"), JOptionPane.ERROR_MESSAGE);
        
    }
    
}
}