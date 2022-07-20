import hearstPatterns.HearstPattern;
import hearstPatterns.SuchAsNp;
import hearstPatterns.SuchNpAs;
import hearstPatterns.IncludingNp;
import hearstPatterns.EspeciallyNp;
import hearstPatterns.WhichIsNp;
import java.io.File;
import java.util.ArrayList;

/**
 * The type DiscoverHypernym.
 */
public class DiscoverHypernym {
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
        ArrayList<NounPhrase> hypernyms = new ArrayList<>();
        ArticleReader reader = new ArticleReader(args[1], hypernyms);

        //update the database according to each of the Hearst patterns
        for (File article : articles) {
            for (HearstPattern pattern : patterns) {
                reader.discoverHypernyms(article, pattern);
            }
        }

        //sort
        hypernyms.sort((h1, h2) -> {
            int comp  = h2.getCount() - h1.getCount();

            if (comp == 0) {
                comp = h1.getValue().compareTo(h2.getValue());
            }

            return comp;
        });

        for (NounPhrase hypernym : hypernyms) {
            //output into the file
            System.out.println(hypernym.getValue() + ": (" + hypernym.getCount() + ")");
        }

        if (hypernyms.isEmpty()) {
            System.out.println("The lemma doesn't appear in the corpus.");
        }
    }
}
