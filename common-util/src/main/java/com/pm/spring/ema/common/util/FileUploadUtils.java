package com.pm.spring.ema.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class FileUploadUtils {

  public static void cleanDir(String dir) {
    Path directoryPath = Paths.get(dir);

    try {
      Files.list(directoryPath)
          .forEach(
              file -> {
                if (!Files.isDirectory(file)) {
                  try {
                    Files.delete(file);
                  } catch (IOException ioException) {
                    System.out.println("Could not delete file: " + file);
                  }
                }
              });
    } catch (IOException ioException) {
      System.out.println("Could not list directory");
    }
  }

  public static void saveFile(String uploadDir, String fileName, InputStream inputStream)
      throws IOException {
    Path uploadPath = Paths.get(uploadDir);

    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    try {
      Path filePath = uploadPath.resolve(fileName);
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException ioException) {
      throw new IOException("Could not save file");
    }
  }

  public static void removeDir(String dir) {
    Path dirPath = Paths.get(dir);

    try {
      Files.list(dirPath)
          .forEach(
              file -> {
                if (!Files.isDirectory(file)) {
                  try {
                    Files.delete(file);
                  } catch (IOException ioException) {
                    System.out.println("Could not delete file: " + file);
                  }
                }
              });
    } catch (IOException ioException) {
      System.out.println("Could not list directory");
    }
  }

  public static Optional<String> getExtensionByString(String filename) {
    return Optional.ofNullable(filename)
        .filter(f -> f.contains("."))
        .map(f -> f.substring(filename.lastIndexOf(".") + 1));
  }
}
