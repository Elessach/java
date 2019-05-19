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
		URL correct = images.get(r.nextInt(size));
		URI uri = null;
		try {
			uri = correct.toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		File f = new File(uri);
		System.out.println(f.getParent());
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	public int getImagesNumber() {
		return difficulty * difficulty;
	}

}
