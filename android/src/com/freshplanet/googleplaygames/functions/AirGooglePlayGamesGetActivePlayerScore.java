package com.freshplanet.googleplaygames.functions;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.freshplanet.googleplaygames.Extension;

public class AirGooglePlayGamesGetActivePlayerScore implements FREFunction {
	
	@Override
    public FREObject call(FREContext arg0, FREObject[] arg1) {

        Extension.context.createHelperIfNeeded(arg0.getActivity());

        // Retrieve alert parameters
        String leaderboardId = null;
        try
        {
            leaderboardId = arg1[0].getAsString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        if( leaderboardId != null )
            Extension.context.getPlayerScore( leaderboardId );

		return null;

    }

}
