import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;


public class UcodeCodeGen {
    public static void main(String[] args) throws Exception{
        MiniCLexer lexer = new MiniCLexer(new ANTLRFileStream("test_Ucode.c"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MiniCParser parser = new MiniCParser(tokens);
        ParseTree tree = parser.program();
        
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new UcodeGenListener(), tree);
    }
}
