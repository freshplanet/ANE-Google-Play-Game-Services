package com.freshplanet.googleplaygames.functions;

import android.content.Intent;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;
import com.freshplanet.googleplaygames.Extension;
import com.freshplanet.googleplaygames.SignInActivity;

public class AirGooglePlayStartAtLaunch implements FREFunction {

	@Override
	public FREObject call(FREContext arg0, FREObject[] arg1) {
		boolean autoSignIn = false;
		
		try {
			arg1[0].getAsBool();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FRETypeMismatchException e) {
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		}
		
		Extension.context.AUTOSIGNIN = autoSignIn;
		
		
		if (autoSignIn)
		{
			Intent intent = new Intent(arg0.getActivity().getApplicationContext(), SignInActivity.class);
			arg0.getActivity().startActivity(intent);
		}
		
		return null;
	}

}
