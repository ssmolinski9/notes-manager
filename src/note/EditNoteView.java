package note;

import core.ColorName;
import gui.Window;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.*;

/**
 * View for Note module
 * Contains editing version of information about specified note
 * @version 1.0
 */
public class EditNoteView {
    private Window wnd;
    private Note note;

    private JButton confirmButton, cancelButton;
    private JComboBox<String> fonts, sizes;
    private JTextArea textField;
    private JTextField title;
    private JComboBox<String> colorField;

    /**
     * Initializes UI elements
     * @param note note model
     */
    EditNoteView(Note note) {
        this.wnd = Window.getInstance();
        this.note = note;
        this.confirmButton = new JButton("Confirm");
        this.cancelButton = new JButton("Cancel");
        this.textField = new JTextArea();
        this.colorField = new JComboBox<>();
        this.title = new JTextField();

        this.fonts = new JComboBox<>(new String[] { "Lato", "Cantarell", "Cousine", "Ubuntu" });
        this.sizes = new JComboBox<>(new String[] { "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25" });
    }

    /**
     * Gets confirm button
     * @return button for confirming updated notes information
     */
    JButton getConfirmButton() {
        return confirmButton;
    }

    /**
     * Gets cancel button
     * @return button for canceling updated notes information
     */
    JButton getCancelButton() {
        return cancelButton;
    }

    /**
     * Renders UI inside window
     */
    public void render() {
        JScrollPane windowPane = prepareWindowPane();
        wnd.getContentPane().add(windowPane);
    }

    /**
     * Gets combo box with fonts
     * @return fonts combo box
     */
    JComboBox<String> getFonts() {
        return fonts;
    }

    /**
     * Gets combo box with fonts size
     * @return fonts size combo box
     */
    JComboBox<String> getSizes() {
        return sizes;
    }

    /**
     * Gets text field with note's content
     * @return text field with new content of this note
     */
    JTextArea getTextField() {
        return textField;
    }

    /**
     * Gets text field with note's title
     * @return text field with new title of this note
     */
    JTextField getTitle() {
        return title;
    }

    private JScrollPane prepareWindowPane() {
        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(panel);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panel.setLayout(new GridBagLayout());

        title = new JTextField(note.getName());
        title.setFont(new Font(note.getFontFamily(), Font.PLAIN, Integer.parseInt(note.getFontSize())));

        addToPanel(title, 0, 0, 3, panel);

        colorField = new JComboBox<>();
        colorField.addItem("Blue");
        colorField.addItem("Yellow");
        colorField.addItem("Orange");
        colorField.addItem("Red");
        colorField.addItem("Green");
        colorField.addItem("Pink");
        colorField.addItem("Magenta");
        colorField.addItem("Cyan");

        String color = note.getColor();
        color = color.substring(0, 1).toUpperCase() + color.substring(1);
        colorField.setSelectedItem(color);
        addToPanel(colorField, 0, 1, 3, panel);

        JScrollPane textFieldScroll = new JScrollPane(textField);
        textFieldScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        textField.setText(note.getBody());
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);
        textField.setRows(30);

        addToPanel(textField, 0, 3, 3, panel);

        fonts.setPreferredSize(sizes.getPreferredSize());
        fonts.setSelectedItem(note.getFontFamily());

        sizes.setSelectedItem(note.getFontSize()+"");

        addToPanel(fonts, 0, 2, 1, panel);
        addToPanel(sizes, 1, 2, 1, panel);
        addToPanel(Box.createHorizontalGlue(), 2, 2, 1, panel);

        confirmButton.setPreferredSize(cancelButton.getPreferredSize());

        addToPanel(confirmButton, 0, 4, 1, panel);
        addToPanel(cancelButton, 2, 4, 1, panel);

        return scrollPane;
    }

    private void addToPanel(Component comp, int x, int y, int colspan, JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = colspan;
        gbc.gridheight = 1;
        gbc.weighty = .1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15,15,15,15);

        if (comp != null) {
            gbc.weightx = 1;
        } else {
            comp = Box.createHorizontalGlue();
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0.1;
            gbc.weighty = 0;
        }

        panel.add(comp, gbc);
    }

    /**
     * Gets JComboBox with selected color
     * @return combobox object with selected note color
     */
    public JComboBox<String> getColorField() { return colorField; }
}
