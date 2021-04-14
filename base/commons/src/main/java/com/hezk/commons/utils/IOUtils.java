package com.hezk.commons.utils;

/**
 * Copied from {@link org.apache.commons.io.IOUtils}
 */

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class IOUtils {

    public static IOException close(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            return ioe;
        }
        return null;
    }

    // support jdk6
    public static IOException close(final ZipFile zip) {
        try {
            if (zip != null) {
                zip.close();
            }
        } catch (final IOException ioe) {
            return ioe;
        }
        return null;
    }

    public static boolean isSubFile(File parent, File child) throws IOException {
        return child.getCanonicalPath().startsWith(parent.getCanonicalPath() + File.separator);
    }

    public static boolean isSubFile(String parent, String child) throws IOException {
        return isSubFile(new File(parent), new File(child));
    }

    public static void unzip(String zipFile, String extractFolder) throws IOException {
        File file = new File(zipFile);
        ZipFile zip = null;
        try {
            int BUFFER = 1024 * 8;

            zip = new ZipFile(file);
            File newPath = new File(extractFolder);
            newPath.mkdirs();

            Enumeration<? extends ZipEntry> zipFileEntries = zip.entries();

            // Process each entry
            while (zipFileEntries.hasMoreElements()) {
                // grab a zip file entry
                ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
                String currentEntry = entry.getName();

                File destFile = new File(newPath, currentEntry);
                if (!isSubFile(newPath, destFile)) {
                    throw new IOException("Bad zip entry: " + currentEntry);
                }

                // destFile = new File(newPath, destFile.getName());
                File destinationParent = destFile.getParentFile();

                // create the parent directory structure if needed
                destinationParent.mkdirs();

                if (!entry.isDirectory()) {
                    BufferedInputStream is = null;
                    BufferedOutputStream dest = null;
                    try {
                        is = new BufferedInputStream(zip.getInputStream(entry));
                        int currentByte;
                        // establish buffer for writing file
                        byte data[] = new byte[BUFFER];

                        // write the current file to disk
                        FileOutputStream fos = new FileOutputStream(destFile);
                        dest = new BufferedOutputStream(fos, BUFFER);

                        // read and write until last byte is encountered
                        while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                            dest.write(data, 0, currentByte);
                        }
                        dest.flush();
                    } finally {
                        close(dest);
                        close(is);
                    }
                }
            }
        } finally {
            close(zip);
        }
    }
}
