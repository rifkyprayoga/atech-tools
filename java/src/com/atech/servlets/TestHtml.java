package com.atech.servlets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class TestHtml
{

    public TestHtml()
    {
        JFrame mainFrame = new JFrame("HTML");
        mainFrame.setLayout(null);
        mainFrame.setBounds(20, 20, 500, 500);

        JTextArea tb = new JTextArea();
        tb.setBounds(20, 20, 200, 200);

        mainFrame.add(tb, null);

        JButton button1 = new JButton("<html><font color='#FF0000'>JButton Text</font></html>");
        JLabel label1 = new JLabel("<html><font color='#FF0000'>Red Text</font>"
                + "<br /><font color='#00FF00'>Blue Text</font>" + "<br /><font color='#0000FF'>Green Text</font>"
                + "<br /><font color='#000000'>Black Text</font>"
                + "<br /><font color='#FFFFFF'>Blue Text</font></html>");

        //mainFrame.add(button1);
        mainFrame.add(label1);

        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        //mainFrame.pack();
    }

    public static void main(String[] args)
    {
        new TestHtml();
    }
}