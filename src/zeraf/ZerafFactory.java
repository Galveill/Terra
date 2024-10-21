package zeraf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

/**
 * Factoría de Zeraf.
 * @author Adrián.
 */
public class ZerafFactory {

	/** La ruta del fichero de configuración. */
	private static final String CONFIG_PATH = "config/Zeraf.conf";

	/**
	 * Crea un objeto Zeraf, así como el archivo de configuración necesario para su funcionamiento solicitando los datos requeridos para ello.
	 * @param system El sistema con el que conectar.
	 * @param uid El id del usuario.
	 * @param group El id del grupo.
	 * 
	 * @return Una instancia de <code>Zeraf</code>.
	 */
	public static Zeraf createZeraf(ESistema system, String uid, String group)
	{
		//TODO Sacada de Hermes.
		//TODO Ha de solicitar el sistema al que conectar.
		//TODO Solicitar códigos de usuario y grupo.

		return new Zeraf(system, uid, group, CONFIG_PATH); //TODO Completar.
	}
	
	/**
	 * Escribe en el archivo de configuración de Zeraf.
	 * @param url La url del servidor.
	 */
	private static void writeConfig(URL url)
	{
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
			new FileOutputStream(ZerafFactory.CONFIG_PATH), "UTF-8")
		)) {
			String json = "{\"URL\": \"" + url.toString() + "\"}";
			bw.write(json);
			bw.flush();
		} catch (IOException e) {
			System.err.println("Error al escribir el fichero de configuración de Zeraf.");
			e.printStackTrace();
		}
	}

	/**
	 * @return La URL al museo o null si hay cualquier problema.
	 */
	private static URL readConfig()
	{
		JsonObject cObj = null;
		try (JsonReader reader = new JsonReader(
			new BufferedReader(
				new InputStreamReader(
					new FileInputStream(ZerafFactory.CONFIG_PATH), "UTF-8"
				)
			)
		)) {
			cObj = JsonParser.parseReader(reader).getAsJsonObject();
			return new URI(cObj.get("URL").getAsString()).toURL();
		} catch (NullPointerException | URISyntaxException | IllegalStateException | IOException e) {
			System.out.println("Archivo de configuración vacío o mal formado, se procede a su creación.");
		}

		return null;
	}
}
