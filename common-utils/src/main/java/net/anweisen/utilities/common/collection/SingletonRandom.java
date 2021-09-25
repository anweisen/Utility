package net.anweisen.utilities.common.collection;

import java.util.Random;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class SingletonRandom extends RandomWrapper {

	public static final SingletonRandom INSTANCE = new SingletonRandom();

	private SingletonRandom() {
		super(new Random());
	}

}
