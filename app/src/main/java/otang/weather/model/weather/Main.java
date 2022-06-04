package otang.weather.model.weather;

import com.google.gson.annotations.SerializedName;

public class Main {

	@SerializedName("temp")
	private Double temp;

	@SerializedName("feels_like")
	private Double feelsLike;

	@SerializedName("temp_min")
	private Double tempMin;

	@SerializedName("temp_max")
	private Double tempMax;

	@SerializedName("pressure")
	private int pressure;

	@SerializedName("humidity")
	private int humidity;

	public void setTemp(Double temp) {
		this.temp = temp;
	}

	public Double getTemp() {
		return temp;
	}

	public void setFeelsLike(Double feelsLike) {
		this.feelsLike = feelsLike;
	}

	public Double getFeelsLike() {
		return feelsLike;
	}

	public void setTempMin(Double tempMin) {
		this.tempMin = tempMin;
	}

	public Double getTempMin() {
		return tempMin;
	}

	public void setTempMax(Double tempMax) {
		this.tempMax = tempMax;
	}

	public Double getTempMax() {
		return tempMax;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

	public int getPressure() {
		return pressure;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public int getHumidity() {
		return humidity;
	}

}
