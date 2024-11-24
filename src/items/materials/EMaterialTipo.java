package items.materials;

import items.IObjetoTipo;

/**
 * Tipos de materiales.
 * @author Adrián.
 */
public enum EMaterialTipo implements IObjetoTipo {
	MINERAL("mineral"),
	LINGOTE("lingote"),
	GEMA("gema"),

	MADERA("madera"),
	RESINA("resina"),

	PIEDRA("piedra"),
	BLOQUE("bloque"),

	ARCILLA("arcilla"),
	CERAMICA("cerámica"),

	PIEL("piel"),
	CUERO("cuero"),
	PLUMA("pluma"),

	CERA("cera"),

	CORAL("coral"),
	PERLA("perla"),
	NACAR("nácar");

	/** El texto que contiene. */
	private String txt;

	/**
	 * Constructor para darle el valor.
	 * @param txt El texto que contiene.
	 */
	private EMaterialTipo(String txt)
	{
		this.txt = txt;
	}

	@Override
	public String toString()
	{
		return this.txt;
	}
}
