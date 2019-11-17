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
import javax.swing.JLabel;
import javax.swing.border.Border;

public class BoxChat extends Box {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BoxChat(String nombreUser, String lastMensaje) {
		//Constructor como un horizontalBox
		super(BoxLayout.LINE_AXIS);
		
		setPreferredSize(new Dimension(250, 80));
		setMinimumSize(new Dimension(250, 80));
		
		Border blackline = BorderFactory.createLineBorder(Color.BLACK);
		setBorder(blackline);
		
		ImageIcon userPic = new ImageIcon("icons/profile_picture.png");
		Image newImage = userPic.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
		ImageIcon userPic2 = new ImageIcon(newImage);
		JLabel imagelabel = new JLabel(userPic2);
		add(imagelabel);
		
		Box userinfo = Box.createVerticalBox();
		JLabel label = new JLabel(nombreUser);
		label.setFont(new Font("Tahome", Font.BOLD, 16));
		userinfo.add(label);
		
		Component verticalStrut = Box.createVerticalStrut(10);
		userinfo.add(verticalStrut);
		userinfo.add(new JLabel(lastMensaje));
		add(userinfo);	
	}
	
	public BoxChat(ImageIcon userPic, String nombreUser, String lastMensaje) {
		//Constructor como un horizontalBox
		super(BoxLayout.LINE_AXIS);
		
		setPreferredSize(new Dimension(250, 80));
		setMinimumSize(new Dimension(250, 80));
		
		Border blackline = BorderFactory.createLineBorder(Color.BLACK);
		setBorder(blackline);
		
		Image newImage = userPic.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);
		ImageIcon userPic2 = new ImageIcon(newImage);
		JLabel imagelabel = new JLabel(userPic2);
		add(imagelabel);
		
		Box userinfo = Box.createVerticalBox();
		JLabel label = new JLabel(nombreUser);
		label.setFont(new Font("Tahome", Font.BOLD, 16));
		userinfo.add(label);
		
		Component verticalStrut = Box.createVerticalStrut(10);
		userinfo.add(verticalStrut);
		userinfo.add(new JLabel(lastMensaje));
		add(userinfo);	
	}

}
