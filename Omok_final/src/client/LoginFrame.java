package client;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;



import java.net.*;

/*
 * 로그인 창 관련 클래스
 * 로그인관련 frame, button 등
 */

public class LoginFrame extends JFrame{
	private static final int FRAME_WIDTH = 750;
	private static final int FRAME_HEIGHT = 580;
	private Login login = new Login();
	//private Image background=new ImageIcon(LoginFrame.class.getResource("../image/omok.png")).getImage();

	public LoginFrame() {
		
		super("Login");
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		setLayout(null);
		setLocationRelativeTo(null);//화면 가운데 정렬
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		

		//Frame 내부 배치 설정
		containerSetup();
		
		//창 보이기
		setVisible(true);
		
	}
	
	/*public void paint(Graphics g)
	{
		super.paint(g);
		g.drawImage(background, 0, 0, null);
	}*/
	
	//------------------------------
	//Frame 내부 배치 설정
	//------------------------------
	private void containerSetup() {
		Container mainCon = this.getContentPane();
		JFrame thisFrame = this;
		mainCon.setLayout(null);
		
		JPanel p=new JPanel();
		
		p.setBounds(0,0,FRAME_WIDTH,FRAME_HEIGHT);
		p.setBackground(Color.BLACK);
		p.setLayout(null);
		

		JLabel title=new JLabel("OMOK GAME",JLabel.CENTER);
		title.setBounds(175,30 , 400, 400);
		title.setFont(new Font("Serif",Font.BOLD, 50));
		title.setForeground(Color.WHITE);
		//------------------------------
		//ID 입력부분
		//------------------------------
		JTextField id = new JTextField();
		id.setBounds(230, 300, 300, 40);
		
		JLabel Idl=new JLabel("ID : ",JLabel.CENTER);
		Idl.setBounds(130,300 , 100, 40);
		Idl.setFont(new Font("Serif",Font.BOLD, 20));
		Idl.setForeground(Color.WHITE);
		
		//------------------------------
		//비밀번호 입력부분
		//------------------------------
		JPasswordField password = new JPasswordField();
		
		password.setBounds(230, 350, 300, 40);
		
		JLabel Pwl=new JLabel("PW : ",JLabel.CENTER);
		Pwl.setBounds(130,350 , 100, 40);
		Pwl.setFont(new Font("Serif",Font.BOLD, 20));
		Pwl.setForeground(Color.WHITE);
		//------------------------------
		//로그인 버튼
		//------------------------------
		JButton loginBtn = new JButton("Login");
		loginBtn.setBounds(230, 400, 100, 40);
		loginBtn.setBackground(Color.BLACK);
		loginBtn.setForeground(Color.WHITE);
		loginBtn.setBorderPainted(false);
		
		//로그인 버튼을 클릭했을 때
		ActionListener loginListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login.checkLoginInfo(thisFrame, id.getText(), password.getPassword());//id, 비밀번호 확인
			}
		};
		loginBtn.addActionListener(loginListener);
		
		//------------------------------
		//회원가입 버튼
		//------------------------------
		JButton signUpBtn = new JButton("Join");
		signUpBtn.setBounds(430, 400, 100, 40);
		signUpBtn.setBackground(Color.BLACK);
		signUpBtn.setForeground(Color.WHITE);
		signUpBtn.setBorderPainted(false);
		
		//회원가입 버튼을 클릭했을 때
		ActionListener signUpListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					new SignUpFrame();//회원가입 창 열기
			}		
		};
		signUpBtn.addActionListener(signUpListener);
		
		p.add(title);
		p.add(id);
		p.add(Idl);
		p.add(password);
		p.add(Pwl);
		p.add(loginBtn);
		p.add(signUpBtn);
		
		
		this.add(p);
		//this.add(id);
		//this.add(password);
		//this.add(loginBtn);
		//this.add(signUpBtn);
		
	
	}
	
}
