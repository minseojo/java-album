import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;


public class image_insert extends JFrame {
	Image img = null;
	image_insert() {
		try {
			File sourceimage = new File("D:\\image\\1.jpg");
			img = ImageIO.read(sourceimage);
		} catch (IOException e) {
			System.out.println("이미지파일이 없습니다.");
		}
		init(null);
		
		setTitle("사진 삽입");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(true);
	}
	public void init(ActionEvent e){
		JLabel label = new JLabel(new ImageIcon(img));
		
		add(label);
	}
	
	public static void main(String[] args) {
		image_insert f = new image_insert();
	}
}
