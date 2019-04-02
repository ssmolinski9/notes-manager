import dashboard.DashboardController;
import javax.swing.SwingUtilities;

/**
 * Simple application for grouping simple notes in categories
 * @author Sebastian Smoliński
 * @version 1.0
 */
class Application {
    /**
     * Starting point. Runs thread with DashboardController initialization
     * @param args unused
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DashboardController::new);
    }
}
