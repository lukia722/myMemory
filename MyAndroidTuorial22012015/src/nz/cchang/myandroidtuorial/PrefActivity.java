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
		// 指定使用的設定畫面配置資料
		addPreferencesFromResource(R.xml.mypreference);
		defaultColor = (Preference)findPreference("DEFAULT_COLOR");
		// 建立SharedPreference物件
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		// 讀取設定的預設顏色
		int color = sharedPreferences.getInt("DEFAULT_COLOR", -1);
		
		if (color != -1) {
			// 設定眼色說明
			defaultColor.setSummary(getString(R.string.default_color_summary) + ": " + ItemActivity.getColors(color));
		}
	}
}
