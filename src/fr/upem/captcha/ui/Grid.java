package fr.upem.captcha.ui;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid {
	private int difficulty;
	private List<URL> images;
	private String correct = "";
	private int nbCorrect = 0;
	
	public Grid(int d) {
		this.difficulty = d;
		this.images = new ArrayList<URL>();
	}
	
	public void addImage(URL u) {
		images.add(u);
	}
	public void setCorrect() {
		Random r = new Random();
		int size = images.size();
		int i = r.nextInt(size);
		System.out.println(i);
		URL correctURL = images.get(i);
		correct = getClassNameFromURL(correctURL);
		for(URL url : images) {
			if(getClassNameFromURL(url).contentEquals(correct)) nbCorrect++;
		}
	}
	
	public boolean isCorrect(List<URL> selected) {
		int n = 0;
		for(URL url : selected) {
			if(getClassNameFromURL(url).contentEquals(correct)) {
				n++;
			}
		}
		if (n == nbCorrect) return true;
		else return false;
	}
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
	
	public int getDifficulty() {
		return difficulty;
	}
	public int getImagesNumber() {
		return difficulty * 3;
	}
	public String getCorrect() {
		return correct;
	}

}
