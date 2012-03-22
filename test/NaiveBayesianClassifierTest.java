import junit.framework.TestCase;

public class NaiveBayesianClassifierTest extends TestCase {

  public void test1() {
      NaiveBayesianClassifier nbc = new NaiveBayesianClassifier();
      nbc.train("If you want hassle-free and convenient money transfers, for which you don't have to loose sleep, then Xoom is for you. I have used them regularly for over 3 years without a single problem. The initial set up is a bit cumbersome, but that is a price you should be willing to pay to use a secure service. " +
              "The fees are overall reasonable, especially if you don't have to make frequent transfers (it's a fixed fee, up to a certain amount, " +
              "I think; so if you send $1000 in 10 installments, you'll end up paying 10 times as much as if you sent it in 1 lump sum).\n" +
              "Overall, I am very happy with the service and would highly recommend it for money transfer to overseas. ",Classifier.Classification.GOOD);
      nbc.train("easy, quick and accurate. money always gets there no later than 2 hours.",Classifier.Classification.GOOD);
//      nbc.train("the quick brown fox jumps",Classifier.Classification.GOOD);

      nbc.train("I will not recommend this to any one,bad experience",Classifier.Classification.BAD);
      nbc.train("The first 3 transfers I made with xoom were excellent. Xoom deliberately held up my 4th transaction for 4 days even " +
              "after I called customer service. service. service. service. and explained how critical it was for the transfer to go through immediately. " +
              "This is inexcusable and definately shows a disregard for the needs of the customer. ",Classifier.Classification.BAD);

//      assertEquals(0.15624999999999997,nbc.prob("quick rabbit", Classifier.Classification.GOOD));
//      assertEquals(0.050000000000000003,nbc.prob("quick rabbit", Classifier.Classification.BAD));
      
      assertEquals(Classifier.Classification.GOOD,nbc.classify("good service. I am very happy."));
      assertEquals(Classifier.Classification.BAD,nbc.classify("bad service. I am sad."));
  }
}
