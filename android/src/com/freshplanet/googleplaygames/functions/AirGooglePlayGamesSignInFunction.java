package com.freshplanet.googleplaygames.functions;

import android.app.Activity;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.freshplanet.googleplaygames.Extension;
import com.freshplanet.googleplaygames.GameHelper;

public class AirGooglePlayGamesSignInFunction implements FREFunction {

	public AirGooglePlayGamesSignInFunction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {

        Activity appActivity = arg0.getActivity();
        GameHelper mHelper = Extension.context.createHelperIfNeeded(null);

        mHelper.onStart(appActivity);
        mHelper.beginUserInitiatedSignIn();

        return null;
	}

}
