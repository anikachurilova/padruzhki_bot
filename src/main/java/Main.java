import java.io.*;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.FileNotFoundException;

public class Main {



    public static void main(String[] args) throws Exception
    {

        BufferedReader readerr = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String str = readerr.readLine().toLowerCase();
            JSONParser jsonParser = new JSONParser();
            try (FileReader reader = new FileReader("JSONExample.json")) {
                try (FileReader reader1 = new FileReader("unknownPhrases.json")) {
                    Object obj = jsonParser.parse(reader);
                    Object obj1 = jsonParser.parse(reader1);

                    String[] words = str.split(" ");
                    JSONArray employeeList = (JSONArray) obj;
                    JSONArray employeeList1 = (JSONArray) obj1;

                    boolean patternFound= false;

                    for (Object emp : employeeList1) {
                        if (str.matches(parsePatternObject((JSONObject) emp).getKey())) {
                            Pair<String, JSONArray> result = parsePatternObject((JSONObject) emp);
                            int l = result.getRight().toArray().length;
                            //    String sentence="";
//                           for(int i=2;i<words.length;i++){
//                               sentence+=words[i]+" ";
//                           }
                            System.out.println(result.getRight().get((int) (Math.random() * l)) + changePronouns(words[1].toLowerCase()) + " " + words[0].toLowerCase() + ".");
                            patternFound=true;
                            break;
                        }
                    }
                    if(!patternFound) {
                        for (Object emp : employeeList) {
                            //якщо є щось простеньке по шаблону
                            if (str.matches(parsePatternObject((JSONObject) emp).getKey())) {
                                Pair<String, JSONArray> result = parsePatternObject((JSONObject) emp);
                                int l = result.getRight().toArray().length;
                                System.out.println(result.getRight().get((int) (Math.random() * l)));
                                patternFound = true;
                                break;
                            }
                        }
                        if(!patternFound){
                            //питання типу where why whose what
                            String sentence="";
                            for(int i=3;i<words.length;i++){
                                sentence+=words[i]+" ";
                            }
                            System.out.println("Do you wanna know "+words[0]+" "+changePronouns(words[2])+" "+sentence);
                        }

                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        }
    }


    private static Pair<String,JSONArray> parsePatternObject(JSONObject obj)
    {
        JSONObject patternObject = (JSONObject) obj.get("phrases");
       // System.out.println(patternObject);
        String pattern = (String) patternObject.get("pattern");

        JSONArray answer = (JSONArray) patternObject.get("answer");

        Pair<String,JSONArray> p = new MutablePair<>(pattern,answer);
        return p;

    }

    private static String changePronouns(String word){
        switch (word){
            case "i":
                word="you";
                break;
            case "you":
                word="i";
                break;
            case "myself":
                word="yourself";
                break;
            case "yourself":
                word="myself";
                break;
            case "mine":
                word="yours";
                break;
            case "yours":
                word="mine";
                break;
            case "my":
                word="your";
                break;
            case "your":
                word="my";
                break;
            case "me":
                word="you";
                break;
            default:
                break;
        }
        return word;
    }

//    private static Pair<String,String> parseUnknownPatternObject(JSONObject obj)
//    {
//        System.out.println("Obj is "+obj);
//        JSONObject patternObject = (JSONObject) obj.get("unknownPhrases");
//        System.out.println(patternObject);
//        String pattern = (String) patternObject.get("pattern");
//
//        String answer = (String) patternObject.get("answer");
//
//        Pair<String,String> p = new MutablePair<>(pattern,answer);
//        return p;
//    }


}