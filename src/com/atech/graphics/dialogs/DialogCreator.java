package com.atech.graphics.dialogs;

import javax.swing.*;

import com.atech.db.hibernate.HibernateObject;

/**
 * Created by andy on 16/02/17.
 */
public interface DialogCreator
{

    <E extends HibernateObject> boolean isApplicable(Class<E> clazz);


    <E extends HibernateObject> StandardDialogForObject createDialog(Class<E> clazz, JDialog parentDialog);


    <E extends HibernateObject> StandardDialogForObject createDialog(Class<E> clazz, JDialog parentDialog,
            E selectableObject, boolean isEdit);

}
