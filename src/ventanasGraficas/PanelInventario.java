package ventanasGraficas;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import deustoCoffee.Producto;

public class PanelInventario extends JPanel {
	/**
	 * Este es el panel en el que saldra el inventario, en el cual también podremos añadir productos, eliminarlos y ver sus detalles.
	 * Además podremos añadir tipos por los que filtrar.
	 * Todos los datos que utiliza están almacenados en la ventana que le llama.
	 */
	private static final long serialVersionUID = 1L;
	
	private VentanaPrincipal ventana;
	private JScrollPane pCentral;
	private JPanel pSur;
	private JButton btnAniadir, btnBorrar, btnDetalles;
	private JTable tabla;
	private ModeloTabla modelo;
	private DefaultMutableTreeNode nodo;
	private JTree jTree;
	private DefaultTreeModel treeModel;
	
	public PanelInventario(VentanaPrincipal ventana) {
		this.ventana = ventana; //nos interesa saber la ventana porque ahi es donde tenemos los datos almacenados.
		
		this.setLayout(new BorderLayout());
		
		//Panel central
		modelo = new ModeloTabla(ventana.productos);
		tabla = new JTable(modelo);
		
		pCentral = new JScrollPane(tabla);
		this.add(pCentral, BorderLayout.CENTER);
		
		//Panel oeste
		nodo = new DefaultMutableTreeNode("DEUSTOCOFFEE");
		treeModel = new DefaultTreeModel(nodo);
		jTree = new JTree(treeModel);
		jTree.expandPath(new TreePath(nodo.getPath()));
		cargarArbol();

		
		jTree.addKeyListener(new KeyAdapter() {		
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_C && e.isControlDown()) {
					String tipo = JOptionPane.showInputDialog("Nuevo tipo: ");
					ventana.tipos.add(tipo);
					cargarArbol();
				}
			}
		});
		
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
		
		this.add(jTree,BorderLayout.WEST);
		
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
		
		public ModeloTabla(ArrayList<Producto> productos) {
			super();
			this.productos = productos;
		}

		@Override
		public int getRowCount() {
			return productos.size();
		}

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public String getColumnName(int column) {
			return header[column];
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if(columnIndex == 0) {
				return false;
			}else {
				return true;
			}
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
	
	private void filtrarModelo(String filtro) {
		/**
		 * Este método filtra el contenido del modelo por un parámetro.
		 */
		ArrayList<Producto> filtrada = new ArrayList<Producto>();
		ventana.productos.forEach(p -> {
			if(p.getTipo() == filtro) {
				filtrada.add(p);
			}
		});
		ModeloTabla modelo = new ModeloTabla(filtrada);
		tabla.setModel(modelo);
		tabla.repaint();
	}
	
	private void cargarModelo() {
		/**
		 * Este método carga el modelo con todos los productos.
		 */
		ArrayList<Producto> productos = ventana.productos;
		ModeloTabla modelo = new ModeloTabla(productos);
		tabla.setModel(modelo);
		tabla.repaint();
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

}

