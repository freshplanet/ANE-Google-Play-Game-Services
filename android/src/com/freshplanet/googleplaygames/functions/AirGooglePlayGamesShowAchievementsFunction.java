package com.freshplanet.googleplaygames.functions;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.freshplanet.googleplaygames.Extension;

public class AirGooglePlayGamesShowAchievementsFunction implements FREFunction {
	
	public AirGooglePlayGamesShowAchievementsFunction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {

		Extension.context.createHelperIfNeeded(arg0.getActivity());
        if (Extension.context.isSignedIn()) {
        	Extension.context.showAchievements();
        }
		
		return null;
	}
}
