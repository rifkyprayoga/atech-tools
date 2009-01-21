package com.atech.graphics.html_editor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.undo.*;

/**
 *  This file is part of ATech Tools library.
 *  
 *  <one line to give the library's name and a brief idea of what it does.>
 *  Copyright (C) 2007  Andy (Aleksander) Rozman (Atech-Software)
 *  
 *  
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 *  
 *  
 *  For additional information about this project please visit our project site on 
 *  http://atech-tools.sourceforge.net/ or contact us via this emails: 
 *  andyrozman@users.sourceforge.net or andy@atech-software.com
 *  
 *  @author Andy
 *
*/


public class HTMLDocEditor extends JFrame implements ActionListener
{

    private static final long serialVersionUID = 1608797065642430368L;
    class RedoAction extends AbstractAction
    {

        private static final long serialVersionUID = 234327098549187946L;

        public void actionPerformed(ActionEvent e)
        {
            try
            {
                undo.redo();
            }
            catch(CannotRedoException ex)
            {
                System.err.println((new StringBuilder()).append("Unable to redo: ").append(ex).toString());
                ex.printStackTrace();
            }
            update();
            undoAction.update();
        }

        protected void update()
        {
            if(undo.canRedo())
            {
                setEnabled(true);
                putValue("Name", undo.getRedoPresentationName());
            } else
            {
                setEnabled(false);
                putValue("Name", "Redo");
            }
        }

        public RedoAction()
        {
            super("Redo");
            setEnabled(false);
        }
    }

    class UndoAction extends AbstractAction
    {

        private static final long serialVersionUID = 7877236529165912956L;


        public void actionPerformed(ActionEvent e)
        {
            try
            {
                undo.undo();
            }
            catch(CannotUndoException ex)
            {
                System.out.println((new StringBuilder()).append("Unable to undo: ").append(ex).toString());
                ex.printStackTrace();
            }
            update();
            redoAction.update();
        }

        protected void update()
        {
            if(undo.canUndo())
            {
                setEnabled(true);
                putValue("Name", undo.getUndoPresentationName());
            } else
            {
                setEnabled(false);
                putValue("Name", "Undo");
            }
        }


        public UndoAction()
        {
            super("Undo");
            setEnabled(false);
        }
    }

    class UndoHandler implements UndoableEditListener
    {

        public void undoableEditHappened(UndoableEditEvent e)
        {
            undo.addEdit(e.getEdit());
            undoAction.update();
            redoAction.update();
        }


        UndoHandler()
        {
            super();
        }
    }

    class HTMLFileFilter extends FileFilter
    {

        public boolean accept(File f)
        {
            return f.isDirectory() || f.getName().toLowerCase().indexOf(".htm") > 0;
        }

        public String getDescription()
        {
            return "html";
        }


        HTMLFileFilter()
        {
            super();
        }
    }

    class StrikeThroughAction extends javax.swing.text.StyledEditorKit.StyledTextAction
    {

        private static final long serialVersionUID = -5816644815136707434L;


        public void actionPerformed(ActionEvent ae)
        {
            javax.swing.JEditorPane editor = getEditor(ae);
            if(editor != null)
            {
                StyledEditorKit kit = getStyledEditorKit(editor);
                javax.swing.text.MutableAttributeSet attr = kit.getInputAttributes();
                boolean strikeThrough = !StyleConstants.isStrikeThrough(attr);
                SimpleAttributeSet sas = new SimpleAttributeSet();
                StyleConstants.setStrikeThrough(sas, strikeThrough);
                setCharacterAttributes(editor, sas, false);
            }
        }


        public StrikeThroughAction()
        {
            super(StyleConstants.StrikeThrough.toString());
        }
    }

    class SuperscriptAction extends javax.swing.text.StyledEditorKit.StyledTextAction
    {

        private static final long serialVersionUID = -6397583463447945632L;


        public void actionPerformed(ActionEvent ae)
        {
            javax.swing.JEditorPane editor = getEditor(ae);
            if(editor != null)
            {
                StyledEditorKit kit = getStyledEditorKit(editor);
                javax.swing.text.MutableAttributeSet attr = kit.getInputAttributes();
                boolean superscript = !StyleConstants.isSuperscript(attr);
                SimpleAttributeSet sas = new SimpleAttributeSet();
                StyleConstants.setSuperscript(sas, superscript);
                setCharacterAttributes(editor, sas, false);
            }
        }


