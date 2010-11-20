package com.atech.update.client.action;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;

import com.atech.update.client.data.UpdateComponentEntry;
import com.atech.update.client.data.UpdateSettings;
import com.atech.update.client.panel.UpdateProgressPanelAbstract;
import com.atech.utils.file.CheckSumUtility;

public class BinaryDownloadThread extends ActionThread
{
    
    UpdateSettings upd_settings = null;
    UpdateComponentEntry upd_entry = null;
    UpdateProgressPanelAbstract upd_progress = null;
    int BLOCK_SIZE = 512;
    
    public BinaryDownloadThread(UpdateSettings sett, UpdateComponentEntry uce, UpdateProgressPanelAbstract upd_prg)
    {
        this.upd_settings = sett;
        this.upd_entry = uce;
        this.upd_progress = upd_prg;
        this.upd_progress.setBaseStatus("Downloading Atech-Tools");
    }
    
    
    
    public void run()
    {
        try
        {
            //StringBuffer sb = new StringBuffer(this.upd_settings.update_server);
            //sb.append(this.upd_entry.getFileCommand());
                
            URL url = new URL(this.upd_settings.update_server + this.upd_entry.getFileCommand()); 

            InputStream is = url.openStream();
            
            
            if (!(new File(this.upd_settings.output_path)).exists())
            {
                (new File(this.upd_settings.output_path)).mkdirs();
            }
            
            
//            OutputStreamWriter osr = new OutputStreamWriter(new FileOutputStream(this.upd_settings.output_path + this.upd_entry.archive_file));
            RandomAccessFile raf = new RandomAccessFile(this.upd_settings.output_path + "tempFile.bin", "rw");
            
            /*
            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());
            */
            
//           ArrayList<Integer> list = new ArrayList<Integer>();
           
           float size=671200;
           long current_size = 0;
               
           System.out.println("File size: " + size);
           
           
           this.upd_progress.setStatus("Download file");
           
           byte[] array = new byte[BLOCK_SIZE];
           
           while (is.available()>0) //; r.ready())
           {
               if (is.available()<BLOCK_SIZE)
               {
                   array = new byte[is.available()];
               }
               else
               {
                   if (array.length < BLOCK_SIZE)
                   {
                       array = new byte[BLOCK_SIZE];
                   }
               }
               
               
               

               is.read(array);
               raf.write(array);
               current_size +=array.length;

               System.out.println("Av: " + is.available());
               
               //list.add(isr.read());
               //osr.write(is.read());
               //raf.write(is.read());
               
               
               System.out.println("Progress: " + ((current_size/size)*100));
               
               int prg = (int)((current_size/size)*100);
               
               this.upd_progress.setProgress(prg);
               
           }
           
//           System.out.println("Size Arr: " + list.size());
/*              
           File f = new File("/home/andy/test.jpg");
           
           FileWriter bfw = new FileWriter(f);
           
           
           for(int i=0; i<list.size(); i++)
           {
               bfw.write(list.get(i));
           }
           
            System.out.println("Write file");
  */          
           
           this.upd_progress.setStatus("Checking file");
           
           CheckSumUtility csu = new CheckSumUtility();
           
           long check = csu.getChecksumValue(this.upd_settings.output_path + "tempFile.bin");
           
           if (check!=this.upd_entry.estimated_crc)
           {
               this.upd_progress.setStatus("Checksum failed.");
           }
           else
               this.upd_progress.setStatus("File downloaded successfully.");

           
           File f = new File(this.upd_settings.output_path + "tempFile.bin");
           f.renameTo(new File(this.upd_settings.output_path + this.upd_entry.output_file));
           
           //System.out.println("Checksum: " + csu.getChecksumValue(this.upd_settings.output_path + "tempFile.bin"));
           
           
           
           
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        
        
        
        
    }



    public void setStatus(String status)
    {
        // TODO Auto-generated method stub
        
    }



    public void setProgress(int proc)
    {
        // TODO Auto-generated method stub
        
    }
    
    

}
