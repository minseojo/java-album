import java.awt.*;
import java.io.*;
import java.util.Vector;
import java.awt.event.*;
import java.awt.image.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * ���� �������� �Ȳ����� �����Ҷ�, ������ n*n+1/2���� ����	=> ���� ���μ��� ���� ã�� || ���α׷� �ִ� ���� 2��
 *	�߰�/	�׸���
*/

public class image_change extends JFrame implements ActionListener {
	public String buf = "";
	char[] arr = new char[100000]; //test.txt �ִ� 100000���� ������ �޾ƿ�		
	String[] copyList = new String[1000];
	String[] copyName = new String[1000];
	String[] copyMemo = new String[1000];
	File file = new File("image\\test.txt");	
	private JButton button1, button2, button3, button4, button5, button6, button7, button8, btn4, btn5, btn6; //���� ��ư, ���� ��ư, ���� �߰�, ���� ����, ���� �˻�, ����, ��κ���, �̵�, ���� ����(8910)
	private JButton BCbtn1,BCbtn2,BCbtn3,BCbtn4,BCbtn5,BCbtn6,BCbtn7,BCbtn8,BCbtn9,BCbtn10,BCbtn11,BCbtn12;
	private JPanel imgPanel; // �̹��� ������ â
	private int button_index = 0; // �̹��� �ε���(1 ~ ���� �ִ� ����, 0�� �̹������� ����)
	private int MAX_SIZE = 0, imageCnt = 0; //�̹��� �ִ� ����, �̹��� �߰� ���� ()
	private boolean flag = false, flag2 = false, flag3 = false, flag5 = false, flag6 = false;// (����, ����) , (���� �߰�, ���� ����) , name2,memo2 ��������
	private BufferedImage img; //ȭ�� �̹��� ���
	private String imageName; // �߰��� ���� �̸�
	private JButton btn = new JButton("����");
	private JButton btn2 = new JButton("ã��");
	private JButton btn3 = new JButton("�̵�");
	Vector<String> list = new Vector<String>();  // ���� ��� �̸� ����
	Vector<String> name = new Vector<String>(); // ���� �̸� ����
	Vector<String> memo = new Vector<String>(); // ���� �޸� ����
	Vector<String> list2 = new Vector<String>();
	private JTextField tfd = new JTextField("", 20); // ���� �̸� �Է� 
	private JTextField tfd2 = new JTextField("", 20); // �޸� ���� �Է�
	private JTextField tfd3 = new JTextField("", 20); // ���� �̸� �Է� 
	private JTextField tfd4 = new JTextField("", 20); // �޸� ���� �Է�
	String name2="", memo2=""; // test.txt�� �����ȿ� ������ �ִ� �̹����� ���ؼ� ������ �ִ� �̹����� ���Ϳ� ���� name,memo����
	JLabel label1 = new JLabel(), label2 = new JLabel(), label3 = new JLabel(), label4 = new JLabel(),label5 = new JLabel();  // �̹���â�� �����̸�, ���� ���� ������ ��, ��ư �ε�������
	JFileChooser fc = new JFileChooser(); //���� ���� ����
	JLabel[] labels = new JLabel[100]; // 
	JPanel panel = new JPanel(); // ���� ������ ,������ �ٲܶ� ������
	
