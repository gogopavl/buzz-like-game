/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import file_operations.FileOperator;
import file_operations.Matchup;
import game_package.Game;
import game_package.Player;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import questions_package.Question;
import questions_package.ImageQuestion;
import rounds_package.QuickAnswer;
import rounds_package.Round;
import rounds_package.StopTimer;
import rounds_package.Thermometer;

/**
 * Class implementing the graphical user interface of the application
 * @author desppapa
 * @author gogopavl
 */
public class BuzzGUI extends javax.swing.JFrame {
    
    Locale l = new Locale("en","US");    
    private static Game buzzGame;
    private int NUMBER_OF_ROUNDS = 0;
    private final int QUESTIONS_PER_ROUND = Game.getNUMBER_OF_QUESTIONS_PER_ROUND();
    private int roundCounter = 0;
    private int questionCounter = 0;
    private ArrayList<Round> roundsList;
    private ArrayList<Question> questionsList;
    private HashSet<String> imageQuestionTypes = new HashSet<String>();
    
    private long millisStart;
    private long millisEnd;
    

    /**
     * Creates new form BuzzGUI & initializes components
     */
    public BuzzGUI() {
        initComponents();       
        buzzGame = new Game(); // create new Game instance
        
        imageQuestionTypes.add("Cinema"); // question files with attached images
        imageQuestionTypes.add("Music");
        
    }
    /**
     * Method used to set the panel labels with questions.It also switches 
     * between panels (single player game and bet interface),
     * so that in case of a "Bet" round the users can bid
     * without seeing the question
     */
    public void setQuestionLabels() throws IOException{        
        if(questionCounter == QUESTIONS_PER_ROUND){ // question counter reaches limit
            Player p1 = buzzGame.getPlayers().get(0);
            Player p2 = buzzGame.getPlayers().get(1);
            
            if("Thermometer".equals(roundsList.get(roundCounter).getRoundType())){
                if(p1.getThermometerCounter() > p2.getThermometerCounter()){
                    p1.addPoints(1000*p1.getThermometerCounter());
                }else if(p1.getThermometerCounter() < p2.getThermometerCounter()){
                    p2.addPoints(1000*p2.getThermometerCounter());
                }
                else{
                    p1.addPoints(500*p1.getThermometerCounter());
                    p2.addPoints(500*p2.getThermometerCounter());
                }
                p1.setThermometerCounter(0);
                p2.setThermometerCounter(0);
            }
            roundCounter ++; // next round
            if(roundCounter == NUMBER_OF_ROUNDS){ // round counter reaches limit 
               this.label_Score.setText(this.label_Score.getText() + String.valueOf(buzzGame.getPlayers().get(0).getPoints()));
               FileOperator.writerFunc(buzzGame.getPlayers().get(0).getName(), buzzGame.getPlayers().get(0).getPoints());
               this.EndOfGame.setVisible(true); // display end of game panel
               this.SinglePlayerGame.setVisible(false); 
               roundCounter = 0; // reset counters
               questionCounter = 0;
               return;
            }else{
                questionsList = roundsList.get(roundCounter).getRoundQuestions(); // fetch next round questions
                questionCounter = 0;            
            }
        }
        Question currentQuestion = questionsList.get(questionCounter);
        
         if("Bet".equals(roundsList.get(roundCounter).getRoundType())){ // if round type is "Bet" don't show question at first
                this.SinglePlayerBet.setVisible(true);
                this.SinglePlayerGame.setVisible(false);
                this.label_SinglePlayerBetQuestionType.setText(currentQuestion.getType());
         }
         else{
            this.SinglePlayerGame.setVisible(true);
         }
        
        if(imageQuestionTypes.contains(currentQuestion.getType())){ // display ImageQuestion
            ImageQuestion currentImageQuestion = (ImageQuestion) questionsList.get(questionCounter);
            
            String sentence = currentImageQuestion.getSentence();
            
            this.label_RoundType.setText(roundsList.get(roundCounter).getRoundType());
            ImageIcon img;
            img = new ImageIcon(new ImageIcon("questions/"+currentImageQuestion.getImageName()).getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT));
            this.label_ImageContainer.setIcon(img);
            this.label_QuestionSentence.setText(sentence);
            this.radioButton_First.setText(currentImageQuestion.getPossibleAnswers()[0]);
            this.radioButton_Second.setText(currentImageQuestion.getPossibleAnswers()[1]);
            this.radioButton_Third.setText(currentImageQuestion.getPossibleAnswers()[2]);
            this.radioButton_Fourth.setText(currentImageQuestion.getPossibleAnswers()[3]);
        }
        else{ // display Question
            String sentence = currentQuestion.getSentence();
            this.label_RoundType.setText(roundsList.get(roundCounter).getRoundType());
            this.label_QuestionSentence.setText(sentence);
            ImageIcon img;
            img = new ImageIcon(new ImageIcon("questions/questionMark.png").getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT));
            this.label_ImageContainer.setIcon(img);
            this.radioButton_First.setText(currentQuestion.getPossibleAnswers()[0]);
            this.radioButton_Second.setText(currentQuestion.getPossibleAnswers()[1]);
            this.radioButton_Third.setText(currentQuestion.getPossibleAnswers()[2]);
            this.radioButton_Fourth.setText(currentQuestion.getPossibleAnswers()[3]);
        
        }
        if("Stop Timer".equals(roundsList.get(roundCounter).getRoundType())){
                this.label_RoundType.setText(roundsList.get(roundCounter).getRoundType()+" 5 secs left!");
                this.SinglePlayerGame.setVisible(true);
                millisStart = System.currentTimeMillis();
                millisEnd = System.currentTimeMillis() + 5000;
                this.SinglePlayerGame.setVisible(true);
         }
        
    }
    
    /**
     * Method used to set the panel labels with questions.It also switches 
     * between panels (single player game and bet interface),
     * so that in case of a "Bet" round the users can bid
     * without seeing the question
     */
    public void setQuestionLabelsMultiPlayer() throws IOException{        
        if(questionCounter == QUESTIONS_PER_ROUND){ // question counter reaches limit
            roundCounter ++; // next round
            if(roundCounter == NUMBER_OF_ROUNDS){ // round counter reaches limit 
               Player p1 = buzzGame.getPlayers().get(0);
               Player p2 = buzzGame.getPlayers().get(1);
               this.label_ScoreMulti.setText(p1.getName()+": "+p1.getPoints()+", "+p2.getName()+": "+p2.getPoints());
               FileOperator.writerFuncMulti(p1.getName(), p1.getPoints(), p2.getName(), p2.getPoints());
               this.EndOfGameMulti.setVisible(true); // display end of game panel
               this.MultiPlayerGame.setVisible(false); 
               roundCounter = 0; // reset counters
               questionCounter = 0;
               return;
            }else{
                questionsList = roundsList.get(roundCounter).getRoundQuestions(); // fetch next round questions
                questionCounter = 0;            
            }
        }
        Question currentQuestion = questionsList.get(questionCounter);
        
         if("Bet".equals(roundsList.get(roundCounter).getRoundType())){ // if round type is "Bet" don't show question at first
                this.MultiPlayerBet.setVisible(true);
                this.MultiPlayerGame.setVisible(false);
                this.MultiPlayerBet.requestFocus(true);
                this.MultiPlayerGame.requestFocus(false);
                this.label_MultiPlayerBetQuestionType.setText(currentQuestion.getType());
         }
         else{
            this.MultiPlayerGame.setVisible(true);
            this.MultiPlayerBet.requestFocus(false);
            this.MultiPlayerGame.requestFocus(true);
            
         }
        
        if(imageQuestionTypes.contains(currentQuestion.getType())){ // display ImageQuestion
            ImageQuestion currentImageQuestion = (ImageQuestion) questionsList.get(questionCounter);
            
            String sentence = currentImageQuestion.getSentence();
            
            this.label_RoundTypeMulti.setText(roundsList.get(roundCounter).getRoundType());
            ImageIcon img;
            img = new ImageIcon(new ImageIcon("questions/"+currentImageQuestion.getImageName()).getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT));
            this.label_ImageContainerMulti.setIcon(img);
            this.label_QuestionSentenceMulti.setText(sentence);
            this.label_QuestionMulti1.setText(currentImageQuestion.getPossibleAnswers()[0]);
            this.label_QuestionMulti2.setText(currentImageQuestion.getPossibleAnswers()[1]);
            this.label_QuestionMulti3.setText(currentImageQuestion.getPossibleAnswers()[2]);
            this.label_QuestionMulti4.setText(currentImageQuestion.getPossibleAnswers()[3]);
        }
        else{ // display Question
            String sentence = currentQuestion.getSentence();
            this.label_RoundTypeMulti.setText(roundsList.get(roundCounter).getRoundType());
            this.label_QuestionSentenceMulti.setText(sentence);
            ImageIcon img;
            img = new ImageIcon(new ImageIcon("questions/questionMark.png").getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT));
            this.label_ImageContainerMulti.setIcon(img);
            this.label_QuestionMulti1.setText(currentQuestion.getPossibleAnswers()[0]);
            this.label_QuestionMulti2.setText(currentQuestion.getPossibleAnswers()[1]);
            this.label_QuestionMulti3.setText(currentQuestion.getPossibleAnswers()[2]);
            this.label_QuestionMulti4.setText(currentQuestion.getPossibleAnswers()[3]);
        
        }
        if("Stop Timer".equals(roundsList.get(roundCounter).getRoundType())){
                this.label_RoundTypeMulti.setText(roundsList.get(roundCounter).getRoundType()+"...5''!");
                this.MultiPlayerGame.setVisible(true);
                millisStart = System.currentTimeMillis();
                millisEnd = millisStart + 5000;
         }
        if("Quick Answer".equals(roundsList.get(roundCounter).getRoundType())){
                millisStart = System.currentTimeMillis();
                millisEnd = millisStart;
         }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup_SinglePlayer = new javax.swing.ButtonGroup();
        buttonGroup_Bet = new javax.swing.ButtonGroup();
        Menu = new javax.swing.JPanel();
        button_NewGame = new javax.swing.JButton();
        button_Exit = new javax.swing.JButton();
        label_Welcome = new javax.swing.JLabel();
        button_GreekLanguage = new javax.swing.JButton();
        button_EnglishLanguage = new javax.swing.JButton();
        button_Highscores = new javax.swing.JButton();
        NewGame = new javax.swing.JPanel();
        label_NumberOfPlayers = new javax.swing.JLabel();
        comboBox_NumberOfPlayers = new javax.swing.JComboBox<>();
        button_Proceed = new javax.swing.JButton();
        button_NewGame_Back = new javax.swing.JButton();
        Highscores = new javax.swing.JPanel();
        label_HighSingle = new javax.swing.JLabel();
        button_Highscores_Back = new javax.swing.JButton();
        label_HighMulti = new javax.swing.JLabel();
        label_HighscoreMultiLabel = new javax.swing.JLabel();
        label_HighscoreSingleLabel = new javax.swing.JLabel();
        SinglePlayerName = new javax.swing.JPanel();
        label_SingleName = new javax.swing.JLabel();
        textField_SingleName = new javax.swing.JTextField();
        button_SinglePlay = new javax.swing.JButton();
        button_SinglePlay_Back = new javax.swing.JButton();
        label_NumberOfRounds = new javax.swing.JLabel();
        textField_NumberOfRounds = new javax.swing.JTextField();
        MultiPlayerNames = new javax.swing.JPanel();
        label_MultiName1 = new javax.swing.JLabel();
        label_MultiName2 = new javax.swing.JLabel();
        textField_MultiName1 = new javax.swing.JTextField();
        textField_Multi_NumberOfRounds = new javax.swing.JTextField();
        button_MultiPlay = new javax.swing.JButton();
        button_MultiPlay_Back = new javax.swing.JButton();
        label_Multi_NumberOfRounds = new javax.swing.JLabel();
        textField_MultiName2 = new javax.swing.JTextField();
        SinglePlayerBet = new javax.swing.JPanel();
        radioButton_Bet250 = new javax.swing.JRadioButton();
        radioButton_Bet500 = new javax.swing.JRadioButton();
        radioButton_Bet750 = new javax.swing.JRadioButton();
        radioButton_Bet1000 = new javax.swing.JRadioButton();
        label_Bet = new javax.swing.JLabel();
        label_SinglePlayerBetQuestionType = new javax.swing.JLabel();
        button_SinglePlayerBet_Proceed = new javax.swing.JButton();
        SinglePlayerGame = new javax.swing.JPanel();
        label_RoundType = new javax.swing.JLabel();
        label_ImageContainer = new javax.swing.JLabel();
        label_QuestionSentence = new javax.swing.JLabel();
        radioButton_First = new javax.swing.JRadioButton();
        radioButton_Second = new javax.swing.JRadioButton();
        radioButton_Third = new javax.swing.JRadioButton();
        radioButton_Fourth = new javax.swing.JRadioButton();
        button_SinglePlayerSubmit = new javax.swing.JButton();
        MultiPlayerBet = new javax.swing.JPanel();
        label_Bet1 = new javax.swing.JLabel();
        label_MultiPlayerBetQuestionType = new javax.swing.JLabel();
        button_MultiPlayerBet_Proceed = new javax.swing.JButton();
        label_Bet2 = new javax.swing.JLabel();
        label_Bet3 = new javax.swing.JLabel();
        label_Bet4 = new javax.swing.JLabel();
        label_Bet5 = new javax.swing.JLabel();
        label_Bet6 = new javax.swing.JLabel();
        label_Bet7 = new javax.swing.JLabel();
        label_Bet8 = new javax.swing.JLabel();
        label_Bet9 = new javax.swing.JLabel();
        MultiPlayerGame = new javax.swing.JPanel();
        label_RoundTypeMulti = new javax.swing.JLabel();
        label_ImageContainerMulti = new javax.swing.JLabel();
        label_QuestionSentenceMulti = new javax.swing.JLabel();
        button_MultiPlayerSubmit = new javax.swing.JButton();
        label_QuestionMulti1 = new javax.swing.JLabel();
        label_QuestionMulti2 = new javax.swing.JLabel();
        label_QuestionMulti3 = new javax.swing.JLabel();
        label_QuestionMulti4 = new javax.swing.JLabel();
        label_Player1Keys = new javax.swing.JLabel();
        label_Player2Keys = new javax.swing.JLabel();
        EndOfGame = new javax.swing.JPanel();
        label_Score = new javax.swing.JLabel();
        button_EndOfGame_Exit = new javax.swing.JButton();
        button_EndOfGame_Back = new javax.swing.JButton();
        label_EndMessage1 = new javax.swing.JLabel();
        EndOfGameMulti = new javax.swing.JPanel();
        label_ScoreMulti = new javax.swing.JLabel();
        button_EndOfGame_Exit_Multi = new javax.swing.JButton();
        button_EndOfGame_Back_Multi = new javax.swing.JButton();
        label_EndMessage_Multi = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(850, 700));
        setPreferredSize(new java.awt.Dimension(850, 700));
        getContentPane().setLayout(new java.awt.CardLayout());

        Menu.setBackground(new java.awt.Color(153, 204, 255));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("GUI/Bundle"); // NOI18N
        button_NewGame.setText(bundle.getString("BuzzGUI.button_NewGame.text")); // NOI18N
        button_NewGame.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_NewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_NewGameActionPerformed(evt);
            }
        });

        button_Exit.setText(bundle.getString("BuzzGUI.button_Exit.text")); // NOI18N
        button_Exit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_ExitActionPerformed(evt);
            }
        });

        label_Welcome.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_Welcome.setForeground(new java.awt.Color(255, 255, 255));
        label_Welcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Welcome.setText(bundle.getString("BuzzGUI.label_Welcome.text")); // NOI18N
        label_Welcome.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        button_GreekLanguage.setText(bundle.getString("BuzzGUI.button_GreekLanguage.text")); // NOI18N
        button_GreekLanguage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_GreekLanguageActionPerformed(evt);
            }
        });

        button_EnglishLanguage.setText(bundle.getString("BuzzGUI.button_EnglishLanguage.text")); // NOI18N
        button_EnglishLanguage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_EnglishLanguageActionPerformed(evt);
            }
        });

        button_Highscores.setText(bundle.getString("BuzzGUI.button_Highscores.text")); // NOI18N
        button_Highscores.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        button_Highscores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_HighscoresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MenuLayout = new javax.swing.GroupLayout(Menu);
        Menu.setLayout(MenuLayout);
        MenuLayout.setHorizontalGroup(
            MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLayout.createSequentialGroup()
                .addGap(212, 212, 212)
                .addGroup(MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_NewGame, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                    .addComponent(button_Exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_Highscores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(228, 228, 228))
            .addGroup(MenuLayout.createSequentialGroup()
                .addComponent(label_Welcome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(MenuLayout.createSequentialGroup()
                .addGap(298, 298, 298)
                .addComponent(button_GreekLanguage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(64, 64, 64)
                .addComponent(button_EnglishLanguage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(324, 324, 324))
        );
        MenuLayout.setVerticalGroup(
            MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLayout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(label_Welcome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(83, 83, 83)
                .addGroup(MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_GreekLanguage)
                    .addComponent(button_EnglishLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75)
                .addComponent(button_NewGame, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_Highscores, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(312, 312, 312))
        );

        MenuLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {button_EnglishLanguage, button_GreekLanguage});

        getContentPane().add(Menu, "panel_Menu");

        NewGame.setBackground(new java.awt.Color(255, 153, 255));

        label_NumberOfPlayers.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_NumberOfPlayers.setForeground(new java.awt.Color(255, 255, 255));
        label_NumberOfPlayers.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_NumberOfPlayers.setText(bundle.getString("BuzzGUI.label_NumberOfPlayers.text")); // NOI18N
        label_NumberOfPlayers.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        comboBox_NumberOfPlayers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));
        comboBox_NumberOfPlayers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBox_NumberOfPlayersActionPerformed(evt);
            }
        });

        button_Proceed.setText(bundle.getString("BuzzGUI.button_Proceed.text")); // NOI18N
        button_Proceed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_ProceedActionPerformed(evt);
            }
        });

        button_NewGame_Back.setText(bundle.getString("BuzzGUI.button_NewGame_Back.text")); // NOI18N
        button_NewGame_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_NewGame_BackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout NewGameLayout = new javax.swing.GroupLayout(NewGame);
        NewGame.setLayout(NewGameLayout);
        NewGameLayout.setHorizontalGroup(
            NewGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NewGameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_NumberOfPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(NewGameLayout.createSequentialGroup()
                .addGap(201, 201, 201)
                .addGroup(NewGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboBox_NumberOfPlayers, 0, 448, Short.MAX_VALUE)
                    .addComponent(button_NewGame_Back, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_Proceed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(241, 241, 241))
        );
        NewGameLayout.setVerticalGroup(
            NewGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NewGameLayout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(label_NumberOfPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(comboBox_NumberOfPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(button_Proceed, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(button_NewGame_Back, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                .addGap(180, 180, 180))
        );

        getContentPane().add(NewGame, "panel_NewGame");

        Highscores.setBackground(new java.awt.Color(0, 153, 51));

        label_HighSingle.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        label_HighSingle.setForeground(new java.awt.Color(255, 255, 255));
        label_HighSingle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_HighSingle.setText(bundle.getString("BuzzGUI.label_HighSingle.text")); // NOI18N
        label_HighSingle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        button_Highscores_Back.setText(bundle.getString("BuzzGUI.button_Highscores_Back.text")); // NOI18N
        button_Highscores_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_Highscores_BackActionPerformed(evt);
            }
        });

        label_HighMulti.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        label_HighMulti.setForeground(new java.awt.Color(255, 255, 255));
        label_HighMulti.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_HighMulti.setText(bundle.getString("BuzzGUI.label_HighMulti.text")); // NOI18N
        label_HighMulti.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        label_HighscoreMultiLabel.setForeground(new java.awt.Color(255, 255, 255));
        label_HighscoreMultiLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_HighscoreMultiLabel.setText(bundle.getString("BuzzGUI.label_HighscoreMultiLabel.text")); // NOI18N

        label_HighscoreSingleLabel.setForeground(new java.awt.Color(255, 255, 255));
        label_HighscoreSingleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_HighscoreSingleLabel.setText(bundle.getString("BuzzGUI.label_HighscoreSingleLabel.text")); // NOI18N

        javax.swing.GroupLayout HighscoresLayout = new javax.swing.GroupLayout(Highscores);
        Highscores.setLayout(HighscoresLayout);
        HighscoresLayout.setHorizontalGroup(
            HighscoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HighscoresLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button_Highscores_Back, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(292, 292, 292))
            .addGroup(HighscoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HighscoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_HighscoreSingleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(HighscoresLayout.createSequentialGroup()
                        .addGroup(HighscoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label_HighSingle, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE)
                            .addComponent(label_HighscoreMultiLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(label_HighMulti, javax.swing.GroupLayout.DEFAULT_SIZE, 870, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        HighscoresLayout.setVerticalGroup(
            HighscoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HighscoresLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(label_HighscoreSingleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_HighSingle, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(label_HighscoreMultiLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_HighMulti, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(button_Highscores_Back, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(201, 201, 201))
        );

        getContentPane().add(Highscores, "panel_Highscores");

        SinglePlayerName.setBackground(new java.awt.Color(255, 204, 51));

        label_SingleName.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_SingleName.setForeground(new java.awt.Color(255, 255, 255));
        label_SingleName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_SingleName.setText(bundle.getString("BuzzGUI.label_SingleName.text")); // NOI18N
        label_SingleName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        textField_SingleName.setToolTipText(bundle.getString("BuzzGUI.textField_SingleName.toolTipText")); // NOI18N

        button_SinglePlay.setText(bundle.getString("BuzzGUI.button_SinglePlay.text")); // NOI18N
        button_SinglePlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_SinglePlayActionPerformed(evt);
            }
        });

        button_SinglePlay_Back.setText(bundle.getString("BuzzGUI.button_SinglePlay_Back.text")); // NOI18N
        button_SinglePlay_Back.setToolTipText(bundle.getString("BuzzGUI.button_SinglePlay_Back.toolTipText")); // NOI18N
        button_SinglePlay_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_SinglePlay_BackActionPerformed(evt);
            }
        });

        label_NumberOfRounds.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_NumberOfRounds.setForeground(new java.awt.Color(255, 255, 255));
        label_NumberOfRounds.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_NumberOfRounds.setText(bundle.getString("BuzzGUI.label_NumberOfRounds.text")); // NOI18N
        label_NumberOfRounds.setToolTipText(bundle.getString("BuzzGUI.label_NumberOfRounds.toolTipText")); // NOI18N
        label_NumberOfRounds.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        textField_NumberOfRounds.setToolTipText(bundle.getString("BuzzGUI.textField_NumberOfRounds.toolTipText")); // NOI18N

        javax.swing.GroupLayout SinglePlayerNameLayout = new javax.swing.GroupLayout(SinglePlayerName);
        SinglePlayerName.setLayout(SinglePlayerNameLayout);
        SinglePlayerNameLayout.setHorizontalGroup(
            SinglePlayerNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_SingleName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(label_NumberOfRounds, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(SinglePlayerNameLayout.createSequentialGroup()
                .addGroup(SinglePlayerNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SinglePlayerNameLayout.createSequentialGroup()
                        .addGap(250, 250, 250)
                        .addComponent(textField_SingleName, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE))
                    .addGroup(SinglePlayerNameLayout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addComponent(textField_NumberOfRounds)
                        .addGap(1, 1, 1)))
                .addGap(271, 271, 271))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SinglePlayerNameLayout.createSequentialGroup()
                .addGap(201, 201, 201)
                .addComponent(button_SinglePlay, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                .addGap(46, 46, 46)
                .addComponent(button_SinglePlay_Back, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                .addGap(196, 196, 196))
        );
        SinglePlayerNameLayout.setVerticalGroup(
            SinglePlayerNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SinglePlayerNameLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(label_SingleName, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addGap(32, 32, 32)
                .addComponent(textField_SingleName, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                .addGap(34, 34, 34)
                .addComponent(label_NumberOfRounds, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(textField_NumberOfRounds, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addGap(84, 84, 84)
                .addGroup(SinglePlayerNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_SinglePlay, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                    .addComponent(button_SinglePlay_Back, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                .addGap(89, 89, 89))
        );

        getContentPane().add(SinglePlayerName, "panel_SinglePlayerName");

        MultiPlayerNames.setBackground(new java.awt.Color(255, 204, 51));

        label_MultiName1.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_MultiName1.setForeground(new java.awt.Color(255, 255, 255));
        label_MultiName1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_MultiName1.setText(bundle.getString("BuzzGUI.label_MultiName1.text")); // NOI18N

        label_MultiName2.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_MultiName2.setForeground(new java.awt.Color(255, 255, 255));
        label_MultiName2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_MultiName2.setText(bundle.getString("BuzzGUI.label_MultiName2.text")); // NOI18N

        button_MultiPlay.setText(bundle.getString("BuzzGUI.button_MultiPlay.text")); // NOI18N
        button_MultiPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_MultiPlayActionPerformed(evt);
            }
        });

        button_MultiPlay_Back.setText(bundle.getString("BuzzGUI.button_MultiPlay_Back.text")); // NOI18N
        button_MultiPlay_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_MultiPlay_BackActionPerformed(evt);
            }
        });

        label_Multi_NumberOfRounds.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_Multi_NumberOfRounds.setForeground(new java.awt.Color(255, 255, 255));
        label_Multi_NumberOfRounds.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Multi_NumberOfRounds.setText(bundle.getString("BuzzGUI.label_Multi_NumberOfRounds.text")); // NOI18N
        label_Multi_NumberOfRounds.setToolTipText(bundle.getString("BuzzGUI.label_Multi_NumberOfRounds.toolTipText")); // NOI18N
        label_Multi_NumberOfRounds.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout MultiPlayerNamesLayout = new javax.swing.GroupLayout(MultiPlayerNames);
        MultiPlayerNames.setLayout(MultiPlayerNamesLayout);
        MultiPlayerNamesLayout.setHorizontalGroup(
            MultiPlayerNamesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MultiPlayerNamesLayout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(button_MultiPlay, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                .addGap(47, 47, 47)
                .addComponent(button_MultiPlay_Back, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addGap(172, 172, 172))
            .addComponent(label_MultiName1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(label_MultiName2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(label_Multi_NumberOfRounds, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MultiPlayerNamesLayout.createSequentialGroup()
                .addGap(252, 252, 252)
                .addGroup(MultiPlayerNamesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MultiPlayerNamesLayout.createSequentialGroup()
                        .addComponent(textField_MultiName1, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                        .addGap(245, 245, 245))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MultiPlayerNamesLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(textField_MultiName2, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                        .addGap(243, 243, 243))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MultiPlayerNamesLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(textField_Multi_NumberOfRounds, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                        .addGap(244, 244, 244))))
        );
        MultiPlayerNamesLayout.setVerticalGroup(
            MultiPlayerNamesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MultiPlayerNamesLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(label_MultiName1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(textField_MultiName1, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(label_MultiName2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(textField_MultiName2, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_Multi_NumberOfRounds, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textField_Multi_NumberOfRounds, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addGap(41, 41, 41)
                .addGroup(MultiPlayerNamesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(button_MultiPlay, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                    .addComponent(button_MultiPlay_Back, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                .addGap(97, 97, 97))
        );

        getContentPane().add(MultiPlayerNames, "panel_MultiPlayerNames");

        SinglePlayerBet.setBackground(new java.awt.Color(102, 0, 204));

        radioButton_Bet250.setBackground(new java.awt.Color(102, 0, 204));
        buttonGroup_Bet.add(radioButton_Bet250);
        radioButton_Bet250.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        radioButton_Bet250.setForeground(new java.awt.Color(255, 255, 255));
        radioButton_Bet250.setText(bundle.getString("BuzzGUI.radioButton_Bet250.text")); // NOI18N

        radioButton_Bet500.setBackground(new java.awt.Color(102, 0, 204));
        buttonGroup_Bet.add(radioButton_Bet500);
        radioButton_Bet500.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        radioButton_Bet500.setForeground(new java.awt.Color(255, 255, 255));
        radioButton_Bet500.setText(bundle.getString("BuzzGUI.radioButton_Bet500.text")); // NOI18N

        radioButton_Bet750.setBackground(new java.awt.Color(102, 0, 204));
        buttonGroup_Bet.add(radioButton_Bet750);
        radioButton_Bet750.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        radioButton_Bet750.setForeground(new java.awt.Color(255, 255, 255));
        radioButton_Bet750.setText(bundle.getString("BuzzGUI.radioButton_Bet750.text")); // NOI18N

        radioButton_Bet1000.setBackground(new java.awt.Color(102, 0, 204));
        buttonGroup_Bet.add(radioButton_Bet1000);
        radioButton_Bet1000.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        radioButton_Bet1000.setForeground(new java.awt.Color(255, 255, 255));
        radioButton_Bet1000.setText(bundle.getString("BuzzGUI.radioButton_Bet1000.text")); // NOI18N

        label_Bet.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_Bet.setForeground(new java.awt.Color(255, 255, 255));
        label_Bet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Bet.setText(bundle.getString("BuzzGUI.label_Bet.text")); // NOI18N

        label_SinglePlayerBetQuestionType.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_SinglePlayerBetQuestionType.setForeground(new java.awt.Color(255, 255, 255));
        label_SinglePlayerBetQuestionType.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_SinglePlayerBetQuestionType.setText(bundle.getString("BuzzGUI.label_SinglePlayerBetQuestionType.text")); // NOI18N

        button_SinglePlayerBet_Proceed.setText(bundle.getString("BuzzGUI.button_SinglePlayerBet_Proceed.text")); // NOI18N
        button_SinglePlayerBet_Proceed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_SinglePlayerBet_ProceedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SinglePlayerBetLayout = new javax.swing.GroupLayout(SinglePlayerBet);
        SinglePlayerBet.setLayout(SinglePlayerBetLayout);
        SinglePlayerBetLayout.setHorizontalGroup(
            SinglePlayerBetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_Bet, javax.swing.GroupLayout.DEFAULT_SIZE, 890, Short.MAX_VALUE)
            .addComponent(label_SinglePlayerBetQuestionType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SinglePlayerBetLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(SinglePlayerBetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(radioButton_Bet250, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(radioButton_Bet500, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(radioButton_Bet750, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(radioButton_Bet1000, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(SinglePlayerBetLayout.createSequentialGroup()
                .addGap(291, 291, 291)
                .addComponent(button_SinglePlayerBet_Proceed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(294, 294, 294))
        );
        SinglePlayerBetLayout.setVerticalGroup(
            SinglePlayerBetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SinglePlayerBetLayout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(label_Bet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addComponent(label_SinglePlayerBetQuestionType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(71, 71, 71)
                .addComponent(radioButton_Bet250, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioButton_Bet500, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioButton_Bet750, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioButton_Bet1000, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(57, 57, 57)
                .addComponent(button_SinglePlayerBet_Proceed, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                .addGap(135, 135, 135))
        );

        getContentPane().add(SinglePlayerBet, "card8");

        SinglePlayerGame.setBackground(new java.awt.Color(204, 0, 51));

        label_RoundType.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_RoundType.setForeground(new java.awt.Color(255, 255, 255));
        label_RoundType.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_RoundType.setText(bundle.getString("BuzzGUI.label_RoundType.text")); // NOI18N

        label_ImageContainer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_ImageContainer.setMaximumSize(new java.awt.Dimension(300, 300));
        label_ImageContainer.setMinimumSize(new java.awt.Dimension(300, 300));

        label_QuestionSentence.setBackground(new java.awt.Color(255, 51, 51));
        label_QuestionSentence.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        label_QuestionSentence.setForeground(new java.awt.Color(255, 255, 255));
        label_QuestionSentence.setText(bundle.getString("BuzzGUI.label_QuestionSentence.text")); // NOI18N

        radioButton_First.setBackground(new java.awt.Color(204, 0, 51));
        buttonGroup_SinglePlayer.add(radioButton_First);
        radioButton_First.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        radioButton_First.setForeground(new java.awt.Color(255, 255, 255));
        radioButton_First.setText(bundle.getString("BuzzGUI.radioButton_First.text")); // NOI18N

        radioButton_Second.setBackground(new java.awt.Color(204, 0, 51));
        buttonGroup_SinglePlayer.add(radioButton_Second);
        radioButton_Second.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        radioButton_Second.setForeground(new java.awt.Color(255, 255, 255));
        radioButton_Second.setText(bundle.getString("BuzzGUI.radioButton_Second.text")); // NOI18N

        radioButton_Third.setBackground(new java.awt.Color(204, 0, 51));
        buttonGroup_SinglePlayer.add(radioButton_Third);
        radioButton_Third.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        radioButton_Third.setForeground(new java.awt.Color(255, 255, 255));
        radioButton_Third.setText(bundle.getString("BuzzGUI.radioButton_Third.text")); // NOI18N

        radioButton_Fourth.setBackground(new java.awt.Color(204, 0, 51));
        buttonGroup_SinglePlayer.add(radioButton_Fourth);
        radioButton_Fourth.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
        radioButton_Fourth.setForeground(new java.awt.Color(255, 255, 255));
        radioButton_Fourth.setText(bundle.getString("BuzzGUI.radioButton_Fourth.text")); // NOI18N

        button_SinglePlayerSubmit.setText(bundle.getString("BuzzGUI.button_SinglePlayerSubmit.text")); // NOI18N
        button_SinglePlayerSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_SinglePlayerSubmitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SinglePlayerGameLayout = new javax.swing.GroupLayout(SinglePlayerGame);
        SinglePlayerGame.setLayout(SinglePlayerGameLayout);
        SinglePlayerGameLayout.setHorizontalGroup(
            SinglePlayerGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_RoundType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SinglePlayerGameLayout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addGroup(SinglePlayerGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SinglePlayerGameLayout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(button_SinglePlayerSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                        .addGap(313, 313, 313))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SinglePlayerGameLayout.createSequentialGroup()
                        .addComponent(label_ImageContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
                        .addGap(187, 187, 187))))
            .addGroup(SinglePlayerGameLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(SinglePlayerGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_QuestionSentence, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(radioButton_Fourth, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(radioButton_Third, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(radioButton_Second, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(radioButton_First, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        SinglePlayerGameLayout.setVerticalGroup(
            SinglePlayerGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SinglePlayerGameLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(label_RoundType, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_ImageContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 283, Short.MAX_VALUE)
                .addGap(46, 46, 46)
                .addComponent(label_QuestionSentence, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioButton_First, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioButton_Second, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioButton_Third, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(radioButton_Fourth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_SinglePlayerSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addGap(31, 31, 31))
        );

        getContentPane().add(SinglePlayerGame, "panel_SinglePlayerGame");

        MultiPlayerBet.setBackground(new java.awt.Color(102, 0, 204));
        MultiPlayerBet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MultiPlayerBetKeyPressed(evt);
            }
        });

        label_Bet1.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_Bet1.setForeground(new java.awt.Color(255, 255, 255));
        label_Bet1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Bet1.setText(bundle.getString("BuzzGUI.label_Bet1.text")); // NOI18N

        label_MultiPlayerBetQuestionType.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_MultiPlayerBetQuestionType.setForeground(new java.awt.Color(255, 255, 255));
        label_MultiPlayerBetQuestionType.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_MultiPlayerBetQuestionType.setText(bundle.getString("BuzzGUI.label_MultiPlayerBetQuestionType.text")); // NOI18N

        button_MultiPlayerBet_Proceed.setText(bundle.getString("BuzzGUI.button_MultiPlayerBet_Proceed.text")); // NOI18N
        button_MultiPlayerBet_Proceed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_MultiPlayerBet_ProceedActionPerformed(evt);
            }
        });

        label_Bet2.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_Bet2.setForeground(new java.awt.Color(255, 255, 255));
        label_Bet2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Bet2.setText(bundle.getString("BuzzGUI.label_Bet2.text")); // NOI18N

        label_Bet3.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_Bet3.setForeground(new java.awt.Color(255, 255, 255));
        label_Bet3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Bet3.setText(bundle.getString("BuzzGUI.label_Bet3.text")); // NOI18N

        label_Bet4.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_Bet4.setForeground(new java.awt.Color(255, 255, 255));
        label_Bet4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Bet4.setText(bundle.getString("BuzzGUI.label_Bet4.text")); // NOI18N

        label_Bet5.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_Bet5.setForeground(new java.awt.Color(255, 255, 255));
        label_Bet5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Bet5.setText(bundle.getString("BuzzGUI.label_Bet5.text")); // NOI18N

        label_Bet6.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_Bet6.setForeground(new java.awt.Color(255, 255, 255));
        label_Bet6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Bet6.setText(bundle.getString("BuzzGUI.label_Bet6.text")); // NOI18N

        label_Bet7.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_Bet7.setForeground(new java.awt.Color(255, 255, 255));
        label_Bet7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Bet7.setText(bundle.getString("BuzzGUI.label_Bet7.text")); // NOI18N

        label_Bet8.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_Bet8.setForeground(new java.awt.Color(255, 255, 255));
        label_Bet8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Bet8.setText(bundle.getString("BuzzGUI.label_Bet8.text")); // NOI18N

        label_Bet9.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_Bet9.setForeground(new java.awt.Color(255, 255, 255));
        label_Bet9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Bet9.setText(bundle.getString("BuzzGUI.label_Bet9.text")); // NOI18N

        javax.swing.GroupLayout MultiPlayerBetLayout = new javax.swing.GroupLayout(MultiPlayerBet);
        MultiPlayerBet.setLayout(MultiPlayerBetLayout);
        MultiPlayerBetLayout.setHorizontalGroup(
            MultiPlayerBetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_Bet1, javax.swing.GroupLayout.DEFAULT_SIZE, 890, Short.MAX_VALUE)
            .addComponent(label_MultiPlayerBetQuestionType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(MultiPlayerBetLayout.createSequentialGroup()
                .addGap(291, 291, 291)
                .addComponent(button_MultiPlayerBet_Proceed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(294, 294, 294))
            .addGroup(MultiPlayerBetLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(MultiPlayerBetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MultiPlayerBetLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(label_Bet5, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(label_Bet8, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(MultiPlayerBetLayout.createSequentialGroup()
                        .addGroup(MultiPlayerBetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label_Bet4, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_Bet2, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label_Bet3, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(MultiPlayerBetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(label_Bet6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                            .addComponent(label_Bet7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(label_Bet9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        MultiPlayerBetLayout.setVerticalGroup(
            MultiPlayerBetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MultiPlayerBetLayout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addComponent(label_Bet1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addComponent(label_MultiPlayerBetQuestionType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(MultiPlayerBetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_Bet3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_Bet6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MultiPlayerBetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_Bet2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_Bet7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MultiPlayerBetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_Bet4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_Bet9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MultiPlayerBetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_Bet5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_Bet8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(44, 44, 44)
                .addComponent(button_MultiPlayerBet_Proceed, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                .addGap(135, 135, 135))
        );

        getContentPane().add(MultiPlayerBet, "card8");

        MultiPlayerGame.setBackground(new java.awt.Color(204, 0, 51));
        MultiPlayerGame.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MultiPlayerGameKeyPressed(evt);
            }
        });

        label_RoundTypeMulti.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_RoundTypeMulti.setForeground(new java.awt.Color(255, 255, 255));
        label_RoundTypeMulti.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_RoundTypeMulti.setText(bundle.getString("BuzzGUI.label_RoundTypeMulti.text")); // NOI18N

        label_ImageContainerMulti.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_ImageContainerMulti.setMaximumSize(new java.awt.Dimension(300, 300));
        label_ImageContainerMulti.setMinimumSize(new java.awt.Dimension(300, 300));

        label_QuestionSentenceMulti.setBackground(new java.awt.Color(255, 51, 51));
        label_QuestionSentenceMulti.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        label_QuestionSentenceMulti.setForeground(new java.awt.Color(255, 255, 255));
        label_QuestionSentenceMulti.setText(bundle.getString("BuzzGUI.label_QuestionSentenceMulti.text")); // NOI18N

        button_MultiPlayerSubmit.setText(bundle.getString("BuzzGUI.button_MultiPlayerSubmit.text")); // NOI18N
        button_MultiPlayerSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_MultiPlayerSubmitActionPerformed(evt);
            }
        });

        label_QuestionMulti1.setBackground(new java.awt.Color(255, 51, 51));
        label_QuestionMulti1.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        label_QuestionMulti1.setForeground(new java.awt.Color(255, 255, 255));
        label_QuestionMulti1.setText(bundle.getString("BuzzGUI.label_QuestionMulti1.text")); // NOI18N

        label_QuestionMulti2.setBackground(new java.awt.Color(255, 51, 51));
        label_QuestionMulti2.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        label_QuestionMulti2.setForeground(new java.awt.Color(255, 255, 255));
        label_QuestionMulti2.setText(bundle.getString("BuzzGUI.label_QuestionMulti2.text")); // NOI18N

        label_QuestionMulti3.setBackground(new java.awt.Color(255, 51, 51));
        label_QuestionMulti3.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        label_QuestionMulti3.setForeground(new java.awt.Color(255, 255, 255));
        label_QuestionMulti3.setText(bundle.getString("BuzzGUI.label_QuestionMulti3.text")); // NOI18N

        label_QuestionMulti4.setBackground(new java.awt.Color(255, 51, 51));
        label_QuestionMulti4.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        label_QuestionMulti4.setForeground(new java.awt.Color(255, 255, 255));
        label_QuestionMulti4.setText(bundle.getString("BuzzGUI.label_QuestionMulti4.text")); // NOI18N

        label_Player1Keys.setBackground(new java.awt.Color(255, 51, 51));
        label_Player1Keys.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        label_Player1Keys.setForeground(new java.awt.Color(255, 255, 255));
        label_Player1Keys.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Player1Keys.setText(bundle.getString("BuzzGUI.label_Player1Keys.text")); // NOI18N

        label_Player2Keys.setBackground(new java.awt.Color(255, 51, 51));
        label_Player2Keys.setFont(new java.awt.Font("Courier New", 1, 14)); // NOI18N
        label_Player2Keys.setForeground(new java.awt.Color(255, 255, 255));
        label_Player2Keys.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Player2Keys.setText(bundle.getString("BuzzGUI.label_Player2Keys.text")); // NOI18N

        javax.swing.GroupLayout MultiPlayerGameLayout = new javax.swing.GroupLayout(MultiPlayerGame);
        MultiPlayerGame.setLayout(MultiPlayerGameLayout);
        MultiPlayerGameLayout.setHorizontalGroup(
            MultiPlayerGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_RoundTypeMulti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MultiPlayerGameLayout.createSequentialGroup()
                .addGap(191, 191, 191)
                .addComponent(label_ImageContainerMulti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(187, 187, 187))
            .addGroup(MultiPlayerGameLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(label_QuestionSentenceMulti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(MultiPlayerGameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_QuestionMulti1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(MultiPlayerGameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MultiPlayerGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_QuestionMulti2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_QuestionMulti3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label_QuestionMulti4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MultiPlayerGameLayout.createSequentialGroup()
                .addGap(293, 293, 293)
                .addComponent(button_MultiPlayerSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(295, 295, 295))
            .addGroup(MultiPlayerGameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label_Player1Keys, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(label_Player2Keys, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        MultiPlayerGameLayout.setVerticalGroup(
            MultiPlayerGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MultiPlayerGameLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(label_RoundTypeMulti, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(label_ImageContainerMulti, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_QuestionSentenceMulti, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_QuestionMulti1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_QuestionMulti2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_QuestionMulti3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label_QuestionMulti4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MultiPlayerGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_Player1Keys, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(label_Player2Keys, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                .addGap(14, 14, 14)
                .addComponent(button_MultiPlayerSubmit, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(MultiPlayerGame, "panel_SinglePlayerGame");

        EndOfGame.setBackground(new java.awt.Color(0, 204, 153));

        label_Score.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_Score.setForeground(new java.awt.Color(255, 255, 255));
        label_Score.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_Score.setText(bundle.getString("BuzzGUI.label_Score.text")); // NOI18N

        button_EndOfGame_Exit.setText(bundle.getString("BuzzGUI.button_EndOfGame_Exit.text")); // NOI18N
        button_EndOfGame_Exit.setToolTipText(bundle.getString("BuzzGUI.button_EndOfGame_Exit.toolTipText")); // NOI18N
        button_EndOfGame_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_EndOfGame_ExitActionPerformed(evt);
            }
        });

        button_EndOfGame_Back.setText(bundle.getString("BuzzGUI.button_EndOfGame_Back.text")); // NOI18N
        button_EndOfGame_Back.setToolTipText(bundle.getString("BuzzGUI.button_EndOfGame_Back.toolTipText")); // NOI18N
        button_EndOfGame_Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_EndOfGame_BackActionPerformed(evt);
            }
        });

        label_EndMessage1.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_EndMessage1.setForeground(new java.awt.Color(255, 255, 255));
        label_EndMessage1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_EndMessage1.setText(bundle.getString("BuzzGUI.label_EndMessage1.text")); // NOI18N

        javax.swing.GroupLayout EndOfGameLayout = new javax.swing.GroupLayout(EndOfGame);
        EndOfGame.setLayout(EndOfGameLayout);
        EndOfGameLayout.setHorizontalGroup(
            EndOfGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_EndMessage1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(label_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EndOfGameLayout.createSequentialGroup()
                .addGap(270, 270, 270)
                .addGroup(EndOfGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_EndOfGame_Back, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                    .addComponent(button_EndOfGame_Exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(266, 266, 266))
        );
        EndOfGameLayout.setVerticalGroup(
            EndOfGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EndOfGameLayout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addComponent(label_EndMessage1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(40, 40, 40)
                .addComponent(label_Score, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(56, 56, 56)
                .addComponent(button_EndOfGame_Back, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addComponent(button_EndOfGame_Exit, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addGap(123, 123, 123))
        );

        getContentPane().add(EndOfGame, "card7");

        EndOfGameMulti.setBackground(new java.awt.Color(0, 204, 153));

        label_ScoreMulti.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_ScoreMulti.setForeground(new java.awt.Color(255, 255, 255));
        label_ScoreMulti.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_ScoreMulti.setText(bundle.getString("BuzzGUI.label_ScoreMulti.text")); // NOI18N

        button_EndOfGame_Exit_Multi.setText(bundle.getString("BuzzGUI.button_EndOfGame_Exit_Multi.text")); // NOI18N
        button_EndOfGame_Exit_Multi.setToolTipText(bundle.getString("BuzzGUI.button_EndOfGame_Exit_Multi.toolTipText")); // NOI18N
        button_EndOfGame_Exit_Multi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_EndOfGame_Exit_MultiActionPerformed(evt);
            }
        });

        button_EndOfGame_Back_Multi.setText(bundle.getString("BuzzGUI.button_EndOfGame_Back_Multi.text")); // NOI18N
        button_EndOfGame_Back_Multi.setToolTipText(bundle.getString("BuzzGUI.button_EndOfGame_Back_Multi.toolTipText")); // NOI18N
        button_EndOfGame_Back_Multi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_EndOfGame_Back_MultiActionPerformed(evt);
            }
        });

        label_EndMessage_Multi.setFont(new java.awt.Font("Courier New", 1, 36)); // NOI18N
        label_EndMessage_Multi.setForeground(new java.awt.Color(255, 255, 255));
        label_EndMessage_Multi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_EndMessage_Multi.setText(bundle.getString("BuzzGUI.label_EndMessage_Multi.text")); // NOI18N

        javax.swing.GroupLayout EndOfGameMultiLayout = new javax.swing.GroupLayout(EndOfGameMulti);
        EndOfGameMulti.setLayout(EndOfGameMultiLayout);
        EndOfGameMultiLayout.setHorizontalGroup(
            EndOfGameMultiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label_EndMessage_Multi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(label_ScoreMulti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EndOfGameMultiLayout.createSequentialGroup()
                .addGap(270, 270, 270)
                .addGroup(EndOfGameMultiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_EndOfGame_Back_Multi, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                    .addComponent(button_EndOfGame_Exit_Multi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(266, 266, 266))
        );
        EndOfGameMultiLayout.setVerticalGroup(
            EndOfGameMultiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EndOfGameMultiLayout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addComponent(label_EndMessage_Multi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(40, 40, 40)
                .addComponent(label_ScoreMulti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(56, 56, 56)
                .addComponent(button_EndOfGame_Back_Multi, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addComponent(button_EndOfGame_Exit_Multi, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addGap(123, 123, 123))
        );

        getContentPane().add(EndOfGameMulti, "card7");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void button_NewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_NewGameActionPerformed
        NewGame.setVisible(true);
        Menu.setVisible(false);
        this.label_NumberOfRounds.setText(this.label_NumberOfRounds.getText()+" (1-"+buzzGame.getMaxNumberOfRounds()+"):"); // so that the user can select how many rounds he wants to play
        this.label_Multi_NumberOfRounds.setText(this.label_Multi_NumberOfRounds.getText()+" (1-"+buzzGame.getMaxNumberOfRounds()+"):"); // same in multi-player mode
    }//GEN-LAST:event_button_NewGameActionPerformed

    private void button_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_ExitActionPerformed
        System.exit(0);
        dispose();
    }//GEN-LAST:event_button_ExitActionPerformed

    private void button_ProceedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_ProceedActionPerformed
        if(comboBox_NumberOfPlayers.getSelectedIndex() == 0){
            this.SinglePlayerName.setVisible(true);
            this.NewGame.setVisible(false);
            buzzGame.setNumberOfPlayers(1);
        }
        else if(comboBox_NumberOfPlayers.getSelectedIndex() == 1){
            this.MultiPlayerNames.setVisible(true);
            this.NewGame.setVisible(false);
            buzzGame.setNumberOfPlayers(2);
        }
    }//GEN-LAST:event_button_ProceedActionPerformed

    private void button_MultiPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_MultiPlayActionPerformed
        String player1Name = this.textField_MultiName1.getText();
        this.label_MultiName1.setText(player1Name);
        String player2Name = this.textField_MultiName2.getText();
        this.label_MultiName2.setText(player2Name);
        
        buzzGame.addPlayer(new Player(player1Name, 0));
        buzzGame.addPlayer(new Player(player2Name, 0));
        buzzGame.setNUMBER_OF_ROUND_TYPES(5);
        
        int numberOfRounds = Integer.parseInt(this.textField_Multi_NumberOfRounds.getText());
        
        if(!Game.validateInput(String.valueOf(numberOfRounds), buzzGame.getMaxNumberOfRounds())){
            if("gr".equals(l.getLanguage())){
                JOptionPane.showMessageDialog(rootPane, "Εισάγετε έναν έγκυρο αριθμό γύρων!");
            }else{
                JOptionPane.showMessageDialog(rootPane, "Please insert a valid number of rounds!");
            }
        }
        else{
            buzzGame.setNumberOfRounds(numberOfRounds);
            NUMBER_OF_ROUNDS = buzzGame.getNumberOfRounds();
            buzzGame.gameSetup();
            roundsList = buzzGame.getRounds();
            questionsList = roundsList.get(roundCounter).getRoundQuestions();
            try {
                setQuestionLabelsMultiPlayer();
            } catch (IOException ex) {
                Logger.getLogger(BuzzGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.MultiPlayerNames.setVisible(false);
            this.MultiPlayerGame.requestFocus();
        }
    }//GEN-LAST:event_button_MultiPlayActionPerformed

    private void button_NewGame_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_NewGame_BackActionPerformed
        this.Menu.setVisible(true);
        this.NewGame.setVisible(false);
    }//GEN-LAST:event_button_NewGame_BackActionPerformed

    private void button_SinglePlay_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_SinglePlay_BackActionPerformed
        this.NewGame.setVisible(true);
        this.SinglePlayerName.setVisible(false);
    }//GEN-LAST:event_button_SinglePlay_BackActionPerformed

    private void button_MultiPlay_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_MultiPlay_BackActionPerformed
        this.NewGame.setVisible(true);
        this.MultiPlayerNames.setVisible(false);
    }//GEN-LAST:event_button_MultiPlay_BackActionPerformed

    private void button_SinglePlayerSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_SinglePlayerSubmitActionPerformed
        int userInput = 0;
        if(this.radioButton_First.isSelected()){
            userInput = 1;
        }
        else if(this.radioButton_Second.isSelected()){
            userInput = 2;
        }
        else if(this.radioButton_Third.isSelected()){
            userInput = 3;
        }
        else if(this.radioButton_Fourth.isSelected()){
            userInput = 4;
        }
        int temp = 0;
        Question q = questionsList.get(questionCounter);
        
        if(roundsList.get(roundCounter).getRoundType().equals("Bet")){
            temp = roundsList.get(roundCounter).evaluateAnwser(q, userInput)*buzzGame.getPlayers().get(0).getBetBid();
            buzzGame.getPlayers().get(0).addPoints(temp);
        }
        else if(roundsList.get(roundCounter).getRoundType().equals("Stop Timer")){
            if(System.currentTimeMillis() < millisEnd){  
                StopTimer tempCurrentStopTimerRound;
                tempCurrentStopTimerRound = (StopTimer) roundsList.get(roundCounter);
                temp = tempCurrentStopTimerRound.evaluateAnwser(q, userInput, millisEnd - System.currentTimeMillis());
                buzzGame.getPlayers().get(0).addPoints(temp);
            }
            else{
                if("gr".equals(l.getLanguage())){
                    JOptionPane.showMessageDialog(rootPane, "Η απάντηση δεν έγινε δεκτή, καθώς ο χρόνος έληξε!");
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Time's up! Answer not accepted!");
                }
                this.MultiPlayerGame.requestFocus(true);
            }
        }
        else{
            temp = roundsList.get(roundCounter).evaluateAnwser(q, userInput);
            buzzGame.getPlayers().get(0).addPoints(temp);
        }
        
        this.buttonGroup_SinglePlayer.clearSelection();
        questionCounter++;
        try {
            setQuestionLabels();
        } catch (IOException ex) {
            Logger.getLogger(BuzzGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_button_SinglePlayerSubmitActionPerformed

    private void button_EndOfGame_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_EndOfGame_ExitActionPerformed
        System.exit(0);
        dispose();
    }//GEN-LAST:event_button_EndOfGame_ExitActionPerformed

    private void button_EndOfGame_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_EndOfGame_BackActionPerformed
        this.Menu.setVisible(true);
        this.EndOfGame.setVisible(false);
        this.textField_SingleName.setText("");
        this.textField_NumberOfRounds.setText("");
        buzzGame = new Game();
        
    }//GEN-LAST:event_button_EndOfGame_BackActionPerformed

    private void button_SinglePlayerBet_ProceedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_SinglePlayerBet_ProceedActionPerformed
        this.SinglePlayerGame.setVisible(true);
        this.SinglePlayerBet.setVisible(false);
        
        if(this.radioButton_Bet250.isSelected()){
            buzzGame.getPlayers().get(0).setBetBid(250);
        }
        else if(this.radioButton_Bet500.isSelected()){
            buzzGame.getPlayers().get(0).setBetBid(500);
        }
        else if(this.radioButton_Bet750.isSelected()){
            buzzGame.getPlayers().get(0).setBetBid(750);
        }
        else if(this.radioButton_Bet1000.isSelected()){
            buzzGame.getPlayers().get(0).setBetBid(1000);
        }
        this.buttonGroup_Bet.clearSelection();
    }//GEN-LAST:event_button_SinglePlayerBet_ProceedActionPerformed

    private void button_MultiPlayerSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_MultiPlayerSubmitActionPerformed
        
        Question q = questionsList.get(questionCounter);
        
        int temp1, temp2;
        
        if((buzzGame.getPlayers().get(0).getInput() == 0) || (buzzGame.getPlayers().get(1).getInput() == 0)){
                    if("gr".equals(l.getLanguage())){
                        JOptionPane.showMessageDialog(rootPane, "Και οι δύο παίκτες πρέπει να απαντήσουν!");
                    }else{
                        JOptionPane.showMessageDialog(rootPane, "Both players must commit their answer!");
                    }
                    this.MultiPlayerGame.requestFocus(true);
                    return;
        }else{        
            if(roundsList.get(roundCounter).getRoundType().equals("Bet")){

                    temp1 = roundsList.get(roundCounter).evaluateAnwser(q, buzzGame.getPlayers().get(0).getInput())*buzzGame.getPlayers().get(0).getBetBid();
                    temp2 = roundsList.get(roundCounter).evaluateAnwser(q, buzzGame.getPlayers().get(1).getInput())*buzzGame.getPlayers().get(1).getBetBid();
                    buzzGame.getPlayers().get(0).addPoints(temp1);
                    buzzGame.getPlayers().get(1).addPoints(temp2);
            }
            else if(roundsList.get(roundCounter).getRoundType().equals("Stop Timer")){ 
                    StopTimer tempCurrentStopTimerRound;
                    tempCurrentStopTimerRound = (StopTimer) roundsList.get(roundCounter);

                    temp1 = tempCurrentStopTimerRound.evaluateAnwser(q, buzzGame.getPlayers().get(0).getInput(), buzzGame.getPlayers().get(0).getMillisST());
                    temp2 = tempCurrentStopTimerRound.evaluateAnwser(q, buzzGame.getPlayers().get(1).getInput(), buzzGame.getPlayers().get(1).getMillisST());

                    buzzGame.getPlayers().get(0).addPoints(temp1);
                    buzzGame.getPlayers().get(1).addPoints(temp2);

                    buzzGame.getPlayers().get(0).setMillisST(0);
                    buzzGame.getPlayers().get(1).setMillisST(0);
            }
            else if(roundsList.get(roundCounter).getRoundType().equals("Quick Answer")){ 
                    QuickAnswer tempCurrentQuickAnswerRound;
                    tempCurrentQuickAnswerRound = (QuickAnswer) roundsList.get(roundCounter);

                    if(buzzGame.getPlayers().get(0).getMillisQA() < buzzGame.getPlayers().get(1).getMillisQA()){
                        temp1 = 1000*tempCurrentQuickAnswerRound.evaluateAnwser(q, buzzGame.getPlayers().get(0).getInput());
                        temp2 = 500*tempCurrentQuickAnswerRound.evaluateAnwser(q, buzzGame.getPlayers().get(1).getInput());
                    }else{
                        temp1 = 500*tempCurrentQuickAnswerRound.evaluateAnwser(q, buzzGame.getPlayers().get(0).getInput());
                        temp2 = 1000*tempCurrentQuickAnswerRound.evaluateAnwser(q, buzzGame.getPlayers().get(1).getInput());
                    }   

                    buzzGame.getPlayers().get(0).addPoints(temp1);
                    buzzGame.getPlayers().get(1).addPoints(temp2);

                    buzzGame.getPlayers().get(0).setMillisQA(0);
                    buzzGame.getPlayers().get(1).setMillisQA(0);
            }
            else if(roundsList.get(roundCounter).getRoundType().equals("Correct Answer")){ // correct answer
                    temp1 = roundsList.get(roundCounter).evaluateAnwser(q, buzzGame.getPlayers().get(0).getInput());
                    temp2 = roundsList.get(roundCounter).evaluateAnwser(q, buzzGame.getPlayers().get(1).getInput());

                    buzzGame.getPlayers().get(0).addPoints(temp1);
                    buzzGame.getPlayers().get(1).addPoints(temp2);
            }
        }
            
            
        buzzGame.getPlayers().get(0).setInput(0);
        buzzGame.getPlayers().get(1).setInput(0);
        
        buzzGame.getPlayers().get(0).setBetBid(0);
        buzzGame.getPlayers().get(1).setBetBid(0);
        questionCounter++;
        try {
            setQuestionLabelsMultiPlayer();
        } catch (IOException ex) {
            Logger.getLogger(BuzzGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_button_MultiPlayerSubmitActionPerformed

    private void button_MultiPlayerBet_ProceedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_MultiPlayerBet_ProceedActionPerformed
        // TODO add your handling code here:
        if((buzzGame.getPlayers().get(0).getBetBid() == 0) || ( buzzGame.getPlayers().get(1).getBetBid() == 0)){
            if("gr".equals(l.getLanguage())){
                JOptionPane.showMessageDialog(rootPane, "Και οι δύο παίκτες πρέπει να ποντάρουν!");
            }else{
                JOptionPane.showMessageDialog(rootPane, "Both players must place their bid!");
            }
            this.MultiPlayerBet.requestFocus(true);
        }else{
            //emfanise multi game
            this.MultiPlayerGame.setVisible(true);
            this.MultiPlayerGame.requestFocus(false);
            this.MultiPlayerGame.requestFocus(true);
            this.MultiPlayerBet.setVisible(false);
        }
    }//GEN-LAST:event_button_MultiPlayerBet_ProceedActionPerformed

    private void MultiPlayerBetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MultiPlayerBetKeyPressed
        // TODO add your handling code here:
        char tempKey = evt.getKeyChar();
        switch(tempKey){
            case 'q' :
                buzzGame.getPlayers().get(0).setBetBid(250);
                break;
            case 'w' :
                buzzGame.getPlayers().get(0).setBetBid(500);
                break;
            case 'e' :
                buzzGame.getPlayers().get(0).setBetBid(750);
                break;
            case 'r' :
                buzzGame.getPlayers().get(0).setBetBid(1000);
                break;
            case 'u' :
                buzzGame.getPlayers().get(1).setBetBid(250);
                break;
            case 'i' :
                buzzGame.getPlayers().get(1).setBetBid(500);
                break;
            case 'o' :
                buzzGame.getPlayers().get(1).setBetBid(750);
                break;
            case 'p' :
                buzzGame.getPlayers().get(1).setBetBid(1000);
                break;
            default:
                break;
            
        }

    }//GEN-LAST:event_MultiPlayerBetKeyPressed

    private void MultiPlayerGameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MultiPlayerGameKeyPressed

        char tempKey = evt.getKeyChar();
        
        switch(tempKey){
            case 'q' :
                buzzGame.getPlayers().get(0).setInput(1);
                buzzGame.getPlayers().get(0).setMillisQA(System.currentTimeMillis());
                if(millisEnd - System.currentTimeMillis() > 0){
                    buzzGame.getPlayers().get(0).setMillisST(millisEnd - System.currentTimeMillis());
                }
                else{
                    buzzGame.getPlayers().get(0).setMillisST(0);
                }
                break;
            case 'w' :
                buzzGame.getPlayers().get(0).setInput(2);
                buzzGame.getPlayers().get(0).setMillisQA(System.currentTimeMillis());
                if(millisEnd - System.currentTimeMillis() > 0){
                    buzzGame.getPlayers().get(0).setMillisST(millisEnd - System.currentTimeMillis());
                }
                else{
                    buzzGame.getPlayers().get(0).setMillisST(0);
                }
                break;
            case 'e' :
                buzzGame.getPlayers().get(0).setInput(3);
                buzzGame.getPlayers().get(0).setMillisQA(System.currentTimeMillis());
                if(millisEnd - System.currentTimeMillis() > 0){
                    buzzGame.getPlayers().get(0).setMillisST(millisEnd - System.currentTimeMillis());
                }
                else{
                    buzzGame.getPlayers().get(0).setMillisST(0);
                }
                break;
            case 'r' :
                buzzGame.getPlayers().get(0).setInput(4);
                buzzGame.getPlayers().get(0).setMillisQA(System.currentTimeMillis());
                if(millisEnd - System.currentTimeMillis() > 0){
                    buzzGame.getPlayers().get(0).setMillisST(millisEnd - System.currentTimeMillis());
                }
                else{
                    buzzGame.getPlayers().get(0).setMillisST(0);
                }
                break;
            case 'u' :
                buzzGame.getPlayers().get(1).setInput(1);
                buzzGame.getPlayers().get(1).setMillisQA(System.currentTimeMillis());
                if(millisEnd - System.currentTimeMillis() > 0){
                    buzzGame.getPlayers().get(1).setMillisST(millisEnd - System.currentTimeMillis());
                }
                else{
                    buzzGame.getPlayers().get(1).setMillisST(0);
                }
                break;
            case 'i' :
                buzzGame.getPlayers().get(1).setInput(2);
                buzzGame.getPlayers().get(1).setMillisQA(System.currentTimeMillis());
                if(millisEnd - System.currentTimeMillis() > 0){
                    buzzGame.getPlayers().get(1).setMillisST(millisEnd - System.currentTimeMillis());
                }
                else{
                    buzzGame.getPlayers().get(1).setMillisST(0);
                }
                break;
            case 'o' :
                buzzGame.getPlayers().get(1).setInput(3);
                buzzGame.getPlayers().get(1).setMillisQA(System.currentTimeMillis());
                if(millisEnd - System.currentTimeMillis() > 0){
                    buzzGame.getPlayers().get(1).setMillisST(millisEnd - System.currentTimeMillis());
                }
                else{
                    buzzGame.getPlayers().get(1).setMillisST(0);
                }
                break;
            case 'p' :
                buzzGame.getPlayers().get(1).setInput(4);
                buzzGame.getPlayers().get(1).setMillisQA(System.currentTimeMillis());
                if(millisEnd - System.currentTimeMillis() > 0){
                    buzzGame.getPlayers().get(1).setMillisST(millisEnd - System.currentTimeMillis());
                }
                else{
                    buzzGame.getPlayers().get(1).setMillisST(0);
                }
                break;
            default:
                break;
        }        
        
    }//GEN-LAST:event_MultiPlayerGameKeyPressed

    private void button_EndOfGame_Exit_MultiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_EndOfGame_Exit_MultiActionPerformed
        System.exit(0);
        dispose();
    }//GEN-LAST:event_button_EndOfGame_Exit_MultiActionPerformed

    private void button_EndOfGame_Back_MultiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_EndOfGame_Back_MultiActionPerformed
        this.Menu.setVisible(true);
        this.EndOfGameMulti.setVisible(false);
        this.textField_MultiName1.setText("");
        this.textField_MultiName2.setText("");
        this.textField_Multi_NumberOfRounds.setText("");
        buzzGame = new Game();
    }//GEN-LAST:event_button_EndOfGame_Back_MultiActionPerformed

    private void comboBox_NumberOfPlayersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBox_NumberOfPlayersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBox_NumberOfPlayersActionPerformed

    private void button_GreekLanguageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_GreekLanguageActionPerformed
        l = new Locale("gr", "GRE");
        ResourceBundle r = ResourceBundle.getBundle("GUI/Bundle", l);
        this.label_Welcome.setText(r.getString("BuzzGUI.label_Welcome.text"));
        this.button_NewGame.setText(r.getString("BuzzGUI.button_NewGame.text"));
        this.button_Exit.setText(r.getString("BuzzGUI.button_Exit.text"));
        this.label_NumberOfPlayers.setText(r.getString("BuzzGUI.label_NumberOfPlayers.text"));
        this.button_Proceed.setText(r.getString("BuzzGUI.button_Proceed.text"));
        this.button_NewGame_Back.setText(r.getString("BuzzGUI.button_NewGame_Back.text"));
        this.label_SingleName.setText(r.getString("BuzzGUI.label_SingleName.text"));
        this.label_NumberOfRounds.setText(r.getString("BuzzGUI.label_NumberOfRounds.text"));
        this.button_SinglePlay.setText(r.getString("BuzzGUI.button_SinglePlay.text"));
        this.button_SinglePlay_Back.setText(r.getString("BuzzGUI.button_SinglePlay_Back.text"));
        this.label_MultiName1.setText(r.getString("BuzzGUI.label_MultiName1.text"));
        this.label_MultiName2.setText(r.getString("BuzzGUI.label_MultiName2.text"));
        this.button_MultiPlay.setText(r.getString("BuzzGUI.button_MultiPlay.text"));
        this.button_MultiPlay_Back.setText(r.getString("BuzzGUI.button_MultiPlay_Back.text"));
        this.label_Bet.setText(r.getString("BuzzGUI.label_Bet.text"));
        this.label_SinglePlayerBetQuestionType.setText(r.getString("BuzzGUI.label_SinglePlayerBetQuestionType.text"));
        this.button_SinglePlayerBet_Proceed.setText(r.getString("BuzzGUI.button_SinglePlayerBet_Proceed.text"));
        this.label_RoundType.setText(r.getString("BuzzGUI.label_RoundType.text"));
        this.button_SinglePlayerSubmit.setText(r.getString("BuzzGUI.button_SinglePlayerSubmit.text"));
        this.label_Bet1.setText(r.getString("BuzzGUI.label_Bet1.text"));
        this.button_MultiPlayerBet_Proceed.setText(r.getString("BuzzGUI.button_MultiPlayerBet_Proceed.text"));
        this.label_RoundTypeMulti.setText(r.getString("BuzzGUI.label_RoundTypeMulti.text"));
        this.button_MultiPlayerSubmit.setText(r.getString("BuzzGUI.button_MultiPlayerSubmit.text"));
        this.label_EndMessage1.setText(r.getString("BuzzGUI.label_EndMessage1.text"));
        this.button_EndOfGame_Back.setText(r.getString("BuzzGUI.button_EndOfGame_Back.text"));
        this.button_EndOfGame_Exit.setText(r.getString("BuzzGUI.button_EndOfGame_Exit.text"));
        this.label_EndMessage_Multi.setText(r.getString("BuzzGUI.label_EndMessage_Multi.text"));
        this.button_EndOfGame_Back_Multi.setText(r.getString("BuzzGUI.button_EndOfGame_Back_Multi.text"));
        this.button_EndOfGame_Exit_Multi.setText(r.getString("BuzzGUI.button_EndOfGame_Exit_Multi.text"));
        this.label_Multi_NumberOfRounds.setText(r.getString("BuzzGUI.label_Multi_NumberOfRounds.text"));
        this.label_Score.setText(r.getString("BuzzGUI.label_Score.text"));
        this.label_HighscoreMultiLabel.setText(r.getString("BuzzGUI.label_HighscoreMultiLabel.text"));
        this.label_HighscoreSingleLabel.setText(r.getString("BuzzGUI.label_HighscoreSingleLabel.text"));
        this.button_Highscores_Back.setText(r.getString("BuzzGUI.button_Highscores_Back.text"));
        this.button_Highscores.setText(r.getString("BuzzGUI.button_Highscores.text"));
        
        
    }//GEN-LAST:event_button_GreekLanguageActionPerformed

    private void button_EnglishLanguageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_EnglishLanguageActionPerformed
        l = new Locale("en", "US");
        ResourceBundle r = ResourceBundle.getBundle("GUI/Bundle", l);
        this.label_Welcome.setText(r.getString("BuzzGUI.label_Welcome.text"));
        this.label_Welcome.setText(r.getString("BuzzGUI.label_Welcome.text"));
        this.button_NewGame.setText(r.getString("BuzzGUI.button_NewGame.text"));
        this.button_Exit.setText(r.getString("BuzzGUI.button_Exit.text"));
        this.label_NumberOfPlayers.setText(r.getString("BuzzGUI.label_NumberOfPlayers.text"));
        this.button_Proceed.setText(r.getString("BuzzGUI.button_Proceed.text"));
        this.button_NewGame_Back.setText(r.getString("BuzzGUI.button_NewGame_Back.text"));
        this.label_SingleName.setText(r.getString("BuzzGUI.label_SingleName.text"));
        this.label_NumberOfRounds.setText(r.getString("BuzzGUI.label_NumberOfRounds.text"));
        this.button_SinglePlay.setText(r.getString("BuzzGUI.button_SinglePlay.text"));
        this.button_SinglePlay_Back.setText(r.getString("BuzzGUI.button_SinglePlay_Back.text"));
        this.label_MultiName1.setText(r.getString("BuzzGUI.label_MultiName1.text"));
        this.label_MultiName2.setText(r.getString("BuzzGUI.label_MultiName2.text"));
        this.button_MultiPlay.setText(r.getString("BuzzGUI.button_MultiPlay.text"));
        this.button_MultiPlay_Back.setText(r.getString("BuzzGUI.button_MultiPlay_Back.text"));
        this.label_Bet.setText(r.getString("BuzzGUI.label_Bet.text"));
        this.label_SinglePlayerBetQuestionType.setText(r.getString("BuzzGUI.label_SinglePlayerBetQuestionType.text"));
        this.button_SinglePlayerBet_Proceed.setText(r.getString("BuzzGUI.button_SinglePlayerBet_Proceed.text"));
        this.label_RoundType.setText(r.getString("BuzzGUI.label_RoundType.text"));
        this.button_SinglePlayerSubmit.setText(r.getString("BuzzGUI.button_SinglePlayerSubmit.text"));
        this.label_Bet1.setText(r.getString("BuzzGUI.label_Bet1.text"));
        this.button_MultiPlayerBet_Proceed.setText(r.getString("BuzzGUI.button_MultiPlayerBet_Proceed.text"));
        this.label_RoundTypeMulti.setText(r.getString("BuzzGUI.label_RoundTypeMulti.text"));
        this.button_MultiPlayerSubmit.setText(r.getString("BuzzGUI.button_MultiPlayerSubmit.text"));
        this.label_EndMessage1.setText(r.getString("BuzzGUI.label_EndMessage1.text"));
        this.button_EndOfGame_Back.setText(r.getString("BuzzGUI.button_EndOfGame_Back.text"));
        this.button_EndOfGame_Exit.setText(r.getString("BuzzGUI.button_EndOfGame_Exit.text"));
        this.label_EndMessage_Multi.setText(r.getString("BuzzGUI.label_EndMessage_Multi.text"));
        this.button_EndOfGame_Back_Multi.setText(r.getString("BuzzGUI.button_EndOfGame_Back_Multi.text"));
        this.button_EndOfGame_Exit_Multi.setText(r.getString("BuzzGUI.button_EndOfGame_Exit_Multi.text"));
        this.label_Multi_NumberOfRounds.setText(r.getString("BuzzGUI.label_Multi_NumberOfRounds.text"));
        this.label_Score.setText(r.getString("BuzzGUI.label_Score.text"));
        this.label_HighscoreMultiLabel.setText(r.getString("BuzzGUI.label_HighscoreMultiLabel.text"));
        this.label_HighscoreSingleLabel.setText(r.getString("BuzzGUI.label_HighscoreSingleLabel.text"));
        this.button_Highscores_Back.setText(r.getString("BuzzGUI.button_Highscores_Back.text"));
        this.button_Highscores.setText(r.getString("BuzzGUI.button_Highscores.text"));
    }//GEN-LAST:event_button_EnglishLanguageActionPerformed

    private void button_HighscoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_HighscoresActionPerformed
        try {
            ArrayList<Player> list = FileOperator.readerFun();   
            String myString = "<html><center>";
            for(int i = list.size()-1 ; i >= list.size()-5 ; --i){
                myString += list.get(i).getName()+ " "+list.get(i).getPoints()+"<br>";
            }
            myString += "</center></html>";
            this.label_HighSingle.setText(myString);
            
            ArrayList<Matchup> multiList = FileOperator.readerFunMulti();
            String myStringMulti = "<html><center>";
            
            for(int i = multiList.size()-1 ; i >= multiList.size()-5 ; --i){
                myStringMulti += multiList.get(i).getP1().getName() +" "+ multiList.get(i).getP1().getPoints()+ " vs "+multiList.get(i).getP2().getName() +" "+ multiList.get(i).getP2().getPoints()+"<br>";
            }
            myStringMulti += "</center></html>";
            this.label_HighMulti.setText(myStringMulti);
            
            
            this.Highscores.setVisible(true);
            this.Menu.setVisible(false);
        } catch (IOException ex) {
            Logger.getLogger(BuzzGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_button_HighscoresActionPerformed

    private void button_SinglePlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_SinglePlayActionPerformed
        String playerName = this.textField_SingleName.getText();
        Player p = new Player(playerName, 0);
        buzzGame.addPlayer(p);
        buzzGame.setNUMBER_OF_ROUND_TYPES(3);

        int numberOfRounds = Integer.parseInt(this.textField_NumberOfRounds.getText());

        if(!Game.validateInput(String.valueOf(numberOfRounds), buzzGame.getMaxNumberOfRounds())){
            if("gr".equals(l.getLanguage())){
                JOptionPane.showMessageDialog(rootPane, "Εισάγετε έναν έγκυρο αριθμό γύρων!");
            }else{
                JOptionPane.showMessageDialog(rootPane, "Please insert a valid number of rounds!");
            }            
        }
        else{
            buzzGame.setNumberOfRounds(numberOfRounds);
            NUMBER_OF_ROUNDS = buzzGame.getNumberOfRounds();
            buzzGame.gameSetup();
            roundsList = buzzGame.getRounds();
            questionsList = roundsList.get(roundCounter).getRoundQuestions();
            try {
                setQuestionLabels();
            } catch (IOException ex) {
                Logger.getLogger(BuzzGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.SinglePlayerName.setVisible(false);

        }
    }//GEN-LAST:event_button_SinglePlayActionPerformed

    private void button_Highscores_BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_Highscores_BackActionPerformed
            this.Menu.setVisible(true);
            this.Highscores.setVisible(false);
    }//GEN-LAST:event_button_Highscores_BackActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BuzzGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuzzGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuzzGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuzzGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BuzzGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel EndOfGame;
    private javax.swing.JPanel EndOfGameMulti;
    private javax.swing.JPanel Highscores;
    private javax.swing.JPanel Menu;
    private javax.swing.JPanel MultiPlayerBet;
    private javax.swing.JPanel MultiPlayerGame;
    private javax.swing.JPanel MultiPlayerNames;
    private javax.swing.JPanel NewGame;
    private javax.swing.JPanel SinglePlayerBet;
    private javax.swing.JPanel SinglePlayerGame;
    private javax.swing.JPanel SinglePlayerName;
    private javax.swing.ButtonGroup buttonGroup_Bet;
    private javax.swing.ButtonGroup buttonGroup_SinglePlayer;
    private javax.swing.JButton button_EndOfGame_Back;
    private javax.swing.JButton button_EndOfGame_Back_Multi;
    private javax.swing.JButton button_EndOfGame_Exit;
    private javax.swing.JButton button_EndOfGame_Exit_Multi;
    private javax.swing.JButton button_EnglishLanguage;
    private javax.swing.JButton button_Exit;
    private javax.swing.JButton button_GreekLanguage;
    private javax.swing.JButton button_Highscores;
    private javax.swing.JButton button_Highscores_Back;
    private javax.swing.JButton button_MultiPlay;
    private javax.swing.JButton button_MultiPlay_Back;
    private javax.swing.JButton button_MultiPlayerBet_Proceed;
    private javax.swing.JButton button_MultiPlayerSubmit;
    private javax.swing.JButton button_NewGame;
    private javax.swing.JButton button_NewGame_Back;
    private javax.swing.JButton button_Proceed;
    private javax.swing.JButton button_SinglePlay;
    private javax.swing.JButton button_SinglePlay_Back;
    private javax.swing.JButton button_SinglePlayerBet_Proceed;
    private javax.swing.JButton button_SinglePlayerSubmit;
    private javax.swing.JComboBox<String> comboBox_NumberOfPlayers;
    private javax.swing.JLabel label_Bet;
    private javax.swing.JLabel label_Bet1;
    private javax.swing.JLabel label_Bet2;
    private javax.swing.JLabel label_Bet3;
    private javax.swing.JLabel label_Bet4;
    private javax.swing.JLabel label_Bet5;
    private javax.swing.JLabel label_Bet6;
    private javax.swing.JLabel label_Bet7;
    private javax.swing.JLabel label_Bet8;
    private javax.swing.JLabel label_Bet9;
    private javax.swing.JLabel label_EndMessage1;
    private javax.swing.JLabel label_EndMessage_Multi;
    private javax.swing.JLabel label_HighMulti;
    private javax.swing.JLabel label_HighSingle;
    private javax.swing.JLabel label_HighscoreMultiLabel;
    private javax.swing.JLabel label_HighscoreSingleLabel;
    private javax.swing.JLabel label_ImageContainer;
    private javax.swing.JLabel label_ImageContainerMulti;
    private javax.swing.JLabel label_MultiName1;
    private javax.swing.JLabel label_MultiName2;
    private javax.swing.JLabel label_MultiPlayerBetQuestionType;
    private javax.swing.JLabel label_Multi_NumberOfRounds;
    private javax.swing.JLabel label_NumberOfPlayers;
    private javax.swing.JLabel label_NumberOfRounds;
    private javax.swing.JLabel label_Player1Keys;
    private javax.swing.JLabel label_Player2Keys;
    private javax.swing.JLabel label_QuestionMulti1;
    private javax.swing.JLabel label_QuestionMulti2;
    private javax.swing.JLabel label_QuestionMulti3;
    private javax.swing.JLabel label_QuestionMulti4;
    private javax.swing.JLabel label_QuestionSentence;
    private javax.swing.JLabel label_QuestionSentenceMulti;
    private javax.swing.JLabel label_RoundType;
    private javax.swing.JLabel label_RoundTypeMulti;
    private javax.swing.JLabel label_Score;
    private javax.swing.JLabel label_ScoreMulti;
    private javax.swing.JLabel label_SingleName;
    private javax.swing.JLabel label_SinglePlayerBetQuestionType;
    private javax.swing.JLabel label_Welcome;
    private javax.swing.JRadioButton radioButton_Bet1000;
    private javax.swing.JRadioButton radioButton_Bet250;
    private javax.swing.JRadioButton radioButton_Bet500;
    private javax.swing.JRadioButton radioButton_Bet750;
    private javax.swing.JRadioButton radioButton_First;
    private javax.swing.JRadioButton radioButton_Fourth;
    private javax.swing.JRadioButton radioButton_Second;
    private javax.swing.JRadioButton radioButton_Third;
    private javax.swing.JTextField textField_MultiName1;
    private javax.swing.JTextField textField_MultiName2;
    private javax.swing.JTextField textField_Multi_NumberOfRounds;
    private javax.swing.JTextField textField_NumberOfRounds;
    private javax.swing.JTextField textField_SingleName;
    // End of variables declaration//GEN-END:variables
}
