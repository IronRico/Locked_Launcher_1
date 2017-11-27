package com.citytelecoin.basic.InmateService;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.citytelecoin.basic.R;

public class InmateService extends Activity {

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		//constructor
		super.onCreate(savedInstanceState);

		//Dismisses the keyguard (lock screen)
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

		//Sets view to activity_main.xml
		setContentView(R.layout.webview);


		//permission to use the camera
		String camPermission = Manifest.permission.CAMERA;
		int camGrant = ContextCompat.checkSelfPermission(this, camPermission);
		if (camGrant != PackageManager.PERMISSION_GRANTED) {
			String[] permission_list = new String[1];
			permission_list[0] = camPermission;
			ActivityCompat.requestPermissions(this, permission_list, 1);
		}

		//permission to use audio
		String micPermission = Manifest.permission.RECORD_AUDIO;
		int micGrant = ContextCompat.checkSelfPermission(this, micPermission);
		if (micGrant != PackageManager.PERMISSION_GRANTED) {
			String[] permission_list = new String[1];
			permission_list[0] = micPermission;
			ActivityCompat.requestPermissions(this, permission_list, 1);
		}

		//Initializes WebView and set it to id webview in activity_main.xml in layout
		WebView myWebView = findViewById(R.id.webview);

		//WebView Settings
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setAllowContentAccess(true);
		webSettings.setLoadsImagesAutomatically(true);
		webSettings.setAllowFileAccess(true);
		webSettings.getDatabaseEnabled();

		//Set WebViewClient and ChromeClient
		myWebView.setWebViewClient(new MyWebViewClient());
		myWebView.setWebChromeClient(new WebChromeClient(){
			// Need to gather permissions to use the camera and audio
			@Override
			public void onPermissionRequest(final PermissionRequest request) {
				InmateService.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {

						request.grant(request.getResources());
					}
				});
			}
		});

		//Initializes URL that will be loaded into WebView
		//private String url = "https://vidtest.citytelecoin.com/kiosk/";
		String url = "https://vidtest.citytelecoin.com/tablet/index.html";

		myWebView.loadUrl(url);
	}
}
