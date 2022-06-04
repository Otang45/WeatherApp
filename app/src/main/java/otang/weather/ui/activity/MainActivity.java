package otang.weather.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Looper;
import androidx.annotation.NonNull;
import otang.weather.R;
import otang.weather.preference.WindowPreference;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.IOException;
import java.util.Locale;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.location.LocationSettingsRequest;
import com.fragula2.animation.SwipeController;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.fragula2.utils.FragmentKt;
import otang.weather.api.ApiClient;
import otang.weather.ui.fragment.HomeFragment;
import otang.weather.util.AppUtils;
import androidx.navigation.fragment.NavHostFragment;
import otang.weather.util.PrefUtils;
import androidx.navigation.NavController;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import android.content.Intent;
import otang.weather.api.ApiInterface;
import otang.weather.databinding.ActivityMainBinding;
import java.util.List;
import pub.devrel.easypermissions.EasyPermissions;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

	private final int REQUEST_CHECK_SETTINGS = 21;
	private final String ACL = Manifest.permission.ACCESS_COARSE_LOCATION;
	private final String AFL = Manifest.permission.ACCESS_FINE_LOCATION;
	private ActivityMainBinding binding;
	private ApiInterface apiInterface;
	private FusedLocationProviderClient fusedLocationClient;
	private LocationRequest locationRequest;
	private NavController navController;
	private NavHostFragment navHostFragment;
	private PrefUtils prefUtils;
	private SwipeController swipeController;

	String city;
	String[] perm;

	double latitude = 0;
	double longitude = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		setupWindow();
		initialize();
		perm = new String[] { ACL, AFL };
		requestPermission();
		logic();
	}

	private boolean requestPermission() {
		if (EasyPermissions.hasPermissions(this, perm)) {
			return true;
		} else {
			EasyPermissions.requestPermissions(this, "Permissions Need", 0, perm);
			AppUtils.showToast(this, "No Permission");
			return false;
		}
	}

	private void initialize() {
		prefUtils = new PrefUtils(this);
		apiInterface = ApiClient.getApiInterface();
		navHostFragment = binding.navHost.getFragment();
		navController = navHostFragment.getNavController();
		swipeController = FragmentKt.findSwipeController(navHostFragment);
		fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
	}

	private void logic() {
		binding.ivLocation.setOnClickListener(v -> {
			if (requestPermission()) {
				getLocation();
			}
		});
		binding.tvDate.setText(getDate());
		binding.tvCity.setText(prefUtils.getString("city"));
	}

	private void startLocationUpdates() {
		fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
	}

	private void stopLocationUpdate() {
		fusedLocationClient.removeLocationUpdates(locationCallback);
	}

	LocationCallback locationCallback = new LocationCallback() {
		@Override
		public void onLocationResult(LocationResult locationResult) {
			if (locationResult == null) {
				return;
			}
			for (Location location : locationResult.getLocations()) {
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				prefUtils.saveAs("lat", (float) latitude);
				prefUtils.saveAs("lon", (float) longitude);
				getCityInfo();
				stopLocationUpdate();
			}
		}
	};

	private void getLocation() {
		locationRequest = LocationRequest.create();
		locationRequest.setInterval(5000);
		locationRequest.setFastestInterval(2000);
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
		builder.addLocationRequest(locationRequest);
		SettingsClient client = LocationServices.getSettingsClient(this);
		Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
		task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
			@Override
			public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
				startLocationUpdates();
			}
		});
		task.addOnFailureListener(this, new OnFailureListener() {
			@Override
			public void onFailure(@NonNull Exception e) {
				if (e instanceof ResolvableApiException) {
					try {
						ResolvableApiException resolvable = (ResolvableApiException) e;
						resolvable.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
					} catch (IntentSender.SendIntentException sendEx) {
					}
				}
			}
		});
	}

	private void getCityInfo() {
		try {
			Geocoder geocoder = new Geocoder(this, Locale.getDefault());
			List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
			String a = addressList.get(0).getSubLocality() + ", " + addressList.get(0).getLocality();
			prefUtils.saveAs("city", a);
			binding.tvCity.setText(a);
			AppUtils.showToast(this, "Refresh!!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getDate() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy");
		Date date = new Date(calendar.getTimeInMillis());
		return sdf.format(date);
	}

	private void setupWindow() {
		new WindowPreference(this).applyEdgeToEdgePreference(getWindow(), getColor(R.color.colorSurface));
		AppUtils.addSystemWindowInsetToPadding(binding.getRoot(), false, true, false, true);
	}

	@Override
	public boolean onSupportNavigateUp() {
		return super.onSupportNavigateUp() || navController.navigateUp();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		// Forward results to EasyPermissions
		EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
	}

	@Override
	protected void onActivityResult(int reqCode, int resCode, Intent data) {
		super.onActivityResult(reqCode, resCode, data);
		switch (reqCode) {
		case REQUEST_CHECK_SETTINGS:
			if (resCode == Activity.RESULT_OK) {
				getLocation();
			}
		}
	}

	@Override
	public void onPermissionsGranted(int requestCode, List<String> perms) {
		getLocation();
	}

	@Override
	public void onPermissionsDenied(int requestCode, List<String> perms) {
	}

}