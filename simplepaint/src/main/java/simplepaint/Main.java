package simplepaint;

import javax.swing.SwingUtilities;

import simplepaint.ui.PaintFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaintFrame());
    }
}
