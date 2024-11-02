package ventanasGraficas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;

import javax.swing.AbstractCellEditor;
import javax.swing.CellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.CellEditorListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
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
	private RenderTabla render;
	private SpinnerEditor editor;
	private DefaultMutableTreeNode nodo;
	private JTree jTree;
	private DefaultTreeModel treeModel;
	
	public PanelInventario(VentanaPrincipal ventana) {
		this.ventana = ventana; //nos interesa saber la ventana porque ahi es donde tenemos los datos almacenados.
		
		this.setLayout(new BorderLayout());
		
		//Panel central
		modelo = new ModeloTabla(ventana.productos);
		tabla = new JTable(modelo);
		tabla.setRowHeight(25);
		tabla.getTableHeader().setReorderingAllowed(false);
		render = new RenderTabla();
		tabla.setDefaultRenderer(Object.class, render);
		editor  = new SpinnerEditor();
		tabla.getColumn("TOTAL").setCellEditor(editor);
		tabla.getColumn("PRECIO/UNIDAD").setCellEditor(editor);
		
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
		
		btnAniadir.addActionListener(e -> {
			JLabel lblName = new JLabel("Nombre: ");
			JLabel lblDescripcion = new JLabel("Descripción: ");
			JLabel lblTipo = new JLabel("Tipo");
			JTextField txtName = new JTextField(20);
			JTextArea txtDescripcion = new JTextArea();
			JLabel lblId = new JLabel("ID: ");
			JTextField txtId = new JTextField();
			String[] suport = new String[ventana.tipos.size()];
			suport = ventana.tipos.toArray(suport);
			JComboBox<String> comboBox = new JComboBox<>(suport);
			JComponent[] components = {lblName, txtName,lblId, txtId ,lblDescripcion, new JScrollPane(txtDescripcion), lblTipo, comboBox};
			if(JOptionPane.showConfirmDialog(null, components) == JOptionPane.OK_OPTION ) {
				Producto p = new Producto(txtName.getText(), comboBox.getSelectedItem().toString(), txtDescripcion.getText(), 0, Integer.parseInt(txtId.getText()), 0);
				if(ventana.productos.keySet().contains(Integer.parseInt(txtId.getText()))) {
					JOptionPane.showConfirmDialog(ventana, "Introduce un ID que aun no esté registrado.");
				}else {
				ventana.productos.put(Integer.parseInt(txtId.getText()), p);
				cargarModelo();
				}
			}
			tabla.repaint();
			tabla.getColumn("TOTAL").setCellEditor(editor);
			tabla.getColumn("PRECIO/UNIDAD").setCellEditor(editor);
		});
		
		btnBorrar.addActionListener(e -> {
			if(tabla.getSelectedRow() != -1) {
				int id = (int) tabla.getModel().getValueAt(tabla.getSelectedRow(), 0);
				ventana.productos.remove(id);
				cargarModelo();
				tabla.getColumn("TOTAL").setCellEditor(editor);
				tabla.getColumn("PRECIO/UNIDAD").setCellEditor(editor);
			}
		});
		
		btnDetalles.addActionListener(e -> {
			if(tabla.getSelectedRow() != -1) {
				VentanaDetallesProducto ventanaDetalle = new VentanaDetallesProducto(ventana.productos.get((int) tabla.getModel().getValueAt(tabla.getSelectedRow(), 0)), ventana);
			}
		});
		
		this.add(pSur,BorderLayout.SOUTH);
		
	}
	
	private class ModeloTabla extends AbstractTableModel{
		private HashMap<Integer, Producto> productos;
		private String[] header = {"ID","NOMBRE", "PRECIO/UNIDAD", "TOTAL"};
		
		public ModeloTabla(HashMap<Integer, Producto> productos) {
			super();
			this.productos = productos;
		}
		
		@Override
		public void setValueAt(Object value, int row, int col) {
			int key = (int) productos.keySet().toArray()[row];
			if(col == 3) {
			productos.get(key).setCantidad((int) value);
			}else if(col == 2) {
				productos.get(key).setPrecio(Double.parseDouble(value.toString()));
			}
		}

		@Override
		public int getRowCount() {
			return productos.size();
		}

		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public String getColumnName(int column) {
			return header[column];
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if(columnIndex == 0 || columnIndex == 1) {
				return false;
			}else {
				return true;
			}
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch(columnIndex) {
			case 0: return productos.get(productos.keySet().toArray()[rowIndex]).getIdProducto();
			case 1: return productos.get(productos.keySet().toArray()[rowIndex]).getNombre();
			case 2: return productos.get(productos.keySet().toArray()[rowIndex]).getPrecio();
			case 3: return productos.get(productos.keySet().toArray()[rowIndex]).getCantidad();
			default: return null;
			}
		}
	}
	
	private class RenderTabla implements TableCellRenderer{

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			JPanel panel = new JPanel();
			JLabel lbl = new JLabel(value.toString());
			
			if((Integer)table.getModel().getValueAt(row, 3) <= 0) {
				panel.setBackground(Color.red);;
			}
			
			lbl.setHorizontalAlignment(SwingConstants.CENTER);
			lbl.setVerticalAlignment(SwingConstants.CENTER);
			panel.add(lbl);
			return panel;
		}
		
	}
	
	private class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {
	    private final JSpinner spinner;

	    public SpinnerEditor() {
	        spinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1)); // Configuración del spinner
	    }

	    @Override
	    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	        spinner.setValue(value);
	        return spinner;
	    }

	    @Override
	    public Object getCellEditorValue() {
	        return spinner.getValue();
	    }

	    @Override
	    public boolean isCellEditable(EventObject e) {
	        return true;
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
	
	private void cargarModelo() {
		/**
		 * Este método carga el modelo con todos los productos.
		 */
		HashMap<Integer, Producto> productos = ventana.productos;
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

