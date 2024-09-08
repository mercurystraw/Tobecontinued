package src.tech.zxuuu.client.opencourse;

import javax.swing.JPanel;

import tech.zxuuu.client.opencourse.StuCourseGUI;
import tech.zxuuu.util.SwingUtils;

import javax.swing.JEditorPane;
import javax.swing.JFrame;

import javax.swing.JLabel;
import java.awt.*;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * 公开课课程信息Block
 * 
 * @author LongChen
 */
public class CourseInfoPane extends JPanel {


	public static final int HEIGHT = 130;

	private int id; // 课程ID
	private String preview; // 预览图链接
	private String courseName; // 课程名称
	private String speaker; // 主讲人名称

	private JEditorPane edpPreview;

	private JLabel lblCourseName;
	private JLabel lblSpeaker;
	private JLabel lblPreview;
	public String videoUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSpeaker() {
		return speaker;
	}

	public void setSpeaker(String speaker) {
		this.speaker = speaker;
	}

	/**
	 * Create the panel.
	 */
	public CourseInfoPane() {

		setLayout(null);

//		this.edpPreview = new JEditorPane();
//		edpPreview.setEditable(false);
//		edpPreview.setContentType("text/html");
//		edpPreview.setBounds(0, 0, 120, 130);
//		add(edpPreview);
		this.lblPreview = new JLabel();
		lblPreview.setBounds(0, 0, 200, 200);
		add(lblPreview);

		this.lblCourseName = new JLabel("这里是课程名");
		lblCourseName.setFont(new Font("微软雅黑", Font.PLAIN, 48));
//		lblCourseName.setBounds(129, 0, 477, 58);
		lblCourseName.setBounds(220, 10, 477, 58); // 调整 x 和 y 坐标
		add(lblCourseName);

		this.lblSpeaker = new JLabel("这里是主讲人");
		lblSpeaker.setFont(new Font("宋体", Font.PLAIN, 35));
//		lblSpeaker.setBounds(135, 72, 421, 41);
		lblSpeaker.setBounds(220, 80, 421, 41); // 调整 x 和 y 坐标
		add(lblSpeaker);

		JButton btnNewButton = new JButton("进入");
		btnNewButton.setIcon(new ImageIcon(CourseInfoPane.class.getResource("/resources/assets/icon/right-circle.png")));
		btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					tech.zxuuu.client.opencourse.StuCourseGUI stuCourseGUI = new StuCourseGUI(id, videoUrl, courseName);
					stuCourseGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					stuCourseGUI.setVisible(true);
				} catch (Exception e1) {
					SwingUtils.showError(null, "视频启动失败，请检查VLC安装！", "错误");
				}
			}
		});
//		btnNewButton.setBounds(561, 59, 119, 59);
		btnNewButton.setBounds(600, 150, 119, 59); // 调整 x 和 y 坐标
		add(btnNewButton);
		
		setLayout(null);
		setVisible(true);
	}

	public CourseInfoPane(int id, String courseName, String speaker, String preview, String videoUrl) {
		this();
		this.id = id;
		this.preview = preview;
		this.courseName = courseName;
		this.speaker = speaker;
		this.videoUrl = videoUrl;
//		this.edpPreview.setText(this.preview);
//		this.lblPreview.setIcon(new ImageIcon(preview));
		// 使用 JLabel 和 ImageIcon 显示图片
		try {
			URL url = new URL(preview);
			ImageIcon icon = new ImageIcon(url);

			// 调整图片大小以适应 JLabel
			Image image = icon.getImage();
			Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			lblPreview.setIcon(new ImageIcon(scaledImage));
		} catch (Exception e) {
			e.printStackTrace();
			lblPreview.setText("无法加载图片");
		}

		this.lblCourseName.setText(this.courseName);
		this.lblSpeaker.setText(this.speaker);
		this.validate();
		this.repaint();
		this.updateUI();
		this.revalidate();
	}
}
