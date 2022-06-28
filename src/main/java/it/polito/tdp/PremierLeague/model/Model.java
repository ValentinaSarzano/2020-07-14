package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private Graph<Team, DefaultWeightedEdge> grafo;
	private Map<Integer, Team> idMap;
	private List<Match> matches;
	
	//Risultati simulazione
	private int partiteCritiche;
	private double mediaReporter;
	
	public Model() {
		this.dao = new PremierLeagueDAO();
		this.idMap = new HashMap<>();
		this.dao.getAllTeams(idMap);
		this.matches = this.dao.listAllMatches();
	}
	
	public void CreaGrafo() {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		//Aggiunta vertici
		Graphs.addAllVertices(this.grafo, dao.listAllTeams()); //In realta li hai gia nella mappa, potresti fare idMap.values()
		
		System.out.println("Vertici del grafo creati!\n");
		System.out.println("#VERTICI: "+ this.grafo.vertexSet().size() + "\n");
		
		//Aggiunta archi
		this.dao.assegnaPunti(idMap);
		
		//Ora dobbiamo creare bene gli archi attribuendo a ciascuno un peso e un orientamento
		//Se peso == 0 non aggiungiamo l'arco
		for(Team t1: idMap.values()) {
			for(Team t2: idMap.values()) {
				if(t1.compareTo(t2)!=0 && this.grafo.vertexSet().contains(t1) && this.grafo.vertexSet().contains(t2) && !this.grafo.edgeSet().contains(this.grafo.getEdge(t1, t2)) && !this.grafo.edgeSet().contains(this.grafo.getEdge(t2, t1))) {
					if(t1.getPunti() - t2.getPunti() > 0) { ////T1 ha il punteggio maggiore, orientamento: T1 --> T2
						Graphs.addEdgeWithVertices(this.grafo, t1, t2, Math.abs(t1.getPunti() - t2.getPunti()));
					} else if(t1.getPunti() - t2.getPunti() < 0) { ////T2 ha il punteggio maggiore, orientamento: T2 --> T1
						Graphs.addEdgeWithVertices(this.grafo, t2, t1, Math.abs(t2.getPunti() - t1.getPunti()));
					}
					
				}
			}
		}
		System.out.println("Archi del grafo creati!\n");
		System.out.println("#ARCHI: "+ this.grafo.edgeSet().size());
		
		
	}
	
	public List<Team> listAllTeams(){
		return dao.listAllTeams();
	}

	public int nVertici() {
		return this.grafo.vertexSet().size();
	}

	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Vicino> getSquadreBattute(Team t){
		List<Vicino> squadreBattute = new ArrayList<>();
		//Le squadre battute saranno quelle collegate da archi uscenti da t (ma non solo, anche tutte quelle che stanno al di sotto)
	   
		for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(t)) {
			Vicino v = new Vicino(this.grafo.getEdgeTarget(e), (int) this.grafo.getEdgeWeight(e));
			squadreBattute.add(v);
		
		}
		Collections.sort(squadreBattute);
		
		return squadreBattute;
	}
	
	public List<Vicino> getSquadreMigliori(Team t){
		List<Vicino> squadreMigliori = new ArrayList<>();
		//Le squadre battute saranno quelle collegate da archi uscenti da t (ma non solo, anche tutte quelle che stanno al di sotto)
	   
		for(DefaultWeightedEdge e: this.grafo.incomingEdgesOf(t)) {
			Vicino vv = new Vicino(this.grafo.getEdgeSource(e), (int) this.grafo.getEdgeWeight(e));
			squadreMigliori.add(vv);
		}
		Collections.sort(squadreMigliori);
		
		return squadreMigliori;
	}
	
	public void simula(int N, int X) {
		Simulatore sim = new Simulatore(this.grafo, this.idMap);
		sim.init(N, X, matches, idMap);
		sim.run();
		this.partiteCritiche = sim.getPartiteCritiche();
		this.mediaReporter = sim.getMediaReporter();
	}
	
	public int getPartiteCritiche() {
		return partiteCritiche;
	}
	
	public double getMediaReporter() {
		return mediaReporter;
	}
	
}
