package pl.edu.tcs.memoizer.plugins.impl.common;

import java.awt.Image;
import java.util.List;

import pl.edu.uj.tcs.memoizer.plugins.EViewType;
import pl.edu.uj.tcs.memoizer.plugins.IDownloadPlugin;
import pl.edu.uj.tcs.memoizer.plugins.IPluginFactory;
import pl.edu.uj.tcs.memoizer.plugins.InvalidViewException;
import pl.edu.uj.tcs.memoizer.serialization.IStateObject;

public class CommonDownloadPluginFactory implements IPluginFactory{

	public String getServiceName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Image getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	public IDownloadPlugin newInstance(IStateObject pluginState,
			EViewType viewType) throws InvalidViewException {
		// TODO Auto-generated method stub
		return null;
	}

	public IDownloadPlugin newInstance(IStateObject pluginState,
			EViewType viewType, Object parameters) throws InvalidViewException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<EViewType> getAvailableDownloadViews() {
		// TODO Auto-generated method stub
		return null;
	}

}
