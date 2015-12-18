package com.freshplanet.googleplaygames;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.PageDirection;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.Leaderboards;

/**
 * Created by renaud on 09/09/2014.
 */
class ScoresLoadedListener implements ResultCallback<Leaderboards.LoadScoresResult> {

    private int currentBufferSize = 0;

    public void ScoresLoadedListener() {}

    public void onResult( Leaderboards.LoadScoresResult scoresResult ) {


        LeaderboardScoreBuffer scores = scoresResult.getScores();

        if( scores.getCount() == currentBufferSize ) {
            Extension.context.onLeaderboardLoaded(scores);
        }
        else {
            currentBufferSize = scores.getCount();
            Games.Leaderboards.loadMoreScores( Extension.context.getApiClient(), scores, 25, PageDirection.NEXT ).setResultCallback( this );
        }

    }

}