package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.PremierLeague.model.Event.EventType;



public class Simulatore {
	
	//Dati in ingresso
	private Graph<Team, DefaultWeightedEdge> grafo;
	private int N; //Numero di reporter per team
	private int X; //Numero minimo di reporter a ogni partita (soglia di criticità)
	private Map<Integer, Team> mTeams;
	private List<Match> matches;
	
	//Dati in uscita
	private int numReporter;
	private double numMedioReporter; //numero medio di reporter che hanno assistito ad ogni partita
	private int numPartiteCritiche; //numero di partite in cui il numero tot di reporter era minore di X
	
	//Modello del mondo
    private List<Team> daAssegnare;
    private Team teamCorrente;
    private int sommaReporter;
    
	
	
	//Coda degli eventi
	private PriorityQueue<Event> queue ;



	public Simulatore(Graph<Team, DefaultWeightedEdge> grafo, Map<Integer, Team> mTeams) {
		super();
		this.grafo = grafo;
	}
	
	
	public void init(int N, int X, List<Match> matches, Map<Integer, Team> idMap) {
		this.N = N;
		this.X = X;
		this.matches = matches;
		this.mTeams = idMap;
		
		//Inizializzo gli output
		this.numMedioReporter = 0;
		this.numPartiteCritiche = 0;
		int sommaReporter = 0;
		
		
		for(Team t: this.grafo.vertexSet()) {
		    t.setnReporter(N); //Inizialmente ad ogni team sono assegnati N reporter
		}
		
		this.queue = new PriorityQueue<Event>();
		for(Match m: matches) {
			queue.add(new Event(m, null));
		}
	}
	
	public void run() {
		while (!queue.isEmpty()) {
			Event e = this.queue.poll();
			this.processEvent(e);
		}
	}


	private void processEvent(Event e) {
		Match m = e.getMatch();
		EventType type = e.getType();
		
		if(mTeams.get(m.getTeamHomeID()).getnReporter() + mTeams.get(m.getTeamAwayID()).getnReporter() < this.X) {
			this.numPartiteCritiche++;
		}
		sommaReporter = mTeams.get(e.getMatch().getTeamHomeID()).getnReporter() + mTeams.get(e.getMatch().getTeamAwayID()).getnReporter();
		
		int resultOfTeamHome = m.getReaultOfTeamHome();
		
		if(resultOfTeamHome == 0) {
			//Faccio nulla
		}
		if(resultOfTeamHome == -1) { //SQUADRA PERDENTE
			if(Math.random()<0.2) { //Nel 20% dei casi
				for(Team t: this.grafo.vertexSet()){
					if(t.getPunti() < mTeams.get(e.getMatch().getTeamHomeID()).getPunti()) {
						t.incrementaReporterdiNumeroRandom((int) Math.random()*(N+1)); //reporter bocciato/i aggiunto/i alla squadra piu scarsa
					mTeams.get(e.getMatch().getTeamHomeID()).decrementaReporter();
					}
				}
			}
		}
		if(resultOfTeamHome == 1) { //SQUADRA VINCENTE 
			if(Math.random()<0.5) { //Nel 50% dei casi
				for(Team t: this.grafo.vertexSet()){
					if(t.getPunti() > mTeams.get(e.getMatch().getTeamHomeID()).getPunti())
					t.incrementaReporter();
					mTeams.get(e.getMatch().getTeamHomeID()).decrementaReporter();
					
				}
			}
		}
		
	}
	
	public int getPartiteCritiche() {
		return numPartiteCritiche;
	}

	public double getMediaReporter() {
		return sommaReporter/matches.size();
	}

}
