package com.househuntersbackend.demo.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.househuntersbackend.demo.entities.Annunci;
import com.househuntersbackend.demo.enumerations.TipoAnnuncio;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AnnuncioUtils {
    private static final Logger logger = LoggerFactory.getLogger(AnnuncioUtils.class);

    @Value("${geoapify.api.key}") private String geoapifyApiKey;
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
            logger.error("Errore durante l'operazione: {}", e.getMessage(), e);
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
            logger.error("Errore durante l'operazione: {}", e.getMessage(), e);
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
