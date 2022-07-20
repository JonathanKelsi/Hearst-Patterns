package hearstPatterns;

/**
 * The type Such np as.
 */
public class SuchNpAs implements HearstPattern {
    @Override
    public String getPattern() {
        return "such <np>[^<>]*</np> as <np>[^<>]*</np>( , <np>[^<>]*</np>)*(( ,)? (or|and) <np>[^<>]*</np>)?";
    }

    @Override
    public String getBasePattern() {
        return "such <np>";
    }

    @Override
    public boolean isLast() {
        return false;
    }
}
