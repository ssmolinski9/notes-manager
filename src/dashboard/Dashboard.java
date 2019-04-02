package dashboard;

import category.Category;
import core.Database;

import java.util.HashMap;

/**
 * Dashboard model as container for categories
 * @version 1.0
 */
class Dashboard {
    private Database database;
    private HashMap<String, Category> categories;

    /**
     * Creates Dashboard model
     * Gets database object and loads initial categories list from database
     */
    Dashboard() {
        this.database = Database.getInstance();
        this.categories = new HashMap<>();
        getCategoriesFromDatabase();
    }

    private void getCategoriesFromDatabase() {
        categories = database.selectCategories();
    }

    /**
     * Adds category to database and builds new list
     * @param name name of the new category to add. Must be unique
     * @param color color of the category to render in dashboard
     * @return 0 if success or -1 if name is not unique or illegal
     */
    int addCategory(String name, String color) {
        if(!isNameLegal(name))
            return -1;

        database.insertCategory(name, color);
        getCategoriesFromDatabase();

        return 0;
    }

    private boolean isNameLegal(String name) {
        return !categories.containsKey(name) && !name.equals("Enter name");
    }

    /**
     * Updates category's name and background color
     * @param oldName old name of the category to identify it
     * @param newName name that category will have after update
     * @param newColor color that category will have after update
     * @return 0 if success or -1 if new name is not unique or illegal
     */
    int editCategory(String oldName, String newName, String newColor) {
        if(!isNameLegal(oldName, newName))
            return -1;

        database.removeCategory(oldName);
        database.notesUpdateCategory(oldName, newName);
        database.insertCategory(newName, newColor);
        getCategoriesFromDatabase();

        return 0;
    }

    private boolean isNameLegal(String oldName, String newName) {
        if(!oldName.equals(newName) && categories.containsKey(newName))
            return false;

        return !newName.equals("Enter name");
    }

    /**
     * Removes category with given name
     * @param name category name that will be delete
     */
    void removeCategory(String name) {
        database.removeCategory(name);
        database.removeNotesInCategory(name);

        getCategoriesFromDatabase();
    }

    /**
     * Gets actual list of categories
     * @return HashMap with category's name as a key and category object as value
     */
    HashMap<String, Category> getCategories() {
        return categories;
    }
}
