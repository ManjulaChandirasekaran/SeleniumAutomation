package Manjula;
import java.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.junit.Assert;

public class SeleniumClass {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("hello");

		
        System.setProperty("webdriver.chrome.driver", "C:\\Selenium Web Driver\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver= new ChromeDriver();
		//Mazimize current window
		
		driver.manage().window().maximize();

		//Delay execution for 5 seconds to view the maximize operation
		Thread.sleep(5000);
		
		driver.get("http://amazon.com");
		
		String expectedTitle ="Amazon.com. Spend less. Smile more."; 
		String actualTitle = driver.getTitle();
		System.out.println(driver.getTitle());
		Assert.assertEquals(expectedTitle, actualTitle);
		
		//Click menu - Get Started
		
		driver.findElement(By.linkText("Best Sellers")).click();
		
		
		//Delay execution for 5 seconds to view the maximize operation
		Thread.sleep(5000);
				
		System.out.println(driver.getTitle());
		
		driver.findElement(By.linkText("Electronics")).click();
		//Delay execution for 5 seconds to view the maximize operation
		Thread.sleep(5000);
				
		//Assert.assertEquals(exp,act);
		System.out.println(driver.getTitle());
		
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("iphone");
		driver.findElement(By.id("nav-search-submit-button")).click();
		//Delay execution for 5 seconds to view the maximize operation
		Thread.sleep(5000);
		
		WebElement paginationLink = null;
		
		int counter = 0;
		int pageCount = 0;
		String priceString = "";
		String titleString = "";
		List<WebElement> searchResults = null;
		
		 Select sortBy=new Select(driver.findElement(By.xpath("//select[@name='s']")));
	     sortBy.selectByValue("exact-aware-popularity-rank");
	     
	     Thread.sleep(5000);
			
		do 
		{	
			searchResults = driver.findElements(By.xpath("//div[@data-csa-c-type=\"item\"]"));
			System.out.println("Let us begin scraping the next page!!!");
			
			for (WebElement item : searchResults) {
				
				try
				{
					WebElement titleElement = item.findElement(By.xpath(".//div[@class=\"a-section a-spacing-none a-spacing-top-small s-title-instructions-style\"]//span[@class=\"a-size-base-plus a-color-base a-text-normal\"]"));
					titleString = titleElement.getAttribute("innerHTML");
				} 				
				catch(org.openqa.selenium.NoSuchElementException exception)
				{
					System.out.println("Title does not exist!");
				} 
				
				try
				{
					WebElement priceElement = item.findElement(By.xpath(".//span[@class=\"a-price\"]/span"));
					priceString = priceElement.getAttribute("innerHTML");
				}
				catch(org.openqa.selenium.NoSuchElementException exception)				
				{
					System.out.println("Price does not exist!");
				}			
				
				
				System.out.println("Item = " + titleString);
				System.out.println("Price =$ " + priceString);
			
				}
			
			System.out.println("Page #" + counter+++" products scraped. Let us go to the next page");
			
		
			//WebDriverWait wait = new WebDriverWait(driver, 30);
			
			try{
				paginationLink = driver.findElement(By.xpath("//a[@class=\"s-pagination-item s-pagination-next s-pagination-button s-pagination-separator\"]"));
				if(paginationLink.isEnabled()) {
					pageCount++;
					if (pageCount == 10)
						break;
					paginationLink.click();
					Thread.sleep(5000);				
				}
				
					
			}			
			catch(org.openqa.selenium.NoSuchElementException exception)			
			{
				System.out.println("Pagination Element does not exist!");				
				counter = -1;//no more pages to scrape so exit
			} 
			
				
		} while ((counter>=0));	
	}

}
