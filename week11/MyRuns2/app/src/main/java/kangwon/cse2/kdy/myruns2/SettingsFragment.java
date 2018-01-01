package kangwon.cse2.kdy.myruns2;


import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat
{
    public void onCreatePreferences(Bundle paramBundle, String paramString)
    {
        addPreferencesFromResource(R.xml.settings_preferences);
    }

}


