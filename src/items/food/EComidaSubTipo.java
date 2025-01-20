package items.food;

/**
 * Subtipos de comida.
 * @author Adrián.
 */
public enum EComidaSubTipo {
	VEGETAL(null, "vegetal"),
		FRUTA(VEGETAL, "fruta"),
			CITRICO(FRUTA, "citrico"),
			TROPICAL(FRUTA, "tropical"),
			BOSQUE(FRUTA, "bosque"),
			SECO(FRUTA, "seco"),
			UVA(FRUTA, "uva"),
			OLIVA(FRUTA, "oliva"),
		VERDURA(VEGETAL, "verdura"),
			BULBO(VERDURA, "bulbo"),
			TUBERCULO(VERDURA, "tuberculo"),
			HOJA(VERDURA, "hoja"),
		LEGUMBRE(VEGETAL, "legumbre"),
		CEREAL(VEGETAL, "cereal"),
		ALGA(VEGETAL, "alga"),
		HONGO(VEGETAL, "hongo"),
	ANIMAL(null, "animal"),
		CARNE(ANIMAL, "carne"),
			VACUNA(CARNE, "vacuna"),
			OVINA(CARNE, "ovina"),
			CAPRINA(CARNE, "caprina"),
			PORCINA(CARNE, "porcina"),
			AVIAR(CARNE, "aviar"),
			VENADO(CARNE, "venado"),
		PESCADO(ANIMAL, "pescado"),
		MARISCO(ANIMAL, "marisco"),
			CRUSTACEO(MARISCO, "crustaceo"),
			MOLUSCO(MARISCO, "molusco"),
		PRODUCTO(ANIMAL, "producto"),
			LECHE(PRODUCTO, "leche"),
			HUEVO(PRODUCTO, "huevo"),
			MIEL(PRODUCTO, "miel"),
	PRODUCIDO(null, "producido"),
		QUESO(PRODUCIDO, "queso"),
		MERMELADA(PRODUCIDO, "mermelada"),
		ZUMO(PRODUCIDO, "zumo"),
		CONSERVADO(PRODUCIDO, "conservado");

	/** El tipo de nivel superior. */
	private EComidaSubTipo parent;
	/** El texto que contiene. */
	private String txt;

	/**
	 * Constructor para darle el valor.
	 * @param txt El texto que contiene.
	 */
	private EComidaSubTipo(EComidaSubTipo parent, String txt)
	{
		this.parent = parent;
		this.txt = txt;
	}

	@Override
	public String toString()
	{
		return this.txt;
	}

	//TODO Métodos obtener si es de la jerarquía.
}
