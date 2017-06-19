package com.atech.utils.file;

/**
 * Created by andy on 01.01.17.
 */
public enum ClassTypeDefinition
{
    Interface, //
    Abstract, //
    EndClass, //
    Interface_Abstract, //
    All, //
    ;

    public static boolean isEnabled(ClassTypeDefinition currentClassTypeDefinition,
            ClassTypeDefinition targetClassTypeDefinition)
    {
        if (targetClassTypeDefinition == Interface)
        {
            return (currentClassTypeDefinition == Interface || //
                    currentClassTypeDefinition == All || //
            currentClassTypeDefinition == Interface_Abstract);
        }

        if (targetClassTypeDefinition == Abstract)
        {
            return (currentClassTypeDefinition == Abstract || //
                    currentClassTypeDefinition == All || //
            currentClassTypeDefinition == Interface_Abstract);
        }

        if (targetClassTypeDefinition == EndClass)
        {
            return (currentClassTypeDefinition == All || //
            currentClassTypeDefinition == EndClass);
        }

        return false;
    }

}
