package enums;

/**
 * El nivel de abundancia de recursos del elemento.
 * @author Adrián.
 */
public enum EAbundancia {
	NULA("Nula", 0),

	MUY_BAJA("Muy baja", 25),
	BAJA("Baja", 50),
	MEDIA("Media", 75),
	COMUN("Común", 100),
	ALTA("Alta", 150),
	MUY_ALTA("Muy alta", 200),
	EX_ALTA("Extremadamente alta", 250),

	ILIMITADA("Ilimitada", -1);

	/** El texto representativo */
	private String txt;
	/** El valor de cantidad base. */
	private int value;

	/**
	 * Constructor básico para darle valor.
	 * @param txt El texto representativo.
	 * @param val El valor base.
	 */
	private EAbundancia(String txt, int val)
	{
		this.txt = txt;
		this.value = val;
	}

	/**
	 * @return El valor de cantidad base asociado.
	 */
	public int getValue()
	{
		return this.value;
	}

	public String toString()
	{
		return this.txt;
	}
}
