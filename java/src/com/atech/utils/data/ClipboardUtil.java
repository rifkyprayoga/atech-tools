package com.atech.utils.data;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Created by andy on 01.08.15.
 */
public class ClipboardUtil
{

    static Clipboard clipboardInstance;


    public static void copyToClipboard(String text)
    {
        Clipboard clipboard = getClipboardInstance();

        clipboard.setContents(new StringSelection(text), null);
    }


    public static String getFromClipboard() throws HeadlessException, UnsupportedFlavorException, IOException
    {
        Clipboard systemClipboard = getClipboardInstance();
        Object text = systemClipboard.getData(DataFlavor.stringFlavor);

        return (String) text;
    }


    public static Clipboard getClipboardInstance()
    {
        if (clipboardInstance == null)
        {

            Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
            clipboardInstance = defaultToolkit.getSystemClipboard();
        }

        return clipboardInstance;
    }

}
