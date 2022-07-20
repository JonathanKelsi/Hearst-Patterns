package hearstPatterns;

/**
 * The type Which is np.
 */
public class WhichIsNp implements HearstPattern {
    @Override
    public String getPattern() {
        return "<np>[^<>]*</np>( ,)? which is( (an example|a kind|a class) of)? <np>[^<>]*</np>";
    }

    @Override
    public String getBasePattern() {
        return "which is";
    }

    @Override
    public boolean isLast() {
        return true;
    }
}
