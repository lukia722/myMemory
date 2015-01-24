package nz.cchang.myandroidtuorial;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class PrefActivity extends PreferenceActivity {
	
	private SharedPreferences sharedPreferences;
	private Preference defaultColor;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// ���w�ϥΪ��]�w�e���t�m���
		addPreferencesFromResource(R.xml.mypreference);
		defaultColor = (Preference)findPreference("DEFAULT_COLOR");
		// �إ�SharedPreference����
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		// Ū���]�w���w�]�C��
		int color = sharedPreferences.getInt("DEFAULT_COLOR", -1);
		
		if (color != -1) {
			// �]�w���⻡��
			defaultColor.setSummary(getString(R.string.default_color_summary) + ": " + ItemActivity.getColors(color));
		}
	}
}
