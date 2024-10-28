package zeraf.solicitudes;

/**
 * Parámetros posibles de solicitud de datos del servidor Atlas de Ravaura.
 * @author Adrián.
 */
public enum EAtlasParams implements IZerafParams {
	RUTA("Ruta"),
	TERRENO("Terreno");
	//TODO Completar con lo necesario.

	/** El texto **/
	private String value;
	
	/**
		Constructor básico
		@param msg El texto.
	*/
	private EAtlasParams(String msg)
	{
		this.value = msg;
	}

	@Override
	public String getParam() {
		return value;
	}
	
}
