package it.polito.tdp.PremierLeague.model;

public class Event implements Comparable<Event> {

	public enum EventType {
		SQUADRA_VINCENTE,
		SQUADRA_PERDENTE,
		PAREGGIO
	}
	
	private Match match;
	private EventType type;
	
	
	public Event(Match match, EventType type) {
		super();
		this.match = match;
		this.type = type;
	}

	public Match getMatch() {
		return match;
	}

	public EventType getType() {
		return type;
	}

	@Override
	public int compareTo(Event o) {
		return this.match.getDate().compareTo(o.getMatch().getDate());
	}

}
