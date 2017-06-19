package com.atech.update.client.action;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;

import com.atech.update.client.data.UpdateComponentEntry;
import com.atech.update.client.data.UpdateSettings;
import com.atech.update.client.panel.UpdateProgressPanelAbstract;
import com.atech.utils.file.CheckSumUtility;

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

    boolean running = false;
    int retry = 0;
    int max_retry = 2;

    @Override
    public void run()
    {

        do
        {

            try
            {

                // StringBuffer sb = new
                // StringBuffer(this.upd_settings.update_server);
                // sb.append(this.upd_entry.getFileCommand());

                this.upd_progress.setJobStatus("Started");

                URL url = new URL(this.upd_settings.update_server + this.upd_entry.getFileCommand());

                InputStream is = url.openStream();

                if (!new File(this.upd_settings.output_path).exists())
                {
                    new File(this.upd_settings.output_path).mkdirs();
                }

                RandomAccessFile raf = new RandomAccessFile(this.upd_settings.output_path + "tempFile.bin", "rw");

                /*
                 * oos = new ObjectOutputStream(client.getOutputStream()); ois =
                 * new ObjectInputStream(client.getInputStream());
                 */

                float size = 671200;
                long current_size = 0;

                System.out.println("File size: " + size);

                this.upd_progress.setStatus("Download file");

                byte[] array = new byte[BLOCK_SIZE];

                while (is.available() > 0) // ; r.ready())
                {
                    if (is.available() < BLOCK_SIZE)
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
                    current_size += array.length;

                    System.out.println("Av: " + is.available());

                    // list.add(isr.read());
                    // osr.write(is.read());
                    // raf.write(is.read());

                    int prg = (int) (current_size / size * 100);
                    System.out.println("Progress: " + prg);

                    this.upd_progress.setProgress(prg);

                }

                // System.out.println("Size Arr: " + list.size());
                /*
                 * File f = new File("/home/andy/test.jpg");
                 * FileWriter bfw = new FileWriter(f);
                 * for(int i=0; i<list.size(); i++) { bfw.write(list.get(i)); }
                 * System.out.println("Write file");
                 */

                this.upd_progress.setStatus("Checking file");

                CheckSumUtility csu = new CheckSumUtility();

                long check = csu.getChecksumValue(this.upd_settings.output_path + "tempFile.bin");

                if (check != this.upd_entry.estimated_crc)
                {
                    this.upd_progress.setStatus("ChecksumUtil failed.");
                    this.upd_progress.setJobStatus("Failed");

                }
                else
                {
                    this.upd_progress.setStatus("File downloaded successfully.");
                    this.upd_progress.setJobStatus("Finished");
                    retry = max_retry + 1;
                }

                File f = new File(this.upd_settings.output_path + "tempFile.bin");
                f.renameTo(new File(this.upd_settings.output_path + this.upd_entry.output_file));

                is.close();
                raf.close();

                // System.out.println("ChecksumUtil: " +
                // csu.getChecksumValue(this.upd_settings.output_path +
                // "tempFile.bin"));

            }
            catch (Exception ex)
            {
                this.upd_progress.setJobStatus("Error");
                ex.printStackTrace();
            }

            retry++;
            System.out.println("Retry: " + retry);

        } while (retry <= max_retry);

    }

}
