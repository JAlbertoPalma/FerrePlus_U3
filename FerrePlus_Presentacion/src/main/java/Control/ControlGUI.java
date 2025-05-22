/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import BO.CajaBO;
import BO.ProductoBO;
import DTO.CajaDTO;
import DTO.ProductoDTO;
import GUI.Compras.frmConsultarCompras;
import GUI.Compras.frmMenuCompras;
import GUI.Compras.frmProductoComprado;
import GUI.Compras.frmRegistrarCompra;
import GUI.Login.frmLogin;
import GUI.Login.frmMenuPrincipal;
import GUI.Login.frmMenuPrincipal;
import GUI.Ventas.frmConsultarVentas;
import GUI.Ventas.frmMenuVentas;
import GUI.Ventas.frmProductoVendido;
import GUI.Ventas.frmRegistrarVenta;
import Interfaces.ICajaBO;
import Interfaces.IProductoBO;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modulo.inventario.frmActualizarProducto;
import modulo.inventario.frmMenuInventario;
import modulo.inventario.frmProductosRegistrados;
import modulo.inventario.frmRegistrarProducto;

/**
 * Clase central que gestiona la navegación entre las interfaces gráficas del
 * sistema y coordina las operaciones de lógica de negocio Maneja módulos como
 * productos, ingredientes, comandas, mesas y clientes frecuentes. Este
 * controlador se encarga de mostrar ventanas, llamar a las clases BO y
 * actualizar datos en pantalla.
 *
 * @author joelr
 */
public class ControlGUI {
    // Atributo global y escencial para el funcionamiento
    public CajaDTO sesionCajaActiva;
    
    // Instancia
    private static ControlGUI instancia;
    // Login
    private frmLogin login;
    private frmMenuPrincipal menuPrincipal;
    //Inventario
    private frmMenuInventario menuInventario;
    private frmRegistrarProducto registrarProducto;
    private frmActualizarProducto actualizarProducto;
    private frmProductosRegistrados productosRegistrados;
    private IProductoBO productoBO = new ProductoBO();
    //Compras
    private frmConsultarCompras consultarCompras;
    private frmMenuCompras menuCompras;
    private frmProductoComprado productoComprado;
    private frmRegistrarCompra registrarCompra;
    //Ventas
    private frmConsultarVentas consultarVentas;
    private frmMenuVentas menuVentas;
    private frmProductoVendido productoVendido;
    private frmRegistrarVenta registrarVenta;
    //Caja
    private ICajaBO cajaBO = new CajaBO();
    //Auxiliares
    private ProductoDTO productoAux = new ProductoDTO();
    private double cantidad;
    private double descuento;
    private double calculoVenta;
    private double subtotal;
    
    public ControlGUI() {
    }

    public static ControlGUI getInstancia() {
        if (instancia == null) {
            instancia = new ControlGUI();
        }
        return instancia;
    }
    

    /*
    Metodo para mostrar el frmLogin.
     */
    public void mostrarLogin() {
        if (this.login == null) {
            this.login = new frmLogin();
            login.setLocationRelativeTo(null);
        }
        this.login.setVisible(true);
    }

    /*
    Metodo para mostrar el frm del Menu Principal.
     */
    public void mostrarMenuPrincipal() {
        if (this.menuPrincipal == null) {
            this.menuPrincipal = new frmMenuPrincipal();
            menuPrincipal.setLocationRelativeTo(null);
        }
        this.menuPrincipal.setVisible(true);
    }

    public void mostrarMenuProducto() {
        this.menuInventario = new frmMenuInventario();
        this.menuInventario.setLocationRelativeTo(null);
        this.menuInventario.setVisible(true);
    }

    /*
    Metodo para mostrar el frm Registrar Producto.
     */
    public void mostrarRegistrarProducto() {
        this.registrarProducto = new frmRegistrarProducto();
        this.registrarProducto.setLocationRelativeTo(null);
        this.registrarProducto.setVisible(true);
    }

    /*
    Metodo para mostrar el frm Actualizar Producto.
     */
    public void mostrarActualizarProducto(String id) throws NegocioException {
        this.actualizarProducto = new frmActualizarProducto(id);
        this.actualizarProducto.setLocationRelativeTo(null);
        this.actualizarProducto.setVisible(true);
    }
  

