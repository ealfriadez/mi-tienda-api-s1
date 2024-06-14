package pe.edu.unfv.mitiendaapis1.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pe.edu.unfv.mitiendaapis1.model.Libro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    public static List<Libro> libros = new ArrayList<>();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<Libro> listar(){
        return libros;
    }

    @GetMapping("/{id}")
    Libro obtener(@PathVariable Integer id) {
        return libros
                .stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Libro crear(@RequestBody Libro libro){
        libro.setId(new Random().nextInt(256));
        libro.setFechaCreacion(LocalDateTime.now());

        libros.add(libro);
        return libro;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    Libro actualizar(@PathVariable Integer id, @RequestBody Libro libroForm){
        Libro libro = libros
                .stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElse(null);

        if(libro == null) return null;

        libro.setSlug(libroForm.getSlug());
        libro.setDescription(libroForm.getDescription());
        libro.setPrecio(libroForm.getPrecio());
        libro.setTitulo(libroForm.getTitulo());
        libro.setFechaActualizacion(LocalDateTime.now());

        return libro;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void eliminar(@PathVariable Integer id){
        libros.removeIf(x -> x.getId() == id);
    }
}
