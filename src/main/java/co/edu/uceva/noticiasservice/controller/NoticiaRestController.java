package co.edu.uceva.noticiasservice.controller;


import co.edu.uceva.noticiasservice.model.entities.Noticia;
import co.edu.uceva.noticiasservice.model.service.IUploadFileService;
import co.edu.uceva.noticiasservice.model.service.NoticiaService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController()
public class NoticiaRestController {


    @Autowired
    private NoticiaService noticiaService;

    private final IUploadFileService uploadService;

    public NoticiaRestController(NoticiaService noticiaService, IUploadFileService uploadService) {
        this.noticiaService = noticiaService;
        this.uploadService = uploadService;
    }
    
    //crear noticia
    @PostMapping("/noticias/crear_noticia")
    public Noticia createNoticia(@RequestBody Noticia noticia)
    {
        return this.noticiaService.save(noticia);
    }

    //eliminar noticia y controlar errores
    @DeleteMapping("/noticias/eliminar_noticia/{id}")
    public void deleteNoticia(@PathVariable int id) {
        Noticia noticia = this.noticiaService.findById(id);
        if(noticia != null) {
            this.noticiaService.delete(noticia);
        }
    }

    // Listar todas las noticias (para usuarios regulares, solo muestra las no eliminadas)
    @GetMapping("/noticias/mostrar_noticia")
    public ResponseEntity<?> getAllNoticias() {
        List<Noticia> noticias = this.noticiaService.listar();
        return new ResponseEntity<List<Noticia>>(noticias, HttpStatus.OK);
    }

    // Listar historial de noticias (para administrador, muestra todas las noticias)
    @GetMapping("/noticia/historial")
    public ResponseEntity<?> getHistorialNoticias() {
        List<Noticia> noticias = this.noticiaService.listarTodas();  // Incluye eliminadas
        return new ResponseEntity<List<Noticia>>(noticias, HttpStatus.OK);
    }
    //Modificar y actualizar noticia
    @PutMapping("/noticias/modificar_noticia/{id}")
    public ResponseEntity<?> actualizarNoticia(@PathVariable int id, @RequestBody Noticia newnoticia){
        try {
            Optional<Noticia> noticiaOptional  = Optional.ofNullable(noticiaService.findById(id));
            if (noticiaOptional .isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Noticia no encontrada, ingrese de nuevo");
            }

            Noticia noticia = noticiaOptional.get();

            noticia.setTitulo(newnoticia.getTitulo());
            noticia.setTexto(newnoticia.getTexto());
            noticia.setAutor(newnoticia.getAutor());
            noticia.setImagen(newnoticia.getImagen());
            //noticia.setFecha(newnoticia.getFecha());
            noticia.setEliminada(newnoticia.isEliminada());
            noticia.setPrograma((newnoticia.getPrograma()));
            noticia.setImportancia(newnoticia.getImportancia());
            noticia.setNocturna(newnoticia.isNocturna());
            noticia.setFavorita(newnoticia.isFavorita());

            Noticia noticiaActualizada = noticiaService.save(noticia);

            return ResponseEntity.ok(noticiaActualizada);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al actualizar la noticia");
        }
    }

    //Filtrar noticias
    @GetMapping("/noticias/filtrar")
    public ResponseEntity<List<Noticia>> filterNoticias(
            @RequestParam(required = false) String programa,
            @RequestParam(required = false) String importancia,
            @RequestParam(required = false) String lugar,
            @RequestParam(required = false) Boolean diurna,
            @RequestParam(required = false) Boolean nocturna,
            @RequestParam(required = false) Boolean evento,
            @RequestParam(required = false) Boolean noticia) {

        List<Noticia> noticias = noticiaService.filterNoticia(programa, importancia, lugar, diurna, nocturna, evento, noticia);
        return ResponseEntity.ok(noticias);
    }

    //Guardar noticia como favorita
    @PutMapping("/noticia/noticias_favorita/{id}")
    public ResponseEntity<?> guardaNoticia(@PathVariable int id){
        try {
            Optional<Noticia> noticiaOptional  = Optional.ofNullable(noticiaService.findById(id));
            if (noticiaOptional .isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Noticia no encontrada, ingrese de nuevo");
            }

            Noticia noticia = noticiaOptional.get();
            noticia.setFavorita(!noticia.isFavorita());

            Noticia noticiaActualizada = noticiaService.save(noticia);

            return ResponseEntity.ok(noticiaActualizada);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al actualizar la noticia");
        }
    }

    //Mostrar noticias favoritas
    @GetMapping("/noticia/mostrar_guardados")
    public ResponseEntity<?> findByFavorita() {
        boolean favorita = true;
        List<Noticia> noticias = noticiaService.findByNoticiaFavorita(favorita);

        if (noticias.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron noticias favoritas guardadas.");
        } else {
            return ResponseEntity.ok(noticias);
        }
    }

    //Guardar imagen
    @PostMapping("/noticia/upload")
    public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") int id) {
        Map<String, Object> response = new HashMap<>();

        Noticia noticia = noticiaService.findById(id);
        if (noticia == null) {
            response.put("mensaje", "No se encontró la noticia con el ID especificado.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        String nombreArchivo = null;
        try {
            nombreArchivo = uploadService.copiar(archivo);

            String nombreFotoAnterior = noticia.getImagen();
            if (nombreFotoAnterior != null && !nombreFotoAnterior.isEmpty()) {
                uploadService.eliminar(nombreFotoAnterior);
            }

            noticia.setImagen(nombreArchivo);
            noticiaService.save(noticia);

            response.put("noticia", noticia);
            response.put("mensaje", "Has subido correctamente la imagen: " + nombreArchivo);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IOException e) {
            response.put("mensaje", "Error al subir la imagen de la noticia");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/uploads/img/{nombreFoto:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {

        Resource recurso;
        try {
            recurso = (Resource) uploadService.cargar(nombreFoto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getClass() + "\"");

        return new ResponseEntity<>(recurso, cabecera, HttpStatus.OK);
    }


}
