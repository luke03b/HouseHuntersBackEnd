package com.househuntersbackend.demo.controllers;

import com.househuntersbackend.demo.entities.Immagini;
import com.househuntersbackend.demo.services.ImmaginiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@RestController
@RequestMapping("/api/immagini")
public class ImmaginiController {

    private final ImmaginiService immaginiService;

    public ImmaginiController(ImmaginiService immaginiService) { this.immaginiService = immaginiService; }

    @Value("${aws.s3.access-key}") String accessKey;
    @Value("${aws.s3.secret-key}") String secretKey;
    @Value("${aws.s3.region}") String region;
    @Value("${aws.s3.bucket-name}") String bucketName;


    @GetMapping("/presigned-url")
    public String generatePresignedUrl(@RequestParam String fileName) {
        // Configura il presigner
        S3Presigner presigner = S3Presigner.builder()
                .region(Region.EU_CENTRAL_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                .build();

        // Crea la richiesta di upload
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        // Configura la richiesta di presigned URL
        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(5)) // 5 minuti di validità
                .putObjectRequest(putObjectRequest)
                .build();


        // Genera il presigned URL
        PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
        String url = presignedRequest.url().toString();

        // Chiudi il presigner
        presigner.close();

        return url;
    }

    @PostMapping("/save")
    public ResponseEntity<Immagini> createImmagini(@RequestBody Immagini immagini) {
        Immagini newImmagini = immaginiService.createImmagini(immagini);
        return new ResponseEntity<>(newImmagini, HttpStatus.CREATED);
    }
}