    public void mostrarProductosRegistrados() throws NegocioException {
        this.productosRegistrados = new frmProductosRegistrados();
        this.productosRegistrados.setLocationRelativeTo(null);
        this.productosRegistrados.setVisible(true);
    }
    public void mostrarProductosRegistrados(int procedencia) throws NegocioException {
        this.productosRegistrados = new frmProductosRegistrados(procedencia);
        this.productosRegistrados.setLocationRelativeTo(null);
        this.productosRegistrados.setVisible(true);
    }
    public void mostrarProductoVendido(String id) throws NegocioException{
        this.productoVendido = new frmProductoVendido(id);
        this.productoVendido.setLocationRelativeTo(null);
        this.productoVendido.setVisible(true);
    }
    public void mostrarRegistrarVenta(String id, double cantidad, double calculoVenta,double descuento, double subtotal) throws NegocioException{
        this.registrarVenta.añadir(id, cantidad, calculoVenta, descuento, subtotal);
        this.registrarVenta.setVisible(true);
    }

    
    public ProductoDTO crearProductoDTO(String SKU, String nombre, String categoria, String unidadMedida, String precioCompraReferencial, String precioVenta, String proveedor, String stock, String observaciones) {
        Double precioCompra;
        Integer stockIni;
        if (precioCompraReferencial.equalsIgnoreCase("No registrado")) {
            precioCompra = null;
        } else {
            precioCompra = Double.parseDouble(precioCompraReferencial);
        }
        if (stock.equalsIgnoreCase("No registrado")) {
            stockIni = null;
        } else {
            stockIni = Integer.parseInt(stock);
        }
        if (observaciones.equalsIgnoreCase("No registrado")) {
            observaciones = "Sin Observaciones";
        }
        ProductoDTO dto = new ProductoDTO(SKU, nombre, categoria, unidadMedida, precioCompra, Double.parseDouble(precioVenta), proveedor, stockIni, observaciones);
        return dto;
    }

    public ProductoDTO registrarProducto(ProductoDTO producto) throws NegocioException {
        if (producto == null) {
            throw new NegocioException("El producto esta vacio");
        }
        return this.productoBO.registrarProducto(producto);
    }

    public List<ProductoDTO> obtenerProductosFiltro(String sku, String categoria, boolean estado) throws NegocioException {
        return this.productoBO.obtenerProductosFiltro(sku, categoria, estado);
    }

    public ProductoDTO obtenerProductoPorNombre(String nombre) throws NegocioException {
        return this.productoBO.obtenerProductoNombre(nombre);
    }

    public ProductoDTO obtenerProductoPorSKU(String sku) throws NegocioException {
        return this.productoBO.obtenerProductoSKU(sku);
    }
    public ProductoDTO obtenerProductoPorID(String id) throws NegocioException {
        return this.productoBO.obtenerProductoID(id);
    }

    public List<ProductoDTO> ObtenerProductos() throws NegocioException {
        return this.productoBO.obtenerProductos();
    }

    public boolean EliminarProducto(String id) throws NegocioException, PersistenciaException {
        try {
            this.productoBO.eliminarProducto(id);
            return true;
        } catch (NegocioException ne) {
            throw new NegocioException("Error al eliminar");
        }

    }

    public boolean ActualizarProducto(ProductoDTO producto) throws NegocioException {
        try {
            this.productoBO.actualizarProducto(producto);
            return true;
        } catch (NegocioException ne) {
            throw new NegocioException("Error al actualizar");
        }
    }

