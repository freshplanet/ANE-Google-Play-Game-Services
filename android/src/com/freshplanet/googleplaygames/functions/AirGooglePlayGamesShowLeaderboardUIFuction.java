

    package com.freshplanet.googleplaygames.functions;

    import com.adobe.fre.FREContext;
    import com.adobe.fre.FREFunction;
    import com.adobe.fre.FREObject;
    import com.google.android.gms.games.Games;
    import com.freshplanet.googleplaygames.GameHelper;
    import com.freshplanet.googleplaygames.Extension;


    public class AirGooglePlayGamesShowLeaderboardUIFuction implements FREFunction {

        @Override
        public FREObject call(FREContext arg0, FREObject[] arg1) {
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


            if( leaderboardId != null ) {
                arg0.getActivity().startActivityForResult(Games.Leaderboards.getLeaderboardIntent(Extension.context.getApiClient(),leaderboardId), GameHelper.RC_UNUSED);
            }else{
                arg0.getActivity().startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(Extension.context.getApiClient()), GameHelper.RC_UNUSED);
            }
 

            return null;

        }

    }
