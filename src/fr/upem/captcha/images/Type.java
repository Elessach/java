package fr.upem.captcha.images;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Type implements Images{

	public List<URL> getPhotos() {
		List<URL> result = new ArrayList<URL>();
		File folder = new File("src/" + this.getClass().getPackage().getName().replace('.', '/'));
		getAllPhotos(folder, result);
		return result;
	}
	private void getAllPhotos(File f, List<URL> u) {
		for(File i : f.listFiles()) {
			if(i.getName().toLowerCase().endsWith(".jpg")) {
				try {
					u.add(i.toURI().toURL());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
			else if(i.isDirectory()) {
				getAllPhotos(i, u);
			}
		}
	}

	public List<URL> getRandomPhotosURL(int i) {
		List<URL> result = new ArrayList<URL>();
		for(int j = 0; j < i ; j++) {
			result.add(getRandomPhotoURL());
		}
		return result;
	}

	public URL getRandomPhotoURL() {
		List<URL> tmp = getPhotos();
		int size = tmp.size();
		Random r = new Random();
		int i = r.nextInt(size);
		return tmp.get(i);
	}

}
