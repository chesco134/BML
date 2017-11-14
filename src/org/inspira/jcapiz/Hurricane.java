package org.inspira.jcapiz;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.inspira.jcapiz.db.DBHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class Hurricane
 */
public class Hurricane extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Hurricane() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("["+ Misc.dateFormat() +"] Llegó un cliente... ("+ request.getRemoteHost() +")");
		JSONObject json = new JSONObject();
		try{
			JSONArray jarr = new JSONArray();
			JSONObject jobj = new JSONObject();
			jobj.put("idPais", 1);
			jobj.put("pais", "México");
			jarr.put(jobj);
			jobj = new JSONObject();
			jobj.put("idPais", 2);
			jobj.put("pais", "Vnezuela");
			jarr.put(jobj);
			json.put("Paises", jarr);
		}catch(JSONException e){
			e.printStackTrace();
		}
		DataOutputStream salida = new DataOutputStream(response.getOutputStream());
		salida.write(json.toString().getBytes());
//		PrintWriter pw = new PrintWriter(response.getWriter());
//		pw.print(json.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("["+ Misc.dateFormat() +"] Llegó un cliente... ("+ request.getRemoteHost() +")");
		DataInputStream entrada = new DataInputStream(request.getInputStream());
		byte[] chunk = new byte[254];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int length;
		while((length = entrada.read(chunk)) != -1)
			baos.write(chunk,0,length);
		String content = baos.toString("UTF-8");
		System.out.println("Dijo: " + content);
		baos.close();
		try{
			JSONObject json = new JSONObject(content);
			switch(json.getInt("action")){
			case 1:
				json = new DBHandler().obtenerPaises();
				break;
			case 2:
				json = new DBHandler().obtenerDatoInteres(json.getInt("idPais"));
				break;
			case 3:
				json = new DBHandler().obtenerPais(json.getDouble("latitud"), json.getDouble("longitud"));
				break;
			case 4:
				json = new DBHandler().obtenerModismos(json.getInt("idPais"));
				break;
			case 5:
				json = new DBHandler().obtenerInformacionModismo(json.getString("Expresion"), json.getInt("pais"));
				break;
			case 6:
				json = new DBHandler().obtenerModismoRelacion(json.getInt("idModismo"));
				break;
			default:
				json.put("content", false);
				json.put("response", "none");
			}
			json.put("content", true);
			DataOutputStream salida = new DataOutputStream(response.getOutputStream());
			salida.write(json.toString().getBytes("UTF-8"));
			salida.flush();
		}catch(JSONException e){
			e.printStackTrace();
		}
		System.out.println("Done");
	}

}