        public SuperscriptAction()
        {
            super(StyleConstants.Superscript.toString());
        }
    }

    class SubscriptAction extends javax.swing.text.StyledEditorKit.StyledTextAction
    {

        private static final long serialVersionUID = -2333034083654733244L;

        public void actionPerformed(ActionEvent ae)
        {
            javax.swing.JEditorPane editor = getEditor(ae);
            if(editor != null)
            {
                StyledEditorKit kit = getStyledEditorKit(editor);
                javax.swing.text.MutableAttributeSet attr = kit.getInputAttributes();
                boolean subscript = !StyleConstants.isSubscript(attr);
                SimpleAttributeSet sas = new SimpleAttributeSet();
                StyleConstants.setSubscript(sas, subscript);
                setCharacterAttributes(editor, sas, false);
            }
        }

        public SubscriptAction()
        {
            super(StyleConstants.Subscript.toString());
        }
    }

    class FrameListener extends WindowAdapter
    {

        public void windowClosing(WindowEvent we)
        {
            exit();
        }


        FrameListener()
        {
            super();
        }
    }


    public HTMLDocEditor()
    {
        super("HTMLDocumentEditor");
        textPane = new JTextPane();
        debug = false;
        undoHandler = new UndoHandler();
        undo = new UndoManager();
        undoAction = new UndoAction();
        redoAction = new RedoAction();
        cutAction = new javax.swing.text.DefaultEditorKit.CutAction();
        copyAction = new javax.swing.text.DefaultEditorKit.CopyAction();
        pasteAction = new javax.swing.text.DefaultEditorKit.PasteAction();
        boldAction = new javax.swing.text.StyledEditorKit.BoldAction();
        underlineAction = new javax.swing.text.StyledEditorKit.UnderlineAction();
        italicAction = new javax.swing.text.StyledEditorKit.ItalicAction();
        insertBreakAction = new javax.swing.text.DefaultEditorKit.InsertBreakAction();
        unorderedListAction = new javax.swing.text.html.HTMLEditorKit.InsertHTMLTextAction("Bullets", "<ul><li> </li></ul>", javax.swing.text.html.HTML.Tag.P, javax.swing.text.html.HTML.Tag.UL);
        bulletAction = new javax.swing.text.html.HTMLEditorKit.InsertHTMLTextAction("Bullets", "<li> </li>", javax.swing.text.html.HTML.Tag.UL, javax.swing.text.html.HTML.Tag.LI);
        HTMLEditorKit editorKit = new HTMLEditorKit();
        document = (HTMLDocument)editorKit.createDefaultDocument();
        init();
    }


