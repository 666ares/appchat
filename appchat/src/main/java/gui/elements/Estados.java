package gui.elements;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Estados extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final int ANCHOW = 430;
	static final int ALTOW = 300;
	
	public Estados() {
		super();
		setTitle("Estados");
		setBounds(100, 100, 430, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		setContentPane(contentPane);
		
		
		
		
	}
	
	public void makeVisible() {
		setVisible(true);
	}
	
	public void makeInvisible() {
		setVisible(false);
	}
	

}
