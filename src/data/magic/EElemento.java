package data.magic;

/**
 * Conjunto de elementos mágicos.
 * @author Adrián.
 */
public enum EElemento {
	TIERRA("Tierra"),
	AGUA("Agua"),
	VIENTO("Viento"),
	FUEGO("Fuego"),
	ETER("Éter"),

	LUZ("Luz"),
	OSCURIDAD("Oscuridad"),

	NATURA("Natura"),
	ELECTRICIDAD("Electricidad"),
	HIELO("Hielo"),
	MAGMA("Magma");

	/** El texto representativo */
	private String txt;

	/**
	 * Constructor básico para darle valor.
	 * @param txt El texto representativo.
	 */
	private EElemento(String txt)
	{
		this.txt = txt;
	}

	public String toString()
	{
		return this.txt;
	}
}