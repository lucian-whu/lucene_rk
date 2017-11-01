import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.pattern.PatternTokenizer;

import java.io.Reader;
import java.util.regex.Pattern;

/**
 * Created by lx201 on 2017/10/31.
 */
public class PatternAnalyzer extends Analyzer {
    String regex;
    public PatternAnalyzer(String regex){
        this.regex = regex;
    }
    @Override
    protected Analyzer.TokenStreamComponents createComponents(String s, Reader reader) {
        return new Analyzer.TokenStreamComponents(new PatternTokenizer(reader, Pattern.compile(regex),-1));
    }

}
