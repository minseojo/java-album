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
import javax.print.DocFlavor.URL;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.transform.Source;
/*
 * ���� �������� �Ȳ����� �����Ҷ�, ������ n*n+1/2���� ����	
*/

public class image_change extends JFrame implements ActionListener {
	static String buf = "";
	char[] arr = new char[100000]; //test.txt �ִ� 100000���� ������ �޾ƿ�		
	File file = new File("image\\test.txt");	
	private BufferedImage img; //ȭ�� �̹��� ���
	private JButton button1, button2, button3, button4, button5, button6, button7; //���� ��ư, ���� ��ư, ���� �߰�, ���� ����, ���� �˻�
	private JPanel imgPanel; // �̹��� ������ â
	private int button_index = 0; // �̹��� �ε���(1 ~ ���� �ִ� ����, 0�� �̹������� ����)
	private int MAX_SIZE = 0, imageCnt = 1; //�̹��� �ִ� ����, �̹��� �߰� ���� ()
	private boolean flag = false, flag2 = false, flag3 = false, flag5 = false, flag6 = false;// (����, ����) , (���� �߰�, ���� ����) , name2,memo2 ��������
	private String imageName; // �߰��� ���� �̸�
	private JButton btn = new JButton("����");
	private JButton btn2 = new JButton("ã��");
	Vector<String> list = new Vector<String>();  // ���� ��� �̸� ����
	Vector<String> name = new Vector<String>(); // ���� �̸� ����
	Vector<String> memo = new Vector<String>(); // ���� �޸� ����
	Vector<String> list2 = new Vector<String>();
	private JTextField tfd = new JTextField(null, 20); // ���� �̸� �Է� 
	private JTextField tfd2 = new JTextField(null, 20); // �޸� ���� �Է�
	private JTextField tfd3 = new JTextField(null, 20); // ���� �̸� �Է� 
	private JTextField tfd4 = new JTextField(null, 20); // �޸� ���� �Է�
	String name2="", memo2=""; // test.txt�� �����ȿ� ������ �ִ� �̹����� ���ؼ� ������ �ִ� �̹����� ���Ϳ� ���� name,memo����
	JLabel label1 = new JLabel(), label2 = new JLabel(), label3 = new JLabel(), label4 = new JLabel();  // �̹���â�� �����̸�, ���� ���� ������ ��, ��ư �ε�������
	JLabel label5 = new JLabel(), label6 = new JLabel(), label7 = new JLabel(), label8 = new JLabel();
	JFileChooser fc = new JFileChooser(); //���� ���� ����
	String s="" , s2="";
	int index=0;
	public image_change() {
		mkDir(); // image\\������ ������ image������ ����� image\\test.txt ������ �����		
		copyText(); // �̶����� ����� image��� ��������
		showFilesInDIr("image\\");	// "image\\"������ �ִ� ���ϵ� ���� �����ͼ� list2�� ��� ���� �ֱ�
		copyList(); // test.txt�� ������� ����� �̹��� ��ο� ���� ������ ������ �̹����� �ִ°�� ���Ϳ� ����
		make_mainFrame(); // ���� ������ �����

	}
	
