package items;

import data.quantity.ERareza;
import repositories.IRegistrable;

/**
 * El elemento es un objeto del sistema.
 * @author Adrián.
 */
public interface IObjeto extends IRegistrable {

	/**
	 * @return El nombre del objeto.
	 */
	public String getName();

	/**
	 * @return La descripción del objeto.
	 */
	public String getDesc();

	/**
	 * @return La rareza del objeto.
	 */
	public ERareza getRarity();

	/**
	 * @return El tipo de objeto de su propia jerarquía.
	 */
	public IObjetoTipo getType();
}
