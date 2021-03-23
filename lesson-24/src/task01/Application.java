package task01;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;

public class Application {

	public static void Menu() {
		System.out.println("| ---------------------ћ≈Ќё--------------------- |");
		System.out.println("| ¬вед≥ть 1, щоб додати ф≥льм                    |");
		System.out.println("| ¬вед≥ть 2, щоб видалити ф≥льм                  |");
		System.out.println("| ¬вед≥ть 3, щоб додати сеанс в розклад дн€      |");
		System.out.println("| ¬вед≥ть 4, щоб видалити сеанс з розкладу дн€   |");
		System.out.println("| ¬вед≥ть 5, щоб показати розклад сеанс≥в        |");
		System.out.println("| ¬вед≥ть 6, щоб вийти з програми                |");
		System.out.println("| ---------------------------------------------- |");
	}

	static Cinema cinema;

	public static void main(String[] args) {

		Scanner sc;
		sc = new Scanner(System.in);
		cinema = initializeCinema();
		Menu();

		while (true) {
			sc = new Scanner(System.in);

			switch (sc.next()) {
			case "1": {
				addMovieFromConsole();
				break;
			}
			case "2": {
				removeMovieFromConsole();
				break;
			}
			case "3": {
				addSeanceFromConsole();
				break;
			}
			case "4": {
				removeSeanceFromConsole();
				break;
			}
			case "5": {
				String day = getDayFromConsole();
				showScheduleByDay(day);
				break;
			}
			case "6": {
				System.out.println("Exit the program.");
				System.exit(0);
				sc.close();
			}

			}

		}
	}

	@SuppressWarnings("resource")
	public static Cinema initializeCinema() {
		Time openTime = Time.timeFromString("06:00");
		Time closeTime = Time.timeFromString("00:00");
		Scanner sc1;
		sc1 = new Scanner(System.in);
		System.out.println("Enter open time of the Cinema (mm:hh)");
		if (sc1.hasNext())
			openTime = Time.timeFromString(sc1.next());
		System.out.println("Enter close time of the Cinema (mm:hh)");
		if (sc1.hasNext())
			closeTime = Time.timeFromString(sc1.next());
		System.out.println("Cinema was created");
		return new Cinema(openTime, closeTime);
	}

	public static void addMovieFromConsole() {

		Scanner sc1 = new Scanner(System.in);
		System.out.println("Enter name of the movie:");
		String title = "";
		if (sc1.hasNext())
			title = sc1.next();
		System.out.println("Enter duration of the movie to add(mm:hh):");
		Time duration = Time.timeFromString("0:00");
		if (sc1.hasNext())
			duration = Time.timeFromString(sc1.next());

		Movie movie = new Movie(title, duration);
		cinema.getMoviesLibrary().add(movie);
		System.out.println("The movie added: " + movie.toString());
	}

	public static void removeMovieFromConsole() {
		Scanner sc2 = new Scanner(System.in);
		System.out.println("Enter name of the movie:");
		String title = "";
		if (sc2.hasNext())
			title = sc2.next();
		Movie movie;
		Optional<Movie> movieOpt = findMovie(title);
		if (movieOpt.isPresent()) {
			movie = movieOpt.get();
			cinema.removeMovie(movie);
			System.out.println(movie.toString() + " was deleted");
			cinema.removeAllSeancesByMovie(title);

		} else
			System.out.println("Something is wrong...");
	}

	public static void addSeanceFromConsole() {
		Movie movie = getMovieFromConsole();
		String day = getDayFromConsole();
		Seance seance = getSeanceFromConsole(movie);

		cinema.addSeance(seance, day);
	}

	public static void removeSeanceFromConsole() {

		String day = getDayFromConsole();
		Scanner sc3 = new Scanner(System.in);
		System.out.println("Enter start time of seance that you want to delete:");
		Time startTime = Time.timeFromString("0:00");
		if (sc3.hasNext())
			startTime = Time.timeFromString(sc3.next());
		Optional<Seance> findSeance = findSeanceByTime(startTime);
		if (findSeance.isPresent()) {
			cinema.removeSeanceByDay(findSeance.get(), day);
		} else
			System.out.println("Seance in this day and this start time is absence:");
	}

	public static void showMovieLibrary() {

		ArrayList<Movie> moviesLibrary = cinema.getMoviesLibrary();
		System.out.println("Movie library:");
		if (!moviesLibrary.isEmpty()) {
			moviesLibrary.stream().forEach(System.out::println);
		} else {
			System.out.println("Movie library is empty yet...");
		}

	}

	public static void showScheduleByDay(String day) {

		for (Entry<Days, Schedule> scheduleEntry : cinema.getSchedules().entrySet()) {
			Days key = scheduleEntry.getKey();
			if (key.toString().equalsIgnoreCase(day)) {
				Schedule value = scheduleEntry.getValue();
				value.getSeances().stream().forEach(System.out::println);

			}

		}
	}

	private static Movie getMovieFromConsole() {
		Optional<Movie> movieOpt;
		while (true) {
			Scanner sc3 = new Scanner(System.in);
			System.out.println("Enter a name for an existing movie:");
			String title = "";
			if (sc3.hasNext())
				title = sc3.next();
			movieOpt = findMovie(title);
			if (movieOpt.isPresent()) {
				System.out.println(movieOpt.get().toString() + " is in the movie library, next...");
				break;
			} else
				System.out.println("There is no movie in the library");
		}
		return movieOpt.get();
	}

	private static String getDayFromConsole() {
		String dayString = "";
		String stringOut = "";
		Scanner sc3 = new Scanner(System.in);
		while (true) {
			System.out.println("Enter seance day");
			if (sc3.hasNext())
				dayString = sc3.next();
			for (Days day : Days.values()) {
				if (day.toString().equalsIgnoreCase(dayString))
					stringOut = day.toString();
			}
			if (stringOut.isEmpty()) {
				System.out.println("Wrong day name, try again");
			} else {
				System.out.println("Day " + stringOut + " choosen");
				break;
			}
		}
		return stringOut;
	}

	private static Seance getSeanceFromConsole(Movie movie) {
		Time startTime = Time.timeFromString("0:00");
		Scanner sc3 = new Scanner(System.in);
		Seance seance;
		while (true) {
			System.out.println("Enter start time of new seance(mm:hh):");
			if (sc3.hasNext())
				startTime = Time.timeFromString(sc3.next());
			Seance seanceTemp = new Seance(movie, startTime);
			if (cinema.checkSeance(seanceTemp)) {
				seance = seanceTemp;
				System.out.println("This movie is on the library now;");
				break;
			}
		}
		return seance;
	}

	private static Optional<Movie> findMovie(String title) {
		for (Movie movie : cinema.getMoviesLibrary()) {
			if (movie.getTitle().equalsIgnoreCase(title)) {
				return Optional.of(movie);
			}
		}
		return Optional.empty();
	}

	private static Optional<Seance> findSeanceByTime(Time startTime) {
		for (Schedule schedule : cinema.getSchedules().values()) {
			for (Seance seance : schedule.getSeances()) {
				if (seance.getStartTime().equals(startTime))
					return Optional.of(seance);
			}
		}
		return Optional.empty();
	}

}