package utils;

import java.util.Properties;
import java.util.Set;
import com.google.gson.*;
import dtos.PersonDTO;

import java.io.UnsupportedEncodingException;

public class Utility {
    private static Gson gson = new GsonBuilder().create();
    
    public static void printAllProperties() {
            Properties prop = System.getProperties();
            Set<Object> keySet = prop.keySet();
            for (Object obj : keySet) {
                    System.out.println("System Property: {" 
                                    + obj.toString() + "," 
                                    + System.getProperty(obj.toString()) + "}");
            }
    }
    
    public static PersonDTO json2DTO(String json) throws UnsupportedEncodingException{
            return gson.fromJson(new String(json.getBytes("UTF8")), PersonDTO.class);
    }
    
    public static String DTO2json(PersonDTO personDTO){
        return gson.toJson(personDTO, PersonDTO.class);
    }
    
    public static void main(String[] args) throws UnsupportedEncodingException {
//        printAllProperties();
        
        //Test json2DTO and back again
        String str2 = "{'id':1, 'str1':'Dette er den første tekst', 'str2':'Her er den ANDEN'}";
        PersonDTO personDTO = json2DTO(str2);
        System.out.println(personDTO);
        
        String backAgain = DTO2json(personDTO);
        System.out.println(backAgain);
    }

}

