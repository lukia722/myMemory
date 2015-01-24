package nz.cchang.myandroidtuorial;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

public class Item implements Serializable {


	private long id;
	private long datetime;
	private Colors color;
	private String title;
	private String content;
	private String fileName;
	private double latitude;
	private double longitude;
	private long lastModify;
	private boolean selected;	
	private String it_date;	
	private String time;	
	private String location;
	private String people;
	private String activity;

	public Item() {

		title = "";
		content = "";
		color = Colors.LIGHTGREY;
		it_date= "";	
		time= "";		
		location= "";
		people= "";
		activity= "";
	}

	public Item(long id, long datetime, String it_date, String time, Colors color, String title, 
			String location, String people, String activity, String content,
			String fileName, double latitude, double longitude, long lastModify
			  ) {
		this.id = id;
		this.datetime = datetime;
		this.it_date = it_date;
		this.time = time;
		this.color = color; 
		this.title = title;
		this.content = content;
		this.fileName = fileName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.lastModify = lastModify;		
		this.location = location;
		this.people = people;
		this.activity = activity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDatetime() {
		return datetime;
	}
	
	public void setDatetime(long datetime) {
		this.datetime = datetime;
	}


	public String getLocalDatetime() {
		return String.format(Locale.getDefault(), "%tF  %<tR", new Date(
				datetime));
	}


	public String getLocalDate() {
		return String.format(Locale.getDefault(), "%tF", new Date(datetime));
	}


	public String getLocalTime() {
		return String.format(Locale.getDefault(), "%tR", new Date(datetime));
	}
	
	public String getIt_date() {
		return it_date;
	}

	public void setIt_date(String it_date) {
		this.it_date = it_date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Colors getColor() {
		return color;
	}

	public void setColor(Colors color) {
		this.color = color;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public long getLastModify() {
		return lastModify;
	}

	public void setLastModify(long lastModify) {
		this.lastModify = lastModify;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPeople() {
		return people;
	}

	public void setPeople(String people) {
		this.people = people;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

}
