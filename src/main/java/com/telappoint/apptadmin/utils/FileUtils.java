package com.telappoint.apptadmin.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Murali G
 * 
 */
public class FileUtils {
		
	public static int copyFile(InputStream source, OutputStream dest, int bufferSize) throws IOException {
		byte[] buffer = new byte[bufferSize];

		int c, total = 0;
		while ((c = source.read(buffer)) > 0) {
			dest.write(buffer, 0, c);
			dest.flush();
			total += c;
		}
		dest.flush();
		return total;
	}
}
