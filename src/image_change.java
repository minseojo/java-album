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
 * 서브 프레임이 안꺼져서 저장할때, 내용이 n*n+1/2개씩 나옴	
*/

public class image_change extends JFrame implements ActionListener {
	static String buf = "";
	char[] arr = new char[100000]; //test.txt 최대 100000글자 까지를 받아옴		
	File file = new File("image\\test.txt");	
	private BufferedImage img; //화면 이미지 출력
	private JButton button1, button2, button3, button4, button5, button6, button7; //다음 버튼, 이전 버튼, 사진 추가, 사진 삭제, 사진 검색
	private JPanel imgPanel; // 이미지 나오는 창
	private int button_index = 0; // 이미지 인덱스(1 ~ 사진 최대 개수, 0은 이미지없음 사진)
	private int MAX_SIZE = 0, imageCnt = 1; //이미지 최대 개수, 이미지 추가 개수 ()
	private boolean flag = false, flag2 = false, flag3 = false, flag5 = false, flag6 = false;// (다음, 이전) , (사진 추가, 사진 삭제) , name2,memo2 변수적용
	private String imageName; // 추가된 사진 이름
	private JButton btn = new JButton("저장");
	private JButton btn2 = new JButton("찾기");
	Vector<String> list = new Vector<String>();  // 사진 경로 이름 저장
	Vector<String> name = new Vector<String>(); // 사진 이름 저장
	Vector<String> memo = new Vector<String>(); // 사진 메모 내용
	Vector<String> list2 = new Vector<String>();
	private JTextField tfd = new JTextField(null, 20); // 사진 이름 입력 
	private JTextField tfd2 = new JTextField(null, 20); // 메모 내용 입력
	private JTextField tfd3 = new JTextField(null, 20); // 사진 이름 입력 
	private JTextField tfd4 = new JTextField(null, 20); // 메모 내용 입력
	String name2="", memo2=""; // test.txt와 폴더안에 실제로 있는 이미지랑 비교해서 실제로 있는 이미지만 벡터에 넣을 name,memo변수
	JLabel label1 = new JLabel(), label2 = new JLabel(), label3 = new JLabel(), label4 = new JLabel();  // 이미지창에 사진이름, 사진 내용 나오는 라벨, 버튼 인덱스나옴
	JLabel label5 = new JLabel(), label6 = new JLabel(), label7 = new JLabel(), label8 = new JLabel();
	JFileChooser fc = new JFileChooser(); //폴더 선택 파일
	String s="" , s2="";
	int index=0;
	public image_change() {
		mkDir(); // image\\폴더가 없으면 image폴더를 만들고 image\\test.txt 파일을 만든다		
		copyText(); // 이때동안 저장된 image경로 가져오기
		showFilesInDIr("image\\");	// "image\\"폴더에 있는 파일들 전부 가져와서 list2에 경로 집어 넣기
		copyList(); // test.txt에 현재까지 저장된 이미지 경로와 현재 폴더에 실제로 이미지가 있는경우 벡터에 삽입
		make_mainFrame(); // 메인 프레임 만들기

	}
	
	// image폴더가 없으면 image폴더를 만들고 미리 만들어둔 image_set폴더에 있는 이미지 2개를 복사.
	public void mkDir() {
		// 애매한거 넣어둠 
		fc.setMultiSelectionEnabled(true);
		list.add("현재 저장된 사진");    //test.txt를 만들 때""를 넣고 |로 구분지어 주는데 이때 사진은 의미 없는거므로 의미없는 아무거나 넣어주고 button_index를 1부터 시작한다
		name.add("환영합니다.");
		memo.add("x");
		MAX_SIZE++;
		//
		String path = "image\\"; //폴더 경로
		File Folder = new File(path);
		
		// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
		if (!Folder.exists()) {
			try{
			    Folder.mkdir(); //폴더 생성합니다.
			    System.out.println("이미지를 저장할 image폴더가 생성되었습니다.");
				FileWriter fileWriter = new FileWriter("image\\test.txt",true); // 폴더 생성과 동시에, 이미지 경로 저장할 파일 생성, 이어서 쓰기
				fileWriter.write("|"); // 사진 경로   
				fileWriter.close();
		    } catch(Exception e) {
		    	e.getStackTrace();
			}        
	     }
		  
	}
	
	public void copyText() {	  
		//buf로 파일 전부 읽기
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
	    	int i=0;
	    	while ((line = br.readLine()) != null) {
	    		if(line.equals("|")) i++; // 구분자|로 구분
	    		buf += line;
	    	}
		} catch (IOException e) {
			e.printStackTrace();
		}   
	
