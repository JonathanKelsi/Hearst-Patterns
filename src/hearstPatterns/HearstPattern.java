package hearstPatterns;

/**
 * The interface Hearst pattern.
 */
public interface HearstPattern {

    /**
     * Gets pattern.
     *
     * @return the pattern
     */
    String getPattern();

    /**
     * Gets base pattern.
     *
     * @return the base pattern
     */
    String getBasePattern();

    /**
     * Is the hypernym in this pattern the last NP in a line of text.
     *
     * @return the boolean
     */
    boolean isLast();
}
