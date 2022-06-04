package otang.weather.model.weather;

import com.google.gson.annotations.SerializedName;

public class Wind {

	@SerializedName("speed")
	private Double speed;

	@SerializedName("deg")
	private int deg;

	@SerializedName("gust")
	private Double gust;

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setDeg(int deg) {
		this.deg = deg;
	}

	public int getDeg() {
		return deg;
	}

	public void setGust(Double gust) {
		this.gust = gust;
	}

	public Double getGust() {
		return gust;
	}

}
