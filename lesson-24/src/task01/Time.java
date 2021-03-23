package task01;

public class Time implements Comparable<Time> {

	private int min;
	private int hour;

	public Time(int min, int hour) {
		super();
		int[] arr = limitTimeArray(hour, min);
		this.hour = arr[0];
		this.min = arr[1];
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public static Time plusTime(Time time, Time plusTime) {
		int minutesSum = time.hour * 60 + time.min + plusTime.hour * 60 + plusTime.min;
		if (minutesSum > 1440)
			minutesSum = minutesSum % 1440;

		return new Time(minutesSum / 60, minutesSum % 60);
	}

	public static Time timeFromString(String string) {
		String[] timeArr = string.split(":");
		int hour = 0;
		int min = 0;
		try {
			hour = Integer.parseInt(timeArr[0]);
			min = Integer.parseInt(timeArr[1]);
		} catch (NumberFormatException e) {
			System.out.println("Wrong time string, time set to 00:00, try again");
			hour = 0;
			min = 0;
		}
		return new Time(hour, min);
	}

	private int[] limitTimeArray(int hour, int min) {

		int[] timeArray = new int[2];
		int minTemp = min;
		int hourTemp = hour;
		if (min < 0)
			minTemp = 0;
		if (min > 60)
			minTemp = 0;
		if (hour < 0)
			hourTemp = 0;
		else if (hour >= 24) {
			if (min > 0)
				hourTemp = 0;
			else
				hourTemp = 24;
		}

		if (min == 60) {
			minTemp = 0;
			hourTemp += 1;
		}

		timeArray[0] = hourTemp;
		timeArray[1] = minTemp;
		return timeArray;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hour;
		result = prime * result + min;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Time other = (Time) obj;
		if (hour != other.hour)
			return false;
		if (min != other.min)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Time [hour=" + hour + ", min=" + min + "]";
	}

	@Override
	public int compareTo(Time o) {
		if (this.hour > o.getHour()) {
			return 1;
		} else if (this.hour < o.getHour()) {
			return -1;
		} else {
			if (this.min > o.getMin()) {
				return 1;
			} else if (this.min < o.getMin()) {
				return -1;
			}
		}
		return 0;
	}

}