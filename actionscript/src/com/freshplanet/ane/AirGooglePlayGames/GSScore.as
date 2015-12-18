package com.freshplanet.ane.AirGooglePlayGames
{
	public class GSScore
	{
		
		private var _value:int;
		public function get value():int { return _value; }
		private var _rank:int;
		public function get rank():int { return _rank; }
		private var _player:GSPlayer;
		public function get player():GSPlayer { return _player; }
		
		public function GSScore( value:int, rank:int, player:GSPlayer )
		{
			
			_value = value;
			_rank = rank;
			_player = player;
			
		}
		
		public static function fromJSONObject( jsonObject:Object ):GSScore {
			
			var player:GSPlayer = GSPlayer.fromJSONObject( jsonObject.player );
			
			if( player == null ) return null;
			
			return new GSScore( jsonObject.value, jsonObject.rank, player );
			
		}
		
	}
}