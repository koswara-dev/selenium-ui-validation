package com.juaracoding.utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class OpenAiVision {

    private static Dotenv dotenv = Dotenv.load();
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = dotenv.get("API_KEY");

    public static String extractTextFromImage(String imagePath) throws Exception {
        // Load image and convert to Base64
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        // Create JSON request body
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("model", "gpt-4o");
        jsonRequest.put("max_tokens", 300);

        JSONArray messages = new JSONArray();
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");

        JSONArray contentArray = new JSONArray();
        JSONObject textContent = new JSONObject();
        textContent.put("type", "text");
        textContent.put("text", "What’s in this image?");
        contentArray.put(textContent);

        JSONObject imageContent = new JSONObject();
        imageContent.put("type", "image_url");
        JSONObject imageUrl = new JSONObject();
        imageUrl.put("url", "data:image/jpeg;base64," + base64Image);
        imageContent.put("image_url", imageUrl);
        contentArray.put(imageContent);

        userMessage.put("content", contentArray);
        messages.put(userMessage);
        jsonRequest.put("messages", messages);

        // Create HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest.toString()))
                .build();

        // Send request and get response
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JSONObject jsonResponse = new JSONObject(response.body());
            return jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
        } else {
            throw new RuntimeException("Failed to extract text: " + response.body());
        }
    }

    public static String compareImages(String imagePath1, String imagePath2) throws Exception {
        // Load images and convert to Base64
        byte[] imageBytes1 = Files.readAllBytes(Paths.get(imagePath1));
        String base64Image1 = Base64.getEncoder().encodeToString(imageBytes1);

        byte[] imageBytes2 = Files.readAllBytes(Paths.get(imagePath2));
        String base64Image2 = Base64.getEncoder().encodeToString(imageBytes2);

        // Create JSON request body
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("model", "gpt-4o");
        jsonRequest.put("max_tokens", 300);

        JSONArray messages = new JSONArray();
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");

        JSONArray contentArray = new JSONArray();
        JSONObject textContent = new JSONObject();
        textContent.put("type", "text");
        textContent.put("text", "What are in these images? Is there any difference between them? " +
                "Then add keyword true if same images or false if images is different and add keyword percentage similarity between them " +
                "with format isSameImage: , similarity: ");
        contentArray.put(textContent);

        JSONObject imageContent1 = new JSONObject();
        imageContent1.put("type", "image_url");
        JSONObject imageUrlObject1 = new JSONObject();
        imageUrlObject1.put("url", "data:image/jpeg;base64," + base64Image1);
        imageContent1.put("image_url", imageUrlObject1);
        contentArray.put(imageContent1);

        JSONObject imageContent2 = new JSONObject();
        imageContent2.put("type", "image_url");
        JSONObject imageUrlObject2 = new JSONObject();
        imageUrlObject2.put("url", "data:image/jpeg;base64," + base64Image2);
        imageContent2.put("image_url", imageUrlObject2);
        contentArray.put(imageContent2);

        userMessage.put("content", contentArray);
        messages.put(userMessage);
        jsonRequest.put("messages", messages);

        // Create HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest.toString()))
                .build();

        // Send request and get response
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JSONObject jsonResponse = new JSONObject(response.body());
            return jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
        } else {
            throw new RuntimeException("Failed to compare images: " + response.body());
        }
    }

    public static String isSameImage(String responseText){
        // Extracting isSameImage
        String isSameImageKey = "isSameImage: ";
        int isSameImageStart = responseText.indexOf(isSameImageKey) + isSameImageKey.length();
        int isSameImageEnd = responseText.indexOf(",", isSameImageStart);
        return responseText.substring(isSameImageStart, isSameImageEnd).trim();
    }

    public static String similarity(String responseText){
        // Extracting similarity
        String similarityKey = "similarity: ";
        int similarityStart = responseText.indexOf(similarityKey) + similarityKey.length();
        int similarityEnd = responseText.indexOf("%", similarityStart);
        return responseText.substring(similarityStart, similarityEnd).trim();
    }

}


