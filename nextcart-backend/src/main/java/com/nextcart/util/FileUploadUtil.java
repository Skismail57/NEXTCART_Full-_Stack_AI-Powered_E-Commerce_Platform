package com.nextcart.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class FileUploadUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);
    private static final String UPLOAD_DIR;
    private static final long MAX_FILE_SIZE;
    private static final List<String> ALLOWED_EXTENSIONS;

    static {
        try {
            Properties props = new Properties();
            InputStream inputStream = FileUploadUtil.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");

            if (inputStream == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }

            props.load(inputStream);
            UPLOAD_DIR = props.getProperty("app.upload.dir", "uploads");
            MAX_FILE_SIZE = Long.parseLong(props.getProperty("app.max-file-size", "10485760"));
            String allowedTypes = props.getProperty("upload.allowed-types", "jpg,jpeg,png,webp");
            ALLOWED_EXTENSIONS = Arrays.asList(allowedTypes.split(","));

            createUploadDirectory();

        } catch (IOException e) {
            logger.error("Failed to load file upload configuration", e);
            throw new RuntimeException("Failed to load file upload configuration", e);
        }
    }

    private FileUploadUtil() {
        // Private constructor to prevent instantiation
    }

    private static void createUploadDirectory() {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
                logger.info("Upload directory created: {}", uploadPath.toAbsolutePath());
            } catch (IOException e) {
                logger.error("Failed to create upload directory", e);
                throw new RuntimeException("Failed to create upload directory", e);
            }
        }
    }

    public static String uploadFile(InputStream inputStream, String originalFileName) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("Input stream cannot be null");
        }

        // Read the entire stream to check file size first
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }
        byte[] fileBytes = baos.toByteArray();
        baos.close();

        // Check file size
        if (fileBytes.length > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File too large. Max allowed size: " + (MAX_FILE_SIZE / 1024 / 1024) + "MB");
        }

        String extension = getFileExtension(originalFileName);
        if (!isAllowedExtension(extension)) {
            throw new IllegalArgumentException("File type not allowed. Allowed types: " + String.join(", ", ALLOWED_EXTENSIONS));
        }

        String uniqueFileName = UUID.randomUUID().toString() + "." + extension;
        Path filePath = Paths.get(UPLOAD_DIR, uniqueFileName);

        Files.copy(new ByteArrayInputStream(fileBytes), filePath, StandardCopyOption.REPLACE_EXISTING);
        logger.info("File uploaded successfully: {}", filePath.toAbsolutePath());

        return uniqueFileName;
    }

    public static boolean deleteFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return false;
        }

        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        try {
            boolean deleted = Files.deleteIfExists(filePath);
            if (deleted) {
                logger.info("File deleted successfully: {}", filePath.toAbsolutePath());
            }
            return deleted;
        } catch (IOException e) {
            logger.error("Failed to delete file: {}", fileName, e);
            return false;
        }
    }

    private static String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    private static boolean isAllowedExtension(String extension) {
        return ALLOWED_EXTENSIONS.contains(extension.toLowerCase());
    }

    public static Path getFilePath(String fileName) {
        return Paths.get(UPLOAD_DIR, fileName);
    }
}
