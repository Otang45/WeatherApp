package otang.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.LinearLayout;
import com.airbnb.lottie.LottieAnimationView;
import android.widget.ImageView;
import android.view.ViewGroup;
import java.util.TimeZone;
import java.util.Date;
import java.text.SimpleDateFormat;
import net.cachapa.expandablelayout.ExpandableLayout;
import otang.weather.R;
import androidx.recyclerview.widget.RecyclerView;
import otang.weather.databinding.ForecastItemBinding;
import otang.weather.databinding.ForecastItemTopBinding;
import otang.weather.model.forecast.ForecastResponse;
import otang.weather.util.AppUtils;
import otang.weather.util.ColourUtils;
import otang.weather.util.DpUtils;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

	private static final int UNSELECTED = -1;
	private static final int TOP = 0;
	private static final int OTHER = 1;

	private Context context;
	private DpUtils dpUtils;
	private ForecastResponse forecastWeather;
	private RecyclerView recyclerView;

	int selectedItem = UNSELECTED;

	public ForecastAdapter(Context context, ForecastResponse forecastWeather, RecyclerView recyclerView) {
		this.context = context;
		this.dpUtils = new DpUtils(context);
		this.forecastWeather = forecastWeather;
		this.recyclerView = recyclerView;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == TOP) {
			return new ViewHolder(ForecastItemTopBinding.inflate(LayoutInflater.from(context), parent, false));
		} else {
			return new ViewHolder(ForecastItemBinding.inflate(LayoutInflater.from(context), parent, false));
		}
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.bind(position);
	}

	@Override
	public int getItemCount() {
		if (forecastWeather != null) {
			return forecastWeather.getList().size();
		} else {
			return 0;
		}
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return TOP;
		} else {
			return OTHER;
		}
	}

	private void setupViewTop(ViewHolder holder, int position) {
		// Card 1
		holder.topBinding.sl.setCornerRadius(dpUtils.dp20(), dpUtils.dp20(), dpUtils.dp20(), dpUtils.dp20());
		holder.topBinding.sl.setShadowColor(context.getColor(R.color.colorPrimary));
		holder.topBinding.sl.setShadowDx(dpUtils.dp0());
		holder.topBinding.sl.setShadowDy(dpUtils.dp5());
		holder.topBinding.sl.setShadowMargin((int) dpUtils.dp16(), (int) dpUtils.dp24(), (int) dpUtils.dp16(),
				(int) dpUtils.dp16());
		holder.topBinding.sl.setShadowRadius(dpUtils.dp5());
		// Data
		if (forecastWeather != null) {
			holder.topBinding.lavIcon.setAnimation(
					AppUtils.getWeatherAnimation(forecastWeather.getList().get(position).getWeather().get(0).getId()));
			holder.topBinding.tvTemp.setText(
					String.valueOf(Math.round(forecastWeather.getList().get(position).getMain().getTemp()) + "°C"));
			holder.topBinding.tvDescription
					.setText(forecastWeather.getList().get(position).getWeather().get(0).getDescription());
			holder.topBinding.tvHumidity
					.setText(String.valueOf(forecastWeather.getList().get(position).getMain().getHumidity()) + "%");
			holder.topBinding.tvPressure
					.setText(String.valueOf(forecastWeather.getList().get(position).getMain().getPressure()) + " hPa");
			holder.topBinding.tvTempMin.setText(
					String.valueOf(Math.round(forecastWeather.getList().get(position).getMain().getTempMin()) + "°C"));
			holder.topBinding.tvTempMax.setText(
					String.valueOf(Math.round(forecastWeather.getList().get(position).getMain().getTempMax()) + "°C"));
			holder.topBinding.tvVisibility
					.setText(String.valueOf(forecastWeather.getList().get(position).getVisibility()) + " Meter");
			holder.topBinding.tvWindSpeed
					.setText(String.valueOf(forecastWeather.getList().get(position).getWind().getSpeed()) + " Km/h");
			holder.topBinding.tvTime.setText(getDate(forecastWeather.getList().get(position).getDt()));
		}
	}

	public class ViewHolder extends RecyclerView.ViewHolder
			implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener {

		private ForecastItemBinding binding;
		private ForecastItemTopBinding topBinding;

		public ViewHolder(ForecastItemTopBinding topBinding) {
			super(topBinding.getRoot());
			this.topBinding = topBinding;
		}

		public ViewHolder(ForecastItemBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
		}

		public void bind(int pos) {
			if (getItemViewType() == 0) {
				setupViewTop(this, pos);
			} else {
				int position = getAdapterPosition();
				boolean isSelected = position == selectedItem;
				// Root view
				binding.llForecast.setOnClickListener(ViewHolder.this);
				if (position == 1) {
					binding.llForecast.setBackgroundResource(R.drawable.forecast_first_item);
					ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.llForecast
							.getLayoutParams();
					params.setMargins((int) dpUtils.dp16(), (int) dpUtils.dp16(), (int) dpUtils.dp16(),
							(int) dpUtils.dp8());
					binding.llForecast.requestLayout();
				} else if (position == forecastWeather.getList().size() - 1) {
					binding.llForecast.setBackgroundResource(R.drawable.forecast_last_item);
					ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.llForecast
							.getLayoutParams();
					params.setMargins((int) dpUtils.dp16(), (int) dpUtils.dp8(), (int) dpUtils.dp16(),
							(int) dpUtils.dp24());
					binding.llForecast.requestLayout();
				} else {
					binding.llForecast.setBackgroundResource(R.drawable.forecast_item);
					ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) binding.llForecast
							.getLayoutParams();
					params.setMargins((int) dpUtils.dp16(), (int) dpUtils.dp8(), (int) dpUtils.dp16(),
							(int) dpUtils.dp8());
					binding.llForecast.requestLayout();
				}
				// Expandable layoyt
				binding.expandableLayout.setExpanded(isSelected, false);
				binding.expandableLayout.setOnExpansionUpdateListener(ViewHolder.this);
				// Indicator
				binding.ivArrow.setSelected(isSelected);
				binding.ivArrow.setImageResource(R.drawable.ic_arrow_down);
				// Data
				if (forecastWeather != null) {
					binding.lavIcon.setAnimation(AppUtils
							.getWeatherAnimation(forecastWeather.getList().get(pos).getWeather().get(0).getId()));
					binding.tvTemp.setText(String
							.valueOf(Math.round(forecastWeather.getList().get(position).getMain().getTemp()) + "°C"));
					binding.tvDescription
							.setText(forecastWeather.getList().get(position).getWeather().get(0).getDescription());
					binding.tvHumidity.setText(
							String.valueOf(forecastWeather.getList().get(position).getMain().getHumidity()) + "%");
					binding.tvPressure.setText(
							String.valueOf(forecastWeather.getList().get(position).getMain().getPressure()) + " hPa");
					binding.tvTempMin.setText(String.valueOf(
							Math.round(forecastWeather.getList().get(position).getMain().getTempMin()) + "°C"));
					binding.tvTempMax.setText(String.valueOf(
							Math.round(forecastWeather.getList().get(position).getMain().getTempMax()) + "°C"));
					binding.tvVisibility.setText(
							String.valueOf(forecastWeather.getList().get(position).getVisibility()) + " Meter");
					binding.tvWindSpeed.setText(
							String.valueOf(forecastWeather.getList().get(position).getWind().getSpeed()) + " Km/h");
					binding.tvTime.setText(getDate(forecastWeather.getList().get(position).getDt()));
				}
			}
		}

		@Override
		public void onExpansionUpdate(float expansionFraction, int state) {
			if (state == ExpandableLayout.State.EXPANDING) {
				recyclerView.smoothScrollToPosition(getAdapterPosition());
			}
		}

		@Override
		public void onClick(View view) {
			ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);
			if (holder != null) {
				holder.binding.ivArrow.setSelected(false);
				holder.binding.ivArrow.setImageResource(R.drawable.ic_arrow_down);
				holder.binding.expandableLayout.collapse();
			}
			int position = getAdapterPosition();
			if (position == selectedItem) {
				selectedItem = UNSELECTED;
			} else {
				binding.ivArrow.setSelected(true);
				binding.ivArrow.setImageResource(R.drawable.ic_arrow_up);
				binding.expandableLayout.expand();
				selectedItem = position;
			}
		}

	}

	private String getDate(long time) {
		Date date = new Date(time * 1000L); // *1000 is to convert seconds to milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd/MM/yyyy | HH:mm"); // the format of your date
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		return sdf.format(date);
	}
}