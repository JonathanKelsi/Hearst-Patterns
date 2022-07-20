package hearstPatterns;

/**
 * The type Especially np.
 */
public class EspeciallyNp implements HearstPattern {
    @Override
    public String getPattern() {
        return "<np>[^<>]*</np>( ,)? especially <np>[^<>]*</np>( , <np>[^<>]*</np>)*(( ,)? (or|and) <np>[^<>]*</np>)?";
    }

    @Override
    public String getBasePattern() {
        return "especially <np>";
    }

    @Override
    public boolean isLast() {
        return false;
    }
}
