import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class inage_change extends JFrame implements ActionListener {
	private BufferedImage img; //ȭ�� �̹��� ���
	private JButton button1, button2, button3, button4; //���� ��ư, ���� ��ư, ���� �߰�, ���� ����
	private JPanel imgPanel,  namePanel; // �̹��� ���� â, ���� �̸� ������ â	
	private int button_index = 0; // �̹��� �ε���(1 ~ ���� �ִ� ����, 0�� �����)
	private int MAX_SIZE = 3; //�̹��� �ִ� ����
	private Checkbox text = new Checkbox("üũ�ڽ�"); 
	public image_change() {
		
		JPanel panel = new JPanel();

		setTitle("���� ����");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);

		imgPanel = new ChangeImagePanel();
		namePanel = new JPanel();
		
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
	 
	public void actionPerformed(ActionEvent e) {
		boolean flag = false;
		String imgFile = ".jpg"; // ���߿� png, jpg, ���
		if(e.getSource() == button1 && button_index < MAX_SIZE) {
			button_index++;
			flag = true;
			System.out.print(flag); //** ���� üũ ���߿� ����
		} else if(e.getSource() == button2 && button_index > 1) {
			button_index--;
			flag = true;
			System.out.print(button_index); //** ���� üũ ���߿� ����
		}
		try {
			if(flag == true)
			img = ImageIO.read(new File("image\\" + button_index + imgFile)); //�̹��� ������ ��������
		} catch (IOException e1) {
			try {
				img = ImageIO.read(new File("image\\100.jpg")); // �̹��� ������ �̹��� ����
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		
		imgPanel.repaint();
		}

	
	public static void main(String[] args) {
		new image_change();
	}

}
/*
https://calsifer.tistory.com/239
https://programmingsummaries.tistory.com/61    GUI ����
*/