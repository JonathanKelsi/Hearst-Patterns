package hearstPatterns;

/**
 * The type Including np.
 */
public class IncludingNp implements HearstPattern {
    @Override
    public String getPattern() {
        return "<np>[^<>]*</np>( ,)? including <np>[^<>]*</np>( , <np>[^<>]*</np>)*(( ,)? (or|and) <np>[^<>]*</np>)?";
    }

    @Override
    public String getBasePattern() {
        return "including <np>";
    }

    @Override
    public boolean isLast() {
        return false;
    }
}
