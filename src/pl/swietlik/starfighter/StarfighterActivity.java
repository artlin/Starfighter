package pl.swietlik.starfighter;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class StarfighterActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
    }
}
