package fr.upem.captcha.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
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
	
	// constantes & paramètres
	private static final int WINDOW_WIDTH = 1024;
	private static final int WINDOW_HEIGHT = 769;
	private static final int FONT_SIZE = 30;
	private static final String FONT_NAME = "Courrier";
	private static final int MAX_DIFFICULTY = 5;
	
	// variables statiques
	private static int difficulty = 3; 										// établit le niveau de difficulter premier
	private static ArrayList<URL> selectedImages = new ArrayList<URL>();	// liste des images sélectionnées
	private static Grid grid = new Grid(difficulty);						// grille d'images
	private static JFrame frame = new JFrame("Captcha");					// fenêtre d'affichage
	
	public static void main(String[] args) throws IOException {
		frame = createCaptcha();	// création de la fenêtre
	}
	
	// crée une nouvelle fenêtre avec une nouvelle grille d'images
	private static JFrame createCaptcha(){
		JFrame frame = createFrame("L&L Captcha");						// création de la fenêtre
		
		// ajout des éléments
		fillGrid(frame);												// remplissage de la grid avec des images aléatoires
		frame.add(createTextArea("Cliquez sur les images représentants des " + grid.getCorrect().toLowerCase()));	// consigne
		frame.add(createOkButton());																				// bouton vérification	
		frame.setVisible(true);																						// affichage
		
		return frame;
	}
	
	private static JFrame createFrame(String name) {
		JFrame frame = new JFrame(name);						// nom de la fenêtre
		
		GridLayout layout = createLayout(grid.getDifficulty());	// grille layout
		frame.setLayout(layout);  								
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); 			// dimension fenêtre
		frame.setResizable(false); 								// redimensionnable ? 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 	// fin du programme quand fermeture de la fenêtre
		
		return frame;
	}
	
	// remplit la grille d'images aléatoirement
	private static void fillGrid(JFrame frame) {
		Type t = new Type();
		for(int i = 0 ; i < grid.getImagesNumber(); i++) {
			URL u = t.getRandomPhotoURL();			// remplissage aléatoire
			try {
				frame.add(createLabelImage(u));		// ajout de l'image à la fenêtre
			} catch (IOException e) {
				e.printStackTrace();
			}
			grid.addImage(u);						// ajout de l'image à la grille
		}
		grid.setCorrect();							// défintion du type d'image à sélectionner
	}
	
	// créer du texte à afficher
	private static JTextArea createTextArea(String text) {
		JTextArea result = new JTextArea(text);
		result.setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE));	// paramètres de la police
		result.setLineWrap(true);									// retour à la ligne automatique
		return result;
	}
	
	// créer une grille
	private static GridLayout createLayout(int i){
		return new GridLayout(4, i);
	}
	
	private static void createAlert(String message) {
		JFrame alert = createFrame("Message");
		alert.add(createTextArea(message));
		alert.setVisible(true);
	}
	
	// créer le bouton de vérification
	private static JButton createOkButton(){
		JButton button = new JButton(new AbstractAction("Vérifier") {	//ajouter l'action du bouton
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() { 				// faire des choses dans l'interface donc appeler cela dans la queue des évènements
					
					@Override
					public void run() { 
						if(grid.isCorrect(selectedImages)) {			// si sélection ok alors message d'alerte		
							createAlert("BRAVO !");
						}
						else {											// si sélection incorrecte, création d'un nouveau captcha avec plus d'éléments
							if(difficulty == MAX_DIFFICULTY) createAlert("Nombre de tentatives atteintes");
							else {
								difficulty++;
								grid = new Grid(difficulty);
								frame.dispose();
								frame = createCaptcha();
							}
						}
					}
				});
			}
		});
		button.setFont(new Font("Courrier", Font.BOLD, 45));
		return button;
	}
	
	private static JLabel createLabelImage(URL url) throws IOException{
				
		BufferedImage img = ImageIO.read(url); 																		// lecture de l'image
		Image sImage = img.getScaledInstance(WINDOW_WIDTH / difficulty , WINDOW_HEIGHT / 4, Image.SCALE_SMOOTH); 	// redimensionner l'image
		
		final JLabel label = new JLabel(new ImageIcon(sImage)); // créer le composant pour ajouter l'image dans la fenêtre
		
		label.addMouseListener(new MouseListener() { //Ajouter le listener d'évenement de souris
			private boolean isSelected = false;
			
			@Override public void mouseReleased(MouseEvent arg0) {}
			@Override public void mousePressed(MouseEvent arg0) {}
			@Override public void mouseExited(MouseEvent arg0) {}
			@Override public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseClicked(MouseEvent arg0) { //ce qui nous intéresse c'est lorsqu'on clique sur une image, il y a donc des choses à faire ici
				EventQueue.invokeLater(new Runnable() { 
					@Override
					public void run() {
						if(!isSelected){
							label.setBorder(BorderFactory.createLineBorder(Color.RED, 3)); 	// si la photo est sélectionnée
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
