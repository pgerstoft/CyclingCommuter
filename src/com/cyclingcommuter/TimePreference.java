package com.cyclingcommuter;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

public class TimePreference extends DialogPreference {
	private static final DateTimeFormatter FORMATTER = DateTimeFormat
			.forPattern("h:mm a");

	private int lastHour = 0;
	private int lastMinute = 0;
	private TimePicker picker = null;

	public TimePreference(Context ctxt, AttributeSet attrs) {
		super(ctxt, attrs);

		setPositiveButtonText("Set");
		setNegativeButtonText("Cancel");
	}

	@Override
	protected View onCreateDialogView() {
		picker = new TimePicker(getContext());

		return picker;
	}

	@Override
	protected void onBindDialogView(View v) {
		super.onBindDialogView(v);

		picker.setCurrentHour(lastHour);
		picker.setCurrentMinute(lastMinute);
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);

		if (positiveResult) {
			lastHour = picker.getCurrentHour();
			lastMinute = picker.getCurrentMinute();

			LocalTime time = new LocalTime(lastHour, lastMinute, 0);
			persistString(time.toString("h:mm a"));
		}
	}

	@Override
	protected Object onGetDefaultValue(TypedArray a, int index) {
		return (a.getString(index));
	}

	@Override
	protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
		String time = null;

		if (restoreValue) {
			if (defaultValue == null) {
				time = getPersistedString("00:00 AM");
			} else {
				time = getPersistedString(defaultValue.toString());
			}
		} else {
			time = defaultValue.toString();
		}

		LocalTime parsedTime = LocalTime.parse(time, FORMATTER);

		lastHour = parsedTime.getHourOfDay();
		lastMinute = parsedTime.getMinuteOfHour();
	}
}