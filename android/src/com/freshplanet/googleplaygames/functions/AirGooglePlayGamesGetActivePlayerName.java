package com.freshplanet.googleplaygames.functions;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;
import com.freshplanet.googleplaygames.Extension;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;

public class AirGooglePlayGamesGetActivePlayerName implements FREFunction {

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		
		Extension.context.createHelperIfNeeded(arg0.getActivity());
		Player player = Games.Players.getCurrentPlayer(Extension.context.getApiClient());
		
		FREObject playerName = null;
		if (player != null)
		{
			try {
				playerName = FREObject.newObject(player.getDisplayName());
			} catch (FREWrongThreadException e) {
				e.printStackTrace();
			}
		}
		
		return playerName;
	}

}
