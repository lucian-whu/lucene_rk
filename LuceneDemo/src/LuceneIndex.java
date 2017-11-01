import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.*;
import java.util.Date;

/**
 * Created by lx201 on 2017/10/31.
 */
public class LuceneIndex {
    //索引器
    private IndexWriter writer;

    public LuceneIndex() {

        try {
            //索引文件的保存位置
            Directory dir = FSDirectory.open(new File(Constants.INDEX_STORE_PATH));
            //分析器
            Analyzer analyzer = new PatternAnalyzer("[;\t\\s+]");//分词规则
            //配置类
            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_43, analyzer);
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);//创建OpenMode.CREATE_OR_APPEND添加模式
            writer = new IndexWriter(dir, iwc);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //将要建立索引的文件构造成一个Document对象，并添加一个域“content”
    private Document getDocument(String line) throws Exception {
        Document doc = new Document();
        Field lineField = new TextField("line", line, Field.Store.YES);
        doc.add(lineField);
        return doc;
    }


    public void writeToIndex()throws Exception{
        String encoding ="utf-8";
        File f = new File(Constants.INDEX_FILE_PATH + "\\result_rk.txt");
        InputStreamReader reader1 = new InputStreamReader(
                new FileInputStream(f), encoding);

        BufferedReader bufferedReader = new BufferedReader(reader1);

        String lineTxt;
        while((lineTxt = bufferedReader.readLine()) != null ) {
            Document doc =getDocument(lineTxt);
            System.out.println("正在为文本"+ "\""+lineTxt+"\""+ "建立索引");
            writer.addDocument(doc);
        }
    }

    public void close()throws Exception{
        writer.close();
    }
    public static void main(String[] args)throws Exception{
        //声明一个对象
        LuceneIndex indexer = new LuceneIndex();
        //建立索引
        Date start = new Date();
        indexer.writeToIndex();
        Date end = new Date();

        System.out.print("建立索引用时" + (end.getTime() -start.getTime())+ "毫秒");

        indexer.close();

    }

}
