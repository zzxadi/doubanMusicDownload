package com.download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class DownloadTest {
	public static void main(String args[]) {
		long start  = System.currentTimeMillis();
		String path = "f://music";
		File dir = new File(path);
		BufferedReader br = null;

		String[] fines = dir.list();
		Map<String, String> kv = new TreeMap<String, String>();
		String line = null;
		String name = null;
		String url = null;
		String pattern = ".mp3";
		Map.Entry<String, String> entry = null;
		Set<Map.Entry<String, String>> set = null;
		Download download = null;
		try {
			for (int i = 0, len = fines.length; i < len; i++) {
				String fileName = fines[i];
				File file = new File(path + File.separator + fileName);
				br = new BufferedReader(new FileReader(file));

				while ((line = br.readLine()) != null) {
					if ((line.indexOf(pattern) != -1)
							&& (line.indexOf("http") != -1)) {
						name = line.substring(line.indexOf("/") + 1,
								line.indexOf('.', line.indexOf("/")));
						System.out.println("name = " + name);
						url = line.substring(line.indexOf("http"));
						System.out.println("url = " + url);
						kv.put(name.trim(), url.trim());
					}
				}
				set = kv.entrySet();
				for (Iterator<Map.Entry<String, String>> it = set.iterator(); it
						.hasNext();) {
					entry = (Map.Entry<String, String>) it.next();
					download = new Download(new URL(entry.getValue()),
							entry.getKey() + ".mp3");
					download.run();
				}
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(System.currentTimeMillis() - start);
	}
}
