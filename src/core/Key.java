package core;

/**
 * Custom hash map key with two fields
 * @version 1.0
 */
public class Key {
    private final String name;
    private final String category;

    /**
     * Creates hash map key with two values
     * @param name name of the note
     * @param category name of the category
     */
    public Key(String name, String category) {
        this.category = category;
        this.name = name;
    }

    /**
     * Compare keys relative to fields. Keys are same when they have two fields equals.
     * @param o given Key object
     * @return true if Key is equals with other Key.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        Key key = (Key) o;
        return name.equals(key.name) && category.equals(key.category);
    }

    /**
     * Hashcode generated depends on sum of fields hash codes
     * @return unique hash code
     */
    public int hashCode() {
        return name.hashCode() + category.hashCode();
    }

    /**
     * Returns name of the category where note is stored
     * @return string with note's category name
     */
    public String getCategory() {
        return category;
    }
}
