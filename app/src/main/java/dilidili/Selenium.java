//package dilidili;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.firefox.FirefoxDriver;
//
//import java.util.List;
//import java.util.Random;
//
///**
// * @author wjf
// * @date 2016-09-18下午 4:29
// */
//
//public class Selenium
//{
//    private static final long waitTime = 3000;
//    private static final int randomWaitTime = 3000;
//    public static void main(String args[]) throws Exception
//    {
//        Random random = new Random(System.currentTimeMillis());//火狐浏览器
//        WebDriver driver = new FirefoxDriver();
//        driver.get(
//                "http://www.yidianzixun.com/home?page=channel&keyword=%E8%8A%B1%E8%8C%B6");//等待页面动态加载完毕
//        Thread.sleep(waitTime + random.nextInt(randomWaitTime));
//        int needLoadPage = 2;
//        for (int i = 1; i < needLoadPage; i++)
//        {
//            driver.findElement(By.className("show-more")).click();
//            //等待页面动态加载完毕
//            Thread.sleep(waitTime + random.nextInt(randomWaitTime));
//        }
//        try
//        {
//            List<WebElement> elementList = driver.findElements(By.className("article"));
//            for (WebElement element : elementList)
//            {
//                System.out.println("标题：" + element.getText());
//            }
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }finally
//        {
//            driver.close();
//        }
//
//
//    }
//}
