
package com.jobportal.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import tools.jackson.databind.JsonNode;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private final RestClient restClient;
    private final String apiKey;
    private final String model;
    private final String fallbackModel;

    public GeminiService(
            RestClient.Builder builder,
            @Value("${gemini.api-key}") String apiKey,
            @Value("${gemini.model}") String model,
            @Value("${gemini.fallback-model}") String fallbackModel,
            @Value("${gemini.base-url}") String baseUrl) {

        this.restClient = builder
                .baseUrl(baseUrl)
                .build();

        this.apiKey = apiKey;
        this.model = model;
        this.fallbackModel = fallbackModel;
    }

    public String generateContent(String prompt) {

        if (prompt == null || prompt.isBlank()) {
            throw new IllegalArgumentException(
                    "Prompt cannot be empty"
            );
        }

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "Gemini API key is missing"
            );
        }

        // First attempt: Primary Gemini model
        try {
            return generateWithRetry(model, prompt);

        } catch (RestClientResponseException exception) {

            // Only use fallback for server-side 503 errors
            if (exception.getStatusCode().value() != 503) {
                throw createGeminiException(exception);
            }

            System.err.println(
                    "Primary Gemini model unavailable. "
                            + "Switching to fallback model: "
                            + fallbackModel
            );
        }

        // Second attempt: Fallback Gemini model
        try {
            return generateWithRetry(fallbackModel, prompt);

        } catch (RestClientResponseException exception) {

            throw createGeminiException(exception);
        }
    }

    private String generateWithRetry(
            String selectedModel,
            String prompt
    ) {

        Map<String, Object> requestBody = Map.of(
                "contents",
                List.of(
                        Map.of(
                                "parts",
                                List.of(
                                        Map.of("text", prompt)
                                )
                        )
                )
        );

        int maxAttempts = 3;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {

            try {

                JsonNode response = restClient.post()
                        .uri(
                                "/v1beta/models/{model}:generateContent",
                                selectedModel
                        )
                        .header("x-goog-api-key", apiKey)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(requestBody)
                        .retrieve()
                        .body(JsonNode.class);

                if (response == null) {
                    throw new IllegalStateException(
                            "Empty response received from Gemini"
                    );
                }

                JsonNode textNode = response
                        .path("candidates")
                        .path(0)
                        .path("content")
                        .path("parts")
                        .path(0)
                        .path("text");

                if (textNode.isMissingNode() ||
                        textNode.isNull()) {

                    throw new IllegalStateException(
                            "Gemini returned no text content: "
                                    + response
                    );
                }

                return textNode.asText();

            } catch (RestClientResponseException exception) {

                boolean is503 =
                        exception.getStatusCode().value() == 503;

                if (!is503 || attempt == maxAttempts) {
                    throw exception;
                }

                long delay = 1000L * (1L << (attempt - 1));

                System.err.println(
                        "Gemini returned 503. Retry "
                                + attempt + "/"
                                + maxAttempts
                                + " after "
                                + delay + " ms."
                );

                try {
                    Thread.sleep(delay);

                } catch (InterruptedException interruptedException) {

                    Thread.currentThread().interrupt();

                    throw new IllegalStateException(
                            "Gemini retry interrupted",
                            interruptedException
                    );
                }
            }
        }

        throw new IllegalStateException(
                "Gemini request failed after all retry attempts"
        );
    }

    private IllegalStateException createGeminiException(
            RestClientResponseException exception
    ) {

        return new IllegalStateException(
                "Gemini API error. HTTP Status: "
                        + exception.getStatusCode()
                        + ". Details: "
                        + exception.getResponseBodyAsString(),
                exception
        );
    }
}
