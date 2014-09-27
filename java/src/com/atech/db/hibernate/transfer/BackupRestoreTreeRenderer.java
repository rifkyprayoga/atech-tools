package com.atech.db.hibernate.transfer;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.UIManager;

import com.atech.graphics.components.tree.CheckNode;
import com.atech.graphics.components.tree.CheckRenderer;

public class BackupRestoreTreeRenderer extends CheckRenderer
{

    /** 
     * getTreeCellRendererComponent
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
            boolean leaf, int row, boolean hasFocus)
    {
        String stringValue = tree.convertValueToText(value, isSelected, expanded, leaf, row, hasFocus);
        setEnabled(tree.isEnabled());
        check.setSelected(((CheckNode) value).isSelected());
        label.setFont(tree.getFont());

        BackupRestoreBase brb = (BackupRestoreBase) ((CheckNode) value).getObject();
        label.setText(brb.getTargetName());
        label.setSelected(isSelected);
        label.setFocus(hasFocus);

        if (leaf)
        {
            label.setIcon(UIManager.getIcon("Tree.leafIcon"));
        }
        else if (expanded)
        {
            label.setIcon(UIManager.getIcon("Tree.openIcon"));
        }
        else
        {
            label.setIcon(UIManager.getIcon("Tree.closedIcon"));
        }
        return this;
    }

}
