import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.awt.image.*;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.transform.Source;
/*
 * ���� �������� �Ȳ����� �����Ҷ�, ������ n*n+1/2���� ����	
*/

public class image_change extends JFrame implements ActionListener {
	private BufferedImage img; //ȭ�� �̹��� ���
	private JButton button1, button2, button3, button4, button5; //���� ��ư, ���� ��ư, ���� �߰�, ���� ����, ���� �˻�
	private JPanel imgPanel; // �̹��� ������ â
	private int button_index = 0; // �̹��� �ε���(1 ~ ���� �ִ� ����, 0�� �̹������� ����)
	private int MAX_SIZE = 0, imageCnt = 1; //�̹��� �ִ� ����, �̹��� �߰� ���� ()
	private boolean flag = false, flag2 = false, flag3 = false;// (����, ����) , (���� �߰�, ���� ����)
	private String imageName; // �߰��� ���� �̸�
	private JButton btn = new JButton("����");
	Vector<String> list = new Vector<String>();  // ���� ��� �̸� ����
	Vector<String> name = new Vector<String>(); // ���� �̸� ����
	Vector<String> memo = new Vector<String>(); // ���� �޸� ����
	Vector<String> list2 = new Vector<String>();
	private JTextField tfd = new JTextField(null, 20); // ���� �̸� �Է� 
	private JTextField tfd2 = new JTextField(null, 20); // �޸� ���� �Է�
	String name2, memo2;
	JLabel label1 = new JLabel(), label2 = new JLabel(); // �̹���â�� �����̸�, ���� ���� ������ ��
	JFileChooser fc;	
	public image_change() {
		mkDir(); //�̹��� ������ ���� + name.txt
		fc = new JFileChooser(); //���� ���� 
		fc.setMultiSelectionEnabled(true);
		String buf = "";

		char[] arr = new char[1000];
		
		File file = new File("image\\test.txt");		  
		
		//buf�� ���� ���� �б�
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    int i=0;
		    while ((line = br.readLine()) != null) {
		    	if(line.equals("|")) i++; // ������|�� ����
		    	buf += line;
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}   
		
		// arr[i]�� buf���������ϱ�
		for(int i=0; i<buf.length(); i++) { //�޸����� ó���� ����� 0��° �ε����� |�� �ʱ�ȭ�������Ƿ� 1����
			arr[i] = buf.charAt(i);
		}
		System.out.println("���� ����� ����");
		showFilesInDIr("image\\");	
		int cnt=0;
		int start=0;
		String s = ""; // ���� ���
		String s2 = "";
		String s3 = ""; //���� ����
		boolean flag5 = false, flag6 = false;
		System.out.println("���� ����� ���� �̸� (�ð��� ���ٸ� ���� �̸��� �˻� �ϼ���.)");
		for(int i=0; i<1000; i++) { // 0�� |�� �ʱ�ȭ �����Ƿ� 1���� ����
			if(arr[i] == '|') { // ���� ������ ������
				s = ""; // �ʱ�ȭ
				s2 = "";
				s3 = ""; // �ʱ�ȭ
				flag5 = false;
				flag6 = false; // �ʱ�ȭ
				MAX_SIZE++;
				for(int j=start; j<i; j++) {
					if(arr[j] == ';') {
						s2="";
						flag5=true;
						j++;
					}
					if(arr[j] == ':') { // :������ �������, :���Ŀ��� ���� ����
						s3="";    
						flag6=true;
						j++;
					}
					if(flag5 == false) { // ���� ���
						s+=arr[j];
					}
					if(flag5==true && flag6 == false) {
						s2+=arr[j];
					}
					s3+=arr[j];
				}
				if(cnt!=0) // ������ ó������ �о�� �������� �Ǵµ�, ó�� ������ null�̹Ƿ� �Ѿ
					System.out.println(cnt + ". "+ s2); // ���� �̸� ���
					//name.add(s2);  // ���� �̸� ����
					//memo.add(s3); //���� ���� ����
					start=i+1; // ���� ������ �����Ҷ��� ���۰��� ���� ������ ������ ���� �ε�����
					cnt++; // ���� ����� �� ���� ����
			}
		}
		
		for(int i=0; i<list2.size(); i++) {
			list.add(list2.get(i));
			name.add("sd");
			memo.add("ASDas");
		}
		
		imgPanel = new ChangeImagePanel();
		JPanel panel = new JPanel(); // ��ư��
		JPanel panel2 = new JPanel(); // ���� �̸�, �޸� ����
		JPanel panel3 = new JPanel(); // ���� �̸� �ؽ�Ʈ, ���� ���� �ؽ�Ʈ
		JPanel panel4 = new JPanel(); // 2,3 ��ħ
		
		label1.setText(name.get(button_index));
		label2.setText(memo.get(button_index));
		
		setTitle("���μ� �ٹ�"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false); // â ���� x

		// ������Ʈ(��ư) �����
		button1 = new JButton("���� ����");
		button2 = new JButton("���� ����");
		button3 = new JButton("���� �߰�");
		button4 = new JButton("���� ����");
		button5 = new JButton("���� �˻�");
		
		//��ư ũ�� ���δ� 480�̹Ƿ� 480/5 = 96 > ����ũ��
		button1.setPreferredSize(new Dimension(90, 35));
		button2.setPreferredSize(new Dimension(90, 35));
		button3.setPreferredSize(new Dimension(90, 35));
		button4.setPreferredSize(new Dimension(90, 35));
		button5.setPreferredSize(new Dimension(90, 35));
		
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
		
		//panel2.add(new JLabel(name.get(button_index).toString()));
		panel2.setLayout(new GridLayout(1,2));
		panel2.setBackground(Color.DARK_GRAY);
		panel3.setLayout(new GridLayout(1,2));
		panel3.setBackground(Color.DARK_GRAY);
		JLabel imgN = new JLabel("���� �̸�: ");
		JLabel imgM = new JLabel("���� ����: ");
		imgN.setFont(new Font("����", Font.BOLD, 22));
		imgM.setFont(new Font("����", Font.BOLD, 22));
		imgN.setForeground(Color.white);
		imgM.setForeground(Color.white);
		
		panel2.add(imgN);
		panel2.add(label1);
		panel3.add(imgM);
		panel3.add(label2);
		panel4.add(panel2);
		panel4.add(panel3);
		panel4.setLayout(new GridLayout(2,2));
		add(imgPanel, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
		add(panel4, BorderLayout.NORTH);
		pack();
		setVisible(true);
		
	}
	
	private void showFilesInDIr(String string) {
		File dir = new File("image\\");
	    File files[] = dir.listFiles();

	    for (int i = 0; i < files.length; i++) {
	        File file = files[i];
	        if (file.isDirectory()) {
	            showFilesInDIr(file.getPath());
	        } else {
	            list2.add(file.getPath());
	            System.out.println(file);
	        }
	    }
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
	
	//���� ���� â
	public void prepareGui() {
		JFrame subFrame = new JFrame("���� ���� (��ø� ��ٷ� �ּ���.)");
		subFrame.setSize(380,165);
		subFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {	
				subFrame.setVisible(false);
				subFrame.dispose();
			}
		});				
		
	
		Panel p1 = new Panel();
		p1.add(new Label("���� �̸�: "));

		p1.add(tfd);
		p1.setSize(200, 100);
		
		Panel p2 = new Panel();
		p2.add(new Label("���� ����: "));
		p2.add(tfd2);
		p1.setSize(200, 100);

		Panel p3 = new Panel();
		btn.setBackground(Color.white);
		p3.add(new Label("������ �����ϰ� X�� ��������."));
		p3.add(btn);
		
		Panel p4 = new Panel();
		p4.add(p1);
		p4.add(p2);
		p4.add(p3);
		
		btn.addActionListener(this);
		subFrame.setResizable(false); // â ���� x
		subFrame.add(p4);
		subFrame.setVisible(true);
	}
	
