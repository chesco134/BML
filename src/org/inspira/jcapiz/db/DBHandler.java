package org.inspira.jcapiz.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DBHandler {

	private DatabaseConnection dbCon;
	
	public DBHandler() throws IOException{
		dbCon = new DatabaseConnection();
	}
	
	public JSONObject obtenerPaises(){
		JSONObject json = new JSONObject();
		try{
			Connection con = dbCon.getConnection();
			ResultSet rs = con.prepareStatement("select * from Pais").executeQuery();
			JSONArray jpaises = new JSONArray();
			JSONObject jpais;
			while(rs.next()){
				jpais = new JSONObject();
				jpais.put("idPais",Integer.parseInt(rs.getString("idPais")));
				jpais.put("pais", rs.getString("Origen"));
				jpaises.put(jpais);
			}
			json.put("Paises", jpaises);
			json.put("content", true);
			json.put("response", "Hecho.");
		}catch(SQLException | JSONException e){
			e.printStackTrace();
		}
		closeDBConnection();
		return json;
	}
	
	public JSONObject obtenerDatoInteres(int pais){
		JSONObject json = new JSONObject();
		try{
			Connection con = dbCon.getConnection();
			PreparedStatement pstmnt = con.prepareStatement("select * from DatoInteres where Pais_idPais = ?");
			pstmnt.setString(1, String.valueOf(pais));
			ResultSet rs = pstmnt.executeQuery();
			JSONArray jdatosInteres = new JSONArray();
			JSONObject jdatoInteres;
			while(rs.next()){
				jdatoInteres = new JSONObject();
				jdatoInteres.put("idDatoInteres", rs.getInt("idDatoInteres"));
				jdatoInteres.put("DatoInteres", rs.getString("DatoInteres"));
				jdatoInteres.put("Pais_idPais", pais);
				jdatosInteres.put(jdatoInteres);
			}
			json.put("DatosInteres", jdatosInteres);
			json.put("content", true);
			json.put("response", "Hecho.");
		}catch(SQLException | JSONException e){
			e.printStackTrace();
		}
		closeDBConnection();
		return json;
	} 	
	
	public JSONObject obtenerPais(double latitud, double longitud) {
		JSONObject json = new JSONObject();
		try {
			Connection con = dbCon.getConnection();
			PreparedStatement pstmnt = con.prepareStatement("select * from Pais where mbrcontains(g, st_geomfromtext('Point("+latitud+" "+longitud+")'))");
//			pstmnt.setDouble(1, latitud);
//			pstmnt.setDouble(2, longitud);
			ResultSet rs = pstmnt.executeQuery();
			if(rs.next()) {
				JSONObject jobj = new JSONObject();
				jobj.put("idPais", Integer.parseInt(rs.getString("idPais")));
				jobj.put("pais", rs.getString("Origen"));
				json.put("Pais", jobj);
				json.put("content", true);
				json.put("response", "Hecho.");
			}else {
				json.put("content", false);
				json.put("response", "Desconocido.");
			}
		}catch(SQLException | JSONException e){
			e.printStackTrace();			
		}
		closeDBConnection();
		return json;
	}
	
	public void closeDBConnection(){
		dbCon.closeConnection();
	}
	
	private String makeStringFromBytes(byte[] byteData) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
			}
		return sb.toString();
	}

	public JSONObject obtenerModismos(int pais) {
		JSONObject json = new JSONObject();
		try {
			Connection con = dbCon.getConnection();
			PreparedStatement pstmnt = con.prepareStatement("select * from Modismo where Pais_idPais = ?");
			pstmnt.setInt(1, pais);
			ResultSet rs = pstmnt.executeQuery();
			JSONArray jModismos = new JSONArray();
			JSONObject jModismo;
			while(rs.next()) {
				jModismo = new JSONObject();
				jModismo.put("idModismo", rs.getInt("idModismo"));
				jModismo.put("Expresion", rs.getString("Expresion"));
				jModismo.put("idPais", pais);
				jModismos.put(jModismo);
			}
			json.put("Modismos", jModismos);
			json.put("content", true);
			json.put("response", "Hecho.");
		}catch(SQLException | JSONException e) {
			e.printStackTrace();
		}
		closeDBConnection();
		return json;
	}
	
	public JSONObject obtenerInformacionModismo(String expresion, int pais) {
		JSONObject json = new JSONObject();
		try {
			Connection con = dbCon.getConnection();
			PreparedStatement pstmnt = con.prepareStatement("select idModismo, idSignificado, Significado, idEjemplo, Ejemplo, idSimilar, Similar from Ejemplo aa inner join Similar bb on aa.Modismo_idModismo = bb.Modismo_idModismo inner join Significado cc on aa.Modismo_idModismo = cc.Modismo_idModismo inner join Modismo dd on aa.Modismo_idModismo = dd.idModismo where dd.Expresion = ? and dd.Pais_idPais = ?");
			pstmnt.setString(1, expresion);
			pstmnt.setInt(2, pais);
			ResultSet rs = pstmnt.executeQuery();
			JSONObject j;
			if(rs.next()) {
				j = new JSONObject();
				j.put("idSignificado", rs.getInt("idSignificado"));
				j.put("Significado", rs.getString("Significado"));
				j.put("idModismo", rs.getInt("idModismo"));
				json.put("Significado",j);
				j = new JSONObject();
				j.put("idEjemplo", rs.getInt("idEjemplo"));
				j.put("Ejemplo", rs.getString("Ejemplo"));
				j.put("idModismo", rs.getInt("idModismo"));
				json.put("Ejemplo",j);
				j = new JSONObject();
				j.put("idSimilar", rs.getInt("idSimilar"));
				j.put("Similar", rs.getString("Similar"));
				j.put("idModismo", rs.getInt("idModismo"));
				json.put("Similar", j);
			}
			json.put("content", true);
			json.put("response", "Hecho.");
		}catch(SQLException | JSONException e) {
			e.printStackTrace();
		}
		closeDBConnection();
		return json;
	}
	
	public JSONObject obtenerModismoRelacion(int idModismo) {
		JSONObject json = new JSONObject();
		try {
				Connection con = dbCon.getConnection();
				PreparedStatement pstmnt = con.prepareStatement("select * from Modismo_Similar where idModismo_2 = ?");
				pstmnt.setInt(1, idModismo);
				ResultSet rs = pstmnt.executeQuery();
				JSONArray jrelaciones = new JSONArray();
				JSONObject jModismoRelacion;
				while(rs.next()) {
					jModismoRelacion = new JSONObject();
					jModismoRelacion.put("idModismo_1", rs.getInt("idModismo_1"));
					jModismoRelacion.put("idModismo_2", idModismo);
					jrelaciones.put(jModismoRelacion);
				}
				json.put("Modismos_Similares", jrelaciones);
				json.put("content", true);
				json.put("response", "Hecho.");
		}catch(SQLException | JSONException e) {
			e.printStackTrace();
		}
		closeDBConnection();
		return json;
	}
}

