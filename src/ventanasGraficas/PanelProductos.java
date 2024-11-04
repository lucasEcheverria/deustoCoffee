package ventanasGraficas;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

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
		
		/*jTree.addMouseListener(new MouseAdapter(){
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
		});;*/
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
		 * Este método carga el JComboBox las cuentas existentes.
		 */
		comboBox.removeAllItems();
		for(Integer id : ventana.cuentas.keySet()) {
			comboBox.addItem(id);
		}
	}
}
