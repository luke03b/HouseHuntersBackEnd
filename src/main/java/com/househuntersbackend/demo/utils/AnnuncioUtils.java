package com.househuntersbackend.demo.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.househuntersbackend.demo.entities.Annunci;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class AnnuncioUtils {
    private static final String GEOAPIFY_API_KEY = "733bfdc35e7a4882b57949a1fd6bbf26";
    private static final String SCUOLA = "Scuola";
    private static final String PARCHI = "Parchi";
    private static final String TRASPORTI = "Trasporti";

    private String urlBuilder(Double latitudine, Double longitudine, String categoria) {
        String url = "https://api.geoapify.com/v2/places?categories=";
        switch (categoria) {
            case SCUOLA:
                url = url + "education";
                break;
            case PARCHI:
                url = url + "leisure.park";
                break;
            case TRASPORTI:
                url = url + "public_transport";
                break;
            default:
                break;
        }

        url = url + "&filter=circle:" + longitudine + "," + latitudine + ",1000&bias=proximity:" + longitudine + "," + latitudine + "&limit=1&apiKey=" + GEOAPIFY_API_KEY;

        return url;
    }

    private boolean isVicino(Double latitudine, Double longitudine, String categoria) throws IOException {
        if (latitudine == null || longitudine == null) {
            return false;
        }

        OkHttpClient client = new OkHttpClient();
        String url = urlBuilder(latitudine, longitudine, categoria);

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.body().string());

                JsonNode featuresNode = rootNode.get("features");
                return featuresNode != null && featuresNode.isArray() && featuresNode.size() > 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null && response.body() != null) {
                response.body().close();
            }
        }
        return false;
    }

    public void setVicinanze(Annunci annuncio) {
        boolean vicinoScuole = false;
        boolean vicinoParchi = false;
        boolean vicinoTrasporti = false;
        try {
            vicinoScuole = isVicino(annuncio.getLatitudine(), annuncio.getLongitudine(), SCUOLA);
            vicinoParchi = isVicino(annuncio.getLatitudine(), annuncio.getLongitudine(), PARCHI);
            vicinoTrasporti = isVicino(annuncio.getLatitudine(), annuncio.getLongitudine(), TRASPORTI);
        } catch (IOException e) {
            e.printStackTrace(); // Gestisci l'eccezione
            // Puoi anche loggare l'errore o intraprendere altre azioni
        }
        annuncio.setVicinoScuole(vicinoScuole);
        annuncio.setVicinoParchi(vicinoParchi);
        annuncio.setVicinoTrasporti(vicinoTrasporti);
    }
}