    public void init()
    {
        addWindowListener(new FrameListener());
        JMenuBar menuBar = new JMenuBar();
        getContentPane().add(menuBar, "North");
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu colorMenu = new JMenu("Color");
        JMenu fontMenu = new JMenu("Font");
        JMenu styleMenu = new JMenu("Style");
        JMenu alignMenu = new JMenu("Align");
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(colorMenu);
        menuBar.add(fontMenu);
        menuBar.add(styleMenu);
        menuBar.add(alignMenu);
        menuBar.add(helpMenu);
        JMenuItem newItem = new JMenuItem("New", new ImageIcon("whatsnew-bang.gif"));
        JMenuItem openItem = new JMenuItem("Open", new ImageIcon("open.gif"));
        JMenuItem saveItem = new JMenuItem("Save", new ImageIcon("save.gif"));
        JMenuItem saveAsItem = new JMenuItem("Save As");
        JMenuItem exitItem = new JMenuItem("Exit", new ImageIcon("exit.gif"));
        newItem.addActionListener(this);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        saveAsItem.addActionListener(this);
        exitItem.addActionListener(this);
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(exitItem);
        JMenuItem undoItem = new JMenuItem(undoAction);
        JMenuItem redoItem = new JMenuItem(redoAction);
        JMenuItem cutItem = new JMenuItem(cutAction);
        JMenuItem copyItem = new JMenuItem(copyAction);
        JMenuItem pasteItem = new JMenuItem(pasteAction);
        JMenuItem clearItem = new JMenuItem("Clear");
        JMenuItem selectAllItem = new JMenuItem("Select All");
        JMenuItem insertBreaKItem = new JMenuItem(insertBreakAction);
        JMenuItem unorderedListItem = new JMenuItem(unorderedListAction);
        JMenuItem bulletItem = new JMenuItem(bulletAction);
        cutItem.setText("Cut");
        copyItem.setText("Copy");
        pasteItem.setText("Paste");
        insertBreaKItem.setText("Break");
        cutItem.setIcon(new ImageIcon("cut.gif"));
        copyItem.setIcon(new ImageIcon("copy.gif"));
        pasteItem.setIcon(new ImageIcon("paste.gif"));
        insertBreaKItem.setIcon(new ImageIcon("break.gif"));
        unorderedListItem.setIcon(new ImageIcon("bullets.gif"));
        clearItem.addActionListener(this);
        selectAllItem.addActionListener(this);
        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(clearItem);
        editMenu.add(selectAllItem);
        editMenu.add(insertBreaKItem);
        editMenu.add(unorderedListItem);
        editMenu.add(bulletItem);
        JMenuItem redTextItem = new JMenuItem(new javax.swing.text.StyledEditorKit.ForegroundAction("Red", Color.red));
        JMenuItem orangeTextItem = new JMenuItem(new javax.swing.text.StyledEditorKit.ForegroundAction("Orange", Color.orange));
        JMenuItem yellowTextItem = new JMenuItem(new javax.swing.text.StyledEditorKit.ForegroundAction("Yellow", Color.yellow));
        JMenuItem greenTextItem = new JMenuItem(new javax.swing.text.StyledEditorKit.ForegroundAction("Green", Color.green));
        JMenuItem blueTextItem = new JMenuItem(new javax.swing.text.StyledEditorKit.ForegroundAction("Blue", Color.blue));
        JMenuItem cyanTextItem = new JMenuItem(new javax.swing.text.StyledEditorKit.ForegroundAction("Cyan", Color.cyan));
        JMenuItem magentaTextItem = new JMenuItem(new javax.swing.text.StyledEditorKit.ForegroundAction("Magenta", Color.magenta));
        JMenuItem blackTextItem = new JMenuItem(new javax.swing.text.StyledEditorKit.ForegroundAction("Black", Color.black));
        redTextItem.setIcon(new ImageIcon("red.gif"));
        orangeTextItem.setIcon(new ImageIcon("orange.gif"));
        yellowTextItem.setIcon(new ImageIcon("yellow.gif"));
        greenTextItem.setIcon(new ImageIcon("green.gif"));
        blueTextItem.setIcon(new ImageIcon("blue.gif"));
        cyanTextItem.setIcon(new ImageIcon("cyan.gif"));
        magentaTextItem.setIcon(new ImageIcon("magenta.gif"));
        blackTextItem.setIcon(new ImageIcon("black.gif"));
        colorMenu.add(redTextItem);
        colorMenu.add(orangeTextItem);
        colorMenu.add(yellowTextItem);
        colorMenu.add(greenTextItem);
        colorMenu.add(blueTextItem);
        colorMenu.add(cyanTextItem);
        colorMenu.add(magentaTextItem);
        colorMenu.add(blackTextItem);
        JMenu fontTypeMenu = new JMenu("Font Type");
        fontMenu.add(fontTypeMenu);
        String fontTypes[] = {
            "SansSerif", "Serif", "Monospaced", "Dialog", "DialogInput"
        };
        for(int i = 0; i < fontTypes.length; i++)
        {
            if(debug)
                System.out.println(fontTypes[i]);
            JMenuItem nextTypeItem = new JMenuItem(fontTypes[i]);
            nextTypeItem.setAction(new javax.swing.text.StyledEditorKit.FontFamilyAction(fontTypes[i], fontTypes[i]));
            fontTypeMenu.add(nextTypeItem);
        }

        JMenu fontSizeMenu = new JMenu("Font Size");
        fontMenu.add(fontSizeMenu);
        int fontSizes[] = {
            6, 8, 10, 12, 14, 16, 20, 24, 32, 36, 
            48, 72
        };
        for(int i = 0; i < fontSizes.length; i++)
        {
            if(debug)
                System.out.println(fontSizes[i]);
            JMenuItem nextSizeItem = new JMenuItem(String.valueOf(fontSizes[i]));
            nextSizeItem.setAction(new javax.swing.text.StyledEditorKit.FontSizeAction(String.valueOf(fontSizes[i]), fontSizes[i]));
            fontSizeMenu.add(nextSizeItem);
        }

        JMenuItem boldMenuItem = new JMenuItem(boldAction);
        boldMenuItem.addActionListener(this);
        boldMenuItem.setActionCommand("bold");
        JMenuItem underlineMenuItem = new JMenuItem(underlineAction);
        JMenuItem italicMenuItem = new JMenuItem(italicAction);
        boldMenuItem.setText("Bold");
        underlineMenuItem.setText("Underline");
        italicMenuItem.setText("Italic");
        boldMenuItem.setIcon(new ImageIcon("bold.gif"));
        underlineMenuItem.setIcon(new ImageIcon("underline.gif"));
        italicMenuItem.setIcon(new ImageIcon("italic.gif"));
        styleMenu.add(boldMenuItem);
        styleMenu.add(underlineMenuItem);
        styleMenu.add(italicMenuItem);
        JMenuItem subscriptMenuItem = new JMenuItem(new SubscriptAction());
        JMenuItem superscriptMenuItem = new JMenuItem(new SuperscriptAction());
        JMenuItem strikeThroughMenuItem = new JMenuItem(new StrikeThroughAction());
        subscriptMenuItem.setText("Subscript");
        superscriptMenuItem.setText("Superscript");
        strikeThroughMenuItem.setText("StrikeThrough");
        subscriptMenuItem.setIcon(new ImageIcon("subscript.gif"));
        superscriptMenuItem.setIcon(new ImageIcon("superscript.gif"));
        strikeThroughMenuItem.setIcon(new ImageIcon("strikethough.gif"));
        styleMenu.add(subscriptMenuItem);
        styleMenu.add(superscriptMenuItem);
        styleMenu.add(strikeThroughMenuItem);
        JMenuItem leftAlignMenuItem = new JMenuItem(new javax.swing.text.StyledEditorKit.AlignmentAction("Left Align", 0));
        JMenuItem centerMenuItem = new JMenuItem(new javax.swing.text.StyledEditorKit.AlignmentAction("Center", 1));
        JMenuItem rightAlignMenuItem = new JMenuItem(new javax.swing.text.StyledEditorKit.AlignmentAction("Right Align", 2));
        leftAlignMenuItem.setText("Left Align");
        centerMenuItem.setText("Center");
        rightAlignMenuItem.setText("Right Align");
        leftAlignMenuItem.setIcon(new ImageIcon("left.gif"));
        centerMenuItem.setIcon(new ImageIcon("center.gif"));
        rightAlignMenuItem.setIcon(new ImageIcon("right.gif"));
        alignMenu.add(leftAlignMenuItem);
        alignMenu.add(centerMenuItem);
        alignMenu.add(rightAlignMenuItem);
        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener(this);
        helpMenu.add(helpItem);
        JMenuItem shortcutsItem = new JMenuItem("Keyboard Shortcuts");
        shortcutsItem.addActionListener(this);
        helpMenu.add(shortcutsItem);
        JMenuItem aboutItem = new JMenuItem("About QuantumHyperSpace");
        aboutItem.addActionListener(this);
        helpMenu.add(aboutItem);
        JPanel editorControlPanel = new JPanel();
        editorControlPanel.setLayout(new FlowLayout());
        JButton cutButton = new JButton(cutAction);
        JButton copyButton = new JButton(copyAction);
        JButton pasteButton = new JButton(pasteAction);
        JToggleButton boldButton = new JToggleButton(boldAction);
        JToggleButton underlineButton = new JToggleButton(underlineAction);
        JToggleButton italicButton = new JToggleButton(italicAction);
        cutButton.setText("Cut");
        copyButton.setText("Copy");
        pasteButton.setText("Paste");
        boldButton.setText("");
        boldButton.addActionListener(this);
        boldButton.setActionCommand("bold");
        underlineButton.setText("");
        underlineButton.addActionListener(this);
        underlineButton.setActionCommand("underline");
        italicButton.setText("");
        italicButton.addActionListener(this);
        italicButton.setActionCommand("italic");
        cutButton.setIcon(new ImageIcon("cut.gif"));
        copyButton.setIcon(new ImageIcon("copy.gif"));
        pasteButton.setIcon(new ImageIcon("paste.gif"));
        boldButton.setIcon(new ImageIcon(getImage("/images/editor/Bold16.gif")));
        underlineButton.setIcon(new ImageIcon(getImage("/images/editor/Underline16.gif")));
        italicButton.setIcon(new ImageIcon(getImage("/images/editor/Italic16.gif")));
        JButton subscriptButton = new JButton(new SubscriptAction());
        JButton superscriptButton = new JButton(new SuperscriptAction());
        JButton strikeThroughButton = new JButton(new StrikeThroughAction());
        subscriptButton.setIcon(new ImageIcon("subscript.gif"));
        superscriptButton.setIcon(new ImageIcon("superscript.gif"));
        strikeThroughButton.setIcon(new ImageIcon("strikethough.gif"));
        JPanel specialPanel = new JPanel();
        specialPanel.setLayout(new FlowLayout());
        specialPanel.add(subscriptButton);
        specialPanel.add(superscriptButton);
        specialPanel.add(strikeThroughButton);
        JButton leftAlignButton = new JButton(new javax.swing.text.StyledEditorKit.AlignmentAction("Left Align", 0));
        JButton centerButton = new JButton(new javax.swing.text.StyledEditorKit.AlignmentAction("Center", 1));
        JButton rightAlignButton = new JButton(new javax.swing.text.StyledEditorKit.AlignmentAction("Right Align", 2));
        JButton colorButton = new JButton(new javax.swing.text.StyledEditorKit.AlignmentAction("Right Align", 2));
        leftAlignButton.setIcon(new ImageIcon(getImage("/images/editor/AlignLeft16.gif")));
        centerButton.setIcon(new ImageIcon(getImage("/images/editor/AlignCenter16.gif")));
        rightAlignButton.setIcon(new ImageIcon(getImage("/images/editor/AlignRight16.gif")));
        colorButton.setIcon(new ImageIcon("color.gif"));
        leftAlignButton.setText("Left Align");
        centerButton.setText("Center");
        rightAlignButton.setText("Right Align");
        JPanel alignPanel = new JPanel();
        alignPanel.setLayout(new FlowLayout());
        alignPanel.add(leftAlignButton);
        alignPanel.add(centerButton);
        alignPanel.add(rightAlignButton);
        document.addUndoableEditListener(undoHandler);
        resetUndoManager();
        textPane = new JTextPane(document);
        textPane.setContentType("text/html");
        JScrollPane scrollPane = new JScrollPane(textPane);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension scrollPaneSize = new Dimension((5 * screenSize.width) / 8, (5 * screenSize.height) / 8);
        scrollPane.setPreferredSize(scrollPaneSize);
        JToolBar tb = new JToolBar();
        tb.add(cutButton);
        tb.add(copyButton);
        tb.add(pasteButton);
        tb.addSeparator();
        tb.addSeparator();
        tb.add(boldButton);
        tb.add(underlineButton);
        tb.add(italicButton);
        tb.addSeparator();
        tb.add(subscriptButton);
        tb.add(superscriptButton);
        tb.add(strikeThroughButton);
        tb.addSeparator();
        tb.addSeparator();
        tb.add(leftAlignButton);
        tb.add(centerButton);
        tb.add(rightAlignButton);
        getContentPane().add(tb, "North");
        setJMenuBar(menuBar);
        getContentPane().add(scrollPane, "South");
        pack();
        setLocationRelativeTo(null);
        startNewDocument();
	setVisible(true);
	//show();
    }

