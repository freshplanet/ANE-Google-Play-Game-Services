package com.freshplanet.googleplaygames.functions;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.freshplanet.googleplaygames.Extension;
import com.google.android.gms.games.Games;

public class AirGooglePlayGamesShowAchievementsFunction implements FREFunction {
	
    final int RC_UNUSED = 5001;
    
	public AirGooglePlayGamesShowAchievementsFunction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {

		Extension.context.createHelperIfNeeded(arg0.getActivity());
        if (Extension.context.isSignedIn()) {
        	arg0.getActivity().startActivityForResult(Games.Achievements.getAchievementsIntent(Extension.context.getApiClient()), RC_UNUSED);
        }
		
		return null;
	}

}
