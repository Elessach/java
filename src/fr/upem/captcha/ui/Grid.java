package fr.upem.captcha.ui;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid {
	private int difficulty;								// difficulté de la grille (nombre de lignes
	private List<URL> images = new ArrayList<URL>();	// images de la grille
	private String correct = "";						// nom de la classe à sélectionner
	private int nbCorrect = 0;							// nombre de photos correcte dans la grille
	
	// constructeur par paramètres
	public Grid(int d) {
		this.difficulty = d;		
	}
	
	// ajout d'une image par son url aux images de la grille
	public void addImage(URL u) {
		images.add(u);
	}
	// définit le type d'image à sélectionner
	public void setCorrect() {
		Random r = new Random();
		int size = images.size();
		URL correctURL = images.get(r.nextInt(size));	// on choisit une des images affichées qui va définir le type à sélectionner
		correct = getClassNameFromURL(correctURL);		// on récuppère le nom de la classe correspondante
		for(URL url : images) {
			if(getClassNameFromURL(url).contentEquals(correct)) nbCorrect++;	// on compte le nombre d'images de ce type dans la grille
		}
	}
	
	// définit si les images sélectionnées sont correctes
	public boolean isCorrect(List<URL> selected) {
		int n = 0;
		for(URL url : selected) {
			if(getClassNameFromURL(url).contentEquals(correct)) {	// on compte le nombre d'images correcte
				n++;
			}
		}
		if (n == nbCorrect) return true;	// si le nombre correspond alors seulement c'est bon
		else return false;
	}
	
	// convertit une url en nom de classe correspondant
	private String getClassNameFromURL(URL url) {
		URI uri = null;
		try {
			uri = url.toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		File f = new File(uri);
		File parent = f.getParentFile();
		String parentName = parent.getName();
		return parentName.substring(0, 1).toUpperCase() + parentName.substring(1);
	}
	
	// retourne la difficulté
	public int getDifficulty() {
		return difficulty;
	}
	// retourne le nombre d'images dans la grille
	public int getImagesNumber() {
		return difficulty * 3;
	}
	// retourne le nom de classe correct
	public String getCorrect() {
		return correct;
	}

}
