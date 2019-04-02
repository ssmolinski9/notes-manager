package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import category.Category;
import note.Note;

/**
 * Singleton class to handling database requests
 * @version 1.1
 */
public class Database {
    private static Database instance = null;
    private Connection connection;

    /**
     * Gets instance of existing database or opens connection
     * @return instance of the database
     */
    public static Database getInstance() {
        if(instance == null) {
            instance = new Database();
        }

        return instance;
    }

    /**
     * Inserts new category into database
     * @param name name for the new category. Must be unique.
     * @param color color for the category background.
     */
    public void insertCategory(String name, String color) {
        String query = "INSERT INTO categories ('name', 'color') VALUES ('" + name + "', '" + color + "');";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Selects all categories from database and puts it into map
     * @return hash map with category's name as string key and category object itself as the value
     */
    public HashMap<String, Category> selectCategories() {
        HashMap<String, Category> categories = new HashMap<>();
        String query = "SELECT * FROM categories";

        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {
                categories.put(set.getString("name"), new Category(set.getString("name"), set.getString("color")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return categories;
    }

    /**
     * Removes category from database with given name
     * @param name name of the category to delete
     */
    public void removeCategory(String name) {
        String query = "DELETE FROM categories WHERE name='" + name + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Removes all notes in specified category
     * @param name name of the category from notes will be deleted
     */
    public void removeNotesInCategory(String name) {
        String query = "DELETE FROM notes WHERE category='" + name + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Inserts new note into database
     * @param name name of the note. Must be unique in same category.
     * @param color color of the background.
     * @param category category's name where note will be displayed.
     * @param fontFamily name of the font family which is using in note.
     * @param fontSize font size in pt which is using in note.
     */
    public void insertNote(String name, String color, String category, String fontFamily, int fontSize) {
        String query = "INSERT INTO notes ('name', 'color', 'category', 'fontfamily', 'fontsize') VALUES ('" + name + "', '" + color + "', '" + category + "', '" + fontFamily + "', '" + fontSize + "');";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets every note to the map.
     * @return hash map with Key object (it contains note's title and note's category name) as a key and Note object itself as the value
     */
    public HashMap<Key, Note> selectNotes() {
        HashMap<Key, Note> notes = new HashMap<>();
        String query = "SELECT * FROM notes";

        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(query);

            while(set.next()) {
                String name = set.getString("name");
                String color = set.getString("color");
                String category = set.getString("category");
                String body = set.getString("body");
                String fontFamily = set.getString("fontfamily");
                String fontSize = set.getString("fontsize");

                notes.put(new Key(name, category), new Note(name, category, color, fontFamily, body, fontSize));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return notes;
    }

    /**
     * Updates note's information.
     * @param oldCategory old category's name to identify note.
     * @param newCategory name of the new category.
     */
    public void notesUpdateCategory(String oldCategory, String newCategory) {
        String query = "UPDATE notes SET category='" + newCategory + "' WHERE category='" + oldCategory + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Updates note's all information
     * @param name name of the note for update
     * @param category name of the category for update
     * @param nname new name of the note
     * @param body new body context of the note
     * @param fontFamily new family of the font used in note
     * @param fontSize new size of the font used in note
     */
    public void notesUpdateAll(String name, String category, String nname, String body, String fontFamily, String fontSize, String color) {
        String query = "UPDATE notes SET name='" + nname + "', " +
                "body='" + body + "', " +
                "fontfamily='" + fontFamily + "', " +
                "fontsize='" + fontSize + "', " +
                "color='" + color + "' " +
                "WHERE name='" + name + "' AND category='" + category + "';";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Removes category from database with given name in given category
     * @param name title of the note to delete
     * @param category specified category where note to delete is
     */
    public void removeNote(String name, String category) {
        String query = "DELETE FROM notes WHERE name='" + name + "' AND category='" + category + "'";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Closes connection with database
     */
    public void closeConnection() {
        try {
            if(connection != null)
                connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private Database() {
        openConnection();
        createDefaultSchema();
    }

    private void openConnection() {
        connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void createDefaultSchema() {
        try {
            Statement statement = connection.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS categories (" +
                    "name varchar PRIMARY KEY," +
                    "color varchar" +
                    ");");

            statement.execute("CREATE TABLE IF NOT EXISTS notes (" +
                    "name varchar," +
                    "color varchar," +
                    "category varchar," +
                    "body text," +
                    "fontfamily varchar," +
                    "fontsize varchar," +
                    "CONSTRAINT PK_NOTES PRIMARY KEY ( name, category ), " +
                    "FOREIGN KEY (category) REFERENCES categories(name) ON DELETE CASCADE" +
                    ");");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
