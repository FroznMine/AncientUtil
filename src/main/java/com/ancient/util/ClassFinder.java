package com.ancient.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public class ClassFinder {
	private static final char DOT = '.';
	private static final char SLASH = '/';
	private static final String CLASS_SUFFIX = ".class";
    private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

	public static List<Class<?>> find(String scannedPackage) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String scannedPath = scannedPackage.replace(DOT, SLASH);
		Enumeration<URL> resources = null;
		try {
			resources = classLoader.getResources(scannedPath);
		} catch (IOException e) {
			throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage), e);
		}
		List<Class<?>> classes = new LinkedList<Class<?>>();
		while (resources.hasMoreElements()) {
			File file = new File(resources.nextElement().getFile());
			classes.addAll(find(file, scannedPackage));
		}
		return classes;
	}

	private static List<Class<?>> find(File file, String scannedPackage) {
		List<Class<?>> classes = new LinkedList<Class<?>>();
		String resource = scannedPackage + DOT + file.getName();
		if (file.isDirectory())
			for (File nestedFile : file.listFiles())
				classes.addAll(find(nestedFile, scannedPackage));
		else if (resource.endsWith(CLASS_SUFFIX)) {
			int beginIndex = 0;
			int endIndex = resource.length() - CLASS_SUFFIX.length();
			String className = resource.substring(beginIndex, endIndex);
			try {
				classes.add(Class.forName(className));
			} catch (ClassNotFoundException e) {}
		}
		return classes;
	}
}