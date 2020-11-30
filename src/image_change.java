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
 * 서브 프레임이 안꺼져서 저장할때, 내용이 n*n+1/2개씩 나옴	
*/

public class image_change extends JFrame implements ActionListener {
	private BufferedImage img; //화면 이미지 출력
	private JButton button1, button2, button3, button4, button5; //다음 버튼, 이전 버튼, 사진 추가, 사진 삭제, 사진 검색
	private JPanel imgPanel; // 이미지 나오는 창
	private int button_index = 0; // 이미지 인덱스(1 ~ 사진 최대 개수, 0은 이미지없음 사진)
	private int MAX_SIZE = 0, imageCnt = 1; //이미지 최대 개수, 이미지 추가 개수 ()
	private boolean flag = false, flag2 = false, flag3 = false;// (다음, 이전) , (사진 추가, 사진 삭제)
	private String imageName; // 추가된 사진 이름
	private JButton btn = new JButton("저장");
	Vector<String> list = new Vector<String>();  // 사진 경로 이름 저장
	Vector<String> name = new Vector<String>(); // 사진 이름 저장
	Vector<String> memo = new Vector<String>(); // 사진 메모 내용
	Vector<String> list2 = new Vector<String>();
	private JTextField tfd = new JTextField(null, 20); // 사진 이름 입력 
	private JTextField tfd2 = new JTextField(null, 20); // 메모 내용 입력
	String name2, memo2;
	JLabel label1 = new JLabel(), label2 = new JLabel(); // 이미지창에 사진이름, 사진 내용 나오는 라벨
	JFileChooser fc;	
	public image_change() {
		mkDir(); //이미지 저장할 폴더 + name.txt
		fc = new JFileChooser(); //폴더 파일 
		fc.setMultiSelectionEnabled(true);
		String buf = "";

		char[] arr = new char[1000];
		
		File file = new File("image\\test.txt");		  
		
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
		System.out.println("현재 저장된 사진");
		showFilesInDIr("image\\");	
		int cnt=0;
		int start=0;
		String s = ""; // 사진 경로
		String s2 = "";
		String s3 = ""; //사진 내용
		boolean flag5 = false, flag6 = false;
		System.out.println("현재 저장된 사진 이름 (시간이 없다면 사진 이름을 검색 하세요.)");
		for(int i=0; i<1000; i++) { // 0은 |로 초기화 했으므로 1부터 시작
			if(arr[i] == '|') { // 만약 구분자 나오면
				s = ""; // 초기화
				s2 = "";
				s3 = ""; // 초기화
				flag5 = false;
				flag6 = false; // 초기화
				MAX_SIZE++;
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
		
		for(int i=0; i<list2.size(); i++) {
			list.add(list2.get(i));
			name.add("sd");
			memo.add("ASDas");
		}
		
		imgPanel = new ChangeImagePanel();
		JPanel panel = new JPanel(); // 버튼들
		JPanel panel2 = new JPanel(); // 사진 이름, 메모 내용
		JPanel panel3 = new JPanel(); // 사진 이름 텍스트, 사진 내용 텍스트
		JPanel panel4 = new JPanel(); // 2,3 합침
		
		label1.setText(name.get(button_index));
		label2.setText(memo.get(button_index));
		
		setTitle("조민서 앨범"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false); // 창 조정 x

		// 컴포넌트(버튼) 만들기
		button1 = new JButton("다음 사진");
		button2 = new JButton("이전 사진");
		button3 = new JButton("사진 추가");
		button4 = new JButton("사진 삭제");
		button5 = new JButton("사진 검색");
		
		//버튼 크기 가로는 480이므로 480/5 = 96 > 가로크기
		button1.setPreferredSize(new Dimension(90, 35));
		button2.setPreferredSize(new Dimension(90, 35));
		button3.setPreferredSize(new Dimension(90, 35));
		button4.setPreferredSize(new Dimension(90, 35));
		button5.setPreferredSize(new Dimension(90, 35));
		
		// 버튼색 변경
		button1.setBackground(Color.WHITE);
		button2.setBackground(Color.WHITE);
		button3.setBackground(Color.WHITE);
		button4.setBackground(Color.WHITE);
		button5.setBackground(Color.WHITE);
		
		// 버튼 액션 작동
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		
		// 패널에 컴포넌트 붙이기
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
		JLabel imgN = new JLabel("사진 이름: ");
		JLabel imgM = new JLabel("사진 내용: ");
		imgN.setFont(new Font("돋움", Font.BOLD, 22));
		imgM.setFont(new Font("돋움", Font.BOLD, 22));
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
	
	//사진 저장 창
	public void prepareGui() {
		JFrame subFrame = new JFrame("사진 정보 (잠시만 기다려 주세요.)");
		subFrame.setSize(380,165);
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
				fileWriter.write(imageName + ";" + name.get(button_index) + ":" + memo.get(button_index) + "|"); // |로 구분자를 정함				   
				fileWriter.close();
			  } catch (IOException e1) {
				  e1.printStackTrace();
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
		    prepareGui(); // 저장하는 버튼창 나오는 함수
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
		        list.add(new String("image\\" + imageName));
		        button_index = MAX_SIZE;
		        MAX_SIZE++; // 사진 최대 개수
	        }	  

		}
		
		// 사진 삭제
		if(e.getSource() == button4 && button_index > 0 && MAX_SIZE > 0 ) {	

			flag2 = true;
			File file = new File(list.get(button_index)); 
			if( file.exists() ) { 
				if(file.delete()) {  		// 이미지 파일 삭제
					System.out.println(button_index + "페이지 사진을 삭제 했습니다.");  
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
			if(MAX_SIZE == 1) System.out.println("사진을 전부 삭제해서 사진이 없습니다.");
		}
		
		
		Scanner sc = new Scanner(System.in);
		
		// 사진 검색 
		if(e.getSource() == button5 && MAX_SIZE == 0) {
			System.out.println("찾을 사진이 없습니다.");
		}
		if(e.getSource() == button5 && MAX_SIZE > 0)  {
			System.out.println();
			System.out.println("찾는 사진 이름을 입력하세요.");
			String s = sc.nextLine();
			for(int i=0; i<MAX_SIZE; i++) {
				if(s.equals(name.get(i))) {
					System.out.println("사진을 찾았습니다.");
					button_index = i;
					flag = true;
					break;
				}
				if(flag == false && i == MAX_SIZE-1)  {
					System.out.println("찾는 사진이 없습니다.");
				}
			}
		}
		// 행동
		try {			
			if(flag == true) {
				img = ImageIO.read(new File(list.get(button_index).toString())); //이미지 있으면 가져오기
			}
			if(flag2 == true) {
				img = ImageIO.read(new File(list.get(button_index).toString()));
			}
			
			label1.setFont(new Font("돋움", Font.BOLD, 22));
			label2.setFont(new Font("돋움", Font.BOLD, 22));
			label1.setForeground(Color.white);
			label2.setForeground(Color.white);
			label1.setText(name.get(button_index));
			label2.setText(memo.get(button_index));
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
	
	
	
	// image폴더가 없으면 image폴더를 만들고 미리 만들어둔 image_set폴더에 있는 이미지 2개를 복사.
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