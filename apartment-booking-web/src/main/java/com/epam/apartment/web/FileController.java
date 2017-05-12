package com.epam.apartment.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.epam.apartment.model.User;
import com.epam.apartment.service.UserService;

@Controller
public class FileController {

	private static Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private UserService userService;

	@PostMapping(value = "/uploadAvatar")
	public String uploadAvatar(@RequestParam("id") int id, @RequestParam("avatar") MultipartFile file, HttpSession session) {
		String name = null;

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				name = file.getOriginalFilename();

				String avatarPath;
				String shortFilePath;
				String directoryPath = session.getServletContext().getRealPath("") + "resources/img/";

				// remove constants later
				shortFilePath = "avatar" + id + "_" + new Date().getTime() + name.substring(name.lastIndexOf("."));
				avatarPath = directoryPath + shortFilePath;

				File uploadedFile = new File(avatarPath);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));

				stream.write(bytes);
				stream.flush();
				stream.close();

				User user = (User) session.getAttribute("user");
				// save in database later
				user.setAvatar(shortFilePath);
				session.setAttribute("user", user);

				logger.info("User " + id + " successfully uploaded avatar!");
			} catch (IOException e) {

				logger.warn("Error while uploading avatar by user " + id);
			}

		}

		return "redirect:/user/edit";
	}

}
