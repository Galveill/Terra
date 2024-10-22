package messages;

/**
 * Base de los envoltorios de mensajes JSON.
 */
public interface IMessageWrapper {
	
	/**
	 * @return La representación en JSON del mensaje.
	 */
	public String getJSON();
}
