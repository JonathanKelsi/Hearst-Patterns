import hearstPatterns.HearstPattern;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Article reader.
 */
public class ArticleReader {
    private TreeMap<String, ArrayList<NounPhrase>> dataBase;
    private String lemma;
    private ArrayList<NounPhrase> hypernyms;

    /**
     * Instantiates a new Article reader.
     *
     * @param dataBase the data base
     */
    public ArticleReader(TreeMap<String, ArrayList<NounPhrase>> dataBase) {
        this.dataBase = dataBase;
        this.lemma = null;
        this.hypernyms = null;
    }

    /**
     * Instantiates a new Article reader.
     *
     * @param lemma     the lemma
     * @param hypernyms the hypernyms
     */
    public ArticleReader(String lemma, ArrayList<NounPhrase> hypernyms) {
        this.lemma = lemma;
        this.hypernyms = hypernyms;
        this.dataBase = null;
    }

    /**
     * extract the NPs out of a line of text.
     *
     * @param text a line of text
     * @return     an array of NPs
     */
    private ArrayList<String> getNps(String text) {
        Pattern np = Pattern.compile("<np>[^<>]*</np>");
        Matcher matcher = np.matcher(text);
        ArrayList<String> result = new ArrayList<>();

        while (matcher.find()) {
            //remove the <np></np> tag and add to the arraylist
            result.add(text.substring(matcher.start() + 4, matcher.end() - 5));
        }

        return result;
    }

    /**
     * remove a hypernym from a list of NPs.
     *
     * @param npList a list of NPs
     * @param isLast is the hypernym the last NP
     * @return       the hypernym
     */
    private String removeHypernym(ArrayList<String> npList, boolean isLast) {
        int index = 0;

        if (isLast) {
            index = npList.size() - 1;
        }

        String hypernym = npList.get(index);
        npList.remove(index);

        return hypernym;
    }

    /**
     * Enter the hypernym and it's hyponyms into the database.
     *
     * @param hyponyms the hypernym's hyponyms
     * @param hypernym the hyponym
     */
    private void insertToDatabase(ArrayList<String> hyponyms, String hypernym) {
        if (!dataBase.containsKey(hypernym)) {
            dataBase.put(hypernym, new ArrayList<>());
        }

        ArrayList<NounPhrase> values = dataBase.get(hypernym);

        for (String hyponym: hyponyms) {
            if (values.contains(new NounPhrase(hyponym))) {
                values.get(values.indexOf(new NounPhrase(hyponym))).increaseCount();
            } else {
                values.add(new NounPhrase(hyponym));
            }
        }
    }

    /**
     * Update database.
     *
     * @param article the article
     * @param pattern the pattern
     */
    public void updateDatabase(File article, HearstPattern pattern) {
        if (this.dataBase == null) {
            return;
        }

        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(new FileInputStream(article)));
            Pattern heartsPattern = Pattern.compile(pattern.getPattern());
            String line = "";

            while ((line = is.readLine()) != null) {
                line = line.toLowerCase();

                //check if the line is worth trying to match
                if (line.contains(pattern.getBasePattern())) {
                    Matcher matcher = heartsPattern.matcher(line);
                    while (matcher.find()) {
                        //get the noun phrases in the line
                        ArrayList<String> npList = getNps(line.substring(matcher.start(), matcher.end()));

                        //extract the hypernym
                        String hypernym = removeHypernym(npList, pattern.isLast());

                        //add the hypernym and it's hyponyms to the database
                        insertToDatabase(npList, hypernym);
                    }
                }
            }

            is.close();
        } catch (Exception e) {
            System.out.println("Something went wrong!");
        }
    }


    /**
     * Discover hypernyms.
     *
     * @param article the article
     * @param pattern the pattern
     */
    public void discoverHypernyms(File article, HearstPattern pattern) {
        if (this.hypernyms == null || this.lemma == null) {
            return;
        }

        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(new FileInputStream(article)));
            Pattern heartsPattern = Pattern.compile(pattern.getPattern());
            String line = "";

            while ((line = is.readLine()) != null) {
                line = line.toLowerCase();

                //check if the line is worth trying to match
                if (line.contains(lemma)) {
                    //if it is, update the database accordingly
                    Matcher matcher = heartsPattern.matcher(line);
                    while (matcher.find()) {
                        ArrayList<String> npList = getNps(line.substring(matcher.start(), matcher.end()));
                        String hypernymString = removeHypernym(npList, pattern.isLast());

                        if (npList.contains(lemma)) {
                            NounPhrase hypernym = new NounPhrase(hypernymString);

                            if (hypernyms.contains(hypernym)) { //if hypernym is already in the database
                                hypernyms.get(hypernyms.indexOf(hypernym)).increaseCount();
                            } else {
                                hypernyms.add(hypernym);
                            }
                        }
                    }
                }
            }

            is.close();
        } catch (Exception e) {
            System.out.println("Something went wrong!");
        }
    }
}
