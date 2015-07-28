Air Native Extension for Google Play Games Services (Android)
======================================

This is an [Air native extension](http://www.adobe.com/devnet/air/native-extensions-for-air.html) for [Google Play Games Services](http://developer.android.com/google/play-services/games.html/) SDK on Android. It has been developed by [FreshPlanet](http://freshplanet.com) and is used in the game [MoviePop](http://moviepop.net).


Installation
---------

The ANE binary (AirGooglePlayGameServices.ane) is located in the *bin* folder. You should add it to your application project's Build Path and make sure to package it with your app (more information [here](http://help.adobe.com/en_US/air/build/WS597e5dadb9cc1e0253f7d2fc1311b491071-8000.html)).


Usage
-----

You need to add the following in your AIR manifest, and replace YOUR_APP_ID by the app id of your Google Play Games Services (don't remove the "\ " at the beginning, otherwise it won't work):

	<application>
		<!-- GooglePlay Games Services -->
		<meta-data android:name="com.google.android.gms.games.APP_ID" android:value="\ YOUR_APP_ID" />
		<activity android:name="com.freshplanet.googleplaygames.SignInActivity" android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen" />
	</application>



```actionscript
// Initialize
AirGooglePlayGames.getInstance().addEventListener(AirGooglePlayGamesEvent.ON_SIGN_IN_SUCCESS, onSignInSuccess);
AirGooglePlayGames.getInstance().addEventListener(AirGooglePlayGamesEvent.ON_SIGN_OUT_SUCCESS, onSignOutSuccess);
AirGooglePlayGames.getInstance().addEventListener(AirGooglePlayGamesEvent.ON_SIGN_IN_FAIL, onSignInFail);
AirGooglePlayGames.getInstance().startAtLaunch();

// Update Achievement
// with steps
AirGooglePlayGames.getInstance().reportAchievement(achievementId, step);
// without steps
AirGooglePlayGames.getInstance().reportAchievement(achievementId);

// Update HighScore
AirGooglePlayGames.getInstance().reportScore(leaderbordId, value);

// Show Achivements
AirGooglePlayGames.getInstance().showStandardAchievements();
```


Build script
---------

Should you need to edit the extension source code and/or recompile it, you will find an ant build script (build.xml) in the *build* folder:

```bash
cd /path/to/the/ane/build
mv example.build.config build.config
#edit the build.config file to provide your machine-specific paths
ant
```


Authors
------

This ANE has been written by [Thibaut Crenn]. It belongs to [FreshPlanet Inc.](http://freshplanet.com) and is distributed under the [Apache Licence, version 2.0](http://www.apache.org/licenses/LICENSE-2.0).