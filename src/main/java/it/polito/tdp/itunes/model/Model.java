package it.polito.tdp.itunes.model;

import java.util.List;
import java.util.Map;
import java.util.LinkedList;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private ItunesDAO dao;
	private Graph<Album, DefaultWeightedEdge> grafo;
	private Map<Integer, Album> mappaAlbum;
	
	public Model() {
		super();
		this.dao = new ItunesDAO();
		this.mappaAlbum = this.dao.getMappaAlbum();
	}
	
	public void creaGrafo(int n) {
		
		this.grafo = new SimpleWeightedGraph<Album, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, this.dao.getVertici(n));
		
		List<Arco> archi = this.dao.getArchi(n, mappaAlbum);
		
		for(Arco a: archi) {
			this.grafo.addEdge(a.getA1(), a.getA2());
			this.grafo.setEdgeWeight(a.getA1(), a.getA2(), a.getPeso());
		}
		
		
		
	}
	
	//mi servono gli adiacenti del vertice passato come parametro e il bilancio degli adiacenti
	public List<AlbumBilancio> getBilanci(Album a){
		
		List<AlbumBilancio> result = new LinkedList<AlbumBilancio>();
		
		List<Album> adiacenti = Graphs.neighborListOf(grafo, a);
		
		double peso;
		int n;
		
		for(Album aa: adiacenti) {
			peso = 0;
			n = 0;
			for(DefaultWeightedEdge d: this.grafo.incomingEdgesOf(aa)) {
				peso += this.grafo.getEdgeWeight(d);
				n++;
			}
			result.add(new AlbumBilancio(aa, peso/n));
		}
		
		return result;
	}

	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}

	public int getNumArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Album> getVertici() {
		
		List<Album> lista = new LinkedList<>();
		
		for(Album a: this.grafo.vertexSet())
			lista.add(a);
		
		return lista;
	}
	
}
