package Instructions;

public class J extends Instruction implements Instructable{
	String label;
	
	public J(String label) {
		super();
		this.label = label;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub		
//		throw new Error("J shouldn't have execute stage");
	}
}