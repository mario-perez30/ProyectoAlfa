
package view;


import java.awt.Color;

import java.awt.Dimension;
import java.awt.Insets;
import static utils.DataValidation.calculateNifLetter;
import static utils.DataValidation.isLetter;
import static utils.DataValidation.isNumber;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.awt.dnd.DropTarget;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

/**
 * Interface used to register a person. It is mandatory to enter at least the 
 * NIF and the name.
 * @author Francesc Perez
 * @version 1.1.0
 */
public class Insert extends javax.swing.JDialog {
    
    
    
    public Insert(java.awt.Frame parent, boolean modal) {
        super(parent, modal); 
        initComponents();
        DropPhotoListener d = new DropPhotoListener(photo, this);
        DropTarget dropTarget = new DropTarget(photo, d);
        insert.setEnabled(false);
        setPlaceholders();
        setDateButton();
    }
    
    public void setDateButton(){
        JButton dateButton = (JButton) ((JComponent) dateOfBirth).getComponent(1);
        dateButton.setText("Select a date");
        dateButton.setPreferredSize(new Dimension(100, 25)); // puedes ajustar tamaño
        dateButton.setMargin(new Insets(0, 5, 0, 5)); // margen interno opcional
        dateOfBirth.revalidate();
        dateOfBirth.repaint();
    }
    

    

    public void setPlaceholders(){
        name.setText("Enter full name");
        nif.setText("Enter NIF number, letter is calculated (e.g., 12345678)");
        phoneNumber.setText("Formats:+34 123 456 789, +1-800-555-1234, 123.456.7890, 123456789.");
        postalCode.setText("Format: 12345");
        name.setForeground(Color.GRAY);
 
        name.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (name.getText().equals("Enter full name")) {
                    name.setText("");
                    name.setForeground(Color.BLACK);
                }
            }
 
