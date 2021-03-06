package com.codepath.apps.twitter;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY ="i6SQZRgQKsALVM72truBTkYvc"; //"IiYlWhmOMmEG48iLWgtvG1tbu";       // Change this
	public static final String REST_CONSUMER_SECRET = "jaMjxueujz6zHgWO9c9DYr5rZWfnDpb9Ogwcm7RHDEG2Y3aAQ1"; //"YvQFqAlkAsjCW3DPM241BjVv65sqv0FIMmfBS05kd24LAwFqaP"; // Change this
	public static final String REST_CALLBACK_URL ="oauth://Twitter_Client123"; // "oauth://CPTwitterProject"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}

	public void getHomeTimeline(AsyncHttpResponseHandler handler) {
		getHomeTimeline(0, handler);
	}

	//METHOD **** endpoint
	/*  -Get home time line for the user
		GET: statuses/home_timeline.json
		count = 25
		since_id =1*/
	public void getHomeTimeline(long max_id, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		//params.put("since_id", 1);
		params.put("max_id", String.valueOf(max_id));
		getClient().get(apiUrl, params, handler);
	}

	public void getHomeTimeline(long max_id, long since_id, int page, AsyncHttpResponseHandler handler){

		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 40);
		params.put("page", String.valueOf(page));
		if(page == 0){
			Log.d("page count", String.valueOf(page));
			params.put("since_id", since_id);
		}
		else{
			Log.d("page count", String.valueOf(page) + "since_id " + String.valueOf(since_id) + "max_id" + String.valueOf(max_id));
			params.put("max_id", max_id);
			params.put("since_id", since_id);
		}

		getClient().get(apiUrl, params, handler);
	}


	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */

	public void postTweet(String status, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", status);
		getClient().post(apiUrl, params, handler);
	}


	public void getUserProfile(AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("account/verify_credentials.json");
		getClient().get(apiUrl, null, handler);
	}

	public void getMentionsTimeline(int page,AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 40);
		params.put("page", String.valueOf(page));
		getClient().get(apiUrl, params, handler);
	}

	public void getUserTimeline(String screen_name, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 40);
		params.put("screen_name", screen_name);
		getClient().get(apiUrl, params, handler);
	}

	public void getRetweet(Long id, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/retweet");
		RequestParams params = new RequestParams();

		getClient().get(apiUrl, null, handler);
	}

	public void favorite(Long id, boolean toCreate, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("favorites/" + (toCreate ? "create" : "destroy") + ".json");
		RequestParams params = new RequestParams();
		params.put("id", id);
		getClient().get(apiUrl, params, handler);
	}


}