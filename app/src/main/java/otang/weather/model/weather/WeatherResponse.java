package otang.weather.model.weather;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class WeatherResponse {

	@SerializedName("coord")
	private Coord coord;

	@SerializedName("weather")
	private List<Weather> weather;

	@SerializedName("base")
	private String base;

	@SerializedName("main")
	private Main main;

	@SerializedName("visibility")
	private int visibility;

	@SerializedName("wind")
	private Wind wind;

	@SerializedName("clouds")
	private Clouds clouds;

	@SerializedName("dt")
	private Long dt;

	@SerializedName("sys")
	private Sys sys;

	@SerializedName("timezone")
	private int timezone;

	@SerializedName("id")
	private int id;

	@SerializedName("name")
	private String name;

	@SerializedName("cod")
	private int cod;

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public Coord getCoord() {
		return coord;
	}

	public void setWeather(List<Weather> weather) {
		this.weather = weather;
	}

	public List<Weather> getWeather() {
		return weather;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getBase() {
		return base;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public Main getMain() {
		return main;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public Wind getWind() {
		return wind;
	}

	public void setClouds(Clouds clouds) {
		this.clouds = clouds;
	}

	public Clouds getClouds() {
		return clouds;
	}

	public void setDt(Long dt) {
		this.dt = dt;
	}

	public Long getDt() {
		return dt;
	}

	public void setSys(Sys sys) {
		this.sys = sys;
	}

	public Sys getSys() {
		return sys;
	}

	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}

	public int getTimezone() {
		return timezone;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public int getCod() {
		return cod;
	}

}
