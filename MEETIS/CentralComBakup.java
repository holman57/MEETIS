import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import twitter4j.TwitterException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

import static java.lang.Thread.sleep;
import static javax.swing.BorderFactory.createEmptyBorder;

/**
 * Created by Jupiter on 1/1/2016.
 */
public class CentralCom extends JFrame {

    JPanel panel1;

    private JButton buildURLButton;
    private JButton twitterTrendsButton;
    private JButton diveURLButton;
    private JButton keywordsButton;
    private JButton xButton;
    private JButton manualEngage;
    private JButton hashTagButton;
    private JButton twitterUpdateButton;
    private JButton SQLUpdateButton;
    private JButton FULLAUTOButton;
    private JLabel meetisLabel;
    private JTextArea centralComDisplayMessage;
    private JPanel centralComMasterJPanel;
    private JScrollPane masterScrollPanel;
    private JProgressBar centralComProgressBar;
    private JButton timelineRedundancyButton;
    private JScrollPane centralComDisplayMessageScroll;
    private JTextArea outputDataArea;
    private JScrollPane outputScroll;
    private JPanel runtimeDataPanel;
    private JLabel completedCycles;
    private JLabel runtimeDuration;
    private JLabel successfulTweets;
    private JLabel sucessfulSQL;
    private JLabel failedTweets;
    private JLabel currentCycle;
    private JButton selfEngageButton;

    //-------------------------------------------------
    //---------------------------     Needed to Initialize Full Auto
    static Thread alphaOmegaThread;

    ArrayList<String> keywords = new ArrayList<>();
    ArrayList<String> keywordPhrase = new ArrayList<>();
    ArrayList<String> avoidURL = new ArrayList<>();
    ArrayList<String> bounceIMG = new ArrayList<>();
    ArrayList<String> buildPoints = new ArrayList<>();

    static boolean trendsExist = false;
    static boolean keywordsExists = false;
    static boolean bounceIMGExists = false;
    static boolean avoidURLExists = false;
    static boolean buildPointsExist = false;
    static boolean fullAutoEngaged = false;
    //-------------------------------------------------
    static boolean omegaURLbeingAccesed = false;
    static boolean alphaURLbeingAccesed = false;

    static boolean canSelfEngage = true;

    ArrayList<String> alphaURL = new ArrayList<>();
    ArrayList<internalComparison> omegaURL = new ArrayList<>();

    static boolean doneWithConnection = false;
    static boolean initUpdate = true;
    static boolean updateReady = true;
    static int position = 0;
    static int omegaURLsize = 0;
    static int cycle = 0;
    static int positiveTweets = 0;
    static int negativeTweets = 0;
    static int positiveSQL = 0;

    long startTime = System.currentTimeMillis();

    GetTwitterTrends trends;

    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int windowWidth = gd.getDisplayMode().getWidth();
    int windowHeight = gd.getDisplayMode().getHeight();

    int screenSizeAverage = ((windowHeight + windowWidth) / 110);

    Font averagedFont = new Font("Arial", Font.BOLD, screenSizeAverage);
    Font listFont = new Font("Arial", Font.BOLD, (screenSizeAverage * 3 / 5 * 5 / 4));

