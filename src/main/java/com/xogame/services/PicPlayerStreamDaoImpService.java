package com.xogame.services;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.xogame.dao.PicPlayerStreamDao;
import com.xogame.model.Player;

@Service
public class PicPlayerStreamDaoImpService implements PicPlayerStreamDao {

	// static file path in project..
	private static final String FILE_PATH = "\\java projects\\spring works\\spring projects\\XO_Game\\src\\main\\resources\\file";

	// logger of the project ..
	private static final Logger LOGGER = Logger.getLogger(PicPlayerStreamDaoImpService.class);

	// for detectes files
	private File file = null;

	public PicPlayerStreamDaoImpService() {
		super();
	}

	@Override
	public boolean checkPlayerFolder(Player player) {
		try {

			file = new File(FILE_PATH + File.separator + player.getId());

			if (file.exists()) {
				return true;
			} else {
				throw new Exception("The folder of player not exist ... ");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	@Override
	public void createPlayerFolder(Player player) {

		try {
			if (checkPlayerFolder(player) == false) {
				LOGGER.info("the folder of player is not exsit ...");
				LOGGER.info("Create folder now ...");
				file.mkdirs();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

	}

	@Override
	public String getFolderPlayerPath(Player player) {
		if (checkPlayerFolder(player)) {
			return file.getAbsolutePath();
		} else {
			createPlayerFolder(player);
			return file.getAbsolutePath();
		}

	}

	@Override
	public void writeToFolder(MultipartFile multipartFile, Player player) throws IOException {

		String name = ""; // name of file !
		String[] splitPoint;

		splitPoint = multipartFile.getOriginalFilename().split("\\.");
		name = String.valueOf(new Date().getTime() + "." + splitPoint[1]);

		try (OutputStream out = new FileOutputStream(getFolderPlayerPath(player) + File.separator + name);
				BufferedOutputStream bout = new BufferedOutputStream(out)) {

			bout.write(multipartFile.getBytes());
			bout.flush();

			player.setPic(name);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	public byte[] readFromFolder_playerImage(Player player) throws FileNotFoundException {

		byte[] bytes = new byte[1024]; // default size .

		String filePlyaer = getFolderPlayerPath(player) + File.separator + player.getPic();

		file = new File(filePlyaer);

		try (InputStream in = new FileInputStream(file);
				BufferedInputStream bin = new BufferedInputStream(in);
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

			while (bin.available() > 0) {
				byteArrayOutputStream.write(bin.read());
			}

			bytes = byteArrayOutputStream.toByteArray();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return bytes;
	}

}
