package enums;

/**
 * El nivel de amenaza de una zona. Determina el número de enemigos que hay.
 * @author Adrián.
 */
public enum EAmenaza {
	PACIFICO(0),
	BAJO(1),
	MEDIO(2),
	DIFICIL(3),
	AVANZADO(4);

	/** El nivel representativo */
	private int lvl;

	/**
	 * Constructor básico para darle valor.
	 * @param lvl El nivel representativo.
	 */
	private EAmenaza(int lvl)
	{
		this.lvl = lvl;
	}

	/**
	 * @return El nivel de amenaza en valor numérico.
	 */
	public int getLvl()
	{
		return this.lvl;
	}

	public String toString()
	{
		return this.lvl + "";
	}
}