            @Override
            public void focusLost(FocusEvent e) {
                if (name.getText().isEmpty()) {
                    name.setForeground(Color.GRAY);
                    name.setText("Enter full name");
                }
            }
        });
        
        nif.setForeground(Color.GRAY);
 
        nif.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nif.getText().equals("Enter NIF number, letter is calculated (e.g., 12345678)")) {
                    nif.setText("");
                    nif.setForeground(Color.BLACK);
                }
            }
 
            @Override
            public void focusLost(FocusEvent e) {
                if (nif.getText().isEmpty()) {
                    nif.setForeground(Color.GRAY);
                    nif.setText("Enter NIF number, letter is calculated (e.g., 12345678)");
                }
            }
        });
        
        phoneNumber.setForeground(Color.GRAY);
 
        phoneNumber.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (phoneNumber.getText().equals("Formats:+34 123 456 789, +1-800-555-1234, 123.456.7890, 123456789.")) {
                    phoneNumber.setText("");
                    phoneNumber.setForeground(Color.BLACK);
                }
            }
 
            @Override
            public void focusLost(FocusEvent e) {
                if (phoneNumber.getText().isEmpty()) {
                    phoneNumber.setForeground(Color.GRAY);
                    phoneNumber.setText("Formats:+34 123 456 789, +1-800-555-1234, 123.456.7890, 123456789.");
                }
            }
        });
        
        postalCode.setForeground(Color.GRAY);
                postalCode.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (postalCode.getText().equals("Format: 12345")) {
                    postalCode.setText("");
                    postalCode.setForeground(Color.BLACK);
                }
            }
 
            @Override
            public void focusLost(FocusEvent e) {
                if (postalCode.getText().isEmpty()) {
                    postalCode.setForeground(Color.GRAY);
                    postalCode.setText("Format: 12345");
                }
            }
        });
    }

    public JButton getReset() {
        return reset;
    }

    public JButton getInsert() {
        return insert;
    }

    public JTextField getNam() {
        return name;
    }

    public JDatePicker getDateOfBirth() {
        return dateOfBirth;
    }
    
    public JTextField getphoneNumber() {
        return phoneNumber;
    }

    public JTextField getPostalCode() {
        return postalCode;
    }

    
    public JTextField getNif() {
        return nif;
    }

    public JLabel getPhoto() {
        return photo;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        insert = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        reset = new javax.swing.JButton();
        photo = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        nif = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dateOfBirth = new org.jdatepicker.JDatePicker();
        phoneNumber = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        phoneNumber1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        postalCode = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Insert - People v1.1.0");
        setMinimumSize(new java.awt.Dimension(810, 280));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        insert.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        insert.setText("INSERT");
        insert.setMaximumSize(new java.awt.Dimension(187, 33));
        insert.setMinimumSize(new java.awt.Dimension(187, 33));
        insert.setPreferredSize(new java.awt.Dimension(187, 33));
        insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(19, 24, 0, 0);
        getContentPane().add(insert, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Name");
        jLabel1.setMaximumSize(new java.awt.Dimension(100, 22));
        jLabel1.setMinimumSize(new java.awt.Dimension(100, 22));
        jLabel1.setPreferredSize(new java.awt.Dimension(100, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 41, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        name.setMaximumSize(new java.awt.Dimension(400, 22));
        name.setMinimumSize(new java.awt.Dimension(400, 22));
        name.setPreferredSize(new java.awt.Dimension(400, 22));
        name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nameKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nameKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 24, 0, 76);
        getContentPane().add(name, gridBagConstraints);

        reset.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        reset.setText("RESET");
        reset.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        reset.setMaximumSize(new java.awt.Dimension(187, 33));
        reset.setMinimumSize(new java.awt.Dimension(187, 33));
        reset.setPreferredSize(new java.awt.Dimension(187, 33));
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(19, 24, 0, 76);
        getContentPane().add(reset, gridBagConstraints);

        photo.setBackground(new java.awt.Color(255, 255, 255));
        photo.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        photo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        photo.setText("<html><center>DROP YOUR FILE HERE</center></br><br><center>PHOTO</center></br><br><center> <i>Supported format: PNG.</i></center></br><br><center><i>Max. size 64KB</i></center></html>");
        photo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        photo.setMaximumSize(new java.awt.Dimension(150, 135));
        photo.setMinimumSize(new java.awt.Dimension(150, 135));
        photo.setPreferredSize(new java.awt.Dimension(150, 135));
        photo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                photoMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.ipadx = 18;
        gridBagConstraints.ipady = 65;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(30, 41, 0, 0);
        getContentPane().add(photo, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("NIF");
        jLabel5.setMaximumSize(new java.awt.Dimension(100, 22));
        jLabel5.setMinimumSize(new java.awt.Dimension(100, 22));
        jLabel5.setPreferredSize(new java.awt.Dimension(100, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(30, 41, 0, 0);
        getContentPane().add(jLabel5, gridBagConstraints);

        nif.setMaximumSize(new java.awt.Dimension(400, 22));
        nif.setMinimumSize(new java.awt.Dimension(400, 22));
        nif.setPreferredSize(new java.awt.Dimension(400, 22));
        nif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nifKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nifKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nifKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(30, 24, 0, 76);
        getContentPane().add(nif, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Date of Birth");
        jLabel8.setMaximumSize(new java.awt.Dimension(150, 22));
        jLabel8.setMinimumSize(new java.awt.Dimension(150, 22));
        jLabel8.setPreferredSize(new java.awt.Dimension(150, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 41, 0, 0);
        getContentPane().add(jLabel8, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 8)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Author: francesc.perez@stucom.com - Version 1.1.0");
        jLabel2.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 570;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 76, 27, 76);
        getContentPane().add(jLabel2, gridBagConstraints);

        dateOfBirth.setToolTipText("");
        dateOfBirth.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        dateOfBirth.setMaximumSize(new java.awt.Dimension(350, 22));
        dateOfBirth.setMinimumSize(new java.awt.Dimension(350, 22));
        dateOfBirth.setPreferredSize(new java.awt.Dimension(350, 22));
        dateOfBirth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateOfBirthActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 24, 0, 76);
        getContentPane().add(dateOfBirth, gridBagConstraints);

        phoneNumber.setMaximumSize(new java.awt.Dimension(400, 22));
        phoneNumber.setMinimumSize(new java.awt.Dimension(400, 22));
        phoneNumber.setPreferredSize(new java.awt.Dimension(400, 22));
        phoneNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneNumberActionPerformed(evt);
            }
        });
        phoneNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                phoneNumberKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                phoneNumberKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 24, 0, 76);
        getContentPane().add(phoneNumber, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("Phone Number");
        jLabel10.setMaximumSize(new java.awt.Dimension(150, 22));
        jLabel10.setMinimumSize(new java.awt.Dimension(150, 22));
        jLabel10.setPreferredSize(new java.awt.Dimension(150, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 41, 0, 0);
        getContentPane().add(jLabel10, gridBagConstraints);

        phoneNumber1.setMaximumSize(new java.awt.Dimension(400, 22));
        phoneNumber1.setMinimumSize(new java.awt.Dimension(400, 22));
        phoneNumber1.setPreferredSize(new java.awt.Dimension(400, 22));
        phoneNumber1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneNumber1ActionPerformed(evt);
            }
        });
        phoneNumber1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                phoneNumber1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                phoneNumber1KeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 24, 0, 76);
        getContentPane().add(phoneNumber1, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Phone Number");
        jLabel11.setMaximumSize(new java.awt.Dimension(150, 22));
        jLabel11.setMinimumSize(new java.awt.Dimension(150, 22));
        jLabel11.setPreferredSize(new java.awt.Dimension(150, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 41, 0, 0);
        getContentPane().add(jLabel11, gridBagConstraints);

        postalCode.setMaximumSize(new java.awt.Dimension(400, 22));
        postalCode.setMinimumSize(new java.awt.Dimension(400, 22));
        postalCode.setPreferredSize(new java.awt.Dimension(400, 22));
        postalCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postalCodeActionPerformed(evt);
            }
        });
        postalCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                postalCodeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                postalCodeKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 24, 0, 76);
        getContentPane().add(postalCode, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setText("Postal code");
        jLabel13.setMaximumSize(new java.awt.Dimension(150, 22));
        jLabel13.setMinimumSize(new java.awt.Dimension(150, 22));
        jLabel13.setPreferredSize(new java.awt.Dimension(150, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 41, 0, 0);
        getContentPane().add(jLabel13, gridBagConstraints);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void showInsert() {
        if (!name.getText().isEmpty() && !nif.isEditable()) {
            insert.setEnabled(true);
        } else {
            insert.setEnabled(false);
        }
    }

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        nif.setEditable(true);
        name.setText("Enter full name");
        nif.setText("Enter NIF number, letter is calculated (e.g., 12345678)");
        phoneNumber.setText("Formats:+34 123 456 789, +1-800-555-1234, 123.456.7890, 123456789.");
        postalCode.setText("Format: 12345");
        
        name.setForeground(Color.GRAY);
        nif.setForeground(Color.GRAY);
        phoneNumber.setForeground(Color.GRAY);
        postalCode.setForeground(Color.GRAY);
        photo.setIcon(null);
        //We reset the calendar date to the current date ...
        LocalDate dateLocate = LocalDate.now();
        ZoneId systemTimeZone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = dateLocate.atStartOfDay(systemTimeZone);
        Date dateUtil = java.sql.Date.from(zonedDateTime.toInstant());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateUtil);
        
       
        //... but do not display it in the JDatePicker box
       
        insert.setEnabled(false);
    }//GEN-LAST:event_resetActionPerformed

    private void nifKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nifKeyTyped
        if (!isNumber(evt.getKeyChar()) && evt.getKeyChar() != KeyEvent.VK_BACK_SPACE && evt.getKeyChar() != KeyEvent.VK_DELETE) {
            JOptionPane.showMessageDialog(this, "Type only numbers [0-9]", this.getTitle(), JOptionPane.ERROR_MESSAGE);
            evt.consume();
        }
    }//GEN-LAST:event_nifKeyTyped

    private void nameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameKeyTyped
        if (!isLetter(evt.getKeyChar()) && evt.getKeyChar() != KeyEvent.VK_BACK_SPACE && evt.getKeyChar() != KeyEvent.VK_DELETE) {
            JOptionPane.showMessageDialog(this, "Type only uppercase or lowercase letters, hyphens, and whitespace.", this.getTitle(), JOptionPane.ERROR_MESSAGE);
            evt.consume();
        } else if (isLetter(evt.getKeyChar()) || evt.getKeyChar() == KeyEvent.VK_BACK_SPACE || evt.getKeyChar() == KeyEvent.VK_DELETE) {
            showInsert();
        }
    }//GEN-LAST:event_nameKeyTyped

    private void nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameKeyReleased
        showInsert();
    }//GEN-LAST:event_nameKeyReleased

    private void photoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_photoMouseClicked
        photo.setIcon(null);
    }//GEN-LAST:event_photoMouseClicked

    private void nifKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nifKeyReleased
        if (nif.getText().length() == 8) {
            nif.setText(calculateNifLetter(nif.getText()));
            nif.setEditable(false);
            showInsert();
        }
    }//GEN-LAST:event_nifKeyReleased

    private void nifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nifKeyPressed
        if (nif.getText().length() == 8) {
            evt.consume();
            nif.setText(calculateNifLetter(nif.getText()));
            nif.setEditable(false);
            showInsert();
        }
    }//GEN-LAST:event_nifKeyPressed

    private void insertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertActionPerformed
        
        
