package items.magic;

/**
 * Tipos de magia.
 * @author Adrián.
 */
public enum EMagiaTipo {
	ELEMENTAL("elemental");

	/** El texto que contiene. */
	private String txt;

	/**
	 * Constructor para darle el valor.
	 * @param txt El texto que contiene.
	 */
	private EMagiaTipo(String txt)
	{
		this.txt = txt;
	}

	@Override
	public String toString()
	{
		return this.txt;
	}
}
