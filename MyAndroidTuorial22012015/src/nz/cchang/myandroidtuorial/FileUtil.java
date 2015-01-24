package nz.cchang.myandroidtuorial;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

public class FileUtil {
	
	
	// ���ε{���x�s�ɮת��ؿ�
	public static final String APP_DIR = "androidtutorial";
	
	
	// �~���x�s�]�ƬO�_�i�H�g�J
	public static boolean isExternalStorageWritable() {
		// ���o�ثe�~���x�s�]�ƪ����A
		String state = Environment.getExternalStorageState();
		
		
		// �P�_�O�_�iŪ�h
		if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		
		return false;
	}
	
	
	// �إߨöǦ^�b���ά�ï�U�Ѽƫ��w���u
	public static File getPublicAlbumStorageFir(String albumName) {
		// ���o�\�Ϊ��Ӥ����|
		File pictures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		
		// �ǳƦb�Ӥ����|�U�إߤ@�ӫ��w���|
		File file = new File(pictures, albumName);
		
		// �p�G�إ߸��|�����\
		if (!file.mkdirs()) {
			Log.e("getAlbumStorageDir", "Directory not created");
		}
		
		return file;
	}
	
	// �إߨöǦ^�~���x�s�C��Ѽƫ��w�����|
	public static File getExternalStorageDir(String dir){
		File result = new File(Environment.getExternalStorageDirectory(), dir);
		
		if (!isExternalStorageWritable()) {
			return null;
		}
		
		if (!result.exists() && !result.mkdirs()) {
			return null;
		}
		
		return result;
	}
	
	
	// Ū�����w�Ӥ��ɮצW�ٳo�w��ImageView����
	public static void fileToImageView(String fileName, ImageView imageView){
		if (new File(fileName).exists()) {
			Bitmap bitmap = BitmapFactory.decodeFile(fileName);
			imageView.setImageBitmap(bitmap);
		} else {
			Log.e("fileToImageView", fileName + " not found. ");
		}
	}
	
	
	// ���Ͱߤ@���ɮצW��
	public static String getUniqueFileName() {
		// �ϥΦ~���Ȯɤ���榡���ɮצW��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		return sdf.format(new Date());
	}
}
