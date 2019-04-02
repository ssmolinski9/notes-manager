package category;

import core.Database;
import core.Key;
import note.Note;

import java.util.HashMap;

/**
 * Category model as containers for notes
 * @version 1.0
 */
public class Category {
    private final String name;
    private final String color;

    private Database database;
    private HashMap<Key, Note> notes;

    /**
     * Creates category model based only on name, gets color from db
     * @param name name of the category
     */
    public Category(String name) {
        this.name = name;
        this.database = Database.getInstance();
        this.notes = new HashMap<>();
        this.color = database.selectCategories().get(name).getColor();

        getNotesFromDatabase();
    }

    /**
     * Creates category model
     * @param name name of the category
     * @param color color name of the category
     */
    public Category(String name, String color) {
        this.name = name;
        this.color = color;
        this.database = Database.getInstance();
        this.notes = new HashMap<>();

        getNotesFromDatabase();
    }

    /**
     * Adds note to database and build new list
     * @param name name of the note to add.
     * @param color color of the category to render in category view
     * @return 0 if success or -1 if name is not unique in that category or illegal
     */
    int addNote(String name, String color) {
        if(!isNameLegal(name, this.name))
            return -1;

        database.insertNote(name, color, this.name, "Ubuntu", 15);
        getNotesFromDatabase();

        return 0;
    }

    private boolean isNameLegal(String name, String category) {
        return !notes.containsKey(new Key(name, category)) && !name.equals("Enter name");
    }

    /**
     * Removes note with given name from this category
     * @param name name of the note
     */
    public void removeNote(String name) {
        database.removeNote(name, this.name);
        getNotesFromDatabase();
    }

    private void getNotesFromDatabase() {
        notes = database.selectNotes();
    }

    /**
     * Gets the name of this category
     * @return name of the category
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the color of this category
     * @return color of the category
     */
    public String getColor() {
        return color;
    }

    /**
     * Gets notes set
     * @return map with 2D key (category name and note name) as a key and Note object as a value
     */
    HashMap<Key, Note> getNotes() {
        return notes;
    }
}
