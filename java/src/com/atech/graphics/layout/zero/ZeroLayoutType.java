package com.atech.graphics.layout.zero;

/**
 * Created by andy on 26.09.15.
 */
public enum ZeroLayoutType
{
    None(false), //
    Dynamic(true), //
    DynamicLocation(true), //

    DynamicSize(true);

    /*
     * Valid constraints are: ZeroLayout.STATIC is used if we don't want to
     * resize
     * Component, ZeroLayout.DYNAMIC is used for Componets that will resize just
     * the
     * size (x,y are static, width and height change). ZeroLayout.DYNAMIC_FULL
     * changes all variables (x,y,width and height).
     */

    boolean isDynamic = false;


    ZeroLayoutType(boolean isDynamic)
    {
        this.isDynamic = isDynamic;
    }
}
