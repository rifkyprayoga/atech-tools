package com.atech.utils.file;

/*************************************************************************
Copyright (C) 2005  Steve Gee
ioexcept@cox.net
This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*************************************************************************/

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class CheckSumUtility
{

    public static final String FILE_SEP = System.getProperties().getProperty("file.separator");
    public ArrayList fileList;

    public long getChecksumValue(String fname) throws Exception
    {
        return getChecksumValue(new File(fname));
    }

    public long getChecksumValue(File fname) throws Exception
    {
        Checksum checksum = new CRC32();
        checksum.reset();
        BufferedInputStream inputStream = null;

        try
        {
            inputStream = new BufferedInputStream(new FileInputStream(fname));
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) >= 0)
            {
                checksum.update(bytes, 0, len);
            }
        }
        catch (Exception ex)
        {
            return -1;
        }
        finally
        {
            try
            {
                inputStream.close();
            }
            catch (Exception ex)
            {}
        }
        return checksum.getValue();

    }

    /*
     * public long getChecksumValue(String _root, Enumeration fileNames) throws
     * Exception{
     * System.out.println("........................XXXXXXXX......................"
     * );
     * ChecksumUtil checksum = new CRC32();
     * checksum.reset();
     * long concatter = 0l;
     * int i = 0;
     * BufferedInputStream inputStream = null;
     * try {
     * while(fileNames.hasMoreElements()){
     * try{
     * i = 0;
     * String checkFile = (String) fileNames.nextElement();
     * inputStream = new BufferedInputStream(new FileInputStream(new File(_root
     * + FILE_SEP + checkFile)));
     * byte[] bytes = new byte[1024];
     * int len = 0;
     * while( (len = inputStream.read(bytes)) >= 0) {
     * checksum.update(bytes, 0, len);
     * }
     * concatter += checksum.getValue();
     * checksum.reset();
     * }catch(FileNotFoundException fnfe){
     * concatter -= 1;
     * }
     * }
     * return concatter;
     * } finally {
     * try { inputStream.close(); } catch(Exception ex) {}
     * }
     * }
     */

    /*
     * public LocalFileMasterCheckSum buildLocalSettings(TagReader iReader)
     * throws Exception{
     * LocalFileMasterCheckSum lfmc = new LocalFileMasterCheckSum();
     * ChecksumUtil checksum = new CRC32();
     * checksum.reset();
     * long concatter = 0l;
     * int i = 0;
     * BufferedInputStream inputStream = null;
     * try {
     * Vector vec = new Vector(iReader.getNodes().keySet());
     * Collections.sort(vec);
     * Iterator iter = vec.iterator();
     * String checkFile = null;
     * while(iter.hasNext()){
     * String key = (String) iter.next();
     * if(key.startsWith("DIR")){
     * String directory = key.substring(3).replaceAll("::",
     * " ").replaceAll("\\:", "\\" + FILE_SEP);
     * Enumeration enm = iReader.getTags(key).elements();
     * String rootFile = null;
     * while(enm.hasMoreElements()) {
     * try {
     * i = 0;
     * rootFile = (String) enm.nextElement();
     * checkFile = rootFile.replaceAll("::", " ");
     * //System.out.println("CHECKING: ." + directory + FILE_SEP + checkFile);
     * inputStream = new BufferedInputStream(new FileInputStream(new File("." +
     * directory + FILE_SEP + checkFile)));
     * byte[] bytes = new byte[1024];
     * int len = 0;
     * while( (len = inputStream.read(bytes)) >= 0) {
     * checksum.update(bytes, 0, len);
     * }//end while
     * concatter += checksum.getValue();
     * lfmc.add(key + ":" + rootFile, checksum.getValue(), directory,
     * checkFile);
     * checksum.reset();
     * } catch(FileNotFoundException fnfe) {
     * concatter -= 1;
     * lfmc.add(key + ":" + rootFile, -1, directory, checkFile);
     * }
     * } //end file Loop
     * }//end if-DIR
     * }//end Dir Loop
     * lfmc.setMasterCheckSum(concatter);
     * return lfmc;
     * } finally {
     * try { inputStream.close(); } catch(Exception ex) {}
     * }
     * }// getChecksumValue
     */

    /*
     * public ClientServerContainer getChecksumValue(ClientServerContainer csc,
     * String _root) throws Exception{
     * // public long getChecksumValue(String _root, TagReader iReader) throws
     * Exception{
     * TagReader iReader = csc.getFileSet();
     * ChecksumUtil checksum = new CRC32();
     * checksum.reset();
     * long concatter = 0l;
     * int i = 0;
     * BufferedInputStream inputStream = null;
     * try {
     * Vector vec = new Vector(iReader.getNodes().keySet());
     * Collections.sort(vec);
     * Iterator iter = vec.iterator();
     * String rootFile = null;
     * String directory = null;
     * while(iter.hasNext()){
     * String key = (String) iter.next();
     * if(key.startsWith("DIR")){
     * directory = key.substring(3).replaceAll("::", " ").replaceAll("\\:", "\\"
     * + FILE_SEP);
     * Enumeration enm = iReader.getTags(key).elements();
     * while(enm.hasMoreElements()) {
     * //System.out.println("\tCHECKSUM: " + _root + FILE_SEP + ((String)
     * enm.nextElement()).replaceAll("::"," "));
     * try {
     * i = 0;
     * rootFile = (String) enm.nextElement();
     * //System.out.println("LOOKING FOR: " + _root + FILE_SEP + directory +
     * FILE_SEP + rootFile.replaceAll("::", " "));
     * inputStream = new BufferedInputStream(new FileInputStream(new File(_root
     * + FILE_SEP + directory + FILE_SEP + rootFile.replaceAll("::", " "))));
     * byte[] bytes = new byte[1024];
     * int len = 0;
     * while( (len = inputStream.read(bytes)) >= 0) {
     * checksum.update(bytes, 0, len);
     * }
     * csc.put(key + ":" + rootFile, new Long(checksum.getValue()));
     * //System.out.println(">>>>>> " + key + ":" + rootFile + "[" +
     * checksum.getValue() + "] <<<<<<<");
     * concatter += checksum.getValue();
     * checksum.reset();
     * } catch(FileNotFoundException fnfe) {
     * //fnfe.printStackTrace();
     * //System.out.println(">>>>>> " + key + ":" + rootFile + " [-1] <<<<<<<");
     * csc.put(key + ":" + rootFile, new Long( -1));
     * concatter -= 1;
     * }
     * }
     * }
     * }
     * csc.setServerCheckSum(concatter);
     * return csc;
     * } finally {
     * try { inputStream.close(); } catch(Exception ex) {}
     * }
     * }// getChecksumValue
     */
    /*
     * public ClientServerContainer getChecksumValueX(ClientServerContainer csc,
     * String rootLoc) throws Exception{
     * ChecksumUtil fileChecksum = new CRC32();
     * long concatter = 0l;
     * Enumeration fileNames = csc.keys();
     * BufferedInputStream inputStream = null;
     * try {
     * while(fileNames.hasMoreElements()) {
     * try {
     * String checkFile = (String) fileNames.nextElement();
     * inputStream = new BufferedInputStream(new FileInputStream(new
     * File(rootLoc + FILE_SEP + checkFile)));
     * byte[] bytes = new byte[1024];
     * int len = 0;
     * while( (len = inputStream.read(bytes)) >= 0) {
     * fileChecksum.update(bytes, 0, len);
     * }
     * concatter += fileChecksum.getValue();
     * csc.put(checkFile,new Long(fileChecksum.getValue()));
     * fileChecksum.reset();
     * } catch(FileNotFoundException fnfe) {}
     * }
     * csc.setServerCheckSum(concatter);
     * return csc;
     * } finally {
     * try { inputStream.close(); } catch(Exception ex) {}
     * }
     * }
     * public ClientServerContainer getChecksumValue(ClientServerContainer csc)
     * throws Exception{
     * ChecksumUtil fileChecksum = new CRC32();
     * long concatter = 0l;
     * TagReader tReader = csc.getTReader();
     * String rootLoc = tReader.getTagValue("structure","server");
     * Enumeration fileNames = csc.keys();
     * BufferedInputStream inputStream = null;
     * try {
     * while(fileNames.hasMoreElements()) {
     * try {
     * String checkFile = (String) fileNames.nextElement();
     * inputStream = new BufferedInputStream(new FileInputStream(new
     * File(rootLoc + FILE_SEP + checkFile)));
     * byte[] bytes = new byte[1024];
     * int len = 0;
     * while( (len = inputStream.read(bytes)) >= 0) {
     * fileChecksum.update(bytes, 0, len);
     * }
     * concatter += fileChecksum.getValue();
     * csc.put(checkFile,new Long(fileChecksum.getValue()));
     * fileChecksum.reset();
     * } catch(FileNotFoundException fnfe) {}
     * }
     * csc.setServerCheckSum(concatter);
     * return csc;
     * } finally {
     * try { inputStream.close(); } catch(Exception ex) {}
     * }
     * }
     */

    public MessageDigest generateChecksum(File filename) throws Exception
    {
        MessageDigest complete = MessageDigest.getInstance("MD5");
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(filename);
            byte[] buffer = new byte[1024];
            int numRead = -1;

            while ((numRead = inputStream.read(buffer)) > 0)
            {
                complete.digest(buffer, 0, numRead);
            }
        }
        finally
        {
            try
            {
                inputStream.close();
            }
            catch (Exception ex)
            {}
        }

        return complete;
    }
}
