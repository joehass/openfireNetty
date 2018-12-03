package org.jivesoftware.openfire.starter;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public class JiveClassLoader extends URLClassLoader {


    public JiveClassLoader(ClassLoader parent, File libDir) throws MalformedURLException {
        super(new URL[] { libDir.toURI().toURL() }, parent);
        File[] jars = libDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                boolean accept = false;
                String smallName = name.toLowerCase();
                if (smallName.endsWith(".jar")) {
                    accept = true;
                }
                else if (smallName.endsWith(".zip")) {
                    accept = true;
                }
                return accept;
            }
        });

        // Do nothing if no jar or zip files were found
        if (jars == null) {
            return;
        }

        // sort jars otherwise order differs between installations (e.g. it's not alphabetical)
        // order may matter if trying to patch an install by adding patch jar to lib folder
        Arrays.sort(jars);
        for (int i = 0; i < jars.length; i++) {
            if (jars[i].isFile()) {
                addURL(jars[i].toURI().toURL());
            }
        }
    }
}