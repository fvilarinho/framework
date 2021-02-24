package br.com.concepting.framework.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.util.helpers.ProbeOptions;
import br.com.concepting.framework.util.types.ProbeType;

/**
 * Class that defines the implementation of a probe.
 * 
 * @author fvilarinho
 * @version 3.8.2
 * 
 * <pre>Copyright (C) 2007 Innovative Thinking.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses.</pre>
 */
public class Probe extends WebDriverBackedSelenium{
	/**
	 * Initializes a probe.
	 * 
	 * @param options Instance that contains the initialization options.
	 * @return Instance that contains the initialized probe.
	 */
	public static Probe initialize(ProbeOptions options){
		ChromeOptions driverOptions = new ChromeOptions();
		HashMap<String, Object> driverPrefs = new HashMap<String, Object>();
		
		driverPrefs.put("profile.default_content_settings.popups", 0);
		driverPrefs.put("download.default_directory", options.getDownloadDir());
		
		driverOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		driverOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		driverOptions.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS, true);
		driverOptions.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
		driverOptions.setCapability(CapabilityType.SUPPORTS_APPLICATION_CACHE, false);
		driverOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
		driverOptions.setExperimentalOption("prefs", driverPrefs);		
		driverOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"}); 
		
		if(options.getType() != null && options.getType() == ProbeType.MOBILE){
			driverOptions.setExperimentalOption("androidPackage", "com.android.chrome");
			
			if(options.getId() != null && options.getId().length() > 0)
				driverOptions.setExperimentalOption("androidDeviceSerial", options.getId());
		}
		else{
			driverOptions.setAcceptInsecureCerts(true);
			
			if(options.getHeadless() != null && options.getHeadless()){
				driverOptions.addArguments("--headless", 
						                   "--disable-gpu");
			}
			
			if(options.getKiosk() != null && options.getKiosk()){
				driverOptions.addArguments("--kiosk");
				driverOptions.addArguments("--window-position=0,0");
			}
			
			driverOptions.addArguments("--ignore-ssl-errors=yes", 
					                   "--ignore-certificate-errors", 
					                   "--disable-dev-shm-usage", 
					                   "--no-sandbox");
			
			if(options.getViewPortWidth() != null && options.getViewPortHeight() != null){
				Map<String, Object> mobileEmulation = PropertyUtil.instantiate(Map.class);
				Map<String, Object> deviceMetrics = PropertyUtil.instantiate(Map.class);
	
				deviceMetrics.put("width", options.getViewPortWidth());
				deviceMetrics.put("height", options.getViewPortHeight());
				
				mobileEmulation.put("deviceMetrics", deviceMetrics);
	
				driverOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
			}
		}
		
		if(options.getProxy() != null && options.getProxy().getUrl() != null && options.getProxy().getUrl().length() > 0){
			Proxy proxy = new Proxy();
			
			proxy.setHttpProxy(options.getProxy().getUrl());
			proxy.setSslProxy(options.getProxy().getUrl());
			proxy.setSocksProxy(options.getProxy().getUrl());
			
			if(options.getProxy().getUser() != null && options.getProxy().getUser().length() > 0)
				proxy.setSocksUsername(options.getProxy().getUser());
			
			if(options.getProxy().getPassword() != null && options.getProxy().getPassword().length() > 0)
				proxy.setSocksPassword(options.getProxy().getPassword());
			
			driverOptions.setProxy(proxy);
		}
		
		LoggingPreferences logPreferences = new LoggingPreferences();

		logPreferences.enable(LogType.PERFORMANCE, Level.ALL);

		driverOptions.setCapability(CapabilityType.LOGGING_PREFS, logPreferences);
		driverOptions.setCapability("goog:loggingPrefs", logPreferences);

		ChromeDriver driver = new ChromeDriver(driverOptions);
		
