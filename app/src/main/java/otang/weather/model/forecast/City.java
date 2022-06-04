package otang.weather.model.forecast;

import com.google.gson.annotations.SerializedName;
import otang.weather.model.forecast.city.Coord;

public class City {

	@SerializedName("id")
	private long id;

	@SerializedName("name")
	private String name;

	@SerializedName("coord")
	private Coord coord;

	@SerializedName("country")
	private String country;

	@SerializedName("population")
	private Long population;

	@SerializedName("timezone")
	private int timezone;

	@SerializedName("sunrise")
	private Long sunrise;

	@SerializedName("sunset")
	private Long sunset;

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public Coord getCoord() {
		return coord;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return country;
	}

	public void setPopulation(Long population) {
		this.population = population;
	}

	public Long getPopulation() {
		return population;
	}

	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}

	public int getTimezone() {
		return timezone;
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
