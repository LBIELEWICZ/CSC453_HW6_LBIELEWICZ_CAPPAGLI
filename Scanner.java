import java.util.LinkedList;

class Token{
  enum TokenType{
    NUM, PLUS, MINUS, MUL, DIV, LT, LTE, GT, GTE, OP, CP, ID, ASSG, EQ, NEQ, INT, END, OB, CB, IF, WHILE, VOID, PUBLIC, PRIVATE, CLASS, AND, OR;
  }

  TokenType tokenType;
  String tokenVal;

  public Token(TokenType tokenType, String tokenVal){
    this.tokenType = tokenType;
    this.tokenVal = tokenVal;
  }

  public String toString(){
    return "|" + this.tokenType + ": " + this.tokenVal + "|";
  }
}

public class Scanner{
  public Token extractToken(StringBuilder stream){
    int itr = 0;
    char tokChar = stream.charAt(itr);
    Token ret = null;
    
    // Handling whitespace
    while(tokChar == ' ' || tokChar == '\t' || tokChar == '\n'){
      stream.deleteCharAt(0);
      if (stream.length() == 0)
        return null;
      tokChar = stream.charAt(0);
    }
    
    // Handling a NUM token
    if (tokChar >= '0' && tokChar <= '9') {
      String num = "";
      num += tokChar;
      itr++;

      while (itr < stream.length() && stream.charAt(itr) >= '0' && stream.charAt(itr) <= '9') {
        num += stream.charAt(itr);
        itr++;
        if (itr == stream.length())
          break;
      }
      
      ret = new Token(Token.TokenType.NUM, num);
    } 

    // Handling an INT token
    else if (stream.length() > 2 && stream.substring(itr,itr+3).equals("int")) {
      ret = new Token(Token.TokenType.INT, "int");
      itr = itr + 3;
    }

    // Handling an IF token
    else if (stream.length() > 1 && stream.substring(itr,itr+2).equals("if")) {
      ret = new Token(Token.TokenType.IF, "if");
      itr = itr + 2;
    }

    // Handling a WHILE token
    else if (stream.length() > 4 && stream.substring(itr,itr+5).equals("while")) {
      ret = new Token(Token.TokenType.WHILE, "while");
      itr = itr + 5;
    }

    // Handling a VOID token
    else if (stream.length() > 3 && stream.substring(itr,itr+4).equals("void")) {
      ret = new Token(Token.TokenType.VOID, "void");
      itr = itr + 4;
    }

    // Handling a PUBLIC token
    else if (stream.length() > 5 && stream.substring(itr,itr+6).equals("public")) {
      ret = new Token(Token.TokenType.PUBLIC, "public");
      itr = itr + 6;
    }

    // Handling a PRIVATE token
    else if (stream.length() > 6 && stream.substring(itr,itr+7).equals("private")) {
      ret = new Token(Token.TokenType.PRIVATE, "private");
      itr = itr + 7;
    }

    // Handling a CLASS token
    else if (stream.length() > 4 && stream.substring(itr,itr+5).equals("class")) {
      ret = new Token(Token.TokenType.CLASS, "class");
      itr = itr + 5;
    }
    
    // Handling an ID token
    else if ((tokChar >= 'a' && tokChar <= 'z') || (tokChar >= 'A' && tokChar <= 'Z')) {
      String id = "";
      id += tokChar;
      itr++;

      while (itr < stream.length() && ((stream.charAt(itr) >= '0' && stream.charAt(itr) <= '9') ||
             (stream.charAt(itr) >= 'a' && stream.charAt(itr) <= 'z') ||
             (stream.charAt(itr) >= 'A' && stream.charAt(itr) <= 'Z'))) {
        id += stream.charAt(itr);
        itr++;
        if (itr == stream.length())
          break;
      }

      ret = new Token(Token.TokenType.ID, id);
    }

    // Handling a PLUS token
    else if (tokChar == '+') {
      ret = new Token(Token.TokenType.PLUS, "+");
      itr++;
    }

    // Handling a MINUS token
    else if (tokChar == '-') {
      ret = new Token(Token.TokenType.MINUS, "-");
      itr++;
    }

    // Handling a MUL token
    else if (tokChar == '*') {
      ret = new Token(Token.TokenType.MUL, "*");
      itr++;
    }

    // Handling a DIV token
    else if (tokChar == '/') {
      ret = new Token(Token.TokenType.DIV, "/");
      itr++;
    }

    // Handling an AND token
    else if (stream.length() > 1 && tokChar == '&' && stream.charAt(itr + 1) == '&') {
      ret = new Token(Token.TokenType.AND, "&&");
      itr = itr + 2;
    }

    // Handling an OR token
    else if (stream.length() > 1 && tokChar == '|' && stream.charAt(itr + 1) == '|') {
      ret = new Token(Token.TokenType.OR, "||");
      itr = itr + 2;
    }
    
    // Handling an NEQ token
    else if (stream.length() > 1 && tokChar == '!' && stream.charAt(itr + 1) == '=') {
      ret = new Token(Token.TokenType.NEQ, "!=");
      itr = itr + 2;
    }

    // Handling EQ and ASSG tokens
    else if (tokChar == '=') {
      if (stream.length() > 1 && stream.charAt(itr + 1) == '=') {
        itr++;
        ret = new Token(Token.TokenType.EQ, "==");
      }
      else
        ret = new Token(Token.TokenType.ASSG, "=");
      itr++;
    }
    
    // Handling LTE and LT tokens
    else if (tokChar == '<') {
      if (stream.length() > 1 && stream.charAt(itr + 1) == '=') {
        itr++;
        ret = new Token(Token.TokenType.LTE, "<=");
      }
      else
        ret = new Token(Token.TokenType.LT, "<");
      itr++;
    }

    // Handling GTE and GT tokens
    else if (tokChar == '>') {
      if (stream.length() > 1 && stream.charAt(itr + 1) == '=') {
        itr++;
        ret = new Token(Token.TokenType.GTE, ">=");
      }
      else
        ret = new Token(Token.TokenType.GT, ">");
      itr++;
    }

    // Handling a OP token
    else if (tokChar == '(') {
      ret = new Token(Token.TokenType.OP, "(");
      itr++;
    }

    // Handling a CP token
    else if (tokChar == ')') {
      ret = new Token(Token.TokenType.CP, ")");
      itr++;
    }
    
    // Handling an OB token
    else if (tokChar == '{') {
      ret = new Token(Token.TokenType.OB, "{");
      itr++;
    }

    // Handling a CB token
    else if (tokChar == '}') {
      ret = new Token(Token.TokenType.CB, "}");
      itr++;
    }

    // Handling an END token
    else if (tokChar == ';') {
      ret = new Token(Token.TokenType.END, ";");
      itr++;
    }

    else if (stream.length() > 1 && stream.substring(itr,itr+2).equals("&&")) {
      ret = new Token(Token.TokenType.AND, "&&");
      itr = itr + 2;
    }

    else if (stream.length() > 1 && stream.substring(itr,itr+2).equals("||")) {
      ret = new Token(Token.TokenType.OR, "||");
      itr = itr + 2;
    }

    // Handling invalid characters
    else {
      System.out.println("ERROR: Invalid character '" + tokChar + "'in " + stream + ". Exiting program.");
      System.exit(1);
    }    
    
    stream.delete(0, itr); // Removing read chars from StringBuilder
    return ret;
  }

  public String extractTokens(String arg){
    String result= "";
  	StringBuilder stream = new StringBuilder(arg);

  	while(stream.length() > 0){
  		Token nextToken = extractToken(stream);
      if (nextToken != null)
  		  result += nextToken.toString();
  	}

    return result;
  }

  public LinkedList<Token> extractTokenList(String arg){
    StringBuilder stream = new StringBuilder(arg);
    LinkedList<Token> tokens = new LinkedList<Token>();
    
    while(stream.length() > 0){
      Token nextToken = extractToken(stream);
      if (nextToken != null)
        tokens.add(nextToken);
    }

    return tokens;
  }

}
