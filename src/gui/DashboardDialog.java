package gui;

import core.ColorName;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Represents dialog for dashboard module
 * @version 1.0
 * @since 1.0
 */
public class DashboardDialog extends JDialog {
    private JTextField textField;
    private JComboBox<String> colorField;
    private JButton confirmButton;
    private JButton cancelButton;
    private JLabel errorMessage;
    private Window wnd;

    /**
     * Sets look and feel of a dialog but doesn't set it visible.
     * Creates Dialog for adding entries (with standard text and color).
     */
    public DashboardDialog() {
        super(Window.getInstance(), "Add new", true);
        wnd = Window.getInstance();

        setDialogLook();
        add(createDialogView("Enter name", "blue"));
        setKeyboardFacilities();
    }

    /**
     * Sets look and feel of a dialog but doesn't set it visible.
     * Creates Dialog for editing entries.
     * @param categoryName actual name of the category.
     * @param color actual color of the background.
     */
    public DashboardDialog(String categoryName, String color) {
        super(Window.getInstance(), "Edit", true);
        wnd = Window.getInstance();

        setDialogLook();
        add(createDialogView(categoryName, color));
        setKeyboardFacilities();
    }

    private void setDialogLook() {
        setSize(350, 200);
        setLocationRelativeTo(wnd);
        setResizable(false);
    }

    private void setKeyboardFacilities() {
        getRootPane().setDefaultButton(confirmButton);
        SwingUtilities.invokeLater(() -> confirmButton.requestFocusInWindow());

        ActionListener escListener = e -> dispose();

        getRootPane().registerKeyboardAction(escListener,
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private JPanel createDialogView(String name, String color) {
        JPanel dialogView = new JPanel();
        dialogView.setLayout(new GridBagLayout());

        errorMessage = new JLabel("");
        errorMessage.setFont(new Font("Ubuntu", Font.PLAIN, 14));
        errorMessage.setForeground(Color.red);
        errorMessage.setPreferredSize(new Dimension(300, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;

        dialogView.add(errorMessage, gbc);

        textField = new JTextField(name);
        textField.setPreferredSize(new Dimension(300, 30));
        textField.setSize(new Dimension(300, 30));
        textField.setLocation(25, 100);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;

        dialogView.add(textField, gbc);

        colorField = new JComboBox<>();
        colorField.addItem("Blue");
        colorField.addItem("Yellow");
        colorField.addItem("Orange");
        colorField.addItem("Red");
        colorField.addItem("Green");
        colorField.addItem("Pink");
        colorField.addItem("Magenta");
        colorField.addItem("Cyan");

        colorField.setPreferredSize(new Dimension(300, 30));
        colorField.setSelectedItem(ColorName.getColorName(color));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;

        dialogView.add(colorField, gbc);

        if(name.equals("Enter name"))
            confirmButton = new JButton("Add");
        else
            confirmButton = new JButton("Edit");

        cancelButton = new JButton("Cancel");

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 15, 0, 0);
        dialogView.add(confirmButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        dialogView.add(Box.createGlue(), gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 15);
        dialogView.add(cancelButton, gbc);

        return dialogView;
    }

    /**
     * Gets button to confirm action from Dialog
     * @return JButton to confirms action
     */
    public JButton getConfirmButton() {
        return confirmButton;
    }

    /**
     * Gets button to cancel action from Dialog
     * @return JButton to cancel action
     */
    public JButton getCancelButton() {
        return cancelButton;
    }

    /**
     * Gets field with new position's name.
     * @return JTextField object with user's text
     */
    public JTextField getTextField() {
        return textField;
    }

    /**
     * Gets color field
     * @return combo box with selected color's name
     */
    public JComboBox<String> getColorField() {
        return colorField;
    }

    /**
     * Gets JLabel to render inside error message
     * @return jlabel with place for message
     */
    public JLabel getErrorMessage() {
        return errorMessage;
    }
}
