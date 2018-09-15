package com.xogame.dao;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.xogame.model.Player;

/**
 * This class will take care on files uploaded by players to store it in server
 * and store it in db.<br>
 * 
 * This class is represent to create (Folder, File), read, write, delete files
 * of player in server.<br>
 * 
 * @author Anas Abu-Hussein
 * @since 15/9/2018
 */
@Repository
public interface PicPlayerStreamDao {

//	public void setGeneralFilePath(Player player);

	/**
	 * This method will check folder player if exist or not .
	 * 
	 * @param player
	 * @return true if exist; false if not exist
	 **/
	public boolean checkPlayerFolder(Player player);

	/**
	 * This method will create folder for player when check and return false from
	 * {@link #checkPlayerFolder(Player) checkPlayerFolder} method.
	 * 
	 * @param player
	 */
	public void createPlayerFolder(Player player);

	/**
	 *
	 * This method will get the path of folder if exist.
	 * 
	 * @param player
	 * @return
	 */
	public String getFolderPlayerPath(Player player);

	/**
	 * This method will read from input stream of multipartFile <br>
	 * and make output straem to the folder of player.
	 * 
	 * @param multipartFile this comming from player request with file maybe is
	 *                      (pic, video).
	 * @param player        will use it to get id and set some data to player such
	 *                      as {@link Player #setPic(String) setPic} method will set
	 *                      the name of file and folder.
	 * @throws IOException
	 */
	public void writeToFolder(MultipartFile multipartFile, Player player) throws IOException;

	/**
	 * 
	 * This method will get outputStream of file of player folder as bytes to read
	 * it in HHTP response.
	 * 
	 * @param player it is use it to search and get folder name of player and access
	 *               to thire files.
	 * @return byte [] to read the stream.
	 * @throws FileNotFoundException
	 */
	public byte[] readFromFolder_playerImage(Player player) throws FileNotFoundException;

}
