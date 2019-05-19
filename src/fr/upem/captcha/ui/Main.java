package fr.upem.captcha.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import fr.upem.captcha.images.Type;

public class Main{
	

	private static int difficulty = 3;
	private static ArrayList<URL> selectedImages = new ArrayList<URL>();
	private static Grid grid = new Grid(difficulty);
	private static JFrame frame = new JFrame("Captcha");
	
	public static void main(String[] args) throws IOException {
		frame = createFrame();	
	}
	
	private static JFrame createFrame(){
		JFrame frame = new JFrame("Captcha");
		GridLayout layout = createLayout(grid.getDifficulty());		
		frame.setLayout(layout);  // affection du layout dans la fenêtre.
		frame.setSize(2048, 1536); // définition de la taille
		frame.setResizable(false);  // On définit la fenêtre comme non redimentionnable
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Lorsque l'on ferme la fenêtre on quitte le programme.
		JButton okButton = createOkButton();
		Type t = new Type();
		for(int i = 0 ; i < grid.getImagesNumber(); i++) {
			URL u = t.getRandomPhotoURL();
			try {
				frame.add(createLabelImage(u));
			} catch (IOException e) {
				e.printStackTrace();
			}
			grid.addImage(u);
		}
		grid.setCorrect();
		
		frame.add(new JTextArea("Cliquez sur les images représentants des " + grid.getCorrect().toLowerCase()));
		frame.add(okButton);	
		frame.setVisible(true);
		return frame;
	}
	
	private static GridLayout createLayout(int i){
		return new GridLayout(4, i);
	}
	
	private static JButton createOkButton(){
		return new JButton(new AbstractAction("Vérifier") { //ajouter l'action du bouton
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() { // faire des choses dans l'interface donc appeler cela dans la queue des évènements
					
					@Override
					public void run() { // c'est un runnable
						if(grid.isCorrect(selectedImages)) {
							JFrame alert = new JFrame("Message");
							alert.setSize(2048, 1536); // définition de la taille
							alert.setResizable(false);  // On définit la fenêtre comme non redimentionnable
							frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Lorsque l'on ferme la fenêtre on quitte le programme.
							alert.add(new JTextArea("Bravo !"));
							alert.setVisible(true);
						}
						else {
							difficulty++;
							grid = new Grid(difficulty);
							frame = createFrame();
						}
					}
				});
			}
		});
	}
	
	private static JLabel createLabelImage(URL url) throws IOException{
				
		//System.out.println(url); 
		
		BufferedImage img = ImageIO.read(url); //lire l'image
		Image sImage = img.getScaledInstance(1024/3,768/4, Image.SCALE_SMOOTH); //redimentionner l'image
		
		final JLabel label = new JLabel(new ImageIcon(sImage)); // créer le composant pour ajouter l'image dans la fenêtre
		
		label.addMouseListener(new MouseListener() { //Ajouter le listener d'évenement de souris
			private boolean isSelected = false;
			
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) { //ce qui nous intéresse c'est lorsqu'on clique sur une image, il y a donc des choses à faire ici
				EventQueue.invokeLater(new Runnable() { 
					
					@Override
					public void run() {
						if(!isSelected){
							label.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
							isSelected = true;
							selectedImages.add(url);
						}
						else {
							label.setBorder(BorderFactory.createEmptyBorder());
							isSelected = false;
							selectedImages.remove(url);
						}
						
					}
				});
				
			}
		});
		
		return label;
	}
}
