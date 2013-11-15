package com.freshplanet.googleplaygames.functions;

import android.content.Intent;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.freshplanet.googleplaygames.Extension;
import com.freshplanet.googleplaygames.SignInActivity;

public class AirGooglePlayStartAtLaunch implements FREFunction {

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		Extension.context.logEvent("AirGooglePlayStartAtLaunch");
		Intent intent = new Intent(arg0.getActivity().getApplicationContext(), SignInActivity.class);
		intent.putExtra("shouldStartSignInFlow", false);
		arg0.getActivity().startActivity(intent);
		
		return null;
	}

}
