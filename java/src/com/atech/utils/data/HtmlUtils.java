package com.atech.utils.data;

/**
 * Created by andy on 01.08.15.
 */
public class HtmlUtils
{

    public static String convertToPlainText(String text)
    {
        String outText = text.replaceAll("<br>", "\n");

        outText = outText.replaceAll("<b>", "");
        outText = outText.replaceAll("</b>", "");

        return outText;
    }

}
