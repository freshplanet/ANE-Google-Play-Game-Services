package com.freshplanet.ane.AirGooglePlayGames
{
	import flash.events.Event;

	public class AirGooglePlayGamesLeaderboardEvent extends Event
	{
		
		public static const LEADERBOARD_LOADED:String = "AirGooglePlayGamesLeaderboardEvent.leaderboard_loaded";
		public static const LEADERBOARD_LOADING_FAILED:String = "AirGooglePlayGamesLeaderboardEvent.leaderboard_loading_failed";
		
		public var leaderboard:GSLeaderboard;
		
		public function AirGooglePlayGamesLeaderboardEvent( type:String, leaderboard:GSLeaderboard )
		{
			
			super( type );
			
			this.leaderboard = leaderboard;
			
		}
		
	}
}