		// arr[i]로 buf가져오기하기
		for(int i=0; i<buf.length(); i++) { //메모장을 처음에 만들고 0번째 인덱스에 |를 초기화시켰으므로 1부터
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
		System.out.println("현재 저장된 사진 이름");
		
		for(int i=0; i<list2.size(); i++) {
			if(list2.get(i).equals("image\\test.txt")) continue; // 사진정보를 입력한 메모장은 컨티뉴
			list.add(list2.get(i)); //
			MAX_SIZE++;
			System.out.println(list2.get(i)); //저장된 사진들
		}
	
		for(int i=0; i<list.size(); i++) {
			String x =list.get(i);
			flag5=false;
			flag6=false;
			if(buf.contains(x)) {
				name2 = "";
				memo2 = "";
				int len = list.get(i).length(); 
				//len은 경로의 글자수
				//buf.lastIndexOf(x)는 경로가 시작하는 위치 
				//따라서 경로시작위치+경로글자수 뒤에는 사진 이름, 사진 내용이 (; , :)로 구분되어 적혀있음
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
	    	g.drawImage(img, 0, 0, 480, 500, null); //사진, 가로위치, 세로위치, 가로크기, 세로크기, null
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
	
	// 사진 (다음, 이전)
	public void actionPerformed(ActionEvent e) {
		//사진삽입 저장 버튼
		if(e.getSource() == btn) {
			flag3 = true;
			name.add(tfd.getText());
			memo.add(tfd2.getText());
			tfd.setText(""); // 입력 후 공백으로 초기화
			tfd2.setText(""); // 입력후 공백로 초기화
			System.out.println("저장한 사진 경로: " + "image\\" + imageName);
			System.out.println("저장한 사진 이름: " + name.get(MAX_SIZE - 1).toString());
			System.out.println("저장한 사진 내용: " + memo.get(MAX_SIZE - 1).toString());
			System.out.println("----------------------------------------------");
			btn.setEnabled(false); // 저장 버튼 비활성화 (사용자가 한번만 저장하게)
		    //메모장에 사진 이름 저장
	    	String filePath = "image\\test.txt"; //사진 경로, 내용
			try {
				FileWriter fileWriter = new FileWriter(filePath,true);
				fileWriter.write("image\\" + imageName + ";" + name.get(button_index) + ":" + memo.get(button_index) + "|"); // |로 구분자를 정함				   
				fileWriter.close();
			  } catch (IOException e1) {
				  e1.printStackTrace();
			  }
		}
		
		if(e.getSource() == btn2) {
			int cnt=0;
			s = tfd3.getText();
			s2 = tfd4.getText();
			index = Integer.valueOf(s2); // 버튼순서로 이동
			tfd3.setText(""); // 입력 후 공백으로 초기화
			tfd4.setText(""); // 입력후 공백로 초기화
			for(int i=0; i<list.size(); i++) {
				if(name.get(i).equals(s)) {
					System.out.println(s + "사진을 찾았습니다.");
					button_index = i;
					flag = true;
					break;
				}
				else if(MAX_SIZE > index && index > 0) { //최대 최소 값
					System.out.println("사진을 찾았습니다.");
					button_index = index;
					flag = true;
					break;
				}
			}
			if(flag == false && cnt==0)  {
				cnt++;
				System.out.println("찾는 사진이 없습니다.");
			}
		}
		
		//다음
		if(e.getSource() == button1 && button_index < MAX_SIZE-1 && button_index > -1) { // 사진 개수가 MAX면 button_index가 안늘어남.
			button_index++;
			flag = true; // 다음 	
			System.out.println("사진 이름: " + name.get(button_index)); // 사진 이름 출력
			System.out.println("사진 내용: " + memo.get(button_index)); // 사진 내용 출력
		} 
		
		// 이전
		else if(e.getSource() == button2 && button_index > 1 && MAX_SIZE > 1) { // 사진 개수가 1보다 작으면 button_index가 안내려감. 사진 다 지우면 button_index == 0되서, 이미지 없음 나옴.
			button_index--;
			flag = true; // 이전
			System.out.println("사진 이름: " + name.get(button_index)); // 사진 이름 출력
			System.out.println("사진 내용: " + memo.get(button_index)); // 사진 내용 출력
		}
		
		//사진 삽입
		if(e.getSource() == button3) {			
		    
			btn.setEnabled(true); // 들어갈때, 다시 저장버튼 활성화
		    // jpg, png가 디폴트값
			FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "png", "jpg", "png");
		    // 위 내용 적용
		    fc.setFileFilter(filter);
		    // 사진추가 창 이름
		    fc.setDialogTitle("이미지 선택 ) 파일 이름(경로)을 변경하지 마세요)");

		    // 다이얼로그 생성
		    int Dialog = fc.showOpenDialog(this);
		    // 예 확인시
		    if(Dialog == JFileChooser.APPROVE_OPTION) {    	
		    	flag2 = true;
		        // 파일 선택
		      	File[] f = fc.getSelectedFiles();		      	
		        for(File n : f) {
		        System.out.println(imageCnt++ + "번째 추가한 사진: " + n.getName());
		        imageName = n.getName();		         //이미지 파일 집어넣기(img경로, 복사할 img이름)
		        }
		       
		        copyFile(fc.getSelectedFile(), imageName);
		        for(int i=0; i<list.size(); i++) {
		        	if(list.get(i).equals("image\\" + imageName)) {
		        		System.out.println(i + "번째 사진과 겹칩니다");
		        		button_index = MAX_SIZE;
		        		break;
		        	}
		        	if(i==list.size()-1) {
		        		make_subFrame(); // 저장하는 버튼창 나오는 함수 		   
		        		list.add(new String("image\\" + imageName));
				        button_index = MAX_SIZE;
				        MAX_SIZE++; // 사진 최대 개수
				        break;
		        	}
		        }
		       
		      
		        
	        }	  
		}
		
		// 사진 삭제
		if(e.getSource() == button4 && button_index > 0 && MAX_SIZE > 1 ) {	
			flag2 = true;
			File file = new File(list.get(button_index)); 
			if( file.exists() ) { 
				if(file.delete()) {  		// 이미지 파일 삭제
					System.out.println(button_index + "page 사진을 삭제 했습니다.");  
				}
			}

			System.out.println("삭제한 사진 이름: " + name.get(button_index));
			System.out.println("삭제한 사진 내용: " + memo.get(button_index));
			list.remove(button_index); // 순간 삭제하는 버튼 인덱스의 사진경로 삭제
			name.remove(button_index); // 순간 삭제하는 버튼 인덱스의 사진이름 삭제
			memo.remove(button_index); // 순간 삭제하는 버튼 인덱스의 사진내용 삭제
			button_index--; // 삭제하고 이전 사진보기
			
			if(button_index == 0 && MAX_SIZE > 2) button_index = 1; // 사진은 여러장인데, 첫 번째 사진을 지우면 이미지 없음사진이 나오는거 방지
			MAX_SIZE--;	
			if(MAX_SIZE == 1) {
				System.out.println("사진을 전부 삭제해서 사진이 없습니다.");
			}
		}
		// 사진 검색 
		if(e.getSource() == button5 && MAX_SIZE > 1)  {
			make_subFrame2(); //btn2로 작동
		}
		// 행동
		try {			
			img = ImageIO.read(new File(list.get(button_index).toString()));

			JLabel imgN = new JLabel("사진 이름: ");
			JLabel imgM = new JLabel("사진 내용: ");
			label1.setFont(new Font("궁서체", Font.BOLD, 33));
			//label2.setFont(new Font("궁서체", Font.BOLD, 20));
			label3.setFont(new Font("궁서체", Font.BOLD, 17));
			label4.setFont(new Font("궁서체", Font.BOLD, 17));
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
			// 의미있는 행동인 경우에만 페이지수를 나타냄.
			if(flag == true || flag2 == true) {
				System.out.println("현재 페이지: " + button_index);	
				System.out.println("----------------------------------------------");
			}
			//마지막 초기화
			flag = false;
			flag2 = false;
			flag3 = false;
			imgPanel.repaint();
		}
	}
	
	// 삽입 버튼을 이용한 파일 복사
	public void copyFile(File Path, String imageName) {
        //복사될 파일경로
        String copyFilePath = "image\\" + imageName;
        //복사파일객체생성
        File copyFile = new File(copyFilePath);
        System.out.println(copyFilePath + "에 사진을 복사했습니다.");
        try {            
            FileInputStream fis = new FileInputStream(Path); //읽을파일
            FileOutputStream fos = new FileOutputStream(copyFile); //복사할파일
            
            int fileByte = 0; 
            // fis.read()가 -1 이면 파일을 다 읽은것
            while((fileByte = fis.read()) != -1) {
                fos.write(fileByte);
            }
            //자원사용종료
            fis.close();
            fos.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	//메인 프레임 만들기
	public void make_mainFrame() {
		imgPanel = new ChangeImagePanel();
		setPreferredSize(new Dimension(570,830));
		JPanel panel = new JPanel(); // 이전 다음

		String index;
		index= String.valueOf(button_index);

		label1.setText(name.get(button_index));
		label2.setText(memo.get(button_index));
		label3.setText(index);
		label5.setText("GALLERY");
		
		setTitle("Gallery"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false); // 창 조정 x
	
		// 컴포넌트(버튼) 만들기
		button1 = new JButton("다음");
		button2 = new JButton("이전");
		button3 = new JButton("추가");
		button4 = new JButton("지우기");
		button5 = new JButton("검색");
		button6 = new JButton("사진 목록");
		button7 = new JButton("...");
		//윤곽선
		button1.setBorderPainted(false); 	button2.setBorderPainted(false); 	button3.setBorderPainted(false);
		button4.setBorderPainted(false); 	button5.setBorderPainted(false); 	button6.setBorderPainted(false);
		button7.setBorderPainted(false);
		// 버튼 액션 작동
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);		
		button7.addActionListener(this);
	
		panel.setLayout(null);	
		
		JLabel imgN = new JLabel(" ");
		JLabel imgM = new JLabel("내용 ");
		
		label1.setText(name.get(button_index));
		label2.setText(memo.get(button_index));
		label3.setText("page. " + button_index);

		// 패널에 컴포넌트 붙이기
		panel.add(imgPanel);
		//panel.add(imgN);   //사진 이ㅡㄻ
		panel.add(label1); // 사진 이름:
		panel.add(label3); // 현재 페이지
		//panel.add(imgM);   // 메모
		//panel.add(label2); //  내용:
		panel.add(label4); // 전체 페이지
		panel.add(label5);
		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(button4);
		panel.add(button5);
		panel.add(button6);	
		panel.add(button7);

		setVisible(true);
		//배경색
		panel.setBackground(Color.DARK_GRAY);
		button1.setBackground(Color.GRAY);
		button2.setBackground(Color.GRAY);
		button3.setBackground(Color.black);
		button4.setBackground(Color.black);
		button5.setBackground(Color.black);
		button6.setBackground(Color.black);
		button7.setBackground(Color.black);
		//글자색

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
		
		//폰트
		imgN.setFont(new Font("궁서체", Font.BOLD, 25));
		imgM.setFont(new Font("궁서체", Font.BOLD, 25));
		button1.setFont(new Font("궁서체", Font.BOLD, 25));
		button2.setFont(new Font("궁서체", Font.BOLD, 25));
		button3.setFont(new Font("궁서체", Font.BOLD, 14));
		button4.setFont(new Font("궁서체", Font.BOLD, 14));
		button5.setFont(new Font("궁서체", Font.BOLD, 14));
		button6.setFont(new Font("궁서체", Font.BOLD, 14));
		button7.setFont(new Font("궁서체", Font.BOLD, 14));
		label1.setFont(new Font("궁서체", Font.ITALIC, 33));
		//label2.setFont(new Font("궁서체", Font.BOLD, 20));
		label3.setFont(new Font("궁서체", Font.BOLD, 17));
		label4.setFont(new Font("궁서체", Font.BOLD, 17));
		label5.setFont(new Font("궁서체", Font.ITALIC , 70));
		//위치
		label1.setBounds(160, 120, 300, 50); // 사진 이름
		//label2.setBounds(200, 145, 300, 30); // 내용
		label3.setBounds(420, 755, 150, 20); //현재 페이지
		//label4.setBounds(400, 750, 150, 20); // 전체페이지
		label5.setBounds(10, 10, 300, 100); // gallery
		button1.setBounds(280, 690, 240, 50); // 다음
		button2.setBounds(40, 690, 240, 50); // 이전
		button3.setBounds(45, 750, 100, 35);	// 추가
		button4.setBounds(275, 750, 100, 35); // 삭제
		button5.setBounds(160, 750, 100, 35); //검색
		button6.setBounds(460, 0, 100, 35); // 모두보기버튼
		imgPanel.setBounds(40, 190, 480, 500);
		button7.setBounds(490, 165, 30, 25); // 편집버튼
		//button6.setBounds(350, 560, 130, 30);
		//add(imgPanel);
		//add(panel2);
		add(panel);
		//add(panel6);
		//add(panel4);
		
		pack();
	}
		
	/* 초기 앨범 기본 파일 복사
	public void copyFile(String path, String imageName) {
        //복사될 파일경로
        String copyFilePath = "image\\" + imageName;
        //복사파일객체생성
        File copyFile = new File(copyFilePath);
        System.out.println(copyFilePath + "사진을 복사했습니다.");
        try {           
            FileInputStream fis = new FileInputStream("image_set\\" + imageName); //저장해둔 image_set 에서 앨범 기본이미지를 가져옴.
            FileOutputStream fos = new FileOutputStream(copyFile); //복사할파일
            
            int fileByte = 0; 
            // fis.read()가 -1 이면 파일을 다 읽은것
            while((fileByte = fis.read()) != -1) {
                fos.write(fileByte);
            }
            //자원사용종료
            fis.close();
            fos.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }		
	}*/
	
	//사진 저장 창
	public void make_subFrame() {
		JFrame subFrame = new JFrame("사진 정보 (잠시만 기다려 주세요.)");
		subFrame.setSize(380,165);
		subFrame.getDefaultCloseOperation();
		subFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {	
				subFrame.setVisible(false);
				subFrame.dispose();
			}
		});				
			
		Panel p1 = new Panel();
		p1.add(new Label("사진 이름: "));

		p1.add(tfd);
		p1.setSize(200, 100);
		
		Panel p2 = new Panel();
		p2.add(new Label("사진 내용: "));
		p2.add(tfd2);
		p1.setSize(200, 100);
		Panel p3 = new Panel();
		btn.setBackground(Color.white);
		p3.add(new Label("내용을 저장하고 X를 누르세요."));
		p3.add(btn);
		
		Panel p4 = new Panel();
		p4.add(p1);
		p4.add(p2);
		p4.add(p3);
			
		btn.addActionListener(this);
		subFrame.setResizable(false); // 창 조정 x
		subFrame.add(p4);
		subFrame.setVisible(true);
	}
	//사진 저장 창
	public void make_subFrame2() {
		JFrame subFrame2 = new JFrame("사진 찾기");
		subFrame2.setSize(380,165);
		subFrame2.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {	
				subFrame2.setVisible(false);
				subFrame2.dispose();
			}
		});				
			
