package otang.weather.model.weather;

import com.google.gson.annotations.SerializedName;

public class Sys {

	@SerializedName("type")
	private int type;

	@SerializedName("id")
	private int id;

	@SerializedName("country")
	private String country;

	@SerializedName("sunrise")
	private Long sunrise;

	@SerializedName("sunset")
	private Long sunset;

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return country;
	}

	public void setSunrise(Long sunrise) {
		this.sunrise = sunrise;
	}

	public Long getSunrise() {
		return sunrise;
	}

	public void setSunset(Long sunset) {
		this.sunset = sunset;
	}

	public Long getSunset() {
		return sunset;
	}

}
