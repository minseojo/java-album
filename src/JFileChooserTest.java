import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
 
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
 
public class JFileChooserTest extends JFrame implements ActionListener {
    // JFileChooser ����
    JFileChooser fc;
    public JFileChooserTest() {
        // JFileChooser ����
        fc = new JFileChooser();
        // �������� ���� ����
        fc.setMultiSelectionEnabled(true);
        // JButton ����
        JButton btn = new JButton("���ϼ���");
        btn.addActionListener(this);
        add("North", btn);
        setBounds(0, 0, 200, 200);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        // jpg, png�� ����Ʈ��
        FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "png");
        // �� ���� ����
        fc.setFileFilter(filter);
        // ���̾�α� ����
        int Dialog = fc.showOpenDialog(this);
        // �� Ȯ�ν�
        if(Dialog == JFileChooser.APPROVE_OPTION) 
        {
        	int i=0;
            // ���� ����
            File[] f = fc.getSelectedFiles();
            for(File n : f) {
            System.out.println(++i + "��° �߰��� ����: " + n.getName());    
            //	img =n.getName() ;
            }
        }
    }
    public static void main(String[] args)  {
    	new	JFileChooserTest();    
    }
}