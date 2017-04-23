package FunctionalUnits;

public abstract class FunctionalUnit {
	private boolean busy = false;
	public int latency = 0;

	public FunctionalUnit(int latency) {
		this.latency = latency;
	}

	public boolean isBusy() {
		return busy;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}
}
