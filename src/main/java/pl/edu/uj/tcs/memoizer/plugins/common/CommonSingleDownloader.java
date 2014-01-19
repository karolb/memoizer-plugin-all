package pl.edu.uj.tcs.memoizer.plugins.common;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import pl.edu.uj.tcs.memoizer.plugins.EViewType;
import pl.edu.uj.tcs.memoizer.plugins.IDownloadPlugin;
import pl.edu.uj.tcs.memoizer.plugins.IPluginFactory;
import pl.edu.uj.tcs.memoizer.plugins.Meme;
import pl.edu.uj.tcs.memoizer.serialization.IStateObject;

public class CommonSingleDownloader implements IDownloadPlugin{
	/*
	 * IPlugin members
	 */
	private String _serviceName;
	private IStateObject _state;
	private IPluginFactory _pluginFactory;
	
	/*
	 * IDownloadPlugin members
	 */
	private URI _workingUrl;
	private EViewType _view;
	
	/*
	 * Implementation specific members
	 */
	private Queue<Meme> _queue;
	private CommonMemeDownloader _memeDownloader;
	
	
	/*
	 * Create new sequential downloader
	 */
	public CommonSingleDownloader(
			String serviceName, IStateObject state, 
			EViewType view, URI workingUrl, IPluginFactory 	pluginFactory,
			CommonMemeDownloader memeDownloader) 
	{
		_serviceName = serviceName;
		_state = state;
		
		_view = view;
		_workingUrl = workingUrl;

		_queue = new LinkedList<Meme>();
		_pluginFactory = pluginFactory;
		_memeDownloader = memeDownloader;
	}
	
	/*
	 * Returns plugin state
	 */
	public IStateObject getState() {
		return _state;
	}
	
	/*
	 * Returns name of handled service
	 */
	public String getServiceName() {
		return _serviceName; 
	}
	
	/*
	 * Gets view as plugin work mode
	 */
	public EViewType getView(){
		return _view;
	}
	
	/*
	 * Check if there is next meme to return
	 * in current view
	 */

	public final boolean hasNext(){
		return !_queue.isEmpty() || updateWaitingQueue();
	}
	
	/*
	 * Returns next meme from waiting queue
	 */

	public final Meme getNext(){
		if(hasNext())
			return _queue.remove();
		return null;
	}
	
	/*
	 * Returns next n memes from waiting queue
	 */

	public Iterable<Meme> getNext(int n){
		List<Meme> result = new LinkedList<Meme>();
		while(n-- > 0 && hasNext()){
			result.add(getNext());
		}
		return result;
	}
	
	private boolean updateWaitingQueue() {
		List<Meme> newMemes = downloadMemes();
		for(Meme meme : newMemes){
			meme.setPluginFactory(this._pluginFactory);
			_queue.add(meme);
		}
		return !_queue.isEmpty();
	}
	
	private List<Meme> downloadMemes() {
		try {
			return _memeDownloader.downloadMemesFromPage(this._workingUrl.toURL(), _view, this._pluginFactory);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return new ArrayList<Meme>();
		}
	}
}
