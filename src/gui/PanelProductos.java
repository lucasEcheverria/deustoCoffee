package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import domain.Producto;

public class PanelProductos extends JPanel  {
	/**
	 * Esta clase se corresponde con un panel que muestra todos los productos en un formato más intuitivo y que cuando se hacel click en ellos se suman 
	 * a alguna cuenta seleccionada.
	 */
	private static final long serialVersionUID = 1L;
	private Integer idSeleccionado;
	protected VentanaPrincipal ventana;
	private JPanel pIzquierda, pComboBox;
	protected JPanel pCentro, pcont; 
	protected PanelCuenta pCuenta;
	private DefaultMutableTreeNode nodo;
	private JTree jTree;
	private DefaultTreeModel treeModel;
	protected JComboBox<Integer> comboBox;
	private JScrollPane scrollpane;
	public PanelProductos(VentanaPrincipal ventana) {
		this.ventana = ventana;
		setLayout(new BorderLayout());
		idSeleccionado = 0;
		
		//pPanel izquierda
		pIzquierda = new JPanel();
		pIzquierda.setLayout(new GridLayout(2, 1));
		//JTree
		nodo = new DefaultMutableTreeNode("DEUSTOCOFFEE");
		treeModel = new DefaultTreeModel(nodo);
		jTree = new JTree(treeModel);
		jTree.expandPath(new TreePath(nodo.getPath()));
		cargarArbol();
		
		jTree.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = jTree.getRowForLocation(e.getX(), e.getY());
                TreePath path = jTree.getPathForLocation(e.getX(), e.getY());
                if(row != -1) {
                	DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                	filtrarPanel(selectedNode.toString());
                	if(selectedNode == nodo) {
                		cargarPanel();
                	}
                }	
			}
		});;
		pIzquierda.add(jTree);
		
		//Panel central
		pCentro = new JPanel();
		pCentro.setLayout(new GridLayout(2,1));
		
		scrollpane = new JScrollPane();
		pcont = new JPanel();
		pcont.setPreferredSize(new Dimension(pCentro.getWidth(),1200));
		
		cargarPanel();
		
		scrollpane.setViewportView(pcont);
		
		pCentro.add(scrollpane);
				
		//JComboBox
		
		pComboBox = new JPanel();
		pCuenta = new PanelCuenta(null, ventana);
		pCentro.add(pCuenta);
		comboBox = new JComboBox<Integer>();
		cargarComboBox();
		comboBox.addActionListener(e->{
			idSeleccionado = (Integer) comboBox.getSelectedItem();
			pCentro.remove(pCuenta);
			pCuenta = new PanelCuenta(ventana.cuentas.get(idSeleccionado), ventana);
			pCentro.add(pCuenta);
			pCentro.revalidate();
			pCentro.repaint();
		});
		pComboBox.add(comboBox);
		pIzquierda.add(pComboBox);
		add(pIzquierda, BorderLayout.WEST);
		
		//
		add(pCentro, BorderLayout.CENTER);
		
	}
	
	private void cargarArbol() {
		/**
		 * Este método carga en el arbol los distintos tipos que tenemos almacenados.
		 */
		nodo.removeAllChildren();
		treeModel.reload();
		ventana.tipos.forEach(t -> {
			treeModel.insertNodeInto(new DefaultMutableTreeNode(t), nodo, nodo.getChildCount());
			jTree.expandPath(new TreePath(nodo.getPath()));
			jTree.revalidate();
			jTree.repaint();
		});
		jTree.setModel(treeModel);
	}
	
	protected void cargarComboBox() {
		/**
		 * Este método carga el JComboBox los id de las cuentas existentes.
		 */
		comboBox.removeAllItems();
		for(Integer id : ventana.cuentas.keySet()) {
			comboBox.addItem(id);
		}
	}
	
	private void filtrarPanel(String filtro) {
		/**
		 * Este método filtra el contenido del panel por un parámetro.
		 */
		pcont.removeAll();
		comboBox.removeAllItems();
		for(Integer key : ventana.productos.keySet()) {
			comboBox.addItem(key);
			if(ventana.productos.get(key).getTipo().equals(filtro)) {
				IconoProducto icono = new IconoProducto(ventana.productos.get(key), this);
				pcont.add(icono);
			}
		}

		pcont.repaint();
	}
	
	protected void cargarPanel() {
		/**
		 * Este método carga el panel con todos los productos.
		 */
		pcont.removeAll();
		for(Integer key : ventana.productos.keySet()) {
			IconoProducto icono = new IconoProducto(ventana.productos.get(key), this);
			pcont.add(icono);
		}
		pcont.repaint();
	}
	
	
	@SuppressWarnings("unused") //ChatGPT
	private class ModeloTabla extends AbstractTableModel{
		
		private static final long serialVersionUID = 1L;
		private HashMap<Integer, Producto> productos;
		private String[] header = {"","","","","",""};

		public ModeloTabla(HashMap<Integer, Producto> productos) {
			super();
			this.productos = productos;
		}

		@Override
		public int getRowCount() {
			return (productos.size()/6)+1;
		}

		@Override
		public int getColumnCount() {
			return 6;
		}
		
		@Override
		public String getColumnName(int column) {
			return header[column];
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if((columnIndex + rowIndex * 6) < productos.size()) {
				switch(columnIndex) {
				case 0: return productos.get(productos.keySet().toArray()[columnIndex + rowIndex * 6]);
				case 1: return productos.get(productos.keySet().toArray()[columnIndex + rowIndex * 6]);
				case 2: return productos.get(productos.keySet().toArray()[columnIndex + rowIndex * 6]);
				case 3: return productos.get(productos.keySet().toArray()[columnIndex + rowIndex * 6]);
				case 4: return productos.get(productos.keySet().toArray()[columnIndex + rowIndex * 6]);
				case 5: return productos.get(productos.keySet().toArray()[columnIndex + rowIndex * 6]);
				default: return null;
				}
			}else {
				return null;
			}
		}
		
	}
	
	@SuppressWarnings("unused") //ChatGPT
	private class RenderTabla implements TableCellRenderer{

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if(value != null) {
				JPanel panel = new JPanel();
				JLabel lbl = new JLabel(value.toString());
				panel.add(lbl);
				lbl.setHorizontalAlignment(JLabel.CENTER);
				lbl.setVerticalAlignment(JLabel.CENTER);
				if(((Producto) value).getCantidad() == 0) {
					panel.setBackground(Color.red);
				}
				return panel;
			}else {
				return null;
			}
		}
		
	}
}
