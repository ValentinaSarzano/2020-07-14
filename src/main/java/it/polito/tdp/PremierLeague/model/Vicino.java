package it.polito.tdp.PremierLeague.model;

public class Vicino implements Comparable<Vicino>{
	private Team team;
	private int peso;
	
	public Vicino(Team team, int peso) {
		super();
		this.team = team;
		this.peso = peso;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(Vicino other) { //Per come è stato creato il grafo
		                                 //ordinare per differenza di punteggio
		return this.peso - other.peso;   //equivale ad ordinare per peso!!!!!
	}

	@Override
	public String toString() {
		return team.getName();
	}
	
	
	

}
