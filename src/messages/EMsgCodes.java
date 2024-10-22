package messages;

/**
 * Códigos de respuesta del servidor al que Zeraf se conecta.
 * @author Adrián
 */
public enum EMsgCodes {
	/** Error de conexión con el servidor. **/
	ERROR_CONNECTION("Error de conexión con el servidor."),
	/** Todo correcto **/
	CORRECT("Datos enviados correctamente."),
	/** Código de usuario incorrecto **/
	ERROR_USER("Código de usuario incorrecto."),
	/** Código de grupo incorrecto **/
	ERROR_GROUP("Código de grupo incorrecto."),
	/** Datos incorrectos **/
	ERROR_DATA("Datos incorrectos."),
	/** Modo pruebas desactivado **/
	NO_TEST("Datos recibidos, pero el modo de pruebas está deshabilitado y serán ignorados."),
	/** Usuario bloqueado **/
	BLOCKED_USER("Usuario bloqueado.");

	/** El texto **/
	private String value;
	
	/**
		Constructor básico
		@param msg El texto.
	*/
	private EMsgCodes(String msg)
	{
		this.value = msg;
	}

	/**
	 * @return El texto
	 */
	public String getMessage() {
		return value;
	}

	@Override
	public String toString()
	{
		return this.value;
	}
}
