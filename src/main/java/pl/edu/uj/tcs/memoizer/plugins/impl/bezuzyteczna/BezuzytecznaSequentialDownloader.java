package pl.edu.uj.tcs.memoizer.plugins.impl.bezuzyteczna;

import java.net.URI;

import pl.edu.uj.tcs.memoizer.plugins.EViewType;
import pl.edu.uj.tcs.memoizer.plugins.IPluginFactory;
import pl.edu.uj.tcs.memoizer.plugins.common.CommonMemeDownloader;
import pl.edu.uj.tcs.memoizer.plugins.common.CommonSequentialDownloader;
import pl.edu.uj.tcs.memoizer.serialization.IStateObject;

public class BezuzytecznaSequentialDownloader extends CommonSequentialDownloader{

	public BezuzytecznaSequentialDownloader(String serviceName,
			IStateObject state, EViewType view, URI workingUrl,
			IPluginFactory pluginFactory, CommonMemeDownloader memeDownloader) {
		super(serviceName, state, view, workingUrl, pluginFactory, memeDownloader);
	}

}
