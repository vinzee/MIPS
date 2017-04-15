package FunctionalUnits;

public class FunctionalUnit {
	private boolean busy = false;
	private int latency = 0;

	public FunctionalUnit(int latency) {
		super();
		this.latency = latency;
	}

	public boolean isBusy() {
		return busy;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}
}
