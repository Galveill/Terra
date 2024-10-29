package messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Envoltorio de la respuesta del servidor.
 * @author Adrián.
 */
public class ResponseWrapper<T> {

	/** El código de respuesta del servidor. */
	protected final EMsgCodes code;
	/** El contenido de la respuesta. */
	protected final T content;

	/**
	 * Constructor básico.
	 * @param code El código de respuesta del servidor.
	 * @param content El contenido de la respuesta.
	 */
	public ResponseWrapper(EMsgCodes code, T content)
	{
		this.code = code;
		this.content = content;
	}

	/**
	 * Constructor con código del servidor.
	 * @param code El código de respuesta del servidor.
	 * @param content El contenido de la respuesta.
	 */
	public ResponseWrapper(String code, T content)
	{
		int rescode = Integer.parseInt(code);
		this.code = EMsgCodes.values()[rescode];
		this.content = content;
	}

	/**
	 * Constructor directo de JSON.
	 * @param json La respuesta pura del servidor con los elementos "code" y "data".
	 * @param container La clase del contenido.
	 */
	public ResponseWrapper(String json, Class<T> container)
	{
		Gson gson = new GsonBuilder().create();
		JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
		int rescode = obj.get("code").getAsInt();
		this.code = EMsgCodes.values()[rescode];
		String data = obj.get("data").toString();
		this.content = gson.fromJson(data, container);
	}

	/**
	 * @return El código de respuesta del servidor.
	 */
	public EMsgCodes getCode() {
		return code;
	}

	/**
	 * @return El contenido de la respuesta.
	 */
	public T getContent() {
		return content;
	}

	@Override
	public String toString()
	{
		return "Código: " + this.code + " | Contenido: " + this.content;
	}
}
