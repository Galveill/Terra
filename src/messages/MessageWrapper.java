package messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Envoltorio para enviar datos al servidor en formato JSON.
 * @author Adrián
 */
public class MessageWrapper<T> implements IMessageWrapper {
	/** El código del usuario. */
	protected final String uid;
	/** El código del grupo. */
	protected final String group;
	/** La información necesaria. */
	protected final T data;

	/**
	 * Constructor parametrizado.
	 * @param uid El código del usuario.
	 * @param group El código del grupo.
	 */
	public MessageWrapper(String uid, String group, T data)
	{
		this.uid = uid;
		this.group = group;
		this.data = data;
	}

	@Override
	public String getJSON()
	{
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}
}
