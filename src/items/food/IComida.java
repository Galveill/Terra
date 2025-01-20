package items.food;

import items.IObjeto;

/**
 * El elemento es un alimento del sistema.
 * @author Adrián.
 */
public interface IComida extends IObjeto {

	/**
	 * @return Los subtipos de comida.
	 */
	public EComidaSubTipo[] getSubTypes();

	@Override
	public EComidaTipo getType();
}
