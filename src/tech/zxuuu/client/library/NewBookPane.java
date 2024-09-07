package tech.zxuuu.client.library;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//import com.sun.org.apache.xpath.internal.operations.Bool;

import tech.zxuuu.client.main.App;
import tech.zxuuu.client.messageQueue.ResponseQueue;
import tech.zxuuu.net.Request;
import tech.zxuuu.net.Response;
import tech.zxuuu.util.ResponseUtils;
import tech.zxuuu.util.SwingUtils;

import java.awt.Desktop;
import java.awt.Font;
import javax.swing.ImageIcon;

/**
 * 书籍添加面板
 * 
 * @author 曾铖
 * @modify z0gSh1u
 */
public class NewBookPane extends JPanel {

	private JTextField txtTitle;
	private JTextField txtauthor;
	private JLabel lbl;
	private JLabel lblAuthor;
	private JTextField txtSetISBN;
	private JLabel lblSetISBN;
	private JButton btnComfirm;
	private JLabel lblCategory;
	private JTextArea txtAreaDetails;
	private JTextField txtPictureURL;
	private JLabel label;
	private JLabel lblPx;
	JComboBox<String> combCategory;
	private JButton btnNewButton;

	/**
	 * Create the panel.
	 */
	public NewBookPane() {
		this.setLayout(null);

		txtTitle = new JTextField();
		txtTitle.setBounds(224, 102, 175, 30);
		this.add(txtTitle);
		txtTitle.setColumns(10);

		txtauthor = new JTextField();
		txtauthor.setBounds(591, 102, 175, 30);
		this.add(txtauthor);
		txtauthor.setColumns(10);

		lbl = new JLabel("标题");
		lbl.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		lbl.setBounds(46, 106, 48, 29);
		this.add(lbl);

		lblAuthor = new JLabel("作者");
		lblAuthor.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		lblAuthor.setBounds(467, 102, 48, 29);
		this.add(lblAuthor);

		txtSetISBN = new JTextField();
		txtSetISBN.setBounds(224, 153, 175, 30);
		this.add(txtSetISBN);
		txtSetISBN.setColumns(10);

		lblSetISBN = new JLabel("图书编号");
		lblSetISBN.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		lblSetISBN.setBounds(46, 153, 157, 30);
		this.add(lblSetISBN);

		btnComfirm = new JButton("确定");
		btnComfirm.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		btnComfirm.setBounds(481, 440, 124, 39);
		btnComfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean ret = ResponseUtils
						.getResponseByHash(new Request(App.connectionToServer, null, "tech.zxuuu.server.library.BookServer.addBook",
								new Object[] { txtSetISBN.getText(), txtTitle.getText(), txtauthor.getText(),
										(String) combCategory.getSelectedItem(), txtAreaDetails.getText(), txtPictureURL.getText() })
												.send())
						.getReturn(Boolean.class);
				if (ret) {
					SwingUtils.showMessage(null, "新增成功！", "提示");
				} else {
					SwingUtils.showError(null, "新增失败！", "错误");
				}
			}
		});
		this.add(btnComfirm);

		JLabel lblDetails = new JLabel("图书详情");
		lblDetails.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		lblDetails.setBounds(48, 230, 88, 30);
		this.add(lblDetails);

		combCategory = new JComboBox<String>();
		combCategory.setBounds(591, 156, 175, 30);
		combCategory.addItem("教育");
		combCategory.addItem("小说");
		combCategory.addItem("文艺");
		combCategory.addItem("社科");
		combCategory.addItem("经管");
		combCategory.addItem("科技");
		combCategory.addItem("励志");
		combCategory.addItem("体育");
		combCategory.setVisible(true);
		add(combCategory);

		lblCategory = new JLabel("分类");
		lblCategory.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		lblCategory.setBounds(467, 153, 48, 29);
		this.add(lblCategory);

		txtAreaDetails = new JTextArea();
		txtAreaDetails.setBounds(224, 230, 542, 123);
		txtAreaDetails.setLineWrap(true);
		this.add(txtAreaDetails);

		JLabel lblurl = new JLabel("封面图片URL");
		lblurl.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		lblurl.setBounds(46, 382, 157, 30);
		this.add(lblurl);

		txtPictureURL = new JTextField();
		txtPictureURL.setBounds(224, 380, 542, 35);
		this.add(txtPictureURL);
		txtPictureURL.setColumns(10);

		label = new JLabel(" 添加书籍");
		label.setIcon(new ImageIcon(NewBookPane.class.getResource("/resources/assets/icon/add.png")));
		label.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		label.setBounds(21, 20, 245, 64);
		add(label);

		lblPx = new JLabel("(185 px * 260 px)");
		lblPx.setBounds(46, 422, 144, 18);
		add(lblPx);
		
		btnNewButton = new JButton("图片外链平台");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URI("https://www.bilibili.com/video/BV1BDa2eTEzS/?spm_id_from=333.788.recommend_more_video.-1&vd_source=aae5af28fe16885b783d8a9b52561e77"));
				} catch (IOException | URISyntaxException e1) {
					SwingUtils.showError(null, "打开失败！", "错误");
				}
			}
		});
		btnNewButton.setBounds(224, 451, 123, 27);
		add(btnNewButton);
	}

}
