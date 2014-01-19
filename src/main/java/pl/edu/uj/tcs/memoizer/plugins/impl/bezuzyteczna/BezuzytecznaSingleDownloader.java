package pl.edu.uj.tcs.memoizer.plugins.impl.bezuzyteczna;

import java.net.URI;

import pl.edu.uj.tcs.memoizer.plugins.EViewType;
import pl.edu.uj.tcs.memoizer.plugins.IPluginFactory;
import pl.edu.uj.tcs.memoizer.plugins.common.CommonMemeDownloader;
import pl.edu.uj.tcs.memoizer.plugins.common.CommonSingleDownloader;
import pl.edu.uj.tcs.memoizer.serialization.IStateObject;

public class BezuzytecznaSingleDownloader extends CommonSingleDownloader{

	public BezuzytecznaSingleDownloader(String serviceName, IStateObject state,
			EViewType view, URI workingUrl, IPluginFactory pluginFactory,
			CommonMemeDownloader memeDownloader) {
		super(serviceName, state, view, workingUrl, pluginFactory, memeDownloader);
	}

}
