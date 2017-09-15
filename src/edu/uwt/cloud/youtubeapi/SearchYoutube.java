/**
 * 
 */
package edu.uwt.cloud.youtubeapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

/**
 * @author smrut
 *
 */
public class SearchYoutube {


	private static final long SEARCH_RESULT_COUNT = 10;
	private static final String YOUTUBE_APIKEY="<Fill API KEY>";
	private String searchKeyword ="";
	private String videoId ="";
	public YouTube youtube;
	public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	public static final JsonFactory JSON_FACTORY = new JacksonFactory();
	public List<SearchResult> searchResult = new ArrayList<SearchResult>();
	public Video videoResult = new Video();
	public Channel channelResult = new Channel();

	public SearchYoutube(){
		
		HttpRequestInitializer httpRequestInitializer = new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }
        };
    	
		youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, httpRequestInitializer)
				.setApplicationName("youtube-cmdline-search-sample").build();

	}
	public Channel getChannelResult() {
		return channelResult;
	}
	
	public List getSearchResult() {
		invokeAPI();
		return searchResult;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String s) {
		searchKeyword=s;
	}
	
	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}


	public Video getVideoResult() {
		invokeVideoAPI();
			return videoResult;
	}
	
		
	private void invokeVideoAPI() {
		
	    try {

	    	System.out.println("********InvokeVideoAPI***********"+videoId);	    	
            YouTube.Videos.List youtubeVideo = youtube.videos().list("id,snippet");
            youtubeVideo.setKey(YOUTUBE_APIKEY);
            youtubeVideo.setId(videoId);
            youtubeVideo.setFields("items(id,snippet/title,snippet/channelTitle, snippet/description,snippet/channelId)");
            youtubeVideo.setMaxResults(SEARCH_RESULT_COUNT);

            // Call the API and print results.
            VideoListResponse videoResponse = youtubeVideo.execute();
            List<Video> videoList = videoResponse.getItems();
            if (videoList != null) {
                System.out.println("***************"+videoList);
              videoResult = videoList.get(0);         
              invokeChannelAPI(youtube, videoResult.getSnippet().getChannelId());

            }
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
		
	}
		
	private void invokeChannelAPI(YouTube youtube,String channelId){
		
		 try {
			 System.out.println("********invokeChannelAPI***********");
			 YouTube.Channels.List youtubeChannels= youtube.channels().list("id,snippet,status,statistics");
			 youtubeChannels.setId(channelId);
			 youtubeChannels.setKey(YOUTUBE_APIKEY);
			 
			ChannelListResponse channelListresponse = youtubeChannels.execute();			
			
			if(null!=channelListresponse.getItems() && channelListresponse.getItems().size()>=0){
				 channelResult= channelListresponse.getItems().get(0);
				 System.out.println(channelResult);
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}		
	}

	private void invokeAPI(){	
		    try {
		    	
				 System.out.println("********invokeAPI***********"+searchKeyword);  	

	            // Define the API request for retrieving search results.
	            YouTube.Search.List search = youtube.search().list("id,snippet");
	            search.setKey(YOUTUBE_APIKEY);
	            search.setQ(searchKeyword);
	            search.setType("video");
	            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
	            search.setMaxResults(SEARCH_RESULT_COUNT);

	            // Call the API and print results.
	            SearchListResponse searchResponse = search.execute();
	            List<SearchResult> searchResultList = searchResponse.getItems();
	            if (searchResultList != null) {
	                System.out.println("***************"+searchResultList);
                    Iterator<SearchResult> iteratorSearchResults=searchResultList.iterator();

	    	        if (!iteratorSearchResults.hasNext()) {
	    	            System.out.println(" There aren't any results for your query.");
	    	        }
	    	        while (iteratorSearchResults.hasNext()) {

	    	            SearchResult singleVideo = iteratorSearchResults.next();
	    	            ResourceId rId = singleVideo.getId();

	    	            // Confirm that the result represents a video. Otherwise, the
	    	            // item will not contain a video ID.
	    	            if (null!=rId && null!=rId.getKind() && rId.getKind().equals("youtube#video")) {
	    		            this.searchResult.add(singleVideo);
	    	            }
	    	        }
	    	        System.out.println(this.searchResult);
	    	   	                
	            }
	        } catch (GoogleJsonResponseException e) {
	            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
	                    + e.getDetails().getMessage());
	        } catch (IOException e) {
	            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
	        } catch (Throwable t) {
	            t.printStackTrace();
	        }
	}
	
}
