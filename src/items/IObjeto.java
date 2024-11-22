package items;

/**
 * El elemento es un objeto del sistema.
 * @author Adrián.
 */
public interface IObjeto<T> {

	/**
	 * @return El código del objeto.
	 */
	public String getCode();

	/**
	 * @return El nombre del objeto.
	 */
	public String getName();

	/**
	 * @return La descripción del objeto.
	 */
	public String getDesc();

	/**
	 * @return El tipo de objeto de su propia jerarquía.
	 */
	public T getType();
}
