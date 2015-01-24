package nz.cchang.myandroidtuorial;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class RecordActivity extends Activity {
	
	private ImageButton record_button;
	private boolean isRecording = false;
	private ProgressBar record_volumn;
	
	private MyRecorder myRecorder;
	
	private String fileName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);
		
		processViews();
		
		// Ū���ɮצW��
		Intent intent = getIntent();
		fileName = intent.getStringExtra("fileName");
	}
	
	private void processViews() {
		// TODO Auto-generated method stub
		record_button = (ImageButton)findViewById(R.id.record_button);
		record_volumn = (ProgressBar)findViewById(R.id.record_volumn);
		// ���ê��A�CProgressBar
		setProgressBarIndeterminateVisibility(false);
	}

	public void onSubmit(View view) {
		if (isRecording) {
			// �������
			myRecorder.stop();
		}
		
		// �T�w
		if (view.getId() == R.id.record_ok) {
			Intent result = getIntent();
			setResult(Activity.RESULT_OK, result);
		}
		
		finish();
	}
	
	public void clickRecord(View view) {
		// ����
		isRecording = !isRecording;
		
		// �}�l����
		if (isRecording) {
			// �]�w���s�ϥܬ�������
			record_button.setImageResource(R.drawable.record_red_icon);
			// �إ߿�������
			myRecorder = new MyRecorder(fileName);
			// �}�l����
			myRecorder.start();
			// �إߨð�����J�����q��AsyncTask����
			new MicLevelTask().execute();
		}
		
		// �������
		else {
			// �]�w���s�ϥܬ��������
			record_button.setImageResource(R.drawable.record_dark_icon);
			// ���J�����q�k�s
			record_volumn.setProgress(0);
			// �������
			myRecorder.stop();
		}
	}
	
	// �b�����L�{����ܳ��i�����q
	private class MicLevelTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... args) {
			// TODO Auto-generated method stub
			while (isRecording) {
				publishProgress();
				
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO: handle exception
					Log.d("RecordActivity", e.toString());
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
//			super.onProgressUpdate(values);
			record_volumn.setProgress((int)myRecorder.getAmplitudeEMA());
		}
	}
	
	// ��������åB�i�H���o���J�����q����������
	private class MyRecorder {
		
		private static final double EMA_FILTER = 0.6;
		private MediaRecorder recorder = null;
		private double mEMA = 0.0;
		private String output;
		
		// �إ߿�������A�ѼƬ������x�s����m�P�ɮצW
		MyRecorder(String output){
			this.output = output;
		}
		
		// �}�l����
		public void start() {
			if (recorder == null) {
				// �إ߿����Ϊ�MediaRecorder����
				recorder = new MediaRecorder();
				// �]�w�����ӷ������J���A�����bsetOutputFormat��k���e�I�s
				recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
				// �]�w��X�榡��3GP���Y�榡�A�����bsetAudioSource��k����A
				// �bprepare��k���e�I�s
				recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
				// �]�w�������s�X�覡�A�����bsetOutputFormat��k����A
				// �bprepare��k���e�I�s
				recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
				// �]�w��X�ɮצW�١A�����bsetOutputFormat��k����A
				// �bprepare��k���e�I�s
				recorder.setOutputFile(output);
				
				try {
					//�ǳư�������u�@�A�����b�Ҧ��w����I�s
					recorder.prepare();
				} catch (IOException e) {
					// TODO: handle exception
					Log.d("RecordActivity", e.toString());
				}
				
				// �}�l����
				recorder.start();
				mEMA = 0.0;
			}
		}
		
		// �������
		public void stop() {
			if (recorder != null) {
				// �������
				recorder.stop();
				// �M�������귽
				recorder.release();
				recorder = null;
			}
		}
		
		public double getAmplitude() {
			if (recorder != null) {
				return (recorder.getMaxAmplitude() / 2700.0);
			} else {
				return 0;
			}
		}
		
		// ���o���J�����q
		public double getAmplitudeEMA() {
			double amp = getAmplitude();
			mEMA = EMA_FILTER * amp + (1.0 -EMA_FILTER)*mEMA;
			return mEMA;
		}
	}
}
