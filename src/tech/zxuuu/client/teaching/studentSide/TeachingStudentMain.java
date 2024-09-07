package tech.zxuuu.client.teaching.studentSide;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tech.zxuuu.client.main.App;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.border.LineBorder;
import java.awt.Color;

/**
 * 教务系统学生端主界面
 * 
 * @author 王志华
 * @author z0gSh1u
 */
public class TeachingStudentMain extends JFrame {

	private JPanel contentPane;

	private JPanel currentDisplay;
	private JPanel classSelectPane;
	private ScheduleTablePane scheduleTablePane;
	private JPanel defaultPanel;
	private JPanel queryScorePane;

	/**
	 * Create the frame.
	 */
	public TeachingStudentMain() {
		setResizable(false);
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(TeachingStudentMain.class.getResource("/resources/assets/icon/fav.png")));
		setTitle("教务管理系统（学生端） - VCampus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 941, 795);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("学生选课");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentDisplay.setVisible(false);
				classSelectPane.setVisible(true);
				currentDisplay = classSelectPane;
			}
		});
		btnNewButton.setIcon(new ImageIcon(TeachingStudentMain.class.getResource("/resources/assets/icon/xk.png")));
		btnNewButton.setBounds(484, 20, 145, 57);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("查看课表");
		btnNewButton_1.setIcon(new ImageIcon(TeachingStudentMain.class.getResource("/resources/assets/icon/table.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentDisplay.setVisible(false);
//				scheduleTablePane.setVisible(true);
//				currentDisplay = scheduleTablePane;

				scheduleTablePane.setVisible(false);
				contentPane.remove(scheduleTablePane);
				scheduleTablePane = new ScheduleTablePane();
				scheduleTablePane.setVisible(true);
				scheduleTablePane.setBounds(24, 90, 889, 683);
				scheduleTablePane.setBorder(new LineBorder(new Color(0, 0, 0)));
				contentPane.add(scheduleTablePane);
				currentDisplay = scheduleTablePane;
			}
		});
		btnNewButton_1.setBounds(634, 20, 145, 57);
		contentPane.add(btnNewButton_1);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(TeachingStudentMain.class.getResource("/resources/assets/icon/doctor.png")));
		label.setBounds(24, 13, 64, 64);
		contentPane.add(label);

		JLabel label_1 = new JLabel("教务管理 - VCampus");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 25));
		label_1.setBounds(112, 32, 239, 34);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("当前登录卡号：");
		label_2.setBounds(365, 29, 105, 18);
		contentPane.add(label_2);

		JLabel label_3 = new JLabel("");
		label_3.setBounds(365, 48, 114, 18);
		contentPane.add(label_3);
		label_3.setText(App.session.getStudent().getCardNumber());

		defaultPanel = new JPanel();
		defaultPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		defaultPanel.setBounds(24, 90, 889, 617);
		contentPane.add(defaultPanel);
		defaultPanel.setLayout(null);
		JLabel label_4 = new JLabel("选择一项功能以开始...");
		label_4.setBounds(363, 211, 159, 18);
		defaultPanel.add(label_4);

		currentDisplay = defaultPanel;

		classSelectPane = new ClassSelectPane();
		classSelectPane.setBounds(24, 90, 889, 683);
		classSelectPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(classSelectPane);
		classSelectPane.setVisible(false);

		queryScorePane = new QueryScorePane();
		queryScorePane.setBounds(24, 90, 889, 683);
		queryScorePane.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(queryScorePane);
		queryScorePane.setVisible(false);

		scheduleTablePane = new ScheduleTablePane();
		scheduleTablePane.setBounds(24, 90, 889, 683);
		scheduleTablePane.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(scheduleTablePane);

		JButton btnQueryScore = new JButton("成绩查询");
		btnQueryScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentDisplay.setVisible(false);
				queryScorePane.setVisible(true);
				currentDisplay = queryScorePane;
			}
		});
		btnQueryScore.setBounds(782, 20, 145, 57);
		contentPane.add(btnQueryScore);
		btnQueryScore.setIcon(new ImageIcon(TeachingStudentMain.class.getResource("/resources/assets/icon/cj.png")));
		scheduleTablePane.setVisible(false);

	}
}
