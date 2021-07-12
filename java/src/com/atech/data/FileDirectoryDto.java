package com.atech.data;

/**
 * Created by andy on 27/01/18.
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