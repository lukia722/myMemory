package nz.cchang.myandroidtuorial;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// �����������ε{�����D
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_about);
	}
	
	// �������s
	public void clickOk(View view) {
		// �I�s�o�Ӥ�k����Activity����
		finish();
	}
}
