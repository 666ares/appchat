package gui.elements;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BotonChat extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BotonChat(String imagen, String data) {
		super(data);
		
		ImageIcon icon = new ImageIcon(imagen);
		Image imageIcon = icon.getImage();
		Image newImage = imageIcon.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		
		setIcon(new ImageIcon(newImage));
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusPainted(false);
	}
	
	public BotonChat(String imagen) {
		super();
		
		ImageIcon icon = new ImageIcon(imagen);
		Image imageIcon = icon.getImage();
		Image newImage = imageIcon.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
		
		setIcon(new ImageIcon(newImage));
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusPainted(false);
	}
	
	public BotonChat(String imagen, int x, int y) {
		super();
		
		ImageIcon icon = new ImageIcon(imagen);
		Image imageIcon = icon.getImage();
		Image newImage = imageIcon.getScaledInstance(x, y, java.awt.Image.SCALE_SMOOTH);
		
		setIcon(new ImageIcon(newImage));
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusPainted(false);
	}
	
	
}
