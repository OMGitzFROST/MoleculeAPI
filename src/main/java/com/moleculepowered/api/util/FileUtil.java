package com.moleculepowered.api.util;

import com.moleculepowered.api.MoleculeAPI;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public final class FileUtil {

    /**
     * Used to return the file name from a string input and will include its file extension
     *
     * @param input Provided input
     * @return The file name from a string path
     */
    public static @NotNull String getFileName(String input) {
        return getFileName(Paths.get(input));
    }

    /**
     * Used to return the file name from a path input and will include its file extension
     *
     * @param input Provided input
     * @return The file name from a string path
     */
    public static @NotNull String getFileName(@NotNull Path input) {
        return input.getFileName().toString();
    }

    /*
    RESOURCE HANDLING
     */

    /**
     * A shorthand method designed to copy a resource from an input stream and into the requested file
     * location, By default, this method will not replace an existing file.
     *
     * @param in Target input stream
     * @param location Target location
     * @see #copy(InputStream, File, boolean)
     */
    public static void copy(@NotNull InputStream in, @NotNull File location) {
        copy(in, location, false);
    }

    /**
     * A shorthand method designed to copy a resource from an input stream and into the requested file
     * location, This method allows you to define whether existing files should be replaced or not.
     *
     * @param in Target input stream
     * @param location Target location
     * @param replace Whether existing files should be replaced
     * @see #copy(InputStream, File)
     */
    public static void copy(@NotNull InputStream in, @NotNull File location, boolean replace) {
        try {
            if (!location.getParentFile().exists() && !location.getParentFile().mkdirs()) {
                throw new IOException("Failed to create parent directory for " + location.getName());
            }

            if (replace) Files.copy(in, location.toPath(), StandardCopyOption.REPLACE_EXISTING);
            else Files.copy(in, location.toPath());
        } catch (FileAlreadyExistsException ignored) {
            // IGNORED
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Returns a resource from within MoleculeAPI, please note that this should not be used to
     * return a resource from within your own plugin's resource folder.
     *
     * @param name Resource name
     * @return A resource from MoleculeAPI
     */
    public static @NotNull InputStream getResource(@NotNull String name) {
        return Objects.requireNonNull(MoleculeAPI.class.getClassLoader().getResourceAsStream(name));
    }

    /**
     * <p>Used to save an internal resource to the specified location. <b>By default, this method will
     * overwrite existing files</b>
     *
     * @param path     Target resource path
     * @param location Target location
     */
    public static void saveResource(String path, File location) {
        saveResource(path, location, true);
    }

    /**
     * Used to save an internal resource to the specified location, with this method you can
     * specify whether we should override an existing file by setting the boolean parameter
     * to true, otherwise set the value to false.
     *
     * @param path     Target resource path
     * @param location Target location
     * @param replace  Whether we should overwrite existing files
     */
    public static void saveResource(String path, File location, boolean replace) {
        copy(getResource(path), location, replace);
    }
}