//       Pattern pattern = Pattern.compile("^\\+?[0-9]{1,4}?[-.\\s]?\\(?\\d{1,3}\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$", Pattern.CASE_INSENSITIVE);
//        Matcher matcher1 = pattern.matcher(getphoneNumber().getText());
        
//        Pattern postalCodePattern = Pattern.compile("^\\d{5}$");
 //       Matcher postalCodeMatcher = postalCodePattern.matcher(getPostalCode().getText());
        
//        boolean matchFound=matcher1.find();
//        
//        if(matchFound){
//            
//        }else{
//            JOptionPane.showMessageDialog(rootPane, "Phone Number not valid. Valid formats: +34 123 456 789, +1-800-555-1234, (123) 456-7890, 123.456.7890 and 123456789.");
//        }
        

        

        
        
        
    }//GEN-LAST:event_insertActionPerformed

    private void dateOfBirthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateOfBirthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateOfBirthActionPerformed

    private void phoneNumberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_phoneNumberKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneNumberKeyReleased

    private void phoneNumberKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_phoneNumberKeyTyped
        
    }//GEN-LAST:event_phoneNumberKeyTyped

    private void phoneNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneNumberActionPerformed

    private void phoneNumber1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneNumber1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneNumber1ActionPerformed

    private void phoneNumber1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_phoneNumber1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneNumber1KeyReleased

    private void phoneNumber1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_phoneNumber1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneNumber1KeyTyped

    private void postalCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postalCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_postalCodeActionPerformed

    private void postalCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_postalCodeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_postalCodeKeyReleased

    private void postalCodeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_postalCodeKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_postalCodeKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdatepicker.JDatePicker dateOfBirth;
    private javax.swing.JButton insert;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField name;
    private javax.swing.JTextField nif;
    private javax.swing.JTextField phoneNumber;
    private javax.swing.JTextField phoneNumber1;
    private javax.swing.JLabel photo;
    private javax.swing.JTextField postalCode;
    private javax.swing.JButton reset;
    // End of variables declaration//GEN-END:variables
}