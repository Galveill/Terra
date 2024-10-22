package zeraf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

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
	/** La ruta de la carpeta de backups. */
	static final String BACKUP_PATH = "backup/";

	/**
	 * Crea un objeto Zeraf, así como el archivo de configuración necesario para su funcionamiento solicitando los datos requeridos para ello.
	 * @param system El sistema con el que conectar.
	 * @param uid El id del usuario.
	 * @param group El id del grupo. Prevalece el del archivo de configuración.
	 * 
	 * @return Una instancia de <code>Zeraf</code>.
	 */
	public static Zeraf createZeraf(ESistema system, String uid, String group)
	{
		ZerafFactory.createFile(ZerafFactory.CONFIG_PATH);
		ZerafFactory.createFile(ZerafFactory.BACKUP_PATH + "/" + group + "_" + uid + ".bkp");

		String[] conf = ZerafFactory.readConfig();
		if(conf == null)
		{
			conf = new String[]{"", group};
			boolean corr = false;
			Scanner sc = new Scanner(System.in);
			while(!corr) {
				System.out.println("Introduce la URL completa al servidor, incluyendo el protocolo y el puerto (http://localhost:8080):");
				String txt = sc.nextLine();
				try {
					URL murl = new URI(txt).toURL();
					ZerafFactory.writeConfig(murl.toString(), group);
					conf[0] = murl.toString();
					corr = true;
				} catch (IllegalArgumentException | MalformedURLException | URISyntaxException e) {
					System.out.println("Dirección incorrecta.");
				}
			}
		}

		return new Zeraf(uid, conf[1], conf[0]);
	}
	
	/**
	 * Escribe en el archivo de configuración de Zeraf.
	 * @param url La url del servidor.
	 * @param group El id del grupo.
	 */
	private static void writeConfig(String url, String group)
	{
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
			new FileOutputStream(ZerafFactory.CONFIG_PATH), "UTF-8")
		)) {
			String json = "{" + 
				"\"URL\": \"" + url + "\", " +
				"\"Group\": \"" + group + "\"" +
				"}";
			bw.write(json);
			bw.flush();
		} catch (IOException e) {
			System.err.println("Error al escribir el fichero de configuración de Zeraf.");
			e.printStackTrace();
		}
	}

	/**
	 * @return La URL y el grupo o null si hay cualquier problema.
	 */
	private static String[] readConfig()
	{
		JsonObject conf = null;
		try (JsonReader reader = new JsonReader(
			new BufferedReader(
				new InputStreamReader(
					new FileInputStream(ZerafFactory.CONFIG_PATH), "UTF-8"
				)
			)
		)) {
			conf = JsonParser.parseReader(reader).getAsJsonObject();

			return new String[]{new URI(conf.get("URL").getAsString()).toURL().toString(), conf.get("Group").getAsString()};
		} catch (NullPointerException | URISyntaxException | IllegalStateException | IOException e) {
			System.out.println("Archivo de configuración vacío o mal formado, se procede a su creación.");
		}

		return null;
	}

	/**
	 * Crea un fichero si no existe y su ruta completa.
	 * @param path La ruta completa del fichero a crear.
	 */
	private static void createFile(String path)
	{
		File f = new File(path);
		if(!f.exists())
		{
			File dir = new File(f.getParentFile().getPath());
			if(!dir.exists())
			{
				dir.mkdirs();
			}
			try {
				f.createNewFile();
			} catch (IOException e) {
				System.err.println("Error al crear el fichero " + path + " de la librería Terra.");
				e.printStackTrace();
			}
		}
	}
}
