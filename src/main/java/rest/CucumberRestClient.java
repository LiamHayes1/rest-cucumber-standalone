package rest;

public interface CucumberRestClient {
   boolean updateTest(String resultOutput);

   String getFeatureString(String testId);
}
