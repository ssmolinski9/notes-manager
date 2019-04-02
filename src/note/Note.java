package note;

import core.Database;
import core.Key;

/**
 * Note model
 * @version 1.0
 */
public class Note {
    private String name, category, color, fontFamily, body, fontSize;
    private Database database;

    /**
     * Creates note model
     * @param name name of the note
     * @param category name of the category where this note should be
     * @param color color of the note to render in category view
     * @param fontFamily family of the font used in note
     * @param body text written inside this note
     * @param fontSize size of the font used in note
     */
    public Note(String name, String category, String color, String fontFamily, String body, String fontSize) {
        this.name = name;
        this.category = category;
        this.color = color;
        this.fontFamily = fontFamily;
        this.body = body;
        this.fontSize = fontSize;
        this.database = Database.getInstance();
    }

    /**
     * Updates note's information and refresh fields
     * @param name new name of the note
     * @param fontFamily new font family for the note
     * @param body new note's content
     * @param fontSize new font size for the note
     */
    int editNote(String name, String fontFamily, String body, String fontSize, String color) {

        if(!name.equals(this.name) && database.selectNotes().containsKey(new Key(name, category)))
            return -1;

        database.notesUpdateAll(this.name, this.category, name, body, fontFamily, fontSize, color);

        this.name = name;
        this.fontFamily = fontFamily;
        this.body = body;
        this.fontSize = fontSize;

        return 0;
    }

    /**
     * Gets name of this note
     * @return name of the note
     */
    public String getName() {
        return name;
    }

    /**
     * Gets name of the category where note should be
     * @return name of the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Gets color of the note
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * Gets font family
     * @return name of the font family
     */
    String getFontFamily() {
        return fontFamily;
    }

    /**
     * Gets content of the note
     * @return content of the note
     */
    String getBody() { return body; }

    /**
     * Gets font size
     * @return size of the font
     */
    String getFontSize() {
        return fontSize;
    }

    /**
     * Sets font family
     * @param fontFamily name of the font family to set
     */
    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    /**
     * Sets font size
     * @param fontSize size of the font to set
     */
    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }
}
