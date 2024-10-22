package zeraf;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import messages.EMsgCodes;
import messages.MessageWrapper;

/**
 * Comunicación con los servidores de los sistemas.
 * @author Adrián.
 */
public abstract class Zeraf {
	
	/** El código de usuario. */
	private String uid;
	/** El código de grupo. */
	private final String group;
	/** La dirección de servidor */
	private final String url;

	/**
	 * Constructor parametrizado.
	 * @param uid El id del usuario.
	 * @param group El id del grupo.
	 * @param url La dirección del servidor.
	 */
	Zeraf(String uid, String group, String url)
	{
		this.uid = uid;
		this.group = group;
		this.url = url;
	}

	/**
	 * Cambia el usuario.
	 * @param uid El código del usuario.
	 */
	public void setUser(String uid)
	{
		this.uid = uid;
	}

	/**
	 * Indica si el servidor está operativo.
	 * @return true si el servidor es accesible, false en caso contrario.
	 */
	public boolean isOnline()
	{
		HttpURLConnection con = null;
		URL obj;
		DataOutputStream dos = null;
		try {
			obj = new URI(this.url).toURL();
			con = (HttpURLConnection) obj.openConnection();
			con.setDoOutput(true);
			dos = new DataOutputStream(con.getOutputStream());
			return true;
		} catch (IOException | URISyntaxException e) {
			
		}finally{
			if(dos != null) {
				try {
					dos.close();
				} catch (IOException e) {}
			}
			if (con != null) {
				con.disconnect();
			}
		}
		
		return false; 
	}

	/**
	 * Envía los datos al servidor.
	 * @param data Los datos a enviar en formato JSON.
	 * @return El código de respuesta del servidor.
	 */
	public <T> EMsgCodes sendData(T data)
	{
		String json = new MessageWrapper<T>(this.uid, this.group, data).getJSON();
		//TODO Si la conexión falla enviando datos, sistema de backup mediante fichero.
		//TODO El fichero es json con un campo en base64 o similar para verificar la integridad de los datos.
		EMsgCodes msgret = EMsgCodes.ERROR_CONNECTION;

		HttpURLConnection con = null;
		DataOutputStream dos = null;
		BufferedReader in = null;
		try {
			URL obj = new URI(this.url + "/receiver.php").toURL();
			con = (HttpURLConnection) obj.openConnection();

			// Configurar la conexión
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoOutput(true);

			// Enviar los datos
			dos = new DataOutputStream(con.getOutputStream());
			dos.writeBytes(json);
			dos.flush();

			// Leer la respuesta
			int responseCode = con.getResponseCode();
			if(responseCode == 200)
			{
				in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
				String inputLine;

				int rescode = Integer.parseInt(in.readLine().toString());
				msgret = EMsgCodes.values()[rescode];
				//Más texto
				while ((inputLine = in.readLine()) != null) {
					System.err.println(inputLine);
				}
			}

		} catch (Exception e) {
			msgret = EMsgCodes.ERROR_CONNECTION;
		} finally {
			if(dos != null) {
				try {
					dos.close();
				} catch (IOException e) {}
			}
			if (con != null) {
				con.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {}
			}
		}

		return msgret;
	}

	//TODO toString.
}
