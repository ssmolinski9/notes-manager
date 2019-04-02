package category;

import core.Key;
import dashboard.DashboardController;
import gui.DashboardDialog;
import gui.Tile;
import gui.Window;
import note.Note;
import note.NoteController;

import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Controller for specified Category
 * Provides actions for notes manipulation
 * @version 1.0
 * @see Category
 * @see CategoryView
 */
public class CategoryController {
    private Window wnd;
    private Category model;
    private CategoryView view;
    private DashboardDialog dialog;

    /**
     * Standard way to initialize Category module
     * Gets window handler, sets up model and view
     * Renders view and register events
     * @param name name of the category to identify which notes should be rendered
     * @param color color of the category to sets up model
     */
    public CategoryController(String name, String color) {
        this.wnd = Window.getInstance();
        this.model = new Category(name, color);
        this.view = new CategoryView(name);

        updateView();
        registerEvents();
    }

    private void updateView() {
        wnd.getContentPane().removeAll();
        wnd.getContentPane().revalidate();
        wnd.getContentPane().repaint();

        view.render(model.getNotes());
    }

    private void registerEvents() {
        registerBackEvent();
        registerTileEvents();
    }

    private void registerBackEvent() {
        wnd.getContentPane().getComponent(0).addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(e.getButton() == MouseEvent.BUTTON3)
                    new DashboardController();
            }
        });
    }

    private void registerTileEvents() {
        for(Tile tile : view.getTiles()) {
            if(!tile.getText().equals("Add new")) {
                tile.addActionListener(e -> redirectToNote(tile.getText()));
            } else {
                tile.addActionListener(e -> registerAddNewNoteEvent());
            }

            tile.getEditItem().addActionListener(e -> registerEditNoteEvent(tile));
            tile.getDeleteItem().addActionListener(e -> registerRemoveNoteEvent(tile));
        }
    }

    private void redirectToNote(String name) {
        Note note = model.getNotes().get(new Key(name, model.getName()));
        new NoteController(note);
    }

    private void registerAddNewNoteEvent() {
        dialog = new DashboardDialog();
        dialog.setLocationRelativeTo(wnd);

        dialog.getConfirmButton().addActionListener(e -> {
            int result = model.addNote(dialog.getTextField().getText(), ((String)dialog.getColorField().getSelectedItem()).toLowerCase());
            if(result != -1) {
                dialog.dispose();
                updateView();
                registerEvents();
            } else {
                dialog.getTextField().setBorder(new LineBorder(Color.red,1));
                dialog.getErrorMessage().setText("Error! That note already exists!");
            }
        });

        dialog.getCancelButton().addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    private void registerEditNoteEvent(Tile tile) {
        Note note = model.getNotes().get(new Key(tile.getText(), model.getName()));
        new NoteController(note).directEditNoteViewRender();
    }

    private void registerRemoveNoteEvent(Tile tile) {
        int result = JOptionPane.showConfirmDialog(wnd, "Are you really want to delete this note?", "Warning", JOptionPane.YES_NO_OPTION);
        if(result == 0) {
            model.removeNote(tile.getText());

            updateView();
            registerEvents();
        }
    }
}
