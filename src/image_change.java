import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class image_change extends JFrame implements ActionListener {
	private BufferedImage img; //ȭ�� �̹��� ���
	private JButton button1, button2, button3, button4, button5; //���� ��ư, ���� ��ư, ���� �߰�, ���� ����
	private JPanel imgPanel; // �̹��� ���� â, ���� �̸� ������ â	
	private JLabel insertLabel;
	private int button_index = 0; // �̹��� �ε���(1 ~ ���� �ִ� ����, 0�� �����)
	private int MAX_SIZE = 3, imageCnt = 0; //�̹��� �ִ� ����, �̹��� �߰� ����
	private boolean flag = false, flag2 = false, flag3 = false; // (����, ����) , ���� �߰�, ���� ����
	private Dialog dd = new Dialog(this, "���� �߰�");
	private JTextField textfield = new JTextField();
	String imageName;
	JFileChooser fc;
	public image_change() {
		fc = new JFileChooser();
		fc.setMultiSelectionEnabled(true);

		JPanel panel = new JPanel();

		setTitle("���� ����");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);

		imgPanel = new ChangeImagePanel();
		
		
		// ������Ʈ �����
		button1 = new JButton("����");
		button2 = new JButton("����");
		button3 = new JButton("���� �߰�");
		button4 = new JButton("���� ����");
		
		// �гο� ������Ʈ ���̱�
		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(button4);

		// ��ư �׼� �۵�
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		
		add(imgPanel, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
		
	}
	class ChangeImagePanel extends JPanel {
		public ChangeImagePanel() {
			
		}
	    @Override
	    public void paint(Graphics g) {
	    	g.drawImage(img, 100, 100, 500, 500, null); //����, ������ġ, ������ġ, ����ũ��, ����ũ��, null
	    }
	    
	    @Override
	    public Dimension getPreferredSize() {
	    	if (img == null) {
	    		return new Dimension(700,700); // ��üȭ�� ũ��
	    		} else {
	    		return new Dimension(img.getWidth(), img.getHeight());
	        }
	    }
	}
	
	// ���� (����, ����)
	public void actionPerformed(ActionEvent e) {

		String imgFile = ".jpg"; // ���߿� png, jpg, ���
		if(e.getSource() == button1 && button_index < MAX_SIZE) {
			button_index++;
			flag = true;

		} else if(e.getSource() == button2 && button_index > 1) {
			button_index--;
			flag = true;
		}
		if(e.getSource() == button3) {
		    {
		        // jpg, png�� ����Ʈ��
		        FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "png", "jpg", "png");
		        // �� ���� ����
		        fc.setFileFilter(filter);
		        // ���̾�α� ����
		        int Dialog = fc.showOpenDialog(this);
		        // �� Ȯ�ν�
		        if(Dialog == JFileChooser.APPROVE_OPTION) 
		        {
		        	flag2 = true;
		            // ���� ����
		            File[] f = fc.getSelectedFiles();
		            for(File n : f) {
		            System.out.println(++imageCnt + "��° �߰��� ����: " + n.getName());    
		            imageName = n.getName();
		            }
		        }
		    }
		}
		try {
			if(flag == true)
				img = ImageIO.read(new File("image\\" + button_index + imgFile)); //�̹��� ������ ��������
			if(flag2 == true)
				img = ImageIO.read(new File("image\\" + imageName));
		} catch (IOException e1) {
			try {
				img = ImageIO.read(new File("image\\100.jpg")); // �̹��� ������ �̹��� ����
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		flag = false;
		imgPanel.repaint();
	}
	
	public static void main(String[] args) {
		new image_change();
	}

}
/*
https://calsifer.tistory.com/239
https://programmingsummaries.tistory.com/61    GUI ����
https://raccoonjy.tistory.com/17
*/