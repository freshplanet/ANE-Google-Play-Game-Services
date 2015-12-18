package com.freshplanet.ane.AirGooglePlayGames
{
	public class GSPlayer
	{
		
		private var _id:String;
		public function get id():String { return _id; }
		private var _displayName:String;
		public function get displayName():String { return _displayName; }
		private var _picture:String;
		public function get picture():String { return _picture; }
		
		public function GSPlayer( id:String, displayName:String, picture:String = null )
		{
			
			_id = id;
			_displayName = displayName;
			_picture = picture;
			
		}
		
		public static function fromJSONObject( jsonObject:Object ):GSPlayer {
			
			if( jsonObject.id == null ) return null;
			
			return new GSPlayer( jsonObject.id, jsonObject.displayName, jsonObject.picture );
			
		}
		
	}
}