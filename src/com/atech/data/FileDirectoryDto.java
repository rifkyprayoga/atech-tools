package com.atech.data;

/**
 * Created by andy on 27/01/18.
 */
/**
 *  This file is part of ATech Tools library.
 *
 *  Copyright (C) 2018  Andy (Aleksander) Rozman (Atech-Software)
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
public class FileDirectoryDto {

    private String fileName;
    private boolean isDirectory;
    private String template;
    private boolean replacementForced;
    private boolean checkNeeded;

    public FileDirectoryDto(String fileName, boolean isDirectory)
    {
        this.fileName = fileName;
        this.isDirectory = isDirectory;
    }

    public FileDirectoryDto(String fileName, boolean isDirectory, String template, boolean replacementForced)
    {
        this(fileName, isDirectory, template, replacementForced, true);
    }

    public FileDirectoryDto(String fileName, boolean isDirectory, String template, boolean replacementForced, boolean checkNeeded)
    {
        this.fileName = fileName;
        this.isDirectory = isDirectory;
        this.template = template;
        this.replacementForced = replacementForced;
        this.checkNeeded = checkNeeded;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public boolean isReplacementForced() {
        return replacementForced;
    }

    public void setReplacementForced(boolean replacementForced) {
        this.replacementForced = replacementForced;
    }

    public boolean isCheckNeeded() {
        return checkNeeded;
    }

    public void setCheckNeeded(boolean checkNeeded) {
        this.checkNeeded = checkNeeded;
    }
}