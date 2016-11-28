
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class MiniCPrintListener extends MiniCBaseListener {
	ParseTreeProperty<String> newTexts = new ParseTreeProperty<String>();

	@Override
	public void exitProgram(MiniCParser.ProgramContext ctx) {
		for(MiniCParser.DeclContext d : ctx.decl()){
			System.out.println(newTexts.get(d));
		}
	}

	@Override
	public void exitDecl(MiniCParser.DeclContext ctx) {
		String decl;
		
		if(ctx.var_decl() == null){
			decl = newTexts.get(ctx.fun_decl());
		}
		
		else{
			decl = newTexts.get(ctx.var_decl());
		}
		
		newTexts.put(ctx, decl);
	}

	@Override
	public void exitVar_decl(MiniCParser.Var_declContext ctx) {
		String typeSpec = newTexts.get(ctx.type_spec());	// type_spec
		String ident = ctx.getChild(1).getText();	// IDENT
		String semicolon = ctx.getChild(ctx.getChildCount() - 1).getText();	// ;
		String varDecl;
		
		if(ctx.getChildCount() == 3){	// type_spec IDENT ';'
			varDecl = typeSpec + " " + ident + semicolon;
		}

		else if(ctx.getChildCount() == 5){	// type_spec IDENT '=' LITERAL ';'
			String equal = ctx.getChild(2).getText();
			String literal = ctx.LITERAL().getText();
			varDecl = typeSpec + " " + ident + " " + equal + " " + literal + semicolon;
		}
		
		else{	// type_spec IDENT '[' LITERAL ']' ';'
			String squareBracket1 = ctx.getChild(2).getText();
			String squareBracket2 = ctx.getChild(4).getText();
			String literal = ctx.LITERAL().getText();
			varDecl = typeSpec + " " + ident + squareBracket1 + literal + squareBracket2 + semicolon;
		}
		
		newTexts.put(ctx, varDecl + "");
	}

	@Override
	public void exitType_spec(MiniCParser.Type_specContext ctx) {
		newTexts.put(ctx, ctx.getChild(0).getText());
	}

	@Override
	public void exitFun_decl(MiniCParser.Fun_declContext ctx) {		
		String typeSpec = newTexts.get(ctx.type_spec());
		String ident = ctx.getChild(1).getText();
		String parens1 = ctx.getChild(2).getText();
		String params = newTexts.get(ctx.params());
		String parens2 = ctx.getChild(4).getText();
		String compound_stmt = newTexts.get(ctx.compound_stmt());
		
		newTexts.put(ctx, typeSpec + " " + ident + parens1 + params + parens2 + "\n" + compound_stmt);
	}

	@Override
	public void exitParams(MiniCParser.ParamsContext ctx) {
		String params;
		
		if(ctx.getChildCount() == 0){
			params = "";
		}
		
		else if(ctx.getChild(0) != ctx.param(0)){	// VOID
			params = ctx.getChild(0).getText();
		}
		
		else{
			StringBuffer buf = new StringBuffer();
			
			for(MiniCParser.ParamContext p : ctx.param()){	// param (',' param)*
				buf.append(newTexts.get(p) + ", ");
			}
			
			params = buf + "";
			params = params.substring(0, params.length() - 2);	// remove ", "
		}
		
		newTexts.put(ctx, params);
	}

	@Override
	public void exitParam(MiniCParser.ParamContext ctx) {
		StringBuffer param = new StringBuffer(newTexts.get(ctx.getChild(0)));	// type_spec
		
		param.append(" ");
		for(int i = 1; i < ctx.getChildCount(); i++){
			param.append(ctx.getChild(i).getText());	// IDENT or IDENTT '[' ']'
		}
				
		newTexts.put(ctx, param + "");
	}

	@Override
	public void exitStmt(MiniCParser.StmtContext ctx) {
		String stmt = newTexts.get(ctx.getChild(0));
		
		newTexts.put(ctx, stmt);
	}

	@Override
	public void exitExpr_stmt(MiniCParser.Expr_stmtContext ctx) {
		String expr = newTexts.get(ctx.expr());
		String semicolon = ctx.getChild(1).getText();
		
		newTexts.put(ctx, expr + semicolon);
	}

	@Override
	public void exitWhile_stmt(MiniCParser.While_stmtContext ctx) {
		String WHILE = ctx.WHILE().getText();
		String parens1 = ctx.getChild(1).getText();
		String expr = newTexts.get(ctx.expr());
		String parens2 = ctx.getChild(3).getText();
		String stmt = newTexts.get(ctx.stmt());
		String whileStmt;
		
		if(ctx.stmt().compound_stmt() == null){
			stmt = indent(stmt);
			whileStmt = WHILE + " " + parens1 + expr + parens2 + "\n" + "{\n" + stmt + "}";
		}
		else{
			whileStmt = WHILE + " " + parens1 + expr + parens2 + "\n" + stmt;
		}
		
		newTexts.put(ctx, whileStmt);
	}

	@Override
	public void exitCompound_stmt(MiniCParser.Compound_stmtContext ctx) {
		String brace1 = ctx.getChild(0).getText();
		String brace2 = ctx.getChild(ctx.getChildCount() - 1).getText();
		StringBuffer localDeclAndStmt = new StringBuffer();
		
		for(MiniCParser.Local_declContext l : ctx.local_decl()){
			localDeclAndStmt.append(indent(newTexts.get(l)));
		}
		
		if(ctx.local_decl(0) != null){
			localDeclAndStmt.append("\n");
		}
		
		for(MiniCParser.StmtContext s : ctx.stmt()){
			localDeclAndStmt.append(indent(newTexts.get(s)));
		}
		
		newTexts.put(ctx, brace1 + "\n" + localDeclAndStmt  + brace2);
	}

	@Override
	public void exitLocal_decl(MiniCParser.Local_declContext ctx) {
		String typeSpec = newTexts.get(ctx.type_spec());	// type_spec
		String ident = ctx.getChild(1).getText();	// IDENT
		String semicolon = ctx.getChild(ctx.getChildCount() - 1).getText();	// ;
		String localDecl;
		
		if(ctx.getChildCount() == 3){	// type_spec IDENT ';'
			localDecl = typeSpec + " " + ident + semicolon;
		}

		else if(ctx.getChildCount() == 5){	// type_spec IDENT '=' LITERAL ';'
			String equal = ctx.getChild(2).getText();
			String literal = ctx.LITERAL().getText();
			localDecl = typeSpec + " " + ident + " " + equal + " " + literal + semicolon;
		}
		
		else{	// type_spec IDENT '[' LITERAL ']' ';'
			String squareBracket1 = ctx.getChild(2).getText();
			String squareBracket2 = ctx.getChild(4).getText();
			String literal = ctx.LITERAL().getText();
			localDecl = typeSpec + " " + ident + squareBracket1 + literal + squareBracket2 + semicolon;
		}
		
		newTexts.put(ctx, localDecl + "");
	}

	@Override
	public void exitIf_stmt(MiniCParser.If_stmtContext ctx) {		
		String IF = ctx.IF().getText();
		String parens1 = ctx.getChild(1).getText();
		String expr = newTexts.get(ctx.expr());
		String parens2 = ctx.getChild(3).getText();
		String stmt1 = newTexts.get(ctx.stmt(0));
		String ifStmt;
		
		if(ctx.stmt(0).compound_stmt() == null){
			stmt1 = indent(stmt1);
			ifStmt = IF + " " + parens1 + expr + parens2 + "\n" + "{\n" + stmt1 + "}";
		}
		else{
			ifStmt = IF + " " + parens1 + expr + parens2 + "\n" + stmt1;
		}
		
		if(ctx.getChildCount() == 7){	// IF '(' expr ')' stmt ELSE stmt
			String ELSE = ctx.ELSE().getText();
			String stmt2 = newTexts.get(ctx.stmt(1));
			
			if(ctx.stmt(1).compound_stmt() == null){
				stmt2 = indent(stmt2);
				ifStmt = ifStmt + "\n" + ELSE + "\n" + "{\n" + stmt2 + "}";
			}
			
			else{
			ifStmt = ifStmt + "\n" + ELSE + "\n" + stmt2;
			}
		}
		
		newTexts.put(ctx, ifStmt);
	}

	@Override
	public void exitReturn_stmt(MiniCParser.Return_stmtContext ctx) {
		String RETURN = ctx.RETURN().getText();
		String semicolon = ctx.getChild(ctx.getChildCount() - 1).getText();
		String returnStmt;
		
		if(ctx.expr() != null){	// RETURN expr ';'
			String expr = newTexts.get(ctx.expr());
			returnStmt = RETURN + " " + expr + semicolon;
		}
		
		else{	// RETURN ';'
			returnStmt = RETURN + semicolon;
		}
		
		newTexts.put(ctx, returnStmt);
	}

	@Override
	public void exitExpr(MiniCParser.ExprContext ctx) {
		String expr1, expr2, op, parens1, parens2, ident;
		
		if(ctx.getChildCount() == 1){
			newTexts.put(ctx, ctx.getChild(0).getText());
		}
		
		else if(ctx.getChildCount() == 2){
			op = ctx.getChild(0).getText();
			expr1 = newTexts.get(ctx.expr(0));
			newTexts.put(ctx, op + expr1);
		}
		
		else if(ctx.getChildCount() == 3){
			if (ctx.getChild(1) != ctx.expr(0)) {
				if(ctx.IDENT() != null){	// IDENT '=' expr
					ident = ctx.IDENT().getText();
					expr1 = newTexts.get(ctx.expr(0));
					op = ctx.getChild(1).getText();
					newTexts.put(ctx, ident + " " + op + " " + expr1);
				}
				else {
					expr1 = newTexts.get(ctx.expr(0));
					expr2 = newTexts.get(ctx.expr(1));
					op = ctx.getChild(1).getText();
					newTexts.put(ctx, expr1 + " " + op + " " + expr2);
				}
			}
			
			else{	// '(' expr ')'
				parens1 = ctx.getChild(0).getText();
				parens2 = ctx.getChild(2).getText();
				expr1 = newTexts.get(ctx.expr(0));
				newTexts.put(ctx, parens1 + expr1 + parens2);
			}
		}
		
		else if(ctx.getChildCount() == 4){
			ident = ctx.IDENT().getText();
			parens1 = ctx.getChild(1).getText();
			parens2 = ctx.getChild(3).getText();
			
			if(ctx.expr(0) != null){	// IDENT '[' expr ']'
				expr1 = newTexts.get(ctx.expr(0));
			}
			
			else{	// IDENT '(' args ')'
				expr1 = newTexts.get(ctx.args());
			}
			
			newTexts.put(ctx, ident + parens1 + expr1 + parens2);
		}
		
		else{	// IDENT '[' expr ']' '=' expr
			ident = ctx.IDENT().getText();
			parens1 = ctx.getChild(1).getText();
			expr1 = newTexts.get(ctx.expr(0));
			parens2 = ctx.getChild(3).getText();
			op = ctx.getChild(4).getText();
			expr2 = newTexts.get(ctx.expr(1));
			
			newTexts.put(ctx, ident + parens1 + expr1 + parens2 + " " + op + " " + expr2);
		}
		
	}

	@Override
	public void exitArgs(MiniCParser.ArgsContext ctx) {
		StringBuffer buf = new StringBuffer();
		
		for(MiniCParser.ExprContext e : ctx.expr()){
			buf.append(newTexts.get(e) + ", ");	// expr (',' expr)*
		}
		
		String args = buf + "";
		args = args.substring(0, args.length() - 2); // remove ", "
		
		newTexts.put(ctx, args);
	}
	
	public String indent(String text){
		StringBuffer buf = new StringBuffer();
		String s[] = text.split("\n");
		
		for(int i = 0; i < s.length; i++){
			buf.append("...." + s[i] + "\n");
		}
		
		return buf + "";
	}
	
}
