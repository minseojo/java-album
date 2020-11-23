import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
 
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
 
public class JFileChooserTest extends JFrame implements ActionListener {
    // JFileChooser 선언
    JFileChooser fc;
    public JFileChooserTest() {
        // JFileChooser 생성
        fc = new JFileChooser();
        // 여러가지 선택 가능
        fc.setMultiSelectionEnabled(true);
        // JButton 생성
        JButton btn = new JButton("파일선택");
        btn.addActionListener(this);
        add("North", btn);
        setBounds(0, 0, 200, 200);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        // jpg, png가 디폴트값
        FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "png");
        // 위 내용 적용
        fc.setFileFilter(filter);
        // 다이어로그 생성
        int Dialog = fc.showOpenDialog(this);
        // 예 확인시
        if(Dialog == JFileChooser.APPROVE_OPTION) 
        {
        	int i=0;
            // 파일 선택
            File[] f = fc.getSelectedFiles();
            for(File n : f) {
            System.out.println(++i + "번째 추가한 사진: " + n.getName());    
            //	img =n.getName() ;
            }
        }
    }
    public static void main(String[] args)  {
    	new	JFileChooserTest();    
    }
}