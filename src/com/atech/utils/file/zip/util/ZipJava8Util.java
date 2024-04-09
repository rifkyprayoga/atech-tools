package com.atech.utils.file.zip.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by andy on 07.04.2024.
 */

/**
 *  This file is part of ATech Tools library.
 *
 *  Copyright (C) 2024  Andy (Aleksander) Rozman (Atech-Software)
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
 *  https://github.com/andyrozman/atech-tools or contact us via this email:
 *  andy@atech-software.com
 *
 *  @author Andy
 *
 */

@Slf4j
public class ZipJava8Util {

    public void unzipFromStream(InputStream inputStream, File destDir) throws Exception {
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(inputStream);
        ZipEntry zipEntry = zis.getNextEntry();

        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                // write file content
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();
    }

    private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }


//    /**
//     * @param source zip stream
//     * @param endTarget target directory
//     * @throws IOException extraction failed
//     */
//    public static void unZipFromStream(InputStream source, File endTarget) throws IOException {
//        final ZipInputStream zipStream = new ZipInputStream(source);
//        ZipEntry nextEntry;
//        File target = endTarget;
//        while ((nextEntry = zipStream.getNextEntry()) != null) {
//            final String name = nextEntry.getName();
//            log.info("Entry: " + name);
//
//            // only extract files
//            if (!name.endsWith("/")) {
//                final File nextFile = new File(target, name);
//
//                // create directories
//                final File parent = nextFile.getParentFile();
//                if (parent != null) {
//                    parent.mkdirs();
//                }
//
//                // write file
//                try (OutputStream targetStream = new FileOutputStream(nextFile)) {
//                    copy(zipStream, targetStream);
//                }
//            }
//        }
//    }

//    private static void copy(final InputStream source, final OutputStream target) throws IOException {
//        final int bufferSize = 4 * 1024;
//        final byte[] buffer = new byte[bufferSize];
//
//        int nextCount;
//        while ((nextCount = source.read(buffer)) >= 0) {
//            target.write(buffer, 0, nextCount);
//        }
//    }

}
