package com.example.service;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobHttpHeaders;
import io.javalin.http.UploadedFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class PhotoStorageService {

    private final BlobContainerClient containerClient;

    public PhotoStorageService(BlobContainerClient containerClient) {
        this.containerClient = containerClient;
    }

    public String upload(UploadedFile file) {
        if (file == null) {
            throw new IllegalArgumentException("Photo is required");
        }

        String contentType = file.contentType();

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Uploaded file must be an image");
        }

        String originalName = file.filename();
        String extension = "";

        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }

        String blobName = UUID.randomUUID() + extension;
        BlobClient blobClient = containerClient.getBlobClient(blobName);

        try (InputStream inputStream = file.content()) {
            blobClient.upload(BinaryData.fromStream(inputStream, file.size()), true);
            blobClient.setHttpHeaders(new BlobHttpHeaders().setContentType(contentType));
            return blobName;
        } catch (IOException e) {
            throw new RuntimeException("Could not upload photo", e);
        }
    }

    public byte[] download(String blobName) {
        return containerClient.getBlobClient(blobName).downloadContent().toBytes();
    }

    public void delete(String blobName) {
        if (blobName == null || blobName.isBlank()) {
            return;
        }

        containerClient.getBlobClient(blobName).deleteIfExists();
    }
}