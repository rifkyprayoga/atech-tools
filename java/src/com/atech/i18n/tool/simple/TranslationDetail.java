package com.atech.i18n.tool.simple;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TranslationDetail extends JDialog implements ActionListener
{
    
    JTextArea ta_english, ta_translate;
    boolean was_action = false;
    
    public TranslationDetail() 
    {
        initGUI();
    }


    public TranslationDetail(JFrame frame, String english, String translation) 
    {
        super(frame, "", true);
        initGUI();
        this.ta_english.setText(english);
        this.ta_translate.setText(translation);
        
        this.setVisible(true);
    }
    
    
    
    private void initGUI() 
    {
        getContentPane().setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);

        
        ta_english = new JTextArea();
//        ta_english.setBounds(48, 66, 368, 85);
        //getContentPane().add(ta_english);
        ta_english.setLineWrap(true);
        ta_english.setWrapStyleWord(true);
        
        
        JScrollPane scrollPane = new JScrollPane(ta_english);
        scrollPane.setBounds(48, 80, 368, 85);
        getContentPane().add(scrollPane);
        
        
        ta_translate = new JTextArea();
        ta_translate.setLineWrap(true);
        ta_translate.setWrapStyleWord(true);
        
//        ta_translate.setBounds(48, 183, 368, 85);
        //getContentPane().add(ta_translate);
        
        
        scrollPane = new JScrollPane(ta_translate);
        scrollPane.setBounds(48, 200, 368, 85);
        getContentPane().add(scrollPane);
        getContentPane().add(panel);
        
        
        

        
        JButton button = new JButton("OK");
        button.setBounds(138, 295, 95, 23);
        button.addActionListener(this);
        button.setActionCommand("ok");
        getContentPane().add(button);
        
        button = new JButton("Cancel");
        button.setBounds(257, 295, 89, 23);
        button.addActionListener(this);
        button.setActionCommand("cancel");
        getContentPane().add(button);
        
        JLabel label = new JLabel("Detailed translation");
        label.setBounds(126, 11, 240, 23);
        getContentPane().add(label);
        
        JLabel label_1 = new JLabel("Original:");
        label_1.setBounds(48, 50, 180, 14);
        getContentPane().add(label_1);
        
        JLabel label_2 = new JLabel("Translation");
        label_2.setBounds(48, 170, 180, 14);
        getContentPane().add(label_2);
        
        
        this.setBounds(40, 40, 500, 400);
        
    }



    public void actionPerformed(ActionEvent e)
    {
        String ac = e.getActionCommand();
        
        System.out.println("Action: "+ ac);
        
        if (ac.equals("ok"))
        {
            was_action = true;
            this.dispose();
        }
        else if (ac.equals("cancel"))
        {
            this.dispose();
        }
            
    }
    
    public String getData()
    {
        return this.ta_translate.getText();
    }
    
    
    public boolean wasAction()
    {
        return this.was_action;
    }
    
    
    
}
