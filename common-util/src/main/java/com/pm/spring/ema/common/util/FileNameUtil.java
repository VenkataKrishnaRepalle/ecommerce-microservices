package com.pm.spring.ema.common.util;

import java.nio.file.Path;

public class FileNameUtil {

  public static String getFileExtension(String fileName) {
    Path path = Path.of(fileName);
    String extension = "";

    if (fileName.contains(".")) {
      extension = path.getFileName().toString().substring(fileName.lastIndexOf(".") + 1);
    }

    return extension;
  }
}