	// ���� (����, ����)
	public void actionPerformed(ActionEvent e) {
		//�������� ���� ��ư
		if(e.getSource() == btn) {
			flag3 = true;
			name.add(tfd.getText());
			memo.add(tfd2.getText());
			tfd.setText(""); // �Է� �� �������� �ʱ�ȭ
			tfd2.setText(""); // �Է��� ����� �ʱ�ȭ
			System.out.println("������ ���� ���: " + "image\\" + imageName);
			System.out.println("������ ���� �̸�: " + name.get(MAX_SIZE - 1).toString());
			System.out.println("������ ���� ����: " + memo.get(MAX_SIZE - 1).toString());
			System.out.println("----------------------------------------------");
			btn.setEnabled(false); // ���� ��ư ��Ȱ��ȭ (����ڰ� �ѹ��� �����ϰ�)
		    //�޸��忡 ���� �̸� ����
	    	String filePath = "image\\test.txt"; //���� ���, ����
			try {
				FileWriter fileWriter = new FileWriter(filePath,true);
				fileWriter.write(imageName + ";" + name.get(button_index) + ":" + memo.get(button_index) + "|"); // |�� �����ڸ� ����				   
				fileWriter.close();
			  } catch (IOException e1) {
				  e1.printStackTrace();
			  }
		}
		
		//����
		if(e.getSource() == button1 && button_index < MAX_SIZE-1 && button_index > -1) { // ���� ������ MAX�� button_index�� �ȴþ.
			button_index++;
			flag = true; // ���� 	
			System.out.println("���� �̸�: " + name.get(button_index)); // ���� �̸� ���
			System.out.println("���� ����: " + memo.get(button_index)); // ���� ���� ���
		} 
		
		// ����
		else if(e.getSource() == button2 && button_index > 1 && MAX_SIZE > 1) { // ���� ������ 1���� ������ button_index�� �ȳ�����. ���� �� ����� button_index == 0�Ǽ�, �̹��� ���� ����.
			button_index--;
			flag = true; // ����
			System.out.println("���� �̸�: " + name.get(button_index)); // ���� �̸� ���
			System.out.println("���� ����: " + memo.get(button_index)); // ���� ���� ���
		}
		
		//���� ����
		if(e.getSource() == button3) {			
		    prepareGui(); // �����ϴ� ��ưâ ������ �Լ�
			btn.setEnabled(true); // ����, �ٽ� �����ư Ȱ��ȭ
		    // jpg, png�� ����Ʈ��
			FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "png", "jpg", "png");
		    // �� ���� ����
		    fc.setFileFilter(filter);
		    // �����߰� â �̸�
		    fc.setDialogTitle("�̹��� ���� ) ���� �̸�(���)�� �������� ������)");

		    // ���̾�α� ����
		    int Dialog = fc.showOpenDialog(this);
		    // �� Ȯ�ν�
		    if(Dialog == JFileChooser.APPROVE_OPTION) {
		      	flag2 = true;
		        // ���� ����
		      	File[] f = fc.getSelectedFiles();
		      	
		        for(File n : f) {
		        System.out.println(imageCnt++ + "��° �߰��� ����: " + n.getName());
		        imageName = n.getName();		         //�̹��� ���� ����ֱ�(img���, ������ img�̸�)
		        }
		        copyFile(fc.getSelectedFile(), imageName);
		        list.add(new String("image\\" + imageName));
		        button_index = MAX_SIZE;
		        MAX_SIZE++; // ���� �ִ� ����
	        }	  

		}
		