    public CentralCom() {
        class Task extends SwingWorker<Void, Void> {
            @Override
            public Void doInBackground() {
                ActionListener actionListener = new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            completedCycles.setText("Cycles : ( " + cycle + " )");
                            long endTime = System.currentTimeMillis();
                            long totalTime = (endTime - startTime) / 60000;
                            if (totalTime < 60) {
                                runtimeDuration.setText("Duration : " + totalTime + " min");
                            } else if (totalTime > 60 && totalTime <= 1440) {
                                runtimeDuration.setText("Duration : " + totalTime / 60 + " hours");
                            } else if (totalTime > 1440) {
                                runtimeDuration.setText("Duration : " + totalTime / 1440 + " days");
                            }
                            successfulTweets.setText("Tweets : " + positiveTweets);
                            failedTweets.setText("Failed : " + negativeTweets);
                            sucessfulSQL.setText("SQL Stmts : " + positiveSQL);
                            centralComMasterJPanel.removeAll();
                            int i = 1;
                            for (internalComparison element : omegaURL) {
                                JButton masterListButton = new JButton("( " + i++ + " ) " + element.printButtton());
                                masterListButton.setBackground(Color.black);
                                masterListButton.setForeground(Color.white);
                                masterListButton.setFont(averagedFont);
                                masterListButton.setHorizontalAlignment(SwingConstants.LEFT);
                                centralComMasterJPanel.add(masterListButton);
                                centralComMasterJPanel.updateUI();
                            }
                        } catch (Exception e) {
                        }
                        omegaURLsize = omegaURL.size();
                    }
                };
                Timer timer = new Timer(15000, actionListener);
                timer.start();
                return null;
            }
        }
        Task task = new Task();
        task.execute();

        class ClearDisplayMessage extends SwingWorker<Void, Void> {
            @Override
            public Void doInBackground() {
                ActionListener actionListener = new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        centralComDisplayMessage.setText("");
                    }
                };
                Timer timer = new Timer(300000, actionListener);
                timer.start();
                return null;
            }
        }
        ClearDisplayMessage clearDisplayMessage = new ClearDisplayMessage();
        clearDisplayMessage.execute();

        class ClearOutput extends SwingWorker<Void, Void> {
            @Override
            public Void doInBackground() {
                ActionListener actionListener = new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        outputDataArea.setText("");
                    }
                };
                Timer timer = new Timer(86400000, actionListener);
                timer.start();
                return null;
            }
        }
        ClearOutput clearOutput = new ClearOutput();
        clearOutput.execute();

        class SlefEngage extends SwingWorker<Void, Void> {
            @Override
            public Void doInBackground() {
                ActionListener actionListener = new ActionListener() {
                    public void actionPerformed(ActionEvent actionEvent) {
                        if (canSelfEngage) {
                            canSelfEngage = false;
                            selfEngageButton.setBackground(new Color(0, 0, 0));
                            FULLAUTOButton.setBackground(new Color(54, 162, 24));
                            FullAuto();
                        }
                    }
                };
                if (canSelfEngage) {
                    Timer timer = new Timer(300000, actionListener);
                    timer.start();
                }
                return null;
            }
        }
        SlefEngage slefEngage = new SlefEngage();
        slefEngage.execute();

        centralComMasterJPanel.setLayout(new GridLayout(0, 1));

        masterScrollPanel.setBackground(Color.BLACK);
        masterScrollPanel.setForeground(Color.white);
        masterScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        masterScrollPanel.getVerticalScrollBar().setBackground(Color.BLACK);
        masterScrollPanel.getVerticalScrollBar().setForeground(Color.white);
        masterScrollPanel.getVerticalScrollBar().setPreferredSize(new Dimension(50, 0));
        masterScrollPanel.getVerticalScrollBar().setBorder(BorderFactory.createEmptyBorder());
        masterScrollPanel.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors(){
                this.thumbColor = Color.white;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton jbutton = new JButton();
                jbutton.setPreferredSize(new Dimension(0, 0));
                jbutton.setMinimumSize(new Dimension(0, 0));
                jbutton.setMaximumSize(new Dimension(0, 0));
                return jbutton;
            }
        });

        outputScroll.setBackground(Color.BLACK);
        outputScroll.setForeground(Color.white);
        outputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outputScroll.getVerticalScrollBar().setBackground(Color.BLACK);
        outputScroll.getVerticalScrollBar().setForeground(Color.white);
        outputScroll.getVerticalScrollBar().setPreferredSize(new Dimension(50, 0));
        outputScroll.getVerticalScrollBar().setBorder(BorderFactory.createEmptyBorder());
        outputScroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors(){
                this.thumbColor = Color.white;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton jbutton = new JButton();
                jbutton.setPreferredSize(new Dimension(0, 0));
                jbutton.setMinimumSize(new Dimension(0, 0));
                jbutton.setMaximumSize(new Dimension(0, 0));
                return jbutton;
            }
        });

        buildURLButton.setFont(averagedFont);
        twitterTrendsButton.setFont(averagedFont);
        diveURLButton.setFont(averagedFont);
        keywordsButton.setFont(averagedFont);
        manualEngage.setFont(averagedFont);
        hashTagButton.setFont(averagedFont);
        twitterUpdateButton.setFont(averagedFont);
        SQLUpdateButton.setFont(averagedFont);
        FULLAUTOButton.setFont(averagedFont);
        timelineRedundancyButton.setFont(averagedFont);
        meetisLabel.setFont(averagedFont);
        outputDataArea.setFont(listFont);
        centralComDisplayMessage.setFont(listFont);
        completedCycles.setFont(listFont);
        runtimeDuration.setFont(listFont);
        successfulTweets.setFont(listFont);
        sucessfulSQL.setFont(listFont);
        failedTweets.setFont(listFont);
        currentCycle.setFont(listFont);

        centralComDisplayMessageScroll.setBorder(createEmptyBorder());

        centralComProgressBar.setIndeterminate(false);
        centralComProgressBar.setValue(0);

        DefaultCaret caret0 = (DefaultCaret) centralComDisplayMessage.getCaret();
        caret0.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        twitterTrendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        JTextArea textArea = new JTextArea("\n                        ( Global Twitter Trend Aggregator )");
                        Random rand = new Random();
                        int random = rand.nextInt(150) + 1;
                        JFrame frame = new JFrame("Twitter Trends");
                        frame.setPreferredSize(new Dimension((windowWidth * 3 / 5), (windowHeight * 4 / 5)));
                        frame.setLocation(200 + random, random);
                        frame.getContentPane().setBackground(Color.BLACK);
                        frame.setLayout(new BorderLayout());
                        frame.pack();
                        frame.setVisible(true);

                        JPanel northContainer = new JPanel(new GridLayout(0, 3));
                        northContainer.setPreferredSize(new Dimension(80, 80));
                        JButton getTwitterTrendsButton = new JButton("Get Trends");
                        getTwitterTrendsButton.setBackground(Color.BLACK);
                        getTwitterTrendsButton.setForeground(Color.WHITE);
                        getTwitterTrendsButton.setFont(averagedFont);
                        getTwitterTrendsButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                trends = new GetTwitterTrends(textArea);
                            }
                        });
                        JButton trimHastagButton = new JButton("Trim Hashtags #");
                        trimHastagButton.setBackground(Color.BLACK);
                        trimHastagButton.setForeground(Color.WHITE);
                        trimHastagButton.setFont(averagedFont);
                        northContainer.add(trimHastagButton);
                        trimHastagButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                SwingUtilities.invokeLater(new Runnable() {
                                    public void run() {
                                        doneWithConnection = false;
                                        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                                        int windowWidth = gd.getDisplayMode().getWidth();
                                        int windowHeight = gd.getDisplayMode().getHeight();
                                        Random rand = new Random();
                                        int random = rand.nextInt(6) + 1;
                                        JFrame frame = new JFrame("Trim Tags");
                                        frame.setPreferredSize(new Dimension((windowWidth / 2), (windowHeight / 6)));
                                        frame.setLocation((windowWidth / random), (windowHeight / random));
                                        frame.getContentPane().setBackground(Color.BLACK);
                                        frame.setLayout(new GridLayout(0, 1));
                                        frame.setUndecorated(true);
                                        frame.pack();
                                        frame.setVisible(true);
                                        JLabel trimTagStatus = new JLabel("TRIM HASTAG #");
                                        trimTagStatus.setFont(averagedFont);
                                        trimTagStatus.setBackground(Color.black);
                                        trimTagStatus.setForeground(Color.white);
                                        trimTagStatus.setHorizontalAlignment(JLabel.CENTER);
                                        JProgressBar progressBar = new JProgressBar();
                                        progressBar.setIndeterminate(true);
                                        frame.add(trimTagStatus);
                                        frame.add(progressBar);

                                        trends.trimHashTags();

                                        class Task extends SwingWorker<Void, Void> {
                                            @Override
                                            public Void doInBackground() {
                                                ActionListener actionListener = new ActionListener() {
                                                    public void actionPerformed(ActionEvent actionEvent) {
                                                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                                                        doneWithConnection = true;
                                                    }
                                                };
                                                if (!doneWithConnection) {
                                                    Timer timer = new Timer(1300, actionListener);
                                                    timer.start();
                                                }
                                                return null;
                                            }
                                        }
                                        Task task = new Task();
                                        task.execute();
                                    }
                                });
                            }
                        });

                        JButton editViewTwitterArrayButton = new JButton("Edit / View Array");
                        editViewTwitterArrayButton.setBackground(Color.BLACK);
                        editViewTwitterArrayButton.setForeground(Color.WHITE);
                        editViewTwitterArrayButton.setFont(averagedFont);
                        northContainer.add(editViewTwitterArrayButton);
                        editViewTwitterArrayButton.addActionListener(
                                new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        SwingUtilities.invokeLater(new Runnable() {
                                            public void run() {
                                                Random rand = new Random();
                                                int random = rand.nextInt(150) + 1;

                                                JFrame frame = new JFrame();
                                                frame.setPreferredSize(new Dimension((windowWidth * 2 / 3), (windowHeight * 2 / 3)));
                                                frame.setLocation(200 + random, random);
                                                frame.getContentPane().setBackground(Color.BLACK);
                                                frame.pack();
                                                frame.setVisible(true);

                                                JPanel panel = new JPanel();
                                                panel.setBackground(Color.black);
                                                panel.setLayout(new GridLayout(0, 2));

                                                try {
                                                    ArrayList<String> trendsArray = trends.getTrends();
                                                    for (String element : trendsArray) {
                                                        JButton trendButton = new JButton(element);
                                                        trendButton.setFont(listFont);
                                                        trendButton.setBackground(Color.black);
                                                        trendButton.setForeground(Color.white);
                                                        panel.add(trendButton);
                                                    }
                                                    if (!trendsArray.equals(0) & !trendsArray.equals(null)) {
                                                        trendsExist = true;
                                                    }
                                                } catch (Exception e1) {
                                                }

                                                JScrollPane scrollPane = new JScrollPane(panel);
                                                scrollPane.setBackground(Color.black);
                                                scrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
                                                scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(70, 0));
                                                frame.add(scrollPane);

                                            }
                                        });
                                    }
                                });

                        northContainer.add(getTwitterTrendsButton);
                        frame.add(northContainer, BorderLayout.NORTH);

                        textArea.setFont(averagedFont);
                        textArea.setForeground(Color.WHITE);
                        textArea.setBackground(Color.BLACK);
                        JScrollPane twitterTrendsScroll = new JScrollPane(textArea);
                        twitterTrendsScroll.getVerticalScrollBar().setBackground(Color.BLACK);
                        twitterTrendsScroll.getVerticalScrollBar().setPreferredSize(new Dimension(70, 0));
                        twitterTrendsScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                        twitterTrendsScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        frame.add(twitterTrendsScroll, BorderLayout.CENTER);

                    }
                });
            }
        });
        keywordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Random rand = new Random();
                        int random = rand.nextInt(50) + 1;
                        JFrame frame = new JFrame("Keywords");
                        frame.setPreferredSize(new Dimension((windowWidth), (windowHeight * 5 / 6)));
                        frame.setLocation(20, random);
                        frame.getContentPane().setBackground(Color.BLACK);
                        frame.setLayout(new GridLayout(0, 3));
                        frame.pack();
                        frame.setVisible(true);

                        JPanel keywordJPanel = new JPanel();
                        keywordJPanel.setBackground(Color.black);
                        keywordJPanel.setLayout(new GridLayout(0, 2));

                        JPanel avoidURLJPanel = new JPanel();
                        avoidURLJPanel.setBackground(Color.black);
                        avoidURLJPanel.setLayout(new GridLayout(0, 1));

                        JPanel bounceIMGJPanel = new JPanel();
                        bounceIMGJPanel.setBackground(Color.black);
                        bounceIMGJPanel.setLayout(new GridLayout(0, 1));

                        JScrollPane scrollPaneKeyword = new JScrollPane(keywordJPanel);
                        scrollPaneKeyword.setBackground(Color.black);
                        scrollPaneKeyword.getVerticalScrollBar().setBackground(Color.BLACK);
                        scrollPaneKeyword.getHorizontalScrollBar().setBackground(Color.BLACK);
                        scrollPaneKeyword.getVerticalScrollBar().setPreferredSize(new Dimension(50, 0));
                        scrollPaneKeyword.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 50));

                        JScrollPane scrollPaneAvoidURL = new JScrollPane(avoidURLJPanel);
                        scrollPaneAvoidURL.setBackground(Color.black);
                        scrollPaneAvoidURL.getVerticalScrollBar().setBackground(Color.BLACK);
                        scrollPaneAvoidURL.getHorizontalScrollBar().setBackground(Color.BLACK);
                        scrollPaneAvoidURL.getVerticalScrollBar().setPreferredSize(new Dimension(50, 0));
                        scrollPaneAvoidURL.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 50));

                        JScrollPane scrollPaneBounceIMG = new JScrollPane(bounceIMGJPanel);
                        scrollPaneBounceIMG.setBackground(Color.black);
                        scrollPaneBounceIMG.getVerticalScrollBar().setBackground(Color.BLACK);
                        scrollPaneBounceIMG.getVerticalScrollBar().setPreferredSize(new Dimension(50, 0));

                        JPanel leftJPanel = new JPanel(new BorderLayout());
                        JPanel middleJPanel = new JPanel(new BorderLayout());
                        JPanel rightJPanel = new JPanel(new BorderLayout());

                        JButton getKeywordButton = new JButton("Get Keywords");
                        getKeywordButton.setPreferredSize(new Dimension(80, 80));
                        getKeywordButton.setBackground(Color.black);
                        getKeywordButton.setForeground(Color.white);
                        getKeywordButton.setFont(averagedFont);

                        JButton getAvoidURLButton = new JButton("Get avoidURL");
                        getAvoidURLButton.setPreferredSize(new Dimension(80, 80));
                        getAvoidURLButton.setBackground(Color.black);
                        getAvoidURLButton.setForeground(Color.white);
                        getAvoidURLButton.setFont(averagedFont);

                        JButton getBounceIMGButton = new JButton("Get bounceIMG");
                        getBounceIMGButton.setPreferredSize(new Dimension(80, 80));
                        getBounceIMGButton.setBackground(Color.black);
                        getBounceIMGButton.setForeground(Color.white);
                        getBounceIMGButton.setFont(averagedFont);

                        leftJPanel.add(getKeywordButton, BorderLayout.NORTH);
                        leftJPanel.add(scrollPaneKeyword, BorderLayout.CENTER);

                        middleJPanel.add(getAvoidURLButton, BorderLayout.NORTH);
                        middleJPanel.add(scrollPaneAvoidURL, BorderLayout.CENTER);

                        rightJPanel.add(getBounceIMGButton, BorderLayout.NORTH);
                        rightJPanel.add(scrollPaneBounceIMG, BorderLayout.CENTER);

                        for (String element : keywords) {
                            JButton tempKey = new JButton(element);
                            tempKey.setFont(listFont);
                            tempKey.setBackground(Color.black);
                            tempKey.setForeground(Color.white);
                            keywordJPanel.add(tempKey);
                        }
                        for (String element : avoidURL) {
                            JButton tempKey = new JButton(element);
                            tempKey.setFont(listFont);
                            tempKey.setBackground(Color.black);
                            tempKey.setForeground(Color.white);
                            avoidURLJPanel.add(tempKey);
                        }
                        for (String element : bounceIMG) {
                            JButton tempKey = new JButton(element);
                            tempKey.setFont(listFont);
                            tempKey.setBackground(Color.black);
                            tempKey.setForeground(Color.white);
                            bounceIMGJPanel.add(tempKey);
                        }

                        frame.add(leftJPanel);
                        frame.add(middleJPanel);
                        frame.add(rightJPanel);

                        getKeywordButton.addActionListener(
                                new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        SwingUtilities.invokeLater(new Runnable() {
                                            public void run() {
                                                if (!keywordsExists) {
                                                    try {
                                                        URL url = new URL("http://www.lukeholman.net/meetis/keywords.txt");
                                                        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                                                        String everything = "";
                                                        String concatenate;
                                                        while ((concatenate = in.readLine()) != null) {
                                                            everything += concatenate;
                                                        }
                                                        in.close();
                                                        String[] Keywords = everything.split("\", \"");
                                                        keywords.clear();

                                                        for (String element : Keywords) {
                                                            keywords.add(element);
                                                            JButton tempKeyword = new JButton(element);
                                                            tempKeyword.setFont(listFont);
                                                            tempKeyword.setBackground(Color.black);
                                                            tempKeyword.setForeground(Color.white);
                                                            keywordJPanel.add(tempKeyword);
                                                            keywordJPanel.updateUI();
                                                        }
                                                        if (!Keywords.equals(0) & !Keywords.equals(null)) {
                                                            keywordsExists = true;
                                                        }

                                                    } catch (MalformedURLException e) {
                                                    } catch (IOException e) {
                                                    }
                                                }

                                            }
                                        });
                                    }
                                });
                        getAvoidURLButton.addActionListener(
                                new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        SwingUtilities.invokeLater(new Runnable() {
                                            public void run() {
                                                if (!avoidURLExists) {
                                                    try {
                                                        URL url = new URL("http://www.lukeholman.net/meetis/avoidURL.txt");
                                                        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                                                        String everything = "";
                                                        String concatenate;
                                                        while ((concatenate = in.readLine()) != null) {
                                                            everything += concatenate;
                                                        }
                                                        in.close();
                                                        String[] Keywords = everything.split("\", \"");
                                                        avoidURL.clear();

                                                        for (String element : Keywords) {
                                                            avoidURL.add(element);
                                                            JButton tempAvoidURL = new JButton(element);
                                                            tempAvoidURL.setFont(listFont);
                                                            tempAvoidURL.setBackground(Color.black);
                                                            tempAvoidURL.setForeground(Color.white);
                                                            avoidURLJPanel.add(tempAvoidURL);
                                                            avoidURLJPanel.updateUI();
                                                        }
                                                        if (!Keywords.equals(0) & !Keywords.equals(null)) {
                                                            avoidURLExists = true;
                                                        }

                                                    } catch (MalformedURLException e) {
                                                    } catch (IOException e) {
                                                    }
                                                }

                                            }
                                        });
                                    }
                                });
                        getBounceIMGButton.addActionListener(
                                new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        SwingUtilities.invokeLater(new Runnable() {
                                            public void run() {
                                                if (!bounceIMGExists) {
                                                    try {
                                                        URL url = new URL("http://www.lukeholman.net/meetis/bounceIMG.txt");
                                                        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                                                        String everything = "";
                                                        String concatenate;
                                                        while ((concatenate = in.readLine()) != null) {
                                                            everything += concatenate;
                                                        }
                                                        in.close();
                                                        String[] Keywords = everything.split("\", \"");
                                                        bounceIMG.clear();

                                                        for (String element : Keywords) {
                                                            bounceIMG.add(element);
                                                            JButton tempBounceIMG = new JButton(element);
                                                            tempBounceIMG.setFont(listFont);
                                                            tempBounceIMG.setBackground(Color.black);
                                                            tempBounceIMG.setForeground(Color.white);
                                                            bounceIMGJPanel.add(tempBounceIMG);
                                                            bounceIMGJPanel.updateUI();
                                                        }
                                                        if (!Keywords.equals(0) & !Keywords.equals(null)) {
                                                            bounceIMGExists = true;
                                                        }

                                                    } catch (MalformedURLException e) {
                                                    } catch (IOException e) {
                                                    }
                                                }

                                            }
                                        });
                                    }
                                });


                    }
                });
            }
        });
        buildURLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Random rand = new Random();
                        int random = rand.nextInt(50) + 1;
                        JFrame frame = new JFrame("Build URL");
                        frame.setPreferredSize(new Dimension((windowWidth * 4 / 5), (windowHeight * 8 / 9)));
                        frame.setLocation(200 + random, random);
                        frame.getContentPane().setBackground(Color.BLACK);
                        frame.setLayout(new BorderLayout());
                        frame.pack();
                        frame.setVisible(true);

                        JPanel northContainer = new JPanel(new GridLayout(2, 1));
                        northContainer.setPreferredSize(new Dimension(40, 120));

                        JPanel northMasterContainer = new JPanel(new BorderLayout());

                        JProgressBar buildURLprogressBar = new JProgressBar();
                        buildURLprogressBar.setIndeterminate(true);
                        buildURLprogressBar.setBackground(Color.black);
                        buildURLprogressBar.setForeground(Color.white);
                        buildURLprogressBar.setPreferredSize(new Dimension(27, 27));
                        buildURLprogressBar.setBorderPainted(false);

                        northMasterContainer.add(northContainer, BorderLayout.CENTER);
                        northMasterContainer.add(buildURLprogressBar, BorderLayout.SOUTH);

                        JButton initializeButton = new JButton("Initialize");
                        initializeButton.setFont(averagedFont);
                        initializeButton.setBackground(Color.black);
                        initializeButton.setForeground(Color.white);
                        initializeButton.setPreferredSize(new Dimension(40, 60));
                        northContainer.add(initializeButton);

                        JButton viewAlphaURL = new JButton("View Alpha URL");
                        viewAlphaURL.setFont(averagedFont);
                        viewAlphaURL.setBackground(Color.black);
                        viewAlphaURL.setForeground(Color.white);
                        viewAlphaURL.setPreferredSize(new Dimension(40, 60));
                        northContainer.add(viewAlphaURL);

                        JButton getBuildPoints = new JButton("Get Build Points");
                        getBuildPoints.setFont(averagedFont);
                        getBuildPoints.setBackground(Color.black);
                        getBuildPoints.setForeground(Color.white);
                        getBuildPoints.setPreferredSize(new Dimension(40, 60));
                        northContainer.add(getBuildPoints);

                        JButton getAlphaURL = new JButton("Get alphaURL");
                        getAlphaURL.setFont(averagedFont);
                        getAlphaURL.setBackground(Color.black);
                        getAlphaURL.setForeground(Color.white);
                        getAlphaURL.setPreferredSize(new Dimension(40, 60));
                        northContainer.add(getAlphaURL);

                        JButton setAlphaURL = new JButton("Set alphaURL");
                        setAlphaURL.setFont(averagedFont);
                        setAlphaURL.setBackground(Color.black);
                        setAlphaURL.setForeground(Color.white);
                        setAlphaURL.setPreferredSize(new Dimension(40, 60));
                        northContainer.add(setAlphaURL);

                        JButton dumpArray = new JButton("Dump alphaURL");
                        dumpArray.setFont(averagedFont);
                        dumpArray.setBackground(Color.black);
                        dumpArray.setForeground(Color.white);
                        dumpArray.setPreferredSize(new Dimension(40, 60));
                        northContainer.add(dumpArray);

                        frame.add(northMasterContainer, BorderLayout.NORTH);

                        JTextArea masterPanel = new JTextArea();
                        DefaultCaret caret = (DefaultCaret) masterPanel.getCaret();
                        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                        masterPanel.setBackground(Color.black);
                        masterPanel.setFont(listFont);
                        masterPanel.setLineWrap(true);

                        JScrollPane mainScrollPane = new JScrollPane(masterPanel);
                        mainScrollPane.setAutoscrolls(true);
                        mainScrollPane.setBackground(Color.black);
                        mainScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                        mainScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                        mainScrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
                        mainScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(50, 0));

                        frame.add(mainScrollPane, BorderLayout.CENTER);

                        initializeButton.addActionListener(
                                new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        Thread thread = new Thread() {
                                            public void run() {
                                                Random rand = new Random();
                                                boolean everythingRequiredToRun = true;
                                                masterPanel.append("\n                  Initializing...\n");
                                                if (!keywordsExists) {
                                                    masterPanel.append("\n         ( Error ) Can't Build Without Keywords??!");
                                                    everythingRequiredToRun = false;
                                                }
                                                if (!avoidURLExists) {
                                                    masterPanel.append("\n             ( Error ) Can't Build Without Bad URL List??!");
                                                    everythingRequiredToRun = false;
                                                }
                                                if (!bounceIMGExists) {
                                                    masterPanel.append("\n                             ( Error ) Can't Build Without Bounce Image List?!?");
                                                    everythingRequiredToRun = false;
                                                }
                                                if (!buildPointsExist) {
                                                    masterPanel.append("\n      ( Error ) Need Initial Build Points?!");
                                                    everythingRequiredToRun = false;
                                                }
                                                if (everythingRequiredToRun) {
                                                    ArrayList<String> tempArray = (ArrayList<String>) buildURL(masterPanel, buildURLprogressBar).clone();
                                                    if (!alphaURLbeingAccesed) {
                                                        alphaURLbeingAccesed = true;
                                                        alphaURL.removeAll(alphaURL);
                                                        alphaURL = (ArrayList<String>) tempArray.clone();
                                                        try {
                                                            String filename = "alphaURL.txt";
                                                            FTPconnection ftPconnection = new FTPconnection(alphaURL, filename);
                                                            masterPanel.append("\n            FTP upload Successful!  ");
                                                        } catch (Exception e1) {
                                                            masterPanel.append("\n            FTP upload Failed... exiting  ");
                                                        }
                                                        alphaURLbeingAccesed = false;
                                                    } else {
                                                        while (alphaURLbeingAccesed) {
                                                            masterPanel.append("\n            alphaURL being accessed... waiting...  ");
                                                            try {
                                                                sleep(10000);
                                                            } catch (InterruptedException e1) {
                                                            }
                                                        }
                                                        alphaURLbeingAccesed = true;
                                                        alphaURL.removeAll(alphaURL);
                                                        alphaURL = (ArrayList<String>) tempArray.clone();
                                                        try {
                                                            String filename = "alphaURL.txt";
                                                            FTPconnection ftPconnection = new FTPconnection(alphaURL, filename);
                                                            masterPanel.append("\n            FTP upload Successful!  ");
                                                        } catch (Exception e1) {
                                                            masterPanel.append("\n            FTP upload Failed... exiting  ");
                                                        }
                                                        alphaURLbeingAccesed = false;
                                                    }
                                                    masterPanel.append("\n            I'm done now thank you!  ");

                                                }
                                            }


                                        };
                                        thread.start();
                                    }
                                });
                        viewAlphaURL.addActionListener(
                                new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        SwingUtilities.invokeLater(new Runnable() {
                                            public void run() {
                                                Random rand = new Random();
                                                int random = rand.nextInt(150) + 1;

                                                JFrame frame = new JFrame();
                                                frame.setPreferredSize(new Dimension((windowWidth * 2 / 3), (windowHeight * 2 / 3)));
                                                frame.setLocation(200 + random, random);
                                                frame.getContentPane().setBackground(Color.BLACK);
                                                frame.pack();
                                                frame.setVisible(true);

                                                JPanel panel = new JPanel();
                                                panel.setBackground(Color.black);
                                                panel.setLayout(new GridLayout(0, 1));

                                                try {
                                                    for (String element : alphaURL) {
                                                        JButton trendButton = new JButton(element);
                                                        trendButton.setFont(listFont);
                                                        trendButton.setBackground(Color.black);
                                                        trendButton.setForeground(Color.white);
                                                        trendButton.setHorizontalAlignment(SwingConstants.LEFT);
                                                        panel.add(trendButton);
                                                    }
                                                    if (!alphaURL.equals(0) & !alphaURL.equals(null)) {
                                                        trendsExist = true;
                                                    }
                                                } catch (Exception e1) {
                                                }

                                                JScrollPane scrollPane = new JScrollPane(panel);
                                                scrollPane.setBackground(Color.black);
                                                scrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
                                                scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(70, 0));
                                                frame.add(scrollPane);

                                            }
                                        });
                                    }
                                });
                        getBuildPoints.addActionListener(
                                new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        SwingUtilities.invokeLater(new Runnable() {
                                            public void run() {
                                                JFrame buildPointsframe = new JFrame("Build Points");
                                                JPanel buildPointsPanel = new JPanel(new GridLayout(0, 1));
                                                JScrollPane buildPointsScrollPane = new JScrollPane(buildPointsPanel);
                                                buildPointsScrollPane.setBackground(Color.black);
                                                buildPointsScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                                                buildPointsScrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
                                                buildPointsScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(50, 0));
                                                buildPointsframe.add(buildPointsScrollPane);

                                                if (buildPointsExist) {
                                                    for (String element : buildPoints) {
                                                        JButton tempKeyword = new JButton(element);
                                                        tempKeyword.setFont(averagedFont);
                                                        tempKeyword.setBackground(Color.black);
                                                        tempKeyword.setForeground(Color.white);
                                                        buildPointsPanel.add(tempKeyword);
                                                    }
                                                }
                                                if (!buildPointsExist) {
                                                    try {
                                                        URL url = new URL("http://www.lukeholman.net/meetis/buildPoints.txt");
                                                        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                                                        String everything = "";
                                                        String concatenate;
                                                        while ((concatenate = in.readLine()) != null) {
                                                            everything += concatenate;
                                                        }
                                                        in.close();
                                                        String[] Keywords = everything.split(", ");
                                                        buildPoints.clear();

                                                        for (String element : Keywords) {
                                                            buildPoints.add(element);
                                                            JButton tempKeyword = new JButton(element);
                                                            tempKeyword.setFont(averagedFont);
                                                            tempKeyword.setBackground(Color.black);
                                                            tempKeyword.setForeground(Color.white);
                                                            buildPointsPanel.add(tempKeyword);
                                                        }
                                                        if (!Keywords.equals(0) & !Keywords.equals(null)) {
                                                            buildPointsExist = true;
                                                        }

                                                    } catch (MalformedURLException e) {
                                                    } catch (IOException e) {
                                                    }
                                                }
                                                SwingUtilities.invokeLater(new Runnable() {
                                                    public void run() {
                                                        Random rand = new Random();
                                                        int random = rand.nextInt(150) + 1;
                                                        buildPointsframe.setPreferredSize(new Dimension((windowWidth * 2 / 3), (windowHeight * 2 / 3)));
                                                        buildPointsframe.setLocation(random, random);
                                                        buildPointsframe.getContentPane().setBackground(Color.BLACK);
                                                        buildPointsframe.setLayout(new GridLayout(0, 1));
                                                        buildPointsframe.pack();
                                                        buildPointsframe.setVisible(true);
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                        getAlphaURL.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Thread thread = new Thread() {
                                    public void run() {
                                        try {
                                            URL url = new URL("http://www.lukeholman.net/meetis/alphaURL.txt");
                                            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                                            String everything = "";
                                            String concatenate;
                                            while ((concatenate = in.readLine()) != null) {
                                                everything += concatenate;
                                            }
                                            in.close();
                                            String[] Keywords = everything.split(", ");
                                            alphaURL.clear();

                                            for (String element : Keywords) {
                                                alphaURL.add(element);
                                            }
                                        } catch (IOException e1) {
                                        }

                                    }
                                };
                                thread.start();
                            }
                        });
                        setAlphaURL.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Thread thread = new Thread() {
                                    public void run() {
                                        String filename = "alphaURL.txt";
                                        FTPconnection ftPconnection = new FTPconnection(alphaURL, filename);
                                    }
                                };
                                thread.start();
                            }
                        });
                        dumpArray.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                alphaURL.removeAll(alphaURL);

                            }
                        });


                    }
                });
            }
        });
        diveURLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread() {
                    public void run() {
                        Random rand = new Random();
                        int random = rand.nextInt(150) + 1;
                        JFrame frame = new JFrame("Dive URL");

                        frame.setPreferredSize(new Dimension((windowWidth * 2 / 3), (windowHeight * 2 / 3)));
                        frame.setLocation(random, random);
                        frame.getContentPane().setBackground(Color.BLACK);
                        frame.setLayout(new BorderLayout());

                        JProgressBar diveURLprogress = new JProgressBar();
                        diveURLprogress.setIndeterminate(true);
                        diveURLprogress.setBackground(Color.black);
                        diveURLprogress.setForeground(Color.white);
                        diveURLprogress.setPreferredSize(new Dimension(27, 27));
                        diveURLprogress.setBorderPainted(false);
                        frame.add(diveURLprogress, BorderLayout.NORTH);

                        JTextArea centralArea = new JTextArea();
                        centralArea.setBackground(Color.black);
                        centralArea.setForeground(Color.white);
                        centralArea.setFont(averagedFont);

                        JScrollPane centralScrollPane = new JScrollPane(centralArea);
                        centralScrollPane.setBackground(Color.BLACK);
                        centralScrollPane.setForeground(Color.white);
                        centralScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                        centralScrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
                        centralScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(50, 0));
                        frame.add(centralScrollPane);

                        frame.pack();
                        frame.setVisible(true);

                        ArrayList<internalComparison> tempArray = (ArrayList<internalComparison>) diveURL(
                                diveURLprogress,
                                centralArea,
                                centralComMasterJPanel,
                                trends,
                                omegaURL,
                                alphaURL,
                                keywords,
                                bounceIMG,
                                averagedFont).clone();

                        if (!omegaURLbeingAccesed) {
                            omegaURLbeingAccesed = true;
                            omegaURL.removeAll(omegaURL);
                            omegaURL = (ArrayList<internalComparison>) tempArray.clone();
                            omegaURLbeingAccesed = false;
                        } else {
                            while (omegaURLbeingAccesed) {
                                centralArea.append("\n            omegaURL being accessed... waiting...  ");
                                try {
                                    sleep(10000);
                                } catch (InterruptedException e1) {
                                }
                            }
                            omegaURLbeingAccesed = true;
                            omegaURL.removeAll(omegaURL);
                            omegaURL = (ArrayList<internalComparison>) tempArray.clone();
                            omegaURLbeingAccesed = false;
                        }
                        diveURLprogress.setIndeterminate(true);
                        centralArea.append("\n            Thank you I'm done now  ");
                    }
                };
                thread.start();
            }
        });
        hashTagButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread() {
                    public void run() {
                        Random rand = new Random();
                        boolean everythingRequiredToRun = true;
                        int random = rand.nextInt(150) + 1;
                        JFrame frame = new JFrame("HashTag#");
                        frame.setPreferredSize(new Dimension((windowWidth * 2 / 3), (windowHeight * 2 / 3)));
                        frame.setLocation(random, random);
                        frame.getContentPane().setBackground(Color.BLACK);
                        frame.setLayout(new BorderLayout());

                        JProgressBar hashTagProgress = new JProgressBar();
                        hashTagProgress.setIndeterminate(true);
                        hashTagProgress.setBackground(Color.black);
                        hashTagProgress.setForeground(Color.white);
                        hashTagProgress.setPreferredSize(new Dimension(27, 27));
                        hashTagProgress.setBorderPainted(false);
                        frame.add(hashTagProgress, BorderLayout.NORTH);

                        JTextArea centralArea = new JTextArea();
                        DefaultCaret caret = (DefaultCaret) centralArea.getCaret();
                        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                        centralArea.setBackground(Color.black);
                        centralArea.setFont(averagedFont);
                        centralArea.setForeground(Color.white);
                        centralArea.setLineWrap(true);

                        JScrollPane centralScrollPane = new JScrollPane(centralArea);
                        centralScrollPane.setBackground(Color.BLACK);
                        centralScrollPane.setForeground(Color.white);
                        centralScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                        centralScrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
                        centralScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(50, 0));
                        frame.add(centralScrollPane);

                        frame.pack();
                        frame.setVisible(true);

                        if (omegaURL.size() < 2) everythingRequiredToRun = false;
                        if (keywords.size() < 2) everythingRequiredToRun = false;
                        if (everythingRequiredToRun) {
                            try {
                                trends.trimHashTags();
                                ArrayList<internalComparison> tempArray = (ArrayList<internalComparison>) hashTag(omegaURL, hashTagProgress, centralArea, trends.getTrends(), keywords).clone();
                                omegaURLbeingAccesed = true;
                                omegaURL.removeAll(omegaURL);
                                omegaURL = (ArrayList<internalComparison>) tempArray.clone();
                                omegaURLbeingAccesed = false;
                            } catch (Exception e) {
                                omegaURLbeingAccesed = false;
                            }
                        } else {
                            centralArea.append("\n       omegaURL and Keywords required \n");
                        }


                    }
                };
                thread.start();
            }
        });
        manualEngage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread() {
                    public void run() {
                        outputDataArea.append("   Skipped Twitter Trends\n");
                        trendsExist = true;
                        selfEngageButton.setBackground(new Color(0, 0, 0));
                        canSelfEngage = false;
                        try {
                            currentCycle.setText("updating AvoidURL");
                            URL url = new URL("http://www.lukeholman.net/meetis/avoidURL.txt");
                            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                            String everything = "";
                            String concatenate;
                            while ((concatenate = in.readLine()) != null) {
                                everything += concatenate;
                            }
                            in.close();
                            String[] Keywords = everything.split("\", \"");
                            avoidURL.removeAll(avoidURL);
                            for (String element : Keywords) {
                                avoidURL.add(element);
                                centralComDisplayMessage.append(" ( " + element + " ) ");
                            }
                            if (!Keywords.equals(0) & !Keywords.equals(null)) {
                                centralComDisplayMessage.append("\n\n                                          ( AvoidURL Array Set* )\n\n");
                                avoidURLExists = true;
                            }

                        } catch (MalformedURLException e) {
                            centralComDisplayMessage.setText("      Something went wrong... bounceIMG do not exist");
                            avoidURLExists = false;
                        } catch (IOException e) {
                            centralComDisplayMessage.setText("    Something went wrong... bounceIMG do not exist");
                            avoidURLExists = false;
                        }
                        try {
                            currentCycle.setText("updating buildPoints");
                            URL url = new URL("http://www.lukeholman.net/meetis/buildPoints.txt");
                            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                            String everything = "";
                            String concatenate;
                            while ((concatenate = in.readLine()) != null) {
                                everything += concatenate;
                            }
                            in.close();
                            String[] Keywords = everything.split(", ");
                            buildPoints.removeAll(buildPoints);

                            for (String element : Keywords) {
                                buildPoints.add(element);
                                centralComDisplayMessage.append("\n ( " + element + " ) ");
                            }
                            if (!Keywords.equals(0) & !Keywords.equals(null)) {
                                centralComDisplayMessage.append("\n\n                                  ( Build Points Up* )\n\n");
                                buildPointsExist = true;
                            }
                        } catch (MalformedURLException e) {
                            centralComDisplayMessage.setText("     Something went wrong... buildPoints do not exist");
                            buildPointsExist = false;
                        } catch (IOException e) {
                            centralComDisplayMessage.setText("      Something went wrong... buildPoints do not exist");
                            buildPointsExist = false;
                        }
                        try {
                            currentCycle.setText("updating Keywords");
                            URL url = new URL("http://www.lukeholman.net/meetis/keywords.txt");
                            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                            String everything = "";
                            String concatenate;
                            while ((concatenate = in.readLine()) != null) {
                                everything += concatenate;
                            }
                            in.close();
                            String[] Keywords = everything.split("\", \"");
                            keywords.removeAll(keywords);
                            for (String element : Keywords) {
                                keywords.add(element);
                                centralComDisplayMessage.append(" ( " + element + " ) ");
                            }
                            if (!Keywords.equals(0) & !Keywords.equals(null)) {
                                centralComDisplayMessage.append("\n\n                             ( Keyword Array Set* )\n\n");
                                keywordsExists = true;
                            }

                        } catch (MalformedURLException e) {
                            centralComDisplayMessage.setText("      Something went wrong... Keywords do not exist");
                            keywordsExists = false;
                        } catch (IOException e) {
                            centralComDisplayMessage.setText("        Something went wrong... Keywords do not exist");
                            keywordsExists = false;
                        }
                        try {
                            currentCycle.setText("updating Keyword Phrase");
                            URL url = new URL("http://www.lukeholman.net/meetis/keywordPhrase.txt");
                            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                            String everything = "";
                            String concatenate;
                            while ((concatenate = in.readLine()) != null) {
                                everything += concatenate;
                            }
                            in.close();
                            String[] Keywords = everything.split("\", \"");
                            keywordPhrase.removeAll(keywordPhrase);
                            for (String element : Keywords) {
                                keywordPhrase.add(element);
                                centralComDisplayMessage.append(" ( " + element + " ) ");
                            }
                            if (!keywordPhrase.equals(0) & !keywordPhrase.equals(null)) {
                                centralComDisplayMessage.append("\n\n                                                  ( Keyword Phrase Array Set* )\n\n");
                            }

                        } catch (MalformedURLException e) {
                            centralComDisplayMessage.setText("      Keyword Phrase went bust  ( MalformedURLException ) ");
                        } catch (IOException e) {
                            centralComDisplayMessage.setText("      Keyword Phrase went bust   ( IOException ) ");
                        }
                        try {
                            currentCycle.setText("updating bounceIMG");
                            URL url = new URL("http://www.lukeholman.net/meetis/bounceIMG.txt");
                            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                            String everything = "";
                            String concatenate;
                            while ((concatenate = in.readLine()) != null) {
                                everything += concatenate;
                            }
                            in.close();
                            String[] Keywords = everything.split("\", \"");
                            bounceIMG.removeAll(bounceIMG);
                            for (String element : Keywords) {
                                bounceIMG.add(element);
                                centralComDisplayMessage.append(" ( " + element + " ) ");
                            }
                            if (!Keywords.equals(0) & !Keywords.equals(null)) {
                                centralComDisplayMessage.append("\n\n                                ( bounceIMG Array Set* )\n\n");
                                bounceIMGExists = true;
                            }

                        } catch (MalformedURLException e) {
                            centralComDisplayMessage.setText("         Something went wrong... bounceIMG do not exist");
                            bounceIMGExists = false;
                        } catch (IOException e) {
                            centralComDisplayMessage.setText("     Something went wrong... bounceIMG do not exist");
                            bounceIMGExists = false;
                        }
                    }
                };
                thread.start();
            }
        });

        SQLUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread() {
                    public void run() {
                        Random rand = new Random();
                        boolean everythingRequiredToRun = true;
                        int random = rand.nextInt(150) + 1;
                        JFrame frame = new JFrame("SQL Update");
                        frame.setPreferredSize(new Dimension((windowWidth * 2 / 3), (windowHeight * 4 / 5)));
                        frame.setLocation(random, random);
                        frame.getContentPane().setBackground(Color.BLACK);
                        frame.setLayout(new GridLayout(0, 1));

                        JTextArea outputTextArea = new JTextArea();
                        JPanel listJPanel = new JPanel();
                        listJPanel.setLayout(new GridLayout(0, 1));
                        listJPanel.setBackground(Color.BLACK);
                        listJPanel.setForeground(Color.white);
                        listJPanel.setFont(averagedFont);
                        JScrollPane listScrollPane = new JScrollPane(listJPanel);
                        listScrollPane.setBackground(Color.BLACK);
                        listScrollPane.setForeground(Color.white);
                        listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                        listScrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
                        listScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(50, 0));

                        try {
                            int i = 1;
                            for (internalComparison element : omegaURL) {
                                JButton masterListButton = new JButton(" ( " + i++ + " ) " + element.titleToString());
                                masterListButton.setBackground(Color.black);
                                masterListButton.setForeground(Color.white);
                                masterListButton.setFont(averagedFont);
                                masterListButton.setHorizontalAlignment(SwingConstants.LEFT);
                                masterListButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        Thread thread = new Thread() {
                                            public void run() {
                                                SQLquery sqLquery = new SQLquery(element, outputTextArea);
                                            }
                                        };
                                        thread.start();
                                    }
                                });
                                listJPanel.add(masterListButton);
                            }
                        } catch (Exception e1) {
                        }

                        outputTextArea.setBackground(Color.BLACK);
                        outputTextArea.setForeground(Color.white);
                        outputTextArea.setFont(averagedFont);
                        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
                        outputScrollPane.setBackground(Color.BLACK);
                        outputScrollPane.setForeground(Color.white);
                        outputScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                        outputScrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
                        outputScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(50, 0));

                        frame.add(listScrollPane);
                        frame.add(outputScrollPane);
                        frame.pack();
                        frame.setVisible(true);
                    }
                };
                thread.start();
            }
        });
        twitterUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread() {
                    public void run() {
                        Random rand = new Random();
                        boolean everythingRequiredToRun = true;
                        int random = rand.nextInt(150) + 1;
                        JFrame frame = new JFrame("Twitter Update");
                        frame.setPreferredSize(new Dimension((windowWidth * 2 / 3), (windowHeight * 4 / 5)));
                        frame.setLocation(random, random);
                        frame.getContentPane().setBackground(Color.BLACK);
                        frame.setLayout(new GridLayout(0, 1));

                        JTextArea outputTextArea = new JTextArea();
                        JPanel listJPanel = new JPanel();
                        listJPanel.setBackground(Color.BLACK);
                        listJPanel.setForeground(Color.white);
                        listJPanel.setFont(averagedFont);
                        listJPanel.setLayout(new GridLayout(0, 1));

                        JScrollPane listScrollPane = new JScrollPane(listJPanel);
                        listScrollPane.setBackground(Color.BLACK);
                        listScrollPane.setForeground(Color.white);
                        listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                        listScrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
                        listScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(50, 0));

                        try {
                            int i = 1;
                            for (internalComparison element : omegaURL) {
                                JButton masterListButton = new JButton(" ( " + i++ + " ) " + element.titleToString());
                                masterListButton.setBackground(Color.black);
                                masterListButton.setForeground(Color.white);
                                masterListButton.setFont(averagedFont);
                                masterListButton.setHorizontalAlignment(SwingConstants.LEFT);
                                masterListButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        Thread thread = new Thread() {
                                            public void run() {
                                                try {
                                                    UpdateStatus updateStatus = new UpdateStatus(element, outputTextArea);
                                                } catch (IOException e1) {
                                                    outputTextArea.append("\n                               (IOException e1)\n");
                                                } catch (TwitterException e1) {
                                                    outputTextArea.append("\n                           (TwitterException e1)\n");
                                                }
                                            }
                                        };
                                        thread.start();
                                    }
                                });
                                listJPanel.add(masterListButton);
                            }
                        } catch (Exception e1) {
                        }

                        outputTextArea.setBackground(Color.BLACK);
                        outputTextArea.setForeground(Color.white);
                        outputTextArea.setFont(averagedFont);
                        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
                        outputScrollPane.setBackground(Color.BLACK);
                        outputScrollPane.setForeground(Color.white);
                        outputScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                        outputScrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
                        outputScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(50, 0));

                        frame.add(listScrollPane);
                        frame.add(outputScrollPane);
                        frame.pack();
                        frame.setVisible(true);
                    }
                };
                thread.start();
            }
        });
        timelineRedundancyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread() {
                    public void run() {
                        doneWithConnection = false;
                        Random rand = new Random();
                        int random = rand.nextInt(6) + 1;
                        JFrame frame = new JFrame("Test Connection");
                        frame.setPreferredSize(new Dimension((windowWidth / 2), (windowHeight / 6)));
                        frame.setLocation((windowWidth / random), (windowHeight / random));
                        frame.getContentPane().setBackground(Color.BLACK);
                        frame.setLayout(new GridLayout(0, 1));
                        //frame.setUndecorated(true);
                        frame.pack();
                        frame.setVisible(true);

                        JLabel hashTagStatus = new JLabel();
                        hashTagStatus.setHorizontalAlignment(JLabel.CENTER);

                        JProgressBar progressBar = new JProgressBar();
                        progressBar.setIndeterminate(true);

                        frame.add(hashTagStatus);
                        frame.add(progressBar);
                        class Task extends SwingWorker<Void, Void> {
                            @Override
                            public Void doInBackground() {
                                ActionListener actionListener = new ActionListener() {
                                    public void actionPerformed(ActionEvent actionEvent) {
                                        timeLineRedundancy();
                                    }
                                };

                                Timer timer = new Timer(2000, actionListener);
                                timer.start();
                                return null;
                            }
                        }
                        Task task = new Task();
                        task.execute();
                    }
                };
                thread.start();
            }
        });
        FULLAUTOButton.addActionListener(new ActionListener() {//------------------------- Full Auto ----------------->
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!fullAutoEngaged) {
                    selfEngageButton.setBackground(new Color(0, 0, 0));
                    FULLAUTOButton.setBackground(new Color(54, 162, 24));
                    canSelfEngage = false;

                        FullAuto();

                } else {
                    FULLAUTOButton.setBackground(new Color(42, 78, 85));
                    fullAutoEngaged = false;
                    centralComProgressBar.setIndeterminate(false);
                    centralComProgressBar.setValue(0);
                }                                       //////  //   //  \\      \\         \\\\  \\  \\  \\\\\\\  \\\\
            }                                          //       //   //  \\      \\        \\  \\ \\  \\    \\    \\  \\
                                                       /////    //   //  \\      \\        \\\\\\ \\  \\    \\    \\  \\
                                                       //       //   //  \\      \\        \\  \\ \\  \\    \\    \\  \\
        });                                            //        /////    \\\\\   \\\\\    \\  \\  \\\\     \\     \\\\
        selfEngageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selfEngageButton.setBackground(new Color(0, 0, 0));
                canSelfEngage = false;
            }
        });
    }

    private void waitRandomShort(JProgressBar progressbar) {
        Random rand = new Random();
        int sleepDuration = 10000 + rand.nextInt(30000);
        for (int i = 0; i < sleepDuration; i++) {
            try {
                sleep(1);
                progressbar.setValue((int) ((((double) i) / sleepDuration) * 100));

            } catch (InterruptedException e1) {
            }
        }
    }

    static void removeBlankSpace(StringBuilder sb) {
        int j = 0;
        for(int i = 0; i < sb.length(); i++) {
            if (!Character.isWhitespace(sb.charAt(i))) {
                sb.setCharAt(j++, sb.charAt(i));
            }
        }
        sb.delete(j, sb.length());
    }

    static void compoundPhraseFormat(StringBuilder sb, int startingPointPhrase, int phraseSize) {
        sb.insert(startingPointPhrase, '#');
        for(int i = startingPointPhrase; i < (startingPointPhrase + phraseSize); i++) {
            if (Character.isWhitespace(sb.charAt(i))) {
                sb.delete(i, (i + 1));
            }
        }
    }

    private static boolean testConnection() throws MalformedURLException {
        final URL url = new URL("http://www.lukeholman.net/meetis.php");
        final URLConnection conn;
        try {
            conn = url.openConnection();
            conn.connect();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    static ArrayList<String> removeDuplicates(ArrayList<String> list) {

        // Store unique items in result.
        ArrayList<String> result = new ArrayList<>();

        // Record encountered Strings in HashSet.
        HashSet<String> set = new HashSet<>();

        // Loop over argument list.
        for (String item : list) {

            // If String is not in set, add it to the list and the set.
            if (!set.contains(item)) {
                result.add(item);
                set.add(item);
            }
        }
        return result;
    }

    private ArrayList<String> buildURL(JTextArea masterPanel, JProgressBar progressBar) {
        alphaURLbeingAccesed = true;
        progressBar.setIndeterminate(false);
        Random rand = new Random();
        masterPanel.append("\n                          First Parse Attempt...                           <-- 1st\n");
        try {
            sleep(1000);
        } catch (InterruptedException e1) {
        }
        //  First Parse-------------------------------------------------
        int x = 1;
        for (String urlElement : buildPoints) {
            progressBar.setValue((int) ((((double) x) / buildPoints.size()) * 100));
            x++;
            try {
                Document doc = Jsoup.connect(urlElement).get();
                Elements link = doc.getElementsByTag("link");
                for (Element element : link) {
                    String src = element.text();
                    if (!(src == null | src.length() == 0)) {
                        masterPanel.append("\n" + src);
                        alphaURL.add(src);
                        sleep(rand.nextInt(35));
                    }
                }
                Elements aTitle = doc.getElementsByTag("a.title");
                for (Element element : aTitle) {
                    String src = element.text();
                    if (!(src == null | src.length() == 0)) {
                        masterPanel.append("\n" + src);
                        alphaURL.add(src);
                        sleep(rand.nextInt(35));
                    }
                }
            } catch (IOException ex) {
                masterPanel.append("\n               ( URL Exception )  ");
            } catch (InterruptedException e1) {
            }
        }
        masterPanel.append("\n\n  ====================     Attempting a Second Parse...                           <-- 2nd\n");
        try {
            sleep(1000);
        } catch (InterruptedException e1) {
        }
        //  Second Parse------------------------------------------------
        int y = 1;
        for (String urlElement : buildPoints) {
            try {
                progressBar.setValue((int) ((((double) y) / buildPoints.size()) * 100));
                y++;
                Document doc = Jsoup.connect(urlElement).get();
                Elements img = doc.getElementsByTag("a");
                for (Element element : img) {
                    String src = element.attr("abs:href");
                    boolean Continue = true;
                    for (int i = 0; i < avoidURL.size(); i++) {
                        if (src.contains(avoidURL.get(i)))
                            Continue = false;
                    }
                    if (Continue)
                        if (!(src == null | src.length() == 0)) {
                            masterPanel.append("\n" + src);
                            alphaURL.add(src);
                            sleep(rand.nextInt(35));
                        }
                }
            } catch (IOException ex) {
                outputDataArea.append("\n ( Second Parse )( Failure )* " + urlElement);
            } catch (InterruptedException e1) {
            }
        }
        // Store unique items in result.
        ArrayList<String> result0 = new ArrayList<>();
        //
        // Record encountered Strings in HashSet.
        HashSet<String> set0 = new HashSet<>();
        //
        // Loop over argument list.                REDUNDANCY CHECK
        for (String item : alphaURL) {
            //
            // If String is not in set, add it to the list and the set.
            if (!set0.contains(item)) {
                result0.add(item);
                set0.add(item);
            }
        }
        alphaURL.clear();
        alphaURL = (ArrayList<String>) result0.clone();
        for (int i = 0; i < alphaURL.size(); i++) {
            progressBar.setValue((int) ((((double) i) / alphaURL.size()) * 100));
            int counter = 0;
            for (int j = 0; j < alphaURL.get(i).length(); j++) {
                if (alphaURL.get(i).charAt(j) == '/') {
                    counter++;
                }
            }
            if (counter < 4 && counter > 1) {
                try {
                    masterPanel.append("\n       ( parseNode )    " + alphaURL.get(i));
                    Document doc = Jsoup.connect(alphaURL.get(i)).get();
                    Elements link = doc.select("h1 a[href], h2 a[href], h3 a[href]");
                    if (link.size() >= 10)
                        for (int j = 0; j < 10; j++) {
                            Element el = link.get(j);
                            String src = el.attr("abs:href");
                            if (src.contains("http")) {
                                boolean redundancy = false;
                                for (int k = 0; k < alphaURL.size(); k++) {
                                    if (alphaURL.get(k).equals(src))
                                        redundancy = true;
                                }
                                if (!redundancy) {
                                    alphaURL.add(src);
                                    masterPanel.append("\n   " + src);
                                }
                            }
                        }
                } catch (IOException e) {
                    masterPanel.append("\n          ( parseUnknown )( IOException )   ");
                }
            }
        }
        ArrayList<String> betaURL = new ArrayList<String>();
        for (int i = 0; i < alphaURL.size(); i++) {
            int counter = 0;
            for (int j = 0; j < alphaURL.get(i).length(); j++) {
                if (alphaURL.get(i).charAt(j) == '/') {
                    counter++;
                }
            }
            if (counter < 5) {
                masterPanel.append("\n           ( Removed )     " + alphaURL.get(i));
            } else {
                betaURL.add(alphaURL.get(i));
            }
        }
        alphaURL.clear();
        // Store unique items in result.
        ArrayList<String> result1 = new ArrayList<>();
        //
        // Record encountered Strings in HashSet.
        HashSet<String> set1 = new HashSet<>();
        //
        // Loop over argument list.                REDUNDANCY CHECK
        for (String item : betaURL) {
            //
            // If String is not in set, add it to the list and the set.
            if (!set1.contains(item)) {
                result1.add(item);
                set1.add(item);
            }
        }
        Collections.shuffle(betaURL);
        for (String element : betaURL) {
            masterPanel.append("\n   " + element);
        }
        masterPanel.append("\n                 Thank you I'm done now...     \n");
        progressBar.setIndeterminate(true);
        alphaURLbeingAccesed = false;
        return result1;
    }

    public ArrayList<internalComparison> diveURL(JProgressBar diveURLprogress,
                                                 JTextArea centralArea,
                                                 JPanel centralComMasterJPanel,
                                                 GetTwitterTrends trends,
                                                 ArrayList<internalComparison> omegaURL,
                                                 ArrayList<String> alphaURL,
                                                 ArrayList<String> keywords,
                                                 ArrayList<String> bounceIMG,
                                                 Font averagedFont) {
        Timer[] timeoutTimer = {null};
        class Timeout extends SwingWorker<Void, Void> {
            ActionListener actionListener = new ActionListener() {
                public void actionPerformed(ActionEvent actionEvent) {
                    try {
                        restartApplication();

                    } catch (URISyntaxException e) {
                    } catch (IOException e) {
                    }
                }
            };
            public Void clearTimer() {
                timeoutTimer[0].stop();
                return null;
            }
            public Void extendTimer(){
                if (timeoutTimer[0] != null) {
                    timeoutTimer[0].stop();
                    timeoutTimer[0] = new Timer(900000, actionListener);
                }
                return null;
            }
            @Override
            public Void doInBackground() {
                timeoutTimer[0] = new Timer(900000, actionListener);
                timeoutTimer[0].start();
                return null;
            }
        }
        Timeout timeout = new Timeout();
        timeout.execute();

        boolean everythingRequiredToRun = true;

        if ((alphaURL.size() < 10) || (alphaURL.equals(null))) everythingRequiredToRun = false;
        if (!everythingRequiredToRun) {
            centralArea.append("\n             (alphaURL.equals(0)) || (alphaURL.equals(null))    ");
            timeout.clearTimer();
        }
        if (everythingRequiredToRun) {
            timeout.extendTimer();
            diveURLprogress.setIndeterminate(false);
            centralArea.append("\n         position = 0   ");
            diveURLprogress.setValue(0);
            position = 0;
            for (String element : alphaURL) {
                diveURLprogress.setValue(10);
                Document doc = null;
                try {
                    doc = Jsoup.connect(element).get();
                } catch (Exception e1) {
                    centralArea.append("( Exception on initialization! )");
                }
                diveURLprogress.setValue(15);
                Elements link = new Elements();
                try {
                    link = doc.getElementsByTag("title");
                } catch (Exception e1) {
                    centralArea.append("link = doc.getElementsByTag(\"title\");");
                }
                diveURLprogress.setValue(20);
                if (doc != null)
                    if (link != null)
                        for (Element el : link) {
                            diveURLprogress.setValue(25);
                            String src = " ";
                            if (el != null)
                                if (el.hasText())
                                    if (el.text().length() < 100)
                                        src = el.text();
                            boolean breakMe = false;                //    breaks image loop             (and try again)
                            boolean die = false;                    //    breaks method and returns
                            boolean Continue = false;               //    check keyword gate
                            diveURLprogress.setValue(30);
                            for (int i = 0; i < keywords.size(); i++) {
                                if (src.toLowerCase().contains(keywords.get(i))) {
                                    Continue = true;
                                    diveURLprogress.setValue(35);
                                }
                            }
                            diveURLprogress.setValue(40);
                            if (trends.getTrends() != null) {
                                diveURLprogress.setValue(45);
                                for (int i = 0; i < trends.getTrends().size(); i++) {
                                    if (src.toLowerCase().contains(trends.getTrends().get(i))) {
                                        Continue = true;
                                        diveURLprogress.setValue(50);
                                    }
                                }
                            }
                            diveURLprogress.setValue(60);
                            if (Continue)//     Keyword Matches
                                if (!(src == null | src.length() == 0)) {
                                    Elements images = doc.select("img");
                                    diveURLprogress.setValue(65);
                                    if (images != null)
                                        for (Element image : images) {
                                            diveURLprogress.setValue(70);
                                            URL url = null;
                                            if ((image.attr("src").contains("gif")) || (image.attr("src").contains("jpg")) || (image.attr("src").contains("png")))
                                                if (image.attr("src").contains("//"))
                                                    if (!(image == null | image.attr("src").length() < 5))
                                                        if (!(breakMe)) {                                   //     Check Image width and height
                                                                                                            //     Create a URL for the image's location
                                                            try {
                                                                url = new URL(image.attr("src"));
                                                                diveURLprogress.setValue(75);
                                                                Image img = new ImageIcon(url).getImage();
                                                                if (img.getWidth(null) < 300)                die = true;
                                                                if (img.getHeight(null) < 200)               die = true;
                                                            } catch (Exception e) {
                                                                die = true;
                                                            }
                                                            diveURLprogress.setValue(80);
                                                            if (!die) {
                                                                if (bounceIMG != null)
                                                                    for (int i = 0; i < bounceIMG.size(); i++) {
                                                                        if (image.attr("src").contains(bounceIMG.get(i))) {
                                                                            die = true;
                                                                            diveURLprogress.setValue(85);
                                                                        }
                                                                    }
                                                            }
                                                            diveURLprogress.setValue(90);
                                                            if (!die) {
                                                                position++;
                                                                diveURLprogress.setValue(100);
                                                                centralArea.append("\n   ( " + position + " )  ");
                                                                internalComparison mark = new internalComparison(centralArea);
                                                                omegaURL.add(mark);
                                                                mark.setTitle(src);
                                                                mark.setUrl(element);
                                                                mark.setImg(image.attr("src"));
                                                                mark.printMark();
                                                                breakMe = true;
                                                            }
                                                            die = false;
                                                        }
                                        }
                                }
                        }
            }
            try {
                int i = 1;
                for (internalComparison element : omegaURL) {
                    JButton masterListButton = new JButton(" ( " + i++ + " ) " + element.printButtton());
                    masterListButton.setBackground(Color.black);
                    masterListButton.setForeground(Color.white);
                    masterListButton.setFont(averagedFont);
                    masterListButton.setHorizontalAlignment(SwingConstants.LEFT);
                    centralComMasterJPanel.add(masterListButton);
                }
            } catch (Exception e) {
            }
        }
        timeout.clearTimer();
        return omegaURL;
    }

    public void restartApplication() throws URISyntaxException, IOException {
        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        final File currentJar = new File(MEETIS.class.getProtectionDomain().getCodeSource().getLocation().toURI());

  /* is it a jar file? */
        if(!currentJar.getName().endsWith(".jar"))
            return;

  /* Build command: java -jar application.jar */
        final ArrayList<String> command = new ArrayList<String>();
        command.add(javaBin);
        command.add("-jar");
        command.add(currentJar.getPath());

        final ProcessBuilder builder = new ProcessBuilder(command);
        builder.start();
        System.exit(0);
    }

    private int prioritizer(String URL,
                            ArrayList<internalComparison> omegaURL,
                            ArrayList<String> trends,
                            ArrayList<String> keywords,
                            JTextArea prioritizerTextArea,
                            JProgressBar progressBar
    ) {
        boolean foundDate = false;
        Random rand = new Random();
        int random = rand.nextInt(800);
        Document doc = null;
        int returnValue = 0;

        for (int k = 0; k < keywordArray.goodURL.length; k++) {
            if (URL.toLowerCase().contains(keywordArray.goodURL[k].toLowerCase())) {
                prioritizerTextArea.append(" ( Good URL + )( " + keywordArray.goodURL[k] + " )( +1500 )");
                returnValue += 10000;
            }
        }
        for (int i = 0; i < omegaURL.size(); i++) {
            if ((omegaURL.get(i).url.toLowerCase().contains(getMonth().toLowerCase() + "/" + getDay())) ||
                    (omegaURL.get(i).url.toLowerCase().contains(getMonth().toLowerCase() + "/" + getZeroDay())) ||
                    (omegaURL.get(i).url.toLowerCase().contains(getYear() + "/" + getMonth())) ||
                    (omegaURL.get(i).url.toLowerCase().contains(getYear() + "/" + getNumericMonth())) ||
                    (omegaURL.get(i).url.toLowerCase().contains(getMonth().toLowerCase() + "-" + getDay())) ||
                    (omegaURL.get(i).url.toLowerCase().contains(getMonth().toLowerCase() + "-" + getZeroDay())) ||
                    (omegaURL.get(i).url.toLowerCase().contains(getYear() + "-" + getMonth())) ||
                    (omegaURL.get(i).url.toLowerCase().contains(getYear() + "-" + getNumericMonth()))) {
                returnValue += 100;
                if ((omegaURL.get(i).url.contains(getYear() + "/" + getNumericMonth() + "/" + getDay())) ||
                        (omegaURL.get(i).url.contains(getYear() + "/" + getZeroNumericMonth() + "/" + getDay())) ||
                        (omegaURL.get(i).url.contains(getYear() + "/" + getNumericMonth() + "/" + getZeroDay())) ||
                        (omegaURL.get(i).url.contains(getYear() + "/" + getZeroNumericMonth() + "/" + getZeroDay()))) {
                    returnValue += 1200;
                    prioritizerTextArea.append(" ( " + getMonth().toUpperCase() + " " + getDay() + " )( +1200 )");
                }
            }
        }
        try {
            if (Jsoup.connect(URL).get() != null)
                doc = Jsoup.connect(URL).get();
            String[] bodyText;
            Elements links = doc.select("body");
            if (doc != null)
                for (Element el : links) {
                    String linkText = el.text();
                    bodyText = linkText.split(" ");
                    for (int i = 0; i < bodyText.length; i++) {
                        if (!foundDate) {
                            if (bodyText[i].toLowerCase().contains(getMonth())) {
                                for (int j = -1; j < 1; j++) {
                                    if (((i + j) > 0) && ((i + j) < bodyText.length)) {
                                        if (bodyText[(i + j)].toLowerCase().contains(getDay())) {
                                            prioritizerTextArea.append(" ( " + getMonth().toUpperCase() + " " + getDay() + " ) ( + 500 )");
                                            returnValue += 500;
                                            foundDate = true;
                                        }
                                    }
                                }
                            }
                        }
                        for (int k = 0; k < keywords.size(); k++) {
                            if (keywords.get(k).equals(bodyText[i])) {
                                prioritizerTextArea.setText(" ( *Keyword ) ( " + keywords.get(k) + " ) ( +" + random + " ) ");
                                returnValue += random;
                            }
                        }
                        for (int k = 0; k < trends.size(); k++) {
                            if (trends.get(k).equals(bodyText[i])) {
                                prioritizerTextArea.setText(" ( Twitter Trend ) ( " + trends.get(k) + " ) ( +1200 ) ");
                                returnValue += 1200;
                            }
                        }
                    }
                }
        } catch (IOException e) {
            prioritizerTextArea.append("    ( Jsoup.connect ) ( IOException )");
        }
        return returnValue;
    }

    public ArrayList<internalComparison> hashTag(ArrayList<internalComparison> list,
                                                 JProgressBar progressBar,
                                                 JTextArea textArea,
                                                 ArrayList<String> trends,
                                                 ArrayList<String> keywords) {
        boolean compoundPhrase = false;
        for (int k = 0; k < list.size(); k++) {
            boolean trending = false;
            String sTemp = list.get(k).getTitle();
            for (int i = 0; i < trends.size(); i++) {
                if (sTemp.toLowerCase().contains(trends.get(i))) {
                    int position = sTemp.toLowerCase().indexOf(trends.get(i));
                    while ((position > 0) && (!Objects.equals(Character.toString(sTemp.charAt(position - 1)), " "))) {
                        position--;
                    }
                    StringBuilder bTemp = new StringBuilder(sTemp);
                    bTemp.insert(position, '#');
                    textArea.append("\n   ( Twitter Trend )  ( " + bTemp + " ) ");
                    list.get(k).setTitle(bTemp.toString());
                    trending = true;
                }
            }
            if (!trending)
                for (int j = 0; j < keywordPhrase.size(); j++) {
                    if (sTemp.toLowerCase().contains(keywordPhrase.get(j))) {
                        compoundPhrase = true;
                        int position = sTemp.toLowerCase().indexOf(keywordPhrase.get(j));
                        StringBuilder bTemp = new StringBuilder(sTemp);
                        compoundPhraseFormat(bTemp, position, keywordPhrase.get(j).length());
                        textArea.append("\n     ( Keyword Phrase )  ( " + bTemp + " ) ");
                        list.get(k).setTitle(bTemp.toString());
                    }
                }
            if (!compoundPhrase)
                for (int i = 0; i < keywords.size(); i++) {
                    if (sTemp.toLowerCase().contains(keywords.get(i))) {
                            int position = sTemp.toLowerCase().indexOf(keywords.get(i));
                            while ((position > 0) && (!Objects.equals(Character.toString(sTemp.charAt(position - 1)), " "))) {
                                position--;
                            }
                            StringBuilder bTemp = new StringBuilder(sTemp);
                            bTemp.insert(position, '#');
                            textArea.append("\n   ( Keyword )  ( " + bTemp + " ) ");
                            list.get(k).setTitle(bTemp.toString());
                        }
                    }
            compoundPhrase = false;
        }
        return list;
    }

    public void timeLineRedundancy() {
        try {//  ----------------------------------  Back-check against twitter timeline (GetTimeline.java) ----->
            List<String> rawTimeline = trimHashtag(GetTimeline.timeline());
            currentCycle.setText("\n       Cross match against timeline");
            outputDataArea.append("\n          Cross match omegaURL against timeline \n");
            if (rawTimeline != null) {
                for (int k = 0; k < rawTimeline.size(); k++) {
                    outputDataArea.append("\n    " + rawTimeline.get(k));
                }

                for (int i = 0; i < omegaURL.size(); i++) {
                    for (String aRawTimeline : rawTimeline) {

                        if (trimHashtag(omegaURL.get(i).title).toLowerCase().substring(0, 10)
                                .contains(
                                        trimHashtag(aRawTimeline).toLowerCase().substring(0, 10)) ) {

                            outputDataArea.append("\n    ->   Found Something (*) " + trimHashtag(omegaURL.get(i).title).toLowerCase().substring(0, 10));
                            outputDataArea.append("\n     ->       (raw timeline) " + trimHashtag(aRawTimeline).toLowerCase().substring(0, 10) + "\n");
                            omegaURL.remove(i);

                        }
                    }
                }
            } else {
                outputDataArea.append("\n     rawTimeline == null ");
            }
        } catch (IOException e) {
            outputDataArea.append("\n          ( IOException )");
        } catch (TwitterException e) {
            outputDataArea.append("\n                             ( TwitterException )");
        } catch (IndexOutOfBoundsException e) {
            outputDataArea.append("\n           ( IndexOutOfBoundsException ) <--- Check this");
        }

    }
    public JPanel getPanel1() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int windowWidth = gd.getDisplayMode().getWidth();
        int windowHeight = gd.getDisplayMode().getHeight();
        panel1.setPreferredSize(new Dimension((windowWidth), (windowHeight)));
        return panel1;
    }
    private String trimHashtag(String hashedString) {
        String tTemp = hashedString;
        int x;
        if (hashedString.contains("#")) {
            int position = hashedString.indexOf("#");
            StringBuilder sTemp = new StringBuilder(hashedString);
            int y;
            sTemp.deleteCharAt(position);
            tTemp = sTemp.toString();
        }
        return tTemp;
    }
    private List<String> trimHashtag(List<String> twitterTrends) {
        List<String> tTemp = new ArrayList<>();
        int y;
        for (int i = 0; i < twitterTrends.size(); i++) {
            if (twitterTrends.get(i).contains("#")) {
                int position = twitterTrends.get(i).indexOf("#");
                StringBuilder sTemp = new StringBuilder(twitterTrends.get(i));
                int x;
                sTemp.deleteCharAt(position);
                tTemp.add(sTemp.toString());
            } else {
                tTemp.add(twitterTrends.get(i));
            }
        }
        return tTemp;
    }
    private List<String> trimHTTP(List<String> twitterTrends) {
        List<String> tTemp = new ArrayList<>();
        for (int i = 0; i < twitterTrends.size(); i++) {
            if (twitterTrends.get(i).toLowerCase().contains("http")) {
                int position = twitterTrends.get(i).indexOf("http");
                StringBuilder sTemp = new StringBuilder(twitterTrends.get(i));
                sTemp.delete((position - 1), sTemp.length());
                tTemp.add(sTemp.toString());
            } else {
                tTemp.add(twitterTrends.get(i));
            }
        }
        return tTemp;
    }
    public String getMonth() {
        Formatter fmt = new Formatter();
        Calendar cal = Calendar.getInstance();
        fmt = new Formatter();
        fmt.format("%tB", cal);
        return fmt.toString().toLowerCase();
    }
    public String getNumericMonth() {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.MONTH);
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        return dayOfMonthStr;
    }
    public String getZeroNumericMonth() {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.MONTH);
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        return ("0" + dayOfMonthStr);
    }
    public String getDay() {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        return dayOfMonthStr;
    }
    public String getZeroDay() {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        return ("0" + dayOfMonthStr);
    }
    public String getYear() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        String sYear = String.valueOf(year);
        return sYear;
    }
    private String getFileSuffix(final String path) {
        String result = null;
        if (path != null) {
            result = "";
            if (path.lastIndexOf('.') != -1) {
                result = path.substring(path.lastIndexOf('.'));
                if (result.startsWith(".")) {
                    result = result.substring(1);
                }
            }
        }
        return result;
    }
    private Dimension getImageDim(final String path) {
        Dimension result = null;
        try {
            String suffix = this.getFileSuffix(path);
            Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
            if (iter.hasNext()) {
                ImageReader reader = iter.next();
                ImageInputStream stream = new FileImageInputStream(new File(path));
                reader.setInput(stream);
                int width = reader.getWidth(reader.getMinIndex());
                int height = reader.getHeight(reader.getMinIndex());
                result = new Dimension(width, height);
            } else {
                //  log("No reader found for given format: " + suffix));
            }
        } catch (Exception e) {
            // ImageIO exception possible with CYMK
            // Convert from CYMK to RGB maybe?
        }
        return result;
    }
    public int inhibitorShort() throws InterruptedException {
        Random rand = new Random();
        int random = rand.nextInt(1000000);
        centralComDisplayMessage.append("\n              inhibitor triggered (*) \n                   "
                + random + " milliseconds                  " + random / 60000 + " minutes \n");
        return random;
    }
    public void FullAuto() {
        DefaultCaret caret1 = (DefaultCaret) outputDataArea.getCaret();
        caret1.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        outputDataArea.setLineWrap(true);

        fullAutoEngaged = true;
        centralComProgressBar.setIndeterminate(true);
        Thread primaryThread = new Thread() {
            public void run() {
                while (fullAutoEngaged) {
                    while (alphaURLbeingAccesed) {
                        try {
                            sleep(inhibitorShort());
                        } catch (InterruptedException e1) {
                        }
                    }
                    while (omegaURLbeingAccesed) {
                        try {
                            sleep(inhibitorShort());
                        } catch (InterruptedException e1) {
                        }
                    }
                    while (!updateReady) {
                        try {
                            sleep(inhibitorShort());
                        } catch (InterruptedException e1) {
                        }
                    }
                    do {
                        if (fullAutoEngaged)
                            if (!trendsExist) {
                                Thread thread = new Thread() {
                                    public void run() {
                                        trends = new GetTwitterTrends(centralComDisplayMessage);
                                        trendsExist = true;
                                        currentCycle.setText("trends = new GetTwitterTrends();");
                                        try {
                                            sleep(60000);
                                        } catch (InterruptedException e1) {
                                        }
                                    }
                                };
                                thread.start();
                            }
                        try {
                            sleep(10000);
                        } catch (InterruptedException e1) {
                        }
                        if (fullAutoEngaged)
                            if (!keywordsExists) {
                                Thread thread = new Thread() {
                                    public void run() {
                                        try {
                                            currentCycle.setText("updating Keywords");
                                            URL url = new URL("http://www.lukeholman.net/meetis/keywords.txt");
                                            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                                            String everything = "";
                                            String concatenate;
                                            while ((concatenate = in.readLine()) != null) {
                                                everything += concatenate;
                                            }
                                            in.close();
                                            String[] Keywords = everything.split("\", \"");
                                            keywords.removeAll(keywords);
                                            for (String element : Keywords) {
                                                keywords.add(element);
                                                centralComDisplayMessage.append(" ( " + element + " ) ");
                                            }
                                            if (!Keywords.equals(0) & !Keywords.equals(null)) {
                                                centralComDisplayMessage.append("\n\n                             ( Keyword Array Set* )\n\n");
                                                keywordsExists = true;
                                            }

                                        } catch (MalformedURLException e) {
                                            centralComDisplayMessage.setText("      Something went wrong... Keywords do not exist");
                                            keywordsExists = false;
                                        } catch (IOException e) {
                                            centralComDisplayMessage.setText("        Something went wrong... Keywords do not exist");
                                            keywordsExists = false;
                                        }
                                        try {
                                            currentCycle.setText("updating Keyword Phrase");
                                            URL url = new URL("http://www.lukeholman.net/meetis/keywordPhrase.txt");
                                            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                                            String everything = "";
                                            String concatenate;
                                            while ((concatenate = in.readLine()) != null) {
                                                everything += concatenate;
                                            }
                                            in.close();
                                            String[] Keywords = everything.split("\", \"");
                                            keywordPhrase.removeAll(keywordPhrase);
                                            for (String element : Keywords) {
                                                keywordPhrase.add(element);
                                                centralComDisplayMessage.append(" ( " + element + " ) ");
                                            }
                                            if (!keywordPhrase.equals(0) & !keywordPhrase.equals(null)) {
                                                centralComDisplayMessage.append("\n\n                                                  ( Keyword Phrase Array Set* )\n\n");
                                            }

                                        } catch (MalformedURLException e) {
                                            centralComDisplayMessage.setText("      Keyword Phrase went bust  ( MalformedURLException ) ");
                                        } catch (IOException e) {
                                            centralComDisplayMessage.setText("      Keyword Phrase went bust   ( IOException ) ");
                                        }
                                    }
                                };
                                thread.start();
                            }
                        try {
                            sleep(10000);
                        } catch (InterruptedException e1) {
                        }
                        if (fullAutoEngaged)
                            if (!bounceIMGExists) {
                                Thread thread = new Thread() {
                                    public void run() {
                                        try {
                                            currentCycle.setText("updating bounceIMG");
                                            URL url = new URL("http://www.lukeholman.net/meetis/bounceIMG.txt");
                                            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                                            String everything = "";
                                            String concatenate;
                                            while ((concatenate = in.readLine()) != null) {
                                                everything += concatenate;
                                            }
                                            in.close();
                                            String[] Keywords = everything.split("\", \"");
                                            bounceIMG.removeAll(bounceIMG);
                                            for (String element : Keywords) {
                                                bounceIMG.add(element);
                                                centralComDisplayMessage.append(" ( " + element + " ) ");
                                            }
                                            if (!Keywords.equals(0) & !Keywords.equals(null)) {
                                                centralComDisplayMessage.append("\n\n                                ( bounceIMG Array Set* )\n\n");
                                                bounceIMGExists = true;
                                            }

                                        } catch (MalformedURLException e) {
                                            centralComDisplayMessage.setText("         Something went wrong... bounceIMG do not exist");
                                            bounceIMGExists = false;
                                        } catch (IOException e) {
                                            centralComDisplayMessage.setText("     Something went wrong... bounceIMG do not exist");
                                            bounceIMGExists = false;
                                        }
                                    }
                                };
                                thread.start();
                            }
                        try {
                            sleep(10000);
                        } catch (InterruptedException e1) {
                        }
                        if (fullAutoEngaged)
                            if (!avoidURLExists) {
                                Thread thread = new Thread() {
                                    public void run() {
                                        try {
                                            currentCycle.setText("updating AvoidURL");
                                            URL url = new URL("http://www.lukeholman.net/meetis/avoidURL.txt");
                                            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                                            String everything = "";
                                            String concatenate;
                                            while ((concatenate = in.readLine()) != null) {
                                                everything += concatenate;
                                            }
                                            in.close();
                                            String[] Keywords = everything.split("\", \"");
                                            avoidURL.removeAll(avoidURL);
                                            for (String element : Keywords) {
                                                avoidURL.add(element);
                                                centralComDisplayMessage.append(" ( " + element + " ) ");
                                            }
                                            if (!Keywords.equals(0) & !Keywords.equals(null)) {
                                                centralComDisplayMessage.append("\n\n                                          ( AvoidURL Array Set* )\n\n");
                                                avoidURLExists = true;
                                            }

                                        } catch (MalformedURLException e) {
                                            centralComDisplayMessage.setText("      Something went wrong... bounceIMG do not exist");
                                            avoidURLExists = false;
                                        } catch (IOException e) {
                                            centralComDisplayMessage.setText("    Something went wrong... bounceIMG do not exist");
                                            avoidURLExists = false;
                                        }
                                    }
                                };
                                thread.start();
                            }
                        try {
                            sleep(10000);
                        } catch (InterruptedException e1) {
                        }
                        if (fullAutoEngaged)
                            if (!buildPointsExist) {
                                Thread thread = new Thread() {
                                    public void run() {
                                        try {
                                            currentCycle.setText("updating buildPoints");
                                            URL url = new URL("http://www.lukeholman.net/meetis/buildPoints.txt");
                                            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                                            String everything = "";
                                            String concatenate;
                                            while ((concatenate = in.readLine()) != null) {
                                                everything += concatenate;
                                            }
                                            in.close();
                                            String[] Keywords = everything.split(", ");
                                            buildPoints.removeAll(buildPoints);

                                            for (String element : Keywords) {
                                                buildPoints.add(element);
                                                centralComDisplayMessage.append("\n ( " + element + " ) ");
                                            }
                                            if (!Keywords.equals(0) & !Keywords.equals(null)) {
                                                centralComDisplayMessage.append("\n\n                                  ( Build Points Up* )\n\n");
                                                buildPointsExist = true;
                                            }
                                        } catch (MalformedURLException e) {
                                            centralComDisplayMessage.setText("     Something went wrong... buildPoints do not exist");
                                            buildPointsExist = false;
                                        } catch (IOException e) {
                                            centralComDisplayMessage.setText("      Something went wrong... buildPoints do not exist");
                                            buildPointsExist = false;
                                        }
                                    }
                                };
                                thread.start();
                                try {
                                    sleep(10000);
                                } catch (InterruptedException e1) {
                                }
                            }

                    }
                    while (!trendsExist || !keywordsExists || !bounceIMGExists || !avoidURLExists || !buildPointsExist);
                    if (updateReady) {
                        updateReady = false;
                        centralComProgressBar.setIndeterminate(false);
                        waitRandomShort(centralComProgressBar);
                        centralComProgressBar.setIndeterminate(true);
                        if (fullAutoEngaged) {
                            alphaOmegaThread = new Thread() {
                                public void run() {
                                    if (!alphaURLbeingAccesed) {
                                        try {
                                            centralComProgressBar.setIndeterminate(false);

                                            while (alphaURLbeingAccesed) {
                                                centralComDisplayMessage.append("\n            alphaURL being accessed... waiting...  ");
                                                try {
                                                    sleep(10000);
                                                } catch (InterruptedException e1) {
                                                }
                                            }
                                            currentCycle.setText("buildURL");
                                            ArrayList<String> tempArray = (ArrayList<String>) buildURL(centralComDisplayMessage, centralComProgressBar).clone();
                                            alphaURLbeingAccesed = true;
                                            alphaURL.removeAll(alphaURL);
                                            alphaURL = (ArrayList<String>) tempArray.clone();
                                            try {
                                                currentCycle.setText("updating alphaURL");
                                                String filename = "alphaURL.txt";
                                                FTPconnection ftPconnection = new FTPconnection(alphaURL, filename, outputDataArea);
                                                outputDataArea.append("\n            FTP upload Successful!  ");
                                            } catch (Exception e1) {
                                                outputDataArea.setText("\n            FTP upload alphaURL Failed...  ");
                                            }
                                            alphaURLbeingAccesed = false;
                                            centralComDisplayMessage.append("\n\n            I'm done now thank you!  \n\n");

                                        } finally {

                                            while (omegaURLbeingAccesed) {
                                                centralComDisplayMessage.append("\n\n                 preparing diveURL...  \n\n");
                                                centralComProgressBar.setIndeterminate(false);
                                                waitRandomShort(centralComProgressBar);
                                                centralComProgressBar.setIndeterminate(true);
                                            }
                                            omegaURLbeingAccesed = true;
                                            currentCycle.setText("diveURL");
                                            ArrayList<internalComparison> tempArray = (ArrayList<internalComparison>) diveURL(
                                                    centralComProgressBar,
                                                    centralComDisplayMessage,
                                                    centralComMasterJPanel,
                                                    trends,
                                                    omegaURL,
                                                    alphaURL,
                                                    keywords,
                                                    bounceIMG,
                                                    averagedFont).clone();
                                            omegaURL.removeAll(omegaURL);
                                            omegaURL = (ArrayList<internalComparison>) tempArray.clone();
                                            omegaURLbeingAccesed = false;
                                        }
                                        centralComProgressBar.setIndeterminate(true);
                                        centralComDisplayMessage.append("\n\n                  ( DiveURL complete )             preparing the prioritizer...  \n\n");
                                        try {
                                            sleep(10000);
                                        } catch (InterruptedException e1) {
                                        }
                                        updateReady = true;
                                    }
                                }
                            };
                            alphaOmegaThread.start();
                        }
                        while (alphaURLbeingAccesed) {
                            try {
                                sleep(inhibitorShort());
                            } catch (InterruptedException e1) {
                            }
                        }
                        while (omegaURLbeingAccesed) {
                            try {
                                sleep(inhibitorShort());
                            } catch (InterruptedException e1) {
                            }
                        }
                        while (!updateReady) {
                            try {
                                sleep(inhibitorShort());
                            } catch (InterruptedException e1) {
                            }
                        }
                        if (initUpdate) {
                            updateReady = false;
                            initUpdate = false;
                            omegaURLbeingAccesed = true;
                            Thread updateThread = new Thread() {
                                public void run() {
     // --------------------------------------------------------------------------------------------------------------------------------->
               // -------------------------------------------  Back-check against twitter timeline (GetTimeline.java) --------------------------->
                                    try {//  ------------------------------------------------------------------------------------>
                                        List<String> rawTimeline = trimHashtag(GetTimeline.timeline());
                                        currentCycle.setText("\n       Cross match against timeline");
                                        outputDataArea.append("\n          Cross match omegaURL against timeline \n");
                                        if (rawTimeline != null) {
                                            for (int k = 0; k < rawTimeline.size(); k++) {
                                                outputDataArea.append("\n    " + rawTimeline.get(k));
                                            }

                                            for (int i = 0; i < omegaURL.size(); i++) {
                                                for (String aRawTimeline : rawTimeline) {
                                                    if (omegaURL.get(i).title.length() > 19){
                                                        String tempOmega = omegaURL.get(i).title;

                                                        if (trimHashtag(tempOmega.replaceAll("\\s+","")).toLowerCase().substring(0, 20)
                                                                .contains(
                                                                        trimHashtag(aRawTimeline.replaceAll("\\s+","")).toLowerCase().substring(0, 20)) ) {

                                                            outputDataArea.append("\n    >   Found Something (*) " + trimHashtag(tempOmega.replaceAll("\\s+", "")).toLowerCase().substring(0, 20));
                                                            omegaURL.get(i).decreasePriority(999999);
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            outputDataArea.append("\n     rawTimeline == null ");
                                        }
                                    } catch (IOException e) {
                                        outputDataArea.append("\n          ( IOException )");
                                    } catch (TwitterException e) {
                                        outputDataArea.append("\n                             ( TwitterException )");
                                    } catch (IndexOutOfBoundsException e) {
                                        outputDataArea.append("\n           ( IndexOutOfBoundsException ) <--- Check this");
                                    }
               //---------------------------------------------------------------------------------------------------------------->
                             //----------------------------------------------------------------    Prioritizer   ------------>
      //---------------------------------------------------------------------------------------------------------------------------------->
                                    int a = 1;
                                    centralComMasterJPanel.removeAll();
                                    for (internalComparison element : omegaURL) {
                                        JButton masterListButton = new JButton(" ( " + a++ + " ) " + element.printButtton());
                                        masterListButton.setBackground(Color.black);
                                        masterListButton.setForeground(Color.white);
                                        masterListButton.setFont(averagedFont);
                                        masterListButton.setHorizontalAlignment(SwingConstants.LEFT);
                                        centralComMasterJPanel.add(masterListButton);
                                        centralComMasterJPanel.updateUI();
                                    }
                                    currentCycle.setText("Prioritizer");
                                    centralComProgressBar.setIndeterminate(false);
                                    for (int k = 0; k < omegaURL.size(); k++) {
                                        omegaURL.get(k).increasePriority(prioritizer(omegaURL.get(k).url, omegaURL, trends.getTrends(), keywords, centralComDisplayMessage, centralComProgressBar));
                                        centralComProgressBar.setValue((int) ((((double) k) / omegaURL.size()) * 100));
                                    }
                                    centralComProgressBar.setIndeterminate(true);
                                    currentCycle.setText("HASHTAG#");
                //---------------------------------------------------------------------------------------------------------------------------->
                           // ------------------------------------------------------------------    HASHTAG#    ----------------------------------------------------->
          //----------------------------------------------------------------------------------------------------------------------------------------------->
                                    omegaURL = (ArrayList<internalComparison>) hashTag(omegaURL, centralComProgressBar, centralComDisplayMessage, trends.getTrends(), keywords).clone();
                    //------------------------------------------------------------------------------------------------------------------------------------------------------>
                                    //                                                                      Implement badURL catcher
                                    currentCycle.setText("badURL catcher");
                                    for (int i = 0; i < omegaURL.size(); i++) {
                                        for (int k = 0; k < keywordArray.badURL.length; k++) {
                                            if (omegaURL.get(i).url.toLowerCase().contains(keywordArray.badURL[k].toLowerCase())) {
                                                centralComDisplayMessage.append("\n     ( Bad URL )( " + keywordArray.badURL[k] + " )( -10000 )");
                                                omegaURL.get(i).decreasePriority(99999);
                                            }
                                        }
                                    }
                                    Collections.sort(omegaURL);
                                    currentCycle.setText("Twitter Update");
                                    centralComDisplayMessage.setText("");
                                    int x = 1;
                                    centralComMasterJPanel.removeAll();
                                    for (internalComparison element : omegaURL) {
                                        JButton masterListButton = new JButton(" ( " + x++ + " ) " + element.printButtton());
                                        masterListButton.setBackground(Color.black);
                                        masterListButton.setForeground(Color.white);
                                        masterListButton.setFont(averagedFont);
                                        masterListButton.setHorizontalAlignment(SwingConstants.LEFT);
                                        centralComMasterJPanel.add(masterListButton);
                                        centralComMasterJPanel.updateUI();
                                    }
                                    centralComDisplayMessage.setText("          Twitter Update");
                                    // --------------------------------------------------------------------------------->
                                // -------------------------------------------------------------     Twitter update    -------------------------------->
                            // ---------------------------------------------------------------------------------------------------------->
                                    centralComProgressBar.setIndeterminate(false);
                                    centralComProgressBar.setValue(0);
                                    Random rand = new Random();
                                    int tweetAttempts = rand.nextInt(6);
                                    tweetAttempts += 3;
                                    int k = 0;
                                    for (int i = 0; i < tweetAttempts; i++) {
                                        try {
                                            UpdateStatus updateStatus = new UpdateStatus(omegaURL.get(k), outputDataArea);
                                            positiveTweets++;

                                        } catch (IOException e) {
                                            negativeTweets++;
                                            outputDataArea.append("\n      ( IOException )( Failure... )");
                                            outputDataArea.append("\n      ( " + omegaURL.get(k).title + " )");
                                            tweetAttempts++;
                                        } catch (TwitterException e) {
                                            negativeTweets++;
                                            outputDataArea.append("\n      ( Twitter didn't like that >_<?! )( Failure... )");
                                            outputDataArea.append("\n      ( " + omegaURL.get(k).title + " )");
                                            tweetAttempts++;
                                            try {
                                                UpdateStatus updateStatus = new UpdateStatus(omegaURL.get(k), outputDataArea, true);
                                                outputDataArea.append("\n      ( ^_____^*! )( second attempt succeeded )");
                                            } catch (IOException e1) {

                                            } catch (TwitterException e1) {
                                                outputDataArea.append("\n      ( >_<?# )( Failure... )( second attempt failed )");
                                                outputDataArea.append("\n      ( " + omegaURL.get(k).title + " )");
                                            }
                                        }

                                        k += 1;
                                        Random iterateRand = new Random();
                                        if (iterateRand.nextInt(10) % 2 != 0) {
                                            k += iterateRand.nextInt(3);
                                        }

                                        Random sleepRand = new Random();
                                        int sleepDuration = 6000;
                                        sleepDuration += sleepRand.nextInt(180000);
                                        for (int j = 0; j < sleepDuration; j++) {
                                            try {
                                                sleep(1);
                                            } catch (InterruptedException e1) {
                                                outputDataArea.append("\n      ( InterruptedException )");
                                            }
                                            centralComProgressBar.setValue((int) ((((double) j) / sleepDuration) * 100));
                                        }


                                    }
                                    currentCycle.setText("SQL update");
                                    centralComDisplayMessage.setText("");
                                    int y = 1;
                                    centralComMasterJPanel.removeAll();
                                    for (internalComparison element : omegaURL) {
                                        JButton masterListButton = new JButton(" ( " + y++ + " ) " + element.printButtton());
                                        masterListButton.setBackground(Color.black);
                                        masterListButton.setForeground(Color.white);
                                        masterListButton.setFont(averagedFont);
                                        masterListButton.setHorizontalAlignment(SwingConstants.LEFT);
                                        centralComMasterJPanel.add(masterListButton);
                                        centralComMasterJPanel.updateUI();
                                    }
                                    centralComDisplayMessage.setText("                                                             SQL update");
                        // ---------------------------------------------------------------------------------------------------------->
                                    //  ----------------------------------------------      SQL update     -------------------------------->
         // --------------------------------------------------------------------------------------------------------------------------------------->
                                    centralComProgressBar.setValue(0);
                                    Random any = new Random();
                                    int listAttempts = any.nextInt(30);
                                    listAttempts += 5;
                                    if (omegaURL.size() > listAttempts)
                                        for (int i = 0; i < listAttempts; i++) {
                                            try {
                                                SQLquery.exUpdate(omegaURL.get(i), outputDataArea);
                                                positiveSQL++;
                                                int sleepDuration = 0;
                                                sleepDuration += rand.nextInt(1500);
                                                for (int j = 0; j < sleepDuration; j++) {
                                                    sleep(1);
                                                    centralComProgressBar.setValue((int) ((((double) j) / sleepDuration) * 100));
                                                }
                                            } catch (InterruptedException e) {
                                                outputDataArea.append("\n      ( Exception )( InterruptedException )");
                                                outputDataArea.append("\n      (" + omegaURL.get(i).title + " )");
                                                listAttempts++;
                                            } catch (ClassNotFoundException e) {
                                                outputDataArea.append("\n     ( Exception )( ClassNotFoundException )");
                                                outputDataArea.append("\n     (" + omegaURL.get(i).title + " )");
                                                listAttempts++;
                                            } catch (SQLException e) {
                                                outputDataArea.append("\n      ( Exception )( SQLException )");
                                                outputDataArea.append("\n      (" + omegaURL.get(i).title + " )");
                                                listAttempts++;
                                            }
                                        }
                                    else {
                                        outputDataArea.append("\n      ( SQL List Failure )");
                                    }
                                    centralComProgressBar.setIndeterminate(true);
                                    centralComDisplayMessage.setText("");
                                    int z = 1;
                                    centralComMasterJPanel.removeAll();
                                    for (internalComparison element : omegaURL) {
                                        JButton masterListButton = new JButton(" ( " + z++ + " ) " + element.printButtton());
                                        masterListButton.setBackground(Color.black);
                                        masterListButton.setForeground(Color.white);
                                        masterListButton.setFont(averagedFont);
                                        masterListButton.setHorizontalAlignment(SwingConstants.LEFT);
                                        centralComMasterJPanel.add(masterListButton);
                                        centralComMasterJPanel.updateUI();
                                    }
                                    cycle++;
                                    centralComDisplayMessage.setText("\n         ( Completed Cycle ) ( " + cycle + " ) ");
                                    waitRandomShort(centralComProgressBar);
                                    omegaURLbeingAccesed = false;
                                    updateReady = true;
                                    initUpdate = true;
                                    trendsExist = false;
                                    avoidURLExists = false;
                                    bounceIMGExists = false;
                                    buildPointsExist = false;
                                    keywordsExists = false;
                                    currentCycle.setText("");
//============                                                        ==========================
//============                                                        ==========================
//============                                                        ==========================
//============                                                        ==========================
//============                                                        ==========================
                                    centralComDisplayMessage.setText("\n                      ( omegaURL will be cleared )");
                                    waitRandomShort(centralComProgressBar);
                                    centralComDisplayMessage.setText("");
                                    omegaURL.removeAll(omegaURL);
                                    alphaURL.removeAll(alphaURL);
                                }   // ----------------------------- run

                            };      // ----------------------------- Thread updateThread
                            updateThread.start();
                        }
                    } // --------------------------------------- if (updateReady)

                } // ------------------------------------------- while (fullAutoEngaged)
            }     // ---  run
        };
        primaryThread.start();
    }

}
