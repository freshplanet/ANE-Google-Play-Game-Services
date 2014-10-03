package com.freshplanet.googleplaygames;

import java.util.Iterator;

import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.achievement.Achievement;
import com.google.android.gms.games.achievement.AchievementBuffer;
import com.google.android.gms.games.achievement.OnAchievementsLoadedListener;

public class AchievementsLoadListener implements OnAchievementsLoadedListener{
	
	private String achievementId;
	private int percent;
	
	public AchievementsLoadListener(String achievementId,int percent){
		this.achievementId = achievementId;
		this.percent = percent;
	}
	
	public void incrementAchievementWhenDataIsLoaded(Achievement achievement){
		if(achievement != null){
			int currentStep = achievement.getCurrentSteps();
			percent -= currentStep;
		}
		if(percent <= 0)
			return;
		
//		Extension.context.getGamesClient().incrementAchievement(achievementId,percent);
	}
	
	@Override
	public void onAchievementsLoaded(int statusCode, AchievementBuffer achBuffer) {
		if (statusCode == GamesClient.STATUS_OK) {
			Iterator<Achievement> iterator = achBuffer.iterator();
			while(iterator.hasNext()){
				Achievement achievement = iterator.next();
				if(achievement.getType() == Achievement.TYPE_INCREMENTAL && achievement.getAchievementId().equals(achievementId))
				{
				    this.incrementAchievementWhenDataIsLoaded(achievement);
				}
			}
			
		}
		else{
			//Achievements loading has failed
		}
		achBuffer.close();
	}
	
}
