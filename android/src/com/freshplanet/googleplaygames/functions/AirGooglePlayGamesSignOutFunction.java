package com.freshplanet.googleplaygames.functions;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.freshplanet.googleplaygames.Extension;

public class AirGooglePlayGamesSignOutFunction implements FREFunction {

	public AirGooglePlayGamesSignOutFunction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		Extension.context.createHelperIfNeeded(arg0.getActivity());
		Extension.context.signOut();
		return null;
	}

}
