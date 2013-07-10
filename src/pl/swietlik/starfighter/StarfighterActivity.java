package pl.swietlik.starfighter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;

public class StarfighterActivity extends Activity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);

		// start new game thread
		new Handler().postDelayed(new Thread() {
			@Override
			public void run() {
				Intent mainMenu = new Intent(StarfighterActivity.this,
					 SFMainMenu.class);
				StarfighterActivity.this.startActivity(mainMenu);
				StarfighterActivity.this.finish();
				overridePendingTransition(R.layout.fadein,R.layout.fadeout);
			}
		}, SFEngine.GAME_THREAD_DELAY);
	}

}
