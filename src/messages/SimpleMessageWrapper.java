package messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Envoltorio para enviar al servidor arrays de datos en formato JSON.
 * @author Adrián
 */
public class SimpleMessageWrapper extends MessageWrapper{
	/** La información necesaria. */
	private final String[] data;

	/**
	 * Constructor parametrizado.
	 * @param uid El código del usuario.
	 * @param group El código del grupo.
	 * @param data Los códigos de los tesoros.
	 */
	public SimpleMessageWrapper(String uid, String group, String[] data)
	{
		super(uid, group);
		this.data = data;
	}

	@Override
	public String getJSON()
	{
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}
}
