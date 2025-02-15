package io.github.amiccoli.gherkindoctor.util;

import io.github.amiccoli.gherkindoctor.exception.InvalidFileException;
import io.github.amiccoli.gherkindoctor.reader.FeatureReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtil {

    public static List<Path> findPaths(String location) {
        Path path = FileUtil.getRelativePath(location);

        try (val stream = Files.walk(path)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(Files::isReadable)
                    .filter(p -> p.toString().endsWith(".feature"))
                    .toList();
        } catch (IOException exception) {
            throw new InvalidFileException("Not capable to walk through [%s]."
                    .formatted(exception.getMessage())
            );
        }
    }

    public static String readFile(Path inputPath) {
        try {
            return Files.readString(inputPath);

        } catch (IOException exception) {
            throw new InvalidFileException("Not capable to read file [%s]."
                    .formatted(exception.getMessage())
            );
        }
    }

    private static Path getRelativePath(String inputPath) {
        var resourceUrl = FeatureReader.class.getClassLoader().getResource(inputPath);

        if (resourceUrl == null) {
            throw new InvalidFileException("Resource not found for path [%s]."
                    .formatted(inputPath)
            );
        }

        return Path.of(resourceUrl.getPath());
    }
}
