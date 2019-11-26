import java.lang.String;

public class TACObject {
	enum OpType{
		ASSIGN, PLUS, MINUS, MUL, DIV, IF_LT, IF_GT, IF_LTE, IF_GTE, IF_EQ, IF_NE, GOTO, LABLE;
	}

	OpType op;
	String src1;
	String src2;
	String dest;

	public TACObject(OpType o, String s1, String s2, String d){
		op = o;
		src1 = s1;
		src2 = s2;
		dest = d;
	}

	public OpType getOp(){
		return op;
	}

	public String getSrc1(){
		return src1;
	}

	public String getSrc2(){
		return src2;
	}

	public String getDest(){
		return dest;
	}

	public String toString(){
		String str = null;
		if(op == OpType.ASSIGN){
			str = dest + " = " + src1 + "\n";
		}
		else if(op == OpType.PLUS){
			str = dest + " = " + src1 + " + " + src2 + "\n";
		}
		else if(op == OpType.MINUS){
			str = dest + " = " + src1 + " - " + src2 + "\n";
		}
		else if(op == OpType.MUL){
			str = dest + " = " + src1 + " * " + src2 + "\n";
		}
		else if(op == OpType.DIV){
			str = dest + " = " + src1 + " / " + src2 + "\n";
		}
		else if(op == OpType.IF_LT){
			str = "IF_LT: " + src1 + ", " + src2 + ", " + dest + "\n";
		}
		else if(op == OpType.IF_GT){
			str = "IF_GT: " + src1 + ", " + src2 + ", " + dest + "\n";
		}
		else if(op == OpType.IF_LTE){
			str = "IF_LTE: " + src1 + ", " + src2 + ", " + dest + "\n";
		}
		else if(op == OpType.IF_GTE){
			str = "IF_GTE: " + src1 + ", " + src2 + ", " + dest + "\n";
		}
		else if(op == OpType.IF_EQ){
			str = "IF_EQ: " + src1 + ", " + src2 + ", " + dest + "\n";
		}
		else if(op == OpType.IF_NE){
			str = "IF_NE: " + src1 + ", " + src2 + ", " + dest + "\n";
		}
		else if(op == OpType.GOTO){
			str = "GOTO: " + dest + "\n";
		}
		else if(op == OpType.LABLE){
			str = src1 + "\n";
		}
		return str;
	}
}
