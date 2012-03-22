import java.util.Arrays;
import java.util.List;

public class WordFeatureImpl implements Feature {

    public List<String> getFeatures(String para) {
        if (para == null || para.length() <= 0) {
            return null;
        }
        String[] paragraphParts = para.split(" "); //split on white space.
        return Arrays.asList(paragraphParts);
    }
}
