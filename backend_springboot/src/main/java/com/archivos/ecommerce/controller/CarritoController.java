package com.archivos.ecommerce.controller;

//import com.archivos.ecommerce.dto.CarritoDTO;
import com.archivos.ecommerce.dto.ItemCarritoDTO;
import com.archivos.ecommerce.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "http://localhost:3000")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerCarritoPorUsuario(@PathVariable Integer idUsuario) {
        return carritoService.obtenerCarritoPorUsuario(idUsuario)
            .map(carrito -> ResponseEntity.ok(carrito))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{idCarrito}/items")
    public ResponseEntity<List<ItemCarritoDTO>> obtenerItemsDelCarrito(@PathVariable Integer idCarrito) {
        List<ItemCarritoDTO> items = carritoService.obtenerItemsDelCarrito(idCarrito);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(Map.of("message", "Carrito controller funcionando"));
    }
}