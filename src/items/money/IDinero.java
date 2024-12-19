package items.money;

import items.IObjeto;

/**
 * El elemento es dinero del sistema.
 * @author Adrián.
 */
public interface IDinero extends IObjeto {

	@Override
	public EDineroTipo getType();

	/**
	 * @return El valor de la moneda.
	 */
	public int getMonetaryValue();
}
