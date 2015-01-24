package nz.cchang.myandroidtuorial;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

public class PlayActivity extends Activity {
	
	
	private SeekBar control;
	private MediaPlayer mediaPlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		processViews();
		processControllers();
		
		Intent intent = getIntent();
		String fileName = intent.getStringExtra("fileName");
		
		// �إ߫��w�귽��MediaPlayer����
		Uri uri = Uri.parse(fileName);
		mediaPlayer = MediaPlayer.create(this, uri);
		// ���U���񧹲���ť�ƥ�
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer player) {
				// TODO Auto-generated method stub
				// �������s���i����
				clickStop(null);
				
			}
		});
		
		// �]�wSeekBar���󪺳̤j�Ȭ����֪��`�ɶ��]�@��^
		control.setMax(mediaPlayer.getDuration());
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mediaPlayer.stop();
		super.onPause();
	}
	
	public void onSubmit(View view) {
		if (view.getId() == R.id.ok_add_item) {
			
		}
		finish();
	}


	private void processViews() {
		// TODO Auto-generated method stub
		control = (SeekBar)findViewById(R.id.control);
	}
	
	private void processControllers() {
		// TODO Auto-generated method stub
		// ���U SeekBar����i�ק��ܨƥ�
		control.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				// �@�w�n�P�_�O�H�γo���ާ@�A�]������L�{�]�|���i��
				if (fromUser) {
					// ���ʭ��֨���w���i��
					mediaPlayer.seekTo(progress);
				}
			}
		});		
	}
	
	public void clickPlay(View view) {
		// �}�l����
		mediaPlayer.start();
		// �إ߰�����ܼ���i�ת�AsyncTask����
		new MyPlayTask().execute();
	}
	
	public void clickPause(View view) {
		// �Ȱ�����
		mediaPlayer.pause();
	}
	
	public void clickStop(View view) {
		// �����
		mediaPlayer.stop();
		
		try {
			// ���s�]�w
			mediaPlayer.prepare();
		} catch (IOException e) {
			// TODO: handle exception
			Log.d("PlayActivity", e.toString());
		}
		
		// �^��}�l����m
		mediaPlayer.seekTo(0);
		control.setProgress(0);
	}
	
	// ���񧹦���ť���O
	private class MyCompletion implements OnCompletionListener {

		@Override
		public void onCompletion(MediaPlayer player) {
			// TODO Auto-generated method stub
			// �M��MediaPlayer����
			player.release();
		}
	}
	
	// ����L�{����ܼ���i��
	private class MyPlayTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... args) {
			// TODO Auto-generated method stub
			while (mediaPlayer.isPlaying()) {
				// �]�w����i��
				control.setProgress(mediaPlayer.getCurrentPosition());
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO: handle exception
					Log.d("PlayActivity", e.toString());
				}
			}
			return null;
		}
		
	}
}
