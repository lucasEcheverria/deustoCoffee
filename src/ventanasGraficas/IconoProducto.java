package ventanasGraficas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import deustoCoffee.Producto;

public class IconoProducto extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private Producto producto;
	private JLabel lblNombre, lblFoto;
	private PanelProductos panel;
	private int idSeleccionado;
	private final int MAX_WIDTH = 150;
	private final int MAX_HIGH = 150;

	public IconoProducto(Producto producto, PanelProductos panel) {
		super();
		this.producto = producto;
		this.panel= panel;
		
		idSeleccionado = 0;
		
		setLayout(new BorderLayout());
		
		lblNombre = new JLabel(producto.getNombre());
		lblNombre.setHorizontalAlignment(JLabel.CENTER);
		add(lblNombre, BorderLayout.NORTH);
		
		lblFoto = new JLabel("");
		if(producto.getIcon() != null) {
			Icon icon = new ImageIcon(producto.getIcon().getImage().getScaledInstance(MAX_WIDTH, MAX_HIGH, Image.SCALE_DEFAULT));
			lblFoto.setIcon(icon);
			add(lblFoto, BorderLayout.CENTER);
		}
		
		if(producto.getCantidad() == 0) {
			setBackground(Color.RED);
		}
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(producto != null && producto.getCantidad() > 0 && panel.comboBox.getSelectedItem() != null) {
			      	int cantidad = panel.ventana.productos.get(producto.getIdProducto()).getCantidad() -1;
			      	panel.ventana.productos.get(producto.getIdProducto()).setCantidad(cantidad);
			        Producto seleccionado = panel.ventana.productos.get(producto.getIdProducto());
			        panel.repaint();
			                	
			        //TODO añadir a cuenta
			        Integer idCuenta = (Integer) panel.comboBox.getSelectedItem();
			        if(panel.ventana.cuentas.get(idCuenta).getProductos().keySet().contains(seleccionado.getIdProducto())) {
				        //Añadir uno
				        int antes = panel.ventana.cuentas.get(idCuenta).getProductos().get(seleccionado.getIdProducto());
				        antes++;
				        panel.ventana.cuentas.get(idCuenta).getProductos().put(seleccionado.getIdProducto(), antes);
			        }else {
			             //Añadir elemento
			             panel.ventana.cuentas.get(idCuenta).getProductos().put(seleccionado.getIdProducto(), 1);
			        }
			        }else if(producto.getCantidad() == 0) {
			             JOptionPane.showMessageDialog(panel.ventana, "NO QUEDAN ARTICULOS DE ESTE PRODUCTO");
			        }else if(panel.comboBox.getSelectedItem() == null) {
			             JOptionPane.showMessageDialog(panel.ventana, "SELECCIONE UNA CUENTA");
			        }
					if(panel.comboBox.getSelectedIndex() != -1) {
				        idSeleccionado = (Integer) panel.comboBox.getSelectedItem();
						panel.pCentro.remove(panel.pCuenta);
						panel.pCuenta = new PanelCuenta(panel.ventana.cuentas.get(idSeleccionado), panel.ventana);
						panel.pCentro.add(panel.pCuenta);
						panel.pCentro.revalidate();
						panel.pCentro.repaint();
					}
			}
		});
	}
}