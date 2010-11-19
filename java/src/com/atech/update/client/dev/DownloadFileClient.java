package com.atech.update.client.dev;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.atech.utils.file.CheckSumUtility;

public class DownloadFileClient extends JFrame
{
    
    public DownloadFileClient()
    {
        super();
        
        //initGUI();
        doAction();
    }
    

    private void initGUI()
    {
        getContentPane().setLayout(null);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        getContentPane().add(panel, null);
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setBounds(96, 98, 266, 23);
        getContentPane().add(progressBar);
        
        JLabel label = new JLabel("New label");
        label.setBounds(83, 44, 70, 15);
        getContentPane().add(label);
        
        JLabel label_1 = new JLabel("New label");
        label_1.setBounds(83, 162, 70, 15);
        getContentPane().add(label_1);
    }
    
    
    public void doAction()
    {
        
        try
        {
            //System.out.println("app: " + m_da.getAppName() + ",version=" + m_da.getCurrentVersion() + ",db_version=" + m_da.getCurrentDbVersion());
            
            System.out.println("app: ggc");
            
            String server_name = "http://192.168.4.3:8080/";
            
            
            
            // check server name
            server_name = server_name.trim();
            
            if (server_name.length()==0)
            {
                server_name = "http://www.atech-software.com/";
            }
            else
            {
                if (!server_name.startsWith("http://"))
                    server_name = "http://" + server_name;
                
                if (!server_name.endsWith("/"))
                    server_name = server_name + "/";
            }
            
            
                
            
            
            // FIXME
            URL url = new URL(server_name + "ATechUpdateGetFile?" + "" +
                    "file_id=1"+ "&" +
                    "version_requested=1");



            
            //HTMLDocument tt = new HTMLDocument();

            //BufferedReader in = new BufferedReader(new InputStreamReader(
            //                    url.openStream()));

            //InputStreamReader isr = new InputStreamReader(
            //                    url.openStream());
            
            InputStream is = url.openStream();
            
            
            OutputStreamWriter osr = new OutputStreamWriter(new FileOutputStream("/home/andy/test.jpg"));
            RandomAccessFile raf = new RandomAccessFile("/home/andy/test.jpg", "rw");
            
            /*
            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());
            */
            
           ArrayList<Integer> list = new ArrayList<Integer>();
           
           float size=671200;
           long current_size = 0;
               
           
           while (is.available()>0) //; r.ready())
           {
               //list.add(isr.read());
               //osr.write(is.read());
               raf.write(is.read());
               
               current_size ++;
               
               System.out.println("Progress: " + ((current_size/size)*100));
           }
           
           System.out.println("Size Arr: " + list.size());
/*              
           File f = new File("/home/andy/test.jpg");
           
           FileWriter bfw = new FileWriter(f);
           
           
           for(int i=0; i<list.size(); i++)
           {
               bfw.write(list.get(i));
           }
           
            System.out.println("Write file");
  */          
           
           CheckSumUtility csu = new CheckSumUtility();
           
           System.out.println("Checksum: " + csu.getChecksumValue("/home/andy/test.jpg"));
           
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        
        
        
        
        
    }
    
    
    public static void main(String[] args)
    {
        new DownloadFileClient();
    }
    
    
}
