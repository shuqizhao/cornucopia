package cornucopia.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cornucopia.entity.JsonResult;
import cornucopia.util.AppSettings;

@RestController
public class UploadController {
	@RequestMapping(value = { "/upload" }, method = RequestMethod.POST)
	public JsonResult<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) {
		int code = 500;
		String guid = java.util.UUID.randomUUID().toString();
		String filePath = AppSettings.getInstance(AppSettings.class).get("filesPath");
		String message = "";
		if (!file.isEmpty()) {
			try {
				File dir = new File(filePath + "/" + guid);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				BufferedOutputStream out = new BufferedOutputStream(
						new FileOutputStream(new File(filePath + "/" + guid + "/" + file.getOriginalFilename())));
				out.write(file.getBytes());
				out.flush();
				out.close();
				code = 200;
				message = id;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				message = e.getMessage();
			} catch (IOException e) {
				e.printStackTrace();
				message = e.getMessage();
			}
		} else {
			message = "file is empty";
		}
		JsonResult<String> jr = new JsonResult<String>();
		jr.setCode(code);
		jr.setMessage(message);
		jr.setData(guid);
		return jr;
	}
}
