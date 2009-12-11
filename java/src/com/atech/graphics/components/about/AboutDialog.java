package com.atech.graphics.components.about;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.atech.i18n.I18nControlAbstract;
import com.atech.utils.ATSwingUtils;

// TODO: Auto-generated Javadoc
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


public class AboutDialog extends javax.swing.JDialog 
{
	
    private static final long serialVersionUID = 2523155552647312422L;
    
    /**
     * The m_ic.
     */
    protected I18nControlAbstract m_ic = null;
    
    /**
     * The about_image.
     */
    protected String about_image = null;
    
    /**
     * The m_utils.
     */
    ATSwingUtils m_utils = null;

    private boolean display_properties = true;
    private boolean display_dialog_centered = true;

    private int licence_type = LicenceInfo.NO_LICENCE;

    private JTabbedPane tabbed_pane;


    private JFrame parent = null;
    private JPanel main_panel;
    private JButton close_button;

    private ArrayList<CreditsGroup> credits = null;
    private ArrayList<LibraryInfoEntry> libraries = null;
    private ArrayList<FeaturesGroup> features = null;

    /**
     * The title.
     */
    String title;

    

    private ArrayList<AboutCustomPanel> tabs_before = null;
    private ArrayList<AboutCustomPanel> tabs_after = null;




    // tabs: about, credits, licence, libs (cst), settings

    /**
     * Instantiates a new about dialog.
     * 
     * @param parent the parent
     * @param modal the modal
     * @param ic the ic
     * @param about_text the about_text
     * @param libs the libs
     * @param type_licence the type_licence
     * @param custom the custom
     * @param show_settings the show_settings
     */
    public AboutDialog(JFrame parent, boolean modal, I18nControlAbstract ic,
                       String about_text, ArrayList<LibraryInfoEntry> libs, int type_licence, String custom, boolean show_settings)
    {
        super(parent, modal);
        this.m_ic = ic;
        this.parent = parent;

        init();

//        initCustom();
//        initComponents();


        // Open in center...
        ATSwingUtils.centerJDialog(this, parent);

    }


    






