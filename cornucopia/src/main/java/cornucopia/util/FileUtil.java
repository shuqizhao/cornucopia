package cornucopia.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
	public static String Store(String name, byte[] bytes) throws IOException {
		String guid = java.util.UUID.randomUUID().toString();
		guid = guid.replaceAll("-", "");
		String filePath = AppSettings.getInstance(AppSettings.class).get("filesPath");
		File dir = new File(filePath + "/" + guid);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(new File(filePath + "/" + guid + "/" + name)));
		out.write(bytes);
		out.flush();
		out.close();
		return guid;
	}

	public static FileEntity GetBytes(String guid) throws IOException {
		FileEntity fileEntity = new FileEntity();
		String filePath = AppSettings.getInstance(AppSettings.class).get("filesPath");
		File dir = new File(filePath + "/" + guid);
		if (dir.exists()) {
			File[] files = dir.listFiles();
			if (files.length != 1) {
				return null;
			}
			File file = files[0];
			String name = file.getName();
			FileInputStream is = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			BufferedInputStream in = new BufferedInputStream(is);
			in.read(bytes);
			in.close();
			is.close();
			fileEntity.setName(name);
			fileEntity.setBytes(bytes);
		}else {
			return null;
		}
		return fileEntity;
	}
	
	public static FileEntity GetStream(String guid) throws IOException {
		FileEntity fileEntity = new FileEntity();
		String filePath = AppSettings.getInstance(AppSettings.class).get("filesPath");
		File dir = new File(filePath + "/" + guid);
		if (dir.exists()) {
			File[] files = dir.listFiles();
			if (files.length != 1) {
				return null;
			}
			File file = files[0];
			FileInputStream is = new FileInputStream(file);
			fileEntity.setName(file.getName());
			fileEntity.setIs(is);
		}else {
			return null;
		}
		return fileEntity;
	}
}
