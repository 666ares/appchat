package gui.elements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class BoxChat extends JButton {

	private static final long serialVersionUID = 1L;

	public BoxChat(String nombreUser, String lastMensaje) {
		
		// Constructor como un horizontalBox
		super();
		
		Box chat = new Box(BoxLayout.LINE_AXIS);
		
		chat.setPreferredSize(new Dimension(277, 75));
		chat.setMinimumSize(new Dimension(277, 75));
		
		ImageIcon userPic = new ImageIcon("icons/profile_picture.png");
		Image newImage = userPic.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
		ImageIcon userPic2 = new ImageIcon(newImage);
		JLabel imagelabel = new JLabel(userPic2);
		chat.add(imagelabel);
		
		Box userinfo = Box.createVerticalBox();
		JLabel label = new JLabel(nombreUser);
		label.setFont(new Font("Tahome", Font.BOLD, 16));
		userinfo.add(label);
		
		Component verticalStrut = Box.createVerticalStrut(10);
		userinfo.add(verticalStrut);
		userinfo.add(new JLabel(lastMensaje));
		chat.add(userinfo);	
		
		add(chat);
		Border blackline = BorderFactory.createLineBorder(Color.BLACK);
		setBorder(blackline);
		
		setOpaque(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
	}
	
	public BoxChat(ImageIcon userPic, String nombreUser, String lastMensaje) {
		
		// Constructor como un horizontalBox
		super();
		
		Box chat = new Box(BoxLayout.LINE_AXIS);
		
		chat.setPreferredSize(new Dimension(277, 75));
		chat.setMinimumSize(new Dimension(277, 75));
		
		Image newImage = userPic.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
		ImageIcon userPic2 = new ImageIcon(newImage);
		JLabel imagelabel = new JLabel(userPic2);
		chat.add(imagelabel);
		
		Box userinfo = Box.createVerticalBox();
		JLabel label = new JLabel(nombreUser);
		label.setFont(new Font("Tahome", Font.BOLD, 16));
		userinfo.add(label);
		
		Component verticalStrut = Box.createVerticalStrut(10);
		userinfo.add(verticalStrut);
		userinfo.add(new JLabel(lastMensaje));
		chat.add(userinfo);	
		
		add(chat);
		Border blackline = BorderFactory.createLineBorder(Color.BLACK);
		setBorder(blackline);
		
		setOpaque(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
	}

	public void setResaltar(boolean ver) {
		
		if (ver) {
			setOpaque(true);
			setContentAreaFilled(true);
			setFocusPainted(true);
			setBackground(Color.GRAY);
		} else {
			setOpaque(false);
			setContentAreaFilled(false);
			setFocusPainted(false);
		}
	}
	
}
