/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Login;

import modulo.inventario.*;
import Control.ControlGUI;
import GUI.Caja.frmAperturaCaja;
import excepciones.NegocioException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author joelr
 */
public class frmLogin extends javax.swing.JFrame {

    /**
     * Creates new form RegistrarProductoGUI
     */
    public frmLogin() {
        initComponents();
        this.setExtendedState(frmLogin.MAXIMIZED_BOTH);
        this.AcomodarContenido();
        try {
            ControlGUI.getInstancia().extraerSesionCajaActiva();
        } catch (NegocioException ex) {
            Logger.getLogger(frmLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFormulario = new javax.swing.JPanel();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblContrasena = new javax.swing.JLabel();
        txtContrasena = new javax.swing.JTextField();
        jButtonIngresar = new javax.swing.JButton();
        jButtonSalir = new javax.swing.JButton();
        pnlTitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlFormulario.setBackground(new java.awt.Color(255, 204, 153));
        pnlFormulario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUsuario.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsuario.setText("Usuario");
        pnlFormulario.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, 120, -1));

        txtUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUsuario.setPreferredSize(new java.awt.Dimension(200, 50));
        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });
        pnlFormulario.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 70, 120, 22));

        lblContrasena.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblContrasena.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContrasena.setText("Contraseña");
        pnlFormulario.add(lblContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 100, 160, -1));

        txtContrasena.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtContrasena.setPreferredSize(new java.awt.Dimension(200, 50));
        pnlFormulario.add(txtContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, 120, 22));

        jButtonIngresar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButtonIngresar.setText("Ingresar");
        jButtonIngresar.setPreferredSize(new java.awt.Dimension(130, 45));
        jButtonIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIngresarActionPerformed(evt);
            }
        });
        pnlFormulario.add(jButtonIngresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 170, 120, 32));

        jButtonSalir.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButtonSalir.setText("Salir");
        jButtonSalir.setPreferredSize(new java.awt.Dimension(130, 45));
        jButtonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalirActionPerformed(evt);
            }
        });
        pnlFormulario.add(jButtonSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 210, 120, 32));

        getContentPane().add(pnlFormulario, java.awt.BorderLayout.CENTER);

        pnlTitulo.setBackground(new java.awt.Color(102, 51, 0));
        pnlTitulo.setDoubleBuffered(false);
        pnlTitulo.setPreferredSize(new java.awt.Dimension(400, 105));
        pnlTitulo.setLayout(new java.awt.GridBagLayout());

        lblTitulo.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 36)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("FERRE-PLUS");
        pnlTitulo.add(lblTitulo, new java.awt.GridBagConstraints());

        getContentPane().add(pnlTitulo, java.awt.BorderLayout.PAGE_START);
        pnlTitulo.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void jButtonIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIngresarActionPerformed
        if (this.txtUsuario.getText().isEmpty() && this.txtContrasena.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Debe escribir un usuario y una contraseña para ingresar");
        }
        
        if (this.txtUsuario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Debe escribir un usuario");
        }
        
        if (this.txtContrasena.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Debe escribir una contraseña");
        }
        
        if(this.txtUsuario.getText().equalsIgnoreCase("administrador") && this.txtContrasena.getText().equalsIgnoreCase("admin123")){
            ControlGUI.getInstancia().mostrarMenuPrincipal();
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(this, "Credenciales inválidas");
        }

    }//GEN-LAST:event_jButtonIngresarActionPerformed

    private void jButtonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalirActionPerformed
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_jButtonSalirActionPerformed
    private void AcomodarContenido() {
        JPanel panel = this.pnlFormulario; 
        this.pnlFormulario.setLayout(new GridBagLayout()); // Permite centrar componentes dentro
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0; // Todos estarán en la misma fila (fila 0), se incrementa para mover a la siguiente fila.
        gbc.insets = new Insets(2, 2, 2, 2); // Margen alrededor del componente
        gbc.anchor = GridBagConstraints.CENTER; // Centrado en la celda
        gbc.weightx = 1.0; // Espacio extra para centrar horizontalmente

        // Columna 0
//        gbc.gridx = 0;
        
        this.pnlFormulario.add(this.lblUsuario,gbc);
        gbc.gridy++;
        this.pnlFormulario.add(this.txtUsuario,gbc);
        gbc.gridy++;
        this.pnlFormulario.add(this.lblContrasena,gbc);
        gbc.gridy++;
        this.pnlFormulario.add(this.txtContrasena,gbc);
        gbc.gridy++;
        this.pnlFormulario.add(this.jButtonIngresar,gbc);
         this.jButtonIngresar.setPreferredSize(new Dimension(200, 50));
          gbc.gridy++;
        this.pnlFormulario.add(this.jButtonSalir,gbc);
         this.jButtonSalir.setPreferredSize(new Dimension(200, 50));
          gbc.gridy++;
        this.add(this.pnlFormulario, BorderLayout.CENTER);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonIngresar;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JLabel lblContrasena;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel pnlFormulario;
    private javax.swing.JPanel pnlTitulo;
    private javax.swing.JTextField txtContrasena;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables

}
