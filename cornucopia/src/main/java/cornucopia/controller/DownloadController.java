package cornucopia.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cornucopia.util.FileEntity;
import cornucopia.util.FileUtil;

@Controller
public class DownloadController {
	@RequestMapping(value = { "/download" }, method = RequestMethod.GET)
	public void download(HttpServletResponse res, @RequestParam("id") String id) throws IOException {
		FileEntity fileEntity = FileUtil.GetStream(id);

		res.setHeader("content-type", "application/octet-stream");
		res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		res.setContentType("application/octet-stream");
		res.setHeader("Pragma", "no-cache");
		res.setHeader("Expires", "0");
		res.setHeader("Content-Disposition", "attachment;filename=" + fileEntity.getName());

		// HttpHeaders headers = new HttpHeaders();
		// headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		// headers.add("Content-Disposition", String.format("attachment;
		// filename=\"%s\"", fileEntity.getName()));
		// headers.add("Pragma", "no-cache");
		// headers.add("Expires", "0");

		byte[] buff = new byte[1024];
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			os = res.getOutputStream();
			bis = new BufferedInputStream(fileEntity.getIs());
			int i = bis.read(buff);
			while (i != -1) {
				os.write(buff, 0, buff.length);
				os.flush();
				i = bis.read(buff);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
