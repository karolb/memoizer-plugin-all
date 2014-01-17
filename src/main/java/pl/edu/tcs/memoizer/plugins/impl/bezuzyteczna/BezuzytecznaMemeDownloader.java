package pl.edu.tcs.memoizer.plugins.impl.bezuzyteczna;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pl.edu.tcs.memoizer.plugins.common.CommonMemeDownloader;
import pl.edu.uj.tcs.memoizer.plugins.EViewType;
import pl.edu.uj.tcs.memoizer.plugins.IPluginFactory;
import pl.edu.uj.tcs.memoizer.plugins.Meme;

public class BezuzytecznaMemeDownloader extends CommonMemeDownloader{

	@Override
	protected Elements extractMemeNodes(Document demotyPageSource) {
		Elements result = new Elements();
		
		if(demotyPageSource == null)
			return result;
		
		Elements demotySection = demotyPageSource.select("section#entries");
		for(Element elem : demotySection){
			Elements demotivators = elem.select("ul.entries > li[id]");
			result.addAll(demotivators);
		}
		
		return result;
	}

	@Override
	protected List<Meme> extractMemesFromNodes(Elements memeNodes,
			EViewType viewType, IPluginFactory pluginFactory) {
List<Meme> lst = new ArrayList<Meme>();
		
		for(Element meme : memeNodes){
			try{
				Element picLink = meme.select("div.entry_img > a[href]").first();
				URL pageLink = extractPageLinkFromATag(picLink);
				
				//<li id="_75839">
				Integer idInput = Integer.parseInt(meme.attr("id").substring(1));
				
				Element image = picLink.select("div.entry_img > a > img[src]").first();
				URL imageLink = extractImageLinkFromImgTag(image);
				

				String fullTitle = extractTitleFromImgTag(image), title = null, description = null;
				int split = fullTitle.indexOf('–');
				System.out.println(fullTitle+" "+split);
				if(split<0)
					title = fullTitle;
				else if(split==0)
					title = fullTitle.substring(1, fullTitle.length()-1).trim();
				else if(split==fullTitle.length()-1)
					title = fullTitle.substring(0, fullTitle.length()-2).trim();
				else{
					title = fullTitle.substring(0, split-1).trim();
					description = fullTitle.substring(split+1, fullTitle.length()-1).trim();
				}
				
				
				int width = extractWidthFromImgTag(image);
				int heigth = extractHeightFromImgTag(image);
				
				if(imageLink != null){
					Meme newMeme = new Meme(imageLink, pageLink, title, description, width, heigth, null, pluginFactory);
					newMeme.setId(idInput);
					lst.add(newMeme);
				}
			} catch(Exception e){
				
			}
		}
		
		return lst;
	}
	
	private URL extractPageLinkFromATag(Element aTagElement){
		try{
			return new URL(aTagElement.attr("href"));
		}catch(Exception e){}
		return null;
	}

}
