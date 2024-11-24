package items.equipment;

import items.IObjetoTipo;

/**
 * Tipos de equipo.
 * @author Adrián.
 */
public enum EEquipoTipo implements IObjetoTipo {
	ROPA("ropa"),
	ARMADURA("armadura"),

	HERRAMIENTA("herramienta"),
	ARMA("arma"),
	ACCESORIO("accesorio");


	/** El texto que contiene. */
	private String txt;

	/**
	 * Constructor para darle el valor.
	 * @param txt El texto que contiene.
	 */
	private EEquipoTipo(String txt)
	{
		this.txt = txt;
	}

	@Override
	public String toString()
	{
		return this.txt;
	}
}
