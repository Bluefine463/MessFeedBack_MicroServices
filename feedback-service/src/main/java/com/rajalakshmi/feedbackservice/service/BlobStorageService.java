package com.rajalakshmi.feedbackservice.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class BlobStorageService {

    private final BlobContainerClient containerClient;

    public BlobStorageService(@Value("${azure.storage.connection-string:}") String connectionString,
                              @Value("${azure.storage.container-name:mess-feedback-images}") String containerName) {
        if(connectionString == null || connectionString.isBlank()) {
            this.containerClient = null;
        } else {
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
            this.containerClient = blobServiceClient.getBlobContainerClient(containerName);
            if (!this.containerClient.exists()) this.containerClient.create();
        }
    }

    public String uploadFile(MultipartFile file) throws Exception {
        if (containerClient == null) throw new IllegalStateException("Blob storage not configured");
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        BlobClient blobClient = containerClient.getBlobClient(filename);
        try (InputStream is = file.getInputStream()) {
            blobClient.upload(is, file.getSize(), true);
        }
        return blobClient.getBlobUrl();
    }
}

