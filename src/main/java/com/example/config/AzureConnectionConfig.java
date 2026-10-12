package com.example.config;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;

public class AzureConnectionConfig {

    private static final String ACCOUNT_NAME = System.getenv("AZURE_STORAGE_ACCOUNT_NAME");
    private static final String ACCOUNT_KEY = System.getenv("AZURE_STORAGE_ACCOUNT_KEY");
    private static final String CONTAINER_NAME = "profile-photos";

    public static BlobContainerClient getContainerClient() {
        if (ACCOUNT_NAME == null || ACCOUNT_NAME.isBlank()
                || ACCOUNT_KEY == null || ACCOUNT_KEY.isBlank()) {
            throw new IllegalStateException("AZURE_STORAGE_ACCOUNT_NAME and AZURE_STORAGE_ACCOUNT_KEY must be set");
        }

        StorageSharedKeyCredential credential = new StorageSharedKeyCredential(ACCOUNT_NAME, ACCOUNT_KEY);

        BlobServiceClient serviceClient = new BlobServiceClientBuilder()
                .endpoint("https://" + ACCOUNT_NAME + ".blob.core.windows.net")
                .credential(credential)
                .buildClient();

        return serviceClient.getBlobContainerClient(CONTAINER_NAME);
    }
}