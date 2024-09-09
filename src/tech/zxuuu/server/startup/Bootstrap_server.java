package tech.zxuuu.server.startup;

import java.awt.EventQueue;
import tech.zxuuu.server.main.App;
import tech.zxuuu.util.SwingUtils;


public class Bootstrap_server {

	public static void main(String[] args) {

		System.out.println("Server started.");
		
		try {
			javax.swing.UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
		} catch (Exception e) {
			SwingUtils.showError(null, "主题加载失败。", "错误");
		}

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					App frame = new App();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
