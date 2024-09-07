package tech.zxuuu.client.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.border.TitledBorder;

import tech.zxuuu.client.studentManage.InManagePane;
import tech.zxuuu.client.studentManage.OutManagePane;
import tech.zxuuu.client.studentManage.ResetPasswordPane;
import tech.zxuuu.client.studentManage.StudentTablePane;
import tech.zxuuu.client.studentManage.SwitchManagePane;
import tech.zxuuu.client.teaching.managerSide.CourseListPane;
import tech.zxuuu.client.teaching.managerSide.DeleteCoursePane;
import tech.zxuuu.client.teaching.managerSide.NewCoursePane;
import tech.zxuuu.client.teaching.managerSide.NewNoticePane;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;

/**
 * 教务管理员界面
 * 
 * @author z0gSh1u
 */
public class AppTeachingManager extends JFrame {

	private JPanel contentPane;

	private JPanel defaultDisplayPane;
	private JPanel currentDisplayPane;

	/* 学生管理相关Panel */
	private JPanel studentTablePane;
	private JPanel outManagePane;
	private JPanel inManagePane;
	private JPanel switchManagePane;
	private JPanel resetPasswordPane;
	/* 课程管理相关Panel */
	private JPanel newCoursePane;
	private JPanel courseListPane;
	private JPanel deleteCoursePane;
	/* 通知发布Panel */
	private JPanel newNoticePane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					AppTeachingManager frame = new AppTeachingManager();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AppTeachingManager() {
		setTitle("教务管理 - VCampus");
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(AppTeachingManager.class.getResource("/resources/assets/icon/fav.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel studentManageButtonGroup = new JPanel();
		studentManageButtonGroup.setBorder(
				new TitledBorder(null, "\u5B66\u751F\u7BA1\u7406", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		studentManageButtonGroup.setBounds(14, 86, 182, 382);
		contentPane.add(studentManageButtonGroup);
		studentManageButtonGroup.setLayout(null);

		JButton btnIn = new JButton("入学");
		btnIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentDisplayPane.setVisible(false);
				inManagePane.setVisible(true);
				currentDisplayPane = inManagePane;
			}
		});
		btnIn.setIcon(new ImageIcon(AppTeachingManager.class.getResource("/resources/assets/icon/jinru.png")));
		btnIn.setBounds(14, 30, 147, 57);
		studentManageButtonGroup.add(btnIn);

		JButton btnOut = new JButton("退学");
		btnOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentDisplayPane.setVisible(false);
				outManagePane.setVisible(true);
				currentDisplayPane = outManagePane;
			}
		});
		btnOut.setIcon(new ImageIcon(AppTeachingManager.class.getResource("/resources/assets/icon/tuichu.png")));
		btnOut.setBounds(14, 170, 147, 57);
		studentManageButtonGroup.add(btnOut);

		JButton btnSwitch = new JButton("转系");
		btnSwitch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentDisplayPane.setVisible(false);
				switchManagePane.setVisible(true);
				currentDisplayPane = switchManagePane;
			}
		});
		btnSwitch.setIcon(new ImageIcon(AppTeachingManager.class.getResource("/resources/assets/icon/zhuanhuan.png")));
		btnSwitch.setBounds(14, 100, 147, 57);
		studentManageButtonGroup.add(btnSwitch);

		JButton btnList = new JButton("列表");
		btnList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentDisplayPane.setVisible(false);
				studentTablePane.setVisible(true);
				currentDisplayPane = studentTablePane;
			}
		});
		btnList.setIcon(new ImageIcon(AppTeachingManager.class.getResource("/resources/assets/icon/liebiao.png")));
		btnList.setBounds(14, 240, 147, 57);
		studentManageButtonGroup.add(btnList);

		JButton btnResetPW = new JButton("改密");
		btnResetPW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentDisplayPane.setVisible(false);
				resetPasswordPane.setVisible(true);
				currentDisplayPane = resetPasswordPane;
			}
		});
		btnResetPW.setIcon(new ImageIcon(AppTeachingManager.class.getResource("/resources/assets/icon/keyicon.png")));
		btnResetPW.setBounds(14, 310, 147, 57);
		studentManageButtonGroup.add(btnResetPW);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(AppTeachingManager.class.getResource("/resources/assets/icon/doctor.png")));
		lblNewLabel.setBounds(14, 9, 64, 64);
		contentPane.add(lblNewLabel);

		JLabel lblVcampus = new JLabel("教务管理 - VCampus");
		lblVcampus.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		lblVcampus.setBounds(102, 28, 239, 34);
		contentPane.add(lblVcampus);

		JLabel lblNewLabel_1 = new JLabel("当前登录卡号：");
		lblNewLabel_1.setBounds(355, 43, 105, 18);
		contentPane.add(lblNewLabel_1);

		JLabel lblCardNumber = new JLabel("");
		lblCardNumber.setBounds(463, 43, 114, 18);
		contentPane.add(lblCardNumber);

		// TODO Release this:
		lblCardNumber.setText(App.session.getManager().getCardNumber());

		/* 默认Panel */
		defaultDisplayPane = new JPanel();
		defaultDisplayPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		defaultDisplayPane.setBounds(225, 86, 732, 654);
		contentPane.add(defaultDisplayPane);
		defaultDisplayPane.setLayout(null);
		currentDisplayPane = defaultDisplayPane;

		JLabel lblNewLabel_2 = new JLabel("选择左侧功能开始...");
		lblNewLabel_2.setBounds(274, 311, 144, 18);
		defaultDisplayPane.add(lblNewLabel_2);

		/* 初始化其他所需Panel */
		studentTablePane = new StudentTablePane();
		studentTablePane.setBounds(225, 86, 732, 654);
		contentPane.add(studentTablePane);
		studentTablePane.setVisible(false);

		outManagePane = new OutManagePane();
		outManagePane.setBounds(225, 86, 732, 654);
		contentPane.add(outManagePane);
		outManagePane.setVisible(false);

		inManagePane = new InManagePane();
		inManagePane.setBounds(225, 86, 732, 654);
		contentPane.add(inManagePane);
		inManagePane.setVisible(false);

		switchManagePane = new SwitchManagePane();
		switchManagePane.setBounds(225, 86, 732, 654);
		contentPane.add(switchManagePane);
		switchManagePane.setVisible(false);

		newCoursePane = new NewCoursePane();
		newCoursePane.setBounds(225, 86, 732, 654);
		contentPane.add(newCoursePane);
		newCoursePane.setVisible(false);

		courseListPane = new CourseListPane();
		courseListPane.setBounds(225, 86, 732, 654);
		contentPane.add(courseListPane);
		courseListPane.setVisible(false);

		deleteCoursePane = new DeleteCoursePane();
		deleteCoursePane.setBounds(225, 86, 732, 654);
		contentPane.add(deleteCoursePane);
		deleteCoursePane.setVisible(false);

		resetPasswordPane = new ResetPasswordPane();
		resetPasswordPane.setBounds(225, 86, 732, 654);
		contentPane.add(resetPasswordPane);
		resetPasswordPane.setVisible(false);
		
		newNoticePane = new NewNoticePane();
		newNoticePane.setBounds(225, 86, 732, 654);
		contentPane.add(newNoticePane);
		newNoticePane.setVisible(false);

		JPanel panel = new JPanel();
		panel.setBorder(
				new TitledBorder(null, "\u8BFE\u7A0B\u7BA1\u7406", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(14, 481, 182, 259);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton button = new JButton("列表");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentDisplayPane.setVisible(false);
				courseListPane.setVisible(true);
				currentDisplayPane = courseListPane;
			}
		});
		button.setIcon(new ImageIcon(AppTeachingManager.class.getResource("/resources/assets/icon/liebiao.png")));
		button.setBounds(14, 184, 147, 57);
		panel.add(button);

		JButton button_1 = new JButton("增加");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentDisplayPane.setVisible(false);
				newCoursePane.setVisible(true);
				currentDisplayPane = newCoursePane;
			}
		});
		button_1.setIcon(new ImageIcon(AppTeachingManager.class.getResource("/resources/assets/icon/add.png")));
		button_1.setBounds(14, 34, 147, 57);
		panel.add(button_1);

		JButton button_2 = new JButton("删除");
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentDisplayPane.setVisible(false);
				deleteCoursePane.setVisible(true);
				currentDisplayPane = deleteCoursePane;

			}
		});
		button_2.setIcon(new ImageIcon(AppTeachingManager.class.getResource("/resources/assets/icon/delete.png")));
		button_2.setBounds(14, 109, 147, 57);
		panel.add(button_2);
		
		JButton btnNewButton = new JButton("发布新通知");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentDisplayPane.setVisible(false);
				newNoticePane.setVisible(true);
				currentDisplayPane = newNoticePane;
			}
		});
		btnNewButton.setIcon(new ImageIcon(AppTeachingManager.class.getResource("/resources/assets/icon/统计.png")));
		btnNewButton.setBounds(798, 16, 159, 57);
		contentPane.add(btnNewButton);
		switchManagePane.setVisible(false);

	}
}
