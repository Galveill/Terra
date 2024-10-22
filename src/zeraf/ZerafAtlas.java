package zeraf;

/**
 * Comunicación con el servidor Atlas de Ravaura.
 * @author Adrián.
 */
public class ZerafAtlas extends Zeraf {

	/**
	 * Constructor parametrizado.
	 * @param uid El id del usuario.
	 * @param group El id del grupo.
	 * @param url La dirección del servidor.
	 */
	ZerafAtlas(String uid, String group, String url)
	{
		super(uid, group, url);
	}
	
}
