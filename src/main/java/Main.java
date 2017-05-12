
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.photos.responses.GetResponse;
import java.awt.Desktop;
import static java.lang.Thread.sleep;
import java.net.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import java.util.regex.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Main {

    public static TransportClient transportClient = HttpTransportClient.getInstance();
    public static VkApiClient vk = new VkApiClient(transportClient);
    public static final String URL = "https://oauth.vk.com/authorize?client_id=5849973&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=787460&response_type=code&v=5.62";
    public static final int APP_ID = 5849973;
    public static final long GROUP_ID = -97611353;
    public static final String ALBUM_ID = "218061508";
    public static final String CLIENT_SECRET = "1qVkwRRKhmF2i6ABqBbf";
    public static final String REDIRECT_URI = "https://oauth.vk.com/blank.html";
    public static final String ACCEPTENCE_BUTTON_XPATH = "//*[@id=\"oauth_wrap_content\"]/div[3]/div/div[1]/button[1]";
    public static final String LOGIN_BUTTON_XPATH = "//*[@id=\"install_allow\"]";
    public static final String LOGIN_INPUT_XPATH = "//*[@id=\"login_submit\"]/div/div/input[6]";
    public static final String PASSWORD_INPUT_XPATH = "//*[@id=\"login_submit\"]/div/div/input[7]";
    public static final String LOGIN_INPUT = "***********";
    public static final String PASSWORD_INPUT = "**********";
    public static final String BODY_XPATH = "/html/body/b[1]";
    public static WebDriver driver;
    public static final String DESC_XPATH = "//*[@id=\"pv_desc\"]/div";
    public static final String DATE_XPATH = "/*[@id=\"pv_date_info\"]/span";
    public static final String IMG_XPATH = "//*[@id=\"pv_photo\"]/img";
    public static final String PIC_URL = "https://vk.com/rip_pages?z=photo-97611353_375406755%2Falbum-97611353_218061508";
    public static String code;
    public static final Main m = new Main();
    public static NewJFrame jFrame;
    public static final String DATE_PATTERN = "date=((?:.|\\n)*?),";
    public static final String DESC_PATTERN = "text='((?:.|\\n)*?)',";
    public static Connection connection = null;
    public static String album;
    public static int countOfPhotos;
    public static UserActor actor;
    public static int photoCountAlbum10;
//public static final String TEXT_PATTERN = "Photo(.*?)\}";

//public static final String LOGIN_BUTTON_XPATH = "//*[@id=\"quick_login_button\"]";//"//*[@id=\"install_allow\"]";
//public static final String LOGIN_INPUT_XPATH = "//*[@id=\"quick_email\"]";//"//*[@id=\"login_submit\"]/div/div/input[6]";
//public static final String PASSWORD_INPUT_XPATH = "//*[@id=\"quick_pass\"]";//"//*[@id=\"login_submit\"]/div/div/input[7]";
    public static void main(String[] args) throws SQLException {
        jFrame = new NewJFrame();
        jFrame.setVisible(true);
        actor = getToken();

        try {
            int access = vk.account().getAppPermissions(actor, 147793554).execute();
            System.out.println(access);

//        GetResponse getResponse = vk.photos().get(actor)
//                .ownerId(-97611353)
//                .albumId("218061508")
//                .count(323)
//                .rev(Boolean.FALSE)
//                .execute();
//        String response = getResponse.toString();
//        System.out.println(response);
//        ArrayList<String> picList = new ArrayList<>();
//        Pattern picPattern = Pattern.compile("Photo\\{((?:.|\\n)*?)\\}");
//        Pattern descPattern = Pattern.compile(DESC_PATTERN);
//        Pattern datePattern = Pattern.compile(DATE_PATTERN);
//        
//        System.out.println(picPattern.toString());
//        Matcher picMatcher = picPattern.matcher(response);
//        
//        int count = 0;
//        while(picMatcher.find()) {
//            System.out.println("found: " + count + " : "
//                    + picMatcher.start() + " - " + picMatcher.end());
//            picList.add(response.substring(picMatcher.start(),picMatcher.end()));
//             System.out.println(picList.get(count));
//             count++;
//        }
//        
//        
//        System.out.println(picList.size());
//        ArrayList<Person> deadPeople = new ArrayList<>();
//            try {
//                Class.forName("org.sqlite.JDBC");
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            
//        connection = DriverManager.getConnection("jdbc:sqlite:/Applications/deadPeople.db");
//        
//        
//        for (int i=0; i<picList.size();i++){
//            Person person = new Person();
//            Matcher descMatcher = descPattern.matcher(picList.get(i));
//            Matcher dateMatcher = datePattern.matcher(picList.get(i));
//            insert(picList.get(i));
//            descMatcher.find();
//            dateMatcher.find();
//            person.setDesc(descMatcher.group(1));
//            person.setDate(dateMatcher.group(1));
//            //picList.get(i).substring(dateMatcher.start(),dateMatcher.end())
//            deadPeople.add(person);
//        }
//        
//        
//        System.out.println("Описание смерти 5 человека: " + deadPeople.get(4).getDesc()+ "время смерти: " + deadPeople.get(4).getDate());
//    
        } catch (ApiException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClientException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void dowloadAndInsert(UserActor actor, String album, int countOfPhotos) {
        GetResponse getResponse = null;
        System.out.println("AlbumID: " + album + "Count: " + countOfPhotos);
        try {
            if (countOfPhotos > 500) {
                getResponse = vk.photos().get(actor)
                        .ownerId(-97611353)
                        .albumId(album)
                        .count(countOfPhotos)
                        .rev(Boolean.FALSE)
                        .execute();
            } else {
                getResponse = vk.photos().get(actor)
                        .ownerId(-97611353)
                        .albumId(album)
                        .count(countOfPhotos)
                        .rev(true)
                        .execute();
            }
            String response = getResponse.toString();
            ArrayList<String> picList = new ArrayList<>();
            Pattern picPattern = Pattern.compile("Photo\\{((?:.|\\n)*?)\\}");
            Matcher picMatcher = picPattern.matcher(response);

            int count = 0;
            while (picMatcher.find()) {
//                    System.out.println("found: " + count + " : "
//                            + picMatcher.start() + " - " + picMatcher.end());
                picList.add(response.substring(picMatcher.start(), picMatcher.end()));
                count++;
            }
            if (countOfPhotos < 500) {
                Collections.reverse(Arrays.asList(picList));
            }

            System.out.println("Колличество фотографий: " + picList.size());
            ArrayList<Person> deadPeople = new ArrayList<>();
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                connection = DriverManager.getConnection("jdbc:sqlite:/Applications/deadPeople.db");
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (int i = 0; i < picList.size(); i++) {
                Person person = new Person();
                insert(picList.get(i));
                deadPeople.add(person);
            }
        } catch (ApiException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClientException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insert(String text) {
        try {
            String sql = "INSERT INTO pics(text) VALUES(?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, text);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//    public static ArrayList<Person> getPersonArray(int length) {
//        WebDriverWait wait = new WebDriverWait(driver, 15);
//        ArrayList<Person> personList = new ArrayList<Person>();
//        for (int i=0;i<length;i++){
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(DESC_XPATH)));
//            String date = driver.findElement(By.xpath(DATE_XPATH)).getText();
//            String desc = driver.findElement(By.xpath(DESC_XPATH)).getText();
//            Person person = new Person();
//            person.setDate(date);
//            person.setDesc(desc);
//            personList.set(i, person);
//            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(IMG_XPATH)));
//            driver.findElement(By.xpath(IMG_XPATH)).click();
//        }
//        driver.quit();
//        return personList;
//    }
    public static UserActor getToken() {
        synchronized (m) {
            getCode();
            try {
                m.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        UserAuthResponse authResponse;
        try {
            authResponse = vk.oauth()
                    .userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URI, code)
                    .execute();
            System.out.print(authResponse.getAccessToken().toString());
            UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());

            return actor;
        } catch (ApiException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClientException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void openWebPage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        //Desktop desktop = Desktop.getDesktop();
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void openWebPage(URL url) {
        try {
            openWebPage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void getCode() {
// WebDriver driver = new FirefoxDriver();
        URI uri;
        try {
            uri = new URI(URL);
            openWebPage(uri);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

//        driver.get(URL);
//        driver.findElement(By.xpath(LOGIN_INPUT_XPATH)).sendKeys(LOGIN_INPUT);
//        driver.findElement(By.xpath(PASSWORD_INPUT_XPATH)).sendKeys(PASSWORD_INPUT);
//        driver.findElement(By.xpath(LOGIN_BUTTON_XPATH)).click();
//     
//        WebDriverWait wait = new WebDriverWait(driver, 5);
//        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ACCEPTENCE_BUTTON_XPATH)));
//       
//        
////        if (existsElement(ACCEPTENCE_BUTTON_XPATH)){
////            driver.findElement(By.xpath(ACCEPTENCE_BUTTON_XPATH)).click();
////        }
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(BODY_XPATH)));
//        System.out.print(driver.getCurrentUrl());
//        String code = driver.getCurrentUrl().split("code=")[1];
//        driver.quit();
    }

    private static boolean existsElement(String xpath) {
        try {
            driver.findElement(By.xpath(xpath));
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public static void login(String url) {
        driver = new FirefoxDriver();
        driver.get(url);
        driver.findElement(By.xpath(LOGIN_INPUT_XPATH)).sendKeys(LOGIN_INPUT);
        try {
            sleep(5);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        driver.findElement(By.xpath(PASSWORD_INPUT_XPATH)).sendKeys(PASSWORD_INPUT);

        driver.findElement(By.xpath(LOGIN_BUTTON_XPATH)).click();
    }

}
