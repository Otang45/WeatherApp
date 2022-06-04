package otang.weather.model.forecast;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import otang.weather.model.forecast.list.Clouds;
import otang.weather.model.forecast.list.Main;
import otang.weather.model.forecast.list.Sys;
import otang.weather.model.forecast.list.Weather;
import otang.weather.model.forecast.list.Wind;

public class Lists {

	@SerializedName("dt")
	private Long dt;

	@SerializedName("main")
	private Main main;

	@SerializedName("weather")
	private List<Weather> weather;

	@SerializedName("clouds")
	private Clouds clouds;

	@SerializedName("wind")
	private Wind wind;

	@SerializedName("visibility")
	private int visibility;

	@SerializedName("pop")
	private double pop;

	@SerializedName("sys")
	private Sys sys;

	@SerializedName("dt_txt")
	private String dtTxt;

	public void setDt(Long dt) {
		this.dt = dt;
	}

	public Long getDt() {
		return dt;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public Main getMain() {
		return main;
	}

	public void setWeather(List<Weather> weather) {
		this.weather = weather;
	}

	public List<Weather> getWeather() {
		return weather;
	}

	public void setClouds(Clouds clouds) {
		this.clouds = clouds;
	}

	public Clouds getClouds() {
		return clouds;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public Wind getWind() {
		return wind;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setPop(double pop) {
		this.pop = pop;
	}

	public double getPop() {
		return pop;
	}

	public void setSys(Sys sys) {
		this.sys = sys;
	}

	public Sys getSys() {
		return sys;
	}

	public void setDtTxt(String dtTxt) {
		this.dtTxt = dtTxt;
	}

	public String getDtTxt() {
		return dtTxt;
	}

}
