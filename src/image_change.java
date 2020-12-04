import java.awt.*;
import java.io.*;
import java.util.Vector;
import java.awt.event.*;
import java.awt.image.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * 서브 프레임이 안꺼져서 저장할때, 내용이 n*n+1/2개씩 나옴	=> 서브 프로세스 종료 찾기 || 프로그램 최대 사진 2개
 *	추가/	그림판
*/

public class image_change extends JFrame implements ActionListener {
	public String buf = "";
	char[] arr = new char[100000]; //test.txt 최대 100000글자 까지를 받아옴		
	String[] copyList = new String[1000];
	String[] copyName = new String[1000];
	String[] copyMemo = new String[1000];
	File file = new File("image\\test.txt");	
	private JButton button1, button2, button3, button4, button5, button6, button7, button8, btn4, btn5, btn6; //다음 버튼, 이전 버튼, 사진 추가, 사진 삭제, 사진 검색, 편집, 모두보기, 이동, 사진 정렬(8910)
	private JButton BCbtn1,BCbtn2,BCbtn3,BCbtn4,BCbtn5,BCbtn6,BCbtn7,BCbtn8,BCbtn9,BCbtn10,BCbtn11,BCbtn12;
	private JPanel imgPanel; // 이미지 나오는 창
	private int button_index = 0; // 이미지 인덱스(1 ~ 사진 최대 개수, 0은 이미지없음 사진)
	private int MAX_SIZE = 0, imageCnt = 0; //이미지 최대 개수, 이미지 추가 개수 ()
	private boolean flag = false, flag2 = false, flag3 = false, flag5 = false, flag6 = false;// (다음, 이전) , (사진 추가, 사진 삭제) , name2,memo2 변수적용
	private BufferedImage img; //화면 이미지 출력
	private String imageName; // 추가된 사진 이름
	private JButton btn = new JButton("저장");
	private JButton btn2 = new JButton("찾기");
	private JButton btn3 = new JButton("이동");
	Vector<String> list = new Vector<String>();  // 사진 경로 이름 저장
	Vector<String> name = new Vector<String>(); // 사진 이름 저장
	Vector<String> memo = new Vector<String>(); // 사진 메모 내용
	Vector<String> list2 = new Vector<String>();
	private JTextField tfd = new JTextField("", 20); // 사진 이름 입력 
	private JTextField tfd2 = new JTextField("", 20); // 메모 내용 입력
	private JTextField tfd3 = new JTextField("", 20); // 사진 이름 입력 
	private JTextField tfd4 = new JTextField("", 20); // 메모 내용 입력
	String name2="", memo2=""; // test.txt와 폴더안에 실제로 있는 이미지랑 비교해서 실제로 있는 이미지만 벡터에 넣을 name,memo변수
	JLabel label1 = new JLabel(), label2 = new JLabel(), label3 = new JLabel(), label4 = new JLabel(),label5 = new JLabel();  // 이미지창에 사진이름, 사진 내용 나오는 라벨, 버튼 인덱스나옴
	JFileChooser fc = new JFileChooser(); //폴더 선택 파일
	JLabel[] labels = new JLabel[100]; // 
	JPanel panel = new JPanel(); // 메인 프레임 ,배경색을 바꿀때 공유함
	
