package cz.laryngektomie.benchmark.helper;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {

	private final AtomicLong counter;

	public IdGenerator(long startId) {
		this.counter = new AtomicLong(startId);
	}

	public long getId() {
		return counter.incrementAndGet();
	}
}
