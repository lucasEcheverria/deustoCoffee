package ventanasGraficas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import deustoCoffee.Producto;

public class PanelProductos extends JPanel  {
	/**
	 * Esta clase se corresponde con un panel que muestra todos los productos en un formato más intuitivo y que cuando se hacel click en ellos se suman 
	 * a alguna cuenta seleccionada.
	 */
	private static final long serialVersionUID = 1L;
	private Integer idSeleccionado;
	private VentanaPrincipal ventana;
	private JPanel pIzquierda, pComboBox;
	private DefaultMutableTreeNode nodo;
	private JTree jTree;
	private DefaultTreeModel treeModel;
	protected JComboBox<Integer> comboBox;
	private JScrollPane scrollpane;
	private JTable tabla;
	private ModeloTabla modelo;
	private RenderTabla render;
	
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
                	filtrarModelo(selectedNode.toString());
                	if(selectedNode == nodo) {
                		cargarModelo();
                	}
                }	
			}
		});;
		pIzquierda.add(jTree);
		//JComboBox
		pComboBox = new JPanel();
		
		comboBox = new JComboBox<Integer>();
		cargarComboBox();
		comboBox.addActionListener(e->{
			idSeleccionado = (Integer) comboBox.getSelectedItem();
		});
		pComboBox.add(comboBox);
		pIzquierda.add(pComboBox);
		add(pIzquierda, BorderLayout.WEST);
		
		//Panel central
		modelo = new ModeloTabla(ventana.productos);
		tabla = new JTable(modelo);
		render = new RenderTabla();
		tabla.setDefaultRenderer(Object.class, render);
		tabla.setRowHeight(100);
		tabla.getTableHeader().setReorderingAllowed(false);
		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int fila = tabla.rowAtPoint(e.getPoint());
                int columna = tabla.columnAtPoint(e.getPoint());
                
                Producto producto = (Producto) tabla.getModel().getValueAt(fila, columna);
                
                if(producto != null && producto.getCantidad() > 0) {
                	int cantidad = ventana.productos.get(producto.getIdProducto()).getCantidad() -1;
                	ventana.productos.get(producto.getIdProducto()).setCantidad(cantidad);
                	tabla.repaint();
                	
                	//TODO añadir a cuenta
                }
                if(producto.getCantidad() == 0) {
                	JOptionPane.showMessageDialog(ventana, "NO QUEDAN ARTICULOS DE ESTE PRODUCTO");
                }
			}
		});
		scrollpane = new JScrollPane(tabla);
		add(scrollpane, BorderLayout.CENTER);
		
		
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
	
	private void filtrarModelo(String filtro) {
		/**
		 * Este método filtra el contenido del modelo por un parámetro.
		 */
		HashMap<Integer, Producto> filtrada = new HashMap<Integer, Producto>();
		ventana.productos.keySet().forEach(k -> {
			if(ventana.productos.get(k).getTipo() == filtro) {
				filtrada.put(ventana.productos.get(k).getIdProducto(), ventana.productos.get(k));
			}
		});
		ModeloTabla modelo = new ModeloTabla(filtrada);
		tabla.setModel(modelo);
		tabla.repaint();
	}
	
	protected void cargarModelo() {
		/**
		 * Este método carga el modelo con todos los productos.
		 */
		HashMap<Integer, Producto> productos = ventana.productos;
		ModeloTabla modelo = new ModeloTabla(productos);
		tabla.setModel(modelo);
		tabla.repaint();
	}
	
	
	private class ModeloTabla extends AbstractTableModel{
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
	
	private class RenderTabla implements TableCellRenderer{

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if(value != null) {
				JPanel panel = new JPanel();
				JLabel lbl = new JLabel(value.toString());
				panel.add(lbl);
				lbl.setHorizontalAlignment(SwingConstants.CENTER);
				lbl.setVerticalAlignment(SwingConstants.CENTER);
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
