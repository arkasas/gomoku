package server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import model.Board;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JSONController {

    ObjectMapper mapper = new ObjectMapper();

    public JSONController() {
    }
    public String parseBoardsToJSON(ArrayList<Board> boards) {
        try {
            String jsonString = mapper.writeValueAsString(boards);
            System.out.println(jsonString);
            return jsonString;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "";
    }

}
