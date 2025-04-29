/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import BO.ProductoBO;
import DTO.ProductoDTO;
import GUI.Login.frmLogin;
import GUI.Login.frmMenuPrincipal;
import GUI.Login.frmMenuPrincipal;
import Interfaces.IProductoBO;
import excepciones.NegocioException;
import java.util.List;
import javax.swing.JOptionPane;
import modulo.inventario.frmActualizarProducto;
import modulo.inventario.frmMenuInventario;
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

    // Instancia
    private static ControlGUI instancia;
    // Login
    private frmLogin login;
    private frmMenuPrincipal menuPrincipal;
    //Inventario
    private frmMenuInventario menuInventario;
    private frmRegistrarProducto registrarProducto;
    private frmActualizarProducto actualizarProducto;

    private IProductoBO producto = new ProductoBO();

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

    public void mostrarActualizarProducto() {
        this.actualizarProducto = new frmActualizarProducto();
        this.actualizarProducto.setLocationRelativeTo(null);
        this.actualizarProducto.setVisible(true);
    }

    public ProductoDTO crearProductoDTO(String SKU, String nombre, String categoria, String unidadMedida, String precioCompraReferencial, String precioVenta, String proveedor, String stock, String observaciones) {
        Double precioCompra;
        Integer stockIni;
        if (precioCompraReferencial.equalsIgnoreCase("")) {
            precioCompra = null;
        } else {
            precioCompra = Double.parseDouble(precioCompraReferencial);
        }
        if (stock.equalsIgnoreCase("")) {
            stockIni = null;
        } else {
            stockIni = Integer.parseInt(stock);
        }
        if (observaciones.equalsIgnoreCase("")) {
            observaciones = "Sin Observaciones";
        }
        ProductoDTO dto = new ProductoDTO(SKU, nombre, categoria, unidadMedida, precioCompra, Double.parseDouble(precioVenta), proveedor, stockIni, observaciones);
        return dto;
    }

    public ProductoDTO registrarProducto(ProductoDTO producto) throws NegocioException {
        if (producto == null) {
            throw new NegocioException("El producto esta vacio");
        }
        return this.producto.registrarProducto(producto);
    }

    public List<ProductoDTO> obtenerProductosFiltro(String sku, String nombre) throws NegocioException {

        return this.producto.obtenerProductosFiltro(sku, nombre, true);
    }

    public ProductoDTO obtenerProductoPorNombre(String nombre) throws NegocioException {
        return this.producto.obtenerProductoNombre(nombre);
    }

    public ProductoDTO obtenerProductoPorSKU(String sku) throws NegocioException {
        return this.producto.obtenerProductoSKU(sku);
    }
    
    public List<ProductoDTO>ObtenerProductos(String sku, String categoria, boolean estado) throws NegocioException{
        return this.producto.obtenerProductosFiltro(sku, categoria, estado);
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
            //Validación de producto ya existente.
        } else if (this.ValidacionProductoExistente(sku, nombre) == true) {
            JOptionPane.showMessageDialog(null, "El nombre o SKU del producto ya esta registrado en el sistema");
        } else {
            seguir = true;
        }
        return seguir;
    }

    public boolean ValidacionProductoExistente(String SKU, String nombre) throws NegocioException {
        // Validación Registro duplicado.
        boolean existe = false;
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
            return existe;

        } catch (NegocioException ne) {
            throw new NegocioException("Error al validar producto");
        }

    }

}
