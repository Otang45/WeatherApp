package otang.weather.model.forecast.city;

import com.google.gson.annotations.SerializedName;

public class Coord {

	@SerializedName("lat")
	private double lat;

	@SerializedName("lon")
	private double lon;

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLat() {
		return lat;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLon() {
		return lon;
	}

}
