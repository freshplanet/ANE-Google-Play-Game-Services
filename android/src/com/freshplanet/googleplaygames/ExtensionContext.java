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

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.adobe.air.ActivityResultCallback;
import com.adobe.air.AndroidActivityWrapper;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.freshplanet.googleplaygames.functions.*;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtensionContext extends FREContext implements GameHelper.GameHelperListener, ActivityResultCallback
{
    private static GameHelper mHelper = null;
    private AndroidActivityWrapper aaw = null;

    final int RC_SHOW_ACHIEVEMENTS = 4237;

    public ExtensionContext()
    {
        aaw = AndroidActivityWrapper.GetAndroidActivityWrapper();
        aaw.addActivityResultListener(this);
    }
	
	@Override
	public void dispose()
    {
        if (aaw != null)
        {
            aaw.removeActivityResultListener(this);
            aaw = null;
        }
    }

	@Override
	public Map<String, FREFunction> getFunctions()
	{
		Map<String, FREFunction> functionMap = new HashMap<String, FREFunction>();
		functionMap.put("signIn", new AirGooglePlayGamesSignInFunction());
		functionMap.put("signOut", new AirGooglePlayGamesSignOutFunction());
		functionMap.put("reportAchievement", new AirGooglePlayGamesReportAchievementFunction());
		functionMap.put("reportScore", new AirGooglePlayGamesReportScoreFunction());
		functionMap.put("showStandardAchievements", new AirGooglePlayGamesShowAchievementsFunction());
		functionMap.put("getActivePlayerName", new AirGooglePlayGamesGetActivePlayerName());
		functionMap.put("getLeaderboard", new AirGooglePlayGamesGetLeaderboardFunction());
		functionMap.put("isSignedIn", new AirGooglePlayGamesIsSignedInFunction());
		return functionMap;
	}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        Extension.log("ExtensionContext.onActivityResult" +
                " requestCode:" + Integer.toString(requestCode) +
                " resultCode:" + Integer.toString(resultCode));

        if (requestCode == RC_SHOW_ACHIEVEMENTS && resultCode == GamesActivityResultCodes.RESULT_RECONNECT_REQUIRED)
        {
            mHelper.disconnect();
            mHelper = null;
            dispatchEvent("ON_SIGN_OUT_SUCCESS");
        }
        else if (mHelper != null)
        {
            mHelper.onActivityResult(requestCode, resultCode, intent);
        }
    }
	
	public void dispatchEvent(String eventName) {
        dispatchEvent(eventName, "OK");
	}
	
	public void logEvent(String eventName) {
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
			mHelper = new GameHelper(getActivity(), GameHelper.CLIENT_GAMES);// | GameHelper.CLIENT_PLUS);
			logEvent("setup");
			mHelper.setup(this);
		}
		return mHelper;
	}

	private List<Activity> _activityInstances;
	
	public void registerActivity(Activity activity)
	{
		if (_activityInstances == null)
		{
			_activityInstances = new ArrayList<Activity>();
		}
		_activityInstances.add(activity);
	}
	
	public void signOut()
	{
		logEvent("signOut");

		mHelper.signOut();
        dispatchEvent("ON_SIGN_OUT_SUCCESS");
	}

	public Boolean isSignedIn() {
        logEvent("isSignedIn");
        return mHelper.isSignedIn();
	}
	
	public GoogleApiClient getApiClient() {
        return mHelper.getApiClient();
    }

	public void reportAchievements(String achievementId)
	{
    	if (!isSignedIn()) {
            return;
        }
    	Games.Achievements.unlock(getApiClient(), achievementId);
	}

    public void showAchievements()
    {
        Intent achievementsIntent = Games.Achievements.getAchievementsIntent(mHelper.getApiClient());
        getActivity().startActivityForResult(achievementsIntent, RC_SHOW_ACHIEVEMENTS);
    }

	public void reportAchievements(String achievementId, double percentDouble)
	{
		if (percentDouble > 0 && percentDouble <= 1){
    		int percent = (int) (percentDouble * 100);
    		Games.Achievements.setSteps(getApiClient(), achievementId, percent);
    	}
	}
	
	public void reportScore(String leaderboardId, int highScore)
	{
		Games.Leaderboards.submitScore(getApiClient(), leaderboardId, highScore);
	}

    public void getLeaderboard( String leaderboardId ) {

		Games.Leaderboards.loadTopScores(
				getApiClient(),
				leaderboardId,
				LeaderboardVariant.TIME_SPAN_ALL_TIME,
				LeaderboardVariant.COLLECTION_SOCIAL,
				25,
				true
		).setResultCallback(new ScoresLoadedListener());
    }

    public void onLeaderboardLoaded( LeaderboardScoreBuffer scores ) {
        dispatchEvent( "ON_LEADERBOARD_LOADED", scoresToJsonString(scores) );
    }

    private String scoresToJsonString( LeaderboardScoreBuffer scores ) {

        int scoresNb = scores.getCount();
        JSONArray jsonScores = new JSONArray();
        for ( int i = 0; i < scoresNb; ++i ) {
            LeaderboardScore score = scores.get(i);
            JSONObject jsonScore = new JSONObject();
            try {
                jsonScore.put("value", score.getRawScore());
                jsonScore.put("rank", score.getRank());

                Player player = score.getScoreHolder();
                JSONObject jsonPlayer = new JSONObject();
                jsonPlayer.put("id", player.getPlayerId());
                jsonPlayer.put("displayName", player.getDisplayName());
                jsonPlayer.put("picture", player.getIconImageUri());

                jsonScore.put("player", jsonPlayer);

                jsonScores.put( jsonScore );

            } catch( JSONException e ) {}
        }
        return jsonScores.toString();

    }

	@Override
	public void onSignInFailed() {
		logEvent("onSignInFailed");
		dispatchEvent("ON_SIGN_IN_FAIL");
		if (_activityInstances != null)
		{
			for (Activity activity : _activityInstances)
			{
				if (activity != null)
				{
					activity.finish();
				}
			}
			_activityInstances = null;
		}
	}

	@Override
	public void onSignInSucceeded() {
		logEvent("onSignInSucceeded");
		dispatchEvent("ON_SIGN_IN_SUCCESS");
		if (_activityInstances != null)
		{
			for (Activity activity : _activityInstances)
			{
				if (activity != null)
				{
					activity.finish();
				}
			}
			_activityInstances = null;
		}
	}
	
}
