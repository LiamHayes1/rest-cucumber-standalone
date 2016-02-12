package filecreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rest.CucumberRestClient;

public class FeatureFileCreator {

   private String featurePath;
   private CucumberRestClient client;
   private Map<String, String> toCreate;

   public FeatureFileCreator() {
   }

   public FeatureFileCreator(CucumberRestClient client) {
      this.client = client;
   }

   public static void main(String[] args) {
      FeatureFileCreator c = new FeatureFileCreator();
      c.createDirectory();
      c.createFeatureFile("aFeature",
         "Feature: test feature \nScenario: test scenario\nGiven something");
   }

   public void createFiles(List<String> testIds) {
      toCreate = new HashMap<String, String>();
      for (String testId : testIds) {
         String featureString = client.getFeatureString(testId);
         toCreate.put(testId, featureString);
      }
      createFeatureFiles();
   }

   public void createDirectory() {
      String path = System.getProperty("user.dir");
      boolean created = new File(path + "\\features").mkdir();
      if (created) {
         featurePath = path + "\\features";
      }
   }

   public void createFeatureFiles() {
      for (Map.Entry<String, String> entry : toCreate.entrySet()) {
         createFeatureFile(entry.getKey(), entry.getValue());
      }
   }

   public void createFeatureFile(String featureName, String feature) {
      if (featurePath != null) {
         try (
            PrintWriter out =
               new PrintWriter(featurePath + "\\" + featureName + ".feature")) {
            out.println(feature);
         } catch (FileNotFoundException e) {
            e.printStackTrace();
         }
      }
   }

}
