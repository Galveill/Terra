package items.furniture;

/**
 * Tipos de muebles.
 * @author Adrián.
 */
public enum EMuebleTipo {
	ADORNO("adorno"),
	MAQUINARIA("maquinaria"),
	ESTATUA("estatua");

	/** El texto que contiene. */
	private String txt;

	/**
	 * Constructor para darle el valor.
	 * @param txt El texto que contiene.
	 */
	private EMuebleTipo(String txt)
	{
		this.txt = txt;
	}

	@Override
	public String toString()
	{
		return this.txt;
	}
}
