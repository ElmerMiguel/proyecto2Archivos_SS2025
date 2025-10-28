package com.archivos.ecommerce.controller;

import com.archivos.ecommerce.dto.*;
import com.archivos.ecommerce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private SancionService sancionService;

    @Autowired
    private NotificacionService notificacionService;

    // 1. Top 10 productos más vendidos (intervalo tiempo)
    @GetMapping("/reportes/productos-mas-vendidos")
    public ResponseEntity<List<Map<String, Object>>> getProductosMasVendidos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        
        List<Map<String, Object>> reporte = pedidoService.getTop10ProductosMasVendidos(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }

    // 2. Top 5 clientes más ganancias (intervalo tiempo)
    @GetMapping("/reportes/clientes-mas-ganancias")
    public ResponseEntity<List<Map<String, Object>>> getClientesMasGanancias(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        
        List<Map<String, Object>> reporte = pedidoService.getTop5ClientesMasGanancias(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }

    // 3. Top 5 clientes más productos vendidos (intervalo tiempo)
    @GetMapping("/reportes/clientes-mas-productos-vendidos")
    public ResponseEntity<List<Map<String, Object>>> getClientesMasProductosVendidos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        
        List<Map<String, Object>> reporte = pedidoService.getTop5ClientesMasProductosVendidos(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }

    // 4. Top 10 clientes más pedidos (intervalo tiempo)
    @GetMapping("/reportes/clientes-mas-pedidos")
    public ResponseEntity<List<Map<String, Object>>> getClientesMasPedidos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        
        List<Map<String, Object>> reporte = pedidoService.getTop10ClientesMasPedidos(fechaInicio, fechaFin);
        return ResponseEntity.ok(reporte);
    }

    // 5. Top 10 clientes más productos en venta
    @GetMapping("/reportes/clientes-mas-productos-venta")
    public ResponseEntity<List<Map<String, Object>>> getClientesMasProductosEnVenta() {
        List<Map<String, Object>> reporte = productoService.getTop10ClientesMasProductosEnVenta();
        return ResponseEntity.ok(reporte);
    }

    // 6. Historial de sanciones
    @GetMapping("/reportes/historial-sanciones")
    public ResponseEntity<List<SancionDTO>> getHistorialSanciones() {
        List<SancionDTO> sanciones = sancionService.listarTodasLasSanciones();
        return ResponseEntity.ok(sanciones);
    }

    // 7. Historial de notificaciones
    @GetMapping("/reportes/historial-notificaciones")
    public ResponseEntity<List<NotificacionDTO>> getHistorialNotificaciones() {
        List<NotificacionDTO> notificaciones = notificacionService.listarTodasLasNotificaciones();
        return ResponseEntity.ok(notificaciones);
    }

    // ==========================================
    // DASHBOARD ADMINISTRATIVO
    // ==========================================

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        Map<String, Object> dashboard = pedidoService.getDashboardAdmin();
        return ResponseEntity.ok(dashboard);
    }

    // ==========================================
    // GESTIÓN DE USUARIOS
    // ==========================================

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Integer id) {
        return usuarioService.obtenerUsuarioPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // ==========================================
    // GESTIÓN DE PRODUCTOS (ADMIN OVERRIDE)
    // ==========================================

    @GetMapping("/productos/pendientes")
    public ResponseEntity<List<ProductoDTO>> getProductosPendientes() {
        List<ProductoDTO> productos = productoService.listarProductosPendientes();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/productos/rechazados")
    public ResponseEntity<List<ProductoDTO>> getProductosRechazados() {
        List<ProductoDTO> productos = productoService.listarProductosRechazados();
        return ResponseEntity.ok(productos);
    }

    // ==========================================
    // ESTADÍSTICAS GENERALES
    // ==========================================

    @GetMapping("/estadisticas/ventas-mensuales")
    public ResponseEntity<List<Map<String, Object>>> getVentasMensuales(
            @RequestParam int year) {
        List<Map<String, Object>> ventas = pedidoService.getVentasMensualesPorAno(year);
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/estadisticas/productos-por-categoria")
    public ResponseEntity<List<Map<String, Object>>> getProductosPorCategoria() {
        List<Map<String, Object>> estadisticas = productoService.getProductosPorCategoria();
        return ResponseEntity.ok(estadisticas);
    }
}