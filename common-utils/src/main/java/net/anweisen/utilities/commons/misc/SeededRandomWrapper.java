package net.anweisen.utilities.commons.misc;

import java.util.Random;

/**
 * Since there is no way of getting the seed of a {@link Random} we create a wrapper
 * which will save seed. This allows us to save randomization and reload it.
 *
 * @author anweisen | https://github.com/anweisen
 * @since 2.0
 */
public class SeededRandomWrapper extends Random {

	protected long seed;

	public SeededRandomWrapper() {
		super();
	}

	public SeededRandomWrapper(long seed) {
		super(seed);
	}

	@Override
	public void setSeed(long seed) {
		super.setSeed(seed);
		this.seed = seed;
	}

	public long getSeed() {
		return seed;
	}

}
