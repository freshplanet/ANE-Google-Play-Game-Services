package com.freshplanet.googleplaygames.functions;

import android.content.Intent;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.freshplanet.googleplaygames.SignInActivity;

public class AirGooglePlayGamesSignInFunction implements FREFunction {

	public AirGooglePlayGamesSignInFunction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		Intent intent = new Intent(arg0.getActivity().getApplicationContext(), SignInActivity.class);
		arg0.getActivity().startActivity(intent);
		return null;
	}

}
