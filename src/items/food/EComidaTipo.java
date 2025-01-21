package items.food;

import items.IObjetoTipo;

/**
 * Tipos de comida.
 * @author Adrián.
 */
public enum EComidaTipo implements IObjetoTipo {
	CRUDO("crudo"),
	PROCESADO("procesado"),
	COCINADO("cocinado");
	

	/** El texto que contiene. */
	private String txt;

	/**
	 * Constructor para darle el valor.
	 * @param txt El texto que contiene.
	 */
	private EComidaTipo(String txt)
	{
		this.txt = txt;
	}

	@Override
	public String toString()
	{
		return this.txt;
	}
}
