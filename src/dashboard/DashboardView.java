package dashboard;

import gui.Tile;
import category.Category;
import gui.Window;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * View for Dashboard module
 * Contains tiles represents every category in the module
 * @version 1.0
 */
class DashboardView {
    private ArrayList<Tile> tiles;
    private Window wnd;

    /**
     * Initializes tiles list and gets window handler
     */
    DashboardView() {
        this.tiles = new ArrayList<>();
        this.wnd = Window.getInstance();
    }

    /**
     * Renders JPanel with tiles list
     * @param categories complete list of categories to render as tiles
     */
    public void render(HashMap<String, Category> categories) {
        buildTilesList(categories);
        JScrollPane windowPane = prepareWindowPane();
        wnd.getContentPane().add(windowPane);
    }

    private void buildTilesList(HashMap<String, Category> categories) {
        tiles.clear();

        for(Category category : categories.values()) {
            tiles.add(new Tile(category.getName(), castStringToColor(category.getColor())));
        }

        tiles.add(new Tile("Add new", Color.lightGray));
    }

    private Color castStringToColor(String color) {
        try {
            Field field = Class.forName("java.awt.Color").getField(color);
            return (Color)field.get(null);
        } catch (Exception e) {
            return Color.red;
        }
    }

    /**
     * Gets complete list of tiles which are created based on categories
     * @return list of tiles
     */
    ArrayList<Tile> getTiles() {
        return tiles;
    }

    private JScrollPane prepareWindowPane() {
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(panel);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panel.setLayout(new GridBagLayout());

        int rowNO = 0, colNO = 0;
        for(Tile tile : tiles) {
            gbc.gridx = colNO;
            gbc.gridy = rowNO;
            gbc.insets = new Insets(5, 5, 5, 5);
            panel.add(tile, gbc);

            colNO = (colNO + 1) % 3;
            if(colNO == 0) rowNO++;
        }

        return scrollPane;
    }
}
