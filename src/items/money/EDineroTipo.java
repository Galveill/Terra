package items.money;

import items.IObjetoTipo;

/**
 * Tipos de muebles.
 * @author Adrián.
 */
public enum EDineroTipo implements IObjetoTipo {
	MONEDA("moneda");

	/** El texto que contiene. */
	private String txt;

	/**
	 * Constructor para darle el valor.
	 * @param txt El texto que contiene.
	 */
	private EDineroTipo(String txt)
	{
		this.txt = txt;
	}

	@Override
	public String toString()
	{
		return this.txt;
	}
}
