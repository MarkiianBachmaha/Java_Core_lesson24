package task01;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

public class Cinema {

	TreeMap<Days, Schedule> schedules;

	private ArrayList<Movie> moviesLibrary;
	private Time openTime;
	private Time closeTime;

	public Cinema(Time openTime, Time closeTime) {
		super();
		this.moviesLibrary = new ArrayList<>();
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.schedules = new TreeMap<>();
		{
			schedules.put(Days.SUNDAY, new Schedule());
			schedules.put(Days.MONDAY, new Schedule());
			schedules.put(Days.TUESDAY, new Schedule());
			schedules.put(Days.WEDNESDAY, new Schedule());
			schedules.put(Days.THURSDAY, new Schedule());
			schedules.put(Days.FRIDAY, new Schedule());
			schedules.put(Days.SATURDAY, new Schedule());
		}

	}

	public TreeMap<Days, Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(TreeMap<Days, Schedule> schedules) {
		this.schedules = schedules;
	}

	public ArrayList<Movie> getMoviesLibrary() {
		return moviesLibrary;
	}

	public void setMoviesLibrary(ArrayList<Movie> moviesLibrary) {
		this.moviesLibrary = moviesLibrary;
	}

	public Time getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Time openTime) {
		this.openTime = openTime;
	}

	public Time getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Time closeTime) {
		this.closeTime = closeTime;
	}

	@Override
	public String toString() {
		return "Cinema [schedules=" + schedules + ", moviesLibrary=" + moviesLibrary + ", openTime=" + openTime
				+ ", closeTime=" + closeTime + "]";
	}

	public boolean checkSeance(Seance seance) {
		if ((seance.getStartTime().compareTo(openTime) < 0) || (seance.getEndTime().compareTo(closeTime) > 0)) {
			System.out.println("The Cinema is closed. Input time when Cinema is open (from "
					+ seance.getStartTime().toString() + " till " + seance.getEndTime().toString() + ")");
			return false;
		}
		return true;
	}

	public boolean addMovie(Movie movie, Time startTime, Time endTime) {
		if (moviesLibrary.add(movie)) {
			for (Schedule schedule : schedules.values()) {
				Set<Seance> seances = schedule.getSeances();
				seances.stream().forEach(s -> {
					s.setStartTime(startTime);
					s.setEndTime(endTime);
					s.setMovie(movie);
					schedule.addSeance(s);
				});
			}
			return true;
		}
		return false;
	}

	public boolean removeMovie(Movie movie) {
		if (moviesLibrary.remove(movie)) {
			System.out.println("The movie [" + movie + "] was removed");
			return true;
		}
		System.out.println("That movie is absence in movie library");
		return false;
	}

	public boolean addSeance(Seance seance, String day) {
		if (checkSeance(seance)) {
			for (Days currentDay : Days.values()) {
				if (currentDay.toString().equalsIgnoreCase(day)) {
					Schedule schedule = schedules.get(currentDay);
					schedule.addSeance(seance);
					schedules.put(currentDay, schedule);
				}
			}
			return true;
		}
		return false;
	}

	public boolean removeSeanceByDay(Seance seance, String day) {
		for (Days currentDay : Days.values()) {
			for (Schedule schedule : schedules.values()) {
				if (currentDay.toString().equalsIgnoreCase(day)) {
					schedule.removeSeance(seance);
				}
			}
		}
		System.out.println("This seance was successfully removed");
		return true;
	}

	public boolean removeAllSeancesByMovie(String title) {
		for (Schedule schedule : schedules.values()) {
			Set<Seance> seances = schedule.getSeances();
			for (Seance seance : seances) {
				if (seance.getMovie().getTitle().toString().equals(title)) {
					schedule.removeSeance(seance);
				}
			}
		}
		System.out.println("This seance was successfully removed");
		return true;
	}

}