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
/* 	1. pair<int,string> 이용해서 (index, 사진 이름) 저장
 	2. 폴더없으면 만들기
	3. 사진 삭제
	4. 사진 검색
*/
public class image_change extends JFrame implements ActionListener {
	private BufferedImage img; //화면 이미지 출력
	private JButton button1, button2, button3, button4, button5; //다음 버튼, 이전 버튼, 사진 추가, 사진 삭제, 사진 검색
	private JPanel imgPanel; // 이미지 나오는 창
	private int button_index = 0; // 이미지 인덱스(1 ~ 사진 최대 개수, 0은 이미지없음 사진)
	private int MAX_SIZE = 0, imageCnt = 0; //이미지 최대 개수, 이미지 추가 개수
	private boolean flag = false, flag2 = false;// (다음, 이전) , (사진 추가, 사진 삭제)
	private String imageName; // 추가된 사진 이름
	
	Vector<String> list = new Vector<String>();  // 사진 경로 이름 저장

	JFileChooser fc;
	public image_change() {
		
		mkDir(); //이미지 저장할 폴더가 없으면 폴더를 만들고 미리 설정해둔 이미지를 저장함.
		
		fc = new JFileChooser(); //폴더 파일 
		fc.setMultiSelectionEnabled(true);
		JPanel panel = new JPanel(); // 버튼들

		setTitle("조민서 앨범"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false); // 창 조정 x

		imgPanel = new ChangeImagePanel();
		
		// 초기 상태
		list.add("image\\0.jpg"); // 버튼인덱스를 1 부터 시작해서 0.jpg는 안나옴(버튼으로 못봄, 모든사진을 삭제해서 사진이 하나도 없으면 나옴)
		list.add("image\\1.jpg");
		
		MAX_SIZE += 2; // 0.jpg = 이미지없음 버튼,  1.jpg = 처음 기본 이미지 => 사진 최대 개수는 0+2 
		
		// 컴포넌트(버튼) 만들기
		button1 = new JButton("다음 사진");
		button2 = new JButton("이전 사진");
		button3 = new JButton("사진 추가");
		button4 = new JButton("사진 삭제");
		button5 = new JButton("사진 검색");
		
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

		String imgFile = ".jpg"; // 나중에 png, jpg, 등등
		//다음
		if(e.getSource() == button1 && button_index < MAX_SIZE-1) { // 사진 개수가 MAX면 button_index가 안늘어남.
			button_index++;
			flag = true; // 다음 	
		// 이전
		} else if(e.getSource() == button2 && button_index > 1 && MAX_SIZE > 0) { // 사진 개수가 1보다 작으면 button_index가 안내려감. 사진 다 지우면 button_index == 0되서, 이미지 없음 나옴.
			button_index--;
			flag = true; // 이전
		}
		
		//사진 삽입
		if(e.getSource() == button3) {
			// jpg, png가 디폴트값
			FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "png", "jpg", "png");
		    // 위 내용 적용
		    fc.setFileFilter(filter);
		    // 사진추가 창 이름
		    fc.setDialogTitle("이미지 선택 (jpg가 아니면 시간이 오래 걸립니다.)");

		    // 다이어로그 생성
		    int Dialog = fc.showOpenDialog(this);
		    // 예 확인시
		    if(Dialog == JFileChooser.APPROVE_OPTION) {
		      	flag2 = true;
		        // 파일 선택
		      	File[] f = fc.getSelectedFiles();
		      	
		        for(File n : f) {
		        System.out.println(++imageCnt + "번째 추가한 사진 이름: " + n.getName());
		        imageName = n.getName();
		        
		        copyFile(fc.getSelectedFile(), imageName); //이미지 파일 집어넣기(img경로, 복사할 img이름)
		        list.add(new String("image\\" + imageName));
		        button_index = MAX_SIZE;
		        MAX_SIZE++; // 사진 최대 개수
		        }
	        }
		}
		
		// 사진 삭제
		if(e.getSource() == button4 && button_index > 0) {
			list.remove(button_index);
			button_index--;
			if(button_index == 0 && MAX_SIZE > 2) button_index = 1; // 사진은 여러장인데, 첫 번째 사진을 지우면 이미지 없음사진이 나오는거 방지
			MAX_SIZE--;			
			flag2 = true;
			System.out.println(button_index + 1 + "번째 사진을 삭제 했습니다.");
			if(MAX_SIZE == 1) System.out.println("사진을 전부 삭제해서 사진이 없습니다.");
		}
		
		// 행동
		try {
			if(flag == true)
				img = ImageIO.read(new File(list.get(button_index).toString())); //이미지 있으면 가져오기
			if(flag2 == true)
				img = ImageIO.read(new File(list.get(button_index).toString()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// 의미있는 행동인 경우에만 페이지수를 나타냄.
		if(flag == true || flag2 == true) {
			System.out.println("현재 페이지: " + button_index);
			System.out.println("");
		}
		
		//마지막 초기화
		flag = false;
		flag2 = false;
		imgPanel.repaint();
	}
	
	// image폴더가 없으면 image폴더를 만들고 미리 만들어둔 image_set폴더에 있는 이미지 2개를 복사.
	public void mkDir() {
		String path = "image"; //폴더 경로
		File Folder = new File(path);

		// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
		if (!Folder.exists()) {
			try{
			    Folder.mkdir(); //폴더 생성합니다.
			    System.out.println("폴더가 생성되었습니다.");
		        copyFile("image_set\\", "0.jpg"); //이미지 파일 집어넣기
		        copyFile("image_set\\", "1.jpg"); //이미지 파일 집어넣기
		    } catch(Exception e) {
		    	e.getStackTrace();
			}        
	     }
	}
	
	// 초기 앨범 기본 파일 복사
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
	}

	// 삽입 버튼을 이용한 파일 복사
	public void copyFile(File Path, String imageName) {
        //복사될 파일경로
        String copyFilePath = "image\\" + imageName;
        //복사파일객체생성
        File copyFile = new File(copyFilePath);
        System.out.println(copyFilePath + "사진을 복사했습니다.");
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
https://programmingsummaries.tistory.com/61    GUI 구현
https://raccoonjy.tistory.com/17
*/