    private Image getImage(String filename)
    {
        InputStream is = getClass().getResourceAsStream(filename);
        if(is == null)
            System.out.println((new StringBuilder()).append("Error reading image: ").append(filename).toString());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Image img;
        try
        {
            int c;
            while((c = is.read()) >= 0) 
                baos.write(c);
            img = getToolkit().createImage(baos.toByteArray());
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
        return img;
    }

    public void actionPerformed(ActionEvent ae)
    {
        String actionCommand = ae.getActionCommand();
        if(debug)
        {
            int modifier = ae.getModifiers();
            long when = ae.getWhen();
            String parameter = ae.paramString();
            System.out.println((new StringBuilder()).append("actionCommand: ").append(actionCommand).toString());
            System.out.println((new StringBuilder()).append("modifier: ").append(modifier).toString());
            System.out.println((new StringBuilder()).append("when: ").append(when).toString());
            System.out.println((new StringBuilder()).append("parameter: ").append(parameter).toString());
        }
        if(actionCommand.compareTo("New") == 0)
            startNewDocument();
        else
        if(actionCommand.compareTo("Open") == 0)
            openDocument();
        else
        if(actionCommand.compareTo("Save") == 0)
            saveDocument();
        else
        if(actionCommand.compareTo("Save As") == 0)
            saveDocumentAs();
        else
        if(actionCommand.compareTo("Exit") == 0)
            exit();
        else
        if(actionCommand.compareTo("Clear") == 0)
            clear();
        else
        if(actionCommand.compareTo("Select All") == 0)
            selectAll();
        else
        if(actionCommand.compareTo("Help") == 0)
            help();
        else
        if(actionCommand.compareTo("Keyboard Shortcuts") == 0)
            showShortcuts();
        else
        if(actionCommand.compareTo("About QuantumHyperSpace") == 0)
            aboutQuantumHyperSpace();
        else
        if(actionCommand.equals("bold"))
            System.out.println("Bol activated");
    }

    protected void resetUndoManager()
    {
        undo.discardAllEdits();
        undoAction.update();
        redoAction.update();
    }

    public void startNewDocument()
    {
        Document oldDoc = textPane.getDocument();
        if(oldDoc != null)
            oldDoc.removeUndoableEditListener(undoHandler);
        HTMLEditorKit editorKit = new HTMLEditorKit();
        document = (HTMLDocument)editorKit.createDefaultDocument();
        textPane.setDocument(document);
        currentFile = null;
        setTitle("HTMLDocumentEditor");
        textPane.getDocument().addUndoableEditListener(undoHandler);
        resetUndoManager();
    }

    public void openDocument()
    {
        try
        {
            File current = new File(".");
            JFileChooser chooser = new JFileChooser(current);
            chooser.setFileSelectionMode(2);
            chooser.setFileFilter(new HTMLFileFilter());
            int approval = chooser.showSaveDialog(this);
            if(approval == 0)
            {
                currentFile = chooser.getSelectedFile();
                setTitle(currentFile.getName());
                FileReader fr = new FileReader(currentFile);
                Document oldDoc = textPane.getDocument();
                if(oldDoc != null)
                    oldDoc.removeUndoableEditListener(undoHandler);
                HTMLEditorKit editorKit = new HTMLEditorKit();
                document = (HTMLDocument)editorKit.createDefaultDocument();
                editorKit.read(fr, document, 0);
                document.addUndoableEditListener(undoHandler);
                textPane.setDocument(document);
                resetUndoManager();
            }
        }
        catch(BadLocationException ble)
        {
            System.err.println((new StringBuilder()).append("BadLocationException: ").append(ble.getMessage()).toString());
        }
        catch(FileNotFoundException fnfe)
        {
            System.err.println((new StringBuilder()).append("FileNotFoundException: ").append(fnfe.getMessage()).toString());
        }
        catch(IOException ioe)
        {
            System.err.println((new StringBuilder()).append("IOException: ").append(ioe.getMessage()).toString());
        }
    }

    public void saveDocument()
    {
        if(currentFile != null)
            try
            {
                FileWriter fw = new FileWriter(currentFile);
                fw.write(textPane.getText());
                fw.close();
            }
            catch(FileNotFoundException fnfe)
            {
                System.err.println((new StringBuilder()).append("FileNotFoundException: ").append(fnfe.getMessage()).toString());
            }
            catch(IOException ioe)
            {
                System.err.println((new StringBuilder()).append("IOException: ").append(ioe.getMessage()).toString());
            }
        else
            saveDocumentAs();
    }

    public void saveDocumentAs()
    {
        try
        {
            File current = new File(".");
            JFileChooser chooser = new JFileChooser(current);
            chooser.setFileSelectionMode(2);
            chooser.setFileFilter(new HTMLFileFilter());
            int approval = chooser.showSaveDialog(this);
            if(approval == 0)
            {
                File newFile = chooser.getSelectedFile();
                if(newFile.exists())
                {
                    String message = (new StringBuilder()).append(newFile.getAbsolutePath()).append(" already exists. \n").append("Do you want to replace it?").toString();
                    if(JOptionPane.showConfirmDialog(this, message) == 0)
                    {
                        currentFile = newFile;
                        setTitle(currentFile.getName());
                        FileWriter fw = new FileWriter(currentFile);
                        fw.write(textPane.getText());
                        fw.close();
                        if(debug)
                            System.out.println((new StringBuilder()).append("Saved ").append(currentFile.getAbsolutePath()).toString());
                    }
                } else
                {
                    currentFile = new File(newFile.getAbsolutePath());
                    setTitle(currentFile.getName());
                    FileWriter fw = new FileWriter(currentFile);
                    fw.write(textPane.getText());
                    fw.close();
                    if(debug)
                        System.out.println((new StringBuilder()).append("Saved ").append(currentFile.getAbsolutePath()).toString());
                }
            }
        }
        catch(FileNotFoundException fnfe)
        {
            System.err.println((new StringBuilder()).append("FileNotFoundException: ").append(fnfe.getMessage()).toString());
        }
        catch(IOException ioe)
        {
            System.err.println((new StringBuilder()).append("IOException: ").append(ioe.getMessage()).toString());
        }
    }

    public void exit()
    {
        String exitMessage = "Are you sure you want to exit?";
        if(JOptionPane.showConfirmDialog(this, exitMessage) == 0)
            System.exit(0);
    }

    public void clear()
    {
        startNewDocument();
    }

    public void selectAll()
    {
        textPane.selectAll();
    }

    public void help()
    {
        JOptionPane.showMessageDialog(this, "DocumentEditor.java\nAuthor: Charles Bell\nVersion: May 25, 2002\nhttp://www.quantumhyperspace.com\nQuantumHyperSpace Programming Services");
    }

    public void showShortcuts()
    {
        String shortcuts = "Navigate in    |  Tab\nNavigate out   |  Ctrl+Tab\nNavigate out backwards    |  Shift+Ctrl+Tab\nMove up/down a line    |  Up/Down Arrown\nMove left/right a component or char    |  Left/Right Arrow\nMove up/down one vertical block    |  PgUp/PgDn\nMove to start/end of line    |  Home/End\nMove to previous/next word    |  Ctrl+Left/Right Arrow\nMove to start/end of data    |  Ctrl+Home/End\nMove left/right one block    |  Ctrl+PgUp/PgDn\nSelect All    |  Ctrl+A\nExtend selection up one line    |  Shift+Up Arrow\nExtend selection down one line    |  Shift+Down Arrow\nExtend selection to beginning of line    |  Shift+Home\nExtend selection to end of line    |  Shift+End\nExtend selection to beginning of data    |  Ctrl+Shift+Home\nExtend selection to end of data    |  Ctrl+Shift+End\nExtend selection left    |  Shift+Right Arrow\nExtend selection right    |  Shift+Right Arrow\nExtend selection up one vertical block    |  Shift+PgUp\nExtend selection down one vertical block    |  Shift+PgDn\nExtend selection left one block    |  Ctrl+Shift+PgUp\nExtend selection right one block    |  Ctrl+Shift+PgDn\nExtend selection left one word    |  Ctrl+Shift+Left Arrow\nExtend selection right one word    |  Ctrl+Shift+Right Arrow\n";
        JOptionPane.showMessageDialog(this, shortcuts);
    }

    public void aboutQuantumHyperSpace()
    {
        JOptionPane.showMessageDialog(this, "QuantumHyperSpace Programming Services\nhttp://www.quantumhyperspace.com\nemail: support@quantumhyperspace.com\n                     or \nemail: charles@quantumhyperspace.com\n", "QuantumHyperSpace", 1, new ImageIcon("quantumhyperspace.gif"));
    }


    public static void main(String args[])
    {
	 /*HTMLDocumentEditor editor =*/ new HTMLDocumentEditor();
    }


    private HTMLDocument document;
    private JTextPane textPane;
    private boolean debug;
    private File currentFile;
    protected UndoableEditListener undoHandler;
    protected UndoManager undo;
    private UndoAction undoAction;
    private RedoAction redoAction;
    private Action cutAction;
    private Action copyAction;
    private Action pasteAction;
    private Action boldAction;
    private Action underlineAction;
    private Action italicAction;
    private Action insertBreakAction;
    private javax.swing.text.html.HTMLEditorKit.InsertHTMLTextAction unorderedListAction;
    private javax.swing.text.html.HTMLEditorKit.InsertHTMLTextAction bulletAction;


}
