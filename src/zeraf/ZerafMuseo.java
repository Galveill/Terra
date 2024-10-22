package zeraf;

/**
 * Comunicación con el servidor Museo de Archaios.
 * @author Adrián.
 */
public class ZerafMuseo extends Zeraf {

	/**
	 * Constructor parametrizado.
	 * @param uid El id del usuario.
	 * @param group El id del grupo.
	 * @param url La dirección del servidor.
	 */
	ZerafMuseo(String uid, String group, String url)
	{
		super(uid, group, url);
	}
	
}