		return new Probe(options, driver);
	}

	private ProbeOptions         options        = null;
	private String               url            = null;
	private Boolean              running        = null;
	private Collection<LogEntry> networkMetrics = null;

	private Probe(ProbeOptions options, ChromeDriver driver){
		super(driver, "http://localhost");
		
		setOptions(options);
		show();
	}
	
	/**
	 * Returns the probe initialization options.
	 * 
	 * @return Instance that contains the probe options.
	 */
	public ProbeOptions getOptions(){
		return this.options;
	}

	/**
	 * Defines the probe initialization options.
	 * 
	 * @param options Instance that contains the probe options.
	 */
	private void setOptions(ProbeOptions options){
		this.options = options;

		if(options.getNetwork() != null && 
		   options.getNetwork().getLatency() != null && 
		   options.getNetwork().getDownloadKbps() != null && 
		   options.getNetwork().getUploadKbps() != null)
			applyNetworkCondition(options.getNetwork().getLatency(), 
					              options.getNetwork().getDownloadKbps(), 
					              options.getNetwork().getUploadKbps());
		
		setTimeout(options.getTimeout());
	}
	
	/**
	 * Defines if the probe is running.
	 * 
	 * @param running True/False.
	 */
	private void setRunning(Boolean running){
		this.running = running;
	}

	/**
	 * Indicates if the probe is running.
	 * 
	 * @return True/False.
	 */
	public Boolean isRunning(){
		return this.running;
	}
	
	/**
	 * Defines the network metrics captured after an execution.
	 * 
	 * @param networkMetrics List that contains the network metrics.
	 */
	private void setNetworkMetrics(Collection<LogEntry> networkMetrics){
		this.networkMetrics = networkMetrics;
	}
	
	/**
	 * Returns the network metrics captured after an execution.
	 * 
	 * @return List that contains the network metrics.
	 */
	public Collection<LogEntry> getNetworkMetrics(){
		return this.networkMetrics;
	}

	/**
	 * Defines the default timeout (in seconds) for the commands.
	 * 
	 * @param timeout Numeric value that contains the timeout (in seconds).
	 */
	public void setTimeout(Integer timeout){
		WebDriver driver = getWrappedDriver();
		
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(timeout, TimeUnit.SECONDS);
	}

	/**
	 * Applies a network condition.
	 * 
	 * @param latency Numeric value that contains the latency (in milliseconds).
	 * @param downloadKbps Numeric value that contains the download speed.
	 * @param uploadKbps Numeric value that contains the upload speed.
	 */
	public void applyNetworkCondition(Integer latency, Integer downloadKbps, Integer uploadKbps){
		if(latency != null && downloadKbps != null && uploadKbps != null){
			Map<String, Object> networkConditions = PropertyUtil.instantiate(Map.class);

			networkConditions.put("latency", latency);
			networkConditions.put("download_throughput", downloadKbps * 1024);
			networkConditions.put("upload_throughput", uploadKbps * 1024);
			
			Map<String, Object> map = PropertyUtil.instantiate(Map.class);
			
			map.put("network_conditions", networkConditions);
			
			WebDriver driver = getWrappedDriver();

			try{
				CommandExecutor executor = ((ChromeDriver)driver).getCommandExecutor();
				
				executor.execute(new Command(((ChromeDriver)driver).getSessionId(), "setNetworkConditions", map));
			}
			catch(IOException e){
			}
		}
	}

	/**
	 * @see com.thoughtworks.selenium.DefaultSelenium#deleteAllVisibleCookies()
	 */
	public void deleteAllVisibleCookies(){
		WebDriver driver = getWrappedDriver();
		
		driver.manage().deleteAllCookies();
	}

	/**
	 * Shows the probe.
	 */
	private void show(){
		if(isRunning() == null || !isRunning()){
			setRunning(true);
			deleteAllVisibleCookies();
			windowFocus();
		}
	}

	/**
	 * @see com.thoughtworks.selenium.DefaultSelenium#windowMaximize()
	 */
	public void windowMaximize(){
		try{
			WebDriver driver = getWrappedDriver();
			
			driver.manage().window().maximize();
		}
		catch(Throwable e){
		}
	}

	/**
	 * @see com.thoughtworks.selenium.DefaultSelenium#windowFocus()
	 */
	public void windowFocus(){
		executeScript("window.focus();");
	}

	/**
	 * Returns the page source.
	 * 
	 * @return String that contains the page source.
	 */
	public String getPageSource(){
		WebDriver driver = getWrappedDriver();
		
		return driver.getPageSource();
	}

	/**
	 * Returns the title of the current page.
	 * 
	 * @return String that contains the title.
	 */
	public String getPageTitle(){
		WebDriver driver = getWrappedDriver();
		
		return driver.getTitle();
	}

	/**
	 * @see com.thoughtworks.selenium.DefaultSelenium#stop()
	 */
	public void stop(){
		close();
	}

	/**
	 * @see com.thoughtworks.selenium.DefaultSelenium#close()
	 */
	public void close(){
		setNetworkMetrics(null);
		setRunning(false);

		WebDriver driver = getWrappedDriver();

		try{
			driver.close();
		}
		catch(Throwable e){
		}
		
		try{
			driver.quit();
		}
		catch(Throwable e){			
		}
	}
	
	@Override
	public void mouseMove(String elementId){
		WebElement element = lookupElement(elementId);
		Actions action = new Actions(getWrappedDriver());

		action.moveToElement(element).build().perform();		
	}

	/**
	 * Pauses the navigation for a period.
	 * 
	 * @param value Numeric value that contains the period (in milliseconds).
	 */
	public void sleep(Long value){
		try{
			Thread.sleep(value);
		}
		catch(InterruptedException e){
		}
	}

	/**
	 * Lookup for a element based on a identifier.
	 * 
	 * @param elementId String that contains the identifier.
	 * @return Instance that contains the element.
	 * @throws NoSuchElementException Occurs when was not possible to find the element.
	 */
	public WebElement lookupElement(String elementId) throws NoSuchElementException{
		WebElement element = null;
		WebDriver driver = getWrappedDriver();

		if(elementId.startsWith("id="))
			element = driver.findElement(By.id(StringUtil.replaceAll(elementId, "id=", "")));
		else if(elementId.startsWith("name="))
			element = driver.findElement(By.name(StringUtil.replaceAll(elementId, "name=", "")));
		else if(elementId.startsWith("class="))
			element = driver.findElement(By.className(StringUtil.replaceAll(elementId, "class=", "")));
		else if((elementId.startsWith(".")) || (elementId.startsWith("#")))
			element = driver.findElement(By.cssSelector(elementId));
		else if(elementId.startsWith("linkText="))
			element = driver.findElement(By.linkText(StringUtil.replaceAll(elementId, "linkText=", "")));
		else if(elementId.startsWith("xpath=") || elementId.startsWith("/"))
			element = driver.findElement(By.xpath(StringUtil.replaceAll(elementId, "xpath=", "")));
		else
			element = driver.findElement(By.tagName(elementId));

		if(element == null)
			throw new NoSuchElementException(elementId);

		return element;
	}

	/**
	 * List all elements of the current page based on a identifier.
	 * 
	 * @param elementId String that contains the identifier.
	 * @return List that contains the found elements.
	 * @throws NoSuchElementException Occurs when was not possible to find the elements.
	 */
	public List<WebElement> lookupElements(String elementId) throws NoSuchElementException{
		return lookupElements(null, elementId);
	}

	/**
	 * List all elements of a parent based on a identifier.
	 * 
	 * @param parent Instance that contains the parent.
	 * @param elementId String that contains the identifier.
	 * @return List that contains the found elements.
	 * @throws NoSuchElementException Occurs when was not possible to find the elements.
	 */
	public List<WebElement> lookupElements(WebElement parent, String elementId) throws NoSuchElementException{
		List<WebElement> elements = null;
		WebDriver driver = getWrappedDriver();

		if(parent != null){
			if(elementId.startsWith("id="))
				elements = parent.findElements(By.id(StringUtil.replaceAll(elementId, "id=", "")));
			else if(elementId.startsWith("name="))
				elements = parent.findElements(By.name(StringUtil.replaceAll(elementId, "name=", "")));
			else if(elementId.startsWith("class="))
				elements = parent.findElements(By.className(StringUtil.replaceAll(elementId, "class=", "")));
			else if((elementId.startsWith(".")) || (elementId.startsWith("#")))
				elements = parent.findElements(By.cssSelector(elementId));
			else if(elementId.startsWith("linkText="))
				elements = parent.findElements(By.linkText(StringUtil.replaceAll(elementId, "linkText=", "")));
			else if(elementId.startsWith("xpath=") || elementId.startsWith("/"))
				elements = parent.findElements(By.xpath(elementId));
			else
				elements = parent.findElements(By.tagName(elementId));
		}
		else{
			if(elementId.startsWith("id="))
				elements = driver.findElements(By.id(StringUtil.replaceAll(elementId, "id=", "")));
			else if(elementId.startsWith("name="))
				elements = driver.findElements(By.name(StringUtil.replaceAll(elementId, "name=", "")));
			else if(elementId.startsWith("class="))
				elements = driver.findElements(By.className(StringUtil.replaceAll(elementId, "class=", "")));
			else if((elementId.startsWith(".")) || (elementId.startsWith("#")))
				elements = driver.findElements(By.cssSelector(elementId));
			else if(elementId.startsWith("linkText="))
				elements = driver.findElements(By.linkText(StringUtil.replaceAll(elementId, "linkText=", "")));
			else if(elementId.startsWith("xpath=") || elementId.startsWith("/"))
				elements = driver.findElements(By.xpath(elementId));
			else
				elements = driver.findElements(By.tagName(elementId));
		}

		if(elements == null || elements.size() == 0)
			throw new NoSuchElementException(elementId);

		return elements;
	}

	/**
	 * Submits a form.
	 * 
	 * @param formId String that contains the identifier of the form.
	 */
	public void submit(String formId){
		WebElement element = lookupElement(formId);
		
		element.submit();
	}

	/**
	 * Waits until the page loads or the timeout is reached.
	 * 
	 * @return Numeric value that contains the fully loaded time (in milliseconds).
	 */
	public Long waitForPageToLoad(){
		return waitForPageToLoad(this.options.getTimeout());
	}
	
	/**
	 * Waits until the page loads or the timeout is reached.
	 * 
	 * @param timeout Numeric value that contains the timeout (in seconds).
	 * @return Numeric value that contains the fully loaded time (in milliseconds).
	 */
	public Long waitForPageToLoad(Integer timeout){
		WebDriver driver = getWrappedDriver();

		new WebDriverWait(driver, timeout).until(new ExpectedCondition<Boolean>(){
	        public Boolean apply(WebDriver driver){
	        	try{
		            JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		            
		            return (Boolean)javascriptExecutor.executeScript("return (jQuery.active == 0);");
	        	}
	        	catch(Throwable e){
	        		return true;
	        	}
	        }
	    });
		
		new WebDriverWait(driver, timeout).until(new ExpectedCondition<Boolean>(){
	        public Boolean apply(WebDriver driver){
	        	try{
		            JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
		            
		            return (Boolean)javascriptExecutor.executeScript("return (document.readyState == 'complete');");
	        	}
	        	catch(Throwable e){
	        		return true;
	        	}
	        }
	    });
		
		int count = 0;
		int waitTime = 0;
		Long fullyLoadedTime = null;
	
		while(count < (timeout * 1000)){
			Collection<LogEntry> buffer = driver.manage().logs().get(LogType.PERFORMANCE).getAll();
			
			if(buffer == null || buffer.size() == 0){
				if(fullyLoadedTime == null)
					fullyLoadedTime = executeScript("return (new Date().getTime() - window.performance.timing.navigationStart);");
				
				if(waitTime >= (ProbeOptions.DEFAULT_WAIT_TIME * 1000))
					break;
			}
			else{
				fullyLoadedTime = null;
				waitTime = 0;
			}
			
			if(this.options.getCaptureNetworkMetrics() != null && this.options.getCaptureNetworkMetrics() && buffer != null && buffer.size() > 0){
				if(this.networkMetrics == null)
					this.networkMetrics = PropertyUtil.instantiate(Collection.class);
	
				this.networkMetrics.addAll(buffer);
			}
			
			try{
				Thread.sleep(100);
			}
			catch(InterruptedException e){
				break;
			}
			
			count += 100;
			waitTime += 100;
		}
		
		if(fullyLoadedTime == null)
			fullyLoadedTime = (timeout * 1000l);
		
		return fullyLoadedTime;
	}

	/**
	 * Waits until a element is present in the current page or the timeout is reached.
	 * 
	 * @param elementId String that contains the identifier of the element.
	 */
	public void waitForElementPresent(String elementId){
		waitForElementPresent(elementId, this.options.getTimeout());
	}

	/**
	 * Waits until a element is present in the current page or the timeout is reached.
	 * 
	 * @param elementId String that contains the identifier of the element.
	 * @param timeout Numeric value that contains the timeout (in seconds).
	 */
	public void waitForElementPresent(String elementId, Integer timeout){
		WebElement element = null;
		int count = 0;

		do{
			element = lookupElement(elementId);

			if(element != null)
				return;

			try{
				Thread.sleep(1000);

				count += 1;
			}
			catch(InterruptedException e){
				return;
			}
		}
		while(count < timeout);

		throw new TimeoutException(timeout.toString());
	}

	/**
	 * Waits until a text is present in the current page or the timeout is reached.
	 * 
	 * @param text String that contains the text.
	 */
	public void waitForTextPresent(String text){
		waitForTextPresent(text, this.options.getTimeout());
	}

	/**
	 * Waits until a text is present in the current page or the timeout is reached.
	 * 
	 * @param text String that contains the text.
	 * @param timeout Numeric value that contains the timeout (in seconds).
	 */
	public void waitForTextPresent(String text, Integer timeout){
		int count = 0;

		do{
			if(this.getPageSource().contains(text))
				return;

			try{
				Thread.sleep(1000);

				count += 1;
			}
			catch(InterruptedException e){
				return;
			}
		}
		while(count < timeout);

		throw new TimeoutException(timeout.toString());
	}

	/**
	 * Waits until a element is visible in the current page or the timeout is reached.
	 * 
	 * @param elementId String that contains the identifier of the element.
	 */
	public void waitForElementVisible(String elementId){
		waitForElementVisible(elementId, this.options.getTimeout());
	}

	/**
	 * Waits until a element is visible in the current page or the timeout is reached.
	 * 
	 * @param elementId String that contains the identifier of the element.
	 * @param timeout Numeric value that contains the timeout (in seconds).
	 */
	public void waitForElementVisible(String elementId, Integer timeout){
		WebElement element = null;
		int count = 0;

		do{
			element = lookupElement(elementId);

			if(element != null && element.isDisplayed())
				return;

			try{
				Thread.sleep(1000);

				count += 1;
			}
			catch(InterruptedException e){
				return;
			}
		}
		while(count < timeout);

		throw new TimeoutException(timeout.toString());
	}

	/**
	 * Waits until a element is editable in the current page or the timeout is reached.
	 * 
	 * @param elementId String that contains the identifier of the element.
	 */
	public void waitForElementEditable(String elementId){
		waitForElementEditable(elementId, this.options.getTimeout());
	}

	/**
	 * Waits until a element is editable in the current page or the timeout is reached.
	 * 
	 * @param elementId String that contains the identifier of the element.
	 * @param timeout Numeric value that contains the timeout (in seconds).
	 */
	public void waitForElementEditable(String elementId, Integer timeout){
		WebElement element = null;
		int count = 0;

		do{
			element = lookupElement(elementId);

			if(element != null && element.isDisplayed() && element.isEnabled() && (element.getAttribute("readonly") == null || element.getAttribute("readonly").equals("false")))
				return;

			try{
				Thread.sleep(1000);

				count += 1;
			}
			catch(InterruptedException e){
				return;
			}
		}
		while(count < timeout);

		throw new TimeoutException(timeout.toString());
	}

	/**
	 * @see com.thoughtworks.selenium.DefaultSelenium#isCookiePresent(java.lang.String)
	 */
	public boolean isCookiePresent(String name){
		WebDriver driver = getWrappedDriver();
		Cookie cookie = driver.manage().getCookieNamed(name);

		return (cookie != null);
	}

	/**
	 * Waits until a cookie is present in the current page or the timeout is reached.
	 * 
	 * @param name String that contains the cookie name.
	 */
	public void waitForCookiePresent(String name){
		waitForCookiePresent(name, this.options.getTimeout());
	}

	/**
	 * Waits until a cookie is present in the current page or the timeout is reached.
	 * 
	 * @param name String that contains the cookie name.
	 * @param timeout Numeric value that contains the timeout (in seconds).
	 */
	public void waitForCookiePresent(String name, Integer timeout){
		int count = 0;

		do{
			if(isCookiePresent(name))
				return;

			try{
				Thread.sleep(1000);

				count += 1;
			}
			catch(InterruptedException e){
				return;
			}
		}
		while(count < timeout);

		throw new TimeoutException(timeout.toString());
	}

	/**
	 * Waits until an alert is present in the current page or the timeout is reached.
	 */
	public void waitForAlertPresent(){
		waitForAlertPresent(this.options.getTimeout());
	}

	/**
	 * Waits until an alert is present in the current page or the timeout is reached.
	 * 
	 * @param timeout Numeric value that contains the timeout (in seconds).
	 */
	public void waitForAlertPresent(Integer timeout){
		WebDriver driver = getWrappedDriver();
		int count = 0;

		do{
			try{
				if(driver.switchTo().alert() != null)
					return;
			}
			catch(Throwable e){
			}

			try{
				Thread.sleep(1000);

				count += 1;
			}
			catch(InterruptedException e){
				return;
			}
		}
		while(count < timeout);

		throw new TimeoutException(timeout.toString());
	}

	/**
	 * Waits until a title is present in the current page or the timeout is reached.
	 * 
	 * @param title String that contains the title.
	 */
	public void waitForTitle(String title){
		waitForTitle(title, this.options.getTimeout());
	}

	/**
	 * Waits until a title is present in the current page or the timeout is reached.
	 * 
	 * @param title String that contains the title.
	 * @param timeout Numeric value that contains the timeout (in seconds).
	 */
	public void waitForTitle(String title, Integer timeout){
		int count = 0;

		do{
			try{
				if(title.equals(getPageTitle()))
					return;
			}
			catch(Throwable e){
			}

			try{
				Thread.sleep(1000);

				count += 1;
			}
			catch(InterruptedException e){
				return;
			}
		}
		while(count < timeout);

		throw new TimeoutException(timeout.toString());
	}

	/**
	 * @see com.thoughtworks.selenium.DefaultSelenium#focus(java.lang.String)
	 */
	public void focus(String elementId){
		WebElement element = lookupElement(elementId);
		Actions action = new Actions(getWrappedDriver());
		
		action.moveToElement(element).build().perform();
	}

	/**
	 * @see com.thoughtworks.selenium.DefaultSelenium#click(java.lang.String)
	 */
	public void click(String elementId){
		WebElement element = lookupElement(elementId);
		Actions action = new Actions(getWrappedDriver());
		
		action.click(element).build().perform();
	}

	/**
	 * Click and wait the page load.
	 * 
	 * @param elementId String that contains the identifier of the element.
	 */
	public void clickAndWait(String elementId){
		click(elementId);

		waitForPageToLoad();
	}

	/**
	 * Click and wait the page load.
	 * 
	 * @param elementId String that contains the identifier of the element.
	 * @param timeout Numeric value that contains the timeout (in seconds).
	 */
	public void clickAndWait(String elementId, Integer timeout){
		click(elementId);

		waitForPageToLoad(timeout);
	}

	/**
	 * Press the TAB key.
	 */
	public void pressTab(){
		Actions action = new Actions(getWrappedDriver());

		action.sendKeys(Keys.chord(Keys.TAB)).build().perform();
	}

	/**
	 * Press the ENTER key.
	 */
	public void pressEnter(){
		Actions action = new Actions(getWrappedDriver());

		action.sendKeys(Keys.chord(Keys.ENTER)).build().perform();
	}

	/**
	 * Type a text.
	 * 
	 * @param value String that contains the text.
	 */
	public void type(String value){
		if(value != null && value.length() > 0){
			Actions action = new Actions(getWrappedDriver());

			action.sendKeys(value).build().perform();
		}
	}

	/**
	 * @see com.thoughtworks.selenium.DefaultSelenium#type(java.lang.String, java.lang.String)
	 */
	public void type(String elementId, String value){
		focus(elementId);

		WebElement element = lookupElement(elementId);

		element.sendKeys(value);
	}

	/**
	 * Executes a script.
	 * 
	 * @param <O> Class that defines the result of the execution. 
	 * @param script String that contains the script (Javascript).
	 * @return Instance that contains the result of the execution.
	 */
	@SuppressWarnings("unchecked")
	public <O> O executeScript(String script){
		JavascriptExecutor executor = (JavascriptExecutor)getWrappedDriver();

		return (O)executor.executeScript(script);
	}

	/**
	 * @see com.thoughtworks.selenium.DefaultSelenium#runScript(java.lang.String)
	 */
	public void runScript(String script){
		executeScript(script);
	}

	/**
	 * Runs a script and wait the page load.
	 * 
	 * @param script String that contains the script (Javascript).
	 */
	public void runScriptAndWait(String script){
		runScriptAndWait(script, this.options.getTimeout());
	}

	/**
	 * Runs a script and wait the page load.
	 * 
	 * @param script String that contains the script (Javascript).
	 * @param timeout Numeric value that contains the timeout (in seconds).
	 */
	public void runScriptAndWait(String script, Integer timeout){
		runScript(script);

		waitForPageToLoad(timeout);
	}

	/**
	 * @see com.thoughtworks.selenium.DefaultSelenium#open(java.lang.String)
	 */
	public void open(String url){
		WebDriver driver = getWrappedDriver();
		
		if(this.url == null || this.url.length() == 0) {
			this.url = url;
			
			driver.navigate().to(url);
		}
		else
			driver.navigate().to(this.url.concat(url));
	}

	/**
	 * Executes a list of commands.
	 * 
	 * @param commands List that contains the desired commands.
	 * @throws InternalErrorException Occurs when was not possible to execute the commands.
	 */
	@SuppressWarnings("unchecked")
	public void execute(Collection<Map<String, Object>> commands) throws InternalErrorException{
		try{
			if(commands != null && commands.size() > 0){
				for(Map<String, ?> command : commands){
					Collection<Map<String, ?>> arguments = (Collection<Map<String, ?>>)command.get("arguments");
					Method method = null;
					String name = (String)command.get("name");

					if(arguments != null && arguments.size() > 0){
						Class<?>[] types = new Class<?>[arguments.size()];
						Object[] values = new Object[arguments.size()];
						Object value = null;
						int cont = 0;

						for(Map<String, ?> argument : arguments){
							value = argument.get("value");
							
							if(value != null){
								types[cont] = value.getClass();
								values[cont] = value;
	
								cont++;
							}
						}

						method = ChromeDriver.class.getMethod(name, types);

						if(method != null)
							method.invoke(this, values);
					}
					else{
						method = ChromeDriver.class.getMethod(name);

						if(method != null)
							method.invoke(this);
					}
				}
			}
		}
		catch(InvocationTargetException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException e){
			throw new InternalErrorException(e);
		}
	}
	
	@SuppressWarnings("javadoc")
	public static void main(String[] args) throws Throwable{
		Date now = new Date();//DateTimeUtil.add(new Date(), 1, DateFieldType.DAYS);
		String dateFilter = DateTimeUtil.format(now, "dd/MM/yyyy");
		ProbeOptions options = new ProbeOptions();
		
		options.setDownloadDir("/Users/fvilarinho");
		options.setHeadless(true);
		
		Probe probe = Probe.initialize(options);
		
		probe.open("https://webnfe.e-datacenter.nddigital.com.br/eFormsColdweb/Account/Login");
		probe.waitForPageToLoad();
		probe.type("id=UserName", "AKAMAI");
		probe.type("id=Password", "Akamai1234");
		probe.clickAndWait("class=t-webbutton");
		probe.executeScript("document.getElementById('IDE_DEMI_FROM').value = '".concat(dateFilter).concat("';"));
		probe.executeScript("document.getElementById('IDE_DEMI_TO').value = '".concat(dateFilter).concat("';"));
		probe.clickAndWait("id=buttonSubmit");
		
		String value = probe.lookupElement("class=t-status-text").getText();
		
		int pos = value.indexOf("of ");
		int itemsPerPage = 10;
		int totalItems = 0;
		
		if(pos >= 0)
			totalItems = Integer.valueOf(value.substring(pos + 3));
		
		int pages = (totalItems / itemsPerPage) + ((totalItems % itemsPerPage) > 0 ? 1 : 0);
		int i = 0;
		int page = 0;
		List<WebElement> elements = null;
		
		if(pages > 0){
			ObjectMapper mapper = PropertyUtil.getMapper();
			List<String> nfes = null;
			
			try{
				nfes = mapper.readValue(FileUtil.fromBinaryFile("/Users/fvilarinho/processedNfes.json"), new TypeReference<List<String>>(){});
			}
			catch(IOException e){
			}
			
			while(true){
				if(elements == null)
					elements = probe.lookupElements("name=id");
				
				WebElement element = elements.get(i);
				String nfe = element.getAttribute("value");
				
				if(nfes == null)
					nfes = PropertyUtil.instantiate(Constants.DEFAULT_LIST_CLASS);
				
				if(!nfes.contains(nfe)){
					nfes.add(nfe);
					
					element.click();
		
					WebElement download = probe.lookupElement("id=actionDownload");
		
					download.click();
					
					probe.waitForPageToLoad();
					
					WebElement option = probe.lookupElement("id=chkDanfeDacte");
					
					option.click();
				
					option = probe.lookupElement("id=chkProc");
					
					option.click();
					
					probe.pressTab();
					probe.pressTab();
					
					option = probe.executeScript("return document.getElementById('chkProcEvent');");
					
					if(option != null)
						probe.pressTab();
					
					probe.pressEnter();
					
					Thread.sleep(1000l);
					
					probe.pressTab();
					probe.pressEnter();
					
					probe.waitForPageToLoad();
					
					elements = probe.lookupElements("name=id");
					element = elements.get(i);
					element.click();
				}
				
				i++;
			
				if(i == elements.size()){
					elements = probe.lookupElements("class=t-link");
					
					Boolean found = false;
					
					for(WebElement next : elements){
						if(next.getText().equals("next")) {
							next.click();
							
							probe.waitForPageToLoad();
							
							found = true;
							
							break;
						}
					}
					
					if(found){
						page++;
	
						if(page >= pages)
							break;
						
						elements = null;
						i = 0;
					}
					else
						break;
				}
			}
			
			FileUtil.toBinaryFile("/Users/fvilarinho/processedNfes.json", mapper.writeValueAsBytes(nfes));
		}
		
		probe.close();
	}
}