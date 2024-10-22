package messages;

/**
 * Envoltorio para enviar datos al servidor en formato JSON.
 * @author Adrián
 */
public abstract class MessageWrapper implements IMessageWrapper {
	/** El código del usuario. */
	protected final String uid;
	/** El código del grupo. */
	protected final String group;

	/**
	 * Constructor parametrizado.
	 * @param uid El código del usuario.
	 * @param group El código del grupo.
	 */
	public MessageWrapper(String uid, String group)
	{
		this.uid = uid;
		this.group = group;
	}

	@Override
	public abstract String getJSON();
}
