package com.atech.update.client.dev;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.atech.update.client.action.ActionThread;
import com.atech.update.client.action.BinaryDownloadThread;
import com.atech.update.client.data.UpdateComponentEntry;
import com.atech.update.client.data.UpdateSettings;
import com.atech.update.client.panel.UpdateProgressPanel;
import com.atech.update.client.panel.UpdateProgressPanelAbstract;
import com.atech.utils.file.CheckSumUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class DownloadFileClient.
 */
public class DownloadFileClient extends JFrame implements ActionListener
{
    
    private static final long serialVersionUID = 5524546247397085042L;

    /** The panel. */
    JPanel panel;
    
    /** The list. */
    JList list;
    
    /** The threads. */
    ArrayList<ActionThread> threads = new ArrayList<ActionThread>();
    
    
    /**
     * Instantiates a new download file client.
     */
    public DownloadFileClient()
    {
        super();
        initGUI();
        doAction();
        this.setVisible(true);
    }
    

    /**
     * Inits the gui.
     */
    private void initGUI()
    {
        getContentPane().setLayout(null);
        
        panel = new JPanel();
        panel.setBounds(0,0, 600, 500);
        panel.setLayout(null);
        getContentPane().add(panel, null);
        
        JLabel label = new JLabel("New label");
        label.setBounds(83, 44, 70, 15);
        panel.add(label);

        
        list = new JList();
        
        JScrollPane pane = new JScrollPane(list);
        pane.setBounds(45, 102, 500, 350);
        panel.add(pane);
        
        
        
        //pane.setViewportView(list);
        
        JButton button = new JButton("New button");
        button.setBounds(319, 26, 117, 25);
        button.addActionListener(this);
        panel.add(button);

        
        
        
        
        
        //getContentPane().add(list, null);
        
        this.setSize(600, 500);
    }
    
    
    /**
     * Do action.
     */
    public void doAction()
    {
        UpdateComponentEntry uce = new UpdateComponentEntry();
        uce.action = 1;
        uce.estimated_crc = 1889989348;
        uce.file_id = 1;
        uce.requested_version_id = 1;
        uce.output_file = "img_2345.jpg";
        

        
        UpdateProgressPanelAbstract p = new UpdateProgressPanel();
        //p.setBounds(0, 0, 450, 100);
        //panel.add(p);
        
        this.list.add((JPanel)p);
        
        this.threads.add(new BinaryDownloadThread(new UpdateSettings(), uce, p));
        
        
        
    }
    
    
    
    /**
     * Do actionxxx.
     */
    public void doActionxxx()
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
            
            
            //OutputStreamWriter osr = new OutputStreamWriter(new FileOutputStream("/home/andy/test.jpg"));
            RandomAccessFile raf = new RandomAccessFile("/home/andy/test.jpg", "rw");
            
            /*
            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());
            */
            
           ArrayList<Integer> list = new ArrayList<Integer>();
           
           float size=671200;
           long current_size = 0;
               
           System.out.println("File size: " + is.available());
           
           byte[] array = new byte[1024];
           
           while (is.available()>0) //; r.ready())
           {
               if (is.available()<1024)
               {
                   array = new byte[is.available()];
               }

               is.read(array);
               raf.write(array);
               current_size +=array.length;
               
               
               //list.add(isr.read());
               //osr.write(is.read());
               //raf.write(is.read());
               
               
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
    
    
    /**
     * The main method.
     * 
     * @param args
     *            the arguments
     */
    public static void main(String[] args)
    {
        new DownloadFileClient();
    }


    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0)
    {
        this.threads.get(0).start();
        
        
        // TODO Auto-generated method stub
        
    }
}
