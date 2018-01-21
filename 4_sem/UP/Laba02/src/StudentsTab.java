/**
 * Created by alex on 2/27/17.
 */
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class StudentsTab extends JPanel {
    List<Student> students;
    JTree tree;
    ImageIcon rootIcon;
    ImageIcon nodeIcon;
    ImageIcon leafIcon;

    public StudentsTab() {
        super();
        students = new ArrayList<>();
        rootIcon = new ImageIcon("img/root.png");
        nodeIcon = new ImageIcon("img/node.png");
        leafIcon = new ImageIcon("img/leaf.png");
        setLayout(new BorderLayout());
        try {
            students = loadStudents();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Incorrect file");
            System.exit(666);
        }
        tree = new JTree();
        tree.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    if (node == null || !node.isLeaf()) {
                        return;
                    }
                    Student oldStudent = (Student) node.getUserObject();
                    StudentDialog dialog = new StudentDialog(oldStudent);
                    dialog.setVisible(true);
                    Student newStudent = dialog.getData();
                    if (newStudent != null) {
                        students.remove(oldStudent);
                        students.add(newStudent);
                        showAll();
                    }
                }
            }
        });

        tree.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (e.VK_DELETE == e.getKeyChar()) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    if (node == null || !node.isLeaf()) {
                        return;
                    }
                    students.remove((Student) node.getUserObject());
                    showAll();
                }
            }
        });

        add(new JScrollPane(tree), BorderLayout.CENTER);
        MyRenderer rend = new MyRenderer();
        rend.setLeafIcon(leafIcon);
        tree.setCellRenderer(rend);
        showAll();
    }

    private void showAll() {
        tree.setModel(new DefaultTreeModel(buildTree(students)));
        expandAllNodes(tree, 0, tree.getRowCount());

    }

    private class MyRenderer extends DefaultTreeCellRenderer {
        public MyRenderer() {
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree,
                                                      Object value, boolean selected, boolean expanded,
                                                      boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            if (tree.getModel().getRoot().equals(node)) {
                setIcon(rootIcon);
            } else if (node.getChildCount() > 0) {
                setIcon(nodeIcon);
            } else {
                setIcon(leafIcon);
            }
            return this;
        }
    }

    private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
        for (int i = startingIndex; i < rowCount; ++i) {
            tree.expandRow(i);
        }

        if (tree.getRowCount() != rowCount) {
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }

    public void addStudent() {
        StudentDialog dialog = new StudentDialog(null);
        dialog.setVisible(true);
        if (dialog.getData() != null) {
            students.add(dialog.getData());
            showAll();
        }
    }

    private DefaultMutableTreeNode getChild(DefaultMutableTreeNode root, Object tag){
        Enumeration<DefaultMutableTreeNode> enumeration=root.children();
        while(enumeration.hasMoreElements()){
            DefaultMutableTreeNode node=enumeration.nextElement();
            if(node.getUserObject().equals(tag)) return node;
        }
        return null;
    }

    public TreeNode buildTree(List<Student> students){
        DefaultMutableTreeNode root=new DefaultMutableTreeNode("BSU");

        Collections.sort(students,new MyComparator());
        for(Student x : students){
            DefaultMutableTreeNode rnode=root;
            DefaultMutableTreeNode node;

            node=getChild(rnode,x.getCourse());
            if(node==null){
                node=new DefaultMutableTreeNode(x.getCourse());
                rnode.add(node);
            }
            rnode=node;

            node=getChild(rnode,x.getGroup());
            if(node==null){
                node=new DefaultMutableTreeNode(x.getGroup());
                rnode.add(node);
            }
            rnode=node;

            node=getChild(rnode,x.getName());
            if(node==null){
                node=new DefaultMutableTreeNode(x);
                rnode.add(node);
            }
            rnode=node;
        }
        return root;
    }

    public List<Student> loadStudents() throws FileNotFoundException{
        List<Student> students=new ArrayList<>();
        Scanner scanner = new Scanner(new File("students.txt"));
        try{
            while(scanner.hasNext())
                students.add(new Student(scanner.nextLine()));
        }catch (Exception ex){

        }
        return students;
    }

}

