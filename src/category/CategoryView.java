package category;

import core.Key;
import gui.Tile;
import gui.Window;
import note.Note;

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
 * View for Category module
 * Contains tiles represents every note in this specified category
 * @version 1.0
 */
public class CategoryView {
    private String name;
    private ArrayList<Tile> tiles;
    private Window wnd;

    /**
     * Initializes tiles list and gets window handler
     * @param name name of the category to identify
     */
    CategoryView(String name) {
        this.name = name;
        this.tiles = new ArrayList<>();
        this.wnd = Window.getInstance();
    }

    /**
     * Renders JPanel with tiles list
     * @param notes complete list of notes to render as tiles
     */
    public void render(HashMap<Key, Note> notes) {
        buildTilesList(notes);
        JScrollPane windowPane = prepareWindowPane();
        wnd.getContentPane().add(windowPane);
    }

    /**
     * Gets complete list of tiles which are created based on notes list
     * @return list of tiles
     */
    ArrayList<Tile> getTiles() {
        return tiles;
    }

    private void buildTilesList(HashMap<Key, Note> notes) {
        tiles.clear();

        for(Note note : notes.values()) {
            if(note.getCategory().equals(name))
                tiles.add(new Tile(note.getName(), castStringToColor(note.getColor())));
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