	// image������ ������ image������ ����� �̸� ������ image_set������ �ִ� �̹��� 2���� ����.
	public void mkDir() {
		// �ָ��Ѱ� �־�� 
		fc.setMultiSelectionEnabled(true);
		list.add("���� ����� ����");    //test.txt�� ���� ��""�� �ְ� |�� �������� �ִµ� �̶� ������ �ǹ� ���°ŹǷ� �ǹ̾��� �ƹ��ų� �־��ְ� button_index�� 1���� �����Ѵ�
		name.add("ȯ���մϴ�.");
		memo.add("x");
		MAX_SIZE++;
		//
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
	
	public void copyText() {	  
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
		System.out.println("101buf=  "+buf);
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
	        }   
	    }
	}

	public void copyList() {
		System.out.println("���� ����� ���� �̸�");
		
		for(int i=0; i<list2.size(); i++) {
			if(list2.get(i).equals("image\\test.txt")) continue; // ���������� �Է��� �޸����� ��Ƽ��
			list.add(list2.get(i)); //
			MAX_SIZE++;
			System.out.println(list2.get(i)); //����� ������
		}
	
		for(int i=0; i<list.size(); i++) {
			String x =list.get(i);
			flag5=false;
			flag6=false;
			if(buf.contains(x)) {
				name2 = "";
				memo2 = "";
				int len = list.get(i).length(); 
				//len�� ����� ���ڼ�
				//buf.lastIndexOf(x)�� ��ΰ� �����ϴ� ��ġ 
				//���� ��ν�����ġ+��α��ڼ� �ڿ��� ���� �̸�, ���� ������ (; , :)�� ���еǾ� ��������
				for(int j= buf.lastIndexOf(x)+len; j< buf.lastIndexOf(x)+44; j++) {
					if(arr[j] == '|') break;
					if(arr[j] == ';') {
						flag5 = true;
						name2 = "";
						j++;
					}
					if(arr[j] == ':') {
						flag6=true;
						memo2 = "";
						j++;
					}
					if(flag5==true && flag6==false) {
						name2+=arr[j];
					}
					if(flag6 == true)
						memo2+=arr[j];
				}
				name.add(name2);
				memo.add(memo2);				
			}			
		}
	
		for(int i=1; i<list.size(); i++) {
			System.out.println(name.get(i));
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
				fileWriter.write("image\\" + imageName + ";" + name.get(button_index) + ":" + memo.get(button_index) + "|"); // |�� �����ڸ� ����				   
				fileWriter.close();
			  } catch (IOException e1) {
				  e1.printStackTrace();
			  }
		}
		
		if(e.getSource() == btn2) {
			int cnt=0;
			s = tfd3.getText();
			s2 = tfd4.getText();
			index = Integer.valueOf(s2); // ��ư������ �̵�
			tfd3.setText(""); // �Է� �� �������� �ʱ�ȭ
			tfd4.setText(""); // �Է��� ����� �ʱ�ȭ
			for(int i=0; i<list.size(); i++) {
				if(name.get(i).equals(s)) {
					System.out.println(s + "������ ã�ҽ��ϴ�.");
					button_index = i;
					flag = true;
					break;
				}
				else if(MAX_SIZE > index && index > 0) { //�ִ� �ּ� ��
					System.out.println("������ ã�ҽ��ϴ�.");
					button_index = index;
					flag = true;
					break;
				}
			}
			if(flag == false && cnt==0)  {
				cnt++;
				System.out.println("ã�� ������ �����ϴ�.");
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
		        for(int i=0; i<list.size(); i++) {
		        	if(list.get(i).equals("image\\" + imageName)) {
		        		System.out.println(i + "��° ������ ��Ĩ�ϴ�");
		        		button_index = MAX_SIZE;
		        		break;
		        	}
		        	if(i==list.size()-1) {
		        		make_subFrame(); // �����ϴ� ��ưâ ������ �Լ� 		   
		        		list.add(new String("image\\" + imageName));
				        button_index = MAX_SIZE;
				        MAX_SIZE++; // ���� �ִ� ����
				        break;
		        	}
		        }
		       
		      
		        
	        }	  
		}
		
		// ���� ����
		if(e.getSource() == button4 && button_index > 0 && MAX_SIZE > 1 ) {	
			flag2 = true;
			File file = new File(list.get(button_index)); 
			if( file.exists() ) { 
				if(file.delete()) {  		// �̹��� ���� ����
					System.out.println(button_index + "page ������ ���� �߽��ϴ�.");  
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
			if(MAX_SIZE == 1) {
				System.out.println("������ ���� �����ؼ� ������ �����ϴ�.");
			}
		}
		// ���� �˻� 
		if(e.getSource() == button5 && MAX_SIZE > 1)  {
			make_subFrame2(); //btn2�� �۵�
		}
		// �ൿ
		try {			
			img = ImageIO.read(new File(list.get(button_index).toString()));

			JLabel imgN = new JLabel("���� �̸�: ");
			JLabel imgM = new JLabel("���� ����: ");
			label1.setFont(new Font("�ü�ü", Font.BOLD, 33));
			//label2.setFont(new Font("�ü�ü", Font.BOLD, 20));
			label3.setFont(new Font("�ü�ü", Font.BOLD, 17));
			label4.setFont(new Font("�ü�ü", Font.BOLD, 17));
			label1.setForeground(Color.white);
			label2.setForeground(Color.white);
			label3.setForeground(Color.white);
			label4.setForeground(Color.white);
			label1.setText(name.get(button_index));
			label2.setText(memo.get(button_index));
			label3.setText("page. " + String.valueOf(button_index) + " / " + String.valueOf(MAX_SIZE-1));
			
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
	//���� ������ �����
	public void make_mainFrame() {
		imgPanel = new ChangeImagePanel();
		setPreferredSize(new Dimension(570,830));
		JPanel panel = new JPanel(); // ���� ����

		String index;
		index= String.valueOf(button_index);

		label1.setText(name.get(button_index));
		label2.setText(memo.get(button_index));
		label3.setText(index);
		label5.setText("GALLERY");
		
		setTitle("Gallery"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false); // â ���� x
	
		// ������Ʈ(��ư) �����
		button1 = new JButton("����");
		button2 = new JButton("����");
		button3 = new JButton("�߰�");
		button4 = new JButton("�����");
		button5 = new JButton("�˻�");
		button6 = new JButton("���� ���");
		button7 = new JButton("...");
		//������
		button1.setBorderPainted(false); 	button2.setBorderPainted(false); 	button3.setBorderPainted(false);
		button4.setBorderPainted(false); 	button5.setBorderPainted(false); 	button6.setBorderPainted(false);
		button7.setBorderPainted(false);
		// ��ư �׼� �۵�
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);		
		button7.addActionListener(this);
	
		panel.setLayout(null);	
		
		JLabel imgN = new JLabel(" ");
		JLabel imgM = new JLabel("���� ");
		
		label1.setText(name.get(button_index));
		label2.setText(memo.get(button_index));
		label3.setText("page. " + button_index);

		// �гο� ������Ʈ ���̱�
		panel.add(imgPanel);
		//panel.add(imgN);   //���� �̤Ѥ�
		panel.add(label1); // ���� �̸�:
		panel.add(label3); // ���� ������
		//panel.add(imgM);   // �޸�
		//panel.add(label2); //  ����:
		panel.add(label4); // ��ü ������
		panel.add(label5);
		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(button4);
		panel.add(button5);
		panel.add(button6);	
		panel.add(button7);

		setVisible(true);
		//����
		panel.setBackground(Color.DARK_GRAY);
		button1.setBackground(Color.GRAY);
		button2.setBackground(Color.GRAY);
		button3.setBackground(Color.black);
		button4.setBackground(Color.black);
		button5.setBackground(Color.black);
		button6.setBackground(Color.black);
		button7.setBackground(Color.black);
		//���ڻ�

		imgN.setForeground(Color.white);
		imgM.setForeground(Color.white);
		label1.setForeground(Color.white);
		label2.setForeground(Color.white);
		label3.setForeground(Color.white);
		label4.setForeground(Color.white);
		label5.setForeground(Color.LIGHT_GRAY);
		button1.setForeground(Color.white);
		button2.setForeground(Color.white);
		button3.setForeground(Color.white);
		button4.setForeground(Color.white);
		button5.setForeground(Color.white);
		button6.setForeground(Color.white);
		button7.setForeground(Color.blue);
		
		//��Ʈ
		imgN.setFont(new Font("�ü�ü", Font.BOLD, 25));
		imgM.setFont(new Font("�ü�ü", Font.BOLD, 25));
		button1.setFont(new Font("�ü�ü", Font.BOLD, 25));
		button2.setFont(new Font("�ü�ü", Font.BOLD, 25));
		button3.setFont(new Font("�ü�ü", Font.BOLD, 14));
		button4.setFont(new Font("�ü�ü", Font.BOLD, 14));
		button5.setFont(new Font("�ü�ü", Font.BOLD, 14));
		button6.setFont(new Font("�ü�ü", Font.BOLD, 14));
		button7.setFont(new Font("�ü�ü", Font.BOLD, 14));
		label1.setFont(new Font("�ü�ü", Font.ITALIC, 33));
		//label2.setFont(new Font("�ü�ü", Font.BOLD, 20));
		label3.setFont(new Font("�ü�ü", Font.BOLD, 17));
		label4.setFont(new Font("�ü�ü", Font.BOLD, 17));
		label5.setFont(new Font("�ü�ü", Font.ITALIC , 70));
		//��ġ
		label1.setBounds(160, 120, 300, 50); // ���� �̸�
		//label2.setBounds(200, 145, 300, 30); // ����
		label3.setBounds(420, 755, 150, 20); //���� ������
		//label4.setBounds(400, 750, 150, 20); // ��ü������
		label5.setBounds(10, 10, 300, 100); // gallery
		button1.setBounds(280, 690, 240, 50); // ����
		button2.setBounds(40, 690, 240, 50); // ����
		button3.setBounds(45, 750, 100, 35);	// �߰�
		button4.setBounds(275, 750, 100, 35); // ����
		button5.setBounds(160, 750, 100, 35); //�˻�
		button6.setBounds(460, 0, 100, 35); // ��κ����ư
		imgPanel.setBounds(40, 190, 480, 500);
		button7.setBounds(490, 165, 30, 25); // ������ư
		//button6.setBounds(350, 560, 130, 30);
		//add(imgPanel);
		//add(panel2);
		add(panel);
		//add(panel6);
		//add(panel4);
		
		pack();
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
	
	//���� ���� â
	public void make_subFrame() {
		JFrame subFrame = new JFrame("���� ���� (��ø� ��ٷ� �ּ���.)");
		subFrame.setSize(380,165);
		subFrame.getDefaultCloseOperation();
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
	//���� ���� â
	public void make_subFrame2() {
		JFrame subFrame2 = new JFrame("���� ã��");
		subFrame2.setSize(380,165);
		subFrame2.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {	
				subFrame2.setVisible(false);
				subFrame2.dispose();
			}
		});				
			
		Panel p1 = new Panel();
		p1.add(new Label("�̸����� ã��"));
		p1.add(tfd3);
		p1.setSize(200, 100);
			
		Panel p2 = new Panel();
		p2.add(new Label("�������� �̵� "));
		p2.add(tfd4);
		p1.setSize(200, 100);
		Panel p3 = new Panel();
		btn2.setBackground(Color.white);
		p3.add(new Label("������ ã�� X�� ��������."));
		p3.add(btn2);
		
			Panel p4 = new Panel();
			p4.add(p1);
			p4.add(p2);
			p4.add(p3);
				
			btn2.addActionListener(this);
			subFrame2.setResizable(false); // â ���� x
			subFrame2.add(p4);
			subFrame2.setVisible(true);
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

for(int i=0; i<1000; i++) { // 0�� |�� �ʱ�ȭ �����Ƿ� 1���� ����
			if(arr[i] == '|') { // ���� ������ ������
				s = ""; // �ʱ�ȭ
				s2 = "";
				s3 = ""; // �ʱ�ȭ
				flag5 = false;
				flag6 = false; // �ʱ�ȭ
				//MAX_SIZE++;
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
*/
