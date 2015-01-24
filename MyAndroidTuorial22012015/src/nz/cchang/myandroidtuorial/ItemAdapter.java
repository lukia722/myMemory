package nz.cchang.myandroidtuorial;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ItemAdapter extends ArrayAdapter<Item> {

	// �e���귽�s��
	private int resource;
	// �]�˪��O�Ƹ��
	private List<Item> items;

	public ItemAdapter(Context context, int resource, List<Item> items) {
		super(context, resource, items);
		this.resource = resource;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LinearLayout itemView;
		// Ū���ثe��m���O�ƪ���
		final Item item = getItem(position);

		if (convertView == null) {
			// �إ߶��صe������
			itemView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater li = (LayoutInflater) getContext().getSystemService(
					inflater);
			li.inflate(resource, itemView, true);
		} else {
			itemView = (LinearLayout) convertView;
		}

		// Ū���O���C��B�H��ܡB���D�P�������
		RelativeLayout typeColor = (RelativeLayout) itemView.findViewById(R.id.type_color);
		ImageView selectedItem = (ImageView) itemView.findViewById(R.id.selected_item);
		TextView titleView = (TextView) itemView.findViewById(R.id.title_text);
		TextView dateView = (TextView) itemView.findViewById(R.id.date_text);
		TextView pplView = (TextView) itemView.findViewById(R.id.ppl_loc_text);

		// �]�w�O���C��
		GradientDrawable background = (GradientDrawable) typeColor
				.getBackground();
		background.setColor(item.getColor().parseColor());

		// �]�w���D�P����ɶ�
		titleView.setText(item.getTitle());
//		dateView.setText(item.getLocalDatetime());
		dateView.setText(item.getIt_date());
		pplView.setText(" : " +  item.getLocation() + " - " + item.getPeople());

		// �]�w�O�_�w�g���
		selectedItem.setVisibility(item.isSelected() ? View.VISIBLE
				: View.INVISIBLE);

		return itemView;
	}

	// �]�w���w�s�����O�Ƹ��
	public void set(int index, Item item) {
		if (index >= 0 && index < items.size()) {
			items.set(index, item);
			notifyDataSetChanged();
		}
	}

	// Ū�����w�s�����O�Ƹ��
	public Item get(int index) {
		return items.get(index);
	}

}
