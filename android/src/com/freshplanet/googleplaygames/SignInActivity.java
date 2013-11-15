package com.freshplanet.googleplaygames;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SignInActivity extends Activity implements GameHelper.GameHelperListener {

	private GameHelper mHelper;
	
	private boolean shouldStartSignInFlow;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Extension.context.logEvent("sign in activiy started");
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		shouldStartSignInFlow = true;

		if (extras != null)
		{
			Extension.context.logEvent("hasExtra");
			shouldStartSignInFlow = extras.getBoolean("shouldStartSignInFlow"); // this will prevent prompting the UI at launch if the user hasn't register yet with Google Play
			Extension.context.logEvent("shouldStartSignInFlow : " +Boolean.toString(shouldStartSignInFlow));
		}
		Extension.context.logEvent("shouldStartSignInFlow2 : " +Boolean.toString(shouldStartSignInFlow));
		mHelper = Extension.context.createHelperIfNeeded(this);
		
		Extension.context.registerActivity(this);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		Extension.context.logEvent("autosignIn");
		mHelper.onStart(this, !shouldStartSignInFlow);
		if (shouldStartSignInFlow)
		{
			Extension.context.logEvent("signIn");
			mHelper.beginUserInitiatedSignIn();
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		mHelper.onActivityResult(requestCode, resultCode, data);
		finish();
	}

	@Override
	public void onSignInFailed() {
		Extension.context.onSignInFailed();
	}

	@Override
	public void onSignInSucceeded() {
		Extension.context.onSignInSucceeded();
	}

}
