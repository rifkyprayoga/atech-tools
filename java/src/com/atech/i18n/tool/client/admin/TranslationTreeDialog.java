package com.atech.i18n.tool.client.admin;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;

import com.atech.graphics.components.EditableAbstractPanel;
import com.atech.i18n.I18nControlAbstract;
import com.atech.i18n.tool.client.DataAccessTT;
import com.atech.i18n.tool.client.db.TTDb;
import com.atech.utils.ATDataAccessAbstract;

/**
 *  Application:   GGC - GNU Gluco Control
 *
 *  See AUTHORS for copyright information.
 * 
 *  This program is free software; you can redistribute it and/or modify it under
 *  the terms of the GNU General Public License as published by the Free Software
 *  Foundation; either version 2 of the License, or (at your option) any later
 *  version.
 * 
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 *  FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 *  details.
 * 
 *  You should have received a copy of the GNU General Public License along with
 *  this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 *  Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 *  Filename:     NutritionTreeDialog 
 *  Description:  Main class for displaying nutrition information.
 * 
 *  Author: andyrozman {andy@atech-software.com}  
 */


public class TranslationTreeDialog extends JDialog implements TreeSelectionListener, MouseListener, ActionListener
{

    private static final long serialVersionUID = 5682055227220084443L;
    private JPanel mainPane;
    private JTree tree;

    private DataAccessTT m_da = null;
    /**
     * I18n Control instance
     */
    public I18nControlAbstract ic = null;
    private EditableAbstractPanel panels[] = null;
    private int selectedPanel = 0;

    /**
     * Tree type
     */
    public int m_tree_type = 1;

    private Hashtable<String, MenuItem> menus;
    boolean selector = false;
    Object selected_object = null;

    PopupMenu pop = new PopupMenu(); // the popup menu you want to use with the
                                     // tree

    /**
     * Constructor 
     * 
     * @param parent 
     * @param da
     * @param type
     */
    public TranslationTreeDialog(JDialog parent, ATDataAccessAbstract da, int type)
    {
        this(parent, da, type, false);
    }
    
    
    /**
     * Constructor 
     * 
     * @param parent 
     * @param da
     * @param type
     */
    public TranslationTreeDialog(JFrame parent, ATDataAccessAbstract da, int type)
    {
        this(parent, da, type, false);
    }

