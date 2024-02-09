package com.atech.graphics.dialogs.guilist;

public class LabelDef extends ButtonDef
{

    String label_text = null;
    int font_type = 0;
    int posX_left = 0;

    public static final int FONT_NORMAL = 0;
    public static final int FONT_BOLD = 1;
    public static final int FONT_ITALIC = 2;
    public static final int FONT_BOLD_ITALIC = 3;

    public LabelDef()
    {
        this("", 0, 0);
    }

    public LabelDef(String text, int font)
    {
        this(text, font, 0);
    }

    public LabelDef(String text, int font, int posX_left)
    {
        super();
        this.label_text = text;
        this.font_type = font;
        this.posX_left = posX_left;
    }

}
