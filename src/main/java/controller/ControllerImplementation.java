package controller;

import model.entity.Person;
import model.entity.PersonException;
import model.dao.DAOArrayList;
import model.dao.DAOFile;
import model.dao.DAOFileSerializable;
import model.dao.DAOHashMap;
import model.dao.DAOJPA;
import model.dao.DAOSQL;
import model.dao.IDAO;
import start.Routes;
import view.DataStorageSelection;
import view.Delete;
import view.Insert;
import view.Menu;
import view.Read;
import view.ReadAll;
import view.Update;
import view.Count;
import view.LogIn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jdatepicker.DateModel;
import utils.Constants;

/**
 * This class starts the visual part of the application and programs and manages
 * all the events that it can receive from it. For each event received the
 * controller performs an action.
 *
 * @author Francesc Perez
 * @version 1.1.0
 */
public class ControllerImplementation implements IController, ActionListener {

    //Instance variables used so that both the visual and model parts can be 
    //accessed from the Controller.
    private final DataStorageSelection dSS;
    private IDAO dao;
    private Menu menu;
    private LogIn login;
    private Insert insert;
    private Read read;
    private Delete delete;
    private Update update;
    private ReadAll readAll;
    private Count count;

    public static String[] loggedUser;

    /**
     * This constructor allows the controller to know which data storage option
     * the user has chosen.Schedule an event to deploy when the user has made
     * the selection.
     *
     * @param dSS
     */
    public ControllerImplementation(DataStorageSelection dSS) {
        this.dSS = dSS;
        ((JButton) (dSS.getAccept()[0])).addActionListener(this);
    }

    /**
     * With this method, the application is started, asking the user for the
     * chosen storage system.
     */
    @Override
    public void start() {
        dSS.setVisible(true);
    }

