package zeraf;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Comunicación con los servidores de los sistemas.
 * @author Adrián.
 */
public class Zeraf {

	/** El sistema al que conectar. */
	private final String system;
	/** El código de usuario. */
	private final String uid;
	/** El código de grupo. */
	private final String group;
	/** La dirección de servidor */
	private final String url;

	/**
	 * Constructor parametrizado.
	 * @param system El sistema con el que conectar.
	 * @param uid El id del usuario.
	 * @param group El id del grupo.
	 * @param url La dirección del servidor.
	 */
	Zeraf(String system, String uid, String group, String url)
	{
		this.system = system;
		this.uid = uid;
		this.group = group;
		this.url = url;
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
	
	//TODO Si la conexión falla enviando datos, sistema de backup mediante fichero.
	//TODO El fichero es json con un campo en base64 o similar para verificar la integridad de los datos.

	//TODO toString.
}
