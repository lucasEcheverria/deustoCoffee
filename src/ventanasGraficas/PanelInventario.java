package ventanasGraficas;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import deustoCoffee.Producto;

public class PanelInventario extends JPanel {
	/**
	 * Este es el panel en el que saldra el inventario, en el cual también podremos añadir productos, eliminarlos y ver sus detalles.
	 * Además podremos añadir tipos por los que filtrar.
	 * Todos los datos que utiliza están almacenados en la ventana que le llama.
	 */
	private static final long serialVersionUID = 1L;
	
	private JFrame ventana;
	private JPanel pSur, pCentral;
	private JButton btnAniadir, btnBorrar, btnDetalles;
	private JTable tabla;
	
	public PanelInventario(JFrame ventana) {
		this.ventana = ventana; //nos interesa saber la ventana porque ahi es donde tenemos los datos almacenados.
		
		this.setLayout(new BorderLayout());
		
		//Panel central
		pCentral = new JPanel();
		tabla = new JTable();
		
		
		//Panel sur
		pSur = new JPanel();
		pSur.setLayout(new GridLayout(1, 3));
		btnAniadir = new JButton("AÑADIR");
		btnBorrar = new JButton("BORRAR");
		btnDetalles = new JButton("DETALLES");
		pSur.add(btnAniadir);
		pSur.add(btnBorrar);
		pSur.add(btnDetalles);
		
		this.add(pSur,BorderLayout.SOUTH);
		
	}
	
	public class ModeloTabla extends AbstractTableModel{
		private ArrayList<Producto> productos;
		private String[] header = {"NOMBRE", "PRECIO/UNIDAD", "TOTAL"};
		
		@Override
		public int getRowCount() {
			return productos.size();
		}

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch(columnIndex) {
			case 0: return productos.get(rowIndex).getNombre();
			case 1: return productos.get(rowIndex).getPrecio();
			case 2: return productos.get(rowIndex).getCantidad();
			default: return null;
			}
		}
		
	}

}

