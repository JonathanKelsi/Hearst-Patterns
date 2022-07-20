import hearstPatterns.HearstPattern;
import hearstPatterns.SuchAsNp;
import hearstPatterns.SuchNpAs;
import hearstPatterns.IncludingNp;
import hearstPatterns.EspeciallyNp;
import hearstPatterns.WhichIsNp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * The type CreateHypernymDatabase.
 */
public class CreateHypernymDatabase {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        //find the corpus
        File corpus = new File(args[0]);
        File[] articles = corpus.listFiles();

        //if something went wrong reading
        if (articles == null) {
            return;
        }

        HearstPattern[] patterns = {new SuchAsNp(), new SuchNpAs(), new IncludingNp(),
                                    new EspeciallyNp(), new WhichIsNp()};

        //construct the database and the reader
        TreeMap<String, ArrayList<NounPhrase>> dataBase = new TreeMap<>();
        ArticleReader reader = new ArticleReader(dataBase);

        //update the database according to each of the Hearst patterns
        for (File article : articles) {
            for (HearstPattern pattern: patterns) {
                reader.updateDatabase(article, pattern);
            }
        }

        //sort the dictionary and output the result into a file
        try {
            PrintWriter os = new PrintWriter(new OutputStreamWriter(new FileOutputStream(args[1])));

            for (String key: dataBase.keySet()) {
                ArrayList<NounPhrase> values = dataBase.get(key);

                if (values.size() >= 3) {
                    //sort
                    dataBase.get(key).sort((h1, h2) -> {
                        int comp  = h2.getCount() - h1.getCount();

                        if (comp == 0) {
                            comp = h1.getValue().compareTo(h2.getValue());
                        }

                        return comp;
                    });

                    //output into the file
                    os.print(key + ": ");
                    int len = values.size();
                    for (int i = 0; i < len - 1; i++) {
                        os.print(values.get(i).toString() + ", ");
                    }
                    os.print(values.get(len - 1).toString() + "\n");
                }
            }

            os.close();
        } catch (IOException e) {
            System.out.println("Something went wrong!");
        }
    }
}
