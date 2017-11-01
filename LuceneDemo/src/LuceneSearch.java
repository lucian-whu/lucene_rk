import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import javax.management.Query;
import java.io.File;
import java.io.IOException;

/**
 * Created by lx201 on 2017/10/31.
 */
public class LuceneSearch {
    public static final int N = 30000000;
    //声明一个IndexSearch对象
    private IndexSearcher searcher;
    //声明一个Query对象
    private Query query;
    //private String field = "line";

    public LuceneSearch(){
        try{
            IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(Constants.INDEX_STORE_PATH)));
            searcher = new IndexSearcher(reader);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    //返回查询结果
    public final TopDocs search(String keyword) throws IOException {
        System.out.println("正在检索关键字："+ keyword);
        try {
//            Analyzer analyzer = new PatternAnalyzer(" ");
//            QueryParser parser = new QueryParser(Version.LUCENE_43, field, analyzer);
//            query = parser.parse(keyword);
//            TopDocs results = searcher.search(query, N);

           // 精确单词检索
            Term term = new Term("line",keyword);
            TermQuery tq = new TermQuery(term);
            TopDocs results = searcher.search(tq, N);
            return results;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    //打印结果
    public int printResult(TopDocs results){
        ScoreDoc[] h = results.scoreDocs;
        if(h.length == 0){
            System.out.println("对不起，没有找到您要的结果");
        }else{
            System.out.println(h.length);
            for (int i = 0; i < h.length; i++){
                try{
                    //System.out.println(h.length);
                    Document doc = searcher.doc(h[i].doc);
                    System.out.print("这是第" + i + "个检索到的结果，文件为：");
                    System.out.println(doc.get("line"));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        System.out.println("-----------------------------");
        return 0;
    }
    public static void main(String[] args) throws Exception {
        LuceneSearch test = new LuceneSearch();
        TopDocs h;
        h = test.search("Head");
        test.printResult(h);
        // h = test.search("人民");
        // test.printResult(h);
        // h =test.search("共和国");
        // test.printResult(h);
    }


}
