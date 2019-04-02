package note;

import gui.Window;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * View for Note module
 * Contains read only version of information about specified note
 * @version 1.0
 */
public class NoteView {

    private Window wnd;
    private Note note;

    private JButton edit, delete;

    /**
     * Initializes UI elements
     * @param note note model
     */
    NoteView(Note note) {
        this.wnd = Window.getInstance();
        this.note = note;
        this.edit = new JButton("Edit");
        this.delete = new JButton("Delete");
    }

    /**
     * Gets edit button
     * @return button for editing chosen note
     */
    JButton getEdit() {
        return edit;
    }

    /**
     * Gets delete button
     * @return button for deleting chosen note
     */
    JButton getDelete() {
        return delete;
    }

    /**
     * Renders note view ui inside window
     */
    public void render() {
        JScrollPane windowPane = prepareWindowPane();
        wnd.getContentPane().add(windowPane);
    }

    private JScrollPane prepareWindowPane() {
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(panel);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panel.setLayout(new GridBagLayout());

        JLabel title = new JLabel(note.getCategory() + ": " + note.getName());
        title.setFont(new Font(note.getName(), Font.BOLD, Integer.parseInt(note.getFontSize())+2));
        title.setHorizontalAlignment(JLabel.LEFT);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.weightx = 4;
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(title, gbc);

        JTextArea textField = new JTextArea();
        JScrollPane textFieldScroll = new JScrollPane(textField);
        textFieldScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        textField.setText(note.getBody());
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);
        textField.setRows(30);
        textField.setEditable(false);
        textField.setBackground(wnd.getContentPane().getBackground());
        textField.setFont(new Font(note.getFontFamily(), Font.PLAIN, Integer.parseInt(note.getFontSize())));

        gbc.gridy = 1;
        gbc.insets = new Insets(5, 15, 5, 15);
        panel.add(textFieldScroll, gbc);

        gbc.gridwidth = 2;
        gbc.weightx = 2;
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(edit, gbc);

        gbc.gridx = 2;
        panel.add(delete, gbc);

        return scrollPane;
    }
}
