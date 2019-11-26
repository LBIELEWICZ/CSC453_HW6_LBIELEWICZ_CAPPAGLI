import java.util.*;
import java.io.*;

public class AdvancedJava {
	
	public void codeGen (String eval, String fileName) {
		EvalParser parse = new EvalParser();
		
		LinkedList<CodeGenTuple> codeGen = parse.getThreeAddr(eval);

		TreeMap<String,SymbolData> symTab;
		String str;
		CodeGenTuple cur;
		List<TACObject> threeAddr;
		TACObject tac;
		int temp;
		
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));	

			writer.write("#include <stdio.h>\n"+
					"#include <inttypes.h>\n"+
					"\n"+
					"int main(int argc, char **argv){\n"+
					"int64_t r1 = 0, r2 = 0, r3 = 0, r4 = 0, r5 = 0, va = 0;\n"+
					"int64_t stack[100];\n"+
					"int64_t *sp = &stack[99];\n"+
					"int64_t *fp = &stack[99];\n"+
					"int64_t *ra = &&exit;\n"+
					"goto mainEntry;\n");

			symTab = parse.getGlobalSymTab();
			if(symTab.size() > 0){
				str = "int64_t";
				temp = 0;
				for(Map.Entry<String,SymbolData> entry : symTab.entrySet()){
					String key = entry.getKey();
					SymbolData value = entry.getValue();
					str = str + " " + key + " = 0";
					temp++;
					if(temp < symTab.size()){
						str = str + ",";
					}
					else{
						str = str + ";\n";
					}
				}
				writer.write(str);
			}

			temp = 0;
			while(temp < codeGen.size()){
				cur = codeGen.get(temp);
				temp++;
				cur.setOffset();
				symTab = cur.getSymTab();
				str = cur.getName() + ":\n" +
						"sp = sp - 2;\n" +
						"*(sp+2) = fp;\n" +
						"*(sp+1) = ra;\n" +
						"fp = sp;\n" +
						"sp = sp - " + Integer.toString(symTab.size()) + ";\n";
				writer.write(str);

				threeAddr = cur.getList();
				String s;
				int i = 0;
				while(i < threeAddr.size()){
					tac = threeAddr.get(i);
					i++;
					if(tac.getOp() == TACObject.OpType.ASSIGN){
						if(symTab.containsKey(tac.getSrc1())){
							str = "r1 = *(fp-";
							str = str + symTab.get(tac.getSrc1()).getOffset();
							str = str + ");\n";
						}
						else{
							str = "r1 = " + tac.getSrc1() + ";\n";
						}
						if(symTab.containsKey(tac.getDest())){
							str = str + "(fp-";
							str = str + symTab.get(tac.getDest()).getOffset();
							str = str + ") = r1;\n";
						}
						else{
							str = str + tac.getDest() + " = r1;\n";
						}
					}
					else if(tac.getOp() == TACObject.OpType.PLUS || tac.getOp() == TACObject.OpType.MINUS || tac.getOp() == TACObject.OpType.MUL || tac.getOp() == TACObject.OpType.DIV){
						if(symTab.containsKey(tac.getSrc1())){
							str = "r1 = *(fp-";
							str = str + symTab.get(tac.getSrc1()).getOffset();
							str = str + ");\n";
						}
						else{
							str = "r1 = " + tac.getSrc1() + ";\n";
						}
						if(symTab.containsKey(tac.getSrc2())){
							str = str + "r2 = *(fp-";
							str = str + symTab.get(tac.getSrc2()).getOffset();
							str = str + ");\n";
						}
						else{
							str = str + "r2 = " + tac.getSrc2() + ";\n";
						}
						str = str + "r3 = r1 ";
						if(tac.getOp() == TACObject.OpType.PLUS){
							str = str + "+";
						}
						else if(tac.getOp() == TACObject.OpType.MINUS){
							str = str + "-";
						}
						else if(tac.getOp() == TACObject.OpType.MUL){
							str = str + "*";
						}
						else if(tac.getOp() == TACObject.OpType.DIV){
							str = str + "/";
						}
						str = str + " r2;\n";
						if(symTab.containsKey(tac.getDest())){
							str = str + "(fp-";
							str = str + symTab.get(tac.getDest()).getOffset();
							str = str + ") = r3;\n";
						}
						else{
							str = str + tac.getDest() + " = r3;\n";
						}
					}
					else if(tac.getOp() == TACObject.OpType.LABLE){
						str = tac.getSrc1() + ":\n";
					}
					else if(tac.getOp() == TACObject.OpType.GOTO){
						str = "goto " + tac.getDest() + ";\n";
					}
					else if(tac.getOp() == TACObject.OpType.PARAM) {
						str = "sp = sp - 1;\n";
						if (symTab.containsKey(tac.getSrc1()))
							str = str + "*(sp+1) = *(fp-(" + 
								symTab.get(tac.getSrc1()).getOffset() + "));\n";
						else {
							System.out.println("ERROR: Code gen - ID has no offset.");
						}
					}
					else if(tac.getOp() == TACObject.OpType.CALL) {
						str = "ra = &&retLabel" + tac.getSrc2() + ";\n";
						str = str + "goto " + tac.getSrc1() + "\n";
						str = str + "retLabel" + tac.getSrc2() + ":\n";
						str = str + "sp + " + symTab.get(tac.getSrc1()).getArgNum() + ";\n"; 
					}
					else if(tac.getOp() == TACObject.OpType.RETRIEVE) {
						str = "*(fp-(" + symTab.get(tac.getSrc1()).getOffset() + ")) = va;\n";
					}
					else if (tac.getOp() == TACObject.OpType.RETURN) {
						str = "va = *(fp-(" + symTab.get(tac.getSrc1()).getOffset() + "));\n";
					}
					else{ //Control Flow
						if(symTab.containsKey(tac.getSrc1())){
							str = "r1 = *(fp-";
							str = str + symTab.get(tac.getSrc1()).getOffset();
							str = str + ");\n";
						}
						else{
							str = "r1 = " + tac.getSrc1() + ";\n";
						}
						if(symTab.containsKey(tac.getSrc2())){
							str = str + "r2 = *(fp-";
							str = str + symTab.get(tac.getSrc2()).getOffset();
							str = str + ");\n";
						}
						else{
							str = str + "r2 = " + tac.getSrc2() + ";\n";
						}
						str = str + "if(r1 < r2) goto " + tac.getDest() + ";\n";
					}
					writer.write(str);
				}

				str = "sp = sp + " + Integer.toString(symTab.size()) + ";\n" +
						"fp = *(sp+2);\n" +
						"ra = *(sp+1);\n" +
						"sp = sp + 2;\n" +
						"goto *ra;\n";
				writer.write(str);
			}
		
			writer.write("exit:\n"+
					"return reserved;\n"+
					"}");
		} catch (IOException ie) {
			System.out.println("ERROR: File error");
			System.exit(1);
		}
	}
}
