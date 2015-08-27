package com.android_gazete;

import com.android_gazete.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebActivity extends Activity{
	private static String TAG = "WebActivity";
	private static Intent intent=null;
	ProgressBar pg;
	WebView webview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Log.v(TAG, "onCreate");
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);//Simply rotating circle in top right corner in Actionbar which represent some background action.
		setProgressBarIndeterminateVisibility(true); 
		setContentView(R.layout.web_activity);
		
		intent= getIntent();
		
		ActionBar actionbar= getActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
		
		webview = (WebView) findViewById(R.id.webview);
		pg = (ProgressBar) findViewById(R.id.progressBar1);//progress bar inside webview
		webview.setVisibility(View.GONE);
		
	    webview.getSettings().setJavaScriptEnabled(true);
	    webview.setClickable(true);
	    webview.setFocusableInTouchMode(true);
	    
	
	    
	    webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
	    
	    //sayfayý tam web view sýðacak þekilde boyutlama
	    webview.getSettings().setLoadWithOverviewMode(true);
	    webview.getSettings().setUseWideViewPort(true);
	    
	    webview.getSettings().setLoadsImagesAutomatically(true);
	    //zoom açma
	    webview.getSettings().setBuiltInZoomControls(true);
	    
	    //web clientý belirleme
	    webview.loadUrl(intent.getStringExtra("webadress"));
	    webview.setWebViewClient(new MyBrowser());
	    
	  
	}
	
	
	private class MyBrowser extends WebViewClient {
			
		//web sayfasýný webview içinde yükleme
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
		     view.loadUrl(url);
		     setProgressBarIndeterminateVisibility(true);
		      return true;
		}
		      
		 @Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, favicon);
		}
		 
		 @Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			setProgressBarIndeterminateVisibility(false);
			webview.setVisibility(View.VISIBLE);
			pg.setVisibility(View.GONE);
		}
		 
		
	}
	//bir sayfa geri gitme
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
			webview.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	//bir önceki aktivitiye geri dönme
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		   switch (item.getItemId()) 
	        {
	        case android.R.id.home: 
	            onBackPressed();
	            break;

	        default:
	            return super.onOptionsItemSelected(item);
	        }
	        return true;
	}

}
