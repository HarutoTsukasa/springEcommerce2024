package com.sena.ecommerce.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {

	// en la DB se guarda el nombre de la imagen y en un directorio del proyecto la
	// imagen
	private String folder = "images//";

	// metodo para subir imagen de producto
	public String saveImages(MultipartFile file, String nombre) throws IOException {
		// validacion de imagen
		if (!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			// variable path que redirige al directorio, se importa el path de .nio.file
			Path path = Paths.get(folder + nombre + "_" + file.getOriginalFilename());
			Files.write(path, bytes);
			return nombre + "_" + file.getOriginalFilename();
		}
		return "default.jpg";
	}

	// metodo para eliminar imagen de producto
	public void deleteImage(String nombre) {
		String ruta = "images//";
		// file importalo de java.io
		File file = new File(ruta + nombre);
		file.delete();
	}

}
