package nz.cchang.myandroidtuorial;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QCardActivity extends Activity {
	
	private TextView header;
	private TextView card_content;
	private Button answer_button;
	private Button next_question_button;
	private Button finish_button;
	
	private ArrayList<Item> itemList;
	private int currentIndex = -1;
	private boolean firstQuestion = true;
	private int currentQuestion = 0;
	private int totalQuestionCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qcard);
		processViews();
	}

	private void processViews() {
		
		header = (TextView) findViewById(R.id.header);
		card_content = (TextView) findViewById(R.id.card_content);
		answer_button = (Button)findViewById(R.id.answer_button);
		next_question_button = (Button)findViewById(R.id.next_question_button);
		finish_button = (Button)findViewById(R.id.finish_button);
		
		try{
		    // Get the Bundle Object       
		    Bundle bundleObject = getIntent().getExtras();
		    
		    if (bundleObject != null) {
		    	// Get ArrayList Bundle
		    	itemList = (ArrayList<Item>) bundleObject.getSerializable("key");
		    	
		    	if (itemList != null && itemList.size() > 0) {
		    		currentIndex = 0;
		    		
		    		Item item = itemList.get(currentIndex); 
		    		card_content.setText("Where did you meet " + item.getPeople() + "?");
		    		
		    		totalQuestionCount = itemList.size() * 2;
		    		currentQuestion++;
		    		header.setText("Question " + currentQuestion++ + " out of " + totalQuestionCount);
		    	}
		    }
		} catch(Exception e){
		    e.printStackTrace();
		}
	}
	
	public void displayAnswer(View view) {
		Item item = itemList.get(currentIndex);
		
		if (firstQuestion) {
			card_content.setText(item.getLocation());
			firstQuestion = false;
		} else {
			card_content.setText(item.getActivity());
			firstQuestion = true;
			currentIndex++;
		}
		
		answer_button.setVisibility(View.GONE); 
		next_question_button.setVisibility(View.VISIBLE); 
	}
	
	public void displayNextQuestion(View view) {
		if (currentIndex == itemList.size()) {
			card_content.setText("Finish!");
			
			header.setVisibility(View.GONE); 
			next_question_button.setVisibility(View.GONE); 
			finish_button.setVisibility(View.VISIBLE); 
			return;
		}
		
		Item item = itemList.get(currentIndex); 
		
		if (firstQuestion) {
			card_content.setText("Where did you meet " + item.getPeople() + "?");
		} else {
			card_content.setText("what did you do at " + item.getLocation() + "?");
		}
		
		header.setText("Question " + currentQuestion++ + " out of " + totalQuestionCount);
		
		next_question_button.setVisibility(View.GONE); 
		answer_button.setVisibility(View.VISIBLE); 
	}
	
	public void backToMain(View view) {
		finish();
	}
}
