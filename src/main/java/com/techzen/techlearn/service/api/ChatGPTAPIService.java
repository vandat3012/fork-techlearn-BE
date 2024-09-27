package com.techzen.techlearn.service.api;

import com.techzen.techlearn.service.AIService;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Primary
public class ChatGPTAPIService implements AIService {

    @Override
    public String callAPI(String mess) throws IOException, InterruptedException {
        System.out.println(mess);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Authorization", "Bearer " + Dotenv.load().get("CHATGPT_API_KEY"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        "{\"model\": \"gpt-4o\","
                                + "\"messages\": [{\"role\": \"user\", \"content\": \"" + mess + "\"}],"
                                + "\"temperature\": 0.7}"
                ))
                .build();
        String response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray choicesArray = jsonResponse.getJSONArray("choices");
        JSONObject firstChoice = choicesArray.getJSONObject(0);
        JSONObject message = firstChoice.getJSONObject("message");
        String content = message.getString("content");
        return content.replaceAll("```html\\n", "").replaceAll("```", "");
    }
}