    /**
     * Instantiates a new about dialog.
     * 
     * @param parent the parent
     * @param modal the modal
     * @param ic the ic
     */
    public AboutDialog(javax.swing.JFrame parent, boolean modal, I18nControlAbstract ic)
    {
        super(parent, modal);
        //m_utils = new ATSwingUtils();
        this.m_ic = ic;
        this.parent = parent;

        init();
        //initCustom();
        //initComponents();

        /*
        this.setTitle(m_ic.getMessage("ABOUT"));

        this.setSize(354, 460);
        */

        // Open in center...
        //ATSwingUtils.centerJDialog(this, parent);

/*
	this.jEditorPaneAbout.setContentType("text/html");
	this.jEditorPaneAbout.setText("<HTML><body><font face=\"SansSerif\" size=\"3\"><center><b>" + m_ic.getMessage("PIS_TITLE") +"</b><br>&nbsp;&nbsp;(c) 2005-2007 by GGC Team<br><a href=\"mailto:andy@t-2.net\">andy@t-2.net</a><br><A HREF=\"http://www.atech-software.com/projects/pis/\">http://www.atech-software.com/projects/pis/</A><br>" + m_ic.getMessage("LICENCE") + " LGPL<br></font></body></html>");
	this.jEditorPane1.setContentType("text/html");
	//this.jEditorPane1.setText ("<h1>The GNU General Public License (GPL)</h1>\n\n<h2>Version 2, June 1991</h2>\n\n<tt>\n\n<p> Copyright (C) 1989, 1991 Free Software Foundation, Inc.<br>\n                       59 Temple Place, Suite 330, Boston, MA  02111-1307  USA</p>\n<p> Everyone is permitted to copy and distribute verbatim copies<br>\n of this license document, but changing it is not allowed.</p>\n\n\t\t<strong><p>Preamble</p></strong>\n\n  <p>The licenses for most software are designed to take away your\nfreedom to share and change it.  By contrast, the GNU General Public\nLicense is intended to guarantee your freedom to share and change free\nsoftware--to make sure the software is free for all its users.  This\nGeneral Public License applies to most of the Free Software\nFoundation's software and to any other program whose authors commit to\nusing it.  (Some other Free Software Foundation software is covered by\nthe GNU Library General Public License instead.)  You can apply it to\nyour programs, too.</p>\n\n  <p>When we speak of free software, we are referring to freedom, not\nprice.  Our General Public Licenses are designed to make sure that you\nhave the freedom to distribute copies of free software (and charge for\nthis service if you wish), that you receive source code or can get it\nif you want it, that you can change the software or use pieces of it\nin new free programs; and that you know you can do these things.</p>\n<p>\n  To protect your rights, we need to make restrictions that forbid\nanyone to deny you these rights or to ask you to surrender the rights.\nThese restrictions translate to certain responsibilities for you if you\ndistribute copies of the software, or if you modify it.</p>\n\n  <p>For example, if you distribute copies of such a program, whether\ngratis or for a fee, you must give the recipients all the rights that\nyou have.  You must make sure that they, too, receive or can get the\nsource code.  And you must show them these terms so they know their\nrights.</p>\n\n  <p>We protect your rights with two steps: (1) copyright the software, and\n(2) offer you this license which gives you legal permission to copy,\ndistribute and/or modify the software.</p>\n\n  <p>Also, for each author's protection and ours, we want to make certain\nthat everyone understands that there is no warranty for this free\nsoftware.  If the software is modified by someone else and passed on, we\nwant its recipients to know that what they have is not the original, so\nthat any problems introduced by others will not reflect on the original\nauthors' reputations.</p>\n\n  <p>Finally, any free program is threatened constantly by software\npatents.  We wish to avoid the danger that redistributors of a free\nprogram will individually obtain patent licenses, in effect making the\nprogram proprietary.  To prevent this, we have made it clear that any\npatent must be licensed for everyone's free use or not licensed at all.</p>\n\n  <p>The precise terms and conditions for copying, distribution and\nmodification follow.</p>\n\n\t\t    <strong><p>TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION</p></strong>\n\n  <p><strong>0</strong>. This License applies to any program or other work which contains\na notice placed by the copyright holder saying it may be distributed\nunder the terms of this General Public License.  The \"Program\", below,\nrefers to any such program or work, and a \"work based on the Program\"\nmeans either the Program or any derivative work under copyright law:\nthat is to say, a work containing the Program or a portion of it,\neither verbatim or with modifications and/or translated into another\nlanguage.  (Hereinafter, translation is included without limitation in\nthe term \"modification\".)  Each licensee is addressed as \"you\".</p>\n\n<p>Activities other than copying, distribution and modification are not\ncovered by this License; they are outside its scope.  The act of\nrunning the Program is not restricted, and the output from the Program\nis covered only if its contents constitute a work based on the\nProgram (independent of having been made by running the Program).\nWhether that is true depends on what the Program does.</p>\n\n  <p><strong>1</strong>. You may copy and distribute verbatim copies of the Program's\nsource code as you receive it, in any medium, provided that you\nconspicuously and appropriately publish on each copy an appropriate\ncopyright notice and disclaimer of warranty; keep intact all the\nnotices that refer to this License and to the absence of any warranty;\nand give any other recipients of the Program a copy of this License\nalong with the Program.</p>\n\n<p>You may charge a fee for the physical act of transferring a copy, and\nyou may at your option offer warranty protection in exchange for a fee.</p>\n\n<p>  <strong>2</strong>. You may modify your copy or copies of the Program or any portion\nof it, thus forming a work based on the Program, and copy and\ndistribute such modifications or work under the terms of Section 1\nabove, provided that you also meet all of these conditions:</p>\n\n<blockquote>\n    <p>a) You must cause the modified files to carry prominent notices\n    stating that you changed the files and the date of any change.</p>\n\n    <p>b) You must cause any work that you distribute or publish, that in\n    whole or in part contains or is derived from the Program or any\n    part thereof, to be licensed as a whole at no charge to all third\n    parties under the terms of this License.</p>\n\n    <p>c) If the modified program normally reads commands interactively\n    when run, you must cause it, when started running for such\n    interactive use in the most ordinary way, to print or display an\n    announcement including an appropriate copyright notice and a\n    notice that there is no warranty (or else, saying that you provide\n    a warranty) and that users may redistribute the program under\n    these conditions, and telling the user how to view a copy of this\n    License.  (Exception: if the Program itself is interactive but\n    does not normally print such an announcement, your work based on\n    the Program is not required to print an announcement.)</p></blockquote>\n\n<p>These requirements apply to the modified work as a whole.  If\nidentifiable sections of that work are not derived from the Program,\nand can be reasonably considered independent and separate works in\nthemselves, then this License, and its terms, do not apply to those\nsections when you distribute them as separate works.  But when you\ndistribute the same sections as part of a whole which is a work based\non the Program, the distribution of the whole must be on the terms of\nthis License, whose permissions for other licensees extend to the\nentire whole, and thus to each and every part regardless of who wrote it.</p>\n\n<p>Thus, it is not the intent of this section to claim rights or contest\nyour rights to work written entirely by you; rather, the intent is to\nexercise the right to control the distribution of derivative or\ncollective works based on the Program.</p>\n\n<p>In addition, mere aggregation of another work not based on the Program\nwith the Program (or with a work based on the Program) on a volume of\na storage or distribution medium does not bring the other work under\nthe scope of this License.</p>\n\n  <p><strong>3</strong>. You may copy and distribute the Program (or a work based on it,\nunder Section 2) in object code or executable form under the terms of\nSections 1 and 2 above provided that you also do one of the following:</p>\n<blockquote>\n    <p>a) Accompany it with the complete corresponding machine-readable\n    source code, which must be distributed under the terms of Sections\n    1 and 2 above on a medium customarily used for software interchange; or,</p>\n\n   <p> b) Accompany it with a written offer, valid for at least three\n    years, to give any third party, for a charge no more than your\n    cost of physically performing source distribution, a complete\n    machine-readable copy of the corresponding source code, to be\n    distributed under the terms of Sections 1 and 2 above on a medium\n    customarily used for software interchange; or,</p>\n\n    <p>c) Accompany it with the information you received as to the offer\n    to distribute corresponding source code.  (This alternative is\n    allowed only for noncommercial distribution and only if you\n    received the program in object code or executable form with such\n    an offer, in accord with Subsection b above.)</p></blockquote>\n\n<p>The source code for a work means the preferred form of the work for\nmaking modifications to it.  For an executable work, complete source\ncode means all the source code for all modules it contains, plus any\nassociated interface definition files, plus the scripts used to\ncontrol compilation and installation of the executable.  However, as a\nspecial exception, the source code distributed need not include\nanything that is normally distributed (in either source or binary\nform) with the major components (compiler, kernel, and so on) of the\noperating system on which the executable runs, unless that component\nitself accompanies the executable.</p>\n\n<p>If distribution of executable or object code is made by offering\naccess to copy from a designated place, then offering equivalent\naccess to copy the source code from the same place counts as\ndistribution of the source code, even though third parties are not\ncompelled to copy the source along with the object code.</p>\n\n  <p><strong>4</strong>. You may not copy, modify, sublicense, or distribute the Program\nexcept as expressly provided under this License.  Any attempt\notherwise to copy, modify, sublicense or distribute the Program is\nvoid, and will automatically terminate your rights under this License.\nHowever, parties who have received copies, or rights, from you under\nthis License will not have their licenses terminated so long as such\nparties remain in full compliance.</p>\n\n <p> <strong>5</strong>. You are not required to accept this License, since you have not\nsigned it.  However, nothing else grants you permission to modify or\ndistribute the Program or its derivative works.  These actions are\nprohibited by law if you do not accept this License.  Therefore, by\nmodifying or distributing the Program (or any work based on the\nProgram), you indicate your acceptance of this License to do so, and\nall its terms and conditions for copying, distributing or modifying\nthe Program or works based on it.</p>\n\n  <p><strong>6</strong>. Each time you redistribute the Program (or any work based on the\nProgram), the recipient automatically receives a license from the\noriginal licensor to copy, distribute or modify the Program subject to\nthese terms and conditions.  You may not impose any further\nrestrictions on the recipients' exercise of the rights granted herein.\nYou are not responsible for enforcing compliance by third parties to\nthis License.</p>\n\n  <p><strong>7</strong>. If, as a consequence of a court judgment or allegation of patent\ninfringement or for any other reason (not limited to patent issues),\nconditions are imposed on you (whether by court order, agreement or\notherwise) that contradict the conditions of this License, they do not\nexcuse you from the conditions of this License.  If you cannot\ndistribute so as to satisfy simultaneously your obligations under this\nLicense and any other pertinent obligations, then as a consequence you\nmay not distribute the Program at all.  For example, if a patent\nlicense would not permit royalty-free redistribution of the Program by\nall those who receive copies directly or indirectly through you, then\nthe only way you could satisfy both it and this License would be to\nrefrain entirely from distribution of the Program.</p>\n\n<p>If any portion of this section is held invalid or unenforceable under\nany particular circumstance, the balance of the section is intended to\napply and the section as a whole is intended to apply in other\ncircumstances.</p>\n\n<p>It is not the purpose of this section to induce you to infringe any\npatents or other property right claims or to contest validity of any\nsuch claims; this section has the sole purpose of protecting the\nintegrity of the free software distribution system, which is\nimplemented by public license practices.  Many people have made\ngenerous contributions to the wide range of software distributed\nthrough that system in reliance on consistent application of that\nsystem; it is up to the author/donor to decide if he or she is willing\nto distribute software through any other system and a licensee cannot\nimpose that choice.</p>\n<p>\nThis section is intended to make thoroughly clear what is believed to\nbe a consequence of the rest of this License.</p>\n\n<p>  <strong>8</strong>. If the distribution and/or use of the Program is restricted in\ncertain countries either by patents or by copyrighted interfaces, the\noriginal copyright holder who places the Program under this License\nmay add an explicit geographical distribution limitation excluding\nthose countries, so that distribution is permitted only in or among\ncountries not thus excluded.  In such case, this License incorporates\nthe limitation as if written in the body of this License.</p>\n<p>\n  <strong>9</strong>. The Free Software Foundation may publish revised and/or new versions\nof the General Public License from time to time.  Such new versions will\nbe similar in spirit to the present version, but may differ in detail to\naddress new problems or concerns.</p>\n\n<p>Each version is given a distinguishing version number.  If the Program\nspecifies a version number of this License which applies to it and \"any\nlater version\", you have the option of following the terms and conditions\neither of that version or of any later version published by the Free\nSoftware Foundation.  If the Program does not specify a version number of\nthis License, you may choose any version ever published by the Free Software\nFoundation.</p>\n\n  <p><strong>10</strong>. If you wish to incorporate parts of the Program into other free\nprograms whose distribution conditions are different, write to the author\nto ask for permission.  For software which is copyrighted by the Free\nSoftware Foundation, write to the Free Software Foundation; we sometimes\nmake exceptions for this.  Our decision will be guided by the two goals\nof preserving the free status of all derivatives of our free software and\nof promoting the sharing and reuse of software generally.</p>\n\n<p><strong>NO WARRANTY</strong></p>\n\n  <p><strong>11</strong>. BECAUSE THE PROGRAM IS LICENSED FREE OF CHARGE, THERE IS NO WARRANTY\nFOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW.  EXCEPT WHEN\nOTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES\nPROVIDE THE PROGRAM \"AS IS\" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED\nOR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF\nMERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.  THE ENTIRE RISK AS\nTO THE QUALITY AND PERFORMANCE OF THE PROGRAM IS WITH YOU.  SHOULD THE\nPROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF ALL NECESSARY SERVICING,\nREPAIR OR CORRECTION.</p>\n\n  <p><strong>12</strong>. IN NO EVENT UNLESS REQUIRED BY APPLICABLE LAW OR AGREED TO IN WRITING\nWILL ANY COPYRIGHT HOLDER, OR ANY OTHER PARTY WHO MAY MODIFY AND/OR\nREDISTRIBUTE THE PROGRAM AS PERMITTED ABOVE, BE LIABLE TO YOU FOR DAMAGES,\nINCLUDING ANY GENERAL, SPECIAL, INCIDENTAL OR CONSEQUENTIAL DAMAGES ARISING\nOUT OF THE USE OR INABILITY TO USE THE PROGRAM (INCLUDING BUT NOT LIMITED\nTO LOSS OF DATA OR DATA BEING RENDERED INACCURATE OR LOSSES SUSTAINED BY\nYOU OR THIRD PARTIES OR A FAILURE OF THE PROGRAM TO OPERATE WITH ANY OTHER\nPROGRAMS), EVEN IF SUCH HOLDER OR OTHER PARTY HAS BEEN ADVISED OF THE\nPOSSIBILITY OF SUCH DAMAGES.</p>\n\n\t\t     <p>END OF TERMS AND CONDITIONS</p>\n\n\n\n\n\n\n\n\n<!-- START OF 'HOW TO APPLY' SECTION -->\n<p>\n<strong> How to Apply These Terms to Your New Programs</strong></p>\n\n<p>  If you develop a new program, and you want it to be of the greatest\npossible use to the public, the best way to achieve this is to make it\nfree software which everyone can redistribute and change under these terms.</p>\n\n <p> To do so, attach the following notices to the program.  It is safest\nto attach them to the start of each source file to most effectively\nconvey the exclusion of warranty; and each file should have at least\nthe \"copyright\" line and a pointer to where the full notice is found.</p>\n\n\n<blockquote>\n    <p>one line to give the program's name and a brief idea of what it does.<br>\n    Copyright (C) <year>  <name of author></p>\n\n    <p>This program is free software; you can redistribute it and/or modify\n    it under the terms of the GNU General Public License as published by\n    the Free Software Foundation; either version 2 of the License, or\n    (at your option) any later version.</p>\n\n  <p>  This program is distributed in the hope that it will be useful,\n    but WITHOUT ANY WARRANTY; without even the implied warranty of\n    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n    GNU General Public License for more details.</p>\n\n    <p>You should have received a copy of the GNU General Public License\n    along with this program; if not, write to the Free Software\n    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA</p>\n</blockquote>\n<p>\nAlso add information on how to contact you by electronic and paper mail.</p>\n\n<p>If the program is interactive, make it output a short notice like this\nwhen it starts in an interactive mode:</p>\n\n<blockquote>\n   <p> Gnomovision version 69, Copyright (C) year name of author\n    Gnomovision comes with ABSOLUTELY NO WARRANTY; for details type `show w'.\n    This is free software, and you are welcome to redistribute it\n    under certain conditions; type `show c' for details.</p>\n</blockquote>\n\n<p>The hypothetical commands `show w' and `show c' should show the appropriate\nparts of the General Public License.  Of course, the commands you use may\nbe called something other than `show w' and `show c'; they could even be\nmouse-clicks or menu items--whatever suits your program.</p>\n<p>\nYou should also get your employer (if you work as a programmer) or your\nschool, if any, to sign a \"copyright disclaimer\" for the program, if\nnecessary.  Here is a sample; alter the names:</p>\n\n<blockquote>\n  <p>Yoyodyne, Inc., hereby disclaims all copyright interest <br>in the program\n  `Gnomovision' (which makes passes at compilers)<br>written by James Hacker.</p>\n\n  <p>signature of Ty Coon, 1 April 1989<br>\n  Ty Coon, President of Vice</p>\n  </blockquote>\n\n<p>This General Public License does not permit incorporating your program into\nproprietary programs.  If your program is a subroutine library, you may\nconsider it more useful to permit linking proprietary applications with the\nlibrary.  If this is what you want to do, use the GNU Library General\nPublic License instead of this License.</p>\n\n");
	//jEditorPane2.setText("<center>\n<font face=\"Arial\" size=\"5\"><b>iReport Credits</b></font><br><br>\n<font face=\"Arial\" size=\"3\"><b>Thanks to all persons that work to make this software available.</b></font><br><br>\n<font face=\"Arial\" size=\"3\"><b>Project lead</b></font><br>\n<font face=\"Arial\" size=\"3\">Giulio Toffoli (giulio at jaspersoft.com)</font><br><br> \n<font face=\"Arial\" size=\"3\"><b>Contributors</b></font><br>\n<font face=\"Arial\" size=\"3\">Alexander (akhorahil at users.sourceforge.net) </font><br>\n<font face=\"Arial\" size=\"3\">Andre Legendre (andleg2000 at users.sourceforge.net) </font><br>\n<font face=\"Arial\" size=\"3\">Wade Chandler (brainjava at users.sourceforge.net) </font><br>\n<font face=\"Arial\" size=\"3\">Craig B Spengler (craigbs at users.sourceforge.net) </font><br>\n<font face=\"Arial\" size=\"3\">David Walters (david1203 at users.sourceforge.net) </font><br>\n<font face=\"Arial\" size=\"3\">ErtanO (ertano at users.sourceforge.net) </font><br>\n<font face=\"Arial\" size=\"3\">G Raghavan (geeraghav at users.sourceforge.net) </font><br>\n<font face=\"Arial\" size=\"3\">Octavio Luna (itaqua at users.sourceforge.net) </font><br>\n<font face=\"Arial\" size=\"3\">Kees Kuip (keeskuip at users.sourceforge.net) </font><br>\n<font face=\"Arial\" size=\"3\">Peter Henderson (phenderson at users.sourceforge.net) </font><br>\n<font face=\"Arial\" size=\"3\">Vinod Kumar Singh (vinodsingh at users.sourceforge.net) </font><br>\n<font face=\"Arial\" size=\"3\">Heiko Wenzel (webhsw at users.sourceforge.net) </font><br>\n<font face=\"Arial\" size=\"3\">Egon R Pereira (egonrp at users.sourceforge.net)<br>\n<font face=\"Arial\" size=\"3\">Robert F Lamping (rlamping at users.sourceforge.net) </font><br><br><b>Icons created by</b></font><br>\n<font face=\"Arial\" size=\"3\">Mark James (mjames at gmail.com)<br>http://www.famfamfam.com/lab/icons/silk/</font><br>");

	//jEditorPane1.setCaretPosition(0);

	Properties system_props = System.getProperties();
	Enumeration system_props_enum = system_props.keys();

	while (system_props_enum.hasMoreElements())
	{
	    String key=(String)system_props_enum.nextElement();
	    if (key.trim().length() == 0) 
		continue;

	    ((javax.swing.table.DefaultTableModel)jTableProperties.getModel()).addRow(new Object[]{key, system_props.getProperty(key,"")});
	}
	jTableProperties.updateUI();
*/


//	createLicence();


	

	//this.setVisible(true);

    }


