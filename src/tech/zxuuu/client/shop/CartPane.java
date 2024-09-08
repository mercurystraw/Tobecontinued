package tech.zxuuu.client.shop;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import tech.zxuuu.entity.Product;
import tech.zxuuu.net.Request;
import tech.zxuuu.client.main.App;
import tech.zxuuu.client.main.AppStudent;
import tech.zxuuu.util.ResponseUtils;
import tech.zxuuu.util.SwingUtils;
import tech.zxuuu.client.rounded.*;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import java.awt.Color;

import javax.swing.border.LineBorder;

import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * 购物车面板
 *
 * @author z0gSh1u
 * @author 杨鹏杰
 */
public class CartPane extends JPanel {
	private JTable table;
	private DefaultTableModel model;
	public JTextArea txtSumDisp;
	String[] head = { "商品", "价格", "数量" };
	float sum = 0;

	/**
	 * Create the panel.
	 */
	public void requireReRender() {
		sum = 0;
		model.setRowCount(0);
		for (Product product : ShopFirstPage.cart) {
			Object[] toAdd = { product.getName(), product.getPrice(), product.getNumber() };
			model.addRow(toAdd);
			sum += product.getPrice() * product.getNumber();
		}
		String str = String.valueOf(sum);
		txtSumDisp.setText(str);
	}

	public CartPane() {

		setBackground(new Color(135, 206, 250));
		setOpaque(true);
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		model = new DefaultTableModel(null, head) {
			@Override
			public boolean isCellEditable(int a, int b) {
				return false;
			}
		};

		JPanel that = this;

		JLabel lblCartIcon = new JLabel("购物车");
		lblCartIcon.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblCartIcon.setIcon(new ImageIcon(CartPane.class.getResource("/resources/assets/icon/cart2.png")));
		lblCartIcon.setBounds(14, 13, 106, 48);
		add(lblCartIcon);

		JLabel lblExit = new JLabel("");
		lblExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				that.setVisible(false);
			}
		});
		lblExit.setIcon(new ImageIcon(CartPane.class.getResource("/resources/assets/icon/crossSign.png")));
		lblExit.setBounds(324, 23, 32, 32);
		add(lblExit);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 74, 342, 344);
		add(scrollPane);

		txtSumDisp = new JTextArea();
		txtSumDisp.setEditable(false);
		txtSumDisp.setFont(new Font("微软雅黑", Font.PLAIN, 17));
		txtSumDisp.setBounds(90, 441, 91, 41);

		add(txtSumDisp);

		JLabel lblSum = new JLabel("共计：");
		lblSum.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		lblSum.setHorizontalAlignment(SwingConstants.CENTER);
		lblSum.setBounds(14, 431, 91, 41);
		add(lblSum);

		table = new JTable();
		// 双击删除一行
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint());
					ShopFirstPage.cart.remove(row);
					requireReRender();
				}
			}
		});
		scrollPane.setViewportView(table);

		JButton btnCheckout = new RoundedButton("校园卡结算",10);
		btnCheckout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String checkoutHelper = "";
				for (Product ele : ShopFirstPage.cart) {
					checkoutHelper += ele.getName();
					checkoutHelper += "@";
					checkoutHelper += ele.getNumber();
					checkoutHelper += "@";
					checkoutHelper += ele.getPrice();
					checkoutHelper += "$";
					// 百事可乐@2@3.0$可口可乐@3@3.5$
				}
				Integer ret = ResponseUtils
						.getResponseByHash(new Request(App.connectionToServer, null, "tech.zxuuu.server.shop.Addons.checkout",
								new Object[] { App.session.getStudent().getCardNumber(), checkoutHelper }).send())
						.getReturn(Integer.class);
				if (ret.equals(-1)) {
					SwingUtils.showError(null, "非法请求！", "错误");
					ShopFirstPage.cart.clear();
					requireReRender();
					ShopFirstPage.lblCartCount.setText("0");
				} else if (ret.equals(0)) {
					SwingUtils.showMessage(null, "消费成功！", "提示");
					ShopFirstPage.cart.clear();
					requireReRender();
					ShopFirstPage.lblCartCount.setText("0");
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							AppStudent.lblBalance
									.setText(String.format("%.2f", Double.parseDouble(AppStudent.lblBalance.getText()) - sum));
						}
					});
				} else if (ret.equals(1)) {
					SwingUtils.showError(null, "一卡通余额不足！", "错误");
				} else if (ret.equals(2)) {
					SwingUtils.showError(null, "商品库存不足！", "错误");
				}
			}
		});
		btnCheckout.setBounds(213, 431, 143, 41);
		btnCheckout.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		add(btnCheckout);

		table.setModel(model);

		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(CartPane.class.getResource("/resources/assets/picture/white.png")));
		lblNewLabel_2.setBounds(0, 0, 370, 486);
		add(lblNewLabel_2);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(111, 111, 111));
		panel.setBounds(170, 13, 111, 48);
		add(panel);

	}
}
