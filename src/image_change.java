import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.Vector;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
/* 	1. pair<int,string> 이용해서 (index, 사진 이름) 저장
 	2. 폴더에 사진 추가하기
	3. 사진 삭제
	4. 사진 검색
*/
public class image_change extends JFrame implements ActionListener {
	private BufferedImage img; //화면 이미지 출력
	private JButton button1, button2, button3, button4, button5; //다음 버튼, 이전 버튼, 사진 추가, 사진 삭제, 사진 검색
	private JPanel imgPanel; // 이미지 나오는 창
	private int button_index = 0; // 이미지 인덱스(1 ~ 사진 최대 개수, 0은 빈공간)
	private int MAX_SIZE = 2, imageCnt = 1; //이미지 최대 개수, 이미지 추가 개수
	private boolean flag = false, flag2 = false, flag3 = false; // (다음, 이전) , 사진 추가, 사진 삭제
	private String imageName; // 추가된 사진 이름
	
	Vector<String> list = new Vector<String>();

	JFileChooser fc;
	public image_change() {
		fc = new JFileChooser(); //폴더 파일 
		fc.setMultiSelectionEnabled(true);
		JPanel panel = new JPanel(); // 버튼들

		setTitle("조민서 앨범");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false); // 창 조정 x

		imgPanel = new ChangeImagePanel();
		
		// 초기 상태
		list.add("image\\0.jpg"); // 인덱스 1 부터 시작해서 없으면 이미지 없음(버튼으로 못봄, 모든사진을 삭제해서 사진이 하나도 없으면 나옴)
		list.add("image\\1.jpg");
		list.add("image\\2.jpg");
		
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
		if(e.getSource() == button1 && button_index < MAX_SIZE) { // 사진 개수가 MAX면 button_index가 안늘어남.
			button_index++;
			flag = true; // 다음 		
		// 이전
		} else if(e.getSource() == button2 && button_index > 1) { // 사진 개수가 1보다 작으면 button_index가 안내려감. 사진 다 지우면 button_index == 0되서, 이미지 없음 나옴.
			button_index--;
			flag = true; // 이전
		}
		
		//사진 삽입
		if(e.getSource() == button3) {
			// jpg, png가 디폴트값
			FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "png", "jpg", "png");
		    // 위 내용 적용
		    fc.setFileFilter(filter);
		    // 다이어로그 생성
		    int Dialog = fc.showOpenDialog(this);
		    // 예 확인시
		    if(Dialog == JFileChooser.APPROVE_OPTION) {
		      	flag2 = true;
		        // 파일 선택
		      	File[] f = fc.getSelectedFiles();
		        for(File n : f) {
		        System.out.println(imageCnt + "번째 추가한 사진 이름: " + n.getName());
		        button_index = MAX_SIZE;
		        imageCnt++; //추가 된 사진 개수
		        imageName = n.getName();
		        list.add(new String("image\\" + imageName));
		        button_index++;
		        MAX_SIZE++; // 사진 최대 개수
		        }
	        }
		}
		
		// 사진 삭제
		if(e.getSource() == button4 && button_index > 0) {
			list.remove(button_index);
			if(button_index == 1) {
				button_index = MAX_SIZE; //사진이 2개이상인데, 1번에서 삭제하면 이미지 없음이 나옴. => 방지
			}
			button_index--;			
			MAX_SIZE--;
			flag = true;
			System.out.print(button_index);
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
		
		//마지막 초기화
		flag = false;
		flag2 = false;
		imgPanel.repaint();
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