import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.Vector;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
/* 	1. pair<int,string> �̿��ؼ� (index, ���� �̸�) ����
 	2. ������ ���� �߰��ϱ�
	3. ���� ����
	4. ���� �˻�
*/
public class image_change extends JFrame implements ActionListener {
	private BufferedImage img; //ȭ�� �̹��� ���
	private JButton button1, button2, button3, button4, button5; //���� ��ư, ���� ��ư, ���� �߰�, ���� ����, ���� �˻�
	private JPanel imgPanel; // �̹��� ������ â
	private int button_index = 0; // �̹��� �ε���(1 ~ ���� �ִ� ����, 0�� �����)
	private int MAX_SIZE = 2, imageCnt = 1; //�̹��� �ִ� ����, �̹��� �߰� ����
	private boolean flag = false, flag2 = false, flag3 = false; // (����, ����) , ���� �߰�, ���� ����
	private String imageName; // �߰��� ���� �̸�
	
	Vector<String> list = new Vector<String>();

	JFileChooser fc;
	public image_change() {
		fc = new JFileChooser(); //���� ���� 
		fc.setMultiSelectionEnabled(true);
		JPanel panel = new JPanel(); // ��ư��

		setTitle("���μ� �ٹ�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false); // â ���� x

		imgPanel = new ChangeImagePanel();
		
		// �ʱ� ����
		list.add("image\\0.jpg"); // �ε��� 1 ���� �����ؼ� ������ �̹��� ����(��ư���� ����, �������� �����ؼ� ������ �ϳ��� ������ ����)
		list.add("image\\1.jpg");
		list.add("image\\2.jpg");
		
		// ������Ʈ(��ư) �����
		button1 = new JButton("���� ����");
		button2 = new JButton("���� ����");
		button3 = new JButton("���� �߰�");
		button4 = new JButton("���� ����");
		button5 = new JButton("���� �˻�");
		
		// ��ư�� ����
		button1.setBackground(Color.WHITE);
		button2.setBackground(Color.WHITE);
		button3.setBackground(Color.WHITE);
		button4.setBackground(Color.WHITE);
		button5.setBackground(Color.WHITE);
		
		// ��ư �׼� �۵�
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		
		// �гο� ������Ʈ ���̱�
		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(button4);
		panel.add(button5);
		panel.setBackground(Color.darkGray);
				
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
	    	g.drawImage(img, 0, 0, 480, 500, null); //����, ������ġ, ������ġ, ����ũ��, ����ũ��, null
	    }
	    
	    @Override
	    public Dimension getPreferredSize() {
	    	if (img == null) {
	    		return new Dimension(480,500);
	    		} else {
	    		return new Dimension(img.getWidth(), img.getHeight());
	        }
	    }
	}
	
	// ���� (����, ����)
	public void actionPerformed(ActionEvent e) {

		String imgFile = ".jpg"; // ���߿� png, jpg, ���
		//����
		if(e.getSource() == button1 && button_index < MAX_SIZE) { // ���� ������ MAX�� button_index�� �ȴþ.
			button_index++;
			flag = true; // ���� 		
		// ����
		} else if(e.getSource() == button2 && button_index > 1) { // ���� ������ 1���� ������ button_index�� �ȳ�����. ���� �� ����� button_index == 0�Ǽ�, �̹��� ���� ����.
			button_index--;
			flag = true; // ����
		}
		
		//���� ����
		if(e.getSource() == button3) {
			// jpg, png�� ����Ʈ��
			FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "png", "jpg", "png");
		    // �� ���� ����
		    fc.setFileFilter(filter);
		    // ���̾�α� ����
		    int Dialog = fc.showOpenDialog(this);
		    // �� Ȯ�ν�
		    if(Dialog == JFileChooser.APPROVE_OPTION) {
		      	flag2 = true;
		        // ���� ����
		      	File[] f = fc.getSelectedFiles();
		        for(File n : f) {
		        System.out.println(imageCnt + "��° �߰��� ���� �̸�: " + n.getName());
		        button_index = MAX_SIZE;
		        imageCnt++; //�߰� �� ���� ����
		        imageName = n.getName();
		        list.add(new String("image\\" + imageName));
		        button_index++;
		        MAX_SIZE++; // ���� �ִ� ����
		        }
	        }
		}
		
		// ���� ����
		if(e.getSource() == button4 && button_index > 0) {
			list.remove(button_index);
			if(button_index == 1) {
				button_index = MAX_SIZE; //������ 2���̻��ε�, 1������ �����ϸ� �̹��� ������ ����. => ����
			}
			button_index--;			
			MAX_SIZE--;
			flag = true;
			System.out.print(button_index);
		}
		
		// �ൿ
		try {
			if(flag == true)
				img = ImageIO.read(new File(list.get(button_index).toString())); //�̹��� ������ ��������
			if(flag2 == true)
				img = ImageIO.read(new File(list.get(button_index).toString()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//������ �ʱ�ȭ
		flag = false;
		flag2 = false;
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