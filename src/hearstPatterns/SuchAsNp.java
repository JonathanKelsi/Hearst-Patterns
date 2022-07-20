package hearstPatterns;

/**
 * The type Such as np.
 */
public class SuchAsNp implements HearstPattern {
    @Override
    public String getPattern() {
        return "<np>[^<>]*</np>( ,)? such as <np>[^<>]*</np>( , <np>[^<>]*</np>)*(( ,)? (or|and) <np>[^<>]*</np>)?";
    }

    @Override
    public String getBasePattern() {
        return "such as <np>";
    }

    @Override
    public boolean isLast() {
        return false;
    }
}