	public image_change() {
		mkDir(); // image\\폴더가 없으면 image폴더를 만들고 image\\test.txt 파일을 만든다		
		fc.setMultiSelectionEnabled(true);
		list.add("현재 저장된 사진");    //test.txt를 만들 때""를 넣고 |로 구분지어 주는데 이때 사진은 의미 없는거므로 의미없는 아무거나 넣어주고 button_index를 1부터 시작한다
		name.add("환영합니다.");
		memo.add("x");
		MAX_SIZE++;
		copyText(); // 이때동안 저장된 image경로 가져오기
		showFilesInDIr("image\\");	// "image\\"폴더에 있는 파일들 전부 가져와서 list2에 경로 집어 넣기
		copyList(); // test.txt에 현재까지 저장된 이미지 경로와 현재 폴더에 실제로 이미지가 있는경우 벡터에 삽입
		make_mainFrame(); // 메인 프레임 만들기
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
	}	
		
	
	// image폴더가 없으면 image폴더를 만듦
	public void mkDir() {
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
	    	g.drawImage(img, 0, 0, 480, 500, null); //사진, + x, + y, 가로크기, 세로크기, null
	    }
	}	
	
	// 사진 (다음, 이전)
	public void actionPerformed(ActionEvent e) {
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
		    if(imageCnt > 1) {
		    	make_subFrame8();
		    }
		    else {
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
		        System.out.println("프로그램을 실행시키고 " + imageCnt + "번째 추가한 사진: " + n.getName());
		        imageName = n.getName();		         //이미지 파일 집어넣기(img경로, 복사할 img이름)
		        }
		       
		        copyFile(fc.getSelectedFile(), imageName);
		        for(int i=0; i<list.size(); i++) {
		        	if(list.get(i).equals("image\\" + imageName)) {
		        		make_subFrame9(i);
		        		System.out.println(i + "번째 사진과 겹칩니다");
		        		break;
		        	}
		        	if(i==list.size()-1) {
		        		imageCnt++; // 최대 사진 개수2
		        		make_subFrame(); // 저장하는 버튼창 나오는 함수 		   
		        		list.add(new String("image\\" + imageName));
				        button_index = MAX_SIZE;
				        MAX_SIZE++; // 사진 최대 개수
				        break;
		        		}
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
		//사진 목록
		if(e.getSource() == button6)  {
			make_subFrame4(); //button로 작동
		}
		//사진 편집
		if(e.getSource() == button7) {	
			make_subFrame5(); // btn(6,5,4)로 작동
		}	
		//페이지 이동
		if(e.getSource() == button8 && MAX_SIZE > 1)  {
			make_subFrame3(); //btn3로 작동
		}
		
		//사진삽입 저장 버튼
		if(e.getSource() == btn) {
			flag3 = true;
			name.add(tfd.getText());
			memo.add(tfd2.getText());
			System.out.println("저장한 사진 경로: " + "image\\" + imageName);
			System.out.println("저장한 사진 이름: " + name.get(MAX_SIZE - 1).toString());
			System.out.println("저장한 사진 내용: " + memo.get(MAX_SIZE - 1).toString());
			System.out.println("----------------------------------------------");
			btn.setEnabled(false); // 저장 버튼 비활성화 (사용자가 한번만 저장하게)
		    //메모장에 사진 이름 저장
	    	String filePath = "image\\test.txt"; //사진 경로, 내용

			try {
				FileWriter fileWriter = new FileWriter(filePath,true);
				if(!buf.contains(tfd.getText())) // test.txt중복체크
				fileWriter.write("image\\" + imageName + ";" + name.get(button_index) + ":" + memo.get(button_index) + "|"); // |로 구분자를 정함				   
				fileWriter.close();
			  } catch (IOException e1) {
				  e1.printStackTrace();
			  }
			tfd.setText("");
			tfd2.setText("");
		}
		
		//사진 이름으로 찾기
		if(e.getSource() == btn2) {
			int cnt=0;
			String s = tfd3.getText();
			tfd3.setText(""); // 입력 후 공백으로 초기화
			for(int i=0; i<list.size(); i++) {
				if(name.get(i).equals(s)) {
					button_index = i;
					flag = true;
					System.out.println(s + "사진을 찾았습니다.");
					break;
				}
			}
			if(flag == false && cnt==0)  {
				cnt++;
				System.out.println("찾는 사진이 없습니다.");
			}
		}
		
		// 페이지 이동
		if(e.getSource() == btn3) {
			int cnt=0;
			int s = Integer.parseInt(tfd4.getText());
			
			tfd4.setText(""); // 입력 후 공백으로 초기화
			if(s < MAX_SIZE && s > 0) {
				button_index = s;
			}
			if(flag == false && cnt==0)  {
				cnt++;
				System.out.println("찾는 사진이 없습니다.");
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
		//흰 라그 그 다그 검 빨 오 노 초 파 핑 씨
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
		// 행동
		try {			
			img = ImageIO.read(new File(list.get(button_index).toString()));

			JLabel imgN = new JLabel("사진 이름: ");
			JLabel imgM = new JLabel("사진 내용: ");
			label1.setFont(new Font("나눔고딕", Font.BOLD, 33));
			//label2.setFont(new Font("나눔고딕", Font.BOLD, 20));
			label3.setFont(new Font("나눔고딕", Font.BOLD, 17));
			label4.setFont(new Font("나눔고딕", Font.BOLD, 17));
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
        System.out.println(copyFilePath + "에 사진을 복사 하는중 입니다.");
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
		setPreferredSize(new Dimension(570,840));

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
		button8 = new JButton("이동");

		//윤곽선
		button1.setBorderPainted(false); 	button2.setBorderPainted(false); 	button3.setBorderPainted(false);
		button4.setBorderPainted(false); 	button5.setBorderPainted(false); 	button6.setBorderPainted(false);
		button7.setBorderPainted(false);	button8.setBorderPainted(false);
		// 버튼 액션 작동
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
		panel.add(button8);

		setVisible(true);
		//배경색
		panel.setBackground(Color.DARK_GRAY);
		button1.setBackground(Color.gray);
		button2.setBackground(Color.gray);
		button3.setBackground(Color.black);
		button4.setBackground(Color.black);
		button5.setBackground(Color.black);
		button6.setBackground(Color.black);
		button7.setBackground(Color.black);
		button8.setBackground(Color.black);
		
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
		button8.setForeground(Color.white);
		
		//폰트
		imgN.setFont(new Font("나눔고딕", Font.BOLD, 25));
		imgM.setFont(new Font("나눔고딕", Font.BOLD, 25));
		button1.setFont(new Font("나눔고딕", Font.BOLD, 25));
		button2.setFont(new Font("나눔고딕", Font.BOLD, 25));
		button3.setFont(new Font("나눔고딕", Font.BOLD, 14));
		button4.setFont(new Font("나눔고딕", Font.BOLD, 14));
		button5.setFont(new Font("나눔고딕", Font.BOLD, 14));
		button6.setFont(new Font("나눔고딕", Font.BOLD, 14));
		button7.setFont(new Font("나눔고딕", Font.BOLD, 14));
		button8.setFont(new Font("나눔고딕", Font.BOLD, 14));
		label1.setFont(new Font("나눔고딕", Font.ITALIC, 33));
		//label2.setFont(new Font("나눔고딕", Font.BOLD, 20));
		label3.setFont(new Font("나눔고딕", Font.BOLD, 17));
		label4.setFont(new Font("나눔고딕", Font.BOLD, 17));
		label5.setFont(new Font("나눔고딕", Font.ITALIC , 55));
		
		//위치
		label1.setBounds(160, 120, 300, 50); // 사진 이름
		//label2.setBounds(200, 145, 300, 30); // 내용
		//label4.setBounds(400, 750, 150, 20); // 전체페이지
		label5.setBounds(10, 5, 500, 100); // gallery
		button1.setBounds(280, 690, 240, 50); // 다음
		button2.setBounds(40, 690, 240, 50); // 이전
		button3.setBounds(45, 750, 80, 35);	// 추가
		button5.setBounds(140, 750, 80, 35); //검색
		button4.setBounds(330, 750, 100, 35); // 삭제
		button8.setBounds(235, 750, 80, 35); // 이동
		button7.setBounds(490, 165, 30, 25); // 편집버튼
		button6.setBounds(460, 0, 100, 35); // 목록보기 버튼
		label3.setBounds(440, 762, 150, 20); //현재 페이지
		imgPanel.setBounds(40, 190, 480, 500);
		//button6.setBounds(350, 560, 130, 30);
		//add(imgPanel);
		//add(panel2);
		add(panel);
		//add(panel6);
		//add(panel4);
		
		pack();
	}
	
	//사진 저장 창
	public void make_subFrame() {
		JFrame subFrame = new JFrame("사진 정보 (잠시만 기다려 주세요.)");
		subFrame.setSize(380,165);

		subFrame.setVisible(true);

		subFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				subFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		});				
			
		Panel p1 = new Panel();
		p1.add(new Label("사진 이름: "));
		p1.setFont(new Font("나눔고딕",Font.BOLD,12));
		p1.setForeground(Color.white);

		p1.add(tfd);
		p1.setSize(200, 100);
		
		btn.setBackground(Color.black);
		btn.setFont(new Font("나눔고딕",Font.BOLD,12));
		btn.setForeground(Color.white);
		btn.setBorderPainted(false);	
		
		Panel p2 = new Panel();
		p2.add(new Label("사진 내용: "));
		p2.add(tfd2);

		p2.setFont(new Font("나눔고딕",Font.BOLD,12));
		p2.setForeground(Color.white);

		Panel p3 = new Panel();
		p3.add(new Label("내용을 저장하고 X를 누르세요."));
		p3.add(btn);

		p3.setFont(new Font("나눔고딕",Font.BOLD,12));
		p3.setForeground(Color.white);
		
		Panel p4 = new Panel();
		p4.add(p1);
		p4.add(p2);
		p4.add(p3);
		p4.setBackground(Color.DARK_GRAY);	
		btn.addActionListener(this);
		subFrame.setResizable(false); // 창 조정 x
		subFrame.add(p4);
	}
	//사진 검색 창
	public void make_subFrame2() {
		JFrame subFrame2 = new JFrame("사진 찾기");
		subFrame2.setSize(380,120);
		subFrame2.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {	
				subFrame2.setVisible(false);
				subFrame2.dispose();
			}
		});				
			
		Panel p1 = new Panel();
		p1.add(new Label("사진 이름으로 찾기"));
		p1.add(tfd3);
		p1.setFont(new Font("나눔고딕",Font.BOLD,12));
		p1.setForeground(Color.white);
		
		Panel p2 = new Panel();
		p2.add(new Label("사진을 찾고 X를 누르세요."));
		p2.setFont(new Font("나눔고딕",Font.BOLD,12));
		p2.setForeground(Color.white);
		
		btn2.setBackground(Color.black);
		btn2.setFont(new Font("나눔고딕",Font.BOLD,12));
		btn2.setForeground(Color.white);
		btn2.setBorderPainted(false);	
		p2.add(btn2);
		
		Panel p3 = new Panel();
		p3.add(p1);
		p3.add(p2);
		p3.setBackground(Color.DARK_GRAY);		
		btn2.addActionListener(this);
		subFrame2.setResizable(false); // 창 조정 x
		subFrame2.add(p3);
		subFrame2.setVisible(true);
	}		
	
	//페이지 이동창
	public void make_subFrame3() {
		JFrame subFrame3 = new JFrame("페이지 이동");
		subFrame3.setSize(380,120);
		subFrame3.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {	
				subFrame3.setVisible(false);
				subFrame3.dispose();
			}
		});						
		Panel p2 = new Panel();
		p2.add(new Label("이동할  페이지 "));
		p2.add(tfd4);

		p2.setFont(new Font("나눔고딕", Font.BOLD, 12));		
		p2.setForeground(Color.white);

		Panel p3 = new Panel();
		btn3.setBackground(Color.black);
		btn3.setFont(new Font("나눔고딕",Font.BOLD,12));
		btn3.setForeground(Color.white);
		btn3.setBorderPainted(false);	
		
		p3.add(new Label("이동하고 X를 누르세요."));
		p3.add(btn3);
		p3.setFont(new Font("나눔고딕", Font.BOLD, 12));		
		p3.setForeground(Color.white);

		Panel p4 = new Panel();
		p4.add(p2);
		p4.add(p3);
		p4.setBackground(Color.DARK_GRAY);
		
		btn3.addActionListener(this);
		subFrame3.setResizable(false); // 창 조정 x
		subFrame3.add(p4);
		subFrame3.setVisible(true);
	}			
	
	//사진 목록 
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
		JFrame subFrame4 = new JFrame("사진 목록");
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
		labels[0] = new JLabel("사진 이름");
		labels[0].setBounds(40, 0, 200, 50); // 다음
		labels[0].setFont(new Font("돋움",Font.BOLD, 25));
		labels[1] = new JLabel("사진 내용");
		labels[1].setBounds(220, 0, 340 + max2, 50); // 다음
		labels[1].setFont(new Font("돋움",Font.BOLD, 25));
		String s1 = " ";
		for(int i=2; i<MAX_SIZE+1; i++) {
			int x= max2 - name.get(i-1).length()  + 20;
			String s2 = s1.repeat(x);
			labels[i] = new JLabel(i-1 + "    " + name.get(i-1) + s2 + memo.get(i-1));
			labels[i].setFont(new Font("돋움",Font.BOLD, 23));
			labels[i].setBounds(0, 30*i, 300*MAX_SIZE, 30); // 다음
		}
		for(int i=0; i<MAX_SIZE+1; i++) {
			labels[i].setForeground(Color.white);
			p1.add(labels[i]);
		}
		p1.setBackground(Color.black);
		p1.setFont(new Font("나눔고딕",Font.BOLD,12));

		
		//btn4.addActionListener(this);
		subFrame4.setResizable(false); // 창 조정 x
		subFrame4.add(p1);
		subFrame4.setVisible(true);
	}
	
	//작업 창
	public void make_subFrame5() {
		JFrame subFrame5 = new JFrame("작업");
		subFrame5.setSize(300, 400);
		subFrame5.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent windowEvent) {	
				subFrame5.setVisible(false);
				subFrame5.dispose();
			}
		});				
		
		Panel p1 = new Panel();
		p1.setLayout(new GridLayout(3,1));
		
		btn4 = new JButton("앨범 배경색 바꾸기");
		btn5 = new JButton("내용 보기");
		btn6 = new JButton("사진 순서 반대로 바꾸기");
		
		btn4.setBorderPainted(false);	
		btn5.setBorderPainted(false);
		btn6.setBorderPainted(false);
		
		btn4.addActionListener(this);				
		btn5.addActionListener(this);
		btn6.addActionListener(this);
		
		btn4.setBackground(Color.black);
		btn5.setBackground(Color.black);
		btn6.setBackground(Color.black);
		
		btn4.setFont(new Font("나눔고딕", Font.BOLD, 14));
		btn5.setFont(new Font("나눔고딕", Font.BOLD, 14));
		btn6.setFont(new Font("나눔고딕", Font.BOLD, 14));
		
		btn4.setForeground(Color.white);
		btn5.setForeground(Color.white);
		btn6.setForeground(Color.white);
		
		p1.add(btn5);
		p1.add(btn6);
		p1.add(btn4);
		
		subFrame5.setResizable(false); // 창 조정 x
		subFrame5.add(p1);
		subFrame5.setVisible(true);
	}
	
	//앨범 색 변경창
	public void make_subFrame6() {
		JFrame subFrame6 = new JFrame("앨범 배경색 변경");
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
		
		BCbtn1.setFont(new Font("나눔고딕", Font.BOLD, 14));
		BCbtn2.setFont(new Font("나눔고딕", Font.BOLD, 14));
		BCbtn3.setFont(new Font("나눔고딕", Font.BOLD, 14));
		BCbtn4.setFont(new Font("나눔고딕", Font.BOLD, 14));
		BCbtn5.setFont(new Font("나눔고딕", Font.BOLD, 14));
		BCbtn6.setFont(new Font("나눔고딕", Font.BOLD, 14));
		BCbtn7.setFont(new Font("나눔고딕", Font.BOLD, 14));
		BCbtn8.setFont(new Font("나눔고딕", Font.BOLD, 14));
		BCbtn9.setFont(new Font("나눔고딕", Font.BOLD, 14));
		BCbtn10.setFont(new Font("나눔고딕", Font.BOLD, 14));
		BCbtn11.setFont(new Font("나눔고딕", Font.BOLD, 14));
		BCbtn12.setFont(new Font("나눔고딕", Font.BOLD, 14));
		
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
		
		subFrame6.setResizable(false); // 창 조정 x
		subFrame6.add(p1);
		subFrame6.setVisible(true);
	}
	
	//사진 내용 보기
	public void make_subFrame7() {
		JFrame subFrame7 = new JFrame("사진 내용");
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
		label1.setFont(new Font("나눔고딕",Font.BOLD,22));		
		label1.setForeground(Color.white);
		p1.setBackground(Color.black);
		
		p1.add(label1);

		subFrame7.setResizable(false); // 창 조정 x
		subFrame7.add(p1);
		subFrame7.setVisible(true);
	}
	
	//사진 2개 초과시 위험경보창
	public void make_subFrame8() {
		JFrame subFrame8 = new JFrame("중독 주의!");
		subFrame8.setSize(800,200);
		subFrame8.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {	
				subFrame8.setVisible(false);
				subFrame8.dispose();
			}
		});			
		
		Panel p1 = new Panel();
		p1.setLayout(new GridLayout(2,1));
		p1.add(new Label("위험) 이 프로그램은 실행하고 2개만 추가 가능합니다. (중독 예방차원)"));
		p1.add(new Label("          급하면 프로그램을 종료하고 다시 실행해서 추가 해주세요."));

		p1.setFont(new Font("나눔고딕", Font.BOLD, 22));		
		p1.setForeground(Color.white);
		p1.setBackground(Color.black);

		subFrame8.setResizable(false); // 창 조정 x
		subFrame8.add(p1);
		subFrame8.setVisible(true);
	}		
	
	//사진 추가할때 겹치는 사진 인경우
		public void make_subFrame9(int index) {
			JFrame subFrame9 = new JFrame("사진 겹침 주의!");
			subFrame9.setSize(900,100);
			subFrame9.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent windowEvent) {	
					subFrame9.setVisible(false);
					subFrame9.dispose();
				}
			});			
			
			Panel p1 = new Panel();
			p1.setLayout(new GridLayout(2,1));
			p1.add(new Label("추가하려는 사진이" + index + "번째 사진과 겹칩니다." + index + "번째 사진을 지우고 다시 추가하세요."));

			p1.setFont(new Font("나눔고딕", Font.BOLD, 22));		
			p1.setForeground(Color.white);
			p1.setBackground(Color.black);

			subFrame9.setResizable(false); // 창 조정 x
			subFrame9.add(p1);
			subFrame9.setVisible(true);
		}		
	
	public void sort() {
	//사진 이름순 오름차순
		//Collections.sort(list,Collections.reverseOrder()); 
		// 사진이름 내림차순
		//Collections.reverse(list);
		for(int i=1; i<list.size(); i++) { //벡터 복사
			copyList[i] = list.get(i);
			copyName[i] = name.get(i);
			copyMemo[i] = memo.get(i);
		}
		int size = list.size(); //원래 벡터 사이즈
		list.clear();
		name.clear();
		memo.clear();
		list.add("현재 저장된 사진");    // 모든 벡터 클리어하고 처음 이미지 없을때 초기 깂값초기화
		name.add("환영합니다.");
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
https://programmingsummaries.tistory.com/61    GUI 구현
https://raccoonjy.tistory.com/17
https://halfmoon9.tistory.com/12
*/