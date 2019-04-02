package gui;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Color;
import java.awt.Dimension;

/**
 * Big menu's tile represents single option
 * @version 1.0
 * @see JButton
 */
public class Tile extends JButton {
    private JMenuItem editItem, deleteItem;

    /**
     * Creates JButton with characteristic features
     * like text, color and popup menu
     * @param name text to render inside tile
     * @param color background color
     */
    public Tile(String name, Color color) {
        super(name);
        this.setPreferredSize(new Dimension(240, 240));
        this.setSize(new Dimension(240, 240));
        this.setBackground(color);

        JPopupMenu popupMenu = new JPopupMenu();

        editItem = new JMenuItem("Edit");
        deleteItem = new JMenuItem("Delete");

        if(!name.equals("Add new")) {
            popupMenu.add(editItem);
            popupMenu.add(deleteItem);
        }

        this.setComponentPopupMenu(popupMenu);
    }

    /**
     * Gets item for editing object representing by tile
     * @return JMenuItem entry for editing
     */
    public JMenuItem getEditItem() {
        return editItem;
    }

    /**
     * Gets item for deleting object representing by tile
     * @return JMenuItem entry for deleting
     */
    public JMenuItem getDeleteItem() {
        return deleteItem;
    }
}
