package com.ticketfly.test.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


import org.apache.log4j.Logger;

/**
 * This class is a utility for searching resources in classpath (including jars that are added as dependency)
 * 
 **/
public class ClassPathSearcher {
	private static Logger log = Logger.getLogger(ClassPathSearcher.class);

	
	/**
	 * Returns the Map with filename and inputstream base on the filename pattern name passed
	 * example .*objectdata.*.xml
	 * 
	 * @param fileNamePattern
	 * @return Map with total filepath and input stream
	 * 
	 */
	public Map<String, InputStream> findFilesInClassPath(String fileNamePattern) {
		Map<String, InputStream> result = new TreeMap<String, InputStream>();
		String classPath = System.getProperty("java.class.path");
		String[] pathElements = classPath.split(System
				.getProperty("path.separator"));
		for (String element : pathElements) {
			log.debug(element);
			try {
				File newFile = new File(element);
				if (newFile.isDirectory()) {
					result.putAll(findResourceInDirectory(newFile,
							fileNamePattern));
				} else {
					result.putAll(findResourceInFile(newFile, fileNamePattern));
				}
			} catch (IOException e) {
				log.error("Exception:", e);
			}
		}
		return result;
	}
	
	/**
	 * private method used by findFilesInClassPath to find files in a jar
	 * @param File object
	 * @param fileNamePattern
	 * @return Map with total filepath and input stream
	 * 
	 */
	

	private Map<String, InputStream> findResourceInFile(File resourceFile,
			String fileNamePattern) throws IOException {
		Map<String, InputStream> result = new TreeMap<String, InputStream>();
		if (resourceFile.canRead()
				&& resourceFile.getAbsolutePath().endsWith(".jar")) {
			log.debug("jar file found: " + resourceFile.getAbsolutePath());
			JarFile jarFile = new JarFile(resourceFile);
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry singleEntry = entries.nextElement();
				log.debug("jar entry: " + singleEntry.getName());
				if (singleEntry.getName().matches(fileNamePattern)) {
					result.put(jarFile.getName() + "/" + singleEntry.getName(),
							jarFile.getInputStream(singleEntry));
				}
			}
		}
		return result;
	}
	
	/**
	 * private method used by findFilesInClassPath to find files in a DIRECTORY
	 * @param File object
	 * @param fileNamePattern
	 * @return Map with total filepath and input stream
	 * 
	 */


	private Map<String, InputStream> findResourceInDirectory(File directory,
			String fileNamePattern) throws IOException {
		Map<String, InputStream> result = new TreeMap<String, InputStream>();
		File[] files = directory.listFiles();
		for (File currentFile : files) {
			log.debug("current file name: " + currentFile.getAbsolutePath());
			if (currentFile.getAbsolutePath().matches(fileNamePattern)) {
				result.put(currentFile.getAbsolutePath(), new FileInputStream(
						currentFile));
			} else if (currentFile.isDirectory()) {

				result.putAll(findResourceInDirectory(currentFile,
						fileNamePattern));
			} else {
				result.putAll(findResourceInFile(currentFile, fileNamePattern));
			}
		}
		return result;
	}

//	public static void main(String[] args) {
//		ClassPathSearcher searcher = new ClassPathSearcher();
//		
//		System.out.println(System.getProperty("path.separator"));
//		//Map<String, InputStream> foundFiles = searcher.findFilesInClassPath(".*objectdata.*.xml");
//		Map<String, InputStream> foundFiles = searcher.findFilesInClassPath(".*objectdata.*.xml");
//		for (String key : foundFiles.keySet()) {
//			System.out.println("filename: " + key);
//		}
//		
//	}

}
