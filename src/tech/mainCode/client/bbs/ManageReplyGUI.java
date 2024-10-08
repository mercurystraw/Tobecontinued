package tech.mainCode.client.bbs;

import tech.mainCode.client.main.AppLibraryManager;
import tech.mainCode.client.rounded.RoundedButton;
import tech.mainCode.entity.PostInfo;
import tech.mainCode.net.Request;
import tech.mainCode.util.ResponseUtils;
import tech.mainCode.client.main.App;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ManageReplyGUI extends JFrame {

    private JPanel contentPane;
    private JPanel pnlReplyList;
    private JScrollPane spnReplyList;
    private String postId; // 当前帖子的ID

    public ManageReplyGUI(String postId) {
        this.postId = postId;
        setResizable(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setIconImage(Toolkit.getDefaultToolkit().getImage(ManageReplyGUI.class.getResource("/resources/assets/icon/seu_icon.png")));
        setTitle("管理回复 ");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int frameWidth = 1000; // 调整界面宽度
        int frameHeight = 800; // 调整界面高度
        setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 回复列表面板
        pnlReplyList = new JPanel();
        pnlReplyList.setLayout(new BoxLayout(pnlReplyList, BoxLayout.Y_AXIS)); // 使用 BoxLayout
        spnReplyList = new JScrollPane(pnlReplyList);
        spnReplyList.setBounds(70, 350, 800, 300); // 调整滚动面板大小
        spnReplyList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        spnReplyList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        spnReplyList.setViewportView(pnlReplyList);

        contentPane.add(spnReplyList);
        // 帖子详情面板
        JPanel pnlPostDetail = new JPanel();
        pnlPostDetail.setBounds(70, 95, 800, 200);
        pnlPostDetail.setLayout(new BorderLayout());


        // 添加主帖的详细信息面板
        JPanel pnlPostInfo = new JPanel();
        pnlPostInfo.setLayout(new GridLayout(4, 1)); // 设置4行布局

        // 帖子ID
        JLabel lblPostID = new JLabel("回帖ID：");
        pnlPostInfo.add(lblPostID);

        // 帖子内容
        JTextArea txtPostContent = new JTextArea("这里是帖子的内容...");
        txtPostContent.setEditable(false);
        pnlPostInfo.add(new JScrollPane(txtPostContent));

        // 发布日期
        JLabel lblPostDate = new JLabel("发布日期：");
        pnlPostInfo.add(lblPostDate);

        // 用户ID
        JLabel lblPostUserID = new JLabel("用户ID：");
        pnlPostInfo.add(lblPostUserID);

        pnlPostDetail.add(pnlPostInfo, BorderLayout.CENTER);
        contentPane.add(pnlPostDetail);

        JLabel lblVcampus = new JLabel("管理回复");
        lblVcampus.setFont(new Font("微软雅黑", Font.PLAIN, 25));
        lblVcampus.setForeground(Color.WHITE); // 设置字体颜色为白色
        lblVcampus.setBounds(102, 27, 239, 34);
        contentPane.add(lblVcampus);

        JLabel label = new JLabel("");
        label.setIcon(new ImageIcon(AppLibraryManager.class.getResource("/resources/assets/icon/seu_icon.png")));
        label.setBounds(14, 13, 64, 64);
        contentPane.add(label);
        // 刷新按钮
        JButton btnRefresh = new RoundedButton("刷 新",10);
        btnRefresh.setBounds(450, 700, 100, 30); // 设置按钮位置和大小
        btnRefresh.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReplyList(); // 点击刷新按钮时更新回复列表
            }
        });
        contentPane.add(btnRefresh);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                showPostDetail(lblPostID, txtPostContent, lblPostDate, lblPostUserID);
                showReplyList();
            }
        });
        JLabel greenStrip = new JLabel("");
        greenStrip.setOpaque(true);
        greenStrip.setBackground(new Color(0, 100, 0)); // Green color
        greenStrip.setBounds(0, 0, 1150, 80); // Adjust the height as needed
        contentPane.add(greenStrip, Integer.valueOf(-1)); // Add to the bottom layer

    }
    private void showPostDetail(JLabel lblPostID, JTextArea txtPostContent, JLabel lblPostDate, JLabel lblPostUserID) {
        // 从数据库获取主帖子的信息
        System.out.println("主帖ID" + postId);

        PostInfo mainPost = ResponseUtils.getResponseByHash(
                        new Request(App.connectionToServer, null,
                                "tech.mainCode.server.bbs.BBSGUI.getPostById", new Object[]{postId}).send())
                .getReturn(PostInfo.class);

        if (mainPost == null) {
            JOptionPane.showMessageDialog(null, "无法获取帖子详情，请稍后再试。", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 更新UI组件内容
        lblPostID.setText("帖子ID：" + mainPost.getId());
        lblPostID.setFont(new Font("微软雅黑", Font.PLAIN, 16)); // 设置字体大小

        txtPostContent.setText(mainPost.getContent());
        txtPostContent.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16)); // 设置字体大小为16，样式为普通
        txtPostContent.setEditable(false);

        lblPostDate.setText("发布日期：" + mainPost.getDate());
        lblPostDate.setFont(new Font("微软雅黑", Font.PLAIN, 16)); // 设置字体大小

        lblPostUserID.setText("发表用户：" + mainPost.getUser_id());
        lblPostUserID.setFont(new Font("微软雅黑", Font.PLAIN, 16)); // 设置字体大小

        System.out.println("主贴ID" + mainPost.getId() + " " + mainPost.getUser_id());
    }

    // 显示回复列表
    private void showReplyList() {
        System.out.println(postId);

        List<PostInfo> replyList = ResponseUtils.getResponseByHash(
                        new Request(App.connectionToServer, null, "tech.mainCode.server.bbs.PostDetails.getChildPostList", new Object[]{postId}).send())
                .getListReturn(PostInfo.class);

        System.out.println("replylist end!");

        SwingUtilities.invokeLater(() -> {

            Collections.sort(replyList, new Comparator<PostInfo>() { // 回复按点赞数排序
                @Override
                public int compare(PostInfo post1, PostInfo post2) {
                    return Integer.compare(post2.getThumbup(), post1.getThumbup()); // 降序排序
                }
            });

            pnlReplyList.removeAll(); // 清空当前的回复列表 用于刷新
            for (PostInfo reply : replyList) {

                JPanel replyPanel = new JPanel();
                replyPanel.setLayout(new BorderLayout()); // 使用 BorderLayout 布局

                // 回复ID
                JLabel lblReplyID = new JLabel("回复ID: " + reply.getId());
                lblReplyID.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); // 添加底部边距
                lblReplyID.setAlignmentX(Component.LEFT_ALIGNMENT);
                replyPanel.add(lblReplyID, BorderLayout.NORTH);

                // 回复内容
                JTextArea txtReplyContent = new JTextArea(reply.getContent());
                txtReplyContent.setEditable(false);
                txtReplyContent.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16)); // 设置字体大小为16，样式为普通
                replyPanel.add(new JScrollPane(txtReplyContent), BorderLayout.CENTER);

                // 发表用户和时间
                JPanel userAndDatePanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.anchor = GridBagConstraints.WEST;
                gbc.insets = new Insets(0, 0, 0, 10); // 添加间隔

                JLabel lblUserID = new JLabel("用户: " + reply.getUser_id());
                userAndDatePanel.add(lblUserID, gbc);

                JLabel lblDate = new JLabel("回复于: " + reply.getDate());
                gbc.gridx = 1; // 放在第二列
                userAndDatePanel.add(lblDate, gbc);

                replyPanel.add(userAndDatePanel, BorderLayout.SOUTH);

                // 点赞数和删除按钮
                JPanel rightPanel = new JPanel();
                rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS)); // 使用 BoxLayout 垂直布局

                // 点赞数
                JLabel lblThumb = new JLabel("点赞数: " + reply.getThumbup());
                lblThumb.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); // 添加底部边距
                lblThumb.setAlignmentX(Component.RIGHT_ALIGNMENT);
                rightPanel.add(lblThumb);

                // 删除按钮
                JButton btnDelete = new RoundedButton("删 除", 10);
                btnDelete.setBackground(new Color(128, 0, 0)); // 设置按钮背景色为橙红色
                btnDelete.setFont(new Font("微软雅黑", Font.PLAIN, 16));
                btnDelete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        int option = JOptionPane.showConfirmDialog(
                                null,
                                "确定要删除这条回复吗？",
                                "确认删除",
                                JOptionPane.YES_NO_OPTION
                        );

                        // 如果用户选择“是”，则调用 deleteReply 函数
                        if (option == JOptionPane.YES_OPTION) {
                            deleteReply(reply.getId()); // 删除回复
                        }
                    }
                });
                btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        btnDelete.setBackground(new Color(158, 0, 0));
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        btnDelete.setBackground(new Color(158, 0, 0));
                    }

                    public void mouseExited(MouseEvent e) {
                        btnDelete.setBackground(new Color(128, 0, 0));
                    }

                    public void mouseReleased(MouseEvent e) {
                        btnDelete.setBackground(new Color(128, 0, 0));
                    }
                });
                rightPanel.add(btnDelete);

                replyPanel.add(rightPanel, BorderLayout.EAST);

                // 添加间隔
                JPanel spacer = new JPanel();
                spacer.setPreferredSize(new Dimension(1, 10)); // 设置间隔高度
                pnlReplyList.add(replyPanel);
                pnlReplyList.add(spacer);

                System.out.println(reply.getId() + " " + reply.getUser_id());
            }
            pnlReplyList.revalidate();
            pnlReplyList.repaint();

        });
    }

    // 删除回复
    private void deleteReply(String postId) {
        // 这里添加删除回复的逻辑
        System.out.println("删除回复ID: " + postId);
        // 例如：调用服务器端的删除回复接口

        Boolean result = ResponseUtils.getResponseByHash(
                        new Request(App.connectionToServer, null,
                                "tech.mainCode.server.bbs.DeleteReply.deleteReply", new Object[]{postId}).send()).
                getReturn(Boolean.class);
        if(result){
            JOptionPane.showMessageDialog(null, "删除回复成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }else{

            JOptionPane.showMessageDialog(null, "删除回复失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }

    }
}
