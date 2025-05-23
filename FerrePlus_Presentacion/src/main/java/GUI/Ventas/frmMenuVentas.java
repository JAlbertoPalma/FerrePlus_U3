/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Ventas;

import GUI.Compras.*;
import GUI.Login.*;
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
public class frmMenuVentas extends javax.swing.JFrame {

    /**
     * Creates new form RegistrarProductoGUI
     */
    public frmMenuVentas() {
        initComponents();
        this.setExtendedState(frmMenuVentas.MAXIMIZED_BOTH);
        this.AcomodarContenido();
        try {
            ControlGUI.getInstancia().extraerSesionCajaActiva();
        } catch (NegocioException ex) {
            Logger.getLogger(frmAperturaCaja.class.getName()).log(Level.SEVERE, null, ex);
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
        jButtonRegistrar = new javax.swing.JButton();
        jButtonConsultar = new javax.swing.JButton();
        jButtonVolver = new javax.swing.JButton();
        pnlTitulo = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlFormulario.setBackground(new java.awt.Color(204, 255, 204));
        pnlFormulario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonRegistrar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButtonRegistrar.setText("Registrar Ventas");
        jButtonRegistrar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistrarActionPerformed(evt);
            }
        });
        pnlFormulario.add(jButtonRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, 160, 40));

        jButtonConsultar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButtonConsultar.setText("Consultar Ventas");
        jButtonConsultar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConsultarActionPerformed(evt);
            }
        });
        pnlFormulario.add(jButtonConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 80, 160, 40));

        jButtonVolver.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jButtonVolver.setText("Volver");
        jButtonVolver.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVolverActionPerformed(evt);
            }
        });
        pnlFormulario.add(jButtonVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 260, 150, 40));

        getContentPane().add(pnlFormulario, java.awt.BorderLayout.CENTER);

        pnlTitulo.setBackground(new java.awt.Color(0, 102, 51));
        pnlTitulo.setDoubleBuffered(false);
        pnlTitulo.setPreferredSize(new java.awt.Dimension(400, 105));
        pnlTitulo.setLayout(new java.awt.GridBagLayout());

        lblTitulo.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 1, 36)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("Menu Ventas");
        pnlTitulo.add(lblTitulo, new java.awt.GridBagConstraints());

        getContentPane().add(pnlTitulo, java.awt.BorderLayout.PAGE_START);
        pnlTitulo.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarActionPerformed
        if(ControlGUI.getInstancia().getSesionCajaActiva() == null){
            JOptionPane.showMessageDialog(this, "No se pueden registrar ventas sin haber abierto una sesión de caja", "Caja no abierta", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            ControlGUI.getInstancia().mostrarRegistrarVenta();
        } catch (NegocioException ex) {
            Logger.getLogger(frmMenuVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_jButtonRegistrarActionPerformed

    private void jButtonVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVolverActionPerformed
        ControlGUI.getInstancia().mostrarMenuPrincipal();
        this.dispose();
    }//GEN-LAST:event_jButtonVolverActionPerformed

    private void jButtonConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarActionPerformed
        try {
            ControlGUI.getInstancia().mostrarConsultarVentas();
        } catch (NegocioException ex) {
            Logger.getLogger(frmMenuVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
       this.dispose();
    }//GEN-LAST:event_jButtonConsultarActionPerformed
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
        
        this.pnlFormulario.add(this.jButtonRegistrar,gbc);
        this.jButtonRegistrar.setPreferredSize(new Dimension(200, 50));
        gbc.gridy++;
        gbc.gridy++;
        this.pnlFormulario.add(this.jButtonConsultar,gbc);
        this.jButtonConsultar.setPreferredSize(new Dimension(200, 50));
        gbc.gridy++;
        this.pnlFormulario.add(this.jButtonVolver,gbc);
        this.jButtonVolver.setPreferredSize(new Dimension(200, 50));
          gbc.gridy++;
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
            java.util.logging.Logger.getLogger(frmMenuVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMenuVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMenuVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMenuVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMenuVentas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonConsultar;
    private javax.swing.JButton jButtonRegistrar;
    private javax.swing.JButton jButtonVolver;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel pnlFormulario;
    private javax.swing.JPanel pnlTitulo;
    // End of variables declaration//GEN-END:variables

}
