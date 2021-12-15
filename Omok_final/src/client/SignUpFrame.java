package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/*
 * 회원가입 창 관련 클래스
 */

public class SignUpFrame extends JFrame{
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 500;
	public JTextField Id,PW,Name,NickName,Email,SNS;
	public JLabel Id_lb,Pw_lb,Name_lb,NickName_lb,Email_lb,SNS_lb;
	public JButton SignUpBtn;
	private SignUp signup = new SignUp();
	
	public SignUpFrame() {
		super("Sign Up");
		
		//Frame 기본 설정
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);//화면 가운데 정렬
		
		//Frame 내부 배치 설정
		containerSetup();
		
		//창 보이기
		setVisible(true);
	}
	
	//------------------------------
	//Frame 내부 배치 설정
	//------------------------------
	private void containerSetup() {
		Container mainCon = this.getContentPane();
		JFrame thisFrame = this;
		mainCon.setLayout(null);
		
		Id=new JTextField();
		Id.setBounds(70,50,300,40);
		Id_lb=new JLabel("ID : ");
		Id_lb.setBounds(5, 50, 300, 40);
		
		PW=new JTextField();
		PW.setBounds(70,100,300,40);
		Pw_lb=new JLabel("PW : ");
		Pw_lb.setBounds(5, 100, 300, 40);
		
		Name=new JTextField();
		Name.setBounds(70,150,300,40);
		Name_lb=new JLabel("Name : ");
		Name_lb.setBounds(5, 150, 300, 40);
		
		NickName=new JTextField();
		NickName.setBounds(70,200,300,40);
		NickName_lb=new JLabel("NickName : ");
		NickName_lb.setBounds(5, 200, 300, 40);
		
		Email=new JTextField();
		Email.setBounds(70,250,300,40);
		Email_lb=new JLabel("Email : ");
		Email_lb.setBounds(5, 250, 300, 40);
		
		SNS=new JTextField();
		SNS.setBounds(70,300,300,40);
		SNS_lb=new JLabel("SNS : ");
		SNS_lb.setBounds(5, 300, 300, 40);
		
		
		SignUpBtn = new JButton("회원가입");
		SignUpBtn.setBounds(70, 350, 100, 40);
		
		
		ActionListener SignUpListener2 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("what??");
				signup.SignUp_pro(thisFrame,1,Id.getText(), PW.getText(),Name.getText(),NickName.getText(),Email.getText(),SNS.getText());
			}
		};
		SignUpBtn.addActionListener(SignUpListener2);
		this.add(Id);
		this.add(Id_lb);
		this.add(PW);
		this.add(Pw_lb);
		this.add(Name);
		this.add(Name_lb);
		this.add(NickName);
		this.add(NickName_lb);
		this.add(Email);
		this.add(Email_lb);
		this.add(SNS);
		this.add(SNS_lb);
		this.add(SignUpBtn);
		
	}
}
