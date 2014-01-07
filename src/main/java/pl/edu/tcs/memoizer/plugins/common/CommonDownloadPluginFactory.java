package pl.edu.tcs.memoizer.plugins.common;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import pl.edu.uj.tcs.memoizer.plugins.EViewType;
import pl.edu.uj.tcs.memoizer.plugins.IDownloadPlugin;
import pl.edu.uj.tcs.memoizer.plugins.IPluginFactory;
import pl.edu.uj.tcs.memoizer.plugins.InvalidViewException;
import pl.edu.uj.tcs.memoizer.serialization.IStateObject;

/**
 * A factory for creating CommonDownloadPlugin objects.
 * It implements common functionality of IPluginFactory.
 */
public abstract class CommonDownloadPluginFactory implements IPluginFactory{
	
	String serviceName;
	Image icon;
	Map<EViewType, ViewProperties> availableViews = new HashMap<EViewType, ViewProperties>();

	/**
	 * Sets the service name.
	 *
	 * @param name the new service name
	 */
	public void setServiceName(String name) {
		serviceName = name;
	}
	
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Sets the icon.
	 *
	 * @param T the new icon
	 */
	public void setIcon(byte T[]) {
		try{
			icon = ImageIO.read(new ByteArrayInputStream(T)); 
		}catch(IOException e){
			icon = null;
		}
	}
	
	public Image getIcon(){
		return icon;
	}
	
	public void addView(EViewType viewType, String name, String path){
		if(viewType == null || name == null || path == null)
			throw new RuntimeException("Incomplete parameters for this view.");
		try {
			availableViews.put(viewType, new ViewProperties(name, path));
		} catch (URISyntaxException e) {
			throw new InvalidViewException();
		}
	}
	
	public ViewProperties getViewProperties(EViewType viewType){
		ViewProperties viewProperty = availableViews.get(viewType);
		if(viewProperty == null)
			throw new InvalidViewException();
		return viewProperty;
	}

	
	public abstract IDownloadPlugin newInstance(IStateObject pluginState,
			EViewType viewType) throws InvalidViewException;

	
	public abstract IDownloadPlugin newInstance(IStateObject pluginState,
			EViewType viewType, Object parameters) throws InvalidViewException;

	public List<EViewType> getAvailableDownloadViews() {
		List<EViewType> availableViewList = new ArrayList<EViewType>();
		availableViewList.addAll(availableViews.keySet());
		return availableViewList;
	}

}
