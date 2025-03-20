package com.househuntersbackend.demo.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.househuntersbackend.demo.entities.Annunci;
import com.househuntersbackend.demo.enumerations.TipoAnnuncio;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AnnuncioUtils {

    @Value("${geoapify.api.key}") private String geoapifyApiKey;
    private final String scuola = "Scuola";
    private final String parchi = "Parchi";
    private final String trasporti = "Trasporti";

    private String urlBuilder(Double latitudine, Double longitudine, String categoria) {
        String url = "https://api.geoapify.com/v2/places?categories=";
        switch (categoria) {
            case scuola:
                url = url + "education";
                break;
            case parchi:
                url = url + "leisure.park";
                break;
            case trasporti:
                url = url + "public_transport";
                break;
            default:
                break;
        }

        url = url + "&filter=circle:" + longitudine + "," + latitudine + ",1000&bias=proximity:" + longitudine + "," + latitudine + "&limit=1&apiKey=" + geoapifyApiKey;

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
            vicinoScuole = isVicino(annuncio.getLatitudine(), annuncio.getLongitudine(), scuola);
            vicinoParchi = isVicino(annuncio.getLatitudine(), annuncio.getLongitudine(), parchi);
            vicinoTrasporti = isVicino(annuncio.getLatitudine(), annuncio.getLongitudine(), trasporti);
        } catch (IOException e) {
            e.printStackTrace();
        }
        annuncio.setVicinoScuole(vicinoScuole);
        annuncio.setVicinoParchi(vicinoParchi);
        annuncio.setVicinoTrasporti(vicinoTrasporti);
    }

    public TipoAnnuncio mappaTipoAnnuncio(String tipo) {
        if(tipo.equals("VENDITA")) {
            return TipoAnnuncio.VENDITA;
        } else {
            return TipoAnnuncio.AFFITTO;
        }
    }
}
