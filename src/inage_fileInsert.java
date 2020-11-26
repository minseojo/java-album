import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class inage_change extends JFrame implements ActionListener {
	private BufferedImage img; //화면 이미지 출력
	private JButton button1, button2, button3, button4; //다음 버튼, 이전 버튼, 사진 추가, 사진 삭제
	private JPanel imgPanel,  namePanel; // 이미지 나올 창, 사진 이름 나오는 창	
	private int button_index = 0; // 이미지 인덱스(1 ~ 사진 최대 개수, 0은 빈공간)
	private int MAX_SIZE = 3; //이미지 최대 개수
	private Checkbox text = new Checkbox("체크박스"); 
	public image_change() {
		
		JPanel panel = new JPanel();

		setTitle("사진 변경");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);

		imgPanel = new ChangeImagePanel();
		namePanel = new JPanel();
		
		// 컴포넌트 만들기
		button1 = new JButton("다음");
		button2 = new JButton("이전");
		button3 = new JButton("사진 추가");
		button4 = new JButton("사진 삭제");
		// 패널에 컴포넌트 붙이기
		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(button4);

		// 버튼 액션 작동
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		
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
	    	g.drawImage(img, 100, 100, 500, 500, null); //사진, 가로위치, 세로위치, 가로크기, 세로크기, null
	    }
	    
	    @Override
	    public Dimension getPreferredSize() {
	    	if (img == null) {
	    		return new Dimension(700,700); // 전체화면 크기
	    		} else {
	    		return new Dimension(img.getWidth(), img.getHeight());
	        }
	    }
	}
	 
	public void actionPerformed(ActionEvent e) {
		boolean flag = false;
		String imgFile = ".jpg"; // 나중에 png, jpg, 등등
		if(e.getSource() == button1 && button_index < MAX_SIZE) {
			button_index++;
			flag = true;
			System.out.print(flag); //** 숫자 체크 나중에 지움
		} else if(e.getSource() == button2 && button_index > 1) {
			button_index--;
			flag = true;
			System.out.print(button_index); //** 숫자 체크 나중에 지움
		}
		try {
			if(flag == true)
			img = ImageIO.read(new File("image\\" + button_index + imgFile)); //이미지 있으면 가져오기
		} catch (IOException e1) {
			try {
				img = ImageIO.read(new File("image\\100.jpg")); // 이미지 없으면 이미지 없음
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
https://programmingsummaries.tistory.com/61    GUI 구현
*/