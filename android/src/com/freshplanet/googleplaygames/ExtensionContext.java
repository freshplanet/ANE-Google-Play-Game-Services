//////////////////////////////////////////////////////////////////////////////////////
//
//  Copyright 2013 Freshplanet (http://freshplanet.com | opensource@freshplanet.com)
//  
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//  
//    http://www.apache.org/licenses/LICENSE-2.0
//  
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//  
//////////////////////////////////////////////////////////////////////////////////////

package com.freshplanet.googleplaygames;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.freshplanet.googleplaygames.functions.AirGooglePlayGamesGetActivePlayerName;
import com.freshplanet.googleplaygames.functions.AirGooglePlayGamesReportAchievementFunction;
import com.freshplanet.googleplaygames.functions.AirGooglePlayGamesReportScoreFunction;
import com.freshplanet.googleplaygames.functions.AirGooglePlayGamesShowAchievementsFunction;
import com.freshplanet.googleplaygames.functions.AirGooglePlayGamesSignInFunction;
import com.freshplanet.googleplaygames.functions.AirGooglePlayGamesSignOutFunction;
import com.google.android.gms.games.GamesClient;

public class ExtensionContext extends FREContext implements GameHelper.GameHelperListener
{
	
    final int RC_UNUSED = 5001;
	// Public API
	
	public static GameHelper mHelper;
	
	public static boolean AUTOSIGNIN;
	
	@Override
	public void dispose() { }

	@Override
	public Map<String, FREFunction> getFunctions()
	{
		Map<String, FREFunction> functionMap = new HashMap<String, FREFunction>();
		functionMap.put("startAtLaunch", new AirGooglePlayGamesSignInFunction());
		functionMap.put("signIn", new AirGooglePlayGamesSignInFunction());
		functionMap.put("signOut", new AirGooglePlayGamesSignOutFunction());
		functionMap.put("reportAchievemnt", new AirGooglePlayGamesReportAchievementFunction());
		functionMap.put("reportScore", new AirGooglePlayGamesReportScoreFunction());
		functionMap.put("showStandardAchievements", new AirGooglePlayGamesShowAchievementsFunction());
		functionMap.put("getActivePlayerName", new AirGooglePlayGamesGetActivePlayerName());
		return functionMap;	
	}
	
	public void dispatchEvent(String eventName)
	{
		dispatchEvent(eventName, "OK");
	}
	
	public void logEvent(String eventName)
	{
		Log.i("[AirGooglePlayGames]", eventName);
	}

	
	public void dispatchEvent(String eventName, String eventData)
	{
		logEvent(eventName);
		if (eventData == null)
		{
			eventData = "OK";
		}
		dispatchStatusEventAsync(eventName, eventData);
	}
	
	public GameHelper createHelperIfNeeded(Activity activity)
	{
		if (mHelper == null)
		{
			logEvent("create helper");
			mHelper = new GameHelper(activity, AUTOSIGNIN);
			logEvent("setup");
			mHelper.setup(this, GameHelper.CLIENT_GAMES);
		}
		return mHelper;
	}
	
	public void signIn()
	{
		logEvent("signIn");
		mHelper.beginUserInitiatedSignIn();
	}
	
	public void signOut()
	{
		logEvent("signOut");

		mHelper.signOut();
		dispatchEvent("ON_SIGN_OUT_SUCCESS");
	}

	public Boolean isSignedIn()
	{
		logEvent("isSignedIn");
        return mHelper.isSignedIn();
	}
	
	public GamesClient getGamesClient() {
        return mHelper.getGamesClient();
    }

	
	public void reportAchivements(String achievementId)
	{
    	if (!isSignedIn()) {
            return;
        }
    	getGamesClient().unlockAchievement(achievementId);
	}

	
	public void reportAchivements(String achievementId, double percentDouble)
	{
		if (percentDouble > 0 && percentDouble <= 1){
    		int percent = (int) (percentDouble * 100);
    		getGamesClient().loadAchievements(new AchievementsLoadListener(achievementId, percent));
    	}
	}
	
	public void reportScore(String leaderboardId, int highScore)
	{
    	getGamesClient().submitScore(leaderboardId, highScore);
	}

	@Override
	public void onSignInFailed() {
		logEvent("onSignInFailed");
		dispatchEvent("ON_SIGN_IN_FAIL");
	}

	@Override
	public void onSignInSucceeded() {
		logEvent("onSignInSucceeded");
		dispatchEvent("ON_SIGN_IN_SUCCESS");
	}
	
}
