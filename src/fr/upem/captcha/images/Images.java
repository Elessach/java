package fr.upem.captcha.images;

import java.net.URL;
import java.util.List;

public interface Images {
	public List<URL> getPhotos();
	public List<URL> getRandomPhotosURL(int i);
	public URL getRandomPhotoURL();
	public boolean isPhotoCorrect(URL u);
}
