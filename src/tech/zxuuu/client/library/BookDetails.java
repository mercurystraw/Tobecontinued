package tech.zxuuu.client.library;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import tech.zxuuu.client.main.App;
import tech.zxuuu.entity.Book;
import tech.zxuuu.net.Request;
import tech.zxuuu.util.ResponseUtils;
import tech.zxuuu.util.SwingUtils;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

/**
 * 书籍详情窗口
 * 
 * @author 曾铖
 * @modify z0gSh1u
 */
public class BookDetails extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public JTable tblRecommand;
	private DefaultTableModel model;
	public JTextField txtCategory;
	public String title;
	public String ISBN;
	private List<Book> list = null;

	JTextArea txtDetails = null;

	/**
	 * Create the dialog.
	 */
	public BookDetails(String title, String ISBN, String category, String details) {
		setTitle("图书详情 - " + title + " - VCampus");
		setIconImage(Toolkit.getDefaultToolkit().getImage(BookDetails.class.getResource("/resources/assets/icon/fav.png")));
		setResizable(false);
		setBounds(100, 100, 680, 550);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 432, 1);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		JLabel lblCategory = new JLabel("分类");
		lblCategory.setIcon(new ImageIcon(BookDetails.class.getResource("/resources/assets/icon/fenlei.png")));
		lblCategory.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		lblCategory.setBounds(14, 25, 100, 32); // 调整宽度以适应文本
		getContentPane().add(lblCategory);

		JLabel lblDetails = new JLabel("图书简介");
		lblDetails.setIcon(new ImageIcon(BookDetails.class.getResource("/resources/assets/icon/jianjie.png")));
		lblDetails.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		lblDetails.setBounds(14, 78, 150, 32); // 调整宽度以适应文本
		getContentPane().add(lblDetails);

		String[] head = { "藏书号", "书名", "作者", "被借次数" };
		model = new DefaultTableModel(null, head);

		tblRecommand = new JTable();
		tblRecommand.setModel(model);
		tblRecommand.setBounds(2, 2, 225, 105);
		JScrollPane jsp = new JScrollPane(tblRecommand);
		jsp.setBounds(14, 336, 624, 154);
		getContentPane().add(jsp);

		txtCategory = new JTextField();
		txtCategory.setEditable(false);
		txtCategory.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		txtCategory.setBounds(140, 22, 119, 40);
		getContentPane().add(txtCategory);
		txtCategory.setColumns(10);
		this.txtCategory.setText(category);
		list = ResponseUtils.getResponseByHash(new Request(App.connectionToServer, null,
				"tech.zxuuu.server.library.BookServer.searchSimilarBook", new Object[] { title, txtCategory.getText() }).send())
				.getListReturn(Book.class);

		model.setRowCount(0);
		String[][] listData = null;
		if (list == null) {
			listData = new String[1][4];
			listData[0][0] = "未找到相关书籍...";
			listData[0][1] = listData[0][2] = listData[0][3] = "";
		} else {
			listData = new String[list.size()][4];
			for (int i = 0; i < list.size(); i++) {
				listData[i][0] = list.get(i).getISBN();
				listData[i][1] = list.get(i).getTitle();
				listData[i][2] = list.get(i).getAuthor();
				listData[i][3] = String.valueOf(list.get(i).getNumofborrowed());
			}
		}
		model = new DefaultTableModel(listData, head) {
			@Override
			public boolean isCellEditable(int a, int b) {
				return false;
			}
		};
		tblRecommand.setModel(model);


		// 使用 JLabel 和 ImageIcon 显示图片
		JLabel imageLabel = new JLabel();
		imageLabel.setBounds(453, 21, 185, 260); // 设置位置和大小
		getContentPane().add(imageLabel);

		String result = ResponseUtils
				.getResponseByHash(new Request(App.connectionToServer, null,
						"tech.zxuuu.server.library.BookServer.searchPicture", new Object[] { ISBN }).send())
				.getReturn(String.class);

		txtDetails = new JTextArea();
		txtDetails.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		txtDetails.setEditable(false);
		txtDetails.setBounds(140, 85, 264, 190);
		getContentPane().add(txtDetails);
		txtDetails.setLineWrap(true);
		txtDetails.setWrapStyleWord(true);
		this.txtDetails.setText(details);

		try {
			URL url = new URL(result);
			ImageIcon icon = new ImageIcon(url);

			// 调整图片大小以适应 JLabel
			Image image = icon.getImage();
			Image scaledImage = image.getScaledInstance(185, 260, Image.SCALE_SMOOTH);
			imageLabel.setIcon(new ImageIcon(scaledImage));
		} catch (Exception e) {
			e.printStackTrace();
			imageLabel.setText("无法加载图片");
		}

		JLabel label = new JLabel("相似图书推荐");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		label.setIcon(new ImageIcon(BookDetails.class.getResource("/resources/assets/icon/zhishiku.png")));
		label.setBounds(248, 302, 144, 32);
		getContentPane().add(label);
		this.title = title;
		this.ISBN = ISBN;

	}
}
