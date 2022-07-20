import java.util.Objects;

/**
 * The type Noun phrase.
 */
public class NounPhrase {
    private String value;
    private int count;

    /**
     * Instantiates a new Noun phrase.
     *
     * @param value the value
     */
    public NounPhrase(String value) {
        this.value = new String(value);
        this.count = 1;
    }

    /**
     * Instantiates a new Noun phrase.
     *
     * @param value the value
     * @param count the count
     */
    public NounPhrase(String value, int count) {
        this.value = new String(value);
        this.count = count;
    }

    /**
     * Increase count.
     */
    public void increaseCount() {
        this.count++;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public int getCount() {
        return this.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, count);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return  false;
        }
        return this.value.equals(((NounPhrase) obj).value);
    }

    @Override
    public String toString() {
        return value + " (" + count + ")";
    }
}