    /**
     * Inits the.
     */
    public void init()
    {

        m_utils = new ATSwingUtils();

        title = "About"; //m_ic.getMessage("ABOUT");

        // tabbed
        tabbed_pane = new JTabbedPane();


        main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());

        

        javax.swing.KeyStroke escape =  javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0, false);
        javax.swing.Action escapeAction = new javax.swing.AbstractAction() {
        	
            private static final long serialVersionUID = -5105934944859361653L;

            public void actionPerformed(ActionEvent e) {
            setVisible(false);
            }
        };

        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(escape, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);

        this.close_button = new JButton("Close"); //m_ic.getMessage("CLOSE"));
        this.close_button.addActionListener(new java.awt.event.ActionListener() 
            {
                public void actionPerformed(java.awt.event.ActionEvent evt) 
                {
                    setVisible(false);
                }
            });

        this.main_panel.add(this.close_button, BorderLayout.SOUTH);


        //to make the default button ...
        this.getRootPane().setDefaultButton(this.close_button);


        this.main_panel.add(tabbed_pane, java.awt.BorderLayout.CENTER);

        getContentPane().add(main_panel, java.awt.BorderLayout.CENTER);

        //pack();


    }



    /**
     * Show about.
     */
    public void showAbout()
    {
        this.setVisible(true);
    }


    /**
     * Sets the display properties.
     * 
     * @param display the new display properties
     */
    public void setDisplayProperties(boolean display)
    {
        this.display_properties = display;
    }


    /**
     * Sets the display dialog centered.
     * 
     * @param display the new display dialog centered
     */
    public void setDisplayDialogCentered(boolean display)
    {
        this.display_dialog_centered = display;
    }


    /** 
     * setSize
     */
    public void setSize(int width, int height)
    {
        super.setSize(width, height);

        if (this.display_dialog_centered)
        {
            // Open in center...
            ATSwingUtils.centerJDialog(this, parent);
        }

    }

    /** 
     * setTitle
     */
    public void setTitle(String title)
    {
        this.title = title;
        super.setTitle(title);
    }


    /**
     * Sets the licence type.
     * 
     * @param licence_type the new licence type
     */
    public void setLicenceType(int licence_type)
    {
        this.licence_type = licence_type;
    }


    /**
     * Sets the credits.
     * 
     * @param lst the new credits
     */
    public void setCredits(ArrayList<CreditsGroup> lst)
    {
        this.credits = lst;
    }

    /**
     * Sets the libraries.
     * 
     * @param lie the new libraries
     */
    public void setLibraries(ArrayList<LibraryInfoEntry> lie)
    {
        this.libraries = lie;
    }


    /**
     * Sets the features.
     * 
     * @param lie the new features
     */
    public void setFeatures(ArrayList<FeaturesGroup> lie)
    {
        this.features = lie;
    }
    
    
    
    /**
     * The Constant PLACEMENT_BEFORE_STATIC_TABS.
     */
    public static final int PLACEMENT_BEFORE_STATIC_TABS = 1;
    
    /**
     * The Constant PLACEMENT_AFTER_STATIC_TABS.
     */
    public static final int PLACEMENT_AFTER_STATIC_TABS = 2;

    /**
     * Adds the custom panel.
     * 
     * @param place the place
     * @param panel the panel
     */
    public void addCustomPanel(int place, AboutCustomPanel panel)
    {
        if (place == AboutDialog.PLACEMENT_BEFORE_STATIC_TABS)
        {
            if (this.tabs_before == null)
                this.tabs_before = new ArrayList<AboutCustomPanel>();

            this.tabs_before.add(panel);
        }
        else
        {
            if (this.tabs_after == null)
                this.tabs_after = new ArrayList<AboutCustomPanel>();

            this.tabs_after.add(panel);
        }
    }

    /*
    private void addPanelInternal(ArrayList<AboutCustomPanel> lst, AboutCustomPanel panel)
    {
        if (lst == null)
            lst = new ArrayList<AboutCustomPanel>();

        lst.add(panel);
    }*/

    private void addPanelToTabs(ArrayList<AboutCustomPanel> lst)
    {
        //System.out.println("Loading custom tabs");

        if (lst==null)
        {
            //System.out.println("Custom == null");
            return;
        }

        for(int i=0; i<lst.size(); i++)
        {
            this.tabbed_pane.add(lst.get(i).getTabName(), lst.get(i).getTabPanel());
        }

    }


    /**
     * Creates the about.
     */
    public void createAbout()
    {

        addPanelToTabs(this.tabs_before);
        
        if ((this.features!=null) && (this.features.size()>0))
        {
            FeaturesInfo ci = new FeaturesInfo(m_ic, this.features);
            this.tabbed_pane.add(ci.getTabName(), ci.getTabPanel());
        }
        
        if ((this.libraries!=null) && (this.libraries.size()>0))
        {
            LibraryInfo li = new LibraryInfo(m_ic, this.libraries);
            this.tabbed_pane.add(li.getTabName(), li.getTabPanel());
        }

        if ((this.credits!=null) && (this.credits.size()>0))
        {
            CreditsInfo ci = new CreditsInfo(m_ic, this.credits);
            this.tabbed_pane.add(ci.getTabName(), ci.getTabPanel());
        }

        if (this.licence_type>0)
        {
            LicenceInfo li = new LicenceInfo(m_ic, this.licence_type);
            this.tabbed_pane.add(li.getTabName(), li.getTabPanel());
        }

        if (this.display_properties)
        {
            this.createSystemProperties();
        }

        addPanelToTabs(this.tabs_after);

    }



    /**
     * Creates the system properties.
     */
    public void createSystemProperties()
    {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTableProperties = new javax.swing.JTable();

        jTableProperties.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                m_ic.getMessage("PROPERTY"), m_ic.getMessage("VALUE")
            }
        ) {

            private static final long serialVersionUID = -503398710319780995L;
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableProperties.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        jScrollPane3.setViewportView(jTableProperties);

        tabbed_pane.addTab(m_ic.getMessage("SYSTEM_PROPERTIES"), jScrollPane3);


        Properties system_props = System.getProperties();
        Enumeration<Object> system_props_enum = system_props.keys();

        while (system_props_enum.hasMoreElements())
        {
            String key=(String)system_props_enum.nextElement();
            if (key.trim().length() == 0) 
            continue;

            ((javax.swing.table.DefaultTableModel)jTableProperties.getModel()).addRow(new Object[]{key, system_props.getProperty(key,"")});
        }
        jTableProperties.updateUI();

    }






    /**
     * Inits the custom.
     */
    public void initCustom()
    {
    }


    /**
     * Adds the about tab.
     * 
     * @param panel the panel
     */
    public void addAboutTab(JPanel panel)
    {

    }







    
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableProperties;



}
