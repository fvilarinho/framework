package br.com.concepting.framework.util;

import br.com.concepting.framework.constants.Constants;
import br.com.concepting.framework.exceptions.InternalErrorException;
import br.com.concepting.framework.util.helpers.ProbeOptions;
import br.com.concepting.framework.util.types.ProbeType;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import org.openqa.selenium.*;
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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

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
 * the Free Software Foundation, either version 3 of the License or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <a href="http://www.gnu.org/licenses"></a>.</pre>
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
        Map<String, Object> driverPrefs = PropertyUtil.instantiate(Constants.DEFAULT_MAP_CLASS);

        if(driverPrefs != null) {
            driverPrefs.put("profile.default_content_settings.popups", 0);
            driverPrefs.put("download.default_directory", options.getDownloadDir());
        }
        
        driverOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        driverOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        driverOptions.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
        driverOptions.setCapability(CapabilityType.SUPPORTS_APPLICATION_CACHE, false);
        driverOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
        driverOptions.setExperimentalOption("prefs", driverPrefs);
        driverOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        
        if(options.getType() != null && options.getType() == ProbeType.MOBILE){
            driverOptions.setExperimentalOption("androidPackage", "com.android.chrome");
            
            if(options.getId() != null && !options.getId().isEmpty())
                driverOptions.setExperimentalOption("androidDeviceSerial", options.getId());
        }
        else{
            driverOptions.setAcceptInsecureCerts(true);
            
            if(options.getHeadless())
                driverOptions.addArguments("--headless", "--disable-gpu");

            if(options.getKiosk()){
                driverOptions.addArguments("--kiosk");
                driverOptions.addArguments("--window-position=0,0");
            }

            driverOptions.addArguments("--remote-allow-origins=*", "--ignore-ssl-errors=yes", "--ignore-certificate-errors", "--disable-dev-shm-usage", "--no-sandbox");
            
            if(options.getViewPortWidth() >0 && options.getViewPortHeight() > 0){
                Map<String, Object> mobileEmulation = PropertyUtil.instantiate(Map.class);
                Map<String, Object> deviceMetrics = PropertyUtil.instantiate(Map.class);

                if(deviceMetrics != null) {
                    deviceMetrics.put("width", options.getViewPortWidth());
                    deviceMetrics.put("height", options.getViewPortHeight());
                }

                if(mobileEmulation != null)
                    mobileEmulation.put("deviceMetrics", deviceMetrics);
                
                driverOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
            }
        }
        
        if(options.getProxy() != null && options.getProxy().getUrl() != null && !options.getProxy().getUrl().isEmpty()){
            Proxy proxy = new Proxy();
            
            proxy.setHttpProxy(options.getProxy().getUrl());
            proxy.setSslProxy(options.getProxy().getUrl());
            proxy.setSocksProxy(options.getProxy().getUrl());
            
            if(options.getProxy().getUser() != null && !options.getProxy().getUser().isEmpty())
                proxy.setSocksUsername(options.getProxy().getUser());
            
            if(options.getProxy().getPassword() != null && !options.getProxy().getPassword().isEmpty())
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
    
    private ProbeOptions options = null;
    private String url = null;
    private boolean running = false;
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
        
        if(options.getNetwork() != null && options.getNetwork().getLatency() != null && options.getNetwork().getDownloadKbps() != null && options.getNetwork().getUploadKbps() != null)
            applyNetworkCondition(options.getNetwork().getLatency(), options.getNetwork().getDownloadKbps(), options.getNetwork().getUploadKbps());
        
        setTimeout(options.getTimeout());
    }
    
    /**
     * Defines if the probe is running.
     *
     * @param running True/False.
     */
    private void setRunning(boolean running){
        this.running = running;
    }
    
    /**
     * Indicates if the probe is running.
     *
     * @return True/False.
     */
    public boolean isRunning(){
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
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeout));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(timeout));
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

            if(networkConditions != null) {
                networkConditions.put("latency", latency);
                networkConditions.put("download_throughput", downloadKbps * 1024);
                networkConditions.put("upload_throughput", uploadKbps * 1024);
            }
            
            Map<String, Object> map = PropertyUtil.instantiate(Map.class);

            if(map != null)
                map.put("network_conditions", networkConditions);
            
            WebDriver driver = getWrappedDriver();
            
            try{
                CommandExecutor executor = ((ChromeDriver) driver).getCommandExecutor();
                
                executor.execute(new Command(((ChromeDriver) driver).getSessionId(), "setNetworkConditions", map));
            }
            catch(IOException ignored){
            }
        }
    }

    @Override
    public void deleteAllVisibleCookies(){
        WebDriver driver = getWrappedDriver();
        
        driver.manage().deleteAllCookies();
    }
    
    /**
     * Shows the probe.
     */
    private void show(){
        if(!isRunning()){
            setRunning(true);
            deleteAllVisibleCookies();
            windowFocus();
        }
    }

    @Override
    public void windowMaximize(){
        try{
            WebDriver driver = getWrappedDriver();
            
            driver.manage().window().maximize();
        }
        catch(Throwable ignored){
        }
    }

    @Override
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

    @Override
    public void stop(){
        close();
    }

    @Override
    public void close(){
        setNetworkMetrics(null);
        setRunning(false);
        
        WebDriver driver = getWrappedDriver();
        
        try{
            driver.close();
        }
        catch(Throwable ignored){
        }
        
        try{
            driver.quit();
        }
        catch(Throwable ignored){
        }
    }
    
    @Override
    public void mouseMove(String elementId) throws NoSuchElementException, TimeoutException{
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
        catch(InterruptedException ignored){
        }
    }
    
    public WebElement lookupElement(String elementId) throws NoSuchElementException, TimeoutException{
        WebElement element;
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
    
    public List<WebElement> lookupElements(String elementId) throws NoSuchElementException, TimeoutException{
        return lookupElements(null, elementId);
    }
    
    public List<WebElement> lookupElements(WebElement parent, String elementId) throws NoSuchElementException, TimeoutException{
        List<WebElement> elements;
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
        
        if(elements == null || elements.isEmpty())
            throw new NoSuchElementException(elementId);
        
        return elements;
    }
    
    public void submit(String formId) throws NoSuchElementException, TimeoutException{
        WebElement element = lookupElement(formId);
        
        element.submit();
    }
    
    public void waitForPageToLoad() throws TimeoutException{
        waitForPageToLoad(this.options.getTimeout());
    }
    
    public void waitForPageToLoad(Integer timeout) throws TimeoutException{
        WebDriver driver = getWrappedDriver();
        
        new WebDriverWait(driver, Duration.ofSeconds(timeout)).until((ExpectedCondition<Object>) d -> {
            try{
                JavascriptExecutor javascriptExecutor = (JavascriptExecutor) d;

                if(javascriptExecutor != null)
                    return (Boolean) javascriptExecutor.executeScript("return (jQuery.active === 0);");
            }
            catch(Throwable ignored){
            }

            return true;
        });
        
        new WebDriverWait(driver, Duration.ofSeconds(timeout)).until((ExpectedCondition<Boolean>) d -> {
            try{
                JavascriptExecutor javascriptExecutor = (JavascriptExecutor) d;

                if(javascriptExecutor != null)
                    return (Boolean) javascriptExecutor.executeScript("return (document.readyState === 'complete');");
            }
            catch(Throwable ignored){
            }

            return true;
        });
        
        int count = 0;
        int waitTime = 0;
        Long fullyLoadedTime = null;
        
        while(count < (timeout * 1000)){
            Collection<LogEntry> buffer = driver.manage().logs().get(LogType.PERFORMANCE).getAll();
            
            if(buffer == null || buffer.isEmpty()){
                if(fullyLoadedTime == null)
                    fullyLoadedTime = executeScript("return (new Date().getTime() - window.performance.timing.navigationStart);");
                
                if(waitTime >= (ProbeOptions.DEFAULT_WAIT_TIME * 1000))
                    break;
            }
            else{
                fullyLoadedTime = null;
                waitTime = 0;
            }
            
            if(this.options.getCaptureNetworkMetrics() && buffer != null && !buffer.isEmpty()){
                if(this.networkMetrics == null)
                    this.networkMetrics = PropertyUtil.instantiate(Collection.class);

                if(this.networkMetrics != null)
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
    }

    public void waitForElementPresent(String elementId) throws NoSuchElementException, TimeoutException{
        waitForElementPresent(elementId, this.options.getTimeout());
    }

    public void waitForElementPresent(String elementId, Integer timeout) throws NoSuchElementException, TimeoutException{
        int count = 0;
        WebElement element;

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

    public void waitForTextPresent(String text) throws TimeoutException{
        waitForTextPresent(text, this.options.getTimeout());
    }
    
    public void waitForTextPresent(String text, Integer timeout) throws TimeoutException{
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
    
    public void waitForElementVisible(String elementId) throws NoSuchElementException, TimeoutException{
        waitForElementVisible(elementId, this.options.getTimeout());
    }
    
    public void waitForElementVisible(String elementId, Integer timeout) throws NoSuchElementException, TimeoutException{
        int count = 0;
        WebElement element;

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
    
    public void waitForElementEditable(String elementId) throws NoSuchElementException, TimeoutException{
        waitForElementEditable(elementId, this.options.getTimeout());
    }

    public void waitForElementEditable(String elementId, Integer timeout) throws NoSuchElementException, TimeoutException{
        int count = 0;
        WebElement element;

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

    @Override
    public boolean isCookiePresent(String name){
        WebDriver driver = getWrappedDriver();
        Cookie cookie = driver.manage().getCookieNamed(name);
        
        return (cookie != null);
    }
    
    public void waitForCookiePresent(String name) throws TimeoutException{
        waitForCookiePresent(name, this.options.getTimeout());
    }
    
    public void waitForCookiePresent(String name, Integer timeout) throws TimeoutException{
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
    
    public void waitForAlertPresent() throws TimeoutException{
        waitForAlertPresent(this.options.getTimeout());
    }

    public void waitForAlertPresent(Integer timeout) throws TimeoutException{
        WebDriver driver = getWrappedDriver();
        int count = 0;
        
        do{
            try{
                if(driver.switchTo().alert() != null)
                    return;
            }
            catch(Throwable ignored){
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
    
    public void waitForTitle(String title) throws TimeoutException{
        waitForTitle(title, this.options.getTimeout());
    }

    public void waitForTitle(String title, Integer timeout) throws TimeoutException{
        int count = 0;
        
        do{
            try{
                if(title.equals(getPageTitle()))
                    return;
            }
            catch(Throwable ignored){
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

    public void focus(String elementId) throws NoSuchElementException, TimeoutException{
        WebElement element = lookupElement(elementId);
        Actions action = new Actions(getWrappedDriver());
        
        action.moveToElement(element).build().perform();
    }
    
    public void click(String elementId) throws NoSuchElementException, TimeoutException{
        WebElement element = lookupElement(elementId);
        Actions action = new Actions(getWrappedDriver());
        
        action.click(element).build().perform();
    }
    
    public void clickAndWait(String elementId) throws NoSuchElementException, TimeoutException{
        click(elementId);
        
        waitForPageToLoad();
    }
    
    public void clickAndWait(String elementId, Integer timeout) throws NoSuchElementException, TimeoutException{
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
        if(value != null && !value.isEmpty()){
            Actions action = new Actions(getWrappedDriver());
            
            action.sendKeys(value).build().perform();
        }
    }
    
    public void type(String elementId, String value) throws NoSuchElementException, TimeoutException{
        focus(elementId);
        
        WebElement element = lookupElement(elementId);
        
        element.sendKeys(value);
    }
    
    @SuppressWarnings("unchecked")
    public <O> O executeScript(String script) throws TimeoutException{
        JavascriptExecutor executor = (JavascriptExecutor) getWrappedDriver();
        
        return (O) executor.executeScript(script);
    }
    
    public void runScript(String script) throws TimeoutException{
        executeScript(script);
    }
    
    public void runScriptAndWait(String script) throws TimeoutException{
        runScriptAndWait(script, this.options.getTimeout());
    }
    
    public void runScriptAndWait(String script, Integer timeout) throws TimeoutException{
        runScript(script);
        
        waitForPageToLoad(timeout);
    }
    
    public void open(String url) throws TimeoutException{
        WebDriver driver = getWrappedDriver();
        
        if(this.url == null || this.url.isEmpty()){
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
            if(commands != null && !commands.isEmpty()){
                for(Map<String, ?> command: commands){
                    String name = (String) command.get("name");
                    Collection<Map<String, ?>> arguments = (Collection<Map<String, ?>>) command.get("arguments");
                    Method method;

                    if(arguments != null && !arguments.isEmpty()){
                        Class<?>[] types = new Class<?>[arguments.size()];
                        Object[] values = new Object[arguments.size()];
                        int cont = 0;
                        
                        for(Map<String, ?> argument: arguments){
                            Object value = argument.get("value");
                            
                            if(value != null){
                                types[cont] = value.getClass();
                                values[cont] = value;
                                
                                cont++;
                            }
                        }
                        
                        method = ChromeDriver.class.getMethod(name, types);

                        method.invoke(this, values);
                    }
                    else{
                        method = ChromeDriver.class.getMethod(name);

                        method.invoke(this);
                    }
                }
            }
        }
        catch(InvocationTargetException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException e){
            throw new InternalErrorException(e);
        }
    }}