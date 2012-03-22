import junit.framework.TestCase;

public class ClassifierTest extends TestCase {

    public void test1() {
        Classifier docClassification = new Classifier();
        docClassification.train("Nobody owns the water.",Classifier.Classification.GOOD);
        docClassification.train("the quick rabbit jumps fences",Classifier.Classification.GOOD);
        docClassification.train("buy pharmaceuticals now",Classifier.Classification.BAD);
        docClassification.train("make quick money at the online casino",Classifier.Classification.BAD);
        docClassification.train("the quick brown fox jumps",Classifier.Classification.GOOD);
        assertEquals(0.625, docClassification.weightedprob("quick",Classifier.Classification.GOOD));
    }
}