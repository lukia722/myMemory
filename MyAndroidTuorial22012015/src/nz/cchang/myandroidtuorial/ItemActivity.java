package nz.cchang.myandroidtuorial;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ItemActivity extends Activity {

	private EditText title_text, content_text;
	private EditText it_date_text, location_text, people_text;
	private Spinner time_spinner, activity_spinner;
	private ArrayAdapter<String> adapTime, adapActivity;

	
	// The code for onActivityResult()
	private static final int START_CAMERA = 0;
	private static final int START_RECORD = 1;
	private static final int START_LOCATION = 2;
	private static final int START_ALARM = 3;
	private static final int START_COLOR = 4;

	// Item object
	private Item item;
	
	// fileName for Item object
	private String fileName;	
	
	// Picture
	private ImageView picture;
	
	// String for saving value for Spinner and DatePicker
	private String strDate, strTime, strActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item);

		processViews();
		
		// Get Intent object
		Intent intent = getIntent();
		// Setting the action
		String action = intent.getAction();

		// If the action is EDIT item when the item existed already 
		if (action.equals("nz.cchang.myandroidtuorial.EDIT_ITEM")) {
			// Receiving the selected item for the content
			item = (Item) intent.getExtras().getSerializable(
					"nz.cchang.myandroidtuorial.Item");

			title_text.setText(item.getTitle());
			content_text.setText(item.getContent());
			it_date_text.setText(item.getIt_date());
			location_text.setText(item.getLocation());
			people_text.setText(item.getPeople());
			
//			if (item.getIt_date() != null){
//				datePicDlgOnDateSelLis.equals(item.getIt_date());
//			}
			
			// Getting value back from the DB 
			if (item.getTime() != null) {
				int timeSpinnerPostion = adapTime.getPosition(item.getTime());
				time_spinner.setSelection(timeSpinnerPostion);
				timeSpinnerPostion = 0;				
			}
			
			if (item.getActivity() != null) {
				int activitySpinnerPostion = adapActivity.getPosition(item.getActivity());
				activity_spinner.setSelection(activitySpinnerPostion);
		        activitySpinnerPostion = 0;				
			}
		}
		
		// ADD new item
		else {
			item = new Item();
			
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
			int color = sharedPreferences.getInt("DEFAULT_COLOR", -1);
			item.setColor(getColors(color));
		}
	}
	
	// Using the DatePicker for setting the date
	public void selectDate(View view) {
		Calendar eDate = Calendar.getInstance();
		DatePickerDialog datePickerDialog = new DatePickerDialog (ItemActivity.this, datePicDlgOnDateSelLis, 
				eDate.get(Calendar.YEAR), eDate.get(Calendar.MONTH), eDate.get(Calendar.DATE));
		datePickerDialog.setTitle("Select date:");
		datePickerDialog.setIcon(android.R.drawable.ic_dialog_info);
		datePickerDialog.show();
	}
	
	public DatePickerDialog.OnDateSetListener datePicDlgOnDateSelLis =
			new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					// TODO Auto-generated method stub
					strDate = Integer.toString(dayOfMonth) + "/"
							+ Integer.toString(monthOfYear + 1) + "/"
							+ Integer.toString(year);
					it_date_text.setText(strDate);
				}
			};
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		// If the fileName is exist
		if (item.getFileName() != null && item.getFileName().length() > 0) {
			// Getting the picture
			File file = configFileName("p", ".jpg");
			
			// If the picture is exists
			if (file.exists()) {
				
				// Presenting the picture
				picture.setVisibility(View.VISIBLE);
				// Setting the picture
				FileUtil.fileToImageView(file.getAbsolutePath(), picture);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			// Taking the picture
			case START_CAMERA:
				item.setFileName(fileName);
				break;
				
			//  Setting the recored fileName
			case START_RECORD:
				item.setFileName(fileName);
				break;
				
			case START_LOCATION:
				break;
				
			case START_ALARM:
				break;
			
			// Setting the color
			case START_COLOR:
				int colorId = data.getIntExtra("colorId", Colors.LIGHTGREY.parseColor());
				item.setColor(getColors(colorId));
				break;
			}
		}
	}
	
	public static Colors getColors(int color) {
		Colors result = Colors.LIGHTGREY;

		if (color == Colors.BLUE.parseColor()) {
			result = Colors.BLUE;
		} else if (color == Colors.PURPLE.parseColor()) {
			result = Colors.PURPLE;
		} else if (color == Colors.GREEN.parseColor()) {
			result = Colors.GREEN;
		} else if (color == Colors.ORANGE.parseColor()) {
			result = Colors.ORANGE;
		} else if (color == Colors.RED.parseColor()) {
			result = Colors.RED;
		}

		return result;

	}
	
	private void processViews() {
		title_text = (EditText) findViewById(R.id.title_text);
		content_text = (EditText) findViewById(R.id.content_text);
		picture = (ImageView)findViewById(R.id.picture);
		it_date_text = (EditText)findViewById(R.id.it_date_text);
		location_text = (EditText)findViewById(R.id.location_text);
		people_text = (EditText)findViewById(R.id.people_text);
		
		time_spinner = (Spinner)findViewById(R.id.time_spinner);
		adapTime = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.times));
		adapTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		time_spinner.setOnItemSelectedListener(time_spinnerItemSelLis);
		
		activity_spinner = (Spinner)findViewById(R.id.activity_spinner);
		adapActivity = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.activities));
		adapActivity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		activity_spinner.setOnItemSelectedListener(activity_spinnerItemSelLis);		
	}	

	
	public Spinner.OnItemSelectedListener time_spinnerItemSelLis = 
			new Spinner.OnItemSelectedListener (){
	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					strTime = parent.getSelectedItem().toString();
				}
	
				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}	
				
			};
		
	public Spinner.OnItemSelectedListener activity_spinnerItemSelLis = 
			new Spinner.OnItemSelectedListener(){

				@Override
				public void onItemSelected(AdapterView<?> parent,
						View view, int position, long id) {
					// TODO Auto-generated method stub
					strActivity = parent.getSelectedItem().toString();
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
		
			};

	public void onSubmit(View view) {
		// Clicking OK button
		if (view.getId() == R.id.ok_item) {
			// Getting the value from inputing 
			String titleText = title_text.getText().toString();
			String contentText = content_text.getText().toString();
			String locationText=location_text.getText().toString();
			String peopleText = people_text.getText().toString();
			
			// Passing the value to item
			item.setTitle(titleText);
			item.setContent(contentText);
			item.setIt_date(strDate);
			item.setTime(strTime);
			item.setLocation(locationText);
			item.setPeople(peopleText);
			item.setActivity(strActivity);
			
			// If the action is EDIT item when the item is NEW 
			if (getIntent().getAction().equals(
					"nz.cchang.myandroidtuorial.EDIT_ITEM")) {
				item.setLastModify(new Date().getTime());
				Toast.makeText(getApplicationContext(), "Memory sucessfully edited.", Toast.LENGTH_SHORT).show();
			}
			// ADD item
			else {
				item.setDatetime(new Date().getTime());
				Toast.makeText(getApplicationContext(), "Memory sucessfully saved.", Toast.LENGTH_SHORT).show();
			}

			Intent result = getIntent();
			// Setting the return item object
			result.putExtra("nz.cchang.myandroidtuorial.Item", item);
			setResult(Activity.RESULT_OK, result);		
		}

		finish();
	}


	public void clickFunction(View view) {
		int id = view.getId();

		switch (id) {
		case R.id.take_picture:
			// Starting the Camera intent
			Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			
			// Getting the fileName for Camera
			File pictureFile = configFileName("p", ".jpg");
			Uri uri = Uri.fromFile(pictureFile);
			
			intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
			
			startActivityForResult(intentCamera, START_CAMERA);
			break;

		case R.id.record_sound:

			final File recordFile = configFileName("R", ".mp3");
			
			if (recordFile.exists()) {

				AlertDialog.Builder d = new AlertDialog.Builder(this);
				
				d.setTitle(R.string.title_record)
				.setCancelable(true);
				d.setPositiveButton(R.string.record_play, new DialogInterface.OnClickListener() {
					
//					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// Playing
						Intent playIntent = new Intent(ItemActivity.this, PlayActivity.class);
						playIntent.putExtra("fileName", recordFile.getAbsolutePath());
						startActivity(playIntent);
						
					}
				});
				d.setNegativeButton(R.string.record_new, new DialogInterface.OnClickListener() {
					
//					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						goToRecord(recordFile);
					}
				});
				

				d.show();
			} else {
				goToRecord(recordFile);
			}
			break;

		case R.id.set_sms:
			String smsContent1 = "[myMemory]" + item.getIt_date();
			String smsContent2 = item.getTitle() + " : " + item.getContent();
			String smsContent3 = item.getActivity() + " with " + item.getPeople() + " at " + item.getLocation();
			Intent intentSms = new Intent(Intent.ACTION_VIEW);
			intentSms.setData(Uri.parse("smsto:"));
			intentSms.setType("vnd.android-dir/mms-sms");
			intentSms.putExtra("sms_body", smsContent1 + " \n" + smsContent2 + "\n" + smsContent3);
			
			startActivity(intentSms);
			break;

		case R.id.set_email:
			String mailContent1 = item.getIt_date();
			String mailContent2 = "It was " + item.getTitle() + " and " + item.getContent() + ".";
			String mailContent3 = "I was " + item.getActivity() + " with " + item.getPeople() + " at " + item.getLocation() + ".";
			Intent intentEmail = new Intent(Intent.ACTION_SENDTO);
			intentEmail.setData(Uri.parse("mailto:"));
			intentEmail.putExtra(Intent.EXTRA_SUBJECT, "[MyMemory]"+ item.getIt_date());
			intentEmail.putExtra(Intent.EXTRA_TEXT, mailContent1 + "\n" +mailContent2 + "\n" +mailContent3 );
			
			startActivity(intentEmail);
			break;

		case R.id.select_color:

			startActivityForResult(new Intent(this, ColorActivity.class), START_COLOR);
			break;
		}
	}
	
	private void goToRecord(File recordFile) {

		Intent recordIntent = new Intent(this, RecordActivity.class);
		recordIntent.putExtra("fileName", recordFile.getAbsolutePath());
		startActivityForResult(recordIntent, START_RECORD);
	}

	private File configFileName(String prefix, String extension) {
		// TODO Auto-generated method stub

		if (item.getFileName() != null && item.getFileName().length() > 0) {
			fileName = item.getFileName();
		} 

		else {
			fileName = FileUtil.getUniqueFileName();
		}
		return new File(FileUtil.getExternalStorageDir(FileUtil.APP_DIR), prefix + fileName + extension);
	}
}
