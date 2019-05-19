package fr.upem.captcha.images;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
				System.out.println(i.getAbsolutePath());
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
		// TODO Auto-generated method stub
		return null;
	}

	public URL getRandomPhotoURL() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isPhotoCorrect(URL u) {
		return getPhotos().contains(u);
	}
}
