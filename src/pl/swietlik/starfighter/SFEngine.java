package pl.swietlik.starfighter;

import android.view.View;

public class SFEngine {
// static values using in game
	public static final int GAME_THREAD_DELAY = 4000;
	public static final int MENU_BUTTON_ALPHA = 0;
	public static final boolean HAPTIC_BUTTON_FEEDBACK = true;
	
	//closing game threads and exit game
	public boolean onExit(View v) {
		try {
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
}