    /**
     * Metodo para las validaciones de Producto.
     *
     * @param sku
     * @param nombre
     * @param precioVenta
     * @return true si paso las validaciones, false si lo contrario.
     * @throws NegocioException
     */
    public boolean ValidarRegistroProducto(String sku, String nombre, String precioVenta, String precioCompra, String stock) throws NegocioException {
        boolean seguir = false; // Define si el proceso de registro continua.
        //Validacion Campos necesarios vacios
        if (nombre.equalsIgnoreCase("")
                || precioVenta.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Necesita llenar los campos para registrar");
            throw new NegocioException("Le falta llenar un campo obligatorio");
            // Validación Nombre debe llevar letras y no numeros.
        } else if (nombre.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(null, "Debe introducir solo texto en el campo de nombre");
            throw new NegocioException("Hay numeros en el campo nombre");
            // Validación precio solo puede llevar numeros.
        } else if (precioVenta.matches(".*[a-zA-Z].*") || precioCompra.matches(".*[a-zA-Z].*") || stock.matches(".*[a-zA-Z].*")) {
            JOptionPane.showMessageDialog(null, "Los campos precioVenta,precioCompra y stock solo pueden llevar numeros");
            throw new NegocioException("Los compra,venta y stock no llevan letras");
            //Validación de productoBO ya existente.
        } else if (this.ValidacionProductoExistente(sku, nombre) == true) {
            JOptionPane.showMessageDialog(null, "El nombre o SKU del producto ya esta registrado en el sistema");
        } else {
            seguir = true;
        }
        return seguir;
    }
    /**
     * Metodo para las validaciones de Producto.
     *
     * @param sku
     * @param nombre
     * @param precioVenta
     * @return true si paso las validaciones, false si lo contrario.
     * @throws NegocioException
     */
    public boolean ValidarRegistroProductoActualizar(String sku, String nombre, String precioVenta, String precioCompra, String stock, String id) throws NegocioException {
        boolean seguir = false; // Define si el proceso de registro continua.
        //Validacion Campos necesarios vacios
        if (nombre.equalsIgnoreCase("")
                || precioVenta.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(null, "Necesita llenar los campos para registrar");
            throw new NegocioException("Le falta llenar un campo obligatorio");
            // Validación Nombre debe llevar letras y no numeros.
        } else if (nombre.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(null, "Debe introducir solo texto en el campo de nombre");
            throw new NegocioException("Hay numeros en el campo nombre");
            // Validación precio solo puede llevar numeros.
        } else if (precioVenta.matches(".*[a-zA-Z].*") || precioCompra.matches(".*[a-zA-Z].*") || stock.matches(".*[a-zA-Z].*")) {
            JOptionPane.showMessageDialog(null, "Los campos precioVenta,precioCompra y stock solo pueden llevar numeros");
            throw new NegocioException("Los compra,venta y stock no llevan letras");
            //Validación de productoBO ya existente.
        } else if (this.ValidacionProductoExistenteActualizar(sku, nombre,id) == true) {
            JOptionPane.showMessageDialog(null, "El producto ya se ha registrado en el sistema");
        } else {
            seguir = true;
        }
        return seguir;
    }

    public boolean ValidacionProductoExistente(String SKU, String nombre) throws NegocioException {
        // Validación Registro duplicado.
        boolean existe = false; //Validacion de campos sku y nombre.
        boolean existe2 = false; //Validacion de id existente.
        try {
            if (this.obtenerProductoPorNombre(nombre) == null || this.obtenerProductoPorSKU(SKU) == null) {
                existe = false;
            } else {
                if (this.obtenerProductoPorSKU(SKU).getSKU().equalsIgnoreCase("")) {
                    existe = false;
                } else {
                    existe = true;
                    
                }

            }
            return existe2;

        } catch (NegocioException ne) {
            throw new NegocioException("Error al validar producto");
        }

    }
    public boolean ValidacionProductoExistenteActualizar(String SKU, String nombre, String id) throws NegocioException {
        // Validación Registro duplicado.
        boolean existe = false; //Validacion de campos sku y nombre.
        boolean existe2 = false; //Validacion de id existente.
        try {
            if (this.obtenerProductoPorNombre(nombre) == null || this.obtenerProductoPorSKU(SKU) == null) {
                existe = false;
            } else {
                if (this.obtenerProductoPorSKU(SKU).getSKU().equalsIgnoreCase("")) {
                    existe = false;
                } else {
                    existe = true;
                    if (existe == true && this.obtenerProductoPorID(id).getId() == id) { //Si tiene los mismos campos y tiene el mismo id.
                        existe2 = true;
                    }else {
                        existe2 = false;
                    }
                    
                }

            }
            return existe2;

        } catch (NegocioException ne) {
            throw new NegocioException("Error al validar producto");
        }
    }
    
    public CajaDTO getSesionCajaActiva(){
        return sesionCajaActiva;
    }
    
    public void extraerSesionCajaActiva() throws NegocioException{
        sesionCajaActiva = cajaBO.obtenerSesionActiva();
        System.out.println("Extraída caja: " + sesionCajaActiva);
    }
    
    public CajaDTO abrirCaja(CajaDTO caja) throws NegocioException{
        try{
            return cajaBO.abrir(caja);
        }catch(NegocioException ne){
            throw new NegocioException(ne.getMessage());
        }
    }
    
    public CajaDTO cerrarCaja(String observaciones) throws NegocioException{
        try{
            sesionCajaActiva.setObservacionesCierre(observaciones);
            return cajaBO.cerrar(sesionCajaActiva);
        }catch(NegocioException ne){
            throw new NegocioException(ne.getMessage());
        }
    }
    

}
