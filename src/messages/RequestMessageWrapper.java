package messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import zeraf.solicitudes.IZerafParams;

/**
 * Envoltorio para enviar una petición al servidor en formato JSON.
 * @author Adrián
 */
public class RequestMessageWrapper<T> implements IMessageWrapper {
	/** El código del usuario. */
	protected final String uid;
	/** El código del grupo. */
	protected final String group;
	/** La acción a realizar. */
	protected IZerafParams action;
	/** La información necesaria. */
	protected final T extra;

	/**
	 * Constructor parametrizado.
	 * @param uid El código del usuario.
	 * @param group El código del grupo.
	 * @param action La petición a realizar.
	 * @param extra Los datos a facilitar. String vacío si no se requieren.
	 */
	public RequestMessageWrapper(String uid, String group, IZerafParams action, T extra)
	{
		this.uid = uid;
		this.group = group;
		this.action = action;
		this.extra = extra;
	}

	@Override
	public String getJSON()
	{
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}
}
