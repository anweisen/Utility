package net.anweisen.utility.database;

import net.anweisen.utility.database.action.hierarchy.OrderedAction;

/**
 * @author anweisen | https://github.com/anweisen
 * @see OrderedAction#order(String, Order)
 * @since 1.0
 */
public enum Order {

	/**
	 * The results will start with the highest value and then descend to the lowest
	 * <p>
	 * For example:
	 *
	 * <ul>
	 *   <li>7</li>
	 *   <li>5</li>
	 *   <li>2</li>
	 *   <li>1</li>
	 * </ul>
	 */
	HIGHEST,

	/**
	 * The results will start with the lowest value and then ascend to the highest
	 * <p>
	 * For example:
	 *
	 * <ul>
	 *   <li>1</li>
	 *   <li>2</li>
	 *   <li>5</li>
	 *   <li>7</li>
	 * </ul>
	 */
	LOWEST

}
