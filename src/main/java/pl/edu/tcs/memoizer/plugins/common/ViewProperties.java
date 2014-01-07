package pl.edu.tcs.memoizer.plugins.common;

import java.net.URI;
import java.net.URISyntaxException;

public class ViewProperties {
	private String name;
	private URI path;
	
	ViewProperties(String viewName, String pathName) throws URISyntaxException{
		name = viewName;
		path = new URI(pathName);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public URI getPath() {
		return path;
	}
	
	public void setPath(URI path) {
		this.path = path;
	}
	
	public void setPath(String pathName) throws URISyntaxException{
		this.path = new URI(pathName);
	}
	
}
