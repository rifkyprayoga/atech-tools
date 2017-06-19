
import java.io.*;
import java.sql.*;

public class QuotesParser
{

    private Connection conn;
    private Statement stmt;
    private ResultSet rs;


    int dolzina[] = new int[300000];
    int par=0;
    int max=0;

    RandomAccessFile raf;
    //RandomAccessFile rwrite;

    FileOutputStream rwrite;


    QuotesParser()
    {

        openFile();
//        connect_db();
        readQuotes();
        closeFile();
//        disconnect_db();


    }



    private boolean connect_db()
    {
		
	try 
        {
            //Class.forName("org.postgresql.Driver");
            //conn = DriverManager.getConnection("jdbc:postgresql://www.atechnet.dhs.org:5432/fan-fiction", "postgres", "pgsql");
            
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            conn = DriverManager.getConnection("jdbc:odbc:Quotes");
            
            stmt = conn.createStatement();


/*
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                        ResultSet.CONCUR_UPDATABLE);
            //stmt.setFetchSize(25);

            rs = stmt.executeQuery("SELECT ID,Quote,Author,Language FROM Quotes");
*/
	} 
        catch(java.lang.ClassNotFoundException e) 
        {
	    System.err.println("Class not found exception: " + e.getMessage());
            return false;
	}
        catch(SQLException ex) 
        {
	    System.err.println("SQLException on Connection: " + ex.getMessage());
            return false;
	}
        return true;
    }



    private boolean disconnect_db()
    {
        try 
        {
            rs.close();
            stmt.close();
            conn.close();
        } 
        catch(SQLException ex) 
        {
      	    System.err.println("SQL Exception on Close: " + ex.getMessage());
            return false;
	}
        return true;
       
    }






    private void openFile()
    {
        try
        {
           // ---
           // ---  Open default file for writing
           // ---
           raf = new RandomAccessFile("quotes.txt", "r");

           //rwrite = new RandomAccessFile("qu.txt", "rw");

           rwrite = new FileOutputStream(new File("qu2.txt"));

        }
        catch (IOException e)
        {
           System.out.println("File could not be opened for writing");
           System.exit(1);
        }
    }



    private void closeFile()
    {

        try {
            raf.close(); 
            rwrite.close();
        }
        catch (IOException e)
        {
           System.out.println("File closed.");
           System.exit(1);
        }
    }



    public void readQuotes()
    {
        
        try
        {
        
            String str;
            String quotes[] = new String[20];

            boolean prev = false;
            int i=0;
                                   
            while ((str=raf.readLine())!=null)
            {
                
                if ((str.indexOf(".")<5) && (str.indexOf(".")!=-1))
                {
                    if (prev) 
                    {
                        saveInBase(quotes,i+1);
                        prev=false;
                    }
                    i=0;
                    prev=true;
                }
                else
                    i++;
                
                quotes[i]=str;
            }
            saveInBase(quotes,i+1);
        }
        catch(IOException e2) 
        {
           System.out.println("Error reading to file!");
           System.exit(1);
        }
    }



    public void saveInBase(String str[], int in)
    {


        int ss=0;
        String total="";
        String author="";
        String tmp="";

        boolean qend= false;
        
        while (ss<in) 
        {
            System.out.println(str[ss]);
            
            if (ss==0) 
            {
                
                // first line
                
                int som = str[ss].indexOf(".");
                total = str[ss].substring(som+2, str[ss].length());
                //System.out.println("Total 1st:"+total);
                
                int tm = total.indexOf("\"");
                tmp = total.substring(tm+1,total.length());
                
                if (tmp.indexOf("\"")>-1)
                    qend=true;
                
                //System.out.println("QEND: "+qend);

                ss++;
                continue;
            }
            
            // all next line
            
            if (!qend) 
            {

                // quote hasn't ended

                if (str[ss].indexOf("\"")>-1)
                {
                    qend=true;
                }

                total = total+" "+str[ss];

            }
            else
            {

                // quote has ended
                // now we parse and delete submitted data and retain authors
                
                if (str[ss].indexOf("Submitted")==-1)   // submitted last line
                {
                    author=str[ss];
                }
                else
                    break;
            }



/*
            if (!qend) 
            {
                if (str[ss].indexOf(".")<5)    // first line
                {
                    int som = str[ss].indexOf(".");
                    total = str[ss].substring(som+2, str[ss].length());

                    //System.out.println("Total 1st:"+total);

                    int tm = total.indexOf("\"");
                    tmp = total.substring(tm+1,total.length());

                    //total = tmp;

                    if (tmp.indexOf("\"")!=-1) 
                    {
                        //qend=true;
                        ss++;
                        
                        if (ss<in) 
                        {
                            if (str[ss].indexOf("Submitted")==-1)   // submitted last line
                            {
                                author=str[ss];
                            //    System.out.println(author);
                            }
                            else
                                break;
                            
                            
                            //break;
                        }
                                                                          
                        //System.out.println("qEND");
                    }


                }
                else // in beetween
                {
                    total = total+" "+str[ss];
                    total += str[ss];
                }
            }
            else     // if we have only quotes left
            {
                if (str[ss].indexOf("Submitted")==-1)   // submitted last line
                {
                    author=str[ss];
                //    System.out.println(author);
                }
            
            }
*/            
            
            ss++;
        }

        //System.out.println("Q:"+total);
        //System.out.println("A:"+author+"\n");

        //System.out.println(total.length());


        par++;                 
        addInDb(par, total, author, 1);
        //dolzina[par]=total.length();
        //par++;



    }


    private void addInDb(int num, String quote, String author, int lang) 
          //          throws Exception
    {
        
        try
        {

            if (author.equals("")) 
            {
                author=" ";
            }
            
            //rwrite.writeInt(num);


            String output = (new Integer(num)).toString()+"|"+quote+"|"+
                            author+"|"+(new Integer(lang)).toString()+"\n";


            rwrite.write(output.getBytes());

            //rwrite.write(((new Integer(num)).toString()).getBytes());
            //rwrite.writeChar('|');
            //rwrite.write(quote.getBytes());
            //rwrite.writeChar('|');
            //rwrite.write(author.getBytes());
            //rwrite.writeChar('|');
            //rwrite.write(((new Integer(lang)).toString()).getBytes());
            
            
               //rwrite.writeInt(lang);
            
            //rwrite.writeChar('\n');
            


//            rwrite.writeChars(num+"|"+quote+"|"+author+"|"+lang+"\n");

            //System.out.println(rs);
            /*
            //rs.moveToInsertRow();
            //rs.updateLong(0, num);
            //rs.updateString(1, quote);
            rs.updateString(2, author);
            rs.updateLong(3,lang);
            rs.insertRow();
            */
            //stmt.executeUpdate("INSERT INTO Quotes VALUES ("+num+
            //               ",'"+quote+"','"+author+"',"+lang+")");
    
        }
        catch (Exception ex)
        {
            System.out.println("Error writing:"+ex);
            System.exit(1);
            //    System.exit(1);
        }
        
        
    }




    public void result()
    {

        for (int i=0; i<par-1;i++) 
        {
            if (dolzina[par]>max)
            {
                max=dolzina[par];
            }

        }

        System.out.println("Maksimalno stevilo: "+max);



    }




    public static void main(String args[])
    {
        QuotesParser qp = new QuotesParser();
        qp.result();
    }



}                                           
