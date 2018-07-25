package com.company.project.utilities;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonUtilities {

  public String jsonToString(String filepath) {
    JSONParser parser = new JSONParser();
    JSONObject jsonObject = null;

    try {
      Object obj = parser.parse(new FileReader(filepath));
      jsonObject = (JSONObject) obj;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return jsonObject.toString();
  }

  //  ************   READ FROM JSON EXAMPLES - JSON SIMPLE   ************  //

  //    {
  //        "age":29,
  //            "name":"test",
  //            "messages":["msg 1","msg 2","msg 3"]
  //    }

  //    JSONParser parser = new JSONParser();
  //    Object obj = parser.parse(new FileReader("JSON FILE PATH"));
  //    JSONObject jsonObject = (JSONObject) obj;

  //    String name = (String) jsonObject.get("name");
  //    long age = (Long) jsonObject.get("age");

  //    JSONArray msg = (JSONArray) jsonObject.get("messages");
  //    Iterator<String> iterator = msg.iterator();
  //            while (iterator.hasNext()) {
  //        System.out.println(iterator.next());
  //    }

  //  ************  WRITE TO JSON EXAMPLES -- JSON SIMPLE   ************  //

  // import org.json.simple.JSONArray;
  // import org.json.simple.JSONObject;
  // import java.io.FileWriter;
  // import java.io.IOException;

  //    JSONObject obj = new JSONObject();
  //        obj.put("name", "mkyong.com");
  //        obj.put("age", new Integer(100));

  //    JSONArray list = new JSONArray();
  //        list.add("msg 1");
  //        list.add("msg 2");
  //        list.add("msg 3");
  //        obj.put("messages", list);

  //      FileWriter file = new FileWriter("f:\\test.json")
  //        file.write(obj.toJSONString());
  //        file.flush();
  //        System.out.print(obj);

  //    Output
  //
  //    f:\\test.json
  //    {
  //        "age":100,
  //            "name":"mkyong.com",
  //            "messages":["msg 1","msg 2","msg 3"]
  //    }

  //  ************  UPDATE EXISTING JSON  -- JSON SIMPLE   ************  //

  //    JSONParser parser = new JSONParser();
  //    Object obj = parser.parse(new FileReader("JSON FILE PATH"));
  //    JSONObject json = (JSONObject) obj;
  //
  //
  //        json.put("startDate", ""+date);
  //        json.put("endDate", date);
  //        json.put("campaignName", "campaign"+random);
  //        json.put("notificationTitle", "title"+random);
  //        json.put("notificationMessage", "message"+random);

}
