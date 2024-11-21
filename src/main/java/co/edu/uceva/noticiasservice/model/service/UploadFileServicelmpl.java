package co.edu.uceva.noticiasservice.model.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class UploadFileServicelmpl implements IUploadFileService{

    private final Logger log = LoggerFactory.getLogger(UploadFileServicelmpl.class);

    private final static String DIRECTORIO_UPLOAD = "uploads";

    @PostConstruct
    public void init() {
        Path uploadsDir = Paths.get("uploads");
        if (!Files.exists(uploadsDir)) {
            try {
                Files.createDirectories(uploadsDir);
                log.info("Directorio 'uploads' creado exitosamente");
            } catch (IOException e) {
                log.error("Error al crear el directorio 'uploads'", e);
            }
        }
    }

    @Override
    public Resource cargar(String nombreFoto) throws MalformedURLException {

        Path rutaArchivo = getPath(nombreFoto);
        log.info("Cargando archivo desde: {}", rutaArchivo.toString());

        Resource recurso = new UrlResource(rutaArchivo.toUri());

        if(!recurso.exists() && !recurso.isReadable()) {
            log.error("No se pudo cargar la imagen: {}", nombreFoto);

            Path rutaImagenPorDefecto = Paths.get("/src/main/resources/static/images").resolve("no-usuario.png").toAbsolutePath();
            log.info("Intentando cargar imagen por defecto: {}", rutaImagenPorDefecto);

            recurso = new UrlResource(rutaArchivo.toUri());

        }

        if (!recurso.exists() || !recurso.isReadable()) {
            throw new RuntimeException("No se pudo cargar la imagen ni la imagen por defecto");
        }

        return recurso;
    }

    @Override
    public String copiar(MultipartFile archivo) throws IOException {
        String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "");
        Path rutaArchivo = getPath(nombreArchivo);
        log.info("Guardando archivo en: " + rutaArchivo);

        Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING); // Asegúrate de usar esta opción
        return nombreArchivo;
    }


    @Override
    public boolean eliminar(String nombreFoto) {

        if(nombreFoto !=null && nombreFoto.length() >0) {
            Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
            File archivoFotoAnterior = rutaFotoAnterior.toFile();
            if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
                archivoFotoAnterior.delete();
                return true;
            }
        }

        return false;
    }

    @Override
    public Path getPath(String nombreFoto) {
        Path ruta = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
        log.info("Ruta completa del archivo: " + ruta);
        return ruta;
    }

}
