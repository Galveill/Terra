package zeraf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;

import messages.EMsgCodes;
import messages.MessageWrapper;

/**
 * Comunicación con los servidores de los sistemas.
 * @author Adrián.
 */
public class Zeraf {
	
	/** El código de usuario. */
	protected String uid;
	/** El código de grupo. */
	protected final String group;
	/** La dirección de servidor */
	protected final String url;

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
	 * @param data Los datos a enviar.
	 * @return El código de respuesta del servidor.
	 */
	public <T> EMsgCodes sendData(T data)
	{
		String json = new MessageWrapper<T>(this.uid, this.group, data).getJSON();
		return this.sendData(json);
	}

	/**
	 * Envía los datos al servidor.
	 * @param data Los datos a enviar.
	 * @return El código de respuesta del servidor.
	 */
	protected EMsgCodes sendData(String data)
	{
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
			dos.writeBytes(data);
			dos.flush();

			// Leer la respuesta
			int responseCode = con.getResponseCode();
			if(responseCode == 200)
			{
				in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
				String inputLine;

				int rescode = Integer.parseInt(in.readLine().toString());
				msgret = EMsgCodes.values()[rescode];
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
		if(msgret == EMsgCodes.ERROR_CONNECTION)
		{
			System.out.println("No se han podido enviar los datos, se almacenan en el sistema de backup.");
		}

		return msgret;
	}

	/**
	 * Guarda en un fichero JSON los datos que se enviarían a un servidor.
	 * Método de salvaguarda en caso de que haya un fallo de conexión.
	 * No deben guardarse datos incorrectos.
	 * @param data Los datos a guardar.
	 */
	public <T> void backupData(T data)
	{
		String json = new MessageWrapper<T>(this.uid, this.group, data).getJSON();

		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
			new FileOutputStream(ZerafFactory.BACKUP_PATH + "/" + this.group + "_" + this.uid + ".bkp"), "UTF-8")
		)) {
			String b64 = Base64.getEncoder().encodeToString(json.getBytes());
			bw.write(b64);
			bw.flush();
		} catch (IOException e) {
			System.err.println("Error al escribir el fichero de backap de los datos.");
			e.printStackTrace();
		}
	}

	/**
	 * Recupera los datos del backup e intenta enviarlos al servidor.
	 */
	public void recoverData()
	{
		String json = "";
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
			new FileInputStream(ZerafFactory.BACKUP_PATH + "/" + this.group + "_" + this.uid + ".bkp"), "UTF-8")
		)) {
			StringBuilder data = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				data.append(line);
			}

			byte[] deco = Base64.getDecoder().decode(data.toString());
			json = new String(deco);
			
		} catch (IOException e) {
			System.err.println("Error al escribir el fichero de backap de los datos.");
			e.printStackTrace();
		}

		this.sendData(json);
	}

	@Override
	public String toString()
	{
		return "Grupo: " + this.group + ", UID: " + this.uid + ", Servidor: " + this.url;
	}
}
