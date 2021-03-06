package nz.cchang.myandroidtuorial;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ListView item_list;
	private TextView show_app_name;


	private ItemAdapter itemAdapter;

	private List<Item> items;


	private MenuItem add_item, search_item, revert_item, share_item,
			delete_item;


	private int selectedCount = 0;
	
	private ItemDAO itemDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		processViews();
		processControllers();

		itemDAO = new ItemDAO(getApplicationContext());
		
		if (itemDAO.getCount() == 0) {
			itemDAO.sample();
		}

		items =itemDAO.getAll();

		itemAdapter = new ItemAdapter(this, R.layout.single_item, items);
		item_list.setAdapter(itemAdapter);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {

			Item item = (Item) data.getExtras().getSerializable(
					"nz.cchang.myandroidtuorial.Item");

			if (requestCode == 0) {

				item = itemDAO.insert(item);

				items.add(item);

				itemAdapter.notifyDataSetChanged();
			}

			else if (requestCode == 1) {

				int poistion = data.getIntExtra("poistion", -1);

				if (poistion != -1) {

					itemDAO.update(item);
					
					items.set(poistion, item);
					itemAdapter.notifyDataSetChanged();
				}
			}
		}
	}

	private void processViews() {
		item_list = (ListView) findViewById(R.id.item_list);
		show_app_name = (TextView) findViewById(R.id.show_app_name);
	}

	private void processControllers() {

		OnItemClickListener itemListener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Item item = itemAdapter.getItem(position);


				if (selectedCount > 0) {

					processMenu(item);

					itemAdapter.set(position, item);
				} else {

					Intent intent = new Intent(
							"nz.cchang.myandroidtuorial.EDIT_ITEM");


					intent.putExtra("poistion", position);
					intent.putExtra("nz.cchang.myandroidtuorial.Item", item);


					startActivityForResult(intent, 1);
				}
			}
		};

		item_list.setOnItemClickListener(itemListener);


		OnItemLongClickListener itemLongListener = new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				Item item = itemAdapter.getItem(position);

				processMenu(item);

				itemAdapter.set(position, item);
				return true;
			}
		};
		item_list.setOnItemLongClickListener(itemLongListener);
		


		OnLongClickListener listener = new OnLongClickListener() {

			@Override
			public boolean onLongClick(View view) {
				// TODO Auto-generated method stub
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						MainActivity.this);
				dialog.setTitle(R.string.app_name).setMessage(R.string.about)
						.show();
				return false;
			}
		};


		show_app_name.setOnLongClickListener(listener);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main_menu, menu);


		add_item = menu.findItem(R.id.add_item);
		search_item = menu.findItem(R.id.search_item);
		revert_item = menu.findItem(R.id.revert_item);
		share_item = menu.findItem(R.id.share_item);
		delete_item = menu.findItem(R.id.delete_item);


		processMenu(null);

		return true;
	}
	

	public void clickMenuItem(MenuItem item) {

		int itemId = item.getItemId();


		switch (itemId) {
		case R.id.search_item:
			break;


		case R.id.add_item:

			Intent intent = new Intent("nz.cchang.myandroidtuorial.ADD_ITEM");

			startActivityForResult(intent, 0);
			break;


		case R.id.revert_item:
			for (int i = 0; i < itemAdapter.getCount(); i++) {
				Item ri = itemAdapter.getItem(i);

				if (ri.isSelected()) {
					ri.setSelected(false);
					itemAdapter.set(i, ri);
				}
			}

			selectedCount = 0;
			processMenu(null);
			break;


		case R.id.delete_item:

			if (selectedCount == 0) {
				break;
			}


			AlertDialog.Builder d = new AlertDialog.Builder(this);
			String message = getString(R.string.delete_item);
			d.setTitle(R.string.delete).setMessage(
					String.format(message, selectedCount));
			d.setPositiveButton(android.R.string.yes,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							

							int index = itemAdapter.getCount() - 1;

							while (index > -1) {
								Item item = itemAdapter.get(index);

								if (item.isSelected()) {
									itemAdapter.remove(item);

									itemDAO.delete(item.getId());
								}

								index--;
							}


							itemAdapter.notifyDataSetChanged();
						}
					});

			d.setNegativeButton(android.R.string.no, null);
			d.show();
			break;

		case R.id.flashcard:
			
			if (selectedCount == 0) {
				break; 
			}
			
			int index = itemAdapter.getCount() -1;
			ArrayList<Item> selectList = new ArrayList<Item>();
			
			while (index > -1) {
				Item item2 = itemAdapter.get(index);
				
				if (item2.isSelected()) {
					selectList.add(item2);
				}
				
				index--;
			}
			
			Intent intentCard = new Intent(this, QCardActivity.class);
			
			// Create a Bundle and Put Bundle in to it
			Bundle bundleObject = new Bundle();
			bundleObject.putSerializable("key", selectList);
			                 
			// Put Bundle in to Intent and call start Activity
			intentCard.putExtras(bundleObject);
			startActivity(intentCard);
			
			break;

		case R.id.scrollingtext:
			break;
			
		case R.id.postit:
			break;
		}
	}
	

	private void processMenu(Item item) {

		if (item != null) {

			item.setSelected(!item.isSelected());


			if (item.isSelected()) {
				selectedCount++;
			} else {
				selectedCount--;
			}

		}

		add_item.setVisible(selectedCount == 0);
		search_item.setVisible(selectedCount == 0);
		revert_item.setVisible(selectedCount > 0);
		share_item.setVisible(selectedCount > 0);
		delete_item.setVisible(selectedCount > 0);
	}
	
	public void clickPreferences(MenuItem item) {

		startActivity(new Intent(this, PrefActivity.class));
	}


	public void aboutApp(View view) {
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}
}