    /**
     * This receives method handles the events of the visual part. Each event
     * has an associated action.
     *
     * @param e The event generated in the visual part
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == dSS.getAccept()[0]) {
            handleDataStorageSelection();
        } else if (e.getSource() == menu.getCount()) {

            handleCount();

        } else if (e.getSource() == menu.getInsert()) {
            handleInsertAction();

        } else if (insert != null && e.getSource() == insert.getInsert()) {
            handleInsertPerson();

        } else if (e.getSource() == menu.getRead()) {
            handleReadAction();

        } else if (read != null && e.getSource() == read.getRead()) {
            handleReadPerson();

        } else if (e.getSource() == menu.getDelete()) {
            handleDeleteAction();

        } else if (delete != null && e.getSource() == delete.getDelete()) {
            handleDeletePerson();

        } else if (e.getSource() == menu.getUpdate()) {
            handleUpdateAction();

        } else if (update != null && e.getSource() == update.getRead()) {
            handleReadForUpdate();

        } else if (update != null && e.getSource() == update.getUpdate()) {
            handleUpdatePerson();

        } else if (e.getSource() == menu.getReadAll()) {
            handleReadAll();

        } else if (e.getSource() == menu.getDeleteAll()) {
            handleDeleteAll();

        }

    }

    private void handleDataStorageSelection() {
        String daoSelected = ((javax.swing.JCheckBox) (dSS.getAccept()[1])).getText();
        dSS.dispose();
        switch (daoSelected) {
            case Constants.storageArrayList:
                dao = new DAOArrayList();
                break;
            case Constants.storageHashMap:
                dao = new DAOHashMap();
                break;
            case Constants.storageFile:
                setupFileStorage();
                break;
            case Constants.storageFileSerialization:
                setupFileSerialization();
                break;
            case Constants.storageSQL:
                setupSQLDatabase();
                break;
            case Constants.storageJPA:
                setupJPADatabase();
                break;
        }
        if (setupLogin()) {
            setupMenu();
        } else {
            JOptionPane.showMessageDialog(null, "Log in Cancelled");
            System.exit(0);
        }
    }

    private boolean setupLogin() {

        JFrame fakeMain = new JFrame();
        fakeMain.setUndecorated(true);
        fakeMain.setSize(0, 0);
        fakeMain.setLocationRelativeTo(null);
        fakeMain.setVisible(true);

        login = new LogIn(fakeMain);
        login.setLocationRelativeTo(null);
        login.setVisible(true);
        fakeMain.dispose();
        return login.isLoginSuccessful();

    }

    private void setupFileStorage() {
        File folderPath = new File(Routes.FILE.getFolderPath());
        File folderPhotos = new File(Routes.FILE.getFolderPhotos());
        File dataFile = new File(Routes.FILE.getDataFile());
        folderPath.mkdir();
        folderPhotos.mkdir();
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(dSS, "File structure not created. Closing application.", "File - People v1.1.0", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
        dao = new DAOFile();
    }

    private void setupFileSerialization() {
        File folderPath = new File(Routes.FILES.getFolderPath());
        File dataFile = new File(Routes.FILES.getDataFile());
        folderPath.mkdir();
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(dSS, "File structure not created. Closing application.", "FileSer - People v1.1.0", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
        dao = new DAOFileSerializable();
    }

    private void setupSQLDatabase() {
        try {
            Connection conn = DriverManager.getConnection(Routes.DB.getDbServerAddress() + Routes.DB.getDbServerComOpt(),
                    Routes.DB.getDbServerUser(), Routes.DB.getDbServerPassword());
            if (conn != null) {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("create database if not exists " + Routes.DB.getDbServerDB() + ";");
                stmt.executeUpdate("create table if not exists " + Routes.DB.getDbServerDB() + "." + Routes.DB.getDbServerTABLE() + "("
                        + "nif varchar(9) primary key not null, "
                        + "name varchar(50), "
                        + "dateOfBirth DATE, "
                        + "phoneNumber varchar(50), "
                        + "postalCOde int, "
                        + "photo varchar(200) );");
                stmt.close();
                conn.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(dSS, "SQL-DDBB structure not created. Closing application.", "SQL_DDBB - People v1.1.0", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        dao = new DAOSQL();
    }

    private void setupJPADatabase() {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(Routes.DBO.getDbServerAddress());
            EntityManager em = emf.createEntityManager();
            em.close();
            emf.close();
        } catch (PersistenceException ex) {
            JOptionPane.showMessageDialog(dSS, "JPA_DDBB not created. Closing application.", "JPA_DDBB - People v1.1.0", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        dao = new DAOJPA();
    }

    private void setupMenu() {
        menu = new Menu(loggedUser);
        menu.setVisible(true);
        menu.getInsert().addActionListener(this);
        menu.getRead().addActionListener(this);
        menu.getUpdate().addActionListener(this);
        menu.getDelete().addActionListener(this);
        menu.getReadAll().addActionListener(this);
        menu.getDeleteAll().addActionListener(this);
        menu.getCount().addActionListener(this);

    }

    private void handleInsertAction() {
        if (loggedUser[2].equalsIgnoreCase("employee")) {
            JOptionPane.showMessageDialog(menu, "Employees cannot access this option.");

        } else {
            insert = new Insert(menu, true);
            insert.getInsert().addActionListener(this);
            insert.setVisible(true);
        }
    }

    private void handleInsertPerson() {
        String postalText = insert.getPostalCode().getText().trim();
        Pattern pattern = Pattern.compile("^\\+?[0-9]{1,4}?[-.\\s]?\\(?\\d{1,3}\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(insert.getphoneNumber().getText());
        Pattern postalCodePattern = Pattern.compile("^\\d{5}$");

        boolean postalMatchFound = postalCodePattern.matcher(postalText).matches();
        boolean matchFound = matcher.find();

        if (matchFound && postalMatchFound) {

            Person p = new Person(insert.getNam().getText(), insert.getNif().getText(), insert.getphoneNumber().getText(), Integer.parseInt(insert.getPostalCode().getText()));
            if (insert.getDateOfBirth().getModel().getValue() != null) {
                p.setDateOfBirth(((GregorianCalendar) insert.getDateOfBirth().getModel().getValue()).getTime());
            }
            if (insert.getPhoto().getIcon() != null) {
                p.setPhoto((ImageIcon) insert.getPhoto().getIcon());
            }
            insert(p);
            insert.getReset().doClick();
        } else {
            if (!postalMatchFound) {
                JOptionPane.showMessageDialog(insert, "Postal code not valid. Valid format: 12345");
            }
            if (!matchFound) {
                JOptionPane.showMessageDialog(insert, "Phone Number not valid. Valid formats: +34 123 456 789, +1-800-555-1234, (123) 456-7890, 123.456.7890 and 123456789.");
            }
        }

    }

    private void handleReadAction() {
        read = new Read(menu, true);
        read.getRead().addActionListener(this);
        read.setVisible(true);
    }

    private void handleReadPerson() {
        Person p = new Person(read.getNif().getText());
        Person pNew = read(p);
        if (pNew != null) {
            read.getNam().setText(pNew.getName());
            if (pNew.getDateOfBirth() != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(pNew.getDateOfBirth());
                DateModel<Calendar> dateModel = (DateModel<Calendar>) read.getDateOfBirth().getModel();
                dateModel.setValue(calendar);
            }
            read.getPhoneNumber().setText(pNew.getPhoneNumber());
            read.getPostalCode().setText(pNew.getPostalCode() + "");
            //To avoid charging former images
            if (pNew.getPhoto() != null) {
                pNew.getPhoto().getImage().flush();
                read.getPhoto().setIcon(pNew.getPhoto());
            }
        } else {
            JOptionPane.showMessageDialog(read, p.getNif() + " doesn't exist.", read.getTitle(), JOptionPane.WARNING_MESSAGE);
            read.getReset().doClick();
        }
    }

    public void handleDeleteAction() {
        if (loggedUser[2].equalsIgnoreCase("employee")) {
            JOptionPane.showMessageDialog(menu, "Employees cannot access this option.");

        } else {
            delete = new Delete(menu, true);
            delete.getDelete().addActionListener(this);
            delete.setVisible(true);
        }
    }

    public void handleDeletePerson() {
        if (delete != null) {
            if (delete != null) {
                Person p = new Person(delete.getNif().getText());

                Person pToDelete = read(p);

                if (pToDelete == null) {
                    JOptionPane.showMessageDialog(delete, "Person does not exist.", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(delete, "Are you sure you want to delete this person?", "WARNING", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {

                    delete(pToDelete);

                    JOptionPane.showMessageDialog(delete, "Person deleted successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);

                    delete.getReset().doClick();
                }
            }
        }
    }

    public void handleUpdateAction() {
        if (loggedUser[2].equalsIgnoreCase("employee")) {
            JOptionPane.showMessageDialog(menu, "Employees cannot access this option.");

        } else {
            update = new Update(menu, true);
            update.getUpdate().addActionListener(this);
            update.getRead().addActionListener(this);
            update.setVisible(true);
        }
    }

    public void handleReadForUpdate() {
        if (update != null) {
            Person p = new Person(update.getNif().getText());
            Person pNew = read(p);

            if (pNew != null) {
                update.getNam().setEnabled(true);
                update.getDateOfBirth().setEnabled(true);
                update.getPhoneNumber().setEnabled(true);
                update.getPostalCode().setEnabled(true);
                update.getPhoto().setEnabled(true);
                update.getUpdate().setEnabled(true);
                update.getNam().setText(pNew.getName());
                update.getPhoneNumber().setText(pNew.getPhoneNumber());
                update.getPostalCode().setText(pNew.getPostalCode() + "");
                if (pNew.getDateOfBirth() != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(pNew.getDateOfBirth());
                    DateModel<Calendar> dateModel = (DateModel<Calendar>) update.getDateOfBirth().getModel();
                    dateModel.setValue(calendar);
                }
                if (pNew.getPhoto() != null) {
                    pNew.getPhoto().getImage().flush();
                    update.getPhoto().setIcon(pNew.getPhoto());
                    update.getUpdate().setEnabled(true);
                }
            } else {
                JOptionPane.showMessageDialog(update, p.getNif() + " doesn't exist.", update.getTitle(), JOptionPane.WARNING_MESSAGE);
                update.getReset().doClick();
            }
        }
    }

    public void handleUpdatePerson() {
        String postalText = update.getPostalCode().getText().trim();
        Pattern pattern = Pattern.compile("^\\+?[0-9]{1,4}?[-.\\s]?\\(?\\d{1,3}\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(update.getPhoneNumber().getText());
        Pattern postalCodePattern = Pattern.compile("^\\d{5}$");

        boolean postalMatchFound = postalCodePattern.matcher(postalText).matches();

        boolean matchFound = matcher.find();

        if (matchFound && postalMatchFound) {
            int postalCode = Integer.parseInt(update.getPostalCode().getText());
            if (update != null) {
                Person p = new Person(update.getNam().getText(), update.getNif().getText(), update.getPhoneNumber().getText(), postalCode);
                if ((update.getDateOfBirth().getModel().getValue()) != null) {
                    p.setDateOfBirth(((GregorianCalendar) update.getDateOfBirth().getModel().getValue()).getTime());
                }
                if ((ImageIcon) (update.getPhoto().getIcon()) != null) {
                    p.setPhoto((ImageIcon) update.getPhoto().getIcon());
                }
                update(p);
                update.getReset().doClick();
            }
        } else {
            if (!postalMatchFound) {
                JOptionPane.showMessageDialog(update, "Postal code not valid. Valid format: 12345");
            }
            if (!matchFound) {
                JOptionPane.showMessageDialog(update, "Phone Number not valid. Valid formats: +34 123 456 789, +1-800-555-1234, (123) 456-7890, 123.456.7890 and 123456789.");
            }
        }
    }

    public void handleCount() {

        try {
            count();
        } catch (Exception ex) {
            if (ex instanceof FileNotFoundException || ex instanceof IOException
                    || ex instanceof ParseException || ex instanceof ClassNotFoundException
                    || ex instanceof SQLException || ex instanceof PersistenceException) {
                JOptionPane.showMessageDialog(menu, ex.getMessage() + " Closing application.", "Delete All - People v1.1.0", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }

    public void count() {

        ArrayList<Person> s = readAll();
        int quantity = s.size();
        if (!s.isEmpty()) {

            count = new Count(menu, true, quantity);
            count.setVisible(true);
        } else {

            JOptionPane.showMessageDialog(menu, "There are no people registered yet.", "Count - People v1.1.0", JOptionPane.WARNING_MESSAGE);

        }
    }

    public void handleReadAll() {
        ArrayList<Person> s = readAll();
        if (s.isEmpty()) {
            JOptionPane.showMessageDialog(menu, "There are not people registered yet.", "Read All - People v1.1.0", JOptionPane.WARNING_MESSAGE);
        } else {
            readAll = new ReadAll(menu, true);
            DefaultTableModel model = (DefaultTableModel) readAll.getTable().getModel();
            for (int i = 0; i < s.size(); i++) {
                model.addRow(new Object[i]);
                model.setValueAt(s.get(i).getNif(), i, 0);
                model.setValueAt(s.get(i).getName(), i, 1);
                if (s.get(i).getDateOfBirth() != null) {
                    model.setValueAt(s.get(i).getDateOfBirth().toString(), i, 2);
                } else {
                    model.setValueAt("", i, 2);
                }
                model.setValueAt(s.get(i).getPhoneNumber(), i, 3);
                model.setValueAt(s.get(i).getPostalCode(), i, 4);
                if (s.get(i).getPhoto() != null) {
                    model.setValueAt("yes", i, 5);
                } else {
                    model.setValueAt("no", i, 5);
                }
            }
            readAll.setVisible(true);
        }
    }

    public void handleDeleteAll() {
        if (!loggedUser[2].equalsIgnoreCase("employee")) {

            Object[] options = {"Yes", "No"};
            //int answer = JOptionPane.showConfirmDialog(menu, "Are you sure to delete all people registered?", "Delete All - People v1.1.0", 0, 0);
            int answer = JOptionPane.showOptionDialog(
                    menu,
                    "Are you sure you want to delete all registered people?",
                    "Delete All - People v1.1.0",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[1] // Default selection is "No"
            );

            if (answer == 0) {
                if (!loggedUser[2].equalsIgnoreCase("employee")) {
                    deleteAll();
                    JOptionPane.showMessageDialog(menu, "All persons have been deleted successfully.", "Message", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(menu, "Employees cannot access this option.");

                }

            }
        }
    }

    /**
     * This function inserts the Person object with the requested NIF, if it
     * doesn't exist. If there is any access problem with the storage device,
     * the program stops.
     *
     * @param p Person to insert
     */
    @Override
    public void insert(Person p) {
        try {
            if (dao.read(p) == null) {
                dao.insert(p);
                JOptionPane.showMessageDialog(insert, "Person inserted successfully!");
            } else {
                throw new PersonException(p.getNif() + " is registered and can not "
                        + "be INSERTED.");
            }
        } catch (Exception ex) {
            //Exceptions generated by file read/write access. If something goes 
            // wrong the application closes.
            if (ex instanceof FileNotFoundException || ex instanceof IOException
                    || ex instanceof ParseException || ex instanceof ClassNotFoundException
                    || ex instanceof SQLException || ex instanceof PersistenceException) {
                JOptionPane.showMessageDialog(insert, ex.getMessage() + ex.getClass() + " Closing application.", insert.getTitle(), JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            if (ex instanceof PersonException) {
                JOptionPane.showMessageDialog(insert, ex.getMessage(), insert.getTitle(), JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * This function updates the Person object with the requested NIF, if it
     * doesn't exist. NIF can not be aupdated. If there is any access problem
     * with the storage device, the program stops.
     *
     * @param p Person to update
     */
    @Override
    public void update(Person p) {
        try {
            dao.update(p);
            JOptionPane.showMessageDialog(insert, "Person updated successfully!");
        } catch (Exception ex) {
            //Exceptions generated by file read/write access. If something goes 
            // wrong the application closes.
            if (ex instanceof FileNotFoundException || ex instanceof IOException
                    || ex instanceof ParseException || ex instanceof ClassNotFoundException
                    || ex instanceof SQLException || ex instanceof PersistenceException) {
                JOptionPane.showMessageDialog(update, ex.getMessage() + ex.getClass() + " Closing application.", update.getTitle(), JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }

    /**
     * This function deletes the Person object with the requested NIF, if it
     * exists. If there is any access problem with the storage device, the
     * program stops.
     *
     * @param p Person to read
     */
    @Override
    public void delete(Person p) {
        try {
            if (dao.read(p) != null) {
                dao.delete(p);
            } else {
                throw new PersonException(p.getNif() + " is not registered and can not "
                        + "be DELETED");
            }
        } catch (Exception ex) {
            //Exceptions generated by file, DDBB read/write access. If something  
            //goes wrong the application closes.
            if (ex instanceof FileNotFoundException || ex instanceof IOException
                    || ex instanceof ParseException || ex instanceof ClassNotFoundException
                    || ex instanceof SQLException || ex instanceof PersistenceException) {
                JOptionPane.showMessageDialog(read, ex.getMessage() + ex.getClass() + " Closing application.", "Insert - People v1.1.0", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            if (ex instanceof PersonException) {
                JOptionPane.showMessageDialog(read, ex.getMessage(), "Delete - People v1.1.0", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * This function returns the Person object with the requested NIF, if it
     * exists. Otherwise it returns null. If there is any access problem with
     * the storage device, the program stops.
     *
     * @param p Person to read
     * @return Person or null
     */
    @Override
    public Person read(Person p) {
        try {
            Person pTR = dao.read(p);
            if (pTR != null) {
                return pTR;
            }
        } catch (Exception ex) {

            //Exceptions generated by file read access. If something goes wrong 
            //reading the file, the application closes.
            if (ex instanceof FileNotFoundException || ex instanceof IOException
                    || ex instanceof ParseException || ex instanceof ClassNotFoundException
                    || ex instanceof SQLException || ex instanceof PersistenceException) {
                JOptionPane.showMessageDialog(read, ex.getMessage() + " Closing application.", read.getTitle(), JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
        return null;
    }

    /**
     * This function returns the people registered. If there is any access
     * problem with the storage device, the program stops.
     *
     * @return ArrayList
     */
    @Override
    public ArrayList<Person> readAll() {
        ArrayList<Person> people = new ArrayList<>();
        try {
            people = dao.readAll();
        } catch (Exception ex) {
            if (ex instanceof FileNotFoundException || ex instanceof IOException
                    || ex instanceof ParseException || ex instanceof ClassNotFoundException
                    || ex instanceof SQLException || ex instanceof PersistenceException) {
                JOptionPane.showMessageDialog(readAll, ex.getMessage() + " Closing application.", readAll.getTitle(), JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
        return people;
    }

    /**
     * This function deletes all the people registered. If there is any access
     * problem with the storage device, the program stops.
     */
    @Override
    public void deleteAll() {
        try {
            dao.deleteAll();
        } catch (Exception ex) {
            if (ex instanceof FileNotFoundException || ex instanceof IOException
                    || ex instanceof ParseException || ex instanceof ClassNotFoundException
                    || ex instanceof SQLException || ex instanceof PersistenceException) {
                JOptionPane.showMessageDialog(menu, ex.getMessage() + " Closing application.", "Delete All - People v1.1.0", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }

}
