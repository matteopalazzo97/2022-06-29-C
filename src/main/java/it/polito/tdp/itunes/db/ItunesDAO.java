package it.polito.tdp.itunes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import it.polito.tdp.itunes.model.Album;
import it.polito.tdp.itunes.model.Arco;
import it.polito.tdp.itunes.model.Artist;
import it.polito.tdp.itunes.model.Genre;
import it.polito.tdp.itunes.model.MediaType;
import it.polito.tdp.itunes.model.Playlist;
import it.polito.tdp.itunes.model.Track;

public class ItunesDAO {
	
	public List<Album> getAllAlbums(){
		final String sql = "SELECT * FROM Album";
		List<Album> result = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Album(res.getInt("AlbumId"), res.getString("Title")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}
	
	public List<Artist> getAllArtists(){
		final String sql = "SELECT * FROM Artist";
		List<Artist> result = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Artist(res.getInt("ArtistId"), res.getString("Name")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}
	
	public List<Playlist> getAllPlaylists(){
		final String sql = "SELECT * FROM Playlist";
		List<Playlist> result = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Playlist(res.getInt("PlaylistId"), res.getString("Name")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}
	
	public List<Track> getAllTracks(){
		final String sql = "SELECT * FROM Track";
		List<Track> result = new ArrayList<Track>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Track(res.getInt("TrackId"), res.getString("Name"), 
						res.getString("Composer"), res.getInt("Milliseconds"), 
						res.getInt("Bytes"),res.getDouble("UnitPrice")));
			
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}
	
	public List<Genre> getAllGenres(){
		final String sql = "SELECT * FROM Genre";
		List<Genre> result = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Genre(res.getInt("GenreId"), res.getString("Name")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}
	
	public List<MediaType> getAllMediaTypes(){
		final String sql = "SELECT * FROM MediaType";
		List<MediaType> result = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new MediaType(res.getInt("MediaTypeId"), res.getString("Name")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}
	
	public List<Album> getVertici(int n){
		final String sql = "SELECT DISTINCT aa.`AlbumId`, aa.`ArtistId`, aa.`Title` "
				+ "FROM album aa, `track` t "
				+ "WHERE aa.`AlbumId`=t.`AlbumId` "
				+ "GROUP BY aa.`AlbumId` "
				+ "HAVING sum(t.`UnitPrice`)>? "
				+ "ORDER BY aa.`Title`";
		List<Album> result = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, n);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Album(res.getInt("AlbumId"), res.getString("Title")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}
	
	public List<Arco> getArchi(int n, Map<Integer, Album> mappa){
		final String sql = "SELECT j1.`AlbumId` AS a1, j2.`AlbumId`AS a2, abs(j1.peso-j2.peso) AS weight "
				+ "FROM (SELECT DISTINCT aa.`AlbumId`, sum(t.`UnitPrice`) AS peso "
				+ "	  FROM album aa, `track` t "
				+ "	  WHERE aa.`AlbumId`=t.`AlbumId` "
				+ "	  GROUP BY aa.`AlbumId` "
				+ "	  HAVING sum(t.`UnitPrice`)>?) AS j1, "
				+ "	  (SELECT DISTINCT aa.`AlbumId`, sum(t.`UnitPrice`) AS peso "
				+ "	  FROM album aa, `track` t "
				+ "	  WHERE aa.`AlbumId`=t.`AlbumId` "
				+ "	  GROUP BY aa.`AlbumId` "
				+ "	  HAVING sum(t.`UnitPrice`)>?) AS j2 "
				+ "WHERE j1.`AlbumId`<j2.`AlbumId` "
				+ "HAVING weight<>0";
		List<Arco> result = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, n);
			st.setInt(2, n);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Arco(mappa.get(res.getInt("a1")), mappa.get(res.getInt("a2")),
						res.getDouble("weight")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}
	
	public Map<Integer, Album> getMappaAlbum(){
		final String sql = "SELECT * FROM Album";
		Map<Integer, Album> result = new HashMap<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.put(res.getInt("AlbumId"), new Album(res.getInt("AlbumId"), res.getString("Title")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}
	
	
}
