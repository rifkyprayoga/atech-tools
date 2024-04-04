package com.atech.i18n.tool.simple;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

/**
 * Created by andy on 01.07.17.
 */
public class TranslationBoxListener extends KeyAdapter implements MouseListener
{

    JComboBox cmb_status;
    JTextArea jt_source, jt_mine;


    public TranslationBoxListener(JComboBox cmb_status, JTextArea jt_source, JTextArea jt_mine)
    {
        this.cmb_status = cmb_status;
        this.jt_source = jt_source;
        this.jt_mine = jt_mine;
    }


    /**
     * Checks to see if there are changes in the translation box and change
     * the status combo box automatically.
     */
    private void applyChanges()
    {
        // .isEmpty() -> .equals("") JDK 1.5.0 compatibility
        switch (cmb_status.getSelectedIndex())
        {
            case 1:
                if (jt_mine.getText().equals("") || jt_mine.getText().equals(jt_source.getText()))
                {
                    cmb_status.setSelectedIndex(0);
                }
                break;
            case 2:
                // if status is "translated", don't automatically change
                break;
            case 0:
            default:
                if (!jt_mine.getText().equals("") && !jt_mine.getText().equals(jt_source.getText()))
                {
                    cmb_status.setSelectedIndex(1);
                }
                break;
        }
    }


    @Override
    public void keyReleased(KeyEvent e)
    {
        applyChanges();
    }


    public void mouseClicked(MouseEvent e)
    {
    }


    public void mousePressed(MouseEvent e)
    {
    }


    public void mouseReleased(MouseEvent e)
    {
        applyChanges();
    }


    public void mouseEntered(MouseEvent e)
    {
    }


    public void mouseExited(MouseEvent e)
    {
    }

}
