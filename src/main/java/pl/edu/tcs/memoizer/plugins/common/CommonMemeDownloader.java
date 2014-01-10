package pl.edu.tcs.memoizer.plugins.common;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pl.edu.uj.tcs.memoizer.plugins.EViewType;
import pl.edu.uj.tcs.memoizer.plugins.IPluginFactory;
import pl.edu.uj.tcs.memoizer.plugins.Meme;

public abstract class CommonMemeDownloader {
	
	private String hostName;
	
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/*
	 * Get a page source, parse it,
	 * extract memes and return
	 */
	List<Meme> downloadMemesFromPage(URL url, EViewType viewType, IPluginFactory pluginFactory){
		return extractMemesFromNodes(
				extractMemeNodes(
				downloadPageSource(url)), viewType, pluginFactory);
	}
	
	/*
	 * Download page source from specific url
	 * If success returns downloaded page
	 * otherwise returns null
	 */
	private Document downloadPageSource(URL url){
		try{
			return Jsoup
					.connect(url.toString())
					.userAgent("Mozilla")
					.get();
		} catch(IOException e){}
		return null;
	}
	
	/*
	 * Extract html meme-linked elements 
	 * from given page source
	 */
	protected abstract Elements extractMemeNodes(Document demotyPageSource);
	
	/*
	 * Parse html meme-linked element and extract meme info
	 * returns list of parsed memes
	 */
	protected abstract List<Meme> extractMemesFromNodes(Elements memeNodes, EViewType viewType, IPluginFactory pluginFactory);
	
	private URL extractPageLinkFromATag(Element aTagElement){
		try{
			return new URL(hostName + aTagElement.attr("href"));
		}catch(Exception e){}
		return null;
	}
	
	private URL extractImageLinkFromImgTag(Element imgTagElement){
		try{
			return new URL(imgTagElement.attr("src"));
		} catch(Exception e){}
		return null;
	}
	
	private String extractTitleFromImgTag(Element imgTagElement){
		try{
			return imgTagElement.attr("alt");
		} catch(Exception e){}
		return "";
	}
	
	private int extractWidthFromImgTag(Element imgTagElement){
		try{
			return Integer.parseInt(imgTagElement.attr("width"));
		} catch(Exception e){}
		return 0;
	}
	
	private int extractHeightFromImgTag(Element imgTagElement){
		try{
			return Integer.parseInt(imgTagElement.attr("height"));
		} catch(Exception e){}
		return 0;
	}
}
