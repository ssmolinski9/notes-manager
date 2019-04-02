package gui;

import core.Database;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Singleton class to window handling
 * @version 1.0
 */
public class Window extends JFrame {
    private static Window instance = null;

    /**
     * Gets window handler if exists or initialize window or not
     * @return initialized object of the window
     */
    public static Window getInstance() {
        if(instance == null) {
            instance = new Window();
        }

        return instance;
    }

    private Window() {
        super();

        setTitle("Note Manager");
        setSize(new Dimension(800, 600));

        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Database database = Database.getInstance();
                database.closeConnection();
                dispose();
            }
        };

        addWindowListener(exitListener);
        setVisible(true);
    }
}
