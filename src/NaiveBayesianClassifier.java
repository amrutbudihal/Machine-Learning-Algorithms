import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * NBC theorem: Pr(A | B) = Pr(B | A) x Pr(A)/Pr(B)
 * Pr(Category | Document) = Pr(Document | Category) x Pr(Category) / Pr(Document)
 * Pr(Category | Document) -- C Probability that a document belongs to a category.
 * Pr(Document | Category) -- C Probability of a document being given a category (use docProb method)
 * Pr(Category) -- Probability that a randomly selected document belongs to this category.
 * A number of documents in the category divided by the total number of documents
 * Pr(Document) -- Probability that a given document belongs to a category.
 * A constant while comparing c probability of different a document belonging to a different categories.
 */

public class NaiveBayesianClassifier extends Classifier {

    NaiveBayesianClassifier() {
        super();
    }
    
    //document probability (product of individual probabilities of its features.
    private double docProb(String paragraph, Classification category) {
        List<String> features = (List<String>)feature.getFeatures(paragraph);
        double p=1;
        for(String feature: features) {
           p *= weightedprob(feature,category);
        }
        return p;
    }


    private double prob(String paragraph, Classification category) {
        double documentProbability = docProb(paragraph, category);
        double categoryProb =  catCount(category)/ totalItems();
        return documentProbability * categoryProb;
    }
    
    
    
    
    public Classification classify(String paragraph) {
        Set<Classification> categories = getAllCategories();
        Map<Classification, Double> classificationMap = new HashMap<Classification,Double>(categories.size());

        Double best = 0.0;
        Classification bestClassify = null;

        for (Classification category: categories) {
            Double classProb = prob(paragraph,category);
            classificationMap.put(category,classProb);
            if (classProb > best) {
                best = classProb;
                bestClassify = category;
            }
        }
        return bestClassify;
    }
}
