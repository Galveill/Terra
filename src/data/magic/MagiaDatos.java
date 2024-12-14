package data.magic;

/**
 * Datos y métodos referentes a la magia.
 * @author Adrián.
 */
public class MagiaDatos {

	/**
	 * Indica el elemento que le hace más daño al elemento dado.
	 * @param elem El elemento a consultar.
	 * @return El elemento al que es débil.
	 */
	public static EElemento getWeakElement(EElemento elem)
	{
		switch (elem) {
			case AGUA:
				return EElemento.FUEGO;
			case FUEGO:
				return EElemento.AGUA;
			case TIERRA:
				return EElemento.VIENTO;
			case VIENTO:
				return EElemento.TIERRA;
			case LUZ:
				return EElemento.OSCURIDAD;
			case OSCURIDAD:
				return EElemento.LUZ;
			case ETER:
				return EElemento.ETER;
			case MAGMA:
				return EElemento.HIELO;
			case HIELO:
				return EElemento.ELECTRICIDAD;
			case ELECTRICIDAD:
				return EElemento.NATURA;
			case NATURA:
				return EElemento.MAGMA;
		}
		return null;
	}

	/**
	 * Indica si los elementos son afines.
	 * @param elemA Primer elemento.
	 * @param elemB Segundo elemento.
	 * @return True si son afines, false en caso contrario.
	 */
	public static boolean related(EElemento elemA, EElemento elemB)
	{
		switch (elemA) {
			case MAGMA:
				return elemB == EElemento.TIERRA || elemB == EElemento.FUEGO;
			case NATURA:
				return elemB == EElemento.TIERRA || elemB == EElemento.AGUA;
			case HIELO:
				return elemB == EElemento.AGUA || elemB == EElemento.VIENTO;
			case ELECTRICIDAD:
				return elemB == EElemento.FUEGO || elemB == EElemento.VIENTO;
			case TIERRA:
				return elemB == EElemento.MAGMA || elemB == EElemento.NATURA;
			case FUEGO:
				return elemB == EElemento.MAGMA || elemB == EElemento.ELECTRICIDAD;
			case VIENTO:
				return elemB == EElemento.HIELO || elemB == EElemento.ELECTRICIDAD;
			case AGUA:
				return elemB == EElemento.HIELO || elemB == EElemento.NATURA;
			default:
				return false;
		}
	}
}