    /**
     * Constructor
     * 
     * @param parent 
     * @param da
     * @param type
     * @param selector
     */
    public TranslationTreeDialog(JFrame parent, ATDataAccessAbstract da, int type, boolean selector)
    {
        super(parent, "", true);
        

        this.selector = selector;
        m_da = DataAccessTT.getInstance();
        ic = m_da.getI18nControlInstance();
        this.m_tree_type = type;

        init();
        
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        
    }


    
    /**
     * Constructor
     * 
     * @param parent 
     * @param da
     * @param type
     * @param selector
     */
    public TranslationTreeDialog(JDialog parent, ATDataAccessAbstract da, int type, boolean selector)
    {
        super(parent, "", true);
        

        this.selector = selector;
        m_da = DataAccessTT.getInstance();
        ic = m_da.getI18nControlInstance();
        this.m_tree_type = type;

        init();
        
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        
    }
    
    
    /**
     * Process Window Event
     * 
     * @see javax.swing.JDialog#processWindowEvent(java.awt.event.WindowEvent)
     */
    public void processWindowEvent(WindowEvent ev)
    {
        if (ev.getID() == WindowEvent.WINDOW_CLOSING)
            this.dispose();
        
        //System.out.println(ev);
        
    }
    
    
    private void init()
    {
        if (selector)
            this.setBounds(120, 90, 740, 560);
        else
            this.setBounds(80, 50, 740, 560);

        setTitle();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 0));

        // Create a tree that allows one selection at a time.
        tree = new JTree();
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.setTreeModel(tree);
        tree.addTreeSelectionListener(this);
        tree.addMouseListener(this);
        tree.add(pop);

        JScrollPane treeView = new JScrollPane(tree);

        mainPane = new JPanel();
        mainPane.setLayout(null);

        // Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT/*
                                                                          * .VERTICAL_SPLIT
                                                                          */);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(mainPane);

        Dimension minimumSize = new Dimension(100, 50);
        mainPane.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(200); // XXX: ignored in some releases
        // of Swing. bug 4101306
        splitPane.setPreferredSize(new Dimension(500, 300));

        panel.add(splitPane);

        createPanels();
        this.add(panel);
        makePanelVisible(0);
        initMenu();

        this.setVisible(true);

    }

    /**
     * Get Type
     * 
     * @return
     */
    public int getType()
    {
        return this.m_tree_type;
    }

    private void setTitle()
    {
        this.setTitle(ic.getMessage("USDA_NUTRITION_DATABASE"));
    }

    private void setTreeModel(JTree tree)
    {
        tree.setModel(new TranslationTreeModel(new TranslationTreeRoot()));
    }

    /**
     * Refresh Tree
     */
    public void refreshTree()
    {
        setTreeModel(this.tree);
    }

    private void createPanels()
    {
/*
        panels = new GGCTreePanel[3];
        // panels = new JPanel[9];

        // System.out.println("getType" + this.getType());

        else if (this.getType() == 2)
        {
            panels = new GGCTreePanel[5];
            panels[0] = new PanelNutritionMain(this);
            panels[1] = new PanelNutritionFoodGroup(this, this.getType());
            panels[2] = new PanelNutritionFood(this);
            panels[3] = new PanelNutritionFoodGroupEdit(this, this.getType());
            panels[4] = new PanelNutritionFoodEdit(this);
        }

        for (int i = 0; i < panels.length; i++)
        {
            mainPane.add(panels[i]);
        }

        makePanelVisible(0);
*/
    }

    /**
     * Panel: Main
     */
    public static final int PANEL_MAIN = 0;
    
    /**
     * Panel: Group
     */
    
    public static final int PANEL_GROUP = 1;
    
    /**
     * Panel: Item
     */
    public static final int PANEL_ITEM = 2;
    
    /**
     * Panel: Group Edit
     */
    public static final int PANEL_GROUP_EDIT = 3;
    
    /**
     * Panel: Item Edit
     */
    public static final int PANEL_ITEM_EDIT = 4;

    /**
     * Makes selected panel visible
     * 
     * @param num 
     */
    public void makePanelVisible(int num)
    {
        /*
        selectedPanel = num;

        for (int i = 0; i < panels.length; i++)
            if (i == num)
                panels[i].setVisible(true);
            else
                panels[i].setVisible(false);
        */
    }

    private EditableAbstractPanel getSelectedPanel()
    {
        return panels[this.selectedPanel];
    }

    Object selected_last_path = null;

    // GGCTreePanel selected_last_panel = null;

    /**
     *   Required by TreeSelectionListener interface. 
     */
    public void valueChanged(TreeSelectionEvent e)
    {

        // System.out.println("panel: " + this.getSelectedPanel());

        if ((this.getSelectedPanel().isPanelEditable()) && (this.getSelectedPanel().hasDataChanged()))
        {
            this.getSelectedPanel().actionWarningAndExecute();

            setTreeModel(this.tree);
            /*
             * tree.cancelEditing(); tree.validate(); tree.repaint();
             * tree.revalidate(); tree.treeDidChange();
             */
        }

        this.selected_last_path = tree.getLastSelectedPathComponent();
        //x this.displayPanel(NutritionTreeDialog.PANEL_VIEW);

    }

    /**
     * Panel: View
     */
    public static int PANEL_VIEW = 0;
    
    /**
     * Panel: Edit
     */
    public static int PANEL_EDIT = 1;
    
    /**
     * Panel: Add
     */
    public static int PANEL_ADD = 2;
    
    /**
     * Panel: Add Item
     */
    public static int PANEL_ADD_ITEM = 3;

    
    private void displayPanel(int special_action)
    {

        System.out.println("display Panel NA");
        /*
        if (this.selected_last_path instanceof GGCTreeRoot)
        {

            if (special_action == NutritionTreeDialog.PANEL_ADD)
            {
                System.out.println("We are in root add)");

                makePanelVisible(NutritionTreeDialog.PANEL_GROUP_EDIT);
                this.panels[NutritionTreeDialog.PANEL_GROUP_EDIT].setParentRoot(this.selected_last_path);

            }
            else
            {
                this.selected_object = null;
                makePanelVisible(NutritionTreeDialog.PANEL_MAIN);

            }

        }
        else if ((this.selected_last_path instanceof FoodGroup) || (this.selected_last_path instanceof MealGroup))
        {
            this.selected_object = null;
            if (special_action == NutritionTreeDialog.PANEL_VIEW)
            {
                makePanelVisible(NutritionTreeDialog.PANEL_GROUP);
                this.panels[NutritionTreeDialog.PANEL_GROUP].setData(this.selected_last_path);
            }
            else if (special_action == NutritionTreeDialog.PANEL_EDIT)
            {
                makePanelVisible(NutritionTreeDialog.PANEL_GROUP_EDIT);
                this.panels[NutritionTreeDialog.PANEL_GROUP_EDIT].setData(this.selected_last_path);

                // System.out.println("FoodGroup Edit failed");
            }
            else if (special_action == NutritionTreeDialog.PANEL_ADD)
            {
                // System.out.println("FoodGroup Add Group failed");
                makePanelVisible(NutritionTreeDialog.PANEL_GROUP_EDIT);
                this.panels[NutritionTreeDialog.PANEL_GROUP_EDIT].setParent(this.selected_last_path);
            }
            else
            {
                // System.out.println("FoodGroup Add Item failed");
                makePanelVisible(NutritionTreeDialog.PANEL_ITEM_EDIT);
                this.panels[NutritionTreeDialog.PANEL_ITEM_EDIT].setParent(this.selected_last_path);
            }
        }
        else if ((this.selected_last_path instanceof FoodDescription) || (this.selected_last_path instanceof Meal))
        {
            selected_object = this.selected_last_path;
            if (special_action == NutritionTreeDialog.PANEL_VIEW)
            {
                makePanelVisible(NutritionTreeDialog.PANEL_ITEM);
                this.panels[NutritionTreeDialog.PANEL_ITEM].setData(this.selected_last_path);
            }
            else if (special_action == NutritionTreeDialog.PANEL_EDIT)
            {
                makePanelVisible(NutritionTreeDialog.PANEL_ITEM_EDIT);
                this.panels[NutritionTreeDialog.PANEL_ITEM_EDIT].setData(this.selected_last_path);
            }

        }
*/
    }


    private Object mouse_selected_object = null;

    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent me)
    {

        if ((me.isPopupTrigger()) || // right click, show popup menu
            (SwingUtilities.isRightMouseButton(me)))
        {
            mouse_selected_object = tree.getClosestPathForLocation(me.getX(), me.getY()).getLastPathComponent();
            tree.setSelectionPath(tree.getClosestPathForLocation(me.getX(), me.getY()));

            //System.out.println("MouseSelected: " + mouse_selected_object);
            //System.out.println(me.getSource());
            createMenu();
            //System.out.println("Mouse: popUp trigger");
            // pop.add(new MenuItem("Test"));
            pop.show(me.getComponent(), me.getX(), me.getY());
        }
        else
        {
            //System.out.println("Mouse: popUp NO trigger");
            //put the image stuff here
        }
    }

    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent me) { }

    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent me) { }

    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent me) { }

    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent me) { }
    
    
    private void initMenu()
    {
        this.menus = new Hashtable<String, MenuItem>();

        String mens[];

        if (this.m_tree_type == 1)
        {
            mens = new String[] { "view", "VIEW", "add_group", "ADD_GROUP", "close", "CLOSE" };

        }
        else if (this.m_tree_type == 2)
        {
            mens = new String[] { "view", "VIEW", "close", "CLOSE", "edit_group", "EDIT_GROUP", "add_group",
                                 "ADD_GROUP", "add_item", "ADD_FOOD_DESCRIPTION", "edit_item", "EDIT_FOOD_DESCRIPTION" };
        }
        else
        {
            mens = new String[] { "view", "VIEW", "close", "CLOSE", "edit_group", "EDIT_GROUP", "add_group",
                                 "ADD_GROUP", "add_item", "ADD_MEAL", "edit_item", "EDIT_MEAL" };
        }

        for (int i = 0; i < mens.length; i += 2)
        {
            MenuItem men = new MenuItem();
            men.setActionCommand(mens[i]);
            men.setLabel(ic.getMessage("PM_NUT_" + mens[i + 1]));
            men.addActionListener(this);

            this.menus.put(mens[i], men);
        }

    }

    boolean made = false;
    int menu_prev_type = 0;

    private void createMenu()
    {
        if (this.m_tree_type == 1)
        {
            if (made)
                return;

            this.pop.add(menus.get("view"));
            this.pop.addSeparator();
            // this.pop.add(menus.get("add_group"));
            // this.pop.addSeparator();
            this.pop.add(menus.get("close"));
            made = true;
        }
        else
        {
            int curr_type = getTreeItemType();

            if (this.menu_prev_type == curr_type)
                return;
            else
            {
                if (curr_type == 1)
                {
                    pop.removeAll();
                    this.pop.add(menus.get("view"));
                    this.pop.addSeparator();
                    this.pop.add(menus.get("add_group"));
                    this.pop.addSeparator();
                    this.pop.add(menus.get("close"));
                    // made = true;

                }
                else if (curr_type == 2)
                {
                    pop.removeAll();

                    pop.add(menus.get("view"));
                    pop.addSeparator();

                    pop.add(menus.get("edit_group"));
                    pop.add(menus.get("add_group"));
                    pop.add(menus.get("add_item"));
                    pop.addSeparator();

                    pop.add(menus.get("close"));

                }
                else if (curr_type == 3)
                {
                    pop.removeAll();

                    pop.add(menus.get("view"));
                    pop.addSeparator();
                    pop.add(menus.get("edit_item"));
                    pop.addSeparator();
                    pop.add(menus.get("close"));
                }
                else
                {
                    pop.removeAll();

                    pop.add(menus.get("view"));
                    pop.addSeparator();
                    pop.add(menus.get("close"));
                }
            }

        }
    }

    private int getTreeItemType()
    {
        /*
        if (this.mouse_selected_object instanceof GGCTreeRoot)
        {
            return 1;
        }
        else
        {
            if (this.m_tree_type == 2)
            {
                if (this.mouse_selected_object instanceof FoodGroup)
                    return 2;
                else
                    return 3;
            }
            else if (this.m_tree_type == 3)
            {
                if (this.mouse_selected_object instanceof MealGroup)
                    return 2;
                else
                    return 3;

            }
            else
            {
                System.out.println("Error on mouse click: Wrong type:" + this.mouse_selected_object);
                return -1;
            }

        }*/
        
        return 0;
        // else

    }

    /**
     * Was Action
     * 
     * @return
     */
    public boolean wasAction()
    {
        return (this.selected_object != null);
    }

    /**
     * Get Selected Object
     * 
     * @return
     */
    public Object getSelectedObject()
    {
        return this.selected_object;
    }

    /**
     * Get Selected Object Type
     * 
     * @return
     */
    public int getSelectedObjectType()
    {
        return this.m_tree_type;
    }

    /**
     * Main
     * 
     * @param args
     */
    public static void main(String args[])
    {
        DataAccessTT da = DataAccessTT.getInstance();

        TTDb db = new TTDb(da);
        db.initDb();

        //da.setDb(db);

        // System.out.println("Load Nutrition #1");
        // db.loadNutritionDb1();

        /*
         * System.out.println("Load Nutrition #2"); db.loadNutritionDb2();
         */
        // System.out.println("Load Meals");
        // db.loadMealsDb();

        // db.initDb();
        // DataAccess da = DataAccess.getInstance();

        // GGCDbLoader loader = new GGCDbLoader(da);
        // loader.start();

        // makeFakeData();

        
         JFrame fr = new JFrame(); //fr.setBounds(0,0,640,480);
         fr.setVisible(true); //da.setParent(fr);
         
        // da.m_nutrition_treeroot = new GGCTreeRoot(1);
        /* NutritionTreeDialog ntd = */
        new TranslationTreeDialog(fr, da, 1);

    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent ae)
    {
        String command = ae.getActionCommand();
/*
        if (command.equals("close"))
        {
        }
        else if (command.equals("view"))
        {
            this.selected_last_path = this.mouse_selected_object;
            this.displayPanel(NutritionTreeDialog.PANEL_VIEW);
        }
        else if (command.equals("add_item"))
        {
            this.selected_last_path = this.mouse_selected_object;
            this.displayPanel(NutritionTreeDialog.PANEL_ADD_ITEM);
        }
        else if (command.equals("add_group"))
        {
            this.selected_last_path = this.mouse_selected_object;
            this.displayPanel(NutritionTreeDialog.PANEL_ADD);
        }
        else if ((command.equals("edit_item")) || (command.equals("edit_group")))
        {
            this.selected_last_path = this.mouse_selected_object;
            this.displayPanel(NutritionTreeDialog.PANEL_EDIT);
        } */

    }

}
