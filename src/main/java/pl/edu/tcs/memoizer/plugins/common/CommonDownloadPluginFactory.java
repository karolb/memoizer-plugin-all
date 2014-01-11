package pl.edu.tcs.memoizer.plugins.common;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
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
 * It's abstract class only to be extended by exact plugin implementation
 */
public abstract class CommonDownloadPluginFactory implements IPluginFactory{

	
	/** The available views. */
	Map<EViewType, ViewProperties> availableViews = new HashMap<EViewType, ViewProperties>();

	public abstract String getServiceName();

	/**
	 * Make the icon.
	 *
	 * @param T the new icon
	 */
	public static Image makeIcon(byte T[]) {
		try{
			return ImageIO.read(new ByteArrayInputStream(T)); 
		}catch(IOException e){
			return null;
		}
	}
	
	public abstract Image getIcon();
	
	/**
	 * Adds the view to Factory.
	 *
	 * @param viewType as type of view 
	 * @param name the name of view
	 * @param path URL to page
	 * 
	 * @throws RuntimeException when parameters are incomplete
	 */
	public void addView(EViewType viewType, String name, String path) throws RuntimeException{
		if(viewType == null || name == null || path == null)
			throw new RuntimeException("Incomplete parameters for this view.");
		availableViews.put(viewType, new ViewProperties(name, path));
	}
	
	/**
	 * Gets the view properties.
	 *
	 * @param viewType the view type
	 * @return the view properties
	 * @throws InvalidViewException - when view isn't available 
	 */
	public ViewProperties getViewProperties(EViewType viewType) throws InvalidViewException{
		ViewProperties viewProperty = availableViews.get(viewType);
		if(viewProperty == null)
			throw new InvalidViewException();
		return viewProperty;
	}

	/**
	 * Function to invoke by class which extend from CommonDownloadPluginFactory to create new instance.
	 *
	 * @param pluginState the plugin state
	 * @param viewType the view type
	 * @param downloadMeme type of implementation of download meme
	 * @return implementation of DownloadPlugin interface
	 * @throws InvalidViewException the invalid view exception
	 */
	public IDownloadPlugin newInstance(IStateObject pluginState, EViewType viewType, CommonMemeDownloader downloadMeme) throws InvalidViewException{
		ViewProperties newInstanceView = getViewProperties(viewType);
		try{
			if(viewType.equals(EViewType.RANDOM)){
				return new CommonSingleDownloader(newInstanceView.getName(), pluginState, viewType, new URI(newInstanceView.getPath()), this, downloadMeme);
			} else if(viewType.equals(EViewType.SEARCH)){
				throw new RuntimeException("Missing keyword for Searching");
			} else {
				return new CommonSequentialDownloader(newInstanceView.getName(), pluginState, viewType, new URI(newInstanceView.getPath()), this, downloadMeme);
			}
		}catch(URISyntaxException e){
			throw new InvalidViewException();
		}
	}
	
	public IDownloadPlugin newInstance(IStateObject pluginState, EViewType viewType, Object parameters, CommonMemeDownloader downloadMeme) throws InvalidViewException{
		ViewProperties newInstanceView = getViewProperties(viewType);
		if(viewType.equals(EViewType.SEARCH)){
			String searchKey = (String)parameters;
			try {
				String url = newInstanceView.getPath() + URLEncoder.encode(searchKey, "UTF-8");
				URI uri = new URI(url);
				return new CommonSequentialDownloader(newInstanceView.getName(), pluginState, viewType, uri, this, downloadMeme);
			} catch (UnsupportedEncodingException | URISyntaxException e) {
				e.printStackTrace();
				throw new InvalidViewException();
			}
		} else {
			return newInstance(pluginState, viewType, downloadMeme);
		}
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
