package com.freshplanet.googleplaygames.functions;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.freshplanet.googleplaygames.Extension;

public class AirGooglePlayGamesReportAchievementFunction implements FREFunction {

	public AirGooglePlayGamesReportAchievementFunction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		String achievementId = null;
		double percent = 0;
		try
		{
			achievementId = arg1[0].getAsString();
			percent = arg1[1].getAsDouble();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}

		Extension.context.createHelperIfNeeded(arg0.getActivity());
		if (percent == 0) // it means we have unlocked it.
		{
			Extension.context.reportAchivements(achievementId);
		} else
		{
			Extension.context.reportAchivements(achievementId, percent);
		}
		
		return null;
	}

}
