package mycompany;



import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Standalonetest2 {
	
	public static void main(String[] args) throws InterruptedException {
		
		//while adding the tentNG dependency in pom.xml, remove the scope tag otherwise some jarfiles wont work
		
		String productName="ZARA COAT 3";
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(2));
		driver.get("https://rahulshettyacademy.com/client/");
		
		driver.findElement(By.id("userEmail")).sendKeys("dineshudgire@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Dummy@222");
		driver.findElement(By.id("login")).click();
		
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		WebElement prod=products.stream().filter(product-> 
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
        Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[routerlink*='cart']"))).click();
		
		List<WebElement> AllCartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		Assert.assertTrue(AllCartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equals(productName)));
		//in above line > while writing Assert, write A in capital or else error
		driver.findElement(By.cssSelector(".totalRow button")).click();
		driver.findElement(By.cssSelector("[placeholder*='Country']")).sendKeys("india");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-item:nth-child(3)"))).click();
		driver.findElement(By.cssSelector(".btnn")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector(".hero-primary")).getText(), "THANKYOU FOR THE ORDER.");
		
	}

}