		Panel p1 = new Panel();
		p1.add(new Label("이름으로 찾기"));
		p1.add(tfd3);
		p1.setSize(200, 100);
			
		Panel p2 = new Panel();
		p2.add(new Label("페이지로 이동 "));
		p2.add(tfd4);
		p1.setSize(200, 100);
		Panel p3 = new Panel();
		btn2.setBackground(Color.white);
		p3.add(new Label("사진을 찾고 X를 누르세요."));
		p3.add(btn2);
		
			Panel p4 = new Panel();
			p4.add(p1);
			p4.add(p2);
			p4.add(p3);
				
			btn2.addActionListener(this);
			subFrame2.setResizable(false); // 창 조정 x
			subFrame2.add(p4);
			subFrame2.setVisible(true);
		}		
	public static void main(String[] args) {
		new image_change();
	}
}
/*
https://calsifer.tistory.com/239
https://programmingsummaries.tistory.com/61    GUI 구현
https://raccoonjy.tistory.com/17
https://halfmoon9.tistory.com/12

for(int i=0; i<1000; i++) { // 0은 |로 초기화 했으므로 1부터 시작
			if(arr[i] == '|') { // 만약 구분자 나오면
				s = ""; // 초기화
				s2 = "";
				s3 = ""; // 초기화
				flag5 = false;
				flag6 = false; // 초기화
				//MAX_SIZE++;
				for(int j=start; j<i; j++) {
					if(arr[j] == ';') {
						s2="";
						flag5=true;
						j++;
					}
					if(arr[j] == ':') { // :전에는 사진경로, :이후에는 사진 내용
						s3="";    
						flag6=true;
						j++;
					}
					if(flag5 == false) { // 사진 경로
						s+=arr[j];
					}
					if(flag5==true && flag6 == false) {
						s2+=arr[j];
					}
					s3+=arr[j];
				}
				if(cnt!=0) // 파일을 처음부터 읽어야 컴파일이 되는데, 처음 내용은 null이므로 넘어감
					System.out.println(cnt + ". "+ s2); // 사진 이름 출력
					//name.add(s2);  // 사진 이름 저장
					//memo.add(s3); //사진 내용 저장
					start=i+1; // 다음 조건이 성립할때의 시작값은 이전 조건이 끝나고 다음 인덱스값
					cnt++; // 현재 저장된 총 사진 개수
			}
		}
*/
