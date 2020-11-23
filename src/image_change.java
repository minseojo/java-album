import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class image_change extends JFrame implements ActionListener {
	private BufferedImage img; //ȭ�� �̹��� ���
	private JButton button1, button2; //���� ��ư, ���� ��ư
	private JPanel imgPanel; // �̹��� ���� â	
	private int button_index = 0; // �̹��� �ε���(1 ~ ���� �ִ� ����, 0�� �����)
	private int MAX_SIZE = 3; //�̹��� �ִ� ����
	public image_change() {
		
		setTitle("���� ����");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		
		imgPanel = new ChangeImagePanel();
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));		
		panel.add(button1 = new JButton("����"));
		button1.addActionListener(this);
		panel.add(button2 = new JButton("����"));
		button2.addActionListener(this);
		
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
		String imgFile = ".jpg"; // ���߿� png, jpg, ���
		int imageWidth, imageHeight;
		if(e.getSource() == button1 && button_index < MAX_SIZE) {
			button_index++;
			System.out.print(button_index); //** ���� üũ ���߿� ����
		} else if(e.getSource() == button2 && button_index > 1) {
			button_index--;
			System.out.print(button_index); //** ���� üũ ���߿� ����
		}
		try {
			img = ImageIO.read(new File("image\\" + button_index + imgFile)); //�̹��� ������ ��������
            imageWidth = img.getWidth(null);  // �̹��� ������ ��������
            imageHeight = img.getHeight(null); //�̹��� ������ ��������
            Image resizeImage = img.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
            BufferedImage newImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);

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
*/