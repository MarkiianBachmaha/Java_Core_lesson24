package task01;

import java.util.Set;
import java.util.TreeSet;

public class Schedule {

	private Set<Seance> seances = new TreeSet<>();

	public Schedule() {
		super();
	}

	public Schedule(Set<Seance> seances) {
		super();
		this.seances = seances;
	}

	public Set<Seance> getSeances() {
		return seances;
	}

	public void setSeances(Set<Seance> seances) {
		this.seances = seances;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((seances == null) ? 0 : seances.hashCode());
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
		Schedule other = (Schedule) obj;
		if (seances == null) {
			if (other.seances != null)
				return false;
		} else if (!seances.equals(other.seances))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Schedule [seances=" + seances + "]";
	}

	public boolean addSeance(Seance seance) {
		if (seances.add(seance))
			return true;
		return false;
	}

	public boolean removeSeance(Seance seance) {
		if (seances.remove(seance))
			return true;
		return false;
	}

}