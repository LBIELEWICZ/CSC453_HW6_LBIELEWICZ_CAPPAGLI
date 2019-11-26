public class ASTNode {
	
	public enum NodeType {
		OP, NUM, ID, IF, WHILE, ASSG, SLIST, PROG, RELOP, COMP, FUNC, AND, OR, FLIST;
	}
	
	private ASTNode left = null;
	private ASTNode right = null;
	private String val = null;
	private NodeType type;
	private int id;
	private int tID;
	private int fID;
	private int rID;
	private int cID;
	private boolean dec;

	public ASTNode(ASTNode.NodeType type) {
		this.type = type;
	}

	public NodeType getType() {
		return this.type;
	}

	public ASTNode getLeft() {
		return left;
	}

	public ASTNode getRight() {
		return right;
	}

	public String getVal() {
		return val;
	}

	public int getID() {
		return id;
	}
	
	public int getTID() {
		return tID;
	}

	public int getFID() {
		return fID;
	}
	
	public int getRID() {
		return rID;
	}
	
	public int CID() {
		return cID;
	}

	public boolean getDec() {
		return dec;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public void setLeft(ASTNode node) {
		left = node;
	}

	public void setRight(ASTNode node) {
		right = node;
	}

	public void setID(int id) {
		this.id = id;
	}

	public void setTID(int tID) {
		this.tID = tID;
	}

	public void setFID(int fID) {
		this.fID = fID;
	}

	public void setRID(int rID) {
		this.rID = rID;
	}

	public void setCID(int cID) {
		this.cID = cID;
	}

	public void setDec(boolean dec) {
		this.dec = dec;
	}
}
