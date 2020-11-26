import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.Vector;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.transform.Source;
/* 	1. pair<int,string> �̿��ؼ� (index, ���� �̸�) ����
 	2. ���������� �����
	3. ���� ����
	4. ���� �˻�
*/
public class image_change extends JFrame implements ActionListener {
	private BufferedImage img; //ȭ�� �̹��� ���
	private JButton button1, button2, button3, button4, button5; //���� ��ư, ���� ��ư, ���� �߰�, ���� ����, ���� �˻�
	private JPanel imgPanel; // �̹��� ������ â
	private int button_index = 0; // �̹��� �ε���(1 ~ ���� �ִ� ����, 0�� �̹������� ����)
	private int MAX_SIZE = 0, imageCnt = 0; //�̹��� �ִ� ����, �̹��� �߰� ����
	private boolean flag = false, flag2 = false;// (����, ����) , (���� �߰�, ���� ����)
	private String imageName; // �߰��� ���� �̸�
	
	Vector<String> list = new Vector<String>();  // ���� ��� �̸� ����

	JFileChooser fc;
	public image_change() {
		
		mkDir(); //�̹��� ������ ������ ������ ������ ����� �̸� �����ص� �̹����� ������.
		
		fc = new JFileChooser(); //���� ���� 
		fc.setMultiSelectionEnabled(true);
		JPanel panel = new JPanel(); // ��ư��

		setTitle("���μ� �ٹ�"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false); // â ���� x

		imgPanel = new ChangeImagePanel();
		
		// �ʱ� ����
		list.add("image\\0.jpg"); // ��ư�ε����� 1 ���� �����ؼ� 0.jpg�� �ȳ���(��ư���� ����, �������� �����ؼ� ������ �ϳ��� ������ ����)
		list.add("image\\1.jpg");
		
		MAX_SIZE += 2; // 0.jpg = �̹������� ��ư,  1.jpg = ó�� �⺻ �̹��� => ���� �ִ� ������ 0+2 
		
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
		if(e.getSource() == button1 && button_index < MAX_SIZE-1) { // ���� ������ MAX�� button_index�� �ȴþ.
			button_index++;
			flag = true; // ���� 	
		// ����
		} else if(e.getSource() == button2 && button_index > 1 && MAX_SIZE > 0) { // ���� ������ 1���� ������ button_index�� �ȳ�����. ���� �� ����� button_index == 0�Ǽ�, �̹��� ���� ����.
			button_index--;
			flag = true; // ����
		}
		
		//���� ����
		if(e.getSource() == button3) {
			// jpg, png�� ����Ʈ��
			FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "png", "jpg", "png");
		    // �� ���� ����
		    fc.setFileFilter(filter);
		    // �����߰� â �̸�
		    fc.setDialogTitle("�̹��� ���� (jpg�� �ƴϸ� �ð��� ���� �ɸ��ϴ�.)");

		    // ���̾�α� ����
		    int Dialog = fc.showOpenDialog(this);
		    // �� Ȯ�ν�
		    if(Dialog == JFileChooser.APPROVE_OPTION) {
		      	flag2 = true;
		        // ���� ����
		      	File[] f = fc.getSelectedFiles();
		      	
		        for(File n : f) {
		        System.out.println(++imageCnt + "��° �߰��� ���� �̸�: " + n.getName());
		        imageName = n.getName();
		        
		        copyFile(fc.getSelectedFile(), imageName); //�̹��� ���� ����ֱ�(img���, ������ img�̸�)
		        list.add(new String("image\\" + imageName));
		        button_index = MAX_SIZE;
		        MAX_SIZE++; // ���� �ִ� ����
		        }
	        }
		}
		
		// ���� ����
		if(e.getSource() == button4 && button_index > 0) {
			list.remove(button_index);
			button_index--;
			if(button_index == 0 && MAX_SIZE > 2) button_index = 1; // ������ �������ε�, ù ��° ������ ����� �̹��� ���������� �����°� ����
			MAX_SIZE--;			
			flag2 = true;
			System.out.println(button_index + 1 + "��° ������ ���� �߽��ϴ�.");
			if(MAX_SIZE == 1) System.out.println("������ ���� �����ؼ� ������ �����ϴ�.");
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
		
		// �ǹ��ִ� �ൿ�� ��쿡�� ���������� ��Ÿ��.
		if(flag == true || flag2 == true) {
			System.out.println("���� ������: " + button_index);
			System.out.println("");
		}
		
		//������ �ʱ�ȭ
		flag = false;
		flag2 = false;
		imgPanel.repaint();
	}
	
	// image������ ������ image������ ����� �̸� ������ image_set������ �ִ� �̹��� 2���� ����.
	public void mkDir() {
		String path = "image"; //���� ���
		File Folder = new File(path);

		// �ش� ���丮�� ������� ���丮�� �����մϴ�.
		if (!Folder.exists()) {
			try{
			    Folder.mkdir(); //���� �����մϴ�.
			    System.out.println("������ �����Ǿ����ϴ�.");
		        copyFile("image_set\\", "0.jpg"); //�̹��� ���� ����ֱ�
		        copyFile("image_set\\", "1.jpg"); //�̹��� ���� ����ֱ�
		    } catch(Exception e) {
		    	e.getStackTrace();
			}        
	     }
	}
	
	// �ʱ� �ٹ� �⺻ ���� ����
	public void copyFile(String path, String imageName) {
        //����� ���ϰ��
        String copyFilePath = "image\\" + imageName;
        //�������ϰ�ü����
        File copyFile = new File(copyFilePath);
        System.out.println(copyFilePath + "������ �����߽��ϴ�.");
        try {           
            FileInputStream fis = new FileInputStream("image_set\\" + imageName); //�����ص� image_set ���� �ٹ� �⺻�̹����� ������.
            FileOutputStream fos = new FileOutputStream(copyFile); //����������
            
            int fileByte = 0; 
            // fis.read()�� -1 �̸� ������ �� ������
            while((fileByte = fis.read()) != -1) {
                fos.write(fileByte);
            }
            //�ڿ��������
            fis.close();
            fos.close();
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
	}

	// ���� ��ư�� �̿��� ���� ����
	public void copyFile(File Path, String imageName) {
        //����� ���ϰ��
        String copyFilePath = "image\\" + imageName;
        //�������ϰ�ü����
        File copyFile = new File(copyFilePath);
        System.out.println(copyFilePath + "������ �����߽��ϴ�.");
        try {
            
            FileInputStream fis = new FileInputStream(Path); //��������
            FileOutputStream fos = new FileOutputStream(copyFile); //����������
            
            int fileByte = 0; 
            // fis.read()�� -1 �̸� ������ �� ������
            while((fileByte = fis.read()) != -1) {
                fos.write(fileByte);
            }
            //�ڿ��������
            fis.close();
            fos.close();
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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