	public image_change() {
		mkDir(); // image\\������ ������ image������ ����� image\\test.txt ������ �����		
		fc.setMultiSelectionEnabled(true);
		list.add("���� ����� ����");    //test.txt�� ���� ��""�� �ְ� |�� �������� �ִµ� �̶� ������ �ǹ� ���°ŹǷ� �ǹ̾��� �ƹ��ų� �־��ְ� button_index�� 1���� �����Ѵ�
		name.add("ȯ���մϴ�.");
		memo.add("x");
		MAX_SIZE++;
		copyText(); // �̶����� ����� image��� ��������
		showFilesInDIr("image\\");	// "image\\"������ �ִ� ���ϵ� ���� �����ͼ� list2�� ��� ���� �ֱ�
		copyList(); // test.txt�� ������� ����� �̹��� ��ο� ���� ������ ������ �̹����� �ִ°�� ���Ϳ� ����
		make_mainFrame(); // ���� ������ �����
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
	}	
		
	
	// image������ ������ image������ ����
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
				for(int j= buf.lastIndexOf(x)+len; j< buf.lastIndexOf(x)+1000; j++) {
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
	    	g.drawImage(img, 0, 0, 480, 500, null); //����, + x, + y, ����ũ��, ����ũ��, null
	    }
	}	
	
	// ���� (����, ����)
	public void actionPerformed(ActionEvent e) {
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
		    if(imageCnt > 1) {
		    	make_subFrame8();
		    }
		    else {
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
		        System.out.println("���α׷��� �����Ű�� " + imageCnt + "��° �߰��� ����: " + n.getName());
		        imageName = n.getName();		         //�̹��� ���� ����ֱ�(img���, ������ img�̸�)
		        }
		       
		        copyFile(fc.getSelectedFile(), imageName);
		        for(int i=0; i<list.size(); i++) {
		        	if(list.get(i).equals("image\\" + imageName)) {
		        		make_subFrame9(i);
		        		System.out.println(i + "��° ������ ��Ĩ�ϴ�");
		        		break;
		        	}
		        	if(i==list.size()-1) {
		        		imageCnt++; // �ִ� ���� ����2
		        		make_subFrame(); // �����ϴ� ��ưâ ������ �Լ� 		   
		        		list.add(new String("image\\" + imageName));
				        button_index = MAX_SIZE;
				        MAX_SIZE++; // ���� �ִ� ����
				        break;
		        		}
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
		//���� ���
		if(e.getSource() == button6)  {
			make_subFrame4(); //button�� �۵�
		}
		//���� ����
		if(e.getSource() == button7) {	
			make_subFrame5(); // btn(6,5,4)�� �۵�
		}	
		//������ �̵�
		if(e.getSource() == button8 && MAX_SIZE > 1)  {
			make_subFrame3(); //btn3�� �۵�
		}
		
		//�������� ���� ��ư
		if(e.getSource() == btn) {
			flag3 = true;
			name.add(tfd.getText());
			memo.add(tfd2.getText());
			System.out.println("������ ���� ���: " + "image\\" + imageName);
			System.out.println("������ ���� �̸�: " + name.get(MAX_SIZE - 1).toString());
			System.out.println("������ ���� ����: " + memo.get(MAX_SIZE - 1).toString());
			System.out.println("----------------------------------------------");
			btn.setEnabled(false); // ���� ��ư ��Ȱ��ȭ (����ڰ� �ѹ��� �����ϰ�)
		    //�޸��忡 ���� �̸� ����
	    	String filePath = "image\\test.txt"; //���� ���, ����

			try {
				FileWriter fileWriter = new FileWriter(filePath,true);
				if(!buf.contains(tfd.getText())) // test.txt�ߺ�üũ
				fileWriter.write("image\\" + imageName + ";" + name.get(button_index) + ":" + memo.get(button_index) + "|"); // |�� �����ڸ� ����				   
				fileWriter.close();
			  } catch (IOException e1) {
				  e1.printStackTrace();
			  }
			tfd.setText("");
			tfd2.setText("");
		}
		
		//���� �̸����� ã��
		if(e.getSource() == btn2) {
			int cnt=0;
			String s = tfd3.getText();
			tfd3.setText(""); // �Է� �� �������� �ʱ�ȭ
			for(int i=0; i<list.size(); i++) {
				if(name.get(i).equals(s)) {
					button_index = i;
					flag = true;
					System.out.println(s + "������ ã�ҽ��ϴ�.");
					break;
				}
			}
			if(flag == false && cnt==0)  {
				cnt++;
				System.out.println("ã�� ������ �����ϴ�.");
			}
		}
		
		// ������ �̵�
		if(e.getSource() == btn3) {
			int cnt=0;
			int s = Integer.parseInt(tfd4.getText());
			
			tfd4.setText(""); // �Է� �� �������� �ʱ�ȭ
			if(s < MAX_SIZE && s > 0) {
				button_index = s;
			}
			if(flag == false && cnt==0)  {
				cnt++;
				System.out.println("ã�� ������ �����ϴ�.");
			}
		}
		if(e.getSource() == btn4) {
			make_subFrame6();
		}
		if(e.getSource() == btn5) {
			make_subFrame7();
		}
		if(e.getSource() == btn6) {
			sort();
		}
		//�� ��� �� �ٱ� �� �� �� �� �� �� �� ��
		if(e.getSource() == BCbtn1) {
			panel.setBackground(Color.white);
		}if(e.getSource() == BCbtn2) {
			panel.setBackground(Color.LIGHT_GRAY);
		}if(e.getSource() == BCbtn3) {
			panel.setBackground(Color.gray);
		}if(e.getSource() == BCbtn4) {
			panel.setBackground(Color.DARK_GRAY);
		}if(e.getSource() == BCbtn5) {
			panel.setBackground(Color.black);
		}if(e.getSource() == BCbtn6) {
			panel.setBackground(Color.red);
		}if(e.getSource() == BCbtn7) {
			panel.setBackground(Color.orange);
		}if(e.getSource() == BCbtn8) {
			panel.setBackground(Color.yellow);
		}if(e.getSource() == BCbtn9) {
			panel.setBackground(Color.green);
		}if(e.getSource() == BCbtn10) {
			panel.setBackground(Color.blue);
		}if(e.getSource() == BCbtn11) {
			panel.setBackground(Color.pink);
		}if(e.getSource() == BCbtn12) {
			panel.setBackground(Color.CYAN);
		}
		// �ൿ
		try {			
			img = ImageIO.read(new File(list.get(button_index).toString()));

			JLabel imgN = new JLabel("���� �̸�: ");
			JLabel imgM = new JLabel("���� ����: ");
			label1.setFont(new Font("�������", Font.BOLD, 33));
			//label2.setFont(new Font("�������", Font.BOLD, 20));
			label3.setFont(new Font("�������", Font.BOLD, 17));
			label4.setFont(new Font("�������", Font.BOLD, 17));
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
        System.out.println(copyFilePath + "�� ������ ���� �ϴ��� �Դϴ�.");
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
		setPreferredSize(new Dimension(570,840));

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
		button8 = new JButton("�̵�");

		//������
		button1.setBorderPainted(false); 	button2.setBorderPainted(false); 	button3.setBorderPainted(false);
		button4.setBorderPainted(false); 	button5.setBorderPainted(false); 	button6.setBorderPainted(false);
		button7.setBorderPainted(false);	button8.setBorderPainted(false);
		// ��ư �׼� �۵�
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);		
		button7.addActionListener(this);
		button8.addActionListener(this);
	
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
		panel.add(button8);

		setVisible(true);
		//����
		panel.setBackground(Color.DARK_GRAY);
		button1.setBackground(Color.gray);
		button2.setBackground(Color.gray);
		button3.setBackground(Color.black);
		button4.setBackground(Color.black);
		button5.setBackground(Color.black);
		button6.setBackground(Color.black);
		button7.setBackground(Color.black);
		button8.setBackground(Color.black);
		
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
		button8.setForeground(Color.white);
		
		//��Ʈ
		imgN.setFont(new Font("�������", Font.BOLD, 25));
		imgM.setFont(new Font("�������", Font.BOLD, 25));
		button1.setFont(new Font("�������", Font.BOLD, 25));
		button2.setFont(new Font("�������", Font.BOLD, 25));
		button3.setFont(new Font("�������", Font.BOLD, 14));
		button4.setFont(new Font("�������", Font.BOLD, 14));
		button5.setFont(new Font("�������", Font.BOLD, 14));
		button6.setFont(new Font("�������", Font.BOLD, 14));
		button7.setFont(new Font("�������", Font.BOLD, 14));
		button8.setFont(new Font("�������", Font.BOLD, 14));
		label1.setFont(new Font("�������", Font.ITALIC, 33));
		//label2.setFont(new Font("�������", Font.BOLD, 20));
		label3.setFont(new Font("�������", Font.BOLD, 17));
		label4.setFont(new Font("�������", Font.BOLD, 17));
		label5.setFont(new Font("�������", Font.ITALIC , 55));
		
		//��ġ
		label1.setBounds(160, 120, 300, 50); // ���� �̸�
		//label2.setBounds(200, 145, 300, 30); // ����
		//label4.setBounds(400, 750, 150, 20); // ��ü������
		label5.setBounds(10, 5, 500, 100); // gallery
		button1.setBounds(280, 690, 240, 50); // ����
		button2.setBounds(40, 690, 240, 50); // ����
		button3.setBounds(45, 750, 80, 35);	// �߰�
		button5.setBounds(140, 750, 80, 35); //�˻�
		button4.setBounds(330, 750, 100, 35); // ����
		button8.setBounds(235, 750, 80, 35); // �̵�
		button7.setBounds(490, 165, 30, 25); // ������ư
		button6.setBounds(460, 0, 100, 35); // ��Ϻ��� ��ư
		label3.setBounds(440, 762, 150, 20); //���� ������
		imgPanel.setBounds(40, 190, 480, 500);
		//button6.setBounds(350, 560, 130, 30);
		//add(imgPanel);
		//add(panel2);
		add(panel);
		//add(panel6);
		//add(panel4);
		
		pack();
	}
	
	//���� ���� â
	public void make_subFrame() {
		JFrame subFrame = new JFrame("���� ���� (��ø� ��ٷ� �ּ���.)");
		subFrame.setSize(380,165);

		subFrame.setVisible(true);

		subFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				subFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		});				
			
		Panel p1 = new Panel();
		p1.add(new Label("���� �̸�: "));
		p1.setFont(new Font("�������",Font.BOLD,12));
		p1.setForeground(Color.white);

		p1.add(tfd);
		p1.setSize(200, 100);
		
		btn.setBackground(Color.black);
		btn.setFont(new Font("�������",Font.BOLD,12));
		btn.setForeground(Color.white);
		btn.setBorderPainted(false);	
		
		Panel p2 = new Panel();
		p2.add(new Label("���� ����: "));
		p2.add(tfd2);

		p2.setFont(new Font("�������",Font.BOLD,12));
		p2.setForeground(Color.white);

		Panel p3 = new Panel();
		p3.add(new Label("������ �����ϰ� X�� ��������."));
		p3.add(btn);

		p3.setFont(new Font("�������",Font.BOLD,12));
		p3.setForeground(Color.white);
		
		Panel p4 = new Panel();
		p4.add(p1);
		p4.add(p2);
		p4.add(p3);
		p4.setBackground(Color.DARK_GRAY);	
		btn.addActionListener(this);
		subFrame.setResizable(false); // â ���� x
		subFrame.add(p4);
	}
	//���� �˻� â
	public void make_subFrame2() {
		JFrame subFrame2 = new JFrame("���� ã��");
		subFrame2.setSize(380,120);
		subFrame2.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {	
				subFrame2.setVisible(false);
				subFrame2.dispose();
			}
		});				
			
		Panel p1 = new Panel();
		p1.add(new Label("���� �̸����� ã��"));
		p1.add(tfd3);
		p1.setFont(new Font("�������",Font.BOLD,12));
		p1.setForeground(Color.white);
		
		Panel p2 = new Panel();
		p2.add(new Label("������ ã�� X�� ��������."));
		p2.setFont(new Font("�������",Font.BOLD,12));
		p2.setForeground(Color.white);
		
		btn2.setBackground(Color.black);
		btn2.setFont(new Font("�������",Font.BOLD,12));
		btn2.setForeground(Color.white);
		btn2.setBorderPainted(false);	
		p2.add(btn2);
		
		Panel p3 = new Panel();
		p3.add(p1);
		p3.add(p2);
		p3.setBackground(Color.DARK_GRAY);		
		btn2.addActionListener(this);
		subFrame2.setResizable(false); // â ���� x
		subFrame2.add(p3);
		subFrame2.setVisible(true);
	}		
	
	//������ �̵�â
	public void make_subFrame3() {
		JFrame subFrame3 = new JFrame("������ �̵�");
		subFrame3.setSize(380,120);
		subFrame3.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {	
				subFrame3.setVisible(false);
				subFrame3.dispose();
			}
		});						
		Panel p2 = new Panel();
		p2.add(new Label("�̵���  ������ "));
		p2.add(tfd4);

		p2.setFont(new Font("�������", Font.BOLD, 12));		
		p2.setForeground(Color.white);

		Panel p3 = new Panel();
		btn3.setBackground(Color.black);
		btn3.setFont(new Font("�������",Font.BOLD,12));
		btn3.setForeground(Color.white);
		btn3.setBorderPainted(false);	
		
		p3.add(new Label("�̵��ϰ� X�� ��������."));
		p3.add(btn3);
		p3.setFont(new Font("�������", Font.BOLD, 12));		
		p3.setForeground(Color.white);

		Panel p4 = new Panel();
		p4.add(p2);
		p4.add(p3);
		p4.setBackground(Color.DARK_GRAY);
		
		btn3.addActionListener(this);
		subFrame3.setResizable(false); // â ���� x
		subFrame3.add(p4);
		subFrame3.setVisible(true);
	}			
	
	//���� ��� 
	public void make_subFrame4() {
		int max=0;
		int max2=0;
		for(int i=1; i<list.size(); i++) {
			if(name.get(i).length() + memo.get(i).length() > max) {
				max = name.get(i).length() + memo.get(i).length();
			}
			if(name.get(i).length() > max2) {
				max2 = name.get(i).length();
			}
		}
		JFrame subFrame4 = new JFrame("���� ���");
		if(max==0) {
			subFrame4.setSize(400, 100);
		}
		else {
			subFrame4.setSize(max*90, MAX_SIZE*60);
		}
		subFrame4.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent windowEvent) {	
				subFrame4.setVisible(false);
				subFrame4.dispose();
			}
		});				
		
		Panel p1 = new Panel();
		p1.setLayout(null);
		labels[0] = new JLabel("���� �̸�");
		labels[0].setBounds(40, 0, 200, 50); // ����
		labels[0].setFont(new Font("����",Font.BOLD, 25));
		labels[1] = new JLabel("���� ����");
		labels[1].setBounds(220, 0, 340 + max2, 50); // ����
		labels[1].setFont(new Font("����",Font.BOLD, 25));
		String s1 = " ";
		for(int i=2; i<MAX_SIZE+1; i++) {
			int x= max2 - name.get(i-1).length()  + 20;
			String s2 = s1.repeat(x);
			labels[i] = new JLabel(i-1 + "    " + name.get(i-1) + s2 + memo.get(i-1));
			labels[i].setFont(new Font("����",Font.BOLD, 23));
			labels[i].setBounds(0, 30*i, 300*MAX_SIZE, 30); // ����
		}
		for(int i=0; i<MAX_SIZE+1; i++) {
			labels[i].setForeground(Color.white);
			p1.add(labels[i]);
		}
		p1.setBackground(Color.black);
		p1.setFont(new Font("�������",Font.BOLD,12));

		
		//btn4.addActionListener(this);
		subFrame4.setResizable(false); // â ���� x
		subFrame4.add(p1);
		subFrame4.setVisible(true);
	}
	
	//�۾� â
	public void make_subFrame5() {
		JFrame subFrame5 = new JFrame("�۾�");
		subFrame5.setSize(300, 400);
		subFrame5.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent windowEvent) {	
				subFrame5.setVisible(false);
				subFrame5.dispose();
			}
		});				
		
		Panel p1 = new Panel();
		p1.setLayout(new GridLayout(3,1));
		
		btn4 = new JButton("�ٹ� ���� �ٲٱ�");
		btn5 = new JButton("���� ����");
		btn6 = new JButton("���� ���� �ݴ�� �ٲٱ�");
		
		btn4.setBorderPainted(false);	
		btn5.setBorderPainted(false);
		btn6.setBorderPainted(false);
		
		btn4.addActionListener(this);				
		btn5.addActionListener(this);
		btn6.addActionListener(this);
		
		btn4.setBackground(Color.black);
		btn5.setBackground(Color.black);
		btn6.setBackground(Color.black);
		
		btn4.setFont(new Font("�������", Font.BOLD, 14));
		btn5.setFont(new Font("�������", Font.BOLD, 14));
		btn6.setFont(new Font("�������", Font.BOLD, 14));
		
		btn4.setForeground(Color.white);
		btn5.setForeground(Color.white);
		btn6.setForeground(Color.white);
		
		p1.add(btn5);
		p1.add(btn6);
		p1.add(btn4);
		
		subFrame5.setResizable(false); // â ���� x
		subFrame5.add(p1);
		subFrame5.setVisible(true);
	}
	
	//�ٹ� �� ����â
	public void make_subFrame6() {
		JFrame subFrame6 = new JFrame("�ٹ� ���� ����");
		subFrame6.setSize(600, 300);
		subFrame6.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent windowEvent) {	
				subFrame6.setVisible(false);
				subFrame6.dispose();
			}
		});				
		
		Panel p1 = new Panel();
		p1.setLayout(new GridLayout(3,4));
		
		BCbtn1 = new JButton("WHITE");
		BCbtn2 = new JButton("LIGHT_GRAY");
		BCbtn3 = new JButton("GRAY");
		BCbtn4 = new JButton("DARK_GRAY");
		BCbtn5 = new JButton("BLACK");
		BCbtn6 = new JButton("RED");
		BCbtn7 = new JButton("ORANGE");
		BCbtn8 = new JButton("YELLOW");
		BCbtn9 = new JButton("GREEN");
		BCbtn10 = new JButton("BLUE");
		BCbtn11 = new JButton("PINK");
		BCbtn12 = new JButton("CYAN");
		
		BCbtn1.setBackground(Color.black);
		BCbtn2.setBackground(Color.black);
		BCbtn3.setBackground(Color.black);
		BCbtn4.setBackground(Color.black);
		BCbtn5.setBackground(Color.black);
		BCbtn6.setBackground(Color.black);
		BCbtn7.setBackground(Color.black);
		BCbtn8.setBackground(Color.black);
		BCbtn9.setBackground(Color.black);
		BCbtn10.setBackground(Color.black);
		BCbtn11.setBackground(Color.black);
		BCbtn12.setBackground(Color.black);
		
		BCbtn1.setBorderPainted(false);	
		BCbtn2.setBorderPainted(false);
		BCbtn3.setBorderPainted(false);
		BCbtn4.setBorderPainted(false);	
		BCbtn5.setBorderPainted(false);
		BCbtn6.setBorderPainted(false);
		BCbtn7.setBorderPainted(false);	
		BCbtn8.setBorderPainted(false);
		BCbtn9.setBorderPainted(false);
		BCbtn10.setBorderPainted(false);	
		BCbtn11.setBorderPainted(false);
		BCbtn12.setBorderPainted(false);

		BCbtn1.addActionListener(this);				
		BCbtn2.addActionListener(this);
		BCbtn3.addActionListener(this);
		BCbtn4.addActionListener(this);				
		BCbtn5.addActionListener(this);
		BCbtn6.addActionListener(this);
		BCbtn7.addActionListener(this);				
		BCbtn8.addActionListener(this);
		BCbtn9.addActionListener(this);
		BCbtn10.addActionListener(this);				
		BCbtn11.addActionListener(this);
		BCbtn12.addActionListener(this);
		
		BCbtn1.setFont(new Font("�������", Font.BOLD, 14));
		BCbtn2.setFont(new Font("�������", Font.BOLD, 14));
		BCbtn3.setFont(new Font("�������", Font.BOLD, 14));
		BCbtn4.setFont(new Font("�������", Font.BOLD, 14));
		BCbtn5.setFont(new Font("�������", Font.BOLD, 14));
		BCbtn6.setFont(new Font("�������", Font.BOLD, 14));
		BCbtn7.setFont(new Font("�������", Font.BOLD, 14));
		BCbtn8.setFont(new Font("�������", Font.BOLD, 14));
		BCbtn9.setFont(new Font("�������", Font.BOLD, 14));
		BCbtn10.setFont(new Font("�������", Font.BOLD, 14));
		BCbtn11.setFont(new Font("�������", Font.BOLD, 14));
		BCbtn12.setFont(new Font("�������", Font.BOLD, 14));
		
		BCbtn1.setForeground(Color.white);
		BCbtn2.setForeground(Color.white);
		BCbtn3.setForeground(Color.white);
		BCbtn4.setForeground(Color.white);
		BCbtn5.setForeground(Color.white);
		BCbtn6.setForeground(Color.white);
		BCbtn7.setForeground(Color.white);
		BCbtn8.setForeground(Color.white);
		BCbtn9.setForeground(Color.white);
		BCbtn10.setForeground(Color.white);
		BCbtn11.setForeground(Color.white);
		BCbtn12.setForeground(Color.white);

		p1.add(BCbtn1);
		p1.add(BCbtn2);
		p1.add(BCbtn3);
		p1.add(BCbtn4);
		p1.add(BCbtn5);
		p1.add(BCbtn6);
		p1.add(BCbtn7);
		p1.add(BCbtn8);
		p1.add(BCbtn9);
		p1.add(BCbtn10);
		p1.add(BCbtn11);
		p1.add(BCbtn12);
		
		subFrame6.setResizable(false); // â ���� x
		subFrame6.add(p1);
		subFrame6.setVisible(true);
	}
	
	//���� ���� ����
	public void make_subFrame7() {
		JFrame subFrame7 = new JFrame("���� ����");
		subFrame7.setSize(600, 400);
		subFrame7.setBounds(300, 100, 450, 200);
		subFrame7.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent windowEvent) {	
				subFrame7.setVisible(false);
				subFrame7.dispose();
			}
		});				
		
		Panel p1 = new Panel();
		p1.setLayout(new FlowLayout());
		
		JLabel label1 = new JLabel(memo.get(button_index));
		label1.setFont(new Font("�������",Font.BOLD,22));		
		label1.setForeground(Color.white);
		p1.setBackground(Color.black);
		
		p1.add(label1);

		subFrame7.setResizable(false); // â ���� x
		subFrame7.add(p1);
		subFrame7.setVisible(true);
	}
	
	//���� 2�� �ʰ��� ����溸â
	public void make_subFrame8() {
		JFrame subFrame8 = new JFrame("�ߵ� ����!");
		subFrame8.setSize(800,200);
		subFrame8.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {	
				subFrame8.setVisible(false);
				subFrame8.dispose();
			}
		});			
		
		Panel p1 = new Panel();
		p1.setLayout(new GridLayout(2,1));
		p1.add(new Label("����) �� ���α׷��� �����ϰ� 2���� �߰� �����մϴ�. (�ߵ� ��������)"));
		p1.add(new Label("          ���ϸ� ���α׷��� �����ϰ� �ٽ� �����ؼ� �߰� ���ּ���."));

		p1.setFont(new Font("�������", Font.BOLD, 22));		
		p1.setForeground(Color.white);
		p1.setBackground(Color.black);

		subFrame8.setResizable(false); // â ���� x
		subFrame8.add(p1);
		subFrame8.setVisible(true);
	}		
	
	//���� �߰��Ҷ� ��ġ�� ���� �ΰ��
		public void make_subFrame9(int index) {
			JFrame subFrame9 = new JFrame("���� ��ħ ����!");
			subFrame9.setSize(900,100);
			subFrame9.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent windowEvent) {	
					subFrame9.setVisible(false);
					subFrame9.dispose();
				}
			});			
			
			Panel p1 = new Panel();
			p1.setLayout(new GridLayout(2,1));
			p1.add(new Label("�߰��Ϸ��� ������" + index + "��° ������ ��Ĩ�ϴ�." + index + "��° ������ ����� �ٽ� �߰��ϼ���."));

			p1.setFont(new Font("�������", Font.BOLD, 22));		
			p1.setForeground(Color.white);
			p1.setBackground(Color.black);

			subFrame9.setResizable(false); // â ���� x
			subFrame9.add(p1);
			subFrame9.setVisible(true);
		}		
	
	public void sort() {
	//���� �̸��� ��������
		//Collections.sort(list,Collections.reverseOrder()); 
		// �����̸� ��������
		//Collections.reverse(list);
		for(int i=1; i<list.size(); i++) { //���� ����
			copyList[i] = list.get(i);
			copyName[i] = name.get(i);
			copyMemo[i] = memo.get(i);
		}
		int size = list.size(); //���� ���� ������
		list.clear();
		name.clear();
		memo.clear();
		list.add("���� ����� ����");    // ��� ���� Ŭ�����ϰ� ó�� �̹��� ������ �ʱ� �����ʱ�ȭ
		name.add("ȯ���մϴ�.");
		memo.add("x");
		for(int i=1; i<size; i++) {
			list.add(copyList[size-i]);
			name.add(copyName[size-i]);
			memo.add(copyMemo[size-i]);
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