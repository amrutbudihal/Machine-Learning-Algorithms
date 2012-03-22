import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Classifier {

    public enum Classification {
        GOOD, BAD, UNKNOWN;
    } //different category types for a document.
    
    private double assumedProb = 0.5;
    private double weight = 1.0; //used in weighted probability.
    private Map<String, Map<Classification,Integer>> featureMap; //storing which feature is classified as category.
    private Map<Classification, Integer> cc; //classificationCount, denotes how many times this classification is used.
    protected Feature feature; //Feature associated with this classifier.

    Classifier() {
        featureMap = new HashMap<String, Map<Classification, Integer>>();
        cc = new HashMap<Classification, Integer>(3);
        feature = new WordFeatureImpl();
    }

    //@todo: make training set more rich.
    public void train(String paragraph, Classification category) {
        List<String> features = (List<String>) feature.getFeatures(paragraph);
        for (String feature: features) {
            incrementFeature(feature, category);
        }
        incc(category);
    }


    protected double weightedprob(String feature, Classification category) {
        //get the basic probability.
        double basicProb = fprob(feature, category);
        int totalFeatureCount = featureCount(feature);
        return ((assumedProb*weight) + (basicProb * totalFeatureCount))/(totalFeatureCount + weight);
    }

    // Increase the count of a feature/category pair
    private void incrementFeature(String feature, Classification category) {
        Map<Classification, Integer> classMap = featureMap.get(feature);
        Integer count = null;
        if (classMap != null) {
            count = classMap.get(category);
        } else {
            classMap = new HashMap<Classification, Integer>();
        }
        count = (count == null)?1:++count;
        classMap.put(category, count);
        featureMap.put(feature, classMap);
    }

    // Increase the count of a category
    private void incc(Classification category) {
       Integer count = cc.get(category);
       count = (count == null)?1:++count;
       cc.put(category, count);
    }

    // The number of times a feature has appeared in a category
    private double featureCount (String feature, Classification category) {
        Map<Classification,Integer> classMap = featureMap.get(feature);
        Integer count = 0;
        if (classMap != null) {
            count = classMap.get(category);
            count = (count == null)?0:count;
        }
        return count;
    }

    //sum of feature in all the categories.
    private int featureCount (String feature) {
        int sumCount = 0;
        Map<Classification, Integer> map = featureMap.get(feature);
        if (map == null) {
            return sumCount;
        } else {
            Set<Classification> keys = map.keySet();
            for(Classification key: keys) {
                sumCount += map.get(key);
            }
        }
        return sumCount;
    }

    // The number of items in a category
    protected double catCount (Classification category) {
       return cc.get(category);
    }

    // The total number of items
    protected int totalItems() {
        Set<Classification> categories = cc.keySet();
        int count=0;
        for (Classification category: categories) {
            count += cc.get(category);
        }
        return count;
    }

    // The list of all categories
    protected Set<Classification> getAllCategories() {
        return cc.keySet();
    }

    //compute the probablity given a feature and category combination.
    private double fprob(String feature, Classification category) {
        if (cc.get(category) == null) {
            return 0.0;
        }
        return featureCount(feature,category)/catCount(category);
    }

}
