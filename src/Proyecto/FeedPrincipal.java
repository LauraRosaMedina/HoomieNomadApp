package Proyecto;

import java.awt.EventQueue;

import javax.swing.JPanel;

public class FeedPrincipal {

	private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FeedPrincipal frame = new FeedPrincipal();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}


}
