package com.freshplanet.ane.AirGooglePlayGames
{
	public class GSLeaderboard
	{
		
		private var _scores:Vector.<GSScore>;
		public function get scores():Vector.<GSScore> { return _scores.slice(); }
		
		public function GSLeaderboard()
		{
			
			_scores = new <GSScore>[];
			
		}
		
		public static function fromJSONObject( jsonArray:Array ):GSLeaderboard {
			
			var leaderboard:GSLeaderboard = new GSLeaderboard();
			
			for each ( var scoreObject:Object in jsonArray ) {
				leaderboard._scores.push( GSScore.fromJSONObject( scoreObject ) );
			}
			
			return leaderboard;
			
		}
		
	}
}