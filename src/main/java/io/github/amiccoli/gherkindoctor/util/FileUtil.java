/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.amiccoli.gherkindoctor.util;

import io.github.amiccoli.gherkindoctor.exception.InvalidFileException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtil {

    /**
     * Finds and returns a list of all readable `.feature` files within the specified location.
     * The search is performed recursively, including all subdirectories.
     *
     * @param location the root directory to start searching for `.feature` files
     * @return a list of {@link Path} objects representing the found `.feature` files
     * @throws InvalidFileException if an I/O error occurs while walking the file tree
     */
    public static List<Path> findPaths(String location) {
        val path = FileUtil.getRelativePath(location);

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

    /**
     * Reads the content of a file at the specified path and returns it as a String.
     *
     * @param inputPath the {@link Path} to the file to be read
     * @return the content of the file as a {@link String}
     * @throws InvalidFileException if an I/O error occurs while reading the file
     */
    public static String readFile(Path inputPath) {
        try {
            return Files.readString(inputPath);
        } catch (IOException exception) {
            throw new InvalidFileException("Not capable to read file [%s]."
                    .formatted(exception.getMessage())
            );
        }
    }

    /**
     * Retrieves an {@link InputStream} for the specified file path.
     * <p>
     * This method attempts to load the file as a resource using the class loader.
     * If the file is not found, an {@link InvalidFileException} is thrown.
     * </p>
     *
     * @param inputPath the path to the resource file.
     * @return an {@link InputStream} for reading the file.
     * @throws IOException if an I/O error occurs while accessing the file.
     * @throws InvalidFileException if the file cannot be found.
     */
    public static InputStream getInputStream(String inputPath) throws IOException {
        val inputStream = FileUtil.class.getClassLoader().getResourceAsStream(inputPath);

        if (inputStream == null) {
            throw new InvalidFileException("File not found: [%s].".formatted(inputPath));
        }

        return inputStream;
    }

    private static Path getRelativePath(String inputPath) {
        val resourceUrl = FileUtil.class.getClassLoader().getResource(inputPath);

        if (resourceUrl == null) {
            throw new InvalidFileException("Resource not found for path [%s]."
                    .formatted(inputPath)
            );
        }

        return Path.of(resourceUrl.getPath());
    }
}
