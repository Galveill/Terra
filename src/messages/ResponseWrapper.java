package messages;

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
	 * Constructor directo.
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
