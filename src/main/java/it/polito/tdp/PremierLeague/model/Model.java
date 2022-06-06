package it.polito.tdp.PremierLeague.model;

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
	
	public Model() {
		this.dao = new PremierLeagueDAO();
		this.idMap = new HashMap<>();
		this.dao.getAllTeams(idMap);
	}
	
	public void CreaGrafo() {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		//Aggiunta vertici
		Graphs.addAllVertices(this.grafo, dao.listAllTeams());
		
		System.out.println("Vertici del grafo creati!\n");
		System.out.println("#VERTICI: "+ this.grafo.vertexSet().size());
		
		//Aggiunta archi
		List<Adiacenza> adiacenze = this.dao.getAdiacenza(idMap);
		for(Adiacenza a: adiacenze) {
			if(this.grafo.containsVertex(a.getT1()) && this.grafo.containsVertex(a.getT2())) {
				//Calcolo il peso effettivo 
				if(a.getPeso() == -1) { //sconfitta per T1, vincita per T2
					a.getT2().setPunti(a.getT2().getPunti()+3);
				} else if(a.getPeso() == 0) { //pareggio
					a.getT1().setPunti(a.getT1().getPunti()+1);
					a.getT2().setPunti(a.getT2().getPunti()+1);
				}else if(a.getPeso() == 1) { //vittoria per T1
					a.getT1().setPunti(a.getT1().getPunti()+3);
				}
			}
		} 
		//A questo punto abbiamo settato i punti per ogni team contenuto nella lista di adiacenze (teams appartenenti al grafo)
		//Ora dobbiamo creare bene gli archi attribuendo a ciascuno un peso e un orientamento
		//Se peso == 0 non aggiungiamo l'arco
		
		for(Adiacenza a: adiacenze) {
			int peso = Math.abs(a.getT1().getPunti() -a.getT2().getPunti());
			//Aggiungo solo se > 0, altrimenti non aggiungo
			if(peso > 0)
				Graphs.addEdgeWithVertices(this.grafo, a.getT1(), a.getT2(), peso);
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
}
