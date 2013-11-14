package com.freshplanet.googleplaygames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SignInActivity extends Activity {

	private GameHelper mHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Extension.context.logEvent("sign in activiy started");
		super.onCreate(savedInstanceState);
		mHelper = Extension.context.createHelperIfNeeded(this);
		Extension.context.signIn();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		mHelper.onStart(this);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		mHelper.onActivityResult(requestCode, resultCode, data);
		finish();
	}

}
