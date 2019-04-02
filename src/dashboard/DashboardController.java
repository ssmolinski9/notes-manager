package dashboard;

import category.CategoryController;
import core.ColorName;
import gui.DashboardDialog;
import gui.Window;
import gui.Tile;

import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import java.awt.Color;

/**
 * Controller for Dashboard module
 * Provides actions for categories manipulation
 * @version 1.0
 * @see Dashboard
 * @see DashboardView
 */
public class DashboardController {
    private Window wnd;
    private Dashboard model;
    private DashboardView view;
    private DashboardDialog dialog;

    /**
     * Standard way to initialize Dashboard module
     * Gets window handler, sets up model and view
     * Renders view and register events
     */
    public DashboardController() {
        this.wnd = Window.getInstance();
        this.model = new Dashboard();
        this.view = new DashboardView();

        updateView();
        registerEvents();
    }

    private void updateView() {
        wnd.getContentPane().removeAll();
        wnd.getContentPane().revalidate();
        wnd.getContentPane().repaint();

        view.render(model.getCategories());
    }

    private void registerEvents() {
        registerTileEvents();
    }

    private void registerTileEvents() {
        for(Tile tile : view.getTiles()) {
            if(!tile.getText().equals("Add new")) {
                tile.addActionListener(e -> redirectToCategory(tile));
            } else {
                tile.addActionListener(e -> registerAddNewCategoryEvent());
            }

            tile.getEditItem().addActionListener(e -> registerEditCategoryEvent(tile));
            tile.getDeleteItem().addActionListener(e -> registerRemoveCategoryEvent(tile));
        }
    }

    private void redirectToCategory(Tile tile) {
        new CategoryController(tile.getText(), ColorName.getColorName(tile.getBackground().toString()).toLowerCase());
    }

    private void registerAddNewCategoryEvent() {
        dialog = new DashboardDialog();
        dialog.setLocationRelativeTo(wnd);

        dialog.getConfirmButton().addActionListener(e -> {
            int result = model.addCategory(dialog.getTextField().getText(), ((String)dialog.getColorField().getSelectedItem()).toLowerCase());
            if(result != -1) {
                dialog.dispose();
                updateView();
                registerEvents();
            } else {
                dialog.getTextField().setBorder(new LineBorder(Color.red,1));
                dialog.getErrorMessage().setText("Error! That category already exists!");
            }
        });

        dialog.getCancelButton().addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    private void registerEditCategoryEvent(Tile tile) {
        dialog = new DashboardDialog(tile.getText(), tile.getBackground().toString());
        dialog.setLocationRelativeTo(wnd);

        dialog.getConfirmButton().addActionListener(a -> {
            int result = model.editCategory(tile.getText(), dialog.getTextField().getText(), dialog.getColorField().getSelectedItem().toString().toLowerCase());
            if(result != -1) {
                dialog.dispose();
                updateView();
                registerEvents();
            } else {
                dialog.getTextField().setBorder(new LineBorder(Color.red,1));
                dialog.getErrorMessage().setText("Error! That category already exists!");
            }
        });

        dialog.getCancelButton().addActionListener(a -> dialog.dispose());
        dialog.setVisible(true);
    }

    private void registerRemoveCategoryEvent(Tile tile) {
        int result = JOptionPane.showConfirmDialog(wnd, "Are you really want to delete this category?", "Warning", JOptionPane.YES_NO_OPTION);
        if(result == 0) {
            model.removeCategory(tile.getText());

            updateView();
            registerEvents();
        }
    }
}
