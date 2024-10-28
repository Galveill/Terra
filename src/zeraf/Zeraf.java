package zeraf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import messages.EMsgCodes;
import messages.MessageWrapper;
import zeraf.solicitudes.IZerafParams;

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
	/** La ruta de la carpeta de backups. */
	protected static final String BACKUP_PATH = "backup/";

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
	 * Si hay datos de respaldo almacenados, los intenta enviar antes que los facilitados.
	 * Si hay un fallo de conexión con el servidor, crea un fichero de respaldo con los datos.
	 * 
	 * @param data Los datos a enviar.
	 * @return El código de respuesta del servidor.
	 */
	protected EMsgCodes sendData(String data)
	{
		EMsgCodes msgret = EMsgCodes.ERROR_CONNECTION;
		this.recoverData();

		try {
			String response = this.HTTPRequest(this.url + "/receiver.php", data);

			if(!response.equals(""))
			{
				int rescode = Integer.parseInt(response);
				msgret = EMsgCodes.values()[rescode];
			}
		} catch (Exception e) {
			msgret = EMsgCodes.ERROR_CONNECTION;
		}

		if(msgret == EMsgCodes.ERROR_CONNECTION)
		{
			System.out.println("No se han podido enviar los datos, se almacenan en el sistema de backup.");
			this.backupData(data);
		}

		return msgret;
	}

	/**
	 * Solicita los datos al servidor.
	 * @param <I> El contenedor de los datos a pasar.
	 * @param <R> El objeto contenedor a recibir, el DTO.
	 * @param params Los parámetros necesarios para la solicitud, lo que se quiere pedir. Cada sistema tiene sus posibles.
	 * @param extra Datos extra a facilitar al servidor.
	 * @param container La clase que servirá de contenedor de los datos de solicitud.
	 * @return Una instancia de la clase facilitada con los datos almacenados en su interior. Null si ha ocurrido algún error o no hay datos que solicitar.
	 * @throws JsonSyntaxException  Si la clase facilitada en <code>container</code> no es compatible con la información solicitada en <code>params</code>.
	*/
	public <I, R> R receiveData(IZerafParams params, I extra, Class<R> container) throws JsonSyntaxException
	{
		Gson gson = new GsonBuilder().create();
		
		String data = "{\"" + params.getParam() + "\":" + params + ",\" extra\":" + gson.toJson(extra) + "}";
		R inst = null;
		try {
			String response = this.HTTPRequest(this.url + "/retriever.php", data);

			if(!response.equals(""))
			{
				inst = gson.fromJson(response, container);
			}
		} catch (Exception e) {
			inst = null;
		}

		return inst;
	}

	/**
	 * Envía los datos al servidor devolviendo la respuesta dada.
	 * @param fullUrl La dirección completa al servidor, incluyendo la página concreta.
	 * @param data La información a enviar.
	 * @return El contenido en formato texto o cadena vacía si hay cualquier error.
	 */
	protected String HTTPRequest(String fullUrl, String data)
	{
		String res = "";

		HttpURLConnection con = null;
		DataOutputStream dos = null;
		BufferedReader in = null;
		try {
			URL obj = new URI(fullUrl).toURL();
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
				StringBuilder inputLine = new StringBuilder();
				String line = "";
				while ((line = in.readLine()) != null) {
					inputLine.append(line);
				}
				res = inputLine.toString();
			}

		} catch (Exception e) {
			res = "";
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
		return res;
	}

	/**
	 * Guarda en un fichero JSON los datos que se enviarían a un servidor.
	 * Método de salvaguarda en caso de que haya un fallo de conexión.
	 * No deben guardarse datos incorrectos.
	 * @param json Los datos a guardar en formato JSON.
	 */
	protected void backupData(String json)
	{
		String bkp = Zeraf.BACKUP_PATH + "/" + group + "_" + uid + ".bkp";
		ZerafFactory.createFile(bkp);

		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
			new FileOutputStream(bkp), "UTF-8")
		)) {
			String b64 = Base64.getEncoder().encodeToString(json.getBytes());
			bw.write(b64);
			bw.flush();
		} catch (IOException e) {
			System.err.println("Error al escribir el fichero de respaldo de los datos.");
			e.printStackTrace();
		}
	}

	/**
	 * Recupera los datos del backup e intenta enviarlos al servidor.
	 */
	protected void recoverData()
	{
		File bkp = new File(Zeraf.BACKUP_PATH + "/" + this.group + "_" + this.uid + ".bkp");
		if(bkp.exists())
		{
			String json = "";
			try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(bkp), "UTF-8")
			)) {
				StringBuilder data = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					data.append(line);
				}
	
				byte[] deco = Base64.getDecoder().decode(data.toString());
				json = new String(deco);
				
			} catch (IOException e) {
				System.err.println("Error al recuperar el fichero de respaldo de los datos.");
				e.printStackTrace();
			}
			bkp.delete();
			this.sendData(json);
		}
	}

	@Override
	public String toString()
	{
		return "Grupo: " + this.group + ", UID: " + this.uid + ", Servidor: " + this.url;
	}
}