		// ���� ����
		if(e.getSource() == button4 && button_index > 0 && MAX_SIZE > 0 ) {	

			flag2 = true;
			File file = new File(list.get(button_index)); 
			if( file.exists() ) { 
				if(file.delete()) {  		// �̹��� ���� ����
					System.out.println(button_index + "������ ������ ���� �߽��ϴ�.");  
				}
			}

			System.out.println("������ ���� �̸�: " + name.get(button_index));
			System.out.println("������ ���� ����: " + memo.get(button_index));
			list.remove(button_index); // ���� �����ϴ� ��ư �ε����� ������� ����
			name.remove(button_index); // ���� �����ϴ� ��ư �ε����� �����̸� ����
			memo.remove(button_index); // ���� �����ϴ� ��ư �ε����� �������� ����
			button_index--; // �����ϰ� ���� ��������
			
			if(button_index == 0 && MAX_SIZE > 2) button_index = 1; // ������ �������ε�, ù ��° ������ ����� �̹��� ���������� �����°� ����
			MAX_SIZE--;	
			if(MAX_SIZE == 1) System.out.println("������ ���� �����ؼ� ������ �����ϴ�.");
		}
		
		
		Scanner sc = new Scanner(System.in);
		
		// ���� �˻� 
		if(e.getSource() == button5 && MAX_SIZE == 0) {
			System.out.println("ã�� ������ �����ϴ�.");
		}
		if(e.getSource() == button5 && MAX_SIZE > 0)  {
			System.out.println();
			System.out.println("ã�� ���� �̸��� �Է��ϼ���.");
			String s = sc.nextLine();
			for(int i=0; i<MAX_SIZE; i++) {
				if(s.equals(name.get(i))) {
					System.out.println("������ ã�ҽ��ϴ�.");
					button_index = i;
					flag = true;
					break;
				}
				if(flag == false && i == MAX_SIZE-1)  {
					System.out.println("ã�� ������ �����ϴ�.");
				}
			}
		}
		// �ൿ
		try {			
			if(flag == true) {
				img = ImageIO.read(new File(list.get(button_index).toString())); //�̹��� ������ ��������
			}
			if(flag2 == true) {
				img = ImageIO.read(new File(list.get(button_index).toString()));
			}
			
			label1.setFont(new Font("����", Font.BOLD, 22));
			label2.setFont(new Font("����", Font.BOLD, 22));
			label1.setForeground(Color.white);
			label2.setForeground(Color.white);
			label1.setText(name.get(button_index));
			label2.setText(memo.get(button_index));
		} catch (IOException e1) {
			e1.printStackTrace();
		}	finally {
			// �ǹ��ִ� �ൿ�� ��쿡�� ���������� ��Ÿ��.
			if(flag == true || flag2 == true) {
				System.out.println("���� ������: " + button_index);	
				System.out.println("----------------------------------------------");
			}
			//������ �ʱ�ȭ
			flag = false;
			flag2 = false;
			flag3 = false;
			imgPanel.repaint();
		}
	}
	
	
	
	// image������ ������ image������ ����� �̸� ������ image_set������ �ִ� �̹��� 2���� ����.
	public void mkDir() {
		String path = "image\\"; //���� ���
		File Folder = new File(path);

		// �ش� ���丮�� ������� ���丮�� �����մϴ�.
		if (!Folder.exists()) {
			try{
			    Folder.mkdir(); //���� �����մϴ�.
			    System.out.println("�̹����� ������ image������ �����Ǿ����ϴ�.");
				FileWriter fileWriter = new FileWriter("image\\test.txt",true); // ���� ������ ���ÿ�, �̹��� ��� ������ ���� ����, �̾ ����
				fileWriter.write("|"); // ���� ���   
				fileWriter.close();
		    } catch(Exception e) {
		    	e.getStackTrace();
			}        
	     }
		  
	}
		
	/* �ʱ� �ٹ� �⺻ ���� ����
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }		
	}*/

	// ���� ��ư�� �̿��� ���� ����
	public void copyFile(File Path, String imageName) {
        //����� ���ϰ��
        String copyFilePath = "image\\" + imageName;
        //�������ϰ�ü����
        File copyFile = new File(copyFilePath);
        System.out.println(copyFilePath + "�� ������ �����߽��ϴ�.");
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
            e.printStackTrace();
        } catch (IOException e) {
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
https://halfmoon9.tistory.com/12
*/