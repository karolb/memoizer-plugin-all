package pl.edu.tcs.memoizer.plugins.common;


public class ViewProperties {
	private String name;
	private String path;
	
	ViewProperties(String viewName, String pathName){
		name = viewName;
		path = pathName;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
}
