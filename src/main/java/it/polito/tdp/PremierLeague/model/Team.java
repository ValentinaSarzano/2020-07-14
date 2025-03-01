package it.polito.tdp.PremierLeague.model;

public class Team implements Comparable<Team>{
	Integer teamID;
	String name;
	int punti;
	int nReporter;
	
	public Team(Integer teamID, String name) {
		super();
		this.teamID = teamID;
		this.name = name;
		this.punti = 0;
	}
	
	public Integer getTeamID() {
		return teamID;
	}
	public void setTeamID(Integer teamID) {
		this.teamID = teamID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPunti(int puntiAggiuntivi) {
		this.punti = punti + puntiAggiuntivi;
	}
	
	public int getPunti() {
		return punti;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((teamID == null) ? 0 : teamID.hashCode());
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
		Team other = (Team) obj;
		if (teamID == null) {
			if (other.teamID != null)
				return false;
		} else if (!teamID.equals(other.teamID))
			return false;
		return true;
	}

	@Override
	public int compareTo(Team other) {
		
		return this.punti-other.punti;
	}
	

	public int getnReporter() {
		return nReporter;
	}

	public void incrementaReporter() {
		this.nReporter++;
	}

	public void decrementaReporter() {
		this.nReporter--;
	}

	public void incrementaReporterdiNumeroRandom(int numeroRandom) {
		this.nReporter = this.nReporter - numeroRandom;
		
	}

	public void setnReporter(int n) {
        this.nReporter = n;
		
	}
}
