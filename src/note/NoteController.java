package note;

import category.Category;
import category.CategoryController;
import gui.Window;

import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Controller for Note module
 * @version 1.0
 * @see Note
 * @see NoteView
 */
public class NoteController {
    private Window wnd;
    private Note model;
    private NoteView view;
    private EditNoteView editView;

    /**
     * Standard way to initialize Note module
     * Gets window handler, sets up model and view
     * Renders view and register events
     * @param note specified note
     */
    public NoteController(Note note) {
        this.wnd = Window.getInstance();
        this.model = note;
        this.view = new NoteView(model);
        this.editView = new EditNoteView(model);

        updateView();
        registerEvents();
    }

    /**
     * Direct way to initialize view for editing note
     */
    public void directEditNoteViewRender() {
        wnd.getContentPane().removeAll();
        wnd.getContentPane().revalidate();
        wnd.getContentPane().repaint();

        editView.render();
    }

    private void updateView() {
        wnd.getContentPane().removeAll();
        wnd.getContentPane().revalidate();
        wnd.getContentPane().repaint();

        view.render();
    }

    private void registerEvents() {
        registerBackEvent();
        registerEditEvent();
        registerDeleteEvent();
        registerBackFromEditEvent();
        registerChangeFontEvent();
        registerEditConfirmEvent();
    }

    private void registerBackEvent() {
        wnd.getContentPane().getComponent(0).addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(e.getButton() == MouseEvent.BUTTON3)
                    new CategoryController(model.getCategory(), new Category(model.getCategory()).getColor());
            }
        });
    }

    private void registerBackFromEditEvent() {
        editView.getCancelButton().addActionListener(e -> {
            view = new NoteView(model);
            updateView();
            registerEvents();
        });
    }

    private void registerChangeFontEvent() {
        editView.getFonts().addActionListener(e -> {
            editView.getTextField().setFont(new Font( (String)editView.getFonts().getSelectedItem(), Font.PLAIN, Integer.parseInt((String) editView.getSizes().getSelectedItem())));
            editView.getTitle().setFont(new Font( (String)editView.getFonts().getSelectedItem(), Font.PLAIN, Integer.parseInt((String) editView.getSizes().getSelectedItem())));
        });

        editView.getSizes().addActionListener(e -> {
            editView.getTextField().setFont(new Font( (String)editView.getFonts().getSelectedItem(), Font.PLAIN, Integer.parseInt((String) editView.getSizes().getSelectedItem())));
            editView.getTitle().setFont(new Font( (String)editView.getFonts().getSelectedItem(), Font.PLAIN, Integer.parseInt((String) editView.getSizes().getSelectedItem())));
        });
    }

    private void registerEditEvent() {
        view.getEdit().addActionListener(e -> {
            wnd.getContentPane().removeAll();
            wnd.getContentPane().revalidate();
            wnd.getContentPane().repaint();

            editView.render();
        });
    }

    private void registerDeleteEvent() {
        view.getDelete().addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(wnd, "Are you really want to delete this note?", "Warning", JOptionPane.YES_NO_OPTION);
            if(result == 0) {
                Category category = new Category(model.getCategory());
                category.removeNote(model.getName());

                new CategoryController(category.getName(), category.getColor());
            }
        });
    }

    private void registerEditConfirmEvent() {
        editView.getConfirmButton().addActionListener(e -> {

            int result = model.editNote(editView.getTitle().getText(), (String)editView.getFonts().getSelectedItem(), editView.getTextField().getText(), (String) editView.getSizes().getSelectedItem(), ((String)editView.getColorField().getSelectedItem()).toLowerCase());

            if(result != -1) {
                updateView();
                registerEvents();
                editView.getTitle().setBorder(new LineBorder(Color.lightGray,1));
                editView.getTitle().setToolTipText(null);
            } else {
                editView.getTitle().setBorder(new LineBorder(Color.red,1));
                editView.getTitle().setToolTipText("That name is already in use!");
            }
        });
    }
}
