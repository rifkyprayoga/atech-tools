package com.atech.print.engine;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;

public class ITextDocumentPrintSettings
{

    private int leftMargin;
    private int rightMargin;
    private int topMargin;
    private int bottomMargin;
    private Rectangle pageSize;

    public ITextDocumentPrintSettings()
    {
        this(PageSize.A4, 20, 20, 20, 20);
    }

    public ITextDocumentPrintSettings(Rectangle size)
    {
        this(size, 20, 20, 20, 20);
    }

    public ITextDocumentPrintSettings(int leftMargin, int rightMargin, int topMargin, int bottomMargin)
    {
        this(PageSize.A4, leftMargin, rightMargin, topMargin, bottomMargin);
    }

    public ITextDocumentPrintSettings(Rectangle size, int leftMargin, int rightMargin, int topMargin, int bottomMargin)
    {
        this.pageSize = size;
        this.leftMargin = leftMargin;
        this.rightMargin = rightMargin;
        this.topMargin = topMargin;
        this.bottomMargin = bottomMargin;

    }

    public int getLeftMargin()
    {
        return leftMargin;
    }

    public void setLeftMargin(int leftMargin)
    {
        this.leftMargin = leftMargin;
    }

    public int getRightMargin()
    {
        return rightMargin;
    }

    public void setRightMargin(int rightMargin)
    {
        this.rightMargin = rightMargin;
    }

    public int getTopMargin()
    {
        return topMargin;
    }

    public void setTopMargin(int topMargin)
    {
        this.topMargin = topMargin;
    }

    public int getBottomMargin()
    {
        return bottomMargin;
    }

    public void setBottomMargin(int bottomMargin)
    {
        this.bottomMargin = bottomMargin;
    }

    public Rectangle getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(Rectangle pageSize)
    {
        this.pageSize = pageSize;
    }

}
